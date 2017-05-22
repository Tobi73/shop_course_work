package ShopAnalytics.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * Created by gman0_000 on 14.05.2017.
 */
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(generator = "RoleGen")
    @SequenceGenerator(name = "RoleGen", sequenceName = "role_seq", allocationSize = 1, initialValue = 1)
    private long id;

    @NotNull
    private String name;

    @NotNull
    public Role() {}

    public Role(String name){
        this.name = name;
    }

}
