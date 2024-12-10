package com.app.s03.cmn.exception;

public class CommonBusinessException extends RuntimeException {

	private static final long serialVersionUID = -6095584297657251954L;

	public CommonBusinessException() {
		super(); 
	}

	public CommonBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommonBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonBusinessException(String message) {
		super(message);
	}

	public CommonBusinessException(Throwable cause) {
		super(cause);
	}

}
