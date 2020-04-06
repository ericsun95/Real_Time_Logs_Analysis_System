package com.gatech.project.utils

import org.junit._
import Assert._
import com.gatech.project.dao.LanguageSearchCountDAO.{cf, qualifer}
import com.gatech.project.params.Params
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes

@Test
class HBaseUtilsTest {

  @Test
  def HBaseUtilsTest() = {
    val value = 3
    HBaseUtils.setConf(Params.quorum, Params.port)
    if(!HBaseUtils.existTable(Params.table)) {
      print("table not existed yet")
      HBaseUtils.createTable(Params.table, Params.cf)
    }
    print("table already existed yet")
    HBaseUtils.singlePut(Params.table, Params.rowkey, Params.cf, Params.column, Params.value)
    val setVal = HBaseUtils.singleGet(Params.table, Params.rowkey, Params.cf, Params.column)
    assert(value == setVal)
  }
}
