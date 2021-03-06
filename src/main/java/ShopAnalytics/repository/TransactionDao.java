package ShopAnalytics.repository;

import ShopAnalytics.model.Product;
import ShopAnalytics.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Transactional
public interface TransactionDao extends CrudRepository<Transaction, Long> {

    List<Transaction> findAllByProduct(Product product);

    @Query("select t.businessEntity.inn, t.businessEntity.name, sum(t.price), count(t.id)\n" +
            " from Transaction t where t.product.id=?1 group by t.businessEntity.inn, t.businessEntity.name")
    List<Object[]> findToBuildGraph(long productId);

    @Query("select month(t.date), sum(t.price) from Transaction t" +
            " where t.product.id=?1 and t.transactionType.name=\'buy\' group by month(t.date)")
    List<Object[]> sortByMonth(long productId);

}
