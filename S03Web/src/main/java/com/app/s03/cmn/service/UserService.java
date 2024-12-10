package com.app.s03.cmn.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.s03.cmn.mapper.CommonMapper;


/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 사용자관리서비스
-===============================================================================================================
*/
@Service("cmn.UserService")
public class UserService {

	private final String DEFAULT_PASSWORD = "APPuser";

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	@Autowired
	private PasswordEncoder passwordEconder;

	/**
	 * 사용자목록
	 * @param param 조회조건.
	 * @return
	 */
	public List<?> getUserList(Map<String, ?> param) {
		return commonMapper.selectList("ac.User.selectUserList", param);// 사용자 목록.
	}

	/**
	 * 사용자
	 * @param param 조회조건.
	 * @return
	 */
	public Map<String,?> getUser(Map<String, ?> param) {
		return commonMapper.selectOne("ac.User.selectUser", param);// 사용자
	}

	/**
	 * 사용자저장
	 * @param data
	 */
	public void saveUser(Map<String, Object> data) {
		if("Y".equals(data.get("_isNew"))) {
			data.put("password",passwordEconder.encode(DEFAULT_PASSWORD));//비밀번호 초기값인코딩.
			commonMapper.insert("ac.User.insertUser", data);// 사용자 정보 추가.
		} else {
			commonMapper.update("ac.User.updateUser", data);// 사용자 정보 수정.
		}
	}

	/**
	 * 사용자잠금해제
	 * @param data
	 */
	public void unlockUser(Map<String, Object> data) {
		commonMapper.update("ac.User.unlockUser", data);// 사용자잠금해제
	}

	/**
	 * 계정만료해제
	 * @param data
	 */
	public void unExpireUser(Map<String, Object> data) {
		commonMapper.update("ac.User.unExpireUser", data);// 계정만료해제
	}

	/**
	 * 계정만료처리
	 * @param data
	 */
	public void expireUser(Map<String, Object> data) {
		commonMapper.update("ac.User.expireUser", data);// 계정만료처리
	}

	/**
	 * 비밀번호초기화
	 * @param data
	 */
	public void resetUserPasswd(Map<String, Object> data) {
		data.put("password",passwordEconder.encode(DEFAULT_PASSWORD));//비밀번호 초기값인코딩.
		commonMapper.update("ac.User.resetUserPasswd", data);// 비밀번호초기화
	}

	/**
	 * 로그인이력
	 * @param param
	 * @return
	 */
	public List<?> getLoginHist(Map<String, ?> param) {
		return commonMapper.selectList("ac.User.selectLoginHist", param);// 로그인이력
	}

	/**
	 * 접근이력
	 * @param param
	 * @return
	 */
	public List<?> getAccesslogList(Map<String, ?> param) {
		return commonMapper.selectList("ac.User.selectAccesslogList", param);// 접근이력
	}

}
