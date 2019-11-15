package edu.ku.cete.report.model;

import java.util.List;

import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.report.domain.QuestarRegistrationReason;

public interface QuestarRegistrationReasonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table questarregistrationreason
     *
     * @mbggenerated Tue Jun 23 11:10:06 CDT 2015
     */
    int insert(QuestarRegistrationReason record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table questarregistrationreason
     *
     * @mbggenerated Tue Jun 23 11:10:06 CDT 2015
     */
    int insertSelective(QuestarRegistrationReason record);
    
    List<BatchRegistrationReason> selectByBatchRegistrationId(Long batchRegistrationId);
}