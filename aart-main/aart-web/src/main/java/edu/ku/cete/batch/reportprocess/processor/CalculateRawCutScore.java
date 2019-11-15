package edu.ku.cete.batch.reportprocess.processor;

 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.StudentReportTestScores;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.RawScoreSectionWeights;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.report.ExcludedItemsService;
import edu.ku.cete.service.report.RawToScaleScoresService;
import edu.ku.cete.util.StageEnum;

/**
 * @author Kiran Reddy Taduru
 * Apr 11, 2018 2:41:18 PM
 */
public class CalculateRawCutScore implements ItemProcessor<StudentReport,Object>
{
	final static Log logger = LogFactory.getLog(CalculateRawCutScore.class);
	
    private StepExecution stepExecution;
    private Long testsCompletedStatusId;
    private Long testsInProgressStatusId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long testStatusInprogressTimedoutId;
    private Long testSectionStatusUnusedId;
    private Long testSectionStatusCompletedId;
    private Long testSectionStatusInProgressId;    
    private List<Long> rawScaleExternalTestIds;
    private Long specialCircumstanceStatusSavedId;
    private Long specialCircumstanceStatusApprovedId;    
    private String contentAreaAbbreviatedName;
    private String gradeCourseAbbreviatedName;
    private List<ExcludedItems> excludedItemsList;
    private String enableTroubleShooting;
    
	@Autowired
	private ExcludedItemsService excludedItemsService;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	@Autowired
	private StudentReportService studentReportService; 	
	
	@Value("${report.subject.math.name}")
	private String CONTENT_AREA_MATH;

	@Value("${report.subject.reading.name}")
	private String CONTENT_AREA_ELA;
	
	@Value("${report.subject.socialstudies.name}")
	private String CONTENT_AREA_SS;
	
	private Long testingProgramId;
	
	@Autowired
	private CategoryService categoryService;
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside CalculateRawCutScore process....: for student::"+studentReport.getStudentId() + " - Subject::" + contentAreaAbbreviatedName + " - Grade::" + gradeCourseAbbreviatedName);
		studentReport.setStatus(true);
		studentReport.setIncompleteStatus(false);
		studentReport.setStage1HasSCCode(false);
		studentReport.setStage2HasSCCode(false);
		studentReport.setStage3HasSCCode(false);
		studentReport.setStage4HasSCCode(false);
		studentReport.setPrfrmStageHasSCCode(false);
		
		List<Long> testIds = new ArrayList<Long>();
		StudentReportTestScores studentReportTestScores = new StudentReportTestScores();
		int taskVariantCount = 0;
		int responseCount = 0;	
		
		List<StudentReportTestResponses> studentReportTestResponses = studentReport.getStudentTestsScore();
		if(CollectionUtils.isEmpty(studentReportTestResponses))
		{
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "No test responses found for student: "+studentReport.getStudentId() ;
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}
		
		BigDecimal rawCutScore = new BigDecimal(0);
		Boolean mdptScorable = null;
		Boolean scCodeStatus = false;
		Boolean nonScorableCodePresent = false;
		Boolean performanceTestNotScored = false;
		
