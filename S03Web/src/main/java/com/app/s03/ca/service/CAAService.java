package com.app.s03.ca.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.service.FileService;
import com.app.s03.cmn.utils.ConChar;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("ca.CAAService")
public class CAAService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;
	@Autowired
    private FileService fileService;
	/**
	 * IOT List 조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchZ01IOTList(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("ca.CaaMapper.searchZ01IOTList", param);
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
			
			commonMapper.update("ca.CaaMapper.updateZ01IOTInfo", dataMap);
			
		}		
	}			
	/**
	 * IMG CAT INFO 
	 * @param param
	 * @return List<?> 
	 */
	public List<?> searchZ01ImgCat(Map<String, ?> param) throws Exception{
		return commonMapper.selectList("ca.CaaMapper.searchZ01ImgCat", param);
	}	
	
	/**
	 * Save IOT List
	 * @param param
	 * @return
	 */
	public void saveZ01ImgCat(Map<String, ?> param) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> sheetList = (List<Map<String, Object>>)param.get("gridData");
		for (Map<String, Object> dataMap : sheetList) {
			String imgGid1 = (String)dataMap.get("imgGid1");
			String catId = (String)dataMap.get("catId"); 
			if(!ConChar.isNull((String)dataMap.get("imgGid1Str"))) {
				imgGid1 = fileService.saveFileInfoJson(imgGid1, (String)dataMap.get("imgGid1Str"), catId);
			}
			dataMap.put("imgGid1", imgGid1);
			if("I".equals(dataMap.get("SSTATUS"))){
				commonMapper.insert("ca.CaaMapper.insertZ01IOTCat", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.update("ca.CaaMapper.updateZ01IOTCat", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("ca.CaaMapper.deleteZ01IOTCat", dataMap);
			}
		}		
	}			
}
