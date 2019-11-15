
package edu.ku.cete.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.ku.cete.domain.common.ActivationEmailTemplateState;
import edu.ku.cete.model.ActivationEmailTemplateStateDao;
import edu.ku.cete.service.ActivationEmailTemplateStateService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ActivationEmailTemplateStateServiceImpl implements ActivationEmailTemplateStateService {

	 /** Generated Serial. */
    private static final long serialVersionUID = 2735963534122808411L;
    /**
     * logger.
     */
    
    @Autowired
    private ActivationEmailTemplateStateDao activationEmailTemplateStateDao;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int add(ActivationEmailTemplateState activationEmailTemplateState){
    	return activationEmailTemplateStateDao.add(activationEmailTemplateState);           
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateActiveFlagByStateIds(Long templateId, Long[] stateIds,Date modifiedDate, Long modifiedUser){
    	return activationEmailTemplateStateDao.updateActiveFlagByStateIds(templateId,stateIds,modifiedDate,modifiedUser);           
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int update(ActivationEmailTemplateState activationEmailTemplateState){
    	return activationEmailTemplateStateDao.update(activationEmailTemplateState);           
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<ActivationEmailTemplateState> updateAndReturnByTemplateId(Long templateId,Date modifiedDate, Long modifiedUser){
    	return activationEmailTemplateStateDao.updateAndReturnByTemplateId(templateId,modifiedDate,modifiedUser);           
    }   
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<ActivationEmailTemplateState> getActiveStatesByTemplateId(Long templateId){
    	return activationEmailTemplateStateDao.getActiveStatesByTemplateId(templateId);           
    }
   
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<ActivationEmailTemplateState> getStatesByAssessmentProgramId(Long assessmentProgramId,Long[] states,Long templateId){
    	return activationEmailTemplateStateDao.getStatesByAssessmentProgramId(assessmentProgramId,states,templateId);           
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Long> getAllStateIdsByTemplateId(Long templateId){
    	return activationEmailTemplateStateDao.getAllStateIdsByTemplateId(templateId);           
    }
    
}
