package com.gatech.project.spark

import com.gatech.project.dao.LanguageSearchCountDAO
import com.gatech.project.domain.{SearchLog, LanguageSearchCount}
import com.gatech.project.utils.DateUtils
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

import scala.collection.mutable.ListBuffer

object LogsStreaming {

  //localhost:9092 test4 streamingtopic
  def main(args: Array[String]): Unit = {

    if (args.length < 3) {
      System.err.println(
        s"""
           |Usage: LogsStreaming <brokers> <groupId> <topics>
           |  <brokers> is a list of one or more Kafka brokers
           |  <groupId> is a consumer group name to consume from topics
           |  <topics> is a list of one or more kafka topics to consume from
           |
          """.stripMargin)
      System.exit(1)
    }

    val Array(brokers, groupId, topics) = args

    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setAppName("StatStreamingApp").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(60))
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
      ConsumerConfig.GROUP_ID_CONFIG -> groupId,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer])
    val messages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet, kafkaParams))

    val lines = messages.map(_.value)

    //get number
    val cleanData = lines.map(line => {
      // infos(2) = "GET /class/130.html HTTP/1.1"
      val infos = line.split("\t")
      //      print("info 2 is " + infos(2))
      val url = infos(2).split(" ")(1)
      var languageId = 0
      if (url.startsWith("/class")) {
        val languageIdHTML = url.split("/")(2)
        languageId = languageIdHTML.substring(0, languageIdHTML.lastIndexOf(".")).toInt
      }
      SearchLog(infos(0), DateUtils.parseToMinute(infos(1)), languageId, infos(3).toInt, infos(4))
    }).filter(SearchLog => SearchLog.languageId != 0)

    cleanData.print()

    // calculate from different search web
    cleanData.map(x => {
      (x.time.substring(0, 8) + "_" + x.languageId, 1)
    }).reduceByKey(_ + _).foreachRDD(rdd => {
      rdd.foreachPartition(partitionRecords => {
        val list = new ListBuffer[LanguageSearchCount]
        partitionRecords.foreach(pair => {
          list.append(LanguageSearchCount(pair._1, pair._2))
        })
        LanguageSearchCountDAO.save(list)
      })
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
