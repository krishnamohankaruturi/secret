/**
 * 
 */
package edu.ku.cete.domain.testsession;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.Reportable;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

/**
 * @author m802r921
 *
 */
public class TestSessionRoster extends TraceableEntity implements Serializable,Reportable{

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 2351270568810842648L;
	/**
	 * primary key.
	 */
	private Long id;
	/**
	 * attendance the student is attending.
	 */
	private Organization attendanceSchool = new Organization();
	
	/**
	 * stateCourse.
	 */
	private ContentArea stateCourse = new ContentArea();
	
	/**
	 * stateSubjectArea.
	 */
	private ContentArea stateSubjectArea = new ContentArea();
	/**
	 * courseenrollment
	 */
	private Category courseEnrollment = new Category();
	/**
	 * rosterId
	 */
	private Long rosterId;
	/**
	 * roster.
	 */
	private Roster roster = new Roster();
	
	/**
	 * testSession.
	 */
	private TestSession testSession = new TestSession();
	
	/**
	 * testCollection.
	 */
	private TestCollection testCollection = new TestCollection();
	
	/**
	 * tcGradeCourse.
	 */
	private GradeCourse tcGradeCourse = new GradeCourse();
	
	/**
	 * tcContentArea.
	 */
	private ContentArea tcContentArea = new ContentArea();
	/**
	 * assessment
	 */
	private Assessment assessment = new Assessment();
	/**
	 * testingProgram
	 */
	private TestingProgram testingProgram = new TestingProgram();
	/**
	 * assessmentProgram
	 */
	private AssessmentProgram assessmentProgram = new AssessmentProgram();
	
	private String qcCompleteStatus;
	
	private String testSessionDeletableFlag;
	
	private Boolean expiredFlag;
	
	private Boolean includeCompletedTestSession;
	
	private Boolean includeInProgressTestSession;
	
	private int totalRecords;
	
	private String studentsTestStatus;

	private String testLetProgress;

	/**
	 * educator.
	 */
	private User educator = new User();
	/**
	 * attendance school id.
	 */
	private Long attendanceSchoolId;
	
	private Set<StudentTestResourceInfo> resourceList;
	
	public Boolean getIncludeCompletedTestSession() {
		return includeCompletedTestSession;
	}

	public void setIncludeCompletedTestSession(Boolean includeCompletedTestSession) {
		this.includeCompletedTestSession = includeCompletedTestSession;
	}

	public Boolean getIncludeInProgressTestSession() {
		return includeInProgressTestSession;
	}

	public void setIncludeInProgressTestSession(Boolean includeInProgressTestSession) {
		this.includeInProgressTestSession = includeInProgressTestSession;
	}
	public final Long getId() {
		return id;
	}

	/**
	 * @param id2 the id to set
	 */
	public final void setId(Long id2) {
		this.id = id2;
	}
	/**
	 * @return the attendanceSchool
	 */
	public Organization getAttendanceSchool() {
		return attendanceSchool;
	}

	/**
	 * @param attendanceSchool the attendanceSchool to set
	 */
	public void setAttendanceSchool(Organization attendanceSchool) {
		if (attendanceSchool != null) {
			this.attendanceSchool = attendanceSchool;
		}
	}

	/**
	 * @return the stateCourse
	 */
	public ContentArea getStateCourse() {
		return stateCourse;
	}

	/**
	 * @param stateCourse the stateCourse to set
	 */
	public void setStateCourse(ContentArea stateCourse) {
		this.stateCourse = stateCourse;
	}

	/**
	 * @return
	 */
	public Category getCourseEnrollment() {
		
		return courseEnrollment;
	}

	/**
	 * @param courseenrollment
	 */
	public void setCourseEnrollment(Category courseEnrollment) {
		this.courseEnrollment = courseEnrollment;
	}
	
	/**
	 * @return the stateSubjectArea
	 */
	public ContentArea getStateSubjectArea() {
		return stateSubjectArea;
	}

	/**
	 * @param stateSubjectArea the stateSubjectArea to set
	 */
	public void setStateSubjectArea(ContentArea stateSubjectArea) {
		this.stateSubjectArea = stateSubjectArea;
	}

	/**
	 * @return the rosterId
	 */
	public Long getRosterId() {
		return rosterId;
	}

	/**
	 * @param rosterId the rosterId to set
	 */
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	/**
	 * @return the roster
	 */
	public Roster getRoster() {
		return roster;
	}

	/**
	 * @param roster the roster to set
	 */
	public void setRoster(Roster roster) {
		this.roster = roster;
	}

	/**
	 * @return the testSession
	 */
	public TestSession getTestSession() {
		return testSession;
	}

