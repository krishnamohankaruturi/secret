/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.SpecialCircumstance;
import edu.ku.cete.domain.StudentSpecialCircumstance;
import edu.ku.cete.domain.StudentSpecialCircumstanceExample;
import edu.ku.cete.web.StudentSpecialCircumstanceDTO;

public interface StudentSpecialCircumstanceService {

    int countByExample(StudentSpecialCircumstanceExample example);

    int deleteByExample(StudentSpecialCircumstanceExample example);

    int insert(StudentSpecialCircumstance record);

    int insertSelective(StudentSpecialCircumstance record);

    List<StudentSpecialCircumstance> selectByExample(StudentSpecialCircumstanceExample example);

    int updateByExampleSelective(@Param("record") StudentSpecialCircumstance record);

    int updateByExample(@Param("record") StudentSpecialCircumstance record, @Param("example") StudentSpecialCircumstanceExample example);
    
    List<SpecialCircumstance> getCEDSBasedOnAssessmentProgram(String assessmentProgramName);
    
    List<SpecialCircumstance> getAllSpecialCircumstances();

	List<SpecialCircumstance> getCEDSByUserState(long contractingOrgId);
	
	Long getCountOfRestrictedCodesByState(long contractingOrgId);
	
	public void saveSpecialCircumstance(Long testSessionId, List<Map<String, Object>> specialCircumstances,
		Boolean highStakesTest, Long userId);

	
	List<StudentSpecialCircumstanceDTO> getStudentSpecialCircumstanceInfo(Long orgChildrenById, Integer currentSchoolYear, List<Long> assessmentPrograms
);

	void deleteApproval(Long studentTestId, Long status);
	
	List<StudentSpecialCircumstance> selectActiveByStudentTestId(Long studentTestId, Boolean activeflag);

	void deleteByStudentTestId(Long studentTestId);

	List<SpecialCircumstance> getSCEntriesByStateAndAssessmentProgram(Long stateId, Long assessmentProgramId);

	List<SpecialCircumstance> getAppliedSCCodesByStudentsTestsId(Long studentsTestsId);
	
	StudentSpecialCircumstance getById(Long id);

	int updateStatusAndApprovedBy(StudentSpecialCircumstance record);
}
