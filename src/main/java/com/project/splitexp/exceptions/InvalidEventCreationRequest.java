package com.project.splitexp.exceptions;

public class InvalidEventCreationRequest extends Exception {

  private static final long serialVersionUID = 1L;
  private String error;

  public InvalidEventCreationRequest(String error) {
    super(error);
    this.error = error;
  }
}
