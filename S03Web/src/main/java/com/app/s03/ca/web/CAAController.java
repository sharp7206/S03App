package com.app.s03.ca.web;

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
import com.app.s03.ca.service.CAAService;
import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.service.MainService;
import com.app.s03.cmn.utils.SysUtils;
import com.app.s03.za.service.Z01Service;

@RestController
@RequestMapping("/api/ca/caa/")
public class CAAController {
	
	@Autowired
	private CAAService caaService;
	
	
	/**
	 * search IOT List
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchZ01IOTList.do")
	public ResponseEntity<?> searchZ01IOTList(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", caaService.searchZ01IOTList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
		
	/**
	 *  IOT List 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01IOTList.do")
	public ResponseEntity<?> saveZ01IOTList(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		caaService.saveZ01IOTList(param);
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * search CAT INFO List
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchZ01ImgCat.do")
	public ResponseEntity<?> searchZ01ImgCat(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", caaService.searchZ01ImgCat(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 *  CAT INFO 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01ImgCat.do")
	public ResponseEntity<?> saveZ01ImgCat(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		caaService.saveZ01ImgCat(param);
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}		
}
