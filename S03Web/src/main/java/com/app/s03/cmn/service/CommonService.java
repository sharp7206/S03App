package com.app.s03.cmn.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.s03.cmn.mapper.CommonMapper;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 공통 서비스
-===============================================================================================================
*/
@Service("cmn.CommonService")
public class CommonService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * 부서검색
	 * @param param
	 * @return
	 */
	public List<?> getDeptList(Map<String, ?> param) {
		return commonMapper.selectList("cmn.Common.selectDeptList", param);
	}
	/**
	 * 사용자검색
	 * @param param
	 * @return
	 */
	public List<?> getUserList(Map<String, ?> param) {
		return commonMapper.selectList("cmn.Common.selectUserList", param);
	}

    /**
     * 코드검색
     * @param param
     * @return
     */
    public List<?> getCodeList(Map<String, ?> param) {
        return commonMapper.selectList("cmn.Common.selectCodeList", param);
    }

}
