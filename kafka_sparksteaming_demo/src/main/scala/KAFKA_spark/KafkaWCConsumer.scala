package KAFKA_spark
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
object KafkaWCConsumer {
    def myFunc = (it: Iterator[(String, Seq[Int], Option[Int])]) => {
        it.map(x => {
            (x._1, x._2.sum + x._3.getOrElse(0))
        })
    }
    def main(args: Array[String]): Unit = {
      //  MyLog.setLogLeavel(Level.ERROR)
        val conf = new SparkConf().setAppName("SPARK_KAFKA").setMaster("local[2]")
        val sc = new SparkContext(conf)
        val ssc = new StreamingContext(sc, Seconds(10))//10秒收集一次
        sc.setCheckpointDir("C:\\Users\\Administrator\\Desktop\\checkpointks01")//checkpoint 到win上

        val zk = "hadoop01.ljnet.com:2181"
        val groupId = "2"
        //key表示topic  value表示处理线程数量
        val topics = Map("test2" -> 3)
        //参数ssc zk groupId topics storageLevel,注意这里的topics是不可变map 习惯用可变map发现一直报错最后才发现需要用不可变map
        //type Map[A, +B] = scala.collection.immutable.Map[A, B]
        val ds = KafkaUtils.createStream(ssc, zk, groupId, topics)
        val res = ds.map((_._2)).flatMap(_.split(" ")).map((_, 1)).updateStateByKey(myFunc, new HashPartitioner(sc.defaultParallelism), true)
        res.print()
        ssc.start()
        ssc.awaitTermination()
    }

}