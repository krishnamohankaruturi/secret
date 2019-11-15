/**
 * 
 */
package edu.ku.cete.test.reports;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.StudentReportTestScores;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.ExcludedItemsService;
import edu.ku.cete.service.report.RawToScaleScoresService;
import edu.ku.cete.service.report.SubscoreFrameworkService;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.StageEnum;

/**
 * @author ktaduru_sta
 *
 */
public class CalculateSubscoreRawScoreTest extends BaseTest {

	final static Log logger = LogFactory.getLog(CalculateSubscoreRawScoreTest.class);
	@Autowired
	private TestService testService;
	
	@Autowired
	private ExcludedItemsService excludedItemsService;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	private StepExecution stepExecution;
	
	@Autowired
	private SubscoreFrameworkService subscoreFrameworkService;
	
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private SubscoreRawToScaleScoresService subscoreRawToScaleScoresService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void testGetStudentTestScores() {
		StudentReport studentReport = getStudentResponses(891766l, 1093142l, "KAP", 12l, "ELA", 3l, "3", 133l, 5748l, null, null, null, null);
		assertTrue("No Completed/InProgress tests/responses found for student: "+studentReport.getStudentId(), studentReport.getStudentTestsScore().size() > 0);
		
	}	
	
	@Test
	public void testCalculateSubscoreRawScore() {
		StudentReport studentReport = getStudentResponses(891754l, 1093130l, "KAP", 12l, "ELA", 3l, "3", 133l, 5761l, 5743l, 5784l, 5758l, null);		
		calculateRawScore(studentReport);
		calculateSubscoreRawScore(studentReport);
		logger.debug("************** Subscore list size: "+studentReport.getSubscoreBuckets().size()+" ************");
		for(int i=0; i<studentReport.getSubscoreBuckets().size(); i++){
			String msg = "Subscore Rawscore for "+studentReport.getSubscoreBuckets().get(i).getSubscoreDefinitionName()+" is: "+ studentReport.getSubscoreBuckets().get(i).getSubscoreRawScore()
					+" - TotalItems: "+studentReport.getSubscoreBuckets().get(i).getTotalItems()
					+" - RespondedItems: "+studentReport.getSubscoreBuckets().get(i).getItemsResponded();
			
			logger.debug(msg);
		}
		logger.debug("************** Complete **************");
		assertTrue("Can not calcuate subscore...", studentReport.getSubscoreBuckets().size()>0);	
		
	}	
	
	//Math grade 8 with performance test 
	@Test
	public void testCalculateSubscoreRawScoreForMathWithPerformanceTest() {
		StudentReport studentReport = getStudentResponses(892134l, 1093722l, "KAP", 12l, "M", 440l, "8", 149l, 5859l, null, null, null, 6233l);		
		calculateRawScore(studentReport);
		calculateSubscoreRawScore(studentReport);
		
		logger.debug("************** Subscore list size: "+studentReport.getSubscoreBuckets().size()+" ************");
		for(int i=0; i<studentReport.getSubscoreBuckets().size(); i++){
			String msg = "Subscore Rawscore for "+studentReport.getSubscoreBuckets().get(i).getSubscoreDefinitionName()+" is: "+ studentReport.getSubscoreBuckets().get(i).getSubscoreRawScore()
					+" - TotalItems: "+studentReport.getSubscoreBuckets().get(i).getTotalItems()
					+" - RespondedItems: "+studentReport.getSubscoreBuckets().get(i).getItemsResponded();
			
			logger.debug(msg);
		}
		logger.debug("************** Complete **************");
		assertTrue("Can not calcuate subscore...", studentReport.getSubscoreBuckets().size()>0);	
		
	}	
	
