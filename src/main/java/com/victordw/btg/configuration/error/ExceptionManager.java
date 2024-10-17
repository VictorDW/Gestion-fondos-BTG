package com.victordw.btg.configuration.error;

import com.victordw.btg.configuration.error.dto.ExceptionArgumentResponse;
import com.victordw.btg.configuration.error.dto.ExceptionResponse;
import com.victordw.btg.domain.exception.FundAlreadyExistsException;
import com.victordw.btg.domain.exception.InvestmentAmountException;
import com.victordw.btg.domain.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ControllerAdvice
public class ExceptionManager {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	public static ResponseEntity<ExceptionResponse> generalExceptionHandler(String exceptionMessage, HttpStatus httpStatus) {

		ExceptionResponse response = new ExceptionResponse(
				FORMATTER.format(LocalDateTime.now()),
				httpStatus.value(),
				httpStatus.getReasonPhrase(),
				exceptionMessage
		);

		return new ResponseEntity<>(response, httpStatus);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ExceptionArgumentResponse>> handlerArgumentInvalidException(MethodArgumentNotValidException exception) {

		var errors = exception.getFieldErrors()
				.stream()
				.map(ExceptionArgumentResponse::new)
				.toList();

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<ExceptionArgumentResponse>> handlerCommandInvalid(ConstraintViolationException exception) {

		var errors = exception.getConstraintViolations()
				.stream()
				.map(ExceptionArgumentResponse::new)
				.toList();

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(FundAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handlerAlreadyExistsException(FundAlreadyExistsException exception) {
		return generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvestmentAmountException.class)
	public ResponseEntity<ExceptionResponse> handlerInvestmentAmountException(InvestmentAmountException exception) {
		return generalExceptionHandler(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> handlerNotFoundException(NotFoundException exception) {
		return generalExceptionHandler(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	
}
