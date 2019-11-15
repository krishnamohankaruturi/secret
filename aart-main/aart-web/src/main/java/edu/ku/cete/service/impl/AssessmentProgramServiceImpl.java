/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.web.AssessmentProgramParticipationDTO;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AssessmentProgramServiceImpl implements AssessmentProgramService {

    @Autowired
    private AssessmentProgramDao assessmentProgramDao;

    /* (non-Javadoc)
     * @see edu.ku.cete.service.AssessmentProgramService#getAll()
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<AssessmentProgram> getAll() {
        return assessmentProgramDao.getAll();
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<AssessmentProgram> getAllActive() {
        return assessmentProgramDao.getAllActive();
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.AssessmentProgramService#findByAssessmentProgramId(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public AssessmentProgram findByAssessmentProgramId(long assessmentProgramId) {
        return assessmentProgramDao.findByAssessmentProgramId(assessmentProgramId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.AssessmentProgramService#selectAllAssessmentProgramParticipation()
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<AssessmentProgramParticipationDTO> selectAllAssessmentProgramParticipation() {
    	return assessmentProgramDao.selectAllAssessmentProgramParticipation();
    }
    
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.AssessmentProgramService#selectAssessmentProgramsForAutoRegistration()
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<AssessmentProgram> selectAssessmentProgramsForAutoRegistration() {
    	return assessmentProgramDao.selectAssessmentProgramsForAutoRegistration();
    } 
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<AssessmentProgram> selectAssessmentProgramsForBatchReporting(Long userOrganizationId) {
    	return assessmentProgramDao.selectAssessmentProgramsForBatchReporting(userOrganizationId);
    } 
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<AssessmentProgram> findByOrganizationId(long userOrganizationId) {
        return assessmentProgramDao.findByOrganizationId(userOrganizationId);
    }

	@Override
	public AssessmentProgram findByStudentId(Long id, User user) {
		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		return assessmentProgramDao.findByStudentId(id, currentSchoolYear);
	}

	@Override
	public AssessmentProgram findByAbbreviatedName(String abbreviatedName) {
		return assessmentProgramDao.findByAbbreviatedName(abbreviatedName);
	}
	
	/*
	 * Added during US16351-To get Assessment programs based on user id
	 */
	@Override
	public List<AssessmentProgram> getAllAssessmentProgramByUserId(long userId) {
		return assessmentProgramDao.getAllAssessmentProgramByUserId(userId);
	}
	
	 //Added during US16425 To get Assessment programs by orgId
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getProgramName(long userOrganizationId) {
		List<String> assessmentProgramName = new ArrayList<String>();
		assessmentProgramName = assessmentProgramDao.getAssessmentProgramName(userOrganizationId);
		return assessmentProgramName;
	}
	
	@Override
	public AssessmentProgram findByTestSessionId(long testSessionId) {
		return assessmentProgramDao.findByTestSessionId(testSessionId);
	}

	@Override
	public List<AssessmentProgram> getPermittedAPsBySelectedStateIds(List<Long> stateIds, Long userId) {
		return assessmentProgramDao.getPermittedAPsBySelectedStateIds(stateIds, userId);
	}

	@Override
	public List<AssessmentProgram> getAssessmentProgramCodeById(List<Long> assessmentProgramIds) {
		return assessmentProgramDao.getAssessmentProgramCodeById(assessmentProgramIds);
	}

	@Override
	public List<Long> getAssessPgmIdByAbbreviatedName(List<String> kidsAssessmentPgm) {
		return assessmentProgramDao.getAssessPgmIdByAbbreviatedName(kidsAssessmentPgm);
	}
	
}