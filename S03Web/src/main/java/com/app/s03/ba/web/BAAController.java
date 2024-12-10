package com.app.s03.ba.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.s03.ba.service.BAAService;
import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.service.MainService;
import com.app.s03.cmn.utils.SysUtils;
import com.app.s03.za.service.Z01Service;

@RestController
@RequestMapping("/api/ba/baa/")
public class BAAController {
	
	@Autowired
	private BAAService baaService;
	
	
	/**
	 * 대부 목록조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("selectS03MortgageLoanList.do")
	public ResponseEntity<?> selectS03MortgageLoanList(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", baaService.selectS03MortgageLoanList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 대부 목록조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("selectS03MortgageLoan.do")
	public ResponseEntity<?> selectS03MortgageLoan(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", baaService.selectS03MortgageLoan(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 대부등록화면 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveS03MortgageLoan.do")
	public ResponseEntity<?> saveS03MortgageLoan(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		String loanId = baaService.saveS03MortgageLoan(param);
		res.put("data", loanId);
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * 대부등록화면 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("deleteS03MortgageLoan.do")
	public ResponseEntity<?> deleteS03MortgageLoan(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		baaService.deleteS03MortgageLoan(param);
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
