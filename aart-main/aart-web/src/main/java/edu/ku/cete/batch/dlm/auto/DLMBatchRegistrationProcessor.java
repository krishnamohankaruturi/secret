package edu.ku.cete.batch.dlm.auto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.dlm.st.StudentTrackerHelper;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.TestSpecification;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.util.CommonConstants;

public class DLMBatchRegistrationProcessor implements ItemProcessor<StudentTracker,DLMAutoWriterContext> {
	
	private static final String MULTIEEOFC = "MULTIEEOFC";

	private final static Log logger = LogFactory .getLog(DLMBatchRegistrationProcessor.class);
	

	@Autowired
	private StudentTrackerService trackerService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private TestStatusConfiguration testStatusConfiguration;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private EnrollmentService enrollmentService;	
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	private Organization contractingOrganization;
	private ContentArea contentArea;
	private List<ComplexityBand> allBands;
	private StepExecution stepExecution;
	private Long operationalWindowId;
	
	private Random randomGenerator = new Random();

	@Override
	public DLMAutoWriterContext process(StudentTracker trackerRecord) throws Exception {
		DLMAutoWriterContext ctx = null;
		if(trackerRecord.getRecommendedBand().getTestSessionId() == null) {
			StudentTrackerBand latestBand = trackerService.getLatestTrackerBand(trackerRecord.getId());
			if(latestBand.getTestSessionId()==null) {
				ctx = processMultiEE(trackerRecord);
			}
		}
		if(ctx == null) {
			stepExecution.setProcessSkipCount(stepExecution.getProcessSkipCount()+1);
		}
		return ctx;
	}

