package com.gatech.project.dao

import com.gatech.project.domain.LanguageSearchCount
import com.gatech.project.params.Params
import com.gatech.project.utils.HBaseUtils

import scala.collection.mutable.ListBuffer


/**
 * Language Search DAO Layer
 */
object LanguageSearchCountDAO {

  val tablename = Params.table
  val cf = Params.cf
  val qualifer = Params.column
  val quorum = Params.quorum
  val port = Params.port
  HBaseUtils.setConf(quorum, port)

  /**
   * get the value of language_search
   * @param language_search
   * @return long value
   */
  def count(language_search: String):Long = {
    HBaseUtils.singleGet(this.tablename, language_search, this.cf, this.qualifer)
  }


  /**
   * save to hbase with replacement
   * @param list list of Language Search Count
   */
  def saveOnce(list: ListBuffer[LanguageSearchCount]): Unit = {
    for(ele <- list) {
      HBaseUtils.singlePut(this.tablename, ele.language, this.cf, this.qualifer, ele.search_count)
    }
  }

  /**
   * save to hbase
   * @param list list of Language Search Count
   */
  def save(list: ListBuffer[LanguageSearchCount]): Unit = {
    for(ele <- list) {
      var countNum = ele.search_count + count(ele.language)
      HBaseUtils.singlePut(this.tablename, ele.language, this.cf, this.qualifer, countNum)
    }
  }
}
