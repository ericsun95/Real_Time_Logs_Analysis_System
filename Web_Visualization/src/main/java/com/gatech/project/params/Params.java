package com.gatech.project.params;

import java.util.HashMap;
import java.util.Map;

public class Params {
    public static String TABLENAME = "language_search_count";
    public static String QUORUM = "localhost";
    public static String PORT = "2181";
    public static String COLUMN_FAMILY = "info";
    public static String COLUMN = "search_count";
    public static String ROWKEY_TEST = "20200405";
    public static Map<String, String> courses = new HashMap<String, String>();
    static {
        courses.put("112", "Spark SQL");
        courses.put("128", "Kafka");
        courses.put("130", "Hadoop");
        courses.put("131", "Storm");
        courses.put("145", "Spark Streaming");
        courses.put("146", "Python");
    }
}
