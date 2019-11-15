package edu.ku.cete.model;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentSpecialCircumstance;
import edu.ku.cete.domain.StudentSpecialCircumstanceExample;
import edu.ku.cete.web.StudentSpecialCircumstanceDTO;

public interface StudentSpecialCircumstanceDao {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table studentspecialcircumstance
	 *
	 * @mbggenerated Thu Jan 02 14:11:45 CST 2014
	 */
	int countByExample(StudentSpecialCircumstanceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table studentspecialcircumstance
	 *
	 * @mbggenerated Thu Jan 02 14:11:45 CST 2014
	 */
	int deleteByExample(StudentSpecialCircumstanceExample example, @Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table studentspecialcircumstance
	 *
	 * @mbggenerated Thu Jan 02 14:11:45 CST 2014
	 */
	int insert(StudentSpecialCircumstance record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table studentspecialcircumstance
	 *
	 * @mbggenerated Thu Jan 02 14:11:45 CST 2014
	 */
	int insertSelective(StudentSpecialCircumstance record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table studentspecialcircumstance
	 *
	 * @mbggenerated Thu Jan 02 14:11:45 CST 2014
	 */
	List<StudentSpecialCircumstance> selectByExample(StudentSpecialCircumstanceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table studentspecialcircumstance
	 *
	 * @mbggenerated Thu Jan 02 14:11:45 CST 2014
	 */
	int updateByExampleSelective(@Param("record") StudentSpecialCircumstance record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table studentspecialcircumstance
	 *
	 * @mbggenerated Thu Jan 02 14:11:45 CST 2014
	 */
	int updateByExample(@Param("record") StudentSpecialCircumstance record,
			@Param("example") StudentSpecialCircumstanceExample example);

	List<StudentSpecialCircumstanceDTO> getStudentSpecialCircumstanceInfo(
			@Param("orgChildrenById") Long orgChildrenById, @Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("assessmentPrograms") List<Long> assessmentPrograms);

	void deleteApproval(@Param("studentTestId") Long studentTestId, @Param("status") Long status,
			@Param("modifiedDate") Date modifiedDate, @Param("modifiedUser") Long modifiedUser);

	StudentSpecialCircumstance selectBy(@Param("studentTestId") Long studentTestId,
			@Param("activeFlag") Boolean activeFlag);

	List<StudentSpecialCircumstance> selectActiveByStudentTestId(@Param("studentTestId") Long studentTestId,
			@Param("activeFlag") Boolean activeFlag);

	void deleteByStudentTestId(StudentSpecialCircumstance record);
	
	StudentSpecialCircumstance selectById(@Param("id") Long id);
	
	int updateStatusAndApprovedBy(StudentSpecialCircumstance record);
}