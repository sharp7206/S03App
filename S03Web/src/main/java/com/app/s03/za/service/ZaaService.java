package com.app.s03.za.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.s03.cmn.mapper.CommonMapper;

/**
-===============================================================================================================
- Z01_USER_INFO 관리서비스
-===============================================================================================================
- 공통 서비스
-===============================================================================================================
*/
@Service("za.ZaaService")
public class ZaaService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * 시스템리스트 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> getSystemList(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("za.ZAA.selectSystemList", param);
	}

	/**
	 * 사용자저장
	 * @param param
	 * @return
	 */
	public void saveSystemList(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {
			commonMapper.insert("za.ZAA.saveZ01UserInfo", data);
		}
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {
			commonMapper.update("za.ZAA.saveZ01UserInfo", data);
		}
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {
			commonMapper.update("za.ZAA.deleteZ01UserInfo", data);
		}
	}

	
	/**
	 * 사용자조회
	 * @param param
	 * @return
	 */
	public List<?> selectS01User(Map<String, ?> param) {
		return commonMapper.selectList("za.ZAA.selectS01User", param);
	}
	
	/**
	 * 사용자저장
	 * @param param
	 * @return
	 */
	public void saveZ01UserInfo(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {
			commonMapper.insert("za.ZAA.saveZ01UserInfo", data);
		}
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {
			commonMapper.update("za.ZAA.saveZ01UserInfo", data);
		}
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {
			commonMapper.update("za.ZAA.deleteZ01UserInfo", data);
		}
	}

    /**
     * 코드검색
     * @param param
     * @return
     */
    public List<?> deleteZ01UserInfo(Map<String, ?> param) {
        return commonMapper.selectList("za.ZAA.deleteZ01UserInfo", param);
    }

}
