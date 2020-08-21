package org.kpu.ihpweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ControllerAdvice
public class MemberControllerAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(Exception e) {
		e.printStackTrace();
		return "exception/error";
	}

	@ExceptionHandler(DataNotFoundException.class)
	public String handleException(DataNotFoundException e) {
		e.printStackTrace();
		return "exception/not_found";
	}

}
