package ski.bedryt

import org.rogach.scallop.ScallopConf

/**
  * Handles application's parameters
  * https://github.com/scallop/scallop
  */
object Arguments {
  def apply(arguments: Array[String]) = new ScallopConf(arguments) {
    banner(
      """
       TBD
      """
    )

    private val xmlToCsvArgument = opt[Boolean](
      "xmlToCsv",
      noshort = true,
      descr = "Transforms xml files (in config) into csv files (target path is in config)"
    )

    private val integrationTestsArgument = opt[Boolean](
      "integrationTests",
      noshort = true,
      descr = "Runs integration tests (the test data should fit on driver!)"
    )

    verify()

    val xmlToCsv: Boolean = xmlToCsvArgument.getOrElse(false)
    val integrationTests: Boolean = integrationTestsArgument.getOrElse(false)

  }
}
