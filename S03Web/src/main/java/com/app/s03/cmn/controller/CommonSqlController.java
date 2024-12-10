package com.app.s03.cmn.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.service.CommonTransactionService;
import com.app.s03.cmn.utils.CommonUtils;


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
@RequestMapping("/api/co/SQL/")
public class CommonSqlController {

    @Autowired
    private CommonTransactionService commonTransactionService;

	/**
	 * 시스템코드목록
	 * @param request
	 * @return
	 */
	@PostMapping("executeSql.do")
	public Map<String, ?> executeSql(@RequestBody String requestData) throws Exception{
	    Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
	    Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String, Object> res = new LinkedHashMap<>();
		res.put("rsltMap", commonTransactionService.excuteSql(param));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

    /**
     * 시스템코드목록
     * @param request
     * @return
     */
    @PostMapping("makeSqlXml.do")
    public Map<String, ?> makeSqlXml(@RequestBody String requestData) throws Exception{
        Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
        Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("rsltMap", commonTransactionService.makeSqlXml(param));
        res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
        return res;
    }
}
