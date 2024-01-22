package edu.pnu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
//RuntimeException을 상속
public class DataNotfoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DataNotfoundException(String message) {
		super(message);
	}

}
