package edu.ku.cete.domain.student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.util.CommonConstants;

public class SurveyJson {
	private Long studentId;
	private String stateStudentidentifier;
	private String studentFirstName;
	private String studentLastName;
	private Long mathBandId;
	private Long sciBandId;
	private Long elaBandId;
	private Long commBandId;
	private Long finalMathBandId;
	private Long finalElaBandId;
	private Long finalSciBandId;	
	
	private Long surveyId;
	private String surveyStatus;	
	private Date createdDate;
	private Date modifiedDate;
	private Long createdUserId;
	private Long modifiedUserId;
	private boolean surveyActive;
	private boolean includeScienceFlag;
	private boolean allQuestionsFlag;
	private String lastEditedOption;
	
	private List<StudentSurveySectionsJson> studentSurveySectionsJson = new ArrayList<StudentSurveySectionsJson>();
	
	
	
	public Long getStudentId() {
		return studentId;
	}



	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}



	public String getStateStudentidentifier() {
		return stateStudentidentifier;
	}



	public void setStateStudentidentifier(String stateStudentidentifier) {
		this.stateStudentidentifier = stateStudentidentifier;
	}



	public String getStudentFirstName() {
		return studentFirstName;
	}



	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}



	public String getStudentLastName() {
		return studentLastName;
	}



	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}



	public Long getMathBandId() {
		return mathBandId;
	}



	public void setMathBandId(Long mathBandId) {
		this.mathBandId = mathBandId;
	}



	public Long getSciBandId() {
		return sciBandId;
	}



	public void setSciBandId(Long sciBandId) {
		this.sciBandId = sciBandId;
	}



	public Long getElaBandId() {
		return elaBandId;
	}



	public void setElaBandId(Long elaBandId) {
		this.elaBandId = elaBandId;
	}



	public Long getCommBandId() {
		return commBandId;
	}



	public void setCommBandId(Long commBandId) {
		this.commBandId = commBandId;
	}



	public Long getFinalMathBandId() {
		return finalMathBandId;
	}



	public void setFinalMathBandId(Long finalMathBandId) {
		this.finalMathBandId = finalMathBandId;
	}



	public Long getFinalElaBandId() {
		return finalElaBandId;
	}



	public void setFinalElaBandId(Long finalElaBandId) {
		this.finalElaBandId = finalElaBandId;
	}



	public Long getFinalSciBandId() {
		return finalSciBandId;
	}



	public void setFinalSciBandId(Long finalSciBandId) {
		this.finalSciBandId = finalSciBandId;
	}


	public Long getSurveyId() {
		return surveyId;
	}



	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}



	public String getSurveyStatus() {
		return surveyStatus;
	}



	public void setSurveyStatus(String surveyStatus) {
		this.surveyStatus = surveyStatus;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public Date getModifiedDate() {
		return modifiedDate;
	}



	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	public Long getCreatedUserId() {
		return createdUserId;
	}



	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}



	public Long getModifiedUserId() {
		return modifiedUserId;
	}



	public void setModifiedUserId(Long modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}



	public boolean isSurveyActive() {
		return surveyActive;
	}



	public void setSurveyActive(boolean surveyActive) {
		this.surveyActive = surveyActive;
	}
	

	public boolean isIncludeScienceFlag() {
		return includeScienceFlag;
	}



	public void setIncludeScienceFlag(boolean includeScienceFlag) {
		this.includeScienceFlag = includeScienceFlag;
	}



	public boolean isAllQuestionsFlag() {
		return allQuestionsFlag;
	}



	public void setAllQuestionsFlag(boolean allQuestionsFlag) {
		this.allQuestionsFlag = allQuestionsFlag;
	}



	public String getLastEditedOption() {
		return lastEditedOption;
	}



	public void setLastEditedOption(String lastEditedOption) {
		this.lastEditedOption = lastEditedOption;
	}



	public List<StudentSurveySectionsJson> getStudentSurveySectionsJson() {
		return studentSurveySectionsJson;
	}



	public void setStudentSurveySectionsJson(
			List<StudentSurveySectionsJson> studentSurveySectionsJson) {
		this.studentSurveySectionsJson = studentSurveySectionsJson;
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
