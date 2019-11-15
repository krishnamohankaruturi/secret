package edu.ku.cete.batch.dlm.survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.DLMStudentSurveyRosterDetailsDto;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;

public class DLMResearchSurveyProcessor implements ItemProcessor<DLMStudentSurveyRosterDetailsDto, DLMResearchSurveyWriterContext>{

	private final static Log logger = LogFactory.getLog(DLMResearchSurveyProcessor.class);
	
	private ContentArea contentArea;
	private Organization contractingOrganization;
	private StepExecution stepExecution;
	private String assessmentProgramCode;
	private Long operationalWindowId;	
	
	private Random random = new Random();
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private TestStatusConfiguration testStatusConfiguration;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private EnrollmentsRostersService enrollmentRostersService;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private ContentAreaService contentAreaService;

	private final static String FCS_COMPLETE_STATUS = "COMPLETE";
	
	@Override
	public DLMResearchSurveyWriterContext process(
			DLMStudentSurveyRosterDetailsDto dlmStudentSurveyRosterDetailsDto) throws Exception {
		DLMResearchSurveyWriterContext wCtx = processDLMResearchSurvey(dlmStudentSurveyRosterDetailsDto);
		if(wCtx == null) {
			stepExecution.setProcessSkipCount(stepExecution.getProcessSkipCount()+1);
		}
		return wCtx;		
	}
	
	@SuppressWarnings("unchecked")
	private DLMResearchSurveyWriterContext processDLMResearchSurvey(
			DLMStudentSurveyRosterDetailsDto dlmStudentSurveyRosterDetailsDto) {
		List<BatchRegistrationReason> reasons = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
		String rosterSubjects = null;
		if(!dlmStudentSurveyRosterDetailsDto.isStudentHaveRosters()) {	
			if(dlmStudentSurveyRosterDetailsDto.isStateHaveScienceFlagSet()) {
				if (CommonConstants.ASSESSMENT_PROGRAM_DLM.equals(assessmentProgramCode)) {
					rosterSubjects = "either Math or ELA or Sci";
				} else if (CommonConstants.ASSESSMENT_PROGRAM_I_SMART.equals(assessmentProgramCode)) {
					rosterSubjects = contentArea.getAbbreviatedName();
				}
				writeReason(dlmStudentSurveyRosterDetailsDto.getStudentId(), 
						String.format("Student " +  dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier()  
								+ " does not have a " + assessmentProgramCode + " roster for " +rosterSubjects+ "."), reasons);
				} else {
					rosterSubjects = "either Math or ELA";
					writeReason(dlmStudentSurveyRosterDetailsDto.getStudentId(), 
							String.format("Student " +  dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier()  
									+ " does not have a " + assessmentProgramCode + " roster for " +rosterSubjects+ "."), reasons);
			}			
			return null;
		}
		
		if(!StringUtils.equalsIgnoreCase(dlmStudentSurveyRosterDetailsDto.getStudentSurveyStatus(), FCS_COMPLETE_STATUS)) {
			writeReason(dlmStudentSurveyRosterDetailsDto.getStudentId(),
					("Student " + dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier() 
							+ " First Contact Band is not in a Complete state."), reasons);
			return null;
		}
		
		List<String> completedBands = new ArrayList<String>();
		
		//DLM uses ELA, M and sometimes Sci, but I-SMART is only IS-Sci
		if (CommonConstants.ASSESSMENT_PROGRAM_DLM.equals(assessmentProgramCode)) {
			if(dlmStudentSurveyRosterDetailsDto.getFinalElaBandId() != null) {			
				completedBands.add("ELA");
			}
			
			if(dlmStudentSurveyRosterDetailsDto.getFinalMathBandId() != null) {			
				completedBands.add("M");
			}
		}
		
		if(dlmStudentSurveyRosterDetailsDto.isStateHaveScienceFlagSet() && dlmStudentSurveyRosterDetailsDto.getFinalSciBandId() != null) {			
			// I-SMART uses a different content area abbreviation for their science rosters
			if(CommonConstants.ASSESSMENT_PROGRAM_I_SMART.equalsIgnoreCase(assessmentProgramCode)) {
				completedBands.add(CommonConstants.ISMART_SCIENCE);
			}else if(CommonConstants.ASSESSMENT_PROGRAM_I_SMART2.equalsIgnoreCase(assessmentProgramCode)) {
				completedBands.add(CommonConstants.ISMART_2_SCIENCE);
			}else {
				completedBands.add(CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_SCIENCE);
			}
		}

		
		// No subjects have completed bands, return null
		if(completedBands.isEmpty() ){
			writeReason(dlmStudentSurveyRosterDetailsDto.getStudentId(), ("Student " + dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier() 
			+ " does not have a final Complexity Band for " + rosterSubjects + "."), reasons);
			return null;
		}
		
		List<Long> rosterIds = enrollmentRostersService.getRosterIdsByEnrollmentIdAndContent(dlmStudentSurveyRosterDetailsDto.getEnrollmentId(), completedBands);
		
		Long rosterId;
		
		if(rosterIds.size() < 1){
			writeReason(dlmStudentSurveyRosterDetailsDto.getStudentId(), ("Student " + dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier() 
			+ " does not have any rosters for subjects they have a final Complexity Band completed."), reasons);
			return null;
			
		}else{
			// get a single random roster
			rosterId = rosterIds.get((new Random()).nextInt(rosterIds.size()));
		}
		
		
		Roster r = rosterService.selectByPrimaryKey(rosterId);
		ContentArea ca = contentAreaService.selectByPrimaryKey(r.getStateSubjectAreaId());
		
		List<String> subjectAbreaviatedNames = new ArrayList<String>();
		subjectAbreaviatedNames.add(ca.getAbbreviatedName());
		
		Map<TestCollection, List<Test>> testCollectionTests = new HashMap<TestCollection, List<Test>>();
		
		List<TestCollection> testCollections = getTestCollections(operationalWindowId, dlmStudentSurveyRosterDetailsDto.getCurrentGradeAbbrName(), contentArea.getId(), subjectAbreaviatedNames);		
		
		
		if(CollectionUtils.isEmpty(testCollections)) {			
			writeReason(dlmStudentSurveyRosterDetailsDto.getStudentId(), ("Student " + dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier() 
					+ " no test collections found for operationaltestwindowid " + operationalWindowId  + " and student grade " + dlmStudentSurveyRosterDetailsDto.getCurrentGradeAbbrName()), reasons);
			return null;
		}
				
				
		for(TestCollection testCollection: testCollections) {
			List<Test> tests = getTests(testCollection);
			if(CollectionUtils.isEmpty(tests)){
				writeReason(dlmStudentSurveyRosterDetailsDto.getStudentId(),
						("Student " + dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier() 
								+ " Tests are not found for operationaltestwindowid " + operationalWindowId  
								+ " and student grade." + dlmStudentSurveyRosterDetailsDto.getCurrentGradeAbbrName()+" and testcollections" + testCollection.getTestCollectionId()), reasons);				
			}else{
				testCollectionTests.put(testCollection, tests);
			}
		}
		
		
		
		DLMResearchSurveyWriterContext wCtx = new DLMResearchSurveyWriterContext();
		wCtx.setStatestudentidentifier(dlmStudentSurveyRosterDetailsDto.getStateStudentIdentifier());
		wCtx.setStudentId(dlmStudentSurveyRosterDetailsDto.getStudentId());
		wCtx.setAttendanceschoolId(dlmStudentSurveyRosterDetailsDto.getAttendanceSchoolId());
		wCtx.setContentAreaId(contentArea.getId());
		wCtx.setStudentFirstName(dlmStudentSurveyRosterDetailsDto.getStudentFirstName());
		wCtx.setStudentLastName(dlmStudentSurveyRosterDetailsDto.getStudentLastName());
		wCtx.setEnrollmentId(dlmStudentSurveyRosterDetailsDto.getEnrollmentId());
		wCtx.setSchoolYear(dlmStudentSurveyRosterDetailsDto.getSchoolYear());
		wCtx.setRosterId(rosterId);
		wCtx.setTestCollectionTests(testCollectionTests);
		wCtx.setAssessmentProgramCode(assessmentProgramCode);
		return wCtx;
		
	}
	
