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
      "pi",
      noshort = true,
      descr = "Runs a simple PI calculation in case you just want to see if your platform works"
    )

    verify()

    val xmlToCsv: Boolean = xmlToCsvArgument.getOrElse(false)
    val integrationTests: Boolean = integrationTestsArgument.getOrElse(false)

  }
}
