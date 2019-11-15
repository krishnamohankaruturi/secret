package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.report.domain.OrganizationReportResults;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.report.BatchUploadService;

@Service
public class UploadExternalOrgReportResultCustomValidationServiceImpl implements BatchUploadCustomValidationService {
	
	final static Log logger = LogFactory
			.getLog(UploadExternalOrgReportResultCustomValidationServiceImpl.class);
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private BatchUploadService batchUploadService;


@Override
public Map<String, Object> customValidation(
	BeanPropertyBindingResult validationErrors, Object rowData,
	Map<String, Object> params, Map<String, String> mappedFieldNames) {
	
	Long batchUploadId = (Long) params.get("batchUploadId");
	Long uploadedUserId = (Long) params.get("uploadedUserId");

	Long selectedAssessmentProgramId = (Long) params.get("assessmentProgramIdOnUI");
	Long stateIdOnUI = (Long) params.get("stateId");
	
	OrganizationReportResults organizationReportDetail = (OrganizationReportResults) rowData;
	
	String lineNumber = organizationReportDetail.getLineNumber();
	organizationReportDetail.setUploadLevel("organization");
	
	//Validate Assessment program
			AssessmentProgram selectedAP = assessmentProgramService.findByAssessmentProgramId(selectedAssessmentProgramId);
			if(!organizationReportDetail.getAssessmentProgram().equalsIgnoreCase(selectedAP.getAbbreviatedname())){
				String errMsg = "Must be assessment program specified on upload page. "+selectedAP.getAbbreviatedname()+" was selected and this line has "+organizationReportDetail.getAssessmentProgram()+".";
				logger.debug(errMsg);
				validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			}else{
				organizationReportDetail.setAssessmentProgramId(selectedAP.getId());
				//Validate common CPASS columns
				batchUploadService.externalResultsUploadCommonValidation(organizationReportDetail, validationErrors, params, mappedFieldNames);
				if(validationErrors.getAllErrors().size() == 0){			  
					organizationReportDetail.setAuditColumnProperties();
					
					if(organizationReportDetail.getSchoolInternalId() != null){
					    organizationReportDetail.setOrganizationId(organizationReportDetail.getSchoolInternalId());
					}else if(organizationReportDetail.getDistrictInternalId() != null){
						organizationReportDetail.setOrganizationId(organizationReportDetail.getDistrictInternalId());	
					}else{
						organizationReportDetail.setOrganizationId(organizationReportDetail.getStateInternalId());
					}
					
					organizationReportDetail.setActiveFlag(true);
					organizationReportDetail.setReportType("CPASS_ORG_SCORE");
					organizationReportDetail.setBatchUploadedId(batchUploadId);
				}
			}
	Map<String, Object> customValidationResults = new HashMap<String, Object>();
	customValidationResults.put("errors", validationErrors.getAllErrors());
	customValidationResults.put("rowDataObject", organizationReportDetail);
	logger.debug("Completed validation completed.");
	return customValidationResults;
}

}
