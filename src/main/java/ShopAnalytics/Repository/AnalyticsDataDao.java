package ShopAnalytics.Repository;

import ShopAnalytics.Model.AnalyticsData;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by gman0_000 on 20.05.2017.
 */
@Transactional
public interface AnalyticsDataDao extends CrudRepository<AnalyticsData, Integer> {}