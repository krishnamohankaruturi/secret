package edu.ku.cete.batch.upload.processor;
 
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.report.DLMOrganizationSummaryReportGenerator;
import edu.ku.cete.service.report.OrganizationGrfCalculationService;

import edu.ku.cete.util.TimerUtil;

public class BatchGRFProcessProcessor implements ItemProcessor<UploadGrfFile,Object>
{
 
	@Autowired
	private DLMOrganizationSummaryReportGenerator dlmOrgSummaryGenerator;
	
	final static Log logger = LogFactory.getLog(BatchGRFProcessProcessor.class);
	
	@Autowired 
	OrganizationGrfCalculationService orgGrfCalculationService;
    
	@Autowired
	private OrganizationDao organizationDao;
	
	@Value("${GRF.original.upload}")
	private String OriginalGrfUpload;
	
	private Long stateId;
	private Long assessmentProgramId;
    private Map<String, Long> subjectMap;
    private Map<String, Long> gradeMap;
    private Map<String, Long> organizationMap;
    private String stateCode;
    private Long reportYear;
    private String grfUploadType;
    private Long batchUploadUserId;
    private Long batchUploadId;

	
	@Override
    public UploadGrfFile process(UploadGrfFile uploadGrfFile) throws Exception {
		TimerUtil timerUtil = TimerUtil.getInstance();
		timerUtil.start();
		//Set appropriate Ids
		if(!StringUtils.isEmpty(uploadGrfFile.getSubject())) uploadGrfFile.setGradeId(gradeMap.get(uploadGrfFile.getSubject().toUpperCase()+"-"+ uploadGrfFile.getCurrentGradelevel().toUpperCase()));
		uploadGrfFile.setDistrictId(organizationMap.get(uploadGrfFile.getResidenceDistrictIdentifier()));
		uploadGrfFile.setSchoolId(organizationMap.get(uploadGrfFile.getSchoolIdentifier()));
		if(!StringUtils.isEmpty(uploadGrfFile.getSubject())) uploadGrfFile.setSubjectId(subjectMap.get(uploadGrfFile.getSubject().toUpperCase()));
		uploadGrfFile.setOriginal(OriginalGrfUpload.equalsIgnoreCase(grfUploadType)?true:false);
		uploadGrfFile.setStateCode(stateCode);
		uploadGrfFile.setActiveFlag(true);		
		uploadGrfFile.setBatchUploadId(getBatchUploadId());
		uploadGrfFile.setUploadedUserId(getBatchUploadUserId());
		uploadGrfFile.setReportYear(reportYear);
		uploadGrfFile.setStateId(stateId);
		uploadGrfFile.setRecentVersion(true);
		uploadGrfFile.setAssessmentProgramId(assessmentProgramId);
		timerUtil.resetAndLogInfo(logger, "### UploadGrfFile Process Duration : ");			
		return uploadGrfFile; 
    }
		
	
	public Long getReportYear() {
		return reportYear;
	}
	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	public Map<String, Long> getSubjectMap() {
		return subjectMap;
	}


	public void setSubjectMap(Map<String, Long> subjectMap) {
		this.subjectMap = subjectMap;
	}


	public Map<String, Long> getGradeMap() {
		return gradeMap;
	}


	public void setGradeMap(Map<String, Long> gradeMap) {
		this.gradeMap = gradeMap;
	}


	public Map<String, Long> getOrganizationMap() {
		return organizationMap;
	}


	public void setOrganizationMap(Map<String, Long> organizationMap) {
		this.organizationMap = organizationMap;
	}


	public String getStateCode() {
		return stateCode;
	}


	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}


	public String getGrfUploadType() {
		return grfUploadType;
	}


	public void setGrfUploadType(String grfUploadType) {
		this.grfUploadType = grfUploadType;
	}


	public Long getBatchUploadUserId() {
		return batchUploadUserId;
	}


	public void setBatchUploadUserId(Long batchUploadUserId) {
		this.batchUploadUserId = batchUploadUserId;
	}


	public Long getBatchUploadId() {
		return batchUploadId;
	}


	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}	
}
