/**
 * 
 */
package edu.ku.cete.domain.apierrors;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author n278i693
 * Changes for F851 API Errors Dashboard
 *
 */

public class ApiErrorsRecord extends APIDashboardError implements Serializable{
	
	private static final long serialVersionUID = 3350215087863991083L;
	
	private String schoolName;
	
	private String districtName;
	
	private String stateName;
	
	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public List<String> buildJSONRow() {
		
		List<String> cells = new ArrayList<String>();
		
		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getCreatedDate()!=null) {
			cells.add(ParsingConstants.BLANK + DateUtil.format(getCreatedDate(),"MM/dd/yyyy hh:mm a z"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRecordType() != null) {
			cells.add(ParsingConstants.BLANK + getRecordType());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getMessage() != null) {
			cells.add(ParsingConstants.BLANK + getMessage());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRequestType() != null) {
			cells.add(ParsingConstants.BLANK + getRequestType());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getName() != null) {
			cells.add(ParsingConstants.BLANK + getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getClassroomID() != null) {
			cells.add(ParsingConstants.BLANK + getClassroomID());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getDistrictName() != null) {
			cells.add(ParsingConstants.BLANK + getDistrictName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getSchoolName() != null) {
			cells.add(ParsingConstants.BLANK + getSchoolName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStateName() != null) {
			cells.add(ParsingConstants.BLANK + getStateName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		
		cells.add("");
		
		return cells;
	}
}		
	     



