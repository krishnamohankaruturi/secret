package edu.ku.cete.model.help;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.help.HelpTopicSlug;

public interface HelpTopicSlugDao {

	public int saveHelpTopicSlug(HelpTopicSlug helpTopicSlug);

	public int updateHelpTopicSlug(HelpTopicSlug helpTopicSlug);

	public HelpTopicSlug getHelpTopicSlugById(@Param("id") Long id);

	public HelpTopicSlug getHelpTopicSlugByHelpTopicId(@Param("helpTopicId") Long id);

}
