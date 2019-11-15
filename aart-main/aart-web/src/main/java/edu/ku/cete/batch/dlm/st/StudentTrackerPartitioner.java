package edu.ku.cete.batch.dlm.st;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.OrganizationService;

public class StudentTrackerPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(StudentTrackerPartitioner.class);
	
	@Autowired
    private OrganizationService orgService;
    
    @Autowired
    private ContentAreaService contentAreaService;	
    
	@Autowired
	private ComplexityBandService cbService;	
		
	private StepExecution stepExecution;
	
	private Long operationalWindowId;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	
	@Value("${report.subject.socialstudies.name}")
	private String CONTENT_AREA_SS;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchRegistrationPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		
		String assessmentProgram = "DLM";
		List<Organization> contractingOrgs = orgService.getDLMStatesWithPooltypeAndOperationalWindow(assessmentProgram, null, operationalWindowId);		
		List<ComplexityBand> allBands = cbService.getAllBandsWithContentArea(assessmentProgramId);
		
		for(Organization contractingOrg: contractingOrgs) {
			List<ContentArea> contentAreas = contentAreaService.getForStudentTracker(contractingOrg.getId());
			
			ContentArea contentAreaSS = contentAreaService.findByAbbreviatedName(CONTENT_AREA_SS);
			
			if(contentAreaSS != null) {
				contentAreas.add(contentAreaSS);
			}
			
			for(ContentArea contentArea: contentAreas) {
				ExecutionContext context = new ExecutionContext();
				context.put("contentArea", contentArea);
				context.put("contractingOrganization", contractingOrg);
				if(CONTENT_AREA_SS.equals(contentArea.getAbbreviatedName())) {
					List<ComplexityBand> bandsByContentArea = cbService.getBandsByAssessmentProgramIdContentAreaId(assessmentProgramId, contentArea.getId());
					context.put("allBands", bandsByContentArea);
				}else {
					context.put("allBands", allBands);					
				}
				
				context.put("assessmentProgram", assessmentProgram);
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

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	
	
}
