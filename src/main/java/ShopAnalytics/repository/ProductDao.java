package ShopAnalytics.repository;

import ShopAnalytics.model.Product;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Transactional
public interface ProductDao extends CrudRepository<Product, Long> {
    Product findByNameIgnoreCase(String productName);
}
