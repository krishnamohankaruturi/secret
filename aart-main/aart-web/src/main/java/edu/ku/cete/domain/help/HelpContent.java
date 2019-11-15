package edu.ku.cete.domain.help;

import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;

public class HelpContent extends AuditableDomain {

	private static final long serialVersionUID = 3226810653759975418L;
	private long id;
	private long helpTopicId;
	private String content;
	private Date expireDate;
	private String helpTitle;
	private String status;
	private String fileName;

	// non-persistent and only for grid usage.
	private String helpTopicName;
	private String helpToicDescription;
	private HelpTopic helpTopic;
	private List<HelpTag> helpTags;
	private String programName;
	private String stateName;
	private String roles;
	private String slug;
	private String createdBy;
	private String modifiedBy;

	private List<HelpContentContext> helpContentContext;

	public List<HelpContentContext> getHelpContentContext() {
		return helpContentContext;
	}

	public void setHelpContentContext(List<HelpContentContext> helpContentContext) {
		this.helpContentContext = helpContentContext;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getHelpTopicId() {
		return helpTopicId;
	}

	public void setHelpTopicId(long helpTopicId) {
		this.helpTopicId = helpTopicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getHelpTitle() {
		return helpTitle;
	}

	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getHelpTopicName() {
		return helpTopicName;
	}

	public void setHelpTopicName(String helpTopicName) {
		this.helpTopicName = helpTopicName;
	}

	public HelpTopic getHelpTopic() {
		return helpTopic;
	}

	public void setHelpTopic(HelpTopic helpTopic) {
		this.helpTopic = helpTopic;
	}

	public List<HelpTag> getHelpTags() {
		return helpTags;
	}

	public void setHelpTags(List<HelpTag> helpTags) {
		this.helpTags = helpTags;
	}

	public String getHelpToicDescription() {
		return helpToicDescription;
	}

	public void setHelpToicDescription(String helpToicDescription) {
		this.helpToicDescription = helpToicDescription;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
