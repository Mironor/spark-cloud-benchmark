package ski.bedryt

import com.typesafe.config.ConfigFactory
import scala.math.random
import ski.bedryt.utils.{Spark, XmlToCsvTransformer}

object Main extends App {

  val arguments = Arguments(args)

  lazy val config = ConfigFactory.load()

  lazy val pathToCommentsXml = config.getString("benchmark.files.xml.comments")
  lazy val pathToUsersXml = config.getString("benchmark.files.xml.users")
  lazy val pathToBadgesXml = config.getString("benchmark.files.xml.badges")

  lazy val commentsHeader = config.getString("benchmark.csv.comments.header")
  lazy val usersHeader = config.getString("benchmark.csv.users.header")
  lazy val badgesHeader = config.getString("benchmark.csv.badges.header")

  lazy val pathToCommentsCsv = config.getString("benchmark.files.csv.comments")
  lazy val pathToUsersCsv = config.getString("benchmark.files.csv.users")
  lazy val pathToBadgesCsv = config.getString("benchmark.files.csv.badges")

  if (arguments.integrationTests) runPi()

  Spark.session.stop()

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

  def convertComments(): Unit = {
    val commentsXml = XmlToCsvTransformer.readAsText(pathToCommentsXml)
    val commentsCsv = XmlToCsvTransformer.xmlToCsv(commentsXml, commentsHeader)
    XmlToCsvTransformer.writeAsText(commentsCsv, pathToCommentsCsv)
  }

  def convertUsers(): Unit = {
    val usersXml = XmlToCsvTransformer.readAsText(pathToUsersXml)
    val usersCsv = XmlToCsvTransformer.xmlToCsv(usersXml, usersHeader)
    XmlToCsvTransformer.writeAsText(usersCsv, pathToUsersCsv)
  }

  def convertBadges(): Unit = {
    val badgesXml = XmlToCsvTransformer.readAsText(pathToBadgesXml)
    val badgesCsv = XmlToCsvTransformer.xmlToCsv(badgesXml, badgesHeader)
    XmlToCsvTransformer.writeAsText(badgesCsv, pathToBadgesCsv)
  }
}
