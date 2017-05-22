package ShopAnalytics.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by gman0_000 on 03.05.2017.
 */

@Data
@Entity
@Table(name = "Business_Entity")
public class BusinessEntity {

    @Id
    @Column(name = "inn", length = 11)
    Long inn;

    @NotNull
    @Column(name = "giro", length = 11)
    int giro;

    String email;
    @NotNull
    String name;
    String address;
    String phone;

    public BusinessEntity() {}

    public BusinessEntity(Long inn, int giro, String email, String name, String address, String phone){
        this.inn = inn;
        this.giro = giro;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

}
