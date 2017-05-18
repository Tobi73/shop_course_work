package ShopAnalytics.Model;

import lombok.Data;

import java.util.Map;

/**
 * Created by andreyzaytsev on 18.05.17.
 */

@Data
public class PriceCriteria {

    private Map<Integer, Integer> priceCriteria;
    private Integer defaultPrice;

    public PriceCriteria(Map<Integer, Integer> priceCriteria, Integer defaultPrice){
        this.priceCriteria = priceCriteria;
        this.defaultPrice = defaultPrice;
    }

    public boolean isPassingCriteria(int price){
        for(Map.Entry<Integer, Integer> priceCriteriaUnit : priceCriteria.entrySet()){
            if(price < priceCriteriaUnit.getKey()){
                return true;
            }
        }
        return false;
    }

    public int foundQuantity(int price){
        for(Map.Entry<Integer, Integer> priceCriteriaUnit : priceCriteria.entrySet()){
            if(price < priceCriteriaUnit.getKey()){
                return priceCriteriaUnit.getValue();
            }
        }
        return -1;
    }





}
