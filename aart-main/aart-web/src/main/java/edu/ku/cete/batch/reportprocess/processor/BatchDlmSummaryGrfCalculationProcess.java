package edu.ku.cete.batch.reportprocess.processor;
 
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.report.OrganizationGrfCalculation;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.util.CommonConstants;

public class BatchDlmSummaryGrfCalculationProcess implements ItemProcessor<Long,Object>
{
	@Autowired 
	UploadGrfFileService uploadGrfFileService;

	@Autowired
	private OrganizationDao organizationDao;
		
	private Long assessmentProgramId;
	private Long batchUploadProcessId;
	private Long reportYear;
	private Long stateId;
	private Long subjectId;
	private Long gradeId;
	private Long userId;
	
	final static Log logger = LogFactory.getLog(BatchDlmSummaryGrfCalculationProcess.class);
 
    
	@Override
    public OrganizationGrfCalculation process(Long organizationId) throws Exception {
		logger.debug("Inside BatchSummaryGrfCalculationProcess for organization report Generation...OrgId." + organizationId+" - subjectId "+subjectId+" - gradeId "+gradeId);
		List<UploadGrfFile> uploadGeneralResearchLists = uploadGrfFileService.getGeneralResearchDataBYOrgIdandSubjectidandgradeId(organizationId, reportYear, assessmentProgramId, subjectId, gradeId, stateId);
		return doCalculationGrfData(uploadGeneralResearchLists,organizationId,subjectId,gradeId);		
    }

	private OrganizationGrfCalculation doCalculationGrfData(List<UploadGrfFile> uploadGeneralResearchLists,Long organizationId,Long subjectId,Long gradeId){	 
		 
		 Long noOfEmerging = 0L;
		 Long noOfApproachingTarget = 0L;
		 Long noOfAtTarget = 0L;
		 Long noOfAdvanced = 0L;
		 int noOfAtTargetOrAdvanced = 0;
		 Long noOfStudentstested = 0L;		
		 OrganizationGrfCalculation orgGrfCalculation = null;		 
		 if(uploadGeneralResearchLists.size()>0){			 
			 for(UploadGrfFile dlmGeneralResearchDTO : uploadGeneralResearchLists){
				 if(dlmGeneralResearchDTO.getPerformanceLevel().longValue()!= CommonConstants.GRF_LEVEL_9 && dlmGeneralResearchDTO.getInvalidationCode().longValue()!= CommonConstants.GRF_LEVEL_1){
					 if(dlmGeneralResearchDTO.getPerformanceLevel()==CommonConstants.GRF_LEVEL_1) noOfEmerging++;
					 if(dlmGeneralResearchDTO.getPerformanceLevel()==CommonConstants.GRF_LEVEL_2) noOfApproachingTarget++;
					 if(dlmGeneralResearchDTO.getPerformanceLevel()==CommonConstants.GRF_LEVEL_3) noOfAtTarget++;
					 if(dlmGeneralResearchDTO.getPerformanceLevel()==CommonConstants.GRF_LEVEL_4) noOfAdvanced++;	 
					 noOfStudentstested++;
				 }			 			
			 }
			 noOfAtTargetOrAdvanced =  Math.round(((noOfAtTarget+noOfAdvanced)/ (float) noOfStudentstested) *100);				
		 }
		 
		 if(uploadGeneralResearchLists.size()>0 && noOfStudentstested>0){
			 orgGrfCalculation= new OrganizationGrfCalculation();
			 Organization orgDetails = organizationDao.get(organizationId);
			 if(orgDetails!=null && orgDetails.getOrganizationType().getTypeCode().equals("DT")){
			 		 orgGrfCalculation.setStateId(stateId);
					 orgGrfCalculation.setStateName(uploadGeneralResearchLists.get(0).getState());
			 		 orgGrfCalculation.setDistrictId(organizationId);
					 orgGrfCalculation.setDistrictName(orgDetails.getOrganizationName());
					 orgGrfCalculation.setDistrictCode(uploadGeneralResearchLists.get(0).getResidenceDistrictIdentifier()); 
			 }else{
					 orgGrfCalculation.setDistrictId(null);
					 orgGrfCalculation.setDistrictName("");
					 orgGrfCalculation.setDistrictCode("");
					 orgGrfCalculation.setStateId(stateId);
					 orgGrfCalculation.setStateName(uploadGeneralResearchLists.get(0).getState());
			 }		 
	
			 orgGrfCalculation.setSubjectId(subjectId);
			 orgGrfCalculation.setGradeId(gradeId);		
			 orgGrfCalculation.setReportYear(reportYear);	
			 orgGrfCalculation.setBatchUploadProcessId(batchUploadProcessId);		
			 orgGrfCalculation.setAssessmentProgramId(assessmentProgramId);		 
			 orgGrfCalculation.setGradeLevel(uploadGeneralResearchLists.get(0).getCurrentGradelevel());
			 orgGrfCalculation.setSubjectName(uploadGeneralResearchLists.get(0).getSubject());
			 orgGrfCalculation.setNoOfStudentsTested(noOfStudentstested);
			 orgGrfCalculation.setNumberOfEmerging(noOfEmerging);
			 orgGrfCalculation.setNumberOfApproachingTarget(noOfApproachingTarget);
			 orgGrfCalculation.setNumberOfAtTarget(noOfAtTarget);
			 orgGrfCalculation.setNumberOfAdvanced(noOfAdvanced);
			 orgGrfCalculation.setPercentageAtTargetAdvanced(noOfAtTargetOrAdvanced);	
			 orgGrfCalculation.setActiveFlag(true);
			 orgGrfCalculation.setCreatedUser(userId);
			 orgGrfCalculation.setModifiedUser(userId);
	    }
		
		 return orgGrfCalculation;
	}


	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getBatchUploadProcessId() {
		return batchUploadProcessId;
	}

	public void setBatchUploadProcessId(Long batchUploadProcessId) {
		this.batchUploadProcessId = batchUploadProcessId;
	}

	public Long getReportYear() {
		return reportYear;
	}

	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getSubjectId() {
		return subjectId;
	}


	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}


	public Long getGradeId() {
		return gradeId;
	}


	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
		
}
