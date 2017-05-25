package ShopAnalytics.model;

import lombok.Data;

/**
 * Created by gman0_000 on 25.05.2017.
 */
@Data
public class TransactionSelectionRule {

    private Rule[] rules;
    private String ruleName;

    public TransactionSelectionRule() {}

}
