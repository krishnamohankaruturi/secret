package edu.ku.cete.service.impl.announcements;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.announcements.CMState;
import edu.ku.cete.domain.announcements.CommunicationMessage;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.model.announcements.CMDAO;
import edu.ku.cete.model.announcements.CMStateDAO;
import edu.ku.cete.service.announcements.AnnouncementsService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AnnouncementsServiceImpl implements AnnouncementsService {

	/**
	 * logger.
	 */
	private static final Log LOGGER = LogFactory
			.getLog(AnnouncementsServiceImpl.class);

	@Autowired
	private CMDAO cmDAO;

	@Autowired
	private CMStateDAO cmOAPDAO;
	
	@Autowired
	private GroupsDao groupsDao;

	@Override
	public List<CommunicationMessage> getMessagesByAssessmentProgram(Long organizationId, String sortByColumn, String sortType,
			int offset, int limitCount, Map<String, Object> criteria, Long currentAssessmentProgramId) {

		return cmDAO.getMessagesByAssessmentProgram(organizationId, sortByColumn, sortType, offset, limitCount, criteria,
				currentAssessmentProgramId);
	}

	@Override
	public List<CommunicationMessage> getAssessmentPrograms(
			CommunicationMessage createMessageDto) {
		return cmDAO.getAssessmentPrograms(createMessageDto);
	}

	@Override
	public List<CommunicationMessage> getStatesList(
			CommunicationMessage createMessageDto) {
		return cmDAO.getStatesList(createMessageDto);
	}

	@Override
	public CommunicationMessage editMessageData(int messageId) {
		CommunicationMessage createMessageDTO = cmDAO
				.editMessageData(messageId);
		List<CMState> stateList = cmOAPDAO.getMessageStates(BigInteger
				.valueOf(messageId));
		List<Long> stateIdList = new ArrayList<Long>();
		List<Long> roleIdList = new ArrayList<Long>();
		LOGGER.debug(stateList.size());
		for (CMState cmState : stateList) {
			if(cmState.getStateId() != null){
				stateIdList.add(cmState.getStateId().longValue());
			}
			if(cmState.getRoleId() != null){
				roleIdList.add(cmState.getRoleId().longValue());
			}
		}
		
		
		// TODO:Added this only for existing Data in QA and It is Not useful at
		// all in Prod May be Remove this.
		if (stateList.size() == 0) {
			stateIdList.add(Long.valueOf(createMessageDTO.getStateProgramId()));
		}
		createMessageDTO.setStateProgramIdList(stateIdList);
		createMessageDTO.setRolesIdList(roleIdList);
		return createMessageDTO;
	}

	@Override
	public Integer messageCountDetails(Map<String, Object> criteria, Long currentAssessmentProgramId) {
		return cmDAO.messageCountDetails(criteria, currentAssessmentProgramId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateMessage(CommunicationMessage createMessageDto) {
		createMessageDto.setAuditColumnPropertiesForUpdate();
		cmDAO.updateMessage(createMessageDto);
		cmOAPDAO.deleteByMessageId(BigInteger.valueOf(createMessageDto
				.getMessageId()));
		
		if(!createMessageDto.getStateProgramIdList().isEmpty() && !createMessageDto.getRolesIdList().isEmpty()){
			for (Long stateProgramId : createMessageDto.getStateProgramIdList()) {
				for (Long rolesId : createMessageDto.getRolesIdList()) {
				CMState cmOAP = new CMState();
				cmOAP.setComminicationMessageId(BigInteger.valueOf(createMessageDto
						.getMessageId()));
				cmOAP.setStateId(BigInteger.valueOf(stateProgramId));
				cmOAP.setRoleId(BigInteger.valueOf(rolesId));
				cmOAPDAO.insert(cmOAP);
				}
			}
		}
		else if(!createMessageDto.getStateProgramIdList().isEmpty()){
			for (Long stateProgramId : createMessageDto.getStateProgramIdList()) {
				CMState cmOAP = new CMState();
				cmOAP.setComminicationMessageId(BigInteger.valueOf(createMessageDto
						.getMessageId()));
				cmOAP.setStateId(BigInteger.valueOf(stateProgramId));
				cmOAPDAO.insert(cmOAP);
			}	
		}
		else if(!createMessageDto.getRolesIdList().isEmpty()){
			for (Long rolesId : createMessageDto.getRolesIdList()) {
				CMState cmOAP = new CMState();
				cmOAP.setComminicationMessageId(BigInteger.valueOf(createMessageDto
						.getMessageId()));
				cmOAP.setRoleId(BigInteger.valueOf(rolesId));
				cmOAPDAO.insert(cmOAP);
			}
		}

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void cancelMessage(CommunicationMessage createMessageDto) {
		cmDAO.cancelMessage(createMessageDto);

	}

	@Override
	public List<CommunicationMessage> getUserMessageList(Long organizationId, Long groupId, Long currentAssessmentProgramId) {
		LOGGER.debug("Create Message DAO Getting User messages");
		LOGGER.debug("logged in user groupId "+groupId);
		Groups groups = groupsDao.getGroup(groupId);
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		
		LOGGER.debug("logged in user id "+user.getId());
		LOGGER.debug("logged in user groupCode "+groups.getGroupCode());
		
		if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
			return cmDAO.getAllUserMessageList();
		}
		return cmDAO.getUserMessageList(organizationId, groupId, currentAssessmentProgramId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void saveMessage(CommunicationMessage createMessageDto) {
		createMessageDto.setAuditColumnProperties();
		cmDAO.saveMessage(createMessageDto);
		LOGGER.debug("Inserting Message ID");
		LOGGER.debug(createMessageDto.getMessageId());
		if(!createMessageDto.getStateProgramIdList().isEmpty() && !createMessageDto.getRolesIdList().isEmpty()){
			for (Long stateProgramId : createMessageDto.getStateProgramIdList()) {
				for (Long rolesId : createMessageDto.getRolesIdList()) {
				CMState cmOAP = new CMState();
				cmOAP.setComminicationMessageId(BigInteger.valueOf(createMessageDto
						.getMessageId()));
				cmOAP.setStateId(BigInteger.valueOf(stateProgramId));
				cmOAP.setRoleId(BigInteger.valueOf(rolesId));
				cmOAPDAO.insert(cmOAP);
				}
			}
		}
		else if(!createMessageDto.getStateProgramIdList().isEmpty()){
			for (Long stateProgramId : createMessageDto.getStateProgramIdList()) {
				CMState cmOAP = new CMState();
				cmOAP.setComminicationMessageId(BigInteger.valueOf(createMessageDto
						.getMessageId()));
				cmOAP.setStateId(BigInteger.valueOf(stateProgramId));
				cmOAPDAO.insert(cmOAP);
			}	
		}
		else if(!createMessageDto.getRolesIdList().isEmpty()){
			for (Long rolesId : createMessageDto.getRolesIdList()) {
				CMState cmOAP = new CMState();
				cmOAP.setComminicationMessageId(BigInteger.valueOf(createMessageDto
						.getMessageId()));
				cmOAP.setRoleId(BigInteger.valueOf(rolesId));
				cmOAPDAO.insert(cmOAP);
			}
		}
		

	}

	@Override
	public List<CommunicationMessage> getAllActiveMessages(String sortByColumn, String sortType, int i,
			int limitCount, Map<String, Object> criteria) {
		return cmDAO.getAllActiveMessages(sortByColumn, sortType, i, limitCount, criteria);
	}

	@Override
	public int getAllActiveMessagesCount(Map<String, Object> criteria) {
		return cmDAO.getAllActiveMessagesCount(criteria);
	}

}
