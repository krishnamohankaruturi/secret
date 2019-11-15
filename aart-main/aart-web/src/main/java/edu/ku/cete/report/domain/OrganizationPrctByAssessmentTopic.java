package edu.ku.cete.report.domain;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class OrganizationPrctByAssessmentTopic extends ExternalUploadResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1430513273550296029L;
	private String topicCode;
	private Long topicId;
	private Float percentCorrect;
	private Long organizationId;
	
	/**
	 * @return the topicCode
	 */
	public String getTopicCode() {
		return topicCode;
	}
	/**
	 * @param topicCode the topicCode to set
	 */
	public void setTopicCode(String topicCode) {
		this.topicCode = topicCode;
	}
	/**
	 * @return the topicId
	 */
	public Long getTopicId() {
		return topicId;
	}
	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	/**
	 * @return the percentCorrect
	 */
	public Float getPercentCorrect() {
		return percentCorrect;
	}
	/**
	 * @param percentCorrect the percentCorrect to set
	 */
	public void setPercentCorrect(Float percentCorrect) {
		this.percentCorrect = percentCorrect;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	
}
