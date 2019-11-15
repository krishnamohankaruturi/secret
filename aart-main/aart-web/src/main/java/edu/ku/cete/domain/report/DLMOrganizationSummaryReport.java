package edu.ku.cete.domain.report;

public class DLMOrganizationSummaryReport {
		
	private String reportDate;
	private OrganizationGrfCalculation orgGrfCalculation;
	private String dlmLogo;
	private String filePath;
	private String csvFilePath;
	private Long pdfFileSize;
	

	public String getDlmLogo() {
		return dlmLogo;
	}
	public void setDlmLogo(String dlmLogo) {
		this.dlmLogo = dlmLogo;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}	
	public OrganizationGrfCalculation getOrgGrfCalculation() {
		return orgGrfCalculation;
	}
	public void setOrgGrfCalculation(OrganizationGrfCalculation orgGrfCalculation) {
		this.orgGrfCalculation = orgGrfCalculation;
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
	public Long getPdfFileSize() {
		return pdfFileSize;
	}
	public void setPdfFileSize(Long pdfFileSize) {
		this.pdfFileSize = pdfFileSize;
	}				
}