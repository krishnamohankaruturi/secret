package edu.ku.cete.ksde.kids.result;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;

public class KidsRecentRecords implements Serializable{
	
	private static final long serialVersionUID = -359688159348927850L;
	
	private String createdDate;
	private String recordType;
	private String attendanceSchoolName;
	private String aypSchoolName;
	private String subjectArea;
	private String educatorIdentifier;
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getAttendanceSchoolName() {
		return attendanceSchoolName;
	}
	public void setAttendanceSchoolName(String attendanceSchoolName) {
		this.attendanceSchoolName = attendanceSchoolName;
	}
	public String getAypSchoolName() {
		return aypSchoolName;
	}
	public void setAypSchoolName(String aypSchoolName) {
		this.aypSchoolName = aypSchoolName;
	}
	public String getSubjectArea() {
		return subjectArea;
	}
	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}
	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}
	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
	}
	
	public List<String> buildJSONRow() {		
		List<String> cells = new ArrayList<String>();
		
		if(getCreatedDate() != null) {			
			cells.add(ParsingConstants.BLANK + getCreatedDate());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRecordType() != null) {
			cells.add(ParsingConstants.BLANK + getRecordType());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getAttendanceSchoolName() != null) {
			cells.add(ParsingConstants.BLANK + getAttendanceSchoolName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getAypSchoolName() != null) {
			cells.add(ParsingConstants.BLANK + getAypSchoolName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getSubjectArea() != null) {
			cells.add(ParsingConstants.BLANK + getSubjectArea());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getEducatorIdentifier()!= null) {
			cells.add(ParsingConstants.BLANK + getEducatorIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}				
		cells.add("");
				
		return cells;
	}
}
