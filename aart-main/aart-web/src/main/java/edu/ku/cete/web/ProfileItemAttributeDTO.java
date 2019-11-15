package edu.ku.cete.web;

/**
 * @author vittaly
 *This class holds id's and names of attributes,attributenicknames 
 *and attributecontaiers for a PNP record.
 */
public class ProfileItemAttributeDTO {

	/**
	 * id
	 */
	private Long id;
	
	/**
     * attributeNameId
     */
    private Long attributeNameId;
   
    /**
     * attributeName
     */
    private String attributeName;
    
    /**
     * attributeContainerId
     */
    private Long attributeContainerId;    
    /**
     * attributeContainerName
     */
    private String attributeContainerName;
    
    /**
     * attributeNicknameId
     */
    private Long attributeNicknameId;
    
    /**
     * attributeNickname
     */
    private String attributeNickname;
    
    /**
     * viewOption
     */
    private String viewOption;
    private Long assessmentProgramId;
    private String assessmentProgramCode;
    private String nonSelectedValue;
    
    private Long attributeNameAttributeContainerId;
    /**
     * @return
     */
    public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getAttributeNameId() {
		return attributeNameId;
	}

	public void setAttributeNameId(Long attributeNameId) {
		this.attributeNameId = attributeNameId;
	}

	/**
	 * @return
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return
	 */
	public Long getAttributeContainerId() {
		return attributeContainerId;
	}

	/**
	 * @param attributeContainerId
	 */
	public void setAttributeContainerId(Long attributeContainerId) {
		this.attributeContainerId = attributeContainerId;
	}

	public String getAttributeContainerName() {
		return attributeContainerName;
	}

	public void setAttributeContainerName(String attributeContainerName) {
		this.attributeContainerName = attributeContainerName;
	}

	/**
	 * @return
	 */
	public Long getAttributeNicknameId() {
		return attributeNicknameId;
	}

	/**
	 * @param attributeNicknameId
	 */
	public void setAttributeNicknameId(Long attributeNicknameId) {
		this.attributeNicknameId = attributeNicknameId;
	}

	/**
	 * @return
	 */
	public String getAttributeNickname() {
		return attributeNickname;
	}

	/**
	 * @param attributeNickname
	 */
	public void setAttributeNickname(String attributeNickname) {
		this.attributeNickname = attributeNickname;
	}

	public String getViewOption() {
		return viewOption;
	}

	public void setViewOption(String viewOption) {
		this.viewOption = viewOption;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public String getNonSelectedValue() {
		return nonSelectedValue;
	}

	public void setNonSelectedValue(String nonSelectedValue) {
		this.nonSelectedValue = nonSelectedValue;
	}

	public Long getAttributeNameAttributeContainerId() {
		return attributeNameAttributeContainerId;
	}

	public void setAttributeNameAttributeContainerId(
			Long attributeNameAttributeContainerId) {
		this.attributeNameAttributeContainerId = attributeNameAttributeContainerId;
	}
}
