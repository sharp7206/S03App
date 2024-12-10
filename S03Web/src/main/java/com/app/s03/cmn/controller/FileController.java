package com.app.s03.cmn.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.service.FileService;
import com.app.s03.cmn.utils.CommonUtils;
import com.app.s03.cmn.utils.ConChar;
import com.app.s03.cmn.utils.DateTime;
import com.app.s03.cmn.utils.SysFileUtils;
import com.app.s03.cmn.utils.SysUtils;
import com.app.s03.cmn.utils.Utility;
import lombok.extern.slf4j.Slf4j;


/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 공통파일 처리 컨트롤러
-===============================================================================================================
*/
@Slf4j
@RestController
@RequestMapping("/api/cmn/file/")
public class FileController {
	@Value("${file.uploadPath}")
	private String uploadDir;
	
	@Value("${file.uploadTempPath}")
	private String tempDir;

	@Value("${sysCd}")
	private String sysCd;

	private final String ALLOWED_FILE_EXTENTINS = ".pdf,.jpg,.tif,.png,.bmp,.hwp,.jpeg,.xlsx,.xls,.tiff,.ppt,.zip,.pptx,.docx,.doc";//허용된 파일확장자

	private final static String FILE_SEPERATOR = "/";

	@Autowired
	private FileService fileservice;

	/**
	 * 파일업로드 ajax 처리
	 * @param fileno 파일번호
	 * @param path 파일패스코드
	 * @param multipartFile 첨부파일
	 * @return
	 */
	@PostMapping(value = "/upload.do")
	public Map<String, ?> upload(@RequestParam("fileno") String fileno, @RequestParam("path") String path, @RequestParam("files") List<MultipartFile> multipartFile) {
		Map<String, Object> res = new LinkedHashMap<>();
		if (multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {// 파일있으면
			String fileRoot = fileservice.getFileRootPath();
			String filePath = "/" + path + "/" + new SimpleDateFormat("yyyy").format(System.currentTimeMillis()) + "/" + new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()) + "/";
			for (MultipartFile file : multipartFile) {
				String originalFileName = file.getOriginalFilename(); // 원 파일명
				String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase(); // 파일 확장자
				if (StringUtils.isEmpty(extension) || ALLOWED_FILE_EXTENTINS.indexOf(extension.toLowerCase()) == -1 ) {
					res.put(ReturnCode.RTN_CODE, ReturnCode.ERROR);
					res.put("errorMessage", "허용되지 않는 형식의 파일입니다.");
					return res;
				}
				String savedFileName = UUID.randomUUID() + extension; // 저장될 파일 명
				File targetFile = new File(fileRoot + filePath + savedFileName);
				if (log.isDebugEnabled()) {
					log.debug("fileno check1 is {}", fileno);
				}
				if (StringUtils.isEmpty(fileno)) {// 파일번호 없으면 생성
					fileno = RandomStringUtils.randomAlphanumeric(5) + "-" + UUID.randomUUID();
				}
				if (log.isDebugEnabled()) {
					log.debug("fileno check2 is {}", fileno);
				}
				try {
					originalFileName = Normalizer.normalize(originalFileName, Normalizer.Form.NFC);//맥에서 한글파일명 자모분리현상 방지
					InputStream fileStream = file.getInputStream();
					FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
					Map<String, Object> fileInfo = new LinkedHashMap<>();
					fileInfo.put("filepath", filePath);// 파일위치폴더
					fileInfo.put("filenm", originalFileName);// 등록파일명
					fileInfo.put("savenm", savedFileName);// 저장파일명
					fileInfo.put("fileno", fileno);// 파일번호
					fileInfo.put("filesize", file.getBytes().length);// 파일용량
					fileservice.addFileInfo(fileInfo);
				} catch (Exception e) {
					// 파일삭제
					FileUtils.deleteQuietly(targetFile); // 저장된 현재 파일 삭제
					e.printStackTrace();
					break;
				}
			}
		}

		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		res.put("fileno", fileno);// 파일번호
		return res;
	}

