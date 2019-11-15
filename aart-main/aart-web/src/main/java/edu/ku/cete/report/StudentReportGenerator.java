package edu.ku.cete.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.xml.XmlUtil;
import edu.ku.cete.web.StudentReportDTO;

@Component
public class StudentReportGenerator extends ReportGenerator {
	
	@Value("/templates/xslt/reports/isr.xsl")
	private Resource xslFile;
	
	@Value("/templates/xslt/reports/writing.xsl")
	private Resource writingXsl;
	
	@Value("/templates/xslt/xhtml-to-fo.xsl")
	private Resource xhtmlToFoXsl;
	
	@Value("/images/reports/report_star1.svg")
	private Resource getScoreStarPerformanceLevel1Path;
	
	@Value("/images/reports/report_star0.svg")
	private Resource getScoreStarPerformanceLevel0Path;
	
	@Value("/images/reports/image_transferred.svg")
	private Resource getTransferredStudentPath;
	
	@Value("/images/reports/image_incomplete.svg")
	private Resource getIncompleteScorePath;
	
	@Value("/images/reports/response_not_scored.svg")
	private Resource getResponseNotScoredPath;
	
	@Value("/images/reports/report_circles0.svg")
	private Resource getOnDemandWritingTaskScore0Path;
	
	@Value("/images/reports/report_circles1.svg")
	private Resource getOnDemandWritingTaskScore1Path;
	
	@Value("/images/reports/report_circles2.svg")
	private Resource getOnDemandWritingTaskScore2Path;
	
	@Value("/images/reports/report_circles3.svg")
	private Resource getOnDemandWritingTaskScore3Path;
	
	@Value("/images/reports/report_circles4.svg")
	private Resource getOnDemandWritingTaskScore4Path;
	
	
	
	@Value("/images/reports/report_star2.svg")
	private Resource getScoreStarPerformanceLevel2Path;	
	
	@Value("/images/reports/report_star3.svg")
	private Resource getScoreStarPerformanceLevel3Path;	
	
	@Value("/images/reports/report_star4.svg")
	private Resource getScoreStarPerformanceLevel4Path;	
	
	@Value("/images/reports/reportp1Arrow.svg")
	private Resource getFooterNextPageArrowPath;
	
	//@Value("/images/reports/icon_below.png")
	@Value("/images/reports/icon_below.svg")
	private Resource getIconBelowPath;
	
	//@Value("/images/reports/icon_exceeds.png")
	@Value("/images/reports/icon_exceeds.svg")
	private Resource getIconExceedsPath;
	
	//@Value("/images/reports/icon_insufficient.png")
	@Value("/images/reports/icon_insufficient.svg")
	private Resource getIconInsufficientPath;
	
	//@Value("/images/reports/icon_meets.png")
	@Value("/images/reports/icon_meets.svg")
	private Resource getIconMeetsPath;
	
	@Value("/images/reports/AMPEEDlogo_48x48.svg")
	private Resource getBlackRightPointingTriangleBulletPath;
	
	@Value("${writing.report.noresponse}")
	private String writingNoResponse;
	
	@Autowired
	private AwsS3Service s3;
	
