package com.tony.sales.exception;

public class PathException extends RuntimeException {

	public PathException(final String message) {
		super(message);
	}

	public PathException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
