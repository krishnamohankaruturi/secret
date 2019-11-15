package edu.ku.cete.batch.reportprocess.processor;

 
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.report.OrganizationSummaryReportGenerator;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.LevelDescriptionService;
public class BatchOrganizationSummaryProcess implements ItemProcessor<Long,Object>
{
    private StepExecution stepExecution;
    private Long assessmentProgramId;
    private String assessmentProgramCode;
    private ContentArea contentArea;
	private GradeCourse gradeCourse;
    private Long batchReportProcessId;
    private Long schoolYear;
    private Long testingProgramId;
    
    @Autowired
	private OrganizationSummaryReportGenerator orgSummaryGenerator;
    
	final static Log logger = LogFactory.getLog(BatchOrganizationSummaryProcess.class);
   
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
	@Autowired
	private OrganizationDao organizationDao;
	
    @Autowired 
    private BatchReportProcessService batchReportProcessService;
 
    
	@Override
    public StudentReport process(Long organizationId) throws Exception {
		logger.info("Inside BatchOrganizationSummaryProcess for organization report Generation...." + organizationId);
		StudentReport studentReport = new StudentReport();
		studentReport.setAssessmentProgramId(assessmentProgramId);
		studentReport.setAssessmentProgramCode(assessmentProgramCode);
		studentReport.setContentAreaId(contentArea.getId());
		studentReport.setContentAreaCode(contentArea.getAbbreviatedName());
		studentReport.setContentAreaName(contentArea.getName());
		studentReport.setCurrentSchoolYear(schoolYear);
		List<Long> orgIds = new ArrayList<Long>();
		List<Long> orgGradeIds = new ArrayList<Long>();
 		Organization orgDetails = organizationDao.getOrgDetailsForSummmaryReport(organizationId);
 		String reportType=orgDetails.getOrganizationType().getTypeCode();
		if(orgDetails.getOrganizationType().getTypeCode().equals("SCH")){
 			studentReport.setAttendanceSchoolId(orgDetails.getId());
 			orgIds.add(orgDetails.getId());
 			
 			if(orgDetails.getShortOrgName()!=null && !orgDetails.getShortOrgName().equals(""))  
 				studentReport.setSummaryHeaderSchoolName(orgDetails.getShortOrgName());
 			else 
 				studentReport.setSummaryHeaderSchoolName(orgDetails.getOrganizationName());
 			
 			
 			studentReport.setAttendanceSchoolName(orgDetails.getOrganizationName());
 			studentReport.setAttSchDisplayIdentifier(orgDetails.getDisplayIdentifier());
			orgGradeIds = batchReportProcessService.getDistinctGradeIdsInOrgFromMedains(assessmentProgramId, contentArea.getId(), schoolYear,  orgDetails.getId());
			Organization districtOrg = organizationDao.getDistrictBySchoolOrgIdForSummaryReport(organizationId);
 			orgIds.add(districtOrg.getId());
			studentReport.setDistrictId(districtOrg.getId());
			studentReport.setDistrictDisplayIdentifier(districtOrg.getDisplayIdentifier());
			
			if(districtOrg.getShortOrgName()!=null && !districtOrg.getShortOrgName().equals("")) 
 				studentReport.setSummaryHeaderDistrictName(districtOrg.getShortOrgName());
 			else 
 				studentReport.setSummaryHeaderDistrictName(districtOrg.getOrganizationName());
			
			
			studentReport.setDistrictName(districtOrg.getOrganizationName());
		} else if(orgDetails.getOrganizationType().getTypeCode().equals("DT")){
			studentReport.setDistrictId(orgDetails.getId());
 			orgIds.add(orgDetails.getId());
			studentReport.setDistrictDisplayIdentifier(orgDetails.getDisplayIdentifier());
			
			if(orgDetails.getShortOrgName()!=null && !orgDetails.getShortOrgName().equals("")) 
 				studentReport.setSummaryHeaderDistrictName(orgDetails.getShortOrgName());
 			else 
 				studentReport.setSummaryHeaderDistrictName(orgDetails.getOrganizationName());
			
			studentReport.setDistrictName(orgDetails.getOrganizationName());
			orgGradeIds = batchReportProcessService.getDistinctGradeIdsInOrgFromMedains(assessmentProgramId, contentArea.getId(), schoolYear,  orgDetails.getId());
    	} 
    	List<Organization> stateOrg = organizationDao.getImmediateParents(studentReport.getDistrictId());
		studentReport.setStateId(stateOrg.get(0).getId()); 
		orgIds.add(stateOrg.get(0).getId());
       	if(orgDetails.getOrganizationType().getTypeCode().equals("SCH") ||
         	   orgDetails.getOrganizationType().getTypeCode().equals("DT") ||
       			orgDetails.getOrganizationType().getTypeCode().equals("ST")){
       		    if(orgDetails.getOrganizationType().getTypeCode().equals("ST")){
       		    }
         	} 
		if(orgGradeIds.size() > 0){
			List<ReportsMedianScore> reportMedianScores = batchReportProcessService.getMedainScoreByOrgIdsAssessmentContentGradeSchoolYear(orgIds,  assessmentProgramId, contentArea.getId(), orgGradeIds, schoolYear);		
			studentReport.setMedianScores(reportMedianScores);
		
			
			if(CollectionUtils.isNotEmpty(reportMedianScores)){
				//TODO : US17665 Removing testid1, testid2 columns from leveldescription table so, this has to be revisited
				/*List<LevelDescription> levelDetails = levelDescriptionService.getDistinctLevelsAndTestCutMinMax(studentReport.getAssessmentProgramId(), 
						studentReport.getContentAreaId(), reportMedianScores.get(0).getGradeId(), studentReport.getCurrentSchoolYear());
				studentReport.setLevels(levelDetails);*/
				
				List<LevelDescription> levelDescriptions = new ArrayList<LevelDescription>();
				
				List<LevelDescription> levelDetails = levelDescriptionService.getLevelDescriptionByLevelId(schoolYear,
						studentReport.getAssessmentProgramId(),studentReport.getContentAreaId(),reportMedianScores.get(0).getGradeId(),"Main", testingProgramId);
				if(CollectionUtils.isNotEmpty(levelDetails)){
					levelDescriptions.addAll(levelDetails);	
				}
				studentReport.setLevels(levelDescriptions);
				
			}
			
			List<ReportsPercentByLevel>  percentByLevels = batchReportProcessService.selectReportPercentByOrganization(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), null, studentReport.getCurrentSchoolYear(), orgIds);
			studentReport.setPercentByLevels(percentByLevels);
		
		}
	
			

