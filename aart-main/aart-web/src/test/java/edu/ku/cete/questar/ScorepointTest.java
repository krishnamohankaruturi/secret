package edu.ku.cete.questar;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import edu.ku.cete.controller.test.QuestarController;
import edu.ku.cete.domain.StudentResponseScore;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.QuestarService;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.xml.XmlUtil;

public class ScorepointTest extends BaseTest {
	
	private Logger LOGGER = LoggerFactory.getLogger(ScorepointTest.class);
	
	@Autowired
	private StudentsResponsesService studentsResponsesService;
	
	@Autowired
	private QuestarService questarService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void testScorable() throws ParserConfigurationException, SAXException, IOException {
		Document doc = XmlUtil.readFromString(getXml("1"));
		List<StudentResponseScore> srss = process(doc);
		if (srss.isEmpty()) {
			fail("No records returned by process() when attempting good-path scorable check.");
		} else {
			assertEquals(Boolean.TRUE, srss.get(0).getScorable());
		}
		
		doc = XmlUtil.readFromString(getXml("BL"));
		srss = process(doc);
		if (srss.isEmpty()) {
			fail("No records returned by process() when attempting good-path non-scorable check.");
		} else {
			assertEquals(Boolean.FALSE, srss.get(0).getScorable());
		}
		
		doc = XmlUtil.readFromString(getXml(null));
		srss = process(doc);
		if (!srss.isEmpty()) {
			fail("Records returned by process() when attempting good-path non-scorable check.");
		}
	}
	
	private String getXml(String score) {
		return getXml(2278974L, 4047050L, 891784L, 32999L, score);
	}
	
	private String getXml(Long studentsTestsId, Long studentsTestSectionsId, Long studentId, Long taskVariantExternalId, String score) {
		return
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
			"<records>"+
			"	<StudentScoreSet RefId=\"" + (studentsTestsId == null ? "" : studentsTestsId) + "\""+
			"                    ScoreMetric=\"\""+
			"                    AssessmentAdministrationRefId=\"" + (studentsTestSectionsId == null ? "" : studentsTestSectionsId) + "\""+
			"                    StudentPersonalRefId=\"" + (studentId == null ? "" : studentId) + "\">"+
			"		<Scores>"+
			"			<Score AssessmentSubTestRefId=\"" + (taskVariantExternalId == null ? "" : taskVariantExternalId) + "\">"+
			"				<ScoreValue>" + (score == null ? "" : score) + "</ScoreValue>"+
			"				<DiagnosticStatement>Test</DiagnosticStatement>"+
			"			</Score>"+
			"			<SIF_ExtendedElements>"+
			"				<SIF_ExtendedElement Name=\"RaterID\">1234</SIF_ExtendedElement>"+
			"				<SIF_ExtendedElement Name=\"RaterName\">Test Rater Name</SIF_ExtendedElement>"+
			"				<SIF_ExtendedElement Name=\"RaterSequence\">1</SIF_ExtendedElement>"+
			"				<SIF_ExtendedElement Name=\"RaterExposure\">10</SIF_ExtendedElement>"+
			"				<SIF_ExtendedElement Name=\"DimensionCount\">1</SIF_ExtendedElement>"+
			"				<SIF_ExtendedElement Name=\"Dimension1\">DIMENSION 1</SIF_ExtendedElement>"+
			"			</SIF_ExtendedElements>"+
			"		</Scores>"+
			"	</StudentScoreSet>"+
			"</records>";
	}
	
