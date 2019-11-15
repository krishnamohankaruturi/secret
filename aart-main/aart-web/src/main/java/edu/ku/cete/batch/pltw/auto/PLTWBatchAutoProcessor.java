/**
 * 
 */
package edu.ku.cete.batch.pltw.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.processor.BatchRegistrationProcessor;
import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.batch.support.WriterContext;
import edu.ku.cete.domain.DashboardMessage;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

/**
 * @author Kiran Reddy Taduru
 *
 * Aug 27, 2018 4:24:17 PM
 */
public class PLTWBatchAutoProcessor extends BatchRegistrationProcessor {

	private final static Log logger = LogFactory.getLog(PLTWBatchAutoProcessor.class);
	protected Long batchRegistrationId;
	protected String assessmentProgramCode;
	protected Long assessmentProgramId;
	protected ContentArea contentArea;
	protected GradeCourse gradeBand;
	protected List<TestCollection> testCollections;	
	protected String enrollmentMethod;
	private Organization contractingOrg;	
	private StepExecution stepExecution;	
	Map<String, AppConfiguration> pltwBatchErrorMessageMap;
	private Stage stage;
	
	@Autowired
	private StudentProfileService studentProfileService;
	
	@Override
	public WriterContext process(Enrollment enrollment) throws Exception {
		logger.debug("Entering PLTWBatchAutoProcessor process:: - batchRegistrationId: " + batchRegistrationId + " - StudentId: " + enrollment.getStudentId() + " - ContentArea");
		WriterContext writerContext = new WriterContext();
		
		if(enrollment != null && enrollment.getStudent() != null) {
			/*
			if(StringUtils.isBlank(enrollment.getStudent().getComprehensiveRace()) && enrollment.getStudent().getHispanicEthnicity() == null) {
				String msg = pltwBatchErrorMessageMap.get(CommonConstants.PLTW_MISSING_RACE_ETHNICITY).getAttributeValue();
				String reason = String.format(msg + " for Student Id: %d, EnrollmentId: %d, ContractingOrganizationId: %d (%s), OperationalTestWindowId: %d, ContentAreaId: %d (%s), GradeBand: %d (%s)", 
						enrollment.getStudentId(), enrollment.getId(),
						contractingOrg.getId(), contractingOrg.getDisplayIdentifier(), contractingOrg.getOperationalWindowId(), 
						contentArea.getId(), contentArea.getAbbreviatedName(),
						gradeBand.getGradeBandId(), gradeBand.getGradeBandAbbrName());
				logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
				writeReason(enrollment.getStudentId(), reason);
				writeDashboardMessage(enrollment, msg);
				throw new SkipBatchException(reason);
				
			} else {
			*/	
				if(StringUtils.isNotBlank(enrollment.getStudent().getComprehensiveRace())) {
					//if(enrollment.getStudent().getHispanicEthnicity() != null) {
						
						
						Set<String> accessibleFlags = getAccessibleFlags(enrollment);
						
						Map<TestCollection, List<Test>> testCollectionsTests = new HashMap<TestCollection, List<Test>>();
						for (TestCollection testCollection : testCollections) {
							List<Test> tests = getTests(enrollment, testCollection, accessibleFlags);
							if (CollectionUtils.isNotEmpty(tests)) {
								testCollectionsTests.put(testCollection, tests);
							} else {
								logger.debug("No published and QC complete tests found in test collection " + testCollection.getId());
							}
						}
						
						if (testCollectionsTests.isEmpty()) {
							String reason = "No tests found in any collections for " + contentArea.getName() + " grade " + gradeBand.getGradeBandAbbrName();
							writeReason(null, reason);
							throw new SkipBatchException(reason);
						}
						
						writerContext.setEnrollment(enrollment);
						writerContext.setAccessibilityFlags(accessibleFlags);
						writerContext.setTestCollectionTests(testCollectionsTests);
						
						logger.debug("Exiting PLTWBatchAutoProcessor process:: - batchRegistrationId: " + batchRegistrationId);
						
						return writerContext;
						
					/*
					Commented for one of PLTW's organizations not tracking hispanic ethnicity (Vermont, I think?)
					}else {
						//Ethnicity is missing for student
						String msg = pltwBatchErrorMessageMap.get(CommonConstants.PLTW_MISSING_HISPANIC_ETHNICITY).getAttributeValue();
						String reason = String.format(msg + " for Student Id: %d, EnrollmentId: %d, ContractingOrganizationId: %d (%s), OperationalTestWindowId: %d, ContentAreaId: %d (%s), GradeBand: %d (%s)", 
								enrollment.getStudentId(), enrollment.getId(),
								contractingOrg.getId(), contractingOrg.getDisplayIdentifier(), contractingOrg.getOperationalWindowId(), 
								contentArea.getId(), contentArea.getAbbreviatedName(),
								gradeBand.getGradeBandId(), gradeBand.getGradeBandAbbrName());
						logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
						writeReason(enrollment.getStudentId(), reason);
						writeDashboardMessage(enrollment, msg);
						throw new SkipBatchException(reason);
					}
					*/
				}else {
					
					//Comprehensiverace is missing for student
					String msg = pltwBatchErrorMessageMap.get(CommonConstants.PLTW_MISSING_COMPREHENSIVE_RACE).getAttributeValue();
					String reason = String.format(msg + " for Student Id: %d, EnrollmentId: %d, ContractingOrganizationId: %d (%s), OperationalTestWindowId: %d, ContentAreaId: %d (%s), GradeBand: %d (%s)", 
							enrollment.getStudentId(), enrollment.getId(),
							contractingOrg.getId(), contractingOrg.getDisplayIdentifier(), contractingOrg.getOperationalWindowId(), 
							contentArea.getId(), contentArea.getAbbreviatedName(),
							gradeBand.getGradeBandId(), gradeBand.getGradeBandAbbrName());
					logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
					writeReason(enrollment.getStudentId(), reason);
					writeDashboardMessage(enrollment, msg);
					throw new SkipBatchException(reason);
				}
			//}
			
			
		}
		
		
		
		logger.debug("Exiting PLTWBatchAutoProcessor process:: - batchRegistrationId: " + batchRegistrationId);
		return null;
	}
	
