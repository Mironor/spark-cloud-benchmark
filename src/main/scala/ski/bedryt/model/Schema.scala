package ski.bedryt.model

import java.sql.Date

import org.apache.spark.sql.types._

object Schema {

  case class Badge(id: Long,
                   name: String,
                   date: Date,
                   userId: Long,
                   classId: Int,
                   tagBased: Boolean)

  object Badge{
    val schema = StructType(Seq(
      StructField("id", LongType),
      StructField("name", StringType),
      StructField("date", DateType),
      StructField("user_id", LongType),
      StructField("class", IntegerType),
      StructField("tag_based", BooleanType)
    ))

    val fieldNames = Seq("id", "name", "date", "userId", "classId", "tagBased")
  }
}
