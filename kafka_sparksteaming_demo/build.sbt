
name := "kafka_sparksteaming_demo"

version := "0.1"

scalaVersion := "2.10.7"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming"% "1.6.0"
// %"provided" 需要注释掉 复制运行时无法调用到依赖的包