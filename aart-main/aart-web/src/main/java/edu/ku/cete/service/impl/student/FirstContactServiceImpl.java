/**
 * 
 */
package edu.ku.cete.service.impl.student;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.OrganizationAnnualResets;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.survey.Survey;
import edu.ku.cete.domain.student.SurveyJson;
import edu.ku.cete.domain.student.survey.StudentSurveyResponse;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseExample;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabel;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabelInfo;
import edu.ku.cete.domain.student.survey.SurveyLabel;
import edu.ku.cete.domain.student.survey.SurveyLabelPreRequisiteInfo;
import edu.ku.cete.domain.student.survey.SurveyLabelPrerequisite;
import edu.ku.cete.domain.student.survey.SurveyPageStatus;
import edu.ku.cete.domain.student.survey.SurveyPageStatusExample;
import edu.ku.cete.domain.student.survey.SurveySection;
import edu.ku.cete.domain.student.survey.SurveySectionComposite;
import edu.ku.cete.domain.upload.FirstContactRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.StudentTrackerBandMapper;
import edu.ku.cete.model.StudentTrackerMapper;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.student.survey.StudentSurveyResponseDao;
import edu.ku.cete.model.student.survey.StudentSurveyResponseLabelDao;
import edu.ku.cete.model.student.survey.SurveyLabelDao;
import edu.ku.cete.model.student.survey.SurveyLabelPrerequisiteDao;
import edu.ku.cete.model.student.survey.SurveyMapper;
import edu.ku.cete.model.student.survey.SurveyPageStatusDao;
import edu.ku.cete.model.student.survey.SurveySectionDao;
import edu.ku.cete.model.student.survey.SurveySettingsDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.FCSComplexityBandHistory;
import edu.ku.cete.report.domain.FirstContactSurveyAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.GroupAuthoritiesService;
import edu.ku.cete.service.OrganizationAnnualResetsService;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.student.FirstContactService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.CompBandSubject;
import edu.ku.cete.util.EventNameForAudit;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.TimerUtil;

