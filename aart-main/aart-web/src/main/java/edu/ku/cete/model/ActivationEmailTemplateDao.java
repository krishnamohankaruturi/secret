package edu.ku.cete.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.common.ActivationEmailTemplate;

public interface ActivationEmailTemplateDao {

  	int add(ActivationEmailTemplate emailActivation);
    
    int update(ActivationEmailTemplate emailActivation);
        
    int updateDefaultEmailTemplate(ActivationEmailTemplate emailActivation);

    int updateInactiveAllStatesByTemplateId(ActivationEmailTemplate emailActivation);
    
    ActivationEmailTemplate getEmailActivationDetailsByTemplateId(long templateId);
    
    ActivationEmailTemplate getDefaultEmailTemplate();
    
    ActivationEmailTemplate getTemplateByTemplateName(ActivationEmailTemplate emailActivation);    
    
    ActivationEmailTemplate getTemplateByTemplateNameandTemplateId(ActivationEmailTemplate emailActivation);    
    
    ActivationEmailTemplate getEmailActivationDetailsByAssesmentProgramIdandStateId(Long assessmentProgramId,Long stateId);
    
    List<ActivationEmailTemplate> getAllActive();
 
 	List<ActivationEmailTemplate> findSelectEmailActivation(Map<String, Object> filters);
   
 	Integer countSelectEmailActivation(Map<String, Object> filters);
 	
 	ActivationEmailTemplate getActivationEmailForAuditById(Long templateId);
 	
 	List<ActivationEmailTemplate> getTemplateByAssesmentAndState(@Param("assessmentprogrsmId") Long assessmentprogrsmId, @Param("stateId")Long stateId);
 	
 	List<ActivationEmailTemplate> getTemplateForClaimUsers(@Param("templateName") String templateName);
 	
}