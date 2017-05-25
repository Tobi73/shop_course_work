package ShopAnalytics.model;

import javafx.beans.property.IntegerProperty;
import lombok.Data;

/**
 * Created by gman0_000 on 24.05.2017.
 */
@Data
public class Pair {

    private Integer key;
    private Integer value;

    public Pair() {}

    public Pair(Integer key, Integer value){
        this.key = key;
        this.value = value;
    }
}
