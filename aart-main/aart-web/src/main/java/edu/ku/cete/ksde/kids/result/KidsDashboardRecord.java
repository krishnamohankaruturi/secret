package edu.ku.cete.ksde.kids.result;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;

public class KidsDashboardRecord implements Serializable {	
    
	private static final long serialVersionUID = -3615472061248701157L;
	
    private Long id;
    private String recordType;
    private String stateStudentIdentifier;
    private String legalLastName;
    private String legalMiddleName;
    private String legalFirstName;
    private Date dateOfBirth;
    private Long attendanceSchoolId;
    private String attendanceSchoolIdentifier;
    private String attendanceSchoolName;
    private Long aypSchoolId;
    private String aypSchoolIdentifier;
    private String aypSchoolName;
    private String subjectArea;
    private String currentGradeLevel;
    private Long schoolYear;
    private String educatorIdentifier;
    private String educatorFirstName;
    private String educatorLastName;
    private String status;
    private String reasons;
    private String messageType;
    private Date createDate;
    private Date modifiedDate;
    private Date processedDate;
    private Date successfullyProcessedDate;
    private Long recordCommonId;
    private Long seqNo;
    
   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRecordType() {
        return recordType;
    }
    public void setRecordType(String recordType) {
        this.recordType = recordType == null ? null : recordType.trim();
    }
    public String getStateStudentIdentifier() {
        return stateStudentIdentifier;
    }
    public void setStateStudentIdentifier(String stateStudentIdentifier) {
        this.stateStudentIdentifier = stateStudentIdentifier == null ? null : stateStudentIdentifier.trim();
    }
    public String getLegalLastName() {
        return legalLastName;
    }
    public void setLegalLastName(String legalLastName) {
        this.legalLastName = legalLastName == null ? null : legalLastName.trim();
    }
    public String getLegalMiddleName() {
        return legalMiddleName;
    }
    public void setLegalMiddleName(String legalMiddleName) {
        this.legalMiddleName = legalMiddleName == null ? null : legalMiddleName.trim();
    }
    public String getLegalFirstName() {
        return legalFirstName;
    }
    public void setLegalFirstName(String legalFirstName) {
        this.legalFirstName = legalFirstName == null ? null : legalFirstName.trim();
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Long getAttendanceSchoolId() {
        return attendanceSchoolId;
    }
    public void setAttendanceSchoolId(Long attendanceSchoolId) {
        this.attendanceSchoolId = attendanceSchoolId;
    }
    public String getAttendanceSchoolIdentifier() {
        return attendanceSchoolIdentifier;
    }
    public void setAttendanceSchoolIdentifier(String attendanceSchoolIdentifier) {
        this.attendanceSchoolIdentifier = attendanceSchoolIdentifier == null ? null : attendanceSchoolIdentifier.trim();
    }
    public String getAttendanceSchoolName() {
        return attendanceSchoolName;
    }
    public void setAttendanceSchoolName(String attendanceSchoolName) {
        this.attendanceSchoolName = attendanceSchoolName == null ? null : attendanceSchoolName.trim();
    }
    public Long getAypSchoolId() {
        return aypSchoolId;
    }
    public void setAypSchoolId(Long aypSchoolId) {
        this.aypSchoolId = aypSchoolId;
    }
    public String getAypSchoolIdentifier() {
        return aypSchoolIdentifier;
    }
    public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
        this.aypSchoolIdentifier = aypSchoolIdentifier == null ? null : aypSchoolIdentifier.trim();
    }
    public String getAypSchoolName() {
        return aypSchoolName;
    }
    public void setAypSchoolName(String aypSchoolName) {
        this.aypSchoolName = aypSchoolName == null ? null : aypSchoolName.trim();
    }
    public String getSubjectArea() {
        return subjectArea;
    }
    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea == null ? null : subjectArea.trim();
    }
    public String getCurrentGradeLevel() {
        return currentGradeLevel;
    }
    public void setCurrentGradeLevel(String currentGradeLevel) {
        this.currentGradeLevel = currentGradeLevel == null ? null : currentGradeLevel.trim();
    }
    public Long getSchoolYear() {
        return schoolYear;
    }
    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }
    public String getEducatorIdentifier() {
        return educatorIdentifier;
    }
    public void setEducatorIdentifier(String educatorIdentifier) {
        this.educatorIdentifier = educatorIdentifier == null ? null : educatorIdentifier.trim();
    }
    public String getEducatorFirstName() {
        return educatorFirstName;
    }
    public void setEducatorFirstName(String educatorFirstName) {
        this.educatorFirstName = educatorFirstName == null ? null : educatorFirstName.trim();
    }
    public String getEducatorLastName() {
        return educatorLastName;
    }
    public void setEducatorLastName(String educatorLastName) {
        this.educatorLastName = educatorLastName == null ? null : educatorLastName.trim();
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    public String getReasons() {
        return reasons;
    }
    public void setReasons(String reasons) {
        this.reasons = reasons == null ? null : reasons.trim();
    }
    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getModifiedDate() {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public Date getProcessedDate() {
        return processedDate;
    }
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
    public Date getSuccessfullyProcessedDate() {
        return successfullyProcessedDate;
    }
    public void setSuccessfullyProcessedDate(Date successfullyProcessedDate) {
        this.successfullyProcessedDate = successfullyProcessedDate;
    }
    public Long getRecordCommonId() {
        return recordCommonId;
    }
    public void setRecordCommonId(Long recordCommonId) {
        this.recordCommonId = recordCommonId;
    }
	public Long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	
	public List<String> buildJSONRow() {		
		List<String> cells = new ArrayList<String>();
		
		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getProcessedDate() != null) {			
			cells.add(ParsingConstants.BLANK + DateUtil.format(getProcessedDate(),"MM/dd/yy hh:mm a z"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStateStudentIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getStateStudentIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getLegalFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getLegalFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getLegalMiddleName() != null) {
			cells.add(ParsingConstants.BLANK + getLegalMiddleName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getLegalLastName() != null) {
			cells.add(ParsingConstants.BLANK + getLegalLastName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getDateOfBirth()!= null) {
			cells.add(ParsingConstants.BLANK + new SimpleDateFormat("MM/dd/yyyy").format(getDateOfBirth()));
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
		if(getCurrentGradeLevel() != null) {
			cells.add(ParsingConstants.BLANK + getCurrentGradeLevel());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}		
		if(getEducatorIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getEducatorIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getEducatorFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getEducatorFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getEducatorLastName() != null) {
			cells.add(ParsingConstants.BLANK + getEducatorLastName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getMessageType() != null) {
			cells.add(ParsingConstants.BLANK + getMessageType());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getReasons() != null) {
			cells.add(ParsingConstants.BLANK + getReasons());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
				
		
		cells.add("");
				
		return cells;
	}
}