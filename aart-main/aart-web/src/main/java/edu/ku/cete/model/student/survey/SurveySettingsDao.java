package edu.ku.cete.model.student.survey;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.student.survey.Survey;

//Per US17690
public interface SurveySettingsDao {

	List<FirstContactSettings> getFirstContactSurveySettings(@Param("organizationId")Long organizationId);

	int updateFirstContactSurveySettings(FirstContactSettings firstContactSettings);

	FirstContactSettings getFirstContactSurveySetting(Long organizationId);

	int updateFCSOnScienceEnrolement(FirstContactSettings firstContactSettings);

	int updateFCSScienceResponses(FirstContactSettings firstContactSettings);
	
	int resetFCSStatusOnOrgScienceContentFlag(@Param("organizationId") Long organizationId,@Param("existingSurveyStatus") Long existingSurveyStatus,
		@Param("newSurveyStatus") Long newSurveyStatus,@Param("modifiedUser") Long modifiedUser,@Param("modifiedDate") Date modifiedDate);
	
	FirstContactSettings getCurrentSchYrFCScienceSettingByOrgId(@Param("organizationId") Long organizationId, @Param("schoolYear") Long schoolYear,@Param("currentSchoolYear") Boolean currentSchoolYear, @Param("prevSchoolYear") Boolean prevSchoolYear);
	
	int inactivateOtherSchoolYearRecords(@Param("schoolYear") Long schoolYear, @Param("organizationId") Long organizationId,@Param("modifiedUser") Long modifiedUser);
	
	FirstContactSettings checkIfOrgSettingsExists(@Param("organizationId") Long organizationId, @Param("schoolYear") Long schoolYear);
	
	int insertFirstContactSettings(FirstContactSettings firstContactSettings);
	void auditSurveyToggleSetting(Survey survey);

	int updateFCSOnMathEnrolement(FirstContactSettings newFirstContactSettings);

	int updateFCSMathResponses(FirstContactSettings newFirstContactSettings);

	int updateFCSOnElaEnrolement(FirstContactSettings newFirstContactSettings);

	int updateFCSElaResponses(FirstContactSettings newFirstContactSettings);


	//int updateFCSOnCategoryChange(FirstContactSettings newFirstContactSettings);

}