	@Test
	public void testMapRawscoreToRating() {		
		
		StudentReport studentReport = getStudentResponses(891754l, 1093130l, "KAP", 12l, "ELA", 3l, "3", 133l, 5761l, 5743l, null, null, null);		
		calculateRawScore(studentReport);
		calculateSubscoreRawScore(studentReport);
		List<Long> testIds = new ArrayList<Long>();
		List<ReportSubscores> subscores = studentReport.getSubscoreBuckets();
		testIds.add(studentReport.getExternalTest1Id());
		testIds.add(studentReport.getExternalTest2Id());
		testIds.add(studentReport.getExternalTest3Id());
		testIds.add(studentReport.getExternalTest4Id());
		testIds.add(studentReport.getPerformanceTestExternalId());
		
		assertNotNull("Subscores not found***********", subscores);
		
		assertTrue("Subscore rawscore "+subscores.get(0).getSubscoreRawScore()+ " is incorrect for: "+subscores.get(0).getSubscoreDefinitionName(), subscores.get(0).getSubscoreRawScore().compareTo(new BigDecimal("8")) == 0);
		
		SubscoreRawToScaleScores subscoreRawToScaleObject = subscoreRawToScaleScoresService.getRatingForSubscoreRawScore(testIds, subscores.get(0).getSubscoreDefinitionName(), subscores.get(0).getSubscoreRawScore(), 
				studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest1Id(), studentReport.getExternalTest2Id(), studentReport.getExternalTest3Id(), 
				studentReport.getExternalTest4Id(), studentReport.getPerformanceTestExternalId());
		String msg = "Rawscore " + subscores.get(0).getSubscoreRawScore() + " not found for subscore definition: "+subscores.get(0).getSubscoreDefinitionName()+" in Subscore Raw To Scale score Upload file for Assessment Program - " 
				+ studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId();					
				
				//logger.debug(msg + ". Skipping this student");
		assertNotNull(msg, subscoreRawToScaleObject);
		
	}
	
