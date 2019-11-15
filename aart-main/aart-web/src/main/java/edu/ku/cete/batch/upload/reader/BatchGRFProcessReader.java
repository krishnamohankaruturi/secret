package edu.ku.cete.batch.upload.reader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.service.report.OrganizationGrfCalculationService;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.util.TimerUtil;
public class BatchGRFProcessReader<T> extends AbstractPagingItemReader<T> {
	
	
	@Autowired 
	private UploadGrfFileService uploadGrfFileService;

	@Value("${GRF.original.upload}")
	private String originalGrfUpload;
	private Long stateId;
	private Long reportYear;
	private Long assessmentProgramId;
	private Long batchUploadId;
	private String districtCode;
	private String grfUploadType;
	
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		logger.debug("Inside BatchDlmOrganizationReportProcessPageReader" );
		boolean grfUploadFlag = originalGrfUpload.equalsIgnoreCase(grfUploadType)?true:false;
		results.addAll(getValidGRFRecords(districtCode,stateId,reportYear,batchUploadId, grfUploadFlag, getPage() * getPageSize(), getPageSize()));
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		// nothing
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getValidGRFRecords(String districtCode, Long stateId,Long reportYear,Long batchUploadId, boolean grfUploadFlag, Integer offset, Integer pageSize) {
		TimerUtil timerUtil = TimerUtil.getInstance();
		timerUtil.start();
		List<T> results = (List<T>) uploadGrfFileService.getValidGRFRecords(districtCode,stateId,reportYear,batchUploadId, grfUploadFlag, offset, pageSize);
		timerUtil.resetAndLogInfo(logger, "### getValidGRFRecords Duration : ");
		return results;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
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

	public Long getBatchUploadId() {
		return batchUploadId;
	}

	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}	
	
	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	public String getGrfUploadType() {
		return grfUploadType;
	}

	public void setGrfUploadType(String grfUploadType) {
		this.grfUploadType = grfUploadType;
	}
}
