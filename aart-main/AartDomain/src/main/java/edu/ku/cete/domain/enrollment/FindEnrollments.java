package edu.ku.cete.domain.enrollment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;

public class FindEnrollments extends TraceableEntity implements Serializable {
	private Long id;
	private String stateStudentIdentifier;
	private String legalFirstName;
	private String legalLastName;
	private String middleName;
	private boolean  active;
	private String districtName;
	private String schoolName;
	private Long schoolId;
	private String gradeName;
	private Long gradeId;
	private Long districtId;
	private Long stateId;
	private String stateName;
	private Long studentId;	
	private List<Long> assessmentPrograms;
	private Integer schoolYear;

	private Date dateOfBirth;
	private String gender;
	
	
	
	
	public final String getDateOfBirthStr() {
		if(dateOfBirth!=null)
			return ParsingConstants.BLANK + DateUtil.format(dateOfBirth, "MM/dd/yyyy");
		else 
			return "";
	}
	
	public final void setDateOfBirthStr(String dob) {
		this.dateOfBirth = DateUtil.parse(dob);
	}
	
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Integer getSchoolYear() {
		return schoolYear;
	}


	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public List<String> buildfindStudentJSONRow() {
		List<String> cells = new ArrayList<String>();

		if(getId() > 0) {
			cells.add(ParsingConstants.BLANK + getId());
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

		if(getLegalLastName() != null) {
			cells.add(ParsingConstants.BLANK + getLegalLastName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(isActive()) {
			cells.add(ParsingConstants.BLANK + "Active");
		} else  {
			cells.add(ParsingConstants.BLANK + "InActive");
		}
		
		if(getDistrictName() != null) {
			cells.add(ParsingConstants.BLANK + getDistrictName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getSchoolName()!= null) {
			cells.add(ParsingConstants.BLANK + getSchoolName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
				
		if(getGradeName() != null) {
			cells.add(ParsingConstants.BLANK + getGradeName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStudentId() != null) {
			cells.add(ParsingConstants.BLANK + getStudentId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getDistrictId() != null) {
			cells.add(ParsingConstants.BLANK + getDistrictId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
	
		if(getSchoolId()!= null) {
			cells.add(ParsingConstants.BLANK + getSchoolId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getGradeId() != null) {
			cells.add(ParsingConstants.BLANK + getGradeId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getStateId() != null) {
			cells.add(ParsingConstants.BLANK + getStateId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getStateName() != null) {
			cells.add(ParsingConstants.BLANK + getStateName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}		
		return cells;
	}
	
	
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getLegalFirstName() {
		return legalFirstName;
	}

	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}

	public String getLegalLastName() {
		return legalLastName;
	}

	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	
	

	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}	
	
	public Long getGradeId() {
		return gradeId;
	}


	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}


	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}


	

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}


	public List<Long> getAssessmentPrograms() {
		return assessmentPrograms;
	}


	public void setAssessmentPrograms(List<Long> assessmentPrograms) {
		this.assessmentPrograms = assessmentPrograms;
	}


	
}
