package edu.ku.cete.ExcelStreamReader;

public class OpenException extends RuntimeException {

  public OpenException() {
    super();
  }

  public OpenException(String msg) {
    super(msg);
  }

  public OpenException(Exception e) {
    super(e);
  }

  public OpenException(String msg, Exception e) {
    super(msg, e);
  }
}
