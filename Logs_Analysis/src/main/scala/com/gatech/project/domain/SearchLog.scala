package com.gatech.project.domain
/**
 * logs data after cleaning
 * @param ip ip address
 * @param time time
 * @param languageId  course id
 * @param statusCode status
 * @param referer referer
 */
case class SearchLog(ip: String, time: String, languageId: Int, statusCode: Int, referer: String)

