package edu.ku.cete.batch.reportprocess.writer;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchSchoolCompressedFileReportProcessWriter implements ItemWriter<OrganizationReportDetails>{
	
	final static Log logger = LogFactory.getLog(BatchSchoolCompressedFileReportProcessWriter.class);

	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	private Long batchReportProcessId;
	
    private String assessmentProgramCode;
    	
	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
		
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;

	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
	
	@Override
	public void write(List<? extends OrganizationReportDetails> organizationReportDetailsList)
			throws Exception {
		if(CollectionUtils.isNotEmpty(organizationReportDetailsList)) {
			for(OrganizationReportDetails organizationReportDetails : organizationReportDetailsList) {
				logger.debug("Inside  BatchSchoolCompressedFileReportProcessWriter for organizationid - " + organizationReportDetails.getOrganizationId());
				if(organizationReportDetails.getDetailedReportPath() != null){ //Added Because For DLM and CPASS there is no compressed file required.
					OrganizationReportDetails schoolZipReportPeoports = new OrganizationReportDetails();
					schoolZipReportPeoports.setAssessmentProgramId(organizationReportDetails.getAssessmentProgramId());
					schoolZipReportPeoports.setOrganizationId(organizationReportDetails.getOrganizationId());
					schoolZipReportPeoports.setDetailedReportPath(organizationReportDetails.getDetailedReportPath());
					schoolZipReportPeoports.setSummaryReportPath(organizationReportDetails.getSummaryReportPath());
					schoolZipReportPeoports.setSchoolReportZipSize(organizationReportDetails.getSchoolReportZipSize());
					schoolZipReportPeoports.setSchoolYear(organizationReportDetails.getSchoolYear());				
					schoolZipReportPeoports.setBatchReportProcessId(batchReportProcessId);
					schoolZipReportPeoports.setCreatedDate(new Date());					
					if(assessmentProgramCode!=null && "CPASS".equals(assessmentProgramCode))
						schoolZipReportPeoports.setReportType(cpassStudentBundledReportTypeCode);
					else if(assessmentProgramCode!=null && "DLM".equals(assessmentProgramCode))
						schoolZipReportPeoports.setReportType(alternateStudentBundledReportTypeCode);
					else if(assessmentProgramCode!=null && "KAP".equals(assessmentProgramCode))
						schoolZipReportPeoports.setReportType(generalStudentBundledReportTypeCode);
					
					batchReportProcessService.insertSchoolReportZip(schoolZipReportPeoports);
				}
				logger.debug("Completed BatchSchoolCompressedFileReportProcessWriter organizationid- " + organizationReportDetails.getOrganizationId());
			}
		}
		
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
}
