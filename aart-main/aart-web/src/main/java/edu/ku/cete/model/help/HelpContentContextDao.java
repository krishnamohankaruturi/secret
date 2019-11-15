package edu.ku.cete.model.help;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.help.HelpContentContext;

public interface HelpContentContextDao {

	public int saveHelpContentContext(HelpContentContext helpContentContext);

	public int updateHelpContentContext(HelpContentContext helpContentContext);

	public void deleteHelpContentContext(HelpContentContext helpContentContext);

	public HelpContentContext getHelpContentContextById(@Param("id") Long id);

	public HelpContentContext getByApStateRoleIds(@Param("helpContentId") Long helpContentId, @Param("apId") Long apId,
			@Param("stateId") Long stateId, @Param("roleId") Long roleId);

	public List<HelpContentContext> getByHelpContentId(@Param("helpContentId") Long helpContentId);

	public void deleteHelpContentContextByIds(@Param("helpContextIds") List<Long> helpContextIds,
			@Param("helpContentId") long helpContentId, @Param("modifiedUser") long modifiedUser);

}
