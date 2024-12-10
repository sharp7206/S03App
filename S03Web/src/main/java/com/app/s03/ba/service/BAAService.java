package com.app.s03.ba.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.service.FileService;
import com.app.s03.cmn.utils.ConChar;
import com.app.s03.cmn.utils.DateTime;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
-===============================================================================================================
- Z01_USER_INFO 관리서비스
-===============================================================================================================
- 공통 서비스
-===============================================================================================================
*/
@Slf4j
@Service("ba.BAAService")
public class BAAService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;
    @Autowired
    private FileService fileService;

	/**
	 * 대부금리스트조회
	 * @param param
	 * @return List<?> 
	 */
	public List<?> selectS03MortgageLoanList(Map<String, ?> param) throws Exception{
		List<?> retList = commonMapper.selectList("ba.BaaMapper.selectS03MortgageLoanList", param);
		return retList;
	}
	
	/**
	 * 대부금리스트조회
	 * @param param
	 * @return List<?> 
	 */
	public Map<String, Object> selectS03MortgageLoan(Map<String, ?> param) throws Exception{
		if(ConChar.isNull((String)param.get("LOAN_ID"))) {
			throw new Exception("대출번호가 없습니다.");
		}
		Map<String, Object> retMap = commonMapper.selectOne("ba.BaaMapper.selectS03MortgageLoan", param);
		if(retMap!=null) {
			if(!ConChar.isNull((String)retMap.get("FILE_GID"))) {
				retMap.put("fileList", fileService.selectFileList((String)retMap.get("FILE_GID")));
			}
			List<?> contackList = commonMapper.selectList("ba.BaaMapper.selectS03MortgageContact", param);
			List<?> payList = new ArrayList<>();
			retMap.put("contactList", contackList);
			if("REPAY_CD-D".equals((String)retMap.get("PAY_MTH"))) {
				payList = commonMapper.selectList("ba.BaaMapper.selectS03MortgagePayListD", param);
			}else {
				payList = commonMapper.selectList("ba.BaaMapper.selectS03MortgagePayList", param);
			}
			retMap.put("payList", payList);
		}
		return retMap;
	}

	/**
	 * 대부금저장
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class})
	public String saveS03MortgageLoan(Map<String, Object> param) throws Exception{
		String loanId = (String)param.get("LOAN_ID");
		List<Map<String, Object>> sheetList = (List<Map<String, Object>>)param.get("gridData");
		List<Map<String, Object>> payList = (List<Map<String, Object>>)param.get("payList");
		String fileGid = (String)param.get("FILE_GID");
		if(ConChar.isNull(loanId)) {
			loanId = commonMapper.selectOne("ba.BaaMapper.createLoanId");
			param.put("LOAN_ID", loanId);
			param.put("FILE_GID", fileGid);
			commonMapper.insert("ba.BaaMapper.insertS03MortgageLoan", param);
		}
		String debtorId = "";
		for (Map<String, Object> dataMap : sheetList) {
			dataMap.put("LOAN_ID", loanId);
			if("I".equals(dataMap.get("SSTATUS"))){
				debtorId = commonMapper.selectOne("ba.BaaMapper.createS03MortgageDebtorId", loanId);
				dataMap.put("DEBTOR_ID", debtorId);
				commonMapper.insert("ba.BaaMapper.insertS03MortgageContact", dataMap);
			}else if("U".equals(dataMap.get("SSTATUS"))){
				commonMapper.update("ba.BaaMapper.updateS03MortgageContact", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("ba.BaaMapper.deleteS03MortgageContact", dataMap);
			}
		}
		for (Map<String, Object> dataMap : payList) {
			dataMap.put("LOAN_ID", loanId);
			if("I".equals(dataMap.get("SSTATUS"))||"U".equals(dataMap.get("SSTATUS"))){
				commonMapper.update("ba.BaaMapper.insertS03MortgagePay", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))){
				commonMapper.delete("ba.BaaMapper.deleteS03MortgagePay", dataMap);
			}
		}
		if(!ConChar.isNull((String)param.get("FILE_GIDStr"))) {
			fileGid = fileService.saveFileInfoJson(fileGid, (String)param.get("FILE_GIDStr"), loanId);
		}
		log.info("FILE_GID", fileGid);
		param.put("FILE_GID", fileGid);
		commonMapper.update("ba.BaaMapper.updateS03MortgageLoan", param);
		return loanId;
	}
	
	/**
	 * 대부금저장
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class})
	public void deleteS03MortgageLoan(Map<String, Object> param) throws Exception{
		String loanId = (String)param.get("LOAN_ID");
		String fileGid = (String)param.get("FILE_GID");
		
		List<Map<String, Object>> sheetList = (List<Map<String, Object>>)param.get("gridData");
		if(ConChar.isNull(loanId)) {
			throw new Exception("LOAN_ID 가 없습니다.");
		}
		commonMapper.delete("ba.BaaMapper.deleteS03MortgageLoan", param);
		/*
		//commonMapper.delete("ba.BaaMapper.deleteS03MortgageContactAll", param);
		//commonMapper.delete("ba.BaaMapper.deleteS03MortgagePayAll", param);
		
		if(!ConChar.isNull((String)param.get("FILE_GID"))) {
			fileService.deleteFileInfoByFileGid((String)param.get("FILE_GID"));
		}
		*/
		log.info("successfully deleted ", DateTime.getGenDate());
	}	
}
