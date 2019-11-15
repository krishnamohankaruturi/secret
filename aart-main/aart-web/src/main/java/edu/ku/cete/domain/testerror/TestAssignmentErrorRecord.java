/**
	 * 
	 * Changes for F845 Test Assignment Errors Dashboard
	 */

package edu.ku.cete.domain.testerror;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.DashboardMessage;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;

public class TestAssignmentErrorRecord extends DashboardMessage implements Serializable{
	
	private static final long serialVersionUID = 3350215087863991083L;
	
	private String ssid;

	private String school;
	
	private String course;
	
	private String educatorId;

	private String studentFirstName;
	
	private String studentLastName;
	
	private String educatorFirstName;
	
	private String educatorLastName;
	
	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getEducatorId() {
		return educatorId;
	}

	public void setEducatorId(String educatorId) {
		this.educatorId = educatorId;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public String getEducatorFirstName() {
		return educatorFirstName;
	}

	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}

	public String getEducatorLastName() {
		return educatorLastName;
	}

	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}


public List<String> buildJSONRow() {
		
		List<String> cells = new ArrayList<String>();
		
		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getCreatedDate()!=null) {
			cells.add(ParsingConstants.BLANK + DateUtil.format(getCreatedDate(),"MM/dd/yyyy hh:mm a z"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getSsid() != null) {
			cells.add(ParsingConstants.BLANK + getSsid());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getMessage() != null) {
			cells.add(ParsingConstants.BLANK + getMessage());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getSchool() != null) {
			cells.add(ParsingConstants.BLANK + getSchool());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getCourse() != null) {
			cells.add(ParsingConstants.BLANK + getCourse());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getClassroomId() != null) {
			cells.add(ParsingConstants.BLANK + getClassroomId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getEducatorId() != null) {
			cells.add(ParsingConstants.BLANK + getEducatorId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStudentFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getStudentFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStudentLastName() != null) {
			cells.add(ParsingConstants.BLANK + getStudentLastName());
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
		
		
		cells.add("");
		
		return cells;
    }

}
