package edu.ku.cete.model.help;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.help.HelpContent;
import edu.ku.cete.domain.help.HelpTopic;

public interface HelpContentDao {

	public int saveHelpContent(HelpContent helpContent);

	public int updateHelpContent(HelpContent helpContent);

	public void deleteHelpContent(HelpContent helpContent);

	public HelpContent getHelpContentsById(@Param("id") Long id);

	public List<HelpContent> getAllHelpContents(@Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType, @Param("offset") int offset, @Param("limit") int limitCount,
			@Param("criteria") Map<String, Object> criteria);

	public int getAllHelpContentsCount(@Param("criteria") Map<String, Object> criteria);

	public List<HelpContent> getHelpContentByContext(@Param("id") Long id, @Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType, @Param("offset") int offset, @Param("limit") int limitCount,
			@Param("criteria") Map<String, Object> criteria, @Param("currentApId") Long currentAssessmentProgramId,
			@Param("currentGroupId") Long currentGroupId, @Param("stateId") Long stateId);

	public int getHelpContentCountByContext(@Param("criteria") Map<String, Object> criteria,
			@Param("currentApId") Long currentApId, @Param("currentGroupId") Long currentGroupId,
			@Param("stateId") Long stateId);

	public List<HelpContent> getHelpContentByUserContext(@Param("currentApId") Long currentApId,
			@Param("currentGroupId") Long currentGroupId, @Param("stateId") Long stateId,
			@Param("includeContent") boolean includeContent, @Param("helpTopicId") Long helpTopicId);

	public List<HelpTopic> getHelpTopicsByUserContext(@Param("currentApId") Long currentApId,
			@Param("currentGroupId") Long currentGroupId, @Param("stateId") Long stateId,
			@Param("includeContent") boolean includeContent);

	public HelpContent getViewHelpContentById(@Param("id") Long id);

	public HelpContent gethelpTopicBySlugUrl(@Param("slugURL") String slugURL, @Param("currentApId") Long currentApId,
			@Param("currentGroupId") Long currentGroupId, @Param("stateId") Long stateId);

	public Boolean existsContentByHelpTitle(@Param("helpContentTitle") String helpContentTitle);

	public Boolean existsContentBySlug(@Param("slug") String slug);

	public Boolean existsEditContentByHelpTitle(@Param("helpContentId") Long helpContentId,
			@Param("helpContentTitle") String helpContentTitle);

	public Boolean existsEditContentBySlug(@Param("helpContentId") Long helpContentId, @Param("slug") String slug);

	public Boolean helpTopicEligibleForDelete(@Param("helpTopicId") Long helpTopicId);
}
