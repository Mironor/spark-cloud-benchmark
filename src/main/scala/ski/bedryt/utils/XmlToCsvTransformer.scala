package ski.bedryt.utils
import org.apache.spark.rdd.RDD

object XmlToCsvTransformer {

  /**
    * Reads the contents of a file as a text
    * @param path path to file
    * @return RDD containing lines from the file as elements
    */
  def readAsText(path: String): RDD[String] = Spark.session.sparkContext.textFile(resolveAbsolutePath(path))

  /**
    * Writes resulting contents into a file from provided path
    * @param content RDD contents to write
    * @param path path to which save the file
    */
  def writeAsText(content: RDD[String], path: String): Unit = content.saveAsTextFile(resolveAbsolutePath(path))

  /**
    * Transforms the content in XML to CSV format.
    * Does not save the result
    * @param content the contents of the XML file
    * @param header header of the CSV file
    * @return RDD containing CSV-formatted contents of the XML file
    */
  def xmlToCsv(content:RDD[String], header: String): RDD[String] = {

    // Extract a list of values between quotes https://stackoverflow.com/a/171499/555355
    val pattern = """(")(?:(?=(\\?))\2.)*?\1""".r
    val headerRdd = Spark.session.sparkContext.parallelize(Seq(header))

    val csv = content.collect {
      // in provided Stackoverflow's files rows are indented by four spaces
      case line if line.startsWith("    ") => pattern.findAllIn(line).mkString(",")
    }

    headerRdd.union(csv)
  }

  /**
    * Spark's "input" function require an absolute path to the source.
    * As local path to some files may be different on each machine (OS dependent), this function
    * will generate it on the fly
    * @param path path to a file (use relative path for local files)
    * @return absolute path to the file
    */
  private[utils] def resolveAbsolutePath(path: String) = {
    val currentDirectory = new java.io.File(".").getCanonicalPath

    if (path.startsWith("hdfs://") || path.startsWith("file://") || path.startsWith("s3://")) path
    else {
      if (System.getProperty("os.name").toLowerCase.startsWith("windows"))  s"$currentDirectory/$path"
      else s"file://$currentDirectory/$path"
    }
  }

}
