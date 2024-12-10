package com.app.s03.cmn.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.exception.CommonBusinessException;
import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.utils.ComProperties;
import com.app.s03.cmn.utils.ConChar;
import com.app.s03.cmn.utils.SysFileUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 파일서비스
-===============================================================================================================
*/
@Slf4j
@Service("cmn.FileService")
public class FileService {

	private final static String FILE_SEPERATOR = "/";
	@Value("${file.uploadPath}")
	private String fileRootPath;

	@Value("${sysCd}")
	private String sysCd;

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * @return
	 */
	public String getFileRootPath() {
		return fileRootPath;
	}

	/**
	 * 파일목록
	 * @param param
	 * @return
	 */
	public List<?> selectFileList(String fileGid) {
		
		Map<String, Object> param = new HashMap();
		param.put("SYS_CD", ComConstants.SYS_CD);
		param.put("FILE_GRP_ID", fileGid);
		return commonMapper.selectList("cmn.File.selectTbAplFileInfoByFileGrpIdAndSeq", param);
	}

	/**
	 * 파일
	 * @param param
	 * @return
	 */
	public Map<String, ?> getFile(Map<String, ?> param) {
		return commonMapper.selectOne("cmn.File.selectFile", param);
	}

	/**
	 * 파일저장
	 * @param file
	 */
	public void addFileInfo(Map<String, Object> file) {
		commonMapper.insert("cmn.File.insertFile", file);
	}

	/**
	 * 파일삭제
	 * @param gridData
	 */
	@SuppressWarnings("unchecked")
	public void removeFileList(Map<String, ?> gridData) {
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {// 삭제 작업
			Map<String, ?> fileInfo = commonMapper.selectOne("cmn.File.selectFile", data);
			File targetFile = new File(this.getFileRootPath() + fileInfo.get("filepath") + fileInfo.get("savenm"));
			log.info("targetFile:{}",targetFile.toString());
			FileUtils.deleteQuietly(targetFile); // 저장된 현재 파일 삭제
			commonMapper.delete("cmn.File.deleteFile", data);
		}
	}
	
	/**
	 * 첨부파일조회 with fileGrpId, sn(map
	 * @param Map(fileGrpId, sn)
	 * @return List<?>
	 */
	public List<?> selectTbAplFileInfoByFileGrpIdAndSeqList(Map<String,?> param){
		return commonMapper.selectList("cmn.File.selectTbAplFileInfoByFileGrpIdAndSeq",param);
	}

	/**
	 * 첨부파일조회 with fileGrpId, sn(map
	 * @param Map(fileGrpId, sn)
	 * @return List<?>
	 */
	public Map<String, Object> selectTbAplFileInfoByFileGrpIdAndSeq(Map<String,?> param){
		return commonMapper.selectOne("cmn.File.selectTbAplFileInfoByFileGrpIdAndSeq", param);
	}


	/**
	 * 첨부파일조회 with List<String<fileGrpId>>
	 * @param Map(fileGrpId)
	 * @return List<?>
	 */
	public List<?> selectTbAplFileInfoByFileGrpIdList(Map<String,?> param){
		return commonMapper.selectList("cmn.File.selectTbAplFileInfoByFileGrpIdList",param);
	}

	/**
	 * 첨부파일조회 with List<String<fileGrpId+sn>>
	 * @param Map(List<fileGrpId+sn>)
	 * @return List<?>
	 */
	public List<?> selectTbAplFileInfoByFileGrpIdAndSnList(Map<String,?> param){
		return commonMapper.selectList("cmn.File.selectTbAplFileInfoByFileGrpIdAndSnList",param);
	}
	
