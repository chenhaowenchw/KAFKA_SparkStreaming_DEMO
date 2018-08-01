package KAFKA_spark

import java.util
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object KafkaWcProducer {
  def main(args: Array[String]): Unit = {
    val topic="test"
    val broker="hadoop01.ljnet.com:9092,hadoop02.ljnet.com:9092,hadoop03.ljnet.com:9092"
    val messageTime=1 //每秒几条信息
    val wcrow=4 //每行几个数据
    val props=new util.HashMap[String,Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,broker)

    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    while(true) {
      (1 to messageTime.toInt).foreach { messageNum =>
        val str = (1 to wcrow.toInt).map(x => scala.util.Random.nextInt(10).toString)
          .mkString(" ")
        val message = new ProducerRecord[String, String](topic, null, str)
        producer.send(message)
        println(message)
      }
      Thread.sleep(1000)
    }

  }
}
