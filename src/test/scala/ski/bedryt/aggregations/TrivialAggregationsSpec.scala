package ski.bedryt.aggregations

import com.typesafe.config.ConfigFactory
import org.scalatest.{Matchers, WordSpec}
import ski.bedryt.utils.Reader

class TrivialAggregationsSpec   extends WordSpec with Matchers{

  lazy val config = ConfigFactory.load()

  "TrivialAggregationsSpec" should {
    "count badges (returning the result to the driver)" in {
      // Given
      val pathToBadgesCsv = config.getString("benchmark.files.csv.comments")
      val badges = Reader.readBadges(pathToBadgesCsv)

      // When
      val result = TrivialAggregations.countBadges(badges)

      // Then
      result shouldBe 8
    }
  }

}
