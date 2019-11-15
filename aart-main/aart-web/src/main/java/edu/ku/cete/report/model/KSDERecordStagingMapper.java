package edu.ku.cete.report.model;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.ksde.kids.result.KSDERecord;

public interface KSDERecordStagingMapper {
	
	int insertKidStaging(KSDERecord record);
	
	int insertTascStaging(KSDERecord record);
	
	KSDERecord getKidStagingRecord( @Param("ksdeXmlAuditId") Long ksdeXmlAuditId, @Param("seqNo") Long seqNo);
	
	KSDERecord getTascStagingRecord( @Param("ksdeXmlAuditId") Long ksdeXmlAuditId, @Param("seqNo") Long seqNo);
	
	List<KSDERecord> getKidsStagingRecords(@Param("kidsEmailDate") Date kidsEmailDate);
	List<KSDERecord> getTASCStagingRecords(@Param("kidsEmailDate") Date kidsEmailDate);
	
	void updateKIDSEmailStatus(KSDERecord record);
	void updateTASCEmailStatus(KSDERecord record);

}