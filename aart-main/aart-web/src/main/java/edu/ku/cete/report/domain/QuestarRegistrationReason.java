package edu.ku.cete.report.domain;

import java.io.Serializable;

public class QuestarRegistrationReason extends BatchRegistrationReason implements Serializable {
	
	private static final long serialVersionUID = -1203750468773629451L;

	private Long questarStagingId;
	private Long questarStagingFileId;

	public Long getQuestarStagingId() {
		return questarStagingId;
	}
	
	public void setQuestarStagingId(Long questarStagingId) {
		this.questarStagingId = questarStagingId;
	}
	
	public Long getQuestarStagingFileId() {
		return questarStagingFileId;
	}
	
	public void setQuestarStagingFileId(Long questarStagingFileId) {
		this.questarStagingFileId = questarStagingFileId;
	}
}