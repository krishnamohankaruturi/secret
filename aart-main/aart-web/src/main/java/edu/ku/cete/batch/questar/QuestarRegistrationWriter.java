package edu.ku.cete.batch.questar;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.ku.cete.batch.questar.exception.QuestarSkipException;
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.SpecialCircumstance;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.Foil;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.report.domain.QuestarRegistrationReason;
import edu.ku.cete.report.domain.QuestarStagingRecord;
import edu.ku.cete.report.domain.QuestarStagingResponse;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.QuestarService;
import edu.ku.cete.service.StudentSpecialCircumstanceService;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;
import edu.ku.cete.web.QuestarStagingDTO;

public class QuestarRegistrationWriter implements ItemWriter<Map<String, Object>> {
	
	private Logger logger = LoggerFactory.getLogger(QuestarRegistrationWriter.class);

	@Autowired
	private StudentsTestsStatusConfiguration studentsTestsStatus;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private TestSessionService testSessionService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private TestStatusConfiguration testStatusConfiguration;
	
	@Autowired
	private StudentSessionRuleConverter studentSessionRuleConverter;
	
	@Autowired
	private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
	
	@Autowired
	private QuestarService questarService;
	
	@Autowired
	private StudentsResponsesService studentsResponsesService;
	
	@Autowired
	private ContentAreaService caService;
	
	@Autowired
	private StudentProfileService studentProfileService;
	
	@Autowired
	private StudentSpecialCircumstanceService studentSpecialCircumstanceService;
	
	@Autowired
	private OrganizationTypeService orgTypeService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;
	
	@Value("${testsession.status.type}")
	private String TEST_SESSION_STATUS_TYPE;

	private Category unusedSession;
	
	private AssessmentProgram assessmentProgram;
	private Organization org;
	private int schoolYear;
	private Assessment assessment;
	private TestingProgram testingProgram; // not currently used
	private StepExecution stepExecution;
	
	private User user;
	
	private long batchRegistrationId;
	
