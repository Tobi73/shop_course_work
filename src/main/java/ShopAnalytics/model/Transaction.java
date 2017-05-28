package ShopAnalytics.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by gman0_000 on 03.05.2017.
 */
@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "TransactionGen")
    @SequenceGenerator(name = "TransactionGen", sequenceName = "trans_seq", allocationSize = 1)
    private long id;

    @NotNull
    private Date date;

    @NotNull
    private int price;

    @ManyToOne
    private BusinessEntity businessEntity;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @OneToOne
    private TransactionType transactionType;

    @NotNull
    @ManyToOne
    private Product product;

    public Transaction() {}

    public Transaction(Date date, int price, BusinessEntity businessEntity,
                       User user, TransactionType transactionType,
                       Product product){
        this.date = date;
        this.price = price;
        this.businessEntity = businessEntity;
        this.user = user;
        this.transactionType = transactionType;
        this.product = product;
    }
}
