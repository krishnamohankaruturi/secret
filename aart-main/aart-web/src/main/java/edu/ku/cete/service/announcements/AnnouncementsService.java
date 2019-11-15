package edu.ku.cete.service.announcements;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.announcements.CommunicationMessage;

/**
 * Annoucement Service for Message Creation and Viewing.
 * @author scriptbees
 *
 */
public interface AnnouncementsService {
	List<CommunicationMessage> getMessagesByAssessmentProgram(Long organizationId,
			String sortByColumn, String sortType, int offset, int limitCount,
			Map<String, Object> criteria, Long currentAssessmentProgramId);

	List<CommunicationMessage> getAssessmentPrograms(
			CommunicationMessage createMessageDto);

	List<CommunicationMessage> getStatesList(
			CommunicationMessage createMessageDto);

	CommunicationMessage editMessageData(int messageId);

	Integer messageCountDetails(Map<String, Object> criteria, Long currentAssessmentProgramId);
	
	void saveMessage(CommunicationMessage createMessageDto);


	void updateMessage(CommunicationMessage createMessageDto);

	void cancelMessage(CommunicationMessage createMessageDto);

	List<CommunicationMessage> getUserMessageList(Long organizationId, Long groupId, Long currentAssessmentProgramId);

	List<CommunicationMessage> getAllActiveMessages(String sortByColumn, String sortType, int i, int limitCount,
			Map<String, Object> criteria);

	int getAllActiveMessagesCount(Map<String, Object> criteria);
}
