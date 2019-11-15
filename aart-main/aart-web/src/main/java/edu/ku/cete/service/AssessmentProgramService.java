package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.web.AssessmentProgramParticipationDTO;

/**
 * @author neil.howerton
 *
 */
public interface AssessmentProgramService {

    List<AssessmentProgram> getAll();

    AssessmentProgram findByAssessmentProgramId(long assessmentProgramId);    
   
    /**
     * to get all Assessment Program Participation numbers.
     * @return
     */
    List<AssessmentProgramParticipationDTO> selectAllAssessmentProgramParticipation();

	List<AssessmentProgram> selectAssessmentProgramsForAutoRegistration();
	
	List<AssessmentProgram> selectAssessmentProgramsForBatchReporting(Long userOrganizationId);

	List<AssessmentProgram> getAllActive();
    
	List<AssessmentProgram> findByOrganizationId(long userOrganizationId);

	AssessmentProgram findByStudentId(Long id, User user);

	AssessmentProgram findByAbbreviatedName(String abbreviatedName);
	
	/*
	 * Added during US16351-To get Assessment programs based on user id
	 */
	List<AssessmentProgram> getAllAssessmentProgramByUserId(long userId);
	 //Added during US16425 To get Assessment programs by orgId
	List<String> getProgramName(long userOrganizationId);
	
	AssessmentProgram findByTestSessionId(long testSessionId);

	List<AssessmentProgram> getPermittedAPsBySelectedStateIds(List<Long> stateIds, Long userId);

	List<AssessmentProgram> getAssessmentProgramCodeById(List<Long> assessmentProgramIds);

	List<Long> getAssessPgmIdByAbbreviatedName(List<String> kidsAssessmentPgm);
	
}
