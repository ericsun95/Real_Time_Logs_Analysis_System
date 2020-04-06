package com.gatech.project.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HBaseUtils {
    /**
     * HBase utils singleton
     */
    HBaseAdmin admin = null;
    Configuration configuration = null;
    Connection connection = null;
    String quorum = null;
    String port = null;

    /**
     * Construct the connection
     * @param quorum quorum
     * @param port connection port
     */
    private HBaseUtils(String quorum, String port){
        this.quorum = quorum;
        this.port = port;
        this.configuration = new Configuration();
        this.configuration.set("hbase.zookeeper.quorum", this.quorum);
        this.configuration.set("hbase.zookeeper.property.clientPort", this.port);
        try {
            this.connection = ConnectionFactory.createConnection(this.configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HBaseUtils instance = null;

    /**
     * Get the Instance
     * @param quorum quorum
     * @param port connection port
     * @return
     */
    public  static synchronized HBaseUtils getInstance(String quorum, String port) {
        if(null == instance) {
            instance = new HBaseUtils(quorum, port);
        }
        return instance;
    }


    /**
     * Get HTable
     */
    public Table getTable(String tableName) {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    /**
     * Add to the Hbase
     * @param tableName HBase table name
     * @param rowkey  HBase rowkey
     * @param cf HBase columnfamily
     * @param column HBase column
     * @param value  Written value
     */
    public void put(String tableName, String rowkey, String cf, String column, Long value) {
        Table table = getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(column), Bytes.toBytes(value));
        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get records
     * @param tableName HBase table name
     * @param dayCourse key dayCourse
     * @return
     */
    public Map<String, Long> query(String cf, String qualifier, String tableName, String dayCourse) {
        Map<String, Long> map = new HashMap<String, Long>();
        Table table = getTable(tableName);
        Scan scan = new Scan();
        Filter filter = new PrefixFilter(Bytes.toBytes(dayCourse));
        scan.setFilter(filter);
        ResultScanner rs = null;
        try {
            rs = table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Result result : rs) {
            String row = Bytes.toString(result.getRow());
            long clickCount = Bytes.toLong(result.getValue(cf.getBytes(), qualifier.getBytes()));
            map.put(row, clickCount);
        }
        return map;
    }

}
