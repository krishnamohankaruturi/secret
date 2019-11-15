package edu.ku.cete.ExcelStreamReader;

public class ReadException extends RuntimeException {

  public ReadException() {
    super();
  }

  public ReadException(String msg) {
    super(msg);
  }

  public ReadException(Exception e) {
    super(e);
  }

  public ReadException(String msg, Exception e) {
    super(msg, e);
  }
}
