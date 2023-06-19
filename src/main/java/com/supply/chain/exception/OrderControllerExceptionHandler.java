package com.supply.chain.exception;

import com.supply.chain.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class OrderControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request){
        ErrorDetailsDto errorDetails = handleBaseException(exception, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }

    private ErrorDetailsDto handleBaseException(Exception exception, HttpStatus status) {
        BaseException baseException = (BaseException) exception;

        return new ErrorDetailsDto(baseException.getMessage(),baseException.getErrorCode(), status.value());
    }
}
