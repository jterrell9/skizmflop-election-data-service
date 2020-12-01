package net.skizmflop.microservice.data.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import net.skizmflop.commons.exception.SFBadRequestException;
import net.skizmflop.commons.exception.SFDataException;
import net.skizmflop.commons.exception.SFNoDataFoundException;

@ControllerAdvice
public class SFDataServiceExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(SFDataServiceExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
		if(logger.isErrorEnabled()) logger.error("Unhandled Exception", ex);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("timestamp", LocalDateTime.now());
        responseMap.put("message", "Uncaught Exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
    }
	
	@ExceptionHandler(SFBadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(SFBadRequestException ex, WebRequest request) {
		if(logger.isErrorEnabled()) logger.error("Bad Request", ex);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("timestamp", LocalDateTime.now());
        responseMap.put("message", "Bad request sent to server");
        return ResponseEntity.badRequest().body(responseMap);
    }
	
	@ExceptionHandler(SFNoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataFound(SFNoDataFoundException ex, WebRequest request) {
		if(logger.isErrorEnabled()) logger.error("No Data Found", ex);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("timestamp", LocalDateTime.now());
        responseMap.put("message", "No Data found");
        return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(responseMap);
    }
	
	@ExceptionHandler(SFDataException.class)
    public ResponseEntity<Object> handleDataException(SFDataException ex, WebRequest request) {
		if(logger.isErrorEnabled()) logger.error("Data Exception", ex);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("timestamp", LocalDateTime.now());
        responseMap.put("message", "Data issue");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMap);
    }

}