	public StudentReport getStudentResponses(Long studentId, Long enrollmentId, String assessmentProramCode, Long assessmentProgramId, String contentAreaCode, Long contentAreaId, String grade, Long gradeCourseId,
			Long test1Id, Long test2Id, Long test3Id, Long test4Id, Long prfrmTestId) {
		
		StudentReport studentReport = getStudentReport(studentId, enrollmentId, assessmentProramCode, assessmentProgramId, contentAreaCode, contentAreaId, grade, gradeCourseId);
		
		List<Long> testsStatusIds = new ArrayList<Long>();
		List<Long> rawScaleExternalTestIds = new ArrayList<Long>();
		Long testsCompletedStatusId = categoryService.selectByCategoryCodeAndType("complete",  "STUDENT_TEST_STATUS").getId();
	    Long testsInprogressStatusId = categoryService.selectByCategoryCodeAndType("inprogress",  "STUDENT_TEST_STATUS").getId();
	    Long testsInprogressTimedoutStatusId = categoryService.selectByCategoryCodeAndType("inprogresstimedout",  "STUDENT_TEST_STATUS").getId();
	    Long testsUnusedStatusId = categoryService.selectByCategoryCodeAndType("unused",  "STUDENT_TEST_STATUS").getId();
	    Long testsPendingStatusId = categoryService.selectByCategoryCodeAndType("pending",  "STUDENT_TEST_STATUS").getId();
	    Category specialCircumstanceStatusSavedCategory = categoryService.selectByCategoryCodeAndType("SAVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Category specialCircumstanceStatusApprovedCategory = categoryService.selectByCategoryCodeAndType("APPROVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Long specialCircumstanceStatusApprovedId = specialCircumstanceStatusApprovedCategory.getId();
		Long specialCircumstanceStatusSavedId = specialCircumstanceStatusSavedCategory.getId();
		testsStatusIds.add(testsInprogressStatusId);
		testsStatusIds.add(testsCompletedStatusId);
		testsStatusIds.add(testsInprogressTimedoutStatusId);
		testsStatusIds.add(testsUnusedStatusId);
		testsStatusIds.add(testsPendingStatusId);
		
		if(test1Id != null){
			rawScaleExternalTestIds.add(test1Id);
		}
		if(test2Id != null){
			rawScaleExternalTestIds.add(test2Id);
		}
		if(test3Id != null){
			rawScaleExternalTestIds.add(test3Id);
		}
		if(test4Id != null){
			rawScaleExternalTestIds.add(test4Id);
		}
		if(prfrmTestId != null){
			rawScaleExternalTestIds.add(prfrmTestId);
		}
		
		List<Long> specialCircumstanceStatusIds = new ArrayList<Long>();
		specialCircumstanceStatusIds.add(specialCircumstanceStatusApprovedId);
		specialCircumstanceStatusIds.add(specialCircumstanceStatusSavedId);
		
		List<Long> enrollmentIds = new ArrayList<Long>();
		for (Enrollment e : studentReport.getEnrollments()) {
			enrollmentIds.add(e.getId());
		}
		
		List<StudentReportTestResponses> studentTestsScore = testService.getTestsScoreByStudentIdExternalTestIds(studentReport.getStudentId(),
				rawScaleExternalTestIds, enrollmentIds, testsStatusIds, specialCircumstanceStatusIds, 51l, 631l);	
		
		if(CollectionUtils.isEmpty(studentTestsScore)){
			List<StudentsResponses> stg1CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg1OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg2CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg2OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg3CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg3OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg4CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg4OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> performanceCompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> performanceOtherstudentsResponses = new ArrayList<StudentsResponses>();
			
			for(StudentReportTestResponses studentReportTestResponse : studentTestsScore){
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("stg1")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;
					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg1CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg1OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg1CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg1CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg1OtherstudentsResponses);
					}

				}
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("stg2")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg2CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg2OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg2CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg2CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg2OtherstudentsResponses);
					}

				}
				
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("stg3")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg3CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg3OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg3CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg3CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg3OtherstudentsResponses);
					}

				}
				
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("stg4")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg4CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg4OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg4CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg4CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg4OtherstudentsResponses);
					}

				}
				
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("prfrm")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								performanceCompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							performanceOtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(performanceCompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(performanceCompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(performanceOtherstudentsResponses);
					}

				}
				
			}
		}			
			
		studentReport.setStudentTestsScore(studentTestsScore);
		return studentReport;
	}
	
	
	public StudentReport getStudentReport(Long studentId, Long enrollmentId, String assessmentProgramCode, Long assessmentProgramId, String subject, Long subjectId, String gradeCourse, Long gradeCourseId) {
		StudentReport studentReport = new StudentReport();
		
		studentReport.setAssessmentProgramCode(assessmentProgramCode);
		studentReport.setAssessmentProgramId(assessmentProgramId);
		studentReport.setStudentId(studentId);
		studentReport.setEnrollmentId(enrollmentId);
		studentReport.setStudentFirstName("Firstname");
		studentReport.setStudentLastName("Lastname");
		studentReport.setStateStudentIdentifier("23232323232");
		studentReport.setStateId(51L);
		studentReport.setDistrictId(361L);
		studentReport.setAttendanceSchoolName("Roosevelt Middle");
		studentReport.setAttendanceSchoolId(2890l);
		studentReport.setDistrictName("Coffeyville");
		studentReport.setDistrictDisplayIdentifier("D0445");
		studentReport.setGradeCode(gradeCourse);
		studentReport.setGradeId(gradeCourseId);
		studentReport.setContentAreaCode(subject);
		studentReport.setContentAreaId(subjectId);
		studentReport.setCurrentSchoolYear(2016L);
		studentReport.setEnrollmentActive(true);
		studentReport.setStage1HasSCCode(false);
		studentReport.setStage2HasSCCode(false);
		studentReport.setStage3HasSCCode(false);
		studentReport.setStage4HasSCCode(false);
		studentReport.setPrfrmStageHasSCCode(false);
		
		return studentReport;
	}
	
	public StudentReport calculateSubscoreRawScore(StudentReport studentReport) {
		
		List<StudentReportTestResponses> studentReportTestResponses = studentReport.getStudentTestsScore();
		Map<String, ReportSubscores> subscoreBucketMap = new HashMap<String, ReportSubscores>();		
		List<ReportSubscores> testLevelSubscoresList =new ArrayList<ReportSubscores>();
		Long testSectionStatusCompletedId = 127l;
		Long testSectionStatusInProgressId = 126l;  
		List<Long> subscoreRawScaleExternalTestIds = new ArrayList<Long>();
		if("ELA".equalsIgnoreCase(studentReport.getContentAreaCode())){
			subscoreRawScaleExternalTestIds.add(5761l);
			subscoreRawScaleExternalTestIds.add(5758l);
			subscoreRawScaleExternalTestIds.add(5743l);
			subscoreRawScaleExternalTestIds.add(5784l);
		}else{
			subscoreRawScaleExternalTestIds.add(5859l);
			//subscoreRawScaleExternalTestIds.add(5743l);
			//subscoreRawScaleExternalTestIds.add(5784l);
			//subscoreRawScaleExternalTestIds.add(5758l);
			subscoreRawScaleExternalTestIds.add(6233l);
		}		
		
		if(CollectionUtils.isNotEmpty(studentReportTestResponses)){
			boolean stage1Through3SCCodePresent = Boolean.TRUE.equals(studentReport.getStage1HasSCCode()) ||
					Boolean.TRUE.equals(studentReport.getStage2HasSCCode()) ||
					Boolean.TRUE.equals(studentReport.getStage3HasSCCode());
			boolean performanceSCCodePresent = studentReport.getPrfrmStageHasSCCode();
			
			List<String> stage1Through3Codes = Arrays.asList(
					StageEnum.STAGE1.getCode(),
					StageEnum.STAGE2.getCode(),
					StageEnum.STAGE3.getCode());
			
			for(StudentReportTestResponses studentReportTestResponse : studentReportTestResponses){
				boolean isStage1Through3 = stage1Through3Codes.contains(studentReportTestResponse.getStageCode());
				boolean isPerformance = StageEnum.PERFORMANCE.getCode().equals(studentReportTestResponse.getStageCode());
				
				if ((isStage1Through3 && stage1Through3SCCodePresent) || (isPerformance && performanceSCCodePresent)) {
					logger.debug("SC Code was applied for student + " + studentReport.getStudentId() + " for stage " +
							studentReportTestResponse.getStageCode() + "...skipping this record");
					continue;
				}
				
				Map<Long, BigDecimal> variantScoreMap = new HashMap<Long, BigDecimal>();
				Map<Long, Long> variantIDsMap = new HashMap<Long, Long>();
				Long testID = studentReportTestResponse.getTestExternalId();
				if(studentReport.getExternalTest1Id()!=testID && studentReport.getExternalTest2Id()!=testID
						&& studentReport.getExternalTest3Id()!=testID && studentReport.getExternalTest4Id()!=testID && studentReport.getPerformanceTestExternalId()!=testID){
					logger.debug("Test ID "+testID+" skipped for student "+studentReport.getStudentId()+" after the exclusion and stage level calculations in CalculateRawCutScore.");
					logger.debug("Skipping this record.");
				}
				
				//External IDs considered for subscore raw score calculations are a subset of the external IDs for Raw score calculations
				//Example: Science Test IDs are used in Raw score calculations but subscore raw scores are not calculated for them 
				else if(!subscoreRawScaleExternalTestIds.contains(testID)){
					logger.debug("Test ID "+testID+" for student "+studentReport.getStudentId()+" not found in Subscore Raw To Scale score Upload file.");
					logger.debug("Skipping this record.");
					
				}else{
					
					List<StudentsResponses> studentResponsesList = studentReportTestResponse.getStudentResponses();
					
					List<ReportSubscores> testLevelSubscoresListTemp = batchReportProcessService.getItemCountBySubscoreDefinitionNameByTestId(studentReportTestResponse.getTestId(), studentReport.getCurrentSchoolYear(), 
							studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId());
					Map<String, ReportSubscores> testLevelSubscoreBucketMap = new HashMap<String, ReportSubscores>();
					
					for(StudentsResponses responses : studentResponsesList){
						if(responses.getSectionStatus().equals(testSectionStatusCompletedId) || responses.getSectionStatus().equals(testSectionStatusInProgressId)){
							if(responses.getTaskVariantExternalId() != null && responses.getScore() != null){
								variantIDsMap.put(responses.getTaskVariantExternalId(), responses.getTaskVariantId());
								//Handle duplicate items
								BigDecimal existingScore = variantScoreMap.get(responses.getTaskVariantExternalId());
								if(existingScore != null){
									variantScoreMap.put(responses.getTaskVariantExternalId(), existingScore.add(responses.getScore()));
								}else{
									variantScoreMap.put(responses.getTaskVariantExternalId(), responses.getScore());
								}
	 						}
						}
					}
				
					if(CollectionUtils.isNotEmpty(variantScoreMap.keySet())){
						List<ExcludedItems> excludedItems= excludedItemsService.getByTaskVariantIds(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), 
						studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), variantScoreMap.keySet(), studentReport.getTestingProgramId(), null);
						if(CollectionUtils.isNotEmpty(excludedItems)){       
							for(ExcludedItems eItems : excludedItems){
								variantScoreMap.remove(eItems.getTaskVariantId());
							}
						}
						
						for(Long taskVariantExternalId : variantScoreMap.keySet()){
							Long taskVariantId = variantIDsMap.get(taskVariantExternalId);
							List<String> subscoreBucketNames = subscoreFrameworkService.mapSubscoreDefinitionFromTaskVariant(studentReport.getSchoolYear(), taskVariantId, studentReport.getAssessmentProgramId(),
									studentReport.getContentAreaId(), studentReport.getGradeId());					
							if(subscoreBucketNames.size()>0){
								BigDecimal responseScore = variantScoreMap.get(taskVariantExternalId);
								if(responseScore != null){
									for(String subscoreBucketName: subscoreBucketNames){
										logger.debug(subscoreBucketName+" "+responseScore);
										
										if(subscoreBucketMap.get(subscoreBucketName)==null)
										{
											logger.debug("TestID: "+testID+" subscoreDefName: "+subscoreBucketName);
											
											ReportSubscores subscoreBucket = new ReportSubscores();
											subscoreBucket.setStudentId(studentReport.getStudentId());
											subscoreBucket.setSubscoreDefinitionName(subscoreBucketName);
											subscoreBucket.setSubscoreRawScore(new BigDecimal(0));
											subscoreBucket.setCreatedDate(new Date());
											subscoreBucketMap.put(subscoreBucketName, subscoreBucket);
										}
										
										BigDecimal newRawScore = subscoreBucketMap.get(subscoreBucketName).getSubscoreRawScore().add(responseScore);
										logger.debug(newRawScore);
										subscoreBucketMap.get(subscoreBucketName).setSubscoreRawScore(newRawScore);
										
										if(testLevelSubscoreBucketMap.get(subscoreBucketName) == null){
											logger.debug("TestID: "+testID+" Test Level subscoreDefName: "+subscoreBucketName);
											
											ReportSubscores testLevelSubscoreBucket = new ReportSubscores();
											testLevelSubscoreBucket.setStudentId(studentReport.getStudentId());
											testLevelSubscoreBucket.setSubscoreDefinitionName(subscoreBucketName);
											testLevelSubscoreBucket.setSubscoreRawScore(new BigDecimal(0));
											testLevelSubscoreBucket.setCreatedDate(new Date());
											testLevelSubscoreBucket.setItemsResponded(0);
											testLevelSubscoreBucketMap.put(subscoreBucketName, testLevelSubscoreBucket);
										}
										
										
										Integer newRespondedItemCount = testLevelSubscoreBucketMap.get(subscoreBucketName).getItemsResponded() + 1;
										BigDecimal newSubscoreBucketRawScore = testLevelSubscoreBucketMap.get(subscoreBucketName).getSubscoreRawScore().add(responseScore);
										
										testLevelSubscoreBucketMap.get(subscoreBucketName).setSubscoreRawScore(newSubscoreBucketRawScore);
										testLevelSubscoreBucketMap.get(subscoreBucketName).setItemsResponded(newRespondedItemCount);
										for(ReportSubscores subscore : testLevelSubscoresListTemp){
											if(testLevelSubscoreBucketMap.get(subscoreBucketName).getSubscoreDefinitionName().equalsIgnoreCase(subscore.getSubscoreDefinitionName())){
												subscore.setSubscoreRawScore(newSubscoreBucketRawScore);
												subscore.setItemsResponded(newRespondedItemCount);
											}
										}
										
									}
								}
							}
						}//For each task variant
					}//IF task variants exist for each testID
					if(CollectionUtils.isNotEmpty(testLevelSubscoresListTemp)){
						testLevelSubscoresList.addAll(testLevelSubscoresListTemp);	
					}								
				
				}			
				
			}//For each testID
		}//IF student responses exist
		List<ReportSubscores> subscores =new ArrayList<ReportSubscores>(subscoreBucketMap.values());
		for(ReportSubscores subscore : subscores){
			BigDecimal rawSubscore = subscore.getSubscoreRawScore();
			subscore.setSubscoreRawScore(rawSubscore.setScale(0, BigDecimal.ROUND_HALF_UP));
			int totalItems = 0;
			int itemsResponded = 0;
			for(ReportSubscores testLevelSubscore : testLevelSubscoresList){
				if(testLevelSubscore.getSubscoreDefinitionName().equalsIgnoreCase(subscore.getSubscoreDefinitionName())){
					totalItems = totalItems + testLevelSubscore.getTotalItems();
					itemsResponded = itemsResponded + testLevelSubscore.getItemsResponded();
				}
				
			}
			subscore.setTotalItems(totalItems);
			subscore.setItemsResponded(itemsResponded);
		}
		studentReport.setSubscoreBuckets(subscores);
		studentReport.setTestLevelSubscoreBuckets(testLevelSubscoresList);
		logger.debug("Completed CalculateSubscoresRawScore.");
    	return studentReport;
	}
	
	@SuppressWarnings("unchecked")
	public StudentReport calculateRawScore(StudentReport studentReport) {
		//StudentReportDto studentReport = getStudentResponses(891766l, 1093142l, "KAP", 12l, "ELA", 3l, "3", 133l, 5748l, null, null, null, null);
		
		studentReport.setStatus(true);
		studentReport.setExitStatus(false);
		studentReport.setIncompleteStatus(false);
		studentReport.setAggregates(true);
		List<Long> testIds = new ArrayList<Long>();
		StudentReportTestScores studentReportTestScores = new StudentReportTestScores();
		Long testSectionStatusCompletedId = 127l;
		Long testSectionStatusInProgressId = 126l;    
		int taskVariantCount = 0;
		int responseCount = 0;
		
		List<StudentReportTestResponses> studentReportTestResponses = studentReport.getStudentTestsScore();
		
		BigDecimal rawCutScore = new BigDecimal(0);
		
		Map<String, BigDecimal> stageTestScore = new HashMap<String, BigDecimal>();
		if(!studentReport.getEnrollmentActive() && studentReport.getExitWithDrawalDate() != null){
				studentReport.setExitStatus(true);
		} 
		for(StudentReportTestResponses studentReportTestResponse : studentReportTestResponses){
			Map<Long, BigDecimal> variantScoreMap = new HashMap<Long, BigDecimal>();
			List<StudentsResponses> studentsResponses = studentReportTestResponse.getStudentResponses();
			taskVariantCount = 0;
			responseCount = 0;
			
			for(StudentsResponses responses : studentsResponses){
				
				//AMP And KAP - A special circumstance was specified for any of the test ID's that make up the student's score
				if(responses.getSpecialCircumstanceId() != null) {
					studentReport.setStatus(false);
				}
				//Take only responses that are from completed and inprogress testsections.
				if(responses.getSectionStatus().equals(testSectionStatusCompletedId) || responses.getSectionStatus().equals(testSectionStatusInProgressId)){
					if(responses.getTaskVariantExternalId() != null){
						taskVariantCount++;
						if(responses.getScore() != null){
							responseCount++;
							//Handle duplicate items
							BigDecimal existingScore = variantScoreMap.get(responses.getTaskVariantExternalId());
							if(existingScore != null){
								variantScoreMap.put(responses.getTaskVariantExternalId(), existingScore.add( responses.getScore()));
							}else{
								variantScoreMap.put(responses.getTaskVariantExternalId(), responses.getScore());
							}
						}
							
					}
				}
				//KAP - Student did not log in to 1 or more of the test ID's that make up the student's score
				//AMP - Student logged into all test ID's but did not log in to 1 or more of the "parts" of one of the test ID's
				if(!responses.getSectionStatus().equals(testSectionStatusCompletedId)){
					studentReport.setIncompleteStatus(true);
				} 
			}
			//AMP Student responded to less than 5 items in any of the test ID's that make up the student's score
			if(studentReport.getAssessmentProgramCode().equalsIgnoreCase("AMP")){
				if(variantScoreMap.size() < 5){
					studentReport.setStatus(false);
				}
			}
			if(!variantScoreMap.isEmpty()){
				List<ExcludedItems> excludedItems= excludedItemsService.getByTaskVariantIds(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(),
						studentReport.getCurrentSchoolYear(), variantScoreMap.keySet(), studentReport.getTestingProgramId(), null);
				if(CollectionUtils.isNotEmpty(excludedItems)){
					for(ExcludedItems eItems : excludedItems){
						variantScoreMap.remove(eItems.getTaskVariantId());
					}
				}
			}
			BigDecimal testScore = new BigDecimal(0);
			for(Long variantScore : variantScoreMap.keySet()){
				BigDecimal responseScore = variantScoreMap.get(variantScore);
				if(responseScore != null)
					testScore = testScore.add(responseScore);
			}
			
			BigDecimal tmpScore = stageTestScore.get(studentReportTestResponse.getStageCode());
			if (tmpScore == null || tmpScore.compareTo(testScore) == 1){
				stageTestScore.put(studentReportTestResponse.getStageCode(), testScore);
				if(StageEnum.STAGE1.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
					studentReport.setExternalTest1Id(studentReportTestResponse.getTestExternalId());
			    	studentReport.setStudentTest1Id(studentReportTestResponse.getTestId());
			    	studentReportTestScores.setTest1IdTaskVariantCount(taskVariantCount);
			    	studentReportTestScores.setTest1IdResponseCount(responseCount);
				}
				if(StageEnum.STAGE2.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
			    	studentReport.setExternalTest2Id(studentReportTestResponse.getTestExternalId());
			    	studentReport.setStudentTest2Id(studentReportTestResponse.getTestId());
			    	studentReportTestScores.setTest2idTaskVariantCount(taskVariantCount);
			    	studentReportTestScores.setTest2IdResponseCount(responseCount);
				}
				if(StageEnum.STAGE3.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
			    	studentReport.setExternalTest3Id(studentReportTestResponse.getTestExternalId());
			    	studentReport.setStudentTest3Id(studentReportTestResponse.getTestId());
			    	studentReportTestScores.setTest3idTaskVariantCount(taskVariantCount);
			    	studentReportTestScores.setTest3IdResponseCount(responseCount);
				}
				if(StageEnum.STAGE4.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
			    	studentReport.setExternalTest4Id(studentReportTestResponse.getTestExternalId());
			    	studentReport.setStudentTest4Id(studentReportTestResponse.getTestId());
			    	studentReportTestScores.setTest4idTaskVariantCount(taskVariantCount);
			    	studentReportTestScores.setTest4IdResponseCount(responseCount);
				}
				if(StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
			    	studentReport.setPerformanceTestExternalId(studentReportTestResponse.getTestExternalId());
			    	studentReport.setStudentPerformanceTestId(studentReportTestResponse.getTestId());
			    	studentReportTestScores.setPrfrmTestTaskVariantCount(taskVariantCount);
			    	studentReportTestScores.setPrfrmTestResponseCount(responseCount);
				}
			}
		}
		//include in aggregates 
		//Meets criteria for generate student report = no
		//Meets criteria for either "incomplete" or "exited" label on student report 
		if(!studentReport.getStatus() || 
		   studentReport.getIncompleteStatus()){
			studentReport.setAggregates(false);
		}
		
		if(studentReport.getExitStatus() && studentReport.getAssessmentProgramCode().equalsIgnoreCase("KAP")){
			studentReport.setAggregates(false);
		}
		
		logger.info(" Stage_1  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE1.getCode()));
		if(stageTestScore.get(StageEnum.STAGE1.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE1.getCode()));
			studentReportTestScores.setTest1IdRawScore(rawCutScore);
		}
		logger.info(" Stage_2  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE2.getCode()));
		if(stageTestScore.get(StageEnum.STAGE2.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE2.getCode()));
			studentReportTestScores.setTest2IdRawScore(stageTestScore.get(StageEnum.STAGE2.getCode()));
		}
		logger.info(" Stage_3  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE3.getCode()));
		if(stageTestScore.get(StageEnum.STAGE3.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE3.getCode()));
			studentReportTestScores.setTest3IdRawScore(stageTestScore.get(StageEnum.STAGE3.getCode()));
		}
		logger.info(" Stage_4  score for student: "+studentReport.getStudentId()+" score: "+stageTestScore.get(StageEnum.STAGE4.getCode()));
		if(stageTestScore.get(StageEnum.STAGE4.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE4.getCode()));
			studentReportTestScores.setTest4IdRawScore(stageTestScore.get(StageEnum.STAGE4.getCode()));
		}
		
				
		testIds.add(studentReport.getExternalTest1Id());
		if(studentReport.getExternalTest2Id() !=null){
			testIds.add(studentReport.getExternalTest2Id());
		}
		if(studentReport.getExternalTest3Id() != null){
			testIds.add(studentReport.getExternalTest3Id());
		}		
		if(studentReport.getExternalTest4Id() != null){
			testIds.add(studentReport.getExternalTest4Id());
		}	
		
		if(stageTestScore.get(StageEnum.PERFORMANCE.getCode()) != null) {
			studentReportTestScores.setPrfrmTestRawScore(stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
			RawToScaleScores rawToScaleScores= rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
																	studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
																	studentReport.getExternalTest1Id(), studentReport.getExternalTest2Id(), studentReport.getExternalTest3Id(), 
																	studentReport.getExternalTest4Id(), studentReport.getPerformanceTestExternalId(), null, 18L, null);
		
			if(rawToScaleScores != null){
				logger.info(" Performance stage  score for student:"+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
				if(rawToScaleScores.getPerformanceRawscoreIncludeFlag() != null){
					studentReport.setPerformanceRawscoreIncludeFlag(rawToScaleScores.getPerformanceRawscoreIncludeFlag());
					if(rawToScaleScores.getPerformanceRawscoreIncludeFlag()){
						if(rawToScaleScores.getPerformanceItemWeight() != null){
							logger.info("Performance Item Weight is: "+ rawToScaleScores.getPerformanceItemWeight()+" for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId());
							rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.PERFORMANCE.getCode()).multiply(rawToScaleScores.getPerformanceItemWeight()));
						}else{
							ReportProcessReason reportProcessReason = new ReportProcessReason();
							String msg = "PerformanceItemWeight is not available for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId();
							logger.info(msg);
							reportProcessReason.setReason(msg);
							reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
							((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
							throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
						}
						
					}else{						
						studentReport.setMdptScore(stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
						studentReport.setMdptScorableFlag(true);
					}				
					
				}
			}else{
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "RawToScaleScore record is not available for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId();
				logger.info(msg);
				reportProcessReason.setReason(msg);
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
			}
			
		}
		
		studentReport.setRawScore(rawCutScore.setScale(0, BigDecimal.ROUND_HALF_UP));
		studentReport.setStudentReportTestScores(studentReportTestScores);
		
		return studentReport;
		
	}
}