	/**
	 * 첨부파일저장
	 * @param Map(List<fileGrpId+sn>)
	 * @return List<?>
	 */
	@Transactional(rollbackFor = {Exception.class})
	public String saveFileInfoList(Map<String,Object> param) throws Exception{
		String fileGrpId = (String)param.get("FILE_GRP_ID");
    	String fileSeq = "";
        String oriFilePath = "";
        String tagFilePath = "";
        String thumbnailPath = "";
        String fileOrgNm = "";
        String fileNm = "";
		List<Map<String, Object>> fileList= (List<Map<String, Object>>)param.get("gridData");// 그리드 데이타
		// Validation Check
        if (fileList == null || fileList.size() <= 0) {
            throw new CommonBusinessException("첨부파일의 정보가 없습니다.");
        }
        
        if(ConChar.isNull(fileGrpId)) {
        	fileGrpId = commonMapper.selectOne("cmn.File.selectFileGrpIdKey", sysCd);//시스템 코드 추가
        }
        if (!SysFileUtils.createDirAll(fileRootPath)) {
			throw new Exception("폴더생성이 안되었습니다.");
		}
    	for (Map<String, Object> dataMap : fileList) {// 신규추가 작업
			dataMap.put("SYS_CD", sysCd);
			if("I".equals(dataMap.get("SSTATUS"))) {
				//FileInfo Save
				dataMap.put("FILE_GRP_ID", fileGrpId);
				fileSeq = commonMapper.selectOne("cmn.File.selectFileGrpSeq",dataMap);
				dataMap.put("FILE_SEQ", fileSeq);
	    		//File Move to Real
				oriFilePath = dataMap.get("FILE_PATH") + FILE_SEPERATOR
	                	+ FilenameUtils.getName((String)dataMap.get("FILE_NM"));
				tagFilePath = 	fileRootPath + FILE_SEPERATOR + FilenameUtils.getName((String)dataMap.get("FILE_NM"));
				File oriFile = new File(oriFilePath);
	            File tagFile = new File(tagFilePath);
				SysFileUtils.fileCopyAndDelOriFile(oriFile, tagFile, true);
				dataMap.put("FILE_PATH", fileRootPath);
				dataMap.put("FILE_ORG_NM", dataMap.get("FILE_ORG_NM"));
				commonMapper.insert("cmn.File.insertTbAplFileInfo", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))) {
				tagFilePath = dataMap.get("FILE_PATH") + FILE_SEPERATOR
                        + FilenameUtils.getName((String)dataMap.get("FILE_NM"));  
				File file = new File(tagFilePath);
                if(file.exists()) {
                	file.delete();
                }
				commonMapper.delete("cmn.File.deleteTbAplFileInfoByFileGrpIdAndSeq", dataMap);
			}
		}
    	return fileGrpId;
	}	
	
	/**
     * 파일정보저장
     * 
     * @param FileMInfoListVO
     * @return FileMInfoListVO
     * @throws Exception
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
	public String saveFileInfoJson(String fileGrpId, String jsonStr, String rfrnDocId) throws Exception{
		//String fileGrpId = (String)param.get("FILE_GRP_ID");
    	String fileSeq = "";
        String oriFilePath = "";
        String tagFilePath = "";
        String thumbnailPath = "";
        String fileOrgNm = "";
        String fileNm = "";
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Map<String, Object>> fileList = objMapper.readValue(jsonStr, new TypeReference<List<Map<String, Object>>>() {
        });
//        List<Map<String, Object>> fileList = (List<Map<String, Object>>)param.get("fileList");
		// Validation Check
        if (fileList == null || fileList.size() <= 0) {
            throw new CommonBusinessException("첨부파일의 정보가 없습니다.");
        }
        
        if(ConChar.isNull(fileGrpId)) {
        	fileGrpId = commonMapper.selectOne("cmn.File.selectFileGrpIdKey", sysCd);//시스템 코드 추가
        }
        if (!SysFileUtils.createDirAll(fileRootPath)) {
			throw new Exception("폴더생성이 안되었습니다.");
		}
    	for (Map<String, Object> dataMap : fileList) {// 신규추가 작업
			dataMap.put("SYS_CD", sysCd);
			if("I".equals(dataMap.get("SSTATUS"))) {
				//FileInfo Save
				dataMap.put("FILE_GRP_ID", fileGrpId);
				fileSeq = commonMapper.selectOne("cmn.File.selectFileGrpSeq",dataMap);
				dataMap.put("FILE_SEQ", fileSeq);
	    		//File Move to Real
				oriFilePath = dataMap.get("FILE_PATH") + FILE_SEPERATOR
	                	+ FilenameUtils.getName((String)dataMap.get("FILE_NM"));
				tagFilePath = 	fileRootPath + FILE_SEPERATOR + FilenameUtils.getName((String)dataMap.get("FILE_NM"));
				File oriFile = new File(oriFilePath);
	            File tagFile = new File(tagFilePath);
				SysFileUtils.fileCopyAndDelOriFile(oriFile, tagFile, true);
				dataMap.put("REF_DOC_ID", rfrnDocId);
				dataMap.put("FILE_PATH", fileRootPath);
				dataMap.put("FILE_ORG_NM", dataMap.get("FILE_ORG_NM"));
				commonMapper.insert("cmn.File.insertTbAplFileInfo", dataMap);
			}else if("D".equals(dataMap.get("SSTATUS"))) {
				tagFilePath = dataMap.get("FILE_PATH") + FILE_SEPERATOR
                        + FilenameUtils.getName((String)dataMap.get("FILE_NM"));  
				File file = new File(tagFilePath);
                if(file.exists()) {
                	file.delete();
                }
				commonMapper.delete("cmn.File.deleteTbAplFileInfoByFileGrpIdAndSeq", dataMap);
			}
		}
    	return fileGrpId;
	}	

	/**
     * 파일정보저장
     * 
     * @param FileMInfoListVO
     * @return FileMInfoListVO
     * @throws Exception
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
	public boolean deleteFileInfoByFileGid(String fileGidStr) throws Exception{
    	
		//String fileGrpId = (String)param.get("FILE_GRP_ID");
    	List<?> fileIdList = ConChar.stringToArrayList(fileGidStr, ",");
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("sysCd", paramMap);
    	paramMap.put("snList", fileIdList);
    	List<Map<String, Object>> fileList = commonMapper.selectList("cmn.File.selectTbAplFileInfoByFileGrpIdList", paramMap);
    	String filePath= "";
    	for (Map<String, Object> dataMap : fileList) {
    		filePath = (String)dataMap.get("FILE_PATH") + FILE_SEPERATOR +(String)dataMap.get("FILE_NM") ;
    		if((new File(filePath)).exists()) {
                (new File(filePath)).delete();
    		}
        }
    	
    	commonMapper.delete("cmn.File.deleteTbAplFileInfoByFileGrpIdList", paramMap);
    	return true;
	}	
}
