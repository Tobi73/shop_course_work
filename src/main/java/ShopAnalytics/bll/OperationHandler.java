package ShopAnalytics.bll;

import ShopAnalytics.model.Product;
import ShopAnalytics.model.Role;
import ShopAnalytics.model.Transaction;
import ShopAnalytics.model.User;
import ShopAnalytics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Created by gman0_000 on 18.05.2017.
 */

@Controller
@Transactional
public class OperationHandler {

    @Autowired
    private ProductDao products;
    @Autowired
    private TransactionDao transactions;
    @Autowired
    private BusinessEntityDao businessEntities;
    @Autowired
    private UserDao users;
    @Autowired
    private TransactionTypeDao transTypes;

    @Autowired
    private CrudRepository<User, Long> use;
    @Autowired
    private CrudRepository<Role, Long> rol;

    public <T> Iterable<T> getBody(CrudRepository<T, Long> repo){
        return repo.findAll();
    }

    private class ResponseBody<T>{

        T body;

        T returnBody(){
            return body;
        }

        public ResponseBody(T body){
            this.body = body;
        }

    }

    @Transactional
    public boolean sellProduct(Long productId, Long customerINN, long userId) {
        System.out.println();
        switch ("role"){
            case "role":
                List<Role> iter = (List<Role>)this.getBody(rol);
                System.out.println(iter);
                ResponseBody<List<Role>> body = new ResponseBody<>(iter);
                break;
            case "user":
                List<User> iterUser = (List<User>)this.getBody(use);
                System.out.println(iterUser);
                break;
        }


        try{
            if(!isPossibleToSell(productId)) return false;
            Product product = products.findOne(productId);
            Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
            Transaction newTransaction = new Transaction(currentDate, product.getSellPrice(),
                    businessEntities.findOne(customerINN), users.findOne(userId), transTypes.findByName("sell"),
                    product);
            transactions.save(newTransaction);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public boolean purchaseProduct(Long productId, Long customerINN, long userId){
        try{
            if(!isPossibleToPurchase(productId)) return false;
            Product product = products.findOne(productId);
            Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
            Transaction newTransaction = new Transaction(currentDate, product.getSellPrice(),
                    businessEntities.findOne(customerINN), users.findOne(userId), transTypes.findByName("buy"),
                    product);
            transactions.save(newTransaction);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    public boolean isPossibleToPurchase(Long productId){
        try{
            return products.exists(productId);
        } catch (Exception e){
            throw e;
        }
    }

    public boolean isPossibleToSell(Long productId){
        try{
            return products.exists(productId);
        } catch (Exception e){
            throw e;
        }
    }

}
