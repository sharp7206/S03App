package com.app.s03.cmn.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.s03.cmn.mapper.CommonMapper;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("cmn.CommonCodeService")
public class CommonCodeService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * 그룹코드트리
	 * @param param
	 * @return
	 */
	public List<?> getGrpcdTree(Map<String,?> param){
		return commonMapper.selectList("cmn.CommonCode.getGrpcdTree",param);
	}

	/**
	 * 그룹코드
	 * @param param
	 * @return
	 */
	public List<?> getCodeList(Map<String,?> param){
		return commonMapper.selectList("cmn.CommonCode.getCodeListN",param);
	}

	
	/**
	 * 그룹코드 저장.
	 * @param gridData
	 */
	@SuppressWarnings("unchecked")
	public void saveZ01Master(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {// 신규추가 작업
			commonMapper.insert("cmn.CommonCode.insertZ01CodeMaster",data);
		}
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {// 수정 작업
			commonMapper.update("cmn.CommonCode.updateZ01CodeMaster",data);
		}
	}

	/**
	 * 그룹코드 삭제
	 * @param gridData
	 */
	@SuppressWarnings("unchecked")
	public void deleteZ01CodeMaster(Map<String, ?> gridData) {
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {// 삭제 작업
			commonMapper.delete("cmn.CommonCode.deleteZ01CodeMaster",data);
		}
	}

	/**
	 * 상세코드
	 * @param param
	 * @return
	 */
	public List<?> selectZ01CodeDetail(Map<String,?> param){
		return commonMapper.selectList("cmn.CommonCode.selectZ01CodeDetail",param);
	}

	/**
	 * 상세코드 저장.
	 * @param gridData
	 */
	@SuppressWarnings("unchecked")
	public void saveDtlcdList(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {// 신규추가 작업
			commonMapper.insert("cmn.CommonCode.insertZ01CodeDetail",data);
		}
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {// 수정 작업
			commonMapper.update("cmn.CommonCode.updateZ01CodeDetail",data);
		}
	}

	/**
	 * 상세코드삭제
	 * @param gridData
	 */
	@SuppressWarnings("unchecked")
	public void removeDtlcdList(Map<String, ?> gridData) {
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {// 삭제 작업
			commonMapper.delete("cmn.CommonCode.deleteZ01CodeDetail",data);
		}
	}
	
    /**
     * 공통코드 조회 cache (시스템구분코드 + 그룹코드 별 cache 관리)
     * @param param
     * @return
     */
 // 특정 코드 return
    @Cacheable(value = "commonCodeCache")
    public List<?> getAllCodeList(Map<String, ?> map) {
        return commonMapper.selectList("cmn.CommonCode.getAllCodeList", map);
    }
}