	@BeforeStep
	public void initializeValues(StepExecution stepExecution) {
		unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void write(List<? extends Map<String, Object>> list) throws Exception {
		logger.debug("--> write  - getBatchRegistrationId() : " + getBatchRegistrationId());
		if(!list.isEmpty()) {
			Map<String, Object> map = list.get(0);
			
			QuestarStagingRecord qsr = (QuestarStagingRecord) map.get("questarStagingRecord");
			TestType testType = (TestType) map.get("testType");
			ContentArea contentArea = (ContentArea) map.get("contentArea");
			GradeCourse gradeCourse = (GradeCourse) map.get("gradeCourse");
			Enrollment enrollment = (Enrollment) map.get("enrollment");
			Map<TestCollection, List<Test>> testCollectionTests = (Map<TestCollection, List<Test>>) map.get("testCollectionTests");
			
			if (qsr.getWalkIn()) {
				qsr.setStudentKiteNumber(enrollment.getStudentId());
			}
			
			for(TestCollection testCollection: testCollectionTests.keySet()) {
				String testSessionName = prepareTestSessionName(testCollection, enrollment, assessment, testType, contentArea, gradeCourse);
				logger.debug("Looking for test session: " + testSessionName);
				//Check if the test session already exists or not.
				List<TestSession> existingTestSessions = testSessionService.getAutoRegisteredSessions(assessment.getId(), testType.getId(),
						contentArea.getId(), gradeCourse.getId(), enrollment.getAttendanceSchoolId(), (long) schoolYear,
						testCollection.getStage().getId(), SourceTypeEnum.QUESTARPROCESS, testCollection.getOperationalTestWindowId());
				TestSession testSession = null;
				if(CollectionUtils.isNotEmpty(existingTestSessions)) {
					testSession = existingTestSessions.get(0);
					logger.debug("Found session "+testSession.getId());
				}
				
	 			if(testSession != null && testSession.getId() > 0L) {
					logger.debug("Adding enrollment - " + enrollment.getId() + " to existing testsession - " + testSession.getId());
					studentsTestsService.addStudentExistingSession(enrollment, testSession, testCollection, testCollectionTests.get(testCollection), null, user.getId());
				} else {
					logger.debug("Adding enrollment - " + enrollment.getId() + " to new Test Session");
					Long otwId = testCollection.getOperationalTestWindowId();
					List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
			        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
					Long subjectAreaId = caService.findByTestTypeAssessmentContentArea(assessment.getId(),testType.getId(), contentArea.getId());
					
					//If the test session does not exists, create a new test session.
					testSession = new TestSession();
					testSession.setSource(SourceTypeEnum.QUESTARPROCESS.getCode());
					testSession.setName(testSessionName);
					testSession.setStatus(unusedSession.getId());
					testSession.setActiveFlag(true);
					testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
					testSession.setTestTypeId(testType.getId());
					testSession.setGradeCourseId(gradeCourse.getId());
					testSession.setStageId(testCollection.getStage().getId());
					testSession.setTestCollectionId(testCollection.getTestCollectionId());
					testSession.setOperationalTestWindowId(otwId);
					testSession.setCreatedUser(user.getId());
					testSession.setModifiedUser(user.getId());
					testSession.setSchoolYear(new Long(schoolYear));
					testSession.setSubjectAreaId(subjectAreaId);
					
					testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
							AARTCollectionUtil.getIds(testCollectionTests.get(testCollection)), studentSessionRule, null); 
					logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - " +  testSession.getId());
				}
	 			List<TestSession> testSessions = (List<TestSession>) map.get("testSessions");
	 			if (testSessions == null) {
	 				testSessions = new ArrayList<TestSession>();
	 			}
	 			testSessions.add(testSession);
	 			map.put("testSessions", testSessions);
	 			
	 			if (StringUtils.isNotBlank(qsr.getScCode())) {
	 				populateSCCode(qsr, enrollment, testSession);
	 			}
	 			
	 			if (qsr.getAccommodation() != null && qsr.getAccommodation().equalsIgnoreCase("Y")) {
	 				String container = "supportsProvidedOutsideSystem";
	 				String name = "supportsStudentProvidedAccommodations";
	 				
	 				populateStudentAccommodation(enrollment, container, name);
	 			}
	 			
	 			try {
	 				populateStudentResponses(qsr, testSession.getId());
	 				List<Long> studentIds = new ArrayList<Long>(1);
	 				studentIds.add(qsr.getStudentKiteNumber());
	 				studentsTestsService.closeStudentsTestSectionsStatus(studentIds, testSession.getId());
	 				studentsTestsService.closeStudentsTestsStatus(studentIds, testSession.getId());
	 				
	 				logger.debug(String.format("Assigned Student Id: %d to Test Session Id: %d", enrollment.getStudentId(), testSession.getId()));
 		    		writeReason(String.format("Assigned Student Id: %d to Test Session Id: %d", qsr.getStudentKiteNumber(), testSession.getId()), 
 		    							qsr.getStudentKiteNumber(), testSession.getId(), qsr);
	 				
	 			} catch (Exception e) {
	 				logger.error("Caught Exception: ", e);
	 				updateStagingRecord(qsr, "FAILED");
	 				writeReason("Exception occurred: " + e.getMessage(), qsr.getStudentKiteNumber(), null, qsr);
	 				throw new QuestarSkipException(e.getMessage());
	 			}
			}
			updateStagingRecord(qsr, "COMPLETE");
		}
		logger.debug("<-- write - getBatchRegistrationId() : " + getBatchRegistrationId());
	}
	
	private String prepareTestSessionName(TestCollection testCollection, Enrollment enrollment, Assessment assessment, TestType testType,
			ContentArea contentArea, GradeCourse gradeCourse) {
		String testTypeName = testType.getTestTypeName().substring(testType.getTestTypeName().indexOf('-') + 1).replace("/", "").trim();
		
		return String.format("%d_%s_%s_%s_%s", schoolYear, enrollment.getAttendanceSchool().getDisplayIdentifier(),
				gradeCourse.getName(), contentArea.getName(), testTypeName);
	}
	
