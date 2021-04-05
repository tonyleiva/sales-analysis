package com.tony.sales.exception;

public class FileException extends RuntimeException {

	public FileException(final String message) {
		super(message);
	}

	public FileException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
