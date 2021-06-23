package com.project.splitexp.exceptions;

public class ExpenseNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;
  private String error;

  public ExpenseNotFoundException(String error) {
    super(error);
    this.error = error;
  }
}
