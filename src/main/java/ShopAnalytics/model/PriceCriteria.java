package ShopAnalytics.model;

import javafx.util.Pair;
import lombok.Data;

import java.util.List;

/**
 * Created by andreyzaytsev on 18.05.17.
 */

@Data
public class PriceCriteria {

    private List<Pair<Integer, Integer>> priceCriteria;
    private Integer defaultPrice;

    public PriceCriteria(List<Pair<Integer, Integer>> priceCriteria, Integer defaultPrice){
        this.priceCriteria = priceCriteria;
        this.defaultPrice = defaultPrice;
    }

    public boolean isPassingCriteria(int price){
        for(Pair<Integer, Integer> priceCriteriaUnit : priceCriteria){
            if(price < priceCriteriaUnit.getKey()){
                return true;
            }
        }
        return false;
    }

    public int foundQuantity(int price){
        for(Pair<Integer, Integer> priceCriteriaUnit : priceCriteria){
            if(price < priceCriteriaUnit.getKey()){
                return priceCriteriaUnit.getValue();
            }
        }
        return -1;
    }





}
