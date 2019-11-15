package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.report.domain.OrganizationPrctByAssessmentTopic;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.report.AssessmentTopicService;
import edu.ku.cete.service.report.BatchUploadService;

@Service
public class UploadOrgPrctByAssessmentTopicCustomValidationServiceImpl
		implements BatchUploadCustomValidationService {
	final static Log logger = LogFactory
			.getLog(UploadOrgPrctByAssessmentTopicCustomValidationServiceImpl.class);
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private AssessmentTopicService assessmentTopicService;
	
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		// TODO Auto-generated method stub
		
		Long batchUploadId = (Long) params.get("batchUploadId");		
		OrganizationPrctByAssessmentTopic orgPrctByAssessmentTopic = (OrganizationPrctByAssessmentTopic) rowData;
		
		String lineNumber = orgPrctByAssessmentTopic.getLineNumber();
		orgPrctByAssessmentTopic.setUploadLevel("organization");
		
		//Validate common CPASS columns
		batchUploadService.externalResultsUploadCommonValidation(orgPrctByAssessmentTopic, validationErrors, params, mappedFieldNames);
		
		if(validationErrors.getAllErrors().size() == 0){
			
			//Validate Topic code
			List<AssessmentTopic> assessmentTopics = assessmentTopicService.getAssessmentTopic(orgPrctByAssessmentTopic.getSchoolYear(), orgPrctByAssessmentTopic.getTestType(),
					orgPrctByAssessmentTopic.getTopicCode());
			if(assessmentTopics.size() == 0){
				String errMsg = "Topic_Code is invalid.";
				logger.debug(errMsg);
				validationErrors.rejectValue("topicCode", "", new String[]{lineNumber, mappedFieldNames.get("topicCode")}, errMsg);
			}else{
				orgPrctByAssessmentTopic.setTopicId((long)assessmentTopics.get(0).getId());
				orgPrctByAssessmentTopic.setAuditColumnProperties();	
				
				if(orgPrctByAssessmentTopic.getSchoolInternalId() != null){
					orgPrctByAssessmentTopic.setOrganizationId(orgPrctByAssessmentTopic.getSchoolInternalId());
				}else if(orgPrctByAssessmentTopic.getDistrictInternalId() != null){
					orgPrctByAssessmentTopic.setOrganizationId(orgPrctByAssessmentTopic.getDistrictInternalId());	
				}else{
					orgPrctByAssessmentTopic.setOrganizationId(orgPrctByAssessmentTopic.getStateInternalId());
				}
				
				orgPrctByAssessmentTopic.setActiveFlag(true);
				orgPrctByAssessmentTopic.setReportType("CPASS_GEN_SD");
				orgPrctByAssessmentTopic.setBatchUploadedId(batchUploadId);
		   }
		}
		
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", orgPrctByAssessmentTopic);
		logger.debug("Completed validation completed.");
		return customValidationResults;
	}

}
