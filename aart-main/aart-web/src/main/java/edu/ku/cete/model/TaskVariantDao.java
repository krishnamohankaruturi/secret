package edu.ku.cete.model;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.TaskVariantExample;
import edu.ku.cete.domain.content.TestSectionsTaskVariants;

public interface TaskVariantDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int countByExample(TaskVariantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int deleteByExample(TaskVariantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int insert(TaskVariant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int insertSelective(TaskVariant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    List<TaskVariant> selectByExample(TaskVariantExample example);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    List<TestSectionsTaskVariants> selectByTestSection(@Param("testSectionIds")List<Long> testSectionIds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    TaskVariant selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int updateByExampleSelective(@Param("record") TaskVariant record, @Param("example") TaskVariantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int updateByExample(@Param("record") TaskVariant record, @Param("example") TaskVariantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int updateByPrimaryKeySelective(TaskVariant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariant
     *
     * @mbggenerated Thu Oct 18 08:24:46 CDT 2012
     */
    int updateByPrimaryKey(TaskVariant record);
    
    /**
     * Gets number of responses
     * 
     */
    int countByTaskVariantId( @Param("taskVariantId") Long taskVariantId);
    
    /**
     * @param taskTypeCode
     * @param testCollectionIds
     * @return
     */
    List<Long> getTestCollectionsByTaskTypeId(@Param("taskTypeCode") String taskTypeCode, 
    		@Param("testCollectionIds") List<Long> testCollectionIds);
    
    /**
     * @param taskTypeCode
     * @param testIds
     * @return
     */
    List<Long> getTestsByTaskTypeId(@Param("taskTypeCode") String taskTypeCode, 
    		@Param("testIds") List<Long> testIds);
    
    /**
     * @param taskTypeId
     * @return
     */
    String getTaskTypeCode(@Param("taskTypeId") Long taskTypeId);
    
    String getTaskTypeCodeByTaskVariant(@Param("taskVariantId") Long taskVariantId);

	List<TaskVariant> getTaskVariantsInQuestarGroup(@Param("groupNumber") long groupNumber, @Param("taskTypeCodes") List<String> taskTypeCodes);
	
	String getTaskFormatLayoutCharactersById(@Param("id") long id);
	
	Integer getTaskVariantFoilOrder(@Param("taskVariantId") long taskVariantId, @Param("foilId") long foilId);
	
	List<TaskVariant> getTaskVariantByExternalIdGradeIdContentId(@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseAbbreviatedName") String gradeCourseAbbreviatedName,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("externalId") Long externalId, @Param("testingProgramName") String testingProgramName);

	List<TaskVariant> getTaskVariantsInSection(@Param("testSectionId") Long testSectionId);

	String getTaskTypeCodeByTaskVariantExternalIdAndTestExternalId(
			@Param("taskVariantExternalId") Long tvExternalId, @Param("testExternalId") Long testExternalId);
	
	@MapKey(value="key")
	HashMap<Long, List<Long>> getQuestarTaskvariantMapByExternalId();
	
	int taskvariantCountByTestId(@Param("testId") long testId);
	
	List<TaskVariant> getPromptAndStudentResponse(@Param("taskVariantIds")Long[] taskVariantIds, @Param("studentId") long studentId,@Param("studentsTestsId") Long studentsTestsId);

	List<TaskVariant> getItemsWithPositionByTestId(@Param("testId") Long testId);

	int findCountScorableTaskVariants(@Param("testId") Long testId,@Param("ccqScoreId") Long ccqScoreId);
	
}
