package com.app.s03.cmn.request;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.app.s03.cmn.annotations.SuppressFBWarnings;
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.util
 * 클래스명: HttpRequestHelper
 * 설명: HTTP Request Helper Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.10.13     ADMIN      1.0 최초작성
 * </pre>
 */
public class HttpRequestHelper {

    // WAS 종류에 따라 Header 정보가 다르므로, 정확한 Client IP 가져오기 위해 분기 처리
    private static final List<String> IP_HEADERS = Arrays.asList("X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP", "X-RealIP", "REMOTE_ADDR");

    /**
     * 현재 HttpServletRequest
     * 
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getRequest();
    }

    /**
     * 현재 HttpServletRequest
     * 
     * @return HttpServletRequest
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }

    /**
     * 클라이언트 IP 주소 반환
     * 
     * @return String
     */
    public static String getRemoteIp() {
        return getRemoteIp(getRequest());
    }

    /**
     * 클라이언트 IP 주소 반환
     * 
     * @param request
     * @return String
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = null;
        for (String header : IP_HEADERS) {
            ip = getHeader(request, header);
            if (!isEmptyValue(ip))
                return ip;
        }

        if (isEmptyValue(ip))
            return request.getRemoteAddr();

        return ip;
    }

    /**
     * Header 항목 값 반환
     * 
     * @param request
     * @param name
     * @return String
     */
    @SuppressFBWarnings(value = "SERVLET_HEADER", justification = "중요 항목 아님")
    private static String getHeader(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }

    /**
     * 빈 값인지 여부 반환
     * 
     * @param val
     * @return boolean
     */
    private static boolean isEmptyValue(String val) {
        return StringUtils.isBlank(val) || StringUtils.equalsIgnoreCase("unknown", val);
    }

    /**
     * User-Agent 정보 반환
     * 
     * @return String
     */
    public static String getUserAgent() {
        return getHeader(getRequest(), "User-Agent");
    }

    /**
     * Client OS 정보 반환
     * 
     * @return String
     */
    public static String getClientOS() {
        String agent = getUserAgent();
        String lowerCaseAgent = agent.toLowerCase();

        if (lowerCaseAgent.contains("windows"))
            return "Windows";
        else if (lowerCaseAgent.contains("mac"))
            return "Mac";
        else if (lowerCaseAgent.contains("x11"))
            return "Unix";
        else if (lowerCaseAgent.contains("android"))
            return "Android";
        else if (lowerCaseAgent.contains("iphone"))
            return "IPhone";

        return "Unknown [" + agent + "]";
    }

}