	private void populateStudentResponses(QuestarStagingRecord qsr, Long testSessionId) throws JsonProcessingException, IOException {
		Long studentId = qsr.getStudentKiteNumber();
		QuestarStagingDTO dto = (QuestarStagingDTO) qsr;
		List<QuestarStagingResponse> responses = dto.getResponses();
		
		if (responses.isEmpty()) {
			return;
		}
		
		List<StudentsTests> studentsTests = studentsTestsService.findByTestSessionAndStudent(testSessionId, studentId);
		if (!studentsTests.isEmpty()) {
			StudentsTests studentsTest = studentsTests.get(0);
			List<StudentsTestSections> studentsTestSections = studentsTestsService.findStudentsTestSectionsByStudentsTestsId(
					studentsTest.getId());
			for (StudentsTestSections studentsTestSection : studentsTestSections) {
				Long testSectionId = studentsTestSection.getTestSectionId();
				List<TaskVariant> tasks = testService.findTaskVariantsInTestSection(testSectionId);
				for (TaskVariant task : tasks) {
					String taskTypeCode = task.getTaskType();
					StudentsResponses sr = null;
					
					if (taskTypeCode.equals("ITP")) {
						sr = populateITPResponse(responses, task);
					} else if (taskTypeCode.equals("ER")) {
						sr = populateERResponse(responses, task);
					} else if (taskTypeCode.equals("CR")) {
						sr = populateCRResponse(responses, task);
					} else if (taskTypeCode.equals("MC-MS")) {
						sr = populateMCMSResponse(responses, task);
					} else { // MC-K, T-F
						sr = populateMCResponse(responses, task);
					}
					if (sr != null) {
						sr.setStudentId(studentId);
						sr.setTestId(studentsTest.getTestId());
						sr.setTestSectionId(testSectionId);
						sr.setStudentsTestsId(studentsTest.getId());
						sr.setStudentsTestSectionsId(studentsTestSection.getId());
						sr.setTaskVariantId(task.getId());
						sr.setCreatedDate((Date) stepExecution.getJobExecution().getJobParameters().getDate("run date"));
						sr.setModifiedDate((Date) stepExecution.getJobExecution().getJobParameters().getDate("run date"));
						sr.setCreatedUser(user.getId());
						sr.setModifiedUser(user.getId());
						sr.setActiveFlag(true);
						studentsResponsesService.insertStudentResponse(sr);
						logger.debug("Inserting student response [studentstestsid = " + studentsTest.getId() + ",taskvariantid = " + task.getId() + "] with " +
								(sr.getFoilId() == null ? ("response " + sr.getResponse()) : (" foil id " + sr.getFoilId())));
					}
				}
			}
			if (!responses.isEmpty()) {
				logger.debug("**********Responses were left after processing************");
			}
		}
	}
	
	/**
	 * Right now, this method simply returns a placeholder object to insert into the database.
	 * NOTE: this removes sequential responses from the same task, similar to the MCMS version.
	 * @param responses
	 * @param task
	 * @return
	 */
	private StudentsResponses populateITPResponse(List<QuestarStagingResponse> responses, TaskVariant task) {
		StudentsResponses sr = null;
		boolean found = false;
		for (int x = 0; x < responses.size(); x++) {
			QuestarStagingResponse response = responses.get(x);
			
			// the itemName field on the response is really the CB task id [followed by the bubble letter, if necessary]
			String itemName = response.getItemName().split("_")[0];
			Long taskVariantExternalId = Long.parseLong(itemName);
			if (taskVariantExternalId.equals(task.getExternalId())) {
				if (sr == null) { // only initialize the object if we have to
					sr = new StudentsResponses();
					sr.setResponse("QUESTAR PAPER/PENCIL ITP RESPONSE");
				}
				
				found = true;
				// trick to remove the current record, then check the same index (but next record, since we removed one)
				responses.remove(x);
				x--;
			} else if (found) { // stop once we stopped hitting continual matches
				break;
			}
		}
		
		if (!found) {
			return null;
		}
		
		return sr;
	}
	
	private StudentsResponses populateERResponse(List<QuestarStagingResponse> responses, TaskVariant task) {
		StudentsResponses sr = null;
		return sr;
	}
	
	private StudentsResponses populateCRResponse(List<QuestarStagingResponse> responses, TaskVariant task) {
		StudentsResponses sr = null;
		return sr;
	}
	