	/**
	 * 파일목록
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("getFileList.do")
	public Map<String,?> getFileList(@RequestBody String requestData)  {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
		Map<String,Object> res = new LinkedHashMap<>();
		res.put("list",fileservice.selectFileList((String)param.get("FILE_GRP_ID")));
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

	/**
	 * 파일삭제
	 * @param requestData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("removeFileList.do")
	public Map<String, ?> removeFileList(@RequestBody String requestData) {
		Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
		Map<String, ?> gridData = (Map<String, ?>) jsonData.get("gridData");// 그리드 데이타
		fileservice.removeFileList(gridData);
		Map<String, Object> res = new LinkedHashMap<>();
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		return res;
	}
	
	/**
	 * uploadFileInfo -
	 * 
	 * @date 2021.10.18
	 * @param HttpServletRequest, HttpServletResponse
	 * @return
	 * @returns void
	 * @example
	 */
	@RequestMapping("uploadFileInfo.do")
	public Map<String, Object> uploadFileInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> res = new LinkedHashMap<>();
		try {
			String fileNm = "";
			String fileOrgNm = "";
			String fileOtxtNm = "";
			String imsiDir = "";
			// String subDir = "";
			long fileSz = 0;
			String uploadFileNm = "";
			String callBackFunc = "";

			MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
			Enumeration<String> e = multi.getParameterNames();
			String name = "";
			while (e.hasMoreElements()) {
				name = e.nextElement().toString();
				if ("imsiDir".equals(name)) {
					imsiDir = request.getParameter(name);
				}
				if ("callBackFunc".equals(name)) {
					callBackFunc = request.getParameter(name);
				}
				// //log.info(name + ":" + request.getParameter(name));
			}
			Iterator<String> iter = multi.getFileNames();
			imsiDir = tempDir + FILE_SEPERATOR + DateTime.getSysdate("yyyy-MM");
			// subDir = baseDir + ((baseDir).endsWith(FILE_SEPERATOR) ? "" : FILE_SEPERATOR)
			// + DateTime.getSysdate("yyyy-MM");
			while (iter.hasNext()) {
				fileNm = UUID.randomUUID().toString();
				uploadFileNm = iter.next();
				MultipartFile mfile = multi.getFile(uploadFileNm);
				fileSz = mfile.getSize();
				fileOrgNm = mfile.getOriginalFilename();
				fileOtxtNm = fileOrgNm.substring(fileOrgNm.lastIndexOf(".") + 1);

				if (!SysFileUtils.createDirAll(imsiDir)) {
					throw new Exception("폴더생성이 안되었습니다.");
				}
				// [SC] 변경 소스 적용시 오류 발생하여 기존 소스 반영
				File file = new File(imsiDir, FilenameUtils.getName(fileNm));
				// if (!file.exists())
				// SysFileUtils.createDirAll(imsiDir);

				FileUtils.writeByteArrayToFile(file, mfile.getBytes());
			}
			Map fileInfoMap = new HashMap();
			fileInfoMap.put("FILE_NM", fileNm);
			fileInfoMap.put("FILE_ORG_NM", fileOrgNm);
			fileInfoMap.put("FILE_SZ", fileSz);
			fileInfoMap.put("FILE_TYPE", fileOtxtNm);
			fileInfoMap.put("FILE_PATH", imsiDir);
			fileInfoMap.put("CNNT_IP", CommonUtils.getRemoteAddr(request));
			res.put("FILEJSON", fileInfoMap);
			// res.put("key", imsiDir + "^" + subDir);
			res.put("message", "");
			res.put(ReturnCode.RTN_CODE, ReturnCode.OK);
		} catch (Exception e) {
			throw e;
		}
		return res;
	}	
	
	/**
	 * 첨부파일 데이터 저장
	 * 
	 * @date 2024.10.18
	 * @param HttpServletRequest, HttpServletResponse
	 * @return
	 * @returns void
	 * @example
	 */
	@RequestMapping("saveFileUploadInfo.do")
	public Map<String, Object> saveFileUploadInfo(@RequestBody String requestData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> res = new LinkedHashMap<>();
		Map<String, Object> jsonData = Utility.readJsonData(requestData);
		String fileGrpId = fileservice.saveFileInfoList(jsonData);
		res.put("fileGrpId", fileGrpId);
		res.put("message", "");
		
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);		
		return res;
	}	
	
	/**
	 * S01_CODE_DETAIL 조회
	 * 
	 */
	@RequestMapping(value = "searchFileUploadInfo.do")
	public Map<String, Object> searchFileUploadInfo(@RequestBody String requestData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> res = new LinkedHashMap<>();
		Map<String, Object> jsonData = Utility.readJsonData(requestData);
		jsonData.put("SYS_CD", sysCd);
		List<?> retList = null;
		retList = fileservice.selectTbAplFileInfoByFileGrpIdAndSeqList(jsonData);

		res.put("Data", retList);
		res.put("message", "");
		res.put(ReturnCode.RTN_CODE, ReturnCode.OK);		
		return res;
	}
	
	/**
	 * <pre>
	 * 이미지 파일을 읽어서 response 스트림으로 전달한다.
	 * <b>NOTE : </b>이미지를 표시하기 위하여 필히 파라미터는 이미지 파일의 경로, 파일명을 파라미터로 전달하여야 한다.
	 * </pre>
	 * 
	 * @param request  HTTP 요청 객체
	 * @param response HTTP 응답 객체
	 * @throws Exception 예외 발생 시
	 */
	@RequestMapping(value = "GetImgVw.do")
	public void GetImgVw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Map<String,Object> res = new LinkedHashMap<>();
		// Map<String, Object> jsonData = Utility.readJsonData(requestData);
		try {

			String filePath = request.getParameter("filePath");
			filePath = filePath.replaceAll("@", FILE_SEPERATOR);

			if (isValidPath(filePath)) {

				File file = new File(filePath);
				if (!file.exists()) {
				}
				int len = (int) file.length();
				FileInputStream inputStream = new FileInputStream(file);
				try {
					printStream(response, inputStream, len, file.getName());
				} catch (Exception e) {
					e.printStackTrace();
					// System.out.println(e.toString());
				}
			}

		} catch (Exception e) {
			//// log.warn(" -------------------- Exception occurred : " +
			//// e.getLocalizedMessage());
		}

	}
	

	/**
	 * <pre>
	 * 이미지 파일을 읽어서 response 스트림으로 전달한다.
	 * <b>NOTE : </b>이미지를 표시하기 위하여 필히 파라미터는 이미지 파일의 경로, 파일명을 파라미터로 전달하여야 한다.
	 * </pre>
	 * 
	 * @param request  HTTP 요청 객체
	 * @param response HTTP 응답 객체
	 * @throws Exception 예외 발생 시
	 */
	@RequestMapping(value = "imageView.do")
	public void viewImageFile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			String filePath = request.getParameter("filePath");
			String fileNm = request.getParameter("fileNm");
			/*
			 * Map<String, Object> paramMap = new HashMap(); paramMap.put("SYS_CD", sysCd);
			 * paramMap.put("FILE_GRP_ID", fileGrpId); paramMap.put("FILE_SEQ", seq);
			 * Map<String, Object> retMap =
			 * fileservice.selectTbAplFileInfoByFileGrpIdAndSeq(paramMap);
			 *///// log.debug(" -------------------- File path : " + retMap.get("FILE_PATH") +
			//// " , name : " + retMap.get("FILE_NM"));

