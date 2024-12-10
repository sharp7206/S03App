/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.exception;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.exception
 * 클래스명: BaseException
 * 설명: Based Exception Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.10.14     ADMIN      1.0 최초작성
 * </pre>
 */
public class BaseException extends Exception {
    private static final long serialVersionUID = -303666453051068897L;

    private static final String EMPTY_MSG = "BaseException without message";

    protected String message;
    protected Exception wrappedException;

    public BaseException() {
        this(EMPTY_MSG, null);
    }

    public BaseException(String message) {
        this(message, null);
    }

    public BaseException(Throwable wrappedException) {
        this(EMPTY_MSG, wrappedException);
    }

    public BaseException(String message, Throwable wrappedException) {
        super(wrappedException);
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the wrappedException
     */
    public Exception getWrappedException() {
        return wrappedException;
    }

    /**
     * @param wrappedException the wrappedException to set
     */
    public void setWrappedException(Exception wrappedException) {
        this.wrappedException = wrappedException;
    }

}
