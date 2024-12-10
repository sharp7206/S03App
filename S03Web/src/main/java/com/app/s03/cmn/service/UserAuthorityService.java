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
- 사용자 관련 서비스
-===============================================================================================================
*/
@Service("cmn.UserAuthorityService")
public class UserAuthorityService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * 사용자 권한 시스템 목록
	 * @param param
	 * @return
	 */
	public List<?> getUserSystemList(Map<String, ?> param) {
		return commonMapper.selectList("cmn.UserAuthority.selectUserSystemList", param);
	}

	/**
	 * 사용자권한시스템 메뉴 목록
	 * @param param
	 * @return
	 */
	public List<?> getUserMenuTreeData(Map<String, ?> param) {
		return commonMapper.selectList("cmn.UserAuthority.selectUserMenuTreeDataList", param);
	}

	/**
	 * 사용자권한시스템 Left메뉴 목록
	 * @param param
	 * @return
	 */
	public List<?> getUserSideMenuTreeData(Map<String, ?> param) {
		return commonMapper.selectList("cmn.UserAuthority.selectUserSideMenuTreeDataList", param);
	}

	/**
	 * 사용자의 프로그램 권한 조회.
	 * @param param
	 * @return
	 */
	public String getUserProgramAuthority(Map<String, ?> param) {
		return commonMapper.selectOne("cmn.UserAuthority.selectUserProgramAuthority", param);
	}

	/**
	 * OTP계정유무확인
	 * @param param
	 * @return
	 */
	public Map<String, Object> searchGoogleOptInfoByuserid(String userid) {
		return commonMapper.selectOne("cmn.UserAuthority.searchGoogleOptInfoByuserid", userid);
	}
	
	/**
	 * OTP otpSecrKey update
	 * @param param
	 * @return
	 */	
	/**
	 * OTP계정유무확인
	 * @param param
	 * @return
	 */
	public int updateOtpSecKey(Map<String, ?> param) {
		return commonMapper.update("cmn.UserAuthority.updateOtpSecKey", param);
	}	

	/**
	 * TOKEN저장
	 * @param param
	 * @return
	 */
	public int insertZ01TokenInfo(String userid) {
		return commonMapper.insert("cmn.UserAuthority.insertZ01TokenInfo", userid);
	}
	

	/**
	 * TOKEN삭제
	 * @param param
	 * @return
	 */
	public int deleteZ01TokenInfo(String userid) {
		return commonMapper.insert("cmn.UserAuthority.deleteZ01TokenInfo", userid);
	}
	
}
