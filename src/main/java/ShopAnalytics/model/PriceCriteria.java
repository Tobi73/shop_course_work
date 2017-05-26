package ShopAnalytics.model;

import lombok.Data;

import java.util.List;

/**
 * Created by andreyzaytsev on 18.05.17.
 */

@Data
public class PriceCriteria {

    private List<ShopAnalytics.model.Pair> priceCriteria;
    private Integer defaultQuantity;

    public PriceCriteria() {};

    public PriceCriteria(List<ShopAnalytics.model.Pair> priceCriteria, Integer defaultQuantity){
        this.priceCriteria = priceCriteria;
        this.defaultQuantity = defaultQuantity;
    }

    public boolean isPassingCriteria(int price){
        for(ShopAnalytics.model.Pair priceCriteriaUnit : priceCriteria){
            if(price < priceCriteriaUnit.getKey()){
                return true;
            }
        }
        return false;
    }

    public int foundQuantity(int price){
        for(ShopAnalytics.model.Pair priceCriteriaUnit : priceCriteria){
            if(price < priceCriteriaUnit.getKey()){
                return priceCriteriaUnit.getValue();
            }
        }
        return -1;
    }





}
