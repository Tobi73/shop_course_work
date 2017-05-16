package ShopAnalytics.Repository;

import ShopAnalytics.Model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by gman0_000 on 14.05.2017.
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {

}
