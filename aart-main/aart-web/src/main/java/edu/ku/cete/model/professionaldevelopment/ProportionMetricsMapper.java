package edu.ku.cete.model.professionaldevelopment;

import edu.ku.cete.domain.professionaldevelopment.ProportionMetrics;
import edu.ku.cete.domain.professionaldevelopment.ProportionMetricsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProportionMetricsMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table proportionmetrics
	 * @mbggenerated  Mon Dec 22 14:36:57 CST 2014
	 */
	int insert(ProportionMetrics record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table proportionmetrics
	 * @mbggenerated  Mon Dec 22 14:36:57 CST 2014
	 */
	int insertSelective(ProportionMetrics record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table proportionmetrics
	 * @mbggenerated  Mon Dec 22 14:36:57 CST 2014
	 */
	ProportionMetrics selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table proportionmetrics
	 * @mbggenerated  Mon Dec 22 14:36:57 CST 2014
	 */
	int updateByPrimaryKeySelective(ProportionMetrics record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table proportionmetrics
	 * @mbggenerated  Mon Dec 22 14:36:57 CST 2014
	 */
	int updateByPrimaryKey(ProportionMetrics record);

	/**
     * @author bmohanty_sta
     * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15536 : Student Tracker - Simple Version 1 (preliminary)
     * Get all proportion metric master data. This will be used for range checking.
     * @return
     */
    List<ProportionMetrics> getAllProportionMetricsData();
    
    List<ProportionMetrics> getAllProportionMetricsByGradeEELevel(@Param("gradeId") Long gradeId, @Param("essentialElement") String essentialElement, 
    		@Param("linkageLevelAbbr") String linkageLevelAbbr);
    
    List<ProportionMetrics> getAllProportionMetricsByGradeBandEELevel(@Param("gradeId") Long gradeId, @Param("essentialElement") String essentialElement, 
    		@Param("linkageLevelAbbr") String linkageLevelAbbr);
}