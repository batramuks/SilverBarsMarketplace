package sliverbars.api.exception;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<CustomErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError("INTERNAL SERVER ERROR");
		errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public final ResponseEntity<CustomErrorResponse> handleUserNotFoundException(OrderNotFoundException ex,
			WebRequest request) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	// ...
}