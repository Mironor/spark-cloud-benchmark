package ski.bedryt.utils
import org.apache.spark.rdd.RDD

object Reader {


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