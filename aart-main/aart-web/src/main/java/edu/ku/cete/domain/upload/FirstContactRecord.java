/**
 * 
 */
package edu.ku.cete.domain.upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import edu.ku.cete.domain.property.ContainsKeyValuePairs;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.student.survey.Survey;
import edu.ku.cete.util.AARTCollectionUtil;

/**
 * @author mahesh
 * Q3-stateStudentIdentifier,Q13_1-contractingOrganizationAlias,Q36_1-question36Choice
 * 
 * This is the object that stores the parsed csv information from first contact.
 * 
 */
public class FirstContactRecord extends TraceableEntity implements StudentRecord,ContainsKeyValuePairs{
	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 2570272661566340533L;
	public static final String TEXT = "TEXT";
	//TODO change it to survey.getId()
	private Survey survey = new Survey();
	private Student student = new Student();
	private String contractingOrganizationAlias;
	private boolean existingRecord = false;
	/**
	 * TODO move it to properties ?
	 */
	private static final String[] UN_FLAGGED_TEXT_LABELS = {"Q154","Q65","Q63"};
	/**
	 * labelResponseMap.
	 */
	private Map<String,String> labelResponseMap = new HashMap<String, String>();
	/**
	 * labelResponseMap for text responses
	 */
	private Map<String,String> textLabelResponseMap = new HashMap<String, String>();	
	/**
	 * @return the survey
	 */
	public Survey getSurvey() {
		return survey;
	}
	/**
	 * @param survey the survey to set
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	/**
	 * @return the stateStudentIdentifier
	 */
	public String getStateStudentIdentifier() {
		return student.getStateStudentIdentifier();
	}
	/**
	 * @param stateStudentIdentifier the stateStudentIdentifier to set
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		student.setStateStudentIdentifier(stateStudentIdentifier);
	}
	/**
	 * @return the contractingOrganizationAlias
	 */
	public final String getContractingOrganizationAlias() {
		return contractingOrganizationAlias;
	}
	/**
	 * @param contractingOrganizationAlias the contractingOrganizationAlias to set
	 */
	public final void setContractingOrganizationAlias(
			String contractingOrganizationDisplayIdentifier) {
		this.contractingOrganizationAlias = contractingOrganizationDisplayIdentifier;
		List<String> keyValuePairs = new ArrayList<String>();
		keyValuePairs.add("Q13_1");
		keyValuePairs.add(contractingOrganizationDisplayIdentifier);
		this.setKeyValuePairs(keyValuePairs);
	}
	/**
	 * @return the labelResponseMap
	 */
	public Map<String,String> getLabelResponseMap() {
		return labelResponseMap;
	}
	/**
	 * @param labelResponseMap the labelResponseMap to set
	 */
	public void setLabelResponseMap(Map<String,String> labelResponseMap) {
		this.labelResponseMap = labelResponseMap;
	}
	/**
	 * @return the textLabelResponseMap
	 */
	public final Map<String, String> getTextLabelResponseMap() {
		return textLabelResponseMap;
	}
	/**
	 * @param textLabelResponseMap the textLabelResponseMap to set
	 */
	public final void setTextLabelResponseMap(
			Map<String, String> textLabelResponseMap) {
		this.textLabelResponseMap = textLabelResponseMap;
	}
	@Override
	public String getIdentifier() {
		return getStateStudentIdentifier();
	}
	@Override
	public Long getId() {
		return getSurvey().getId();
	}
	@Override
	public Long getId(int order) {
		return getId();
	}
	@Override
	public String getStringIdentifier(int order) {
		return getStateStudentIdentifier();
	}
	@Override
	public Student getStudent() {
		return student;
	}
	/**
	 * @param student the student to set
	 */
	public final void setStudent(Student student) {
		this.student = student;
	}
	@Override
	public List<String> getKeyValuePairs() {
		return new ArrayList<String>();
	}
	@Override
	public void setKeyValuePairs(List<String> keyValuePair) {
		if(keyValuePair != null
				&& keyValuePair.size() == 2
				&& keyValuePair.get(0) != null
				&& StringUtils.isNotEmpty(keyValuePair.get(0))) {
			if(keyValuePair.get(0).endsWith(TEXT)
					) {
				//INFO: if label ends with text then this is a text response question
				this.textLabelResponseMap.put(keyValuePair.get(0).trim(), keyValuePair.get(1));
			} else if(AARTCollectionUtil.contains(UN_FLAGGED_TEXT_LABELS,keyValuePair.get(0))) {
				this.textLabelResponseMap.put(keyValuePair.get(0).trim()+"_"+TEXT, keyValuePair.get(1));
			} else {
				this.labelResponseMap.put(keyValuePair.get(0).trim(), keyValuePair.get(1));
			}
		}
	}
	/**
	 * @return the existingRecord
	 */
	public boolean isExistingRecord() {
		return existingRecord;
	}
	/**
	 * @param existingRecord the existingRecord to set
	 */
	public void setExistingRecord(boolean existingRecord) {
		this.existingRecord = existingRecord;
	}
	

}
