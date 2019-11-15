package edu.ku.cete.domain.enrollment;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.student.Student;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author vittaly
 *
 */
public class AutoRegisteredStudentsDTO {
	
	private Long studentId;
	private Long studentTestId;
	private Long specialCircumstanceId;
	private String testStatus;
	private Double interimTheta;
	private Boolean pnpBrailleSelected;
	private String specialCircumstanceStatus;
	private Long specialCircumstanceStatusId;
	private int totalRecords;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * student
	 */
	private Student student = new Student();

	/**
	 * @return
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Long getSpecialCircumstanceId() {
		return specialCircumstanceId;
	}

	public void setSpecialCircumstanceId(Long specialCircumstanceId) {
		this.specialCircumstanceId = specialCircumstanceId;
	}
	
	public String getSpecialCircumstanceStatus() {
		return specialCircumstanceStatus;
	}

	public void setSpecialCircumstanceStatus(String specialCircumstanceStatus) {
		this.specialCircumstanceStatus = specialCircumstanceStatus;
	}
	
	public Long getStudentTestId() {
		return studentTestId;
	}

	public void setStudentTestId(Long studentTestId) {
		this.studentTestId = studentTestId;
	}	
	
	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}				

	public Double getInterimTheta() {
		return interimTheta;
	}

	public void setInterimTheta(Double interimTheta) {
		this.interimTheta = interimTheta;
	}

	public Boolean getPnpBrailleSelected() {
		return pnpBrailleSelected;
	}

	public void setPnpBrailleSelected(Boolean pnpBrailleSelected) {
		this.pnpBrailleSelected = pnpBrailleSelected;
	}
	
	public Long getSpecialCircumstanceStatusId() {
		return specialCircumstanceStatusId;
	}

	public void setSpecialCircumstanceStatusId(Long specialCircumstanceStatusId) {
		this.specialCircumstanceStatusId = specialCircumstanceStatusId;
	}

	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();
		
		//Set id
		if(getStudent().getId() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set stateStudentIdentifier
		if(getStudent().getStateStudentIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getStateStudentIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		//Set legalFirstName
		if(getStudent().getLegalFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
				
		//Set legalLastName
		if(getStudent().getLegalLastName() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalLastName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		//Set roster		
		//cells.add(ParsingConstants.BLANK);
				
		//Set tickets		
		cells.add("Print Ticket");
		
		//Set id
		if(this.getStudentTestId() != null) {
			cells.add(ParsingConstants.BLANK + getStudentTestId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//set Test Status
		if(this.getTestStatus() != null){
			cells.add(ParsingConstants.BLANK + getTestStatus());
			
			if("Complete".equalsIgnoreCase(getTestStatus())){
				//set Interim theta value
				if(this.getInterimTheta() != null){
					cells.add(ParsingConstants.BLANK + getInterimTheta());
				}else{
					cells.add(ParsingConstants.BLANK);
				}
			}else{
				cells.add(ParsingConstants.BLANK);
			}
			
		}else{
			cells.add(ParsingConstants.NOT_AVAILABLE);
			//Interim theta
			cells.add(ParsingConstants.BLANK);
		}
		
		//set PNP Braille selected flag
		cells.add(ParsingConstants.BLANK + getPnpBrailleSelected());
		
		//specialCircumstanceId
		if(this.getSpecialCircumstanceId() != null) {
			cells.add(ParsingConstants.BLANK + getSpecialCircumstanceId());
		} else {
			cells.add(ParsingConstants.BLANK);
		}
		
		cells.add("Save Special Circumstance");
		
		if(this.getSpecialCircumstanceStatus()!= null) {
			cells.add(ParsingConstants.BLANK + getSpecialCircumstanceStatus());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(this.getSpecialCircumstanceStatusId()!= null) {
			cells.add(ParsingConstants.BLANK + getSpecialCircumstanceStatusId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		return cells;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
}
