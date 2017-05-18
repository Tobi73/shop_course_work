package ShopAnalytics.BLL;

import ShopAnalytics.Model.*;
import ShopAnalytics.Repository.ProductDao;
import ShopAnalytics.Repository.TransactionDao;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gman0_000 on 14.05.2017.
 */
public class DataAnalysis {

    @Autowired
    private TransactionDao transactions;
    @Autowired
    private ProductDao products;



    @Transactional
    public AnalyticsData determineBestPurchaseOption(String productName, int price, PriceCriteria priceCriteria){
        Product foundProduct = products.findByNameIgnoreCase(productName);
        List<Transaction> foundTransactions = transactions.findAllByProduct(foundProduct);
        if(foundProduct == null || foundTransactions.size() == 0){
            if(priceCriteria.isPassingCriteria(price)){
                return new AnalyticsData(null, priceCriteria.foundQuantity(price), foundProduct);
            }
            if(!priceCriteria.isPassingCriteria(price)){
                return new AnalyticsData(null, priceCriteria.getDefaultPrice(), foundProduct);
            }
//            if(price < 5000){
//                return new AnalyticsData(null, 10, foundProduct);
//            }
//            if(price >= 5000 && price < 10000){
//                return new AnalyticsData(null, 5, foundProduct);
//            }
//            if (price >= 10000){
//                return new AnalyticsData(null, 1, foundProduct);
//            }
        }
        if(foundProduct != null){
            Pair<BusinessEntity, Integer> bestOption = determineBestBusinessPartner(foundTransactions);
            if(priceCriteria.isPassingCriteria(bestOption.getValue())){
                return new AnalyticsData(bestOption.getKey(), priceCriteria.foundQuantity(bestOption.getValue()), foundProduct);
            }
            if(!priceCriteria.isPassingCriteria(bestOption.getValue())){
                return new AnalyticsData(null, priceCriteria.getDefaultPrice(), foundProduct);
            }
        }
        return null;

    }

    public Pair<BusinessEntity, Integer> determineBestBusinessPartner(List<Transaction> transactions){
        int minPrice = transactions.get(0).getPrice();
        BusinessEntity bestPartner = transactions.get(0).getBusinessEntity();
        for(Transaction transaction : transactions){
            if(transaction.getPrice() < minPrice){
                minPrice = transaction.getPrice();
                bestPartner = transaction.getBusinessEntity();
            }
        }
        return new Pair<>(bestPartner, minPrice);
    }


}
