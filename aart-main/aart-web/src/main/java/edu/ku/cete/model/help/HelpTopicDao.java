package edu.ku.cete.model.help;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.help.HelpTopic;

public interface HelpTopicDao {

	public Integer saveHelpTopic(HelpTopic helpTopic);

	public int updateHelpTopic(HelpTopic helpTopic);

	public int deleteHelpTopic(HelpTopic helpTopic);

	public List<HelpTopic> getHelpTopics();

	public Long getHelpTopicCount();

	public HelpTopic getHelpTopic(@Param(value = "helpTopicId") Long helpTopicId);

	public HelpTopic getByHelpContentId(@Param(value = "helpContentId") Long helpContentId);

	public Boolean existsTopicByNames(@Param(value = "name") String name);
	
	public Boolean existsTopicBySlug(@Param(value = "slug") String slug);
	
	public Boolean existsEditTopicByNames(@Param(value="helpTopicId") Long helpTopicId,@Param(value="name") String name);

	public Boolean existsEditTopicBySlug(@Param(value="helpTopicId") Long helpTopicId, @Param(value="slug") String slug);
}
