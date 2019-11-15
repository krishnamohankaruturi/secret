package edu.ku.cete.domain.report;

import java.util.List;

public class DLMOrganizationSummaryGrade {
		
	private String gradeLevel;
	private List<DLMOrganizationSummarySubject> orgSummarySubjectLists;
	
	
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public List<DLMOrganizationSummarySubject> getOrgSummarySubjectLists() {
		return orgSummarySubjectLists;
	}
	public void setOrgSummarySubjectLists(
			List<DLMOrganizationSummarySubject> orgSummarySubjectLists) {
		this.orgSummarySubjectLists = orgSummarySubjectLists;
	}

}