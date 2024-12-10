package com.app.s03.cmn.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.s03.cmn.exception.CommonBusinessException;
import com.app.s03.cmn.mapper.CommonMapper;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 메인화면 서비스
-===============================================================================================================
*/
@Service("cmn.MainService")
public class MainService {

	private final boolean IS_TEST = true;

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	@Autowired
	private PasswordEncoder passwordEconder;

	/**
	 * 사용자비번저장
	 * @param param
	 * @param principal
	 * @return
	 */
	public int saveUserPassword(Map<String, Object> param,String userid) {
		param.put("userid",userid);
		param.put("password",passwordEconder.encode((String)param.get("password")));//비밀번호인코딩.
		return commonMapper.update("cmn.APPUser.updateUserPassword", param);// 사용자비번저장
	}

	/**
	 * 사용자비번동일체크
	 * @param param
	 * @param principal
	 * @return
	 */
	public String validPrivpasswordYn(Map<String, Object> param,String userid) {
		String privpassword = commonMapper.selectOne("cmn.APPUser.userPassword",userid);
		return passwordEconder.matches((String)param.get("privpassword"), privpassword) ? "Y" :"N";
	}

	/**
	 * 사용자사진
	 * @param param
	 * @return
	 */
	public String getUserPhoto(Map<String, ?> param) {
		return commonMapper.selectOne("cmn.APPUser.getUserPhoto",param);
	}

	/**
	 * 최근공지 10개
	 * @param param
	 * @return
	 */
	public List<?> noticeList(Map<String, ?> param) {
		/*
		if(IS_TEST) {
			throw new CommonBusinessException("▨▨▨▨▨ 확인 ▨▨▨▨▨<p>사용자 정의 예외(APPBusinessException) 테스트 입니다!!!!!.</p>");
		}
		*/
		return commonMapper.selectList("cmn.Main.selectNoticeList",param);
	}

	/**
	 * 공지사항
	 * @param param
	 * @return
	 */
	public Object notice(Map<String, ?> param) {
		return commonMapper.selectOne("cmn.Main.selectNotice",param);
	}

	/**
	 * 나의정보조회
	 * @param param
	 * @param principal
	 * @return
	 */
	public Object getMyInfo(Map<String, Object> param) {
		return commonMapper.selectOne("cmn.APPUser.selectMyInfo",param);
	}

	/**
	 * 나의정보저장
	 * @param param
	 * @param principal
	 */
	public void saveMyInfo(Map<String, Object> param) {
		commonMapper.update("cmn.APPUser.updateMyInfo",param);
		if((int)commonMapper.selectOne("cmn.APPUser.selectMyPhoto",param)==0) {
			commonMapper.update("cmn.APPUser.insertMyPhoto",param);
		} else {
			commonMapper.update("cmn.APPUser.updateMyPhoto",param);
		}
	}

}