/**
 * @author mahesh
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class FirstContactServiceImpl implements FirstContactService {

	/**
	 * logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FirstContactServiceImpl.class);

	
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * SURVEY
	 */
	private static final String SURVEY = "Survey";

	/**
	 * studentSurveyResponseDao
	 */
	@Autowired
	private StudentSurveyResponseDao studentSurveyResponseDao;
	
	@Autowired
	private SurveySettingsDao surveySettingsDao;

	/**
	 * studentSurveyResponseLabelDao
	 */
	@Autowired
	private StudentSurveyResponseLabelDao studentSurveyResponseLabelDao;

	@Autowired
	private StudentTrackerService studentTrackerService;

	/**
	 * surveySectionDao
	 */
	@Autowired
	private SurveySectionDao surveySectionDao;
	
	@Autowired
	private SurveyLabelDao surveyLabelDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private CategoryTypeDao categoryTypeDao;

	@Autowired
	private StudentTrackerBandMapper studentTrackerBandMapper;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private StudentTrackerMapper studentTrackerMapper;

	/**
	 * surveyPageStatusDao
	 */
	@Autowired
	private SurveyPageStatusDao surveyPageStatusDao;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
    private GroupAuthoritiesService gaService;
	
	/**
	 * surveyLabelPrerequisiteDao.
	 */
	@Autowired
	private SurveyLabelPrerequisiteDao surveyLabelPrerequisiteDao;

	@Autowired
	private ComplexityBandService complexityBandService;
	
	@Autowired
	private OrganizationDao organizationDao;	
	
	@Autowired
	private OrganizationAnnualResetsService organizationAnnualResetsService;
	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;
	
	@Autowired
	private SurveyMapper surveyMapper;
	
	@Autowired
    UserAuditTrailHistoryMapper userAuditTrailHistoryMapperDao;
	
	 @Autowired
	 private UserService userService;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SurveySectionComposite> selectAllSurveySections() {
		TimerUtil timerUtil = TimerUtil.getInstance();
		List<SurveySectionComposite> surveySections = surveySectionDao.selectCompositeByExample(null);
		for (SurveySectionComposite surveySection : surveySections) {
			surveySection.addChildSurveySection(surveySections);
		}
		timerUtil.resetAndLog(logger, "Retrieving survey sections took ");
		return surveySections;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public FirstContactRecord cascadeAddOrUpdate(FirstContactRecord firstContactRecord) {
		TimerUtil timerUtil = TimerUtil.getInstance();
		boolean validInput = false;
		Survey survey = null;

		int fcsPageCount = getFirstContactActivePageCount();
		if (firstContactRecord != null && firstContactRecord.getStudent() != null
				&& firstContactRecord.getStudent().getId() != null) {
			survey = studentSurveyResponseDao.findByStudentId(firstContactRecord.getStudent().getId());
			validInput = true;
		} else {
			logger.debug("Given first contact record is invalid" + firstContactRecord);
			if (firstContactRecord != null) {
				firstContactRecord.addInvalidField(ParsingConstants.BLANK + FieldName.STUDENT, ParsingConstants.BLANK,
						true, InvalidTypes.NOT_FOUND);
			}
		}
		if (validInput && survey == null) {
			survey = addSurveyIfNotPresent(firstContactRecord);
			firstContactRecord.setSurvey(survey);
			List<StudentSurveyResponseLabel> existingStudentSurveyResponseLabels = studentSurveyResponseLabelDao
					.selectSurveyResult(survey.getId());
			List<StudentSurveyResponse> toBeAddedStudentSurveyResponses = new ArrayList<StudentSurveyResponse>();
			List<StudentSurveyResponse> toBeRemovedStudentSurveyResponses = new ArrayList<StudentSurveyResponse>();
			List<StudentSurveyResponse> toBeUpdatedStudentSurveyResponses = new ArrayList<StudentSurveyResponse>();

			for (StudentSurveyResponseLabel existingStudentSurveyResponseLabel : existingStudentSurveyResponseLabels) {
				existingStudentSurveyResponseLabel.setGivenLabelResponseMap(firstContactRecord.getLabelResponseMap());
				existingStudentSurveyResponseLabel
						.setGivenTextLabelResponseMap(firstContactRecord.getTextLabelResponseMap());
				if (existingStudentSurveyResponseLabel.isGiven()) {
					logger.debug("is given " + existingStudentSurveyResponseLabel);
				}
				if (existingStudentSurveyResponseLabel.isExisting()) {
					logger.debug("is existing " + existingStudentSurveyResponseLabel);
				}
				if (existingStudentSurveyResponseLabel.doAdd()) {
					StudentSurveyResponse toAdd = existingStudentSurveyResponseLabel
							.getStudentSurveyResponse(survey.getId());
					// INFO: for setting the auditing columns.
					toAdd.setAuditColumnProperties();
					toBeAddedStudentSurveyResponses.add(toAdd);
					logger.debug("The student response label need to be inserted" + existingStudentSurveyResponseLabel);
				} else if (existingStudentSurveyResponseLabel.doRemove()) {
					StudentSurveyResponse toRemove = existingStudentSurveyResponseLabel
							.getStudentSurveyResponse(survey.getId());
					toRemove.getCurrentContextUserId();
					toRemove.setActiveFlag(false);
					toRemove.setAuditColumnPropertiesForUpdate();
					toBeRemovedStudentSurveyResponses.add(toRemove);
					logger.debug("The student response label need to be removed" + existingStudentSurveyResponseLabel);
				} else if (existingStudentSurveyResponseLabel.doUpdate()) {
					StudentSurveyResponse toUpdate = existingStudentSurveyResponseLabel
							.getStudentSurveyResponse(survey.getId());
					toUpdate.getCurrentContextUserId();
					toUpdate.setActiveFlag(true);
					toUpdate.setAuditColumnPropertiesForUpdate();
					toBeUpdatedStudentSurveyResponses.add(toUpdate);
					logger.debug("The student response label need to be removed" + existingStudentSurveyResponseLabel);
				} else {
					logger.debug("The student response label exists and need not be modifed"
							+ existingStudentSurveyResponseLabel);
				}
			}
			for (StudentSurveyResponse toBeAddedStudentSurveyResponse : toBeAddedStudentSurveyResponses) {
				studentSurveyResponseDao.insert(toBeAddedStudentSurveyResponse);
			}
			for (StudentSurveyResponse toBeUpdatedStudentSurveyResponse : toBeUpdatedStudentSurveyResponses) {
				studentSurveyResponseDao.updateByPrimaryKey(toBeUpdatedStudentSurveyResponse);
			}
			for (StudentSurveyResponse toBeRemovedStudentSurveyResponse : toBeRemovedStudentSurveyResponses) {
				studentSurveyResponseDao.updateByPrimaryKey(toBeRemovedStudentSurveyResponse);
			}
			Long userStateId = getUserStateId();
			// insert page statuses.
			for (int i = 1; i <= fcsPageCount; i++) {
				insertSurveyPageStatus(i, survey.getStudentId());
				updateSurveyPageStatus(i, survey.getId(), true, userStateId);
			}
		} else if (validInput && survey != null) {
			firstContactRecord.setExistingRecord(true);
		}
		timerUtil.resetAndLog(logger, "Processing " + firstContactRecord + "took");

		return firstContactRecord;
	}

	@Override
	public int getFirstContactActivePageCount() {
		Long stateId = getUserStateId();
		FirstContactSettings firstContactSettings =surveySettingsDao.getFirstContactSurveySetting(stateId);
		int activePageCount = surveySectionDao.getFirstContactActivePageCount();
		if(firstContactSettings != null && firstContactSettings.getElaFlag()){
			// Do nothing.
		} else {
			activePageCount= activePageCount -3;
		}
		if(firstContactSettings != null && firstContactSettings.getMathFlag()){
			// Do nothing.
		} else {
			activePageCount = activePageCount -1;
		}
		if(firstContactSettings != null && firstContactSettings.getScienceFlag()){
			return activePageCount;
		} else {
			return activePageCount -1;
		}
	}

	/**
	 * Finds the survey for a given survey and adds if its not present.
	 * 
	 * @param studentId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Survey addSurveyIfNotPresent(FirstContactRecord firstContactRecord) {
		Survey survey = null;

		survey = new Survey();
		survey.setStudentId(firstContactRecord.getStudent().getId());
		survey.setSurveyName(firstContactRecord.getStudent().getLegalLastName() + SURVEY);
		Long inProgressStatusid = categoryDao.getCategoryId("IN_PROGRESS", "SURVEY_STATUS");
		survey.setStatus(inProgressStatusid);
		survey.setAuditColumnProperties();
		Long userStateId = getUserStateId();
		FirstContactSettings firstContactSettings =  getFirstContactSurveySetting(userStateId);
		Long lastEditedOption = firstContactSettings.getCategoryId();
        Category allQuestionsRequiredOption = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
        survey.setAllQuestionsFlag(allQuestionsRequiredOption.getId().equals(lastEditedOption));
        survey.setIncludeScienceFlag(firstContactSettings.getScienceFlag());
        //Added mathFlag and elaFlag for F607
        survey.setIncludeMathFlag(firstContactSettings.getMathFlag());
        survey.setIncludeElaFlag(firstContactSettings.getElaFlag());
		survey.setLastEditedOption(lastEditedOption);
		studentSurveyResponseDao.insertSurvey(survey);
		
		survey = studentSurveyResponseDao.findByStudentId(firstContactRecord.getStudent().getId());
		firstContactRecord.setSurvey(survey);
		firstContactRecord.setCreated(true);

		return survey;
	}

	/**
	 * This method pulls all the labels and responses along with student marked
	 * responses.
	 * 
	 * @param studentId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentSurveyResponseLabelInfo> selectAllowedStudentSurveyResponseLabels(Long surveyId,
			Integer globalPageNum) {
		logger.trace("Entering selectAllowedStudentSurveyResponseLabels method");

		List<StudentSurveyResponseLabelInfo> existingStudentSurveyResponseLabelInfos = null;
		List<Boolean> respondedItemsActiveFlag = new ArrayList<Boolean>();
		Map<Long, SurveyLabelPreRequisiteInfo> surveyLabelPreRequisiteInfoMap = null;
		List<Long> prerequisiteSurveyLabelsInfoList = null;

		// Survey survey = addSurveyIfNotPresent(studentId);

		existingStudentSurveyResponseLabelInfos = studentSurveyResponseLabelDao
				.selectAllowedStudentSurveyResponseLabels(surveyId, globalPageNum);

		surveyLabelPreRequisiteInfoMap = selectAllowedStudentSurveyResponseLabelInfoMap(globalPageNum);

		prerequisiteSurveyLabelsInfoList = selectPrerequisiteSurveyLabelsInfoList();
		
		// Get state specific option from firstcontactsurveysettings for the state
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long organizationId = userDetails.getUser().getOrganizationId();
        
        FirstContactSettings firstContactSettings = getFirstContactSurveySetting(organizationId);
        
        Category allQuestionsRequiredOption = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
        
        boolean allQuestionsRequireResponse = false;
        if(allQuestionsRequiredOption != null && firstContactSettings != null){
        	if(allQuestionsRequiredOption.getId().equals(firstContactSettings.getCategoryId())){
        		allQuestionsRequireResponse = true;
        	}
        }

		for (StudentSurveyResponseLabelInfo existingStudentSurveyResponseLabelInfo : existingStudentSurveyResponseLabelInfos) {
			
			// Per US17306 all the questions are mandatory based on First Contact Settings (ALL_QUESTIONS) selected.
			if(allQuestionsRequireResponse){
				existingStudentSurveyResponseLabelInfo.setOptional(false);
			}
			
			existingStudentSurveyResponseLabelInfo.setIsPreRequisite(prerequisiteSurveyLabelsInfoList
					.contains(existingStudentSurveyResponseLabelInfo.getSurveyLabelId()));

			// INFO: set on whether it has prerequisite or not.
			existingStudentSurveyResponseLabelInfo.setHasPreRequisite(surveyLabelPreRequisiteInfoMap
					.containsKey(existingStudentSurveyResponseLabelInfo.getSurveyLabelId()));

			if (existingStudentSurveyResponseLabelInfo.isHasPreRequisite()) {
				// INFO: If it has prerequisite then set the prerequisite response ids.
				existingStudentSurveyResponseLabelInfo.setPreRequisiteResponseLabelIds(surveyLabelPreRequisiteInfoMap
						.get(existingStudentSurveyResponseLabelInfo.getSurveyLabelId()).getSurveyResponseIds());
			}

			if (existingStudentSurveyResponseLabelInfo.isHasPreRequisite()
					&& !existingStudentSurveyResponseLabelInfo.isMetPreRequisite()) {
				respondedItemsActiveFlag = surveyLabelPrerequisiteDao.selectResponseActiveFlagBySurvey(surveyId,
						surveyLabelPreRequisiteInfoMap.get(existingStudentSurveyResponseLabelInfo.getSurveyLabelId())
								.getSurveyResponseIds());

				logger.debug(respondedItemsActiveFlag.toString());
				existingStudentSurveyResponseLabelInfo.setMetPreRequisite(getMetPreRequisiteValue(
						respondedItemsActiveFlag,
						surveyLabelPreRequisiteInfoMap.get(existingStudentSurveyResponseLabelInfo.getSurveyLabelId())
								.getPreRequisiteConditions(),
						surveyLabelPreRequisiteInfoMap.get(existingStudentSurveyResponseLabelInfo.getSurveyLabelId())
								.getSurveyResponseIds()));

			}
		}

		logger.trace("Leaving selectAllowedStudentSurveyResponseLabels method");
		return existingStudentSurveyResponseLabelInfos;
	}

	/**
	 * @param respondedItemsActiveFlag
	 * @param preRequisiteConditions
	 * @return
	 */
	private Boolean getMetPreRequisiteValue(List<Boolean> respondedItemsActiveFlag, List<String> preRequisiteConditions,
			List<Long> surveyResponseIds) {

		Boolean metPreRequisite = false;

		if (preRequisiteConditions.contains(CommonConstants.FIRST_CONTACT_PREREQUISITE_AND)) {
			logger.debug("Met PreRequisite Flag AND CONDITION");
			if (respondedItemsActiveFlag.contains(false)) {
				metPreRequisite = false;
			} else {
				if (respondedItemsActiveFlag != null && surveyResponseIds != null
						&& respondedItemsActiveFlag.size() == surveyResponseIds.size()) {
					metPreRequisite = true;
				} else {
					metPreRequisite = false;
				}
			}
		} else if (preRequisiteConditions.contains(CommonConstants.FIRST_CONTACT_PREREQUISITE_OR)) {
			logger.debug("Met PreRequisite Flag OR CONDITION");
			if (respondedItemsActiveFlag.contains(true)) {
				metPreRequisite = true;
			} else {
				metPreRequisite = false;
			}
		}
		logger.debug("Met PreRequisite Flag");
		logger.debug(metPreRequisite.toString());
		return metPreRequisite;
	}

	/**
	 * Finds the survey for a given student and adds if its not present.
	 * 
	 * @param studentId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Survey addSurveyIfNotPresent(Long studentId) {
		logger.trace("Entering addSurveyIfNotPresent method");
		
		int fcsPageCount = getFirstContactActivePageCount();

		Survey survey = studentSurveyResponseDao.findByStudentId(studentId);

		if (survey == null) {
			survey = new Survey();
			survey.setStudentId(studentId);
			survey.setSurveyName(SURVEY);
			Long inProgressStatusid = categoryDao.getCategoryId("IN_PROGRESS", "SURVEY_STATUS");
		    survey.setStatus(inProgressStatusid);
			survey.setAuditColumnProperties();
			Long userStateId = getUserStateId();
			FirstContactSettings firstContactSettings = getFirstContactSurveySetting(userStateId);
			Category coreSet = categoryService.selectByCategoryCodeAndType("CORE_SET_QUESTIONS", "FIRST CONTACT SETTINGS");
			Long lastEditedOption = firstContactSettings!= null ? firstContactSettings.getCategoryId() : coreSet.getId();
			survey.setLastEditedOption(lastEditedOption);
	        Category allQuestionsRequiredOption = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
	        survey.setAllQuestionsFlag(allQuestionsRequiredOption.getId().equals(lastEditedOption));
	        survey.setIncludeScienceFlag(firstContactSettings.getScienceFlag());
	        //Added mathFlag and elaFlag For F607
	        survey.setIncludeMathFlag(firstContactSettings.getMathFlag());
	        survey.setIncludeElaFlag(firstContactSettings.getElaFlag());
			studentSurveyResponseDao.insertSurvey(survey);
			survey = studentSurveyResponseDao.findByStudentId(studentId);
			for (int i = 1; i <= fcsPageCount; i++) {
				insertSurveyPageStatus(i, studentId);
			}
		}

		logger.trace("Leaving addSurveyIfNotPresent method");
		return survey;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertIntoDomainAuditHistory(Long objectId,String action, String source,String objectBeforUpdate,String objectAfterUpdate){
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(source);
		domainAuditHistory.setObjectType("SURVEY");
		domainAuditHistory.setObjectId(objectId);
		domainAuditHistory.setCreatedUserId(getUserId().intValue() );
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(action);
		domainAuditHistory.setObjectBeforeValues(objectBeforUpdate);
		domainAuditHistory.setObjectAfterValues(objectAfterUpdate);
		domainAuditHistoryDao.insert(domainAuditHistory);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> getFirstContactResponses(Long studentId, Long surveyId,
			int lastPageToBeSaved,int pageNumber, String status, boolean settingsChanged, Long stateId,boolean isToggleOnCategoryChange) {
		logger.trace("Entering getFirstContactResponses Service method");
		Long userStateId = getUserStateId();
		if(settingsChanged){
			// Only use passed in stateId while updating the surveys else user's stateId
			userStateId = stateId;
		}
		List<StudentSurveyResponseLabelInfo> studentSurveyResponseLabelInfo = null;
		List<String> surveyQuestionsDisplayType = null;
		Map<String, Object> firstContactResponsesMap = new HashMap<String, Object>();
		String surveyPageStatus = null;
		Survey survey = null;
		String action = StringUtils.EMPTY;
		String jsonBeforeUpdate = null;
		String jsonAfterUpdate = null;
		
		if (surveyId == null) {
			survey = addSurveyIfNotPresent(studentId);
			surveyId = survey.getId();
			action = EventTypeEnum.FCS_INSERTED.getCode();
		} else {
			action = EventTypeEnum.FCS_SECTION_EDITED.getCode();
			survey = studentSurveyResponseDao.findBySurveyId(surveyId);
		}
		SurveyJson surveyJson =  surveyMapper.getStudentAndSurveyValuesForJson(surveyId, null);
		if(surveyJson != null) {
			jsonBeforeUpdate = surveyJson.buildjsonString();
		} else {
			surveyJson = surveyMapper.getSurveyStatusJsonByPage(surveyId, pageNumber);
			if(surveyJson!=null) jsonAfterUpdate = surveyJson.buildjsonString();
		}
		if(lastPageToBeSaved == 0 && survey != null){
	        FirstContactSettings firstContactSettings = getFirstContactSurveySetting(userStateId);
	        Category coreSet = categoryService.selectByCategoryCodeAndType("CORE_SET_QUESTIONS", "FIRST CONTACT SETTINGS");
	        if(settingsChanged){
	        	Long enforcingOption = firstContactSettings!= null ? firstContactSettings.getCategoryId() : coreSet.getId();
    			Long lastEditedOption = survey.getLastEditedOption();
    			flipFirstContactSurveyOnOptionChange(survey.getId(), enforcingOption, lastEditedOption,isToggleOnCategoryChange, userStateId);
	        }
    		// Update last edited option once user edits survey with in-force setting
    		
    		Long lastEditedOption = firstContactSettings!= null ? firstContactSettings.getCategoryId() : coreSet.getId();
    		survey = studentSurveyResponseDao.findBySurveyId(surveyId);
    		survey.setLastEditedOption(lastEditedOption);
    		if(!hasFcsReadOnlyPermission() || ((hasFcsReadOnlyPermission()==true)&&(isToggleOnCategoryChange==true))){
    			studentSurveyResponseDao.updateSurveyStatus(survey);
    		}
		}

		// Get all labels/questions and responses along with student marked
		// responses.
		studentSurveyResponseLabelInfo = selectAllowedStudentSurveyResponseLabels(surveyId, pageNumber);

		if(!hasFcsReadOnlyPermission() || ((hasFcsReadOnlyPermission()==true)&&(isToggleOnCategoryChange==true))){
			if(status.equals("complete")){
				// Update pagestatus for previous page to complete.
				// If we Get the Last Page From Front End and Should Use that Instead of
				// the Last Page.
				updateSurveyPageStatus(lastPageToBeSaved, surveyId, false, userStateId);
			} else{
				updateSurveyPageStatusToNotComplete(lastPageToBeSaved, surveyId, status, userStateId);
			}
		}
		
		surveyPageStatus = getSurveyPageStatus(pageNumber, surveyId, userStateId);

		List<Integer> surveyPageStatusList = getSurveyPageStatusList(surveyId);

		List<SurveyLabelPrerequisite> surveyLabelPrerequisites = selectAllowedStudentSurveyResponseLabelInfoList(
				pageNumber);

		firstContactResponsesMap.put("studentSurveyResponseLabels", studentSurveyResponseLabelInfo);
		firstContactResponsesMap.put("surveyQuestionsDisplayTypeValues", surveyQuestionsDisplayType);
		firstContactResponsesMap.put("surveyLabelPrerequisites", surveyLabelPrerequisites);
		firstContactResponsesMap.put("pageNumber", pageNumber);
		firstContactResponsesMap.put("prevoiusPageStatus", surveyPageStatus);
		firstContactResponsesMap.put("surveyPageStatusList", surveyPageStatusList);
		firstContactResponsesMap.put("surveyId", surveyId);

		Category category = categoryDao.selectByPrimaryKey(survey.getStatus());
		surveyJson =  surveyMapper.getStudentAndSurveyValuesForJson(surveyId, null);
		if(surveyJson != null) {
			jsonAfterUpdate = surveyJson.buildjsonString();
		} else {
			surveyJson = surveyMapper.getSurveyStatusJsonByPage(surveyId, pageNumber);
			if(surveyJson!=null) jsonAfterUpdate = surveyJson.buildjsonString();
		}
		firstContactResponsesMap.put("surveyStatus", category.getCategoryCode());
		if (!hasFcsReadOnlyPermission()) {
			if (jsonBeforeUpdate == null && jsonAfterUpdate == null && !action.equals(EventTypeEnum.FCS_INSERTED.getCode())) {
				// If both values are null do not process
			} else if (StringUtils.isNotBlank(jsonBeforeUpdate) && StringUtils.isNotBlank(jsonAfterUpdate)
					&& jsonBeforeUpdate.equals(jsonAfterUpdate)) {
				// If both values are equal do not process
			} else {
				insertIntoDomainAuditHistory(surveyId, action, SourceTypeEnum.MANUAL.getCode(), jsonBeforeUpdate, jsonAfterUpdate);
			}
		}
		logger.trace("Leaving getFirstContactResponses Service method");
		return firstContactResponsesMap;
	}

	/**
	 * This method checks for already existing student surveys.
	 * 
	 * @param studentId
	 * @return {@link Boolean}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean checkForExistingSurvey(Long studentId) {

		boolean result;
		Survey survey = studentSurveyResponseDao.findByStudentId(studentId);
		if (survey != null) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String getSurveyPageStatus(Integer globalPageNum, Long surveyId, Long userStateId) {
		logger.trace("Entering getSurveyPageStatus method");

		Boolean isComplete = false;
		String surveyPageStatus = null;
		boolean isCoreSetQuestions = isCoreSetQuestions(userStateId);
		// In case of Core set Questions option is opted
		if(isCoreSetQuestions){
			Long stateId = getUserStateId();
			isComplete = surveyPageStatusDao.checkCompletionBySurveyAndPageNum(globalPageNum, surveyId, stateId, isCoreSetQuestions);
		} else {
			isComplete = getAllQuestionsAnsweredFlag(globalPageNum, surveyId);
		}
		
		if (isComplete != null && !isComplete) {
			surveyPageStatus = "Inprogress";
		} else {
			surveyPageStatus = "Complete";
		}

		logger.trace("Entering getSurveyPageStatus method");
		return surveyPageStatus;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertSurveyPageStatus(Integer globalPageNum, Long studentId) {
		logger.trace("Entering insertSurveyPageStatus method");

		List<SurveyPageStatus> surveyPageStatuses = null;

		Survey survey = studentSurveyResponseDao.findByStudentId(studentId);
		Boolean isComplete = false;

		if (isComplete != null && !isComplete) {
		} else {
			isComplete = true;
		}

		SurveyPageStatusExample surveyPageStatusExample = new SurveyPageStatusExample();
		SurveyPageStatusExample.Criteria surveyPageStatusCriteria = surveyPageStatusExample.createCriteria();

		surveyPageStatusCriteria.andSurveyIdEqualTo(survey.getId());
		surveyPageStatusCriteria.andGlobalPageNumEqualTo(globalPageNum);

		surveyPageStatuses = surveyPageStatusDao.selectByExample(surveyPageStatusExample);

		if (surveyPageStatuses == null || CollectionUtils.isEmpty(surveyPageStatuses)) {
			SurveyPageStatus newSurveyPageStatus = new SurveyPageStatus();

			newSurveyPageStatus.setIsCompleted(isComplete);
			newSurveyPageStatus.setSurveyId(survey.getId());
			newSurveyPageStatus.setGlobalPageNum(globalPageNum);
			newSurveyPageStatus.setActiveFlag(true);
			newSurveyPageStatus.setAuditColumnProperties();

			surveyPageStatusDao.insert(newSurveyPageStatus);
		}

		logger.trace("Entering insertSurveyPageStatus method");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateSurveyPageStatusToNotComplete(Integer globalPageNum, Long surveyId, String status, Long userStateId){
		// Find all dependent pages for the survey section and if globalpagenum exists in the list then update.
		String action = "FCS_SECTION_EDITED";
		String jsonBeforeUpdate = null;
		String jsonAfterUpdate = null;
		SurveyJson surveyJson = null;
		boolean isCoreSetQuestions = isCoreSetQuestions(userStateId);
		surveyJson = surveyMapper.getStudentAndSurveyValuesForJson(surveyId, globalPageNum);
    	if(surveyJson != null) {
    		jsonBeforeUpdate = surveyJson.buildjsonString();
    		if(StringUtils.isBlank(jsonBeforeUpdate)) {
    			surveyJson = surveyMapper.getSurveyStatusJson(surveyId);
    			jsonBeforeUpdate = surveyJson.buildjsonString();
    		}
    	}
		List<Integer> dependentPages = surveyLabelDao.getAllDependentPages(globalPageNum);
		if(dependentPages.contains(globalPageNum)){
			Boolean isComplete = false;
			// In case of Core set Questions option is opted
			if(isCoreSetQuestions){
				Long stateId = getUserStateId();
				isComplete = surveyPageStatusDao.checkCompletionBySurveyAndPageNum(globalPageNum, surveyId, stateId, isCoreSetQuestions);
			} else {
				isComplete = getAllQuestionsAnsweredFlag(globalPageNum, surveyId);
			}
			if ((isComplete != null && !isComplete) || (StringUtils.isNotBlank(status) && status.equals("incomplete")) ) {
				// Update passed-in pages
				updateSurveySectionStatusToNotComplete(globalPageNum, surveyId);
				// Update dependent pages also. 
				List<SurveyLabel> surveyLabels  = selectSurveyLabelsByAndCondition();
				if(surveyLabels != null){
					for(SurveyLabel surveyLabel: surveyLabels){
						updateSurveySectionStatusToNotComplete(surveyLabel.getGlobalPageNumber(), surveyId);
					}
				}
			}
			if(dependentPages.contains(globalPageNum)){
				List<SurveyLabel>   surveyLabels  = selectSurveyLabelsByAndCondition();
				if(surveyLabels != null){
					for(SurveyLabel surveyLabel: surveyLabels){
						updateSurveySectionStatusToNotComplete(surveyLabel.getGlobalPageNumber(), surveyId);
					}
				}
			}
		
		} else if ((null == status || (StringUtils.isNotBlank(status) && status.equals("incomplete"))) 
			&& !isCoreSetQuestions){
			// Update passed-in pages (dependent pages with OR condition)
			updateSurveySectionStatusToNotComplete(globalPageNum, surveyId);
		}
		surveyJson = surveyMapper.getStudentAndSurveyValuesForJson(surveyId, globalPageNum);
		if(surveyJson != null) {
    		jsonAfterUpdate = surveyJson.buildjsonString();
    	}
    	insertIntoDomainAuditHistory(surveyId, action, SourceTypeEnum.MANUAL.getCode(), jsonBeforeUpdate, jsonAfterUpdate);
	}

	private void updateSurveySectionStatusToNotComplete(Integer globalPageNum, Long surveyId) {
		List<SurveyPageStatus> surveyPageStatuses = null;
		SurveyPageStatusExample surveyPageStatusExample = new SurveyPageStatusExample();
		SurveyPageStatusExample.Criteria surveyPageStatusCriteria = surveyPageStatusExample.createCriteria();
		surveyPageStatusCriteria.andSurveyIdEqualTo(surveyId);
		surveyPageStatusCriteria.andGlobalPageNumEqualTo(globalPageNum);
		surveyPageStatuses = surveyPageStatusDao.selectByExample(surveyPageStatusExample);
		if (CollectionUtils.isNotEmpty(surveyPageStatuses)) {
			//This always has to come here.
			SurveyPageStatus surveyPageStatus = surveyPageStatuses.get(0);
			surveyPageStatus.setIsCompleted(false);
			surveyPageStatus.setAuditColumnProperties();
			surveyPageStatusDao.updateByPrimaryKey(surveyPageStatus);
			
			Long catId = categoryDao.getCategoryId("IN_PROGRESS", "SURVEY_STATUS");
			Survey survey = new Survey();
			survey.setStatus(catId);
			survey.setId(surveyId);
			survey.setAuditColumnPropertiesForUpdate();
			studentSurveyResponseDao.updateSurveyStatus(survey);
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public boolean getAllQuestionsAnsweredFlag(Integer globalPageNum, Long surveyId){
		// In case of All Questions option is opted
		List<StudentSurveyResponseLabelInfo> studentSurveyResponseLabelInfos = selectAllowedStudentSurveyResponseLabels(surveyId, globalPageNum);
		Set<String> displayQuestions = new HashSet<String>();
		Set<String> answeredQuestions = new HashSet<String>();
		for(StudentSurveyResponseLabelInfo studentSurveyResponseLabelInfo : studentSurveyResponseLabelInfos){
			String labelNumber = StringUtils.substringBefore(studentSurveyResponseLabelInfo.getLabelNumber(), "_");
			if(studentSurveyResponseLabelInfo.isDoDisplay()){
				displayQuestions.add(labelNumber);
				if(studentSurveyResponseLabelInfo.getStudentResponseId() != null && studentSurveyResponseLabelInfo.isActiveFlag()){
					answeredQuestions.add(labelNumber);
				}
			}
		}
		logger.debug("Page Status "+ displayQuestions.size() + " : " + answeredQuestions.size());
		return displayQuestions.size() == answeredQuestions.size();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public void updateSurveyPageStatus(Integer globalPageNum, Long surveyId, boolean batch, Long userStateId) {
		logger.trace("Entering updateSurveyPageStatus method");

		List<SurveyPageStatus> surveyPageStatuses = null;
		boolean isCoreSetQuestions = isCoreSetQuestions(userStateId);
		Boolean isComplete = false;
		// In case of Core set Questions option is opted
		if(isCoreSetQuestions){
			isComplete = surveyPageStatusDao.checkCompletionBySurveyAndPageNum(globalPageNum, surveyId, userStateId, isCoreSetQuestions);
		} else {
			isComplete = getAllQuestionsAnsweredFlag(globalPageNum, surveyId);
		}
		if (isComplete != null && !isComplete) {
		} else {
			isComplete = true;
		}

		SurveyPageStatusExample surveyPageStatusExample = new SurveyPageStatusExample();
		SurveyPageStatusExample.Criteria surveyPageStatusCriteria = surveyPageStatusExample.createCriteria();

		surveyPageStatusCriteria.andSurveyIdEqualTo(surveyId);
		surveyPageStatusCriteria.andGlobalPageNumEqualTo(globalPageNum);

		surveyPageStatuses = surveyPageStatusDao.selectByExample(surveyPageStatusExample);
		int surveyPagesCount = getFirstContactActivePageCount();
		if (CollectionUtils.isEmpty(surveyPageStatuses) && globalPageNum > 0) {
			SurveyPageStatus newSurveyPageStatus = new SurveyPageStatus();
			newSurveyPageStatus.setIsCompleted(true);
			newSurveyPageStatus.setSurveyId(surveyId);
			newSurveyPageStatus.setGlobalPageNum(globalPageNum);
			newSurveyPageStatus.setActiveFlag(true);
			newSurveyPageStatus.setAuditColumnProperties();
			surveyPageStatusDao.insert(newSurveyPageStatus);
		} else if (CollectionUtils.isNotEmpty(surveyPageStatuses)) {
			SurveyPageStatus surveyPageStatus = surveyPageStatuses.get(0);
			surveyPageStatus.setIsCompleted(isComplete);
			surveyPageStatus.setAuditColumnProperties();
			surveyPageStatusDao.updateByPrimaryKey(surveyPageStatus);
		}

		List<StudentSurveyResponse> studentResponses = studentSurveyResponseDao.findResponsesBySurveyId(surveyId);
		
		// Get the Dependent Survey Questions and If that Question is Mandatory then update that Page Status.
		List<SurveyLabelPrerequisite> surveyLabelPrerequisitesForAndCondition = surveyLabelPrerequisiteDao
				.selectByAndCondition();
		// If student responses contain all the three survey label prerequisites
		// then update the Page Number
		List<Long> studentSurveyResponseIdList = new ArrayList<Long>();
		for (StudentSurveyResponse studentSurveyResponse : studentResponses) {
			studentSurveyResponseIdList.add(studentSurveyResponse.getSurveyResponseId());
		}
		List<Long> containedList = new ArrayList<Long>();
		for (SurveyLabelPrerequisite preRequisiteAndCheck : surveyLabelPrerequisitesForAndCondition) {
			if (studentSurveyResponseIdList.contains(preRequisiteAndCheck.getSurveyResponseId())) {
				containedList.add(preRequisiteAndCheck.getSurveyResponseId());
			}
		}
		
		List<Integer> dependentPages = surveyLabelDao.getAllDependentPages(globalPageNum);
		if(dependentPages.contains(globalPageNum)){
		// Check And Condition here
		if ((studentSurveyResponseIdList.isEmpty() && containedList.isEmpty())
				||containedList.size() == surveyLabelPrerequisitesForAndCondition.size()) {
			List<StudentSurveyResponseLabel> surveyResponseLabels = studentSurveyResponseDao
					.findDependentResponseLabelsNotInSamePage(surveyId);
			if (surveyResponseLabels.size() == 0) {
				if(studentSurveyResponseIdList.isEmpty() && containedList.isEmpty()){
					updateSurveyPageStatusToNotComplete(globalPageNum, surveyId, "incomplete", userStateId);
				} else {
					updateSurveyPageStatusToNotComplete(globalPageNum, surveyId, null, userStateId);
				}
				
			}
		}
		}
		Survey survey = studentSurveyResponseDao.findBySurveyId(surveyId);
		Long completeStatusId = categoryDao.getCategoryId("COMPLETE", "SURVEY_STATUS");
		Long readyToSubmitId = categoryDao.getCategoryId("READY_TO_SUBMIT", "SURVEY_STATUS");
		String categoryCode = null;
		boolean flag = false;
		List<Integer> surveyPageStatusList = surveyPageStatusDao.getPageStatusList(surveyId);
		// Changed the Logic to Less than active survey page count and also Changed the Ready to
		// Submit Logic
		if (surveyPageStatusList.size() >= 1 && surveyPageStatusList.size() < surveyPagesCount) {
			categoryCode = "IN_PROGRESS";
			flag = true;
		}else if(surveyPageStatusList.size() == surveyPagesCount && !survey.getStatus().equals(completeStatusId) 
			&& !survey.getStatus().equals(readyToSubmitId)){
            categoryCode="READY_TO_SUBMIT";
            flag = true;
		}
		
		if (flag) {
			Long catId = categoryDao.getCategoryId(categoryCode, "SURVEY_STATUS");			
			survey.setStatus(catId);
			survey.setId(surveyId);
			survey.setAuditColumnPropertiesForUpdate();
			studentSurveyResponseDao.updateSurveyStatus(survey);
		}
		logger.trace("Exit updateSurveyPageStatus method");
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Integer> getSurveyPageStatusList(Long surveyId) {
		logger.trace("Entering getSurveyPageStatusList method");

		List<Integer> surveyPageStatusList = surveyPageStatusDao.getPageStatusList(surveyId);

		logger.trace("Exiting getSurveyPageStatusList method");
		return surveyPageStatusList;

	}

	/**
	 * Inserts/Updates the student responses upon selection on the UI.
	 *
	 * @param surveyId
	 * @param surveyResponseId
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addOrUpdateStudentSurvey(Long surveyId, String labelNumber, Long surveyResponseId,
			String surveyResponseText, User user) {
		logger.trace("Entering addOrUpdateStudentSurvey method");

		StudentSurveyResponseExample studentSurveyResponseExample = new StudentSurveyResponseExample();
		StudentSurveyResponseExample.Criteria studentSurveyResponseCriteria = studentSurveyResponseExample
				.createCriteria();
		List<StudentSurveyResponse> studentSurveyResponseList = null;

		// Updates the existing response active flag to false if there is any in
		// the DB.
		updateExistingStudentSurveyResponseActiveFlag(surveyId, labelNumber);

		if (surveyResponseId != null) {
			studentSurveyResponseCriteria.andSurveyIdEqualTo(surveyId);
			studentSurveyResponseCriteria.andSurveyResponseidEqualTo(surveyResponseId);
			studentSurveyResponseList = studentSurveyResponseDao.selectByExample(studentSurveyResponseExample);

			if (studentSurveyResponseList == null || CollectionUtils.isEmpty(studentSurveyResponseList)) {
				insertStudentSurveyResponse(surveyId, surveyResponseId, surveyResponseText);
			} else {
				updateStudentSurveyResponse(studentSurveyResponseList.get(0), surveyResponseText);
			}
			complexityBandService.calculateComplexityBandsForSurvey(surveyId, user,false);
		}

		logger.trace("Leaving addOrUpdateStudentSurvey method");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addOrUpdateStudentSurvey(Long surveyId, List<StudentSurveyResponseLabel> studentSurveyResponseLabelList,
			User user, int currentPageNumber) {
		logger.trace("Entering addOrUpdateStudentSurvey method");

		for (StudentSurveyResponseLabel studentSurveyResponseLabel : studentSurveyResponseLabelList) {
			StudentSurveyResponseExample studentSurveyResponseExample = new StudentSurveyResponseExample();
			StudentSurveyResponseExample.Criteria studentSurveyResponseCriteria = studentSurveyResponseExample
					.createCriteria();
			List<StudentSurveyResponse> studentSurveyResponseList = null;
			// Updates the existing response active flag to false if there is
			// any in the DB.
			updateExistingStudentSurveyResponseActiveFlag(surveyId, studentSurveyResponseLabel.getLabelNumber());

			if (studentSurveyResponseLabel.getStudentSurveyResponseId() != null) {
				studentSurveyResponseCriteria.andSurveyIdEqualTo(surveyId);
				studentSurveyResponseCriteria
						.andSurveyResponseidEqualTo(studentSurveyResponseLabel.getStudentSurveyResponseId());
				studentSurveyResponseList = studentSurveyResponseDao.selectByExample(studentSurveyResponseExample);

				if (studentSurveyResponseList == null || CollectionUtils.isEmpty(studentSurveyResponseList)) {
					insertStudentSurveyResponse(surveyId, studentSurveyResponseLabel.getStudentSurveyResponseId(),
							studentSurveyResponseLabel.getStudentSurveyResponseText());
				} else {
					updateStudentSurveyResponse(studentSurveyResponseList.get(0),
							studentSurveyResponseLabel.getStudentSurveyResponseText());
				}
			}
		}

		List<StudentSurveyResponse> studentResponses = studentSurveyResponseDao.findResponsesBySurveyId(surveyId);
		
		// Get the Dependent Survey Questions and If that Question is Mandatory
		// then update that Page Status.
		List<SurveyLabelPrerequisite> surveyLabelPrerequisitesForAndCondition = surveyLabelPrerequisiteDao
				.selectByAndCondition();
		// If student responses contain all the three survey label prerequisites
		// then update the Page Number
		List<Long> studentSurveyResponseIdList = new ArrayList<Long>();
		for (StudentSurveyResponse studentSurveyResponse : studentResponses) {
			studentSurveyResponseIdList.add(studentSurveyResponse.getSurveyResponseId());
		}
		List<Long> containedList = new ArrayList<Long>();
		for (SurveyLabelPrerequisite preRequisiteAndCheck : surveyLabelPrerequisitesForAndCondition) {
			if (studentSurveyResponseIdList.contains(preRequisiteAndCheck.getSurveyResponseId())) {
				containedList.add(preRequisiteAndCheck.getSurveyResponseId());
			}
		}
		// Check And Condition here
		if (containedList.size() == surveyLabelPrerequisitesForAndCondition.size()) {
			List<StudentSurveyResponseLabel> surveyResponseLabels = studentSurveyResponseDao
					.findDependentResponseLabelsNotInSamePage(surveyId);
			if (surveyResponseLabels.size() == 0) {
				updateSurveyPageStatusToNotComplete(currentPageNumber, surveyId, null, getUserStateId());
			}
		}
		complexityBandService.calculateComplexityBandsForSurvey(surveyId, user,false);
		
		logger.trace("Leaving addOrUpdateStudentSurvey method");
	}

	/**
	 * Updates the existing response activeflag to false if there is any in the
	 * DB.
	 * 
	 * @param studentSurveyResponseId
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateExistingStudentSurveyResponseActiveFlag(Long surveyId, String labelNumber) {
		logger.trace("Entering updateStudentSurveyResponse method");

		List<StudentSurveyResponseLabel> existingStudentSurveyResponseLabels = null;
		existingStudentSurveyResponseLabels = studentSurveyResponseLabelDao
				.selectExistingStudentSurveyResponseLabels(surveyId, labelNumber);

		if (existingStudentSurveyResponseLabels != null
				&& CollectionUtils.isNotEmpty(existingStudentSurveyResponseLabels)) {

			StudentSurveyResponse studentSurveyResponse = new StudentSurveyResponse();
			studentSurveyResponse.setId(existingStudentSurveyResponseLabels.get(0).getStudentSurveyResponseId());
			studentSurveyResponse.setSurveyId(surveyId);
			studentSurveyResponse.setSurveyResponseId(existingStudentSurveyResponseLabels.get(0).getSurveyResponseId());
			studentSurveyResponse.setAuditColumnProperties();
			studentSurveyResponse.setActiveFlag(false);

			studentSurveyResponseDao.updateByPrimaryKey(studentSurveyResponse);
			updateDependentResponses(studentSurveyResponse,
					existingStudentSurveyResponseLabels.get(0).getGlobalPageNum());

		} else {
			logger.debug(" Nothing to update for surveyId,labelNumber values - " + surveyId + "," + labelNumber);
		}

		logger.trace("Entering updateStudentSurveyResponse method");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateFirstContactSkippedResponses(Long surveyId, String[] labelNumbers) {
		logger.debug("Entering updateFirstContactSkippedResponses method");

		for (String labelNumber : labelNumbers) {
			updateExistingStudentSurveyResponseActiveFlag(surveyId, labelNumber);
		}
		logger.debug("Leaving updateFirstContactSkippedResponses method");
	}

	/**
	 * Inserts the student responses upon selection on the UI.
	 * 
	 * @param surveyId
	 * @param surveyResponseId
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertStudentSurveyResponse(Long surveyId, Long surveyResponseId, String surveyResponseText) {
		logger.trace("Entering insertStudentSurveyResponse method");

		StudentSurveyResponse studentSurveyResponse = new StudentSurveyResponse();
		studentSurveyResponse.setSurveyId(surveyId);
		studentSurveyResponse.setSurveyResponseId(surveyResponseId);
		studentSurveyResponse.setAuditColumnProperties();
		if (surveyResponseText != null && StringUtils.isNotEmpty(surveyResponseText)) {
			studentSurveyResponse.setResponseText(surveyResponseText);
		}
		studentSurveyResponseDao.insert(studentSurveyResponse);
		// updateDependentResponses(studentSurveyResponse);

		logger.trace("Entering insertStudentSurveyResponse method");
	}

	/**
	 * @param studentSurveyResponse
	 * @param currentLabelPageNum
	 */
	private void updateDependentResponses(StudentSurveyResponse studentSurveyResponse, int currentLabelPageNum) {
		// Added the Dependency Update.
		List<StudentSurveyResponse> dependencyresponseLabels = studentSurveyResponseDao.findDependentResponses(
				studentSurveyResponse.getSurveyId(), studentSurveyResponse.getSurveyResponseId());
		for (StudentSurveyResponse studentSurveyResponseLabel : dependencyresponseLabels) {
			StudentSurveyResponse dependentStudentSurveyResponse = new StudentSurveyResponse();
			dependentStudentSurveyResponse.setId(studentSurveyResponseLabel.getId());
			dependentStudentSurveyResponse.setSurveyId(studentSurveyResponseLabel.getSurveyId());
			dependentStudentSurveyResponse.setSurveyResponseId(studentSurveyResponseLabel.getSurveyResponseId());
			dependentStudentSurveyResponse.setAuditColumnProperties();
			dependentStudentSurveyResponse.setActiveFlag(false);
			//Get Dependencies and Student responses to false.
			studentSurveyResponseDao.updateByPrimaryKey(dependentStudentSurveyResponse);
		}
	}

	/**
	 * Updates the student responses upon selection on the UI, by setting the
	 * activeflag to true.
	 * 
	 * @param studentSurveyResponseId
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentSurveyResponse(StudentSurveyResponse studentSurveyResponse, String surveyResponseText) {
		logger.trace("Entering updateStudentSurveyResponse method");

		if (surveyResponseText != null && StringUtils.isNotEmpty(surveyResponseText)) {
			studentSurveyResponse.setResponseText(surveyResponseText);
		}
		studentSurveyResponse.setAuditColumnProperties();
		studentSurveyResponse.setActiveFlag(true);

		studentSurveyResponseDao.updateByPrimaryKey(studentSurveyResponse);

		// updateDependentResponses(studentSurveyResponse);

		logger.trace("Entering updateStudentSurveyResponse method");
	}

	/**
	 * @return
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final Map<Long, SurveyLabelPreRequisiteInfo> selectAllowedStudentSurveyResponseLabelInfoMap(Integer globalPageNum) {
		logger.debug("selectAllowedStudentSurveyResponseLabelInfoMap");

		List<SurveyLabelPrerequisite> surveyLabelPrerequisites = surveyLabelPrerequisiteDao.selectByExample(globalPageNum);
		logger.debug("surveyLabelPrerequisites" + surveyLabelPrerequisites);
		Map<Long, SurveyLabelPreRequisiteInfo> surveyLabelPreRequisiteInfoMap = new HashMap<Long, SurveyLabelPreRequisiteInfo>();

		for (SurveyLabelPrerequisite surveyLabelPrerequisite : surveyLabelPrerequisites) {
			if(surveyLabelPrerequisite.getActiveFlag().booleanValue()){
				if (!surveyLabelPreRequisiteInfoMap.containsKey(surveyLabelPrerequisite.getSurveyLabelId())) {
					
					SurveyLabelPreRequisiteInfo surveyLabelPreRequisiteInfo = SurveyLabelPreRequisiteInfo
							.getInstance(surveyLabelPrerequisite);
					surveyLabelPreRequisiteInfoMap.put(surveyLabelPrerequisite.getSurveyLabelId(),
							surveyLabelPreRequisiteInfo);
				} else {
					surveyLabelPreRequisiteInfoMap.get(surveyLabelPrerequisite.getSurveyLabelId())
							.addSurveyResponseId(surveyLabelPrerequisite);
				}
			}
		}

		return surveyLabelPreRequisiteInfoMap;
	}

	/**
	 * @return
	 * 
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<SurveyLabelPrerequisite> selectAllowedStudentSurveyResponseLabelInfoList(Integer globalPageNum) {

		logger.debug("selectAllowedStudentSurveyResponseLabelInfoList");

		List<SurveyLabelPrerequisite> surveyLabelPrerequisites = surveyLabelPrerequisiteDao
				.selectByGlobalPageNum(globalPageNum);

		logger.debug("infos are formed", surveyLabelPrerequisites);
		return surveyLabelPrerequisites;
	}

	/**
	 * @return
	 * 
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<SurveyLabelPrerequisite> selectBySurveyAndGlobalPageNum(Long surveyId, Integer globalPageNum) {

		logger.debug("selectBySurveyAndGlobalPageNum");

		// Survey survey = studentSurveyResponseDao.findByStudentId(studentId);

		List<SurveyLabelPrerequisite> surveyLabelPrerequisites = surveyLabelPrerequisiteDao
				.selectBySurveyAndGlobalPageNum(surveyId, globalPageNum);

		logger.debug("infos are formed", surveyLabelPrerequisites);
		return surveyLabelPrerequisites;
	}

	/**
	 * @return
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final List<Long> selectPrerequisiteSurveyLabelsInfoList() {
		logger.debug("Entering selectPrerequisiteSurveyLabelsInfoList()");

		List<Long> prerequisiteSurveyLabelsInfoList = surveyLabelPrerequisiteDao
				.selectPrerequisiteSurveyLabelsInfoList();

		logger.debug("Leaving selectPrerequisiteSurveyLabelsInfoList()");
		return prerequisiteSurveyLabelsInfoList;
	}

	/**
	 * @return
	 * 
	 */
	/*@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Collection<SurveyLabelPreRequisiteInfo> selectAllowedStudentSurveyResponseLabelInfos() {

		logger.debug("selectAllowedStudentSurveyResponseLabelInfos");
		List<SurveyLabelPrerequisite> surveyLabelPrerequisites = surveyLabelPrerequisiteDao.selectByExample(null);
		logger.debug("surveyLabelPrerequisites" + surveyLabelPrerequisites);
		Map<Long, SurveyLabelPreRequisiteInfo> surveyLabelPreRequisiteInfoMap = selectAllowedStudentSurveyResponseLabelInfoMap();
		logger.debug("infos are formed", surveyLabelPreRequisiteInfoMap.values());
		return surveyLabelPreRequisiteInfoMap.values();

	}*/

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private Category getInProgressCategory() {
		CategoryTypeExample catTypeExample = new CategoryTypeExample();
		catTypeExample.createCriteria().andTypeCodeEqualTo("SURVEY_STATUS");
		List<CategoryType> statusTypes = categoryTypeDao.selectByExample(catTypeExample);
		CategoryType statusType = statusTypes.get(0);
		CategoryExample catExample = new CategoryExample();
		catExample.createCriteria().andCategoryCodeEqualTo("IN_PROGRESS").andCategoryTypeIdEqualTo(statusType.getId());
		List<Category> statuses = categoryDao.selectByExample(catExample);
		Category inProgress = statuses.get(0);
		return inProgress;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateSurveyStatusToComplete(Long surveyId, User user) {
		String jsonBeforeUpdate = null;
		String jsonAfterUpdate = null;
		SurveyJson surveyJson = surveyMapper.getStudentAndSurveyValuesForJson(surveyId, null);
		if(surveyJson != null) {
			jsonBeforeUpdate = surveyJson.buildjsonString();
		}
		CategoryTypeExample catTypeExample = new CategoryTypeExample();
		catTypeExample.createCriteria().andTypeCodeEqualTo("SURVEY_STATUS");
		List<CategoryType> statusTypes = categoryTypeDao.selectByExample(catTypeExample);
		CategoryType statusType = statusTypes.get(0);
		CategoryExample catExample = new CategoryExample();
		catExample.createCriteria().andCategoryCodeEqualTo("COMPLETE").andCategoryTypeIdEqualTo(statusType.getId());
		List<Category> statuses = categoryDao.selectByExample(catExample);
		Category complete = statuses.get(0);
		Survey survey = studentSurveyResponseDao.findBySurveyId(surveyId);
		logger.debug("Updating survey for survery id = " + surveyId);
		logger.debug("Complete status  id = " + complete.getId());
		survey.setStatus(complete.getId());
		survey.setAuditColumnPropertiesForUpdate();
		int count = studentSurveyResponseDao.updateSurveyStatus(survey);
		if (count > 0) {
			complexityBandService.calculateFinalComplexityBandsForSurvey(surveyId, user);
			updateStudentTrackerTestSession(survey.getStudentId(), user);
			surveyJson = surveyMapper.getStudentAndSurveyValuesForJson(surveyId, null);
			if(survey != null) {
				jsonAfterUpdate = surveyJson.buildjsonString();
			}
			insertIntoDomainAuditHistory(surveyId, "FCS_SUBMMITTED", "MAUAL", jsonBeforeUpdate, jsonAfterUpdate);
		}
		logger.debug("Updated Count = " + count);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentTrackerTestSession(Long studentId, User user) {
		Student student = studentDao.findById(studentId);
		// List<StudentTrackerBand> studentTrackerBandList =
		// studentTrackerService.getStudentTrackerBandByStudentId(studentId);
		List<StudentTrackerBand> studentTrackerBandList = studentTrackerService
				.getStudentTrackerBandByStudentIdWithActiveOTW(studentId);

		if (studentTrackerBandList != null && studentTrackerBandList.size() > 0) {
			Set<Long> studentTrackerBandIds = new HashSet<Long>();
			Set<Long> studentTrackerIds = new HashSet<Long>();
			Set<Long> testSessionIds = new HashSet<Long>();
			for (StudentTrackerBand studentTrackerBand : studentTrackerBandList) {
				if ("ELA".equals(studentTrackerBand.getContentAreaCode())
						&& !studentTrackerBand.getComplexityBandId().equals(student.getFinalElaBandId())
						&& "FC".equals(studentTrackerBand.getSource())
						&& (!"complete".equalsIgnoreCase(studentTrackerBand.getTestStatus()))) {
					studentTrackerBandIds.add(studentTrackerBand.getId());
					studentTrackerIds.add(studentTrackerBand.getStudentTrackerId());
					if (studentTrackerBand.getTestSessionId() != null) {
						testSessionIds.add(studentTrackerBand.getTestSessionId());
					}
				}

				if ("M".equals(studentTrackerBand.getContentAreaCode())
						&& !studentTrackerBand.getComplexityBandId().equals(student.getFinalMathBandId())
						&& "FC".equals(studentTrackerBand.getSource())
						&& (!"complete".equalsIgnoreCase(studentTrackerBand.getTestStatus()))) {
					studentTrackerBandIds.add(studentTrackerBand.getId());
					studentTrackerIds.add(studentTrackerBand.getStudentTrackerId());
					if (studentTrackerBand.getTestSessionId() != null) {
						testSessionIds.add(studentTrackerBand.getTestSessionId());
					}
				}
				/*if ("Sci".equalsIgnoreCase(studentTrackerBand.getContentAreaCode())
						&& !studentTrackerBand.getComplexityBandId().equals(student.getFinalSciBandId())
						&& "FC".equals(studentTrackerBand.getSource())
						&& (!"complete".equalsIgnoreCase(studentTrackerBand.getTestStatus()))) {
					studentTrackerBandIds.add(studentTrackerBand.getId());
					studentTrackerIds.add(studentTrackerBand.getStudentTrackerId());
					if (studentTrackerBand.getTestSessionId() != null) {
						testSessionIds.add(studentTrackerBand.getTestSessionId());
					}
				}*/
			}

			if (studentTrackerBandIds.size() > 0) {
				studentTrackerBandMapper.inactiveByIds(studentTrackerBandIds, user.getId());
			}

			if (testSessionIds.size() > 0) {
				complexityBandService.fcUnenrollStudent(testSessionIds, user);
			}

			if (studentTrackerIds.size() > 0) {
				studentTrackerMapper.changeStatusToUnTrackedByIds(studentTrackerIds, user.getId());
			}
		}
	}

	@SuppressWarnings("unused")
	private boolean checkStudentTestComplete(List<StudentTrackerBand> studentTrackerBandList) {
		boolean flag = false;
		for (StudentTrackerBand studentTrackerBand : studentTrackerBandList) {
			if ("complete".equalsIgnoreCase(studentTrackerBand.getTestStatus())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	
    @Override
    public Survey findByStudentId(Long studentId) {
    	return studentSurveyResponseDao.findByStudentId(studentId);
    }

	@Override
	public Survey findBySurveyId(Long surveyId) {
		return studentSurveyResponseDao.findBySurveyId(surveyId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateSurveyStatus(Survey survey) {
		survey.setAuditColumnPropertiesForUpdate();
		return studentSurveyResponseDao.updateSurveyStatus(survey);
	}

	// Per US17690
	@Override
	public List<FirstContactSettings> getFirstContactSurveySettings(Long organizationId) {
		return surveySettingsDao.getFirstContactSurveySettings(organizationId);
	}
	
	@Override
	public FirstContactSettings getFirstContactSurveySetting(Long organizationId){
		return surveySettingsDao.getFirstContactSurveySetting(organizationId);
	}
	
	// Per US17690
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateFirstContactSurveySettings(FirstContactSettings newFirstContactSettings){
		// Per US18081
		OrganizationAnnualResets OrgAnnualResets =organizationAnnualResetsService.selectAcademicSchoolYearByOrgId(newFirstContactSettings.getOrganizationId());
		Long currentSchoolYear = organizationAnnualResetsService.getSchoolYearByOrganization(newFirstContactSettings.getOrganizationId());
		boolean schYrChanged = false;
		
		if(OrgAnnualResets != null && OrgAnnualResets.getSchoolYear() != null && currentSchoolYear != null && currentSchoolYear.equals(OrgAnnualResets.getSchoolYear())){
			schYrChanged = true;
		}		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
		FirstContactSettings existingFirstContactSettings = surveySettingsDao.getFirstContactSurveySetting(newFirstContactSettings.getOrganizationId());
		newFirstContactSettings.setAuditColumnPropertiesForUpdate();
		
		if(existingFirstContactSettings.getElaFlag() != newFirstContactSettings.getElaFlag()){
			if(newFirstContactSettings.getElaFlag()){
				
				logger.info("Reading and writing skills opted for state : "+ newFirstContactSettings.getOrganizationId());
				surveySettingsDao.updateFCSOnElaEnrolement(newFirstContactSettings);
			}else{
				logger.info("Reading and writing skills removed for state : "+ newFirstContactSettings.getOrganizationId());
				surveySettingsDao.updateFCSElaResponses(newFirstContactSettings);
			}
		}
		if(existingFirstContactSettings.getMathFlag() != newFirstContactSettings.getMathFlag()){
			if(newFirstContactSettings.getMathFlag()){
				
				logger.info("Math skills opted for state : "+ newFirstContactSettings.getOrganizationId());
				surveySettingsDao.updateFCSOnMathEnrolement(newFirstContactSettings);
			}else{
				logger.info("Math skills removed for state : "+ newFirstContactSettings.getOrganizationId());
				surveySettingsDao.updateFCSMathResponses(newFirstContactSettings);
			}
		}
		if(existingFirstContactSettings.getScienceFlag() != newFirstContactSettings.getScienceFlag()){
			if(newFirstContactSettings.getScienceFlag()){
				// Commented out as we do nothave annual reset at start, and user can
				// modify science flag with out annual reset.
				// if(schYrChanged){//update FC status with in the current school year only, and not before annual resets
					logger.info("Science skills opted for state : "+ newFirstContactSettings.getOrganizationId());
					surveySettingsDao.updateFCSOnScienceEnrolement(newFirstContactSettings);
				//}				
			}else{
				logger.info("Science skills removed for state : "+ newFirstContactSettings.getOrganizationId());
				surveySettingsDao.updateFCSScienceResponses(newFirstContactSettings);
			}
		}
		return surveySettingsDao.updateFirstContactSurveySettings(newFirstContactSettings);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void toggleSurveyOnCategoryChange(FirstContactSettings persistedFcs, FirstContactSettings newFirstContactSettings) {
        List<Long> surveyIds = new ArrayList<Long>();
        boolean isToggleOnCategoryChange=true;
		if(persistedFcs.getCategoryId().longValue() != newFirstContactSettings.getCategoryId().longValue())
		{
			logger.info("Organization : "+ newFirstContactSettings.getOrganizationId() + " in db :" + persistedFcs.getCategoryId() + " new setting : " + newFirstContactSettings.getCategoryId());
			List<Survey> surveys=studentSurveyResponseDao.findSurveyByOrgId(newFirstContactSettings.getOrganizationId());
			for(Survey survey:surveys)
			{
				getFirstContactResponses(survey.getStudentId(), survey.getId(), 0, 1, "complete", true, newFirstContactSettings.getOrganizationId(),isToggleOnCategoryChange);
				surveyIds.add(survey.getId());
			}
			logger.info("Surveys update for state id : "+ newFirstContactSettings.getOrganizationId());
			logger.info("Updated surveys are : " + surveyIds );
			logger.info("Update completed : "+ true);
		}
	}
	
	// Per US17306
	@Override
	public List<SurveySection> getSurveyRootSections(){
		List<SurveySection> rootSurveySections= surveySectionDao.getSurveyRootSections();
		return rootSurveySections;
		
	}

	// Per US17306
	@Override
	public List<SurveySection> getSurveySubSections() {
		List<SurveySection> suverySubSections=surveySectionDao.getSurveySubSections();
		Long stateId = getUserStateId();
		FirstContactSettings firstContactSettings =surveySettingsDao.getFirstContactSurveySetting(stateId);
		if(firstContactSettings != null && firstContactSettings.getElaFlag()){
			// Do nothing.
		} else {
			List<Integer> elaSectionIndexList = new ArrayList<Integer>();
			for(SurveySection surveySubSection : suverySubSections){
				if(surveySubSection.getSurveySectionName().equals("Reading Skills") || surveySubSection.getSurveySectionName().equals("Writing Skills")){
					elaSectionIndexList.add(suverySubSections.indexOf(surveySubSection));
				}
			}
			Collections.sort(elaSectionIndexList,Collections.reverseOrder());
			if((elaSectionIndexList!=null && !elaSectionIndexList.isEmpty())){
				for(Integer elaSectionIndex:elaSectionIndexList){
					suverySubSections.remove(elaSectionIndex.intValue());
				}
			}
		}
		
		if(firstContactSettings != null && firstContactSettings.getMathFlag()){
			// Do nothing.
		} else {
			int mathSectionIndex = -999;
			for(SurveySection surveySubSection : suverySubSections){
				if(surveySubSection.getSurveySectionName().equals("Math Skills")){
					mathSectionIndex = suverySubSections.indexOf(surveySubSection);
				}
			}
			if(!(mathSectionIndex == -999)){
				suverySubSections.remove(mathSectionIndex);
			}
		}
		
		if(firstContactSettings != null && firstContactSettings.getScienceFlag()){
			// Do nothing.
		} else {
			int scienceSectionIndex = -999;
			for(SurveySection surveySubSection : suverySubSections){
				if(surveySubSection.getSurveySectionName().equals("Science Skills")){
					scienceSectionIndex = suverySubSections.indexOf(surveySubSection);
				}
			}
			if(!(scienceSectionIndex == -999)){
				suverySubSections.remove(scienceSectionIndex);
			}
		}
		for(SurveySection surveySubSection : suverySubSections){
			surveySubSection.setPageIndex(suverySubSections.indexOf(surveySubSection)+1);
		}
		return suverySubSections;
	}

	public Long getUserStateId() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long organizationId = userDetails.getUser().getOrganizationId();
        List<Organization> userOrgHierarchy = new ArrayList<Organization>();
        userOrgHierarchy = organizationDao.getAllParents(organizationId);
        userOrgHierarchy.add(organizationDao.get(organizationId));
        Long stateId = null;
        for(Organization org : userOrgHierarchy){
        	if(org.getOrganizationType().getTypeCode().equals("ST")){
        		stateId = org.getId();
        	}
        }
		return stateId;
	}
	
	private Long getUserId() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return user.getId();
	}
	
	@Override
	public List<Integer> getActiveRootSections(Long surveyId){
		return surveySectionDao.getActiveRootSections(surveyId);
	}

	@Override
	public List<SurveyLabel> selectSurveyLabelsByAndCondition() {
		return surveyLabelDao.selectSurveyLabelsByAndCondition();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void flipFirstContactSurveyOnOptionChange(Long surveyId, Long enforcingOption, Long lastEditedOption,
		boolean isToggleOnCategoryChange, Long userStateId) {
		// Flip from Core to All 
		Category allQuestions = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
		// Update all page status as per existing logic
		List<SurveyPageStatus> surveyPageStatusList = getSurveyPageStatus(surveyId);
		for(SurveyPageStatus surveyPageStatus : surveyPageStatusList){
			if(!hasFcsReadOnlyPermission() || ((hasFcsReadOnlyPermission()==true)&&(isToggleOnCategoryChange==true))){
				updateSurveyPageStatus(surveyPageStatus.getGlobalPageNum(), surveyId, false, userStateId);
			}
		}
		auditSurveyToggleSetting(surveyId, enforcingOption, allQuestions);
	}

	private void auditSurveyToggleSetting(Long surveyId, Long enforcingOption, Category allQuestions) {
		Survey survey = new Survey();
		survey.setId(surveyId);
		survey.setAuditColumnPropertiesForUpdate();
		logger.debug("Updating :" + surveyId + " with allquestionsflag as : " + enforcingOption.equals(allQuestions.getId()));
		survey.setAllQuestionsFlag(enforcingOption.equals(allQuestions.getId()));
		if(!hasFcsReadOnlyPermission()){
			surveySettingsDao.auditSurveyToggleSetting(survey);
		}
	}
	
	private boolean isCoreSetQuestions(Long userStateId){
		FirstContactSettings firstContactSettings = getFirstContactSurveySetting(userStateId);
        Category coreSetQuestions = categoryService.selectByCategoryCodeAndType("CORE_SET_QUESTIONS", "FIRST CONTACT SETTINGS");
		Long enforcingOption = firstContactSettings != null ? firstContactSettings.getCategoryId() : coreSetQuestions.getId();
		boolean isCoreSet = enforcingOption.equals(coreSetQuestions.getId());
		logger.debug("Enforcing option for state : " + userStateId + " is coreset? "+ isCoreSet);
		return isCoreSet;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SurveyPageStatus> getSurveyPageStatus(Long surveyId) {
		logger.trace("Entering getSurveyPageStatus method");
		return surveyPageStatusDao.getAllSurveyPageStatus(surveyId);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public FirstContactSettings getOrganizationScienceFlagSetting(Long organizationId, Long schoolYear, Boolean currentSchYr, Boolean prevSchYr){
		return surveySettingsDao.getCurrentSchYrFCScienceSettingByOrgId(organizationId, schoolYear, currentSchYr, prevSchYr);
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void resetSurveyStatus(Long organizationId, Long inProgressStatusId, Long completeStatusId, Long readyToSubmitId){
		
		OrganizationAnnualResets orgAnnualResets =organizationAnnualResetsService.selectAcademicSchoolYearByOrgId(organizationId);
		Boolean currentYrScienceSetting = null;
		Boolean prevYrScieneSetting= null;
		int rowsUpdated = 0;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		Long modifiedUser = userDetails.getUserId();
		
		if(orgAnnualResets != null && !orgAnnualResets.getFcsResetComplete()){
			FirstContactSettings currentSchYrFCSettings = getOrganizationScienceFlagSetting(organizationId, orgAnnualResets.getSchoolYear(), true, null);
			FirstContactSettings previousSchYrFCSettings = getOrganizationScienceFlagSetting(organizationId, (orgAnnualResets.getSchoolYear() - 1), null, true);
			if(currentSchYrFCSettings != null){
				currentYrScienceSetting = currentSchYrFCSettings.getScienceFlag();
			}
			if(previousSchYrFCSettings != null){
				prevYrScieneSetting = previousSchYrFCSettings.getScienceFlag();
			}
			
			if(currentYrScienceSetting != prevYrScieneSetting){
				if(currentYrScienceSetting){//enabled
					//set to inprogress from complete and readytosubmit
					rowsUpdated = surveySettingsDao.resetFCSStatusOnOrgScienceContentFlag(organizationId, completeStatusId, inProgressStatusId,
							modifiedUser,new Date());
					logger.debug("Status on "+ rowsUpdated+" surveys has been updated from COMPLETD to INPROGRESS");
					
					rowsUpdated = surveySettingsDao.resetFCSStatusOnOrgScienceContentFlag(organizationId, readyToSubmitId, inProgressStatusId,
							modifiedUser,new Date());					
					logger.debug("Status on "+ rowsUpdated+" surveys has been updated from READY_TO_SUBMITTED to INPROGRESS");
					
					organizationAnnualResetsService.updateFCSResetCompleteFlagByOrgId(organizationId, true);
				}else{//disabled
					//Do nothing, status remains same, it is hard to find out the remaining response required questions on each survey
				}
			}else if(currentYrScienceSetting != null && prevYrScieneSetting != null){
				rowsUpdated = surveySettingsDao.resetFCSStatusOnOrgScienceContentFlag(organizationId, completeStatusId, readyToSubmitId,
						modifiedUser,new Date());
				logger.debug("Status on "+ rowsUpdated+" surveys has been updated from COMPLETD to READY_TO_SUBMITTED");
				
				organizationAnnualResetsService.updateFCSResetCompleteFlagByOrgId(organizationId, true);
			}
		}
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int inactivateOtherSchoolYearRecords(Long schoolYear, Long organizationId) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		
		return surveySettingsDao.inactivateOtherSchoolYearRecords(schoolYear, organizationId,userDetails.getUserId());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertFirstContactSettings(FirstContactSettings firstContactSettings) {
		return surveySettingsDao.insertFirstContactSettings(firstContactSettings);
	}	
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public FirstContactSettings checkIfOrgSettingsExists(Long organizationId, Long schoolYear){
		return surveySettingsDao.checkIfOrgSettingsExists(organizationId, schoolYear);
	}
	
	@Override
	public boolean addToGroupAuditTrailHistory(DomainAuditHistory domainAuditHistory) {
		JsonNode before = null;
		JsonNode after = null;
		boolean isProcessed = false;
		String surveyStatsBeforeEdit = StringUtils.EMPTY;
		String stateStudentidentifier = StringUtils.EMPTY;
		String studentFirstName = StringUtils.EMPTY;
		String studentLastName = StringUtils.EMPTY;
		Long studentId = new Long(0L);
		String surveyStatus = StringUtils.EMPTY;
		try {
			if (StringUtils.isBlank(domainAuditHistory.getObjectBeforeValues())
					&& EventTypeEnum.FCS_INSERTED.getCode().equals(domainAuditHistory.getAction())) {
				if (StringUtils.isNotBlank(domainAuditHistory.getObjectAfterValues())) {
					after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
					stateStudentidentifier = after.get("stateStudentidentifier").asText();
					studentFirstName = after.get("studentFirstName").asText();
					studentLastName = after.get("studentLastName").asText();
					studentId = after.get("studentId").longValue();
					surveyStatus = after.get("surveyStatus").asText();
				}
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(), stateStudentidentifier, studentFirstName,
						studentLastName, domainAuditHistory.getId(), domainAuditHistory.getCreatedUserId().longValue(),
						EventTypeEnum.FCS_INSERTED.getCode(), studentId, "NOT STARTED", "IN-PROGRESS",
						domainAuditHistory.getObjectBeforeValues(), domainAuditHistory.getObjectAfterValues());
				isProcessed = true;
			} else if (StringUtils.isNotBlank(domainAuditHistory.getObjectBeforeValues())
					&& StringUtils.isNotBlank(domainAuditHistory.getObjectAfterValues())
					&& domainAuditHistory.getObjectBeforeValues().equals(domainAuditHistory.getObjectAfterValues())) {
				// If both are equal no need to process them as there is no
				// change in state.
				isProcessed = true;
				logger.warn(
						"In-valid (no change) entry in Domainaudithistory table" + domainAuditHistory.getObjectId());
			} else if (EventTypeEnum.FCS_SECTION_EDITED.getCode().equals(domainAuditHistory.getAction())) {
				String eventName = StringUtils.EMPTY;
				String beforeActiveFlag = StringUtils.EMPTY;
				String afterActiveFlag = StringUtils.EMPTY;
				String beforeResponseJson = "{}";
				String afterResponseJson = "{}";

				Map<String, String> beforeSurveySections = new HashMap<>();
				Map<String, String> afterSurveySections = new HashMap<>();

				Map<String, String> beforeQuestions = new HashMap<>();
				Map<String, String> afterQuestions = new HashMap<>();

				if (StringUtils.isNotBlank(domainAuditHistory.getObjectAfterValues())) {
					after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				}
				if (StringUtils.isNotBlank(domainAuditHistory.getObjectBeforeValues())) {
					before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
					surveyStatsBeforeEdit = before.get("surveyStatus").asText();
				} else {
					surveyStatsBeforeEdit = "NOT_STARTED";
				}
				if (before == null && after == null) {
					// do nothing
					logger.warn(
							"In-valid (blank) entry in Domainaudithistory table" + domainAuditHistory.getObjectId());
					isProcessed = true;
				} else if (before == null && after != null) {
					ArrayNode afterStudentSurveySections = (ArrayNode) after.get("studentSurveySectionsJson");
					Set<String> afterSuveySectionCodes = new HashSet<String>();
					// Process any page updates.
					for (JsonNode afterStudentSurveySection : afterStudentSurveySections) {
						String afterSectionCode = afterStudentSurveySection.get("surveySectionCode").asText();
						afterSuveySectionCodes.add(afterSectionCode);
						JsonNode surveyPageJsons = afterStudentSurveySection.get("surevyPageJsons");
						if (surveyPageJsons != null & StringUtils.isNotBlank(surveyPageJsons.toString())) {
							for (JsonNode afterSurevyPageJson : surveyPageJsons) {
								if (afterSurevyPageJson.get("studentSurveyResponsesJsons").size() > 0) {
									for (JsonNode afterSurveyResponse : afterSurevyPageJson
											.get("studentSurveyResponsesJsons")) {
										String afterLabelNumber = afterSurveyResponse.get("labelNumber").asText();
										afterActiveFlag = afterSurveyResponse.get("responseActiveFlag").asText();
										if (afterActiveFlag.equals("true")) {
											eventName = afterLabelNumber + "_EDITED";
											afterResponseJson = afterSurveyResponse.toString();
											insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
													after.get("stateStudentidentifier").asText(),
													after.get("studentFirstName").asText(),
													after.get("studentLastName").asText(), domainAuditHistory.getId(),
													domainAuditHistory.getCreatedUserId().longValue(), eventName,
													after.get("studentId").longValue(), surveyStatsBeforeEdit,
													after.get("surveyStatus").asText(), beforeResponseJson,
													afterResponseJson);
										}
									}
								} else {
									eventName = afterSectionCode + "_EDITED";
									insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
											after.get("stateStudentidentifier").asText(),
											after.get("studentFirstName").asText(),
											after.get("studentLastName").asText(), domainAuditHistory.getId(),
											domainAuditHistory.getCreatedUserId().longValue(), eventName,
											after.get("studentId").longValue(), surveyStatsBeforeEdit,
											after.get("surveyStatus").asText(), beforeResponseJson,
											afterStudentSurveySection.toString());
								}

							}
						} else {
							eventName = afterSectionCode + "_EDITED";
							insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
									after.get("stateStudentidentifier").asText(),
									after.get("studentFirstName").asText(), after.get("studentLastName").asText(),
									domainAuditHistory.getId(), domainAuditHistory.getCreatedUserId().longValue(),
									eventName, after.get("studentId").longValue(), surveyStatsBeforeEdit,
									after.get("surveyStatus").asText(), beforeResponseJson,
									afterStudentSurveySection.toString());
						}

					}
					isProcessed = true;
				} else if (before != null && after == null) {
					// do nothing
					logger.warn("In-valid entry in Domainaudithistory table" + domainAuditHistory.getObjectId());
					isProcessed = true;
				} else if (before != null && after != null) {
					ArrayNode beforeStudentSurveySections = (ArrayNode) before.get("studentSurveySectionsJson");
					ArrayNode afterStudentSurveySections = (ArrayNode) after.get("studentSurveySectionsJson");
					stateStudentidentifier = after.get("stateStudentidentifier").asText();
					studentFirstName = after.get("studentFirstName").asText();
					studentLastName = after.get("studentLastName").asText();
					studentId = after.get("studentId").longValue();
					surveyStatus = after.get("surveyStatus").asText();

					// Process get a list of questions before answering.
					for (JsonNode beforeStudentSurveySection : beforeStudentSurveySections) {
						String beforeSectionCode = beforeStudentSurveySection.get("surveySectionCode").asText();
						logger.debug("beforeSectionCode : " + beforeSectionCode);
						for (JsonNode beforeSurevyPageJson : beforeStudentSurveySection.get("surevyPageJsons")) {
							String beforeSurveySectionId = beforeSurevyPageJson.get("surveySectionId").asText();
							logger.debug("beforeSurveySectionId : " + beforeSurveySectionId);
							for (JsonNode beforeSurveyResponse : beforeSurevyPageJson
									.get("studentSurveyResponsesJsons")) {
								String beforeLabelNumber = beforeSurveyResponse.get("labelNumber").asText();
								logger.debug("beforeLabelNumber : " + beforeLabelNumber);
								beforeActiveFlag = beforeSurveyResponse.get("responseActiveFlag").asText();
								if (beforeActiveFlag.equals("true")) {
									beforeResponseJson = beforeSurveyResponse.toString();
									beforeQuestions.put(beforeLabelNumber, beforeResponseJson);
								}
							}
							beforeSurveySections.put(beforeSectionCode, beforeSurevyPageJson.toString());
						}
					}
					// Process get a list of questions after answering.
					for (JsonNode afterStudentSurveySection : afterStudentSurveySections) {
						String afterSectionCode = afterStudentSurveySection.get("surveySectionCode").asText();
						for (JsonNode afterSurevyPageJson : afterStudentSurveySection.get("surevyPageJsons")) {
							for (JsonNode afterSurveyResponse : afterSurevyPageJson
									.get("studentSurveyResponsesJsons")) {
								String afterLabelNumber = afterSurveyResponse.get("labelNumber").asText();
								afterActiveFlag = afterSurveyResponse.get("responseActiveFlag").asText();
								if (afterActiveFlag.equals("true")) {
									afterResponseJson = afterSurveyResponse.toString();
									afterQuestions.put(afterLabelNumber, afterResponseJson);

								}
							}
							afterSurveySections.put(afterSectionCode, afterSurevyPageJson.toString());
						}
					}

					boolean questionsModified = false;
					for (String question : afterQuestions.keySet()) {
						// check if there is any change and process
						if (beforeQuestions.containsKey(question)) {
							if (!beforeQuestions.get(question).equals(afterQuestions.get(question))) {
								questionsModified = true;
								eventName = question + "_EDITED";
								insertToAuditTrailHistory(domainAuditHistory.getObjectId(), stateStudentidentifier,
										studentFirstName, studentLastName, domainAuditHistory.getId(),
										domainAuditHistory.getCreatedUserId().longValue(), eventName, studentId,
										surveyStatsBeforeEdit, surveyStatus, beforeQuestions.get(question),
										afterQuestions.get(question));
							}
						} else {
							questionsModified = true;
							// If no prior answer, treat them as new answers.
							eventName = question + "_EDITED";
							insertToAuditTrailHistory(domainAuditHistory.getObjectId(), stateStudentidentifier,
									studentFirstName, studentLastName, domainAuditHistory.getId(),
									domainAuditHistory.getCreatedUserId().longValue(), eventName, studentId,
									surveyStatsBeforeEdit, surveyStatus, "{}", afterQuestions.get(question));
						}
					}

					if (!questionsModified) {
						for (String surveySection : afterSurveySections.keySet()) {
							// check if there is any change and process
							if (beforeSurveySections.containsKey(surveySection)) {
								if (!beforeSurveySections.get(surveySection)
										.equals(afterSurveySections.get(surveySection))) {
									eventName = surveySection + "_EDITED";
									insertToAuditTrailHistory(domainAuditHistory.getObjectId(), stateStudentidentifier,
											studentFirstName, studentLastName, domainAuditHistory.getId(),
											domainAuditHistory.getCreatedUserId().longValue(), eventName, studentId,
											surveyStatsBeforeEdit, surveyStatus,
											beforeSurveySections.get(surveySection),
											afterSurveySections.get(surveySection));
								}
							} else {
								eventName = surveySection + "_EDITED";
								insertToAuditTrailHistory(domainAuditHistory.getObjectId(), stateStudentidentifier,
										studentFirstName, studentLastName, domainAuditHistory.getId(),
										domainAuditHistory.getCreatedUserId().longValue(), eventName, studentId,
										surveyStatsBeforeEdit, surveyStatus, "{}",
										afterSurveySections.get(surveySection));
							}
						}
					}
				}
				isProcessed = true;
			} else if (EventTypeEnum.FCS_SUBMMITTED.getCode().equals(domainAuditHistory.getAction())) {
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
						after.get("stateStudentidentifier").asText(), after.get("studentFirstName").asText(),
						after.get("studentLastName").asText(), domainAuditHistory.getId(),
						domainAuditHistory.getCreatedUserId().longValue(), EventNameForAudit.FCS_SUBMMITTED.getCode(),
						after.get("studentId").longValue(), before.get("surveyStatus").asText(),
						after.get("surveyStatus").asText(), domainAuditHistory.getObjectBeforeValues(),
						domainAuditHistory.getObjectAfterValues());
				isProcessed = true;
			} else if (EventTypeEnum.FCS_COMP_BANDS.getCode().equals(domainAuditHistory.getAction())) {
				auditComplexityBands(domainAuditHistory);
				isProcessed = true;
			}
		} catch (JsonProcessingException e) {
			logger.error("value insertion in firstcontactsurveyaudithistory table Failed for "
					+ domainAuditHistory.getObjectId(), e);
			isProcessed = false;
		} catch (IOException e) {
			logger.error("value insertion in firstcontactsurveyaudithistory table Failed for "
					+ domainAuditHistory.getObjectId(), e);
			isProcessed = false;
		} catch (Exception e) {
			logger.error("value insertion in firstcontactsurveyaudithistory table Failed for "
					+ domainAuditHistory.getObjectId(), e);
			isProcessed = false;
		}
		if (isProcessed) {
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(), "COMPLETED");
		} else {
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(), "FAILED");
		}
		return isProcessed;
	}

	private void auditComplexityBands(DomainAuditHistory domainAuditHistory)
			throws IOException, JsonProcessingException, ParseException {
		JsonNode before;
		JsonNode after;
		Long studentId;
		after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
		before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
		studentId = after.get("studentId").longValue();
		String studentName = after.get("studentName").asText();
		Long surveyModifiedUserId = after.get("surveyModifiedUserId").longValue();
		String surveyModifiedDateStr = after.get("surveyModifiedDate").asText();
		Long schoolYear = after.get("currentSchoolYear").longValue();

		// final complexity band values after update
        String afterFinalElaBand = StringUtils.EMPTY;
        if (after.get("finalElaBand") != null) {
            afterFinalElaBand = after.get("finalElaBand").asText();
        }

        String afterFinalMathBand = StringUtils.EMPTY;
        if (after.get("finalMathBand") != null) {
            afterFinalMathBand = after.get("finalMathBand").asText();
        }

        String afterFinalSciBand = StringUtils.EMPTY;
        if (after.get("finalSciBand") != null) {
            afterFinalSciBand = after.get("finalSciBand").asText();
        }

        String afterCommBand = StringUtils.EMPTY;
        if (after.get("commBand") != null) {
            afterCommBand = after.get("commBand").asText();
        }

        String afterWritingBand = StringUtils.EMPTY;
        if (after.get("writingBand") != null) {
            afterWritingBand = after.get("writingBand").asText();
        }
                
        // final complexity band values before update
        String beforeFinalElaBand = StringUtils.EMPTY;
        if (before.get("finalElaBand") != null) {
            if (StringUtils.isNotBlank(before.get("finalElaBand").asText())) {
                beforeFinalElaBand = before.get("finalElaBand").asText();
            }
        }else {
            beforeFinalElaBand = afterFinalElaBand;
        }

        String beforeFinalMathBand = StringUtils.EMPTY;
        if (before.get("finalMathBand") != null) {
            if (StringUtils.isNotBlank(before.get("finalMathBand").asText())) {
                beforeFinalMathBand = before.get("finalMathBand").asText();
            }
        }else {
            beforeFinalMathBand = afterFinalMathBand;
        }

        String beforeFinalSciBand = StringUtils.EMPTY;
        if (before.get("finalSciBand") != null) {
            if (StringUtils.isNotBlank(before.get("finalSciBand").asText())) {
                beforeFinalSciBand = before.get("finalSciBand").asText();
            }
        }else {
            beforeFinalSciBand = afterFinalSciBand;
        }

        String beforeCommBand = StringUtils.EMPTY;
        if (before.get("commBand") != null) {
            if (StringUtils.isNotBlank(before.get("commBand").asText())) {
                beforeCommBand = before.get("commBand").asText();
            }
        }else {
            beforeCommBand = afterCommBand;
        }

        String beforeWritingBand = StringUtils.EMPTY;
        if (before.get("writingBand") != null) {
            if (StringUtils.isNotBlank(before.get("writingBand").asText())) {
                beforeWritingBand = before.get("writingBand").asText();
            }
        }else {
            beforeWritingBand = afterWritingBand;
        }

			if (StringUtils.isNotBlank(afterFinalElaBand)) {
				insertToFcsCompBandHistory(studentId, studentName, CompBandSubject.FINAL_ELA.getName(),
						beforeFinalElaBand, afterFinalElaBand, surveyModifiedDateStr, surveyModifiedUserId,
						schoolYear);
			}
			if (StringUtils.isNotBlank(afterFinalMathBand)) {
				insertToFcsCompBandHistory(studentId, studentName, CompBandSubject.FINAL_MATH.getName(),
						beforeFinalMathBand, afterFinalMathBand, surveyModifiedDateStr, surveyModifiedUserId,
						schoolYear);
			}
			if (StringUtils.isNotBlank(afterFinalSciBand)) {
				insertToFcsCompBandHistory(studentId, studentName, CompBandSubject.FINAL_SCI.getName(),
						beforeFinalSciBand, afterFinalSciBand, surveyModifiedDateStr, surveyModifiedUserId,
						schoolYear);
			}
			if (StringUtils.isNotBlank(afterCommBand)) {
				insertToFcsCompBandHistory(studentId, studentName, CompBandSubject.COMM_BAND.getName(),
						beforeCommBand, afterCommBand, surveyModifiedDateStr, surveyModifiedUserId, schoolYear);
			}
			if (StringUtils.isNotBlank(afterWritingBand)) {
				insertToFcsCompBandHistory(studentId, studentName, CompBandSubject.WRITING_BAND.getName(),
						beforeWritingBand, afterWritingBand, surveyModifiedDateStr, surveyModifiedUserId,
						schoolYear);
			}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToAuditTrailHistory(Long objectId,String stateStudentId,String studentFirstName,String studentLastName,
			Long domainAuditHistoryId,Long modifiedUserId,String eventName,Long studentId, String surveyStatusBeforeEdit, String surveyStatusAfterEdit, String beforeValue,String currentValue){
		User user=userService.get(modifiedUserId);
		FirstContactSurveyAuditHistory firstContactSurveyAuditHistory = new FirstContactSurveyAuditHistory();
		firstContactSurveyAuditHistory.setSurveyId(objectId);
		firstContactSurveyAuditHistory.setStateStudentIdentifier(stateStudentId);
		firstContactSurveyAuditHistory.setStudentFirstName(studentFirstName);
		firstContactSurveyAuditHistory.setStudentLastName(studentLastName);
		firstContactSurveyAuditHistory.setDomainAuditHistoryId(domainAuditHistoryId);
		firstContactSurveyAuditHistory.setModifiedUser(modifiedUserId);
		firstContactSurveyAuditHistory.setEventName(eventName);
		firstContactSurveyAuditHistory.setStudentId(studentId);
		firstContactSurveyAuditHistory.setSurveyStatusBeforeEdit(surveyStatusBeforeEdit);
		firstContactSurveyAuditHistory.setSurveyStatusAfterEdit(surveyStatusAfterEdit);
		firstContactSurveyAuditHistory.setBeforeValue(beforeValue);
		firstContactSurveyAuditHistory.setCurrentValue(currentValue);
		firstContactSurveyAuditHistory.setModifiedUserName(user==null ? null:user.getUserName());
		firstContactSurveyAuditHistory.setModifiedUserFirstName(user==null ? null:user.getFirstName());
		firstContactSurveyAuditHistory.setModifiedUserLastName(user==null ? null:user.getSurName());
		firstContactSurveyAuditHistory.setModifiedUserEducatorIdentifier(user==null ? null:user.getUniqueCommonIdentifier());
		userAuditTrailHistoryMapperDao.insertSurvey(firstContactSurveyAuditHistory);
		
		logger.trace("value inserted in studentaudittrail table ");
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToFcsCompBandHistory(Long studentId, String studentName, String compBandSubject,
			String previousCompBand, String updatedCompBand,String modifieddate, Long surveyModifiedUserId,Long schoolYear) throws ParseException {
		FCSComplexityBandHistory fcsComplexityBandHistory = new FCSComplexityBandHistory();
		DateFormat format = new SimpleDateFormat(CommonConstants.JSON_TIME_FORMAT);
		Date surveyModifieddate = format.parse(modifieddate);
		
		fcsComplexityBandHistory.setStudentId(studentId);
		fcsComplexityBandHistory.setStudentName(studentName);
		fcsComplexityBandHistory.setCompBandSubject(compBandSubject);
		fcsComplexityBandHistory.setPreviousCompBand(previousCompBand);
		fcsComplexityBandHistory.setUpdatedCompBand(updatedCompBand);
		fcsComplexityBandHistory.setSchoolYear(schoolYear);
		fcsComplexityBandHistory.setSurveyModifiedDate(surveyModifieddate);
		fcsComplexityBandHistory.setSurveyModifiedUserId(surveyModifiedUserId);
		userAuditTrailHistoryMapperDao.insertComplexityBandHistory(fcsComplexityBandHistory);
		
		logger.trace("value inserted in fcscompbandhistory table ");
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean hasFcsReadOnlyPermission(){
		boolean hasFcsReadOnlyPermission = false;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Long stateId = getStateIdByOrgId(user.getCurrentOrganizationId());
		Authorities editPermission =  gaService.getAuthorityForCsap("EDIT_FIRST_CONTACT_SURVEY", user.getCurrentGroupsId(), 
				stateId, user.getCurrentAssessmentProgramId());
		Authorities viewPermission =  gaService.getAuthorityForCsap("VIEW_FIRST_CONTACT_SURVEY", user.getCurrentGroupsId(), 
				stateId, user.getCurrentAssessmentProgramId());
		if(viewPermission != null){
			hasFcsReadOnlyPermission = true;
		}
		if(editPermission != null){
			hasFcsReadOnlyPermission = false;
		}
		return hasFcsReadOnlyPermission;
	}
	
	private Long getStateIdByOrgId(Long organizationId) {
        List<Organization> userOrgHierarchy = new ArrayList<Organization>();
        userOrgHierarchy = organizationDao.getAllParents(organizationId);
        userOrgHierarchy.add(organizationDao.get(organizationId));
        Long stateId = null;
        for(Organization org : userOrgHierarchy){
        	if(org.getOrganizationType().getTypeCode().equals("ST")){
        		stateId = org.getId();
        	}
        }
		return stateId;
	}

	@Async
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void toggleSurveyOnCategoryChange(List<FirstContactSettings> fcsSettings) {
		for (FirstContactSettings firstContactSurveySetting : fcsSettings) {
			FirstContactSettings persistedFcs=surveySettingsDao.getFirstContactSurveySetting(firstContactSurveySetting.getOrganizationId());
			updateFirstContactSurveySettings(firstContactSurveySetting);
			toggleSurveyOnCategoryChange(persistedFcs, firstContactSurveySetting);
		}		
	}

	@Override
	public List<SurveySection> getNoOfSubSections() {
		return surveySectionDao.getSurveySubSections();
	}
}
