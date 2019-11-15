package edu.ku.cete.report;

import java.util.List;

import edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO;

public class DLMMonitoringSummaryDistrictSchoolData {
	
	private String districtName;
	private Boolean noRecordFound;
	private List<DLMTestAdminMonitoringSummaryDTO> testAdminSummaryDtos;
	
	public DLMMonitoringSummaryDistrictSchoolData(String districtName) {
		this.districtName = districtName;
	}
	
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public List<DLMTestAdminMonitoringSummaryDTO> getTestAdminSummaryDtos() {
		return testAdminSummaryDtos;
	}
	public void setTestAdminSummaryDtos(List<DLMTestAdminMonitoringSummaryDTO> testAdminSummaryDtos) {
		this.testAdminSummaryDtos = testAdminSummaryDtos;
	}

	public Boolean getNoRecordFound() {
		return noRecordFound;
	}

	public void setNoRecordFound(Boolean noRecordFound) {
		this.noRecordFound = noRecordFound;
	}

}