	/**
	 * NOTE: This removes responses from the list, to keep from re-using the first set of responses if the same task was used in multiple sections.
	 * @param responses
	 * @param task
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	private StudentsResponses populateMCMSResponse(List<QuestarStagingResponse> responses, TaskVariant task) throws JsonProcessingException, IOException {
		final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		List<Foil> foils = testService.getFoilsForTaskVariant(task.getId());
		List<Foil> answered = new ArrayList<Foil>();
		String responseStr = "[";
		boolean found = false;
		for (int x = 0; x < responses.size(); x++) {
			QuestarStagingResponse response = responses.get(x);
			if (response.getResponse() != null) {
				// the itemName field on the response is really the CB task id [followed by the bubble letter, if necessary]
				String itemName = response.getItemName().split("_")[0];
				Long taskVariantExternalId = Long.parseLong(itemName);
				if (taskVariantExternalId.equals(task.getExternalId())) {
					int foilIndex = ALPHA.indexOf(response.getResponse().toLowerCase().charAt(0));
					if (found) {
						responseStr += ",";
					}
					responseStr += String.valueOf(foils.get(foilIndex).getId());
					answered.add(foils.get(foilIndex));
					found = true;
					responses.remove(x);
					x--;
				} else if (found) { // stop once we stopped hitting continual matches
					break;
				}
			}
		}
		if (!found) {
			return null;
		}
		StudentsResponses sr = new StudentsResponses();
		sr.setResponse(responseStr + "]");
		if (task.getScoringNeeded()) {
			sr.setScore(scoreMCMS(answered, task, foils));
		}
		return sr;
	}
	
	// so far there's correctOnly, partialcredit, and partialquota
	private BigDecimal scoreMCMS(List<Foil> selectedFoils, TaskVariant task, List<Foil> allFoils)
			throws JsonProcessingException, IOException {
		BigDecimal score = null;
		String scoringMethod = task.getScoringMethod();
		String scoringData = task.getScoringData();
		if (scoringMethod != null && scoringData != null) {
			score = new BigDecimal(0);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(scoringData);
			if (scoringMethod.equals("correctOnly")) {
				if (checkForCorrectMCResponses(selectedFoils, allFoils)) {
					score = score.add(new BigDecimal(task.getMaxScore()));
				}
			} else if (scoringMethod.equals("partialcredit")) {
				JsonNode correctResponse = root.get("correctResponse");
				Integer correctScore = correctResponse.get("score").asInt();
				if (correctScore != task.getMaxScore()) {
					logger.warn("Max scores for correct response does not match task max score for task variant id " + task.getId());
				}
				ArrayNode partialResponsesArray = (ArrayNode) root.get("partialResponses");
				for (Foil selectedFoil : selectedFoils) {
					if (selectedFoil.getCorrectResponse()) {
						Long selectedFoilId = selectedFoil.getExternalId(); // need the CB ID here, so use external
						for (int x = 0; x < partialResponsesArray.size(); x++) {
							JsonNode partialResponse = partialResponsesArray.get(x);
							long jsonResponseId = partialResponse.get("responseid").asLong(0);
							if (selectedFoilId.longValue() == jsonResponseId) {
								String partialScoreValue = partialResponse.get("score").asText("0");
								BigDecimal partialScore = new BigDecimal(partialScoreValue);
								score = score.add(partialScore);
								break;
							}
						}
					}
				}
			}/* else if (scoringMethod.equals("partialquota")) {
				JsonNode correctResponse = root.get("correctResponse");
				Integer correctScore = correctResponse.get("score").asInt();
				if (correctScore != task.getMaxScore()) {
					logger.warn("Max scores for correct response(s) does not match task max score for task variant id " + task.getId());
				}
				int quota = 0;
				for (Foil selectedFoil : selectedFoils) {
					if (selectedFoil.getCorrectResponse()) {
						quota++;
					}
				}
				if (quota > 0) {
					ArrayNode partialResponsesArray = (ArrayNode) root.get("partialResponses");
					for (int x = 0; x < partialResponsesArray.size(); x++) {
						JsonNode partialResponse = partialResponsesArray.get(x);
						if (quota == partialResponse.get("quota").asInt(0)) {
							String partialScoreValue = partialResponse.get("score").asText("0");
							BigDecimal partialScore = new BigDecimal(partialScoreValue);
							score = score.add(partialScore);
						}
					}
				} else {
					score = new BigDecimal(0);
				}
			}*/
		}
		return score;
	}
	
	private boolean checkForCorrectMCResponses(List<Foil> selectedFoils, List<Foil> allFoils) {
		List<Long> selectedFoilIds = new ArrayList<Long>(selectedFoils.size());
		for (int x = 0; x < selectedFoils.size(); x++) {
			selectedFoilIds.add(selectedFoils.get(x).getId());
		}
		
		boolean allCorrect = true;
		for (int x = 0; allCorrect && x < allFoils.size(); x++) {
			Foil foil = allFoils.get(x);
			if (foil.getCorrectResponse() && !selectedFoilIds.contains(foil.getId())) {
				allCorrect = false;
			}
		}
		
		return allCorrect;
	}
	
	/**
	 * NOTE: This removes responses from the list, to keep from re-using the first response if the same task was used in multiple sections.
	 * @param responses
	 * @param task
	 * @return
	 */
	private StudentsResponses populateMCResponse(List<QuestarStagingResponse> responses, TaskVariant task) {
		final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		List<Foil> foils = testService.getFoilsForTaskVariant(task.getId());
		List<Foil> selectedFoils = new ArrayList<Foil>();
		boolean found = false;
		for (int x = 0; x < responses.size(); x++) {
			QuestarStagingResponse response = responses.get(x);
			if (response.getResponse() != null) {
				// the itemName field on the response is really the CB task id [followed by the bubble letter, if necessary]
				String itemName = response.getItemName().split("_")[0];
				Long taskVariantExternalId = Long.parseLong(itemName);
				if (taskVariantExternalId.equals(task.getExternalId())) {
					int foilIndex = ALPHA.indexOf(response.getResponse().toLowerCase().charAt(0));
					selectedFoils.add(foils.get(foilIndex));
					found = true;
					responses.remove(x);
					x--;
				} else if (found) { // stop once we stopped hitting continual matches
					break;
				}
			}
		}
		if (!found) {
			return null;
		}
		
		StudentsResponses sr = new StudentsResponses();
		if (selectedFoils.size() > 1) {
			String questarResponseText = "";
			for (int x = 0; x < selectedFoils.size(); x++) {
				Foil selectedFoil = selectedFoils.get(x);
				questarResponseText += (x != 0 ? "," : "") + String.valueOf(selectedFoil.getId());
				if (!selectedFoil.getCorrectResponse() && sr.getFoilId() == null) {
					sr.setFoilId(selectedFoil.getId());
				}
			}
			sr.setQuestarResponseText(questarResponseText);
			if (task.getScoringNeeded()) {
				sr.setScore(new BigDecimal(0));
			}
		} else {
			Foil selectedFoil = selectedFoils.get(0);
			sr.setFoilId(selectedFoil.getId());
			if (task.getScoringNeeded()) {
				sr.setScore(new BigDecimal(selectedFoils.get(0).getCorrectResponse() ? task.getMaxScore() : 0));
			}
		}
		return sr;
	}
	
	private void populateSCCode(QuestarStagingRecord qsr, Enrollment enrollment, TestSession testSession) {
		Organization ak = orgService.getByDisplayIdAndType("AK", orgTypeService.getByTypeCode("ST").getOrganizationTypeId(), null);
		boolean found = false;
		List<SpecialCircumstance> circumstances = studentSpecialCircumstanceService.getCEDSByUserState(ak.getId());
		for (SpecialCircumstance circumstance : circumstances) {
			String ppCode = circumstance.getPaperPencilCode();
			if (ppCode != null && ppCode.equals(qsr.getScCode())) {
				Map<String, Object> specialCircumstanceMap = new HashMap<String, Object>();
				specialCircumstanceMap.put("studentId", enrollment.getStudentId());
				specialCircumstanceMap.put("specialCircumstanceValue", circumstance.getId());
				specialCircumstanceMap.put("requireConfirmation", circumstance.getRequireConfirmation());
				
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
				tmp.add(specialCircumstanceMap);
				studentSpecialCircumstanceService.saveSpecialCircumstance(testSession.getId(), tmp, true, userDetails.getUser().getId());
				found = true;
				break;
			}
		}
		if (!found) {
			logger.error("Could not find matching SC Code for \"" + qsr.getScCode() + "\" in Alaska");
		}
	}
	
	private void populateStudentAccommodation(Enrollment enrollment, String container, String name) throws JsonProcessingException {
		Long pianacId = studentProfileService.getPianacId(container, name);
		
		// shouldn't ever happen, but better safe than sorry
		if (pianacId == null) {
			logger.error("Was not able to find pianacId for container \""+container+"\", name:\""+name+"\"");
		} else {
			// this is the way PNP does it, might as well stay consistent
			// this should only add (or edit) the setting we've specified for the student, no other records should be touched
			Map<String, String> tmp = new HashMap<String, String>();
			tmp.put(pianacId.toString(), "true");
			studentProfileService.saveStudentProfileItemAttributes(tmp, enrollment.getStudentId());
		}
	}
	
	private int updateStagingRecord(QuestarStagingRecord qsr, String status) {
		qsr.setStatus(status);
		qsr.setModifiedDate((Date) stepExecution.getJobExecution().getJobParameters().getDate("run date"));
		return questarService.updateStagingRecord(qsr);
	}
	
	@SuppressWarnings("unchecked")
	private void writeReason(String msg, Long studentId, Long testSessionId, QuestarStagingRecord qsr) {
		QuestarRegistrationReason brr = new QuestarRegistrationReason();
		brr.setBatchRegistrationId(batchRegistrationId);
		brr.setStudentId(studentId);
		brr.setTestSessionId(testSessionId);
		brr.setReason(msg);
		brr.setQuestarStagingId(qsr.getId());
		brr.setQuestarStagingFileId(qsr.getQuestarStagingFileId());
		((CopyOnWriteArrayList<QuestarRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brr);
	}
	
	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
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

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}