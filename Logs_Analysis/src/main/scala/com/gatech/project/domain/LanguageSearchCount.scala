package com.gatech.project.domain

/**
 *
 * @param language HBase row key: 20201111_1
 * @param search_count corresponding total sum
 */
case class LanguageSearchCount(language:String, search_count:Long)