package edu.ku.cete.batch.dlm.auto.multiassign;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.service.EnrollmentService;

public class DLMMultiAssignAutoEnrollmentReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(DLMMultiAssignAutoEnrollmentReader.class);
	private ContentArea contentArea;
	private Organization contractingOrganization;
	private Long operationalTestWindowId; 
	private String enrollmentMethod;
	private Integer multiAssignLimit;
	private String interimFlag;
	
	@Autowired
	private EnrollmentService enrollmentService;

	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		results.addAll(getEnrollments(contentArea, contractingOrganization, getPage() * getPageSize(), getPageSize()));
	}

	@SuppressWarnings("unchecked")
	private List<T> getEnrollments(ContentArea contentArea, Organization contractingOrganization, Integer offset, Integer pageSize) {
		boolean isInterim = interimFlag != null && "true".equalsIgnoreCase(interimFlag);
		List<Enrollment> enrollments = enrollmentService.getMultiAssignEnrollments(contentArea.getId(), contentArea.getAbbreviatedName(), enrollmentMethod,
				operationalTestWindowId, contractingOrganization.getId(), contractingOrganization.getCurrentSchoolYear(), multiAssignLimit, isInterim, offset, pageSize);
		
		return (List<T>) enrollments;
	} 
	@Override
	protected void doJumpToPage(int arg0) {
		//no-op
		logger.debug("NO-OP");
	};

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

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public Integer getMultiAssignLimit() {
		return multiAssignLimit;
	}

	public void setMultiAssignLimit(Integer multiAssignLimit) {
		this.multiAssignLimit = multiAssignLimit;
	}

	public String getInterimFlag() {
		return interimFlag;
	}

	public void setInterimFlag(String interimFlag) {
		this.interimFlag = interimFlag;
	}
}
