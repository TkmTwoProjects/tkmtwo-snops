package com.tkmtwo.snops;


/**
 *
 */
public class UncategorizedException
  extends RuntimeException {
  
  public UncategorizedException(String msg) { super(msg); }
  
  public UncategorizedException(String msg, Throwable t) { super(msg, t); }
  
  public UncategorizedException(Throwable t) { super(t); }
}
