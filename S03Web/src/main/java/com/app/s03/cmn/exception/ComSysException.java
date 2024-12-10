/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.exception;

import com.app.s03.cmn.type.SystemMessage;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.exception
 * 클래스명: ComSysException
 * 설명: System Exception Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.04     ADMIN      1.0 최초작성
 * </pre>
 */
public class ComSysException extends RuntimeException {
    private static final long serialVersionUID = 7407006869935376817L;

    private SystemMessage systemMessage;
    private Exception wrappedException;
    private String additionalMessage;

    public ComSysException() {
        this(SystemMessage.SYS0000, null, null);
    }

    public ComSysException(SystemMessage systemMessage) {
        this(systemMessage, null, null);
    }

    public ComSysException(SystemMessage systemMessage, Exception wrappedException) {
        this(systemMessage, wrappedException, null);
    }

    public ComSysException(SystemMessage systemMessage, String additionalMessage) {
        this(systemMessage, null, additionalMessage);
    }

    public ComSysException(SystemMessage systemMessage, Exception wrappedException, String additionalMessage) {
        this.systemMessage = systemMessage;
        this.wrappedException = wrappedException;
        this.additionalMessage = additionalMessage;
    }

    @Override
    public String getMessage() {
        return systemMessage.getMessage();
    }

    /**
     * @return the systemMessage
     */
    public SystemMessage getSystemMessage() {
        return systemMessage;
    }

    /**
     * @return the wrappedException
     */
    public Exception getWrappedException() {
        return wrappedException;
    }

    /**
     * @return the additionalMessage
     */
    public String getAdditionalMessage() {
        return additionalMessage;
    }

    /**
     * @param systemMessage the systemMessage to set
     */
    public void setSystemMessage(SystemMessage systemMessage) {
        this.systemMessage = systemMessage;
    }

    /**
     * @param wrappedException the wrappedException to set
     */
    public void setWrappedException(Exception wrappedException) {
        this.wrappedException = wrappedException;
    }

    /**
     * @param additionalMessage the additionalMessage to set
     */
    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

}
