package com.battle.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);
	
	@ExceptionHandler(BattleException.class)
	public ResponseEntity<ErrorInfo> battleExceptionHandler(BattleException exception){
		LOGGER.error(exception.getMessage(),exception);
		
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setMessage(exception.getMessage());
		errorInfo.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodeArgumentNotValidException(MethodArgumentNotValidException exception){
		Map<String,String> errorMap = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException exception){
		Map<String,String> errorMap = new HashMap<>();
		exception.getConstraintViolations().forEach(error -> {
			errorMap.put((String) error.getInvalidValue(), error.getMessage());
		});
		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	
}
