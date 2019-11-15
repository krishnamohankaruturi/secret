package edu.ku.cete.domain.sif;

public class SifXMLUpload {
	private Long id;
	private String type;
	private String xml;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml == null ? null : xml.trim();
	}

	public void validate() {
		if (this.id == null) {
			this.id = 0L;
		}
		if (this.type == null) {
			this.type = " ";
		}
		if (this.xml == null) {
			this.xml = " ";
		}

	}

}
