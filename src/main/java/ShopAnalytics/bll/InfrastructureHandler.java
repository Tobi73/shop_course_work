package ShopAnalytics.bll;

import ShopAnalytics.model.*;
import ShopAnalytics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Controller
@Transactional
public class InfrastructureHandler {

    @Autowired
    private BusinessEntityDao businessEntities;
    @Autowired
    private AnalyticsDataDao analyticsData;
    @Autowired
    private ProductDao products;
    @Autowired
    private TransactionDao transactions;
    @Autowired
    private TransactionTypeDao transactionTypes;
    @Autowired
    private UserDao users;
    @Autowired
    private RoleDao roles;
    private Map<Predicate, BiConsumer> functions;

    public InfrastructureHandler(){
        functions = new HashMap<>();
        Predicate<?> roleType = x -> x instanceof Role;
        BiConsumer<Role, Long> roleInsert = (Role y, Long id) -> roles.save(y);
        functions.put(roleType, roleInsert);

        Predicate<?> prodType = x -> x instanceof Product;
        BiConsumer<Product, Long> prodInsert = (Product y, Long id) -> products.save(y);
        functions.put(prodType, prodInsert);

        Predicate<?> userType = x -> x instanceof User;
        BiConsumer<User, Long> userInsert = (User y, Long id) -> {
            if(id != null){
                y.setRole(roles.findOne(id));
            }
            users.save(y);
        };
        functions.put(userType, userInsert);

        Predicate<?> businessEntityType = x -> x instanceof BusinessEntity;
        BiConsumer<BusinessEntity, Long> businessEntityInsert = (BusinessEntity y, Long id) -> businessEntities.save(y);
        functions.put(businessEntityType, businessEntityInsert);

        Predicate<?> transTypeType = x -> x instanceof TransactionType;
        BiConsumer<TransactionType, Long> transactionTypeInsert = (TransactionType y, Long id) -> transactionTypes.save(y);
        functions.put(transTypeType, transactionTypeInsert);
    }

    public ResponseBody<?> handleControllerRequest(String entityName,
                                                   String operationName,
                                                   RequestBody params) throws Exception {
        switch (entityName){
            case "role":
                return handleOperation(roles, operationName, params);
            case "user":
                return handleOperation(users, operationName, params);
            case "transaction":
                return handleOperation(transactions, operationName, params);
            case "product":
                return handleOperation(products, operationName, params);
            case "analyticsData":
                return this.<AnalyticsDataDao, AnalyticsData>handleOperation(analyticsData, operationName, params);
            case "businessEntity":
                return handleOperation(businessEntities, operationName, params);
            case "transactionType":
                return handleOperation(transactionTypes, operationName, params);
            default:
                throw new Exception();
        }
    }

    @Transactional
    private  <T extends CrudRepository, R> ResponseBody<?> handleOperation(T repository,
                                               String operationName,
                                               RequestBody params) throws Exception {
        switch (operationName){
            case "getOne":
                if(params.getId() != null){
                    return new ResponseBody<>(repository.findOne(params.getId()));
                } else throw new Exception();
            case "getAll":
                return new ResponseBody<>((List<T>) repository.findAll());
            case "delete":
                if(params.getId() != null){
                    repository.delete(params.getId());
                    return new ResponseBody<>(true);
                } else throw new Exception();
            default:
                throw new Exception();
        }
    }

    @Transactional
    public Boolean authenticate(String username, String password){
        User foundUser = users.findByLogin(username);
        if(foundUser == null){
            return false;
        }
        if(!foundUser.getPassword().equals(password)){
            return false;
        }
        return true;
    }

    private class ResponseBody<T>{

        T body;

        public ResponseBody(T body){
            this.body = body;
        }

        public void setBody(T body){
            this.body = body;
        }

        public T getBody(){
            return body;
        }

    }

    public <T> void insertNewObject(T newObject, Long id){
        for(Predicate predicate: functions.keySet()){
            if(predicate.test(newObject)){
                functions.get(predicate).accept(newObject, id);
                return;
            }
        }
    }

}
