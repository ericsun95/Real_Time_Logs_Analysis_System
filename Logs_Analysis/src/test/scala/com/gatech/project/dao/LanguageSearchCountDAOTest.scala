package com.gatech.project.dao

import org.junit._
import Assert._
import com.gatech.project.domain.LanguageSearchCount

import scala.collection.mutable.ListBuffer

@Test
class LanguageSearchCountDAOTest {

  @Test
  def LanguageSearchCountDAOTest() = {
    val list = new ListBuffer[LanguageSearchCount]
    list.append(LanguageSearchCount("20200111_7",8))
    list.append(LanguageSearchCount("20200111_8",9))
    list.append(LanguageSearchCount("20200111_9",100))

    LanguageSearchCountDAO.saveOnce(list)

    assert(LanguageSearchCountDAO.count("20200111_7") == 8.toLong)
    assert(LanguageSearchCountDAO.count("20200111_8") == 9.toLong)
    assert(LanguageSearchCountDAO.count("20200111_9") == 100.toLong)

    val list2 = new ListBuffer[LanguageSearchCount]
    list2.append(LanguageSearchCount("20200111_7",8))
    list2.append(LanguageSearchCount("20200111_8",9))
    list2.append(LanguageSearchCount("20200111_9",100))
    LanguageSearchCountDAO.save(list2)
    assert(LanguageSearchCountDAO.count("20200111_7") == 16.toLong)
    assert(LanguageSearchCountDAO.count("20200111_8") == 18.toLong)
    assert(LanguageSearchCountDAO.count("20200111_9") == 200.toLong)


  }

}
