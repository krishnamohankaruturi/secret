package edu.ku.cete.model;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import edu.ku.cete.domain.common.ActivationEmailTemplateState;

public interface ActivationEmailTemplateStateDao {

   int add(ActivationEmailTemplateState emailActivation);
    
    int update(ActivationEmailTemplateState emailActivation);
    
    int updateActiveFlagByStateIds(@Param("templateId") Long templateId,@Param("stateIds") Long[] stateIds, @Param("modifiedDate") Date modifiedDate,@Param("modifiedUser") Long modifiedUser);
    
    List<ActivationEmailTemplateState> getActiveStatesByTemplateId(@Param("templateId") long templateId);
    
    List<ActivationEmailTemplateState> getStatesByAssessmentProgramId(@Param("assessmentProgramId") Long assessmentProgramId, @Param("states") Long[] states,@Param("templateId") Long templateId);
    
    List<Long> getAllStateIdsByTemplateId(@Param("templateId") long templateId);
 
	List<ActivationEmailTemplateState> updateAndReturnByTemplateId(@Param("templateId") Long templateId,@Param("modifiedDate") Date modifiedDate,@Param("modifiedUser") Long modifiedUser);
}