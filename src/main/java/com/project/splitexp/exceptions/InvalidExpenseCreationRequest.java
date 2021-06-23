package com.project.splitexp.exceptions;

public class InvalidExpenseCreationRequest extends Exception {

  private static final long serialVersionUID = 1L;
  private String error;

  public InvalidExpenseCreationRequest(String error) {
    super(error);
    this.error = error;
  }
}
