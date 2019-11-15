package edu.ku.cete.batch.dlm.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.dlm.st.StudentTrackerHelper;
import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.student.FirstContactService;

public class DLMAutoEnrollmentPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(DLMAutoEnrollmentPartitioner.class);
	
    @Autowired
    private OrganizationService orgService;
    
    @Autowired
    private ContentAreaService contentAreaService;
    
	@Autowired
	private ComplexityBandService cbService;
	
	@Autowired
	private FirstContactService firstContactService;
	
	private StepExecution stepExecution;
	
	private Long operationalWindowId;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	
	@Value("${report.subject.socialstudies.name}")
	private String CONTENT_AREA_SS;
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchRegistrationPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		String assessmentProgram = "DLM";
		List<Organization> contractingOrgs = orgService.getDLMStatesWithPooltypeAndOperationalWindow(assessmentProgram, null, operationalWindowId);
		if(CollectionUtils.isEmpty(contractingOrgs)){
			BatchRegistrationReason reason = new BatchRegistrationReason();
			reason.setReason("No States found for Operational Test window id : " + operationalWindowId);
			((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(reason);
		}
		List<ComplexityBand> allBands = cbService.getAllBandsWithContentArea(assessmentProgramId);
		List<ComplexityBand> bandsByContentArea = null;
		
		for(Organization contractingOrg: contractingOrgs) {
			List<ContentArea> contentAreas = contentAreaService.getForStudentTracker(contractingOrg.getId());
			
			ContentArea contentAreaSS = contentAreaService.findByAbbreviatedName(CONTENT_AREA_SS);
			
			if(contentAreaSS != null) {
				contentAreas.add(contentAreaSS);
			}
			
			for(ContentArea contentArea: contentAreas) {
				ExecutionContext context = new ExecutionContext();
				bandsByContentArea = new ArrayList<ComplexityBand>();				
				context.put("contractingOrganization", contractingOrg);
				context.put("contentArea", contentArea);
				if(CONTENT_AREA_SS.equals(contentArea.getAbbreviatedName())) {
					bandsByContentArea = cbService.getBandsByAssessmentProgramIdContentAreaId(assessmentProgramId, contentArea.getId());
				}else {
					bandsByContentArea = StudentTrackerHelper.getBandsByContentArea(allBands, contentArea.getAbbreviatedName());					
				}
				context.put("allBands", bandsByContentArea);
				context.put("operationalWindowId", contractingOrg.getOperationalWindowId());
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
