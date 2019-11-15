package edu.ku.cete.batch.dlm.st;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.service.StudentTrackerService;

public class StudentTrackerOnlyUntrackedReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(StudentTrackerOnlyUntrackedReader.class);
	private ContentArea contentArea;
	private Organization contractingOrganization;
	private Long operationalWindowId;

	@Autowired
	private StudentTrackerService studentTrackerService;

	private StepExecution stepExecution;	

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
		return (Collection<? extends T>) studentTrackerService.getOnlyUntrackedStudentsFromStudentTracker(contractingOrganization, contentArea, operationalWindowId, getPage() * getPageSize(), getPageSize());
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
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}
	
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}
}