	@Override
	protected List<Test> getTests(Enrollment enrollment, TestCollection testCollection,
			Set<String> accessibilityFlags) {
		logger.debug("--> getTests");
		
		Boolean accessibleForm = null;
		Set<String> accessibilityFlagsParam = null;
		
 		if (CollectionUtils.isNotEmpty(accessibilityFlags)) {
 			accessibleForm = true;
 			accessibilityFlagsParam = accessibilityFlags;
 		}
 		
 		Long operationalTestWindowId = contractingOrg.getOperationalWindowId();
 		Long stagePredecessorId = stage.getPredecessorId();
 		
		List<Test> tests = testService.findQCTestsByTestCollectionAndStatusAndAccFlagsForPLTW(testCollection.getId(),
				testStatusConfiguration.getPublishedTestStatusCategory().getId(),
				AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, accessibleForm, accessibilityFlagsParam,
				operationalTestWindowId, stagePredecessorId, enrollment.getStudentId());
		
		logger.debug("<-- getTests");
		return tests;
	}


	@SuppressWarnings("unchecked")
	private void writeReason(Long studentId, String msg) {
		logger.debug(msg);

		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setStudentId(studentId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext()
				.get("jobMessages")).add(brReason);
	}
	
	@SuppressWarnings("unchecked")
	private void writeDashboardMessage(Enrollment enrollment, String msg) {
		logger.debug(msg);

		DashboardMessage dbMsg = new DashboardMessage();
		dbMsg.setBatchRegistrationId(batchRegistrationId);
		dbMsg.setRecordType(CommonConstants.DASHBOARD_MESSAGE_RECORD_TYPE);
		dbMsg.setStudentId(enrollment.getStudentId());
		dbMsg.setEnrollmentId(enrollment.getId());
		dbMsg.setAssessmentProgramId(assessmentProgramId);
		dbMsg.setContentAreaId(contentArea.getId());
		dbMsg.setGradeBandId(gradeBand.getGradeBandId());
		dbMsg.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
		dbMsg.setSchoolYear(contractingOrg.getCurrentSchoolYear());
		dbMsg.setRosterId(enrollment.getRoster().getId());
		dbMsg.setClassroomId(enrollment.getRoster().getClassroomId());
		dbMsg.setMessage(msg);
		((CopyOnWriteArrayList<DashboardMessage>) stepExecution.getJobExecution().getExecutionContext()
				.get("jobDashboardMessages")).add(dbMsg);
	}
	
