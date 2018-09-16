package ski.bedryt.aggregations

import com.typesafe.config.ConfigFactory
import org.scalatest.{Matchers, WordSpec}
import ski.bedryt.utils.Reader

class TrivialAggregationsSpec   extends WordSpec with Matchers{

  lazy val config = ConfigFactory.load()

  "TrivialAggregationsSpec" should {
    val pathToBadgesCsv = config.getString("benchmark.files.csv.badges")

    "count badges (returning the result to the driver)" in {
      // Given
      val badges = Reader.readBadges(pathToBadgesCsv)

      // When
      val result = TrivialAggregations.countBadges(badges)

      // Then
      result shouldBe 9
    }

    "count distinct badges names" in {
      // Given
      val badges = Reader.readBadges(pathToBadgesCsv)

      // When
      val result = TrivialAggregations.countDistinctBadges(badges)

      // Then
      result shouldBe 5
    }
  }

}
