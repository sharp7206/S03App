package com.app.s03.cmn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;

/**
 * Character Conversion에 관련된 Class.
 * <p>
 * Character 변환하는 메서드인 E2K()와 K2E() 및 retVal() 메서드는
 * <p>
 * Servlet엔진에 Dependent하기에 Private으로 선언해주었고,
 * <p>
 * 개발자들은 직접적으로 이들 메서드를 호출하는 대신
 * <p>
 * 작업성격에 따라 insStr(), selStr(), readName() 및 writeName()을 호출하여야 한다.
 * <p>
 * 나머지 기존 Global Function에서 구현되었던 메서드들은 deprecated됨을 알린다.
 * <p>
 * 
 */

public class ConChar implements java.io.Serializable {
	private static Log log = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6352093140617141678L;

	public ConChar() {
	}

	/**
	 * URL 에 한글값을 같이 넘겨줄때 Encoding 해주는 메서드
	 * 
	 * @param string
	 * @return string
	 * @see java.net.URLEncoder
	 */

	public static synchronized String URLencode(String string) {
		return java.net.URLEncoder.encode(convertNull(string));
	}

	/**
	 * URL 에 넘겨온 한글값을 받아오는 메서드
	 * 
	 * @param string
	 * @return string
	 * @see java.net.URLDecoder
	 */

	public static synchronized String URLdecode(String string) {
		return java.net.URLDecoder.decode(convertNull(string));
	}

	/**
	 * 자기자신을 반환하는 메서드
	 * 
	 * @param string
	 * @return string
	 */
	private static synchronized String retThis(String string) {
		return convertNull(string);
	}

	/**
	 * String 문자열이 Null 값이면 ""을 반환하는 메서드
	 * 
	 * @param string
	 * @return string
	 */
	public static String convertNull(String string) {
		return (string == null) ? "" : string;
	}

	/**
	 * String 문자열이 Null 값이면 ""을 반환하는 메서드
	 * 
	 * @param string
	 * @return string
	 */
	public static String[] convertNull(String[] string) {
		return (string == null) ? null : string;
	}

	/**
	 * check wheather the imput parameter is null or not
	 * 
	 * @param string
	 * @return string
	 */
	public static boolean isNull(String string) {
		return (string == null || string.equals("")) ? true : false;
	}

	/**
	 * 첫번째 param의 문자열이 Null 값이면 두번째 param을 반환하는 메서드
	 * 
	 * @param string1
	 * @param string2
	 */
	public static String convertNull(String string1, String string2) {
		return (ConChar.isNull(string1)) ? string2 : string1;
	}

	/**
	 * BigDecimal이 Null 값이면 0.0을 반환하는 메서드
	 * 
	 * @param bigDecimal
	 * @return bigDecimal
	 */

	public static BigDecimal convertNull(BigDecimal bigDecimal) {
		return (bigDecimal == null) ? new BigDecimal(0.0) : bigDecimal;
	}

	/**
	 * 첫번째 param의 BigDecimal이 Null 값이면 두번째 param을 반환하는 메서드
	 * 
	 * @param bigDecimal1
	 * @param bigDecimal2
	 */

	public static BigDecimal convertNull(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
		return (bigDecimal1 == null) ? bigDecimal2 : bigDecimal1;
	}

	/**
	 * 데이터베이스에서 String 문자열을 조회시 사용.<br>
	 * 내부적으로 구현되는 메서드 없음
	 * 
	 * @param string
	 * @return string
	 */

	public static synchronized String getString(String string) {
		return retThis(string);
	}

	/**
	 * 데이터베이스에 String 문자열을 추가 또는 수정시 사용하는 메소드.
	 * <p>
	 * 내부적으로 8859_1 을 KSC5601로 변환하는 메서드 호출함
	 * 
	 * @param string
	 * @return string
	 */

	public static synchronized String setString(String string) {
		return retThis(string);
	}

	/**
	 * File에서 값을 불러들일때 사용하는 메서드.
	 * <p>
	 * 내부적으로 8859_1 을 KSC5601로 변환하는 메서드 호출함.<br>
	 * 주의) 위의 경우를 제외하고 직접적으로 호출하지 말것
	 * 
	 * @param string
	 * @return string
	 */

