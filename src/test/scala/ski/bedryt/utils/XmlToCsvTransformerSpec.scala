package ski.bedryt.utils

import org.scalatest.{Matchers, WordSpec}

class XmlToCsvTransformerSpec  extends WordSpec with Matchers{

  "XmlToCsvTransformer" should {
    "transform an RDD with XML contents into CSV contents" in {
      // Given
      val path = "src/test/resources/xml/comments.xml"
      val content = XmlToCsvTransformer.readAsText(path)

      val pathToExpected = "src/test/resources/csv/comments.csv"
      val expected = XmlToCsvTransformer.readAsText(pathToExpected).collect()

      val header = expected.head

      // When
      val result = XmlToCsvTransformer.xmlToCsv(content, header).collect()

      // Then
      result.length shouldBe 9
      result shouldBe expected
    }
  }

}
