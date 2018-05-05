package common

import grails.converters.JSON
import java.text.SimpleDateFormat

class DateService {

	def defaultPattern = 'yyyy-MM-dd HH:mm:ss'
	def yearToMinPattern = 'yyyy-MM-dd HH:mm'

	//取得預設格式當地目前時間(String)
	def getUserStrDate(timeZoneId) {
		def strDate = new Date().format(defaultPattern, getUserTimeZone(timeZoneId))
	}

	//取得指定格式的當地目前時間(String)
	def getUserStrDate(format, timeZoneId) {
		if (format)
			def strdate = new Date().format(format, getUserTimeZone(timeZoneId))
		else
			def strdate = getUserStrDate(timeZoneId)
	}

	/** not yet used **/
	//取得UTC目前時間(String)
	def getStrDate() {
		def strDate = new Date().format(defaultPattern, TimeZone.getTimeZone("UTC"))
	}

	/** not yet used **/
	//取得指定格式的UTC目前時間(String)
	def getStrDate(format) {
		if (format)
			def strdate = new Date().format(format, TimeZone.getTimeZone("UTC"))
		else
			def strdate = getStrDate()
	}

	/** not yet used **/
	//取得格式年-月-日 時:分 的UTC目前時間(String)
	def getStrDateYEARtoMIN() {
		getStrDate(yearToMinPattern)
	}

	/** not yet used **/
	//取得格式年-月-日 時:分 的UTC目前時間(Date)
	def getDateYEARtoMIN() {
		Date.parse(yearToMinPattern, getStrDateYEARtoMIN())
	}

	//取得當地時區 timeZoneId: GMT+8
	def getUserTimeZone(timeZoneId) {
        return TimeZone.getTimeZone(timeZoneId)
	}

	//將指定的UTC時間(Date)->轉換為UTC時間(String) 依指定格式
	//Sun May 31 16:00:00 UTC 2015 -> 2015-05-31 16:00:00
	def formatWithFormat(date, format) {
		if (date && format)
			def strDate = date.format(format)
		else
			null
	}

	//將指定的UTC時間(Date)->轉換為UTC時間(String) ISO8601格式 ex: 2014-04-01T14:04:01Z
	//Sun May 31 16:00:00 UTC 2015 -> 2015-05-31T16:00:00Z
	def formatWithISO8601(date) {
		formatWithFormat(date, "yyyy-MM-dd'T'HH:mm:ss'Z'")
	}

	/** not yet used **/
	//將指定的UTC時間(Date)->轉換為當地時間(String) 依指定格式
	//Sun May 31 16:00:00 UTC 2015 -> 2015-06-01 00:00:00
	def formatWithUserFormat(date, format, timeZoneId) {
		if (date && format)
			def strDate = date.format(format, getUserTimeZone(timeZoneId))
		else
			null
	}

	/** not yet used **/
	//將指定的UTC時間(Date)->轉換為當地時間(String) 年-月-日 時:分 格式 ex: 2014-04-01 14:04
	//Sun May 31 16:00:00 UTC 2015 -> 2015-06-01 00:00
	def formatWithUserYEARtoMIN(date, timeZoneId) {
		formatWithUserFormat(date, yearToMinPattern, timeZoneId)
	}

	//將指定的UTC時間(String)->轉換為UTC時間(Date)
	//ex:yyyy-MM-dd HH:mm:ss 2015-05-31 16:00:00 -> Sun May 31 16:00:00 UTC 2015
	def parseUTCToUTC(format, strDate) {
		def date = Date.parse(format, strDate)
		return date
	}

	//將指定的UTC時間(String) ISO8601格式->轉換為UTC時間(Date)
	//ex:yyyy-MM-ddTHH:mm:ss.sssZ 2015-05-31T16:00:00.000Z -> Sun May 31 16:00:00 UTC 2015
	//ex:yyyy-MM-ddTHH:mm:ssZ 2015-05-31T16:00:00Z -> Sun May 31 16:00:00 UTC 2015
	def parseUTCISO8601ToUTC(strDate) {
		def date
		if (strDate ==~ /^\d\d\d\d\-\d\d\-\d\dT\d\d:\d\d:\d\d.\d\d\dZ$/) {
			date = parseUTCToUTC("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", strDate)
		}
		else if (strDate ==~ /^\d\d\d\d\-\d\d\-\d\dT\d\d:\d\d:\d\dZ$/) {
			date = parseUTCToUTC("yyyy-MM-dd'T'HH:mm:ss'Z'", strDate)
		}
		return date
	}

	//將指定的當地時間(String)->轉換為UTC時間(Date)
	//ex:yyyy-MM-dd HH:mm:ss 2015-06-01 00:00:00 -> Sun May 31 16:00:00 UTC 2015
	def parseUserToUTC(format, strDate, timeZoneId) {
		//須先將TimeZone預設時區轉換為當地時區 才可使用format轉換為UTC
		TimeZone.setDefault(getUserTimeZone(timeZoneId))
		def date = Date.parse(format, strDate)
		strDate = date.format(defaultPattern, TimeZone.getTimeZone("UTC"))
		TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
		date = Date.parse(defaultPattern, strDate)
		return date
	}

	//將指定的當地時間(String) ISO8601格式->轉換為UTC時間(Date)
	//ex:2015-06-01T00:00:00Z -> Sun May 31 16:00:00 UTC 2015
	def parseUserISO8601ToUTC(strDate, timeZoneId) {
		return parseUserToUTC("yyyy-MM-dd'T'HH:mm:ss'Z'", strDate, timeZoneId)
	}

	/** not yet used **/
	//將指定的當地時間(String) CST格式->轉換為UTC時間(Date)
	//ex:Sat Jul 25 2015 00:00:00 GMT+0800 (CST) -> Fri Jul 24 16:00:00 UTC 2015
	def parseUserCSTToUTC(strDate, timeZoneId) {
		//須先將TimeZone預設時區轉換為當地時區 才可使用format轉換為UTC
		TimeZone.setDefault(getUserTimeZone(timeZoneId))
		def format = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z '(CST)'"
		def date = new SimpleDateFormat(format, Locale.US).parse(strDate)
		strDate = date.format(defaultPattern, TimeZone.getTimeZone("UTC"))
		TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
		date = Date.parse(defaultPattern, strDate)
		return date
	}

}
