package edu.ku.cete.model.help;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.help.HelpContentSlug;

public interface HelpContentSlugDao {

	public int saveHelpContentSlug(HelpContentSlug helpContentSlug);

	public int updateHelpContentSlug(HelpContentSlug helpContentSlug);

	public HelpContentSlug getHelpContentSlugById(@Param("id") Long id);

	public HelpContentSlug getHelpContentSlugByHelpContentId(@Param("helpContentId") Long helpContentId);

	public void updateSlugViewsByUrl(@Param("slugURL") String slugURL);

}
