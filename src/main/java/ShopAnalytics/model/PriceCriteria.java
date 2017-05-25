package ShopAnalytics.model;

import javafx.util.Pair;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by andreyzaytsev on 18.05.17.
 */

@Data
public class PriceCriteria {

    private List<ShopAnalytics.model.Pair> priceCriteria;
    private Integer defaultPrice;

    public PriceCriteria() {};

    public PriceCriteria(List<ShopAnalytics.model.Pair> priceCriteria, Integer defaultPrice){
        this.priceCriteria = priceCriteria;
        this.defaultPrice = defaultPrice;
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
