package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KSDEStudentTestDTO {

	private Long studentstestsid;
	private Long testid;
	private Date testBeginDate;
	private Date testEndTime;
	private Long responseCount;
	private String stageCode;	
	private String studentTeststatus;
	private String ksdeScCode;		
	private Long numofExludedItems;
	private Long sectionQuestionsCount;
	
	private List<KSDEStudentTestSectionsDTO> ksdeStudentTestSectionDtos = new ArrayList<KSDEStudentTestSectionsDTO>();

	/**
	 * @return the studentstestsid
	 */
	public Long getStudentstestsid() {
		return studentstestsid;
	}

	/**
	 * @param studentstestsid the studentstestsid to set
	 */
	public void setStudentstestsid(Long studentstestsid) {
		this.studentstestsid = studentstestsid;
	}

	/**
	 * @return the testid
	 */
	public Long getTestid() {
		return testid;
	}

	/**
	 * @param testid the testid to set
	 */
	public void setTestid(Long testid) {
		this.testid = testid;
	}

	/**
	 * @return the testBeginDate
	 */
	public Date getTestBeginDate() {
		return testBeginDate;
	}

	/**
	 * @param testBeginDate the testBeginDate to set
	 */
	public void setTestBeginDate(Date testBeginDate) {
		this.testBeginDate = testBeginDate;
	}

	/**
	 * @return the testEndTime
	 */
	public Date getTestEndTime() {
		return testEndTime;
	}

	/**
	 * @param testEndTime the testEndTime to set
	 */
	public void setTestEndTime(Date testEndTime) {
		this.testEndTime = testEndTime;
	}

	/**
	 * @return the responseCount
	 */
	public Long getResponseCount() {
		return responseCount;
	}

	/**
	 * @param responseCount the responseCount to set
	 */
	public void setResponseCount(Long responseCount) {
		this.responseCount = responseCount;
	}

	/**
	 * @return the stageCode
	 */
	public String getStageCode() {
		return stageCode;
	}

	/**
	 * @param stageCode the stageCode to set
	 */
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}	

	/**
	 * @return the studentTeststatus
	 */
	public String getStudentTeststatus() {
		return studentTeststatus;
	}

	/**
	 * @param studentTeststatus the studentTeststatus to set
	 */
	public void setStudentTeststatus(String studentTeststatus) {
		this.studentTeststatus = studentTeststatus;
	}

	/**
	 * @return the ksdeStudentTestSectionDtos
	 */
	public List<KSDEStudentTestSectionsDTO> getKsdeStudentTestSectionDtos() {
		return ksdeStudentTestSectionDtos;
	}

	/**
	 * @param ksdeStudentTestSectionDtos the ksdeStudentTestSectionDtos to set
	 */
	public void setKsdeStudentTestSectionDtos(
			List<KSDEStudentTestSectionsDTO> ksdeStudentTestSectionDtos) {
		this.ksdeStudentTestSectionDtos = ksdeStudentTestSectionDtos;
	}

	/**
	 * @return the ksdeScCode
	 */
	public String getKsdeScCode() {
		return ksdeScCode;
	}

	/**
	 * @param ksdeScCode the ksdeScCode to set
	 */
	public void setKsdeScCode(String ksdeScCode) {
		this.ksdeScCode = ksdeScCode;
	}

	public Long getNumofExludedItems() {
		return numofExludedItems;
	}

	public void setNumofExludedItems(Long numofExludedItems) {
		this.numofExludedItems = numofExludedItems;
	}

	public Long getSectionQuestionsCount() {
		return sectionQuestionsCount;
	}

	public void setSectionQuestionsCount(Long sectionQuestionsCount) {
		this.sectionQuestionsCount = sectionQuestionsCount;
	}	
	
}
