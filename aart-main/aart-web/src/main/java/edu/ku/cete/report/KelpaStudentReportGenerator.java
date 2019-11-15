package edu.ku.cete.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.FileUtil;

@Component
public class KelpaStudentReportGenerator extends ReportGenerator {
	
	@Value("/templates/xslt/reports/kelpaStudentReport.xsl")
	private Resource xslFile;
	
	@Value("/images/reports/kelpa_report_star_0.svg")
	private Resource getKelpaScoreStarPerformanceLevel0Path;	
	
	@Value("/images/reports/kelpa_report_star_1.svg")
	private Resource getKelpaScoreStarPerformanceLevel1Path;	
	
	@Value("/images/reports/kelpa_report_star_2.svg")
	private Resource getKelpaScoreStarPerformanceLevel2Path;	
	
	@Value("/images/reports/kelpa_report_star_3.svg")
	private Resource getKelpaScoreStarPerformanceLevel3Path;	
	
	@Value("/images/reports/KELPA2_header_logo.svg")
	private Resource kELPAHeaderLogoFile;
	
	@Value("/images/reports/new_KAPFooterLogo.svg")
	private Resource kapFooterLogoFile;
	
	@Value("${label.kelpa.student.report.incomplete}")
	private String reportInCompleteLevel;
	
	@Value("${label.kelpa.student.report.nottested}")
	private String reportNotTestedLabel;
	
	@Autowired
	private AwsS3Service s3;
	
