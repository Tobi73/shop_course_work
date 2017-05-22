package ShopAnalytics.model;

import javafx.util.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreyzaytsev on 22.05.17.
 */

@Data
public class BusinessPartnerNode {

    private String companyName;
    private Long companyId;
    private List<Pair<Long, Double>> relations;

    public BusinessPartnerNode(String companyName, Long companyId){
        this.companyName = companyName;
        this.companyId = companyId;
        this.relations = new ArrayList<>();
    }

    public void addRelation(Pair<Long, Double> relation){
        this.relations.add(relation);
    }
}
