package edu.ku.cete.model.amp;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.web.AmpExtractStudentProfileItemAttributeDTO;
import edu.ku.cete.web.AmpExtractStudentSujectSectionItemCountDTO;
import edu.ku.cete.web.AmpStudentDataExtractDTO;
import edu.ku.cete.web.StudentGradesTestedDTO;

public interface AmpDataExtractDao {
	
	List<Long> getAMPTestCollections(@Param("currentSchoolYear")int currentSchoolYear, 
			@Param("operationalTestWindows")List<Long> testCollections);
	

	List<StudentGradesTestedDTO> getStudentsGradesTested(@Param("testCollections")List<Long> testCollections, 
			@Param("currentSchoolYear")int currentSchoolYear);
	
	List<AmpStudentDataExtractDTO> getStudentData(
			@Param("testCollections")List<Long> testCollections, 
			@Param("currentSchoolYear")int currentSchoolYear);
	
	@MapKey(value="studentId")
    HashMap<Long, HashMap<Long,List<AmpExtractStudentProfileItemAttributeDTO>>> getStudentProfileItemAttributes(
    		@Param("testCollections")List<Long> testCollections, 
			@Param("currentSchoolYear")int currentSchoolYear);
    
	@MapKey(value="studentId")
	HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectItemsCount(
			@Param("testCollections")List<Long> testCollections,
			@Param("currentSchoolYear")int currentSchoolYear,
			@Param("choiceStr")String choice);
	
	@MapKey(value="studentId")
	HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> getStudentSubjectIncludedItemsCount(
			@Param("testCollections")List<Long> testCollections,
			@Param("currentSchoolYear")int currentSchoolYear,
			@Param("choiceStr")String choice);
	
	@MapKey(value="studentId")
	HashMap<Long, List<StudentsTests>> getStudentTestsStatus(
			@Param("studentIds")List<Long> studentIds);

//	@MapKey(value="studentId")
//	HashMap<Long, List<HashMap<String,Long>>> getSubjectViewedItemsCount(
//			@Param("studentIds")List<Long> studentIds, 
//			@Param("sections")List<Long> sections);
//	
//	@MapKey(value="studentId")
//	HashMap<Long, List<HashMap<String,Long>>> getSubjectAnsweredItemsCount(
//			@Param("studentIds")List<Long> studentIds, 
//			@Param("sections")List<Long> sections);
	
	@MapKey(value="studentId")
	HashMap<Long, List<HashMap<String,Long>>> getSubjectAnsweredItemsCorrectlyCount(
			@Param("studentIds")List<Long> studentIds, 
			@Param("currentSchoolYear")int currentSchoolYear,
			@Param("sections")List<Long> sections);
	
	@MapKey(value="studentId")
	HashMap<Long, List<HashMap<String,String>>> getStudentReportDataBySubject(
			@Param("studentIds")List<Long> studentIds,
			@Param("currentSchoolYear")int currentSchoolYear,
			@Param("contentAreas")List<Long> contentAreas);
//	@MapKey(value="studentId")
//	HashMap<Long, List<SubScoreDto>> getSubScoreDetails(
//			@Param("studentIds")List<Long> studentIds, 
//			@Param("contentAreas")List<Long> contentAreas, 
//			@Param("displaySequence")Long displaySequence);
	
 }