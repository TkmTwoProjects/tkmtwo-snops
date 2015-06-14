package com.tkmtwo.snops;


/**
 *
 */
public class IncorrectResultSizeException
  extends RuntimeException {
  
  private int expectedSize = -1;
  private int actualSize = -1;
  

  public IncorrectResultSizeException(String msg) {
    super(msg);
  }
  
  public IncorrectResultSizeException(int expectedSize) {
    super("Incorrect result size: expected " + expectedSize);
    setExpectedSize(expectedSize);
    setActualSize(-1);
  }
  
  public IncorrectResultSizeException(int expectedSize, int actualSize) {
    super("Incorrect result size: expected " + expectedSize + ", actual " + actualSize);
    setExpectedSize(expectedSize);
    setActualSize(actualSize);
  }
  
  public IncorrectResultSizeException(String msg, int expectedSize) {
    super(msg);
    setExpectedSize(expectedSize);
    setActualSize(-1);
  }
  
  public IncorrectResultSizeException(String msg, int expectedSize, Throwable t) {
    super(msg, t);
    setExpectedSize(expectedSize);
    setActualSize(-1);
  }
  
  public IncorrectResultSizeException(String msg, int expectedSize, int actualSize) {
    super(msg);
    setExpectedSize(expectedSize);
    setActualSize(actualSize);
  }
  
  public IncorrectResultSizeException(String msg, int expectedSize, int actualSize, Throwable t) {
    super(msg, t);
    setExpectedSize(expectedSize);
    setActualSize(actualSize);
  }
  
  public int getExpectedSize() {
    return expectedSize;
  }
  
  private void setExpectedSize(int i) {
    expectedSize = i;
  }
  
  public int getActualSize() {
    return actualSize;
  }
  private void setActualSize(int i) {
    actualSize = i;
  }
  
}