	public StudentReport generateReportFile(List<StudentReport> studentReportLists, Map<String, String> pdfStaticContentMap, List<Category> domainPerformanceLevelLists, Map<Long, String> testStatusMap, Map<Long, String> scoringStatusMap, List<LevelDescription> levelDescriptions, Map<String, String> progressionTextMap) throws Exception {
		
		StudentReport data = null;
		if(studentReportLists.size()>1) data = studentReportLists.get(1);
		else data = studentReportLists.get(0);
		
		try {		
			String outDir = createStudentDir(data);
			ReportContext reportData = new ReportContext();
			data.setLevels(levelDescriptions);
			reportData.setData(data);			
			reportData.setKelpaReportIntroParagraph(pdfStaticContentMap.get(CommonConstants.KELPA_STUDENT_REPORT_PDF_STATIC_CONTENT_PARAGRAPH_2));
			reportData.setDomainPerformanceLevelCategoryLists(domainPerformanceLevelLists);		
			reportData.setDomainNotTestedLabel(reportNotTestedLabel);		
			
			Resource getScoreStarPerformanceLevelPath = null;

			reportData.setProgressStatus(getProgressTextBasedonDomainScores(studentReportLists, testStatusMap, progressionTextMap, scoringStatusMap));
			
			getDomainScoreText(studentReportLists, testStatusMap, scoringStatusMap);
			
			reportData.setDomainScoreList(getDomainScoreList(studentReportLists));
					
			reportData.setLogoPath(kELPAHeaderLogoFile.getFile().toURI().toString());
			reportData.setFooterLogoPath(kapFooterLogoFile.getFile().toURI().toString());				
			ArrayList<Character> delimiter = new ArrayList<Character>();
			delimiter.add(' ');
			delimiter.add('-');
			data.setStudentLastName(replaceCapsAtSpecifiedChar(data.getStudentLastName(), delimiter));
			data.setStudentFirstName(replaceCapsAtSpecifiedChar(data.getStudentFirstName(), delimiter));
			
			//Checking Overall Proficiency Level Logic
			if((StringUtils.equalsIgnoreCase(testStatusMap.get(data.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&								
									
					(StringUtils.equalsIgnoreCase(testStatusMap.get(data.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
					
					(StringUtils.equalsIgnoreCase(testStatusMap.get(data.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
					
					(StringUtils.equalsIgnoreCase(testStatusMap.get(data.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
							StringUtils.equalsIgnoreCase(testStatusMap.get(data.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT))
					
					){
						
				if(data.getLevel() != null){
					Long mainLevel = data.getLevel();
					if(mainLevel.equals(CommonConstants.KELPA_STUDENT_REPORT_LEVEL_1)){
						getScoreStarPerformanceLevelPath = getKelpaScoreStarPerformanceLevel1Path;
					}else if(mainLevel.equals(CommonConstants.KELPA_STUDENT_REPORT_LEVEL_2)){
						getScoreStarPerformanceLevelPath = getKelpaScoreStarPerformanceLevel2Path;
					}else if(mainLevel.equals(CommonConstants.KELPA_STUDENT_REPORT_LEVEL_3)){
						getScoreStarPerformanceLevelPath = getKelpaScoreStarPerformanceLevel3Path;
					}
				}
			
			}else{
				getScoreStarPerformanceLevelPath = getKelpaScoreStarPerformanceLevel0Path;
				data.setPrevLevelString(reportInCompleteLevel);
			}
			
			File FileScoreStar = getScoreStarPerformanceLevelPath.getFile();
			String scoreStarPerformanceLevelPath = FileScoreStar.toURI().toString();
			reportData.setScoreStarPerformanceLevelPath(scoreStarPerformanceLevelPath);
			XStream xstream = new XStream();
			xstream.alias("reportDetails", ReportContext.class);
			xstream.aliasField("data", ReportContext.class, "data");
			TraxSource source = new TraxSource(reportData, xstream);
			String fullPath = FileUtil.buildFilePath(outDir, "SR_"+ sanitizeForPath(data.getStateStudentIdentifier())
			+ ".pdf");
			String[] splitFullPath = fullPath.split("\\.");
			File pdfFile =File.createTempFile(splitFullPath[0], ".pdf");
			File foFile = xslFile.getFile();
			generatePdf(pdfFile, foFile, source);
			s3.synchMultipartUpload(fullPath, pdfFile);
			data.setFilePath(getPathForDB(fullPath));
			FileUtils.deleteQuietly(pdfFile);
			data.setProgressionText(reportData.getProgressStatus());
			data.setGenerated(true);
			return data;
		} finally {
			
		}
	}

	private String createStudentDir(StudentReport data) throws IOException {
		String studentDir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+"KELPA"+File.separator+data.getSchoolYear()+File.separator+ "ISR" + File.separator
				+ data.getStateId() + File.separator + data.getDistrictId()
				+ File.separator + data.getAttendanceSchoolId()
				+ File.separator + data.getContentAreaCode()
				+ File.separator + data.getGradeCode() + File.separator
				+ data.getStudentId());
		return studentDir;
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
	
	private void getDomainScoreText(List<StudentReport> studentReportLists, Map<Long, String> testStatusMap, Map<Long, String> scoringStatusMap){
		
		//To setting minus -1 for Not Tested test status for each domain for current Year and Last Year
		
		for(StudentReport studentReport :studentReportLists){
			
			
			if(!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) &&
					!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) &&
					!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)
			)
					
				studentReport.setSpeakingLevel(-1l);
			
			if(!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) &&
					!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) &&
					!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)
			 )
				studentReport.setWritingLevel(-1l);
						
			if(!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) &&
			!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) &&
			!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)
			)
				studentReport.setListeningLevel(-1l);			
			
			if(!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) &&
			!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) &&
			!StringUtils.equalsIgnoreCase(testStatusMap.get(studentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)
			)
				studentReport.setReadingLevel(-1l);
			
		}	
	}
	
	private String getProgressTextBasedonDomainScores(List<StudentReport> studentReportLists, Map<Long, String> testStatusMap, Map<String, String> progressionTextMap, Map<Long, String> scoringStatusMap){
					
			boolean currentYearTestStatus = false;
			boolean lastYearTestStatus = false;
			
			StudentReport currentYearStudentReport = null;
			StudentReport lastYearStudentReport = null;
			
			Long differenceOfDomainScore = 0L;
			String progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_BLANK_CODE);
					
			if(studentReportLists.size()>1){
				
				 currentYearStudentReport = studentReportLists.get(1);
				 lastYearStudentReport = studentReportLists.get(0);
							
				Long sumOfLastYearDomainScores = lastYearStudentReport.getSpeakingLevel()+
						lastYearStudentReport.getWritingLevel()+
						lastYearStudentReport.getListeningLevel()+
						lastYearStudentReport.getReadingLevel();
				
				Long sumOfCurrentYearDomainScores = currentYearStudentReport.getSpeakingLevel()+
						currentYearStudentReport.getWritingLevel()+
						currentYearStudentReport.getListeningLevel()+
						currentYearStudentReport.getReadingLevel();
				
				differenceOfDomainScore = sumOfCurrentYearDomainScores - sumOfLastYearDomainScores;
				
				if((StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&								
								
						(StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
						
						(StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
						
						(StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT))								
					){
						
					currentYearTestStatus = true;
				}
				
				if(		(StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
						
						(StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
						
						(StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
						
						(StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(lastYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT))
						
						){
						
					lastYearTestStatus = true;
				}
			}else{
				
				 currentYearStudentReport = studentReportLists.get(0);
				 
				if(		(StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getListeningTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&								
										
						(StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getReadingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
						
						(StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getSpeakingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT)) &&
						
						(StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS) ||
						 StringUtils.equalsIgnoreCase(testStatusMap.get(currentYearStudentReport.getWritingTestStatus()), CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT))
						
						){
						
					currentYearTestStatus = true;
				}
				
			}
					
			//Current year Test Status complete
			if(currentYearTestStatus){
								
				//Last year Test Status complete and current year test complete				
					if(lastYearTestStatus){
						
						//Current year Overall Proficiency Level 3
						if(currentYearStudentReport.getLevel() == CommonConstants.KELPA_STUDENT_REPORT_LEVEL_3){
							progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROFICIENT_CODE);						
						}
						
						//Current year Overall Proficiency Level 1 or 2							
						else if(currentYearStudentReport.getLevel()== CommonConstants.KELPA_STUDENT_REPORT_LEVEL_1 || currentYearStudentReport.getLevel()== CommonConstants.KELPA_STUDENT_REPORT_LEVEL_2){
							
							//Sum of current Year domain scores and last year domain scores greater than zero
							if(differenceOfDomainScore>0){
								progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_STATISFACTORY_PROGRESS_CODE);								
							}
							
							//Sum of current Year domain scores and last year domain scores less than or equal to zero
							else if(differenceOfDomainScore<=0){
								progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROGRESS_NOT_DEMONSTRATED_CODE);
							}	
						}	
						
						//Sum of current Year domain scores and last year domain scores greater than zero
					/*	if(differenceOfDomainScore>0){
							
							//Current year Overall Proficiency Level 3
							if(currentYearStudentReport.getLevel() == CommonConstants.KELPA_STUDENT_REPORT_LEVEL_3){
								progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROFICIENT_CODE);
							}
							
							//Current year Overall Proficiency Level 1 or 2							
							else if(currentYearStudentReport.getLevel()== CommonConstants.KELPA_STUDENT_REPORT_LEVEL_1 || currentYearStudentReport.getLevel()== CommonConstants.KELPA_STUDENT_REPORT_LEVEL_2){
								progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_STATISFACTORY_PROGRESS_CODE);
							}							
						}
						
						//Sum of current Year domain scores and last year domain scores less than or equal to zero
						else if(differenceOfDomainScore<=0){
							progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROGRESS_NOT_DEMONSTRATED_CODE);
						}*/
						
					}
					
					//Last year Test Status incomplete and current year test complete										
					else{
						//Current year Overall Proficiency Level 3						
						if(currentYearStudentReport.getLevel() == CommonConstants.KELPA_STUDENT_REPORT_LEVEL_3){
							progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROFICIENT_CODE);
						}
						
						//Current year Overall Proficiency Level 1 or 2						
						else if(currentYearStudentReport.getLevel()== CommonConstants.KELPA_STUDENT_REPORT_LEVEL_1 || currentYearStudentReport.getLevel()== CommonConstants.KELPA_STUDENT_REPORT_LEVEL_2){
							progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_BLANK_CODE);
						}									
					}			
			
			}else{
				//Current year Test Status incomplete
				progressStatus = progressionTextMap.get(CommonConstants.KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROGRESS_NOT_DEMONSTRATED_CODE);
		   }	
			
			return progressStatus;
			
	}
	
	private List<StudentReport> getDomainScoreList(List<StudentReport> studentReportList){
		
		List<StudentReport> domainScoresLists = new ArrayList<StudentReport>();
		List<StudentReport> performanceLevelLists = new ArrayList<StudentReport>();
		
		if(studentReportList.size()>1){
			
			StudentReport lastYearStudentReport = studentReportList.get(0);
			StudentReport currentYearStudentReport = studentReportList.get(1);
		
			//Checking last year test status for all domain was completed
			if(lastYearStudentReport.getSpeakingLevel().longValue()==-1l && 
				lastYearStudentReport.getWritingLevel().longValue()==-1l &&
				lastYearStudentReport.getListeningLevel().longValue()==-1l && 
				lastYearStudentReport.getReadingLevel().longValue()==-1l){
					domainScoresLists.add(currentYearStudentReport);
			}else{
				domainScoresLists.add(lastYearStudentReport);
				domainScoresLists.add(currentYearStudentReport);
			}
		}else{
			StudentReport currentYearStudentReport = studentReportList.get(0);
			domainScoresLists.add(currentYearStudentReport);
		}
		
		for(StudentReport studentReport : domainScoresLists){
			StudentReport domainScore = new StudentReport();
			domainScore.setSchoolYear(studentReport.getSchoolYear());
			domainScore.setSpeakingLevel(studentReport.getSpeakingLevel());
			domainScore.setWritingLevel(studentReport.getWritingLevel());
			domainScore.setListeningLevel(studentReport.getListeningLevel());
			domainScore.setReadingLevel(studentReport.getReadingLevel());		
			performanceLevelLists.add(domainScore);
		}
		
		return performanceLevelLists;
	}

}