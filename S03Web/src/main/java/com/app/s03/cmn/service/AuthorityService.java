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
- 권한관리서비스
-===============================================================================================================
*/
@Service("cmn.AuthorityService")
public class AuthorityService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * 권한코드목록
	 * @param param
	 * @return
	 */
	public List<?> getAuthorityList(Map<String, ?> param) {
		return commonMapper.selectList("ac.Authority.selectAuthorityList", param);
	}

	/**
	 * 권한코드저장
	 * @param savedata
	 */
	@SuppressWarnings("unchecked")
	public void saveAuthorityList(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {
			commonMapper.insert("ac.Authority.insertAuthority", data);
		}
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {
			commonMapper.update("ac.Authority.updateAuthority", data);
		}
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {
			commonMapper.update("ac.Authority.deleteAuthority", data);
		}
	}

	/**
	 * 권한부여자목록
	 * @param param
	 * @return
	 */
	public List<?> getAuthorityUserList(Map<String, ?> param) {
		return commonMapper.selectList("ac.Authority.selectAuthorityUserList", param);
	}

	/**
	 * 권한미부여자목록
	 * @param param
	 * @return
	 */
	public List<?> getNotAuthorityUserList(Map<String, ?> param) {
		return commonMapper.selectList("ac.Authority.selectNotAuthorityUserList", param);
	}

	/**
	 * 권한부여자추가
	 * @param createdata
	 */
	@SuppressWarnings("unchecked")
	public void createAuthorityUserList(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {
			commonMapper.insert("ac.Authority.insertAuthorityUser", data);// 권한사용자추가
			data.put("granttp", "1000");//권한부여
			commonMapper.insert("ac.Authority.insertAuthorityUserHist", data);// 이력추가

		}
	}

	/**
	 * 권한부여자삭제
	 * @param removedata
	 */
	@SuppressWarnings("unchecked")
	public void removeAuthorityUserList(Map<String, ?> gridData) {
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {
			commonMapper.delete("ac.Authority.deleteAuthorityUser", data);//권한사용자삭제
			data.put("granttp", "2000");//권한회수
			commonMapper.insert("ac.Authority.insertAuthorityUserHist", data);// 이력추가
		}
	}

	/**
	 * 메뉴권한목록
	 * @param param
	 * @return
	 */
	public List<?> getMenuAuthList(Map<String, ?> param) {
		return commonMapper.selectList("ac.Authority.selectMenuAuthList", param);
	}

	/**
	 * 메뉴미권한목록
	 * @param param
	 * @return
	 */
	public List<?> getNotMenuAuthList(Map<String, ?> param) {
		return commonMapper.selectList("ac.Authority.selectNotMenuAuthList", param);
	}

	/**
	 * 메뉴권한추가
	 * @param createdata
	 */
	@SuppressWarnings("unchecked")
	public void createMenuAuthList(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {
			data.put("cauthyn", "N");
			data.put("uauthyn", "N");
			data.put("dauthyn", "N");
			data.put("eauthyn", "N");
			data.put("pauthyn", "N");
			data.put("vauthyn", "N");
			data.put("wauthyn", "N");
			data.put("xauthyn", "N");
			data.put("yauthyn", "N");
			data.put("zauthyn", "N");
			commonMapper.insert("ac.Authority.insertAuthorityProgram", data);//권한프로그램추가
			data.put("granttp", "1000");//권한부여
			commonMapper.insert("ac.Authority.insertAuthorityProgramHist", data);//이력추가
		}
	}

	/**
	 * 메뉴권한저장
	 * @param createdata
	 */
	@SuppressWarnings("unchecked")
	public void saveMenuAuthList(Map<String, ?> gridData) {
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {
			commonMapper.update("ac.Authority.updateAuthorityProgram", data);//권한프로그램수정
			data.put("granttp", "3000");//권한수정
			commonMapper.insert("ac.Authority.insertAuthorityProgramHist", data);//이력추가
		}
	}

	/**
	 * 권한프로그램권한부여삭제
	 * @param createdata
	 */
	@SuppressWarnings("unchecked")
	public void removeMenuAuthList(Map<String, ?> gridData) {
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {
			commonMapper.delete("ac.Authority.deleteAuthorityProgram", data);//권한프로그램삭제
			data.put("granttp", "2000");//권한회수
			commonMapper.insert("ac.Authority.insertAuthorityProgramHist", data);//이력추가
		}
	}

	/**
	 * 권한프로그램이력목록
	 * @param param
	 * @return
	 */
	public List<?> getAuthProgamHistList(Map<String, ?> param) {
		return commonMapper.selectList("ac.Authority.selectAuthProgamHistList", param);
	}

	/**
	 * 권한사용자이력목록
	 * @param param
	 * @return
	 */
	public List<?> getAuthUserHistList(Map<String, ?> param) {
		return commonMapper.selectList("ac.Authority.selectAuthorityUserHistList", param);
	}

}