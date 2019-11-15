/**
 * 
 */
package edu.ku.cete.util;

/**
 * @author m802r921
 *
 */
public enum AartResource {
	UPLOAD_FOLDER("upload"),
	UPLOAD_JSP("csvUpload"),
	WEB_SERVICE_FOLDER("webService"),
	IMMEDIATE_UPLOAD("immediateUpload");

	/**
	 * resourceName {@link String}
	 */
	private String resourceName;
	/**
	 * @param rsName {@link String}
	 */
	AartResource(String rsName) {
		this.setResourceName(rsName);
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resName the resourceName to set
	 */
	public void setResourceName(String resName) {
		this.resourceName = resName;
	}

	/**
	 * @return {@link String}
	 */
	public String toString() {
		return resourceName;
	}
}
