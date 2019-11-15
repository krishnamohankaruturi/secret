package edu.ku.cete.batch.dlm.survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.util.CommonConstants;

public class DLMResearchSurveyPartitioner implements Partitioner{

	private final static Log logger = LogFactory .getLog(DLMResearchSurveyPartitioner.class); 
	
	@Autowired
    private OrganizationService orgService;
    
    @Autowired
    private ContentAreaService contentAreaService; 
	
	private StepExecution stepExecution;
	
	private Long operationalWindowId;
	
	private String assessmentProgramCode;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter DLMResearchSurveyPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);		
		List<Organization> contractingOrgs = orgService.getDLMStatesForResearchSurvey(assessmentProgramCode, operationalWindowId);
		
		//I-SMART requires IS-Sci as the content area for the survey test collection
		//DLM requires OTH as the content area for the survey test collection
		List<ContentArea> contentAreas = new ArrayList<ContentArea>();
		if (CommonConstants.ASSESSMENT_PROGRAM_I_SMART.equals(assessmentProgramCode)) {
			contentAreas = contentAreaService.getContentAreasForISMARTResearchSurvey();
		} else if (CommonConstants.ASSESSMENT_PROGRAM_DLM.equals(assessmentProgramCode)) {
			contentAreas = contentAreaService.getContentAreasForResearchSurvey();
		}
		
		for(ContentArea contentArea: contentAreas) {
			for(Organization contractingOrg: contractingOrgs) {
				ExecutionContext context = new ExecutionContext();
				context.put("contentArea", contentArea);
				context.put("contractingOrganization", contractingOrg);					
				context.put("assessmentProgram", assessmentProgramCode);								
				context.put("operationalWindowId", contractingOrg.getOperationalWindowId());
				logger.info("contentArea = " + contentArea.getAbbreviatedName()  +" ,contractingOrganization = " + contractingOrg.getId() + " , OTW = " + contractingOrg.getOperationalWindowId());
				partitionMap.put(contractingOrg.getId()+"_"+contentArea.getAbbreviatedName(), context);				
			}
		}

		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
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

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}	
}
