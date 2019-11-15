package edu.ku.cete.model;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.report.ReportProcessReason;

public interface ReportProcessReasonMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reportprocessreason
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	int insert(ReportProcessReason record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reportprocessreason
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	int insertSelective(ReportProcessReason record);
	
	List<ReportProcessReason> selectByReportProcessId(@Param("reportProcessId") Long reportProcessId);
}