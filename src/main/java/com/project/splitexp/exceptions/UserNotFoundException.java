package com.project.splitexp.exceptions;

public class UserNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;
  private String error;

  public UserNotFoundException(String error) {
    super(error);
    this.error = error;
  }
}
