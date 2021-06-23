package com.project.splitexp.exceptions;

public class EventNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;
  private String error;

  public EventNotFoundException(String error) {
    super(error);
    this.error = error;
  }
}
