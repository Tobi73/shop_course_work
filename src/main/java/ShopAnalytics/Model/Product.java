package ShopAnalytics.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by gman0_000 on 03.05.2017.
 */

@Data
@Entity
@Table(name = "Product")
public class Product {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private int quantity;

    public Product() {}

    public Product(String id, String name){
        this.id = id;
        this.name = name;
        this.quantity = 0;
    }

}
