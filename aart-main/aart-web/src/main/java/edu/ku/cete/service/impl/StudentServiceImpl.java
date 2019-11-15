package edu.ku.cete.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.domain.StudentAssessmentProgram;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsOrganizationInfo;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.student.StudentExitCodesDTO;
import edu.ku.cete.domain.student.StudentInformationRecord;
import edu.ku.cete.domain.student.StudentJson;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.student.StudentRosterITIRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.StudentAssessmentProgramMapper;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.StudentAuditTrailHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.EventNameForAudit;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StringUtil;
import edu.ku.cete.util.StudentUtil;
import edu.ku.cete.web.IAPStudentTestStatusDTO;
import edu.ku.cete.web.ViewStudentDTO;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.domain.enrollment.FindEnrollments;

/**
 * @author m802r921
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class StudentServiceImpl implements StudentService {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 6729294708668693990L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	private static final String DLM = "DLM";

    /** StudentDao holder. */
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentUtil studentUtil;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    private EnrollmentTestTypeSubjectAreaDao enrollmentTestTypeSubjectAreaDao;

    /**
     * simplifiedLimit
     */
    @Value("${studentrecords.simplified.limit}")
    private Integer simplifiedLimit;

    /**
     * simplifiedLimitOrgSize
     */
    @Value("${studentrecords.simplified.limit.orgsize}")
    private Integer simplifiedLimitOrgSize;
    
    /*
     * limit the size of an in clause being sent to query
     */
    @Value("${in.clause.split.limit}")
    private int inClauseLimit;
    
    @Value("${testsession.status.gradechangeinactivated}")
    private String gradeChangeInactivatedPrefix;
    
    @Value("${ssidValidate.errorMessage.accessDecline}")
    private String accessDeclineErrorMessage;
    
    @Value("${ssidValidate.errorMessage.unAuthorize}")
    private String unAuthorizeErrorMessage;
    
    @Value("${ssidValidate.errorMessage.noRecord}")
    private String noRecordErrorMessage;
    
    @Value("${ssidValidate.errorMessage.activate}")
    private String activateErrorMessage;
    
    @Value("${ssidValidate.errorMessage.notAssociatedOrg}")
    private String notAssociatedOrgErrorMessage;
    
    @Value("${ssidValidate.errorMessage.notAssociatedAsp}")
    private String notAssociatedAspErrorMessage;
        
    /**
     * permissionUtil.
     */
    @Autowired
    private PermissionUtil permissionUtil;
    
    @Autowired
    private AssessmentProgramDao assessmentProgramDao;
    
    @Autowired
    private DomainAuditHistoryMapper domainAuditHistoryDao;
    
    @Autowired
    private StudentAssessmentProgramMapper studentAssessmentProgramsDao;
    
    @Autowired
    UserAuditTrailHistoryMapper userAuditTrailHistoryMapperDao;

    @Autowired
    private StudentProfileService studentProfileService;
    
    @Autowired
	private EnrollmentDao enrollmentDao;
    
    @Autowired
    private TestSessionService testSessionService;
    
    @Autowired
    private StudentsTestsService studentsTestsService;
    
    @Autowired
    private StudentTrackerService studentTrackerService;
    
    @Autowired
    private ItiTestSessionService itiService;
    
    @Autowired
	private DataExtractService dataExtractService;
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private EnrollmentService enrollmentService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Student add(Student toAdd) {
    	studentUtil.generateStudentCredentials(toAdd);
    	String username = studentDao.getUsername(toAdd.getUsername());
    	toAdd.setUsername(username);
        studentDao.add(toAdd);
        //TODO why is this still necessary.
        toAdd.setId(studentDao.lastid());
        toAdd.setSaveStatus(RecordSaveStatus.STUDENT_ADDED);
        return toAdd;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean delete(final Long id) {
        if (studentDao.delete(id) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public final Student findById(long studentId) {
        return studentDao.findById(studentId);
    }

    /**
     * @param rosterId long
     * @return List<Student>
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Student> findByRoster(long rosterId) {
        return studentDao.findByRoster(rosterId);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Student> getAll() {
        return studentDao.getAll();
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentService#getByStudentID(String)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Student getByStudentID(final Long studentID) {
        return studentDao.getByStudentID(studentID);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentService#getByStudentID(String)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Student getByStudentIDWithFirstContact(final Long studentID) {
        return studentDao.getByStudentIDWithFirstContact(studentID);
    }
    
    /**
     * @param student {@link StudentExample}
     * @return {@link List}
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Student> getByCriteria(StudentExample student) {
        return studentDao.getByCriteria(student);
    }
    /**
     * @param student {@link StudentExample}
     * @return {@link List}
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Student> getByCriteriaForResync(StudentExample student) {
        return studentDao.getByCriteriaForResync(student);
    }
    /**
     * @param toSaveorUpdate {@link Student}
     * @return {@link List}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Student addOrUpdate(Student toSaveorUpdate, Long attendanceSchoolId) {
    	Student studentFound = null;
    	studentFound = getByStateStudentIdentifier(toSaveorUpdate, attendanceSchoolId);
    	
        if (toSaveorUpdate.getId() == 0) {
            //studentFound = getByStateStudentIdentifier(toSaveorUpdate, attendanceSchoolId);            
            if(!studentFound.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND) && !studentFound.isDoReject()){
            	//Student is not found and nothing invalid on it.
                if (!toSaveorUpdate.isDoReject()) {
					//studentUtil.generateStudentCredentials(toSaveorUpdate);
					toSaveorUpdate = add(toSaveorUpdate);
					toSaveorUpdate
							.setSaveStatus(RecordSaveStatus.STUDENT_ADDED);
				}
            } else if (studentFound.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND) && !studentFound.isDoReject()) {
            	
            	//Code change for defect:DE2902 - Start
            	//toSaveorUpdate = student;
            	toSaveorUpdate = populateStudentToUpdate(toSaveorUpdate,studentFound);
            	//Code change for defect:DE2902 - End
            	
            	LOGGER.debug("Student is found and valid " + toSaveorUpdate);
            } else {
            	LOGGER.debug("Student is rejected " + toSaveorUpdate);
            }
        } else {        	
            //because the id is valid no need to search.
            toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_FOUND);
        }

        if (toSaveorUpdate.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
        	StudentJson studentJson = studentDao.getStudentjsonData(toSaveorUpdate.getId());
            if(studentJson  != null){
            	toSaveorUpdate.setBeforeJsonString(studentJson.buildjsonString());
            }
            toSaveorUpdate = update(toSaveorUpdate);
            toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_UPDATED);
        }
        //All Students should not be pushed to TDE only the students claimed by teachers.
        return toSaveorUpdate;
    }
    /**
     * @param toSaveorUpdate {@link Student}
     * @param attendanceSchoolIds {@link List}
     * @return {@link List}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Student getByStateStudentIdentifier(Student toSaveorUpdate, Long attendanceSchoolId) {
        //Student is not fully updated if found.
        //less information.
    	boolean validInput = true;
    	List<Student> students = null;
    	if(toSaveorUpdate == null || attendanceSchoolId == null) {
    		validInput = false;
    	}
        if (validInput) {
			students = studentDao.getBySsidAndState(toSaveorUpdate.getStateStudentIdentifier(), toSaveorUpdate.getStateId()); //new ArrayList<Student>();
            //for (Long schoolId : attendanceSchoolIds){
            	//List<Student> matchingStudents = studentDao.getBySsidAndAsids(toSaveorUpdate.getStateStudentIdentifier(), schoolId);
            	//students.addAll(matchingStudents);
            //}
		}
		if(students == null || CollectionUtils.isEmpty(students)) {
        	LOGGER.warn("No Students found");
        	toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_NOT_FOUND);
        } else {
    		//Code change to remove the validation on statestudentidentifier to fix the defect - 
        		//DE5556 EP: Production - Uploading rosters sometimes produces an invalid error message 
            //toSaveorUpdate = students.get(0);
        	populateStudentToUpdate(toSaveorUpdate, students.get(0));
            toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_FOUND);
        }
		
		//Commented code to remove the validation on statestudentidentifier to fix the defect - 
			//DE5556 EP: Production - Uploading rosters sometimes produces an invalid error message
		/*else if (students.size() == 1) {
            //toSaveorUpdate = students.get(0);
        	populateStudentToUpdate(toSaveorUpdate, students.get(0));
            toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_FOUND);
        } else if (students.size() > 1) {
        	LOGGER.error("Multiple students for "+toSaveorUpdate.getStateStudentIdentifier());
        	toSaveorUpdate.addInvalidField(
        			ParsingConstants.BLANK + FieldName.STUDENT_STATE_ID,
        			ParsingConstants.BLANK + toSaveorUpdate.getStateStudentIdentifier(),
        			true, InvalidTypes.MULTIPLE_FOUND);
        }*/
		
        return toSaveorUpdate;
    	
    }

    /**
     * @param contractingOrganizationIds {@link List}
     * @param userOrganizationIds {@link List}
     * @param differentialOrgIds {@link List}
     * @param toSaveOrUpdate {@link Student}
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public Student verifyStateStudentIdentifierExists(List<Long> contractingOrganizationIds,
    		List<Long> userOrganizationIds,List<Long> differentialOrgIds,
    		Student toSaveOrUpdate) {
		LOGGER.debug("Enforcing uniqueness of state id in "
				+ contractingOrganizationIds);
		boolean validInput = false;
		boolean verifyStateId = true;
		Boolean stateIdExists = null;
		if(userOrganizationIds == null ||
				CollectionUtils.isEmpty(userOrganizationIds) ||
				contractingOrganizationIds == null ||
				CollectionUtils.isEmpty(contractingOrganizationIds)) {
			LOGGER.debug("Invalid input organizations" +
					" when Enforcing uniqueness of state id in student"
					+ toSaveOrUpdate);
			toSaveOrUpdate
			.setSaveStatus(RecordSaveStatus.SCHOOL_NOT_CONTRACTING_FOR_ASSESSMENT);
			toSaveOrUpdate
			.addInvalidField(
					ParsingConstants.BLANK
							+ FieldName.STUDENT_STATE_ID,
					ParsingConstants.BLANK
							+ toSaveOrUpdate
									.getStateStudentIdentifier(),
					true, InvalidTypes.SCHOOL_NOT_CONTRACTING_FOR_ASSESSMENT);
			LOGGER.debug("Student is rejected because "
			+ toSaveOrUpdate + "School not contracting for assessment");
		} else {
			LOGGER.debug("Organization input is valid");
			validInput = true;
		}
		if(validInput && (differentialOrgIds == null || CollectionUtils.isEmpty(differentialOrgIds)) ) {
			LOGGER.debug(" The user organization tree is bigger than contracting org tree no verification needed");
			verifyStateId = false;
		} else {
			LOGGER.debug("State id needs to be verified for " + toSaveOrUpdate);
		}
		if(validInput && verifyStateId) {
			stateIdExists = studentDao
					.verifyStateStudentIdentifier(
							toSaveOrUpdate.getStateStudentIdentifier(),
							differentialOrgIds);
			if(stateIdExists == null) {
				stateIdExists = false;
			} else {
				toSaveOrUpdate
				.setSaveStatus(RecordSaveStatus.STUDENT_STATE_ID_EXISTS);
				toSaveOrUpdate
				.addInvalidField(
						ParsingConstants.BLANK
								+ FieldName.STUDENT_STATE_ID,
						ParsingConstants.BLANK
								+ toSaveOrUpdate
										.getStateStudentIdentifier(),
						true, InvalidTypes.NOT_UNIQUE);					
			}
		} else if(validInput && !verifyStateId) {
			LOGGER.debug(" The user organization tree is bigger than contracting org tree no verification needed");			
		} else if (!validInput) {
			LOGGER.debug(" In valid input");
		}
		return toSaveOrUpdate;
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public List<? extends StudentRecord> verifyStateStudentIdentifiersExist(Collection<Long> contractingOrganizationIds,
    		Collection<Long> userOrganizationIds,Collection<Long> differentialOrgIds,
    		List<? extends StudentRecord> studentRecords) {
		LOGGER.debug("Enforcing uniqueness of state id in "
				+ contractingOrganizationIds);
		boolean validInput = false;
		boolean verifyStateId = true;
		//Validate the input.
		if(studentRecords == null || CollectionUtils.isEmpty(studentRecords)) {
			LOGGER.debug("No student is passed for verification");
		} else if (userOrganizationIds == null ||
				CollectionUtils.isEmpty(userOrganizationIds)) {
			LOGGER.debug("User is not part of any organization" +
					" when Enforcing uniqueness of state id in students"
					+ studentRecords);
		} else if(contractingOrganizationIds == null ||
				CollectionUtils.isEmpty(contractingOrganizationIds)) {
			LOGGER.debug("Invalid input organizations" +
					" when Enforcing uniqueness of state id in students"
					+ studentRecords);
			for(StudentRecord studentRecord:studentRecords) {
				if (studentRecord != null && studentRecord.getStudent() != null) {
					studentRecord.addInvalidField(ParsingConstants.BLANK
							+ FieldName.STUDENT_STATE_ID,
							ParsingConstants.BLANK
									+ studentRecord.getStudent()
											.getStateStudentIdentifier(), true,
							InvalidTypes.SCHOOL_NOT_CONTRACTING_FOR_ASSESSMENT);
					LOGGER.debug("Student is rejected because " + studentRecord
							+ "School not contracting for assessment");
				}
			}
		} else {
			LOGGER.debug("Organization input is valid");
			validInput = true;
		}
		if(validInput && (differentialOrgIds == null || CollectionUtils.isEmpty(differentialOrgIds)) ) {
			LOGGER.debug(" The user organization tree is bigger than contracting org tree no verification needed");
			verifyStateId = false;
		} else {
			LOGGER.debug("State id needs to be verified for " + studentRecords);
		}
		
		
        //Commented code to remove the validation on statestudentidentifier to fix the defect - 
    		//DE5556 EP: Production - Uploading rosters sometimes produces an invalid error message
		
		/*if(validInput && verifyStateId) {
			
            //DE5505 - added because the size of this list is greater than the max size of an in clause
            //for the postgres java driver - it uses Integer for the size of the clause
            //splitting up the calls in  chunks
            int orgIdsLength = differentialOrgIds.size();
            List<String> inValidStateStudentIdentifiers = new ArrayList<String>();
            if (orgIdsLength > inClauseLimit){
                int numberOfCalls = orgIdsLength/inClauseLimit;
                int remainder = orgIdsLength % inClauseLimit;
                if (remainder > 0)
                	numberOfCalls++;
                for (int i = 0; i < numberOfCalls; i++){
                	int from = i * inClauseLimit;
                	int to =  ((i+1) * inClauseLimit)-1;
                	if (i == numberOfCalls-1)
                		to = orgIdsLength -1;
                	inValidStateStudentIdentifiers.addAll(studentDao
        					.verifyStateStudentIdentifiers(
        							AARTCollectionUtil.getStringIdentifiers(studentRecords, 2),
        							((ArrayList<Long>)differentialOrgIds).subList(from, to)));
                }            
            }else{
            	inValidStateStudentIdentifiers.addAll(studentDao
    					.verifyStateStudentIdentifiers(
    							AARTCollectionUtil.getStringIdentifiers(studentRecords, 2),
    							differentialOrgIds));
            }
            
			if (inValidStateStudentIdentifiers != null
					&& CollectionUtils.isNotEmpty(inValidStateStudentIdentifiers)) {
				for (StudentRecord studentRecord : studentRecords) {
					if (studentRecord != null
							&& studentRecord.getStudent() != null
							&& inValidStateStudentIdentifiers
									.contains(studentRecord.getStudent()
											.getStateStudentIdentifier())) {
						studentRecord.addInvalidField(ParsingConstants.BLANK
								+ FieldName.STUDENT_STATE_ID,
								ParsingConstants.BLANK
										+ studentRecord.getStudent()
												.getStateStudentIdentifier(),
								true, InvalidTypes.NOT_UNIQUE);
					} else {
						LOGGER.debug("Student is valid " + studentRecord);
					}
				}
			} else {
				LOGGER.debug("All state student identifiers are valid");
			}
		} else if(validInput && !verifyStateId) {
			LOGGER.debug(" The user organization tree is bigger than contracting org tree no verification needed");			
		} else if (!validInput) {
			LOGGER.debug(" In valid input");
		}*/
		
		return studentRecords;
    }
    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentService#verifyStudentsExist(java.util.Collection, java.util.List)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public List<? extends StudentRecord> verifyStudentsExist(
    		Collection<Long> userOrganizationIds,
    		List<? extends StudentRecord> inputStudentRecords) {
		LOGGER.debug("Enforcing uniqueness of state id in "
				+ userOrganizationIds);
		List<String> existingStudentIdentifiers = new ArrayList<String>();
		Map<String,Student> existingStudentIdentifierMap = new HashMap<String, Student>();
		//INFO: in the given list there can be multiple rows with the same state student identifier.
		// in that case just go with the last one ? except they could be for a different state, but that means CETEsysadmin...
		// cetesysadmin should (or) should not be permitted to do this ?
		Map<String,List<StudentRecord>> inputStudentIdentifierRecordsMap = new HashMap<String, List<StudentRecord>>();
		boolean validInput = false;
		//Validate the input.
		if(inputStudentRecords == null || CollectionUtils.isEmpty(inputStudentRecords)) {
			LOGGER.debug("No student is passed for verification");
			//INFO: Group the input student records by the state student identifier.
			for(StudentRecord inputStudentRecord:inputStudentRecords) {
				if(!inputStudentIdentifierRecordsMap.containsKey(
						inputStudentRecord.getStringIdentifier(2))) {
					inputStudentIdentifierRecordsMap.put(inputStudentRecord.getStringIdentifier(2),new ArrayList<StudentRecord>());
				}
				inputStudentIdentifierRecordsMap.get(
						inputStudentRecord.getStringIdentifier(2)).add(inputStudentRecord);
			}
		} else if (userOrganizationIds == null ||
				CollectionUtils.isEmpty(userOrganizationIds)) {
			LOGGER.debug("User is not part of any organization" +
					" when Enforcing uniqueness of state id in students"
					+ inputStudentRecords);
		} else {
			LOGGER.debug("Organization input is valid");
			validInput = true;
		}
		if(validInput) {
			List<Student> existingStudents = studentDao
					.verifyStudentsExist(
							AARTCollectionUtil.getStringIdentifiers(inputStudentRecords, 2),
							userOrganizationIds);
			if (existingStudents != null && CollectionUtils.isNotEmpty(existingStudents)) {
				//TODO address the condition of multiple students with same state student identifier in different state.
				//when and if this functionality is to be addressed for a cetesysadmin
				existingStudentIdentifiers = AARTCollectionUtil.getStringIdentifiers(existingStudents, 1);
				for(Student existingStudent:existingStudents) {
					if(existingStudentIdentifierMap.containsKey(existingStudent.getStateStudentIdentifier())) {
						//INFO: Multiple student found for the same state id.
						LOGGER.debug("Multiple student found with the same state student identifier "
					+ existingStudent.getStateStudentIdentifier());
						List<StudentRecord> inputStudentRecordsForStateId= inputStudentIdentifierRecordsMap.get(
								existingStudent.getStateStudentIdentifier()
								);
						for(StudentRecord inputStudentRecordForStateId:inputStudentRecordsForStateId) {
							inputStudentRecordForStateId.addInvalidField(ParsingConstants.BLANK
									+ FieldName.STUDENT_STATE_ID,
									ParsingConstants.BLANK
											+ existingStudent.getStateStudentIdentifier(),
									true, InvalidTypes.MULTIPLE_FOUND);							
						}
					}
					existingStudentIdentifierMap.put(existingStudent.getStateStudentIdentifier(), existingStudent);
				}
				
			}

			
			if (existingStudentIdentifiers != null
					&& CollectionUtils.isNotEmpty(existingStudentIdentifiers)) {
				for (StudentRecord inputStudentRecord : inputStudentRecords) {
					if (inputStudentRecord != null
							&& inputStudentRecord.getStudent() != null
							&& existingStudentIdentifiers
									.contains(inputStudentRecord.getStudent()
											.getStateStudentIdentifier())) {
						LOGGER.debug("Student is valid " + inputStudentRecord);
						//INFO if found in inputStudentRecords set the id
						if(existingStudentIdentifierMap.containsKey(
								inputStudentRecord.getStudent().getStateStudentIdentifier())) {
							inputStudentRecord.getStudent().setId(
									existingStudentIdentifierMap.get(
											inputStudentRecord.getStudent().getStateStudentIdentifier()
											).getId()
											);
						}
						
					} else {
						inputStudentRecord.addInvalidField(ParsingConstants.BLANK
								+ FieldName.STUDENT_STATE_ID,
								ParsingConstants.BLANK
										+ inputStudentRecord.getStudent()
												.getStateStudentIdentifier(),
								true, InvalidTypes.NOT_FOUND);
					}
				}
			} else {
				LOGGER.debug("All state student identifiers are not found");
				for (StudentRecord studentRecord : inputStudentRecords) {
					studentRecord.addInvalidField(ParsingConstants.BLANK
							+ FieldName.STUDENT_STATE_ID,
							ParsingConstants.BLANK
									+ studentRecord.getStudent()
											.getStateStudentIdentifier(),
							true, InvalidTypes.NOT_FOUND);
				}
			}
		} else if (!validInput) {
			LOGGER.debug("Invalid input: StudentService#verifyStudentsExist");
		}
		return inputStudentRecords;
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Student addOrUpdateSelective(Student toSaveorUpdate,
    		Long attendanceSchoolId) {
    	Long userId = toSaveorUpdate.getCurrentContextUserId();
    	toSaveorUpdate = getByStateStudentIdentifier(toSaveorUpdate, attendanceSchoolId);
        if (!toSaveorUpdate.isDoReject() && toSaveorUpdate.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
        	//The student is uniquely found.
			if (!hasCredentials(toSaveorUpdate)) {
				//This should only happen for unknown cases. All students added through AART will have credentials.
				studentUtil.generateStudentCredentials(toSaveorUpdate);
			}
			toSaveorUpdate.setCurrentContextUserId(userId);
			toSaveorUpdate.setAuditColumnProperties();
			StudentExample studentUpdateExample = new StudentExample();
			studentUpdateExample.createCriteria().andIdEqualTo(
					toSaveorUpdate.getId());
			int updated = studentDao.updateByExampleSelective(toSaveorUpdate,
					studentUpdateExample);
			addStudentAssmntIfNotPresent(toSaveorUpdate);
			if (updated > 0) {
				LOGGER.debug("Number of student records updated "+toSaveorUpdate + updated);
				//toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_UPDATED);
			}
		} else if(!toSaveorUpdate.isDoReject()){
			add(toSaveorUpdate);
			addStudentAssmntIfNotPresent(toSaveorUpdate);
		}
        /*//All Students should not be pushed to TDE only the students claimed by teachers.
		if(!toSaveorUpdate.isDoReject()) {			
        	toSaveorUpdate = upsertStudent(toSaveorUpdate);
        	if(!toSaveorUpdate.getSynced()) {
        		toSaveorUpdate.addInvalidField(
        				FieldName.STUDENT + ParsingConstants.BLANK,
        				ParsingConstants.BLANK, false,
        				InvalidTypes.NOT_SYNCED);
        	}
        	LOGGER.debug("Saved the student successfully to TDE "+ toSaveorUpdate);
        }*/

        return toSaveorUpdate;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addStudentAssmntIfNotPresent(Student toSaveorUpdate) {
		if(toSaveorUpdate.getAssessmentProgramId() != null) {
			boolean isStudentAssessmentPresent = studentAssessmentProgramsDao.
					isStudentInAssessmentProgram(toSaveorUpdate.getId(), toSaveorUpdate.getAssessmentProgramId());
			if(!isStudentAssessmentPresent) {
				addStudentsAssessmentProgram(toSaveorUpdate); 
			}
		}
	}
        
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addStudentsAssessmentProgram(Student toSaveorUpdate) {
		StudentAssessmentProgram stuAssessmentPrograms = new StudentAssessmentProgram();
		stuAssessmentPrograms.setActiveFlag(true);
		stuAssessmentPrograms.setAssessmentProgramId(toSaveorUpdate.getAssessmentProgramId());
		stuAssessmentPrograms.setStudentId(toSaveorUpdate.getId());					
		studentAssessmentProgramsDao.insert(stuAssessmentPrograms);
	}

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Student update(Student toUpdate) {
		if (!hasCredentials(toUpdate)) {
			studentUtil.generateStudentCredentials(toUpdate);
			String username = studentDao.getUsername(toUpdate.getUsername());
			toUpdate.setUsername(username);
		}    	
		studentDao.updateIgnoreFinalBands(toUpdate);
        return toUpdate;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExampleSelective(Student record, StudentExample example) {
        return studentDao.updateByExampleSelective(record, example);
    }

    /**
     *
     *@param toSaveorUpdate {@link Student}
     *@return boolean - whether the student has credentials already created.
     */
    private boolean hasCredentials(Student toSaveorUpdate) {
        return (toSaveorUpdate.getUsername() != null && !toSaveorUpdate.getUsername().equals(StringUtils.EMPTY)
                && toSaveorUpdate.getPassword() != null && !toSaveorUpdate.getPassword().equals(StringUtils.EMPTY));
    }

    /**
     * Copy selected properties that should not be updated.
     * This is to be used only enrollment CSV, test CSV and KIDS upload.
     * 
     * @param toSaveorUpdate
     * @param studentFound
     * @return toSaveorUpdate 
     */
    private Student populateStudentToUpdate(Student studentToUpdate, Student studentFound) {       
    	studentToUpdate.setId(studentFound.getId());
    	//Credentials should not change for the student.
    	
    	if(!StringUtil.compareLikeIgnoreCase(studentFound.getLegalFirstName(),studentToUpdate.getLegalFirstName())||
    			!StringUtil.compareLikeIgnoreCase(studentFound.getLegalLastName(),studentToUpdate.getLegalLastName()))
			{
    			studentToUpdate.setUsername(studentUtil.generateUsername(studentToUpdate));
			}else {
				studentToUpdate.setUsername(studentFound.getUsername());
			}
    	studentToUpdate.setPassword(studentFound.getPassword());
    	studentToUpdate.setSaveStatus(studentFound.getSaveStatus());
    	studentToUpdate.setCreatedDate(studentFound.getCreatedDate());
    	studentToUpdate.setCreatedUser(studentFound.getCreatedUser());
    	//If the student is already synced,
    	//no need to sync them untill the credentials are changed.
    	studentToUpdate.setSynced(studentFound.getSynced());
    	//Changed during DE12837 -- set source type in student 
    	if(studentToUpdate.getSourceType() == null && studentFound.getSourceType() != null) {
    		studentToUpdate.setSourceType(studentFound.getSourceType());
    	}else if(studentFound.getSourceType() == null){
    		//stop to update for existing record whose have source column empty
    		studentToUpdate.setSourceType(null);
    	}else if(studentToUpdate.getSourceType() != null && studentFound.getSourceType() != null){
    		studentToUpdate.setSourceType(studentFound.getSourceType());
    	}
    	//INFO: first language, comprehensive race, primary disabilitycode,
    	//hispanic ethnicity. These fields should be updated during enrollment and test.
    	if(studentFound.getSynced()
    			&& (
    					!StringUtil.compareLikeIgnoreCase(
    							studentFound.getLegalFirstName(),
    							studentToUpdate.getLegalFirstName()
    							)
    							||
    					!StringUtil.compareLikeIgnoreCase(
    	    			studentFound.getLegalLastName(),
    	    			studentToUpdate.getLegalLastName()
    	    			)
    	    		)
    					) {
    		LOGGER.warn("Student is already synced and name has changed" + studentToUpdate);
    	}
    	return studentToUpdate;
    }

	/* (non-Javadoc)
	 * @see edu.ku.cete.service.StudentService#upsertUnsyncedStudents(java.util.List)
	 * INFO: Students are not getting synced consistently between AART and TDE.
	 * This is to try and sync the students.
	 
	@Override
    @Transactional(readOnly = false,
    propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean upsertUnsyncedStudents(List<Long> studentIds) {
		boolean syncSucceded = true;
		List<Student>
		students = studentDao.findByIdAndSynced(studentIds, false);
		if(students != null && CollectionUtils.isNotEmpty(students)) {
			LOGGER.error("Un synced students found "+ students);
			for(Student student:students) {
				student = upsertStudent(student);
				if(!student.getSynced()) {
					//sync failed becase it failed for one of the students.
					syncSucceded = false;
				}
			}
		} else {
			LOGGER.debug("No unsynced students found");
		}
		return syncSucceded;
	}*/
	
    
    /* (non-Javadoc)
	 * @see edu.ku.cete.service.StudentService#upsertUnsyncedStudents(java.util.List)
	 * INFO: Students are not getting synced consistently between AART and TDE.
	 * This is to try and sync the students.
	 */
	/*@Override
    @Transactional(readOnly = false,
    propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateUnsyncedStudents(List<Long> studentIds) {
		//boolean syncSucceded = true;
		List<Student>
		students = studentDao.findByIdAndSynced(studentIds, false);
		if(students != null && CollectionUtils.isNotEmpty(students)) {
			//LOGGER.error("Un synced students found "+ students);
			for(Student student:students) {				
				student.setSynced(true);
		            StudentExample example = new StudentExample();
		            StudentExample.Criteria criteria = example.createCriteria();
		            criteria.andIdEqualTo(student.getId());
		            studentDao.updateByExampleSelective(student, example);
			}
		} else {
			LOGGER.debug("No unsynced students found");
		}		
	}*/
	

	/* (non-Javadoc)
	 * @see edu.ku.cete.service.StudentService#getStudentInformationRecords(java.util.List, java.util.List, 
	 * edu.ku.cete.domain.user.UserDetailImpl, boolean, java.lang.String, int, int, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentInformationRecord> getStudentInformationRecords(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String orderByClause, Integer offset,
    		Integer limitCount,	Map<String,String> studentInformationRecordCriteriaMap, Long educatorId) {
		
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		//TODO sort type	    
	    List<StudentInformationRecord> studentInformationRecords = studentDao.getStudentInformationRecords(
	    		attendanceSchoolIds,
				userOrganizationIds,
				orgChildrenById,
				educatorId,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				orderByClause, offset,
				limitCount, studentInformationRecordCriteriaMap, currentSchoolYear);
	    return studentInformationRecords;
    
	}
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15234 : Data extract - Enrollment
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getViewStudentInformationRecordsExtract(Long orgChildrenById,
    		boolean isTeacher, Long educatorId, Integer currentSchoolYear,List<Long> assessmentPrograms) {
  
	    List<ViewStudentDTO> studentInformationRecords = studentDao.getViewStudentInformationRecordsExtract(
				orgChildrenById,
				isTeacher,
				educatorId,currentSchoolYear,assessmentPrograms);
	    return studentInformationRecords;
    
	}
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15460 : View Student - new detail overlay
	 * Get student details including enrollment end roster information by given student id
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getStudentDetailsByStudentId(Map<String,Long> studentInformationRecordCriteriaMap, boolean isTeacher, int currentSchoolYear) {

		List<ViewStudentDTO> studentInformationRecords = studentDao.getStudentDetailsByStudentId(studentInformationRecordCriteriaMap, isTeacher, currentSchoolYear);
	    return studentInformationRecords;
    
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getInactiveStudentDetailsByStudentId(Map<String,Long> studentInformationRecordCriteriaMap, boolean isTeacher, int currentSchoolYear) {

		List<ViewStudentDTO> studentInformationRecords = studentDao.getInactiveStudentDetailsByStudentId(studentInformationRecordCriteriaMap, isTeacher, currentSchoolYear);
	    return studentInformationRecords;
    
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.StudentService#getStudentInformationRecords(java.util.List, java.util.List, 
	 * edu.ku.cete.domain.user.UserDetailImpl, boolean, java.lang.String, int, int, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getViewStudentInformationRecords(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentInformationRecordCriteriaMap, 
    		boolean isTeacher, Long educatorId) {

		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		//TODO sort type	 
		List<ViewStudentDTO> studentInformationRecords = studentDao.getViewStudentInformationRecords(
	    		attendanceSchoolIds,
				userOrganizationIds,
				orgChildrenById,
				isTeacher,
				educatorId,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()), 
						sortByColumn, sortType, offset, 
						limitCount, studentInformationRecordCriteriaMap, currentSchoolYear,
						userDetails.getUser().getCurrentAssessmentProgramId());
	    return studentInformationRecords;
    
	}
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getViewStudentInformationRecordsForDLMTestlets(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentInformationRecordCriteriaMap, 
    		boolean isTeacher, Long educatorId) {

		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		//TODO sort type	 
		List<ViewStudentDTO> studentInformationRecords = studentDao.getViewStudentInformationRecordsForDLM(
	    		attendanceSchoolIds,
				userOrganizationIds,
				orgChildrenById,
				isTeacher,
				educatorId,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()), 
						sortByColumn, sortType, offset, 
						limitCount, studentInformationRecordCriteriaMap, currentSchoolYear,
						userDetails.getUser().getCurrentAssessmentProgramId());
	    return studentInformationRecords;
    
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getViewStudentInformationRecordsInLcs(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentInformationRecordCriteriaMap, 
    		boolean isTeacher, Long educatorId) {

		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		//TODO sort type	 
		List<ViewStudentDTO> studentInformationRecords = studentDao.getViewStudentInformationRecordsInLcs(
	    		attendanceSchoolIds,
				userOrganizationIds,
				orgChildrenById,
				isTeacher,
				educatorId,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()), 
						sortByColumn, sortType, offset, 
						limitCount, studentInformationRecordCriteriaMap, currentSchoolYear,
						userDetails.getUser().getCurrentAssessmentProgramId());
	    return studentInformationRecords;
    
	}
	
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getCreateTestRecordStudentsGridData(Long orgId,
			Long assessmentProgramId, UserDetailImpl userDetails, String sortByColumn, String sortType, Integer offset, Integer limitCount, Map<String, String> studentInformationRecordCriteriaMap) {
		
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		//TODO sort type	 
		List<ViewStudentDTO> studentInformationRecords = studentDao.getCreateTestRecordStudentsGridData(
				orgId,sortByColumn, sortType, offset, 
						limitCount, studentInformationRecordCriteriaMap, currentSchoolYear,assessmentProgramId);
	    return studentInformationRecords;
    
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> getViewStudentInformationRecordsForFirstLanguageCategoryCodeName(){
		Map<String, Object> studentInfo = new HashMap<String, Object>();
		ArrayList<Category> firstLanguageList  = studentDao.getViewStudentInformationRecordsForFirstLanguage(); 
		studentInfo.put("firstLanguageList",firstLanguageList);
		/*
		  	Category category = new Category();
        	category.getCategoryName();
        	category.getCategoryCode();
        */
			ArrayList<Category> comprehensiveRaceList = studentDao.getViewStudentInformationRecordsForComprehensiveRace();
			studentInfo.put("comprehensiveRaceList", comprehensiveRaceList);
		return studentInfo;
	}
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.StudentService#getStudentInformationRecordsCount(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, boolean, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getStudentInformationRecordsCount(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		Map<String,String> studentInformationRecordCriteriaMap, Long educatorId) {
		Integer totalCount = null;
		
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		
		//Code change for defect fix - DE5559 Name: Production Unable to Sort Student Records 

		//if(userOrganizationIds.size() < simplifiedLimitOrgSize) {
			totalCount = studentDao.getStudentInformationRecordsCount(
					attendanceSchoolIds,
					userOrganizationIds, orgChildrenById,
					educatorId,
					permissionUtil.hasPermission(userDetails.getAuthorities(),
							RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
							studentInformationRecordCriteriaMap, currentSchoolYear);
		/*} else {
			//If too many Orgs then use this simplified query to get count by limiting the
			  // records to 10000.
			totalCount = studentDao.getSimplifiedStudentInformationRecordsCount(
					attendanceSchoolIds,
					userOrganizationIds,
					userDetails.getUserId(),
					permissionUtil.hasPermission(userDetails.getAuthorities(),
							RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
							studentInformationRecordCriteriaMap, simplifiedLimit);
		}*/
	    return totalCount;
    
	}

	/* (non-Javadoc)
	 * @see edu.ku.cete.service.StudentService#getStudentInformationRecordsCount(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, boolean, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getViewStudentInformationRecordsCount(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		Map<String,String> studentInformationRecordCriteriaMap, boolean isTeacher, Long educatorId) {
		Integer totalCount = null;
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		totalCount = studentDao.getViewStudentInformationRecordsCount(
					attendanceSchoolIds,
					userOrganizationIds, orgChildrenById,
					isTeacher, educatorId,
					permissionUtil.hasPermission(userDetails.getAuthorities(),
							RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
							studentInformationRecordCriteriaMap, currentSchoolYear, userDetails.getUser().getCurrentAssessmentProgramId());
	    return totalCount;
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getCreateTestRecordStudentsGridDataCount(Long orgId,
			Long assessmentProgramId, UserDetailImpl userDetails,
			Map<String, String> studentInformationRecordCriteriaMap){
		
		Integer totalCount = null;
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		totalCount = studentDao.getCreateTestRecordStudentsGridDataCount(
					orgId,assessmentProgramId,currentSchoolYear,
		         		studentInformationRecordCriteriaMap);
	    return totalCount;

		
	}

	
	@Override
	public List<StudentRosterITIRecord> getStudentInformationRecordsForDLM(
			List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			UserDetailImpl userDetails, boolean hasViewAllPermission,
			String orderByClause, Integer offset, Integer limitCount,
			Map<String, String> studentInformationRecordCriteriaMap) {
		if(CollectionUtils.isEmpty(attendanceSchoolIds) || CollectionUtils.isEmpty(attendanceSchoolIds)) {
			return new ArrayList<StudentRosterITIRecord>();
		}
		//TODO sort type	    
	    List<StudentRosterITIRecord> studentInformationRecords = studentDao.getStudentInformationRecordsForDLM(
	    		attendanceSchoolIds,
				userOrganizationIds,
				userDetails.getUserId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				orderByClause, offset,
				limitCount, studentInformationRecordCriteriaMap);
	    return studentInformationRecords;
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.StudentService#getStudentInformationRecordsCount(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, boolean, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getStudentInformationRecordsCountForDLM(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		Map<String,String> studentInformationRecordCriteriaMap) {
		if(CollectionUtils.isEmpty(attendanceSchoolIds) || CollectionUtils.isEmpty(attendanceSchoolIds)) {
			return 0;
		}
		
		Integer totalCount = null;
		
		if(userOrganizationIds.size() < simplifiedLimitOrgSize) {
			totalCount = studentDao.getStudentInformationRecordsCountForDLM(
					attendanceSchoolIds,
					userOrganizationIds,
					userDetails.getUserId(),
					permissionUtil.hasPermission(userDetails.getAuthorities(),
							RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
							studentInformationRecordCriteriaMap);
		} else {
			//If too many Orgs then use this simplified query to get count by limiting the
			  // records to 10000.
			totalCount = studentDao.getSimplifiedStudentInformationRecordsCountForDLM(
					attendanceSchoolIds,
					userOrganizationIds,
					userDetails.getUserId(),
					permissionUtil.hasPermission(userDetails.getAuthorities(),
							RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
							studentInformationRecordCriteriaMap, simplifiedLimit);
		}
	    return totalCount;
    
	}
	
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Student validateIfStudentExists(Student toSaveorUpdate) {
    	
    	toSaveorUpdate = getBySsidAndState(toSaveorUpdate, toSaveorUpdate.getStateId());
    	if(!toSaveorUpdate.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
    		toSaveorUpdate.addInvalidField(
    				FieldName.STUDENT + ParsingConstants.BLANK,
    				ParsingConstants.BLANK, true,
    				InvalidTypes.NOT_FOUND);
    	}

        return toSaveorUpdate;
    }
    
    /**
     * @param toSaveorUpdate {@link Student}
     * @param attendanceSchoolIds {@link List}
     * @return {@link List}
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private Student getBySsidAndState(Student toSaveorUpdate,Long attendanceSchoolId) {
        //Student is not fully updated if found.
        //less information.
    	boolean validInput = true;
    	if(toSaveorUpdate == null) {
    		validInput = false;
    	}
        if (validInput) {
        	List<Student> students = studentDao.getBySsidAndState(toSaveorUpdate
							.getStateStudentIdentifier(), toSaveorUpdate.getStateId());
        	if(students == null || CollectionUtils.isEmpty(students)) {
            	LOGGER.warn("No Students found");
            } else {
				populateStudentToUpdate(toSaveorUpdate, students.get(0));
	            toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_FOUND);
	        }
		}
		
		//Commented code to remove the validation on statestudentidentifier to fix the defect - 
			//DE5556 EP: Production - Uploading rosters sometimes produces an invalid error message
		/*else if (students.size() == 1) {
            //toSaveorUpdate = students.get(0);
        	populateStudentToUpdate(toSaveorUpdate, students.get(0));
            toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_FOUND);
        } else if (students.size() > 1) {
        	LOGGER.error("Multiple students for "+toSaveorUpdate.getStateStudentIdentifier());
        	toSaveorUpdate.addInvalidField(
        			ParsingConstants.BLANK + FieldName.STUDENT_STATE_ID,
        			ParsingConstants.BLANK + toSaveorUpdate.getStateStudentIdentifier(),
        			true, InvalidTypes.MULTIPLE_FOUND);
        }*/
        
        
        return toSaveorUpdate;
    	
    }
    
    // purely so we can get a null into that column selectively
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateNullableFieldsForEditStudent(Student student) {
    	return studentDao.updateNullableFieldsForEditStudent(student);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String editStudent(Long studentId, Student newStudent, Long currentSchoolYear) throws JsonProcessingException {
		StudentExample example = new StudentExample();
		example.createCriteria().andIdEqualTo(studentId);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Student oldStudent = findById(studentId);
		
		if(!isStateStudentIdDuplicate(newStudent.getStateStudentIdentifier(), oldStudent.getStateId(), studentId)) {

			if(!StringUtil.compareLikeIgnoreCase(oldStudent.getLegalFirstName(),newStudent.getLegalFirstName())||
		    			!StringUtil.compareLikeIgnoreCase(oldStudent.getLegalLastName(),newStudent.getLegalLastName()))
			{
				newStudent.setUsername(studentUtil.generateUsername(newStudent));
				oldStudent.setUsername(newStudent.getUsername());
			}
			oldStudent.setLegalFirstName(newStudent.getLegalFirstName());
			oldStudent.setLegalMiddleName(newStudent.getLegalMiddleName());
			oldStudent.setLegalLastName(newStudent.getLegalLastName());
			oldStudent.setGenerationCode(newStudent.getGenerationCode());
			oldStudent.setDateOfBirth(newStudent.getDateOfBirth());
			oldStudent.setStateStudentIdentifier(newStudent.getStateStudentIdentifier());
			oldStudent.setGender(newStudent.getGender());
			oldStudent.setComprehensiveRace(newStudent.getComprehensiveRace());
			oldStudent.setHispanicEthnicity(newStudent.getHispanicEthnicity());
			oldStudent.setFirstLanguage(newStudent.getFirstLanguage());
			oldStudent.setPrimaryDisabilityCode(newStudent.getPrimaryDisabilityCode());
			oldStudent.setAssessmentProgramId(newStudent.getAssessmentProgramId());
			oldStudent.setEsolParticipationCode(newStudent.getEsolParticipationCode());
			
			
			
			if(newStudent.getEsolProgramEntryDate() != null) {
				oldStudent.setEsolProgramEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(sdf.format(newStudent.getEsolProgramEntryDate()), "US/Central",  "MM/dd/yyyy")
						.getTime()));
			} else {
				oldStudent.setEsolProgramEntryDate(newStudent.getEsolProgramEntryDate());
			}
			if(newStudent.getUsaEntryDate() != null) {
				oldStudent.setUsaEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(sdf.format(newStudent.getUsaEntryDate()), "US/Central",  "MM/dd/yyyy")
						.getTime()));
			} else {
				oldStudent.setUsaEntryDate(newStudent.getUsaEntryDate());
			}
			oldStudent.setModifiedDate(new Date());
			oldStudent.setModifiedUser(newStudent.getModifiedUser());
			
            StudentJson studentJson = studentDao.getStudentjsonData(oldStudent.getId());
			
			if(studentJson != null){
		      oldStudent.setBeforeJsonString(studentJson.buildjsonString());
		    }
			
			updateNullableFieldsForEditStudent(oldStudent);
			
			//updateByExampleSelective(oldStudent, example);
			studentDao.updateOnEditOrActivate(oldStudent);

			
			
			//Commented During US17559 - To avoid hard delete from student assessment program.
			
			/*studentAssessmentProgramsDao.deleteAssessmentPrograms(studentId);
			studentAssessmentProgramsDao.insertMultipleAssessmentProgram(newStudent.getStudentAssessmentPrograms(),studentId);*/
			
			
			
			//Added During US17559 - To avoid hard delete from student assessment program.			
			List<StudentAssessmentProgram> studentAssessmentPrograms = studentAssessmentProgramsDao.getByStudentId(studentId);
			
			for (StudentAssessmentProgram studentAssessmentProgram : studentAssessmentPrograms) {
				
				if(!ArrayUtils.contains(newStudent.getStudentAssessmentPrograms(),studentAssessmentProgram.getAssessmentProgramId()) && studentAssessmentProgram.getActiveFlag()){
					//setting flag to false if programs is unselected.
					studentAssessmentProgram.setActiveFlag(false);
					studentAssessmentProgramsDao.updateByPrimaryKey(studentAssessmentProgram);
				}
			}
			
			studentProfileService.removeNonAssociatedPNPSettings(studentId, Arrays.asList(newStudent.getStudentAssessmentPrograms()), newStudent.getModifiedUser(), oldStudent.getStateId());
			
			for (StudentAssessmentProgram studentAssessmentProgram : studentAssessmentPrograms) {
				for (Long studentAssessmentProgramId : newStudent.getStudentAssessmentPrograms()) {
					
					if(studentAssessmentProgramId == studentAssessmentProgram.getAssessmentProgramId()){
						
						if(!studentAssessmentProgram.getActiveFlag()){
							//Instead of adding duplicate record,set flag to true if already assessmentprogram is mapped to the student.
							studentAssessmentProgram.setActiveFlag(true);
							studentAssessmentProgramsDao.updateByPrimaryKey(studentAssessmentProgram);
						}
						newStudent.setStudentAssessmentPrograms((Long[])ArrayUtils.removeElement(newStudent.getStudentAssessmentPrograms(),studentAssessmentProgramId));
					}
				}
			}
			
			if(newStudent.getStudentAssessmentPrograms().length>0){
			   studentAssessmentProgramsDao.insertMultipleAssessmentProgram(newStudent.getStudentAssessmentPrograms(),studentId);
			}
			
           
			updateByExampleSelective(oldStudent, example);
			
            studentJson = studentDao.getStudentjsonData(oldStudent.getId());
			
			if(studentJson != null){
		      oldStudent.setAfterJsonString(studentJson.buildjsonString());
		    }
			List<Enrollment> enrollments = enrollmentDao.findEnrollmentForStudentEdit(studentId, currentSchoolYear);
			if (!enrollments.isEmpty()) {
				Enrollment enrollment = enrollments.get(0);
				Long oldGradeLevel =  enrollment.getCurrentGradeLevel();
				boolean gradeChanged = !newStudent.getCurrentGradeLevelEnrollment().equals(oldGradeLevel);
				
				//US19204 - update the grade level if the student is in only DLM		
				if (gradeChanged && studentAssessmentProgramsDao.isStudentOnlyInThisAssessmentProgram(studentId, DLM)) {
					Long attendanceSchoolId = enrollment.getAttendanceSchoolId();
					Long newGradeLevel = newStudent.getCurrentGradeLevelEnrollment();
					
					boolean doReactivateAndDeactivate = true;
					
					Organization state = organizationDao.get(oldStudent.getStateId());
					if ("NJ".equals(state.getDisplayIdentifier())) {
						// New Jersey has some special rules for 11th and 12th grade...as they both test with 11th grade content
						GradeCourse oldGc = gradeCourseService.selectByPrimaryKey(oldGradeLevel);
						GradeCourse newGc = gradeCourseService.selectByPrimaryKey(newGradeLevel);
						
						// 11th -> 12th grade is the only concern here, because 12th to 11th would still leave all of the tests active,
						// as the kid's new grade would match the content's grade
						if ("11".equals(oldGc.getAbbreviatedName()) && "12".equals(newGc.getAbbreviatedName())) {
							doReactivateAndDeactivate = false;
						}
					}
					
					//lookup iti plans, test sessions, student tracker for new grade
					Long modifiedUserId = newStudent.getModifiedUser();
					if (doReactivateAndDeactivate) {
						reactivateTestsForStudent(gradeChangeInactivatedPrefix, studentId, currentSchoolYear.intValue(), attendanceSchoolId, newGradeLevel, modifiedUserId);
						deactivateTestsForStudentGradeChange(studentId, currentSchoolYear.intValue(), attendanceSchoolId, oldGradeLevel, newGradeLevel, modifiedUserId);
					}

					enrollmentDao.updateOnStudentEdit(studentId, newGradeLevel, currentSchoolYear.intValue(), newStudent.getModifiedUser());
					try {
						oldStudent.setBeforeJsonString(new JSONObject(oldStudent.getBeforeJsonString()).put("enrollment.currentGradeLevelId", oldGradeLevel).toString());
						oldStudent.setAfterJsonString(new JSONObject(oldStudent.getAfterJsonString()).put("enrollment.currentGradeLevelId", newGradeLevel).toString());
					} catch (JSONException e) {/*ignore*/}					
				}
				
				if(newStudent.getStateEntryDateEnrollment() != null) {
					newStudent.setStateEntryDateEnrollment(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(sdf.format(newStudent.getStateEntryDateEnrollment()), "US/Central",  "MM/dd/yyyy")
							.getTime()));
				} 
				
				if(newStudent.getDistrictEntryDateEnrollment() != null) {
					newStudent.setDistrictEntryDateEnrollment(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(sdf.format(newStudent.getDistrictEntryDateEnrollment()), "US/Central",  "MM/dd/yyyy")
							.getTime()));
				} 
				
				if(newStudent.getSchoolEntryDateEnrollment() != null) {
					newStudent.setSchoolEntryDateEnrollment(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(sdf.format(newStudent.getSchoolEntryDateEnrollment()), "US/Central",  "MM/dd/yyyy")
							.getTime()));
				} 
				   
				enrollmentDao.updateOnStudentEnrollmentEdit(studentId, newStudent.getLocalStudentIdentifier(), newStudent.getStateEntryDateEnrollment(), newStudent.getDistrictEntryDateEnrollment(), newStudent.getSchoolEntryDateEnrollment(),  newStudent.getGiftedStudentEnrollment(), currentSchoolYear.intValue(), newStudent.getModifiedUser());
				
			}

			insertIntoDomainAuditHistory(studentId, oldStudent.getModifiedUser(), EventTypeEnum.UPDATE.getCode(), SourceTypeEnum.MANUAL.getCode(), oldStudent.getBeforeJsonString(), oldStudent.getAfterJsonString());
		} else {
			return "duplicate";
		}
		return "success";
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean reactivateTestsForStudent(String inactivationType, Long studentId, Integer currentSchoolYear, Long attendanceSchoolId, Long newGradeLevel, Long modifiedUserId) {
		boolean reactivationOccurred = false;
		if (studentAssessmentProgramsDao.isStudentOnlyInThisAssessmentProgram(studentId, DLM)) {
			List<Long> inactivatedTestSessionIds = testSessionService.findTestSessionsInactivatedBy(inactivationType, studentId, currentSchoolYear.intValue(), attendanceSchoolId, newGradeLevel);
			if (inactivatedTestSessionIds != null && !inactivatedTestSessionIds.isEmpty()) {
				List<Long> inactivatedStudentsTestIds = studentsTestsService.findIdsByTestSession(inactivatedTestSessionIds);
				List<Long> inactivatedStudentsTestSectionIds = studentsTestsService.findStudentTestSectionsIdsByStudentsTests(inactivatedStudentsTestIds);
				List<Long> inactivatedStudentTrackerBandIds = studentTrackerService.findStudentTrackerBandsByStudentAndTestSession(studentId, inactivatedTestSessionIds, false);
				List<Long> inactivatedITIPlanIds = itiService.findInactivatedITIPlansByStudentAndTestSession(inactivationType, studentId, inactivatedTestSessionIds);
				//reactivate iti plans, test sessions, student tracker for new grade if exists
				if (inactivatedStudentsTestSectionIds != null && !inactivatedStudentsTestSectionIds.isEmpty()) {
					studentsTestsService.reactivateSectionsForGradeChange(inactivatedStudentsTestSectionIds, modifiedUserId);
					reactivationOccurred = true;
				}
				if (inactivatedStudentsTestIds != null && !inactivatedStudentsTestIds.isEmpty()) {
					studentsTestsService.reactivateForGradeChange(inactivatedStudentsTestIds, modifiedUserId);
					reactivationOccurred = true;
				}
				if (inactivatedTestSessionIds != null && !inactivatedTestSessionIds.isEmpty()) {
					testSessionService.reactivateForGradeChange(inactivatedTestSessionIds, modifiedUserId);
					reactivationOccurred = true;
				}
				if (inactivatedStudentTrackerBandIds != null && !inactivatedStudentTrackerBandIds.isEmpty()) {
					studentTrackerService.reactivate(inactivatedStudentTrackerBandIds, modifiedUserId);
					List<StudentTrackerBand> studentTrackerBands = studentTrackerService.getStudentTrackerBandsByIds(inactivatedStudentTrackerBandIds);
					for (StudentTrackerBand band : studentTrackerBands) {
						//delete from studenttrackerblueprint
						studentTrackerService.deleteCompletedStudentTrackerBlueprintStatus(band.getStudentTrackerId(), band.getOperationalWindowId());
					}
					reactivationOccurred = true;
				}
				if (inactivatedITIPlanIds != null && !inactivatedITIPlanIds.isEmpty()) {
					itiService.reactivateForGradeChange(inactivatedITIPlanIds, modifiedUserId);
					reactivationOccurred = true;
				}
			}
			List<Long> inactivatedPendingITIPlanIds = itiService.findInactivatedPendingITIPlansByStudent(inactivationType, studentId, newGradeLevel);
			if (inactivatedPendingITIPlanIds != null && !inactivatedPendingITIPlanIds.isEmpty()) {
				itiService.reactivateForGradeChange(inactivatedPendingITIPlanIds, modifiedUserId);
				reactivationOccurred = true;
			}
		}
		return reactivationOccurred;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public boolean deactivateTestsForStudentGradeChange(Long studentId, Integer currentSchoolYear, Long attendanceSchoolId, Long oldGradeLevel, Long newGradeLevel, Long modifiedUserId) {
		boolean deactivationOccurred = false;
		if (studentAssessmentProgramsDao.isStudentOnlyInThisAssessmentProgram(studentId, DLM)) {
			List<Long> testSessionIdsForDeactivation = testSessionService.findTestSessionsToDeactivateForGradeChange(studentId, currentSchoolYear.intValue(), attendanceSchoolId, oldGradeLevel, newGradeLevel);
			if (testSessionIdsForDeactivation != null && !testSessionIdsForDeactivation.isEmpty()) {
				List<Long> studentsTestIdsForDeactivation = studentsTestsService.findIdsByTestSessionForDeactivation(testSessionIdsForDeactivation);
				List<Long> studentsTestSectionIdsForDeactivation = studentsTestsService.findStudentTestSectionsIdsByStudentsTestsForDeactivation(studentsTestIdsForDeactivation);
				List<Long> studentTrackerBandIdsForDeactivation = studentTrackerService.findStudentTrackerBandsByStudentAndTestSession(studentId, testSessionIdsForDeactivation, true);
				List<Long> itiPlanIdsForDeactivation = itiService.findITIPlansByStudentAndTestSessionForDeactivation(studentId, testSessionIdsForDeactivation);
				//inactivate iti plans, test sessions, student tracker for old grade
				if (studentsTestSectionIdsForDeactivation != null && !studentsTestSectionIdsForDeactivation.isEmpty()) {
					studentsTestsService.deactivateSectionsForGradeChange(studentsTestSectionIdsForDeactivation, modifiedUserId);
					deactivationOccurred = true;
				}
				if (studentsTestIdsForDeactivation != null && !studentsTestIdsForDeactivation.isEmpty()) {
					studentsTestsService.deactivateForGradeChange(studentsTestIdsForDeactivation, modifiedUserId);
					deactivationOccurred = true;
				}
				if (testSessionIdsForDeactivation != null && !testSessionIdsForDeactivation.isEmpty()) {
					//need to add a check to see if other students tests are associated beside this student
					//if none, then deactivate
					testSessionService.deactivateForGradeChange(testSessionIdsForDeactivation, modifiedUserId);
					deactivationOccurred = true;
				}
				if (studentTrackerBandIdsForDeactivation != null && !studentTrackerBandIdsForDeactivation.isEmpty()) {
					studentTrackerService.deactivate(studentTrackerBandIdsForDeactivation, modifiedUserId);
					List<StudentTrackerBand> studentTrackerBands = studentTrackerService.getStudentTrackerBandsByIds(studentTrackerBandIdsForDeactivation);
					Set<Long> studentTrackerIds = new HashSet<Long>();
					for (StudentTrackerBand band : studentTrackerBands) {
						studentTrackerIds.add(band.getStudentTrackerId());
						//delete from studenttrackerblueprint
						studentTrackerService.deleteCompletedStudentTrackerBlueprintStatus(band.getStudentTrackerId(), band.getOperationalWindowId());
					}
					studentTrackerService.changeStatusToUntrackedByIds(studentTrackerIds, modifiedUserId);
					deactivationOccurred = true;
				}
				if (itiPlanIdsForDeactivation != null && !itiPlanIdsForDeactivation.isEmpty()) {
					itiService.deactivateForGradeChange(itiPlanIdsForDeactivation, modifiedUserId);
					deactivationOccurred = true;
				}
			}
			List<Long> itiPendingPlanIdsForDeactivation = itiService.findPendingITIPlansByStudentForDeactivation(studentId, oldGradeLevel);
			if (itiPendingPlanIdsForDeactivation != null && !itiPendingPlanIdsForDeactivation.isEmpty()) {
				itiService.deactivateForGradeChange(itiPendingPlanIdsForDeactivation, modifiedUserId);
				deactivationOccurred = true;
			}
		}
		return deactivationOccurred;
	}
	
	/**
	 * @author bmohanty_sta
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get list of student ids by passing a list of state student identifiers.
	 */
	public List<String> getStudentIdsByStateIdentifiers(List<String> studentStateIds){
		return studentDao.getStudentIdsByStateIdentifiers(studentStateIds);
	}
	
	private boolean isStateStudentIdDuplicate(String stateStudentIdentifier, Long stateId, Long studentId) {
		Student student = studentDao.getStudentByStateStuIdAndStateId(stateStudentIdentifier, stateId);
		if(student != null && student.getId() != null) {
			if(student.getId().equals(studentId)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isStateStuIdExits(String stateStudentIdentifier, Long stateId) {
		Student student = studentDao.getStudentByStateStuIdAndStateId(stateStudentIdentifier, stateId);
		if(student != null && student.getId() != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Long> getNonDLMStudentIdsEnrolledInOrg(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId,int currentSchoolYear, int offset, int limit, List<Long> assessmentPrograms) {
		return studentDao.getNonDLMStudentIdsInOrg(orgId, onlyRostered, teacherId, currentSchoolYear,offset,limit, assessmentPrograms);
	}
	
	@Override
	public List<Long> getDLMStudentIdsEnrolledInOrg(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId, int currentSchoolYear, int offset, int limit) {
		return studentDao.getDLMStudentIdsInOrg(orgId, onlyRostered, teacherId, currentSchoolYear,offset,limit);
	}

	@Override
	public List<Long> getActiveStudentIdsWithPNPEnrolledInOrg(
			UserDetailImpl userDetails, Long orgId, boolean onlyRostered,
			Long teacherId, int limit, int offset, int currentSchoolYear, List<Long> assessmentProgramIds) {
		return studentDao.getActiveStudentIdsWithPNPInOrg(orgId, onlyRostered, teacherId, currentSchoolYear, limit, offset, assessmentProgramIds);
	}
	
	@Override
	public List<AssessmentProgram> getStudentAssessmentProgram(Long studentId) {
		return assessmentProgramDao.getAllAssessmentProgramByStudentId(studentId);
	}
	
	@Override
	public List<ViewStudentDTO> getClearTestRecordStudentsGridData(Long orgId,
			String testTypeCode, String subjectCode, Long assessmentProgramId,
			UserDetailImpl userDetails, String sortByColumn, String sortType,
			int offset, int limitCount,
			Map<String, String> studentInformationRecordCriteriaMap) {
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
			
		Long subjectAreaId = getSubjectAreaId(subjectCode);
		List<ViewStudentDTO> studentInformationRecords = studentDao.getClearTestRecordStudentsGridData(
				orgId,sortByColumn, sortType, offset, 
						limitCount, studentInformationRecordCriteriaMap, currentSchoolYear,assessmentProgramId,testTypeCode,subjectAreaId);
	    return studentInformationRecords;
	
	}

	private Long getSubjectAreaId(String subjectCode) {
		Long subjectAreaId =null;
		
		List<SubjectArea> subjectAreas = enrollmentTestTypeSubjectAreaDao.selectAllSubjectAreas();
		Map<String, SubjectArea> subjectAreaMap = new HashMap<String, SubjectArea>();
		
		for(SubjectArea subjectArea : subjectAreas) {
    		subjectAreaMap.put(subjectArea.getSubjectAreaCode(), subjectArea);
    	}

		if("M".equals(subjectCode) ){
			subjectAreaId = subjectAreaMap.get("D74").getId();
			
		}else if("ELA".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("SELAA").getId();
			
		}else if("GKS".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("GKS").getId();   
			
		}else if("AgF&NR".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("AgF&NR").getId();
			
		}else if("BM&A".equals(subjectCode) ){
			subjectAreaId = subjectAreaMap.get("BM&A").getId();
		}else if( "Arch&Const".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("Arch&Const").getId();
		}else if("Mfg".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("Mfg").getId();
		}else if("Sci".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("SSCIA").getId();
		}else if("SS".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("SHISGOVA").getId();
		}else if("ELP".equals(subjectCode)){
			subjectAreaId = subjectAreaMap.get("D83").getId();
		}
		
		return subjectAreaId;
	}

	@Override
	public int getClearTestRecordStudentsGridDataCount(Long orgId,
			String testTypeCode, String subjectCode, Long assessmentProgramId,
			UserDetailImpl userDetails,
			Map<String, String> studentInformationRecordCriteriaMap) {
		Integer totalCount = null;
		Long subjectAreaId	= getSubjectAreaId(subjectCode);
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		totalCount = studentDao.getClearTestRecordStudentsGridDataCount(
					orgId,assessmentProgramId,currentSchoolYear,testTypeCode,subjectAreaId,
		         		studentInformationRecordCriteriaMap);
	    return totalCount;
	
	}

	@Override
	public List<ViewStudentDTO> getTestRecordByStudentsGridData(
			Long studentId ,Long orgId, String sortByColumn,
			String sortType, int offSet, int limitCount,
			Map<String, String> studentInformationRecordCriteriaMap,Long assessmentProgramId,Integer currentSchoolYear ) {
	
		return studentDao.getTestRecordByStudentsData(studentId,orgId,sortByColumn,sortType,offSet,limitCount,studentInformationRecordCriteriaMap,assessmentProgramId,currentSchoolYear);
	}

	@Override
	public int getTestRecordByStudentsGridDataCount(Long studentId,
			Long orgId, Map<String, String> studentInformationRecordCriteriaMap,Long assessmentProgramId,Integer currentSchoolYear) {

		return studentDao.getTestRecordByStudentsDataGridCount(studentId,orgId,studentInformationRecordCriteriaMap,assessmentProgramId,currentSchoolYear);
	}
	
	@Override
	public boolean isStudentAssociatedWithAssessmentProgram(long studentId, String assessmentProgramAbbreviatedName) {
		return studentAssessmentProgramsDao.isStudentOnlyInThisAssessmentProgram(studentId, assessmentProgramAbbreviatedName);
	}
	//To get school name and district name of the student based on state student identifier
	@Override
	public List<EnrollmentsOrganizationInfo> getOrganizationsByStateStudentId(String stateStudentIdentifier, Long stateId, int currentSchoolYear){
		
		return  studentDao.getOrganizationsByStateStudentId(stateStudentIdentifier, stateId, currentSchoolYear);
	}
	
	@Override
	public List<Student> findStudentsByIds(List<Long> studentIds) {
		return studentDao.findStudentsByIds(studentIds);
	}

	@Override
	public List<ViewStudentDTO> getStudentUserNamePasswordExtract(Long orgChildrenById, boolean isTeacher,
		Long educatorId, Integer currentSchoolYear, String gradeAbbreviatedName, List<Map<Long, Long>> contentAreaAssessment, Long rosterId, List<Integer> assessmentProgramIds) {
		List<ViewStudentDTO> results = new ArrayList<ViewStudentDTO>();

			results.addAll(studentDao.getStudentUserNamePasswordExtract(orgChildrenById, isTeacher, educatorId, 
								currentSchoolYear, gradeAbbreviatedName, contentAreaAssessment, assessmentProgramIds));

		return results; 
	}
	
	/*
	 * Sudhansu :Created for US18184 - For  student json story
	 * */
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertIntoDomainAuditHistory(Long objectId, Long createdUserId,String action, String source,String userBeforUpdate,String userAfterUpdate){
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(source);
		domainAuditHistory.setObjectType("STUDENT");
		domainAuditHistory.setObjectId(objectId);
		domainAuditHistory.setCreatedUserId(createdUserId.intValue() );
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(action);
		domainAuditHistory.setObjectBeforeValues(userBeforUpdate);
		domainAuditHistory.setObjectAfterValues(userAfterUpdate);
		domainAuditHistoryDao.insert(domainAuditHistory);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addToGroupsAuditTrailHistory(DomainAuditHistory domainAuditHistory){
		
		JsonNode before = null;
		JsonNode after = null;
		ObjectMapper beforeObjectMapper = new ObjectMapper();
		ObjectMapper afterObjectMapper=new ObjectMapper();
		JsonNode beforeObjectRoot = beforeObjectMapper.createObjectNode();
		JsonNode afterObjectRoot = afterObjectMapper.createObjectNode();
		boolean isProcessed=false;
		
		ObjectMapper innerMapper = new ObjectMapper();
		try {
			if(domainAuditHistory.getObjectBeforeValues() == null && domainAuditHistory.getObjectAfterValues() == null){
				LOGGER.debug("In-valid entry in Domainaudithistory table"+ domainAuditHistory.getObjectId());
				
			}else if(EventTypeEnum.INSERT.getCode().equals(domainAuditHistory.getAction())){
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());				
				JsonNode root = innerMapper.createObjectNode();
				
				ArrayNode afterArray = (ArrayNode) after.get("studentOrganization");
				
				for (JsonNode jsonNode : afterArray) {
					
					((ObjectNode)root).put("state",jsonNode.get("stateName")==null ?  "": jsonNode.get("stateName").asText());
					((ObjectNode)root).put("District",jsonNode.get("districtName") ==null ?"":jsonNode.get("districtName").asText());
					((ObjectNode)root).put("School",jsonNode.get("attendanceSchoolName")==null ?"":jsonNode.get("attendanceSchoolName").asText());
				}
				
				((ObjectNode)root).set("Assessment Program",after.get("assessmentPrograms"));
				
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(),after.get("statestudentidentifier")==null? "":after.get("statestudentidentifier").asText(),
						after.get("firstName")==null ? "": after.get("firstName").asText(),after.get("lastName")==null ?"":after.get("lastName").asText(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
						           ,EventNameForAudit.STUDENT_ADDED.getCode(),null,innerMapper.writeValueAsString(root)); 
				
			}else if(EventTypeEnum.UPDATE.getCode().equals(domainAuditHistory.getAction())){
				
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				int count = 0;
				for(Iterator<Entry<String, JsonNode>> it = before.fields();it.hasNext();){
					Entry<String, JsonNode> entry = it.next();
					String key = entry.getKey();
					  if(before.get(key) != null && after.get(key)!=null && before.get(key).isArray()){
						     ArrayNode afterArray = (ArrayNode) after.get(key);
							 ArrayNode beforeArray = (ArrayNode) before.get(key);
							 
							 if(beforeArray.size() != afterArray.size()){
								 ((ObjectNode)beforeObjectRoot) .put(key,beforeArray);
								 ((ObjectNode)afterObjectRoot) .put(key, afterArray);
								 count++;
							 }
						  
					  }else if((!StringUtils.isEmpty(before.get(key)==null? null :before.get(key).asText())|| !StringUtils.isEmpty(after.get(key)==null? null:after.get(key).asText()) )&& 
							   !StringUtils.equalsIgnoreCase(before.get(key)==null? null :before.get(key).asText()
									   ,after.get(key)==null? null: after.get(key).asText())){					 
						  ((ObjectNode)beforeObjectRoot) .put(key,before.get(key));
						  ((ObjectNode)afterObjectRoot) .put(key, after.get(key));
						  count++;						
					 }
				}
				if(count > 0){
					insertToAuditTrailHistory(domainAuditHistory.getObjectId(),after.get("statestudentidentifier")==null ? "":after.get("statestudentidentifier").asText(),
							after.get("firstName")==null ? "" :after.get("firstName").asText(),after.get("lastName")==null ? "" :after.get("lastName").asText(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
					           ,EventNameForAudit.STUDENT_UPDATED.getCode(),beforeObjectMapper.writeValueAsString(beforeObjectRoot),afterObjectMapper.writeValueAsString(afterObjectRoot)); 
				}
								
			}else if(EventTypeEnum.EXIT.getCode().equals(domainAuditHistory.getAction())){
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				
				JsonNode root = innerMapper.createObjectNode();
				
				ArrayNode afterArray = (ArrayNode) after.get("studentOrganization");
				
				for (JsonNode jsonNode : afterArray) {
					if(jsonNode.get("attendanceSchoolid")!=null && after.get("exitedSchool") !=null && jsonNode.get("attendanceSchoolid").asLong() == after.get("exitedSchool").asLong()){
						((ObjectNode)root).put("state",jsonNode.get("stateName")==null ? "" : jsonNode.get("stateName").asText());
						((ObjectNode)root).put("District",jsonNode.get("districtName")==null ? "" :jsonNode.get("districtName").asText());
						((ObjectNode)root).put("School",jsonNode.get("attendanceSchoolName")==null ? "" :jsonNode.get("attendanceSchoolName").asText());
					}
				}
				
				((ObjectNode)root).set("Assessment Program",after.get("assessmentPrograms"));
				((ObjectNode)root).put("Exit Reason",after.get("exitReason")==null ? "" :after.get("exitReason").asText());
				
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(),after.get("statestudentidentifier")==null ? "" :after.get("statestudentidentifier").asText(),
						after.get("firstName")==null ? "" :after.get("firstName").asText(),after.get("lastName")==null ? "" :after.get("lastName").asText(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
				           ,EventNameForAudit.STUDENT_EXITED.getCode(),null,innerMapper.writeValueAsString(root));
				
			}else if(EventTypeEnum.TRANSFER_STUDENT.getCode().equals(domainAuditHistory.getAction())){
				
				String destinationSchoolName = null;
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				
                JsonNode rootA = innerMapper.createObjectNode();
                JsonNode rootB = innerMapper.createObjectNode();
        
                ArrayNode beforeArray = (ArrayNode) before.get("studentOrganization");
                
                for (JsonNode jsonNode : beforeArray) {                	
					if( jsonNode.get("attendanceSchoolid") !=null && after.get("exitedSchool")!=null && jsonNode.get("attendanceSchoolid").asLong() == after.get("exitedSchool").asLong()){
						((ObjectNode)rootB).put("District",jsonNode.get("districtName")==null ? "" :jsonNode.get("districtName").asText());
						((ObjectNode)rootB).put("Last Attendance School",jsonNode.get("attendanceSchoolName")==null ? "" :jsonNode.get("attendanceSchoolName").asText());
						((ObjectNode)rootB).put("Last AYP School",jsonNode.get("aypSchoolName")==null ? "" :jsonNode.get("aypSchoolName").asText());
						((ObjectNode)rootB).put("Last Local ID",jsonNode.get("localId") == null ?"":jsonNode.get("localId").asText());
					}
				}
                
				ArrayNode afterArray = (ArrayNode) after.get("studentOrganization");
				for (JsonNode jsonNode : afterArray) {
					
					if(jsonNode.get("attendanceSchoolid") !=null && after.get("destinationAttendanceSchool") !=null && jsonNode.get("attendanceSchoolid").asLong() == after.get("destinationAttendanceSchool").asLong()){
						destinationSchoolName = jsonNode.get("attendanceSchoolName")==null ? "" :jsonNode.get("attendanceSchoolName").asText();
					}
				}
				if("".equals(after.get("destinationDistrict")==null ? "" :after.get("destinationDistrict").asText())){
				   ((ObjectNode)rootA).put("District",rootB.get("District")==null ? "" :rootB.get("District").asText());
				}else{
					((ObjectNode)rootA).put("District",after.get("destinationDistrict")==null ? "":after.get("destinationDistrict").asText());
				}
				((ObjectNode)rootA).put("Destination Attendance School",destinationSchoolName);
				((ObjectNode)rootA).put("Destination AYP School",after.get("destinationAypSchool")==null ? "":after.get("destinationAypSchool").asText());
				((ObjectNode)rootA).put("Destination Local ID",after.get("destinationLocalId") == null ?"":after.get("destinationLocalId").asText());
				((ObjectNode)rootA).put("Exit Reason",after.get("exitReason")==null ? "":after.get("exitReason").asText());
				((ObjectNode)rootA).put("Exit Date",after.get("exitDate").asText());
				
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(),after.get("statestudentidentifier")==null ? "" :after.get("statestudentidentifier").asText(),
						 after.get("firstName")==null ? "" : after.get("firstName").asText(),after.get("lastName")==null ? "" :after.get("lastName").asText(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
				           ,EventNameForAudit.STUDENT_TRANSFERED.getCode(),innerMapper.writeValueAsString(rootB),innerMapper.writeValueAsString(rootA));
			}
			isProcessed=true;
		
		} catch (JsonProcessingException e) {	
			// TODO Auto-generated catch block
			LOGGER.error("value inserted in studentaudittrailhistory table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();
			isProcessed=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("value inserted in studentaudittrailhistory table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();		
			isProcessed=false;
		}catch (Exception e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
			isProcessed=false;
		}
		if (isProcessed){
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"COMPLETED");
		}
		else{
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"FAILED");
		}
		return isProcessed;
	}
	
	/*
	 * Sudhansu :Created for US18184 - For  Student json comparision value store in db
	 * */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToAuditTrailHistory(Long objectId,String stateStudentId,String studentFirstName,String studentLastName,
			Long domainAuditHistoryId,Long modifiedUserId,String eventName,String beforeValue,String currentValue){
		User user=userService.get(modifiedUserId);
		StudentAuditTrailHistory studentAuditTrailHistory = new StudentAuditTrailHistory();
		studentAuditTrailHistory.setStudentId(objectId);
		studentAuditTrailHistory.setCreatedDate(new Date());
		studentAuditTrailHistory.setModifiedUser(modifiedUserId);
		studentAuditTrailHistory.setStudentStateId(stateStudentId);
		studentAuditTrailHistory.setStudentFirstName(studentFirstName);
		studentAuditTrailHistory.setStudentLastName(studentLastName);
		studentAuditTrailHistory.setEventName(eventName);
		studentAuditTrailHistory.setBeforeValue(beforeValue);
		studentAuditTrailHistory.setCurrentValue(currentValue);
		studentAuditTrailHistory.setDomainAuditHistoryId(domainAuditHistoryId);
		studentAuditTrailHistory.setModifiedUserName(user==null ? null:user.getUserName());
		studentAuditTrailHistory.setModifiedUserFirstName(user==null ? null:user.getFirstName());
		studentAuditTrailHistory.setModifiedUserLastName(user==null ? null:user.getSurName());
		studentAuditTrailHistory.setModifiedUserEducatorIdentifier(user==null ? null:user.getUniqueCommonIdentifier());

		userAuditTrailHistoryMapperDao.insertStudent(studentAuditTrailHistory);
		
		LOGGER.trace("value inserted in studentaudittrail table ");
	}
	
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean isStudentOnlyInDLM(Long studentId) {
        return studentAssessmentProgramsDao.isStudentOnlyInThisAssessmentProgram(studentId, DLM);
    }

    @Override
	public String checkStudentUsername(String newStudUsername,Long id) {
    	
    	return studentDao.checkStudentUsername(newStudUsername, id);
	}
    
    @Override
	public String checkStudentPassword(Long studentId) {
		
		return studentDao.getPasswordByStudentId(studentId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentPassword(Long studentId, String newStudPassword) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		studentDao.updateStudentPassword(studentId, newStudPassword,userDetails.getUserId(),new Date() );
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentUsername(Long studentId, String newStudUsername) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		studentDao.updateStudentUsername(studentId, newStudUsername,userDetails.getUserId(),new Date());
		
	}
	@Override
	public List<ViewStudentDTO> getViewDuplicateStudentInformationRecords(List<Long> attendanceSchoolIds,
			List<Long> userOrganizationIds, Long orgChildrenById, UserDetailImpl userDetails, boolean hasPermission,
			String sortByColumn, String sortType, Integer offset, Integer limitCount,
			Map<String, String> studentInformationRecordCriteriaMap, boolean isTeacher, Long educatorId) {
		
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		
		List<ViewStudentDTO> studentInformationRecords = studentDao.getViewDuplicateStudentInformationRecords(
	    		attendanceSchoolIds,
				userOrganizationIds,
				orgChildrenById,
				isTeacher,
				educatorId,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()), 
						sortByColumn, sortType, offset, 
						limitCount, studentInformationRecordCriteriaMap, currentSchoolYear,
						userDetails.getUser().getCurrentAssessmentProgramId());
		
		return studentInformationRecords;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void mergeStudents(Long studentToRetain,Long studentToRemove, Long studentIdForPnp, Long studentIdForFcs, Long studentIdForRoster, List<Long> selectedStudentTestIds, List<Long> unSelectedStudentTestIds) {
		
		Long studentPnpRemove,studentFcsRemove,studentRostersRemove;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		if(studentToRetain.equals(studentIdForPnp)){
			studentPnpRemove =studentToRemove;
			studentDao.removeDestPnpSettings(studentPnpRemove, new Date(), userDetails.getUserId());
		}else{
			studentPnpRemove=studentToRetain;
			studentDao.removeSourcePnpSettings(studentIdForPnp,studentPnpRemove, new Date(), userDetails.getUserId());
		}
		
		if(studentToRetain.equals(studentIdForFcs)){
			studentFcsRemove = studentToRemove;
		}else{
			studentFcsRemove = studentToRetain;
			studentDao.removeSourceFcsSettings(studentIdForFcs,studentFcsRemove, new Date(), userDetails.getUserId());
		}
		
		if(studentToRetain.equals(studentIdForRoster)){
			studentRostersRemove = studentToRemove;
			studentDao.removeDestinationRosters(studentRostersRemove, new Date(), userDetails.getUserId());
		}else{
			studentRostersRemove = studentToRetain;
			studentDao.removeSourceRosters(studentIdForRoster,studentRostersRemove, new Date(), userDetails.getUserId());
		}
		
		studentDao.mergeStudents(studentToRetain,studentToRemove, new Date(), userDetails.getUserId(), selectedStudentTestIds, unSelectedStudentTestIds);
	}


    @Override
    //@Transactional
    public final String gets3Credentials(final String filename) throws ServiceException { 
    	return studentsTestsService.gets3Credentials(filename);
    }

	@Override
	public void removeAssessmentProgram(Long organizationId, Long schoolYear,
			Long assessmentProgramId, Long modifiedUserId) {
		studentAssessmentProgramsDao.deactivateByorgIdAndassessmentId(organizationId, schoolYear, assessmentProgramId, modifiedUserId);
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void resetStudentPasswordOnAnnualReset(Long orgId,
			int passwordLength, String[] qcStates) {
		// TODO Auto-generated method stub
	
		Organization affectedState=organizationDao.getOrganizationByIdExcludingGivenDispalyIdentifiers(orgId,qcStates);
		if(affectedState!=null && affectedState.getId()!=null){
			int offset = 0,recordCount=0, limit = 5000;
			 recordCount = studentDao.getStudentCountForPasswordReset(affectedState.getId());
			
		while ( recordCount> 0) {
			
		studentDao.resetStudentPasswordOnAnnualReset(affectedState.getId(),passwordLength,offset,limit);
		offset=offset+limit;
		recordCount=recordCount-limit;
		}
		
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public String generateStudentPassword(){
		return studentUtil.generatePasswordString();
	}
	@Override
	public List<Student> getBySsidAndState(String stateStudentIdentifier, Long stateId){
		return studentDao.getBySsidAndState(stateStudentIdentifier, stateId);
		
	}
	
	@Override
	public boolean isStudentInAssessmentProgram(long studentId, long assessmentProgramId) {
		return studentAssessmentProgramsDao.isStudentInAssessmentProgram(studentId, assessmentProgramId);
	}

	@Override
	public Student getByExternalId(String externalId) {
		return studentDao.getByExternalId(externalId);
	}	

	@Override
	public List<ViewStudentDTO> getStudentUserNamePasswordExtractForDlmAndPltw(Long orgChildrenById, boolean isTeacher,
		Long educatorId, Integer currentSchoolYear, String gradeAbbreviateName, List<Map<Long, Long>> contentAreaAssessmentForDlmAndPltw, Long rosterId, List<Integer> assessmentProgramIds) {
		List<ViewStudentDTO> results = new ArrayList<ViewStudentDTO>();

			results.addAll(studentDao.getStudentUserNamePasswordExtractForDlmAndPltw(orgChildrenById, isTeacher, educatorId, 
								currentSchoolYear, gradeAbbreviateName, contentAreaAssessmentForDlmAndPltw, assessmentProgramIds));

		return results; 
	}
	
	@Override
	public boolean isAddStudentDemographicValueExists(String legalFirstName, String legalLastName, Long gender, Date dobDates, Long stateId) {
		return studentDao.isAddStudentDemographicValueExists(legalFirstName, legalLastName, gender, dobDates, stateId);
	}	
	
	@Override
	public boolean isEditStudentDemographicValueExists(String stateStudentId, String legalFirstName, String legalLastName, Long gender, Date dobDates, Long stateId) {
		return studentDao.isEditStudentDemographicValueExists(stateStudentId, legalFirstName, legalLastName, gender, dobDates, stateId);
	}
	
	@Override
	public Long getStudentStateIdBySchoolId(Long schoolId) {
		return studentDao.getStudentStateIdBySchoolId(schoolId);
	}


	@Override
	public List<StudentExitCodesDTO> getExitCodesByState(Long currentAssessmentProgramId, Long currentOrganizationId,
			Long currentSchoolYear) {
		
		
		Boolean isState = organizationDao.isGivenOrganizationIdIsState(currentOrganizationId);
		
		List<StudentExitCodesDTO> exitCodesByState = studentDao.getExitCodesByState(currentAssessmentProgramId, currentOrganizationId, currentSchoolYear, isState);
		if(!(exitCodesByState.size() > 0)) {
			currentOrganizationId = 0L;
			exitCodesByState = studentDao.getExitCodesByState(currentAssessmentProgramId, currentOrganizationId, currentSchoolYear, isState);
		}
		
		return exitCodesByState;
	}

	@Override
	public List<Integer> getStateSpecificExitCodes(Long stateId, Long assessmentProgramId, Long schoolYear) {		
		
		List<Integer> exitCodesByState = studentDao.getStateOrAssesPgmSpecificExitCodes(stateId, assessmentProgramId, schoolYear);
		if(!(exitCodesByState.size() > 0)) {
			stateId = 0L;
			exitCodesByState = studentDao.getStateOrAssesPgmSpecificExitCodes(stateId, assessmentProgramId, schoolYear);
		}
				
		return exitCodesByState;
	}

	@Override
	public List<Student> getStudentBySchoolIDandGradeIDandSchoolyear(Long schoolId, Long schoolYear, Long[] grades,
			Long assesmentProgramID, Long teacherID) {
		return studentDao.getStudentBySchoolIDandGradeIDandSchoolyear(schoolId,schoolYear, grades, assesmentProgramID, teacherID);
	}

	@Override
	public List<User> getTeacherByStudentIDandSchoolIDandGradeID(Long schoolId, Long schoolYear, Long[] grades,
			Long assesmentProgramID, Long[] studentIDs) {
		return studentDao.getTeacherByStudentIDandSchoolIDandGradeID(schoolId,schoolYear, grades, assesmentProgramID,studentIDs);
	}

	@Override
	public List<IAPStudentTestStatusDTO> iapHomeStudentsTestStatusRecords(Long schoolYear, Long stateID, Long schoolID,
			Long[] educatorIDs, Long[] studentIDs, String stateStudentID, Long[] grades, Long offSet, Long paginationLimit, Long contentareaid,
			Long operationalTestWindowID) {
		return studentDao.iapHomeStudentsTestStatusRecords( schoolYear, stateID, schoolID, educatorIDs,studentIDs,stateStudentID, 
				grades, offSet, paginationLimit,contentareaid, operationalTestWindowID);
	}
	
	@Override
	public List<Integer> getStateSpecificExitCodesForKids(Long stateId, List<Long> assessmentProgramIds, Long schoolYear) {		
		
		List<Integer> exitCodesByState = studentDao.getStateSpecificExitCodesForKids(stateId, assessmentProgramIds, schoolYear);
		if(!(exitCodesByState.size() > 0)) {
			stateId = 0L;
			exitCodesByState = studentDao.getStateSpecificExitCodesForKids(stateId, assessmentProgramIds, schoolYear);
		}
				
		return exitCodesByState;
	}
	
	
	@Override
	public String getStateStudentIdentifierLengthByStateID() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	  	          ).getAuthentication().getPrincipal();
	  	String organizationType = userDetails.getUser().getCurrentOrganizationType();
	  	Long userStateId;
	  	Long isStateStudentIdentifierLength;
	  	if(organizationType.equalsIgnoreCase("CONS")) {
	  	userStateId = null;
	  	}else if(organizationType.equalsIgnoreCase("ST")) {
	  		userStateId = userDetails.getUser().getCurrentOrganizationId();
	  		isStateStudentIdentifierLength = studentDao.countStateStudentIdentifierLength(userStateId);
	  	if(isStateStudentIdentifierLength == 0) {
	  		userStateId =null;
	  	}
	  	}else {
	  	userStateId = userService.getUserStateId(userDetails.getUser().getOrganizationId());
	  	isStateStudentIdentifierLength = studentDao.countStateStudentIdentifierLength(userStateId);
	  	if(isStateStudentIdentifierLength == 0) {
	  		userStateId =null;
	  	}
	  	}
	  	String stateStudentIdentifierLength = studentDao.getStateStudentIdentifierLength(userStateId);
	 	return stateStudentIdentifierLength;
	}

	@Override
	public boolean hasCompletedTestByExtId(String uniqueStudentId) {
		return studentDao.hasCompletedTestByExtId(uniqueStudentId);
	}
	
	@Override
	public Map<String, Object> validateStateStudentId(String studentStateId){
		
		 Map<String,Object> validationDetails = new HashMap();
	        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
	       User user= userDetails.getUser();
		 	Integer currentSchoolYear= user.getContractingOrganization().getCurrentSchoolYear().intValue();
			Long stateId=user.getContractingOrganization().getId();
			Long currentorgId=user.getCurrentOrganizationId();
			Long currentAssessmentProgramId=user.getCurrentAssessmentProgramId();
			String action=new String();
			String errorMessage=new String();
			boolean activeflag=false;
			boolean sameOrganizationflag=false;
			
			boolean permission = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_STUDENTRECORD_MODIFY");
			
			
			List<FindEnrollments> studentEnrollmentsRecords = enrollmentService.findStudentEnrollment(studentStateId, stateId,currentSchoolYear,user.getId(),user.isTeacher());
			if (CollectionUtils.isEmpty(studentEnrollmentsRecords)) {
				// if we don't find any in the regular database, check the data warehouse for legacy data
				LOGGER.debug("Did not find enrollments for SSID " + studentStateId + " in regular database, checking data warehouse...");
				studentEnrollmentsRecords = dataExtractService.findStudentEnrollment(studentStateId, stateId,currentSchoolYear,user.getId(),user.isTeacher());
				LOGGER.debug("Found " + (CollectionUtils.isEmpty(studentEnrollmentsRecords) ? "no " : "") + "enrollment records for SSID " + studentStateId + " in data warehouse");
			}
			
			
			List<FindEnrollments> unRelatedStudentEnrollmentsRecords=new ArrayList<FindEnrollments>();
			boolean sameAssesmentProgram=false;
			
			for(FindEnrollments enrollment:studentEnrollmentsRecords){
	        	if(enrollment.isActive()){
	        		if(enrollment.getSchoolYear().intValue() == currentSchoolYear.intValue()){
	        		  activeflag=true;
	        		}
	        		if(stateId.equals(currentorgId)){
	        			sameOrganizationflag=true;
	        			if(enrollment.getAssessmentPrograms()!=null && !enrollment.getAssessmentPrograms().contains(currentAssessmentProgramId)){
							unRelatedStudentEnrollmentsRecords.add(enrollment);
						}else if(enrollment.getAssessmentPrograms()!=null && enrollment.getAssessmentPrograms().contains(currentAssessmentProgramId)){
							sameAssesmentProgram=true;
						}
	        		}else if(user.getCurrentOrganizationType().equals("DT") && currentorgId.equals(enrollment.getDistrictId()) ){
	        			sameOrganizationflag=true;
	        			if(enrollment.getAssessmentPrograms()!=null && !enrollment.getAssessmentPrograms().contains(currentAssessmentProgramId)){
							unRelatedStudentEnrollmentsRecords.add(enrollment);
						}else{
							sameAssesmentProgram=true;
						}
	        		}else if(user.getCurrentOrganizationType().equals("SCH") && currentorgId.equals(enrollment.getSchoolId())){
	        			sameOrganizationflag=true;
	        			if(enrollment.getAssessmentPrograms()!=null && !enrollment.getAssessmentPrograms().contains(currentAssessmentProgramId)){
							unRelatedStudentEnrollmentsRecords.add(enrollment);
						}else{
							sameAssesmentProgram=true;
						}
	        		}
	        		
	        		if(!sameOrganizationflag && (enrollment.getAssessmentPrograms()!=null && !enrollment.getAssessmentPrograms().contains(currentAssessmentProgramId)) ){
	        			unRelatedStudentEnrollmentsRecords.add(enrollment);
	        		}else if(!sameOrganizationflag && (enrollment.getAssessmentPrograms()!=null && enrollment.getAssessmentPrograms().contains(currentAssessmentProgramId)) ){
	        			sameAssesmentProgram=true;
	        		}
	        	}
	        	
	        }				
			if(studentEnrollmentsRecords.isEmpty()){
	        	 action="NoRecord";
	        	 errorMessage=noRecordErrorMessage;
	         }else if(!activeflag){
	        	 action="Activate";
	        	 errorMessage=activateErrorMessage;
	         }else if(activeflag && sameOrganizationflag && sameAssesmentProgram){
	        	 action="Edit";
	         }else if(activeflag && !sameOrganizationflag && sameAssesmentProgram){
	        	 action="AcessDecline";
	        	 errorMessage=accessDeclineErrorMessage;
	         }else if(activeflag  && sameOrganizationflag && !sameAssesmentProgram ){
	        	 action="NotAssociatedAsp";
	        	 errorMessage=notAssociatedAspErrorMessage;
	         }
	         else if(activeflag  && !sameOrganizationflag && !sameAssesmentProgram ){
	        	 action="NotAssociatedOrg";
	        	 errorMessage=notAssociatedOrgErrorMessage;
	         }
			if((user.isTeacher()||user.isProctor())&&(!action.equalsIgnoreCase("Edit"))){
				
				if(!action.equalsIgnoreCase("NoRecord")){
					action="Unauthorized";
					errorMessage=unAuthorizeErrorMessage;
					
				}
				if(action.equalsIgnoreCase("NoRecord")&&user.isTeacher()){
					List<FindEnrollments> studentEnrollmentsWithOutTeacher=dataExtractService.findStudentEnrollment(studentStateId, stateId,currentSchoolYear,user.getId(),!user.isTeacher());
				if(studentEnrollmentsWithOutTeacher!=null&&studentEnrollmentsWithOutTeacher.size()==0){
					action="NoRecord";
					errorMessage=noRecordErrorMessage;
				}else{
					action="Unauthorized";
					errorMessage=unAuthorizeErrorMessage;
				}
				}
				
			}else if(!activeflag && (!user.isTeacher()||!user.isProctor())&&(!action.equalsIgnoreCase("Edit")&& !action.equalsIgnoreCase("NoRecord")) && !permission){
				action="Unauthorized";
				errorMessage=unAuthorizeErrorMessage;
			}
				validationDetails.put("isEnrollmentActive",activeflag);
				validationDetails.put("Action", action);
				validationDetails.put("errorMessage",errorMessage);				
	         if(!(CollectionUtils.isEmpty(studentEnrollmentsRecords))&&(action.equalsIgnoreCase("Edit")||action.equalsIgnoreCase("Activate"))){
	        	 validationDetails.put("Student", studentEnrollmentsRecords.get(0));
	         }	         
	         return validationDetails;
		
	}
	
	@Override
	public boolean isEnrolledInSameSchoolBefore(String externalId, Long schoolId, int schoolYear) {
		return studentDao.isEnrolledInSameSchoolBefore(externalId,schoolId,schoolYear);
	}
	
}
