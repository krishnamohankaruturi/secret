package edu.ku.cete.model.security;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.security.TestingProgramExample;

public interface TestingProgramDao {
	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int countByExample(TestingProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int deleteByExample(TestingProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int insert(TestingProgram record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int insertSelective(TestingProgram record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    List<TestingProgram> selectByExample(TestingProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    TestingProgram selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int updateByExampleSelective(@Param("record") TestingProgram record, @Param("example") TestingProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int updateByExample(@Param("record") TestingProgram record, @Param("example") TestingProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int updateByPrimaryKeySelective(TestingProgram record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.testingprogram
     *
     * @mbggenerated Wed Dec 12 11:18:45 CST 2012
     */
    int updateByPrimaryKey(TestingProgram record);

    List<TestingProgram> selectByTestCollection(Long testCollectionId);
    
    /**
     * @param testCollectionId {@link Long}
     * @param assessmentId {@link Long}
     * @return TestingProgram
     */
    TestingProgram selectByTestCollectionAndAssessment(@Param("testCollectionId") Long testCollectionId, @Param("assessmentId") Long assessmentId);
    
    /**
     * If testing program abbreviation is passed then only those are returned. If it is passed as null
     * then testing program abbreviation is not checked.
     * 
     * @param assessmentProgramId {@link Long}
     * @param testingProgramAbbreviation {@link String} 
     * @return List<{@link TestingProgram}>
     */
    List<TestingProgram> getByAssessmentProgramId(
    		@Param("assessmentProgramId") Long assessmentProgramId);
    
    /**
     * @return
     */
    List<TestingProgram> selectAllTestingPrograms(long organizationId);
    /**
     * 
     * @return
     */
    List<TestingProgram>selectTestingProgramsForAutoRegistration(@Param("assessmentProgramId") Long assessmentProgramId);
   
    List<TestingProgram> selectByAssessmentProgIdAndTestingProgAbbr(@Param("assessmentProgramId") Long assessmentProgramId,
    		@Param("testingProgramAbbr") String testingProgramAbbr);

	List<TestingProgram> getDynamicTestingProgramByAssessmentProgramId(Long assessmentProgramId);

	List<TestingProgram> selectAllTestingProgramsDropdown(@Param("assessmentProgramId")Long currentAssessmentProgramId);
    
	List<TestingProgram> getTestingProgramsForReporting(@Param("assessmentProgramId")Long assessmentProgramId);
	
    
}