	@SuppressWarnings("unchecked")
	private DLMAutoWriterContext processMultiEE(StudentTracker trackerRecord) {
		List<BatchRegistrationReason> reasons = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
		Enrollment enrollment = getEnrollment(trackerRecord, contentArea.getId());
		String message = StringUtils.EMPTY;
		String poolType = contractingOrganization.getPoolType();
		if(enrollment == null) {
			message = StudentTrackerHelper.constructCommonMessage("No enrollment found for student: ", trackerRecord, poolType,
					operationalWindowId, contractingOrganization, null, null, null, null);
			writeReason(trackerRecord, message, reasons);
			return null;
		}
		trackerRecord.setEnrollment(enrollment);

		/* This change was made by Rohit Yadav on April 4, 2019
		 * If a student is in NJ, grade 12, rostered to ELA/Math, they get treated like an 11th grader for autoenrollment
		*/
		if(StringUtils.equalsIgnoreCase("NJ", contractingOrganization.getDisplayIdentifier()) 
 		  &&  (
		        StringUtils.equalsIgnoreCase(CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_ELA, contentArea.getAbbreviatedName()) ||
			StringUtils.equalsIgnoreCase(CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_MATH, contentArea.getAbbreviatedName())
		      )
		  && "12".equals(enrollment.getGradeCourse().getName())) {
			GradeCourse gradeCourseNJBatch = gradeCourseService.getByContentAreaAndAbbreviatedName(contentArea.getId(),"11");
			trackerRecord.setGradeCourseId(gradeCourseNJBatch.getId());
			trackerRecord.setGradeCode(gradeCourseNJBatch.getAbbreviatedName());
		}else {
			// enrollment.getGradeCourse().getName() returns the AbbreviatedName and not the actual name
			trackerRecord.setGradeCode(enrollment.getGradeCourse().getName());
			trackerRecord.setGradeCourseId(enrollment.getGradeCourse().getId());
		}
		
		String orgPoolType = StudentTrackerHelper.getPoolType(contractingOrganization, trackerRecord.getGradeCode(), contentArea.getAbbreviatedName());
		ComplexityBand recommendedBand = getBandById(allBands, trackerRecord.getRecommendedBand().getComplexityBandId());
		
		Roster roster = getRoster(trackerRecord, orgPoolType, trackerRecord.getCourseId());
		if(roster == null) {
			if(MULTIEEOFC.equals(orgPoolType)) {
				message = StudentTrackerHelper.constructCommonMessage("No course based roster found for student: ", trackerRecord, poolType,
						operationalWindowId, contractingOrganization, null, null, trackerRecord.getGradeCode(), null);
				writeReason(trackerRecord, message, reasons);
			} else {
				message = StudentTrackerHelper.constructCommonMessage("No roster found for student: ", trackerRecord, poolType,
						operationalWindowId, contractingOrganization, null, null, trackerRecord.getGradeCode(), null);
				writeReason(trackerRecord, message, reasons);
			}
			return null;
		}
		String gcCode = getGradeCourseAbbr(roster, orgPoolType, trackerRecord.getGradeCode());
		
		TestSpecification testSpec = trackerService.getTestSpecByPoolGradeContentArea(orgPoolType, gcCode, contentArea.getId(), operationalWindowId);
		
		if(testSpec == null) {
			message = StudentTrackerHelper.constructCommonMessage("There is no Test Specification avaialble for student: ", trackerRecord, poolType,
					operationalWindowId, null, null, null, null, null);
			writeReason(trackerRecord, message, reasons);
			return null;
		}
		
		TestSpecification stTestSpec = null;
		int numberOfBandsAssigned = 0;
		List<TestSpecification> stTestSpecs = trackerService.getTestSpecByStudentTracker(trackerRecord.getId(), operationalWindowId);
		if(stTestSpecs.size() > 1) {
			List<String> testSpecNames = new ArrayList<String>(stTestSpecs.size());
			for(TestSpecification ts : stTestSpecs) {
				testSpecNames.add(ts.getSpecificationName());
			}
			message = StudentTrackerHelper.constructMessage("Multiple test overview's found for student: ", trackerRecord,
					operationalWindowId, null, testSpecNames);
			writeReason(trackerRecord, message, reasons);
			return null;
		} else if(stTestSpecs.size() > 0) {
			stTestSpec = stTestSpecs.get(0);
		}
		
		//check if it is same test spec and verify if min number of ee's completed 
		//else student will be started on new test spec
		if(stTestSpec != null && stTestSpec.getId().equals(testSpec.getId())) {
			numberOfBandsAssigned = trackerService.getCountOfBandsByStudentTracker(trackerRecord.getId(), testSpec.getId(), operationalWindowId);
			if(numberOfBandsAssigned >= testSpec.getMinimumNumberOfEEs().intValue()) {
				message = StudentTrackerHelper.constructCommonMessage("Minimum number of EE's met for : studentId: ", trackerRecord, 
						poolType, operationalWindowId, contractingOrganization, null, null, gcCode, testSpec.getId());
				writeReason(trackerRecord, message, reasons);
				return null;
			}
		} 

		numberOfBandsAssigned++; //increment group number to assign
		TestCollection testCollection = null;
		List<String> contentCodes = new ArrayList<String>();
		if(orgPoolType.equalsIgnoreCase("SINGLEEE")){
			contentCodes.add(trackerRecord.getRecommendedBand().getEssentialElementCode());
			if(contentCodes.isEmpty()){
				message = StudentTrackerHelper.constructContentCodeMessage("No content codes found for ", testSpec, numberOfBandsAssigned);
				writeReason(trackerRecord, message, reasons);
			} else {
				testCollection = getTestCollection(trackerRecord, recommendedBand, contentCodes, testSpec, gcCode, orgPoolType);
				if(testCollection == null) {
					message = StudentTrackerHelper.constructNoTestCollectionMessage("No Test collection found for ", orgPoolType, gcCode, contentArea,
							contentCodes, numberOfBandsAssigned, recommendedBand.getId(), testSpec, null);
					writeReason(trackerRecord, message, reasons);
					return null;
				}
			}
		}else{
			//Checking for test collection and stopping the process if no test collection found for the required ranking
			if(numberOfBandsAssigned<=testSpec.getMinimumNumberOfEEs().intValue()) {
				contentCodes = getContentCodes(trackerRecord.getId(), testSpec.getId(), numberOfBandsAssigned);
				if(contentCodes.isEmpty()){
					message = StudentTrackerHelper.constructContentCodeMessage("No content codes found for ", testSpec, numberOfBandsAssigned);
					writeReason(trackerRecord, message, reasons);
				} else {
					testCollection = getTestCollection(trackerRecord, recommendedBand, contentCodes, testSpec, gcCode, orgPoolType);
					if(testCollection == null) {
						message = StudentTrackerHelper.constructNoTestCollectionMessage("No Test collection found for ", orgPoolType, gcCode, contentArea,
								contentCodes, numberOfBandsAssigned, recommendedBand.getId(), testSpec, null);
						writeReason(trackerRecord, message, reasons);
						return null;
					}
				}
			}
		}
		
		List<Test> tests = getTests(trackerRecord.getStudentId(), testCollection, contentCodes, testSpec.getId());
		if(tests.isEmpty()){
			message = StudentTrackerHelper.constructNoTestCollectionMessage("No Tests found for ", orgPoolType, gcCode, contentArea,
					contentCodes, numberOfBandsAssigned, recommendedBand.getId(), testSpec, null);
			writeReason(trackerRecord, message, reasons);
			return null;
		}
		
		DLMAutoWriterContext ctx = new DLMAutoWriterContext();
		ctx.setRosterId(roster.getId());
		ctx.setTestCollection(testCollection);
		ctx.setTests(tests);
		ctx.setNumberOfTestsRequired(testSpec.getMinimumNumberOfEEs());
		ctx.setCurrentTestNumber(numberOfBandsAssigned);
		ctx.setStudentTracker(trackerRecord);
		ctx.setComplexityBand(recommendedBand);
		return ctx;
	}
	
