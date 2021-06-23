package com.project.splitexp.exceptions;

public class GroupNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;
  private String error;

  public GroupNotFoundException(String error) {
    super(error);
    this.error = error;
  }
}
