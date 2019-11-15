package edu.ku.cete.service.impl.student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.ComplexityBandRule;
import edu.ku.cete.domain.ComplexityBandRuleExample;
import edu.ku.cete.domain.EssentialElementLinkageTranslationValues;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.EnrollmentsRostersExample;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.student.ComplexityBandJson;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.student.survey.Survey;
import edu.ku.cete.domain.student.survey.DLMAutoRegistrationDTO;
import edu.ku.cete.domain.student.survey.StudentSurveyResponse;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabel;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ComplexityBandDao;
import edu.ku.cete.model.ComplexityBandMapper;
import edu.ku.cete.model.ComplexityBandRuleMapper;
import edu.ku.cete.model.EssentialElementLinkageTranslationValuesMapper;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.student.survey.StudentSurveyResponseDao;
import edu.ku.cete.model.student.survey.StudentSurveyResponseLabelDao;
import edu.ku.cete.model.student.survey.SurveyMapper;
import edu.ku.cete.model.student.survey.SurveySettingsDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;
/**
 * Implementation for computing the complexity band rules.
 * @author craigshatswell_sta
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ComplexityBandServiceImpl implements ComplexityBandService {

	private static final String BAND_TYPE_WRITING = "Writing";

	private static final String BAND_TYPE_SCIENCE = "Sci";

	private static final String BAND_TYPE_MATH = "Math";

	private static final String BAND_TYPE_ELA = "ELA";

	private static final String BAND_TYPE_COMMUNICATION = "Communication";

	private static final Logger logger = LoggerFactory.getLogger(
			ComplexityBandServiceImpl.class);
	
	@Autowired
	private ComplexityBandMapper bandDao;
	
	@Autowired
	private ComplexityBandRuleMapper ruleDao;
	
	@Autowired
	private StudentDao studentDao;

	@Autowired
	private StudentSurveyResponseDao studentSurveyResponseDao;
	
	@Autowired
	private StudentSurveyResponseLabelDao studentSurveyResponseLabelDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CategoryTypeDao categoryTypeDao;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private EnrollmentDao enrollmentDao;
	
	@Autowired
	private EnrollmentsRostersDao enrollmentRostersDao;
	
	@Autowired
	private RosterDao rosterDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private OperationalTestWindowService operationalTestWindowService;
	
	@Autowired
	private EssentialElementLinkageTranslationValuesMapper eelTranslationValuesMapper;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private SurveyMapper surveyDao;
	
	@Autowired
	private SurveySettingsDao surveySettingsDao;
	
	 @Autowired
	 private DomainAuditHistoryMapper domainAuditHistoryDao;
	
	@Value("${autoregistration.subject.math.name}")
	private String mathCode;
	
	@Value("${autoregistration.subject.reading.name}")
	private String elaCode;
	
	@Value("${session.name.identifier}")
	private String sessionNameIdentifier;
	
	@Value("${last.name.length}")
	private int lastNameLength;
	
	@Value("${collection.name.length}")
	private int collectionNameLength;
	
	@Value("${linkage.name.length}")
	private int linkageNameLength;
	
	@Value("${session.name.date.format}")
	private String sessionNameDateFormat;
			
	@Value("${ela.session.limit}")
	private Integer elaSessionLimit;
	
	@Value("${math.session.limit}")
	private Integer mathSessionLimit;
	
	private final static String DASH = "-"; 
	
	private Long calculateMathBand(Survey survey, List<StudentSurveyResponseLabel> responses, User user){
		Long q543ResponseId = null;
		Long q545ResponseId = null;
		Long q547ResponseId = null;
		Long q548ResponseId = null;
		Long q543Id = null;
		Long q545Id = null;
		Long q547Id = null;
		Long q548Id = null;
		
		
		for (StudentSurveyResponseLabel label : responses){
			if (label.getLabelNumber().equals("Q543") && label.isActiveFlag()){
				q543Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q543ResponseId = response.getSurveyResponseId();
			}
			if (label.getLabelNumber().equals("Q545") && label.isActiveFlag()){
				q545Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q545ResponseId = response.getSurveyResponseId();
			}
			if (label.getLabelNumber().equals("Q547") && label.isActiveFlag()){
				q547Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q547ResponseId = response.getSurveyResponseId();
			}
			if (label.getLabelNumber().equals("Q548") && label.isActiveFlag()){
				q548Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q548ResponseId = response.getSurveyResponseId();
			}
		}

		ComplexityBandRuleExample example = new ComplexityBandRuleExample();
		example.createCriteria().andRuleLike("%\"label\":"+q543Id+",\"responseid\":"+q543ResponseId+"%");
		example.or().andRuleLike("%\"label\":"+q545Id+",\"responseid\":"+q545ResponseId+"%");
		example.or().andRuleLike("%\"label\":"+q547Id+",\"responseid\":"+q547ResponseId+"%");
		example.or().andRuleLike("%\"label\":"+q548Id+",\"responseid\":"+q548ResponseId+"%");
		List<RuleAndId> rulesList = getRulesList(example);
		Long bandId = null;
		for (RuleAndId rni : rulesList){
			if (rni.getRule().size() == 2){
				if (q547ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &
						q548ResponseId.equals(rni.getRule().get(1).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				}
			}else if (rni.getRule().size() == 3){
				if (q545ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &
						q547ResponseId.equals(rni.getRule().get(1).get("responseid").asLong()) &
						q548ResponseId.equals(rni.getRule().get(2).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				}
			}else if (rni.getRule().size() == 4){
				if (q543ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &
						q545ResponseId.equals(rni.getRule().get(1).get("responseid").asLong()) &
						q547ResponseId.equals(rni.getRule().get(2).get("responseid").asLong())&
						q548ResponseId.equals(rni.getRule().get(3).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				}
			}
		}
		if (bandId != null){
			setBandOnStudent(survey.getStudentId(), bandId, user, BAND_TYPE_MATH);
		}else{
			logger.debug("!@#***There was a error in calculating the math complexity band for surveyid: "+survey.getId());
		}
		
		
		return bandId;
	}
	
	private Long calculateCommBand(Survey survey, List<StudentSurveyResponseLabel> responses, User user) {
		Long q36ResponseId = null;
		Long q37ResponseId = null;
		Long q39ResponseId = null;
		Long q40ResponseId = null;
		Long q43ResponseId = null;
		Long q44ResponseId = null;
		StudentSurveyResponseLabel q36Label = null;
		StudentSurveyResponseLabel q37Label = null;
		StudentSurveyResponseLabel q39Label = null;
		StudentSurveyResponseLabel q40Label = null;
		StudentSurveyResponseLabel q43Label = null;
		StudentSurveyResponseLabel q44Label = null;
		
		for (StudentSurveyResponseLabel label : responses){
			if (label.getLabelNumber().equals("Q36") && label.isActiveFlag()){
				q36Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q36ResponseId = response.getSurveyResponseId();
			} else if (label.getLabelNumber().equals("Q37") && label.isActiveFlag()){
				q37Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q37ResponseId = response.getSurveyResponseId();
			} else if (label.getLabelNumber().equals("Q39") && label.isActiveFlag()){
				q39Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q39ResponseId = response.getSurveyResponseId();
			} else if (label.getLabelNumber().equals("Q40") && label.isActiveFlag()){
				q40Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q40ResponseId = response.getSurveyResponseId();
			} else if (label.getLabelNumber().equals("Q43") && label.isActiveFlag()){
				q43Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q43ResponseId = response.getSurveyResponseId();
			} else if (label.getLabelNumber().equals("Q44") && label.isActiveFlag()){
				q44Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q44ResponseId = response.getSurveyResponseId();
			}
		}

		ComplexityBandRuleExample example = new ComplexityBandRuleExample();
		
		boolean checkQ37 = false;
		boolean checkQ40 = false;
		boolean checkQ44 = false;
		
		if (q36Label != null) {
			example.or().andRuleLike("%\"label\":"+q36Label.getSurveyLabelId()+",\"responseid\":"+q36ResponseId+"%");
			if ("Yes".equalsIgnoreCase(q36Label.getSurveyResponseValue())) {
				if (q37Label != null) {
					checkQ37 = true;
					example.or().andRuleLike("%\"label\":"+q37Label.getSurveyLabelId()+",\"responseid\":"+q37ResponseId+"%");
				} else {
					logger.debug("Survey id " + survey.getId() + " (for student id " + survey.getStudentId() + ") has Q36 answered as Yes, but no Q37 answer.");
				}
			}
		}
		
		if (q39Label != null) {
			example.or().andRuleLike("%\"label\":"+q39Label.getSurveyLabelId()+",\"responseid\":"+q39ResponseId+"%");
			if ("Yes".equalsIgnoreCase(q39Label.getSurveyResponseValue())) {
				if (q40Label != null) {
					checkQ40 = true;
					example.or().andRuleLike("%\"label\":"+q40Label.getSurveyLabelId()+",\"responseid\":"+q40ResponseId+"%");
				} else {
					logger.debug("Survey id " + survey.getId() + " (for student id " + survey.getStudentId() + ") has Q39 answered as Yes, but no Q40 answer.");
				}
			}
		}
		
		if (q43Label != null) {
			example.or().andRuleLike("%\"label\":"+q43Label.getSurveyLabelId()+",\"responseid\":"+q43ResponseId+"%");
			if ("Yes".equalsIgnoreCase(q43Label.getSurveyResponseValue())) {
				if (q44Label != null) {
					checkQ44 = true;
					example.or().andRuleLike("%\"label\":"+q44Label.getSurveyLabelId()+",\"responseid\":"+q44ResponseId+"%");
				} else {
					logger.debug("Survey id " + survey.getId() + " (for student id " + survey.getStudentId() + ") has Q43 answered as Yes, but no Q44 answer.");
				}
			}
		}
		
		boolean tryToFindARule = (q36Label != null || q39Label != null || q43Label != null);
		Long bandId = null;
		
		// if they're all null, all of the rule records would be returned, which would be BAD
		if (tryToFindARule) {
			List<RuleAndId> rulesList = getRulesList(example);
			for (RuleAndId rni : rulesList){
				if (rni.getRule().size() == 2 && checkQ37 && q37ResponseId != null &&
						q36ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &&
						q37ResponseId.equals(rni.getRule().get(1).get("responseid").asLong())) {
					bandId = rni.getComplexityBandId();
					break;
				} else if (rni.getRule().size() == 3 && checkQ40 && q40ResponseId != null &&
							q36ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &&
							q39ResponseId.equals(rni.getRule().get(1).get("responseid").asLong()) &&
							q40ResponseId.equals(rni.getRule().get(2).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				} else if (rni.getRule().size() == 3 && q43ResponseId != null &&
							q36ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &&
							q39ResponseId.equals(rni.getRule().get(1).get("responseid").asLong()) &&
							q43ResponseId.equals(rni.getRule().get(2).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				} else if (rni.getRule().size() == 4 && checkQ44 && q44ResponseId != null &&
							q36ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &&
							q39ResponseId.equals(rni.getRule().get(1).get("responseid").asLong()) &&
							q43ResponseId.equals(rni.getRule().get(2).get("responseid").asLong()) &&
							q44ResponseId.equals(rni.getRule().get(3).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				}
			}
		}
		
		if (bandId != null){
			setBandOnStudent(survey.getStudentId(), bandId, user, BAND_TYPE_COMMUNICATION);
		} else {
			if (tryToFindARule) { // tried and failed
				logger.debug("!@#***There was a error in calculating the communication complexity band for surveyid "
						+ "(no retrieved rules matched survey answers): "+survey.getId());
			} else {
				logger.debug("!@#***There was a error in calculating the communication complexity band for surveyid "
						+ "(no rules retrieved): "+survey.getId());
			}
		}
		return bandId;
	}

	private Long calculateELABand(Survey survey, List<StudentSurveyResponseLabel> responses, User user) {
		Long q52ResponseId = null;
		Long q51_1ResponseId = null;
		StudentSurveyResponseLabel q52Label = null;
		StudentSurveyResponseLabel q51_1Label = null;
		for (StudentSurveyResponseLabel label : responses){
			if (label.getLabelNumber().equals("Q511") && label.isActiveFlag()){
				q51_1Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q51_1ResponseId = response.getSurveyResponseId();
			}
			if (label.getLabelNumber().equals("Q52") && label.isActiveFlag()){
				q52Label = label;
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q52ResponseId = response.getSurveyResponseId();
			}
		}
		Long q52Id = q52Label.getSurveyLabelId();
		Long q51_1Id = q51_1Label.getSurveyLabelId();
		ComplexityBandRuleExample example = new ComplexityBandRuleExample();
		example.createCriteria().andRuleLike("%\"label\":"+q52Id+",\"responseid\":"+q52ResponseId+"%");
		example.or().andRuleLike("%\"label\":"+q51_1Id+",\"responseid\":"+q51_1ResponseId+"%");
		List<RuleAndId> rulesList = getRulesList(example);
		Long bandId = null;
		for (RuleAndId rni : rulesList){
			if (rni.getRule().size() == 1){
				if (q52ResponseId.equals(rni.getRule().get(0).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				}
			}else if (rni.getRule().size() == 2){
				if (q51_1ResponseId.equals(rni.getRule().get(0).get("responseid").asLong()) &
						q52ResponseId.equals(rni.getRule().get(1).get("responseid").asLong())){
					bandId = rni.getComplexityBandId();
					break;
				}
			}
		}
		if (bandId != null){
			setBandOnStudent(survey.getStudentId(), bandId, user, BAND_TYPE_ELA);
		}else{
			logger.debug("!@#***There was a error in calculating the ela complexity band for surveyid: "+survey.getId());
		}
		return bandId;
	}

	private boolean areAllMathQuestionsComplete(List<StudentSurveyResponseLabel> responses){
		boolean q54_8Complete = false;
		boolean q54_7Complete = false;
		boolean q54_5Complete = false;
		boolean q54_3Complete = false;
		for (StudentSurveyResponseLabel label : responses){
			if (label.getLabelNumber().equals("Q543") && label.isActiveFlag()){
				q54_3Complete = true;
			}
			if (label.getLabelNumber().equals("Q545") && label.isActiveFlag()){
				q54_5Complete = true;
			}
			if (label.getLabelNumber().equals("Q547") && label.isActiveFlag()){
				q54_7Complete = true;
			}
			if (label.getLabelNumber().equals("Q548") && label.isActiveFlag()){
				q54_8Complete = true;
			}
		}
		
		return q54_3Complete & q54_5Complete & q54_7Complete & q54_8Complete;
	}
	
	private boolean areAllCommQuestionsComplete(List<StudentSurveyResponseLabel> responses) {
		boolean q36Complete = false;
		boolean q37Complete = false;
		boolean q39Complete = false;
		boolean q40Complete = false;
		boolean q43Complete = false;
		boolean q44Complete = false;
		boolean q43Answer = false;
		for (StudentSurveyResponseLabel label : responses){
			if (label.getLabelNumber().equals("Q36") && label.isActiveFlag()){
				q36Complete = true;
			}
			if (label.getLabelNumber().equals("Q37") && label.isActiveFlag()){
				q37Complete = true;
			}
			if (label.getLabelNumber().equals("Q39") && label.isActiveFlag()){
				q39Complete = true;
			}
			if (label.getLabelNumber().equals("Q40") && label.isActiveFlag()){
				q40Complete = true;
			}
			if (label.getLabelNumber().equals("Q43") && label.isActiveFlag()){
				q43Complete = true;
				String response = studentSurveyResponseDao.findResponseValueByResponseId(label.getStudentSurveyResponseId());
				q43Answer = response.equalsIgnoreCase("YES") ? true : false;
			}
			if (label.getLabelNumber().equals("Q44") && label.isActiveFlag()){
				q44Complete = true;
			}
		}

		return (q36Complete & q37Complete ) 
				| (q36Complete & q39Complete & q40Complete) 
				| (q36Complete & q39Complete & q43Complete & !q43Answer) 
				| (q36Complete & q39Complete & q43Complete & q44Complete);
	}

	private boolean areAllELAQuestionsComplete(List<StudentSurveyResponseLabel> responses) {
		boolean q52Complete = false;
		boolean q51_1Complete = false;

		for (StudentSurveyResponseLabel label : responses){
			if (label.getLabelNumber().equals("Q511") && label.isActiveFlag()){
				q51_1Complete = true;
			}
			if (label.getLabelNumber().equals("Q52") && label.isActiveFlag()){
				q52Complete = true;
			}
		}

		return q52Complete & q51_1Complete;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> calculateFinalComplexityBandsForSurvey(Long surveyId, User user){
		logger.trace("Entering calculateFinalComplexityBandsForSurvey");
		Map<String, Object> processResult = new HashMap<String, Object>();
		List<StudentSurveyResponseLabel> responses = studentSurveyResponseLabelDao.selectSurveyResult(surveyId);
		Survey survey = studentSurveyResponseDao.findBySurveyId(surveyId);
		Category surveyStatus = categoryDao.selectByPrimaryKey(survey.getStatus());
		Student student = studentDao.findById(survey.getStudentId());
		
		// complexity bands before FCS submittion
		String jsonBeforeUpdate = null;
		String jsonAfterUpdate = null;
		Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();

		ComplexityBandJson complexityBandJson = bandDao.getCompBandsForJson(surveyId,currentSchoolYear);
		if(complexityBandJson != null) {
			jsonBeforeUpdate = complexityBandJson.buildjsonString();
		}
		
		Long elaBandId = student.getElaBandId();
		Long commBandId = student.getCommBandId();
		Long mathBandId = student.getMathBandId();
		Long sciBandId = student.getSciBandId();
	
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long organizationId = userDetails.getUser().getContractingOrgId();
		FirstContactSettings firstContactSettings = surveySettingsDao.getFirstContactSurveySetting(organizationId);
		
		calculateComplexityBandsForSurvey(surveyId, user,true);
		student = studentDao.findById(survey.getStudentId());
		elaBandId = student.getElaBandId();
		commBandId = student.getCommBandId();
		mathBandId = student.getMathBandId();
		sciBandId = student.getSciBandId();

		Long finalELABandId = null;
		Long finalMathBandId = null;
		Long finalSciBandId = null;
		Long writingBandId = null;
		//only process the final bands when the survey has been completed
		if (surveyStatus != null && surveyStatus.getCategoryCode().equals("COMPLETE")){
			if (firstContactSettings != null && firstContactSettings.getElaFlag()) {
				if(areAllELAQuestionsComplete(responses) && areAllCommQuestionsComplete(responses)){
					if (elaBandId == null){
						String errorMsg = "Unable to calculate final ela complexity band because ela band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
					
						return processResult;
					}
					if (commBandId == null){
						String errorMsg = "Unable to calculate final ela complexity band because comm band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;
					}
					finalELABandId = calculateFinalELABand(survey.getStudentId(), elaBandId, commBandId, user);
					if (finalELABandId == null){
						String errorMsg = "Unable to calculate complexity band because final ela band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;
					}
				}else{
					String errorMsg = "The survey has been marked complete but the responses for ELA are not all complete.  surveyId: "+surveyId;
					processResult.put("success", false);
					processResult.put("reason", errorMsg);
					processResult.put("firstname", student.getLegalFirstName());
					processResult.put("lastname", student.getLegalLastName());
					processResult.put("studentid", student.getId());
					logger.debug(errorMsg);
				}
				
				if(finalELABandId != null) {
					if(isWritingQuestionAnswred(responses)) {
						writingBandId = calculateWritingBand(survey, responses, user);
						if(writingBandId == null) {
							String errorMsg = "Unable to calculate complexity band because writing band was null for surveyid: "+survey.getId();
							logger.debug(errorMsg);
							processResult.put("success", false);
							processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
							return processResult;
						}
					}
				} 
			}
			if (firstContactSettings != null && firstContactSettings.getMathFlag()) {
				if (areAllMathQuestionsComplete(responses) && areAllCommQuestionsComplete(responses)){
					if (mathBandId == null){
						String errorMsg = "Unable to calculate final math complexity band because math band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;
					}
					if (commBandId == null){
						String errorMsg = "Unable to calculate final math complexity band because comm band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;
					}
					finalMathBandId = calculateFinalMathBand(survey.getStudentId(), mathBandId, commBandId, user);
					if (finalMathBandId == null){
						String errorMsg = "Unable to calculate complexity band because final math band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;
					}
				}else{
					String errorMsg = "The survey has been marked complete but the responses for Math are not all complete.  surveyId: "+surveyId;
					processResult.put("success", false);
					processResult.put("reason", errorMsg);
					processResult.put("firstname", student.getLegalFirstName());
					processResult.put("lastname", student.getLegalLastName());
					processResult.put("studentid", student.getId());
					logger.debug(errorMsg);
				
				}
			}
			if(firstContactSettings != null && firstContactSettings.getScienceFlag()) {
				if (areAllSciQuestionsComplete(responses) && areAllCommQuestionsComplete(responses)){
					if (sciBandId == null){
						String errorMsg = "Unable to calculate final Science complexity band because Science band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;					
					}
					if (commBandId == null){
						String errorMsg = "Unable to calculate final Science complexity band because comm band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;					
					}
					finalSciBandId = calculateFinalSciBand(survey.getStudentId(), sciBandId, commBandId, user);
					if (finalSciBandId == null){
						String errorMsg = "Unable to calculate complexity band because final Science band was null for surveyid: "+survey.getId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						return processResult;
					}
					
				} else {
					String errorMsg = "The survey has been marked complete but the responses for Science are not all complete.  surveyId: "+surveyId;
					processResult.put("success", false);
					processResult.put("reason", errorMsg);
					processResult.put("firstname", student.getLegalFirstName());
					processResult.put("lastname", student.getLegalLastName());
					processResult.put("studentid", student.getId());
					logger.debug(errorMsg);
				}
			}
		}
		responses = null;
		complexityBandJson = bandDao.getCompBandsForJson(surveyId,currentSchoolYear);
		if(complexityBandJson != null) {
			jsonAfterUpdate = complexityBandJson.buildjsonString();
		}
		insertIntoDomainAuditHistory(surveyId, EventTypeEnum.FCS_COMP_BANDS.getCode(), SourceTypeEnum.MANUAL.getCode(),
				jsonBeforeUpdate, jsonAfterUpdate);
		logger.trace("Leaving calculateFinalComplexityBandsForSurvey");
		return processResult;
	}
	
	private Long calculateWritingBand(Survey survey,
			List<StudentSurveyResponseLabel> responses, User user) {
		Long q500_ResponseId = null;
		Long q500_Id = null;
		for(StudentSurveyResponseLabel label: responses) {
			if("Q500".equalsIgnoreCase(label.getLabelNumber()) && label.isActiveFlag()) {
				q500_Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q500_ResponseId = response.getSurveyResponseId();
			}
		}
		ComplexityBandRuleExample example = new ComplexityBandRuleExample();
		example.createCriteria().andRuleLike("%\"label\":"+q500_Id+",\"responseid\":"+q500_ResponseId+"%");
		List<RuleAndId> rulesList = getRulesList(example);
		Long bandId = null;
		Map<Long, Long> complexityResponsesAndBandsMap = new HashMap<Long, Long>();
		for (RuleAndId rni : rulesList){
			complexityResponsesAndBandsMap.put(rni.getRule().get(0).get("responseid").asLong(), rni.getComplexityBandId());
		}
		if(complexityResponsesAndBandsMap != null && !complexityResponsesAndBandsMap.isEmpty()) {
			if(complexityResponsesAndBandsMap.containsKey(q500_ResponseId)) {
				bandId = complexityResponsesAndBandsMap.get(q500_ResponseId);
			}
		}
		
		if(bandId != null) {
			setBandOnStudent(survey.getStudentId(), bandId, user, BAND_TYPE_WRITING);
		} else {
			logger.debug("!@#***There was a error in calculating the writing complexity band for surveyid: "+survey.getId());
		}
		return bandId;
	}	

	private boolean isWritingQuestionAnswred(
			List<StudentSurveyResponseLabel> responses) {
		for (StudentSurveyResponseLabel label : responses){
			if ("Q500".equals(label.getLabelNumber()) && label.isActiveFlag()){
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> calculateComplexityBandsForSurvey(Long surveyId, User user,boolean isCalculatingCommBand){
		logger.trace("Entering calculateComplexityBandsForSurvey");
		
		Map<String, Object> processResult = new HashMap<String, Object>();
		List<StudentSurveyResponseLabel> responses = studentSurveyResponseLabelDao.selectSurveyResult(surveyId);
		Survey survey = studentSurveyResponseDao.findBySurveyId(surveyId);
		Long elaBandId = null;
		Long commBandId = null;
		Long mathBandId = null;
		Long sciBandId = null;
		if(areAllELAQuestionsComplete(responses)){
			elaBandId = calculateELABand(survey, responses, user);
			if (elaBandId == null){
				String errorMsg = "Unable to calculate complexity band because ela band was null for surveyid: "+survey.getId();
				logger.debug(errorMsg);
				processResult.put("success", false);
				processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
				return processResult;
			}
		}
		if(isCalculatingCommBand) {
			if(areAllCommQuestionsComplete(responses)){
				commBandId = calculateCommBand(survey, responses, user);
				if (commBandId == null){
					String errorMsg = "Unable to calculate complexity band because communication band was null for surveyid: "+survey.getId();
					logger.debug(errorMsg);
					processResult.put("success", false);
					processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
					return processResult;
				}
			}
		}
		
		if (areAllMathQuestionsComplete(responses)){
			mathBandId = calculateMathBand(survey, responses, user);
			if (mathBandId == null){
				String errorMsg = "Unable to calculate complexity band because math band was null for surveyid: "+survey.getId();
				logger.debug(errorMsg);
				processResult.put("success", false);
				processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
				return processResult;
			}
		}
		if(areAllSciQuestionsComplete(responses)){
			sciBandId = calculateScienceBand(survey, responses, user);
			if (sciBandId == null){
				String errorMsg = "Unable to calculate complexity band because Science band was null for surveyid: "+survey.getId();
				logger.debug(errorMsg);
				processResult.put("success", false);
				processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
				return processResult;
			}
			
		}
		
		logger.trace("Leaving calculateComplexityBandsForSurvey");
		return processResult;
	}
	
	
	private Long calculateScienceBand(Survey survey, List<StudentSurveyResponseLabel> responses, User user) {
		Long q212_1ResponseId = null;
		Long q212_2ResponseId = null;
		Long q212_5ResponseId = null;
		Long q212_1Id = null;
		Long q212_2Id = null;
		Long q212_5Id = null;
		
		
		for (StudentSurveyResponseLabel label : responses){
			if (StringUtils.equalsIgnoreCase(label.getLabelNumber(), "Q212_1") && label.isActiveFlag()){
				q212_1Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q212_1ResponseId = response.getSurveyResponseId();
			}
			if (StringUtils.equalsIgnoreCase(label.getLabelNumber(), "Q212_2") && label.isActiveFlag()){
				q212_2Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q212_2ResponseId = response.getSurveyResponseId();
			}
			if (StringUtils.equalsIgnoreCase(label.getLabelNumber(),"Q212_5") && label.isActiveFlag()){
				q212_5Id = label.getSurveyLabelId();
				StudentSurveyResponse response = label.getStudentSurveyResponse();
				q212_5ResponseId = response.getSurveyResponseId();
			}
		}

		ComplexityBandRuleExample example = new ComplexityBandRuleExample();
		example.createCriteria().andRuleLike("%\"label\":"+q212_1Id+",\"responseid\":"+q212_1ResponseId+"%");
		example.or().andRuleLike("%\"label\":"+q212_2Id+",\"responseid\":"+q212_2ResponseId+"%");
		example.or().andRuleLike("%\"label\":"+q212_5Id+",\"responseid\":"+q212_5ResponseId+"%");		
		List<RuleAndId> rulesList = getRulesList(example);
		Long bandId = null;
		Map<Long, Long> complexityResponsesAndBandsMap = new HashMap<Long, Long>();
		for (RuleAndId rni : rulesList){
			complexityResponsesAndBandsMap.put(rni.getRule().get(0).get("responseid").asLong(), rni.getComplexityBandId());			
		}
		if(complexityResponsesAndBandsMap != null && !complexityResponsesAndBandsMap.isEmpty()) {
			if(complexityResponsesAndBandsMap.containsKey(q212_5ResponseId)) {
				bandId = complexityResponsesAndBandsMap.get(q212_5ResponseId);			
			} else if(complexityResponsesAndBandsMap.containsKey(q212_2ResponseId)) {
				bandId = complexityResponsesAndBandsMap.get(q212_2ResponseId);
			} else if(complexityResponsesAndBandsMap.containsKey(q212_1ResponseId)){
				bandId = complexityResponsesAndBandsMap.get(q212_1ResponseId);
			}
		}		
		if (bandId != null){
			setBandOnStudent(survey.getStudentId(), bandId, user, BAND_TYPE_SCIENCE);
		}else{
			logger.debug("!@#***There was a error in calculating the Science complexity band for surveyid: "+survey.getId());
		}
		
		
		return bandId;
	}

	private boolean areAllSciQuestionsComplete(List<StudentSurveyResponseLabel> responses) {
		boolean q212_1Complete = false;
		boolean q212_2Complete = false;
		boolean q212_5Complete = false;
		for (StudentSurveyResponseLabel label : responses){
			if (StringUtils.equalsIgnoreCase(label.getLabelNumber(), "Q212_1") && label.isActiveFlag()){
				q212_1Complete = true;
			}
			if (StringUtils.equalsIgnoreCase(label.getLabelNumber(), "Q212_2") && label.isActiveFlag()){
				q212_2Complete = true;
			}
			if (StringUtils.equalsIgnoreCase(label.getLabelNumber(), "Q212_5") && label.isActiveFlag()){
				q212_5Complete = true;
			}
		}
		return q212_1Complete && q212_2Complete && q212_5Complete;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> calculateComplexityBandsForBatch(Long surveyId, User user){
		logger.trace("Entering calculateComplexityBandsForBatch");
		Map<String, Object> processResult = new HashMap<String, Object>();
		Survey survey = studentSurveyResponseDao.findBySurveyId(surveyId);
		Category surveyStatus = categoryDao.selectByPrimaryKey(survey.getStatus());
		
		//only process the final bands when the survey has been completed
		if (surveyStatus != null && surveyStatus.getCategoryCode().equals("COMPLETE")) {
			Long elaBandId = null;
			Long commBandId = null;
			Long mathBandId = null;
			Long finalELABandId = null;
			Long finalMathBandId = null;
			List<StudentSurveyResponseLabel> responses = studentSurveyResponseLabelDao.selectSurveyResult(surveyId);
			
			if(areAllELAQuestionsComplete(responses)){
				elaBandId = calculateELABand(survey, responses, user);
				if (elaBandId == null){
					String errorMsg = "Unable to calculate complexity band because ela band was null for surveyid: "+survey.getId();
					logger.debug(errorMsg);
					processResult.put("success", false);
					processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
					return processResult;
				}
			}
			if(areAllCommQuestionsComplete(responses)){
				commBandId = calculateCommBand(survey, responses, user);
				if (commBandId == null){
					String errorMsg = "Unable to calculate complexity band because communication band was null for surveyid: "+survey.getId();
					logger.debug(errorMsg);
					processResult.put("success", false);
					processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
					return processResult;
				}
			}
			
			if (areAllMathQuestionsComplete(responses)){
				mathBandId = calculateMathBand(survey, responses, user);
				if (mathBandId == null){
					String errorMsg = "Unable to calculate complexity band because math band was null for surveyid: "+survey.getId();
					logger.debug(errorMsg);
					processResult.put("success", false);
					processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
					return processResult;
				}
			}
		
			Student student = studentDao.findById(survey.getStudentId());
			if (areAllELAQuestionsComplete(responses) && areAllCommQuestionsComplete(responses)){
				//does student already have finalELABand calculated?
				Long previousFinalELABandId = student.getFinalElaBandId();
				finalELABandId = calculateFinalELABand(survey.getStudentId(), elaBandId, commBandId, user);	
				if (finalELABandId == null){
					String errorMsg = "Unable to calculate complexity band because final ela band was null for surveyid: "+survey.getId();
					logger.debug(errorMsg);
					processResult.put("success", false);
					processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
					return processResult;
				}
				//if the final ela band has not changed so we do not need to 
				//create a new test session unless it is a batch process
				boolean skipToProcessMath = false;
				boolean isNewBand = !finalELABandId.equals(previousFinalELABandId);
				if (isNewBand){
					Enrollment enrollment = getEnrollment(survey.getStudentId(), user, elaCode);
					if (enrollment == null){
						//no enrollment matched don't process further
						String errorMsg = "Unable to find a current enrollment for student id: "+survey.getStudentId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsg);
						processResult.put("firstname", student.getLegalFirstName());
						processResult.put("lastname", student.getLegalLastName());
						processResult.put("studentid", student.getId());
						skipToProcessMath = true;
					}else{
						EssentialElementLinkageTranslationValues eel = null;
						if (previousFinalELABandId != null){
							Category previousFinalBand = categoryDao.selectByPrimaryKey(previousFinalELABandId);
							eel = getEssentialElementLinkage(previousFinalBand.getCategoryName());
						}
						List<TestSession> sessions = 
								testSessionService.selectForStudentGradeSubjectAndPartialName(
										survey.getStudentId(), 
										enrollment.getCurrentGradeLevelCode(),
										elaCode, 
										createSessionNameForSearch(student, "%", eel),
										elaSessionLimit);
						if (sessions != null && !sessions.isEmpty()){
							checkSessionAndUnenrollIfNeeded(sessions, student, survey, user);
						}
					}
					if (!skipToProcessMath){
						//check for existing sessions in the newly calculated band
						Category finalBand = categoryDao.selectByPrimaryKey(finalELABandId);
						EssentialElementLinkageTranslationValues eel = getEssentialElementLinkage(finalBand.getCategoryName());
						List<TestSession> existingSessions = 
								testSessionService.selectForStudentGradeSubjectAndPartialName(
										survey.getStudentId(), 
										enrollment.getCurrentGradeLevelCode(),
										elaCode, 
										createSessionNameForSearch(student, "%", eel),
										elaSessionLimit);					
						int elaSessionsCount = existingSessions != null ? existingSessions.size() : 0;
						int neededElaSessions = elaSessionLimit - elaSessionsCount;
						if (neededElaSessions > 0){
							List<String> collectionNames = getCollectionNamesFrom(existingSessions, student, eel.getCategoryName());
							processResult = createTestSession(finalELABandId, survey.getStudentId(), elaCode, neededElaSessions, user, collectionNames);
							if((Boolean) processResult.get("success")){
								logger.debug("Created an ELA test session for student id: "+survey.getStudentId());
							}else{
								logger.debug("Unable to create an ELA test session for student id: "+survey.getStudentId());
							}
						}else{
							String sessionNames = getExistingNames(existingSessions);
							String errorMsg = existingSessions.size() + " ELA test session(s) already existed for student id: "+survey.getStudentId() + " with the names: " + sessionNames;
							processResult.put("success", true);
							processResult.put("reason", errorMsg);
							processResult.put("firstname", student.getLegalFirstName());
							processResult.put("lastname", student.getLegalLastName());
							processResult.put("studentid", student.getId());
							logger.debug(errorMsg);
						}
					}
				}else{
					//the band didn't change so see if we need to create a session for the first time
					List<TestSession> existingSessions = null;
					EssentialElementLinkageTranslationValues eel = null;
					Enrollment enrollment = getEnrollment(survey.getStudentId(), user, elaCode);
					if (enrollment == null){
						//no enrollment matched don't process further
						String errorMsg = "Unable to create ELA test session because a current enrollment was not found for student id: "+survey.getStudentId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsg);
						processResult.put("firstname", student.getLegalFirstName());
						processResult.put("lastname", student.getLegalLastName());
						processResult.put("studentid", student.getId());						
						skipToProcessMath = true;
					}else{
						Category finalBand = categoryDao.selectByPrimaryKey(finalELABandId);
						eel = getEssentialElementLinkage(finalBand.getCategoryName());
						existingSessions = 
								testSessionService.selectForStudentGradeSubjectAndPartialName(
										survey.getStudentId(), 
										enrollment.getCurrentGradeLevelCode(),
										elaCode, 
										createSessionNameForSearch(student, "%", eel),
										elaSessionLimit);
					}
					if (!skipToProcessMath){
						int elaSessionsCount = existingSessions != null ? existingSessions.size() : 0;
						int neededElaSessions = elaSessionLimit - elaSessionsCount;
						if (neededElaSessions > 0){
							List<String> collectionNames = getCollectionNamesFrom(existingSessions, student, eel.getCategoryName());
							processResult = createTestSession(finalELABandId, survey.getStudentId(), elaCode, neededElaSessions, user, collectionNames);
							if((Boolean) processResult.get("success")){
								logger.debug("Created an ELA test session for student id: "+survey.getStudentId());
							}else{
								logger.debug("Unable to create an ELA test session for student id: "+survey.getStudentId());
							}
						}else{
							String sessionNames = getExistingNames(existingSessions);
							String errorMsg = existingSessions.size() + " ELA test session(s) already existed for student id: "+survey.getStudentId() + " with the names: " + sessionNames;
							processResult.put("success", true);
							processResult.put("reason", errorMsg);
							processResult.put("firstname", student.getLegalFirstName());
							processResult.put("lastname", student.getLegalLastName());
							processResult.put("studentid", student.getId());
							logger.debug(errorMsg);
						}
					}
				}
			}else{
				String errorMsg = "The survey has been marked complete but the responses for ELA are not all complete.  surveyId: "+surveyId;
				processResult.put("success", false);
				processResult.put("reason", errorMsg);
				processResult.put("firstname", student.getLegalFirstName());
				processResult.put("lastname", student.getLegalLastName());
				processResult.put("studentid", student.getId());
				logger.debug(errorMsg);
			}
			
			if (areAllMathQuestionsComplete(responses) && areAllCommQuestionsComplete(responses)){
				//does student already have finalMathBand calculated?
				Long previousFinalMathBandId = student.getFinalMathBandId();

				finalMathBandId = calculateFinalMathBand(survey.getStudentId(), mathBandId, commBandId, user);
				if (finalMathBandId == null){
					String errorMsg = "Unable to calculate complexity band because final math band was null for surveyid: "+survey.getId();
					logger.debug(errorMsg);
					processResult.put("success", false);
					processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
					return processResult;
				}
				//if the final math band has not changed so we do not need to 
				//create a new test session unless it is a batch process
				boolean skip = false;
				boolean isNewBand = !finalMathBandId.equals(previousFinalMathBandId);
				if (isNewBand){
					Enrollment enrollment = getEnrollment(survey.getStudentId(), user, mathCode);
					if (enrollment == null){
						//no enrollment matched don't process further
						String errorMsg = "Unable to find a current enrollment for student id: "+survey.getStudentId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						skip = true;
					}else{
						EssentialElementLinkageTranslationValues eel = null;
						if (previousFinalMathBandId != null){
							Category previousFinalBand = categoryDao.selectByPrimaryKey(previousFinalMathBandId);
							eel = getEssentialElementLinkage(previousFinalBand.getCategoryName());
						}
						List<TestSession> sessions = 
								testSessionService.selectForStudentGradeSubjectAndPartialName(
										survey.getStudentId(), 
										enrollment.getCurrentGradeLevelCode(),
										mathCode, 
										createSessionNameForSearch(student, "%", eel),
										mathSessionLimit);
						if (sessions != null && !sessions.isEmpty()){
							checkSessionAndUnenrollIfNeeded(sessions, student, survey, user);
						}
					}
					if (!skip){
						//check for existing sessions in the newly calculated band
						Category finalBand = categoryDao.selectByPrimaryKey(finalMathBandId);
						EssentialElementLinkageTranslationValues eel = getEssentialElementLinkage(finalBand.getCategoryName());
						List<TestSession> existingSessions = 
								testSessionService.selectForStudentGradeSubjectAndPartialName(
										survey.getStudentId(), 
										enrollment.getCurrentGradeLevelCode(),
										mathCode, 
										createSessionNameForSearch(student, "%", eel),
										mathSessionLimit);					
						int mathSessionsCount = existingSessions != null ? existingSessions.size() : 0;
						int neededMathSessions = mathSessionLimit - mathSessionsCount;
						if (neededMathSessions > 0){
							List<String> collectionNames = getCollectionNamesFrom(existingSessions, student, eel.getCategoryName());
							processResult = createTestSession(finalMathBandId, survey.getStudentId(), elaCode, neededMathSessions, user, collectionNames);
							if((Boolean) processResult.get("success")){
								logger.debug("Created an Math test session for student id: "+survey.getStudentId());
							}else{
								logger.debug("Unable to create an Math test session for student id: "+survey.getStudentId());
							}
						}else{
							String sessionNames = getExistingNames(existingSessions);
							String errorMsg = existingSessions.size() + " Math test session(s) already existed for student id: "+survey.getStudentId() + " with the names: " + sessionNames;
							processResult.put("success", true);
							processResult.put("reason", errorMsg);
							processResult.put("firstname", student.getLegalFirstName());
							processResult.put("lastname", student.getLegalLastName());
							processResult.put("studentid", student.getId());
							logger.debug(errorMsg);
						}
					}
				}else{
					//the band didn't change so see if we need to create a session for the first time
					List<TestSession> existingSessions = null;
					EssentialElementLinkageTranslationValues eel = null;
					Enrollment enrollment = getEnrollment(survey.getStudentId(), user, mathCode);
					if (enrollment == null){
						//no enrollment matched don't process further
						String errorMsg = "Unable to create Math test session because a current enrollment was not found for student id: "+survey.getStudentId();
						logger.debug(errorMsg);
						processResult.put("success", false);
						processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
						skip = true;
					}else{
						Category finalBand = categoryDao.selectByPrimaryKey(finalMathBandId);
						eel = getEssentialElementLinkage(finalBand.getCategoryName());
						existingSessions = 
								testSessionService.selectForStudentGradeSubjectAndPartialName(
										survey.getStudentId(), 
										enrollment.getCurrentGradeLevelCode(),
										mathCode, 
										createSessionNameForSearch(student, "%", eel),
										mathSessionLimit);
					}
					if (!skip){
						int mathSessionsCount = existingSessions != null ? existingSessions.size() : 0;
						int neededMathSessions = mathSessionLimit - mathSessionsCount;
						if (neededMathSessions > 0){
							List<String> collectionNames = getCollectionNamesFrom(existingSessions, student, eel.getCategoryName());
							processResult = createTestSession(finalMathBandId, survey.getStudentId(), mathCode, neededMathSessions, user, collectionNames);
							if((Boolean) processResult.get("success")){
								logger.debug("Created an Math test session for student id: "+survey.getStudentId());
							}else{
								logger.debug("Unable to create an Math test session for student id: "+survey.getStudentId());
							}
						}else{
							String sessionNames = getExistingNames(existingSessions);
							String errorMsg = existingSessions.size() + " Math test session(s) already existed for student id: "+survey.getStudentId() + " with the names: " + sessionNames;
							processResult.put("success", true);
							processResult.put("reason", errorMsg);
							processResult.put("firstname", student.getLegalFirstName());
							processResult.put("lastname", student.getLegalLastName());
							processResult.put("studentid", student.getId());
							logger.debug(errorMsg);
						}
					}
				}
			}else{
				String errorMsg = "The survey has been marked complete but the responses for Math are not all complete.  surveyId: "+surveyId;
				processResult.put("success", false);
				processResult.put("reason", errorMsg);
				processResult.put("firstname", student.getLegalFirstName());
				processResult.put("lastname", student.getLegalLastName());
				processResult.put("studentid", student.getId());
				logger.debug(errorMsg);
			}
		} else {
			String errorMsg = "Survey is not in complete state: "+survey.getId();
			logger.debug(errorMsg);
			processResult.put("success", false);
			processResult.put("reason", errorMsgProcess(errorMsg, (String) processResult.get("reason")));
		}
		logger.trace("Leaving calculateComplexityBandsForBatch");
		return processResult;
	}
	
	private List<String> getCollectionNamesFrom(List<TestSession> existingSessions, Student student, String eelName) {
		List<String> names = new ArrayList<String>();
		String newEelName = eelName.replace(" ", "");
		for (TestSession session : existingSessions){
			String sessionName = session.getName();
			int stTrimLength = getStudentTrimLength(student);
			int eelTrimLength = getEelTrimLength(newEelName);
			String newSessionName = sessionName.replace("DLMI-"+student.getLegalLastName().substring(0, stTrimLength)+DASH, "");
			String collectionName = newSessionName.replace(DASH+newEelName.substring(0, eelTrimLength)+DASH+student.getId(), "");
			names.add(collectionName+"%");
		}
		
		return names;
	}

	private String getExistingNames(List<TestSession> existingSessions) {
		//sessions already exists
		StringBuffer sessionNames = new StringBuffer();
		for (TestSession session : existingSessions){
			sessionNames.append(session.getName());
			sessionNames.append(" ");
		}
		return sessionNames.toString();
	}

	private Long calculateFinalSciBand(Long studentId, Long sciBandId, Long commBandId, User user) {
		logger.trace("Entering calculateFinalSciBand");
		//get both the math and comm band and take the minimum of the two
		Long finalSciBandId = null;
		Category sciBand = categoryDao.selectByPrimaryKey(sciBandId);
		Category commBand = categoryDao.selectByPrimaryKey(commBandId);
		String sciComplexityBand = sciBand.getCategoryCode();
		String commComplexityBand = null;
		if(commBand != null) {
			commComplexityBand = commBand.getCategoryCode();
		}
		
		if (sciComplexityBand != null && commComplexityBand != null && 
				ComplexityBandEnum.valueOf(sciComplexityBand).ordinal() < ComplexityBandEnum.valueOf(commComplexityBand).ordinal()){
			finalSciBandId = sciBandId;
		}else{
			finalSciBandId = commBandId;
		}
		setFinalBandOnStudent(studentId, finalSciBandId, user, BAND_TYPE_SCIENCE);
		logger.trace("Leaving calculateFinalSciBand");
		return finalSciBandId;
	}
	
	private Long calculateFinalMathBand(Long studentId, Long mathBandId, Long commBandId, User user){
		logger.trace("Entering calculateFinalMathBand");
		//get both the math and comm band and take the minimum of the two
		Long finalMathBandId = null;
		Category mathBand = categoryDao.selectByPrimaryKey(mathBandId);
		Category commBand = categoryDao.selectByPrimaryKey(commBandId);
		String mathComplexityBand = mathBand.getCategoryCode();
		String commComplexityBand = null;
		if(commBand != null) {
			commComplexityBand = commBand.getCategoryCode();
		}
		
		if (mathComplexityBand != null && commComplexityBand != null && 
				ComplexityBandEnum.valueOf(mathComplexityBand).ordinal() < ComplexityBandEnum.valueOf(commComplexityBand).ordinal()){
			finalMathBandId = mathBandId;
		}else{
			finalMathBandId = commBandId;
		}
		setFinalBandOnStudent(studentId, finalMathBandId, user, BAND_TYPE_MATH);
		logger.trace("Leaving calculateFinalMathBand");
		return finalMathBandId;
	}
	
	private Long calculateFinalELABand(Long studentId, Long elaBandId, Long commBandId, User user) {
		logger.trace("Entering calculateFinalELABand");
		//get both the ela and comm band and take the minimum of the two
		Long finalELABandId = null;
		Category elaBand = categoryDao.selectByPrimaryKey(elaBandId);
		Category commBand = categoryDao.selectByPrimaryKey(commBandId);
		String elaComplexityBand = elaBand.getCategoryCode();
		String commComplexityBand = null;
		if(commBand != null) {
			commComplexityBand = commBand.getCategoryCode();
		}
		
		if (elaComplexityBand != null && commComplexityBand != null && 
				ComplexityBandEnum.valueOf(elaComplexityBand).ordinal() < ComplexityBandEnum.valueOf(commComplexityBand).ordinal()) {
			finalELABandId = elaBandId;
		}else{
			finalELABandId = commBandId;
		}
		setFinalBandOnStudent(studentId, finalELABandId, user, BAND_TYPE_ELA);
		logger.trace("Leaving calculateFinalELABand");
		return finalELABandId;
	}
	
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private List<RuleAndId> getRulesList(ComplexityBandRuleExample example){
		List<ComplexityBandRule> rules = ruleDao.selectByExample(example);
		ObjectMapper jsonConverter = new ObjectMapper();
		List<RuleAndId> rulesList= new ArrayList<RuleAndId>();
		for (ComplexityBandRule cbr : rules){
			try {
				JsonNode rootNode = jsonConverter.readTree(cbr.getRule());
				JsonNode node = rootNode.path("rule");
				RuleAndId rni = new RuleAndId(cbr.getComplexityBandId(), node);
				rulesList.add(rni);
			} catch (Exception e) {
				logger.error("Error parsing complexity band rules", e);
			}
		}
		return rulesList;
	}
	
    private void setBandOnStudent(Long studentId, Long bandId, User user, String bandType){
    	StudentExample studentExample = new StudentExample();
		studentExample.createCriteria().andIdEqualTo(studentId);

		Student student = new Student();
		student.setCreatedDate(null);//this is an update
		student.setActiveFlag(true);
		student.setModifiedUser(user.getId());
		student.setModifiedDate(new Date());
		if(BAND_TYPE_ELA.equalsIgnoreCase(bandType)) {
			student.setElaBandId(bandId);
		} else if(BAND_TYPE_MATH.equalsIgnoreCase(bandType)) {
			student.setMathBandId(bandId);
		} else if(BAND_TYPE_COMMUNICATION.equalsIgnoreCase(bandType)) {
			student.setCommBandId(bandId);
		} else if(BAND_TYPE_SCIENCE.equalsIgnoreCase(bandType)) {
			student.setSciBandId(bandId);
		} else if (BAND_TYPE_WRITING.equalsIgnoreCase(bandType)) {
			student.setWritingBandId(bandId);
		}		
		studentDao.updateByExampleSelective(student, studentExample);
    }
    
    private void setFinalBandOnStudent(Long studentId, Long bandId, User user, String bandType){
    	StudentExample studentExample = new StudentExample();
		studentExample.createCriteria().andIdEqualTo(studentId);

		Student student = new Student();
		student.setCreatedDate(null);//this is an update
		student.setActiveFlag(true);
		student.setModifiedUser(user.getId());
		student.setModifiedDate(new Date());
		if(BAND_TYPE_ELA.equalsIgnoreCase(bandType)) {
			student.setFinalElaBandId(bandId);
		} else if(BAND_TYPE_MATH.equalsIgnoreCase(bandType)) {
			student.setFinalMathBandId(bandId);
		} else if(BAND_TYPE_SCIENCE.equalsIgnoreCase(bandType)) {
			student.setFinalSciBandId(bandId);
		}		
		studentDao.updateByExampleSelective(student, studentExample);
    }
	
	private Map<String, Object> createTestSession(Long finalBandId, Long studentId, String contentAreaCode, int limit, User user, List<String> collectionNames){
		Map<String, Object> processResult = new HashMap<String, Object>();
		ComplexityBandDao finalBand = testCollectionService.getComplexityBandById(finalBandId);
		Student student = studentDao.findById(studentId);
		Enrollment theEnrollment = getEnrollment(studentId, user, contentAreaCode);
		if (theEnrollment != null){
			String gradeLevelCode = theEnrollment.getCurrentGradeLevelCode();
				if(gradeLevelCode != null){					
					Float linkageLevelLowerBound = 0F;
					Float linkageLevelUpperBound = 0F;
					if(finalBand != null) {
						linkageLevelLowerBound = (float) finalBand.getMinRange();
						linkageLevelUpperBound = (float) finalBand.getMaxRange();
					}					
					//here need to find the pooltype based on the student attendence schoolid -> state and pass to the findMatchingTestCollections query
					String testCollectionPoolType = organizationDao.getPoolType(theEnrollment.getAttendanceSchoolId());
					List<TestCollection> collections = null;
					if(testCollectionPoolType != null){
						collections = testCollectionService.findMatchingTestCollections(linkageLevelLowerBound, linkageLevelUpperBound, gradeLevelCode, contentAreaCode, limit, collectionNames, testCollectionPoolType);
					}
					if (collections != null && !collections.isEmpty()){
						if (collections.size() < limit){
							String errorMsg = "Unable to find "+limit+" test collections for state student identifier: " + student.getStateStudentIdentifier() + " and subject: " 
									+contentAreaCode + " and enrollment: " + theEnrollment.getId()+". " + collections.size() + " test collections were found.";
							//processResult.put("success", true); not really a success yet
							processResult.put("reason", errorMsg);
							processResult.put("firstname", student.getLegalFirstName());
							processResult.put("lastname", student.getLegalLastName());
							processResult.put("studentid", studentId);
							logger.debug(errorMsg);
						}
						for (TestCollection collection : collections){
							//create a test session
							//DE6054 use the roster state subject area to determine if the student needs the session created
							List<Roster> rosters = rosterDao.selectByStateSubjectAreaAndEnrollment(contentAreaCode, theEnrollment.getId());
							
					        List<Long> rosterIds = new ArrayList<Long>();
					        for (Roster r : rosters){
					        	rosterIds.add(r.getId());
					        }
							if (!rosterIds.isEmpty()){
								EnrollmentsRostersExample enrollmentExample = new EnrollmentsRostersExample();
								enrollmentExample.createCriteria().andEnrollmentIdEqualTo(theEnrollment.getId()).andRosterIdIn(rosterIds);
								List<EnrollmentsRosters> enrollmentsRosters = enrollmentRostersDao.selectByExample(enrollmentExample);
								
						    	TestSession testSession = new TestSession();
						    	EssentialElementLinkageTranslationValues eel = getEssentialElementLinkage(finalBand.getBandCode());
								String sessionName = createSessionNameForCreation(student, collection.getName(), eel);
						        testSession.setName(sessionName);
						        testSession.setTestCollectionId(collection.getId());
						        testSession.setFinalBandId(finalBandId);
						        
								Category unusedStatus = getUnusedStatus();
						        if (unusedStatus != null) {
						            testSession.setStatus(unusedStatus.getId());
						        }
						        List<Long> enrollmentRosterIds = new ArrayList<Long>();
						        for (EnrollmentsRosters er : enrollmentsRosters){
						        	enrollmentRosterIds.add(er.getId());
						        }
						        try {            
						        	Long testCollectionId = collection.getId();
						            if (enrollmentRosterIds != null && enrollmentRosterIds.size() > 0 
						            		&& testCollectionId > 0) {            	 
						                boolean successful = studentsTestsService.createTestSessions(
						                        enrollmentRosterIds, testCollectionId, null, testSession, null, null);
						                if(successful){
						                	if(processResult.get("testsessionids") == null) {
						                        processResult.put("testsessionids", new ArrayList<Long>());
						                    }
						                    
						                	@SuppressWarnings("unchecked")
						                	List<Long> tsIds = (List<Long>) processResult.get("testsessionids");
						                    tsIds.add(testSession.getId());						                	

						                	processResult.put("success", true);
						                }else{
						                	processResult.put("success", false);
						                }
						            }
						        } catch (DuplicateTestSessionNameException e) {
						        	String errorMsg = "A test session with the name: "+sessionName+" already exists.";
									processResult.put("success", false);
									processResult.put("reason", errorMsg);
									processResult.put("firstname", student.getLegalFirstName());
									processResult.put("lastname", student.getLegalLastName());
									processResult.put("studentid", studentId);
									logger.error(errorMsg);
						        }
							}else{
								String errorMsg = "No rosters were found for content area: " + contentAreaCode + " and enrollment: " + theEnrollment.getId();
								processResult.put("success", false);
								processResult.put("reason", errorMsg);
								processResult.put("firstname", student.getLegalFirstName());
								processResult.put("lastname", student.getLegalLastName());
								processResult.put("studentid", studentId);
								logger.debug(errorMsg);
							}
						}
					}else{
						String errorMsg="";
						if(collections == null || collections.isEmpty())
							errorMsg = "No test collection matches were found for student id: "+studentId+" for "+testCollectionPoolType+" model, content area: "+contentAreaCode+" and band: "+finalBand.getBandName();
						if(testCollectionPoolType == null || testCollectionPoolType.isEmpty())
							errorMsg = "Pool type is not defined for contracting organization of attendance school - " + theEnrollment.getAttendanceSchoolProgramIdentifier();
						processResult.put("success", false);
						processResult.put("reason", errorMsg);
						processResult.put("firstname", student.getLegalFirstName());
						processResult.put("lastname", student.getLegalLastName());
						processResult.put("studentid", studentId);
						logger.debug(errorMsg);
					}
				}else{
					String errorMsg = "A grade level was not found for enrollment id: "+theEnrollment.getId();
					processResult.put("success", false);
					processResult.put("reason", errorMsg);
					processResult.put("firstname", student.getLegalFirstName());
					processResult.put("lastname", student.getLegalLastName());
					processResult.put("studentid", studentId);
					logger.debug(errorMsg);
				}
		}else{
			String errorMsg = "An enrollment or roster was not found for content area: "+contentAreaCode;
			processResult.put("success", false);
			processResult.put("reason", errorMsg);
			processResult.put("firstname", student.getLegalFirstName());
			processResult.put("lastname", student.getLegalLastName());
			processResult.put("studentid", studentId);
			logger.debug(errorMsg);
		}
				
		return processResult;
	}
	
	private void checkSessionAndUnenrollIfNeeded(List<TestSession> sessions, Student student, Survey survey, User user){
		for (TestSession session : sessions){
			//is the operational test window open?
			OperationalTestWindow otw = operationalTestWindowService.selectTestCollectionById(session.getTestCollectionId());
			if (otw.getEffectiveDate() != null & otw.getExpiryDate() != null){
				Date now = new Date();
				if (otw.getEffectiveDate().before(now) & otw.getExpiryDate().after(now)){
					//window is active
					//if so, did the student start the test session
					//check the studenttests table
					List<StudentsTests> studentsTests = studentsTestsService.findByTestSession(session.getId());
					StudentsTests theStudentsTests = null;
					for (StudentsTests test : studentsTests){
						if (test.getStudentId().equals(survey.getStudentId())){
							theStudentsTests = test;
							break;
						}
					}
					if(theStudentsTests != null && !hasSessionStarted(theStudentsTests.getStatus())){
						//if not  then mark as test session FC unenrolled
						fcUnenrollStudent(session.getId(), user);
					}else{
						//if started then mark as test session FC mid unenrolled
						fcMidUnenrollStudent(session.getId(), user);
					}
						
				}else if (otw.getEffectiveDate().after(now)){
					//window hasn't started yet
					//unenroll from test session and studentstests
					fcUnenrollStudent(session.getId(), user);
				}
			}else{
				logger.debug("The operational test window for test collection id: "+session.getTestCollectionId()+" did not have effective and expiry dates set.");
			}
		}
	}
	
	private void fcUnenrollStudent(Long sessionId, User user){
		//complete the studentstests
		studentsTestsService.fcUnenrollStudentsTests(sessionId, user);
		//since there is only one student per test session complete the session too
		testSessionService.fcUnenrollTestSession(sessionId, user);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void fcUnenrollStudent(Set<Long> testSessionIds, User user){
		for(Long sessionId:testSessionIds) {
			fcUnenrollStudent(sessionId, user);
		}
	}
	
	private void fcMidUnenrollStudent(Long sessionId, User user){
		//complete the studentstests
		studentsTestsService.fcMidUnenrollStudentsTests(sessionId, user);
		//since there is only one student per test session complete the session too
		testSessionService.fcMidUnenrollTestSession(sessionId, user);
	}
	
	
	private int getSchoolYear(User user, List<Enrollment> enrollments){
		int year = -1;
		Long currentOrgId = user.getCurrentOrganizationId();
		Organization userOrg = organizationDao.get(currentOrgId);
		List<Organization> parents;
		if (userOrg.getTypeCode().equals("CONS")){
			//go through the enrollments and get all that have not been exited
			//then go up the hierarchy of each school and get the state
			//create a list of states and order by school end date
			//the first in the list should be the current enrollment's state
			//use that year
			List<Long> schoolIds = new ArrayList<Long>();
			for (Enrollment enrollment : enrollments){
				if (!schoolIds.contains(enrollment.getAttendanceSchoolId())
						&& enrollment.getExitWithdrawalDate() == null){
					schoolIds.add(enrollment.getAttendanceSchoolId());
				}
			}
			List<Organization> states = new ArrayList<Organization>();
			for (Long schoolId : schoolIds){
				for (Organization parent : organizationDao.getAllParents(schoolId)){
					if (parent.getOrganizationType().getTypeCode().equals("ST")){
						states.add(parent);
					}
				}
			}
			if (states.size() > 1){
				Collections.sort(states, new Comparator<Organization>() {
			        @Override public int compare(Organization o1, Organization o2) {
			        	int result = 0;
			        	if (o1.getSchoolEndDate().after( o2.getSchoolEndDate())){
			        		result=1;
			        	}else if (o1.getSchoolEndDate().before( o2.getSchoolEndDate())){
			        		result=-1;
			        	}
			            return result;		       
			      }
			    });
			}
			if (!states.isEmpty() && states.get(0) != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(states.get(0).getSchoolEndDate());
				year = cal.get(Calendar.YEAR);
			}else{
				logger.debug("**A school year was not found for the state related to org: "+user.getCurrentOrganizationId()+" because CONS level user is logged in and no enrollments or states matched.");
			}
		}else{
			parents = organizationDao.getAllParents(currentOrgId);
			//in the case the user is a state level user
			//add the state
			parents.add(userOrg);
			for (Organization org : parents){
				if (org.getOrganizationType().getTypeCode().equals("ST")){
					Calendar cal = Calendar.getInstance();
					cal.setTime(org.getSchoolEndDate());
					year = cal.get(Calendar.YEAR);
					break;
				}
			}
		}
		if (year == -1){
			logger.debug("A school year was not found for the state related to org: "+user.getCurrentOrganizationId());
		}
		return year;
	}
	
	private Enrollment getEnrollment(Long studentId, User user, String subject){	
		Student student = studentDao.findById(studentId);
		List<Enrollment> enrollments = enrollmentDao.getEnrollmentForAuto(student.getStateStudentIdentifier(), subject);
		int schoolYear = getSchoolYear(user, enrollments);
		Enrollment theEnrollment = null;
		if (schoolYear != -1){
			for (Enrollment enrollment : enrollments){
				if (enrollment.getCurrentSchoolYear() == schoolYear){
					theEnrollment = enrollment;
					break;
				}
			}	
			if (theEnrollment == null){
				logger.debug("An enrollment was not found for school year: "+schoolYear);
			}
		}
		
		return theEnrollment;
	}
	
	private boolean hasSessionStarted(Long statusId){
		boolean isStarted = false;
		Category inprogress = getInProgressStatus();
		if (statusId.equals(inprogress.getId())){
			isStarted = true;
		}
		return isStarted;
	}

	
	private EssentialElementLinkageTranslationValues getEssentialElementLinkage(String bandName){
		Integer complexityBandValue = Integer.decode(bandName);
		EssentialElementLinkageTranslationValues eel = eelTranslationValuesMapper.selectByComplexityBandValue(complexityBandValue);
		
		return eel;
	}
	
	private Category getUnusedStatus(){
		CategoryTypeExample catTypeExample = new CategoryTypeExample();
		catTypeExample.createCriteria().andTypeCodeEqualTo("STUDENT_TEST_STATUS");
		List<CategoryType> types = categoryTypeDao.selectByExample(catTypeExample);
		CategoryType type = types.get(0);
		CategoryExample categoryExample = new CategoryExample();
		categoryExample.createCriteria().andCategoryCodeEqualTo("unused").andCategoryTypeIdEqualTo(type.getId());
		List<Category> unusedSessions  = categoryDao.selectByExample(categoryExample);
		Category unusedSession = null;
		if (unusedSessions != null && !unusedSessions.isEmpty()){
			unusedSession = unusedSessions.get(0);
		}
		return unusedSession;
	}
	
	private Category getInProgressStatus(){
		CategoryTypeExample catTypeExample = new CategoryTypeExample();
		catTypeExample.createCriteria().andTypeCodeEqualTo("STUDENT_TEST_STATUS");
		List<CategoryType> types = categoryTypeDao.selectByExample(catTypeExample);
		CategoryType type = types.get(0);
		CategoryExample categoryExample = new CategoryExample();
		categoryExample.createCriteria().andCategoryCodeEqualTo("inprogress").andCategoryTypeIdEqualTo(type.getId());
		List<Category> inprogressStatuses  = categoryDao.selectByExample(categoryExample);
		Category inprogressStatus = null;
		if (inprogressStatuses != null && !inprogressStatuses.isEmpty()){
			inprogressStatus = inprogressStatuses.get(0);
		}
		return inprogressStatus;
	}
	
	private Category getCompleteStatus(){
		CategoryTypeExample catTypeExample = new CategoryTypeExample();
		catTypeExample.createCriteria().andTypeCodeEqualTo("STUDENT_TEST_STATUS");
		List<CategoryType> types = categoryTypeDao.selectByExample(catTypeExample);
		CategoryType type = types.get(0);
		CategoryExample categoryExample = new CategoryExample();
		categoryExample.createCriteria().andCategoryCodeEqualTo("complete").andCategoryTypeIdEqualTo(type.getId());
		List<Category> completeStatuses  = categoryDao.selectByExample(categoryExample);
		Category completeStatus = null;
		if (completeStatuses != null && !completeStatuses.isEmpty()){
			completeStatus = completeStatuses.get(0);
		}
		return completeStatus;
	}
	
	private String createSessionName(Student student, String collectionName, String eelName){
		int stTrimLength = getStudentTrimLength(student);
		int cnTrimLength = collectionName.length() < collectionNameLength ? collectionName.length() : collectionNameLength;
		int eelTrimLength = getEelTrimLength(eelName);
		return sessionNameIdentifier+DASH
				+student.getLegalLastName().substring(0, stTrimLength)+DASH
				+collectionName.substring(0, cnTrimLength)+DASH
				+eelName.substring(0, eelTrimLength);
	}
	
	private int getEelTrimLength(String eelName){
		return eelName.length() < linkageNameLength ? eelName.length() : linkageNameLength;
	}
	private int getStudentTrimLength(Student student ) {
		return student.getLegalLastName().length() < lastNameLength ? student.getLegalLastName().length() : lastNameLength;
	}

	private String createSessionNameForCreation(Student student, String collectionName, EssentialElementLinkageTranslationValues eel){
		String eelName = eel.getCategoryName().replace(" ", "");
		return createSessionName(student, collectionName, eelName)+DASH+student.getId();
	}
	
	private String createSessionNameForSearch(Student student, String collectionName, EssentialElementLinkageTranslationValues eel){
		String eelName = "%";
		if (eel != null)
			eelName = eel.getCategoryName().replace(" ", "");
		return createSessionName(student, collectionName, eelName)+DASH+student.getId();
	}
	
	public Set<Long> findSurveysForBatchAutoRegistration(Long contractingOrgId, Long assessmentProgramId, User user){	
		Set<Long> surveyIds = new TreeSet<Long>();
		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		List<DLMAutoRegistrationDTO> dtos = surveyDao.findSurveysForBatchAutoRegistration(contractingOrgId, assessmentProgramId, currentSchoolYear);
		//check each completed survey to see if there is a matching session 
		//and if not keep the survey id
		
		for (DLMAutoRegistrationDTO dto : dtos){
			List<TestSession> mathSessions = new ArrayList<TestSession>();
			List<TestSession> elaSessions = new ArrayList<TestSession>();
			Student student = studentDao.findById(dto.getStudentId());
			if(dto.getFinalMathBandId() != null) {
				Category mathFinalBand = categoryDao.selectByPrimaryKey(dto.getFinalMathBandId());
				EssentialElementLinkageTranslationValues mathEEL = getEssentialElementLinkage(mathFinalBand.getCategoryName());
				 List<TestSession> tempMathSessions = 
						testSessionService.selectForStudentGradeSubjectAndPartialName(
								dto.getStudentId(), 
								dto.getCurrentGradeLevelCode(),
								mathCode, 
								createSessionNameForSearch(student, "%", mathEEL),
								mathSessionLimit);
				 if (tempMathSessions != null){
					 mathSessions.addAll(tempMathSessions);
				 }
			}
			if(dto.getFinalELABandId() != null) {
				Category elaFinalBand = categoryDao.selectByPrimaryKey(dto.getFinalELABandId());
				EssentialElementLinkageTranslationValues elaEEL = getEssentialElementLinkage(elaFinalBand.getCategoryName());
				 List<TestSession> tempElaSessions = 
						testSessionService.selectForStudentGradeSubjectAndPartialName(
								dto.getStudentId(), 
								dto.getCurrentGradeLevelCode(),
								elaCode, 
								createSessionNameForSearch(student, "%", elaEEL),
								elaSessionLimit);
				if (tempElaSessions != null){
					elaSessions.addAll(tempElaSessions);
				}
			}
			//surveys to process
			boolean processSurvey = false;
			if (mathSessions.size() > 0){
				for (TestSession mathSession : mathSessions){
					if (mathSession == null || !mathSession.getStatusCategory().getCategoryCode().equalsIgnoreCase("complete")){
						processSurvey = true;
					}
				}
			}else{
				processSurvey = true;
			}

			if (!processSurvey){
				if (elaSessions.size() > 0){
					for (TestSession elaSession : elaSessions){
						if (elaSession == null || !elaSession.getStatusCategory().getCategoryCode().equalsIgnoreCase("complete")){
							processSurvey = true;
						}
					}
				}else{
					processSurvey = true;
				}
			}
			if (processSurvey){
				surveyIds.add(dto.getSurveyId());
			}
		}
		
		return surveyIds;
	}
	
	public Integer getElaSessionLimit() {
		return elaSessionLimit;
	}

	public Integer getMathSessionLimit() {
		return mathSessionLimit;
	}

	private class RuleAndId{
		private Long complexityBandId;
		private JsonNode rule;
		public RuleAndId(Long complexityBandId, JsonNode node) {
			this.complexityBandId = complexityBandId;
			this.rule = node;
		}
		public Long getComplexityBandId() {
			return complexityBandId;
		}
		public JsonNode getRule() {
			return rule;
		}
	}
	
	private enum ComplexityBandEnum{
		FOUNDATIONAL, 
		BAND_1, 
		BAND_2, 
		BAND_3;
	}

	private String errorMsgProcess(String currentErrMsg, String oldErrMsg ){
		if(oldErrMsg == null){
			return currentErrMsg;
		}else{
			return oldErrMsg + "<br />" + currentErrMsg;
		}
	}
	
	@Override
	public List<String> checkPoolTypes() {
		return organizationDao.checkPoolTypes();
	}
	
	@Override
	public List<ComplexityBand> getAllBands() {
		return bandDao.selectNullContentAreaWithLinkageLevelInfo();
	}
	
	@Override
	public List<ComplexityBand> getAllBandsWithContentArea(Long assessmentProgramId) {
		return bandDao.selectAllWithLinkageLevelInfo(assessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ComplexityBand> getBandsByAssessmentProgramIdContentAreaId(Long assessmentProgramId, Long contentAreaId) {
		return bandDao.getAllBandsWithLinkageLevelInfoByAssessmentProgramIdContentAreaId(assessmentProgramId, contentAreaId);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ComplexityBand> getAll() {
		return bandDao.getAll();
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
	
	private Long getUserId() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return user.getId();
	}
	
}