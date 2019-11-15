package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.service.StudentReportService;


public class BatchKELPAStudentReportProcessReader<T> extends AbstractPagingItemReader<T> {

	private Long assessmentProgramId;
	private Long schoolYear;
	private Long stateId;
	private Long gradeCourseId;
	private Long contentAreaId;
	private String processByStudentId;
	private String reprocessEntireDistrict;
	private String generateSpecificISROption;
	
	@Autowired
	private StudentReportService studentReportService;

	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}	
		
		if (getGenerateSpecificISROption()!=null && StringUtils.equalsIgnoreCase(getGenerateSpecificISROption(), "2")){
			setReprocessEntireDistrict("TRUE");
		}else{
			setReprocessEntireDistrict("FALSE");
		}
		
		results.addAll(getStudentsForReportGeneration(assessmentProgramId, schoolYear, stateId, gradeCourseId, contentAreaId,  getPage() * getPageSize(), getPageSize()));

	}

	private List<T> getStudentsForReportGeneration(Long assessmentProgramId, Long schoolYear, Long stateId, Long gradeCourseId, Long contentAreaId, int offset, int pageSize) {
		  return (List<T>)studentReportService.getStudentIdsFromStudentReport(assessmentProgramId, schoolYear, stateId, gradeCourseId, contentAreaId, processByStudentId, reprocessEntireDistrict, offset, pageSize);
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		// TODO Auto-generated method stub

	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public String getProcessByStudentId() {
		return processByStudentId;
	}

	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}

	public String getReprocessEntireDistrict() {
		return reprocessEntireDistrict;
	}

	public void setReprocessEntireDistrict(String reprocessEntireDistrict) {
		this.reprocessEntireDistrict = reprocessEntireDistrict;
	}

	public String getGenerateSpecificISROption() {
		return generateSpecificISROption;
	}

	public void setGenerateSpecificISROption(String generateSpecificISROption) {
		this.generateSpecificISROption = generateSpecificISROption;
	}


}
