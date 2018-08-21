package com.mh.core.exception;

/**
 * Class Description：MHException
 * @author Joe
 * @date 2013-4-16
 * @version 1.0
 */
public class MHException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MHException() {
		super();
	}

	public MHException(String message, Throwable cause) {
		super(message, cause);
	}

	public MHException(String message) {
		super(message);
	}

	public MHException(Throwable cause) {
		super(cause);
	}

}
