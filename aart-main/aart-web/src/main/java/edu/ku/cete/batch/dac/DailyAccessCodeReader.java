package edu.ku.cete.batch.dac;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.util.CommonConstants;

public class DailyAccessCodeReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(DailyAccessCodeReader.class);

	private Long operationalTestWindowId;
	private String assessmentProgramCode;
	private Long batchRegistrationId;
	
	@Autowired
	private OperationalTestWindowService otwService;

	@Override
	protected void doReadPage() {
		logger.debug("reading data batchRegistrationId:"+batchRegistrationId);
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		if(!StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW))
		{
		results.addAll(getPartsByWindowId(operationalTestWindowId, getPage() * getPageSize(), getPageSize()));
		}else
		{
			results.addAll(getPartsByGradeBandId(operationalTestWindowId, getPage() * getPageSize(), getPageSize()));
		}
		logger.debug("done reading data batchRegistrationId:"+batchRegistrationId);
	}

	@Override
	protected void doJumpToPage(int arg0) {
		//no-op
		logger.debug("NO-OP");
	}
	
	@SuppressWarnings("unchecked")
	private List<T> getPartsByWindowId(Long testWindowId, Integer offset, Integer pageSize) {
		
		return (List<T>) otwService.getDacTestStagesByWindow(testWindowId);
	}
	
	@SuppressWarnings("unchecked")
	private List<T> getPartsByGradeBandId(Long testWindowId, Integer offset, Integer pageSize) {
		
		return (List<T>) otwService.getDacTestStagesByGradeBand(testWindowId);
	}
	
	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}


}
