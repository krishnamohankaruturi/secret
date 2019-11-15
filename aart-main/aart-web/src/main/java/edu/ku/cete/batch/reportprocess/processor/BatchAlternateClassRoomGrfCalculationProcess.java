package edu.ku.cete.batch.reportprocess.processor;
 
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.domain.report.AlternateAggregateStudents;
import edu.ku.cete.domain.report.AlternateAggregateSubject;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.util.CommonConstants;

public class BatchAlternateClassRoomGrfCalculationProcess implements ItemProcessor<AlternateAggregateReport,Object>
{
	@Autowired 
	UploadGrfFileService uploadGrfFileService;
		
	private Long assessmentProgramId;
	private Long batchUploadProcessId;
	private Long reportYear;
	private Long stateId;
	private Long userId;
	
	final static Log logger = LogFactory.getLog(BatchAlternateClassRoomGrfCalculationProcess.class);
    
	@Override
    public AlternateAggregateReport process(AlternateAggregateReport grfData) throws Exception {
		logger.debug("Inside BatchAlternateClassRoomGrfCalculationProcess for Classroom Report Calculation - SchoolId - "+ grfData.getSchoolId()+" - TeacherId - "+grfData.getKiteEducatorIdentifier());
		AlternateAggregateReport aggregateGRFReportData = uploadGrfFileService.getGeneralResearchDataBYTeacherIdSchoolId(grfData.getKiteEducatorIdentifier(),grfData.getSchoolId(), reportYear, assessmentProgramId, stateId);
		return doCalculationGrfDataForClassRoomReport(aggregateGRFReportData);		
    }

	private AlternateAggregateReport doCalculationGrfDataForClassRoomReport(AlternateAggregateReport aggregateGRFReportData){
		Map<String, Long> levels = new HashMap<String, Long>();		
		 if(aggregateGRFReportData!=null && aggregateGRFReportData.getAlternateAggregateStudents().size()>0){			 
			 for(AlternateAggregateStudents alternateAggregateStudent : aggregateGRFReportData.getAlternateAggregateStudents()){
				 String educatorFirstName  = alternateAggregateStudent.getEducatorFirstName();
				 String educatorLastName  = alternateAggregateStudent.getEducatorLastName();
				 if(alternateAggregateStudent.getAlternateAggregateSubject().size()>0){			 
					 for(AlternateAggregateSubject alternateAggregateSubject : alternateAggregateStudent.getAlternateAggregateSubject()){
						 levels.put("EESTested", 0l);
						 levels.put("aboveTarget", 0l);
						 levels.put("skillsMastered", 0l);						 
						 if(alternateAggregateSubject.getPerformanceLevel().longValue()!=CommonConstants.GRF_LEVEL_9.longValue()){							 
							 calcluateStudentGRF(alternateAggregateSubject.getEe1(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe2(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe3(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe4(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe5(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe6(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe7(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe8(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe9(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe10(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe11(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe12(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe13(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe14(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe15(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe16(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe17(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe18(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe19(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe20(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe21(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe22(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe23(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe24(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe25(), alternateAggregateSubject.getSubjectCode(), levels);
							 calcluateStudentGRF(alternateAggregateSubject.getEe26(), alternateAggregateSubject.getSubjectCode(), levels);
							 alternateAggregateSubject.setEESTested(levels.get("EESTested"));
							 alternateAggregateSubject.setAboveTarget(levels.get("aboveTarget"));
							 alternateAggregateSubject.setSkillsMastered(levels.get("skillsMastered"));	
							 if(alternateAggregateSubject.getPerformanceLevel().longValue()==CommonConstants.GRF_LEVEL_1.longValue()) alternateAggregateSubject.setAchievementLevel("Emerging");
							 else if(alternateAggregateSubject.getPerformanceLevel().longValue()==CommonConstants.GRF_LEVEL_2.longValue()) alternateAggregateSubject.setAchievementLevel("Approaching Target");
							 else if(alternateAggregateSubject.getPerformanceLevel().longValue()==CommonConstants.GRF_LEVEL_3.longValue()) alternateAggregateSubject.setAchievementLevel("At Target");
							 else if(alternateAggregateSubject.getPerformanceLevel().longValue()==CommonConstants.GRF_LEVEL_4.longValue()) alternateAggregateSubject.setAchievementLevel("Advanced");
	
					     }	
						 else if(alternateAggregateSubject.getPerformanceLevel().longValue()==CommonConstants.GRF_LEVEL_9.longValue()){
							 alternateAggregateSubject.setEESTested(0L);
							 alternateAggregateSubject.setAboveTarget(0L);
							 alternateAggregateSubject.setSkillsMastered(0L);	
							 alternateAggregateSubject.setAchievementLevel("-"); 
						 }
			         }		 			 
			     }	
				 alternateAggregateStudent.setEducatorFirstName(educatorFirstName);
				 alternateAggregateStudent.setEducatorLastName(educatorLastName);
		     }
			 aggregateGRFReportData.setEducatorFirstName(aggregateGRFReportData.getAlternateAggregateStudents().get(0).getEducatorFirstName());
			 aggregateGRFReportData.setEducatorLastName(aggregateGRFReportData.getAlternateAggregateStudents().get(0).getEducatorLastName());
			 aggregateGRFReportData.setAssessmentProgramId(assessmentProgramId); 
		 	 aggregateGRFReportData.setCreatedUser(userId);
			 aggregateGRFReportData.setModifiedUser(userId);
		 }
		
		 return aggregateGRFReportData;
	}

	private Map<String, Long> calcluateStudentGRF(Long EESValue,String subjectCode, Map<String, Long> levels){
		EESValue = EESValue==null?0L:EESValue;
		if(EESValue!=9){
			if((subjectCode.equalsIgnoreCase("ELA") || subjectCode.equalsIgnoreCase("M")) && (EESValue== 4 || EESValue== 5)) 
				levels.put("aboveTarget", levels.get("aboveTarget").longValue()+1);
			else if(subjectCode.equalsIgnoreCase("Sci") && EESValue== 3) 
				levels.put("aboveTarget", levels.get("aboveTarget").longValue()+1);
			
			levels.put("skillsMastered", levels.get("skillsMastered").longValue()+EESValue);
			levels.put("EESTested", levels.get("EESTested").longValue()+1);
			
			return levels;
		}
		return levels;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getBatchUploadProcessId() {
		return batchUploadProcessId;
	}

	public void setBatchUploadProcessId(Long batchUploadProcessId) {
		this.batchUploadProcessId = batchUploadProcessId;
	}

	public Long getReportYear() {
		return reportYear;
	}

	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
