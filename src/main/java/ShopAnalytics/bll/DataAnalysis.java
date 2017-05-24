package ShopAnalytics.bll;

import ShopAnalytics.model.*;
import ShopAnalytics.repository.ProductDao;
import ShopAnalytics.repository.TransactionDao;
import javafx.util.Pair;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                return new AnalyticsData(null, priceCriteria.foundQuantity(price), foundProduct);
            }
            if(!priceCriteria.isPassingCriteria(price)){
                return new AnalyticsData(null, priceCriteria.getDefaultPrice(), foundProduct);
            }
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

    @Transactional
    public List<BusinessPartnerNode> buildBusinessPartnerGraphRelation(Long productId) throws Exception {
        List<Object[]> rawData = transactions.findToBuildGraph();
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
                    partnerNode.addRelation(new Pair<>(other.getBusiness_entity_inn(),
                            advantage));
                }
            }
            partnerNodes.add(partnerNode);
        }
//        for(RetailerInfo info: retailerInfos){
//            info.
//        }

//        Product foundProduct = products.findOne(productId);
//        if(foundProduct == null){
//            throw new Exception("Could not find product");
//        }
//        List<Transaction> foundTransaction = transactions.findAllByProduct(foundProduct);
//        if(foundTransaction == null || foundTransaction.size() == 0){
//            throw new Exception("Could not find related transaction");
//        }
//        List<CompanyToProduct> compToProd = Collections.emptyList();
//        for(Transaction transaction: foundTransaction){
//            if(!"buy".equals(transaction.getTransactionType().getName())){
//                continue;
//            }
//            BusinessEntity relatedPartner = transaction.getBusinessEntity();
//            for(CompanyToProduct ctp : compToProd){
//                if(ctp.getCompanyId().equals(relatedPartner.getInn())){
//                    ctp.setAmountOfProduct(ctp.getAmountOfProduct() + 1);
//                    ctp.setTotalPrice(ctp.getTotalPrice() + transaction.getPrice());
//                    continue;
//                }
//                compToProd.add(new CompanyToProduct(relatedPartner.getInn(), relatedPartner.getName()));
//            }
//        }
//        List<BusinessPartnerNode> partners = Collections.emptyList();
//        for(CompanyToProduct ctp : compToProd){
//            BusinessPartnerNode partnerNode = new BusinessPartnerNode(ctp.companyName, ctp.companyId);
//            double productAveragePrice = ctp.getTotalPrice() / ctp.getAmountOfProduct();
//            for(CompanyToProduct insideCtp: compToProd){
//                if(ctp.equals(insideCtp)){
//                    continue;
//                }
//                double productAveragePriceToCompare = insideCtp.getTotalPrice() / insideCtp.getAmountOfProduct();
//                double relation = productAveragePrice / productAveragePriceToCompare;
//                partnerNode.addRelation(new Pair<>(insideCtp.companyId, relation));
//            }
//            partners.add(partnerNode);
//        }
//        return partners;
        return partnerNodes;
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
    private class RetailerInfo{

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

    private class CompanyToProduct{

        private Long companyId;
        private int totalPrice;
        private int amountOfProduct;
        private String companyName;

        public CompanyToProduct(Long companyId, String companyName){
            this.companyId = companyId;
            this.totalPrice = 0;
            this.amountOfProduct = 0;
            this.companyName = companyName;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getAmountOfProduct() {
            return amountOfProduct;
        }

        public void setAmountOfProduct(int amountOfProduct) {
            this.amountOfProduct = amountOfProduct;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Long companyId) {
            this.companyId = companyId;
        }
    }


}