	public static synchronized String en2ko(String string) {
		// return E2K(string);
		return string;
	}

	/**
	 * @param string
	 * @return string
	 */

	public static synchronized String ko2en(String string) {
		// return K2E(string);
		return string;
	}

	/**
	 * String형을 int 형으로 변환하여주는 메서드.
	 * 
	 * @param str
	 *            변환할 String
	 * @return int null이거나 "" 일 경우 0 return
	 */
	public static int s2i(String str) {
		return s2i(str, 0);
	}

	/**
	 * String형을 int 형으로 변환하여주는 메서드
	 * 
	 * @param str
	 *            변환할 String
	 * @param defaultValue
	 *            str이 null 이거나 "" 일 경우의 기본값
	 * @return int 변환된 int 값. str이 null 이거나 "" 일 경우 defaultValue
	 */
	public static int s2i(String str, int defaultValue) {
		return (str == null || str == "") ? defaultValue : Integer.parseInt(str);
	}

	/**
	 * String형을 BigDecimal 형으로 변환하여주는 메서드
	 * 
	 * @param str
	 *            변환할 String
	 * @return BigDecimal str이 null 이거나 "" 일 경우 0 return
	 */
	public static BigDecimal s2bd(String str) {
		return s2bd(str, new BigDecimal(0));
	}

	/**
	 * String형을 BigDecimal 형으로 변환하여주는 메서드
	 * 
	 * @param str
	 *            변환할 String
	 * @param defaultValue
	 *            str이 null 이거나 "" 일 경우의 기본값
	 * @return BigDecimal
	 */
	public static BigDecimal s2bd(String str, BigDecimal defaultValue) {
		return (str == null || str == "") ? defaultValue : new BigDecimal(str);
	}

	/**
	 * URL 에 한글값을 같이 넘겨줄때 Encoding 해주는 메서드
	 * 
	 * @param string
	 * @return string
	 * @see java.net.URLEncoder
	 */

	public static synchronized String URLSetString(String string) {
		return URLencode(string);
	}

	/**
	 * URL 에 넘겨온 한글값을 받아오는 메서드
	 * 
	 * @param string
	 * @return string
	 * @see java.net.URLDecoder
	 */

	public static synchronized String URLGetString(String string) {
		return URLdecode(string);
	}

	/**
	 * 대문자로 변환시켜주는 메서드
	 * 
	 * @param string
	 * @return string
	 * @see java.net.URLDecoder
	 */

	public static String upper(String s) {
		return convertNull(s).toUpperCase();
	}

	/**
	 * 소문자로 변환시켜주는 메서드
	 * 
	 * @param string
	 * @return string
	 * @see java.net.URLDecoder
	 */

	public static String lower(String s) {
		return convertNull(s).toLowerCase();
	}

	/**
	 * 스트림을 String 으로 변환하는 메서드
	 * 
	 * @param InputStream
	 * @return string
	 * 
	 */

	public static String is2String(InputStream iS) throws UnsupportedEncodingException, IOException {
		String str = null;
		BufferedReader dis = null;
		dis = new BufferedReader(new InputStreamReader(iS, "UTF-8"));
		str = dis.readLine();
		dis.close();
		iS.close();
		return str;
	}

	/**
	 * 문자열중 특정문자열을 다른문자열로 변환하는 메서드
	 * 
	 * @param String
	 *            str 문자열
	 * @param String
	 *            n1 변환하고자 하는 문자열
	 * @param String
	 *            n2 바꾸고 싶은 문자열
	 * @return string
	 * 
	 */

	public static String replace(String str, String n1, String n2) {
		int itmp = 0;
		if (str == null)
			return null;

		String tmp = str;
		StringBuffer sb = new StringBuffer();

		itmp = tmp.indexOf(n1);
		while (itmp > -1) {
			sb.append(tmp.substring(0, itmp));
			sb.append(n2);
			tmp = tmp.substring(itmp + n1.length());
			itmp = tmp.indexOf(n1);
		}
		sb.append(tmp);
		return sb.toString();
	}

	/**
	 * 문자열중 특정문자열을 다른문자열로 변환하는 메서드
	 * 
	 * @param String
	 *            str 문자열
	 * @param String
	 *            n1 변환하고자 하는 문자열
	 * @param String
	 *            n2 바꾸고 싶은 문자열
	 * @return string
	 * 
	 */

