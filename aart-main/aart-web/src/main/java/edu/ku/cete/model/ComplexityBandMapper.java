package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.student.ComplexityBandJson;

public interface ComplexityBandMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexityband
     *
     * @mbggenerated Wed Dec 17 22:08:25 CST 2014
     */
    int insert(ComplexityBand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexityband
     *
     * @mbggenerated Wed Dec 17 22:08:25 CST 2014
     */
    int insertSelective(ComplexityBand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexityband
     *
     * @mbggenerated Wed Dec 17 22:08:25 CST 2014
     */
    ComplexityBand selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexityband
     *
     * @mbggenerated Wed Dec 17 22:08:25 CST 2014
     */
    int updateByPrimaryKeySelective(ComplexityBand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexityband
     *
     * @mbggenerated Wed Dec 17 22:08:25 CST 2014
     */
    int updateByPrimaryKey(ComplexityBand record);
    
    List<ComplexityBand> selectAllWithLinkageLevelInfo(@Param("assessmentProgramId") Long assessmentProgramId);
    
    List<ComplexityBand> selectNullContentAreaWithLinkageLevelInfo();
    
    List<ComplexityBand> getAllBandsWithLinkageLevelInfoByAssessmentProgramIdContentAreaId(
    			@Param("assessmentProgramId") Long assessmentProgramId,
    			@Param("contentAreaId") Long contentAreaId);
    
    ComplexityBandJson getCompBandsForJson(@Param("surveyId")Long surveyId , @Param("currentSchoolYear") Long currentSchoolYear);
    
    List<ComplexityBand> getAll();
}
