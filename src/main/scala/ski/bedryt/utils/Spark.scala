package ski.bedryt.utils

import org.apache.spark.sql.SparkSession

/**
  * Singleton holding reference to the Spark Session and initialising its parameters
  */
object Spark {
  val session: SparkSession = SparkSession.builder.appName("Spark Cloud Benchmark").getOrCreate()
  session.sparkContext.setLogLevel("WARN")
}
