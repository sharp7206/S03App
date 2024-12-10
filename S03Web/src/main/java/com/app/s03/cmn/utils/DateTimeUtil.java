/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.app.s03.cmn.constant.ComConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.util
 * 클래스명: DateTimeUtil
 * 설명: 날짜 및 시간 관련 Utility Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.03     ADMIN      1.0 최초작성
 * </pre>
 */
@Slf4j
public class DateTimeUtil {

    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String DATE_24H_MIN_PATTERN = "yyyyMMddHHmm";
    public static final String DATE_24H_SEC_PATTERN = "yyyyMMddHHmmss";
    public static final String TIMESTAMP_24H_PATTERN = "yyyyMMddHHmmssSSS";
    public static final int TIMESTAMP_LENGTH = 17;

    /**
     * 기본 Data Format
     * 
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getDefaultSimpleDateFormat() {
        return new SimpleDateFormat(TIMESTAMP_24H_PATTERN, Locale.KOREA);
    }

    /**
     * <pre>
     * 24시간 유형으로 현재 Timestamp 반환
     * [egovframework.com.utl.fcc.service.EgovStringUtil.getTimeStamp() 대체]
     * </pre>
     * 
     * @return String 현재 Timestamp (yyyyMMddHHmmssSSS)
     */
    public static String getCurrentTimestamp() {
        SimpleDateFormat sdf = getDefaultSimpleDateFormat();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return sdf.format(ts.getTime());
    }

    /**
     * 날짜 문자열 <code>fromDt</code>과 <code>toDt</code> 차이값 반환
     * 
     * @param fromDt 날짜 문자열
     * @param toDt   날짜 문자열
     * @param dtType TimeUnit
     * @return long 차이값
     */
    public static long getDateTimeGap(String fromDt, String toDt, TimeUnit dtType) {
        long gap = 0;
        SimpleDateFormat sdf = getDefaultSimpleDateFormat();
        try {
            Timestamp fromTs = new Timestamp(sdf.parse(StringUtils.rightPad(fromDt, TIMESTAMP_LENGTH, "0")).getTime());
            Timestamp toTs = new Timestamp(sdf.parse(StringUtils.rightPad(toDt, TIMESTAMP_LENGTH, "0")).getTime());
            gap = toTs.getTime() - fromTs.getTime();
        } catch (ParseException e) {
            log.error("DateTimeUtil getDateTimeGap - parse exception", e);
        }

        return dtType.convert(gap, TimeUnit.MILLISECONDS);
    }

    /**
     * 날짜 문자열 <code>toDt</code>와 현재일시 차이값 반환
     * 
     * @param toDt   날짜 문자열
     * @param dtType TimeUnit
     * @return long 차이값
     */
    public static long getDateTimeGap(String toDt, TimeUnit dtType) {
        return getDateTimeGap(getCurrentTimestamp(), toDt, dtType);
    }

    /**
     * 날짜 문자열 <code>dt</code>를 기준으로 <code>addNum</code>만큼 증가한 날짜 반환
     * 
     * @param dt        날짜
     * @param addNum    가감할 수
     * @param dtType    TimeUnit
     * @param dtPattern 반환받을 날짜시간 패턴
     * @return String 계산된 날짜 반환
     */
    public static String addDateTime(String dt, long addNum, TimeUnit dtType, String dtPattern) {
        Timestamp result = null;
        try {
            long time = getDefaultSimpleDateFormat().parse(StringUtils.rightPad(dt, TIMESTAMP_LENGTH, "0")).getTime();
            result = new Timestamp(time + TimeUnit.MILLISECONDS.convert(addNum, dtType));
        } catch (ParseException e) {
            log.error("DateTimeUtil addDateTime - parse exception", e);
        }

        if (result != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(dtPattern, Locale.KOREA);
            return sdf.format(result.getTime());
        }
        return null;
    }

    /**
     * 날짜 문자열 <code>dt</code>를 기준으로 <code>addNum</code>만큼 증가한 날짜 반환
     * 
     * @param dt     날짜
     * @param addNum 가감할 수
     * @param dtType TimeUnit
     * @return String 계산된 날짜 반환 (yyyyMMddHHmmss)
     */
    public static String addDateTime(String dt, long addNum, TimeUnit dtType) {
        return addDateTime(dt, addNum, dtType, DATE_24H_SEC_PATTERN);
    }

    /**
     * 날짜 및 시간 구분자 제거 후 반환 (숫자만 반환)
     * 
     * @param dateTime 날짜, 시간 문자열
     * @return String 구분자 제거한 문자열
     */
    public static String removeDateTimeSeparator(String dateTime) {
        return StringUtils.defaultString(dateTime).replaceAll("\\D", ComConstants.EMPTY_STRING);
    }

}
