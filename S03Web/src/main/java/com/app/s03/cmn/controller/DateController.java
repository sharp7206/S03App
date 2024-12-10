package com.app.s03.cmn.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.utils.CommonUtils;


/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 날짜관련 공통 컨트롤러
-===============================================================================================================
*/
@RestController
@RequestMapping("/api/cmn/date/")
public class DateController {

	/**
	 * 시스템 현재년도
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("sysyear.do")
	public Map<String, ?> sysyear(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String dateformat = (String)param.get("dateformat");
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("data", new SimpleDateFormat(dateformat).format(System.currentTimeMillis()));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 시스템 현재월
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("sysmonth.do")
	public Map<String, ?> sysmonth(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String dateformat = (String)param.get("dateformat");
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("data", new SimpleDateFormat(dateformat).format(System.currentTimeMillis()));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 시스템 현재일자
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("sysdate.do")
	public Map<String, ?> sysdate(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String dateformat = (String)param.get("dateformat");
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("data", new SimpleDateFormat(dateformat).format(System.currentTimeMillis()));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 시스템현재 timestamp
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("systimestamp.do")
	public Map<String, ?> systimestamp(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String dateformat = (String)param.get("dateformat");
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("data", new SimpleDateFormat(dateformat).format(System.currentTimeMillis()));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 특정일의 달의 마지막날 계산
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("lastday.do")
	public Map<String, ?> lastday(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String date = (String)param.get("date");
		String dateformat = (String)param.get("dateformat");
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateformat));
		convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("data", convertedDate.format(DateTimeFormatter.ofPattern(dateformat)));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 특정일자의 일수 더하기
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("adddays.do")
	public Map<String, ?> adddays(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String date = (String)param.get("date");
		String dateformat = (String)param.get("dateformat");
		int days = (int)param.get("adddays");
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateformat));
		convertedDate = convertedDate.plusDays(days);
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("data", convertedDate.format(DateTimeFormatter.ofPattern(dateformat)));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 특정일자의 개월 더하기
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("addmonths.do")
	public Map<String, ?> addMonths(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String date = (String)param.get("date");
		String dateformat = (String)param.get("dateformat");
		int months = (int)param.get("addmonths");
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateformat));
		convertedDate = convertedDate.plusMonths(months);
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("data", convertedDate.format(DateTimeFormatter.ofPattern(dateformat)));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

}