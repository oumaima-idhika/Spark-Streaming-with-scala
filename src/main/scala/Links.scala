import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.SparkConf
object Links{


  def main(args: Array[String]) {


    Logger.getLogger("org").setLevel(Level.ERROR)


    val sc = new SparkContext("local[*]", "scalaproject")


    val lines = sc.textFile("link.txt")


    val links = lines.map(x => x.split(" ")(2))


    val spark = SparkSession
      .builder
      .appName("project")
      .master("local[*]")
      .getOrCreate()


    import spark.implicits._

    val df = links.toDF("sites")
    df.coalesce(1).write.option("header",true).format("csv").save("../datas/data68.csv")
//fichier plat
    /*val lines1=spark.read.option("header","true").csv("../datas/data68.csv");
    lines1.write
      .format("org.elasticsearch.spark.sql")
      .option("es.port","9200")
      .option("es.nodes","localhost")
      .mode("append")
      .save("data67/_doc")

*/
    //spark streaming
  val schema = StructType(List(
     StructField("sites", StringType, true),
     StructField("views", StringType, true)))
   val StreamDF = spark.readStream.option("delimiter", ",").schema(schema)
     .csv("../datas")
    StreamDF.createOrReplaceTempView("SDF")
    val outDF = spark.sql("select * from SDF")
    val conf = new SparkConf().setAppName("test")
    conf.set("es.index.auto.create", "true");
    outDF.writeStream
      .format("org.elasticsearch.spark.sql")
      .option("es.port","9200")
      .option("checkpointLocation", "/tmp/")
      .option("es.nodes","localhost")
      .outputMode("append")
      .start("data68/_doc")


   // outDF.writeStream.format("console").outputMode("append").start().awaitTermination()
  }

}

