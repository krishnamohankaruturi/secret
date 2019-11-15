package edu.ku.cete.domain.test;

import java.util.ArrayList;
import java.util.List;


public class StudentsTestsDto {

	private Long studentId;
	private String name;
	private List<StudentsTestSectionsDto> sectionStatus; 
	
	public Long getstudentId() {
		return studentId;
	}
	public void setstudentId(Long stiudentId) {
		this.studentId = stiudentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<StudentsTestSectionsDto> getSectionStatus() {
		return sectionStatus;
	}
	public void setSectionStatus(List<StudentsTestSectionsDto> sectionStatus) {
		this.sectionStatus = sectionStatus;
	}	
	
	public void add(StudentsTestSectionsDto sectionStatusdto) {
		if (sectionStatus == null) {
			sectionStatus = new ArrayList<StudentsTestSectionsDto>();
    	}
		this.sectionStatus.add(sectionStatusdto);
	}
}
