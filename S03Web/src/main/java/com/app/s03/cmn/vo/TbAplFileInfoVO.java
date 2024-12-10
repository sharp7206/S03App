/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename TbAplFileInfoVO.java
 */
package com.app.s03.cmn.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.vo
 * 클래스명: TbAplFileInfoVO
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.02.01     hslee      1.0 최초작성
 * </pre>
 */
@Data
public class TbAplFileInfoVO implements Serializable {

	/** 변수설명 */
	private static final long serialVersionUID = 5793168296999747395L;
    private String sysCd;
    //SYSTEM CODE
	private String fileGrpId;
	//FILE GROUP ID
	private String fileSeq;
	  //FILE SEQUENCE
	private String fileOrgNm;
	//FILE ORG NAME
	private String fileNm;
	   //FILE NAME
	private String fileSz;
	   //FILE SIZE
	private String filePath;
	 //FILE 경로
	private String refDocId;
	 //FILE 첨조문서ID
	private String refCatId;
	 //FILE CATAGORY ID
	private String cnntIp;
	   //CLIENT IP ADDR
	private String mAddr;
	    //MAC ADDR
	private String fstRgprId;
	//최조 등록자ID
	private String fstRegOnce;
	//최초 등록일시
	private String ltChprId;
	 //최종 수정자ID
	private String ltChprOnce;
	//최종 수정일시
}
