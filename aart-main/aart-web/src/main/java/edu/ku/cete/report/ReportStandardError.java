package edu.ku.cete.report;

public class ReportStandardError implements Comparable<ReportStandardError> {
	private String gradeName;
	private Long gradeId;
	private String schoolStandardError;
	private String districtStandardError;
	private String stateStandardError;


	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}	
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public String getSchoolStandardError() {
		return schoolStandardError;
	}
	public void setSchoolStandardError(String schoolStandardError) {
		this.schoolStandardError = schoolStandardError;
	}
	public String getDistrictStandardError() {
		return districtStandardError;
	}
	public void setDistrictStandardError(String districtStandardError) {
		this.districtStandardError = districtStandardError;
	}
	public String getStateStandardError() {
		return stateStandardError;
	}
	public void setStateStandardError(String stateStandardError) {
		this.stateStandardError = stateStandardError;
	}
	public int compareTo(ReportStandardError other) {
        return gradeId.compareTo(other.gradeId);
    }

}
