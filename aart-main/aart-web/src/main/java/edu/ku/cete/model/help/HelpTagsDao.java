package edu.ku.cete.model.help;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.help.HelpTag;

public interface HelpTagsDao {

	public int saveHelpTags(HelpTag helpContentTags);

	public int updateHelpTags(HelpTag helpContentTags);

	public void deleteHelpTags(HelpTag helpContentTags);

	public HelpTag getHelpTagsById(@Param("id") Long id);

	public List<HelpTag> getHelpTags(@Param(value = "tag") String tag);

	public HelpTag getHelpTagByName(@Param(value = "tag") String tag);

	public List<HelpTag> getByHelpContentId(@Param(value = "contentId") Long contentId);
}
