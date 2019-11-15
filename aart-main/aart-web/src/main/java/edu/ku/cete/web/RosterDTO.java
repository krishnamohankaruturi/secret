package edu.ku.cete.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.util.ParsingConstants;
/**
 *
 * @author neil.howerton
 *
 */
public class RosterDTO {

    private Roster roster;
    private int numStudents;
	

    /**
     *
     *@return {@link Roster}
     */
    public final Roster getRoster() {
        return roster;
    }

    /**
     *
     *@param roster {@link Roster}
     */
    public final void setRoster(Roster roster) {
        this.roster = roster;
    }

    /**
     * @return the numStudents
     */
    public final int getNumStudents() {
        return numStudents;
    }

    /**
     * @param numStudents the numStudents to set
     */
    public final void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    /**
	 * Method to construct the json list for view rosters record browser.
	 * 
	 * @return
	 */
	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();

		if(getRoster().getId() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getRoster().getCourseSectionName() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getCourseSectionName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRoster().getTeacher().getUniqueCommonIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getTeacher().getUniqueCommonIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRoster().getTeacher().getFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getTeacher().getFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getRoster().getTeacher().getSurName() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getTeacher().getSurName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRoster().getSubject().getName() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getSubject().getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getRoster().getCourse() != null && getRoster().getCourse().getName() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getCourse().getName());
		} else {
			cells.add(ParsingConstants.BLANK);
		}
		if(getRoster().getTeacher().getId() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getTeacher().getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getRoster().getStateSubjectAreaId() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getStateSubjectAreaId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getRoster().getTeacher().getStatusCode() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getTeacher().getStatusCode());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getRoster().getStateCoursesId() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getStateCoursesId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRoster().getCurrentSchoolYear() != 0) {
			cells.add(ParsingConstants.BLANK + getRoster().getCurrentSchoolYear());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRoster().getSchoolName() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getSchoolName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// Changed During US16275
		Long schoolId = getRoster().getSchoolId();
		if(schoolId != null){
			cells.add(ParsingConstants.BLANK + schoolId);
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		return cells;
	}
	
}
