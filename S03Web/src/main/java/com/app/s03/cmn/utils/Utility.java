package com.app.s03.cmn.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.AntPathMatcher;

import com.app.s03.cmn.exception.CommonBusinessException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 공통 유틸리티 클래스
 * @author 이호성
 * since 2018. 04. 06.
 */ 
//@Slf4j
public final class Utility {  
	private static ObjectMapper mapper = new ObjectMapper();
	private static SimpleDateFormat shortDateFmt = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat longDateFmt = new SimpleDateFormat("yyyy/MM/dd");
	
	private static SimpleDateFormat longToDateFmt = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat dayDateFmt = new SimpleDateFormat("MM/dd");
	
	private static SimpleDateFormat shortTimeFmt = new SimpleDateFormat("yyyyMMddHHmm");
	private static SimpleDateFormat LongTimeFmt = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat LongTimeFmtUnder = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
	private static SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	/**
	*	List Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터를 가지고있는 배열
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toJSON(List obj) throws Exception{
		return toNoDataCoverJSON(obj);
	}
	
	/**
	*	List Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터를 가지고있는 배열
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toNoDataCoverJSON(List obj) throws Exception{
		String jsonStr = "";
		Gson gson = new Gson();
        
        jsonStr = gson.toJson(obj);
        //jsonStr = "["+jsonStr+"]";
		return jsonStr;
	}	
	
	/**
	*	Map Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터 
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toJSON(Map obj) throws Exception{
		String jsonStr = "";
		Gson gson = new Gson();

        
        //Map Object 를 JSON 문자열로 변환
        jsonStr = gson.toJson(obj);
        jsonStr = "{\"DATA\":"+jsonStr+"}";
		return jsonStr;
	}
	
	/**
	*	Map Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터 
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toETCJSON(Map obj) throws Exception{
		String jsonStr = "";
		Gson gson = new Gson();

        
        //Map Object 를 JSON 문자열로 변환
        jsonStr = gson.toJson(obj);
        jsonStr = "{\"ETC\":"+jsonStr+"}";
		return jsonStr;
	}	
	/**
	*	List Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터를 가지고있는 배열
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toETCJSON(List obj) throws Exception{
		String jsonStr = "";
		Gson gson = new Gson();
		if(obj != null && obj.size()>0){
			Map mp = (Map)obj.get(0);
	        jsonStr = gson.toJson(mp);
	        jsonStr = "\"ETC\":"+jsonStr;
		}
		return jsonStr;
	}
	
	/**
	*	Map Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터 
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toJSONdefault(Map obj) throws Exception{
		String jsonStr = "";
		Gson gson = new Gson();
        
        //Map Object 를 JSON 문자열로 변환
        jsonStr = gson.toJson(obj);
		return jsonStr;
	}
	
	/**
	*	Map Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터 
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toJSONdefault(List obj) throws Exception{
		String jsonStr = "";
		Gson gson = new Gson();
        
        //Map Object 를 JSON 문자열로 변환
        jsonStr = gson.toJson(obj);
        jsonStr = jsonStr.replaceAll("\r", "");
		return jsonStr;
	}
	
	/**
	*	JSON 문자열을 출력한다.
	*	@param res  HttpServletResponse
	*	@param jsonStr json 형태의 문자열  
	*	@return void
	*   @throws Exception
	*/	
	public static void flushJSON(HttpServletResponse res, String jsonStr) throws Exception{
		PrintWriter out = new PrintWriter( new OutputStreamWriter(res.getOutputStream(), "utf-8") ) ;
		jsonStr = jsonStr.replaceAll("\r", "<br/>"); //두드림 결재창에서 엔터값 먹도록 처리함.
        out.print(jsonStr);
        out.flush();
        out.close();
	}
	
	
	/**
	*	JSON 문자열을 출력한다.
	*	@param res  HttpServletResponse
	*	@param jsonStr json 형태의 문자열  
	*	@return void
	*   @throws Exception
	*/	
	public static void flushJSONKR(HttpServletResponse res, String jsonStr) throws Exception{
		PrintWriter out = new PrintWriter( new OutputStreamWriter(res.getOutputStream(), "euc-kr") ) ;
        out.print(jsonStr);
        out.flush();
        out.close();
	}
	
	
	
	/**
	*	XML 문자열을 출력한다.
	*	@param res  HttpServletResponse
	*	@param xmlStr xml 형태의 문자열  
	*	@return void
	*   @throws Exception
	*/	
	public static void flushXML(HttpServletResponse res, String xmlStr) throws Exception{
		PrintWriter out = new PrintWriter( new OutputStreamWriter(res.getOutputStream(), "utf-8") ) ;
		
        StringBuffer xmlBuff = new StringBuffer();
        xmlBuff.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>\n") ;
        xmlBuff.append(xmlStr);
        out.print(xmlBuff.toString());
        out.flush();
        out.close();
	}
	
	
	