	public String generateReportFile(StudentReport data) throws Exception {
		File studentMeter = null;
		File schoolMeter = null;
		File districtMeter = null;
		File stateMeter = null;
		
		File studentSubScore = null;
		File schoolSubScore = null;
		File districtSubScore = null;
		File axisLineSubScore = null;
		File stateSubScore = null;
		try {
			adjustLevels(data);
			String outDir = createStudentDir(data);
			ReportContext reportData = new ReportContext();
			
			Resource getScoreStarPerformanceLevelPath = null;
			Resource getOnDemandWritingTaskScorePath = null;
			
			File FilesArrow = getFooterNextPageArrowPath.getFile();
			String footerNextPageArrowPath = FilesArrow.toURI().toString();
			reportData.setFooterNextPageArrowPath(footerNextPageArrowPath);

			reportData.setData(data);
			reportData.setStudentMeterWidth(260);
			reportData.setStudentMeterHeight(90);
			reportData.setSmallMeterWidth(95);
			reportData.setSmallMeterHeight(50);
			studentMeter = generateMeter(data.getAssessmentProgramCode(),data.getLevels(), getMeterValue(data, "student"), "student", outDir,(int)(reportData.getStudentMeterWidth()+0.8),reportData.getStudentMeterHeight());
			schoolMeter = generateMeter(data.getAssessmentProgramCode(), data.getLevels(), getMeterValue(data, "school"), "school", outDir,(int) (reportData.getSmallMeterWidth()-1),reportData.getSmallMeterHeight());
			districtMeter = generateMeter(data.getAssessmentProgramCode(), data.getLevels(), getMeterValue(data, "district"), "district", outDir,(int) (reportData.getSmallMeterWidth()-1),reportData.getSmallMeterHeight());
			stateMeter = generateMeter(data.getAssessmentProgramCode(), data.getLevels(), getMeterValue(data, "state"), "state", outDir, (int)(reportData.getSmallMeterWidth()-1),reportData.getSmallMeterHeight());
			
			
					
					
			List<ReportSubscores> allSubScores = getSubScores(data, "state");
			
			studentSubScore = generateSubScoreChart(reportData, data, "student", outDir, allSubScores);
			schoolSubScore = generateSubScoreChart(reportData, data, "school", outDir, allSubScores);
			districtSubScore = generateSubScoreChart(reportData, data, "district", outDir, allSubScores);
			stateSubScore = generateSubScoreChart(reportData, data, "state", outDir, null);
			axisLineSubScore = generateSubScoreXAxis(reportData, data, outDir);
			
			//to not to generate page2
			if(data.getSubscoreBuckets() != null && data.getSubscoreBuckets().isEmpty()) {
				data.setSubscoreBuckets(null);
			}
			List<ReportSubscores> schoolSubScores = getSubScores(data, "school");
			if(schoolSubScores != null && !schoolSubScores.isEmpty()){
				for(ReportSubscores subScore: schoolSubScores) {
					if(subScore.getSubscoreScaleScore() == null) {
						reportData.setSchoolPrivacyStmtFlag("true");
						break;
					}
				}
			} else {
				reportData.setSchoolPrivacyStmtFlag("true");
			}
			List<ReportSubscores> districtSubScores = getSubScores(data, "district");
			if(districtSubScores != null && !districtSubScores.isEmpty()){
				for(ReportSubscores subScore: districtSubScores) {
					if(subScore.getSubscoreScaleScore() == null) {
						reportData.setDistrictPrivacyStmtFlag("true");
						break;
					}
				}
			} else {
				reportData.setDistrictPrivacyStmtFlag("true");
			}
			List<ReportSubscores> stateSubScores = getSubScores(data, "state");
			if(stateSubScores != null && !stateSubScores.isEmpty()){
				for(ReportSubscores subScore: stateSubScores) {
					if(subScore.getSubscoreScaleScore() == null) {
						reportData.setStatePrivacyStmtFlag("true");
						break;
					}
				}
			} else {
				reportData.setStatePrivacyStmtFlag("true");
			}
			File logoFile = getLogo(data.getAssessmentProgramCode());
			File footerLogoFile = getFooterLogo(data.getAssessmentProgramCode());
			reportData.setStudentMeter(studentMeter.toURI().toString());
			reportData.setSchoolMeter(schoolMeter.toURI().toString());
			reportData.setDistrictMeter(districtMeter.toURI().toString());
			reportData.setStateMeter(stateMeter.toURI().toString());
			reportData.setAxisSubScoreChart(axisLineSubScore.toURI().toString());
			reportData.setStudentSubScoreChart(studentSubScore.toURI().toString());
			reportData.setSchoolSubScoreChart(schoolSubScore.toURI().toString());
			reportData.setDistrictSubScoreChart(districtSubScore.toURI().toString());
			reportData.setStateSubScoreChart(stateSubScore.toURI().toString());
			reportData.setLogoPath(logoFile.toURI().toString());
			reportData.setFooterLogoPath(footerLogoFile.toURI().toString());
			reportData.setIncompleteStatusIconPath(getIconPath("incomplete"));
			reportData.setExitStatusIconPath(getIconPath("exited"));
			
			if(reportData.getSubScoreChartHeight() == 0) {
				reportData.setSubScoreChartHeight(35);
			}
			
			ArrayList<Character> delimiter = new ArrayList<Character>();
			delimiter.add(' ');
			delimiter.add('-');
			data.setStudentLastName(replaceCapsAtSpecifiedChar(data.getStudentLastName(), delimiter));
			data.setStudentFirstName(replaceCapsAtSpecifiedChar(data.getStudentFirstName(), delimiter));
			
			// Assigning file path according to the level 
			//Resource getScoreStarPerformanceLevelPath = null;
			//Resource getOnDemandWritingTaskScorePath = null;
			
			Long dataScaleScore = data.getScaleScore();
			String getLevelValue = "";
			for(LevelDescription scaleScore: data.getLevels()) {
				if(scaleScore.getLevel() != null){
					if(dataScaleScore!=null&&(dataScaleScore >= scaleScore.getLevelLowCutScore()) && (dataScaleScore <= scaleScore.getLevelHighCutScore())) {
						getLevelValue = scaleScore.getLevel().toString();
					}
				}
			}
			data.setLevel(Long.parseLong(getLevelValue));
			String suppressmdptScore = String.valueOf(data.getSuppressMdptScore());
			String suppressCombinedScore = String.valueOf(data.getSuppressCombinedScore());
			String suppressMainScore = String.valueOf(data.getSuppressMainScalescorePrfrmLevel());
			String mdptScorableFlag = String.valueOf(data.isMdptScorableFlag());
			String scCodesExist = String.valueOf(data.getScCodeExist());
			String performanceRawScoreFlag = String.valueOf(data.getPerformanceRawscoreIncludeFlag());
			
				
				if("true".equalsIgnoreCase(suppressMainScore) || "true".equalsIgnoreCase(suppressCombinedScore) ){
				
					getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel0Path;
					
					if(data.getMdptLevel() != null){
						Long mdptLevel = data.getMdptLevel().longValue();
						if(mdptLevel.equals(new Long(1))){
							getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore1Path;
						}else if (mdptLevel.equals(new Long(2))){
							getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore2Path;
						}else if (mdptLevel.equals(new Long(3))){
							getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore3Path;
						}else if (mdptLevel.equals(new Long(4))){
							getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore4Path;
						}
					}else{
						getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore0Path;
					}
				}else {
					if(!("false".equalsIgnoreCase(performanceRawScoreFlag))){						
						if(data.getLevel() != null){
							Long mainLevel = data.getLevel();
							if(mainLevel.equals(new Long(1))){
								getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel1Path;
							}else if(mainLevel.equals(new Long(2))){
								getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel2Path;
							}else if(mainLevel.equals(new Long(3))){
								getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel3Path;
							}else if(mainLevel.equals(new Long(4))){
								getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel4Path;
							}
						}
					}else{
						if("true".equalsIgnoreCase(suppressmdptScore)){
							// Conditions added for scenario 4 and 5
							if(!("false".equalsIgnoreCase(mdptScorableFlag))){
								getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel0Path;
								getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore0Path;
							}else{
								if("true".equalsIgnoreCase(scCodesExist)){
									getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel0Path;
									getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore0Path;
								}else{
									getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore0Path;
									
									if(data.getCombinedLevel() != null){
										Long combinedLevel = data.getCombinedLevel().longValue();
										if(combinedLevel.equals(new Long(1))){
											getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel1Path;
										}else if(combinedLevel.equals(new Long(2))){
											getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel2Path;
										}else if(combinedLevel.equals(new Long(3))){
											getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel3Path;
										}else if(combinedLevel.equals(new Long(4))){
											getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel4Path;
										}
									}
								}
								
							}
						}else{
							if(data.getCombinedLevel() == null && data.getMdptLevel() == null){
								
								if(data.getLevel() != null){
									Long mainLevel = data.getLevel();
									if(mainLevel.equals(new Long(1))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel1Path;
									}else if(mainLevel.equals(new Long(2))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel2Path;
									}else if(mainLevel.equals(new Long(3))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel3Path;
									}else if(mainLevel.equals(new Long(4))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel4Path;
									}
								}
							}	
							else{
								
								if(data.getCombinedLevel() != null){
									Long combinedLevel = data.getCombinedLevel().longValue();
									if(combinedLevel.equals(new Long(1))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel1Path;
									}else if(combinedLevel.equals(new Long(2))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel2Path;
									}else if(combinedLevel.equals(new Long(3))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel3Path;
									}else if(combinedLevel.equals(new Long(4))){
										getScoreStarPerformanceLevelPath = getScoreStarPerformanceLevel4Path;
									}
								}
								
								
								
								if(data.getMdptLevel() != null){
									Long mdptLevel = data.getMdptLevel().longValue();
									if(mdptLevel.equals(new Long(1))){
										getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore1Path;
									}else if (mdptLevel.equals(new Long(2))){
										getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore2Path;
									}else if (mdptLevel.equals(new Long(3))){
										getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore3Path;
									}else if (mdptLevel.equals(new Long(4))){
										getOnDemandWritingTaskScorePath = getOnDemandWritingTaskScore4Path;
									}
								}
							}
						}
					}
				}
			
			
			//US18542 add transferred student img
			File fileTransferredStudent = getTransferredStudentPath.getFile();
			String transferredStudentPath = fileTransferredStudent.toURI().toString();
			reportData.setTransferredStudentPath(transferredStudentPath);
			
			File fileIncompleteScore = getIncompleteScorePath.getFile();
			String incompleteScorePath = fileIncompleteScore.toURI().toString();
			reportData.setIncompleteScorePath(incompleteScorePath);
			
			File fileResponseNotScored = getResponseNotScoredPath.getFile();
			String responseNotScored = fileResponseNotScored.toURI().toString();
			reportData.setResponseNotScoredPath(responseNotScored);
			
			if(getOnDemandWritingTaskScorePath != null){
				File FileTaskScore = getOnDemandWritingTaskScorePath.getFile();
				String onDemandWritingTaskScorePath = FileTaskScore.toURI().toString();
				reportData.setOnDemandWritingTaskScorePath(onDemandWritingTaskScorePath);
			}
			
			if(getScoreStarPerformanceLevelPath != null){
				File FileScoreStar = getScoreStarPerformanceLevelPath.getFile();
				String scoreStarPerformanceLevelPath = FileScoreStar.toURI().toString();
				reportData.setScoreStarPerformanceLevelPath(scoreStarPerformanceLevelPath);
			}
			
			File iconExceedsFile = getIconExceedsPath.getFile();
			String iconExceedsFilePath = iconExceedsFile.toURI().toString();
			reportData.setIconExceedsPath(iconExceedsFilePath);
			
			reportData.setIconBelowPath(getIconBelowPath.getFile().toURI().toString());
			reportData.setIconMeetsPath(getIconMeetsPath.getFile().toURI().toString());
			reportData.setIconInsufficientPath(getIconInsufficientPath.getFile().toURI().toString());

			
			XStream xstream = new XStream();
			xstream.alias("reportDetails", ReportContext.class);
			xstream.aliasField("data", ReportContext.class, "data");
			TraxSource source = new TraxSource(reportData, xstream);
			String fullPath = FileUtil.buildFilePath(outDir, "SR_"
					+ sanitizeForPath(data.getStateStudentIdentifier())
					+ ".pdf");
			String[] splitFullPath = fullPath.split("\\.");
			File pdfFile = File.createTempFile(splitFullPath[0], ".pdf");
			File foFile = xslFile.getFile();
			generatePdf(pdfFile, foFile, source);
			s3.synchMultipartUpload(fullPath, pdfFile);
			String pathForDB = getPathForDB(fullPath);
			FileUtils.deleteQuietly(pdfFile);
			return pathForDB;
		} finally {
			FileUtils.deleteQuietly(studentMeter);
			FileUtils.deleteQuietly(schoolMeter);
			FileUtils.deleteQuietly(districtMeter);
			FileUtils.deleteQuietly(stateMeter);
			
			FileUtils.deleteQuietly(studentSubScore);
			FileUtils.deleteQuietly(schoolSubScore);
			FileUtils.deleteQuietly(districtSubScore);
			FileUtils.deleteQuietly(stateSubScore);
			FileUtils.deleteQuietly(axisLineSubScore);
		}
	}

