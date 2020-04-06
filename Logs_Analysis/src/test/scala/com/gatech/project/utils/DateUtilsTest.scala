package com.gatech.project.utils

import org.junit._
import Assert._

@Test
class DateUtilsTest {

  @Test
  def DateUtilsTest() = {
    assert(DateUtils.parseToMinute("2020-10-22 14:46:01") == "20201022144601")
  }

}