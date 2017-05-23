package ShopAnalytics.repository;

import ShopAnalytics.bll.DataAnalysis;
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

    @Query("select t.businessEntity.inn, sum(t.price), count(t.id)\n" +
            " from Transaction t group by t.businessEntity.inn")
    List<Object[]> findToBuildGraph();

}
