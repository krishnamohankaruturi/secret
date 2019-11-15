package edu.ku.cete.domain.help;

import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;

public class HelpTopic extends AuditableDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private String slug;
	
	private List<HelpContent> helpContent;
	

	public List<HelpContent> getHelpContent() {
		return helpContent;
	}

	public void setHelpContent(List<HelpContent> helpContent) {
		this.helpContent = helpContent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

}
