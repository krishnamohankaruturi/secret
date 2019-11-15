/**
 * 
 */
package edu.ku.cete.domain.content;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author neil.howerton
 *
 */
public class AssessmentProgram extends AuditableDomain {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4008174649577907963L;
	private Long id;
    private String programName;

    private String abbreviatedname;
    private Boolean isDefault;
    
    private Long beginReportYear;

    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the programName
     */
    public final String getProgramName() {
        return programName;
    }
    /**
     * @param programName the programName to set
     */
    public final void setProgramName(String programName) {
        this.programName = programName;
    }

	/**
	 * @return the abbreviatedname
	 */
	public String getAbbreviatedname() {
		return abbreviatedname;
	}
	/**
	 * @param abbreviatedname the abbreviatedname to set
	 */
	public void setAbbreviatedname(String abbreviatedname) {
		this.abbreviatedname = abbreviatedname;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			if(this.getId() == null){
				result = false;
			} else {
				AssessmentProgram ap = (AssessmentProgram) object;
				if (this.getId().equals(ap.getId())) {
					result = true;
				}
			}
		}
		return result;
	}

	// just omitted null checks
	@Override
	public int hashCode() {
		int hash = 3;
		if(this.id != null){
			hash = 7 * hash + this.id.hashCode();
		}
		return hash;
	}
	public Long getBeginReportYear() {
		return beginReportYear;
	}
	public void setBeginReportYear(Long beginReportYear) {
		this.beginReportYear = beginReportYear;
	}
}
