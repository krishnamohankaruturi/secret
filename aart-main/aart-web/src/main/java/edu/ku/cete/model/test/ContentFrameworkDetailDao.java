package edu.ku.cete.model.test;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.test.ContentFrameworkDetailExample;
import edu.ku.cete.domain.test.EEConceptualAndClaimDetailsDTO;
import edu.ku.cete.web.BlueprintCriteriaAndEEDTO;
import edu.ku.cete.web.IAPContentFramework;

public interface ContentFrameworkDetailDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int countByExample(ContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int deleteByExample(ContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int insert(ContentFrameworkDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int insertSelective(ContentFrameworkDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    List<ContentFrameworkDetail> selectByExample(ContentFrameworkDetailExample example);
    List<ContentFrameworkDetail> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    ContentFrameworkDetail selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int updateByExampleSelective(@Param("record") ContentFrameworkDetail record, @Param("example") ContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int updateByExample(@Param("record") ContentFrameworkDetail record, @Param("example") ContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int updateByPrimaryKeySelective(ContentFrameworkDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.contentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 18:12:59 CDT 2012
     */
    int updateByPrimaryKey(ContentFrameworkDetail record);        

	EEConceptualAndClaimDetailsDTO getClaimConceptualAreasForSelectedEE(@Param("contentCodeId") Long contentCodeId);

	List<ContentFrameworkDetail> selectAllInterim(@Param("contentAreaId") Long contentAreaId,
			@Param("gradeCourseId") Long gradeCourseId, @Param("isInterim") Boolean isInterim,
			@Param("purposeId") Long purposeId, @Param("organizationId") Long organizationId,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear);
	
	List<BlueprintCriteriaAndEEDTO> selectCriteriaAndEEsInBlueprintBySubjectAndGrade(@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseAbbr") String gradeCourseAbbr);

    List<Long> selectFrameWorkTypeForAssessmentProgram(@Param("assessmentProgramId") Long assessmentProgramId, @Param("frameworkCode") String frameworkCode);

    Long selectContentFrameworkCodeWithLevelAssessmentGradeContentarea(@Param("assessmentProgramId") Long assessmentProgramId, 
    											 @Param("gradeId") Long gradeId,
    											 @Param("contentAreaId") Long contentAreaId,
    											 @Param("frameworkTypeIds") List<Long> frameworkTypeIds,
    											 @Param("levelTitle") String levelTitle,
    											 @Param("contentCode") String contentCode);
	
	List<IAPContentFramework> getIAPContentFrameworkForWindow(
		@Param("operationalTestWindowId") Long operationalTestWindowId,
		@Param("contentAreaAbbreviatedName") String contentAreaAbbreviatedName,
		@Param("gradeCourseAbbreviatedName") String gradeCourseAbbreviatedName
	);
	
}