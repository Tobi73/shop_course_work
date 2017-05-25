package ShopAnalytics.model;

import lombok.Data;

/**
 * Created by gman0_000 on 25.05.2017.
 */
@Data
public class Rule {

    private int numValues;
    private String stringValue;
    private String operation;
    private String field;

    public Rule() {}
}
