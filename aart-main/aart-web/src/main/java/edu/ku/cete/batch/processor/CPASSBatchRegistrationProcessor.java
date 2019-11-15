package edu.ku.cete.batch.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.batch.support.WriterContext;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.report.domain.BatchRegistrationReason;

public class CPASSBatchRegistrationProcessor extends BatchRegistrationProcessor {
	
	protected Map<String, List<TestCollection>> testTypeToTestCollectionsMap;
	
	@SuppressWarnings("unchecked")
	@Override
	public WriterContext process(Enrollment enrollment) throws Exception {
		logger.debug("--> process" );
		WriterContext writerCtx = new WriterContext();
 		Set<String> accessibilityFlags = getAccessibleFlags(enrollment);
 		Map<TestCollection, List<Test>> testCollectionTests = new HashMap<TestCollection, List<Test>>();
 		
 		// override the inherited class variable so we can tweak it
 		List<TestCollection> testCollections = null;
		for (String testTypeCode : testTypeToTestCollectionsMap.keySet()) {
			if (testTypeCode.equals(testType.getTestTypeCode())) {
				testCollections = testTypeToTestCollectionsMap.get(testTypeCode);
				logger.info("found collections for test type " + testTypeCode);
				break;
			}
		}
 		if (CollectionUtils.isNotEmpty(testCollections)) {
			for(TestCollection testCollection: testCollections) {
				List<Test> tests = null;
				tests = getTests(enrollment, testCollection, accessibilityFlags);
				if(CollectionUtils.isNotEmpty(tests)) {
					String logMsg = String.format("Tests found with this criteria - testtype: %s(%d), testcollection: %d, accessibleflgs: %s.", 
							testType.getTestTypeCode(), testType.getId(), testCollection.getId(), accessibilityFlags);
					logger.debug(logMsg);
					testCollectionTests.put(testCollection, tests);
				} else {
					String cErrorMsg = String.format("No tests found with this criteria - testtype: %s(%d), testcollection: %d, accessibleflgs: %s.", 
								testType.getTestTypeCode(), testType.getId(), testCollection.getId(),accessibilityFlags);
					logger.debug(cErrorMsg);
					BatchRegistrationReason brReason = new BatchRegistrationReason();
					brReason.setBatchRegistrationId(batchRegistrationId);
					brReason.setStudentId(enrollment.getStudentId());
					brReason.setReason(cErrorMsg);
					((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
				}
			}
		}
		if(testCollectionTests.size() == testCollections.size()) {
			logger.debug("Found tests for all test collections");
			writerCtx.setEnrollment(enrollment);
			writerCtx.setTestCollectionTests(testCollectionTests);
			writerCtx.setAccessibilityFlags(accessibilityFlags);
			return writerCtx;
		} else if(testCollectionTests.size() > 0) {
			logger.debug("Found tests for some test collections");
			writerCtx.setEnrollment(enrollment);
			writerCtx.setTestCollectionTests(testCollectionTests);
			writerCtx.setAccessibilityFlags(accessibilityFlags);
			return writerCtx;
		} else {
			StringBuilder errorMessage = new StringBuilder("[");
			int i=0;
			for(TestCollection testCollection: testCollections) {
				if(i > 0) {
					errorMessage.append(",");
				}
				errorMessage.append(testCollection.getId());
				i=1;
			}
			errorMessage.append("]");
			String cErrorMsg = String.format("No tests found with criteria - grade: %s(%d), testtype: %s(%d), contentarea: %s(%d), testcollections: %s, accessibleflgs: %s.", 
					gradeCourse.getAbbreviatedName(), gradeCourse.getId(), testType.getTestTypeCode(), testType.getId(),
					contentArea.getAbbreviatedName(), contentArea.getId(), errorMessage.toString(),accessibilityFlags);
			logger.debug(cErrorMsg);
			logger.debug("<-- process raiseSkipError");
			throw new SkipBatchException(cErrorMsg);
		}
	}

	protected List<Test> getTests(Enrollment enrollment, TestCollection testCollection, Set<String> accessibilityFlags) {
		logger.debug("--> getTests" );
		List<Test> tests = testService.findQCTestsByTestCollectionAndStatus(testCollection.getId(), 
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), 
				AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, null, null);
		logger.debug("<-- getTests" );
		return tests;
	}
	
	protected Set<String> getAccessibleFlags(Enrollment enrollment) {
		return new HashSet<String>();
	}

	public Map<String, List<TestCollection>> getTestTypeToTestCollectionsMap() {
		return testTypeToTestCollectionsMap;
	}

	public void setTestTypeToTestCollectionsMap(
			Map<String, List<TestCollection>> testTypeToTestCollectionsMap) {
		this.testTypeToTestCollectionsMap = testTypeToTestCollectionsMap;
	}
}