		List<ReportSubscores> orgReportSubScores = new ArrayList<ReportSubscores>();
		List<ReportSubscores> organizationScores = batchReportProcessService.selectSubscoresDetailsForOrganization(organizationId,  assessmentProgramId, contentArea.getId(), orgGradeIds, schoolYear,reportType);
		
		
		
			if(CollectionUtils.isNotEmpty(organizationScores)){
				orgReportSubScores.addAll(organizationScores);
			}
		
		studentReport.setTestLevelSubscoreBuckets(orgReportSubScores);
				
				
		
		try {
			String summaryReportFilePath = orgSummaryGenerator.generateReportFile(studentReport);
			studentReport.setFilePath(summaryReportFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentReport;
		
    }
	
	
		public StepExecution getStepExecution() {
			return stepExecution;
		}

		public void setStepExecution(StepExecution stepExecution) {
			this.stepExecution = stepExecution;
		}

		public Long getAssessmentProgramId() {
			return assessmentProgramId;
		}

		public void setAssessmentProgramId(Long assessmentProgramId) {
			this.assessmentProgramId = assessmentProgramId;
		}

		public String getAssessmentProgramCode() {
			return assessmentProgramCode;
		}

		public void setAssessmentProgramCode(String assessmentProgramCode) {
			this.assessmentProgramCode = assessmentProgramCode;
		}

		public Long getBatchReportProcessId() {
			return batchReportProcessId;
		}

		public void setBatchReportProcessId(Long batchReportProcessId) {
			this.batchReportProcessId = batchReportProcessId;
		}


		public ContentArea getContentArea() {
			return contentArea;
		}


		public void setContentArea(ContentArea contentArea) {
			this.contentArea = contentArea;
		}


		public GradeCourse getGradeCourse() {
			return gradeCourse;
		}


		public void setGradeCourse(GradeCourse gradeCourse) {
			this.gradeCourse = gradeCourse;
		}


		public Long getSchoolYear() {
			return schoolYear;
		}


		public void setSchoolYear(Long schoolYear) {
			this.schoolYear = schoolYear;
		}


		public Long getTestingProgramId() {
			return testingProgramId;
		}


		public void setTestingProgramId(Long testingProgramId) {
			this.testingProgramId = testingProgramId;
		}
		
		
}
