package com.gatech.project.utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client.{Admin, Connection, ConnectionFactory, Get, Put, Table}
import org.apache.hadoop.hbase.util.Bytes


/**
 * HBase utils
 */
object HBaseUtils {

  var conf: Configuration = _
  // line pool
  lazy val connection: Connection = ConnectionFactory.createConnection(conf)
  lazy val admin: Admin = connection.getAdmin

  /**
   * hbase configuration
   *
   * @param quorum hbase zookeeper location
   * @param port   zookeeper port: 2181
   * @return
   */
  def setConf(quorum: String, port: String): Unit = {
    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum", quorum)
    conf.set("hbase.zookeeper.property.clientPort", port)
    this.conf = conf
  }

  /**
   * get the table
   * @param tableName name of the table
   */
  def getTable(tableName: String): Table = {
    connection.getTable(TableName.valueOf(tableName))
  }

  /**
   * create a table
   * @param tableName name of the table
   * @param columnFamily hbase columnfamily
   */
  def createTable(tableName: String, columnFamily: String): Unit = {
    val tbName = TableName.valueOf(tableName)
    if (!admin.tableExists(tbName)) {
      val tableDescriptor = new HTableDescriptor(tbName)
      tableDescriptor.addFamily(new HColumnDescriptor(columnFamily))
      admin.createTable(tableDescriptor)
    }
  }

  /**
   * check whether exists table
   * @param tableName name of the table
   * @return
   */
  def existTable(tableName: String): Boolean = {
    admin.tableExists(TableName.valueOf(tableName))
  }

  /**
   * single value input
   * @param tableName name of the table
   * @param rowKey hbase rowkey
   * @param family hbase columnfamily
   * @param qualifier hbase column
   * @param value input value
   */
  def singlePut(tableName: String, rowKey: String, family: String, qualifier: String, value: Long): Unit = {
    val put: Put = new Put(Bytes.toBytes(rowKey))
    put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value))
    val table: Table = connection.getTable(TableName.valueOf(tableName))
    table.put(put)
    table.close()
  }

  /**
   * single value get
   * @param tableName name of the table
   * @param rowKey hbase rowkey
   * @param family hbase columnfamily
   * @param qualifier hbase column
   * @return
   */
  def singleGet(tableName: String, rowKey: String, family: String, qualifier: String): Long = {
    val table = HBaseUtils.getTable(tableName)
    val get = new Get(Bytes.toBytes(rowKey))
    val value = table.get(get).getValue(family.getBytes, qualifier.getBytes)
    if(value == null) {
      0L
    }else{
      Bytes.toLong(value)
    }
  }
  /**
   * close the connection and admin
   */
  def close(): Unit = {
    admin.close()
    connection.close()
  }
}
