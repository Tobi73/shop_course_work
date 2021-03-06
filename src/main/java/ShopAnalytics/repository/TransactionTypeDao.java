package ShopAnalytics.repository;

import ShopAnalytics.model.TransactionType;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Transactional
public interface TransactionTypeDao extends CrudRepository<TransactionType, Long> {
    TransactionType findByName(String name);
}