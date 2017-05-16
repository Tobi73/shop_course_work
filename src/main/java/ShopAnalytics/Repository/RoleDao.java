package ShopAnalytics.Repository;

import ShopAnalytics.Model.Role;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by gman0_000 on 14.05.2017.
 */

@Transactional
public interface RoleDao extends CrudRepository<Role, Long> {
}
