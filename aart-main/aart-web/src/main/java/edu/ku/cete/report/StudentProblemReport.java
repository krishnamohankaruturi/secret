package edu.ku.cete.report;

import java.util.List;

/**
 * @author vittaly
 *
 */
public class StudentProblemReport {
	 
    /**
     * rowQuestionsCount
     */
    private Integer rowQuestionsCount;

    /**
     * currentRowCount 
     */
    private Integer currentRowCount;
    
    /**
     * rowResponseCount 
     */
    private Long rowResponseCount;

    /**
     * sortedStudentNames
     */
    private String sortedStudentNames;

    /**
     * The local identifier for the student.
     */
    private String localStudentIdentifier;
    
    /**
     * List of Correct Responses
     */
    private String correctResponses;
    
    /**
     * Number of Responses
     */
    private String numOfResponses;
    
    /**
     * Context Path 
    */ 
    private String imagePath;
    
    /**
     * studentResponses
     */
    private List<String> studentResponses;
    
    /**
     * scoreRowIndex
     */
    private Long scoreRowIndex;
    /**
     *  Test Section Name
     */
    private String testSectionName;
	/**
	 * @return the rowQuestionsCount
	 */
	public Integer getRowQuestionsCount() {
		return rowQuestionsCount;
	}

	/**
	 * @param rowQuestionsCount the rowQuestionsCount to set
	 */
	public void setRowQuestionsCount(Integer rowQuestionsCount) {
		this.rowQuestionsCount = rowQuestionsCount;
	}

	/**
	 * @return the currentRowCount
	 */
	public Integer getCurrentRowCount() {
		return currentRowCount;
	}

	/**
	 * @param currentRowCount the currentRowCount to set
	 */
	public void setCurrentRowCount(Integer currentRowCount) {
		this.currentRowCount = currentRowCount;
	}

	/**
	 * @return the totalRowCount
	 */
	public Long getRowResponseCount() {
		return rowResponseCount;
	}

	/**
	 * @return the sortedStudentNames
	 */
	public String getSortedStudentNames() {
		return sortedStudentNames;
	}

	/**
	 * @param sortedStudentNames the sortedStudentNames to set
	 */
	public void setSortedStudentNames(String sortedStudentNames) {
		this.sortedStudentNames = sortedStudentNames;
	}

	/**
	 * @param rowResponseCount the rowResponseCount to set
	 */
	public void setRowResponseCount(Long rowResponseCount) {
		this.rowResponseCount = rowResponseCount;
	}		

	/**
	 * @return the localStudentIdentifier
	 */
	public String getLocalStudentIdentifier() {
		return localStudentIdentifier;
	}

	/**
	 * @param localStudentIdentifier the localStudentIdentifier to set
	 */
	public void setLocalStudentIdentifier(String localStudentIdentifier) {
		this.localStudentIdentifier = localStudentIdentifier;
	}

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

	/**
	 * @return the studentResponses
	 */
	public List<String> getStudentResponses() {
		return studentResponses;
	}

	/**
	 * @param studentResponses the studentResponses to set
	 */
	public void setStudentResponses(List<String> studentResponses) {
		this.studentResponses = studentResponses;
	}

	/**
	 * @return the scoreRowIndex
	 */
	public Long getScoreRowIndex() {
		return scoreRowIndex;
	}

	/**
	 * @param scoreRowIndex the scoreRowIndex to set
	 */
	public void setScoreRowIndex(Long scoreRowIndex) {
		this.scoreRowIndex = scoreRowIndex;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getTestSectionName() {
		return testSectionName;
	}

	public void setTestSectionName(String testSectionName) {
		this.testSectionName = testSectionName;
	}

	

}