	public static String replaceAll(String str, String n1, String n2) {
		return replaceAll(new StringBuffer(str), n1, n2).toString();
	}

	/**
	 * 문자열전체에서 특정문자열을 다른문자열로 변환하는 메서드
	 * 
	 * @param StringBuffer
	 *            str 문자열
	 * @param String
	 *            n1 변환하고자 하는 문자열
	 * @param String
	 *            n2 바꾸고 싶은 문자열
	 * @return StringBuffer
	 * 
	 */
	public static StringBuffer replaceAll(StringBuffer buffer, String find, String replacement) {

		int bufidx = buffer.length() - 1;
		int offset = find.length();
		while (bufidx > -1) {
			int findidx = offset - 1;
			while (findidx > -1) {
				if (bufidx == -1) {
					// Done
					return buffer;
				}
			}
			buffer.replace(bufidx + 1, bufidx + 1 + offset, replacement);
		}
		return buffer;
	}

	/**
	 * String을 BigDecimal로 변환
	 * 
	 * @param num
	 *            String으로 된 수
	 * @return BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(String num) {
		try {
			return new BigDecimal(num);
		} catch (Exception e) {
			return new BigDecimal(0);
		}
	}

	/**
	 * String을 ArrayList 로 변환시키는 함수 "1,2,33,44,555" 를 "," 로 구분하여 각각을 ArrayList 에
	 * 담는다.
	 * 
	 * @param string
	 *            String
	 * @param seperator
	 *            String 구분자
	 * @return ArrayList
	 */
	public static ArrayList<String> stringToArrayList(String string, String seperator) {

		ArrayList<String> arrayList = new ArrayList<String>();
		if (string == null || string.equals("")) {
			return arrayList;
		}

		// int len = string.length();
		int idx = 0;
		int bidx = 0;
		String strTmp = null;
		int endidx = string.lastIndexOf(seperator);

		while (true) {
			if (bidx > endidx) {
				strTmp = string.substring(bidx);
				arrayList.add(strTmp);
				break;
			}
			idx = string.indexOf(seperator, bidx);
			strTmp = string.substring(bidx, idx);
			arrayList.add(strTmp);
			bidx = idx + 1;
		}
		return arrayList;
	}

	/**
	 * String을 일정한 크기로 얻어내는 메소드 - 한글의 경우에 String.substring() 메서드의 경우 문제가 있으므로
	 * 이메서드를 이용한다
	 * 
	 * @param string
	 *            String
	 * @param length
	 *            int 잘라낼 크기
	 * @return String
	 */
	public static String fixLenHangul(String string, int length) {
		if (!isNull(string)) {
			String str = new String(string.getBytes(), 0, length);
			if (str.equals(""))
				return new String(string.getBytes(), 0, length - 1);
			else
				return str;
		} else {
			return "";
		}
	}

	/**
	 * String을 일정한 크기로 얻어내는 메소드 - 한글의 경우에 String.substring() 메서드의 경우 문제가 있으므로
	 * 이메서드를 이용한다
	 * 
	 * @param string
	 *            String
	 * @param length
	 *            int 잘라낼 크기
	 * @return String
	 */

	public static String substring(String string, int stdinx, int endinx) {

		if (!isNull(string)) {
			String str = new String(string.getBytes(), stdinx, endinx - stdinx);
			if (str.equals("")) {
				return new String(string.getBytes(), stdinx, endinx - stdinx - 1);
			} else {
				return str;
			}
		} else {
			return "";
		}
	}

	/**
	 * SAP 회계 데이터중 THE FORMAT OF NUMBER VALUE 가 "392039-" 형태로 변환되어 반환된다. "-"를
	 * 숫자앞으로 전환하는 메소드
	 * 
	 * @param string
	 *            String
	 * @return String
	 */
	public static String reverseMinus(String s) {
		String r = s;
		if (s.endsWith("-")) {
			r = "-" + s.replace('-', ' ').trim();
		}
		return r;
	}

	public static String getConvertUsd(String str) {
		if (str == null || str.length() < 3)
			return str;
		str = str.substring(0, str.length() - 2) + "." + str.substring(str.length() - 2);
		return str;
	}

