package edu.ku.cete.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.common.ActivationEmailTemplateState;

public interface ActivationEmailTemplateStateService extends Serializable {

   int add(ActivationEmailTemplateState emailTemplateStates);
   
   int update(ActivationEmailTemplateState emailTemplateStates);
   
   int updateActiveFlagByStateIds(Long templateId, Long[] stateIds, Date modifiedDate, Long modifiedUser);
   
   List<ActivationEmailTemplateState> updateAndReturnByTemplateId(Long templateId, Date modifiedDate, Long modifiedUser);

   List<ActivationEmailTemplateState> getActiveStatesByTemplateId(Long templateId);
   
   List<Long> getAllStateIdsByTemplateId(Long templateId);
   
   List<ActivationEmailTemplateState> getStatesByAssessmentProgramId(Long assessmentProgramId, Long[] states,Long templateId);

}
