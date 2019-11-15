package edu.ku.cete.service;

import edu.ku.cete.report.domain.KSDEXMLAudit;

public interface KSDEXMLAuditService {
	
	int insert(KSDEXMLAudit xml);

	int updateByPrimaryKeySelective(KSDEXMLAudit xml);

	int selectInProcessCount();

	KSDEXMLAudit selectOneNotProcessed(String string);
	
}
