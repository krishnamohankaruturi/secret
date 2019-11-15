package edu.ku.cete.batch.questar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.questar.exception.QuestarSkipException;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.report.domain.QuestarRegistrationReason;
import edu.ku.cete.report.domain.QuestarStagingRecord;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.QuestarService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.SubjectAreaService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.web.QuestarStagingDTO;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

public class QuestarRegistrationProcessor implements ItemProcessor<QuestarStagingRecord, Map<String, Object>> {
	
	private final static Log logger = LogFactory.getLog(QuestarRegistrationProcessor.class);
	
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private TestingProgramService testingProgramService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentProfileService studentProfileService;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Autowired
	private SubjectAreaService subjectAreaService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private QuestarService questarService;
	
	private Long batchRegistrationId;
	private Long operationalTestWindowId;
	
	private AssessmentProgram assessmentProgram;
	private Organization org;
	private int schoolYear;
	private Assessment assessment;
	private TestingProgram testingProgram;
	private StepExecution stepExecution;
	
	@Override
	public Map<String, Object> process(QuestarStagingRecord questarStagingRecord) throws Exception {
		Map<String, Object> writerMap = new HashMap<String, Object>();
		
		String studentStateIdentifier = questarStagingRecord.getStudentStateId();
		Long studentId = questarStagingRecord.getStudentKiteNumber();
		
		// look for the student's enrollment record
		Enrollment enrollment = getEnrollment(questarStagingRecord, org.getId(), schoolYear);
		
		if (questarStagingRecord.getWalkIn()) { // there is no StudentKiteNumber (State Student ID) on the record, so we can't compare
			studentId = enrollment.getStudentId();
		} else {
			if (enrollment.getStudentId() != studentId) {
				String msg = "Student State ID " + studentStateIdentifier + "'s personal student ID (" + studentId +
						") did not match the ID on the enrollment record.";
				fail(msg, null, questarStagingRecord);
				throw new QuestarSkipException(msg);
			}
		}
		
		Student student = studentService.findById(studentId);
		if (!student.getLegalLastName().equalsIgnoreCase(questarStagingRecord.getStudentLastName())) {
			String msg = "Student State ID " + student.getStateStudentIdentifier() + "'s last name  from the staging record did not match what was in the system (\"" +
					questarStagingRecord.getStudentLastName() + "\" != \"" + student.getLegalLastName() + "\").";
			fail(msg, student.getId(), questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		// TODO change how this is compared, as we added studentassessmentprogram
		if (student.getAssessmentProgramId() != null) {
			String msg = "Student State ID " + student.getStateStudentIdentifier() + " is a DLM student.";
			fail(msg, student.getId(), questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		SubjectArea subjectArea = getSubjectArea(questarStagingRecord);
		if (subjectArea == null) {
			String msg = "Student " + studentStateIdentifier + " does not have a valid Subject name.";
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		String[] testTypeCodes = {"P", "GN"};
		String testTypeCode = null;
		for (int x = 0; x < testTypeCodes.length; x++) {
			int enrolledToTestCount = enrollmentService.countEnrollmentsTestTypeSubjectArea(enrollment.getId(), assessment.getId(),
					subjectArea.getId(), testTypeCodes[x]);
			if (enrolledToTestCount > 0) {
				if (enrolledToTestCount == 1) {
					testTypeCode = testTypeCodes[x];
					break;
				} else {
					logger.warn("Found multiple enrollments");
				}
			}
		}
		
		if (testTypeCode == null) {
			String msg = "Student has no TEC record for test type P/GN with subject code " +
					subjectArea.getSubjectAreaCode() + ".";
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		} else {
			if (testTypeCode.equals("GN")) {
				Map<String, String> pnpSettings = getPNPSettings(studentId);
				if (!verifyPNPSettings(pnpSettings)) {
					String msg = "Student does not have Braille/PaperPencil/LargePrint PNP settings for a GN test.";
					fail(msg, studentId, questarStagingRecord);
					throw new QuestarSkipException(msg);
				}
			}
		}
		
		Long testExternalId = questarService.mapTestExternalId(questarStagingRecord.getFormNumber());
		if (testExternalId == null) {
			String msg = "Could not find mapping for test external ID " + questarStagingRecord.getFormNumber() +
					" for student state ID " + questarStagingRecord.getStudentStateId();
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		GradeCourse gradeCourse = gradeCourseService.getIndependentGradeByAbbreviatedName(
				questarStagingRecord.getGrade().replaceFirst("^0+", ""));
		ContentArea contentArea = contentAreaService.findBySubjectAreaIdAndTestTypeCodeAndAssessmentId(testTypeCode,
				subjectArea.getId(), assessment.getId());
		
		if (gradeCourse == null) {
			String msg = "Could not find grade course for grade abbreviated name" + questarStagingRecord.getGrade().replaceFirst("^0+", "");
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		if (contentArea == null) {
			String msg = "Could not find content area with test type code " + testTypeCode +
					", subject area ID " + subjectArea.getId() + ", and assessment ID " + assessment.getId();
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		QuestarStagingDTO dto = (QuestarStagingDTO) questarStagingRecord;
		dto.setResponses(questarService.getResponsesForStagingRecord(dto.getId()));
		/*if (dto.getResponses().isEmpty()) {
			String msg = "Student State ID " + questarStagingRecord.getStudentStateId() + " had no responses for form number " + questarStagingRecord.getFormNumber() + ".";
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}*/
		
		List<TestType> testTypes = testTypeService.getTestTypeByAssessmentId(assessment.getId());
		TestType testType = null;
		for (TestType tt : testTypes) {
			if (tt.getTestTypeCode().equals(testTypeCode)) {
				testType = tt;
				break;
			}
		}
		
		if (testType == null) {
			String msg = "No test type found for assessment id " + assessment.getId() + " and testTypeCode " + testTypeCode + ".";
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		TestCollection testCollection = getTestCollectionsWithTestExternalId(assessmentProgram, assessment, testType, contentArea,
				gradeCourse, testExternalId);
		if (testCollection == null) {
			String msg = "No test collections found in " + assessmentProgram.getAbbreviatedname() + " containing CB test id " + testExternalId +
					" for assessment id " + assessment.getId() + ", test type id " + testType.getId() + ", content area id " + contentArea.getId() +
					", grade course id " + gradeCourse.getId() + ".";
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		Test test = testService.findLatestByExternalId(testExternalId);
		if (test == null) {
			String msg = "No test found with external id " + testExternalId;
			fail(msg, studentId, questarStagingRecord);
			throw new QuestarSkipException(msg);
		}
		
		Map<TestCollection, List<Test>> testCollectionsTests = new HashMap<TestCollection, List<Test>>();
		List<Test> tests = new ArrayList<Test>();
		tests.add(test);
		testCollectionsTests.put(testCollection, tests);
		writerMap.put("testCollectionTests", testCollectionsTests);
		
		// info for the writer that is specific to this record
		writerMap.put("enrollment", enrollment);
		writerMap.put("testType", testType);
		writerMap.put("contentArea", contentArea);
		writerMap.put("gradeCourse", gradeCourse);
		writerMap.put("questarStagingRecord", questarStagingRecord);
		
		return writerMap;
	}
	
	private Map<String, String> getPNPSettings(Long studentId) {
		List<String> itemAttributeList = new ArrayList<String>();
		itemAttributeList.add("onscreenKeyboard");
		itemAttributeList.add("Spoken");
		itemAttributeList.add("Braille");
		List<StudentProfileItemAttributeDTO> pnpAttribuites = studentProfileService.getStudentProfileItemAttribute(studentId, itemAttributeList);
		itemAttributeList.clear();
		itemAttributeList.add("supportsTwoSwitch");
		itemAttributeList.add("SpokenSourcePreference");
		itemAttributeList.add("UserSpokenPreference");
		itemAttributeList.add("preferenceSubject");
		itemAttributeList.add("paperAndPencil");//Alternate Form - Paper and Pencil
		itemAttributeList.add("largePrintBooklet");//Alternate Form - Large print booklet
		pnpAttribuites.addAll(studentProfileService.getStudentProfileItemContainer(studentId, itemAttributeList));
		
		Map<String, String> pnpAttributeMap = new HashMap<String, String>();
		for(StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites){
			if(pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0) {
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), "false");
			} else {
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), pnpAttribute.getSelectedValue());
			}
		}
		
		return pnpAttributeMap;
	}
	
	private boolean verifyPNPSettings(Map<String, String> pnpAttributeMap) {
		if (pnpAttributeMap != null && !pnpAttributeMap.isEmpty()) {
			if((pnpAttributeMap.get("Braille") != null && pnpAttributeMap.get("Braille").equalsIgnoreCase("true")) ||
				   (pnpAttributeMap.get("paperAndPencil") != null && pnpAttributeMap.get("paperAndPencil").equalsIgnoreCase("true")) ||
				   (pnpAttributeMap.get("largePrintBooklet") != null && pnpAttributeMap.get("largePrintBooklet").equalsIgnoreCase("true"))){
				return true;
			}
		}
		return false;
	}
	
	private Enrollment getEnrollment(QuestarStagingRecord questarStagingRecord, Long stateId, int schoolYear) {
		String studentStateIdentifier = questarStagingRecord.getStudentStateId();
		List<Enrollment> enrollments = enrollmentService.getEnrollmentBySSIDAndStateId(studentStateIdentifier, stateId, schoolYear);
		if (enrollments == null || enrollments.isEmpty()) {
			if (questarStagingRecord.getWalkIn()) {
				String msg = "Student State ID " + studentStateIdentifier + " is a walk-in and must be enrolled in state ID " + stateId + " first.";
				fail(msg, null, questarStagingRecord);
				throw new QuestarSkipException(msg);
			} else {
				String msg = "Student State ID " + studentStateIdentifier + " was not found enrolled in state ID " + stateId + ".";
				fail(msg, null, questarStagingRecord);
				throw new QuestarSkipException(msg);
			}
		} else {
			Enrollment foundEnrollment = null;
			for (Enrollment enrollment : enrollments) {
				String dtoSchoolCode = questarStagingRecord.getSchoolCode();
				//String enrollmentAccountabilitySchoolIdentifier = enrollment.getAccountabilityschoolidentifier();
				//String enrollmentAypSchoolIdentifier = enrollment.getAypSchoolIdentifier();
				String enrollmentAttendanceSchoolIdentifier = enrollment.getAttendanceSchoolProgramIdentifier();
				
				if (dtoSchoolCode != null && /*enrollmentAccountabilitySchoolIdentifier != null && enrollmentAypSchoolIdentifier != null &&*/
						enrollmentAttendanceSchoolIdentifier != null) {
						/*dtoSchoolCode.equals(enrollmentAccountabilitySchoolIdentifier) &&
						dtoSchoolCode.equals(enrollmentAypSchoolIdentifier) &&*/
						String[] dtoSchoolCodeParts = dtoSchoolCode.split("-");
						String schoolCode = dtoSchoolCodeParts[0];
						if(schoolCode != null && schoolCode.replaceAll("^0+", "").equals(enrollmentAttendanceSchoolIdentifier)) {
							foundEnrollment = enrollment;
							break;
						}
				}
			}
			
			if (foundEnrollment == null) {
				String msg = "Student State ID " + studentStateIdentifier + "'s staging school code did not match enrollment school code.";
				fail(msg, null, questarStagingRecord);
				throw new QuestarSkipException(msg);
			} else {
				return foundEnrollment;
			}
		}
	}
	
	private SubjectArea getSubjectArea(QuestarStagingRecord questarStagingRecord) {
		if (questarStagingRecord.getSubject() != null) {
			List<SubjectArea> sas = subjectAreaService.getByName(questarStagingRecord.getSubject());
			if (!sas.isEmpty()) {
				return sas.get(0);
			}
		}
		return null;
	}
	
	private TestCollection getTestCollectionsWithTestExternalId(AssessmentProgram ap,
			Assessment assessment, TestType testType, ContentArea contentArea, GradeCourse gradeCourse, Long testExternalId) {
		List<TestCollection> testCollections = testCollectionService.getTestCollectionForAMPPPWithTestExternalId(ap.getAbbreviatedname(), assessment.getId(),
				gradeCourse.getId(), testType.getId(), contentArea.getId(), "enrollment", contentArea.getAbbreviatedName(), assessment.getAssessmentCode(),
				testExternalId, this.operationalTestWindowId);
		if (testCollections.size() == 0) { 
			return null;
		}
		return testCollections.get(0);
	}
	
	@SuppressWarnings("unchecked")
	private void writeReason(String msg, Long studentId, QuestarStagingRecord record) {
		QuestarRegistrationReason brr = new QuestarRegistrationReason();
		brr.setBatchRegistrationId(batchRegistrationId);
		brr.setStudentId(studentId);
		brr.setReason(msg);
		brr.setQuestarStagingId(record.getId());
		brr.setQuestarStagingFileId(record.getQuestarStagingFileId());
		((CopyOnWriteArrayList<QuestarRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brr);
	}
	
	private int updateRecordToFailed(QuestarStagingRecord record) {
		record.setStatus("FAILED");
		record.setModifiedDate((Date) stepExecution.getJobExecution().getJobParameters().getDate("run date"));
		return questarService.updateStagingRecord(record);
	}
	
	private void fail(String msg, Long studentId, QuestarStagingRecord record) {
		if(studentId == null) {
			studentId = record.getStudentKiteNumber();
		}
		logger.debug(msg);
		writeReason(msg, studentId, record);
		updateRecordToFailed(record);
	}
	
	public AssessmentProgram getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(AssessmentProgram assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	public Assessment getAssessment() {
		return assessment;
	}
	
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}
	
	public TestingProgram getTestingProgram() {
		return testingProgram;
	}

	public void setTestingProgram(TestingProgram testingProgram) {
		this.testingProgram = testingProgram;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}
	
}