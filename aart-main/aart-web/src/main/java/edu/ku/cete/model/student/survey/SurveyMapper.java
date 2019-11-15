package edu.ku.cete.model.student.survey;

import edu.ku.cete.domain.student.SurveyJson;
import edu.ku.cete.domain.student.survey.DLMAutoRegistrationDTO;
import edu.ku.cete.domain.student.survey.Survey;
import edu.ku.cete.domain.student.survey.SurveyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SurveyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int countByExample(SurveyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int deleteByExample(SurveyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int insert(Survey record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int insertSelective(Survey record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	List<Survey> selectByExample(SurveyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	Survey selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int updateByExampleSelective(@Param("record") Survey record,
			@Param("example") SurveyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int updateByExample(@Param("record") Survey record,
			@Param("example") SurveyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int updateByPrimaryKeySelective(Survey record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table survey
	 * @mbggenerated  Sat Jan 25 22:21:48 CST 2014
	 */
	int updateByPrimaryKey(Survey record);
	
	List<DLMAutoRegistrationDTO> findSurveysForBatchAutoRegistration(
			@Param("contractingOrgId")Long contractingOrgId, 
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("currentSchoolYear") int currentSchoolYear);
	
	String findStatusCodeByStudent(@Param("studentId") Long studentId);
	
	SurveyJson getStudentAndSurveyValuesForJson(@Param("surveyId") Long surveyId, @Param("globalPageNum") Integer globalPageNum);
	SurveyJson getSurveyStatusJson(@Param("surveyId") Long surveyId);

	SurveyJson getSurveyStatusJsonByPage(@Param("surveyId")Long surveyId, @Param("globalPageNum") int globalPageNum);
}