		Map<String, BigDecimal> stageTestScore = new HashMap<String, BigDecimal>();
		for(StudentReportTestResponses studentReportTestResponse : studentReportTestResponses){
			scCodeStatus = false;
			if(studentReportTestResponse.getSpecialCircumstanceId() != null && studentReportTestResponse.getScCodeStatus() != null) {
				if(studentReportTestResponse.getKsdeRestricted() != null){
					if(studentReportTestResponse.getKsdeRestricted()){
						if(specialCircumstanceStatusApprovedId.equals(studentReportTestResponse.getScCodeStatus())){
							scCodeStatus = true;
						}
					} else if(!Boolean.TRUE.equals(studentReportTestResponse.getReportScore()) && specialCircumstanceStatusSavedId.equals(studentReportTestResponse.getScCodeStatus())){
						scCodeStatus = true;
					}
				}
				
			}
			if(testsCompletedStatusId.equals(studentReportTestResponse.getStudentsTestStatus()) || 
							testsInProgressStatusId.equals(studentReportTestResponse.getStudentsTestStatus()) || 
							testStatusInprogressTimedoutId.equals(studentReportTestResponse.getStudentsTestStatus())){
				
				Boolean historySectionsFlag = false;
				List<RawScoreSectionWeights> sectionWeightsList = null;
				if(CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName)){
					sectionWeightsList = studentReportService.getRawScoreSectionWeights(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReportTestResponse.getTestExternalId(), studentReport.getCurrentSchoolYear());
				}
				
				if(CollectionUtils.isNotEmpty(sectionWeightsList)){
					historySectionsFlag = true;
				}					
				
				Map<Long, BigDecimal> variantScoreMap = new HashMap<Long, BigDecimal>();
				List<StudentsResponses> studentsResponses = studentReportTestResponse.getStudentResponses();
				if(!"FALSE".equalsIgnoreCase(enableTroubleShooting)){
					taskVariantCount = studentReportService.taskvariantCountByTestId(studentReportTestResponse.getTestId());
				}
				
				responseCount = 0;
				
				for(StudentsResponses responses : studentsResponses){
					//Take only responses that are from completed and inprogress, inprogress timedout tests
					if(testsCompletedStatusId.equals(responses.getStudentsTestStatus()) || 
							testsInProgressStatusId.equals(responses.getStudentsTestStatus()) || 
							testStatusInprogressTimedoutId.equals(responses.getStudentsTestStatus())){
						
						boolean studentAnsweredQuestion = responses.getTaskVariantExternalId() != null &&
							(
								responses.getFoilId() != null || StringUtils.isNotBlank(responses.getResponse())
							);
						
						if(studentAnsweredQuestion) {
							responseCount++;
							logger.debug("Student answered " + studentReportTestResponse.getStageCode() + " taskvariant.externalid " + responses.getTaskVariantExternalId() + " -- responseCount = " + responseCount);
							
							BigDecimal currentTaskScore = new BigDecimal(0);
							
							if (Boolean.TRUE.equals(responses.getScoringNeeded())) {
								if (responses.getScore() != null) {
									
									if(historySectionsFlag){//for HGSS
										if(getSectionOrderByTaskVariantPosition(gradeCourseAbbreviatedName, responses.getTaskVariantPosition()) == 1){
											currentTaskScore = responses.getScore().multiply(sectionWeightsList.get(0).getSectionWeight1());
										}else if(getSectionOrderByTaskVariantPosition(gradeCourseAbbreviatedName, responses.getTaskVariantPosition()) == 2){
											currentTaskScore = responses.getScore().multiply(sectionWeightsList.get(0).getSectionWeight2());
										}else{
											currentTaskScore = responses.getScore();
										}
									}else{
										currentTaskScore = responses.getScore();
									}									
								} else {
									logger.warn("No score found in studentsresponses for studentsTestSectionsId: " + responses.getStudentsTestSectionsId() +
											", taskVariantId: " + responses.getTaskVariantId());
								}
							} else {
								
								if ("KAP".equals(studentReport.getAssessmentProgramCode()) && 
										StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())) {
									
									if(responses.getManualScore() != null){
										currentTaskScore = responses.getManualScore();
										if(responses.getNonScoringReasonId() != null){
											Category nonScoreReason = categoryService.selectByPrimaryKey(responses.getNonScoringReasonId());
											//non scorable reason other than Harm to Self or Others found, so score will not be considered and students gets text only report (not score report)
											if (!nonScoreReason.getCategoryCode().equalsIgnoreCase("HSO")) {
												currentTaskScore = new BigDecimal(0);
												nonScorableCodePresent = true;
												logger.debug("Found non-score reason other than HSO for studentsTestsId: " + studentReportTestResponse.getStudentsTestsId() +
														", taskVariantId: " + responses.getTaskVariantId() + ", Stage: " + studentReportTestResponse.getStageCode());
											}
										}
									} else{
										performanceTestNotScored = true;
									}
									
									
								}
							}
							logger.debug("adding taskvariant.externalid " + responses.getTaskVariantExternalId() + " to map");
							//Handle duplicate items
							BigDecimal existingScore = variantScoreMap.get(responses.getTaskVariantExternalId());
							if (existingScore != null){
								variantScoreMap.put(responses.getTaskVariantExternalId(), existingScore.add(currentTaskScore));
							} else {
								variantScoreMap.put(responses.getTaskVariantExternalId(), currentTaskScore);
							}
						}else{
							logger.debug("Student DID NOT answer " + studentReportTestResponse.getStageCode() + " taskvariant.externalid " + responses.getTaskVariantExternalId());
							if ("KAP".equals(studentReport.getAssessmentProgramCode()) && 
									StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())) {
								performanceTestNotScored = true;
							}
						}
					}
					
					
				}
				
				
				if(!variantScoreMap.isEmpty()){
					List<ExcludedItems> excludedItems = null;
					if(CollectionUtils.isEmpty(excludedItemsList)){
						excludedItems = excludedItemsService.getByTaskVariantIds(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(),
								studentReport.getCurrentSchoolYear(), variantScoreMap.keySet(), studentReport.getTestingProgramId(), null);
					}else{
						excludedItems = getExcludedItemsList();
					}
					
					if(CollectionUtils.isNotEmpty(excludedItems)){
						BigDecimal removedItem = null;
						for(ExcludedItems eItems : excludedItems){
							removedItem = variantScoreMap.remove(eItems.getTaskVariantId());
							if(removedItem != null){
								responseCount = responseCount - 1;
								logger.debug(eItems.getTaskVariantId() + " was excluded and removed -- responseCount = " + responseCount);
							}							
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
				    	studentReport.setStage1RespondedItems(responseCount);
				    	studentReport.setStage1HasSCCode(scCodeStatus);
				    	studentReport.setStage1TestStatus(studentReportTestResponse.getStudentsTestStatus());
				    	studentReport.setStage1EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.STAGE2.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setExternalTest2Id(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentTest2Id(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setTest2idTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setTest2IdResponseCount(responseCount);
				    	studentReport.setStage2RespondedItems(responseCount);
				    	studentReport.setStage2HasSCCode(scCodeStatus);
				    	studentReport.setStage2TestStatus(studentReportTestResponse.getStudentsTestStatus());
				    	studentReport.setStage2EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.STAGE3.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setExternalTest3Id(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentTest3Id(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setTest3idTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setTest3IdResponseCount(responseCount);
				    	studentReport.setStage3HasSCCode(scCodeStatus);
				    	studentReport.setStage3TestStatus(studentReportTestResponse.getStudentsTestStatus());
				    	studentReport.setStage3EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.STAGE4.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setExternalTest4Id(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentTest4Id(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setTest4idTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setTest4IdResponseCount(responseCount);
				    	studentReport.setStage4HasSCCode(scCodeStatus);
				    	studentReport.setStage4TestStatus(studentReportTestResponse.getStudentsTestStatus());
				    	studentReport.setStage4EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setPerformanceTestExternalId(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentPerformanceTestId(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setPrfrmTestTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setPrfrmTestResponseCount(responseCount);
				    	studentReport.setPrfrmStageHasSCCode(scCodeStatus);
				    	studentReport.setPrfrmTestStatus(studentReportTestResponse.getStudentsTestStatus());
				    	studentReport.setPrfrmStageEnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
				}
			}else{//Pending or Unused status
				if(stageTestScore.get(studentReportTestResponse.getStageCode()) == null) {
					if(StageEnum.STAGE1.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage1TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage1HasSCCode(scCodeStatus);
						studentReport.setStage1EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.STAGE2.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage2TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage2HasSCCode(scCodeStatus);
						studentReport.setStage2EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.STAGE3.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage3TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage3HasSCCode(scCodeStatus);
						studentReport.setStage3EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.STAGE4.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage4TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage4HasSCCode(scCodeStatus);
						studentReport.setStage4EnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
					if(StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setPrfrmTestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setPrfrmStageHasSCCode(scCodeStatus);
						studentReport.setPrfrmStageEnrollmentId(studentReportTestResponse.getEnrollmentId());
					}
				}
				
			}
		
		}		
		
		logger.debug(" Stage_1  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE1.getCode()));
		if(stageTestScore.get(StageEnum.STAGE1.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE1.getCode()));
			studentReportTestScores.setTest1IdRawScore(rawCutScore);
		}
		logger.debug(" Stage_2  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE2.getCode()));
		if(stageTestScore.get(StageEnum.STAGE2.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE2.getCode()));
			studentReportTestScores.setTest2IdRawScore(stageTestScore.get(StageEnum.STAGE2.getCode()));
		}
		logger.debug(" Stage_3  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE3.getCode()));
		if(stageTestScore.get(StageEnum.STAGE3.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE3.getCode()));
			studentReportTestScores.setTest3IdRawScore(stageTestScore.get(StageEnum.STAGE3.getCode()));
		}
		logger.debug(" Stage_4  score for student: "+studentReport.getStudentId()+" score: "+stageTestScore.get(StageEnum.STAGE4.getCode()));
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
																	studentReport.getExternalTest4Id(), studentReport.getPerformanceTestExternalId(), null, testingProgramId, null);
		
			if(rawToScaleScores != null){
				logger.debug(" Performance stage  score for student:"+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
				if(rawToScaleScores.getPerformanceRawscoreIncludeFlag() != null){
					studentReport.setPerformanceRawscoreIncludeFlag(rawToScaleScores.getPerformanceRawscoreIncludeFlag());
					if(rawToScaleScores.getPerformanceRawscoreIncludeFlag()){
						if(rawToScaleScores.getPerformanceItemWeight() != null){
							logger.debug("Performance Item Weight is: "+ rawToScaleScores.getPerformanceItemWeight()+" for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId());
							rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.PERFORMANCE.getCode()).multiply(rawToScaleScores.getPerformanceItemWeight()));
						}else{
							ReportProcessReason reportProcessReason = new ReportProcessReason();
							String msg = "PerformanceItemWeight is not available for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId();
							logger.debug(msg);
							reportProcessReason.setStudentId(studentReport.getStudentId());
							reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
							reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
							reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
							reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
							reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
							reportProcessReason.setReason(msg);
							reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
							((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
							throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
						}
						
					}else{
						logger.debug("Assigning Performance stage score to MDPT Score....for student: "+studentReport.getStudentId()+" MDPT Score:"+stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
						studentReport.setMdptScorableFlag(mdptScorable);
						if (mdptScorable!=null && mdptScorable) {
							studentReport.setMdptScore(stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
						} else {
							studentReport.setMdptScore(null);
						}
					}
					
				}
			}else{
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "RawToScaleScore record is not available for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId();
				logger.debug(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
				reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
				reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
				reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
				reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
				reportProcessReason.setReason(msg);
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
			}
			
		}		
			
		//Do not produce score if minimum criteria is not met
		//ELA, Math, Sci : stage1 must be in complete status, minimum 5 responses, no SC code:: stage2 can be in complete or inprogress or inprogresstimedout status, minimum 5 responses, no SC code
		//SocialStudies : Stage1 must be in complete or inprogress or inprogresstimedout status, minimum 5 responses, no SC code:: Prfrm stage can be in complete or inprogress or inprogresstimedout status, no SC code, scoring must be completed, nonscorereason not present other than HSO
		if(studentReport.getExternalTest1Id() != null && ((!CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && testsCompletedStatusId.equals(studentReport.getStage1TestStatus()))
				|| (CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && 
							(testsCompletedStatusId.equals(studentReport.getPrfrmTestStatus())
							|| testsInProgressStatusId.equals(studentReport.getPrfrmTestStatus())
							|| testStatusInprogressTimedoutId.equals(studentReport.getPrfrmTestStatus())))) 
				&& studentReport.getStage1RespondedItems() >= 5 && !studentReport.getStage1HasSCCode()){
			if((!CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && studentReport.getExternalTest2Id() != null && 
					(testsCompletedStatusId.equals(studentReport.getStage2TestStatus())
					|| testsInProgressStatusId.equals(studentReport.getStage2TestStatus())
					|| testStatusInprogressTimedoutId.equals(studentReport.getStage2TestStatus())) 
						&&  studentReport.getStage2RespondedItems() >= 5 
						&& !studentReport.getStage2HasSCCode()) 
					|| (CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && 
							(testsCompletedStatusId.equals(studentReport.getPrfrmTestStatus())
							|| testsInProgressStatusId.equals(studentReport.getPrfrmTestStatus())
							|| testStatusInprogressTimedoutId.equals(studentReport.getPrfrmTestStatus())) 
							&& !studentReport.getPrfrmStageHasSCCode()
							&& !nonScorableCodePresent
							&& !performanceTestNotScored
							&& studentReport.getPerformanceTestExternalId() != null)
					){
				studentReport.setStatus(true);
			}else{
				studentReport.setStatus(false);
			}
		}else{
			studentReport.setStatus(false);			
		}		
		
		if(!studentReport.getStatus()){
			studentReport.setIncompleteStatus(true);
		}
		
		studentReport.setRawScore(rawCutScore.setScale(0, BigDecimal.ROUND_HALF_UP));
		studentReport.setStudentReportTestScores(studentReportTestScores);
		logger.debug("Completed CalculateRawCutScore....: for student::"+studentReport.getStudentId() + " - Subject::" + contentAreaAbbreviatedName + " - Grade::" + gradeCourseAbbreviatedName);
    	return studentReport;
    }
	
	
	/**
	 * Kiran Reddy Taduru 04/11/2018 : This year, HGSS tests have single section, to apply different section weights on questions, 
	 * devided questions into 2 parts based on taskvariant position for each grade per JRP request, based on that determining which section weight to apply
	 * @param gradeCourseAbbreviateName
	 * @param taskVariantPosition
	 * @return
	 */
	private int getSectionOrderByTaskVariantPosition(String gradeCourseAbbreviateName, int taskVariantPosition){
		int[] grade6_section1 = { 1, 2, 3, 4, 5, 6, 7 };
		int[] grade6_section2 = { 8, 9, 10 };
		int[] grade8_section1 = { 1, 2, 3, 4, 5 };
		int[] grade8_section2 = { 6, 7 , 8 };
		int[] grade11_section1 = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int[] grade11_section2 = { 9, 10, 11 };
		
		if("6".equals(gradeCourseAbbreviateName)){
			if(Arrays.binarySearch(grade6_section1, taskVariantPosition) >= 0){
				return 1;
			}else if(Arrays.binarySearch(grade6_section2, taskVariantPosition) >= 0){
				return 2;
			}
		}else if("8".equals(gradeCourseAbbreviateName)){
			if(Arrays.binarySearch(grade8_section1, taskVariantPosition) >= 0){
				return 1;
			}else if(Arrays.binarySearch(grade8_section2, taskVariantPosition) >= 0){
				return 2;
			}
		}else if("11".equals(gradeCourseAbbreviateName)){
			if(Arrays.binarySearch(grade11_section1, taskVariantPosition) >= 0){
				return 1;
			}else if(Arrays.binarySearch(grade11_section2, taskVariantPosition) >= 0){
				return 2;
			}
		}
		
		return 1;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	
	public Long getTestsCompletedStatusId() {
		return testsCompletedStatusId;
	}

	public void setTestsCompletedStatusId(Long testsCompletedStatusId) {
		this.testsCompletedStatusId = testsCompletedStatusId;
	}


	public Long getTestsInProgressStatusId() {
		return testsInProgressStatusId;
	}


	public void setTestsInProgressStatusId(Long testsInProgressStatusId) {
		this.testsInProgressStatusId = testsInProgressStatusId;
	}


	public Long getTestStatusUnusedId() {
		return testStatusUnusedId;
	}


	public void setTestStatusUnusedId(Long testStatusUnusedId) {
		this.testStatusUnusedId = testStatusUnusedId;
	}
	
	public Long getTestStatusPendingId() {
		return testStatusPendingId;
	}


	public void setTestStatusPendingId(Long testStatusPendingId) {
		this.testStatusPendingId = testStatusPendingId;
	}


	public Long getTestSectionStatusUnusedId() {
		return testSectionStatusUnusedId;
	}


	public void setTestSectionStatusUnusedId(Long testSectionStatusUnusedId) {
		this.testSectionStatusUnusedId = testSectionStatusUnusedId;
	}


	public Long getTestSectionStatusCompletedId() {
		return testSectionStatusCompletedId;
	}


	public void setTestSectionStatusCompletedId(Long testSectionStatusCompletedId) {
		this.testSectionStatusCompletedId = testSectionStatusCompletedId;
	}


	public Long getTestSectionStatusInProgressId() {
		return testSectionStatusInProgressId;
	}


	public void setTestSectionStatusInProgressId(Long testSectionStatusInProgressId) {
		this.testSectionStatusInProgressId = testSectionStatusInProgressId;
	}


	public List<Long> getRawScaleExternalTestIds() {
		return rawScaleExternalTestIds;
	}


	public void setRawScaleExternalTestIds(List<Long> rawScaleExternalTestIds) {
		this.rawScaleExternalTestIds = rawScaleExternalTestIds;
	}

	public Long getSpecialCircumstanceStatusSavedId() {
		return specialCircumstanceStatusSavedId;
	}

	public void setSpecialCircumstanceStatusSavedId(Long specialCircumstanceStatusSavedId) {
		this.specialCircumstanceStatusSavedId = specialCircumstanceStatusSavedId;
	}

	public Long getSpecialCircumstanceStatusApprovedId() {
		return specialCircumstanceStatusApprovedId;
	}

	public void setSpecialCircumstanceStatusApprovedId(Long specialCircumstanceStatusApprovedId) {
		this.specialCircumstanceStatusApprovedId = specialCircumstanceStatusApprovedId;
	}


	public String getContentAreaAbbreviatedName() {
		return contentAreaAbbreviatedName;
	}


	public void setContentAreaAbbreviatedName(String contentAreaAbbreviatedName) {
		this.contentAreaAbbreviatedName = contentAreaAbbreviatedName;
	}


	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}


	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}


	public Long getTestingProgramId() {
		return testingProgramId;
	}


	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}


	public String getGradeCourseAbbreviatedName() {
		return gradeCourseAbbreviatedName;
	}


	public void setGradeCourseAbbreviatedName(String gradeCourseAbbreviatedName) {
		this.gradeCourseAbbreviatedName = gradeCourseAbbreviatedName;
	}


	public List<ExcludedItems> getExcludedItemsList() {
		return excludedItemsList;
	}


	public void setExcludedItemsList(List<ExcludedItems> excludedItemsList) {
		this.excludedItemsList = excludedItemsList;
	}


	public String getEnableTroubleShooting() {
		return enableTroubleShooting;
	}


	public void setEnableTroubleShooting(String enableTroubleShooting) {
		this.enableTroubleShooting = enableTroubleShooting;
	}			 
		
}
