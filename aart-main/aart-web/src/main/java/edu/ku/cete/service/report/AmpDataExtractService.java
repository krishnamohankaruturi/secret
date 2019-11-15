package edu.ku.cete.service.report;

import java.util.HashMap;
import java.util.List;

import edu.ku.cete.web.AmpExtractStudentProfileItemAttributeDTO;
import edu.ku.cete.web.AmpExtractStudentSujectSectionItemCountDTO;
import edu.ku.cete.web.AmpStudentDataExtractDTO;
import edu.ku.cete.web.StudentGradesTestedDTO;

public interface AmpDataExtractService {

	public List<Long> getAMPTestCollections(int currentschoolyear, List<Long> operationalTestWindowIds);
	
	public List<AmpStudentDataExtractDTO> getStudentData(List<Long> ampTestCollections, int currentSchoolyear);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentProfileItemAttributeDTO>>> getStudentProfileItemAttributes(List<Long> ampTestCollections, int currentSchoolyear);
	
	public List<StudentGradesTestedDTO> getStudentsGradesTested(List<Long> ampTestCollections, int currentSchoolyear);
	
	//public HashMap<Long, List<StudentsTests>> getStudentTestsStatus(List<Long> studentIds);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectTotalItemsCount(List<Long> ampTestCollections, int currentSchoolyear);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectViewedItemsCount(List<Long> ampTestCollections, int currentSchoolyear);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectAnsweredItemsCount(List<Long> ampTestCollections, int currentSchoolyear);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectTotalIncludedItemsCount(List<Long> ampTestCollections, int currentSchoolyear);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectViewedIncludedItemsCount(List<Long> ampTestCollections, int currentSchoolyear);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectAnsweredIncludedItemsCount(List<Long> ampTestCollections, int currentSchoolyear);
	
	public HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectAnsweredIncludedItemsCorrectlyCount(List<Long> ampTestCollections, int currentSchoolyear);
	
//	public HashMap<Long, List<HashMap<String,String>>> getStudentReportDataBySubject(List<Long>studentIds, Long enrollmentId, List<Long> contentAreas);
	
	//public HashMap<Long, List<SubScoreDto>> getSubScoreDetails(List<Long>studentIds, Long enrollmentId, List<Long> contentAreas, Long displaySequence);
	
}
