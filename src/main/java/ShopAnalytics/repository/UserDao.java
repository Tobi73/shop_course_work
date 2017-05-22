package ShopAnalytics.repository;

import ShopAnalytics.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {

    User findByLogin(String login);

}