//			String fullPath = baseDir + pImgDir + File.separator + pImgName;
//			fullPath = filePathBlackList(fullPath);

			String fullPath = "";

			// 전체 파일 경로
			fullPath = filePath.replaceAll("@", FILE_SEPERATOR) + FILE_SEPERATOR + fileNm;

			//// log.debug(" -------------------- Path trans : " + fullPath + ", valid : " +
			//// retMap.get("FILE_NM"));

			if (isValidPath(fullPath)) {

				response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				FileInputStream fis = null;
				try {
					File imgFile = new File(fullPath);	
					fis = new FileInputStream(imgFile);
					
		            byte[] buffer = new byte[1024];
		            int bytesRead;
		            while ((bytesRead = fis.read(buffer)) != -1) {
		                response.getOutputStream().write(buffer, 0, bytesRead);
		            }
				} catch (Exception e) {
					//// log.warn(" -------------------- Error occured while rendering image
					//// file...");
				} finally {
					try {
						if (fis != null) {
							fis.close();
						}
					} catch (Exception e2) {
						//// log.warn(" -------------------- Error occured while rendering image
						//// file...");
					}
				}
			}

		} catch (Exception e) {
			//// log.warn(" -------------------- Exception occurred : " +
			//// e.getLocalizedMessage());
		}

	}	
		
	/**
	 * inputStream을 화면에 출력하기 위한 메소드<br>
	 * 
	 * @param res javax.servlet.http.HttpServletRequest
	 * @param rs  java.io.InputStream
	 * @exception java.io.IOException.
	 * @exception java.io.SQLException .
	 */
	public static void printStream(HttpServletResponse res, InputStream is, int len, String fileNm) throws Exception {
		ServletOutputStream os = res.getOutputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] buffer = new byte[1024];
		int k = 0;
		while ((k = bis.read(buffer)) != -1) {
			os.write(buffer, 0, k);
		}
		os.close();
		bis.close();
	}
	
	/**
	 * <pre>
	 * 파일 경로에 부적절한 문자열이 존재하는지 체크한다.
	 * </pre>
	 * 
	 * @param argPath - 체크할 경로
	 * @return 경로에 부적절한 문자가 없으면 true, 그렇지 않으면 false 반환
	 */
	public static boolean isValidPath(String argPath) {
		if (argPath.indexOf("../") != -1 || argPath.indexOf("..\\") != -1 || argPath.indexOf("*") != -1)
			return false;
		return true;
	}
	
	/**
	 * 파일다운로드
	 * @param requestData
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "download.do")
	public void download(@RequestParam("fileno") String fileno, @RequestParam("fileseq") int fileseq, HttpServletResponse response) throws IOException {
		Map<String, Object> param = new LinkedHashMap<>();
		param.put("fileno", fileno);
		param.put("fileseq", fileseq);
		Map<String, ?> fileInfo = fileservice.getFile(param);
		File file = new File((String)fileInfo.get("FILE_PATH") +FILE_SEPERATOR+ fileInfo.get("FILE_NM"));
		response.setContentType("application/octet-stream; charset=UTF-8");
		String useFileName = ((String) fileInfo.get("filenm")).replaceAll(" ", "_");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(useFileName, "UTF-8") + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
	}	

	/**
	 * 파일다운로드
	 * @param requestData
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "downloadN.do")
	public void download(@RequestParam("filePath") String filePath, @RequestParam("fileNm") String fileNm, HttpServletResponse response) throws IOException {
		//filePath = filePath.replaceAll("^", FILE_SEPERATOR);
		//Map<String, Object> param = new LinkedHashMap<>();
		//filePath = filePath+FILE_SEPERATOR+fileNm;
		File file = new File(filePath);
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileNm, "UTF-8") + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
	}	
}
