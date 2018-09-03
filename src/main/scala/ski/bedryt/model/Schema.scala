package ski.bedryt.model

import java.sql.Date

import org.apache.spark.sql.types._

object Schema {

  case class Badge(Id: Long,
                   UserId: Long,
                   Name: String,
                   Date: Date,
                   Class: Int,
                   TagBased: Boolean)

  object Badge{
    val schema = StructType(Seq(
      StructField("Id", LongType),
      StructField("UserId", LongType),
      StructField("Name", StringType),
      StructField("Date", DateType),
      StructField("Class", IntegerType),
      StructField("TagBased", BooleanType)
    ))
  }

}
