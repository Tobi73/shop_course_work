package ShopAnalytics.bll;

import ShopAnalytics.model.*;
import ShopAnalytics.repository.ProductDao;
import ShopAnalytics.repository.TransactionDao;
import javafx.util.Pair;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * Created by gman0_000 on 14.05.2017.
 */
@Controller
@Transactional
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
                return new AnalyticsData(null, priceCriteria.foundQuantity(price), foundProduct, price);
            }
            if(!priceCriteria.isPassingCriteria(price)){
                return new AnalyticsData(null, priceCriteria.getDefaultQuantity(), foundProduct);
            }
        }
        if(foundProduct != null){
            Pair<BusinessEntity, Integer> bestOption = determineBestBusinessPartner(foundTransactions);
            if(priceCriteria.isPassingCriteria(bestOption.getValue())){
                return new AnalyticsData(bestOption.getKey(),
                        priceCriteria.foundQuantity(bestOption.getValue()), foundProduct, bestOption.getValue());
            }
            if(!priceCriteria.isPassingCriteria(bestOption.getValue())){
                return new AnalyticsData(null, priceCriteria.getDefaultQuantity(), foundProduct);
            }
        }
        return null;
    }

    @Transactional
    public List<BusinessPartnerNode> buildBusinessPartnerGraphRelation(Long productId) throws Exception {
        List<Object[]> rawData = transactions.findToBuildGraph(productId);
        List<RetailerInfo> retailerInfos = mapRetailerInfo(rawData);
        List<BusinessPartnerNode> partnerNodes = new ArrayList<>();
        for(RetailerInfo info : retailerInfos){
            BusinessPartnerNode partnerNode = new BusinessPartnerNode(info.getCompanyName(), info.getBusiness_entity_inn());
            for(RetailerInfo other: retailerInfos){
                if(info.getBusiness_entity_inn() == other.getBusiness_entity_inn()){
                    continue;
                }
                double companyAveragePrice = info.getSum() / info.getCount();
                double otherCompanyAveragePrice = other.getSum() / other.getCount();
                if(companyAveragePrice < otherCompanyAveragePrice){
                    double advantage = 100 - companyAveragePrice / (otherCompanyAveragePrice / 100);
                    partnerNode.addRelation(new Pair<>(other.companyName,
                            advantage));
                }
            }
            partnerNodes.add(partnerNode);
        }
        return partnerNodes;
    }

    @Transactional
    public List<Transaction> selectTransactionByRule(TransactionSelectionRule rule) throws Exception {
        List<Transaction> allTransactions = (List<Transaction>) transactions.findAll();
        List<Transaction> selectedTransactions = new ArrayList<>();
        for(Transaction transaction : allTransactions){
            if(isValid(rule, transaction)) selectedTransactions.add(transaction);
        }
        return selectedTransactions;
    }

    public boolean isValid(TransactionSelectionRule rule, Transaction transaction) throws Exception {
        for(Rule ruleUnit : rule.getRules()){
            if(!isPassingRule(ruleUnit, transaction)) return false;
        }
        return true;
    }

    public boolean isPassingRule(Rule rule, Transaction transaction) throws Exception {
        switch (rule.getOperation()){
            case "=":
                if(rule.getStringValue() == null || rule.getStringValue().length() == 0){
                    BiPredicate<Integer, Integer> equalPredicate = (x, y) -> x.equals(y);
                    return passRule(equalPredicate, rule.getNumValues(), rule.getField(), transaction);
                }
                BiPredicate<String, String> stringEqualPredicate = (x, y) -> x.equals(y);
                return passRule(stringEqualPredicate, rule.getStringValue(), rule.getField(), transaction);
            case ">":
                BiPredicate<Integer, Integer> greaterPredicate = (x, y) -> x > y;
                return passRule(greaterPredicate, rule.getNumValues(), rule.getField(), transaction);
            case "<":
                BiPredicate<Integer, Integer> lessPredicate = (x, y) -> x < y;
                return passRule(lessPredicate, rule.getNumValues(), rule.getField(), transaction);
            case ">=":
                BiPredicate<Integer, Integer> greaterEqualPredicate = (x, y) -> y >= y;
                return passRule(greaterEqualPredicate, rule.getNumValues(), rule.getField(), transaction);
            case "<=":
                BiPredicate<Integer, Integer> lessEqualPredicate = (x, y) -> y <= y;
                return passRule(lessEqualPredicate, rule.getNumValues(), rule.getField(), transaction);
            default:
                throw new Exception("Wrong operation");
        }
    }

    public <T> boolean passRule(BiPredicate predicate, T rule, String field, Transaction transaction) throws Exception {
        switch (field){
            case "businessEntity":
                return predicate.test(transaction.getBusinessEntity().getName(), rule);
            case "product":
                return predicate.test(transaction.getProduct().getName(), rule);
            case "price":
                return predicate.test(transaction.getPrice(), rule);
            case "transactionType":
                return predicate.test(transaction.getTransactionType().getName(), rule);
            default:
                throw new Exception("Wrong field");
        }
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

    public List<RetailerInfo> mapRetailerInfo(List<Object[]> rawData){
        List<RetailerInfo> retailerInfos = new ArrayList<>();
        for(Object[] rawItem : rawData){
            retailerInfos.add(new RetailerInfo((String) rawItem[1],(long)rawItem[0],
                    (long)rawItem[2],(long)rawItem[3]));
        }
        return retailerInfos;
    }

    @Data
    private class RetailerInfo implements Serializable {

        private long business_entity_inn;
        private long sum;
        private long count;
        private String companyName;

        public RetailerInfo() {}

        public RetailerInfo(String companyName, long business_entity_inn, long sum, long count){
            this.business_entity_inn = business_entity_inn;
            this.sum = sum;
            this.count = count;
            this.companyName = companyName;
        }
    }


}
