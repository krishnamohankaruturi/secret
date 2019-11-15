package edu.ku.cete.batch.reportprocess.processor;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.SubScoresMissingStages;
import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;
import edu.ku.cete.util.NumericUtil;

public class CalculateSubscoresScaleScore implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;
    private List<Long> subscoreRawScaleExternalTestIds;
    Map<Long, List<ReportSubscores>> testLevelSubscoreBucketMap;
    private String gradeCourseAbbreviatedName;
    
    @Autowired
	private SubscoreRawToScaleScoresService subscoreRawToScaleScoresService;
    
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
    final static Log logger = LogFactory.getLog(CalculateSubscoresScaleScore.class);
    
    @Value("${subscore.rating.insufficientData}")
	private int insufficientDataRating;
    
	@SuppressWarnings("unchecked")
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside CalculateSubscoreRawscoreToRating process.....Student - " + studentReport.getStudentId());
		
		List<ReportSubscores> subscores = studentReport.getSubscoreBuckets();
		if(subscores==null){
			String msg = "No subscores for this report. Skipping subscores for student: "+studentReport.getStudentId()+"; TestIDs: ("+studentReport.getExternalTest1Id()+", "
						+studentReport.getExternalTest2Id()+", "+ studentReport.getExternalTest3Id()+", "+studentReport.getExternalTest4Id()+", "+studentReport.getPerformanceTestExternalId() +").";
			logger.debug(msg);
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
			reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
			reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
			reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
			reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			return studentReport;
		}
		
		Integer defaultTestCount = 0;
		List<Long> testIds = new ArrayList<Long>();
		List<SubScoresMissingStages> missingStagesTestIdsList = new ArrayList<SubScoresMissingStages>();
		SubScoresMissingStages missingStagesTestIds = null;
		Long stage2externaltestid = null, stage3externaltestid = null, stage4externaltestid = null, performanceexternaltestid = null;
		
		if(studentReport.getExternalTest2Id() == null){ //|| studentReport.getExternalTest3Id() == null || studentReport.getPerformanceTestExternalId() == null				
			missingStagesTestIdsList = subscoreRawToScaleScoresService.getDefaultTestIDForSubscoreMissingStages(studentReport.getContentAreaId(), studentReport.getGradeId()
					, studentReport.getAssessmentProgramId(), studentReport.getCurrentSchoolYear());
			
			if(missingStagesTestIdsList.size() > 0)
				missingStagesTestIds = missingStagesTestIdsList.get(0);
		}
		
		if(studentReport.getExternalTest1Id() !=null && subscoreRawScaleExternalTestIds.contains(studentReport.getExternalTest1Id()))
			testIds.add(studentReport.getExternalTest1Id());
		else
			logger.debug("Skipping TestID1: "+studentReport.getExternalTest1Id());
		
			
		if(studentReport.getExternalTest2Id() !=null && subscoreRawScaleExternalTestIds.contains(studentReport.getExternalTest2Id())){
			testIds.add(studentReport.getExternalTest2Id());
			stage2externaltestid = studentReport.getExternalTest2Id();
			
		}else if(missingStagesTestIds != null && missingStagesTestIds.getDefaultStage2EpTestId()!=null){
			testIds.add(missingStagesTestIds.getTestId2());
			stage2externaltestid = missingStagesTestIds.getTestId2();
			
			List<ReportSubscores> stage2SubscoresList = null;
			
			if(testLevelSubscoreBucketMap != null){
				stage2SubscoresList = testLevelSubscoreBucketMap.get(missingStagesTestIds.getDefaultStage2EpTestId());
				if(CollectionUtils.isEmpty(stage2SubscoresList)){
					stage2SubscoresList = batchReportProcessService.getItemCountBySubscoreDefinitionNameByTestId(missingStagesTestIds.getDefaultStage2EpTestId(), studentReport.getCurrentSchoolYear(), 
							studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId());
				}
			}
			
			if(stage2SubscoresList.size()>0){
				studentReport.getTestLevelSubscoreBuckets().addAll(stage2SubscoresList);
			}
			
			Map<String, Integer> stage2SubscoresTotalItems = new HashMap<String, Integer>();
			for(ReportSubscores testLevelSubscore : stage2SubscoresList){
				stage2SubscoresTotalItems.put(testLevelSubscore.getSubscoreDefinitionName(), testLevelSubscore.getTotalItems());
			}
			
			if(stage2SubscoresTotalItems.size()>0){
				for(ReportSubscores subscore : subscores){
					if(stage2SubscoresTotalItems.get(subscore.getSubscoreDefinitionName()) != null)	{
						defaultTestCount = stage2SubscoresTotalItems.get(subscore.getSubscoreDefinitionName());
						subscore.setTotalItems(subscore.getTotalItems() + defaultTestCount);
					}
				}
		
			}
			
		}else{
			logger.debug("Skipping TestID2: "+studentReport.getExternalTest2Id());
		}
			
		
		if(studentReport.getExternalTest3Id() != null && subscoreRawScaleExternalTestIds.contains(studentReport.getExternalTest3Id())){
			testIds.add(studentReport.getExternalTest3Id());
			stage3externaltestid = studentReport.getExternalTest3Id();
		}else if(missingStagesTestIds != null && missingStagesTestIds.getDefaultStage3EpTestId() != null){
			testIds.add(missingStagesTestIds.getTestId3());
			stage3externaltestid = missingStagesTestIds.getTestId3();
			
			List<ReportSubscores> stage3SubscoresList = null;
			
			if(testLevelSubscoreBucketMap != null){
				stage3SubscoresList = testLevelSubscoreBucketMap.get(missingStagesTestIds.getDefaultStage3EpTestId());
				if(CollectionUtils.isEmpty(stage3SubscoresList)){
					batchReportProcessService.getItemCountBySubscoreDefinitionNameByTestId(missingStagesTestIds.getDefaultStage3EpTestId(), studentReport.getCurrentSchoolYear(), 
							studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId());
				}
			}
			
			if(stage3SubscoresList.size()>0){
				studentReport.getTestLevelSubscoreBuckets().addAll(stage3SubscoresList);
			}
			
			Map<String, Integer> stage3SubscoresTotalItems = new HashMap<String, Integer>();
			for(ReportSubscores testLevelSubscore : stage3SubscoresList){
				stage3SubscoresTotalItems.put(testLevelSubscore.getSubscoreDefinitionName(), testLevelSubscore.getTotalItems());
			}
			if(stage3SubscoresTotalItems.size()>0){
				for(ReportSubscores subscore : subscores){
					if(stage3SubscoresTotalItems.get(subscore.getSubscoreDefinitionName()) != null){
						defaultTestCount = stage3SubscoresTotalItems.get(subscore.getSubscoreDefinitionName());
						subscore.setTotalItems(subscore.getTotalItems() + defaultTestCount);
					}
				}
			}
		}else{
			logger.debug("Skipping TestID3: "+studentReport.getExternalTest3Id());
		}
			
		
		if(studentReport.getExternalTest4Id() !=null && subscoreRawScaleExternalTestIds.contains(studentReport.getExternalTest4Id())){
			testIds.add(studentReport.getExternalTest4Id());
			stage4externaltestid = studentReport.getExternalTest4Id();
		}else {
			logger.debug("Skipping TestID4: "+studentReport.getExternalTest4Id());
		}
			
		
		if(studentReport.getPerformanceTestExternalId() !=null && subscoreRawScaleExternalTestIds.contains(studentReport.getPerformanceTestExternalId())){
			testIds.add(studentReport.getPerformanceTestExternalId());
			performanceexternaltestid = studentReport.getPerformanceTestExternalId();
		}else if(missingStagesTestIds != null && missingStagesTestIds.getDefaultPerformanceEpTestId() !=null){
			testIds.add(missingStagesTestIds.getPerformanceTestId());
			performanceexternaltestid = missingStagesTestIds.getPerformanceTestId();
			
			List<ReportSubscores> performanceSubscoresList = null;
			
			if(testLevelSubscoreBucketMap != null){
				performanceSubscoresList = testLevelSubscoreBucketMap.get(missingStagesTestIds.getDefaultPerformanceEpTestId());
				if(CollectionUtils.isEmpty(performanceSubscoresList)){
					batchReportProcessService.getItemCountBySubscoreDefinitionNameByTestId(missingStagesTestIds.getDefaultPerformanceEpTestId(), studentReport.getCurrentSchoolYear(), 
							studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId());
				}
			}
			
			if(performanceSubscoresList.size()>0){
				studentReport.getTestLevelSubscoreBuckets().addAll(performanceSubscoresList);
			}
			
			Map<String, Integer> performanceSubscoresTotalItems = new HashMap<String, Integer>();
			for(ReportSubscores testLevelSubscore : performanceSubscoresList){
				performanceSubscoresTotalItems.put(testLevelSubscore.getSubscoreDefinitionName(), testLevelSubscore.getTotalItems());
			}
				
			
			if(performanceSubscoresTotalItems.size()>0){
				for(ReportSubscores subscore : subscores){
					if(performanceSubscoresTotalItems.get(subscore.getSubscoreDefinitionName()) != null){
						defaultTestCount = performanceSubscoresTotalItems.get(subscore.getSubscoreDefinitionName());
						subscore.setTotalItems(subscore.getTotalItems() + defaultTestCount);
					}
				}
			}
		}else{
			logger.debug("Skipping PerformanceTestID: "+studentReport.getPerformanceTestExternalId());
		}
			
		
		if(testIds.size()==0){
			logger.debug("No testIds found for student "+studentReport.getStudentId());
			return studentReport;
		}		
		
		for(ReportSubscores subscore : subscores){			
			SubscoreRawToScaleScores subscoreRawToScaleObject = subscoreRawToScaleScoresService.getRatingForSubscoreRawScore(testIds, subscore.getSubscoreDefinitionName(), subscore.getSubscoreRawScore(), 
					studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
					studentReport.getExternalTest1Id(), stage2externaltestid, stage3externaltestid, 
					stage4externaltestid, performanceexternaltestid);
			if(subscoreRawToScaleObject==null){
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "Rawscore " + subscore.getSubscoreRawScore() + " not found for subscore definition: "+subscore.getSubscoreDefinitionName()+" in Subscore Raw Score to Rating Upload file for Assessment Program - " 
							+ studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + "(Grade " + gradeCourseAbbreviatedName + ")" + " Subject Id -" + studentReport.getContentAreaId();					
				
				logger.debug(msg + ". Skipping this student");
				reportProcessReason.setReason(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
				reportProcessReason.setTestId2(stage2externaltestid);
				reportProcessReason.setTestId3(stage3externaltestid);
				reportProcessReason.setTestId4(stage4externaltestid);
				reportProcessReason.setPerformanceTestExternalId(performanceexternaltestid);
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
				
			}else {
				BigDecimal minPercentResponses = subscoreRawToScaleObject.getMinimumPercentResponses();
				if(minPercentResponses != null){
					if(subscore.getTotalItems() != null){
						BigDecimal minResponsesNeeded = minPercentResponses.multiply(new BigDecimal(subscore.getTotalItems()));
						int minimumResponsesNeeded = NumericUtil.getCustomRoundedNumber(minResponsesNeeded);
						if(subscore.getItemsResponded() >= minimumResponsesNeeded){
							subscore.setRating(subscoreRawToScaleObject.getRating());
						}else{
							subscore.setRating(insufficientDataRating);
						}
					}
				}
				if(subscore.getRating() == null){
					subscore.setRating(insufficientDataRating);
				}
			}
		}				
		
		logger.debug("Completed CalculateSubscoreRawscoreToRating....Student - " + studentReport.getStudentId());
    	return studentReport;
    }
	
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}


	public List<Long> getSubscoreRawScaleExternalTestIds() {
		return subscoreRawScaleExternalTestIds;
	}


	public void setSubscoreRawScaleExternalTestIds(
			List<Long> subscoreRawScaleExternalTestIds) {
		this.subscoreRawScaleExternalTestIds = subscoreRawScaleExternalTestIds;
	}


	public Map<Long, List<ReportSubscores>> getTestLevelSubscoreBucketMap() {
		return testLevelSubscoreBucketMap;
	}


	public void setTestLevelSubscoreBucketMap(Map<Long, List<ReportSubscores>> testLevelSubscoreBucketMap) {
		this.testLevelSubscoreBucketMap = testLevelSubscoreBucketMap;
	}


	public String getGradeCourseAbbreviatedName() {
		return gradeCourseAbbreviatedName;
	}


	public void setGradeCourseAbbreviatedName(String gradeCourseAbbreviatedName) {
		this.gradeCourseAbbreviatedName = gradeCourseAbbreviatedName;
	}
	
	

}
