package edu.ku.cete.domain.help;

import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;

public class HelpContentTag extends AuditableDomain {

	private static final long serialVersionUID = -305171127494159970L;
	private long id;
	private long helpContentId;
	private long helpTagId;
	
	private List<HelpTag> helpTags;
	
	
	public List<HelpTag> getHelpTags() {
		return helpTags;
	}

	public void setHelpTags(List<HelpTag> helpTags) {
		this.helpTags = helpTags;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getHelpContentId() {
		return helpContentId;
	}

	public void setHelpContentId(long helpContentId) {
		this.helpContentId = helpContentId;
	}

	public long getHelpTagId() {
		return helpTagId;
	}

	public void setHelpTagId(long helpTagId) {
		this.helpTagId = helpTagId;
	}

}