	private List<StudentResponseScore> process(Document doc) {
		List<StudentResponseScore> rets = new ArrayList<StudentResponseScore>();
		
		Date now = new Date();
		
		List<Category> nonScorableCodes = categoryService.selectByCategoryType("NON_SCORABLE_CODE");
		Map<String, Long> nonScorableCodesMap = new HashMap<String, Long>();
		for (Category nsc : nonScorableCodes) {
			nonScorableCodesMap.put(nsc.getCategoryCode(), nsc.getId());
		}
		
		HashMap<Long, List<Long>> externalTaskIdMap = questarService.getQuestarTaskvariantMapByExternalId();
		
		NodeList nodeList = doc.getElementsByTagName("StudentScoreSet");
		
		for (int x = 0; x < nodeList.getLength(); x++) {
			Element studentScoreSet = (Element) nodeList.item(x);
			String sssRefId = StringUtils.trim(studentScoreSet.getAttribute("RefId"));
			String sssScoreMetric = StringUtils.trim(studentScoreSet.getAttribute("ScoreMetric"));
			String sssAssessmentAdministrationRefId = StringUtils.trim(studentScoreSet.getAttribute("AssessmentAdministrationRefId"));
			String sssStudentPersonalRefId = StringUtils.trim(studentScoreSet.getAttribute("StudentPersonalRefId"));
			
			//String studentScoreSetXML = XmlUtil.convertToXMLString(studentScoreSet, false);
			String scoreElementXML = "StudentId:" + sssStudentPersonalRefId + ", StudentTestId:"+ sssRefId 
					+ ", StudentTestSectionId:"+ sssAssessmentAdministrationRefId;
			
			Long studentId = null;
			Long studentsTestsId = null;
			Long studentsTestSectionsId = null;
			try {
				studentId = Long.parseLong(sssStudentPersonalRefId);
				studentsTestsId = Long.parseLong(sssRefId);
				studentsTestSectionsId = Long.parseLong(sssAssessmentAdministrationRefId);
			} catch (NumberFormatException nfe) {
				fail("Could not parse studentId/studentsTestsId/studentsTestSectionsId");
			}
			
			List<StudentsResponses> stuResponsesOfTestSection = studentsResponsesService.findQuestarResponseByStudentTestSectionId(studentsTestSectionsId);
			
			NodeList scores = studentScoreSet.getElementsByTagName("Scores");
			Element scoresElement = (Element) scores.item(0);
			
			NodeList scoreElements = scoresElement.getElementsByTagName("Score");
			
			Map<String, Object> extendedElements = parseExtendedElementsMap((Element) scoresElement.getElementsByTagName("SIF_ExtendedElements").item(0));
			
			String strRaterId = (String) extendedElements.get("RaterID");
			Long raterId = null;
			try {
				raterId = Long.parseLong(strRaterId);
			} catch (NumberFormatException nfe) {
				fail("Could not parse raterId");
			}
			
			Integer dimensionCount = null;
			try {
				String strDimensionCount = (String) extendedElements.get("DimensionCount");
				dimensionCount = Integer.parseInt(strDimensionCount);
			} catch (NumberFormatException nfe) {
			}
			
			String raterName = (String) extendedElements.get("RaterName");
			if ("".equals(raterName)) {
				raterName = null;
			}
			
			String strRaterOrder = (String) extendedElements.get("RaterSequence");
			Short raterOrder = null;
			try {
				raterOrder = Short.valueOf(strRaterOrder);
			} catch (NumberFormatException nfe) {
			}
			
			String strRaterExposure = (String) extendedElements.get("RaterExposure");
			Integer raterExposure = null;
			try {
				raterExposure = Integer.valueOf(strRaterExposure);
			} catch (NumberFormatException nfe) {
			}
								
			Long processedTVId = null;
			List<Long> taskVariantIds = null;
			
			Element scoreElement = (Element) scoreElements.item(0);
			
			String sAssessmentSubTestRefId = StringUtils.trim(scoreElement.getAttribute("AssessmentSubTestRefId"));
			String scoreValue = StringUtils.trim(XmlUtil.getChildProperty(scoreElement, "ScoreValue"));
			String diagnosticStatement = StringUtils.trim(XmlUtil.getChildProperty(scoreElement, "DiagnosticStatement"));
			
			// try to parse taskVariantId
			Long taskVariantExternalId = null;
			try {
				taskVariantExternalId = Long.parseLong(sAssessmentSubTestRefId);
				if(processedTVId == null) {
					processedTVId = taskVariantExternalId;
				}
			} catch (NumberFormatException nfe) {
				fail("Could not parse taskVariantExternalId");
			}
			
			Long nonScorableCodeId = null;
			// try to parse score
			BigDecimal score = null;
			if (StringUtils.isNotBlank(scoreValue)) {
				try {
					score = new BigDecimal(scoreValue);
				} catch (NumberFormatException nfe) {
					nonScorableCodeId = nonScorableCodesMap.get(scoreValue);
					if (nonScorableCodeId == null) {
						continue;
					}
				}
			} else {
				continue;
			}
			
			if (taskVariantIds == null) {							
				if(externalTaskIdMap.containsKey(taskVariantExternalId)) {
					taskVariantIds =(List<Long>) ((HashMap)externalTaskIdMap.get(taskVariantExternalId)).get("value");
				}
			}
			
			if (processedTVId.longValue() != taskVariantExternalId.longValue()) {
				taskVariantIds = null;
				
				if(externalTaskIdMap.containsKey(taskVariantExternalId)) {
					taskVariantIds = (List<Long>) ((HashMap) externalTaskIdMap.get(taskVariantExternalId)).get("value");
					processedTVId = taskVariantExternalId;
				}
			}
			
			if (isStudentResponseFound(taskVariantExternalId, studentId, studentsTestsId, studentsTestSectionsId, 
					stuResponsesOfTestSection, taskVariantIds)) {
				
				String dimension = (String) extendedElements.get("Dimension1");
				if (dimension == null) {
					fail("No dimension found");
				}
				
				StudentResponseScore record = new StudentResponseScore();
				record.setStudentsTestSectionsId(studentsTestSectionsId);
				record.setTaskVariantExternalId(taskVariantExternalId);
				record.setScore(score);
				record.setDimension(dimension);
				record.setDiagnosticStatement(diagnosticStatement);
				record.setRaterId(raterId);
				record.setRaterName(raterName);
				record.setRaterOrder(raterOrder);
				record.setRaterExposure(raterExposure);
				record.setCreateDate(now);
				record.setModifiedDate(now);
				record.setActiveFlag(true);
				record.setScorable(score != null);
				record.setNonScorableCodeId(nonScorableCodeId);
				
				rets.add(record);
				
				StudentsResponsesService studentsResponsesServiceMock = mock(StudentsResponsesService.class);
				when(studentsResponsesServiceMock.addOrUpdateStudentResponseScore(record)).thenReturn(record);
				
				try {
					studentsResponsesServiceMock.addOrUpdateStudentResponseScore(record);
				} catch (Exception e) {
				}
			} else {
			}
		}
		return rets;
	}
	
	private Map<String, Object> parseExtendedElementsMap(Element extendedElementsNode) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		NodeList extendedElements = extendedElementsNode.getElementsByTagName("SIF_ExtendedElement");
		for (int x = 0; x < extendedElements.getLength(); x++) {
			Element e = (Element) extendedElements.item(x);
			String content = e.getTextContent() == null ? null : e.getTextContent().trim();
			map.put(e.getAttribute("Name"), content);
		}
		return map;
	}
	
	private boolean isStudentResponseFound(Long externalTaskVariantId, Long studentId, Long studentTestId, 
			Long studentTestSectionId, List<StudentsResponses> studentResponses, List<Long> taskVariantIds) {
		
		for (StudentsResponses stuResponse : studentResponses) {

			if(CollectionUtils.isNotEmpty(taskVariantIds) 
					&& taskVariantIds.contains(stuResponse.getTaskVariantId())
						&& stuResponse.getStudentId().longValue() == studentId.longValue() 
							&& stuResponse.getStudentsTestsId().longValue() == studentTestId.longValue()) {
					return true;
			}
		}
		
		return false;
	}
}