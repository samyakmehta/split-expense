package com.project.splitexp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

  public ExceptionResponseHandler() {
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = { Exception.class })
  protected ExceptionResponse internalError(Exception ex) {

    log.error(ex.getMessage(), ex);
    return ExceptionResponse.builder().error(ex.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = { InvalidExpenseCreationRequest.class, InvalidEventCreationRequest.class })
  protected ExceptionResponse badRequest(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ExceptionResponse.builder().error(ex.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = { UserNotFoundException.class, GroupNotFoundException.class, EventNotFoundException.class,
      ExpenseNotFoundException.class })
  protected ExceptionResponse notFound(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ExceptionResponse.builder().error(ex.getMessage()).build();
  }

}