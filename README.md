# Real Time Logs Analysis System

## Configuration
- Install the hadoop, hbase, kafka, flume and spark with homebrew
- Write the configuration files: \
**kafka**: zookeeper.properties, server.properties \
**flume**: streaming_project_kafka.conf \
**hadoop**: core-site.xml, hadoop-env.sh, hdfs-site.xml, mapred-site.xml, yarn-site.xml

## Generate Logs (Python)
Using `crontab` to generate streaming logs.
1. Get into the **Logs_Generation** folder to give authorization: \
`chmod u+x log_generator.sh` and `tail -200f access.log`
2. Use Crontab to generate Logs with time:
- `crontab -e`
- `*/1 * * * * sh /Users/..../log_generator.sh`
- `:wq`
3. Check crontab, vim, list, remove
`crontab -e`, `crontab -l`, `crontab -r`


## Cosume the Logs (Flume, Kakfa)
1. Edit the file "**streaming_project_kafka.conf**"
2. Run the **Zookeeper**, **Kafka**, **Flume**
 - `zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties &`
 - `kafka-server-start -daemon /usr/local/etc/kafka/server.properties &`
 - `kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafka_streaming_topic`
 - Get into the flume folder, and run `bin/flume-ng agent -Xms1000m -Xmx1000m --conf conf --conf-file conf_files/streaming_project_kafka.conf --name exec-memory-kafka -Dflume.root.logger=INFO,console`
3. Receive the data throught kafka consumer (Check)
 - `kafka-console-consumer --bootstrap-server localhost:9092 --topic streamingtopic --from-beginning`
 
 ## Store the proccessed data (Hbase)
 1. Start hadoop server
 - `cd /usr/local/cellar/hadoop/3.2.1_1/sbin`
	- `./start-all.sh`
 2. Start hbase
 - `cd /usr/local/cellar/hbase/1.3.5/bin`
 -  `./start-hbase.sh`
 -  `./hbase shell`
 3. Create Table
 `create 'language_search_count', 'info'`

 ## Cosume the logs(Spark Streaming)
 **Spark Streaming** to consume topics from **Kafka** and save to the **Hbase** \
 run `LogsStreaming` in folder **Logs_Analysis** in intellij.

 ## Web visualization (SpringBoot, Echarts)
 - run `WebVisualizationApplication` in folder **Web_Visualization** in intellij.
 - open the `http://localhost:9999/gatech/echarts`
  
 
 
