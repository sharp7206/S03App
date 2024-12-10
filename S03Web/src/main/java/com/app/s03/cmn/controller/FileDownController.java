package com.app.s03.cmn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.s03.cmn.service.FileService;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 공통 파일 다운로드 컨트롤러
-===============================================================================================================
*/
@Controller
@RequestMapping("/api/cmn/filedown/")
public class FileDownController {

	@Autowired
	private FileService service;

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
		Map<String, ?> fileInfo = service.getFile(param);
		File file = new File(service.getFileRootPath() + fileInfo.get("filepath") + fileInfo.get("savenm"));
		response.setContentType("application/octet-stream; charset=UTF-8");
		String useFileName = ((String) fileInfo.get("filenm")).replaceAll(" ", "_");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(useFileName, "UTF-8") + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
	}
	
	/**
	 * 파일다운로드2
	 * @param requestData
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "downloadN.do")
	public void downloadN(@RequestParam("fileno") String fileno, @RequestParam("fileseq") int fileseq, HttpServletResponse response) throws IOException {
		Map<String, Object> param = new LinkedHashMap<>();
		param.put("fileno", fileno);
		param.put("fileseq", fileseq);
		Map<String, ?> fileInfo = service.getFile(param);
		File file = new File((String)fileInfo.get("FILE_PATH") + (String)fileInfo.get("FILE_NM"));
		response.setContentType("application/octet-stream; charset=UTF-8");
		String useFileName = ((String) fileInfo.get("filenm")).replaceAll(" ", "_");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(useFileName, "UTF-8") + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
	}	
}
