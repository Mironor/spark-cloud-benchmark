package ski.bedryt

import com.typesafe.config.ConfigFactory
import ski.bedryt.aggregations.TrivialAggregations

import scala.math.random
import ski.bedryt.utils.{Reader, Spark}

object Main extends App {

  val arguments = Arguments(args)

  lazy val config = ConfigFactory.load()

  lazy val pathToCommentsCsv = config.getString("benchmark.files.csv.comments")
  lazy val pathToUsersCsv = config.getString("benchmark.files.csv.users")
  lazy val pathToBadgesCsv = config.getString("benchmark.files.csv.badges")

  if (arguments.integrationTests) runPi()
  if (arguments.trivialAggregations) runTrivialAggregations()

  def runPi(): Unit = {
    val slices = 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = Spark.session.sparkContext.parallelize(1 until n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x * x + y * y < 1) 1 else 0
    }.reduce(_ + _)
    println("BENCHMARK: Pi is roughly " + 4.0 * count / n)
  }

  def runTrivialAggregations(): Unit = {
    println(pathToBadgesCsv)
    val badges = Reader.readBadges(pathToBadgesCsv)
    val badgesCount = TrivialAggregations.countBadges(badges)
    println(s"Badges count: $badgesCount")
  }

}
