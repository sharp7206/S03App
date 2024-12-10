package com.app.s03.cmn.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.service.FileService;
import com.app.s03.cmn.utils.CommonUtils;
import com.app.s03.cmn.utils.DateTime;
import com.app.s03.cmn.utils.SysFileUtils;
import com.app.s03.cmn.utils.Utility;

/**
 * 메뉴 관리 컨트롤러.
 */
//@Slf4j
@RequestMapping("/api/file/")
@RestController
public class CommonFileController {
	@Value("${file.uploadPath}")
	private String uploadDir;
	
	@Value("${file.uploadTempPath}")
	private String tempDir;

	@Value("${sysCd}")
	private String sysCd;

	
	private FileService fileService;
	private final static String FILE_SEPERATOR = "/";

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
			fileInfoMap.put("FILE_SIZE", fileSz);
			fileInfoMap.put("FILE_TYPE", fileOtxtNm);
			fileInfoMap.put("FILE_PATH", imsiDir);

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
	 * uploadFileInfo -
	 * 
	 * @date 2021.10.18
	 * @param HttpServletRequest, HttpServletResponse
	 * @returns void
	 * @example
	 */
	@PostMapping(value = "/uploadFileInfo2")
	public void uploadFileInfo2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String fileNm = "";
			String fileOtxtNm = "";
			String imsiDir = "";
			String subDir = "";
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
			imsiDir = tempDir;
			while (iter.hasNext()) {
				fileNm = DateTime.getSysdate("yyyyMMdd") + "_" + UUID.randomUUID().toString();
				uploadFileNm = iter.next();
				MultipartFile mfile = multi.getFile(uploadFileNm);
				fileSz = mfile.getSize();
				fileOtxtNm = mfile.getOriginalFilename();

				if (!SysFileUtils.createDirAll(imsiDir)) {
					throw new Exception("폴더생성이 안되었습니다.");
				}
				// [SC] 변경 소스 적용시 오류 발생하여 기존 소스 반영
				File file = new File(imsiDir, FilenameUtils.getName(fileNm));
				if (!file.exists())
					SysFileUtils.createDirAll(imsiDir);

				FileUtils.writeByteArrayToFile(file, mfile.getBytes());
			}
			StringBuffer stb1 = new StringBuffer();
			stb1.append("<script>window.onload = doInit;function doInit() {");
			// stb1.append("parent." + callBackFunc + "(\"<ret>\n");
			stb1.append("callBackFileUploadDone(\"<ret>\n");
			stb1.append("<key>" + imsiDir + "^" + subDir + "</key>\n");
			stb1.append("<storedFileList>" + fileNm + "</storedFileList>\n");
			stb1.append("<localfileList>" + URLEncode(fileOtxtNm) + "</localfileList>\n");
			stb1.append("<fileSizeList>" + fileSz + "</fileSizeList>\n");
			stb1.append("<maxUploadSize></maxUploadSize>");
			stb1.append("<subSize></subSize>");
			stb1.append("<deniedList></deniedList>");
			stb1.append("<deniedCodeList></deniedCodeList>");
			stb1.append("</ret>\");}</script>");
			// //log.info("chk==" + stb1.toString());
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
			wrapper.setContentType("text/html;charset=UTF-8");
			wrapper.setHeader("Content-length", "" + (stb1.toString()).getBytes("utf-8").length);
			// [PMD] Inswave File Upload Guide 방안에 따른 전송 방식(Filter 방식으로 하게 되면 추가적인 변수전달이 안되어
			// 해당방식 사용)
			response.getWriter().print(stb1.toString());
		} catch (IOException e) {
			//// log.error("IOException", e);
			SysFileUtils.getAlertMessage(response, "EZZ0067");
		}
	}

	/**
	 * 파일다운로드
	 * 
	 * @param requestData
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "download.do", method = RequestMethod.POST)
	public void download(@RequestParam("fileGrpId") String fileGrpId, @RequestParam("fileSeq") int fileSeq,
			HttpServletResponse response) throws Exception {
		Map<String, Object> param = new LinkedHashMap<>();
		param.put("fileGrpId", fileGrpId);
		param.put("fileSeq", fileSeq);
		String fileUploadPath = "";
		List<?> fileList = fileService.selectTbAplFileInfoByFileGrpIdAndSeqList(param);
		Map<String, ?> fileInfo = null;
		File file = null;
		for (int i = 0; i < fileList.size(); i++) {
			fileInfo = (Map) fileList.get(i);
			file = new File((String) fileInfo.get("filepath") + (String) fileInfo.get("savename"));
		}
		response.setContentType("application/octet-stream; charset=UTF-8");
		String useFileName = ((String) fileInfo.get("filename")).replaceAll(" ", "_");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + URLEncoder.encode(useFileName, "UTF-8") + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
	}

	/**
	 * MAJOR KEY 생성
	 */
	@RequestMapping(value = "downloadFile.do")
	public void downloadFile(@RequestBody String requestData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> res = new LinkedHashMap<>();
		Map<String, Object> jsonData = Utility.readJsonData(requestData);
		List<?> fileList = fileService.selectTbAplFileInfoByFileGrpIdAndSnList(jsonData);
		try {
			Map<String, ?> fileInfo = null;
			// File file = null;
			String fileOrgNm = "";
			String filePath = "";
			for (int i = 0; i < fileList.size(); i++) {
				fileInfo = (Map) fileList.get(i);
				fileOrgNm = (String) fileInfo.get("FILE_ORG_NM");
				filePath = (String) fileInfo.get("FILE_PATH") + FILE_SEPERATOR + (String) fileInfo.get("FILE_NM");
				// file = new File(filePath);
				SysFileUtils.downloadFile(response, request, fileOrgNm, filePath);
			}
		} catch (Exception e) {
			//// log.error("Exception", e);
			SysFileUtils.getAlertMessage(response, "EZZ0067");
		}
	}

	public String URLEncode(String data) {
		try {
			// return URLEncoder.encode(data, "UTF-8");
			// return URLEncoder.encode(data, "utf-8");
			return Base64.getEncoder().encodeToString(data.getBytes());
			// return data;
		} catch (Exception e) {
			return data;
		}
		// return data;
	}

	/**
	 * S01FileInfo -
	 * 
	 * @date 2021.10.18
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
		String fileGid = fileService.saveFileInfoList(jsonData);
		res.put("FILE_GID", fileGid);
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
		List<?> retList = null;
		retList = fileService.selectTbAplFileInfoByFileGrpIdAndSeqList(jsonData);

		res.put("Data", retList);
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
	@RequestMapping(value = "imageView.do")
	public void viewImageFile(@RequestBody String requestData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Map<String,Object> res = new LinkedHashMap<>();
		// Map<String, Object> jsonData = Utility.readJsonData(requestData);
		try {

			String fileGrpId = request.getParameter("fileGrpId");
			String fileSeq = request.getParameter("fileSeq");
			Map<String, Object> param = new HashMap();
			param.put("sysCd", sysCd);
			param.put("fileGrpId", fileGrpId);
			param.put("fileSeq", fileSeq);
			Map<String, Object> retMap = fileService.selectTbAplFileInfoByFileGrpIdAndSeq(param);
			//// log.debug(" -------------------- File path : " + retMap.get("FILE_PATH") +
			//// " , name : " + retMap.get("FILE_NM"));

//			String fullPath = baseDir + pImgDir + File.separator + pImgName;
//			fullPath = filePathBlackList(fullPath);

			String fullPath = "";

			// 전체 파일 경로
			fullPath = (String) retMap.get("FILE_PATH") + FILE_SEPERATOR + retMap.get("FILE_NM");

			//// log.debug(" -------------------- Path trans : " + fullPath + ", valid : " +
			//// retMap.get("FILE_NM"));

			if (isValidPath(fullPath)) {

				FileInputStream fis = null;

				try {

					File imgFile = new File(fullPath);
					fis = new FileInputStream(imgFile);
					FileCopyUtils.copy(fis, response.getOutputStream());
					response.getOutputStream().flush();

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
	 * <pre>
	 * 이미지 파일을 읽어서 response 스트림으로 전달한다.
	 * <b>NOTE : </b>이미지를 표시하기 위하여 필히 파라미터는 이미지 파일의 경로, 파일명을 파라미터로 전달하여야 한다.
	 * </pre>
	 * 
	 * @param request  HTTP 요청 객체
	 * @param response HTTP 응답 객체
	 * @throws Exception 예외 발생 시
	 */
	@RequestMapping(value = "imageViewBase64.do")
	public Map<String, ?> viewImageBase64(@RequestBody String requestData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Map<String,Object> res = new LinkedHashMap<>();
		Map<String, Object> res = new LinkedHashMap<>();
		Map<String, Object> jsonData = Utility.readJsonData(requestData);
		Map<String, ?> param = (Map<String, ?>) jsonData.get("param");

		try {

			Map<String, ?> retMap = fileService.selectTbAplFileInfoByFileGrpIdAndSeq(param);
			//// log.debug(" -------------------- File path : " + retMap.get("FILE_PATH") +
			//// " , name : " + retMap.get("FILE_NM"));

//			String fullPath = baseDir + pImgDir + File.separator + pImgName;
//			fullPath = filePathBlackList(fullPath);

			String fullPath = "";

			fullPath = (String) retMap.get("FILE_PATH") + FILE_SEPERATOR + retMap.get("FILE_NM");

			//// log.debug(" -------------------- Path trans : " + fullPath + ", valid : " +
			//// retMap.get("FILE_NM"));

			if (isValidPath(fullPath)) {

				FileInputStream fis = null;

				try {

					File imgFile = new File(fullPath);
					byte[] fileByte = FileCopyUtils.copyToByteArray(imgFile);
					String base64String = Base64.getEncoder().encodeToString(fileByte);
					res.put("IMG64", base64String);
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
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * <pre>
	 * 이미지 파일을 읽어서 response 스트림으로 전달한다.
	 * <b>NOTE : </b>이미지를 표시하기 위하여 필히 파라미터는 이미지 파일의 경로, 파일명을 파라미터로 전달하여야 한다.
	 * 
	 * </pre>
	 * 
	 * @param request  HTTP 요청 객체 JSON DATA정의 : param:
	 *                 '20221206-0008-00001|20221206-0008-00002||20221206-0008-00003'
	 * @param response HTTP 응답 객체
	 * @throws Exception 예외 발생 시
	@RequestMapping(value = "imageViewBase64List.do")
	public Map<String, ?> viewImageBase64List(@RequestBody String requestData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Map<String,Object> res = new LinkedHashMap<>();
		Map<String, Object> res = new LinkedHashMap<>();
		Map<String, Object> jsonData = Utility.readJsonData(requestData);
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");

		try {

			List fileInfoList = ConChar.stringToArrayList((String) param.get("FILE_STR"), "|");
			param.put("snList", fileInfoList);
			List retList = commonFileService.selectFileinfoByGrpId(param);

//			String fullPath = baseDir + pImgDir + File.separator + pImgName;
//			fullPath = filePathBlackList(fullPath);

			String fullPath = "";
			Map<String, Object> retMap = new HashMap();
			for (int i = 0; i < retList.size(); i++) {
				retMap = (Map<String, Object>) retList.get(i);
				// 전체 파일 경로
				if ("S01_CATALOGIMG".equals(retMap.get("REF_TABLE_ID"))) {
					fullPath = (String) retMap.get("FILE_PATH") + FILE_SEPERATOR + retMap.get("FILE_ORG_NM");
				} else if ("S01_STAMP".equals(retMap.get("REF_TABLE_ID"))) {
					fullPath = (String) retMap.get("FILE_PATH") + FILE_SEPERATOR + retMap.get("FILE_NM");
				} else {
					fullPath = (String) retMap.get("FILE_PATH") + FILE_SEPERATOR + retMap.get("FILE_NM");
				}

				//// log.debug(" -------------------- Path trans : " + fullPath + ", valid : " +
				//// retMap.get("FILE_NM"));

				if (isValidPath(fullPath)) {

					FileInputStream fis = null;

					try {

						File imgFile = new File(fullPath);
						byte[] fileByte = FileCopyUtils.copyToByteArray(imgFile);
						String base64String = Base64.getEncoder().encodeToString(fileByte);
						res.put((String) retMap.get("FILE_GID") + "-" + (String) retMap.get("SEQ"), base64String);
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
				//// log.debug(" -------------------- File path : " + retMap.get("FILE_PATH") +
				//// " , name : " + retMap.get("FILE_NM"));
			}

		} catch (Exception e) {
			//// log.warn(" -------------------- Exception occurred : " +
			//// e.getLocalizedMessage());
		}
		return res;
	}
	 */

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
	@RequestMapping(value = "GetResImg.do")
	public Map<String, ?> GetResImg(@RequestBody String requestData) throws IOException {
		String base64Image = "";
		Map<String, Object> res = new LinkedHashMap<>();
		try {
			Map<String, Object> jsonData = CommonUtils.readJsonData(requestData);
			Map<String, ?> param = (Map<String, ?>) jsonData.get("param");
	        ApplicationContext context = new ClassPathXmlApplicationContext();
	        // 클래스 패스 상의 sqlmap 폴더에서 XML 파일을 찾아옵니다.
	        Resource[] resources = context.getResources("classpath*:/images/*");
	        
            for (Resource resource : resources) {
                // 각 파일에 대해 처리 (예시로 sample.png를 처리)
                if (resource.getFilename().equals(param.get("imgNm"))) {
                    base64Image = convertResourceToBase64(resource);
                    System.out.println("Base64 Encoded Image: " + base64Image);
                }
            }	        

		} catch (Exception e) {
			//// log.warn(" -------------------- Exception occurred : " +
			//// e.getLocalizedMessage());
		}
		res.put("imgSrc", base64Image);
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}
	
    public static String convertResourceToBase64(Resource resource) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        return Base64Utils.encodeToString(imageBytes);
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
}
