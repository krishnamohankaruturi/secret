package edu.ku.cete.batch.reportprocess.processor;

 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.report.OrganizationDetailReportGenerator;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.LevelDescriptionService;
public class BatchOrganizationReportProcess implements ItemProcessor<Long,Object>
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
	private OrganizationDetailReportGenerator odrGenerator;
    
	
    final static Log logger = LogFactory.getLog(BatchOrganizationReportProcess.class);
    @Autowired
	private LevelDescriptionService levelDescriptionService;
	
	@Autowired
	private OrganizationDao organizationDao;
	
    @Autowired 
    private BatchReportProcessService batchReportProcessService;
 
    
	@Override
    public StudentReport process(Long organizationId) throws Exception {
		logger.info("Inside BatchOrganizationReportProcess for organization report Generation....Org - " + organizationId);
		StudentReport studentReport = new StudentReport();
		studentReport.setAssessmentProgramId(assessmentProgramId);
		studentReport.setAssessmentProgramCode(assessmentProgramCode);
		studentReport.setContentAreaId(contentArea.getId());
		studentReport.setContentAreaCode(contentArea.getAbbreviatedName());
		studentReport.setContentAreaName(contentArea.getName());
		studentReport.setGradeCode(gradeCourse.getAbbreviatedName());
		studentReport.setGradeName(gradeCourse.getName());
		studentReport.setGradeId(gradeCourse.getId());
		studentReport.setCurrentSchoolYear(schoolYear);
		List<Long> orgIds = new ArrayList<Long>();
		
		//TODO : US17665 Removing testid1, testid2 columns from leveldescription table so, this has to be revisited
/*		List<LevelDescription> levelDetails = levelDescriptionService.getDistinctLevelsAndTestCutMinMax(studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear());
*/		
		List<LevelDescription> levelDescriptions = new ArrayList<LevelDescription>();
		
		List<LevelDescription> levelDetails = levelDescriptionService.getLevelDescriptionByLevelId(schoolYear,
				studentReport.getAssessmentProgramId(),studentReport.getContentAreaId(),studentReport.getGradeId(),"Main", testingProgramId);
		if(CollectionUtils.isNotEmpty(levelDetails)){
			levelDescriptions.addAll(levelDetails);	
		}
		studentReport.setLevels(levelDescriptions);
		
		List<ReportSubscores> subscores = new ArrayList<ReportSubscores>();
		
		Organization orgDetails = organizationDao.get(organizationId);
		String reportType = "School";
		if(orgDetails.getOrganizationType().getTypeCode().equals("SCH")){
 			studentReport.setAttendanceSchoolId(orgDetails.getId());
 			orgIds.add(orgDetails.getId());
			studentReport.setAttendanceSchoolName(orgDetails.getOrganizationName());
			List<ReportSubscores> schoolSubScores = batchReportProcessService.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, orgDetails.getId(), reportType);
			if(CollectionUtils.isNotEmpty(schoolSubScores)){
				subscores.addAll(schoolSubScores);
			}
			Organization districtOrg = organizationDao.getDistrictBySchoolOrgId(organizationId);
 			orgIds.add(districtOrg.getId());
			studentReport.setDistrictId(districtOrg.getId());
			studentReport.setDistrictDisplayIdentifier(districtOrg.getDisplayIdentifier());
			studentReport.setDistrictName(districtOrg.getOrganizationName());
			List<ReportSubscores> districtSubScores = batchReportProcessService.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, districtOrg.getId(), reportType);
			if(CollectionUtils.isNotEmpty(districtSubScores)){
				subscores.addAll(districtSubScores);
			}
		} else if(orgDetails.getOrganizationType().getTypeCode().equals("DT")){
			reportType = "District";
			studentReport.setDistrictId(orgDetails.getId());
 			orgIds.add(orgDetails.getId());
			studentReport.setDistrictDisplayIdentifier(orgDetails.getDisplayIdentifier());
			studentReport.setDistrictName(orgDetails.getOrganizationName());
			List<ReportSubscores> districtSubScores = batchReportProcessService.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, orgDetails.getId(), reportType);
			if(CollectionUtils.isNotEmpty(districtSubScores)){
				subscores.addAll(districtSubScores);
			}
    	} 
    	List<Organization> stateOrg = organizationDao.getImmediateParents(studentReport.getDistrictId());
		studentReport.setStateId(stateOrg.get(0).getId()); 
		orgIds.add(stateOrg.get(0).getId());
       	if(orgDetails.getOrganizationType().getTypeCode().equals("SCH") ||
         	   orgDetails.getOrganizationType().getTypeCode().equals("DT") ||
       			orgDetails.getOrganizationType().getTypeCode().equals("ST")){
       		    if(orgDetails.getOrganizationType().getTypeCode().equals("ST")){
       		    	reportType = "State";
       		    }
     			List<ReportSubscores> stateSubScores = batchReportProcessService.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, stateOrg.get(0).getId(), reportType);
     			if(CollectionUtils.isNotEmpty(stateSubScores)){
     				subscores.addAll(stateSubScores);
     			}
         	} 
    	studentReport.setSubscoreBuckets(subscores);
		List<ReportsMedianScore> reportMedianScores = batchReportProcessService.getMedainScoreByOrgIdsAssessmentContentGradeSchoolYear(orgIds,  assessmentProgramId, contentArea.getId(), Arrays.asList(gradeCourse.getId()), schoolYear);		
		studentReport.setMedianScores(reportMedianScores);
		
		List<ReportsPercentByLevel>  percentByLevels = batchReportProcessService.selectReportPercentByOrganization(studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), Arrays.asList(studentReport.getGradeId()), studentReport.getCurrentSchoolYear(), orgIds);
		
		studentReport.setPercentByLevels(percentByLevels);
		
		String filePath = odrGenerator.generateReportFile(studentReport);
		studentReport.setFilePath(filePath);
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
