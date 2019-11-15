package edu.ku.cete.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.help.HelpContent;
import edu.ku.cete.domain.help.HelpContentContext;
import edu.ku.cete.domain.help.HelpContentSlug;
import edu.ku.cete.domain.help.HelpContentTag;
import edu.ku.cete.domain.help.HelpTag;
import edu.ku.cete.domain.help.HelpTopic;
import edu.ku.cete.domain.help.HelpTopicSlug;
import edu.ku.cete.model.help.HelpContentContextDao;
import edu.ku.cete.model.help.HelpContentDao;
import edu.ku.cete.model.help.HelpContentSlugDao;
import edu.ku.cete.model.help.HelpContentTagsDao;
import edu.ku.cete.model.help.HelpTagsDao;
import edu.ku.cete.model.help.HelpTopicDao;
import edu.ku.cete.model.help.HelpTopicSlugDao;
import edu.ku.cete.service.HelpContentService;
import edu.ku.cete.util.DateUtil;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class HelpContentServiceImpl implements HelpContentService {

	@Autowired
	private HelpTopicDao helpTopicDao;

	@Autowired
	private HelpContentDao helpContentDao;

	@Autowired
	private HelpTopicSlugDao helpTopicSlugDao;

	@Autowired
	private HelpContentSlugDao helpContentSlugDao;

	@Autowired
	private HelpContentContextDao helpContentContextDao;

	@Autowired
	private HelpTagsDao helpTagsDao;

	@Autowired
	private HelpContentTagsDao helpContentTagsDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer saveHelpTopic(HelpTopic helpTopic, String slug) {
		Integer val = helpTopicDao.saveHelpTopic(helpTopic);
		HelpTopicSlug helpTopicSlug = helpTopicSlugDao.getHelpTopicSlugByHelpTopicId(helpTopic.getId());

		if (helpTopicSlug != null) {
			helpTopicSlug.setAuditColumnPropertiesForUpdate();
			helpTopicSlug.setUrl(slug);
			helpTopicSlug.setHelpTopicId(helpTopic.getId());
			helpTopicSlugDao.updateHelpTopicSlug(helpTopicSlug);
		} else {
			helpTopicSlug = new HelpTopicSlug();
			helpTopicSlug.setAuditColumnPropertiesForUpdate();
			helpTopicSlug.setUrl(slug);
			helpTopicSlug.setHelpTopicId(helpTopic.getId());
			helpTopicSlugDao.saveHelpTopicSlug(helpTopicSlug);
		}
		return val;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<HelpTopic> getHelpTopics() {
		return helpTopicDao.getHelpTopics();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long getHelpTopicCount() {
		return helpTopicDao.getHelpTopicCount();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer updateHelpTopic(HelpTopic helpTopic, String slug) {
		Integer val = helpTopicDao.updateHelpTopic(helpTopic);
		HelpTopicSlug helpTopicSlug = helpTopicSlugDao.getHelpTopicSlugByHelpTopicId(helpTopic.getId());

		if (helpTopicSlug != null) {
			helpTopicSlug.setAuditColumnPropertiesForUpdate();
			helpTopicSlug.setUrl(slug);
			helpTopicSlug.setHelpTopicId(helpTopic.getId());
			helpTopicSlugDao.updateHelpTopicSlug(helpTopicSlug);
		} else {
			helpTopicSlug = new HelpTopicSlug();
			helpTopicSlug.setAuditColumnPropertiesForUpdate();
			helpTopicSlug.setHelpTopicId(helpTopic.getId());
			helpTopicSlug.setUrl(slug);
			helpTopicSlugDao.saveHelpTopicSlug(helpTopicSlug);
		}
		return val;
	}

	@Override
	public HelpTopic getHelpTopic(Long helpTopicId) {
		return helpTopicDao.getHelpTopic(helpTopicId);
	}

	@Override
	public List<HelpTag> getHelpTags(String term) {
		return helpTagsDao.getHelpTags(term);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public long saveHelpContent(List<Long> apIds, List<Long> states, List<Long> roles, Long helpTopicId,
			String helpContentTitle, String helpContentText, String helpContentTags, String expireHelpContentDate,
			String operation, Long helpContentId, String fileName, String slug) throws ParseException {
		HelpContent helpContent = new HelpContent();
		
		if (StringUtils.isNotBlank(expireHelpContentDate)) {
			// check and convert expire date if exists
			Date expireDate = DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(expireHelpContentDate + " 11:59 PM", "US/Central",  "MM/dd/yyyy hh:mm a");
			helpContent.setExpireDate(expireDate);
		}
		helpContent.setHelpTitle(helpContentTitle);
		helpContent.setContent(helpContentText);
		helpContent.setHelpTopicId(helpTopicId);
		helpContent.setFileName(fileName);

		// logic for save vs publish
		if (operation.equals("save")) {
			helpContent.setStatus("Pending");
		} else if (operation.equals("publish")) {
			helpContent.setStatus("Active");
		} else if (operation.equals("edit")) {
			helpContent.setStatus(null);
		}

		if (helpContentId != null) {
			helpContent.setId(helpContentId);
			helpContent.setAuditColumnPropertiesForUpdate();
			if(StringUtils.isBlank(helpContent.getFileName())){
				helpContent.setFileName(null);
				
			}
			helpContentDao.updateHelpContent(helpContent);
		} else {
			helpContent.setAuditColumnProperties();
			helpContentDao.saveHelpContent(helpContent);
		}

		HelpContentSlug helpContentSlug = helpContentSlugDao.getHelpContentSlugByHelpContentId(helpContent.getId());
		if (helpContentSlug != null) {
			helpContentSlug.setUrl(slug);
			helpContentSlug.setHelpContentId(helpContent.getId());
			helpContentSlug.setAuditColumnPropertiesForUpdate();
			helpContentSlugDao.updateHelpContentSlug(helpContentSlug);
		} else {
			helpContentSlug = new HelpContentSlug();
			helpContentSlug.setAuditColumnProperties();
			helpContentSlug.setHelpContentId(helpContent.getId());
			helpContentSlug.setUrl(slug);
			helpContentSlugDao.saveHelpContentSlug(helpContentSlug);
		}

		if (StringUtils.isNotBlank(helpContentTags)) {
			// Process help content tags. Save if there are new tags in the
			// list.
			List<String> terms = Arrays.asList(helpContentTags.split(","));
			for (String term : terms) {
				HelpTag helpTag = helpTagsDao.getHelpTagByName(term);
				if (helpTag == null) {
					helpTag = new HelpTag();
					helpTag.setAuditColumnProperties();
					helpTag.setTag(term);
					helpTagsDao.saveHelpTags(helpTag);
				}

				HelpContentTag helpContentTag = helpContentTagsDao.getByTagAndContentId(helpTag.getId(),
						helpContent.getId());
				if (helpContentTag == null) {
					helpContentTag = new HelpContentTag();
					helpContentTag.setAuditColumnProperties();
					helpContentTag.setHelpTagId(helpTag.getId());
					helpContentTag.setHelpContentId(helpContent.getId());
					helpContentTagsDao.saveHelpContentTags(helpContentTag);
				}
			}
		}
		List<Long> helpContextIds = new ArrayList<Long>();
		// save help content context variants
		if (!apIds.isEmpty() && !states.isEmpty() && !roles.isEmpty()) {
			for (Long apId : apIds) {
				for (Long stateId : states) {
					for (Long roleId : roles) {
						HelpContentContext helpContentContext = new HelpContentContext();
						helpContentContext.setHelpContentId(helpContent.getId());
						helpContentContext.setAuditColumnProperties();
						helpContentContext.setAssessmentProgramId(apId);
						helpContentContext.setStateId(stateId);
						helpContentContext.setRolesId(roleId);
						HelpContentContext persistedEntry = helpContentContextDao
								.getByApStateRoleIds(helpContent.getId(), apId, stateId, roleId);
						if (persistedEntry == null) {
							helpContentContextDao.saveHelpContentContext(helpContentContext);
							helpContextIds.add(helpContentContext.getId());
						} else {
							helpContextIds.add(persistedEntry.getId());
						}
					}
				}
			}
		} else if (!apIds.isEmpty() && !states.isEmpty() && roles.isEmpty()) {
			for (Long apId : apIds) {
				for (Long stateId : states) {
					HelpContentContext helpContentContext = new HelpContentContext();
					helpContentContext.setHelpContentId(helpContent.getId());
					helpContentContext.setAuditColumnProperties();
					helpContentContext.setAssessmentProgramId(apId);
					helpContentContext.setStateId(stateId);
					helpContentContextDao.saveHelpContentContext(helpContentContext);
					HelpContentContext persistedEntry = helpContentContextDao.getByApStateRoleIds(helpContent.getId(),
							apId, stateId, null);
					if (persistedEntry == null) {
						helpContentContextDao.saveHelpContentContext(helpContentContext);
						helpContextIds.add(helpContentContext.getId());
					} else {
						helpContextIds.add(persistedEntry.getId());
					}
				}
			}
		} else if (!apIds.isEmpty() && states.isEmpty() && roles.isEmpty()) {
			for (Long apId : apIds) {
				HelpContentContext helpContentContext = new HelpContentContext();
				helpContentContext.setHelpContentId(helpContent.getId());
				helpContentContext.setAuditColumnProperties();
				helpContentContext.setAssessmentProgramId(apId);
				HelpContentContext persistedEntry = helpContentContextDao.getByApStateRoleIds(helpContent.getId(), apId,
						null, null);
				if (persistedEntry == null) {
					helpContentContextDao.saveHelpContentContext(helpContentContext);
					helpContextIds.add(helpContentContext.getId());
				} else {
					helpContextIds.add(persistedEntry.getId());
				}
			}
		} else if (!apIds.isEmpty() && states.isEmpty() && !roles.isEmpty()) {
			for (Long apId : apIds) {
				HelpContentContext helpContentContext = new HelpContentContext();
				helpContentContext.setHelpContentId(helpContent.getId());
				helpContentContext.setAuditColumnProperties();
				helpContentContext.setAssessmentProgramId(apId);
				for (Long roleId : roles) {
					helpContentContext.setRolesId(roleId);
					HelpContentContext persistedEntry = helpContentContextDao.getByApStateRoleIds(helpContent.getId(),
							apId, null, roleId);
					if (persistedEntry == null) {
						helpContentContextDao.saveHelpContentContext(helpContentContext);
						helpContextIds.add(helpContentContext.getId());
					} else {
						helpContextIds.add(persistedEntry.getId());
					}
				}
			}
		}
		helpContentContextDao.deleteHelpContentContextByIds(helpContextIds, helpContent.getId(), helpContent.getCurrentContextUserId());
		return helpContent.getId();
		
	}

	@Override
	public List<HelpContent> getAllHelpContents(String sortByColumn, String sortType, int i, int limitCount,
			Map<String, Object> criteria) {
		return helpContentDao.getAllHelpContents(sortByColumn, sortType, i, limitCount, criteria);
	}

	@Override
	public int getAllHelpContentsCount(Map<String, Object> criteria) {
		return helpContentDao.getAllHelpContentsCount(criteria);
	}

	@Override
	public List<HelpContent> getHelpContentByContext(Long id, String sortByColumn, String sortType, int offset,
			int limitCount, Map<String, Object> criteria, Long currentApId, Long currentGroupId, Long stateId) {
		return helpContentDao.getHelpContentByContext(id, sortByColumn, sortType, offset, limitCount, criteria,
				currentApId, currentGroupId, stateId);
	}

	@Override
	public int getHelpContentCountByContext(Map<String, Object> criteria, Long currentApId, Long currentGroupId,
			Long stateId) {
		return helpContentDao.getHelpContentCountByContext(criteria, currentApId, currentGroupId, stateId);
	}

	@Override
	public HelpContent getHelpContentById(Long id) {
		HelpContent helpContent = helpContentDao.getHelpContentsById(id);
		helpContent.setHelpContentContext(helpContentContextDao.getByHelpContentId(id));
		helpContent.setHelpTags(helpTagsDao.getByHelpContentId(id));
		helpContent.setHelpTopic(helpTopicDao.getByHelpContentId(id));
		return helpContent;
	}

	@Override
	public List<HelpTopic> getHelpTopics(boolean includeContent) {
		return helpContentDao.getHelpTopicsByUserContext(0L, 0L, 0L, includeContent);
	}

	@Override
	public List<HelpTopic> getHelpTopicsByUserContext(Long currentAssessmentProgramId, long currentGroupsId,
			long currentOrganizationId, boolean includeContent) {
		return helpContentDao.getHelpTopicsByUserContext(currentAssessmentProgramId, currentGroupsId,
				currentOrganizationId, includeContent);
	}

	@Override
	public HelpContent getViewHelpContentById(Long id) {
		return helpContentDao.getViewHelpContentById(id);
	}

	@Override
	public List<HelpContent> getHelpContentByUserContext(Long currentAssessmentProgramId, long currentGroupsId,
			long currentOrganizationId, boolean includeContent, Long helpTopicId) {
		return helpContentDao.getHelpContentByUserContext(currentAssessmentProgramId, currentGroupsId,
				currentOrganizationId, includeContent, helpTopicId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public HelpContent gethelpTopicBySlugUrl(String slugURL, Long currentAssessmentProgramId, long currentGroupsId,
			long currentOrganizationId) {
		helpContentSlugDao.updateSlugViewsByUrl(slugURL);
		return helpContentDao.gethelpTopicBySlugUrl(slugURL, currentAssessmentProgramId, currentGroupsId,
				currentOrganizationId);
	}

	@Override
	public Boolean existsTopicByNames(String name) {
		
		return helpTopicDao.existsTopicByNames(name);
	}

	@Override
	public Boolean existsTopicBySlug(String slug) {
		
		return helpTopicDao.existsTopicBySlug(slug);
	}

	@Override
	public Boolean existsContentByHelpTitle(String helpContentTitle) {
		return helpContentDao.existsContentByHelpTitle(helpContentTitle);
	}

	@Override
	public Boolean existsContentBySlug(String slug) {
		return helpContentDao.existsContentBySlug(slug);
	}

	@Override
	public Boolean existsEditTopicByNames(Long helpTopicId, String name) {
		
		return helpTopicDao.existsEditTopicByNames(helpTopicId, name);
	}

	@Override
	public Boolean existsEditTopicBySlug(Long helpTopicId, String slug) {
		
		return helpTopicDao.existsEditTopicBySlug(helpTopicId, slug);
	}

	@Override
	public Boolean existsEditContentByHelpTitle(Long helpContentId, String helpContentTitle) {
		return helpContentDao.existsEditContentByHelpTitle(helpContentId, helpContentTitle);
	}

	@Override
	public Boolean existsEditContentBySlug(Long helpContentId, String slug) {
		return helpContentDao.existsEditContentBySlug(helpContentId, slug);
	}

	@Override
	public boolean helpTopicEligibleForDelete(Long helpTopicId) {
		return helpContentDao.helpTopicEligibleForDelete(helpTopicId).booleanValue();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteHelpTopic(Long helpTopicId) {
		HelpTopic helpTopic = new HelpTopic();
		helpTopic.setId(helpTopicId);
		helpTopic.setAuditColumnPropertiesForDelete();
		helpTopicDao.deleteHelpTopic(helpTopic);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteHelpContent(Long helpContentId) {
		HelpContent helpContent = new HelpContent();
		helpContent.setId(helpContentId);
		helpContent.setAuditColumnPropertiesForDelete();
		helpContentDao.deleteHelpContent(helpContent);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public long updateHelpContent(HelpContent helpContent) {
		return	helpContentDao.updateHelpContent(helpContent);
	}
}