	private String createStudentDir(StudentReport data) throws IOException {
		String studentDir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+data.getCurrentSchoolYear()+File.separator+ "ISR" + File.separator
				+ data.getStateId() + File.separator + data.getDistrictId()
				+ File.separator + data.getAttendanceSchoolId()
				+ File.separator + data.getContentAreaCode()
				+ File.separator + data.getGradeCode() + File.separator
				+ data.getStudentId());
		return studentDir;
	}
	
	private Long getMeterValue(StudentReport data, String type) {
		Long meterValue = null;
		String suppressMainPrfrmLevel = String.valueOf(data.getSuppressMainScalescorePrfrmLevel());
		
		if(!("true".equalsIgnoreCase(suppressMainPrfrmLevel))){
			if (type.equals("student")) {
				meterValue = data.getScaleScore();
			} else if (type.equals("school") || type.equals("district") || type.equals("state")) {
				for (ReportsMedianScore median : data.getMedianScores()) {
					if (type.equals("school") && data.getAttendanceSchoolId().equals(median.getOrganizationId())) {
						meterValue = median.getScore();
					} else if (type.equals("district") && data.getDistrictId().equals(median.getOrganizationId())) {
						meterValue = median.getScore();
					} else if (type.equals("state") && data.getStateId().equals(median.getOrganizationId())) {
						meterValue = median.getScore();
					}
				}
			}
		}
		return meterValue;
	}
	
	private String replaceCapsAtSpecifiedChar(String data, ArrayList<Character> delimiter){
		String result = "";
		boolean changeNeed = false;
		char dataSet[] = data.toCharArray();		
			for(char limiter : delimiter){
				for(int loop=0; loop < dataSet.length; loop++){
					char dataChar = dataSet[loop];
					if(dataChar == limiter){
						changeNeed = true;
					}else if(dataChar != limiter){
						char upperCase = Character.toUpperCase(dataChar);
						dataSet[loop] = (changeNeed ?  upperCase : dataChar);						
						changeNeed = false;
					}
				}
			}
		result = String.valueOf(dataSet);
		return result; 
	}
	
	private void adjustLevels(StudentReport data) {
		Collections.sort(data.getLevels(), new Comparator<LevelDescription>() {
			@Override
			public int compare(LevelDescription o1, LevelDescription o2) {
				return o1.getLevel().compareTo(o2.getLevel());
			}
		});
		int descLength = 146;
		if(data.getAssessmentProgramCode().equals("AMP")) {
			descLength = 140;
		}
		
		if(data.getLevels().size() > 4) {
			descLength = 200;
		}
		
		Pattern pattern = Pattern.compile("^.{0,"+descLength+"}\\b");
		boolean isfirst = true;
		for(LevelDescription level: data.getLevels()) {
			if(level.getLevelDescription() != null && level.getLevelDescription().indexOf("~~row~~") == -1) {
				if(isfirst && data.getAssessmentProgramCode().equals("AMP")) {
					if(data.getContentAreaCode().equals("M")) {
						descLength = 160;
					} else if(data.getContentAreaCode().equals("ELA")) {
						descLength = 154;
					}
					pattern = Pattern.compile("^.{0,"+descLength+"}\\b");
				}
				if(data.getAssessmentProgramCode().equals("AMP") && data.getContentAreaCode().equals("M")) {
					descLength = 160;
					if(data.getGradeName().contains("10") && level.getLevel() == 2) {
						descLength = 154;
					} else if(data.getGradeName().contains("10") && level.getLevel() == 3) {
						descLength = 154;
					} else if(data.getGradeName().contains("9") && level.getLevel() == 2) {
						descLength = 143;
					} else if(data.getGradeName().contains("8") && level.getLevel() == 2) {
						descLength = 154;
					} else if(data.getGradeName().contains("8") && level.getLevel() == 3) {
						descLength = 147;
					} else if(data.getGradeName().contains("8") && level.getLevel() == 4) {
						descLength = 143;
					} else if(data.getGradeName().contains("7") && level.getLevel() == 2) {
						descLength = 143;
					} else if(data.getGradeName().contains("7") && level.getLevel() == 3) {
						descLength = 154;
					} else if(data.getGradeName().contains("6") && level.getLevel() == 3) {
						descLength = 154;
					} else if(data.getGradeName().contains("6") && level.getLevel() == 2) {
						descLength = 147;
					} else if(data.getGradeName().contains("5") && level.getLevel() == 1) {
						descLength = 154;
					} else if(data.getGradeName().contains("5") && level.getLevel() == 2) {
						descLength = 149;
					} else if(data.getGradeName().contains("5") && level.getLevel() == 3) {
						descLength = 149;
					} else if(data.getGradeName().contains("4") && level.getLevel() == 2) {
						descLength = 149;
					} else if(data.getGradeName().contains("4") && level.getLevel() == 4) {
						descLength = 143;
					} else if(data.getGradeName().contains("3") && level.getLevel() == 2) {
						descLength = 143;
					} else if(data.getGradeName().contains("3") && level.getLevel() == 3) {
						descLength = 143;
					} else if(level.getLevel() == 4) {
						descLength = 148;
					    pattern = Pattern.compile("^.{0,"+descLength+"}\\b");
					}

				    pattern = Pattern.compile("^.{0,"+descLength+"}\\b");
				}
				/*if(level.getLevelDescription().length() > descLength) {
					Matcher m = pattern.matcher(level.getLevelDescription());
					m.find();
					level.setLevelDescription(m.replaceFirst(m.group(0)+"~~row~~"));
				}*/
			}
			isfirst = false;
		}
	}
	
	public File generateWritingReport(List<StudentReportDTO> studentReports) throws Exception {
		List<String> tagsToKeepIfEmpty = Arrays.asList("li");
		
		ReportContext reportContext = new ReportContext();
		reportContext.setData(studentReports);
		
		// for each report, generate the FO markup for the writing response
		for (StudentReportDTO report : studentReports) {
			if (reportContext.getLogoPath() == null) {
				reportContext.setLogoPath(getLogo(report.getAssessmentProgramCode()).toURI().toString());
			}
			String response = report.getWritingResponse() != null ? report.getWritingResponse() : writingNoResponse;
			String writingHtml = "<div>" + convertSlashXEncodings(response) + "</div>";
			writingHtml = XmlUtil.sanitizeXmlChars(writingHtml);
			writingHtml = escapeHTMLForFOPXML(writingHtml);
			Document writingResponseDocument = readMarkupIntoDocument(writingHtml);
			
			if (isNodeTextBlank(writingResponseDocument.getDocumentElement())) {
				writingResponseDocument = readMarkupIntoDocument("<div>" + writingNoResponse + "</div>");
			} else {
				NodeList children = writingResponseDocument.getElementsByTagName("*");
				for (int x = 0; x < children.getLength(); x++) {
					Node item = children.item(x);
					if (!tagsToKeepIfEmpty.contains(item.getNodeName()) && isNodeTextBlank(item)) {
						item.getParentNode().removeChild(item);
					}
				}
			}
			
			StringWriter writer = new StringWriter();
			transform(new DOMSource(writingResponseDocument), xhtmlToFoXsl.getFile(), writer);
			report.setWritingResponseFO(writer.toString());
		}
		
		XStream xstream = new XStream();
		xstream.alias("reportContext", ReportContext.class);
		xstream.aliasField("data", ReportContext.class, "data");
		TraxSource traxSource = new TraxSource(reportContext, xstream);
		
		File tmpXslFile = new File("/tmp/transformed-writing_" + new Date().getTime() + "_" + ((int) (Math.random() * 100000)) + ".xsl");
		transform(traxSource, writingXsl.getFile(), new FileWriter(tmpXslFile));
		
		File pdfFile = new File("/tmp/WritingResponse_" + new Date().getTime() + "_" + ((int) (Math.random() * 100000)) + ".pdf");
		generatePdf(pdfFile, tmpXslFile, traxSource);
		FileUtils.deleteQuietly(tmpXslFile);
		
		return pdfFile;
	}
	
	public String convertSlashXEncodings(String str) {
		if (str == null || StringUtils.isEmpty(str)) {
			return str;
		}
		return StringEscapeUtils.unescapeJava(str.replace("\\x", "\\u00"));
	}
	
	public String escapeHTMLForFOPXML(String html) {
		Map<String, String> entities = new HashMap<String, String>();
		entities.put("&lt;", "$$$ATS_LESSTHAN$$$");
		entities.put("&gt;", "$$$ATS_GREATERTHAN$$$");
		
		String newHtml = html;
		
		// perform replace on things we don't want to unescape
		for (Map.Entry<String, String> entity : entities.entrySet()) {
			newHtml = StringUtils.replace(newHtml, entity.getKey(), entity.getValue());
		}
		
		newHtml = HtmlUtils.htmlUnescape(newHtml);
		
		// this needs to come FIRST after the unescape,
		// otherwise ampersands for any other replacements will show in the content
		newHtml = StringUtils.replace(newHtml, "&", "&amp;");
		
		// re-insert the entities we didn't want unescaped
		for (Map.Entry<String, String> entity : entities.entrySet()) {
			newHtml = StringUtils.replace(newHtml, entity.getValue(), entity.getKey());
		}
		
		return newHtml;
	}
	
	public Document readMarkupIntoDocument(String source) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setNamespaceAware(true);
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(false);
		dbf.setExpandEntityReferences(true);
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(new InputSource(new StringReader(source)));
	}
	
	private boolean isNodeTextBlank(Node node) {
		String text = StringUtils.replace(node.getTextContent(), "&nbsp;", " "); // encoding for non-breaking space
		text = StringUtils.replace(text, Character.toString((char) 160), " "); // ASCII non-breaking space
		return StringUtils.isBlank(text);
	}
}