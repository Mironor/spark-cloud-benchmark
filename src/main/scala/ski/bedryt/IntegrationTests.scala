package ski.bedryt

import ski.bedryt.utils.{Spark, Reader}

object IntegrationTests extends App {

  val pathToExpectedCommentsCsv = Main.config.getString("benchmark.files.expected.csv.comments")
  val pathToExpectedUsersCsv = Main.config.getString("benchmark.files.expected.csv.users")
  val pathToExpectedBadgesCsv = Main.config.getString("benchmark.files.expected.csv.badges")

  Spark.session.stop()

}
