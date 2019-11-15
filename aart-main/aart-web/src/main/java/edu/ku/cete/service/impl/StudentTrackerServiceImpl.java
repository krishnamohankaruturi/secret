/**
 * @author bmohanty_sta
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15536 : Student Tracker - Simple Version 1 (preliminary)
 * Service class to implement student tracker batch process related functionality.
 */
package edu.ku.cete.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.batch.dlm.st.StudentTrackerHelper;
import edu.ku.cete.batch.dlm.st.StudentTrackerIMPartiallyMetCasesEnum;
import edu.ku.cete.dlm.iti.BPCriteriaAndGroups;
import edu.ku.cete.dlm.iti.BPGroupsInfo;
import edu.ku.cete.dlm.iti.StudentTrackerTest;
import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.StudentTrackerBluePrintStatus;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSpecification;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.professionaldevelopment.ProportionMetrics;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.BluePrintMapper;
import edu.ku.cete.model.StudentTrackerBandMapper;
import edu.ku.cete.model.StudentTrackerBluePrintStatusMapper;
import edu.ku.cete.model.StudentTrackerMapper;
import edu.ku.cete.model.StudentsResponsesDao;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TestCollectionDao;
import edu.ku.cete.model.TestSpecificationMapper;
import edu.ku.cete.model.professionaldevelopment.ProportionMetricsMapper;
import edu.ku.cete.model.student.survey.SurveyMapper;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;

