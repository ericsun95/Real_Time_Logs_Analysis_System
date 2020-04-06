package com.gatech.project.utils

import java.util.Date
import org.apache.commons.lang3.time.FastDateFormat

/**
 * time tool class
 */
object DateUtils {
  val YYYYMMDDHHMMSS_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
  val TARGE_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss")

  /**
   * get the time in fast date format
   * @param time String format time
   * @return Long time value
   */
  def getTime(time: String) = {
    YYYYMMDDHHMMSS_FORMAT.parse(time).getTime
  }


  /**
   * parse the time to Minute
   * @param time String format time
   * @return String time value
   */
  def parseToMinute(time: String) = {
    TARGE_FORMAT.format(new Date(getTime(time)))
  }
}