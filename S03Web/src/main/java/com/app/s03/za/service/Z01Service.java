package com.app.s03.za.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.utils.ConChar;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
-===============================================================================================================
- Z01_USER_INFO 관리서비스
-===============================================================================================================
- 공통 서비스
-===============================================================================================================
*/
@Service("za.Z01Service")
public class Z01Service {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;
    
	/**
	 * 시스템리스트 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchSysList(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("z01.Z01Mapper.searchSysList", param);
	}

	/**
	 * 시스템리스트저장
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void saveZ01Sys(List<Map<String, ?>> sheetList) throws Exception{
		for (Map<String, ?> dataMap : sheetList) {
			if("I".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.createZ01Sys", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.update("z01.Z01Mapper.updateZ01Sys", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01Sys", dataMap);
			}
		}
	}

	
	/**
	 * 메뉴정보조회
	 * @param param
	 * @return
	 */
	public List<?> selectZ01Menu(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.selectZ01Menu", param);
	}
	
	

	/**
	 * 메뉴리스트저장
	 * @param param
	 * @return
	 */
	public void saveZ01Menu(List<Map<String, ?>> sheetList) throws Exception{
		for (Map<String, ?> dataMap : sheetList) {
			if("I".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.createZ01Menu", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.update("z01.Z01Mapper.updateZ01Menu", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01Menu", dataMap);
			}
		}
	}
	
	
	/**
	 * ROLE정보조회
	 * @param param
	 * @return
	 */
	public List<?> searchRoleList(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.searchRoleList", param);
	}
	
	/**
	 * ROLE정보저장
	 * @param param
	 * @return
	 */
	public void saveZ01Role(List<Map<String, ?>> sheetList) throws Exception{
		for (Map<String, ?> dataMap : sheetList) {
			if("I".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.createZ01Role", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.update("z01.Z01Mapper.updateZ01Role", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01Role", dataMap);
			}
		}
	}
	
	/**
	 * ROLE Item정보조회
	 * @param param
	 * @return
	 */
	public List<?> searchZ01RoleItem(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.searchZ01RoleItem", param);
	}
	/**
	 * ROLE Item정보저장
	 * @param param
	 * @return
	 */
	public void saveZ01RoleItem(List<Map<String, ?>> sheetList) throws Exception{
		for (Map<String, ?> dataMap : sheetList) {
			if("I".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.createZ01RoleItem", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.update("z01.Z01Mapper.updateZ01RoleItem", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01RoleItem", dataMap);
			}
		}
	}
		
	
	/**
	 * 사용자조회(team)
	 * @param param
	 * @return
	 */
	public List<?> searchPeopleTeam(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.searchPeopleTeam", param);
	}
	
	/**
	 * ROLE별 화면을 조회(team)
	 * @param param
	 * @return
	 */
	public List<?> searchSysMenuForRole(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.searchSysMenuForRole", param);
	}	
	
	/**
	 * 사용자조회(team)
	 * @param param
	 * @return
	 */
	public List<?> searchZ01SysUser(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.searchZ01SysUser", param);
	}
	
	/**
	 * 사용자조회2(team)
	 * @param param
	 * @return
	 */
	public List<?> searchPeoplePersonal(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.searchPeoplePersonal", param);
	}	
	
	/**
	 * system 사용자저장
	 * @param param
	 * @return
	 */
	public void saveZ01SysUser(List<Map<String, ?>> sheetList) throws Exception{
		
		for (Map<String, ?> dataMap : sheetList) {
			if("I".equals(dataMap.get("SSTATUS"))||"U".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.mergeZ01SysUser", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01SysUser", dataMap);
			}
		}		
	}	
	
	/**
	 * 사용자조회
	 * @param param
	 * @return
	 */
	public List<?> selectS01User(Map<String, ?> param) {
		return commonMapper.selectList("z01.Z01Mapper.selectS01User", param);
	}	


	/**시스템로그저장
	 * @param Map<String, ?>
	 * @return void
	 * */
	public void createZ01SystemLog(Map<String, ?> dataMap) {
		commonMapper.insert("z01.Z01Mapper.insertZ01SystemLog", dataMap);
	}


	/**
	 * 코드마스터 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchZ01CodeMaster(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("z01.Z01Mapper.searchZ01CodeMaster", param);
	}	

	/**
	 * 코드마스터 저장
	 * @param param
	 * @return
	 */
	public void saveZ01CodeMaster(List<Map<String, ?>> sheetList) throws Exception{
		
		for (Map<String, ?> dataMap : sheetList) {
			if("I".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.insertZ01CodeMaster", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.updateZ01CodeMaster", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01CodeMaster", dataMap);
			}
		}		
	}	

	/**
	 * 코드디테일 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchZ01CodeDetail(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("z01.Z01Mapper.searchZ01CodeDetail", param);
	}	

	/**
	 * 코드디테일 저장
	 * @param param
	 * @return
	 */
	public void saveZ01CodeDetail(List<Map<String, ?>> sheetList) throws Exception{
		
		for (Map<String, ?> dataMap : sheetList) {
			if("I".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.insertZ01CodeDetail", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.updateZ01CodeDetail", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01CodeDetail", dataMap);
			}
		}		
	}	
	
	/**
	 * 시스템로그 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchZ01SystemLog(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("z01.Z01Mapper.selectZ01SystemLog", param);
	}		
	
	
	/**
	 * Job 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<Map<String, Object>> searchZ01SchInfo() throws Exception{
		Map<String, Object> param = new HashMap<>();
		param.put("SYS_CD", ComConstants.SYS_CD);
		return commonMapper.selectList("z01.Z01Mapper.searchZ01SchInfo", param);
	}		
	
	/**
	 * Job 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchZ01SchInfo(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("z01.Z01Mapper.searchZ01SchInfo", param);
	}		
	
	
	
	/**
	 * Job 저장
	 * @param param
	 * @return
	 */
	public void saveZ01SchInfo(List<Map<String, Object>> sheetList) throws Exception{
		String jobId = "";
		String sysCd = ComConstants.SYS_CD;
		for (Map<String, Object> dataMap : sheetList) {
			jobId = (String)dataMap.get("JOB_ID");
			if(ConChar.isNull(jobId)) {
				jobId = commonMapper.selectOne("z01.Z01Mapper.createJobId", dataMap);
				dataMap.put("JOB_ID", jobId);
			}
			
			if("I".equals(dataMap.get("SSTATUS")) || "U".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("z01.Z01Mapper.mergeZ01SchInfo", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("z01.Z01Mapper.deleteZ01SchInfo", dataMap);
			}
		}		
	}	
	
	/**
	 * IOT List 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchZ01IOTList(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("z01.Z01Mapper.searchZ01IOTList", param);
	}	
	
	/**
	 * Save IOT List
	 * @param param
	 * @return
	 */
	public void saveZ01IOTList(Map<String, ?> param) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> sheetList =objectMapper.readValue((String)param.get("gridData"), new TypeReference<List<Map<String, Object>>>() {});
		for (Map<String, Object> dataMap : sheetList) {
			dataMap.put("SITE_CD", param.get("SITE_CD"));
			dataMap.put("FACTORY_CD", dataMap.get("factory_cd"));
			dataMap.put("IOT_ID", dataMap.get("id"));
			
			commonMapper.update("z01.Z01Mapper.updateZ01IOTInfo", dataMap);
			
		}		
	}		
}
