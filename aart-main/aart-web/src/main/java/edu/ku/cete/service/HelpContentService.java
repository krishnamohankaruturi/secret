package edu.ku.cete.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.help.HelpContent;
import edu.ku.cete.domain.help.HelpTag;
import edu.ku.cete.domain.help.HelpTopic;

public interface HelpContentService {

	public Integer saveHelpTopic(HelpTopic helpTopic, String slug);

	public Integer updateHelpTopic(HelpTopic helpTopic, String slug);

	public List<HelpTopic> getHelpTopics();

	public Long getHelpTopicCount();

	public HelpTopic getHelpTopic(Long helpTopicId);

	public List<HelpTag> getHelpTags(String term);

	public long saveHelpContent(List<Long> apIds, List<Long> states, List<Long> roles, Long helpTopicId,
			String helpContentTitle, String helpContentText, String helpContentTags, String expireHelpContentDate,
			String operation, Long helpContentId, String uploadFileName, String slug) throws ParseException;

	public List<HelpContent> getAllHelpContents(String sortByColumn, String sortType, int i, int limitCount,
			Map<String, Object> criteria);

	public int getAllHelpContentsCount(Map<String, Object> criteria);

	public List<HelpContent> getHelpContentByContext(Long id, String sortByColumn, String sortType, int i,
			int limitCount, Map<String, Object> criteria, Long currentApId, Long currentGroupId, Long stateId);

	public int getHelpContentCountByContext(Map<String, Object> criteria, Long currentApId, Long currentGroupId,
			Long stateId);

	public HelpContent getHelpContentById(Long id);

	public List<HelpTopic> getHelpTopics(boolean includeContent);

	public List<HelpTopic> getHelpTopicsByUserContext(Long currentAssessmentProgramId, long currentGroupsId,
			long currentOrganizationId, boolean includeContent);

	public HelpContent getViewHelpContentById(Long id);

	public List<HelpContent> getHelpContentByUserContext(Long currentAssessmentProgramId, long currentGroupsId,
			long currentOrganizationId, boolean includeContent, Long id);

	public HelpContent gethelpTopicBySlugUrl(String slugURL, Long currentAssessmentProgramId, long currentGroupsId,
			long currentOrganizationId);

	public Boolean existsTopicByNames(String name);

	public Boolean existsTopicBySlug(String slug);

	public Boolean existsContentByHelpTitle(String helpContentTitle);

	public Boolean existsContentBySlug(String slug);

	public Boolean existsEditTopicByNames(Long helpTopicId, String name);

	public Boolean existsEditTopicBySlug(Long helpTopicId, String slug);

	public Boolean existsEditContentByHelpTitle(Long helpContentId, String helpContentTitle);

	public Boolean existsEditContentBySlug(Long helpContentId, String slug);

	public boolean helpTopicEligibleForDelete(Long helpTopicId);

	public void deleteHelpTopic(Long helpTopicId);

	public void deleteHelpContent(Long helpContentId);

	public long updateHelpContent(HelpContent helpContent);
}
