package edu.ku.cete.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import edu.ku.cete.domain.common.ActivationEmailTemplate;
import edu.ku.cete.report.domain.DomainAuditHistory;

public interface ActivationEmailTemplateService extends Serializable {

    ActivationEmailTemplate add(ActivationEmailTemplate toAdd) throws ServiceException;

    ActivationEmailTemplate get(Long templateId);
    
    List<ActivationEmailTemplate> getAllActive();
    
    boolean createCustomEmailTemplate(Long templateId,ActivationEmailTemplate emailActivation,Long[] states);
    
    int updateDefaultEmailTemplate(ActivationEmailTemplate emailActivation);
    
    ActivationEmailTemplate getEmailActivationDetailsByTemplateId(Long templateId);
 
    ActivationEmailTemplate getDefaultEmailTemplate();
  
    /**
     * 
     * @param filters
     * @param orderByClause
     * @param offset
     * @param limitCount
     * @return
     */
    List<ActivationEmailTemplate> getAllChildrenToView(Map<String, Object> filters, String sortByColumn, String sortType, 
    		Integer offset, Integer limitCount);
	
    Integer countAllChildrenToView(Map<String, Object> filters);

	ActivationEmailTemplate addOrUpdateEmailTemplate(ActivationEmailTemplate emailActivation);

	ActivationEmailTemplate getTemplateByTemplateName(ActivationEmailTemplate emailActivation);
	
	ActivationEmailTemplate getTemplateByTemplateNameandTemplateId(ActivationEmailTemplate emailActivation);

	ActivationEmailTemplate getEmailActivationDetailsByAssesmentProgramIdandStateId(Long assessmentProgramId, Long stateId);

	int updateInactiveAllStatesByTemplateId(ActivationEmailTemplate emailActivation);
	
	List<ActivationEmailTemplate> getTemplateByAssesmentAndState(Long assessmentprogrsmId,Long stateId);
	
	boolean addToactivationEmailTemplateAuditTrailHistory(DomainAuditHistory domainAuditHistory);

	void insertIntoDomainAuditHistory(Long objectId, Long createdUserId,String action, String source, String rosterBeforUpdate,	String rosterAfterUpdate);

	boolean updateOldTemplateStatesActiveFalse(String[] statesAlreadyTemplateIds);

	List<ActivationEmailTemplate> getTemplateForClaimUsers(String templateName);
}
