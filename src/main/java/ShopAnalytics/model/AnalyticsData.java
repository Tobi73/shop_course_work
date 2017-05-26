package ShopAnalytics.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by gman0_000 on 18.05.2017.
 */
@Entity
@Data
@Table(name = "analytics_data")
public class AnalyticsData {

    @Id
    @GeneratedValue(generator = "AnalyticsGen")
    @SequenceGenerator(name = "AnalyticsGen", sequenceName = "analytics_seq", allocationSize = 1)
    private long id;

    @OneToOne
    private BusinessEntity businessEntity;

    private int quantity;

    private int price = -1;

    @OneToOne
    private Product product;

    public AnalyticsData() {}

    public AnalyticsData(BusinessEntity businessEntity, int quantity, Product product, int price){
        this.businessEntity = businessEntity;
        this.quantity = quantity;
        this.product = product;
        this.price = price;
    }

    public AnalyticsData(BusinessEntity businessEntity, int quantity, Product product){
        this.businessEntity = businessEntity;
        this.quantity = quantity;
        this.product = product;
    }

}
