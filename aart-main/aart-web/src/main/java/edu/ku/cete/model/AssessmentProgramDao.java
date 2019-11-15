package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.web.AssessmentProgramParticipationDTO;

/**
 * @author neil.howerton
 *
 */
public interface AssessmentProgramDao {

    List<AssessmentProgram> getAll();
    
    List<AssessmentProgram> getAllActive();

    AssessmentProgram findByAssessmentProgramId(@Param("assessmentProgramId")long assessmentProgramId);
    
    AssessmentProgram findByProgramName(@Param("programName")String programName);
    
    /**
     * to get all Assessment Program Participation numbers.
     * @return
     */
    List<AssessmentProgramParticipationDTO> selectAllAssessmentProgramParticipation(); 
    
    List<AssessmentProgram> selectAssessmentProgramsForAutoRegistration();
    
    List<AssessmentProgram> selectAssessmentProgramsForBatchReporting(@Param("userOrganizationId") Long userOrganizationId);
    
    List<AssessmentProgram> findByOrganizationId(@Param("userOrganizationId")long userOrganizationId);

	AssessmentProgram findByStudentId(@Param("studentId") Long id, @Param("currentSchoolYear") int currentSchoolYear);
	
    AssessmentProgram findByAbbreviatedName(@Param("abbreviatedName") String abbreviatedName);
    
    //Added during US16351-To get Assessment programs by userId
	List<AssessmentProgram> getAllAssessmentProgramByUserId(@Param("userId") Long userId);
	
	List<AssessmentProgram> getAllAssessmentProgramByStudentId(@Param("studentId") Long studentId);
	 //Added during US16425 To get Assessment programs by orgId
	List<String> getAssessmentProgramName(@Param("userOrganizationId")long userOrganizationId);

	List<AssessmentProgram> findByTestTypeCode(@Param("testTypeCode") String testTypeCode);
	
	AssessmentProgram findByTestSessionId(@Param("testSessionId") long testSessionId);

	List<AssessmentProgram> getPermittedAPsBySelectedStateIds(@Param("stateIds") List<Long> stateIds,@Param("userId") Long userId);

	List<AssessmentProgram> getAssessmentProgramCodeById(@Param("assessmentProgramIds") List<Long> assessmentProgramIds);

	Long findAssessPgmIdByAbbreviatedName(@Param("abbreviatedName") String abbreviatedName);
	
	List<Long> getAssessPgmIdByAbbreviatedName(@Param("abbreviatedName") List<String> abbreviatedName);
}
