package edu.ku.cete.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.report.domain.KSDEXMLAudit;
import edu.ku.cete.report.model.KSDEXMLAuditMapper;
import edu.ku.cete.service.KSDEXMLAuditService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class KSDEXMLAuditServiceImpl implements KSDEXMLAuditService {

	private static final Log LOGGER = LogFactory.getLog(KSDEXMLAuditServiceImpl.class);

    @Autowired
    private KSDEXMLAuditMapper xmlDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insert(KSDEXMLAudit xml) {
		LOGGER.debug("Entering KSDEXMLAuditService insert");
		int count = xmlDao.insert(xml);
		LOGGER.debug("Exiting KSDEXMLAuditService insert");
		return count;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelective(KSDEXMLAudit xml){
		LOGGER.debug("Entering KSDEXMLAuditService updateByPrimaryKeySelective");
		int count = xmlDao.updateByPrimaryKeySelective(xml);
		LOGGER.debug("Exiting KSDEXMLAuditService updateByPrimaryKeySelective");
		return count;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int selectInProcessCount() {
		LOGGER.debug("Entering KSDEXMLAuditService selectInProcessCount");
		int count = xmlDao.selectInProcessCount();
		LOGGER.debug("Exiting KSDEXMLAuditService selectInProcessCount");
		return count;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public KSDEXMLAudit selectOneNotProcessed(String string) {
		LOGGER.debug("Entering KSDEXMLAuditService selectOneNotProcessed");
		KSDEXMLAudit xml = xmlDao.selectOneNotProcessed(string);
		LOGGER.debug("Exiting KSDEXMLAuditService selectOneNotProcessed");
		return xml;
	}
}
