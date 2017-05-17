package ShopAnalytics.BLL;

import ShopAnalytics.Model.Product;
import ShopAnalytics.Model.Transaction;
import ShopAnalytics.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Calendar;

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
    public boolean sellProduct(String productId, int customerINN, long userId){
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
    public boolean sellProduct(String productId, long userId, int customerINN, Date date){
        try{
            if(!isPossibleToSell(productId)) return false;
            Product product = products.findOne(productId);
            Transaction newTransaction = new Transaction(date, product.getSellPrice(),
                    null, users.findOne(userId), transTypes.findByName("sell"),
                    product);
            transactions.save(newTransaction);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    public boolean isPossibleToSell(String productId){
        try{
            return products.exists(productId) && products.findOne(productId).getQuantity() > 0;
        } catch (Exception e){
            throw e;
        }
    }

}
