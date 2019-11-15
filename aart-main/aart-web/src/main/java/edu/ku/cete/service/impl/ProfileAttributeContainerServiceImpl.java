package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.common.PnpAccomodation;
import edu.ku.cete.domain.common.PnpStateSettings;
import edu.ku.cete.model.PnpOptionsDao;
import edu.ku.cete.service.ProfileAttributeContainerService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProfileAttributeContainerServiceImpl implements ProfileAttributeContainerService {

	@Autowired
	private PnpOptionsDao pnpOptionsDao;

	@Override
	public List<PnpAccomodation> getPnpAccomodations() {
		return pnpOptionsDao.getPnpAccomodations();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer updatePnpOptionSettings(Long categoryId, String assessmentProgram, Long pianacId, String viewOption,
			Long userId) {
		return pnpOptionsDao.updatePnpOptionSettings(categoryId, assessmentProgram, pianacId, viewOption, userId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertPnpOptionSettings(Long categoryId, String assessmentProgram, Long pianacId, String viewOption,
			Long userId) {
		pnpOptionsDao.insertPnpOptionSettings(categoryId, assessmentProgram, pianacId, viewOption, userId);
	}

	@Override
	public List<PnpStateSettings> getPnpStateSettings() {
		return pnpOptionsDao.getPnpStateSettings();
	}

	@Override
	public void insert(Long pianacId, String viewOption, Long assessmentProgramId, Long stateId) {
		PnpStateSettings pnpStateSettings = new PnpStateSettings();
		pnpStateSettings.setPianacId(pianacId);
		pnpStateSettings.setViewOption(viewOption);
		pnpStateSettings.setAssessmentProgramId(assessmentProgramId);
		pnpStateSettings.setStateId(stateId);
		pnpOptionsDao.insert(pnpStateSettings);
	}

	@Override
	public List<PnpStateSettings> getPnpStateSettings(String assessmentProgram, Long pianacId) {
		return pnpOptionsDao.getPnpStateSettingsData(assessmentProgram, pianacId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer updateStatePnpOptionSettings(Long stateId, String assessmentProgram, Long pianacId,
			String viewOption, Long userId) {
		return pnpOptionsDao.updateStatePnpOptionSettings(stateId, assessmentProgram, pianacId, viewOption, userId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertStatePnpOptionSettings(Long stateId, String assessmentProgram, Long pianacId, String viewOption,
			Long userId) {
		pnpOptionsDao.insertStatePnpOptionSettings(stateId, assessmentProgram, pianacId, viewOption, userId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void clearStatePnpOptionSettings(String assessmentProgram, Long pianacId, Long userId) {
		pnpOptionsDao.clearStatePnpOptionSettings(assessmentProgram, pianacId, userId);
	}

	@Override
	public List<Long> getChildPnpSettingIds(Long pianacId) {
		return pnpOptionsDao.getChildPnpSettingIds(pianacId);
	}

	@Override
	public PnpStateSettings getPnpStateSettingsByState(String assessmentProgram, Long pianacId, Long stateId) {
		return pnpOptionsDao.getPnpStateSettingsByState(assessmentProgram, pianacId, stateId);
	}

	@Override
	public Long getProfileItemAttributenameAttributeContainerId(String attributeName) {
		return pnpOptionsDao.getProfileItemAttributenameAttributeContainerId(attributeName);
	}

	@Override
	public String getPnpStateSettingsByState(String currentAssessmentProgramName, Long stateId) {
		Long ebaePnpStateSettingId = getProfileItemAttributenameAttributeContainerId("ebaeFileType");
		Long uebPnpStateSettingId = getProfileItemAttributenameAttributeContainerId("uebFileType");
		String brailleFileType = "NONE";
		
		PnpStateSettings ebaePnpStateSettings = getPnpStateSettingsByState(currentAssessmentProgramName, ebaePnpStateSettingId, stateId);
		PnpStateSettings uebPnpStateSettings = getPnpStateSettingsByState(currentAssessmentProgramName, uebPnpStateSettingId, stateId);
		
		if (ebaePnpStateSettings == null && uebPnpStateSettings == null) {
			brailleFileType = "NONE";
		} else if (ebaePnpStateSettings != null && uebPnpStateSettings != null
				&& ebaePnpStateSettings.getViewOption().equals("enable")
				&& uebPnpStateSettings.getViewOption().equals("enable")) {
			brailleFileType = "BOTH";
		} else if (ebaePnpStateSettings != null && uebPnpStateSettings != null
				&& uebPnpStateSettings.getViewOption().equals("enable") && ebaePnpStateSettings.getViewOption().equals("disable")) {
			brailleFileType = "UEB";
		} else if (uebPnpStateSettings != null && ebaePnpStateSettings != null
				&& ebaePnpStateSettings.getViewOption().equals("enable") && uebPnpStateSettings.getViewOption().equals("disable")) {
			brailleFileType = "EBAE";
		}
		return brailleFileType;
	}
}
