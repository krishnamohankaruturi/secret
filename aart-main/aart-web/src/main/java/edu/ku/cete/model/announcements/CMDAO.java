package edu.ku.cete.model.announcements;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.announcements.CommunicationMessage;

/**
 * @author venkat
 *
 */
public interface CMDAO {
	List<CommunicationMessage> getMessagesByAssessmentProgram(@Param("organizationId") Long organizationId,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType, @Param("offset") int offset,
			@Param("limit") int limitCount, @Param("criteria") Map<String, Object> criteria,
			@Param("currentAssessmentProgramId") Long currentAssessmentProgramId);

	void saveMessage(CommunicationMessage createMessageDto);

	List<CommunicationMessage> getAssessmentPrograms(CommunicationMessage createMessageDto);

	List<CommunicationMessage> getStatesList(CommunicationMessage createMessageDto);

	CommunicationMessage editMessageData(@Param("messageId") int messageId);

	Integer messageCountDetails(@Param("criteria") Map<String, Object> criteria,
			@Param("currentAssessmentProgramId") Long currentAssessmentProgramId);

	void updateMessage(CommunicationMessage createMessageDto);

	void cancelMessage(CommunicationMessage createMessageDto);

	List<CommunicationMessage> getUserMessageList(@Param("stateId") Long organizationId, @Param("groupId") Long groupId,
			@Param("currentAssessmentProgramId") Long currentAssessmentProgramId);

	List<CommunicationMessage> getAllUserMessageList();

	List<CommunicationMessage> getAllActiveMessages(
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType, @Param("offset") int offset,
			@Param("limit") int limitCount, @Param("criteria") Map<String, Object> criteria);

	int getAllActiveMessagesCount(@Param("criteria") Map<String, Object> criteria);

}
