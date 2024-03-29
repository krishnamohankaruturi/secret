package edu.ku.cete.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsResponsesExample;
import edu.ku.cete.domain.StudentsResponsesReportDto;
import edu.ku.cete.web.QuestarDTO;

public interface StudentsResponsesDao {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	int countByExample(StudentsResponsesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	int insert(StudentsResponses record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	int insertSelective(StudentsResponses record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	List<StudentsResponses> selectByExample(StudentsResponsesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	StudentsResponses selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	int updateByExampleSelective(@Param("record") StudentsResponses record,
			@Param("example") StudentsResponsesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	int updateByExample(@Param("record") StudentsResponses record, @Param("example") StudentsResponsesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	int updateByPrimaryKeySelective(StudentsResponses record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.studentsresponses
	 *
	 * @mbggenerated Fri Oct 12 14:13:47 CDT 2012
	 */
	int updateByPrimaryKey(StudentsResponses record);

	/**
	 * @return
	 */
	List<StudentsResponsesReportDto> selectStudentTestResponsesByStudentTest(
			@Param("studentsTestsIdList") List<Long> studentsTestsIdList,
			@Param("includeFoilLessItems") boolean includeFoilLessItems);

	/**
	 * @return
	 */
	StudentsResponses getStudentResponse(@Param("studentId") Long studentId, @Param("taskVariantId") Long taskVariantId,
			@Param("testId") Long testId);

	/**
	 * @param record
	 * @return
	 */
	int updateByFoilIdAndStudentsTestsId(StudentsResponses record);

	/**
	 * @author bmohanty_sta Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15536
	 *         : Student Tracker - Simple Version 1 (preliminary) Find all
	 *         responses for a given student and test.
	 * @param studentId
	 * @param testId
	 * @return
	 */
	
	@MapKey(value ="essentialElement")
	Map<String, List<StudentsResponses>> findResponsesByStudentTest(@Param("studentsTestsId") Long studentsTestsId);

	List<StudentsResponses> findQuestarResponseByStudentTestSectionId(
			@Param("studentsTestSectionsId") Long studentsTestSectionsId);

	@MapKey(value = "studentsTestSectionsId")
	HashMap<Long, List<StudentsResponses>> findQuestarResponseMapByStudentTestId(
			@Param("studentsTestsId") Long studentsTestsId);

	List<QuestarDTO> getQuestarInfo(Map<String, Object> criteria);

	int updateQuestarRequestId(@Param("questarRequestId") Long questarRequestId,
			@Param("studentsTestSectionsId") Long studentsTestSectionsId, @Param("taskVariantId") Long taskVariantId);

	StudentsResponses selectQuestarResponse(@Param("studentId") Long studentId,
			@Param("studentsTestsId") Long studentsTestsId,
			@Param("studentsTestSectionsId") Long studentsTestSectionsId, @Param("taskVariantId") Long taskVariantId);

	int updateScoreForQuestar(@Param("userId") Long userId,
			@Param("studentsTestSectionsId") Long studentsTestSectionsId, @Param("taskVariantId") Long taskVariantId,
			@Param("score") BigDecimal score);

	int insertStudentResponse(StudentsResponses sr);

	int countTotalNumberOfScoreableItems(@Param("studentId") Long studentId, @Param("testId") Long testId);

	int countNumberOfScoreableItemsWithCorrectResponses(@Param("studentId") Long studentId,
			@Param("testId") Long testId);

	StudentsResponses getStudentResponseInterim(@Param("studentId") Long studentId, @Param("taskVariantId") Long taskVariantId,
			@Param("testId") Long testId,@Param("testSessionId")Long testSessionId);

	int getNoOfNotResponsesMachineScoreItems(@Param("studentsTestId") Long studentsTestId);
}