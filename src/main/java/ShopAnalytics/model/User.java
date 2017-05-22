package ShopAnalytics.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by gman0_000 on 03.05.2017.
 */

@Data
@Entity
@Table(name = "ShopUser")
public class User {

    @Id
    @GeneratedValue(generator = "UserGen")
    @SequenceGenerator(name = "UserGen", sequenceName = "user_seq", allocationSize = 1)
    private long id;

    @OneToOne
    private Role role;

    @NotNull
    private String login;
    @NotNull
    private String password;

    public User() {}

    public User(String login, String password, Role role){
        this.login = login;
        this.password = password;
        this.role = role;
    }


}
