package edu.ku.cete.batch.dlm.auto;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.service.StudentTrackerService;

public class DLMAutoEnrollmentReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(DLMAutoEnrollmentReader.class);
	private ContentArea contentArea;
	private Organization contractingOrganization;
	private Long operationalWindowId;
	private String interimFlag;

	@Autowired
	private StudentTrackerService studentTrackerService;

	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		results.addAll(getUntrackedStudents());
	}

	@SuppressWarnings("unchecked")
	private Collection<? extends T> getUntrackedStudents() {
		boolean isInterim = interimFlag != null && "true".equalsIgnoreCase(interimFlag);
		return (Collection<? extends T>) studentTrackerService.getTrackedStudents(contractingOrganization, contentArea, operationalWindowId, isInterim, getPage() * getPageSize(), getPageSize());
	}

	@Override
	protected void doJumpToPage(int arg0) {
		//no-op
		logger.debug("NO-OP");
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}

	public String getInterimFlag() {
		return interimFlag;
	}

	public void setInterimFlag(String interimFlag) {
		this.interimFlag = interimFlag;
	}
	
}
