package edu.ku.cete.report;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.student.Student;

/**
 * @author vkulkarni_sta
 *
 */
public class GraphScoreReport {
	 /**
	  * CorrectResponses
   **/
    private String correctResponses;
    
    /**
     * Number of Responses
     */
    private String numOfResponses;
    
    /**
     * Number of Test Sections
     */
    private List<Integer> testSectionsList;
    
    /**
     * List of Task Variant Ids for a Test
     */
    private List<Long> taskVariantIds;
    /**
     * List of Test Section Names for a Test
     */
    private List<String> testSectionNames;

	/**
	 * @return the correctResponses
	 */
	public String getCorrectResponses() {
		return correctResponses;
	}

	/**
	 * @param correctResponses the correctResponses to set
	 */
	public void setCorrectResponses(String correctResponses) {
		this.correctResponses = correctResponses;
	}

	public String getNumOfResponses() {
		return numOfResponses;
	}

	public void setNumOfResponses(String numOfResponses) {
		this.numOfResponses = numOfResponses;
	}

	public List<Integer> getTestSectionsList() {
		return testSectionsList;
	}

	public void setTestSectionsList(List<Integer> testSectionsList) {
		this.testSectionsList = testSectionsList;
	}

	public List<Long> getTaskVariantIds() {
		return taskVariantIds;
	}

	public void setTaskVariantIds(List<Long> taskVariantIds) {
		this.taskVariantIds = taskVariantIds;
	}

	public List<String> getTestSectionNames() {
		return testSectionNames;
	}

	public void setTestSectionNames(List<String> testSectionNames) {
		this.testSectionNames = testSectionNames;
	}

	

}