	@Override
	protected Set<String> getAccessibleFlags(Enrollment enrollment) {
		
		List<String> itemAttributeList = new ArrayList<String>();
		itemAttributeList.add("keywordTranslationDisplay");
		itemAttributeList.add("onscreenKeyboard"); //Single Switches 
		itemAttributeList.add("Spoken");
		itemAttributeList.add("Braille");
		itemAttributeList.add("Signing");
		//itemAttributeList.add("Magnification");
		List<StudentProfileItemAttributeDTO> pnpAttribuites = studentProfileService.getStudentProfileItemAttribute(enrollment.getStudentId(), itemAttributeList);
		itemAttributeList.clear();
		itemAttributeList.add("supportsTwoSwitch");//Two switch system
		itemAttributeList.add("paperAndPencil");//Alternate Form - Paper and Pencil
		itemAttributeList.add("largePrintBooklet");//Alternate Form - Large print booklet
		itemAttributeList.add("SpokenSourcePreference");// Voice Source 
		itemAttributeList.add("UserSpokenPreference");//Spoken Preference 
		itemAttributeList.add("Language");//Language
		itemAttributeList.add("SigningType");
		pnpAttribuites.addAll(studentProfileService.getStudentProfileItemContainer(enrollment.getStudentId(), itemAttributeList));
		Map<String, String> pnpAttributeMap = new HashMap<String, String>();
		for(StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites){
			if(pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0)
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), "false");
			else
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), pnpAttribute.getSelectedValue());

		}
		return pnpConditionCheck(pnpAttributeMap);
	}
	
	private Set<String> pnpConditionCheck(Map<String, String> pnpAttributeMap){
		logger.debug("--> pnpConditionCheck" );
		Set<String> accessibilityFlag = new HashSet<String>();
		if(!pnpAttributeMap.isEmpty()){
			
			boolean language = pnpAttributeMap.get("Language") != null && pnpAttributeMap.get("Language").equalsIgnoreCase("spa");
			boolean keywordTranslationDisplay = pnpAttributeMap.get("keywordTranslationDisplay") != null && pnpAttributeMap.get("keywordTranslationDisplay").equalsIgnoreCase("true");
			boolean spoken = pnpAttributeMap.get("Spoken") != null && pnpAttributeMap.get("Spoken").equalsIgnoreCase("true");
			boolean onscreenKeyboard = pnpAttributeMap.get("onscreenKeyboard") != null && pnpAttributeMap.get("onscreenKeyboard").equalsIgnoreCase("true");
			boolean supportsTwoSwitch = pnpAttributeMap.get("supportsTwoSwitch") != null && pnpAttributeMap.get("supportsTwoSwitch").equalsIgnoreCase("true");
			boolean paperAndPencil = pnpAttributeMap.get("paperAndPencil") != null && pnpAttributeMap.get("paperAndPencil").equalsIgnoreCase("true");
			boolean largePrintBooklet = pnpAttributeMap.get("largePrintBooklet") != null && pnpAttributeMap.get("largePrintBooklet").equalsIgnoreCase("true");
			boolean braille = pnpAttributeMap.get("Braille") != null && pnpAttributeMap.get("Braille").equalsIgnoreCase("true");
			boolean signing = "true".equalsIgnoreCase(pnpAttributeMap.get("Signing"));
			boolean signingIsASL = "asl".equalsIgnoreCase(pnpAttributeMap.get("SigningType"));
			//boolean magnification = "true".equalsIgnoreCase(pnpAttributeMap.get("Magnification"));
			boolean userSpokenPreference = pnpAttributeMap.get("UserSpokenPreference") != null && (pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("nonvisual") || 
					pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("textandgraphics"));
			//Students With Spanish Accommodation
			//Students With Spanish and Spoken Accommodations
			//For ONLY the subjects of Math and Science
			if(language && keywordTranslationDisplay){
				if(contentArea.getAbbreviatedName().equalsIgnoreCase("M") || contentArea.getAbbreviatedName().equalsIgnoreCase("Sci")){
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " 'spanish' PNP valid" );	
					accessibilityFlag.add("spanish");
					  
					//Spoken = true
					if(spoken){	
						logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " 'Spoken' PNP valid" );	
						accessibilityFlag.add("read_aloud");
					} 
				}
			}
			
			//Students With Switch Accommodations
			if(onscreenKeyboard || supportsTwoSwitch){
				accessibilityFlag.add("switch");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Switch' valid" );	
				
				//Students With Switch and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Switch', PNP 'Spoken' valid" );	
				}		
			}
			
			//Students With Paper Pencil Accommodations
			if(paperAndPencil){
				accessibilityFlag.add("paper");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'paperAndPencil' valid" );	
				
				//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - paperAndPencil - PNP 'Spoken' valid" );	
				}		
			}
			
			//Students With Large Print Accommodations
			if(largePrintBooklet /*|| magnification*/){
				accessibilityFlag.add("large_print");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'largePrintBooklet' or 'magnification' valid" );	
				
				//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - largePrintBooklet or magnification - PNP 'Spoken' valid" );	
				}
			}
			
			//Students With Braille Accommodations
			if(braille){
				accessibilityFlag.add("braille");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Braille' valid" );	
				
				//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - Braille - PNP 'Spoken' valid" );	
				}		
			}
			
			//Students With Only Spoken Accommodations
			if(spoken){
				if(userSpokenPreference){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Spoken', UserSpokenPreference - textandgraphics or nonvisual valid" );	
				}
			}
			
			//US16879, US16900
			if (signing && signingIsASL) {
				accessibilityFlag.add("signed");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Signing' valid" );	
				
				/*//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - Signing - PNP 'Spoken' valid" );	
				}*/
			}
		}
		return  accessibilityFlag;
	}
	
	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}


	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}


	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}


	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}


	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}


	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}


	public ContentArea getContentArea() {
		return contentArea;
	}


	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}


	public GradeCourse getGradeBand() {
		return gradeBand;
	}


	public void setGradeBand(GradeCourse gradeBand) {
		this.gradeBand = gradeBand;
	}


	public List<TestCollection> getTestCollections() {
		return testCollections;
	}


	public void setTestCollections(List<TestCollection> testCollections) {
		this.testCollections = testCollections;
	}


	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}


	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}


	public Organization getContractingOrganization() {
		return contractingOrg;
	}


	public void setContractingOrganization(Organization contractingOrg) {
		this.contractingOrg = contractingOrg;
	}


	public StepExecution getStepExecution() {
		return stepExecution;
	}


	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Map<String, AppConfiguration> getPltwBatchErrorMessageMap() {
		return pltwBatchErrorMessageMap;
	}

	public void setPltwBatchErrorMessageMap(Map<String, AppConfiguration> pltwBatchErrorMessageMap) {
		this.pltwBatchErrorMessageMap = pltwBatchErrorMessageMap;
	}
	
	public Stage getStageCode() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
