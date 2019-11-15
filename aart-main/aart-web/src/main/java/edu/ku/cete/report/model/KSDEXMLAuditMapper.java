package edu.ku.cete.report.model;

import edu.ku.cete.report.domain.KSDEXMLAudit;
import edu.ku.cete.report.domain.KSDEXMLAuditExample;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface KSDEXMLAuditMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int countByExample(KSDEXMLAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int deleteByExample(KSDEXMLAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int insert(KSDEXMLAudit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int insertSelective(KSDEXMLAudit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	List<KSDEXMLAudit> selectByExample(KSDEXMLAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	KSDEXMLAudit selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int updateByExampleSelective(@Param("record") KSDEXMLAudit record, @Param("example") KSDEXMLAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int updateByExample(@Param("record") KSDEXMLAudit record, @Param("example") KSDEXMLAuditExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int updateByPrimaryKeySelective(KSDEXMLAudit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ksdexmlaudit
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
	int updateByPrimaryKey(KSDEXMLAudit record);
	
	KSDEXMLAudit selectOneNotProcessed(@Param("xmlType") String type);
	
	int selectProcessedCount(@Param("kidsEmailDate") Date kidsEmailDate);
	int selectInProcessCount();
}