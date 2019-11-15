/**
 * 
 */
package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.util.ParsingConstants;

/**
 * @author ktaduru_sta
 *
 */
public class DailyAccessCodesDTO implements Serializable {

	private static final long serialVersionUID = 1752516256619904300L;
	private Long id;
	private Long assessmentProgramId;
    private String assessmentProgramName;    
    private Long gradeCourseId;
    private String gradeCourseName;    
    private Long contentAreaId;
    private String contentAreaName;
    
	
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public String getAssessmentProgramName() {
		return assessmentProgramName;
	}
	public void setAssessmentProgramName(String assessmentProgramName) {
		this.assessmentProgramName = assessmentProgramName;
	}
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public String getGradeCourseName() {
		return gradeCourseName;
	}
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}	
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public String getContentAreaName() {
		return contentAreaName;
	}
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}
	
	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();

		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add("");
		}
		
		if(getContentAreaName() != null) {
			cells.add(ParsingConstants.BLANK + getContentAreaName());
		} else {
			cells.add("");
		}
        
		if(getGradeCourseName() != null) {
			cells.add(ParsingConstants.BLANK + getGradeCourseName());
		} else {
			cells.add("");
		}
        
		//pdf, csv
		cells.add("");
        
		
        
		return cells;
	}
}
