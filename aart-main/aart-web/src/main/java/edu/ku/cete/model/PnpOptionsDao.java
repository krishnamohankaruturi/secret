package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.common.PnpAccomodation;
import edu.ku.cete.domain.common.PnpStateSettings;

public interface PnpOptionsDao {

	List<PnpAccomodation> getPnpAccomodations();

	Integer updatePnpOptionSettings(@Param("categoryId") Long categoryId,
			@Param("assessmentProgram") String assessmentProgram, @Param("pianacId") Long pianacId,
			@Param("viewOption") String viewOption, @Param("userId") Long userId);

	void insertPnpOptionSettings(@Param("categoryId") Long categoryId,
			@Param("assessmentProgram") String assessmentProgram, @Param("pianacId") Long pianacId,
			@Param("viewOption") String viewOption, @Param("userId") Long userId);

	List<PnpStateSettings> getPnpStateSettings();

	void insert(PnpStateSettings pnpStateSettings);

	List<PnpStateSettings> getPnpStateSettingsData(@Param("assessmentProgram") String assessmentProgram,
			@Param("pianacId") Long pianacId);

	Integer updateStatePnpOptionSettings(@Param("stateId") Long stateId,
			@Param("assessmentProgram") String assessmentProgram, @Param("pianacId") Long pianacId,
			@Param("viewOption") String viewOption, @Param("userId") Long userId);

	void insertStatePnpOptionSettings(@Param("stateId") Long stateId,
			@Param("assessmentProgram") String assessmentProgram, @Param("pianacId") Long pianacId,
			@Param("viewOption") String viewOption, @Param("userId") Long userId);

	void clearStatePnpOptionSettings(@Param("assessmentProgram") String assessmentProgram,
			@Param("pianacId") Long pianacId, @Param("userId") Long userId);

	List<Long> getChildPnpSettingIds(@Param("pianacId") Long pianacId);

	PnpStateSettings getPnpStateSettingsByState(@Param("assessmentProgram") String assessmentProgram,
			@Param("pianacId") Long pianacId, @Param("stateId") Long stateId);

	Long getProfileItemAttributenameAttributeContainerId(@Param("attributeName")String attributeName);
}
