package edu.ku.cete.batch.dlm.auto.multiassign;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.dlm.st.StudentTrackerHelper;
import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.TestEnrollmentMethod;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.OrganizationService;

public class DLMMultiAssignAutoEnrollmentPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(DLMMultiAssignAutoEnrollmentPartitioner.class);
	
    @Autowired
    private OrganizationService orgService;
    @Autowired
    private ContentAreaService contentAreaService;
	@Autowired
	private ComplexityBandService cbService;	
	@Autowired
	private BatchRegistrationService brService;
	@Autowired
	private OperationalTestWindowService otwService;
	
	private StepExecution stepExecution;
	private Long batchRegistrationId;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	private Long operationalTestWindowId;
	private Long enrollmentMethodId;
	private String enrollmentMethod;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchRegistrationPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		if(enrollmentMethod != null && enrollmentMethod.length() > 0 ){
			if(enrollmentMethod.equalsIgnoreCase("STDNTTRKR")){
				enrollmentMethod = "MLTASGNFT";
				TestEnrollmentMethod testEnrollmentMethod = brService.getTestEnrollmentMethodByCode(assessmentProgramId, enrollmentMethod);
				enrollmentMethodId = testEnrollmentMethod.getId();
			}
			Map<Long, Long> orgWindowIds = new HashMap<Long, Long>();
			if((enrollmentMethod.equalsIgnoreCase("MLTASGNFT"))
					|| (operationalTestWindowId == null && enrollmentMethod.equalsIgnoreCase("MLTASGN"))) {
				List<OperationalTestWindow> otws = brService.getEffectiveStateWindows(assessmentProgramId, enrollmentMethodId);
				for (OperationalTestWindow otw : otws) {
						orgWindowIds.put(otw.getMultipleStateId(), otw.getId());
				}
			} else if (enrollmentMethod.equalsIgnoreCase("MLTASGN")) {
				Map<Long, String> states = otwService.getOperationalTestWindowSelectedState(operationalTestWindowId);
				for(Long contractingOrgId: states.keySet()) {
					orgWindowIds.put(contractingOrgId, operationalTestWindowId);
				}
			}
			if(!orgWindowIds.isEmpty()) {
				List<ContentArea> contentAreas = contentAreaService.getContentAreasForDLMMultiAssignments(Arrays.asList("ELA","M","Sci","SS"));
				List<ComplexityBand> allBands = cbService.getAllBandsWithContentArea(assessmentProgramId);
				
				for(ContentArea contentArea: contentAreas) {
					// this line was commented in spring 2018 for Oklahoma Social Studies
					// bands need to be mapped from the ELA/M bands to SS bands, so having just one content area of bands was not getting necessary info
					//List<ComplexityBand> bandsByContentArea = StudentTrackerHelper.getBandsByContentArea(allBands, contentArea.getAbbreviatedName());
					for(Long orgId: orgWindowIds.keySet()) {
						
						Long multiAssignTestWindowId = orgWindowIds.get(orgId);
						Integer multiAssignLimit = otwService.getOTWMultiAssignDetail(multiAssignTestWindowId, contentArea.getId()).getNumberoftests();
						if(multiAssignLimit > 0) {
							ExecutionContext context = new ExecutionContext();
							context.put("contractingOrganization", orgService.get(orgId));
							context.put("contentArea", contentArea);
							context.put("allBands", allBands/*bandsByContentArea*/);
							context.put("multiAssignTestWindowId", multiAssignTestWindowId);
							context.put("multiAssignLimit", multiAssignLimit); 
							context.put("multiAssignEnrollmentMethod", enrollmentMethod); 
							partitionMap.put(orgId+"_"+contentArea.getAbbreviatedName(), context);
						}
					}
				}
			} else {
				logger.warn(enrollmentMethod+" job: no partions created. Check OTW details. BatchRegistrationId:"+batchRegistrationId);
			}
		}else{
			logger.warn("Job: no partions created. Enrollment Method not found. BatchRegistrationId:"+batchRegistrationId);
		}
		logger.debug("Created "+partitionMap.size()+" partitions.BatchRegistrationId:"+batchRegistrationId);
		return partitionMap;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
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

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public Long getEnrollmentMethodId() {
		return enrollmentMethodId;
	}

	public void setEnrollmentMethodId(Long enrollmentMethodId) {
		this.enrollmentMethodId = enrollmentMethodId;
	}
}
