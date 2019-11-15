package edu.ku.cete.model.help;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.help.HelpContentTag;
import edu.ku.cete.domain.help.HelpTag;

public interface HelpContentTagsDao {

	public int saveHelpContentTags(HelpContentTag helpContentTags);

	public int updateHelpContentTags(HelpContentTag helpContentTags);

	public void deleteHelpContentTags(HelpContentTag helpContentTags);

	public HelpContentTag getHelpContentTagsById(@Param("id") Long id);

	public HelpContentTag getByTagAndContentId(@Param("tagId") Long tagId, @Param("contentId") Long contentId);

	public List<HelpTag> getByHelpContentId(@Param("contentId") Long contentId);

}
