package ShopAnalytics.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by gman0_000 on 14.05.2017.
 */

@Data
@Entity
@Table(name = "Transaction_type")
public class TransactionType {

    @Id
    @GeneratedValue(generator = "TransType")
    @SequenceGenerator(name = "TransType", sequenceName = "trans_type_seq", allocationSize = 1)
    private long id;

    @NotNull
    private String name;

    public TransactionType(){}

    public TransactionType(String name){
        this.name = name;
    }

}