import edu.ku.cete.service.GradeCourseService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class StudentTrackerServiceImpl implements StudentTrackerService {

	private static final long FIRST_CRITERIA = 1l;

	private static final String TEST_TYPE_ITI = "ITI";
	
	private static final String TEST_TYPE_ST = "ST";

	private static final String SOURCE_TYPE_STUDENT_TRACKER = "ST";

	public static final String SOURCE_TYPE_FIRST_CONTACT = "FC";

	private Logger logger = LoggerFactory.getLogger(StudentTrackerServiceImpl.class);
	
	private static final String MULTIEEOFC = "MULTIEEOFC";

	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private StudentTrackerMapper studentTrackerDao;
	
	@Autowired
	private StudentTrackerBandMapper studentTrackerBandDao;
	
	@Autowired
	private TestSpecificationMapper testSpecificationDao;
	
	@Autowired
	private StudentsTestsDao studentsTestDao;

	@Autowired
	private StudentsResponsesDao studentsResponsesDao;
	
	@Autowired
	private BluePrintMapper bluePrintMapper;

	@Autowired
	private ProportionMetricsMapper proportionMetricsDao;
	
	@Autowired
	private ItiTestSessionService itiTestSessionService;
	
	@Autowired
	private TestCollectionDao testCollectionDao;

	@Autowired
	private SurveyMapper surveyDao;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private StudentTrackerBluePrintStatusMapper studentTrackerBluePrintStatusMapper;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	private Random randomGenerator = new Random();

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public void addBandRecommendation(StudentTracker trackerStudent) {
		trackerStudent.setStatus("TRACKED");
		if(trackerStudent.getId() == null) {
			trackerStudent.setAuditColumnProperties();
			studentTrackerDao.insertSelective(trackerStudent);
		} else {
			trackerStudent.setAuditColumnPropertiesForUpdate();
			studentTrackerDao.updateByPrimaryKeySelective(trackerStudent);
		}
		if(trackerStudent.getRecommendedBand() != null) {
			trackerStudent.getRecommendedBand().setStudentTrackerId(trackerStudent.getId());
			trackerStudent.getRecommendedBand().setAuditColumnProperties();
			studentTrackerBandDao.insertSelective(trackerStudent.getRecommendedBand());
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateBandTestSession(StudentTracker trackerStudent) {
		trackerStudent.getRecommendedBand().setAuditColumnPropertiesForUpdate();
		studentTrackerBandDao.updateByPrimaryKeySelective(trackerStudent.getRecommendedBand());
	}

	/**
	 * Perform student tracker update for given current school year, subject for
	 * DLM multiple ee state.
	 * 
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public StudentTrackerBand processStudentForMultiEE(StudentTracker trackerStudent, ContentArea contentArea, List<ComplexityBand> allBands,
			List<BatchRegistrationReason> reasons, String orgPoolType, Long operationalWindowId, Organization contractingOrganization, User user) {
		Long studentId = trackerStudent.getStudentId();
		Long currentSurveyBandId = trackerStudent.getSurveyBandId();
		Long gradeId = trackerStudent.getGradeCourseId();
		Long contentAreaId = contentArea.getId();
		String contentAreaAbbreviatedName = contentArea.getAbbreviatedName();
		StudentTrackerBand bandRecommendation = null;
		Enrollment enrollment = getEnrollment(trackerStudent, contentArea.getId(), contractingOrganization);
		String message = StringUtils.EMPTY;
		Long writingBandId = trackerStudent.getWritingBandId();
		Boolean NJGrade12 = false;
		if(enrollment == null) {
			message = StudentTrackerHelper.constructCommonMessage("No enrollment found for student: ", trackerStudent, orgPoolType, 
					operationalWindowId, contractingOrganization, null, null, null, null);
			writeReason(studentId, message, reasons);
			return null;
		}
		trackerStudent.setEnrollment(enrollment);
		Roster roster = getRoster(trackerStudent, orgPoolType, contentAreaId, trackerStudent.getCourseId());
		if(roster == null) {
			if(MULTIEEOFC.equals(orgPoolType)) {
				message = StudentTrackerHelper.constructCommonMessage("No course based roster found for student: ", trackerStudent, orgPoolType,
						operationalWindowId, contractingOrganization, null, null, trackerStudent.getGradeCode(), null);
				writeReason(studentId, message, reasons);
			} else {
				message = StudentTrackerHelper.constructCommonMessage("No roster found for student ", trackerStudent, orgPoolType,
						operationalWindowId, contractingOrganization, null, null, trackerStudent.getGradeCode(), null);
				writeReason(studentId, message, reasons);
			}
			return null;
		}
		
		/* This change was made by Rohit Yadav on April 4, 2019
		 * If a student is in NJ, grade 12, rostered to ELA/Math, they get treated like an 11th grader for autoenrollment
		*/

		if(enrollment != null && StringUtils.equalsIgnoreCase("NJ", contractingOrganization.getDisplayIdentifier()) 
		  &&  (
			StringUtils.equalsIgnoreCase(CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_ELA, contentArea.getAbbreviatedName()) ||
			StringUtils.equalsIgnoreCase(CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_MATH, contentArea.getAbbreviatedName())
		      ) 
                  && "12".equals(enrollment.getGradeCourse().getName())) {
				NJGrade12 = true;
				GradeCourse gradeCourseNJ = gradeCourseService.getByContentAreaAndAbbreviatedName(contentAreaId,"11");
				trackerStudent.setGradeCourseId(gradeCourseNJ.getId());
				trackerStudent.setGradeCode(gradeCourseNJ.getAbbreviatedName());
		}
		
		logger.debug("processStudentForMultiEE.......studentId"+studentId);
		if (currentSurveyBandId != null) {
			String surveyStatus = surveyDao.findStatusCodeByStudent(studentId);
			if(surveyStatus == null || !surveyStatus.equalsIgnoreCase("COMPLETE")) {
				message =  StudentTrackerHelper.constructMessage("Survey for student is not completed state for studentId: ", 
						trackerStudent, operationalWindowId, surveyStatus, null);
				writeReason(studentId, message, reasons);
				return null;
			}
			String gcCode = getGradeCourseAbbr(roster, orgPoolType, trackerStudent.getGradeCode());
			/**
			 * Check if student information already exist in outcome table or
			 * student band recommendation table. For first time students, there
			 * will not be any data For all subsequent processing there will be
			 * data After end of this process a row is inserted in the student
			 * band recommendation table.
			 */
			TestSpecification testSpec = getTestSpecByPoolGradeContentArea(orgPoolType, gcCode, contentArea.getId(), operationalWindowId);
			if(testSpec == null) {
				message = StudentTrackerHelper.constructCommonMessage("There is no Test Specification avaialble for student: ", trackerStudent,
						orgPoolType, operationalWindowId, null, null, null, null, null);
				writeReason(studentId, message, reasons);
				return null;
			}
			StudentTrackerBand latestBand = getLatestTrackerBandWithTestSession(trackerStudent, testSpec.getId(), operationalWindowId);
			List<TestSpecification> stTestSpecs = getTestSpecByStudentTracker(trackerStudent.getId(), operationalWindowId);
			if(stTestSpecs.size() > 1) {
				List<String> testSpecNames = new ArrayList<String>(stTestSpecs.size());
				for(TestSpecification ts : stTestSpecs) {
					testSpecNames.add(ts.getSpecificationName());
				}
				message = StudentTrackerHelper.constructMessage("Multiple test overview's found for student: ", trackerStudent,
						operationalWindowId, null, testSpecNames);
				writeReason(studentId, message, reasons);
				return null;
			}
			if (latestBand == null) {
				logger.debug("studentId=" + studentId + ", currentSurveyBandId=" + currentSurveyBandId + ", gradeId=" + gradeId);

				/**
				 * First time the student data is being processed. Put the final
				 * band same as retrieved from student table for the content
				 * area. The essential element field will be blank. The
				 * recommended linkage level will be same based on the first
				 * contact band. The source will be FC so that we know that this
				 * is First Contact data. Test Id and TestConnectionId will be
				 * empty since there is no information if student has taken any
				 * test.
				 */
				Long recommendedBandId = null;
				if(CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_SCIENCE.equalsIgnoreCase(contentAreaAbbreviatedName)						
						|| CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_SOCIAL_STUDIES.equalsIgnoreCase(contentAreaAbbreviatedName)){
					ComplexityBand band = getBandById(allBands, currentSurveyBandId);
					//Map ELA/M Band to SCI band
					ComplexityBand sciBand =  StudentTrackerHelper.getBandInContentArea(allBands, band.getBandCode(), contentAreaAbbreviatedName);
					if(sciBand.getBandCode().equals(CommonConstants.STRING_ZERO)){
						ComplexityBand sciBandCode1 =  StudentTrackerHelper.getBandInContentArea(allBands, CommonConstants.STRING_ONE, contentAreaAbbreviatedName);
						recommendedBandId = sciBandCode1.getId();
					}else{
						recommendedBandId = sciBand.getId();
					}
				}else{
					if(StringUtils.equalsIgnoreCase(CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_ELA, contentAreaAbbreviatedName) 
							&& isWritingContentAvailableInRanking(1l, testSpec.getId())) {
						if(writingBandId == null) {
							message = StudentTrackerHelper.constructCommonMessage("Survey writing band has not been calculated for studentId: ", trackerStudent,
									null, operationalWindowId, null, null, null, null, null);
							writeReason(studentId, message, reasons);
							return null;
						}
						recommendedBandId = writingBandId;
					} else {
						recommendedBandId = currentSurveyBandId;
					}
				}
				bandRecommendation = getRecommendedStudentTrackerBand(recommendedBandId, SOURCE_TYPE_FIRST_CONTACT, null);
				
			} else if(latestBand.getTestSessionId() == null) {
				message = StudentTrackerHelper.constructCommonMessage("Recommended band exists with no assigned test session for studentId: ", trackerStudent, null,
						operationalWindowId, null, null, currentSurveyBandId, null, null);
				writeReason(studentId, message, reasons);
				
			} else {
				/**
				 * Student has been processed at least once. So a record already
				 * exists in student band recommendation table. Check if test id
				 * and test collection id is present for this student band
				 * recommendation record. If they exists (i.e. not empty), that
				 * means that these values were updated by batch auto enrollment
				 * process. Batch auto enrollment process looks through latest
				 * student recommendation row and assigns a test and test
				 * collection based on recommended complexity band for the
				 * content area. So modify currentSurveyBandId to use the latest
				 * from the table, so complexity band recommendation process
				 * will now to evaluated based on this value.
				 */
				logger.debug("get completed studenttest by last recommendedband"+studentId+", "+latestBand.getTestSessionId());
				
				long bandCount = getCountOfBandsByStudentTracker(latestBand.getStudentTrackerId(), testSpec.getId(), operationalWindowId);
				long testSpecMinRequired = testSpec.getMinimumNumberOfEEs();
				logger.debug("bandCount=" + bandCount + ", testSpecMinRequired=" + testSpecMinRequired);
				if(bandCount<testSpecMinRequired) {
					StudentsTests stest = studentsTestDao.findCompletedByStudentAndTestSession(studentId, latestBand.getTestSessionId());
					if (stest != null) {
						try {
							if(StringUtils.equalsIgnoreCase("ELA", contentAreaAbbreviatedName)) {
								if(isWritingContentAvailableInRanking(bandCount, testSpec.getId())) {
									stest = findMostRecentCompletedNonWritingTestForMultiEE(studentId, latestBand.getTestSessionId());
									if(stest == null && bandCount == 1) {
										bandRecommendation = getRecommendedStudentTrackerBand(currentSurveyBandId, SOURCE_TYPE_STUDENT_TRACKER, null);
										bandRecommendation.setOperationalWindowId(operationalWindowId);
										return bandRecommendation;
									} else if(stest == null){
										message = StudentTrackerHelper.constructCommonMessage("Assigned test based on recommended band is not in completed state for studentId: ",
												trackerStudent, orgPoolType, operationalWindowId, contractingOrganization, null, currentSurveyBandId, null, null);
										writeReason(studentId, message, reasons);
										return null;
									}
								} else {
									if(isWritingContentAvailableInRanking(bandCount + 1, testSpec.getId())) {
										if(writingBandId == null) {
											message = StudentTrackerHelper.constructCommonMessage("Survey writing band has not been calculated for studentId: ", trackerStudent,
													null, operationalWindowId, null, null, null, null, null);
											writeReason(studentId, message, reasons);
											return null;
										}
										bandRecommendation = getRecommendedStudentTrackerBand(writingBandId, SOURCE_TYPE_STUDENT_TRACKER, null);
										bandRecommendation.setOperationalWindowId(operationalWindowId);
										return bandRecommendation;
									}
								}
							}
							/**
							 * Get all items/responses/taskvariants/questions of
							 * the test i.e. if a test has 15 questions, we will
							 * get 15 rows. Each row may belong to same or
							 * different essential element say 4 distinct
							 * essential elements Now group responses based on
							 * essential elements and put in a map.
							 */
							Map<String, List<StudentsResponses>> responsesGroupedByEEAndLinkageMap = studentsResponsesDao.findResponsesByStudentTest(stest.getId());
							/**
							 * Iterate over the grouped responses for this
							 * test. Find individual proportions
							 * taskvariantid=1, score=1, maxscore=1,
							 * essentialelement=ELA.E1.L1.5a
							 * taskvariantid=2, score=4, maxscore=4,
							 * essentialelement=ELA.E1.L1.5b
							 * taskvariantid=3, score=2, maxscore=4,
							 * essentialelement=ELA.E1.L1.5a
							 * taskvariantid=4, score=3, maxscore=3,
							 * essentialelement=ELA.E1.L1.5a
							 * taskvariantid=5, score=1, maxscore=1,
							 * essentialelement=ELA.E1.L1.5a
							 * taskvariantid=6, score=0.5, maxscore=1,
							 * essentialelement=ELA.E1.L1.5b
							 * taskvariantid=7, score=0.4, maxscore=1,
							 * essentialelement=ELA.E1.L1.5b
							 * taskvariantid=8, score=2, maxscore=2,
							 * essentialelement=ELA.E1.L1.5a
							 * taskvariantid=9, score=3, maxscore=4,
							 * essentialelement=ELA.E1.L1.5a
							 * taskvariantid=10, score=1, maxscore=4,
							 * essentialelement=ELA.E1.L1.5b
							 * 
							 * For ELA.E1.L1.5b, proportion =(4+0.5+0.4+1) /
							 * (4+1+1+4) For ELA.E1.L1.5a, proportion
							 * =(1+2+3+1+2+3) / (1+4+3+1+2+4)
						    */
							if (responsesGroupedByEEAndLinkageMap != null && responsesGroupedByEEAndLinkageMap.size() > 0) {
								Long finalRecoLevel = 0L;
								// String finalRecoEE = null;
								
								ComplexityBand band = getBandById(allBands, latestBand.getComplexityBandId());
								String currentLinkageLevelCode = band.getLinkageLevel().getCategoryCode();
								//Long level = new Long(band.getBandCode());
									finalRecoLevel = new Long(band.getBandCode());
									finalRecoLevel = getFinalRecommendedLevel(
											reasons, studentId, gradeId,
											contentAreaId, responsesGroupedByEEAndLinkageMap,
											finalRecoLevel, currentLinkageLevelCode,contentAreaAbbreviatedName, allBands);

									ComplexityBand recommendedBand = getBandByLinkageLevel(allBands, finalRecoLevel, contentAreaAbbreviatedName);
									
									logger.debug("source=ST" + ", currentSurveyBandId=" + currentSurveyBandId + ", recommendedfinalband=" + recommendedBand.getId()
											+ ", recommendedfinallevel=" + recommendedBand.getLinkageLevel().getCategoryCode());

									bandRecommendation = getRecommendedStudentTrackerBand(recommendedBand.getId(), SOURCE_TYPE_STUDENT_TRACKER, null);
								} else {
								ComplexityBand band = getBandById(allBands, latestBand.getComplexityBandId());
								Long finalRecoLevel = new Long(band.getBandCode());
								if(finalRecoLevel.longValue()>0) {
									finalRecoLevel=finalRecoLevel-1L;
								}
								ComplexityBand recommendedBand = getBandByLinkageLevel(allBands, finalRecoLevel, contentAreaAbbreviatedName);
								bandRecommendation = getRecommendedStudentTrackerBand(recommendedBand.getId(), SOURCE_TYPE_STUDENT_TRACKER, null);
							}
						} catch (Exception e) {
							logger.error("Exception:", e);
							message = StudentTrackerHelper.constructCommonMessage("System Error : studentId: ", trackerStudent, 
									null, null, null, e.getMessage(), null, null, null);
							writeReason(studentId, message, reasons);
						}

					} else {
						message = StudentTrackerHelper.constructCommonMessage("Assigned test based on recommended band is not in completed state for studentId: ", trackerStudent,
								orgPoolType, operationalWindowId, contractingOrganization, null, currentSurveyBandId, null, null);
						writeReason(studentId, message, reasons);
					}
				} else {
					message = StudentTrackerHelper.constructCommonMessage("Minimum required EEs met for : studentId: ", trackerStudent, 
							orgPoolType, operationalWindowId, contractingOrganization, null, null, gcCode, testSpec.getId());
					writeReason(studentId, message, reasons);
					
					checkAndInsertStudentTrackerBluePrintStatus(trackerStudent,
							operationalWindowId, user);
				}
			}

		} else {
			message = StudentTrackerHelper.constructCommonMessage("Survey final band has not been calculated for studentId: ", trackerStudent,
					null, operationalWindowId, null, null, null, null, null);
			logger.debug(message);
			writeReason(studentId, message, reasons);
		}
		if(bandRecommendation != null){
			bandRecommendation.setOperationalWindowId(operationalWindowId);
		}
		return bandRecommendation;
	}

	private StudentsTests findMostRecentCompletedNonWritingTestForMultiEE(Long studentId, Long writingTestSessionId) {
		return studentsTestDao.findMostRecentCompletedNonWritingTestForMultiEE(studentId, writingTestSessionId);
	}

	private boolean isWritingContentAvailableInRanking(Long ranking, Long testSpecificationId) { 
		return testSpecificationDao.isWritingContentAvailableInTestSpecRank(ranking, testSpecificationId);
	}

	private StudentTrackerBand getRecommendedStudentTrackerBand(Long recommendedBandId, String source, Long essentialElementId) {
		StudentTrackerBand bandRecommendation = new StudentTrackerBand();
		bandRecommendation.setSource(source);
		bandRecommendation.setComplexityBandId(recommendedBandId);
		if(essentialElementId != null) {
			bandRecommendation.setEssentialElementId(essentialElementId);
		}
		return bandRecommendation;
	}

	private void checkAndInsertStudentTrackerBluePrintStatus(
			StudentTracker trackerStudent, Long operationalWindowId, User user) {
		StudentTrackerBluePrintStatus stBPStatus = new StudentTrackerBluePrintStatus();
		stBPStatus.setStudentTrackerId(trackerStudent.getId());
		stBPStatus.setCreatedDate(new Date());
		stBPStatus.setCreatedUser(user.getId());
		stBPStatus.setStatusCode("STCOMPLETED");
		stBPStatus.setOperationalWindowId(operationalWindowId);
		StudentTrackerBluePrintStatus studentTrackerBluePrintStatusRec = selectStudentTrackerBluePrintStatus(stBPStatus);
		if(studentTrackerBluePrintStatusRec == null){
			insertStudentTrackerBluePrintStatus(stBPStatus);
		}
	}

	private Long getFinalRecommendedLevel(
			List<BatchRegistrationReason> reasons,
			Long studentId,
			Long gradeId,
			Long contentAreaId,
			Map<String, List<StudentsResponses>> responsesGroupedByEEAndLinkageMap,
			Long finalRecoLevel, String currentLinkageLevelCode, String contentAreaAbbreviatedName,
			List<ComplexityBand> allBands) {
		List<Long> recLevels = new ArrayList<Long>();
		for (String eeCode : responsesGroupedByEEAndLinkageMap.keySet()) {
			logger.debug("studentId=" + studentId + ", gradeId=" + gradeId + ", essential element=" + eeCode);
			List<StudentsResponses> srList = (List<StudentsResponses>) ((HashMap) responsesGroupedByEEAndLinkageMap.get(eeCode)).get("value");
			if (srList != null && srList.size() > 0) {
				/**
				 * Calculate Proportion
				 */
				BigDecimal totalScore = BigDecimal.ZERO;
				totalScore = totalScore.setScale(2);
				BigDecimal totalMaxScore = BigDecimal.ZERO;
				totalMaxScore = totalMaxScore.setScale(2);
				BigDecimal proportion = BigDecimal.ZERO;
				proportion = proportion.setScale(2);
				for (StudentsResponses sr : srList) {
					if(sr.getScore()!=null){
						totalScore = totalScore.add(sr.getScore());	
					}
					if(sr.getTaskVariant()!=null && sr.getTaskVariant().getMaxScore()!=null) {
						totalMaxScore = totalMaxScore.add(new BigDecimal(sr.getTaskVariant().getMaxScore()));
					}
				}
				if(totalMaxScore.doubleValue()>0) {
					proportion = totalScore.divide(totalMaxScore,2, RoundingMode.HALF_UP);	
				}
				

				Map<String, ProportionMetrics> proportionMetricMap = getProportionMetrics(gradeId, eeCode, currentLinkageLevelCode);

				/**
				 * Get low metric from
				 * proportionmetric map.
				 */
				
				String pmKey = gradeId + "-" + eeCode + "-" + currentLinkageLevelCode;
				BigDecimal lowProp = null;
				if (proportionMetricMap.get(pmKey) != null) {
					ProportionMetrics pmetric = (ProportionMetrics) proportionMetricMap.get(pmKey);
					if (pmetric.getProportionLow() != null) {
						lowProp = pmetric.getProportionLow();
					} else {
						 lowProp = BigDecimal.ZERO;
					}
				}

				/**
				 * Get high metric from
				 * proportionmetric map.
				 */
				BigDecimal highProp = null;
				if (proportionMetricMap.get(pmKey) != null) {
					ProportionMetrics pmetric = (ProportionMetrics) proportionMetricMap.get(pmKey);
					if (pmetric.getProportionHigh() != null) {
						highProp = pmetric.getProportionHigh();
					} else {
						highProp = BigDecimal.ZERO;
					}
				}

				logger.debug("lowProp=" + lowProp + ", highProp=" + highProp + ", totalScore=" + totalScore + ", totalMaxScore=" + totalMaxScore
						+ ", proportion=" + proportion + ", studentId=" + studentId + ", gradeId=" + gradeId + ", essential element=" + eeCode);

				/**
				 * Check the position of the
				 * computed proportion i.e. X low Y
				 * high Z
				 */
				Map<String, String> minMaxLevel = StudentTrackerHelper.getMinMaxLevels(allBands, contentAreaAbbreviatedName);
				if(highProp != null && proportion.compareTo(highProp) > 0) {
					//finalRecoLevel = Math.min(4, finalRecoLevel + 1L);
					recLevels.add(Math.min(new Long(minMaxLevel.get("max")), finalRecoLevel + 1L));
				} else if(lowProp != null && proportion.compareTo(lowProp) < 0) {
					//finalRecoLevel = Math.max(0, finalRecoLevel - 1L);
					recLevels.add(Math.max(new Long(minMaxLevel.get("min")), finalRecoLevel - 1L));
				} else {
					recLevels.add(finalRecoLevel);
				}
			} else {
				// This should never happen.
				logger.debug("System Error : studentid=" + studentId + " and gradeId=" + gradeId + ". EE=" + eeCode
						+ " exists but responses does not exist.");
				writeReason(studentId, String.format("System Error : studentId: %d, contentAreaId: %d gradeId: %d, EE: %s exists but responses does not exist.", 
						studentId, contentAreaId, gradeId, eeCode), reasons);
			}
		}
		//get min level of all EE'z DE8773
		if(!recLevels.isEmpty()) {
			finalRecoLevel = Collections.min(recLevels);
		}
		return finalRecoLevel;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public StudentTrackerBand processStudentForSingleEE(StudentTracker trackerStudent, ContentArea contentArea, List<ComplexityBand> allBands,
		List<BatchRegistrationReason> reasons, String orgPoolType, Long currentSchoolYear, Long operationalWindowId, User user) {
		Long studentId = trackerStudent.getStudentId();
		Long currentSurveyBandId = trackerStudent.getSurveyBandId();
		Long contentAreaId = contentArea.getId();
		StudentTrackerBand bandRecommendation = null;
		Long gradeId = trackerStudent.getGradeCourseId();
 		String message = StringUtils.EMPTY;
		Long writingBandId = trackerStudent.getWritingBandId();
		logger.debug("processStudentForSingleEE......studentId:"+studentId+",contentAreaId:"+contentAreaId+",gradeId:"+gradeId+",gradeCode:"+trackerStudent.getGradeCode()
		 + " , operationalWindowId:" + operationalWindowId);
		
		if (currentSurveyBandId != null) {
			String surveyStatus = surveyDao.findStatusCodeByStudent(studentId);
			if(surveyStatus == null || !surveyStatus.equalsIgnoreCase("COMPLETE")) {
				message = StudentTrackerHelper.constructMessage("Survey for student is not completed state for studentId: ", trackerStudent, operationalWindowId, surveyStatus, null);
				writeReason(studentId, message, reasons);
				return null;
			}
			
			TestSpecification testSpec = getTestSpecByPoolGradeContentArea(orgPoolType, trackerStudent.getGradeCode(), contentArea.getId(), operationalWindowId);
			if(testSpec == null) {
				message = StudentTrackerHelper.constructCommonMessage("There is no Test Specification avaialble for : studentId: ", trackerStudent, 
						orgPoolType, operationalWindowId, null, null, null, null, null);
				writeReason(studentId, message, reasons);
				return null;
			}
			List<TestSpecification> stTestSpecs = getTestSpecByStudentTracker(trackerStudent.getId(), operationalWindowId);
			if(stTestSpecs.size() > 1) {
				List<String> testSpecNames = new ArrayList<String>(stTestSpecs.size());
				for(TestSpecification ts : stTestSpecs) {
					testSpecNames.add(ts.getSpecificationName());
				}
				message = StudentTrackerHelper.constructMessage("Multiple test overview's found for student: ",
						trackerStudent, operationalWindowId, null, testSpecNames);
				writeReason(studentId, message, reasons);
				return null;
			}
			List<BPCriteriaAndGroups> bluePrintList = getBluePrintByContentAreaAndGrade(contentAreaId, trackerStudent.getGradeCode());
			Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST = new HashMap<Long, BPCriteriaAndGroups>();
			
			if(bluePrintList.size()>0) {
				gradeId = bluePrintList.get(0).getGradeCourseId();
				fillBluePrintMapDetails(bluePrintList, bpCriteriasMapForITIAndST);
			} else {
				message = StudentTrackerHelper.constructCommonMessage("There is no data in the Blue Print : studentId: ", trackerStudent, 
						orgPoolType, operationalWindowId, null, null, null, null, null);
				writeReason(studentId, message, reasons);
				return null;
			}
			
			List<ContentFrameworkDetail> listOfEEsCompletedInITI = testCollectionDao.
					getStudentITITestsForSubGradeAndCriteria(null, trackerStudent.getGradeCode(), contentAreaId, studentId, currentSchoolYear.intValue());
			logger.debug("bluePrintList size:"+bluePrintList.size());
			logger.debug("itiTestSessions size:"+listOfEEsCompletedInITI.size());
			fillITITestDetails(bpCriteriasMapForITIAndST, listOfEEsCompletedInITI);
			boolean studentMetBluePrintInITI = isStudentFullyMetBluePrintInITI(bpCriteriasMapForITIAndST);
			StudentTrackerBand latestBand = getLatestTrackerBandWithTestSession(trackerStudent, testSpec.getId(), operationalWindowId);
			ContentFrameworkDetail processEssentialElement = null;
			if (latestBand == null && studentMetBluePrintInITI) {
				bandRecommendation = getFirstTestRecommenedBandForFullyMetInITI(trackerStudent, contentArea, allBands, reasons,
						bandRecommendation, bpCriteriasMapForITIAndST);
				if(bandRecommendation == null) {
					message = StudentTrackerHelper.constructCommonMessage("Unable to assign first test in fully met criteria for studentId: ", trackerStudent, 
							orgPoolType, operationalWindowId, null, null, null, null, null);
					writeReason(studentId, message, reasons);
					return null;
				}
			} else {
				Long recommendedBandId = null;
				StudentsTests currentStudentTest = null;
				StudentTrackerTest mostRecentNonWritingSTTest = null;
				if(latestBand != null) {
					currentStudentTest = studentsTestDao.findCompletedByStudentAndTestSession(studentId, latestBand.getTestSessionId());
					mostRecentNonWritingSTTest = studentsTestDao.findMostRecentCompletedNonWritingTestForSingleEE(latestBand.getStudentTrackerId());
				}
				if(mostRecentNonWritingSTTest == null) {
					if(!studentMetBluePrintInITI && (currentStudentTest != null || latestBand == null)) {
						recommendedBandId = currentSurveyBandId;
					} else {
						message = StudentTrackerHelper.constructCommonMessage("Assigned test based on recommended band is not in completed state for studentId: ", trackerStudent,
								orgPoolType, operationalWindowId, null, null, currentSurveyBandId, null, null);
						writeReason(studentId, message, reasons);
						return null;
					}							
				} 
				long bandCount = (latestBand == null) ? 0l: getCountOfBandsByStudentTracker(latestBand.getStudentTrackerId(), testSpec.getId(), operationalWindowId);
				long testSpecMinRequired = testSpec.getMinimumNumberOfEEs();
				if(bandCount<testSpecMinRequired) {
					List<ContentFrameworkDetail> listOfEEsCompletedInST = (latestBand == null)? new ArrayList<ContentFrameworkDetail>()
							:testCollectionDao.getEEsTestedInStudentTracker(latestBand.getStudentTrackerId(), testSpec.getId(), operationalWindowId);
					fillStudentTrackerTestDetails(bpCriteriasMapForITIAndST, listOfEEsCompletedInST);
					if(studentMetBluePrintInITI) {
						processEssentialElement = getRandomEETestedInITI(latestBand, bpCriteriasMapForITIAndST);
					} else {
						processEssentialElement = getEEForITIPartiallyMetScenario(bpCriteriasMapForITIAndST);
					}
					if(processEssentialElement == null) {
						message = StudentTrackerHelper.constructCommonMessage("Unable to find the eligible essential element for studentId: ", trackerStudent,
								orgPoolType, operationalWindowId, null, null, currentSurveyBandId, null, null);
						writeReason(studentId, message, reasons);
						return null;
					}
					Long eesCriteira = processEssentialElement.getCriteriaNumber();
					if(bpCriteriasMapForITIAndST.get(eesCriteira).isWritingCriteria()) {
						if(writingBandId == null) {
							message = StudentTrackerHelper.constructCommonMessage("Survey writing band has not been calculated for studentId: ", trackerStudent,
									null, operationalWindowId, null, null, null, null, null);
							writeReason(studentId, message, reasons);
							return null;
						}
						recommendedBandId = writingBandId;
						List<String> currentLinkageLevelCodes = listOfLinkageLevels(allBands, recommendedBandId);
						processEssentialElement = getWritingBluePrintByLinkageLevel(contentAreaId, trackerStudent.getGradeCode(), currentLinkageLevelCodes, 
								eesCriteira);
					} else {
						if(mostRecentNonWritingSTTest != null) {
							ComplexityBand recommendedBand = getFinalRecommendedBand(mostRecentNonWritingSTTest.getId(), null,
									allBands, reasons, trackerStudent, gradeId, contentArea, TEST_TYPE_ST, mostRecentNonWritingSTTest.getComplexityBandId());
							if(recommendedBand != null) {
								recommendedBandId = recommendedBand.getId();
							}
						}
					}
					if(recommendedBandId != null && processEssentialElement != null) {
						bandRecommendation = getRecommendedStudentTrackerBand(recommendedBandId, (latestBand == null)?SOURCE_TYPE_FIRST_CONTACT : SOURCE_TYPE_STUDENT_TRACKER, 
								processEssentialElement.getId());
					} else {
						message = StudentTrackerHelper.constructCommonMessage("Recommonded band is null, unable to assign test for studentId: ", trackerStudent,
								orgPoolType, operationalWindowId, null, null, currentSurveyBandId, null, null);
						writeReason(studentId, message, reasons);
						return null;
					}
					
				} else {
					message = StudentTrackerHelper.constructCommonMessage("Minimum required EEs met for : studentId: ", trackerStudent, 
							orgPoolType, operationalWindowId, null, null, null, null, testSpec.getId());
					writeReason(studentId, message, reasons);
					checkAndInsertStudentTrackerBluePrintStatus(trackerStudent, operationalWindowId, user);
				}
			}
			
		} else {
			message = StudentTrackerHelper.constructCommonMessage("Survey final band has not been calculated for studentId: ", trackerStudent,
					null, operationalWindowId, null, null, null, null, null);
			logger.debug(message);
			writeReason(studentId, message, reasons);
		} 
		if(bandRecommendation != null)
			bandRecommendation.setOperationalWindowId(operationalWindowId);
		return bandRecommendation;
	}

	private StudentTrackerBand getFirstTestRecommenedBandForFullyMetInITI(StudentTracker trackerStudent, ContentArea contentArea, List<ComplexityBand> allBands,
			List<BatchRegistrationReason> reasons, StudentTrackerBand bandRecommendation, Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST) {
		ContentFrameworkDetail processEssentialElement;
		ItiTestSessionHistory latestITINonWritingTest = itiTestSessionService.getMostRecentlyCompletedNonWringITITestSession(trackerStudent.getStudentId(), contentArea.getId(),
				trackerStudent.getGradeCode(), trackerStudent.getSchoolYear());
		processEssentialElement = getRandomEETestedInITIByCriteria(bpCriteriasMapForITIAndST, 1l);
		String message = StringUtils.EMPTY;
		if(processEssentialElement == null) {
			message = "Fully Met, Unable to find the essential element to process for first testlet for student:" + trackerStudent.getStudentId() 
				+ ", subject: " + contentArea.getId() ;
			writeReason(trackerStudent.getStudentId(), message, reasons);
		}
		if(latestITINonWritingTest == null || latestITINonWritingTest.getStudentsTestsId() == null) {
			message = "Fully Met, Unable to find the latest non writing testlet from ITI to calculate band for first testlet for student:" 
						+ trackerStudent.getStudentId() + ", subject: " + contentArea.getId() + " ITI testsedEEs size in criteria1: " 
						+ bpCriteriasMapForITIAndST.get(1l).getItiTestedEEs().size() + "School Year: " +  trackerStudent.getSchoolYear() + "gradeCode: " + trackerStudent.getGradeCode();
			if(latestITINonWritingTest != null && latestITINonWritingTest.getStudentsTestsId() == null) {
				message = message + " studentstestsId is null";
			}
			writeReason(trackerStudent.getStudentId(), message, reasons);
		}
		if(latestITINonWritingTest != null && latestITINonWritingTest.getStudentsTestsId() != null 
				&& processEssentialElement != null) {
			ComplexityBand recommendedBand = getFinalRecommendedBand(latestITINonWritingTest.getStudentsTestsId(), latestITINonWritingTest.getLinkageLevel(),
					allBands, reasons, trackerStudent, trackerStudent.getGradeCourseId(), contentArea, TEST_TYPE_ITI, null);
			if(recommendedBand != null) {
				bandRecommendation = getRecommendedStudentTrackerBand(recommendedBand.getId(), SOURCE_TYPE_STUDENT_TRACKER, processEssentialElement.getId());
			} else {
				message = "Fully Met, Unable to get band recommandation for first testlet for student:" 
						+ trackerStudent.getStudentId() + ", subject: " + contentArea.getId();
				writeReason(trackerStudent.getStudentId(), message, reasons);
			}
		}
		return bandRecommendation;
	}

	private void fillBluePrintMapDetails(List<BPCriteriaAndGroups> bluePrintList, 
			Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST) {
		for(BPCriteriaAndGroups bpcriteria: bluePrintList) {
			Map<Long, List<ContentFrameworkDetail>> eesByGroup = bpcriteria.getEesByGroup();
			for(ContentFrameworkDetail cfd: bpcriteria.getListOfEEs()) {
				for(Long groupNumber: cfd.getGroupsNumbers()) {
					if(bpcriteria.getEesByGroup().get(groupNumber) == null) {
						eesByGroup.put(groupNumber, new ArrayList<ContentFrameworkDetail>());
					}
					eesByGroup.get(groupNumber).add(cfd);
				}
			}
			if(!bpCriteriasMapForITIAndST.containsKey(bpcriteria.getCriteriaNum())) {
				bpCriteriasMapForITIAndST.put(bpcriteria.getCriteriaNum(), bpcriteria);
			}
		}
	}

	private ContentFrameworkDetail getEEForITIPartiallyMetScenario(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST) {
		ContentFrameworkDetail selectedEE = null;
		Long maxCriteria = bpCriteriasMapForITIAndST.get(FIRST_CRITERIA).getMaxCriteriaNum();
		
		Iterator<StudentTrackerIMPartiallyMetCasesEnum> partiallyMetCasesIterator = Arrays.asList(StudentTrackerIMPartiallyMetCasesEnum.values()).iterator();
		
		do {
			StudentTrackerIMPartiallyMetCasesEnum partiallyMetCase = partiallyMetCasesIterator.next();
			selectedEE = partiallyMetCase.getEligibleEE(bpCriteriasMapForITIAndST, maxCriteria);
		}while(partiallyMetCasesIterator.hasNext() && selectedEE == null);
		
		return selectedEE;
	}

	private List<String> listOfLinkageLevels(List<ComplexityBand> allBands, Long recommendedBandId) {
		List<String> linkageLevelsList = new ArrayList<String>();
		for (ComplexityBand band : allBands) {
			if (band.getId().longValue() == recommendedBandId.longValue()) {
				linkageLevelsList.add(band.getLinkageLevel().getCategoryCode());
			}
		}
		return linkageLevelsList;
	}

	private void fillStudentTrackerTestDetails(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST,
			List<ContentFrameworkDetail> listOfEEsCompletedInST) {
		for(ContentFrameworkDetail stEE : listOfEEsCompletedInST) {
			BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(stEE.getCriteriaNumber());
			List<BPGroupsInfo> bpGroups = bpCriteriaAndGroups.getGrouspInfos();
			bpCriteriaAndGroups.getStudentTrackerTestedEEs().add(stEE.getName());
			for(BPGroupsInfo bpg : bpGroups) {
				if(stEE.getGroupsNumbers().contains(bpg.getGroupNumber())) {
					bpg.incrementNumberOfSTEEsCompleted();
				}
			}
		}
	}

	private void fillITITestDetails(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, List<ContentFrameworkDetail> listOfEEsCompletedInITI) {
		for(ContentFrameworkDetail itiEE:listOfEEsCompletedInITI) {
			BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(itiEE.getCriteriaNumber());
			List<BPGroupsInfo> bpGroups = bpCriteriaAndGroups.getGrouspInfos();
			Map<Long, List<ContentFrameworkDetail>> itiTestedEEsByGroup = bpCriteriaAndGroups.getItiTestedEEsByGroup();
			bpCriteriaAndGroups.getItiTestedEEs().add(itiEE);
			for(BPGroupsInfo bpg : bpGroups) {
				if(itiEE.getGroupsNumbers().contains(bpg.getGroupNumber())) {
					bpg.incrementNumberOfITIEEsCompleted();
					if(!itiTestedEEsByGroup.containsKey(bpg.getGroupNumber())) {
						itiTestedEEsByGroup.put(bpg.getGroupNumber(), new ArrayList<ContentFrameworkDetail>());
					}
						itiTestedEEsByGroup.get(bpg.getGroupNumber()).add(itiEE);
				}
			}
		}
	}
	
	private ContentFrameworkDetail getRandomEETestedInITI(StudentTrackerBand latestBand, 
			Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST) {
		Long currentCriteriaNum = latestBand.getCriteriaNumber();
		Long maxCriteriaInBluePrint = bpCriteriasMapForITIAndST.get(FIRST_CRITERIA).getMaxCriteriaNum();
		List<ContentFrameworkDetail> listOfEEsToProcess = new ArrayList<ContentFrameworkDetail>();		
		for(Long nextCriteria = currentCriteriaNum + 1; nextCriteria <= maxCriteriaInBluePrint; nextCriteria++) {
			Set<String> studentTrackerTestedEEs = bpCriteriasMapForITIAndST.get(nextCriteria).getStudentTrackerTestedEEs();
			if(studentTrackerTestedEEs.size() <= 0) {
				listOfEEsToProcess = getItiTestedEEsByCriteria(bpCriteriasMapForITIAndST, nextCriteria);
				if(listOfEEsToProcess.size() > 0) {
					return listOfEEsToProcess.get(randomGenerator.nextInt(listOfEEsToProcess.size()));
				}
			}
		}
		
		if(listOfEEsToProcess.size() == 0) {
			SetITIEEsFromAllCriteriasAndGroups(bpCriteriasMapForITIAndST, listOfEEsToProcess);
		}
		
		if(listOfEEsToProcess.size() == 0) {
			logger.debug("validBluePrintEssentialElementsList size is 0");
			return null;
		}
		return listOfEEsToProcess.get(randomGenerator.nextInt(listOfEEsToProcess.size()));
	}

	private void addListOfITIEEsToProcess(List<ContentFrameworkDetail> listOfEEsToProcess, List<ContentFrameworkDetail> listOfITIEEsToProcess,
			Set<String> studentTrackerTestedEEs) {
		for(ContentFrameworkDetail itiEE: listOfITIEEsToProcess) {
			if(!studentTrackerTestedEEs.contains(itiEE.getName())) {
				listOfEEsToProcess.add(itiEE);
			}
		}
	}

	private void SetITIEEsFromAllCriteriasAndGroups(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST,
			List<ContentFrameworkDetail> listOfEEsToProcess) {
		for(Long criteria : bpCriteriasMapForITIAndST.keySet()) {
			List<ContentFrameworkDetail> listOfITIEEsToProcess = new ArrayList<ContentFrameworkDetail>();
			BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteria);
			List<Long> groupsNotMetInSt = bpCriteriaAndGroups.getListOfGroupsNotMetInST();
			Set<String> studentTrackerTestedEEs = bpCriteriaAndGroups.getStudentTrackerTestedEEs();
			if(groupsNotMetInSt.size() > 0) {
				for(Long groupNum : groupsNotMetInSt) {
					listOfITIEEsToProcess.addAll(bpCriteriaAndGroups.getItiTestedEEsByGroup().get(groupNum));
				}
				addListOfITIEEsToProcess(listOfEEsToProcess, listOfITIEEsToProcess, studentTrackerTestedEEs);
			}
		}
	}

	private ComplexityBand getFinalRecommendedBand(Long studentsTestsId, String linkageLevel, List<ComplexityBand> allBands,
			List<BatchRegistrationReason> reasons, StudentTracker trackerStudent, Long gradeId, ContentArea contentArea, String testType, Long processComplexityBandId) {
		String message = StringUtils.EMPTY;
		ComplexityBand recommendedBand = null;
		Long studentId = trackerStudent.getStudentId();
		try {
			Map<String, List<StudentsResponses>> responsesGroupedByEEAndLinkageMap = studentsResponsesDao.findResponsesByStudentTest(studentsTestsId);
			// NOTE: getBandByCategoryName excludes writing bands! 
			// Should be fine, because its only used for ITI tests, which base recommended band off of NON-writing only 
			ComplexityBand band = StringUtils.equalsIgnoreCase(testType, TEST_TYPE_ITI) ? getBandByCategoryName(allBands, linkageLevel, contentArea.getAbbreviatedName())
					: getBandById(allBands, processComplexityBandId);
			if (responsesGroupedByEEAndLinkageMap != null && responsesGroupedByEEAndLinkageMap.size() > 0) {
				logger.debug("There are student responses existed for studentstestsid: " + studentsTestsId 
						+ " , studentId: " + trackerStudent.getStudentId() + ", contentArea:" + contentArea.getId() 
						+ ", Grade: " + gradeId);
				String currentLinkageLevelCode = band.getLinkageLevel().getCategoryCode();
				Long finalRecoLevel = getFinalRecommendedLevel(reasons, studentId, gradeId,
						contentArea.getId(), responsesGroupedByEEAndLinkageMap,
						new Long(band.getBandCode()), currentLinkageLevelCode, contentArea.getAbbreviatedName(), allBands);
				recommendedBand = getBandByLinkageLevel(allBands, finalRecoLevel, contentArea.getAbbreviatedName());
				if(recommendedBand == null) {
					logger.debug("Recommanded band is null for the Student: " + trackerStudent.getStudentId() 
						+ " Subject: " + contentArea.getId() + " finalRecoLevel: " + finalRecoLevel + " currentLinkageLevelCode: " + currentLinkageLevelCode);
				}
			} else {
				Long finalRecoLevel = new Long(band.getBandCode());
				if(finalRecoLevel.longValue()>0) {
					finalRecoLevel=finalRecoLevel-1L;
				}
				recommendedBand = getBandByLinkageLevel(allBands, finalRecoLevel, contentArea.getAbbreviatedName());
				if(recommendedBand == null) {
					logger.debug("Recommanded band is null for the Student: " + trackerStudent.getStudentId() 
						+ " Subject: " + contentArea.getId() + " finalRecoLevel: " + finalRecoLevel);
				}
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			message = StudentTrackerHelper.constructCommonMessage("System Error : studentId: ", trackerStudent, 
					null, null, null, e.getMessage(), null, null, null);
			writeReason(studentId, message, reasons);
		}
		return recommendedBand;
	}

	private ContentFrameworkDetail getRandomEETestedInITIByCriteria(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, long criteriaNumber) {
		List<ContentFrameworkDetail> validBluePrintEssentialElementsList = getItiTestedEEsByCriteria(bpCriteriasMapForITIAndST, criteriaNumber);
		if(validBluePrintEssentialElementsList.size()==0) {
			logger.debug("validBluePrintEssentialElementsList size is 0");
			return null;
		} else {
			return validBluePrintEssentialElementsList.get(randomGenerator.nextInt(validBluePrintEssentialElementsList.size()));
		}		
	}
	
	private List<ContentFrameworkDetail> getItiTestedEEsByCriteria(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST, long criteriaNumber) {
		List<ContentFrameworkDetail> validBluePrintEssentialElementsList = new ArrayList<ContentFrameworkDetail>();
		validBluePrintEssentialElementsList = bpCriteriasMapForITIAndST.get(criteriaNumber).getItiTestedEEs();		
		return validBluePrintEssentialElementsList;
	}
	
	private boolean isStudentFullyMetBluePrintInITI(Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST) {
		for(Long criteriaNumber : bpCriteriasMapForITIAndST.keySet()) {
			BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteriaNumber);
			if(!bpCriteriaAndGroups.isCriteriaRequirmentMetForITI()) {
				return false;
			}
		}
		return true;
	}
	
	private StudentTrackerBand getLatestTrackerBandWithTestSession(StudentTracker trackerStudent, Long testSpecId, Long operationalWindowId) {
		if(trackerStudent.getId() == null || testSpecId == null) {
			return null;
		} else {
			return studentTrackerBandDao.selectLatestWithTestSessionByStudentTracker(trackerStudent.getId(), testSpecId, operationalWindowId);
		}
	}
	
	private ComplexityBand getBandById(List<ComplexityBand> allBands, Long bandId) {
		for (ComplexityBand band : allBands) {
			if (band.getId().longValue() == bandId.longValue()) {
				return band;
			}
		}
		return null;
	}
	
	/**
	 * DO NOT USE WITH WRITING BANDS!
	 * 
	 * @param allBands
	 * @param categoryName
	 * @param contentAreaAbbreviatedName
	 * @return ComplexityBand
	 */
	private ComplexityBand getBandByCategoryName(List<ComplexityBand> allBands, String categoryName, String contentAreaAbbreviatedName) {
		List<ComplexityBand> bandsByContentArea = StudentTrackerHelper.getBandsByContentArea(allBands, contentAreaAbbreviatedName);
		for (ComplexityBand band : bandsByContentArea) {
			if (band.getLinkageLevel().getCategoryName().equals(categoryName)
					&& !(StringUtils.equalsIgnoreCase(CommonConstants.COMPLEXITY_BAND_EMERGENT, band.getBandName()) 
					|| StringUtils.equalsIgnoreCase(CommonConstants.COMPLEXITY_BAND_CONVENTIONAL, band.getBandName()))) {
				return band;
			}
		}
		return null;
	}
	
	private ComplexityBand getBandByLinkageLevel(List<ComplexityBand> allBands, Long levelCode, String contentAreaAbbreviatedName) {
		List<ComplexityBand> bandsByContentArea = StudentTrackerHelper.getBandsByContentArea(allBands, contentAreaAbbreviatedName);
		for (ComplexityBand band : bandsByContentArea) {
			if (band.getBandCode().equals(String.valueOf(levelCode)) 
					&& !(StringUtils.equalsIgnoreCase(CommonConstants.COMPLEXITY_BAND_EMERGENT, band.getBandName()) 
					|| StringUtils.equalsIgnoreCase(CommonConstants.COMPLEXITY_BAND_CONVENTIONAL, band.getBandName()))) {
				return band;
			}
		}
		return null;
	}
	
	private Map<String, ProportionMetrics> getProportionMetrics(Long gradeId, String essentialElementCode, String linkageLevelCode) {
		// first pull for gradeband if nothing then find for grade
		List<ProportionMetrics> proportionMetricList = proportionMetricsDao.getAllProportionMetricsByGradeBandEELevel(gradeId, essentialElementCode, linkageLevelCode);
		if (CollectionUtils.isEmpty(proportionMetricList)) {
			proportionMetricList = proportionMetricsDao.getAllProportionMetricsByGradeEELevel(gradeId, essentialElementCode, linkageLevelCode);
		}
		Map<String, ProportionMetrics> proportionMetricMap = new HashMap<String, ProportionMetrics>();
		if (proportionMetricList != null && proportionMetricList.size() > 0) {
			for (ProportionMetrics pmetric : proportionMetricList) {
				proportionMetricMap.put(gradeId + "-" + essentialElementCode + "-" + linkageLevelCode, pmetric);
			}
		}
		return proportionMetricMap;
	}

	@Override
	public List<StudentTracker> getUntrackedStudents(Organization contractingOrganization, ContentArea contentArea, Long operationalTestWindowId, Integer offset, Integer limitCount) {
		return studentTrackerDao.getUntrackedStudents(contractingOrganization.getCurrentSchoolYear(), contentArea,
				contractingOrganization.getId(), contractingOrganization.getAssessmentProgramId(), operationalTestWindowId, offset, limitCount);
	}
	
	@Override
	public List<StudentTracker> getOnlyUntrackedStudentsFromStudentTracker(Organization contractingOrganization, ContentArea contentArea, Long operationalTestWindowId, Integer offset, Integer limitCount) {
		return studentTrackerDao.getOnlyUntrackedStudents(contractingOrganization.getCurrentSchoolYear(), contentArea,
				contractingOrganization.getId(), contractingOrganization.getAssessmentProgramId(), operationalTestWindowId,  offset, limitCount);
	}	

	@Override
	public List<StudentTracker> getTrackedStudents(Organization contractingOrganization, ContentArea contentArea, Long operationalTestWindowId, boolean isInterim, 
			Integer offset, Integer limitCount ) {
		return studentTrackerDao.getTrackedStudents(contractingOrganization.getCurrentSchoolYear(), contentArea.getId(), contractingOrganization.getId(),
				contractingOrganization.getAssessmentProgramId(), contractingOrganization.getPoolType(), operationalTestWindowId, isInterim, offset, limitCount );
	}
	 
	@Override
	public StudentTracker getTrackerByStudentAndContentArea(Long studentId, Long contentAreaId, Long operationalWindowId, Long courseId, Long schoolYear) {
		return studentTrackerDao.selectByStudentAndContentArea(studentId, contentAreaId, operationalWindowId, courseId, schoolYear);
	}
	
	@Override
	public int getCountOfBandsByStudentTracker(Long studentTrackerId, Long testSpecificationId, Long operationalWindowId) {
		return studentTrackerBandDao.countBandsByStudentTracker(studentTrackerId, testSpecificationId, operationalWindowId);
	}
	
	@Override
	public List<String> getEEsByStudentTracker(Long studentTrackerId, Long testSpecificationId, Long operationalWindowId) {
		List<String> studentTrackerBandEEs = new ArrayList<String>();
		if(studentTrackerId!=null && testSpecificationId!=null) {
			studentTrackerBandEEs = studentTrackerBandDao.selectEEsByStudentTracker(studentTrackerId, testSpecificationId, operationalWindowId);	
		}
		
		return studentTrackerBandEEs;
	}
	
	@Override
	public List<TestSpecification> getTestSpecByStudentTracker(Long studentTrackerId, Long operationalWindowId) {
		return testSpecificationDao.findByStudentTracker(studentTrackerId, operationalWindowId);
	}
	
	@Override
	public TestSpecification getTestSpecByPoolGradeContentArea(String poolType, String gradeCode, Long contentAreaId, Long operationalWindowId) {
		TestSpecification testSpec = testSpecificationDao.findByPoolGradBandContentArea(poolType, gradeCode, contentAreaId, operationalWindowId);
		if(testSpec == null) {
			 testSpec = testSpecificationDao.findByPoolGradeOrCourseContentArea(poolType, gradeCode, contentAreaId, operationalWindowId);
		}
		return testSpec;
	}
		
	private List<BPCriteriaAndGroups> getBluePrintByContentAreaAndGrade(Long contentAreaId, String gradeCode) {		
		return bluePrintMapper.getBluePrintCriteriasByGradeAndSub(contentAreaId, gradeCode);
	}
	
	
	private ContentFrameworkDetail getWritingBluePrintByLinkageLevel(Long contentAreaId, String gradeCode, List<String> linkageLevelCodes, Long criteriaNum) {
		List<ContentFrameworkDetail> writingEEs = bluePrintMapper.selectWritingByLinkageLevel(contentAreaId, gradeCode, linkageLevelCodes, criteriaNum);
		if(writingEEs.size() > 0) {
			return writingEEs.get(randomGenerator.nextInt(writingEEs.size()));
		}
		return null;
	}	
	
	@Override
	public List<String> getContentCodesByTestSpecAndRandking(Long testSpecificationId, Long ranking) {
		return testSpecificationDao.findByTestSpecAndRanking(testSpecificationId, ranking);
	}
	
	@Override
	public List<StudentTrackerBand> getStudentTrackerBandByStudentId(Long studentId) {
		return studentTrackerBandDao.selectByStudentId(studentId);
	}	
		
	@Override
	public List<StudentTrackerBand> getStudentTrackerBandByStudentIdWithActiveOTW(Long studentId) {
		return studentTrackerBandDao.selectByStudentIdWithActiveOTW(studentId);
	}		
	
	@Override
	public int clearTestSessionByStudentIdAndTestSessionId(Long studentId, Long testSessionId, Long userId) {
		return studentTrackerBandDao.clearTestSessionByStudentIdAndTestSessionId(studentId, testSessionId, userId);
	}
	
	@Override
	public StudentTrackerBand getLatestTrackerBand(Long studentTrackerId) {
		return studentTrackerBandDao.selectLatestByStudentTracker(studentTrackerId);
	}	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertStudentTrackerBluePrintStatus(StudentTrackerBluePrintStatus studentTrackerBluePrintStatus) {
		return  studentTrackerBluePrintStatusMapper.insert(studentTrackerBluePrintStatus);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StudentTrackerBluePrintStatus selectStudentTrackerBluePrintStatus(StudentTrackerBluePrintStatus studentTrackerBluePrintStatus) {
		return  studentTrackerBluePrintStatusMapper.selectByPrimaryKey(studentTrackerBluePrintStatus);
	}	
	
	private String getGradeCourseAbbr(Roster roster, String poolType, String gradeCode) {
		logger.debug("returning getGradeCourseAbbr for poolType: "+poolType);
		if(MULTIEEOFC.equals(poolType)) {
			return roster.getCourse().getAbbreviatedName();
		} else {
			return gradeCode;
		}
	}
	
	private Roster getRoster(StudentTracker trackerRecord, String poolType, Long contentAreaId, Long courseId) {
		List<Roster> rosters = rosterService.getRostersByContentAreaAndEnrollment(contentAreaId, trackerRecord.getEnrollment());
		
		List<Roster> finalRosters = new ArrayList<Roster>();
		//if MULTIEEOFC look for course roster
		if(MULTIEEOFC.equals(poolType)) {
			for(Roster roster: rosters) {
				if(roster.getStateCoursesId() != null && courseId != null && courseId.equals(roster.getStateCoursesId())) {
					finalRosters.add(roster);
				}
			}
		} else {
			finalRosters.addAll(rosters);
		}
		
		//check if any proctor roster exists
		for(Roster roster: finalRosters) {
			if(roster.getSourceType() != null && roster.getSourceType().equals("TEST")) {
				return roster;
			}
		}
		
		if(!finalRosters.isEmpty()) {
			//if no proctor return one of the roster
			return finalRosters.get(0);
		}
		return null;
	}
	
	private void writeReason(Long studentId, String msg, List<BatchRegistrationReason> reasons) {
		BatchRegistrationReason reason = new BatchRegistrationReason();
		reason.setStudentId(studentId);
		reason.setReason(msg);
		reasons.add(reason);
	}
	
	private Enrollment getEnrollment(StudentTracker trackerRecord, Long contentAreaId, Organization contractingOrganization) {
		Enrollment enrollment = enrollmentService.getEnrollmentByStudentId(trackerRecord.getStudentId(), contractingOrganization.getId(), 
				contractingOrganization.getCurrentSchoolYear(), contentAreaId);
		return enrollment;
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findStudentTrackerBandsByStudentAndTestSession(Long studentId, List<Long> inactivatedTestSessionIds, boolean activeFlag) {
		return studentTrackerBandDao.findStudentTrackerBandsByStudentAndTestSession(studentId, inactivatedTestSessionIds, activeFlag);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deactivate(List<Long> studentTrackerBandIdsForDeactivation, Long modifiedUserId) {
		studentTrackerBandDao.inactiveByIds(new HashSet<Long>(studentTrackerBandIdsForDeactivation), modifiedUserId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void reactivate(List<Long> studentTrackerBandIdsForDeactivation, Long modifiedUserId) {
		studentTrackerBandDao.reactivateByIds(new HashSet<Long>(studentTrackerBandIdsForDeactivation), modifiedUserId);
	}	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void changeStatusToUntrackedByIds(Set<Long> studentTrackerIds, Long modifiedUserId) {
		studentTrackerDao.changeStatusToUnTrackedByIds(studentTrackerIds, modifiedUserId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentTrackerBand> getStudentTrackerBandsByIds(List<Long> studentTrackerBandIds) {
		return studentTrackerBandDao.selectByPrimaryKeys(studentTrackerBandIds);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteCompletedStudentTrackerBlueprintStatus(Long studentTrackerId, Long operationalWindowId) {
		return studentTrackerBluePrintStatusMapper.deleteCompletedStudentTrackerBlueprintStatus(studentTrackerId, operationalWindowId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentTrackerBand> getStudentTrackerBandByFieldTestEEs(Long studentTrackerId,
			List<ContentFrameworkDetail> listOfContentCodesInFieldTests) {
		return studentTrackerBandDao.getStudentTrackerBandByFieldTestEEs(studentTrackerId, listOfContentCodesInFieldTests);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean didStudentMeetSpecificBlueprintCriteria(Long studentId, Long contentAreaId, String gradeCode, int schoolYear, Long criteriaNumber){
		List<BPCriteriaAndGroups> bluePrintList = getBluePrintByContentAreaAndGrade(contentAreaId, gradeCode);
		Map<Long, BPCriteriaAndGroups> bpCriteriasMapForITIAndST = new HashMap<Long, BPCriteriaAndGroups>();
		List<ContentFrameworkDetail> listOfEEsCompletedInITI = testCollectionDao.
				getStudentITITestsForSubGradeAndCriteria(criteriaNumber, gradeCode, contentAreaId, studentId, schoolYear);
		
		fillBluePrintMapDetails(bluePrintList, bpCriteriasMapForITIAndST);
		fillITITestDetails(bpCriteriasMapForITIAndST, listOfEEsCompletedInITI);
		BPCriteriaAndGroups bpCriteriaAndGroups = bpCriteriasMapForITIAndST.get(criteriaNumber);
		
		return bpCriteriaAndGroups != null && bpCriteriaAndGroups.isCriteriaRequirmentMetForITI();
	}
}
