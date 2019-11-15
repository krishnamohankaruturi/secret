package edu.ku.cete.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldSpecification;

public interface UploadFileService {
	
	public Map<String,Object> create(UploadFile uploadFile, Errors errors, Long uploadFileRecordId) throws IOException, ServiceException;
	
	public void uploadUsersFromFile(UploadFile uploadFile, Errors result, Map<String,Object> mav, Organization currentContext,
	    		ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser);
	
	public void uploadOrganizationsFromFile(UploadFile uploadFile, Errors result, Map<String,Object> mav,
    		Organization currentUserOrg, ContractingOrganizationTree contractingOrganizationTree);
	
    public Map<String, FieldSpecification> getFieldSpecificationRecordMap(String uploadTypeCode, String recordType);
    public Map<String, FieldSpecification> getFieldSpecificationRecordMapForKids(String uploadTypeCode, String recordType);

    /**
     * US16352:Upload TEC Queue process
     */
    void checkTecRecordDependencies(List<TecRecord> tecRecords);

	List<FieldSpecification> selectPNPColumnsBySettingsForAssessmentProgramAndOrganization(Long assessmentProgramId,
			Long organizationId);
}
