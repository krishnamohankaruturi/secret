package edu.ku.cete.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.ksde.kids.result.KSDERecord;
import edu.ku.cete.report.model.KSDERecordStagingMapper;
import edu.ku.cete.service.KSDERecordStagingService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class KSDERecordStagingServiceImpl implements KSDERecordStagingService{
	private static final Log LOGGER = LogFactory.getLog(KSDERecordStagingServiceImpl.class);

    @Autowired
    private KSDERecordStagingMapper mapper;   
    
    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertKidsRecord(KSDERecord stgRec){
    	LOGGER.debug("Entering KSDERecordStagingService insertKidsRecord");
		int count = mapper.insertKidStaging(stgRec);
		LOGGER.debug("Exiting KSDERecordStagingService insertKidsRecord");
		return count;
	}
    
    @Override
   	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertTascRecord(KSDERecord stgRec){
		LOGGER.debug("Entering KSDERecordStagingService insertTascRecord");
		int count = mapper.insertTascStaging(stgRec);
		LOGGER.debug("Exiting KSDERecordStagingService insertTascRecord");
		return count;
	}
	
    @Override
	public KSDERecord getKidStagingRecord(Long ksdeXmlAuditId, Long seqNo){
		return mapper.getKidStagingRecord(ksdeXmlAuditId, seqNo);
	}
	
    @Override
	public KSDERecord getTascStagingRecord(Long ksdeXmlAuditId, Long seqNo){
		return mapper.getTascStagingRecord(ksdeXmlAuditId, seqNo);
	}
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
   	public List<KSDERecord> getKidsStagingRecords(Date kidsEmailDate){
   		LOGGER.debug("Entering KSDERecordStagingService getKidStagingRecords");
   		List<KSDERecord> records = mapper.getKidsStagingRecords(kidsEmailDate);
   		LOGGER.debug("Exiting KSDERecordStagingService getKidStagingRecords");
   		return records;
   	}
       
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
   	public List<KSDERecord> getTASCStagingRecords(Date kidsEmailDate){
   		LOGGER.debug("Entering KSDERecordStagingService getTASCStagingRecords");
   		List<KSDERecord> records = mapper.getTASCStagingRecords(kidsEmailDate);
   		LOGGER.debug("Exiting KSDERecordStagingService getTASCStagingRecords");
   		return records;
   	}
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateKIDSEmailStatus(KSDERecord kidsRecord){
    	mapper.updateKIDSEmailStatus(kidsRecord);
    }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateTASCEmailStatus(KSDERecord tascRecord){
    	mapper.updateTASCEmailStatus(tascRecord);
    }
    
}
