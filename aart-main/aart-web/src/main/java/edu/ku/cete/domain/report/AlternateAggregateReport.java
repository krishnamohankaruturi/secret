package edu.ku.cete.domain.report;

import java.io.Serializable;
import java.util.List;
import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;

public class AlternateAggregateReport extends AuditableDomain implements Serializable, Identifiable {
	

	private static final long serialVersionUID = 1L;
		
	private String reportDate;
	private String dlmLogo;
	private String filePath;
	private String csvFilePath;
	private Long pdfFileSize;
	private Long assessmentProgramId;
	private Long reportYear;
	private String state;
	private Long stateId;
	private String stateCode;
	private String residenceDistrictIdentifier;
	private String districtName;
	private String districtShortName;
	private Long districtId;
	private String schoolName;
	private Long schoolId;
	private String kiteEducatorIdentifier;
	private String educatorFirstName;
	private String educatorLastName;
	private List<AlternateAggregateStudents> alternateAggregateStudents;
		
		
	public Long getPdfFileSize() {
		return pdfFileSize;
	}
	public void setPdfFileSize(Long pdfFileSize) {
		this.pdfFileSize = pdfFileSize;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDlmLogo() {
		return dlmLogo;
	}
	public void setDlmLogo(String dlmLogo) {
		this.dlmLogo = dlmLogo;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCsvFilePath() {
		return csvFilePath;
	}
	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public Long getReportYear() {
		return reportYear;
	}
	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getResidenceDistrictIdentifier() {
		return residenceDistrictIdentifier;
	}
	public void setResidenceDistrictIdentifier(String residenceDistrictIdentifier) {
		this.residenceDistrictIdentifier = residenceDistrictIdentifier;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}		
	public String getDistrictShortName() {
		return districtShortName;
	}
	public void setDistrictShortName(String districtShortName) {
		this.districtShortName = districtShortName;
	}	
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getKiteEducatorIdentifier() {
		return kiteEducatorIdentifier;
	}
	public void setKiteEducatorIdentifier(String kiteEducatorIdentifier) {
		this.kiteEducatorIdentifier = kiteEducatorIdentifier;
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
	public List<AlternateAggregateStudents> getAlternateAggregateStudents() {
		return alternateAggregateStudents;
	}
	public void setAlternateAggregateStudents(
			List<AlternateAggregateStudents> alternateAggregateStudents) {
		this.alternateAggregateStudents = alternateAggregateStudents;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getId(int order) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStringIdentifier(int order) {
		// TODO Auto-generated method stub
		return null;
	}

}