	private List<Test> getTests(TestCollection testCollection) {
		logger.debug("--> getTests" );
		List<Test> tests = testService.findTestsForDLMResearchSurvey(testCollection.getTestCollectionId(), 
        		testStatusConfiguration.getPublishedTestStatusCategory().getId());
		logger.debug("<-- getTests" );
		return tests;
	}
	
	private void writeReason(Long studentId, String msg, List<BatchRegistrationReason> reasons) {
		logger.debug("writeReason: studentid: "+studentId+", "+msg);
		BatchRegistrationReason reason = new BatchRegistrationReason();
		reason.setStudentId(studentId);
		reason.setReason(msg);
		reasons.add(reason);
	}
	
	private List<TestCollection> getTestCollections(Long operationalTestWindowId, 
			String gradeAbbrName, Long contentAreaId, List<String> stageCode) {
		
		List<TestCollection> testCollections = testCollectionService.getDLMResearchSurveyTestCollections(operationalTestWindowId, 
						gradeAbbrName, contentAreaId, stageCode);
		
		
		if(!testCollections.isEmpty()) {
			int index = random.nextInt(testCollections.size());
			
			return Arrays.asList(testCollections.get(index));
		}
		return testCollections;		
	}	
	
	/**
	 * @return the contentArea
	 */
	public ContentArea getContentArea() {
		return contentArea;
	}

	/**
	 * @param contentArea the contentArea to set
	 */
	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	/**
	 * @return the contractingOrganization
	 */
	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	/**
	 * @param contractingOrganization the contractingOrganization to set
	 */
	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	/**
	 * @return the stepExecution
	 */
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	/**
	 * @param stepExecution the stepExecution to set
	 */
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	/**
	 * @return the assessmentProgramCode
	 */
	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	/**
	 * @param assessmentProgramCode the assessmentProgramCode to set
	 */
	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}	

	/**
	 * @return the operationalWindowId
	 */
	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	/**
	 * @param operationalWindowId the operationalWindowId to set
	 */
	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}
}
