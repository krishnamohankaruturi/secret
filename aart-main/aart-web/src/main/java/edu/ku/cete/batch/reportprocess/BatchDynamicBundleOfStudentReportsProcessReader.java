package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.report.BatchReportProcessService;

/* sudhansu.b
 * Added for F460 - Dynamic bundle report
 */	
public class BatchDynamicBundleOfStudentReportsProcessReader<T> extends AbstractPagingItemReader<T> {

	@Autowired
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;
	
	@Value("${sort.option.subject}")
	private String subject;
	
	@Value("${sort.option.school}")
	private String school;
	
	@Value("${sort.option.grade}")
	private String grade;
	
    private JobExecution jobExecution;
    private Long requestProcessId;
    private String assessmentProgramCode;
    private OrganizationBundleReport bundleRequest;	
    private List<Long> schoolIds;
    private List<Long> subjectIds;
	private List<Long> gradeIds;
    
    
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getSeparateBundleParameter(getPage() * getPageSize(), getPageSize()));
	}

	@SuppressWarnings("unchecked")
	private List<T> getSeparateBundleParameter(int offset, int pageSize) {
		
		List<T> results = null;
		
		List<Long> separateList = new ArrayList<Long>();
		int offestLimit = 0;
		
		if(bundleRequest.getSeparateFile()){
	          if(subject.equalsIgnoreCase(bundleRequest.getSort1())){
					if(subjectIds.size() > 0 && offset < subjectIds.size()){
						 offestLimit = offset+ pageSize > subjectIds.size() ? subjectIds.size() : offset+pageSize;
					      for(int i = offset;  i < offestLimit; i++ ){
					    	  separateList.add(subjectIds.get(i)); 
					      }
						
					}
				}else if(grade.equalsIgnoreCase(bundleRequest.getSort1())){
					if(gradeIds.size() > 0 && offset < gradeIds.size()){
						 offestLimit = offset+ pageSize > gradeIds.size() ? gradeIds.size() : offset+pageSize;
					      for(int i = offset;  i < offestLimit; i++ ){
					    	  separateList.add(gradeIds.get(i)); 
					      }
						
					}
				}else if(school.equalsIgnoreCase(bundleRequest.getSort1())){
					if(schoolIds.size() > 0 && offset < schoolIds.size()){
						 offestLimit = offset+ pageSize > schoolIds.size() ? schoolIds.size() : offset+pageSize;
					      for(int i = offset;  i < offestLimit; i++ ){
					    	  separateList.add(schoolIds.get(i)); 
					      }
						
					}
				}
		}else{
			separateList.add(-1l);
		}		
		results = (List<T>) separateList;
		return results;
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		// Nothing		
	}

	public JobExecution getStepExecution() {
		return jobExecution;
	}

	public void setStepExecution(JobExecution jobExecution) {
		this.jobExecution = jobExecution;
	}
	
	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public OrganizationBundleReport getBundleRequest() {
		return bundleRequest;
	}

	public void setBundleRequest(OrganizationBundleReport bundleRequest) {
		this.bundleRequest = bundleRequest;
	}

	public List<Long> getSchoolIds() {
		return schoolIds;
	}

	public void setSchoolIds(List<Long> schoolIds) {
		this.schoolIds = schoolIds;
	}

	public List<Long> getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(List<Long> subjectIds) {
		this.subjectIds = subjectIds;
	}

	public List<Long> getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(List<Long> gradeIds) {
		this.gradeIds = gradeIds;
	}

	public Long getRequestProcessId() {
		return requestProcessId;
	}

	public void setRequestProcessId(Long requestProcessId) {
		this.requestProcessId = requestProcessId;
	}		
	
	
}
