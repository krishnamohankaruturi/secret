/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.RubricReportDto;
import edu.ku.cete.domain.content.Foil;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.TaskVariantsFoils;
import edu.ku.cete.domain.content.TaskVariantsFoilsExample;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.content.TestExample;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.content.TestSectionExample;
import edu.ku.cete.domain.content.TestSectionsTaskVariants;
import edu.ku.cete.domain.lm.LmNode;
import edu.ku.cete.domain.lm.TaskVariantLearningMapNode;
import edu.ku.cete.domain.lm.TaskVariantLearningMapNodeExample;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.domain.test.StimulusVariant;
import edu.ku.cete.domain.test.StimulusVariantExample;
import edu.ku.cete.domain.test.TaskVariantContentFrameworkDetail;
import edu.ku.cete.domain.test.TaskVariantContentFrameworkDetailExample;
import edu.ku.cete.lm.webservice.client.LMWebClientConfiguration;
import edu.ku.cete.model.RubricCategoryDao;
import edu.ku.cete.model.TaskVariantDao;
import edu.ku.cete.model.TaskVariantsFoilsDao;
import edu.ku.cete.model.TestCollectionDao;
import edu.ku.cete.model.TestDao;
import edu.ku.cete.model.TestSectionDao;
import edu.ku.cete.model.TestSectionsTaskVariantsDao;
import edu.ku.cete.model.lm.TaskVariantLearningMapNodeDao;
import edu.ku.cete.model.test.FoilDao;
import edu.ku.cete.model.test.StimulusVariantDao;
import edu.ku.cete.model.test.TaskVariantContentFrameworkDetailDao;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.AARTCollectionUtil;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	/**
	 * testSectionDao.
	 */
	@Autowired
	private TestSectionDao testSectionDao;
	/**
	 * testSectionsTaskVariantsDao.
	 */
	@Autowired
	private TestSectionsTaskVariantsDao testSectionsTaskVariantsDao;

	/**
	 * taskVariantDao.
	 */
	@Autowired
	private TaskVariantDao taskVariantDao;
	/**
	 * taskVariantsFoilsDao.
	 */
	@Autowired
	private TaskVariantsFoilsDao taskVariantsFoilsDao;
	/**
	 * stimulusVariantDao.
	 */
	@Autowired
	private StimulusVariantDao stimulusVariantDao;

	@Autowired
	private RubricCategoryDao rubricCategoryDao;

	/**
	 * Task variant content framework detail DAO.
	 */
	@Autowired
	private TaskVariantContentFrameworkDetailDao taskVariantContentFrameworkDetailDao;
	/**
	 * sectionDao.
	 */
	@Autowired
	private TestSectionDao sectionDao;
	@Autowired
	private TestCollectionDao testCollectionDao;
	@Autowired
	private LMWebClientConfiguration lmWebClientConfiguration;
	@Autowired
	private TaskVariantLearningMapNodeDao taskVariantLearningMapNodeDao;

	@Autowired
	private FoilDao foilDao;

	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);;

	/**
	 *
	 * @param keyword
	 *            {@link String}
	 * @param assessmentProgramId
	 *            long
	 * @return List<Test>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> findByKeyword(final String keyword, final long assessmentProgramId) {
		String newKeyword = "%" + keyword + "%";

		return testDao.findByKeyword(newKeyword, assessmentProgramId);
	}

	/**
	 *
	 * @param testId
	 *            long
	 * @return {@link Test}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Test findById(final Long testId) {
		Test test = null;
		if (testId != null) {
			test = testDao.findById(testId);
		}
		return test;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public final List<TestSection> findTestSectionByTest(final Long testId) {

		if (testId != null) {
			TestSectionExample testSectionExample = new TestSectionExample();
			TestSectionExample.Criteria criteria = testSectionExample.createCriteria();
			criteria.andTestIdEqualTo(testId);
			List<TestSection> testSections = sectionDao.selectByExample(testSectionExample);

			return testSections;
		}

		return null;
	}

	/**
	 * Optimized to not expand the width or depth of the result set. Selecting
	 * in multiple steps to show tasks without foils, etc. sections with no
	 * items etc..
	 * 
	 * @param testId
	 *            long
	 * @return {@link Test}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public final Test findTestAndSectionById(final Long testId) {
		Test test = null;
		if (testId != null) {
			test = testDao.findById(testId);
		}
		if (test != null) {
			TestSectionExample testSectionExample = new TestSectionExample();
			TestSectionExample.Criteria criteria = testSectionExample.createCriteria();
			criteria.andTestIdEqualTo(testId);
			List<TestSection> testSections = sectionDao.selectByExample(testSectionExample);
			test.setTestSections(testSections);

			List<TestSectionsTaskVariants> testSectionsTaskVariantsList = null;
			if (testSections != null && CollectionUtils.isNotEmpty(testSections)) {
				testSectionsTaskVariantsList = taskVariantDao
						.selectByTestSection(AARTCollectionUtil.getIds(testSections));
			}

			if (testSectionsTaskVariantsList != null && CollectionUtils.isNotEmpty(testSectionsTaskVariantsList)) {
				TaskVariantsFoilsExample taskVariantsFoilsExample = new TaskVariantsFoilsExample();
				TaskVariantsFoilsExample.Criteria taskVariantsFoilsCriteria = taskVariantsFoilsExample.createCriteria();
				taskVariantsFoilsCriteria
						.andTaskVariantIdIn(AARTCollectionUtil.getIds(testSectionsTaskVariantsList, 3));
				taskVariantsFoilsExample.setOrderByClause("tvf.taskvariantid, tvf.responseorder");
				List<TaskVariantsFoils> taskVariantsFoilsList = taskVariantsFoilsDao
						.selectExtendedByExample(taskVariantsFoilsExample);
				// set the foils
				for (TestSectionsTaskVariants testSectionsTaskVariants : testSectionsTaskVariantsList) {
					testSectionsTaskVariants.getTaskVariant().setApplicableTaskVariantsFoils(taskVariantsFoilsList);
				}
				// set the task variants to the section.
				for (TestSection testSection : testSections) {
					testSection.setApplicableTestSectionsTaskVariants(testSectionsTaskVariantsList);
				}

			}
		}
		return test;
	}

	/**
	 *
	 * @return List<Test>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> getAll() {
		return testDao.getAll();
	}

	/**
	 * @return List<Test>
	 * @param example
	 *            {@link TestExample}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> selectByExample(TestExample example) {
		return testDao.selectByExample(example);
	}

	/**
	 * @param example
	 *            {@link TestExample}
	 * @param assessmentProgramId
	 *            long
	 * @return List<Test>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> selectByExampleAndAssessmentProgram(TestExample example, long assessmentProgramId) {
		return testDao.selectByExampleAndAssessmentProgram(example, assessmentProgramId);
	}

	/**
	 * Optimized to not expand the width or depth of the result set. Selecting
	 * in multiple steps to show tasks without standards, foils, etc.
	 * 
	 * @param testCollectionId
	 *            long
	 * @param testStatusId
	 *            long
	 * @return List<Test>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final TestCollection findContentByTestCollectionAndStatus(long testCollectionId, long testId,
			long testStatusId) {
		TestCollection testCollection = testCollectionDao.selectByPrimaryKey(testCollectionId);
		List<Test> tests = testDao.findByTestAndStatus(testId, testStatusId);
		TestSectionExample testSectionExample;
		testSectionExample = new TestSectionExample();
		List<TestSection> testSections = null;
		List<TaskVariantsFoils> taskVariantsFoilsList = null;
		List<TestSectionsTaskVariants> testSectionsTaskVariantsList = null;
		// INFO Preview allowed or disallow is taken care much earlier.
		if (tests != null && CollectionUtils.isNotEmpty(tests)) {
			testCollection.setTests(tests);
			TestSectionExample.Criteria testSectionCriteria = testSectionExample.createCriteria();
			testSectionCriteria.andTestIdIn(AARTCollectionUtil.getIds(tests));
			testSectionExample.setOrderByClause("externalid");
			testSections = testSectionDao.selectByExample(testSectionExample);
			if (tests != null && CollectionUtils.isNotEmpty(tests) && testSections != null
					&& CollectionUtils.isNotEmpty(testSections)) {
				for (Test test : tests) {
					test.setApplicableTestSections(testSections);
				}
			}
		}
		List<Long> stimulusVariantIds = new ArrayList<Long>();
		if (testSections != null && CollectionUtils.isNotEmpty(testSections)) {
			for (TestSection testSection : testSections) {
				/*
				 * List<Long> contextStimulusIds =
				 * testSectionDao.getContextStimulsIdByTestSectionId(testSection
				 * .getId()); if( contextStimulusIds != null &&
				 * contextStimulusIds.size() > 0 && contextStimulusIds.get(0) !=
				 * null )
				 * testSection.setContextStimulusId(contextStimulusIds.get(0));
				 * 
				 * if (testSection != null && testSection.getContextStimulusId()
				 * != null) {
				 * stimulusVariantIds.add(testSection.getContextStimulusId()); }
				 */
				// }
				/*
				 * if (stimulusVariantIds != null &&
				 * CollectionUtils.isNotEmpty(stimulusVariantIds)) {
				 * StimulusVariantExample stimulusVariantExample = new
				 * StimulusVariantExample(); StimulusVariantExample.Criteria
				 * stimulusVariantExampleCriteria =
				 * stimulusVariantExample.createCriteria();
				 * stimulusVariantExampleCriteria.andIdIn(stimulusVariantIds);
				 * List<StimulusVariant> stimulusVariants =
				 * stimulusVariantDao.selectByExample(stimulusVariantExample);
				 * for (TestSection testSection : testSections) { if
				 * (testSection != null && testSection.getContextStimulusId() !=
				 * null) { testSection.setContextStimulus(stimulusVariants); } }
				 * }
				 */
				List<Long> testSectionIds = new ArrayList<Long>();
				testSectionIds.add(testSection.getId());
				// TODO set the content framework detail along with it.
				testSectionsTaskVariantsList = taskVariantDao.selectByTestSection(testSectionIds);

				if (testSectionsTaskVariantsList != null && CollectionUtils.isNotEmpty(testSectionsTaskVariantsList)) {
					TaskVariantsFoilsExample taskVariantsFoilsExample = new TaskVariantsFoilsExample();
					TaskVariantsFoilsExample.Criteria taskVariantsFoilsCriteria = taskVariantsFoilsExample
							.createCriteria();
					taskVariantsFoilsCriteria
							.andTaskVariantIdIn(AARTCollectionUtil.getIds(testSectionsTaskVariantsList, 3));
					taskVariantsFoilsExample.setOrderByClause("tvf.taskvariantid, tvf.responseorder");
					taskVariantsFoilsList = taskVariantsFoilsDao.selectExtendedByExample(taskVariantsFoilsExample);

					TaskVariantContentFrameworkDetailExample taskVariantContentFrameworkDetailExample = new TaskVariantContentFrameworkDetailExample();
					TaskVariantContentFrameworkDetailExample.Criteria taskVariantContentFrameworkDetailExampleCriteria = taskVariantContentFrameworkDetailExample
							.createCriteria();
					taskVariantContentFrameworkDetailExampleCriteria
							.andTaskvariantidIn(AARTCollectionUtil.getIds(testSectionsTaskVariantsList, 3));
					taskVariantContentFrameworkDetailExampleCriteria.andIsprimaryEqualTo(true);

					List<TaskVariantContentFrameworkDetail> taskVariantContentFrameworkDetails = taskVariantContentFrameworkDetailDao
							.selectExtendedByExample(taskVariantContentFrameworkDetailExample);

					Long prevContextStimulsId = 0L;
					// set the foils and content standards
					for (TestSectionsTaskVariants testSectionsTaskVariants : testSectionsTaskVariantsList) {

						testSectionsTaskVariants.getTaskVariant().setApplicableTaskVariantsFoils(taskVariantsFoilsList);
						testSectionsTaskVariants.getTaskVariant()
								.setPrimaryContentFrameworkDetail(taskVariantContentFrameworkDetails);
						testSectionsTaskVariants.getTaskVariant().setScoringData(testSectionsTaskVariants.getTaskVariant().getScoringData());
						Long contextStimulsId = testSectionsTaskVariants.getTaskVariant().getContextStimulusId();

						if (contextStimulsId != null
								&& contextStimulsId.longValue() != prevContextStimulsId.longValue()) {
							StimulusVariant stimulusVariant = stimulusVariantDao.selectByPrimaryKey(contextStimulsId);
							testSectionsTaskVariants.setContextStimulus(stimulusVariant);
							testSection.setContextStimulus(stimulusVariant);
							prevContextStimulsId = contextStimulsId;
						}
					}
					// set the taskvariants to the section.
					// for (TestSection testSection:testSections) {
					testSection.setApplicableTestSectionsTaskVariants(testSectionsTaskVariantsList);
					// }
				}
			}

		}
		return testCollection;
	}

	/**
	 * @param testCollectionId
	 *            long
	 * @param testStatusId
	 *            long
	 * @return List<Test>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> findByTestCollectionAndStatus(long testCollectionId, long testStatusId) {
		return testDao.findByTestCollectionAndStatus(testCollectionId, testStatusId);
	}

	/**
	 * If fetching the node information fails roll back should NOT happen.
	 * 
	 * @param nodeKeys
	 * @return {@link Set}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Set<LmNode> findNodeInformation(Set<String> nodeKeys) {
		logger.warn("Starting to fetch node info for " + nodeKeys);
		Set<LmNode> lmNodes = new HashSet<LmNode>();
		try {
			if (nodeKeys != null && CollectionUtils.isNotEmpty(nodeKeys)) {
				logger.debug("Getting lm nodes for " + nodeKeys);
				lmNodes = lmWebClientConfiguration.getNodesByKey(nodeKeys);
			}
		} catch (Exception e) {
			logger.error("Exception in getting node information for node ids ", e);
		}
		return lmNodes;
	}

	/**
	 * If fetching the node information fails roll back should NOT happen.
	 * 
	 * @param nodeKeys
	 * @return {@link Set}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Set<LmNode> findNodeInformationForNodeResponse(Set<String> nodeKeys) {
		logger.warn("Starting to fetch node info for " + nodeKeys);
		Set<LmNode> lmNodes = new HashSet<LmNode>();
		try {
			if (nodeKeys != null && CollectionUtils.isNotEmpty(nodeKeys)) {
				logger.debug("Getting lm nodes for " + nodeKeys);
				lmNodes = lmWebClientConfiguration.getNodesByKeyNodeResponse(nodeKeys);
			}
		} catch (Exception e) {
			logger.error("Exception in getting node information for node ids ", e);
		}
		return lmNodes;
	}

	/**
	 * @param testCollection
	 * @param nodeKeys
	 * @return
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final TestCollection findNodeInformation(TestCollection testCollection, Set<String> nodeKeys) {
		Set<LmNode> lmNodes = findNodeInformation(nodeKeys);
		if (nodeKeys != null && CollectionUtils.isNotEmpty(nodeKeys) && lmNodes != null
				&& CollectionUtils.isNotEmpty(lmNodes)) {
			for (Test test : testCollection.getTests()) {
				for (TestSection testSection : test.getTestSections()) {
					List<TestSectionsTaskVariants> testSectionsTaskVariantsList = testSection
							.getTestSectionsTaskVariants();
					if (testSectionsTaskVariantsList != null
							&& CollectionUtils.isNotEmpty(testSectionsTaskVariantsList)) {
						for (TestSectionsTaskVariants testSectionsTaskVariants : testSectionsTaskVariantsList) {
							testSectionsTaskVariants.getTaskVariant().updateNodeInfo(lmNodes);
							logger.debug("Set lm nodes for " + testSectionsTaskVariants.getTaskVariantId() + " as "
									+ testSectionsTaskVariants.getTaskVariant().getLmNodeMap());
						}
					}
				}
			}
		}
		return testCollection;
	}

	/**
	 * Optimized to not expand the width or depth of the result set. Selecting
	 * in multiple steps to show tasks without standards, foils, etc.
	 * 
	 * @param testCollectionId
	 *            long
	 * @param testStatusId
	 *            long
	 * @return List<Test>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final TestCollection findNodeInfoByTestCollectionAndStatus(long testCollectionId, long testStatusId) {
		TestCollection testCollection = null;
		testCollection = testCollectionDao.selectByPrimaryKey(testCollectionId);
		List<Test> tests = testDao.findByTestCollectionAndStatus(testCollectionId, testStatusId);
		testCollection.setTests(tests);
		TestSectionExample testSectionExample;
		testSectionExample = new TestSectionExample();
		List<TestSection> testSections = null;
		List<TestSectionsTaskVariants> testSectionsTaskVariantsList = null;
		Set<String> nodeKeys = new HashSet<String>();
		if (tests != null && CollectionUtils.isNotEmpty(tests)) {
			TestSectionExample.Criteria testSectionCriteria = testSectionExample.createCriteria();
			testSectionCriteria.andTestIdIn(AARTCollectionUtil.getIds(tests));
			testSectionExample.setOrderByClause("externalid");
			testSections = testSectionDao.selectByExample(testSectionExample);
			for (Test test : tests) {
				test.setApplicableTestSections(testSections);
			}
		}
		List<Long> stimulusVariantIds = new ArrayList<Long>();
		if (testSections != null && CollectionUtils.isNotEmpty(testSections)) {
			for (TestSection testSection : testSections) {
				if (testSection != null && testSection.getContextStimulusId() != null) {
					stimulusVariantIds.add(testSection.getContextStimulusId());
				}
			}
			if (stimulusVariantIds != null && CollectionUtils.isNotEmpty(stimulusVariantIds)) {
				StimulusVariantExample stimulusVariantExample = new StimulusVariantExample();
				StimulusVariantExample.Criteria stimulusVariantExampleCriteria = stimulusVariantExample
						.createCriteria();
				stimulusVariantExampleCriteria.andIdIn(stimulusVariantIds);
				List<StimulusVariant> stimulusVariants = stimulusVariantDao.selectByExample(stimulusVariantExample);
				for (TestSection testSection : testSections) {
					if (testSection != null && testSection.getContextStimulusId() != null) {
						testSection.setContextStimulus(stimulusVariants);
					}
				}
			}
			testSectionsTaskVariantsList = taskVariantDao.selectByTestSection(AARTCollectionUtil.getIds(testSections));
			// TODO fetch the node information from new table.
			TaskVariantLearningMapNodeExample taskVariantLearningMapNodeExample = new TaskVariantLearningMapNodeExample();
			taskVariantLearningMapNodeExample.createCriteria()
					.andTaskVariantIdIn(AARTCollectionUtil.getIds(testSectionsTaskVariantsList, 3));
			List<TaskVariantLearningMapNode> taskVariantLearningMapNodes = taskVariantLearningMapNodeDao
					.selectByExample(taskVariantLearningMapNodeExample);

			if (testSectionsTaskVariantsList != null && CollectionUtils.isNotEmpty(testSectionsTaskVariantsList)) {
				for (TestSectionsTaskVariants testSectionsTaskVariants : testSectionsTaskVariantsList) {
					testSectionsTaskVariants.getTaskVariant()
							.setApplicableLearningMapNodes(taskVariantLearningMapNodes);
					nodeKeys.addAll(testSectionsTaskVariants.getTaskVariant().getLmNodeMap().keySet());
				}

				TaskVariantContentFrameworkDetailExample taskVariantContentFrameworkDetailExample = new TaskVariantContentFrameworkDetailExample();
				TaskVariantContentFrameworkDetailExample.Criteria taskVariantContentFrameworkDetailExampleCriteria = taskVariantContentFrameworkDetailExample
						.createCriteria();
				taskVariantContentFrameworkDetailExampleCriteria
						.andTaskvariantidIn(AARTCollectionUtil.getIds(testSectionsTaskVariantsList, 3));
				taskVariantContentFrameworkDetailExampleCriteria.andIsprimaryEqualTo(true);

				List<TaskVariantContentFrameworkDetail> taskVariantContentFrameworkDetails = taskVariantContentFrameworkDetailDao
						.selectExtendedByExample(taskVariantContentFrameworkDetailExample);

				// set the foils and content standards
				for (TestSectionsTaskVariants testSectionsTaskVariants : testSectionsTaskVariantsList) {
					testSectionsTaskVariants.getTaskVariant()
							.setPrimaryContentFrameworkDetail(taskVariantContentFrameworkDetails);
					testSectionsTaskVariants.getTaskVariant()
							.setApplicableLearningMapNodes(taskVariantLearningMapNodes);
				}
				// set the taskvariants to the section.
				for (TestSection testSection : testSections) {
					testSection.setApplicableTestSectionsTaskVariants(testSectionsTaskVariantsList);
				}
			}
			testCollection = findNodeInformation(testCollection, nodeKeys);
		}
		return testCollection;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String getTestTypeName(String testTypeCode) {

		return testDao.getTestTypeName(testTypeCode);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByPrimaryKeySelective(Test record) {
		return testDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * @param testCollectionId
	 *            long
	 * @param testStatusId
	 *            long
	 * @param variantTypeCode
	 *            String
	 * @return List<Test>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> findQCTestsByTestCollectionAndStatus(long testCollectionId, long testStatusId,
			String variantTypeCode, Boolean accessibleForm, String accessibilityFlagCode) {
		return testDao.findQCTestsByTestCollectionAndStatus(testCollectionId, testStatusId, accessibleForm,
				accessibilityFlagCode);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> findQCTestsByTestCollectionAndStatusAndAccFlags(long testCollectionId, long testStatusId,
			String variantTypeCode, Boolean accessibleForm, Set<String> accessibilityFlagCode) {
		int accessibilityFlagCounter = 0;
		if (accessibilityFlagCode != null)
			accessibilityFlagCounter = accessibilityFlagCode.size();
		return testDao.findQCTestsByTestCollectionAndStatusAndAccFlags(testCollectionId, testStatusId, accessibleForm,
				accessibilityFlagCode, accessibilityFlagCounter);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> findQCTestsAccFlagsByTestCollectionAndStatus(Long testCollectionId, Long testStatusId,
			List<String> eElements, Long testSpecificationId) {
		return testDao.findQCTestsAccFlagsByTestCollectionAndStatus(testCollectionId, testStatusId, eElements,
				testSpecificationId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> findProfessionalDevelopmentTests(@Param("testingProgramCode") String testingProgramCode,
			Long organizationId) {
		return testDao.findProfessionalDevelopmentTests(testingProgramCode, organizationId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> findProfessionalDevelopmentTutorials(@Param("testingProgramCode") String testingProgramCode,
			Long organizationId) {
		return testDao.findProfessionalDevelopmentTutorials(testingProgramCode, organizationId);
	}

	/**
	 *
	 * @return List<Long>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Long> getAllIds() {
		return testDao.getAllIds();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer publishRepublishingTest(Long newTestId) {
		return testDao.publishRepublishingTest(newTestId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestSection> getTestSectionsWithMaxNumberOfItemsByTestSessionId(Long testSessionId) {
		return testSectionDao.getTestSectionsWithMaxNumberOfItemsByTestSessionId(testSessionId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer updateQCComplete(Long[] testIds, Long userId) {
		return testDao.updateQCComplete(testIds, userId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer removeQCComplete(Long[] testIds, Long userId) {
		return testDao.removeQCComplete(testIds, userId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String getTaskTypeCodeByTaskVariant(Long taskVariantId) {
		return taskVariantDao.getTaskTypeCodeByTaskVariant(taskVariantId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String getTaskTypeCode(Long taskTypeId) {
		return taskVariantDao.getTaskTypeCode(taskTypeId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public TaskVariant getTaskVariantById(Long taskVariantId) {
		return taskVariantDao.selectByPrimaryKey(taskVariantId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<RubricReportDto> getRubricByTaskVariant(Long taskVariantId) {
		return rubricCategoryDao.getRubricByTaskVariant(taskVariantId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getTaskVariantAndSectionByTestId(@Param("testId") Long testId,
			@Param("includeSectionName") boolean includeSectionName) {
		return testSectionsTaskVariantsDao.getTaskVariantAndSectionByTestId(testId, includeSectionName);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getTaskVariantsByTestId(@Param("testId") Long testId) {
		return testSectionsTaskVariantsDao.getTaskVariantsByTestId(testId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String getTaskLayoutFormatLettersById(long id) {
		return taskVariantDao.getTaskFormatLayoutCharactersById(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getTaskVariantFoilOrder(long taskVariantId, long foilId) {
		return taskVariantDao.getTaskVariantFoilOrder(taskVariantId, foilId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TaskVariant> getTaskVariantByExternalIdGradeIdContentId(Long contentAreaId,
			String gradeCourseAbbreviatedName, Long assessmentProgramId, Long externalId, String testingProgramName) {
		return taskVariantDao.getTaskVariantByExternalIdGradeIdContentId(contentAreaId, gradeCourseAbbreviatedName,
				assessmentProgramId, externalId, testingProgramName);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> getActiveTestByExternalIdGradeIdContentIdAssessmentId(Long contentAreaId,
			String gradeCourseAbbreviatedName, Long assessmentProgramId, Long testId, String testingProgramName) {
		return testDao.getActiveTestByExternalIdGradeIdContentIdAssessmentId(testId, gradeCourseAbbreviatedName,
				contentAreaId, assessmentProgramId, testingProgramName);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportTestResponses> getKelpaTestsScoreByStudentIdExternalTestIds(Long studentId,
			List<Long> externalTestIds, List<Long> enrollmentIds, List<Long> testsStatusIds,
			List<Long> specialCircumstanceStatusIds, Long contractOrgId) {
		return testDao.getKelpaTestsScoreByStudentIdExternalTestIds(studentId, externalTestIds, enrollmentIds,
				testsStatusIds, specialCircumstanceStatusIds, contractOrgId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportTestResponses> getTestsScoreByStudentIdExternalTestIds(Long studentId,
			List<Long> externalTestIds, List<Long> enrollmentIds, List<Long> testsStatusIds,
			List<Long> specialCircumstanceStatusIds, Long contractOrgId, Long scoringStatusCompletedId) {
		return testDao.getTestsScoreByStudentIdExternalTestIds(studentId, externalTestIds, enrollmentIds,
				testsStatusIds, specialCircumstanceStatusIds, contractOrgId, scoringStatusCompletedId);
	}

	@Override
	public Test findLatestByExternalId(Long testExternalId) {
		return testDao.findLatestByExternalId(testExternalId);
	}

	@Override
	public List<TaskVariant> findTaskVariantsInTestSection(Long testSectionId) {
		return taskVariantDao.getTaskVariantsInSection(testSectionId);
	}

	@Override
	public List<Foil> getFoilsForTaskVariant(Long id) {
		return foilDao.selectByTaskVariantId(id);
	}

	@Override
	public String getTaskTypeCodeByTaskVariantExternalIdAndTestExternalId(Long tvExternalId, Long testExternalId) {
		return taskVariantDao.getTaskTypeCodeByTaskVariantExternalIdAndTestExternalId(tvExternalId, testExternalId);
	}

	@Override
	public List<Long> getPanelTestsFromThetaValues(Long studentId, Long previousTestSessionId, Long panelId) {
		return testDao.getPanelTestsFromThetaValues(studentId, previousTestSessionId, panelId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> findPanelStageTestsByTestCollectionAndStatusAndAccFlags(long testCollectionId, long testStatusId,
			String variantTypeCode, Boolean accessibleForm, Set<String> accessibilityFlagCode,
			List<Long> testsBasedOnTheta) {
		int accessibilityFlagCounter = 0;
		if (accessibilityFlagCode != null)
			accessibilityFlagCounter = accessibilityFlagCode.size();
		return testDao.findThetaBasedTestsByTestCollectionAndStatusAndAccFlags(testCollectionId, testStatusId,
				accessibleForm, accessibilityFlagCode, accessibilityFlagCounter, testsBasedOnTheta);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> findTestsForDLMFixedAssign(long testCollectionId, long testStatusId) {
		return testDao.findTestsForDLMFixedAssign(testCollectionId, testStatusId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean isTestIdFromTestCollectionsWithPerformanceTests(Long testId) {
		return (testDao.getCountOfTestCollectionsWithPerformanceTests(testId) >= 1);
	}

	/*@Override
	public List<Test> getInterimTest(Long gradeCourseId, Long contentAreaId) {
		return testDao.getInterimTest(gradeCourseId, contentAreaId);
	}*/

	@Override
	@Transactional(readOnly = false)
	public int createTest(Test testCreate) {
		return testDao.insert(testCreate);
	}

	@Override
	@Transactional(readOnly = false)
	public int createInterimTest(Test testCreate) {
		return testDao.insertInterim(testCreate);
	}

	@Override
	@Transactional(readOnly = false)
	public void softDeleteById(Long id) {
		 testDao.softDeleteByPrimaryKey(id);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TaskVariant> getPromptAndStudentResponse(Long[] taskVariantIds,Long studentId, Long studentsTestsId) {
		return taskVariantDao.getPromptAndStudentResponse(taskVariantIds, studentId, studentsTestsId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TaskVariant> getItemsWithPositionByTestId(Long testId){
		return taskVariantDao.getItemsWithPositionByTestId(testId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int findCountScorableTaskVariants(Long testId,Long ccqScoreId){
		return taskVariantDao.findCountScorableTaskVariants(testId,ccqScoreId);
	}
	
	@Override
	public Long getTestStaus(Long selectedTestId) {
		
		return testDao.getTestStaus(selectedTestId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> findTestsForDLMResearchSurvey(Long testCollectionId,
			Long testStatusId) {		
		return testDao.findTestsForDLMResearchSurvey(testCollectionId, testStatusId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean isTestIdFromTestCollectionsWithCorrectStageTests(Long subjectId, String gradeAbbrName, Long assessmentProgramId,
			Long testId, String stageCode) {
		
		return (testDao.getCountOfTestCollectionsWithCorrectStageTests(subjectId, gradeAbbrName, assessmentProgramId, testId, stageCode) >= 1);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long getTestIdBySchoolYearAndExternalId(Long schoolYear, Long testId) {
		return testDao.getTestIdBySchoolYearAndExternalId(schoolYear, testId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> findPredictiveTestsInTestCollection(Long testCollectionId, Long publishedTestStatusId) {
		return testDao.getPredictiveTestsInCollection(testCollectionId, publishedTestStatusId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportTestResponses> getPredictiveTestsScoreByStudentIdExternalTestIds(Long studentId,
			List<Long> externalTestIds, List<Long> enrollmentIds, List<Long> testsStatusIds) {
		return testDao.getPredictiveTestsScoreByStudentIdExternalTestIds(studentId, externalTestIds, enrollmentIds, testsStatusIds);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getAllPublishedTestByExternalId(Long externalTestId, Long deployedStatusId) {		
		return testDao.getAllPublishedTestByExternalId(externalTestId, deployedStatusId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Test> gtAllQCTestsAccFlagsByTestCollectionIdAndTestStatus(Long testCollectionId, Long testStatusId) {
		return testDao.gtAllQCTestsAccFlagsByTestCollectionIdAndTestStatus(testCollectionId, testStatusId);
	}

	@Override
	public List<Test> findQCTestsByTestCollectionAndStatusAndAccFlagsForPLTW(Long testCollectionId, Long statusId,
			String variantTypeCode, Boolean accessibleForm,
			Set<String> accessibilityFlagCode, Long operationalTestWindowId, Long stagePredecessorId,
			Long studentId) {
		int accessibilityFlagCounter = 0;
		if (accessibilityFlagCode != null)
			accessibilityFlagCounter = accessibilityFlagCode.size();
		return testDao.findQCTestsByTestCollectionAndStatusAndAccFlagsForPLTW(testCollectionId, statusId, accessibleForm,
				accessibilityFlagCode, accessibilityFlagCounter, operationalTestWindowId, stagePredecessorId, studentId);
	}
	
}
