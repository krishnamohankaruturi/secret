package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.common.PnpAccomodation;
import edu.ku.cete.domain.common.PnpStateSettings;

public interface ProfileAttributeContainerService {

	List<PnpAccomodation> getPnpAccomodations();

	Integer updatePnpOptionSettings(Long categoryId, String assessmentProgram, Long pianacId, String viewOption,
			Long userId);

	void insertPnpOptionSettings(Long categoryId, String assessmentProgram, Long pianacId, String viewOption,
			Long userId);

	List<PnpStateSettings> getPnpStateSettings();

	void insert(Long pianacId, String viewOption, Long assessmentProgramid, Long stateId);

	List<PnpStateSettings> getPnpStateSettings(String assessmentProgram, Long pianacId);

	Integer updateStatePnpOptionSettings(Long stateId, String assessmentProgram, Long pianacId, String viewOption,
			Long userId);

	void insertStatePnpOptionSettings(Long stateId, String assessmentProgram, Long pianacId, String viewOption,
			Long userId);

	void clearStatePnpOptionSettings(String assessmentProgram, Long pianacId, Long userId);

	List<Long> getChildPnpSettingIds(Long pianacId);

	PnpStateSettings getPnpStateSettingsByState(String assessmentProgram, Long pianacId, Long stateId);

	Long getProfileItemAttributenameAttributeContainerId(String attributeName);

	String getPnpStateSettingsByState(String currentAssessmentProgramName, Long userStateId);

}
