package com.app.s03.cmn.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.service.MainService;
import com.app.s03.cmn.service.UserAuthorityService;
import com.app.s03.cmn.utils.CommonUtils;
import com.app.s03.cmn.utils.Utility;


/**
-===============================================================================================================
- 메인화면관련공통처리
-===============================================================================================================
*/
@RestController
@RequestMapping("/api/cmn/main/")
public class MainApiController {

	boolean isTrue = true;

	@Resource(name = "cmn.UserAuthorityService")
	private UserAuthorityService userAuthorityService;

	@Autowired
	private MainService service;

	/**
	 * dummy
	 * @param requestData
	 * @return
	 */
	@PostMapping("dummy.do")
	public Map<String,?> dummy(@RequestBody String requestData) {
		Map<String,Object> res = new LinkedHashMap<>();
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 공지사항목록
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("noticeList.do")
	public Map<String,?> noticeList(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("list", service.noticeList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 공지사항
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("notice.do")
	public Map<String,?> notice(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("notice", service.notice(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 사용자비번저장
	 * @param requestData
	 * @param principal 처리자.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("saveUserPassword.do")
	public Map<String,?> saveUserPassword(@RequestBody String requestData,Principal principal,Authentication authentication)  {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);// UI 요청 자료.
		Map<String, Object> user = (Map<String, Object>) jsonData.get("user");//그리드 데이타.
		service.saveUserPassword(user,principal.getName());//사용자 저장
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userDetails.setPwsetyn("Y");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 이전비번 맞는지체크
	 * @param requestData
	 * @param principal 처리자.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("validPrivpasswordYn.do")
	public Map<String,?> validPrivpasswordYn(@RequestBody String requestData,Principal principal)  {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);// UI 요청 자료.
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		String validYn = service.validPrivpasswordYn(param,principal.getName());//사용자 저장
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("validYn", validYn);
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 나의등록정보조회
	 * @param requestData
	 * @param principal 처리자.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("getMyInfo.do")
	public Map<String,?> getMyInfo(@RequestBody String requestData,Principal principal)  {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);// UI 요청 자료.
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("myInfo", service.getMyInfo(param));
		List<String> authorities = new ArrayList<>();
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for (GrantedAuthority auth : userDetails.getAuthorities()) {
			authorities.add(auth.getAuthority());
		}
		param.put("authorities",authorities);
		param.put("userid",principal.getName());
		//res.put("userSys", userAuthorityService.getUserSystemList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 나의등록정보저장
	 * @param requestData
	 * @return
	*/
	@SuppressWarnings("unchecked")
	@PostMapping("saveMyInfo.do")
	public Map<String,?> saveMyInfo(@RequestBody String requestData)  {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);// UI 요청 자료.
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		service.saveMyInfo(param);
		Map<String,Object> res = new LinkedHashMap<>();
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}
	
    /**
     * 나의등록정보저장
     * @param requestData
     * @return
    */
    @SuppressWarnings("unchecked")
    @PostMapping("select01.do")
    public Map<String,?> select01(@RequestBody String requestData)  {
        Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);// UI 요청 자료.
        Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
        service.saveMyInfo(param);
        Map<String,Object> res = new LinkedHashMap<>();
        res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
        return res;
    }
    
	/**
	 * 본사 제품 출고 조회 
	 */
	@RequestMapping(value="selectSideMenu.do")
	public Map<String, Object> selectSideMenu(@RequestBody String requestData, HttpServletRequest request,Principal principal) throws Exception{
		Map<String,Object> res = new LinkedHashMap<>();
		Map<String, Object> jsonData = Utility.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		List<String> authorities = new ArrayList<>();
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for (GrantedAuthority auth : userDetails.getAuthorities()) {
			authorities.add(auth.getAuthority());
		}
		param.put("syscd", ComConstants.SYS_CD);
		param.put("authorities", authorities);
		List<?> retList = userAuthorityService.getUserSideMenuTreeData(param);
		res.put("Data", retList);
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;	
	}	    
	
	
}