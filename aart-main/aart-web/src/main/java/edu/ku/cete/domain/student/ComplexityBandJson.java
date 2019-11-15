package edu.ku.cete.domain.student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.util.CommonConstants;

public class ComplexityBandJson {

	private Long studentId;
	private String studentName;
	private String finalElaBand;
	private String finalMathBand;
	private String finalSciBand;
	private String commBand;
	private String writingBand;
	private Date surveyCreatedDate;
	private Long surveyCreatedUserId;
	private Date surveyModifiedDate;
	private Long surveyModifiedUserId;
	private Long currentSchoolYear;

	public Date getSurveyModifiedDate() {
		return surveyModifiedDate;
	}

	public void setSurveyModifiedDate(Date surveyModifiedDate) {
		this.surveyModifiedDate = surveyModifiedDate;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getFinalElaBand() {
		return finalElaBand;
	}

	public void setFinalElaBand(String finalElaBand) {
		this.finalElaBand = finalElaBand;
	}

	public String getFinalMathBand() {
		return finalMathBand;
	}

	public void setFinalMathBand(String finalMathBand) {
		this.finalMathBand = finalMathBand;
	}

	public String getFinalSciBand() {
		return finalSciBand;
	}

	public void setFinalSciBand(String finalSciBand) {
		this.finalSciBand = finalSciBand;
	}

	public String getCommBand() {
		return commBand;
	}

	public void setCommBand(String commBand) {
		this.commBand = commBand;
	}

	public String getWritingBand() {
		return writingBand;
	}

	public void setWritingBand(String writingBand) {
		this.writingBand = writingBand;
	}

	public Date getSurveyCreatedDate() {
		return surveyCreatedDate;
	}

	public void setSurveyCreatedDate(Date surveyCreatedDate) {
		this.surveyCreatedDate = surveyCreatedDate;
	}

	public Long getSurveyCreatedUserId() {
		return surveyCreatedUserId;
	}

	public void setSurveyCreatedUserId(Long surveyCreatedUserId) {
		this.surveyCreatedUserId = surveyCreatedUserId;
	}

	public Long getSurveyModifiedUserId() {
		return surveyModifiedUserId;
	}

	public void setSurveyModifiedUserId(Long surveyModifiedUserId) {
		this.surveyModifiedUserId = surveyModifiedUserId;
	}

	public Long getCurrentSchoolYear() {
		return currentSchoolYear;
	}

	public void setCurrentSchoolYear(Long currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

	public String buildjsonString() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		try {
			mapper.setSerializationInclusion(Include.NON_NULL);
			DateFormat df = new SimpleDateFormat(CommonConstants.JSON_TIME_FORMAT);
			mapper.setDateFormat(df);
			jsonInString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		return jsonInString;
	}

}