	public static List<?> getGridArray2(String jsonStr) throws Exception{
		List<Map> dataArr = new ArrayList<Map>();
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String,Object> map = objectMapper.readValue(jsonStr,  new TypeReference<Map<String, Object>>(){});
	
			dataArr = (ArrayList<Map>)map.get("data");
		}catch(Exception e){
			////log.error("JSON Error : "+e.toString());
			dataArr = new ArrayList<Map>();
			throw e;
		}
		return dataArr;
	}	
	
	public static List<Map<String, Object>> getGridArray3(String jsonStr){
		List<Map<String, Object>> dataArr = new ArrayList<Map<String, Object>>();
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String,Object> map = objectMapper.readValue(jsonStr,  new TypeReference<Map<String, Object>>(){});
	
			dataArr = (ArrayList<Map<String, Object>>)map.get("data");
		}catch(Exception e){
			////log.error("JSON Error : "+e.toString());
			dataArr = new ArrayList<Map<String, Object>>();
		}
		return dataArr;
	}	
	
	/**
	*	JSON 그리드 전체 로우 데이터를 DataClass 로 변환혀여 배열에 담아 리턴한다.
	*	@param jsonArr  json배열데이터 문자열
	*	@return ArrayList 전체 그리드데이터 배열
	*   @throws Exception
	*/	
	public static List getGridArrayMap(String jsonArr) throws Exception{
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement je = parser.parse(jsonArr);

		JsonElement dataJson = je.getAsJsonObject().get("data");
		JsonArray array = dataJson.getAsJsonArray();
		
		String key = "";
		String val = "";
		Iterator it = null;
		Map.Entry me = null;
		Map data = new HashMap();
		List dataArr = new ArrayList();
		 
		for(int i=0; i<array.size(); i++){
			data = new HashMap();
			it = (array.get(i).getAsJsonObject().entrySet()).iterator();
			
			while(it.hasNext()){
				me = (Map.Entry)it.next();
				
				key = me.getKey().toString().toUpperCase();
				val = me.getValue().toString();
				val = ConChar.replace(val, "\\n", "\n");
				val = ConChar.replace(val, "\\r", "");
				if(val.startsWith("\"")){
					val = val.substring(1);
				}
				if(val.endsWith("\"")){
					val = val.substring(0, val.length()-1);
				}
				data.put(key, val);
			}
			
			dataArr.add(data);
		}
		return dataArr;
	}

	/**
	 * 현재 일자를 yyyy-MM-dd 형태로 리턴한다.
	 *
	 * @return		String
	 */
	public static String getLongDate() {
		return toLongDate(null);
	}
	
	/**
	 * 현재 일자를 yyyyMMdd 형태로 리턴한다.
	 *
	 * @return		String
	 */
	public static String getShortDate() {
		return toShortDate(null);
	}
	/**
	 * 현재 일자를 yyyyMMddhhmmss 형태로 리턴한다.
	 *
	 * @return		String
	 */
	public static String getLongDateTime() {
		return LongTimeFmt.format(new Date());
	}
	
	
	/**
	 * 현재 일자를 yyyy_MM_dd_hh_mm 형태로 리턴한다.
	 *
	 * @return		String
	 */
	public static String getLongDateTimeUnder() {
		return LongTimeFmtUnder.format(new Date());
	}

	/**
	 * yyyyMMdd 형태의 날짜 문자열을 yyyy-MM-dd 형태의 날짜 문자열로 변환하여 리턴한다.
	 *
	 * @param		s String
	 * @return		String
	 */
	public static String toLongDate(String s) {
		Date t;
		if (s == null) {
			t = new Date();
		} else if( s.length() == 0 ){
			return "";
		} else {
		    s = replace(s, "-", "");

		    ParsePosition pos = new ParsePosition(0);
			t = shortDateFmt.parse(s, pos);
		}
		if (t == null) return s;
		return longToDateFmt.format(t);
	}
	
	
	/**
	 * yyyyMMdd 형태의 날짜 문자열을 yyyy/MM/dd HH:mm 형태의 날짜 문자열로 변환하여 리턴한다.
	 *
	 * @param		s String
	 * @return		String
	 */
	public static String toTimeDate(String s) {
		Date t;
		if (s == null) {
			t = new Date();
		} else if( s.length() == 0 ){
			return "";
		} else {
		    s = replace(s, "-", "");
		    ParsePosition pos = new ParsePosition(0);
			t = shortTimeFmt.parse(s, pos);
		}
		if (t == null) return s;
		return timeFmt.format(t);
	}
	
	
	/**
	 * yyyyMMdd 형태의 날짜 문자열을 yyyy-MM-dd 형태의 날짜 문자열로 변환하여 리턴한다.
	 *
	 * @param		s String
	 * @return		String
	 */
	public static String toLongToDate() {
		Date t = new Date();
		return longToDateFmt.format(t);
	}

	/**
	 * yyyy-MM-dd 형태의 날짜 문자열을 yyyyMMdd 형태의 날짜 문자열로 변환하여 리턴한다.
	 *
	 * @param		s String
	 * @return		String
	 */
	public static String toShortDate(String s) {
		Date t;
		if (s == null) {
			t = new Date();
		}else if( s.length() == 0 ){

			return "";

		} else {
		    s = replace(s, "-", "");
		    s = toLongDate(s);

			ParsePosition pos = new ParsePosition(0);
			t = longToDateFmt.parse(s, pos);
		}
		if (t == null) return s;
		return shortDateFmt.format(t);
	}
	
	/**
	 * yyyy-MM-dd 형태의 날짜 문자열을 MM/dd 형태의 날짜 문자열로 변환하여 리턴한다.
	 *
	 * @param		s String
	 * @return		String
	 */
	public static String toDayDate(String s) {
		Date t;
		if (s == null) {
			t = new Date();
		}else if( s.length() == 0 ){

			return "";

		} else {
		    s = replace(s, "-", "");
		    s = toLongDate(s);

			ParsePosition pos = new ParsePosition(0);
			t = longDateFmt.parse(s, pos);
		}
		if (t == null) return s;
		return dayDateFmt.format(t);
	}
	
	/**
	 * 현재 년도를 return 한다.
	 * @return 현재년도 
	 */
	public static String getYearString() {
		String yyyyMMdd = getShortDate();
		return yyyyMMdd.substring(0,4);
	}
	
	/**
	 * 현재 월을  return 한다.
	 * @return 현재 월
	 */
	public static String getMonthString() {
		String yyyyMMdd = getShortDate();
		return yyyyMMdd.substring(4,6);
	}
	
	/**
	 * 현재 월 기준 +/-한 월을  return 한다.
	 * @return 현재 월
	 */
	public static String getMonthStringSel(int val) {
		String yyyyMMdd = getShortDate();
		int month = Integer.parseInt(yyyyMMdd.substring(4,6));
		int value = month+val;
		if(value < 0){
			value = value+12;
		}else if(value > 12){
			value = value-12;
		}
		
		if(value == 0){
			return "12";
		}else if(value<10){
			return "0"+value;
		}else{
			return ""+value;
		}
	}
	/**
	 * 현재 일을  return 한다.
	 * @return 현재 일
	 */
	public static String getDayString() {
		String yyyyMMdd = getShortDate();
		return yyyyMMdd.substring(6,8);
	}
	
	/**
	 * 현재 월의 마지막일  return 한다.
	 * @return 현재 일
	 */
	public static String getLastDayString() {
		java.util.GregorianCalendar currDtim = new GregorianCalendar();
		return ""+Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 해당 일의 달에 마지막 일을 리턴한다. 
	 * @return 현재 일자
	 */
	public static String getLastDay(){
		return  getYearString()+ getMonthString()+getLastDayString();
	}
	
	/**
	 * 설정 일의 마지막 일을 리턴한다. 
	 * @return 현재 일자
	 */
	public static String getLastDay(String s){
		
		Date t;
		if (s == null) {
			t = new Date();
		} else if( s.length() == 0 ){
			return "";
		} else {
		    s = replace(s, "-", "");
		    
		    ParsePosition pos = new ParsePosition(0);
			t = shortDateFmt.parse(s, pos);
		}
		if (t == null) return s;
		
		String lastDay = "";
		GregorianCalendar gc= new GregorianCalendar();
	    gc.set(Integer.parseInt(s.substring(0,4)), Integer.parseInt(s.substring(4,6))-1, Integer.parseInt(s.substring(6,8)));
	    
	    lastDay = gc.get(Calendar.YEAR)+ "";
	    lastDay += gc.get(Calendar.MONTH)+1+ "";
	    lastDay += gc.getActualMaximum(Calendar.DAY_OF_MONTH)+ "";
		return toLongDate(lastDay);
	}

	/**
	 * 숫자 세자리마다 , 를 삽입한다.
	 * @param		str String 숫자형식의 String
	 * @return		String ,가 삽입된 숫자형식의 String
	 */
	public static String getCommaNum(String str) {
		return getCommaNum(str, "###,###.###");
	}
	
	
	public static String getCommaNum(String str, String form) {
		String rtn = "";
		if(str == null || "".equals(str)) {
			rtn = "";
		} else {
			try {
				java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
				java.text.DecimalFormat df = (java.text.DecimalFormat)nf ;
				df.applyPattern(form);
				rtn = df.format(Double.valueOf(str)) ;
			} catch(Exception e) {
				//rtn = "";
				//출력물 스트링 반환
				rtn = str;
			}
		}
		return rtn;
	}

	/**
	 * String을 받아 oldstr String을 찾아 newstr String으로 바꿔준다.
	 *
	 * @param		original String 원본
	 * @param		oldstr String 바뀔 String
	 * @param		newstr String 바꿀 String
	 * @return		String
	 */
	public static String replace(String original, String oldstr, String newstr) {
		StringBuffer convert = new StringBuffer();
		int pos = 0;
		int begin = 0;
		pos = original.indexOf(oldstr);

		if(pos == -1)
			return original;

		while(pos != -1) {
			convert.append(original.substring(begin, pos) + newstr);
			begin = pos + oldstr.length();
			pos = original.indexOf(oldstr, begin);
		}
		convert.append(original.substring(begin));

		return convert.toString();
	}


	/**
	 * 문자열의 byte 수를 리턴한다.
	 *
	 * @param		in String
	 * @return		int
	 */
	public static int getBytesLength(String in) {
		if(in == null) {
			return 0;
		} else {
			try {
				return in.getBytes("KSC5601").length;
			} catch(Exception e) {
				return 0;
			}
		}
	}

	/**
	 * day 수 만큼 +,- 한 일자를 리턴한다.
	 *
	 * @param		day int
	 * @return		String
	 */
	public static String getDateAddString(int day) {
		java.util.GregorianCalendar currDtim = new GregorianCalendar();
		currDtim.add(Calendar.DATE, day);
		java.sql.Date date = new java.sql.Date(currDtim.getTime().getTime());

		return toLongDate(date.toString());
	}
	
	
	
	/**
	 * 넘겨받은 날짜형 String에 day 수 만큼 +,- 한 일자를 리턴한다.
	 * @param s String 
	 * @param day int
	 * @return String 
	 */
	public static String getDateAddString(String s, int day) {
		Date t;
		if (s == null) {
			t = new Date();
		} else if( s.length() == 0 ){
			return "";
		} else {
		    s = replace(s, "-", "");
		    
		    ParsePosition pos = new ParsePosition(0);
			t = shortDateFmt.parse(s, pos);
		}
		if (t == null) return s;
		
		GregorianCalendar gc= new GregorianCalendar();
	    gc.set(Integer.parseInt(s.substring(0,4)), Integer.parseInt(s.substring(4,6))-1, Integer.parseInt(s.substring(6,8)));
	    
	    gc.add(Calendar.DATE, day);
		java.sql.Date date = new java.sql.Date(gc.getTime().getTime());
		return toLongDate(date.toString());
	}

	/**
	 * month 수 만큼 +,- 한 일자를 리턴한다.
	 *
	 * @param		mon int
	 * @return		String
	 */
	public static String getMonthAddString(int mon) {
		java.util.GregorianCalendar currDtim = new GregorianCalendar();
		currDtim.add(Calendar.MONTH, mon);
//		currDtim.add(Calendar.DATE, -1);
		java.sql.Date date = new java.sql.Date(currDtim.getTime().getTime());

		return toLongDate(date.toString());
	}
	/**
	 * year 수 만큼 +,- 한 일자를 리턴한다.
	 *
	 * @param		year int
	 * @return		String
	 */
	public static String getYearAddString(int year) {
		java.util.GregorianCalendar currDtim = new GregorianCalendar();
		currDtim.add(Calendar.YEAR, year);
//		currDtim.add(Calendar.DATE, -1);
		java.sql.Date date = new java.sql.Date(currDtim.getTime().getTime());

		return toLongDate(date.toString());
	}

	/**
	 * String => int 변환
	 *
	 * @param		str String
	 * @return		int
	 */
    public static int convInt(String str) {
        return convInt( str, "0" );
    }

	/**
	 * String => int 변환. str이 null이면 def 사용
	 *
	 * @param		str String
	 * @param		def String def
	 * @return		int
	 */
    public static int convInt(String str, String def) {
        str = replace(nullCheck(str), ",", "");
        if(str.indexOf(".") > -1) {
        	str = str.substring(0, str.indexOf("."));
        }
        if("".equals(str)) str = def;
        return Integer.parseInt(str);
    }

	/**
	 * String => double 변환
	 *
	 * @param		str String 
	 * @return		double
	 */
    public static double convDouble(String str) {
        return convDouble( str, "0" );
    }


	/**
	 * String => double 변환. str이 null이면 def 사용
	 *
	 * @param		str String
	 * @param		def String
	 * @return		double
	 */
    public static double convDouble(String str, String def) {
        str = replace(nullCheck(str), ",", "");
        if("".equals(str)) str = def;
        return Double.parseDouble(str);
    }
    
    /**
	 * String => long 변환. str이 null이면 def 사용
	 *
	 * @param		str String
	 * @param		def String
	 * @return		double
	 */
    public static long convLong(String str, String def) {
        str = replace(nullCheck(str), ",", "");
        if("".equals(str)) str = def;
        return Long.parseLong(str);
    }


	/**
	 * null => ""으로 변환
	 *
	 * @param		param String
	 * @return		String
	 */
	public static String nullCheck(String param){
        if(param == null || "null".equals(param)){
            param =  "";
        }
		return param.trim();
    }

	/**
	 * HTML 문자( < > " & ) => &lt; &gt; &quot; &amp;nbsp; 로 변환
	 *
	 * @param		htmlstr String
	 * @return		String
	 */
	public static String convTag2Char(String htmlstr)	{
		String convert = new String();
		convert = replace(htmlstr,"<", "&lt;");
		convert = replace(convert, ">", "&gt;");
		convert = replace(convert, "\"", "&quot;");
		convert = replace(convert, "&nbsp;", "&amp;nbsp;");
		return convert;
	}

	/**
	 * &lt; &gt; &quot; &amp;nbsp; 를 HTML 문자( < > " & ) 로 변환
	 *
	 * @param		str String
	 * @return		String
	 */
	public static String convChar2Tag(String str) {
		String convert = new String();
		convert = replace(str, "&lt;", "<");
		convert = replace(convert,"&gt;", ">");
		convert = replace(convert,"&quot;", "\"");
		convert = replace(convert, "&amp;nbsp;", "&nbsp;");
		return convert;
	}
	

	/**
	 * 주어진 String을 encoding한다.
	 *
	 * @param		str String
	 * @return		String
	 */
	public static String ksc2ascii(String str){
	   	String result = null;
		try {
          result = new String(str.getBytes("KSC5601"),"8859_1");
       	} catch(Exception e) {}
       	return result;
    }
	/**
	 * 주어진 String을 encoding한다.
	 *
	 * @param		str String
	 * @return		String
	 */
	public static String ascii2ksc(String str){
	   	String result = null;
		try {
          result = new String(str.getBytes("8859_1"),"KSC5601");
       	} catch(Exception e) {}
       	return result;
    }
	
 
	/**
     * 8859-1로 encoding 한다.
     * @param str
     * @return
     */
    public static String toascii(String str){
        String result = null;
        try{
            result = new String(str.getBytes(),"8859_1");
        } catch(Exception e) {}
        return result;
    }
	/**
     * EUC-KR로 encoding 한다.
     * @param str
     * @return
     */
    public static String toksc(String str){
        String result = null;
        try{
            result = new String(str.getBytes(),"KSC5601");
        } catch(Exception e) {}
        return result;
    }

	 
	 
	/**
	 * String을 start부터 substring 한다.
	 *
	 * @param attFile String 파일정보
	 * @return int
	 */
	public static String cutString(String str, int start) {
	    if(str.length() > start) {
	    	return str.substring(start);
	    } else {
	    	return "";
	    }
	}
	
	/**
	 * String을 byte 개수만큼 잘라낸다. (한글이 잘리는 경우 고려)
	 *
	 * @param attFile String 파일정보
	 * @return int
	 */
	public static String cutStringBytes(String str, int length) {
	    byte[] bytes = str.getBytes();
	    int len = bytes.length;
	    int counter = 0;
	    if(length >= len) {
	    	return str;
	    }
		for(int i = length - 1; i >= 0; i--) {
			if((bytes[i] & 0x80) != 0)
				counter++;
		}
	    return new String(bytes, 0, length - (counter % 2));
	}
	
	/**
	 * 현재 월,일,요일을 가져온다.  
	 *
	 * @return string
	 */
	public static String getWeek(){
		
		Calendar time = Calendar.getInstance();
		
		final String[] week = { "일" , "월" , "화" , "수" , "목" , "금" , "토" };
		
		String yyyyMMdd = getShortDate();
		String MM = yyyyMMdd.substring(4,6);//월
		
		String dd = yyyyMMdd.substring(6,8);//일
		
		StringBuffer timing = new StringBuffer();
		
		timing.append( MM +"월 " + dd + "일 " + week[time.get(Calendar.DAY_OF_WEEK)-1] +  "요일" );
		
		String weekday = timing.toString();
		return weekday;
		
	}

	
	/** 
	 * 연도관련 셀렉트박스를 생성한다.
	 * @param  int min(현재년도로부터 몇년전부터..)
	 * @param int max(현재년도로부터 몇년후까지..)
	 * @param String isAll(all:전체, sel:선택)
	 * @param String selYear(선택연도)
	 * @return String
	 */
	public static String mkSelBoxYear(int min, int max, String selYear){
		StringBuffer buff = new StringBuffer();
		
		String nowYear = getYearString();//현재연도를 가져온다.
		
		int minYear = Integer.parseInt(nowYear) - min;
		int maxYear = Integer.parseInt(nowYear) +max;
		
		for(int i=maxYear; i>=minYear; i--) {
			buff.append("\t\t<option value='"+i+"'");
			
			if(!"".equals(selYear)){
				if((i+"").equals(selYear)) buff.append(" selected='yes' ");
			}else{
				if((i+"").equals(nowYear)) buff.append(" selected='yes' ");
			}
			
			
			buff.append(">");
			buff.append(i);
			buff.append("</option>\n");
		}
		
		return buff.toString();
	}
	
	/** 
	 * 연도관련 셀렉트박스를 생성한다.
	 * @param int min(현재년도로부터 몇년전부터..)
	 * @param int max(현재년도로부터 몇년후까지..)
	 * @param String selYear(선택연도)
	 * @param int term(현재년도로부터 몇년전 값을 디폴트선택할것인가..)
	 * @return String
	 */
	public static String mkSelBoxYearTerm(int min, int max, String selYear, int term){
		StringBuffer buff = new StringBuffer();
		
		String nowYear = getYearString();//현재연도를 가져온다.
		
		int minYear = Integer.parseInt(nowYear) - min;
		int maxYear = Integer.parseInt(nowYear) +max;
		
		for(int i=maxYear; i>=minYear; i--) {
			buff.append("\t\t<option value='"+i+"'");
			
			if(!"".equals(selYear)){
				if((i+"").equals(selYear)) buff.append(" selected='yes' ");
			}else{
				//term 값이 현재년도보다 클순없다.
				if(Integer.parseInt(nowYear) > term){
					if((i == (Integer.parseInt(nowYear) + term))) buff.append(" selected='yes' ");
				}
			}
			
			
			buff.append(">");
			buff.append(i);
			buff.append("</option>	\n");
		}
		
		return buff.toString();
	}
	
	/** 
	 * 시작년도 설정가능한 연도관련 셀렉트박스를 생성한다.
	 * @param  int start(시작년도)
	 * @param int end(현재년도 기준으로부터 몇년 전후까지.. ex: -1)
	 * @param String selYear(선택연도)
	 * @return String
	 */
	public static String mkSelBoxYearStart(int start, int end, String selYear){
		StringBuffer buff = new StringBuffer();
		
		String nowYear = getYearString();//현재연도를 가져온다.
		int startYear = start;
		
		int endYear = Integer.parseInt(nowYear) + end;
				
		for(int i=endYear; i>=startYear; i--) {
			buff.append("\t\t<option value='"+i+"'");
			
			/*if(!"".equals(selYear)){
				if((i+"").equals(selYear)) buff.append(" selected='yes' ");
			}else{
				if((i+"").equals(nowYear)) buff.append(" selected='yes' ");
			}	*/		
			
			buff.append(">");
			buff.append(i);
			buff.append("</option>\n");
		}
		
		return buff.toString();
	}
	
	/** 
	 * 월관련 셀렉트박스를 생성한다.
	 * @param String selMonth(선택 월)
	 * @return String
	 */
	public static String mkSelBoxMonth(String selMonth){
		StringBuffer buff = new StringBuffer();
		
		String nowMonth = getMonthString();
		
		if(selMonth == null || "".equals(selMonth.trim())){
			selMonth = nowMonth;
		}
		
		for(int i=1; i<=12; i++) {
			
			if((i+"").length() ==1){
				buff.append("\t\t<option value='0"+i+"'");
			
				if(!"".equals(selMonth)){
					if(("0"+i).equals(selMonth)) buff.append(" selected='yes' ");
				} 
				
			}else{
				buff.append("\t\t<option value='"+i+"'");
				
				if(!"".equals(selMonth)){
					if((i+"").equals(selMonth)) buff.append(" selected='yes' ");
				} 
			}
			
			buff.append(">");
			
			if((i+"").length() ==1){
				buff.append("0"+i);
			}else{
				buff.append(i);
			}
			
			
			buff.append("</option>\n");
		}
		
		return buff.toString();
	}
	
	
	/** 
	 * 월관련 셀렉트박스를 생성한다.
	 * @param String selMonth(선택 월)
	 * @return String
	 */
	public static String mkSelBoxWeek(String selMonth){
		StringBuffer buff = new StringBuffer();
		
		String nowMonth = getMonthString();
		
		if(selMonth == null || "".equals(selMonth.trim())){
			selMonth = nowMonth;
		}
		
		for(int i=1; i<=52; i++) {
			
			if((i+"").length() ==1){
				buff.append("\t\t<option value='0"+i+"'");
			
				if(!"".equals(selMonth)){
					if(("0"+i).equals(selMonth)) buff.append(" selected='yes' ");
				} 
				
			}else{
				buff.append("\t\t<option value='"+i+"'");
				
				if(!"".equals(selMonth)){
					if((i+"").equals(selMonth)) buff.append(" selected='yes' ");
				} 
			}
			
			buff.append(">");
			
			if((i+"").length() ==1){
				buff.append("0"+i);
			}else{
				buff.append(i);
			}
			
			
			buff.append("</option>\n");
		}
		
		return buff.toString();
	}
	
	/** 
	 * 일자 관련 셀렉트박스를 생성한다.
	 * @param String selDate(선택 일)
	 * @return String
	 */
	public static String mkSelBoxDate(String selDate){
		StringBuffer buff = new StringBuffer();
		
		for(int i=1; i<=31; i++) {
			
			if((i+"").length() ==1){
				buff.append("\t\t<option value='0"+i+"'");
			
				if(!"".equals(selDate)){
					if(("0"+i).equals(selDate)) buff.append(" selected='yes' ");
				} 
				
			}else{
				buff.append("\t\t<option value='"+i+"'");
				
				if(!"".equals(selDate)){
					if((i+"").equals(selDate)) buff.append(" selected='yes' ");
				} 
			}
			
			buff.append(">");
			
			if((i+"").length() ==1){
				buff.append("0"+i);
			}else{
				buff.append(i);
			}
			
			
			buff.append("</option>\n");
		}
		
		return buff.toString();
	}
	
	/** 
	 * 분기관련 셀렉트박스를 생성한다.
	 * @param String selQuarter(선택 분기)
	 * @return String
	 */
	public static String mkSelBoxQuarter(String selQuarter){
		StringBuffer buff = new StringBuffer();
		
		for(int i=1; i<=4; i++) {
			
			if((i+"").length() ==1){
				buff.append("\t\t<option value='"+i+"'");
			
				if(!"".equals(selQuarter)){
					if((""+i).equals(selQuarter)) buff.append(" selected='yes' ");
				} 
				
			}else{ 
				buff.append("\t\t<option value='"+i+"'");
				
				if(!"".equals(selQuarter)){
					if((""+i).equals(selQuarter)) buff.append(" selected='yes' ");
				} 
			}
			
			buff.append(">");
			
			if((i+"").length() ==1){
				buff.append("0"+i);
			}else{
				buff.append(i);
			}
			buff.append("분기");
			
			buff.append("</option>\n");
		}
		
		return buff.toString();
	}
	
	/** 
	 * 시간관련 셀렉트박스를 생성한다.
	 * @param String selTime(선택시간)
	 * @param String selTime(선택시간)
	 * @return String
	 */
	public static String mkSelBoxTime(String selTime){
		StringBuffer buff = new StringBuffer();
		
		for(int i=0; i<=23; i++) {
			buff.append("\t\t<option value='"+i+"'");
			
			if(!"".equals(selTime)){
				if((i+"").equals(selTime)) buff.append(" selected='yes' ");
			} 
			buff.append(">");
			
			if((i+"").length() ==1){
				buff.append("0"+i);
			}else{
				buff.append(i);
			}
			
			buff.append("</option>\n");
		}
		
		return buff.toString();
	}
	
	
	/** 
	 * 분 관련 셀렉트박스를 생성한다.
	 * @param String term(몇분단위로 표현할것인지)
	 * @param String selMin(선택 분)
	 * @return String
	 */
	public static String mkSelBoxMin(int term, String selMin){
		StringBuffer buff = new StringBuffer();
		
		for(int i=0; i<=59; i++) {
			
			if(i%term == 0 && term != 0){
				buff.append("\t\t<option value='"+i+"'");
				
				if(!"".equals(selMin)){
					if((i+"").equals(selMin)) buff.append(" selected='yes' ");
				} 
				
				buff.append(">");
				
				if((i+"").length() ==1){
					buff.append("0"+i);
				}else{
					buff.append(i);
				}
				
				buff.append("</option>\n");
			}
		}
		
		return buff.toString();
	}
	
	
	
	
	/** 
	 * 파일이 저장되어있는 full경로를 어플리케이션 경로로 변경시켜준다.
	 * @param fullPath String 파일이 저장되어있는 full 경로
	 * @ex D:/project/upload/mp/1354236009855.jpg -> /mp/1354236009855.jpg
	 * @return String 파일의 어플리케이션 경로
	 */
	public static String getFilePath(String fullPath) {
		
		String str = nullCheck(fullPath);
		if(str.indexOf("upload") >  -1){
			str = str.substring(str.indexOf("upload")+6);
			str = str.replaceAll("\\\\", "/");
		}
		
		
		
		return str;
	}
	
	/** 
	 * 파일이 image 파일인지 판단한다
	 * @param 파일full경로 또는 파일명 (확장자포함) 
	 * @return Boolean 이미지파일여부
	 */
	public static Boolean isImageFile(String str) {
		String ext = "";
		int idx = str.lastIndexOf(".");
		if(idx > -1){
			ext = str.substring(idx+1).toLowerCase();//확장자			
			if(ext.equals("jpg") || ext.equals("jpeg") || ext.equals("jpe") || 
					ext.equals("png") || ext.equals("bmp") || ext.equals("gif")){
				return true;
			}	
		}
		return false;
	}
	
	
	
	/** 
	 * 현사이트의 도메인을 가져온다.
	 * @param request
	 * @return 도메인명
	 */
	public static String getSiteDomain(HttpServletRequest request) {
		
		StringBuffer sb = request.getRequestURL();
		String domainStr = sb.toString();
		
		if(domainStr.indexOf("/ehs") > -1){
			domainStr = domainStr.substring(0, domainStr.indexOf("/ehs"));
		} 
		return domainStr;
	}
	

	/**
	*	Map Object 를 JSON 문자열로 변환한다.
	*	@param obj Map데이터 
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toSuccessJSON() throws Exception{
		
		/*
		JSONObject result = new JSONObject();
		JSONObject inside = new JSONObject();
		
		inside.put("Code", 1);
		result.put("Result", inside);
		error = error.replaceAll("\r\n", "\\r\\n");
		String jsonStr = "";
		jsonStr +=("{");
		jsonStr +=("Result:{Code:-1, Message:\"Error Message \\n"+error+"\"}");
		jsonStr +=("}");
		
		*/
		String jsonStr = "";
		jsonStr +=("{Result:{Code:1, Message:\"\"}}");
		return jsonStr;
	}
		
	/**
	*	오류사항에 대해 메시지 처리.
	*	@param obj Map데이터 
	*	@return String json형태의 문자열
	*   @throws Exception
	*/
	public static String toErrorJSON(String error) throws Exception{
		String jsonStr = "";
		jsonStr +=("{Result:{Code:-1, Message:\"\\n"+error+"\"}}");
		return jsonStr;
	}	
	
	public static List<Map<String, String>> getGridData(HttpServletRequest request, String colNM) {
		List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
		
		Map paramMap = Collections.synchronizedSortedMap( new TreeMap<String,String[]>(request.getParameterMap()));
		List colList = ConChar.stringToArrayList(colNM, ",");
		//Debug.println(""+paramMap.toString());
		if(ConChar.isNull(colNM)) return new ArrayList();
		
		synchronized(paramMap){
			Map dmyMap;
			String [] targetM = (String [])paramMap.get(colList.get(0));
			for(int i = 0; i < targetM.length; i++){
				dmyMap = new HashMap();
				for(int j=0; j<colList.size(); j++){
					dmyMap.put(colList.get(j)    , ((String [])paramMap.get(colList.get(j)))[i]);
				}
				paramList.add(dmyMap);
			}// end of for
			
		}
		
		return paramList;
	}	
	
	/**
	 * 문자열 JSON DATA --> Map<String, Object> 으로 파싱.
	 * @param jsonData
	 * @return
	 */
	public static Map<String, Object> readJsonData(String jsonData) {
		try {
			return mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new CommonBusinessException(e);
		} catch (JsonMappingException e) {
			e.printStackTrace();
            throw new CommonBusinessException(e);
		} catch (IOException e) {
			e.printStackTrace();
            throw new CommonBusinessException(e);
		}
	}

	/**
	 * url 으로 부터 프로그램코드 뽑기.
	 * @param url
	 * @return
	 */
	public static String getPrograCdFromUrl(String url) {
		if (url == null) {
			return null;
		}
		AntPathMatcher pathMatcher = new AntPathMatcher();
		if (pathMatcher.match("/page/cmn/**/*", url)) {
			return null;
		}
		if (pathMatcher.match("/page/*/*", url)) {
			String[] paths = url.split("/");
			if (paths[3].length() >= 2 && StringUtils.isAlphanumeric(paths[3].substring(0, 2))) {
				return paths[3].substring(0, 2);
			}
			return null;
		}
		if (pathMatcher.match("/page/*/*/*", url)) {
			String[] paths = url.split("/");
			if (paths[4].length() >= 6 && StringUtils.isAlphanumeric(paths[4].substring(0, 6))) {
				return paths[4].substring(0, 6);
			}
		}

		if (pathMatcher.match("/api/cmn/**/*", url)) {
			return null;
		}
		if (pathMatcher.match("/api/*/common/*", url)) {
			return null;
		}
		if (pathMatcher.match("/api/*/*/*/*", url)) {
			String[] paths = url.split("/");
			if (paths[4].length() == 6 && StringUtils.isAlphanumeric(paths[4])) {
				return paths[4];
			}
			return null;
		}
		return null;
	}

	/**
	 * underscore ('_') 가 포함되어 있는 문자열을 Camel Case ( 낙타등
	 * 표기법 - 단어의 변경시에 대문자로 시작하는 형태. 시작은 소문자) 로 변환해주는
	 * utility 메서드 ('_' 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리
	 * 함.)
	 * @param underScore
	 *        - '_' 가 포함된 변수명
	 * @return Camel 표기법 변수명
	 */
	public static String convert2CamelCase(String underScore) {

		// '_' 가 나타나지 않으면 이미 camel case 로 가정함.
		// 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
		// 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
		if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
			return underScore;
		}
		return JdbcUtils.convertUnderscoreNameToPropertyName(underScore);
	}


	public static String SHA(String str) throws Exception {
		StringBuffer sb = new StringBuffer();
		int i;
		String data = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			byte[] mdBytes = md.digest();

			data = byteAsString(mdBytes);

			return data;
			// return new BASE64Encoder().encode(mdBytes);
		} catch (NoSuchAlgorithmException e) {
		}
		return data;
	}
	public static String byteAsString(byte[] dataBytes) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dataBytes.length; i++) {
			String hex = Integer.toHexString(0xff & dataBytes[i]);
			if (hex.length() == 1)
				sb.append('0');
			sb.append(hex);
		}
		return sb.toString();
	}
	
	/**
	 * jsp 연도 콤보 설정 (다음달)
	 * -1, 금년, +1
	 * <option value='yyyy'>yyyy</option>
	 * @return String
	 */
	public static String getYear2() {
		StringBuffer retData = new StringBuffer();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		int iYear = Integer.parseInt(sdf.format(date));
		int iMonth = Integer.parseInt(sdf2.format(date)); 
		
		if (iMonth == 12) {iYear += 1;}
		
		for (int i = -1; i <= 1; i++) {
			retData.append("<option value='");
			retData.append(iYear + i);
			if (0 == i) {
				retData.append("' selected>");	
			} else {
				retData.append("'>");
			}
			retData.append(iYear + i);
			retData.append("</option>\n");
		}
		return retData.toString();
	}

	/**
	 * jsp 월 콤보 설정(다음 달)
	 * 월설정
	 * <option value='01'>01</option>
	 * @return String
	 */
	public static String getMonth2() {
		StringBuffer retData = new StringBuffer();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		int iMonth = Integer.parseInt(sdf.format(date));
		
		if (iMonth == 12) {
			iMonth = 1;
		}else {
			iMonth += 1;
		}
		
		for (int i = 1; i <= 12; i++) {
			String sMonth = String.valueOf(i);
			if (i < 10) sMonth = "0" + i;
			
			retData.append("<option value='");
			retData.append(sMonth);
			if (i == iMonth) {
				retData.append("' selected>");	
			} else {
				retData.append("'>");
			}
			retData.append(sMonth);
			retData.append("</option>\n");
		}
		return retData.toString();
	}	

}
