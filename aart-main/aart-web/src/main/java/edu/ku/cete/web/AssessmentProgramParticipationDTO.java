package edu.ku.cete.web;

import edu.ku.cete.domain.common.AartBeanToCsv;
import edu.ku.cete.util.CommonConstants;

/**
 * DTO class that holds Organization level monitor student participation numbers 
 * from db to generate CSV file.
 * @author vittaly
 *
 */
public class AssessmentProgramParticipationDTO implements AartBeanToCsv {

	/**
	 * assessmentProgramName
	 */
	public String assessmentProgramName;
	
	/**
	 * testCollectionName
	 */
	public String testCollectionName;
	
	/**
	 * testName
	 */
	public String testName;
	
	/**
	 * state
	 */
	public String state;
	
	/**
	 * district
	 */
	public String district;
	
	/**
	 * school
	 */
	public String school;
	
	/**
	 * day
	 */
	public String day;
	
	/**
	 * status
	 */
	public String status;
	
	/**
	 * total
	 */
	public String total;
	
	/**
	 * @return
	 */
	public String getAssessmentProgramName() {
		return assessmentProgramName;
	}

	/**
	 * @param assessmentProgramName
	 */
	public void setAssessmentProgramName(String assessmentProgramName) {
		this.assessmentProgramName = assessmentProgramName;
	}

	/**
	 * @return
	 */
	public String getTestCollectionName() {
		return testCollectionName;
	}

	/**
	 * @param testCollectionName
	 */
	public void setTestCollectionName(String testCollectionName) {
		this.testCollectionName = testCollectionName;
	}

	/**
	 * @return
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * @param testName
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	
	/** 
	 * @param style
	 * @return
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.assessmentProgramName);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.testCollectionName);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.testName);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.state);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.district);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.school);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.day);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.status);
		builder.append(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM);
		builder.append(this.total);

		return builder.toString();
	}
	
}
