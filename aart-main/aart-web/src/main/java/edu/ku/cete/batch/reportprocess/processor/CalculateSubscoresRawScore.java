package edu.ku.cete.batch.reportprocess.processor;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.ExcludedItemsService;
import edu.ku.cete.service.report.SubscoreFrameworkService;
import edu.ku.cete.util.StageEnum;

public class CalculateSubscoresRawScore implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;
    private Long testsCompletedStatusId;
    private List<Long> subscoreRawScaleExternalTestIds;
    private Long testSectionStatusCompletedId;
    private Long testSectionStatusInProgressId;
    private Long testsInProgressStatusId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long testStatusInprogressTimedoutId;
    Map<Long, List<ReportSubscores>> testLevelSubscoreBucketMap;
    private List<ExcludedItems> excludedItemsList;
    
	@Autowired
	private ExcludedItemsService excludedItemsService;
	
	@Autowired
	private SubscoreFrameworkService subscoreFrameworkService;
	
	@Autowired 
	BatchReportProcessService batchReportProcessService;	
	
    final static Log logger = LogFactory.getLog(CalculateSubscoresRawScore.class);
    
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside CalculateSubscoresRawScore process......Student - " + studentReport.getStudentId());
		
		List<StudentReportTestResponses> studentReportTestResponses = studentReport.getStudentTestsScore();
		Map<String, ReportSubscores> subscoreBucketMap = new HashMap<String, ReportSubscores>();		
		List<ReportSubscores> testLevelSubscoresList =new ArrayList<ReportSubscores>();
		
		//If status = false from calculaterawcutscore step, then student gets text report so skipping subscore calculations
		if(studentReport.getStatus() != null && studentReport.getStatus() 
				&& CollectionUtils.isNotEmpty(studentReportTestResponses) && subscoreRawScaleExternalTestIds != null && subscoreRawScaleExternalTestIds.size() > 0){
			
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
				
				if(testsCompletedStatusId.equals(studentReportTestResponse.getStudentsTestStatus()) || 
								testsInProgressStatusId.equals(studentReportTestResponse.getStudentsTestStatus()) || 
								testStatusInprogressTimedoutId.equals(studentReportTestResponse.getStudentsTestStatus())){
					
					Map<Long, BigDecimal> variantScoreMap = new HashMap<Long, BigDecimal>();
					Map<Long, Long> variantIDsMap = new HashMap<Long, Long>();
					Long testID = studentReportTestResponse.getTestExternalId();
					if(studentReport.getExternalTest1Id()!=testID && studentReport.getExternalTest2Id()!=testID
							&& studentReport.getExternalTest3Id()!=testID && studentReport.getExternalTest4Id()!=testID && studentReport.getPerformanceTestExternalId()!=testID){
						logger.debug("Test ID "+testID+" skipped for student "+studentReport.getStudentId()+" after the exclusion and stage level calculations in CalculateRawCutScore.");
						logger.debug("Skipping this record.");
					}
					
					//External IDs considered for subscore raw score calculations are a subset of the external IDs for Raw score calculations
					else if(!subscoreRawScaleExternalTestIds.contains(testID)){
						logger.debug("Test ID "+testID+" for student "+studentReport.getStudentId()+" not found in Subscore Raw To Scale score Upload file.");
						logger.debug("Skipping this record.");
						
					}else{
						
						List<StudentsResponses> studentResponsesList = studentReportTestResponse.getStudentResponses();
						List<ReportSubscores> testLevelSubscoresListTemp = new ArrayList<ReportSubscores>();
						
						if(testLevelSubscoreBucketMap != null){
							testLevelSubscoresListTemp = testLevelSubscoreBucketMap.get(studentReportTestResponse.getTestId());
							if(CollectionUtils.isEmpty(testLevelSubscoresListTemp)){
								testLevelSubscoresListTemp = batchReportProcessService.getItemCountBySubscoreDefinitionNameByTestId(studentReportTestResponse.getTestId(), studentReport.getCurrentSchoolYear(), 
										studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId());
							}
						}						
						
						if(testLevelSubscoresListTemp != null && testLevelSubscoresListTemp.size() > 0){
							for(ReportSubscores rstemp: testLevelSubscoresListTemp){
								rstemp.setItemsResponded(0);
								rstemp.setSubscoreRawScore(new BigDecimal(0));
							}
						}
						
						Map<String, ReportSubscores> testLevelSubscoreBucketMap = new HashMap<String, ReportSubscores>();
						
						for(StudentsResponses responses : studentResponsesList){
								if(responses.getTaskVariantExternalId() != null) {
									BigDecimal currentTaskScore = new BigDecimal(0);
									if (Boolean.TRUE.equals(responses.getScoringNeeded())) {
										if (responses.getScore() != null){
											variantIDsMap.put(responses.getTaskVariantExternalId(), responses.getTaskVariantId());
											currentTaskScore = responses.getScore();
										}
									} else if ("KAP".equals(studentReport.getAssessmentProgramCode()) &&
											StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())) {
										/*Long studentsTestSectionsId = responses.getStudentsTestSectionsId();
										Long taskVariantExternalId = responses.getTaskVariantExternalId();
										List<StudentResponseScore> studentResponseScores = studentsResponsesService.findStudentResponseScores(
												studentsTestSectionsId, taskVariantExternalId, Arrays.asList(1));
										if (CollectionUtils.isNotEmpty(studentResponseScores)) {
											for (StudentResponseScore srs : studentResponseScores) {
												if (Boolean.TRUE.equals(srs.getScorable()) && srs.getScore() != null) {
													variantIDsMap.put(responses.getTaskVariantExternalId(), responses.getTaskVariantId());
													currentTaskScore = currentTaskScore.add(srs.getScore());
												}
											}
										}*/
									}
									
									BigDecimal existingScore = variantScoreMap.get(responses.getTaskVariantExternalId());
									if(existingScore != null){
										variantScoreMap.put(responses.getTaskVariantExternalId(), existingScore.add(currentTaskScore));
									}else{
										variantScoreMap.put(responses.getTaskVariantExternalId(), currentTaskScore);
									}
								}
						}
					
						if(CollectionUtils.isNotEmpty(variantScoreMap.keySet())){
							List<ExcludedItems> excludedItems = null;
							if(CollectionUtils.isEmpty(excludedItemsList)){
								excludedItems = excludedItemsService.getByTaskVariantIds(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), 
										studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), variantScoreMap.keySet(), studentReport.getTestingProgramId(), null);
							}else{
								excludedItems = getExcludedItemsList();
							}
							
							if(CollectionUtils.isNotEmpty(excludedItems)){       
								for(ExcludedItems eItems : excludedItems){
									variantScoreMap.remove(eItems.getTaskVariantId());
								}
							}
							
							for(Long taskVariantExternalId : variantScoreMap.keySet()){
								Long taskVariantId = variantIDsMap.get(taskVariantExternalId);
								List<String> subscoreBucketNames = subscoreFrameworkService.mapSubscoreDefinitionFromTaskVariant(studentReport.getSchoolYear(), taskVariantId, studentReport.getAssessmentProgramId(),
										studentReport.getContentAreaId(), studentReport.getGradeId());					
								if(CollectionUtils.isNotEmpty(subscoreBucketNames)){
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
				}//inprogress, inprogresstimedout and complete tests only						
				
			}//For each testID
		}//IF student responses exist
		
		List<ReportSubscores> subscores =new ArrayList<ReportSubscores>(subscoreBucketMap.values());
		if(CollectionUtils.isNotEmpty(subscores)){
			for(ReportSubscores subscore : subscores){
				BigDecimal rawSubscore = subscore.getSubscoreRawScore();
				subscore.setSubscoreRawScore(rawSubscore.setScale(0, BigDecimal.ROUND_HALF_UP));
				int totalItems = 0;
				int itemsResponded = 0;
				for(ReportSubscores testLevelSubscore : testLevelSubscoresList){
					if(testLevelSubscore.getSubscoreDefinitionName().equalsIgnoreCase(subscore.getSubscoreDefinitionName())){
						if(testLevelSubscore.getTotalItems() != null){
							totalItems = totalItems + testLevelSubscore.getTotalItems();
						}
						if(testLevelSubscore.getItemsResponded() != null){
							itemsResponded = itemsResponded + testLevelSubscore.getItemsResponded();
						}					
					}				
				}
				subscore.setTotalItems(totalItems);
				subscore.setItemsResponded(itemsResponded);
			}
		}
		
		studentReport.setSubscoreBuckets(subscores);
		studentReport.setTestLevelSubscoreBuckets(testLevelSubscoresList);
		logger.debug("Completed CalculateSubscoresRawScore....Student - " + studentReport.getStudentId());
    	return studentReport;
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

	public List<Long> getSubscoreRawScaleExternalTestIds() {
		return subscoreRawScaleExternalTestIds;
	}

	public void setSubscoreRawScaleExternalTestIds(List<Long> subscoreRawScaleExternalTestIds) {
		this.subscoreRawScaleExternalTestIds = subscoreRawScaleExternalTestIds;
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


	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}


	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}


	public Map<Long, List<ReportSubscores>> getTestLevelSubscoreBucketMap() {
		return testLevelSubscoreBucketMap;
	}


	public void setTestLevelSubscoreBucketMap(Map<Long, List<ReportSubscores>> testLevelSubscoreBucketMap) {
		this.testLevelSubscoreBucketMap = testLevelSubscoreBucketMap;
	}


	public List<ExcludedItems> getExcludedItemsList() {
		return excludedItemsList;
	}


	public void setExcludedItemsList(List<ExcludedItems> excludedItemsList) {
		this.excludedItemsList = excludedItemsList;
	}

}

