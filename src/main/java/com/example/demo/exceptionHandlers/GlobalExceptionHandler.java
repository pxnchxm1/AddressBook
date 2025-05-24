package com.example.demo.exceptionHandlers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dtos.ValidationErrorResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(AddressBookException.class)
	public ResponseEntity<Map<String, String>> handleAddressBookException(AddressBookException ex) {
	    Map<String, String> error = new HashMap<>();
	    error.put("error", ex.getMessage());
	    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	} 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error ->
	        errors.put(error.getField(), error.getDefaultMessage())
	    );
	    ValidationErrorResponse response = new ValidationErrorResponse(
	        HttpStatus.BAD_REQUEST.value(),
	        "Validation failed",
	        errors
	    );
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
