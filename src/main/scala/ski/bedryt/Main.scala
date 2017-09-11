package ski.bedryt

import com.typesafe.config.ConfigFactory
import ski.bedryt.utils.XmlToCsvTransformer

object Main extends App{

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

  if (arguments.integrationTests) runIntegrationTests()

  private def runIntegrationTests(): Unit ={
    val pathToExpectedCommentsCsv = config.getString("benchmark.files.expected.csv.comments")
    val pathToExpectedUsersCsv = config.getString("benchmark.files.expected.csv.users")
    val pathToExpectedBadgesCsv = config.getString("benchmark.files.expected.csv.badges")

    convertComments()
    convertUsers()
    convertBadges()

    val commentsCsvGenerated = XmlToCsvTransformer.readAsText(pathToCommentsCsv).collect().mkString("")
    val commentsCsvExpected = XmlToCsvTransformer.readAsText(pathToExpectedCommentsCsv).collect().mkString("")

    assert(commentsCsvGenerated == commentsCsvExpected)

    val usersCsvGenerated = XmlToCsvTransformer.readAsText(pathToUsersCsv).collect().mkString("")
    val usersCsvExpected = XmlToCsvTransformer.readAsText(pathToExpectedUsersCsv).collect().mkString("")

    assert(usersCsvGenerated == usersCsvExpected)

    val badgesCsvGenerated = XmlToCsvTransformer.readAsText(pathToBadgesCsv).collect().mkString("")
    val badgesCsvExpected = XmlToCsvTransformer.readAsText(pathToExpectedBadgesCsv).collect().mkString("")

    assert(badgesCsvGenerated == badgesCsvExpected)
  }

  private def convertComments(): Unit ={
    val commentsXml = XmlToCsvTransformer.readAsText(pathToCommentsXml)
    val commentsCsv = XmlToCsvTransformer.xmlToCsv(commentsXml, commentsHeader)
    XmlToCsvTransformer.writeAsText(commentsCsv, pathToCommentsCsv)
  }

  private def convertUsers(): Unit ={
    val usersXml = XmlToCsvTransformer.readAsText(pathToUsersXml)
    val usersCsv = XmlToCsvTransformer.xmlToCsv(usersXml, usersHeader)
    XmlToCsvTransformer.writeAsText(usersCsv, pathToUsersCsv)
  }

  private def convertBadges(): Unit ={
    val badgesXml = XmlToCsvTransformer.readAsText(pathToBadgesXml)
    val badgesCsv = XmlToCsvTransformer.xmlToCsv(badgesXml, badgesHeader)
    XmlToCsvTransformer.writeAsText(badgesCsv, pathToBadgesCsv)
  }
}
