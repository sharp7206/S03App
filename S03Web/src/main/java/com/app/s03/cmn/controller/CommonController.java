package com.app.s03.cmn.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.warrenstrange.googleauth.GoogleAuthenticator;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.service.CommonCodeService;
import com.app.s03.cmn.service.CommonService;
import com.app.s03.cmn.service.MainService;
import com.app.s03.cmn.service.UserAuthorityService;
import com.app.s03.cmn.utils.CommonUtils;
import com.app.s03.cmn.utils.ConChar;
import com.app.s03.cmn.utils.GoogleOTPUtil;
import com.app.s03.cmn.utils.MessageUtils;
import com.app.s03.schedule.service.SchedulerService;
import com.app.s03.za.service.Z01Service;


/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 시스템공통컨트롤러
-===============================================================================================================
*/
@RestController
@RequestMapping("/api/cmn/common/")
public class CommonController {

	@Autowired
	private com.app.s03.cmn.service.SystemService service;
	
	@Autowired
	private MainService mainService;

    @Autowired
    private CommonService commonService;
    

    @Autowired
    private UserAuthorityService userAuthorityService;    

    @Autowired
    private CommonCodeService commonCodeService;
    @Autowired
    private SchedulerService schedulerService;
	/**
	 * 시스템코드목록
	 * @param request
	 * @return
	 */
	@PostMapping("syscdlist.do")
	public Map<String, ?> geSyscdList() {
		Map<String, String> param = new HashMap<>();
		param.put("activeyn", "Y");
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("list", service.getSystemList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 권한코드목록
	 * @param request
	 * @return
	 */
	@PostMapping("authoritylist.do")
	public Map<String, ?> getAuthorityList() {
		Map<String, String> param = new HashMap<>();
		param.put("activeyn", "Y");
		Map<String, Object> res = new LinkedHashMap<>();
		//res.put("list", authorityService.getAuthorityList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 사용자사진
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("message.do")
	public Map<String,?> message(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		String msgkey = (String)param.get("key");
		String msgparam = (String)param.get("param");
		String message = "";
		if(StringUtils.isNotEmpty(msgparam)) {
			message = messageSource.getMessage(msgkey, msgparam.split("\\|"), LocaleContextHolder.getLocale());
		} else {
			message = messageSource.getMessage(msgkey, null, LocaleContextHolder.getLocale());
		}
		//param.put("message", message);
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("message",message);
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 사용자사진
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("getUserPhoto.do")
	public Map<String,?> getUserPhoto(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("photo",mainService.getUserPhoto(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 부서검색
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("getDeptList.do")
	public Map<String,?> getDeptList(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("list",commonService.getDeptList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 사용자검색
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("userlist.do")
	public Map<String,?> userlist(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("list",commonService.getUserList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 코드검색
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("getCodeList.do")
	public Map<String,Object> getCodeList(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String, Object> res = new LinkedHashMap<>();
		param.put("syscd", ComConstants.SYS_CD);
		List<String> codeList = ConChar.stringToArrayList((String)param.get("codeStr"), ",");
		param.put("syscd", ComConstants.SYS_CD);
		param.put("codeList", codeList);
		res.put("list",commonCodeService.getCodeList(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * AUIGrid Export 서버 처리
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value="/export.do")
	public void export(HttpServletRequest request,HttpServletResponse response) throws IOException{

		//AUIGrid Export 시 로컬에 다운로드 가능하도록 작성된 서버사이드 예제입니다.
		//AUIGrid 가 xlsx, csv, xml 등의 형식을 작성하여 base64 로 인코딩하여 data 파라메터로 post 요청을 합니다.
		//해당 서버 예제(본 JSP) 에서는 base64 로 인코딩 된 데이터를 디코드하여 다운로드 가능하도록 붙임으로 마무리합니다.
		//참고로 org.apache.commons.codec.binary.Base64 클래스 사용을 위해는 commons-codec-1.4.jar 파일이 필요합니다.

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		String data = request.getParameter("data"); // 파라메터 data
		String extension = request.getParameter("extension"); // 파라메터 확장자

		// AUIGrid.exportToXlsx() 사용시 exportProps 로 파일명을 지정해 줬다면 다음과 같이 지정된 파일명을 얻을 수 있습니다.
		String reqFileName = request.getParameter("filename"); // 파라메터 파일명
		if(StringUtils.isEmpty(reqFileName)) reqFileName = "export";

		byte[] dataByte = Base64.decodeBase64(data.getBytes()); // 데이터 base64 디코딩

		// csv 를 엑셀에서 열기 위해서는 euc-kr 로 작성해야 함.
		if(extension.equals("csv")) {
			String sting = new String(dataByte, "utf-8");
			outputStream.write(sting.getBytes("euc-kr"));
		} else {
			outputStream.write(dataByte);
		}
		String filename = reqFileName + "." + extension; // 다운로드 될 파일명

		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode(filename,"UTF-8"));
		response.setHeader("Content-Length",String.valueOf(outputStream.size()));

		ServletOutputStream sos = response.getOutputStream();
		sos.write(outputStream.toByteArray());
		sos.flush();
		sos.close();
	}

	/**
	 * 코드검색
	 * @param requestData
	 * @return
*/
	@SuppressWarnings("unchecked")
	@PostMapping("setOtpVal.do")
	public Map<String,Object> setOtpVal(@RequestBody String requestData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		String encodedKey = (String)param.get("encodedKey");
		String optCode = (String)param.get("optCode");

		Map<String, Object> res = new LinkedHashMap<>();
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		boolean verify = googleAuthenticator.authorize(encodedKey, Integer.parseInt(optCode));
		res.put("rslt", verify);
		if(verify) {
			res.put("url", "intro.do");
			res.put("message", "");
		}else{
			res.put("url", "otpLogin.do");
			res.put("message", "OPT 코드 오류입니다. 다시 입력하세요");
		}
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}	


	/**
	 * 코드검색
	 * @param requestData
	 * @return
	 * @throws Exception 
*/
	@SuppressWarnings("unchecked")
	@PostMapping("checkGOtpVal.do")
	public Map<String,Object> checkGOtpVal(@RequestBody String requestData, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		String otpSecrKey = (String)param.get("otpSecrKey");
		String optCode = (String)param.get("optCode");

		Map<String, Object> res = new LinkedHashMap<>();
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		boolean verify = googleAuthenticator.authorize(otpSecrKey, Integer.parseInt(optCode));
		if(verify) {
			if(userAuthorityService.updateOtpSecKey(param)<=0)
				throw new Exception("otpSecrKey update 오류");
		}
		param.put("verify", verify);
		res.put("Data", param);
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}	
	/**
	 * 코드검색
	 * @param requestData
	 * @return
	 **/ 
	@SuppressWarnings("unchecked")
	@PostMapping("checkOPTUserInfo.do")
	public Map<String,Object> checkOPTUserInfo(@RequestBody String requestData, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		param = userAuthorityService.searchGoogleOptInfoByuserid((String)param.get("userid"));
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("Data", param);
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}	

	/**
	 * 스케즐실행
	 * @return
	 **/ 
	@SuppressWarnings("unchecked")
	@PostMapping("updateSchedule.do")
	public Map<String,Object> updateSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		schedulerService.updateSchedule();
		Map<String, Object> res = new LinkedHashMap<>();
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}	

}
