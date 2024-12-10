package com.app.s03.za.web;

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

import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.service.MainService;
import com.app.s03.cmn.utils.SysUtils;
import com.app.s03.za.service.Z01Service;

@RestController
@RequestMapping("/api/za/zaa/")
public class ZAAController {
	
	@Autowired
	private Z01Service z01Service;
	
	  
	/**
	 * 시스템 목록
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("getSystemList.do")
	public ResponseEntity<?> getSystemList(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchSysList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 시스템 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveSystemList.do")
	public ResponseEntity<?> saveSystemList(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01Sys((List<Map<String, ?>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 메뉴정보 목록
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("selectZ01Menu.do")
	public ResponseEntity<?> selectZ01Menu(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.selectZ01Menu(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 메뉴정보 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01Menu.do")
	public ResponseEntity<?> saveZ01Menu(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01Menu((List<Map<String, ?>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * Role정보 목록
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("selectZ01Role.do")
	public ResponseEntity<?> selectZ01Role(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchRoleList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	/**
	 * 메뉴정보 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01Role.do")
	public ResponseEntity<?> saveZ01Role(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01Role((List<Map<String, ?>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
		
	
	@SuppressWarnings("unchecked")
	@PostMapping("searchZ01RoleItem.do")
	public ResponseEntity<?> searchZ01RoleItem(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchZ01RoleItem(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 메뉴정보 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01RoleItem.do")
	public ResponseEntity<?> saveZ01RoleItem(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01RoleItem((List<Map<String, ?>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}		
	
	@SuppressWarnings("unchecked")
	@PostMapping("searchSysMenuForRole.do")
	public ResponseEntity<?> searchSysMenuForRole(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchSysMenuForRole(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	/**
	 * Z01SysUser 조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchSysUserList.do")
	public ResponseEntity<?> searchSysUserList(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchZ01SysUser(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}	
	
	/**
	 * searchPeoplePersonal 조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchPeoplePersonal.do")
	public ResponseEntity<?> searchPeoplePersonal(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchPeoplePersonal(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}	
	
	/**
	 * Z01SysUser 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01SysUser.do")
	public ResponseEntity<?> saveZ01SysUser(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01SysUser((List<Map<String, ?>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 코드마스터 조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchZ01CodeMaster.do")
	public ResponseEntity<?> searchZ01CodeMaster(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchZ01CodeMaster(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 코드마스터 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01CodeMaster.do")
	public ResponseEntity<?> saveZ01CodeMaster(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01CodeMaster((List<Map<String, ?>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}	

	/**
	 * 코드디테일 조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchZ01CodeDetail.do")
	public ResponseEntity<?> searchZ01CodeDetail(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchZ01CodeDetail(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 코드디테일 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01CodeDetail.do")
	public ResponseEntity<?> saveZ01CodeDetail(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01CodeDetail((List<Map<String, ?>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 시스템Log 조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchZ01SystemLog.do")
	public ResponseEntity<?> searchZ01SystemLog(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchZ01SystemLog(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * Job 조회
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("searchZ01JobList.do")
	public ResponseEntity<?> searchZ01JobList(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		res.put("data", z01Service.searchZ01SchInfo(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	/**
	 * 코드디테일 저장
	 * @param requestData
	 * @return
	 */
	@PostMapping("saveZ01SchInfo.do")
	public ResponseEntity<?> saveZ01SchInfo(@RequestBody String requestData) throws Exception{
		Map<String, Object> jsonData = SysUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap();
		z01Service.saveZ01SchInfo((List<Map<String, Object>>)param.get("gridData"));
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
		
	
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
		res.put("data", z01Service.searchZ01IOTList(param));
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
		z01Service.saveZ01IOTList(param);
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}		
}
