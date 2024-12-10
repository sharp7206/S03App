/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename QRCodeUtil.java
 */
package com.app.s03.cmn.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.utils
 * 클래스명: QRCodeUtil
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.19     hslee      1.0 최초작성
 * </pre>
 */
@Slf4j
public class QRCodeUtil {
    /**
     * 주어진 링크를 인코딩하여 QR 코드 이미지를 생성하고,
     * 그 이미지를 byte 배열 형태로 반환하는 메서드
     * @param link
     * @return QR 코드 이미지를 base64 로 리턴
     * @throws WriterException
     * @throws IOException
     * example: <!-- Replace "your-base64-string-here" with your actual Base64-encoded image string -->
    <img src="data:image/png;base64,base64Str" alt="Base64 Image">
     */
    public static String generateQRCodeImage(String link) throws Exception {
    	String base64Image = "";
        // QR코드 생성 옵션 설정
    	try {
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.MARGIN, 0);
            hintMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
     
            // QR 코드 생성
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, 200, 200, hintMap);
     
            // QR 코드 이미지 생성
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
     
            // QR 코드 이미지를 바이트 배열로 변환, byteArrayOutputStream에 저장
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage,"png", byteArrayOutputStream);
            
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
    	}catch(WriterException | IOException e) {
    		log.error("QRCodeUtil >> generateQRCodeImage :" + e.toString());
    		throw e;
    	}
 
        return base64Image;
    }
}