	public static String getConvertDate8(String str, String delim) {
		if (str == null || str.length() != 8)
			return str;
		str = str.trim();
		if (str.length() != 8)
			return str;
		str = str.substring(0, 4) + delim + str.substring(4, 6) + delim + str.substring(6, 8);
		return str;
	}

	public static String getConvertTime6(String str, String delim) {
		if (str == null || str.length() != 6)
			return str;
		str = str.substring(0, 2) + delim + str.substring(2, 4) + delim + str.substring(4, 6);
		return str;
	}

	public static String convertComNumber(String str) {
		String result = "";
		if (str == null || str.length() < 10)
			return str;
		result = str.substring(0, 3) + "-" + str.substring(3, 5) + "-" + str.substring(5);
		return result;
	}

	/**
	 * Method cropByte. 문자열 바이트수만큼 끊어주고, 생략표시하기
	 * 
	 * @param str
	 *            문자열
	 * @param i
	 *            바이트수
	 * @param trail
	 *            생략 문자열. 예) "..."
	 * @return String
	 */
	public static String cropByte(String str, int i, String trail) {
		if (str == null)
			return "";
		String tmp = str;
		int slen = 0, blen = 0;
		char c;
		try {
			if (tmp.getBytes("MS949").length > i) {// 2-byte character..
				// if(tmp.getBytes("UTF-8").length>i) {//3-byte character..
				while (blen + 1 < i) {
					c = tmp.charAt(slen);
					blen++;
					slen++;
					if (c > 127)
						blen++;
				}
				tmp = tmp.substring(0, slen) + trail;
			}
		} catch (Exception e) {
		}
		return tmp;
	}

	/**
	 * 입력받은 String 문자열을 화폐단위로 변환시켜주는 메서드<br>
	 * .
	 * 
	 * <pre>
	 * 두번째 인자에 따라 다음과 같이 다르게 표현될수 있다 예) 1234567
	 * <table>
	 * <tr>
	 * <th align=center>pattern</th>
	 * <th>return value</th>
	 * </tr>
	 * <tr>
	 * <td align=center>00.000E0</td>
	 * <td align=right>12.346E5</td>
	 * </tr>
	 * <tr>
	 * <td align=center>###,##0.00</td>
	 * <td align=right>1,234,567.00</td>
	 * </tr>
	 * <tr>
	 * <td align=center>###,##0</td>
	 * <td align=right>1,234,567</td>
	 * </tr>
	 * </table>
	 * 
	 * <p>
	 * <table border=1>
	 * <tr>
	 * <th>Symbol
	 * <th>Location
	 * <th>Localized?
	 * <th>Meaning
	 * </tr>
	 * <tr>
	 * <td>0
	 * <td>Number
	 * <td>Y
	 * <td>Digit
	 * <tr>
	 * <td>#
	 * <td>Number
	 * <td>Y
	 * <td>Digit, zero shows as absent
	 * </tr>
	 * <tr>
	 * <td>.
	 * <td>Number
	 * <td>Y
	 * <td>Decimal separator or monetary decimal separator
	 * </tr>
	 * <tr>
	 * <td>-
	 * <td>Number
	 * <td>Y
	 * <td>Minus sign
	 * </tr>
	 * <tr>
	 * <td>,
	 * <td>Number
	 * <td>Y
	 * <td>Grouping separator
	 * </tr>
	 * <tr>
	 * <td>E
	 * <td>Number
	 * <td>Y
	 * <td>Separates mantissa and exponent in scientific notation.
	 * <em>Need not be quoted in prefix or suffix.</em>
	 * </tr>
	 * <tr>
	 * <td>;
	 * <td>Subpattern boundary
	 * <td>Y
	 * <td>Separates positive and negative subpatterns
	 * </tr>
	 * <tr>
	 * <td>%
	 * <td>Prefix or suffix
	 * <td>Y
	 * <td>Multiply by 100 and show as percentage
	 * </tr>
	 * <tr>
	 * <td>&#92;u2030
	 * <td>Prefix or suffix
	 * <td>Y
	 * <td>Multiply by 1000 and show as per mille
	 * </tr>
	 * <tr>
	 * <td>&#164;<br>
	 * (&#92;u00A4)
	 * <td>Prefix or suffix
	 * <td>N
	 * <td>Currency sign, replaced by currency symbol. If doubled, replaced by
	 * international currency symbol. If present in a pattern, the monetary
	 * decimal separator is used instead of the decimal separator.
	 * </tr>
	 * <tr>
	 * <td>'
	 * <td>Prefix or suffix
	 * <td>N
	 * <td>Used to quote special characters in a prefix or suffix, for example,
	 * <code>"'#'#"</code> formats 123 to <code>"#123"</code>. To create a
	 * single quote itself, use two in a row: <code>"# o''clock"</code>.
	 * </tr>
	 * </table>
	 * 
	 * @param w
	 *            double
	 * @param pattern
	 *            String
	 * @return formated string
	 */

