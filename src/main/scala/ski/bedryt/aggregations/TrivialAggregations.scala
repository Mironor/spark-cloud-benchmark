package ski.bedryt.aggregations

import org.apache.spark.sql.Dataset
import ski.bedryt.model.Schema.Badge

object TrivialAggregations {

  def countBadges(badges: Dataset[Badge]): Long = badges.count()

  def countDistinctBadges(badges: Dataset[Badge]): Long =  badges.groupBy(badges("name")).count().count()

}
