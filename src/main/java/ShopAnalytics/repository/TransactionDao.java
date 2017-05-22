package ShopAnalytics.repository;

import ShopAnalytics.model.Product;
import ShopAnalytics.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Transactional
public interface TransactionDao extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByProduct(Product product);
}