	public static String getForm(double w, String pattern) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		return df.format(w);
	}

	/**
	 * 화폐단위로 변환시켜주는 메서드.
	 * 
	 * @return formated string, "###,##0".
	 * @param w
	 *            double
	 */
	public static String getForm(double w) {

		return getForm(w, "###,##0");
	}

	/**
	 * 화폐단위로 변환시켜주는 메서드.
	 * 
	 * @return formated string, "###,##0".
	 * @param w
	 *            double
	 */
	public static String getFormB(double w) {

		return getForm(w, "###,##0.00");
	}

	/**
	 * 화폐단위로 변환시켜주는 메서드.
	 * 
	 * @return formated string, "###,##0".
	 * @param bigDecimal
	 *            BigDecimal
	 */

	public static String getForm(java.math.BigDecimal bigDecimal) {
		if (bigDecimal == null)
			return "0";
		else
			return getForm(bigDecimal.toString());
	}

	/**
	 * 화폐단위로 변환시켜주는 메서드.
	 * 
	 * @return formated string, "###,##0".
	 * @param w
	 *            int
	 */
	public static String getForm(int w) {

		return getForm((double) w);
	}

	/**
	 * 화폐단위로 변환시켜주는 메서드.
	 * 
	 * @return formated string, "###,##0".
	 * @param w
	 *            long
	 */
	public static String getForm(long w) {

		return getForm((double) w);
	}

	/**
	 * 화폐단위로 변환시켜주는 메서드.
	 * 
	 * @return formated string, "###,##0".
	 * @param w
	 *            java.lang.String
	 */
	public static String getForm(String w) {
		double d = 0;
		try {
			d = Double.valueOf(w).doubleValue();
		} catch (Exception e) {
			d = 0;
		}

		return getForm(d);
	}

	/**
	 * 화폐단위로 변환시켜주는 메서드.
	 * 
	 * @return formated string, "###,##0.00".
	 * @param w
	 *            java.lang.String
	 */
	public static String getFormB(String w) {
		double d = 0;
		try {
			d = Double.valueOf(w).doubleValue();
		} catch (Exception e) {
			d = 0;
		}

		return getFormB(d);
	}

	/**
	 * lpad 함수
	 *
	 * @param str
	 *            대상문자열, len 길이, addStr 대체문자
	 * @return 문자열
	 */

	public static String LPad(String str, int len, String addStr) {
		String result = str;
		int templen = len - result.length();

		for (int i = 0; i < templen; i++) {
			result = addStr + result;
		}

		return result;
	}

	/**
	 * lpad 함수
	 *
	 * @param str
	 *            대상문자열, len 길이, addStr 대체문자
	 * @return 문자열
	 */

	public static String RPad(String str, int len, String addStr) {
		String result = str;
		int templen = len - result.length();

		for (int i = 0; i < templen; i++) {
			result = result + addStr;
		}
		return result;
	}

	public static String getReplaceETC(String content) {
		Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
		Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
		Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
		Pattern WHITESPACE = Pattern.compile("\\s\\s+");

		Matcher m;

		m = SCRIPTS.matcher(content);
		content = m.replaceAll("");
		m = STYLE.matcher(content);
		content = m.replaceAll("");
		m = TAGS.matcher(content);
		content = m.replaceAll("");
		m = ENTITY_REFS.matcher(content);
		content = m.replaceAll("");
		m = WHITESPACE.matcher(content);
		content = m.replaceAll(" ");

		return content;
	}

	/**
	 * 주어진 변수에 맞는 날짜 형식을 반환해주는 메서드<br>
	 * 
	 * @param pattern
	 *            원하는 날짜 형식 패턴
	 * @return String
	 * @see DateTime#getDateString
	 */

	public static String getDateString(String pattern) {
		return getDateString(pattern, java.util.Locale.KOREA);
	}

	/**
	 * 주어진 변수에 맞는 날짜 형식을 반환해주는 메서드<br>
	 * 
	 * @param pattern
	 *            원하는 날짜 형식 패턴
	 * @param locale
	 *            알아보려 하는 {@link java.util.Locale Locale}
	 * @return String
	 * @see DateTime#getDateString
	 */
	public static String getDateString(String pattern, java.util.Locale locale) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern, locale);
		return formatter.format(new java.util.Date());

	}

	/**
	 * Timestamp를 반환해주는 메서드<br>
	 * 
	 * @return String 2002050305304886 : 연도(4자리) + 달(2) + 일(2) + 시간(2) + 분(2)+
	 *         초(2) + milisecond(3)
	 */

	public static String getTimeStamp() {
		return getDateString("yyyyMMddHHmmssSSS");
	}

	/**
	 * 일반적인 날짜 형식을 반환해주는 메서드<br>
	 * 
	 * @return String 2002-05-03 05:30:48 : 연도(4자리) + 달(2) + 일(2) + 시간(2) +
	 *         분(2)+ 초(2)
	 */

	public static String getGenDate() {
		return getDateString("yyyy-MM-dd HH:mm:ss");
	}

	public static String getString(java.sql.Timestamp ts, String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		return sd.format(ts);
	}

	/**
	 * return month balance of two Dates for ordering.
	 * 
	 * @param fromDate
	 *            a String
	 * @param toDate
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return int
	 */
	public static int monthsBetween(String fromDate, String toDate, String pattern) {
		return monthsBetween(getCalendar(fromDate, pattern), getCalendar(toDate, pattern));
	}

	/**
	 * return month balance of two Dates for ordering.
	 * 
	 * @param fromCalendar
	 * @param toCalendar
	 * @return int
	 */
	public static int monthsBetween(Calendar toCalendar, Calendar fromCalendar) {
		int balance = (fromCalendar.get(Calendar.YEAR) - toCalendar.get(Calendar.YEAR)) * 12
				+ fromCalendar.get(Calendar.MONTH) - toCalendar.get(Calendar.MONTH);
		return balance;
	}

	/**
	 * return day balance of two Dates for ordering.
	 * 
	 * @param toDate
	 *            a String
	 * @param fromDate
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return int
	 */
	public static int daysBetween(String toDate, String fromDate, String pattern) {
		java.util.Date date1 = getDate(fromDate, pattern);
		java.util.Date date2 = getDate(toDate, pattern);
		return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));

	}

	/**
	 * Compares two Dates for ordering.
	 * 
	 * @param date1
	 *            a String
	 * @param date2
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return int the return value 0 if the argument Date is equal to this
	 *         Date; a value less than 0 if this Date is before the Date
	 *         argument; and a value greater than 0 if this Date is after the
	 *         Date argument
	 */
	public static int compare(String date1, String date2, String pattern) {
		return compare(date1, date2, pattern, java.util.Locale.KOREA);

	}

	/**
	 * Compares two Dates for ordering.
	 * 
	 * @param date1
	 *            a String
	 * @param date2
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param Locale
	 * @return int the return value 0 if the argument Date is equal to this
	 *         Date; a value less than 0 if this Date is before the Date
	 *         argument; and a value greater than 0 if this Date is after the
	 *         Date argument
	 */
	public static int compare(String date1, String date2, String pattern, Locale locale) {
		java.util.Date dt1 = getDate(date1, pattern, locale);
		java.util.Date dt2 = getDate(date2, pattern, locale);
		return dt1.compareTo(dt2);

	}

	/**
	 * Return a last day of Week as a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param day_of_week
	 *            int a day of week <br>
	 *            For example,
	 *            Calendar.SUNDAY,Calendar.MONDAY,Calendar.TUESDAY,Calendar.
	 *            WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY,Calendar.SATURDAY,
	 *            Calendar.SUNDAY
	 * @return java.lang.String
	 */
	public static String nextDay(String date, String pattern, int day_of_week) {
		return calendar2String(nextDay(getCalendar(date, pattern), day_of_week), pattern);
	}

	/**
	 * return a last day of Week as a Calendar class
	 * 
	 * @param calendar
	 *            Calendar class
	 * @param day_of_week
	 *            int a day of week <br>
	 *            For example,
	 *            Calendar.SUNDAY,Calendar.MONDAY,Calendar.TUESDAY,Calendar.
	 *            WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY,Calendar.SATURDAY,
	 *            Calendar.SUNDAY
	 * @return java.util.Calendar
	 */
	public static Calendar nextDay(Calendar calendar, int day_of_week) {
		Calendar c = (Calendar) calendar.clone();
		calendar.set(Calendar.DAY_OF_WEEK, day_of_week);
		if (calendar.before(c)) {
			calendar.add(Calendar.DAY_OF_MONTH, 7);
		}

		return calendar;
	}

	/**
	 * return last day of Month as a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String lastDay(String date, String pattern) {
		return calendar2String(lastDay(getCalendar(date, pattern)), pattern);
	}

	/**
	 * return last day of Month as a Calendar class
	 * 
	 * @param calendar
	 *            Calendar class
	 * @return java.util.Calendar
	 */
	public static Calendar lastDay(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar;
	}

	/**
	 * return increase or decrease Day field a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */
	public static String addDays(String date, String pattern, int size) throws java.text.ParseException {
		return calendar2String(addDays(getCalendar(date, pattern), size), pattern);
	}

	/**
	 * return increase or decrease Day field a Calendar class
	 * 
	 * @param calendar
	 *            Calendar
	 * @param size
	 *            int
	 * @return java.util.Calendar
	 */
	public static Calendar addDays(Calendar calendar, int size) {
		return changeCalendar(calendar, Calendar.DAY_OF_MONTH, size);
	}

	/**
	 * return increase or decrease Month field a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */

	public static String addMonths(String date, String pattern, int size) throws java.text.ParseException {
		return calendar2String(addMonths(getCalendar(date, pattern), size), pattern);
	}

	/**
	 * return increase or decrease Month field a Calendar class
	 * 
	 * @param calendar
	 *            Calendar
	 * @param size
	 *            int
	 * @return java.util.Calendar
	 */
	public static Calendar addMonths(Calendar calendar, int size) {
		return changeCalendar(calendar, Calendar.MONTH, size);
	}

	/**
	 * return increase or decrease Year field a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */

	public static String addYears(String date, String pattern, int size) throws java.text.ParseException {
		return calendar2String(addYears(getCalendar(date, pattern), size), pattern);
	}

	/**
	 * return increase or decrease Year field a Calendar class
	 * 
	 * @param calendar
	 *            Calendar
	 * @param size
	 *            int
	 * @return java.util.Calendar
	 */
	public static Calendar addYears(Calendar calendar, int size) {
		return changeCalendar(calendar, Calendar.YEAR, size);
	}

	/**
	 * return increase or decrease sysdate as a String class
	 * 
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param field
	 *            string increase/decrease field. For example,
	 *            "MONTH, DAY_OF_MONTH".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */
	public static String add(String pattern, int field, int size) {
		return changeString(getSysdate(pattern), pattern, field, size);
	}

	/**
	 * return sysdate as a specific pattern
	 * 
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String getSysdate(String pattern) {
		return getSysdate(pattern, java.util.Locale.KOREA);
	}

	/**
	 * return sysdate as a specific pattern
	 * 
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param Locale
	 * @return java.lang.String
	 */
	public static String getSysdate(String pattern, Locale locale) {
		return calendar2String(Calendar.getInstance(locale), pattern);
	}

	/**
	 * return date as a String class
	 * 
	 * @param date
	 *            date string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param field
	 *            string increase/decrease field. For example,
	 *            "MONTH, DAY_OF_MONTH".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */
	public static String changeString(String date, String pattern, int field, int size) {
		return calendar2String(changeCalendar(getCalendar(date, pattern), field, size), pattern);
	}

	/**
	 * return date as a Calender class
	 * 
	 * @param date
	 *            java.util.Calender string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param field
	 *            string increase/decrease field. For example,
	 *            "MONTH, DAY_OF_MONTH".
	 * @return java.util.Calender
	 */
	public static Calendar changeCalendar(Calendar calendar, int field, int size) {
		calendar.add(field, size);
		return calendar;

	}

	/**
	 * check a valid date format or not
	 * 
	 * @param date
	 *            date string you want to check
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return boolean
	 */
	public static boolean isDate(String date, String pattern) {
		return isDate(date, pattern, java.util.Locale.KOREA);
	}

	/**
	 * check a valid date format or not
	 * 
	 * @param date
	 *            date string you want to check
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return boolean
	 */
	public static boolean isDate(String date, String pattern, Locale locale) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
		Calendar calendar = df.getCalendar();
		try {
			calendar.setTime(df.parse(date));
		} catch (java.text.ParseException pe) {
			return false;
		}
		return true;
	}

	/**
	 * return Calender class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.util.Calender
	 */
	public static Calendar getCalendar(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, java.util.Locale.KOREA);
		Calendar calendar = df.getCalendar();
		try {
			calendar.setTime(df.parse(date));
		} catch (java.text.ParseException pe) {
		}
		return calendar;
	}

	/**
	 * return Date class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.util.Date
	 */
	public static java.util.Date getDate(String date, String pattern) {

		return getDate(date, pattern, java.util.Locale.KOREA);
	}

	/**
	 * return Date class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param locale
	 * @return java.util.Date
	 */

	public static java.util.Date getDate(String date, String pattern, Locale locale) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
		java.util.Date dt = null;
		try {
			dt = df.parse(date);
		} catch (java.text.ParseException pe) {
		}
		return dt;
	}

	/**
	 * return change Calendar class to String as specific format
	 * 
	 * @param calendar
	 *            Calendar class you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String calendar2String(Calendar calendar, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, java.util.Locale.KOREA);
		return df.format(calendar.getTime());
	}

	/**
	 * return java.sql.Timestamp class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.util.Date
	 */
	public static java.sql.Timestamp getTimestamp(String date, String pattern) {
		if (ConChar.isNull(date)) {
			return null;
		} else {
			return new Timestamp(getDate(date, pattern).getTime());
		}
	}

	/**
	 * String 타입의 날자를 받아 원하는 pattern으로 변형 시키는 메서드
	 * 
	 * @param date
	 *            string 바꾸려고 하는 날자 형식
	 * @param pattern
	 *            string input 날자 패턴. 예) "yyyy-MM-dd".
	 * @param pattern
	 *            string output 날자 패턴. 예) "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String changePatten(String date, String in_pattern, String out_pattern) {
		if (ConChar.isNull(date)) {
			return null;
		} else {
			return calendar2String(getCalendar(date, in_pattern), out_pattern);
		}
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
     * z01_code_detail의 값을 가져오는 공통함수
     * 
     * @version
     * @param Map requestMap
     * @return String (a=1&b=2&c=3....)
     * @throws Exception
     *
     */
    public static String getCodeSelectBox(List sourceList, String clssCd, String selVal, HttpServletRequest request) throws Exception {
        StringBuffer sp = new StringBuffer();        
        for(int i= 0; i<sourceList.size(); i++){
        	if(clssCd.equals(((Map)sourceList.get(i)).get("CLASS_CD"))){
        		if(selVal.equals(((Map)sourceList.get(i)).get("CLASS_DTL_CD"))){
        			sp.append("<option value='" + ((Map)sourceList.get(i)).get("CLASS_DTL_CD")+"' selected>"+((Map)sourceList.get(i)).get("CLASS_DTL_NM") +"</option>\n");
        		}else{
        			sp.append("<option value='" + ((Map)sourceList.get(i)).get("CLASS_DTL_CD")+"'>"+((Map)sourceList.get(i)).get("CLASS_DTL_NM") +"</option>\n");
        		}
        	}
		}
        return sp.toString();
    }
    
    /**
     * 대상문자열에서 특정 String이 있는지 확인하고자한다.
     * 
     * @version
     * @param String str
     * @param List<String> pattern 
     * @return boolean
     * @throws Exception
     *
     */    
    public static boolean checkPattern(String str, List<String> pattern) {
        for (String ch : pattern) {
            if (str.indexOf(ch) == -1) {
                return false; // 패턴 문자가 문자열에 존재하지 않으면 false 반환
            }
        }
        return true; // 모든 패턴 문자가 문자열에 존재하면 true 반환
    }
}
