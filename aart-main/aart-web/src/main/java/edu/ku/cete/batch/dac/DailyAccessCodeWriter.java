package edu.ku.cete.batch.dac;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.service.StudentsTestsService;

public class DailyAccessCodeWriter implements ItemWriter<List<DailyAccessCode>> {
	private final static Log logger = LogFactory.getLog(DailyAccessCodeWriter.class);

	private Long batchRegistrationId;
	
	@Autowired
	private StudentsTestsService stService;
	
	@Override
	public void write(List<? extends List<DailyAccessCode>> accessCodes) throws Exception {
		logger.debug("--> write : batchRegistrationId "+batchRegistrationId);
		if(!accessCodes.isEmpty() && !accessCodes.get(0).isEmpty()) {
			stService.saveDailyAccessCodes(accessCodes.get(0));
		}
		logger.debug("<-- write : batchRegistrationId "+batchRegistrationId);
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}
}
