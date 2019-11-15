package edu.ku.cete.service;

import java.util.Date;
import java.util.List;

import edu.ku.cete.ksde.kids.result.KSDERecord;

public interface KSDERecordStagingService {
	
	int insertKidsRecord(KSDERecord stgRec);
	
	int insertTascRecord(KSDERecord stgRec);
	
	KSDERecord getKidStagingRecord(Long ksdeXmlAuditId, Long seqNo);
	
	KSDERecord getTascStagingRecord(Long ksdeXmlAuditId, Long seqNo);
	
	List<KSDERecord> getKidsStagingRecords(Date lastSubmissionDate);
	List<KSDERecord> getTASCStagingRecords(Date lastSubmissionDate);
	
	void updateKIDSEmailStatus(KSDERecord kidsRecord);
	void updateTASCEmailStatus(KSDERecord tascRecord);

}
