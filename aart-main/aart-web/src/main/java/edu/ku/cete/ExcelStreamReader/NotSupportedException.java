package edu.ku.cete.ExcelStreamReader;

public class NotSupportedException extends RuntimeException {

  public NotSupportedException() {
    super();
  }

  public NotSupportedException(String msg) {
    super(msg);
  }

  public NotSupportedException(Exception e) {
    super(e);
  }

  public NotSupportedException(String msg, Exception e) {
    super(msg, e);
  }
}