	/**
	 * @param testSession the testSession to set
	 */
	public void setTestSession(TestSession testSession) {
		this.testSession = testSession;
	}

	/**
	 * @return the testCollection
	 */
	public TestCollection getTestCollection() {
		return testCollection;
	}

	/**
	 * @return the assessment
	 */
	public Assessment getAssessment() {
		return assessment;
	}

	/**
	 * @param assessment the assessment to set
	 */
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	/**
	 * @return the testingProgram
	 */
	public TestingProgram getTestingProgram() {
		return testingProgram;
	}

	/**
	 * @param testingProgram the testingProgram to set
	 */
	public void setTestingProgram(TestingProgram testingProgram) {
		this.testingProgram = testingProgram;
	}
	/**
	 * @return the assessmentProgram
	 */
	public AssessmentProgram getAssessmentProgram() {
		return assessmentProgram;
	}

	public String getQcCompleteStatus() {
		return qcCompleteStatus;
	}

	public void setQcCompleteStatus(String qcCompleteStatus) {
		this.qcCompleteStatus = qcCompleteStatus;
	}

	/**
	 * @param assessmentProgram the assessmentProgram to set
	 */
	public void setAssessmentProgram(AssessmentProgram assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	/**
	 * @param testCollection the testCollection to set
	 */
	public void setTestCollection(TestCollection testCollection) {
		this.testCollection = testCollection;
	}

	/**
	 * @return the tcGradeCourse
	 */
	public GradeCourse getTcGradeCourse() {
		return tcGradeCourse;
	}

	/**
	 * @param tcGradeCourse the tcGradeCourse to set
	 */
	public void setTcGradeCourse(GradeCourse tcGradeCourse) {
		this.tcGradeCourse = tcGradeCourse;
	}

	/**
	 * @return the tcContentArea
	 */
	public ContentArea getTcContentArea() {
		return tcContentArea;
	}

	/**
	 * @param tcContentArea the tcContentArea to set
	 */
	public void setTcContentArea(ContentArea tcContentArea) {
		this.tcContentArea = tcContentArea;
	}

	/**
	 * @return the educator
	 */
	public User getEducator() {
		return educator;
	}

	/**
	 * @param educator the educator to set
	 */
	public void setEducator(User educator) {
		this.educator = educator;
	}
	/**
	 * @return the attendanceSchoolId
	 */
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	/**
	 * @param attendanceSchoolId the attendanceSchoolId to set
	 */
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getTestSessionDeletableFlag() {
		return testSessionDeletableFlag;
	}

	public void setTestSessionDeletableFlag(String testSessionDeletableFlag) {
		this.testSessionDeletableFlag = testSessionDeletableFlag;
	}
	
	public Boolean getExpiredFlag() {
		return expiredFlag;
	}

	public void setExpiredFlag(Boolean expiredFlag) {
		this.expiredFlag = expiredFlag;
	}
	
	public String getStudentsTestStatus() {
		return studentsTestStatus;
	}

	public void setStudentsTestStatus(String studentsTestStatus) {
		this.studentsTestStatus = studentsTestStatus;
	}

	/**
	 * @return {@link String}
	 */
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int getNumberOfAttributes() {
		return 26;
	}

	@Override
	public String getAttribute(int i) {
		String result = null;
		if (i == 0) {
			if (getId() != null) {
				result = ParsingConstants.BLANK + getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 1) {
			if (getTestSession() != null) {
				result = StringUtil.convert(getTestSession().getId(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 2) {
			if (getTestSessionDeletableFlag() != null) {
				result = StringUtil.convert(getTestSessionDeletableFlag(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 3) {
			if (getTestSession() != null) {
				result = StringUtil.convert(getTestSession().getName(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 4) {
			result = "Print Ticket";
		} else if (i == 5) {
			if (getResourceList() != null) {
				StringBuffer pdfInfo = new StringBuffer();
				StringBuffer ucbInfo = new StringBuffer();
				int pdfIndex = 0, ucbIndex = 0;
				for (StudentTestResourceInfo sinfo : getResourceList()) {
					if (sinfo.getFileType().equalsIgnoreCase("pdf")) {
						if (pdfIndex == 0) {
							pdfInfo = pdfInfo.append(sinfo.getFileLocation()).append("`--|-!")
									.append(sinfo.getFileName()).append("`--|-!").append(sinfo.getFileType());
						} else {
							pdfInfo = pdfInfo.append(",").append(sinfo.getFileLocation()).append("`--|-!")
									.append(sinfo.getFileName()).append("`--|-!").append(sinfo.getFileType());
						}
						pdfIndex++;
					} else if (sinfo.getFileType().equalsIgnoreCase("UCB")
							|| sinfo.getFileType().equalsIgnoreCase("UEB")
							|| sinfo.getFileType().equalsIgnoreCase("EBAE")) {
						if (ucbIndex == 0) {
							ucbInfo = ucbInfo.append(sinfo.getFileLocation()).append("`--|-!")
									.append(sinfo.getFileName()).append("`--|-!").append(sinfo.getFileType());
						} else {
							ucbInfo = ucbInfo.append(",").append(sinfo.getFileLocation()).append("`--|-!")
									.append(sinfo.getFileName()).append("`--|-!").append(sinfo.getFileType());
						}
						ucbIndex++;
					}

				}
				if (StringUtils.isNotBlank(ucbInfo)) {
					result = pdfInfo.toString() + "," + ucbInfo.toString();
				} else {
					result = pdfInfo.toString();
				}
			} else {
				result = ParsingConstants.BLANK;
			}
		} else if (i == 6) {
			if (getTestCollection() != null) {
				result = StringUtil.convert(getTestCollection().getId(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 7) {
			if (getTestCollection() != null) {
				result = StringUtil.convert(getAssessment().getAssessmentName(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 8) {
			if (StringUtils.isNotBlank(getTestLetProgress())) {
				result = getTestLetProgress();
			} else {
				result = "NA";
			}
		} else if (i == 9) {
			if (getTcGradeCourse() != null) {
				result = StringUtil.convert(getTcGradeCourse().getName(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 10) {
			if (getTcContentArea() != null) {
				result = StringUtil.convert(getTcContentArea().getName(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		}

		else if (i == 11) {
			if (getTestingProgram() != null) {
				result = StringUtil.convert(getTestingProgram().getProgramName(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 12) {
			if (getAssessmentProgram() != null) {
				result = StringUtil.convert(getAssessmentProgram().getProgramName(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 13) {
			if (getRoster().getId() != null) {
				result = ParsingConstants.BLANK + getRoster().getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 14) {
			if (getRoster().getCourseSectionName() != null
					&& StringUtils.isNotEmpty(getRoster().getCourseSectionName())) {
				result = ParsingConstants.BLANK + getRoster().getCourseSectionName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 15) {
			if (getAttendanceSchool().getDisplayIdentifier() != null
					&& StringUtils.isNotEmpty(getAttendanceSchool().getDisplayIdentifier())) {
				result = ParsingConstants.BLANK + getAttendanceSchool().getDisplayIdentifier();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 16) {
			if (getAttendanceSchool().getOrganizationName() != null
					&& StringUtils.isNotEmpty(getAttendanceSchool().getOrganizationName())) {
				result = ParsingConstants.BLANK + getAttendanceSchool().getOrganizationName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 17) {
			if (getEducator().getUniqueCommonIdentifier() != null
					&& StringUtils.isNotEmpty(getEducator().getUniqueCommonIdentifier())) {
				result = ParsingConstants.BLANK + getEducator().getUniqueCommonIdentifier();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 18) {
			if (getEducator().getFirstName() != null && StringUtils.isNotEmpty(getEducator().getFirstName())) {
				result = ParsingConstants.BLANK + getEducator().getFirstName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 19) {
			if (getEducator().getSurName() != null && StringUtils.isNotEmpty(getEducator().getSurName())) {
				result = ParsingConstants.BLANK + getEducator().getSurName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 20) {
			if (getTestingProgram() != null) {
				result = StringUtil.convert(getTestingProgram().getHighStakesFlag() ? "Y" : "N",
						ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 21) {
			if (getExpiredFlag() != null) {
				result = StringUtil.convert(getExpiredFlag() ? "Y" : "N", ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 22) {
			if (getTestSession() != null) {
				result = StringUtil.convert(getTestSession().getSource(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 23) {
			if (getTestCollection() != null) {
				result = StringUtil.convert(getTestCollection().getRandomizationType(), ParsingConstants.NOT_AVAILABLE);
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 24) {
			if (getTestSession() != null) {
				result = ParsingConstants.BLANK + getTestSession().getCreatedDate();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if (i == 25) {
			if (getStudentsTestStatus() != null) {
				result = ParsingConstants.BLANK + getStudentsTestStatus();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}

		}
		return result;
	}

	@Override
	public String[] getAttributes() {
		String[] result = new String[getNumberOfAttributes()];
		for(int i=0;i<result.length;i++) {
			result[i] = getAttribute(i);
		}
		return result;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getTestLetProgress() {
		return testLetProgress;
	}

	public void setTestLetProgress(String testLetProgress) {
		this.testLetProgress = testLetProgress;
	}

	public Set<StudentTestResourceInfo> getResourceList() {
		return resourceList;
	}

	public void setResourceList(Set<StudentTestResourceInfo> resourceList) {
		this.resourceList = resourceList;
	}
}
