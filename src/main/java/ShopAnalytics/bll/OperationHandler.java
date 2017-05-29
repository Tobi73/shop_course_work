package ShopAnalytics.bll;

import ShopAnalytics.model.*;
import ShopAnalytics.repository.*;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.*;
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

    @Transactional
    public boolean sellProduct(Long productId, Long customerINN, long userId) {
        try{
            if(!isPossibleToSell(productId)) return false;
            processSell(productId, customerINN, userId);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public boolean purchaseProduct(Long productId, Long customerINN, long userId, int price){
        try{
            if(!isPossibleToPurchase(productId)) return false;
            processPurchase(productId, customerINN, userId, price);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public boolean purchaseProducts(List<Transaction> transactionsToProcess,
                                            long userId) throws Exception {
        try{
            for(Transaction transaction : transactionsToProcess){
                if(!isPossibleToPurchase(transaction.getProduct().getId())) throw  new Exception("Insufficient amount of product");
            }
            for(Transaction transaction : transactionsToProcess){
                Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
                transaction.setDate(currentDate);
                transaction.setTransactionType(transTypes.findByName("buy"));
                transaction.setUser(users.findOne(userId));
                transactions.save(transaction);
            }
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public boolean sellProducts(List<Pair<Long, Long>> productsToSell, long customerINN,
                                    long userId) throws Exception {
        try{
            for(Pair<Long, Long> product : productsToSell){
                if(!isPossibleToSell(product.getKey())) throw  new Exception("Insufficient amount of product");
            }
            for(Pair<Long, Long> product : productsToSell){
                for(int i = 0; i < product.getValue(); i++){
                    sellProduct(product.getKey(), customerINN, userId);
                }
            }
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public List<StatInfo> recieveStatistics(long productId) throws Exception {
        try{
            List<StatInfo> mappedList = new ArrayList<>();
            List<Object[]> rawData = transactions.sortByMonth(productId);
            for(Object[] rawObject : rawData){
                mappedList.add(new StatInfo((Integer)rawObject[0], (Long)rawObject[1]));
            }
            return mappedList;
        } catch (Exception e){
            throw e;
        }
    }

    @Data
    private class StatInfo{

        private Integer month;
        private Long quantity;

        public StatInfo(Integer month, Long quantity){
            this.month = month;
            this.quantity = quantity;
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

    private void processPurchase(Long productId, Long customerINN, long userId, int price){
        Product product = products.findOne(productId);
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        Transaction newTransaction = new Transaction(currentDate, price,
                businessEntities.findOne(customerINN), users.findOne(userId), transTypes.findByName("buy"),
                product);
        transactions.save(newTransaction);
    }

    private  void  processSell(Long productId, Long customerINN, long userId){
        Product product = products.findOne(productId);
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        Transaction newTransaction = new Transaction(currentDate, product.getSellPrice(),
                businessEntities.findOne(customerINN), users.findOne(userId), transTypes.findByName("sell"),
                product);
        transactions.save(newTransaction);
    }

}
