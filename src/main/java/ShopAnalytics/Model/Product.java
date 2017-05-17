package ShopAnalytics.Model;

import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by gman0_000 on 03.05.2017.
 */

@Data
@Entity
@Table(name = "Product")
@Check(constraints = "quantity >= 0")
public class Product {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "sell_price")
    private int sellPrice;

    public Product() {}

    public Product(String id, String name, int sellPrice){
        this.id = id;
        this.name = name;
        this.quantity = 0;
        this.sellPrice = sellPrice;
    }

}
