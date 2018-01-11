package ski.bedryt

import ski.bedryt.utils.{Spark, XmlToCsvTransformer}

object IntegrationTests extends App {

  val pathToExpectedCommentsCsv = Main.config.getString("benchmark.files.expected.csv.comments")
  val pathToExpectedUsersCsv = Main.config.getString("benchmark.files.expected.csv.users")
  val pathToExpectedBadgesCsv = Main.config.getString("benchmark.files.expected.csv.badges")

  Main.convertComments()
  Main.convertUsers()
  Main.convertBadges()

  val commentsCsvGenerated = XmlToCsvTransformer.readAsText(Main.pathToCommentsCsv).collect().mkString("")
  val commentsCsvExpected = XmlToCsvTransformer.readAsText(pathToExpectedCommentsCsv).collect().mkString("")

  assert(commentsCsvGenerated == commentsCsvExpected)

  val usersCsvGenerated = XmlToCsvTransformer.readAsText(Main.pathToUsersCsv).collect().mkString("")
  val usersCsvExpected = XmlToCsvTransformer.readAsText(pathToExpectedUsersCsv).collect().mkString("")

  assert(usersCsvGenerated == usersCsvExpected)

  val badgesCsvGenerated = XmlToCsvTransformer.readAsText(Main.pathToBadgesCsv).collect().mkString("")
  val badgesCsvExpected = XmlToCsvTransformer.readAsText(pathToExpectedBadgesCsv).collect().mkString("")

  assert(badgesCsvGenerated == badgesCsvExpected)

  Spark.session.stop()

}