	private Roster getRoster(StudentTracker trackerRecord, String poolType, Long courseId) {
		List<Roster> rosters = rosterService.getRostersByContentAreaAndEnrollment(contentArea.getId(), trackerRecord.getEnrollment());
		
		List<Roster> finalRosters = new ArrayList<Roster>();
		//if MULTIEEOFC look for course roster
		if(MULTIEEOFC.equals(poolType)) {
			for(Roster roster: rosters) {
				if(roster.getStateCoursesId() != null && courseId != null && courseId.equals(roster.getStateCoursesId())) {
					finalRosters.add(roster);
				}
			}
		} else {
			finalRosters.addAll(rosters);
		}
		
		//check if any proctor roster exists
		for(Roster roster: finalRosters) {
			if(roster.getSourceType() != null && roster.getSourceType().equals("TEST")) {
				return roster;
			}
		}
		
		if(!finalRosters.isEmpty()) {
			//if no proctor return one of the roster
			return finalRosters.get(0);
		}
		return null;
	}
	
	
	private Enrollment getEnrollment(StudentTracker trackerRecord, Long contentAreaId) {
		Enrollment enrollment = enrollmentService.getEnrollmentByStudentId(trackerRecord.getStudentId(), contractingOrganization.getId(), 
				contractingOrganization.getCurrentSchoolYear(), contentAreaId);
		return enrollment;
	}	

	private void writeReason(StudentTracker trackerRecord, String msg, List<BatchRegistrationReason> reasons) {
		BatchRegistrationReason reason = new BatchRegistrationReason();
		reason.setStudentId(trackerRecord.getStudentId());
		reason.setReason(msg);
		reasons.add(reason);
	}
	
	private ComplexityBand getBandById(List<ComplexityBand> allBands, Long bandId) {
		for (ComplexityBand band : allBands) {
			if (band.getId().equals(bandId)) {
				return band;
			}
		}
		return null;
	}
	
	private List<String> getContentCodes(Long trackerRecordId, Long testSpecId, int ranking) {
		List<String> contentCodes = new ArrayList<String>();
		List<String> testSpecContentCodes = trackerService.getContentCodesByTestSpecAndRandking(testSpecId, (long) ranking);
		List<String> studentTrackerBandEEs = trackerService.getEEsByStudentTracker(trackerRecordId, testSpecId, operationalWindowId);
		for(String testSpecContentCode:testSpecContentCodes) {
			if(!studentTrackerBandEEs.contains(testSpecContentCode)) {
				contentCodes.add(testSpecContentCode);
			}
		}
		return contentCodes;
	}
	
	private TestCollection getTestCollection(StudentTracker trackerStudent, ComplexityBand band, List<String> contentCodes, TestSpecification testSpec, 
			String gradeCourseCode, String poolType){

		float linkageLevelLowerBound = band.getMinRange().floatValue();
		float linkageLevelUpperBound = band.getMaxRange().floatValue();

		List<TestCollection> testCollections = testCollectionService.getTestCollectionsForDlmAuto(linkageLevelLowerBound, linkageLevelUpperBound, 
				gradeCourseCode, contentArea.getAbbreviatedName(), trackerStudent.getStudentId(), contentCodes, 
				poolType, testSpec.getId(), trackerStudent.getRecommendedBand().getOperationalWindowId());
		
		if(!testCollections.isEmpty()) {
			return testCollections.get(randomGenerator.nextInt(testCollections.size()));
		}
		return null;
	}
	
	private String getGradeCourseAbbr(Roster roster, String poolType, String gradeCode) {
		logger.debug("returning getGradeCourseAbbr for poolType: "+poolType);
		if(MULTIEEOFC.equals(poolType)) {
			return roster.getCourse().getAbbreviatedName();
		} else {
			return gradeCode;
		}
	}
	
	
	private List<Test> getTests(Long studentId, TestCollection testCollection, List<String> contentCodes, Long testSpecId) {
		logger.debug("--> getTests" );
		List<Test> tests = testService.findQCTestsAccFlagsByTestCollectionAndStatus(testCollection.getId(), testStatusConfiguration.getPublishedTestStatusCategory().getId(), 
				contentCodes, testSpecId);
		List<Test> filteredTests = studentsTestsService.dlmFilterTestsBasedOnPNP(studentId, tests);
		logger.debug("<-- getTests" );
		return filteredTests;
	}

	 
	
	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}
	
	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public List<ComplexityBand> getAllBands() {
		return allBands;
	}

	public void setAllBands(List<ComplexityBand> allBands) {
		this.allBands = Collections.unmodifiableList(allBands);
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}
	
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}

}
