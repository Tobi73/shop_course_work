package ShopAnalytics.Repository;

import ShopAnalytics.Model.Product;
import ShopAnalytics.Model.Transaction;
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
