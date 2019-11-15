/**
 * 
 */
package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.report.ReportSubscores;

/**
 * @author ktaduru_sta
 *
 */
public class StudentScoreDTO {

	private Long studentId;
	private Long studentReportId;
	private String subjectCode;
	private String subject;
	private Long reportYear;
	private String reportSchoolIdentifier;
	private String reportSchoolName;
	private String gradeLevel;
	private String reportDistrictIdentifier;
	private String reportDistrictName;
	private Long scaleScore;
	private Long performanceLevel;
	private String performanceLevelName;
	private Boolean exitStatus;
	private Boolean incompleteStatus;
	private Boolean transferred;
	private String reportFlags;
	private Boolean suppressMainScalescorePrfrmLevel;
	private String metametricsMeasure;
	private Long listeningScaleScore;
	private Long listeningPerformanceLevel;
	private Long readingScaleScore;
	private Long readingPerformanceLevel;
	private Long speakingScaleScore;
	private Long speakingPerformanceLevel;
	private Long writingScaleScore;
	private Long writingPerformanceLevel;
	private Long overAllProficiencyLevel;
	private String readingStatus;
	private String listeningStatus;
	private String speakingStatus;
	private String writingStatus;
	private String speakingScoringStatus;
	private String writingScoringStatus;
	
	List<ReportSubscores> subscores = new ArrayList<ReportSubscores>();
	
	
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}	
	public Long getStudentReportId() {
		return studentReportId;
	}
	public void setStudentReportId(Long studentReportId) {
		this.studentReportId = studentReportId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public Long getReportYear() {
		return reportYear;
	}
	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	public String getReportSchoolIdentifier() {
		return reportSchoolIdentifier;
	}
	public void setReportSchoolIdentifier(String reportSchoolIdentifier) {
		this.reportSchoolIdentifier = reportSchoolIdentifier;
	}
	public String getReportSchoolName() {
		return reportSchoolName;
	}
	public void setReportSchoolName(String reportSchoolName) {
		this.reportSchoolName = reportSchoolName;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public String getReportDistrictIdentifier() {
		return reportDistrictIdentifier;
	}
	public void setReportDistrictIdentifier(String reportDistrictIdentifier) {
		this.reportDistrictIdentifier = reportDistrictIdentifier;
	}
	public String getReportDistrictName() {
		return reportDistrictName;
	}
	public void setReportDistrictName(String reportDistrictName) {
		this.reportDistrictName = reportDistrictName;
	}
	public Long getScaleScore() {
		return scaleScore;
	}
	public void setScaleScore(Long scaleScore) {
		this.scaleScore = scaleScore;
	}
	public Long getPerformanceLevel() {
		return performanceLevel;
	}
	public void setPerformanceLevel(Long performanceLevel) {
		this.performanceLevel = performanceLevel;
	}
	public String getPerformanceLevelName() {
		return performanceLevelName;
	}
	public void setPerformanceLevelName(String performanceLevelName) {
		this.performanceLevelName = performanceLevelName;
	}
	public String getReportFlags() {
		return reportFlags;
	}
	public void setReportFlags(String reportFlags) {
		this.reportFlags = reportFlags;
	}	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<ReportSubscores> getSubscores() {
		return subscores;
	}
	public void setSubscores(List<ReportSubscores> subscores) {
		this.subscores = subscores;
	}
	public Boolean getExitStatus() {
		return exitStatus;
	}
	public void setExitStatus(Boolean exitStatus) {
		this.exitStatus = exitStatus;
	}
	public Boolean getIncompleteStatus() {
		return incompleteStatus;
	}
	public void setIncompleteStatus(Boolean incompleteStatus) {
		this.incompleteStatus = incompleteStatus;
	}
	public Boolean getTransferred() {
		return transferred;
	}
	public void setTransferred(Boolean transferred) {
		this.transferred = transferred;
	}
	public Boolean getSuppressMainScalescorePrfrmLevel() {
		return suppressMainScalescorePrfrmLevel;
	}
	public void setSuppressMainScalescorePrfrmLevel(Boolean suppressMainScalescorePrfrmLevel) {
		this.suppressMainScalescorePrfrmLevel = suppressMainScalescorePrfrmLevel;
	}

	public String getMetametricsMeasure() {
		return metametricsMeasure;
	}
	public void setMetametricsMeasure(String metametricsMeasure) {
		this.metametricsMeasure = metametricsMeasure;
	}

	public Long getListeningScaleScore() {
		return listeningScaleScore;
	}

	public void setListeningScaleScore(Long listeningScaleScore) {
		this.listeningScaleScore = listeningScaleScore;
	}

	public Long getListeningPerformanceLevel() {
		return listeningPerformanceLevel;
	}

	public void setListeningPerformanceLevel(Long listeningPerformanceLevel) {
		this.listeningPerformanceLevel = listeningPerformanceLevel;
	}

	public Long getReadingScaleScore() {
		return readingScaleScore;
	}

	public void setReadingScaleScore(Long readingScaleScore) {
		this.readingScaleScore = readingScaleScore;
	}

	public Long getReadingPerformanceLevel() {
		return readingPerformanceLevel;
	}

	public void setReadingPerformanceLevel(Long readingPerformanceLevel) {
		this.readingPerformanceLevel = readingPerformanceLevel;
	}

	public Long getSpeakingScaleScore() {
		return speakingScaleScore;
	}

	public void setSpeakingScaleScore(Long speakingScaleScore) {
		this.speakingScaleScore = speakingScaleScore;
	}

	public Long getSpeakingPerformanceLevel() {
		return speakingPerformanceLevel;
	}

	public void setSpeakingPerformanceLevel(Long speakingPerformanceLevel) {
		this.speakingPerformanceLevel = speakingPerformanceLevel;
	}

	public Long getWritingScaleScore() {
		return writingScaleScore;
	}

	public void setWritingScaleScore(Long writingScaleScore) {
		this.writingScaleScore = writingScaleScore;
	}

	public Long getWritingPerformanceLevel() {
		return writingPerformanceLevel;
	}

	public void setWritingPerformanceLevel(Long writingPerformanceLevel) {
		this.writingPerformanceLevel = writingPerformanceLevel;
	}

	public Long getOverAllProficiencyLevel() {
		return overAllProficiencyLevel;
	}

	public void setOverAllProficiencyLevel(Long overAllProficiencyLevel) {
		this.overAllProficiencyLevel = overAllProficiencyLevel;
	}

	public String getReadingStatus() {
		return readingStatus;
	}

	public void setReadingStatus(String readingStatus) {
		this.readingStatus = readingStatus;
	}

	public String getListeningStatus() {
		return listeningStatus;
	}

	public void setListeningStatus(String listeningStatus) {
		this.listeningStatus = listeningStatus;
	}

	public String getSpeakingStatus() {
		return speakingStatus;
	}

	public void setSpeakingStatus(String speakingStatus) {
		this.speakingStatus = speakingStatus;
	}

	public String getWritingStatus() {
		return writingStatus;
	}

	public void setWritingStatus(String writingStatus) {
		this.writingStatus = writingStatus;
	}

	public String getSpeakingScoringStatus() {
		return speakingScoringStatus;
	}

	public void setSpeakingScoringStatus(String speakingScoringStatus) {
		this.speakingScoringStatus = speakingScoringStatus;
	}

	public String getWritingScoringStatus() {
		return writingScoringStatus;
	}

	public void setWritingScoringStatus(String writingScoringStatus) {
		this.writingScoringStatus = writingScoringStatus;
	}

}
