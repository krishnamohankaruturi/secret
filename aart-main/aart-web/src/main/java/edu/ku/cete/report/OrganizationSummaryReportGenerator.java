package edu.ku.cete.report;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.jfree.chart.ui.RectangleInsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.Rating;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.charts.AlphanumComparator;
import edu.ku.cete.report.charts.LevelCategoryDataset;
import edu.ku.cete.report.charts.SummaryLevelBarChartPlot;
import edu.ku.cete.report.charts.SummaryScoreBarPlot;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.util.FileUtil;

@Component
public class OrganizationSummaryReportGenerator extends ReportGenerator {

	@Value("/templates/xslt/reports/schoolsummary.xsl")
	private Resource schoolXslFile;
	
	@Value("/templates/xslt/reports/districtsummary.xsl")
	private Resource districtXslFile;
	
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
	
	@Autowired
	private AwsS3Service s3;

	public String generateReportFile(StudentReport data) throws Exception {
		List<File> gradeChartFiles = new ArrayList<File>();
		File axisLineSubScore = null;
		File axisLinePercentLevel = null;
		try {
			Collections.sort(data.getLevels(), new Comparator<LevelDescription>() {
				@Override
				public int compare(LevelDescription o1, LevelDescription o2) {
					return o1.getLevel().compareTo(o2.getLevel());
				}
			});
			
			String outDir = createOrgDir(data);
			
			ReportContext reportData = new ReportContext();
			reportData.setData(data);
			reportData.setPercentLevelChartWidth(515);
			reportData.setPercentLevelChartHeight(65);
			
			
			reportData.setLogoPath(getLogo(data.getAssessmentProgramCode()).toURI().toString());
			reportData.setFooterLogoPath(getFooterLogo(data.getAssessmentProgramCode()).toURI().toString());
			reportData.setUnusedAreaIconPath(getGraphUnusedAreaFile().getURI().toString());
	//		reportData.setGradePercentLevelCharts(new ArrayList<String>());
			reportData.setMainPercentLevelCharts(new ArrayList<String>());
			reportData.setMdptPercentLevelCharts(new ArrayList<String>());
			reportData.setCombinedPercentLevelCharts(new ArrayList<String>());
			reportData.setGradeMedianCharts(new ArrayList<String>());
			reportData.setSubScoreChartWidth(448);
			//added for rating pages start
			
			/*File fileTransferredStudent = getTransferredStudentPath.getFile();
			String transferredStudentPath = fileTransferredStudent.toURI().toString();
			reportData.setTransferredStudentPath(transferredStudentPath);
			*/
			reportData.setIconBelowPath((getIconBelowPath.getFile()).toURI().toString());
			reportData.setIconMeetsPath((getIconMeetsPath.getFile()).toURI().toString());
			reportData.setIconExceedsPath((getIconExceedsPath.getFile()).toURI().toString());
			reportData.setIconInsufficientPath((getIconInsufficientPath.getFile()).toURI().toString());
			//added for rating pages end
			if(data.getAttendanceSchoolId() != null) {
				reportData.setSubScoreChartHeight((15 * 3)+6);
			} else {
				reportData.setSubScoreChartHeight((15 * 2)+6);
			}
			
			/*
			 * X-Axis creation is removed in US-18799
			 */
			
			axisLineSubScore = generateMedianScoreXAxis(reportData, data, outDir);
			reportData.setAxisSubScoreChart(axisLineSubScore.toURI().toString());
			
			List<String> allGrades = getAllGrades(data);
			reportData.setAllGrades(allGrades);
			List<ReportStandardError> standardErrors=getstandardErrors(data,allGrades);
			reportData.setStandardErrorList(standardErrors);
			addRatingsforAllgrades(data,allGrades);
			File chart = null;
			boolean schPrivacy = true;
			boolean dtPrivacy = true;

			List<ReportsPercentByLevel> mainListReport = new ArrayList<ReportsPercentByLevel>();
			List<ReportsPercentByLevel> combinedListReport = new ArrayList<ReportsPercentByLevel>();
			List<ReportsPercentByLevel> mdptListReport = new ArrayList<ReportsPercentByLevel>();

			
			boolean isFirst = true;
			for(String grade: allGrades) {
				//check privacy
				schPrivacy = true;
				 dtPrivacy = true;
				if(data.getAttendanceSchoolId() != null && data.getAttendanceSchoolId().intValue() != 0) {
					for(ReportsMedianScore median: data.getMedianScores()) {
						if(data.getAttendanceSchoolId().equals(median.getOrganizationId())
								&& median.getGradeName().equals(grade)
								&& median.getScore() != null) {
							schPrivacy = false;
							break;
						}
					}
				} 
				if(data.getDistrictId() != null && data.getDistrictId().intValue() != 0) {
					for(ReportsMedianScore median: data.getMedianScores()) {
						if(data.getDistrictId().equals(median.getOrganizationId())
								&& median.getGradeName().equals(grade)
								&& median.getScore() != null) {
							dtPrivacy = false;
							break;
						}
					}
				}
				if(data.getAttendanceSchoolId() == null || data.getAttendanceSchoolId().intValue() == 0) {
					schPrivacy = false;
				}
				chart = generateMedianBarChart(reportData, data, grade, outDir, schPrivacy, dtPrivacy);
				reportData.getGradeMedianCharts().add(chart.toURI().toString());
				gradeChartFiles.add(chart);
				
				if(isFirst){				
					for (ReportsPercentByLevel reportPercent : data.getPercentByLevels()) {
						if("Main".equalsIgnoreCase(reportPercent.getLevelType())){
							mainListReport.add(reportPercent);
							
							if("10".equalsIgnoreCase(reportPercent.getGradeCode())){
								combinedListReport.add(reportPercent);
							}
						}
						if("Combined".equalsIgnoreCase(reportPercent.getLevelType())){
							if(!"10".equalsIgnoreCase(reportPercent.getGradeCode())){
							   combinedListReport.add(reportPercent);
							}
						}
						if("MDPT".equalsIgnoreCase(reportPercent.getLevelType())){
							if(!"10".equalsIgnoreCase(reportPercent.getGradeCode())){
							   mdptListReport.add(reportPercent);
							}
						}
						
					}
				}
				
				data.setPercentByLevels(mainListReport);
				chart = generatePercentLevelChart(reportData, data, grade, outDir, isFirst, schPrivacy, dtPrivacy,"Main");
				reportData.getMainPercentLevelCharts().add(chart.toURI().toString());
				gradeChartFiles.add(chart);
				
				data.setPercentByLevels(combinedListReport);
				chart = generatePercentLevelChart(reportData, data, grade, outDir, isFirst, schPrivacy, dtPrivacy,"Combined");
				reportData.getCombinedPercentLevelCharts().add(chart.toURI().toString());
				gradeChartFiles.add(chart);
				
				data.setPercentByLevels(mdptListReport);
				chart = generatePercentLevelChart(reportData, data, grade, outDir, isFirst, schPrivacy, dtPrivacy,"MDPT");
				reportData.getMdptPercentLevelCharts().add(chart.toURI().toString());
				gradeChartFiles.add(chart);
				
				isFirst = false;
			}
			
			/*
			 * X-Axis creation is removed in US-18799
			 */
			
			axisLinePercentLevel = generatePercentLevelXAxis(reportData, data, "",  outDir); 
			reportData.setAxisPercentLevelChart(axisLinePercentLevel.toURI().toString());
			
			XStream xstream = new XStream();
			xstream.alias("reportDetails", ReportContext.class);
			xstream.aliasField("data", ReportContext.class, "data");
			xstream.aliasField("allGrades", ReportContext.class, "allGrades");
			TraxSource source = new TraxSource(reportData, xstream);
			String fullPath = null;
			File pdfFile = null;
			File xslFile = null;
			if(data.getAttendanceSchoolId() != null) {
				fullPath = FileUtil.buildFilePath(outDir, "SSUMMARY_" + sanitizeForPath(data.getAttendanceSchoolName()) + ".pdf");
				xslFile = schoolXslFile.getFile();
			} else {
				fullPath = FileUtil.buildFilePath(outDir, "DSUMMARY_" + sanitizeForPath(data.getDistrictName()) + ".pdf");
				xslFile = districtXslFile.getFile();
			}
			String[] splitFullPath = fullPath.split("\\.");
			pdfFile = File.createTempFile(splitFullPath[0], ".pdf");
			generatePdf(pdfFile, xslFile, source);
			s3.synchMultipartUpload(fullPath, pdfFile);
			String dbFilePath = getPathForDB(fullPath);
			FileUtils.deleteQuietly(pdfFile);
			return dbFilePath;
		} finally {
			for(File f: gradeChartFiles) {
				FileUtils.deleteQuietly(f);
			}
			FileUtils.deleteQuietly(axisLineSubScore);
			FileUtils.deleteQuietly(axisLinePercentLevel);
		}
	}

	private String createOrgDir(StudentReport data) throws IOException {
		String orgDir = null;
		if(data.getAttendanceSchoolId() == null) {
			orgDir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+data.getCurrentSchoolYear()+File.separator+ "DS" + File.separator
					+ data.getStateId() + File.separator + data.getDistrictId()
					+ File.separator + data.getContentAreaCode());
		} else {
			orgDir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+data.getCurrentSchoolYear()+File.separator+ "SS" + File.separator
				+ data.getStateId() + File.separator + data.getDistrictId()
				+ File.separator + data.getAttendanceSchoolId()
				+ File.separator + data.getContentAreaCode());
		}
		return orgDir;
	}
	
	private File generatePercentLevelChart(ReportContext reportData, StudentReport data, String grade, String outDir, boolean isFirst, boolean schPrivacy, boolean dtPrivacy,String levelType) throws IOException {
		LevelCategoryDataset result = new LevelCategoryDataset();
		
		//School
		if(data.getAttendanceSchoolId() != null && data.getAttendanceSchoolId().intValue() != 0) {
			populateSchoolLevels(result, data, grade, reportData,schPrivacy,levelType);
		}
		//District
		populateDistrictLevels(result, data, grade, reportData, dtPrivacy,levelType);
		
		//State
		double totalStateLevelPercent=0;
		double stateLevelOverridedPercentage=0;
		int noOfOneCount=0;
		int totalNoOfNonOneCountExcludingZero =0;
		List<ReportsPercentByLevel> statePctLevels = getPercentLevels(data, "state", grade);
		for(int i=0; i<statePctLevels.size(); i++) {
			ReportsPercentByLevel pcts=statePctLevels.get(i);
			totalStateLevelPercent+=pcts.getPercent();
			if((pcts.getPercent()==1)) noOfOneCount++;		
			if((pcts.getPercent()>0)) totalNoOfNonOneCountExcludingZero++;
		}
		
		double noOfEmptySpace =noOfOneCount*0.5;
						
		for(int i=0; i<statePctLevels.size(); i++) {
			ReportsPercentByLevel pcts=statePctLevels.get(i);
			if("MDPT".equalsIgnoreCase(levelType) && grade.contains("10")){
					result.addValue(0, pcts.getLevel(), "STATE");
			}else{ 
			//if(i<2) {
			//	result.addValue(-1*pcts.getPercent(), pcts.getLevel(), "State");
			//} else {
				result.addValue(pcts.getPercent(), pcts.getLevel(), "STATE");
				
				stateLevelOverridedPercentage=(pcts.getPercent()/totalStateLevelPercent)*100;	
				if(pcts.getPercent()==1) 
				stateLevelOverridedPercentage = stateLevelOverridedPercentage+0.5;
				if(pcts.getPercent()>1)
				stateLevelOverridedPercentage = stateLevelOverridedPercentage - (noOfEmptySpace/(totalNoOfNonOneCountExcludingZero-noOfOneCount));
				
				result.setpercentMap(stateLevelOverridedPercentage, pcts.getLevel(), "STATE");
			//}
			}
		}
		
		if(!data.getLevels().isEmpty()) {
			result.setFirstLevel(data.getLevels().get(0).getLevel());
			result.setLastLevel(data.getLevels().get(data.getLevels().size()-1).getLevel());
		}
		
		int width = reportData.getPercentLevelChartWidth();
		int height = reportData.getPercentLevelChartHeight();
		SummaryLevelBarChartPlot plot = new SummaryLevelBarChartPlot(result, data.getAssessmentProgramCode(), grade, false);
		plot.setFirst(isFirst);

		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		chart.setBackgroundPaint(new Color(0,0,0,0));
		
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
        Rectangle r = new Rectangle(0, 0, width, height);
        chart.draw(g2, r);

        String fullPath = null;
        if(schPrivacy && dtPrivacy && !("MDPT".equalsIgnoreCase(levelType) && grade.contains("10"))) {
        	fullPath = FileUtil.buildFilePath(outDir, "privacy3_"+grade+"_"+levelType+"_percentlevel_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        } else  if(schPrivacy &&  !dtPrivacy && !("MDPT".equalsIgnoreCase(levelType) && grade.contains("10"))) {
        	fullPath = FileUtil.buildFilePath(outDir, "privacy1_"+grade+"_"+levelType+"_percentlevel_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        } else  if(!schPrivacy &&  dtPrivacy && !("MDPT".equalsIgnoreCase(levelType) && grade.contains("10"))) {
        	fullPath = FileUtil.buildFilePath(outDir, "privacy2_"+grade+"_"+levelType+"_percentlevel_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        } else {
        	fullPath = FileUtil.buildFilePath(outDir, grade+"_"+levelType+"_percentlevel_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        }
        String[] splitFullPath = fullPath.split("\\.");
        File f = File.createTempFile(splitFullPath[0], ".svg");
        SVGUtils.writeToSVG(f, g2.getSVGElement());
        
		return f;
	}
	
	private File generatePercentLevelXAxis(ReportContext reportData, StudentReport data, String grade, String outDir) throws IOException {
		LevelCategoryDataset result = new LevelCategoryDataset();
		
		if(!data.getLevels().isEmpty()) {
			result.setFirstLevel(data.getLevels().get(0).getLevel());
			result.setLastLevel(data.getLevels().get(data.getLevels().size()-1).getLevel());
		}
		
		int width = reportData.getPercentLevelChartWidth();
		int height = 21;
		SummaryLevelBarChartPlot plot = new SummaryLevelBarChartPlot(result, data.getAssessmentProgramCode(), grade, true);
		
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		chart.setBackgroundPaint(new Color(0,0,0,0));
		
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
        Rectangle r = new Rectangle(0, 0, width, height);
        chart.draw(g2, r);
        String fullPath = FileUtil.buildFilePath(outDir, "xaxis_percentlevel_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        String[] splitFullPath = fullPath.split("\\.");
        File f = File.createTempFile(splitFullPath[0], ".svg");
        SVGUtils.writeToSVG(f, g2.getSVGElement());
        
		return f;
	}

	private void populateSchoolLevels(LevelCategoryDataset result, StudentReport data, String grade, ReportContext reportData, boolean privacy,String levelType) {
		int numberOfStudents=0;
		for(ReportsMedianScore median: data.getMedianScores()) {
			if(median.getStudentCount() != null 
					&& data.getAttendanceSchoolId().equals(median.getOrganizationId())
					&& median.getGradeName().equals(grade)) {
				numberOfStudents = median.getStudentCount().intValue();
				break;
			}
		}
		List<ReportsPercentByLevel> schoolPctLevels = getPercentLevels(data, "school", grade);
		
		int actualLevelCount = 0;
		boolean lessThanTwo = false;
		boolean suppressLevels = false;
		/*for(ReportsPercentByLevel pctLevel: schoolPctLevels) {
			if(pctLevel.getPercent() != null && pctLevel.getPercent().intValue() > 0) {
				actualLevelCount++;
				if(pctLevel.getStudentCount().intValue() <=2) {
					lessThanTwo = true;
				}
			}
		}
		
		if(actualLevelCount == 1 || (actualLevelCount == 2 && lessThanTwo)) {
			suppressLevels = true;
		}*/
		
		double totalSchoolLevelPercent=0;
		double schoolLevelOverridedPercentage=0;
		
		int noOfOneCount=0;
		int totalNoOfNonOneCountExcludingZero =0;
		for(int i=0; i<schoolPctLevels.size(); i++) {
			ReportsPercentByLevel pcts=schoolPctLevels.get(i);
			totalSchoolLevelPercent+=pcts.getPercent();	
			if((pcts.getPercent()==1)) noOfOneCount++;
			if((pcts.getPercent()>0)) totalNoOfNonOneCountExcludingZero++;
		}
		double noOfEmptySpace =noOfOneCount*0.5;		
		for(int i=0; i<schoolPctLevels.size(); i++) {
			ReportsPercentByLevel pcts=schoolPctLevels.get(i);
			
			if("MDPT".equalsIgnoreCase(levelType) && grade.contains("10")){
				result.addValue(0, pcts.getLevel(), "SCHOOL");
			}else{ 
				if(privacy) {
					/*if(i<2) {
						result.addValue(-0, pcts.getLevel(), "School");
					} else {*/
						result.addValue(0, pcts.getLevel(), "SCHOOL");
					//}
				} else {
					if(suppressLevels) {
						/*if(i<2) {
							result.addValue(-1*25, pcts.getLevel(), "School **");
						} else {*/
							result.addValue(pcts.getPercent(), pcts.getLevel(), "SCHOOL **");
							schoolLevelOverridedPercentage=(pcts.getPercent()/totalSchoolLevelPercent)*100;
							if(pcts.getPercent()==1) 
								schoolLevelOverridedPercentage = schoolLevelOverridedPercentage+0.5;
							if(pcts.getPercent()>1)
								schoolLevelOverridedPercentage = schoolLevelOverridedPercentage - (noOfEmptySpace/(totalNoOfNonOneCountExcludingZero-noOfOneCount));
							result.setpercentMap(schoolLevelOverridedPercentage, pcts.getLevel(), "SCHOOL **");
						//}
						result.setSupressLabelValue(getSupressLabelValue(pcts, schoolPctLevels, numberOfStudents), pcts.getLevel(), "SCHOOL **");
						reportData.setSuppressLevels(1);
					}else {
	/*					if(i<2) {
							result.addValue(-1*pcts.getPercent(), pcts.getLevel(), "School");
						} else {*/
							result.addValue(pcts.getPercent(), pcts.getLevel(), "SCHOOL");
							schoolLevelOverridedPercentage=(pcts.getPercent()/totalSchoolLevelPercent)*100;
							if(pcts.getPercent()==1) 
								schoolLevelOverridedPercentage = schoolLevelOverridedPercentage+0.5;
							if(pcts.getPercent()>1)
								schoolLevelOverridedPercentage = schoolLevelOverridedPercentage - (noOfEmptySpace/(totalNoOfNonOneCountExcludingZero-noOfOneCount));
							result.setpercentMap(schoolLevelOverridedPercentage, pcts.getLevel(), "SCHOOL **");result.setpercentMap(schoolLevelOverridedPercentage, pcts.getLevel(), "SCHOOL");
					//	}
					}
				}
			}
		}
	}
	
	private void populateDistrictLevels(LevelCategoryDataset result, StudentReport data, String grade, ReportContext reportData, boolean privacy,String levelType) {
		int numberOfStudents=0;
		for(ReportsMedianScore median: data.getMedianScores()) {
			if(median.getStudentCount() != null
					&& data.getDistrictId().equals(median.getOrganizationId())
					&& median.getGradeName().equals(grade)) {
				numberOfStudents = median.getStudentCount().intValue();
			}
		}
		List<ReportsPercentByLevel> districtPctLevels = getPercentLevels(data, "district", grade);

		int actualLevelCount = 0;
		boolean lessThanTwo = false;
		boolean suppressLevels = false;
	/*	for(ReportsPercentByLevel pctLevel: districtPctLevels) {
			if(pctLevel.getPercent() != null && pctLevel.getPercent().intValue() > 0) {
				actualLevelCount++;
				if(pctLevel.getStudentCount().intValue() <=2) {
					lessThanTwo = true;
				}
			}
		}
		
		if(actualLevelCount == 1 || (actualLevelCount == 2 && lessThanTwo)) {
			suppressLevels = true;
		}
*/		
		double totalDistrictLevelPercent=0;
		double districtLevelOverridedPercentage=0;	
		int noOfOneCount=0;
		int totalNoOfNonOneCountExcludingZero =0;
		for(int i=0; i<districtPctLevels.size(); i++) {
			ReportsPercentByLevel pcts=districtPctLevels.get(i);
			totalDistrictLevelPercent+=pcts.getPercent();	
			if((pcts.getPercent()==1)) noOfOneCount++;
			if((pcts.getPercent()>0)) totalNoOfNonOneCountExcludingZero++;
		}
		double noOfEmptySpace =noOfOneCount*0.5;
		for(int i=0; i<districtPctLevels.size(); i++) {
			ReportsPercentByLevel pcts=districtPctLevels.get(i);
			if("MDPT".equalsIgnoreCase(levelType) && grade.contains("10")){
					result.addValue(0, pcts.getLevel(), "DISTRICT");
			} else{ 
				if(privacy) {
					/*if(i<2) {
						result.addValue(-0, pcts.getLevel(), "District");
					} else {*/
						result.addValue(0, pcts.getLevel(), "DISTRICT");
					//}
				}else {
					if(suppressLevels) {
						/*if(i<2) {
							result.addValue(-1*25, pcts.getLevel(), "District **");
						} else {*/
							result.addValue(pcts.getPercent(), pcts.getLevel(), "DISTRICT **");
						//}
						result.setSupressLabelValue(getSupressLabelValue(pcts, districtPctLevels, numberOfStudents), pcts.getLevel(), 
								"DISTRICT **");
						districtLevelOverridedPercentage=(pcts.getPercent()/totalDistrictLevelPercent)*100;
						if(pcts.getPercent()==1) 
							districtLevelOverridedPercentage = districtLevelOverridedPercentage+0.5;
						if(pcts.getPercent()>1)
							districtLevelOverridedPercentage = districtLevelOverridedPercentage - (noOfEmptySpace/(totalNoOfNonOneCountExcludingZero-noOfOneCount));
						
						result.setpercentMap(districtLevelOverridedPercentage, pcts.getLevel(), "DISTRICT **");
						reportData.setSuppressLevels(1);
					} else {
						/*if(i<2) {
							result.addValue(-1*pcts.getPercent(), pcts.getLevel(), "District");
						} else {*/
							result.addValue(pcts.getPercent(), pcts.getLevel(), "DISTRICT");
							districtLevelOverridedPercentage=(pcts.getPercent()/totalDistrictLevelPercent)*100;
							
							if(pcts.getPercent()==1) 
								districtLevelOverridedPercentage = districtLevelOverridedPercentage+0.5;
							if(pcts.getPercent()>1)
								districtLevelOverridedPercentage = districtLevelOverridedPercentage - (noOfEmptySpace/(totalNoOfNonOneCountExcludingZero-noOfOneCount));
							result.setpercentMap(districtLevelOverridedPercentage, pcts.getLevel(), "DISTRICT");
						//}
					}
				}
			}
		}
	}
	
	private String getSupressLabelValue(ReportsPercentByLevel currentLevelPct, List<ReportsPercentByLevel> schoolPctLevels, int numberOfStudents) {
		ReportsPercentByLevel maxLevel = Collections.max(schoolPctLevels, new Comparator<ReportsPercentByLevel>() {
			@Override
			public int compare(ReportsPercentByLevel o1, ReportsPercentByLevel o2) {
				return o1.getStudentCount().compareTo(o2.getStudentCount());
			}
		});

		String levelLabel = "-";
		if(maxLevel.getLevel().equals(currentLevelPct.getLevel())) {
			if(numberOfStudents >=5 && numberOfStudents <=7) {
				levelLabel = ">=60";
			} else if(numberOfStudents >=8 && numberOfStudents <=9) {
				levelLabel = ">=75";
			} else if(numberOfStudents >=10 && numberOfStudents <=19) {
				levelLabel = ">=80";
			} else if(numberOfStudents >=20 && numberOfStudents <=39) {
				levelLabel = ">=90";
			} else if(numberOfStudents >=40) {
				levelLabel = ">=95";
			}
		} else {
			if(numberOfStudents >=5 && numberOfStudents <=7) {
				levelLabel = "<=40";
			} else if(numberOfStudents >=8 && numberOfStudents <=9) {
				levelLabel = "<=25";
			} else if(numberOfStudents >=10 && numberOfStudents <=19) {
				levelLabel = "<=20";
			} else if(numberOfStudents >=20 && numberOfStudents <=39) {
				levelLabel = "<=10";
			} else if(numberOfStudents >=40) {
				levelLabel = "<=5";
			}
		}
		return levelLabel;
	}
	
	private List<ReportsPercentByLevel> getPercentLevels(final StudentReport data, final String type, final String grade) {
		List<ReportsPercentByLevel> pctLevels = new ArrayList<ReportsPercentByLevel>();
		if(data.getPercentByLevels() != null){
			pctLevels.addAll(data.getPercentByLevels());
			CollectionUtils.filter(pctLevels, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					ReportsPercentByLevel pctLevel = (ReportsPercentByLevel) object;
					if(grade.equals(pctLevel.getGradeName())) {
						if(type.equals("school") && data.getAttendanceSchoolId() != null && data.getAttendanceSchoolId().equals(pctLevel.getOrganizationId())) {
							return true;
						} else if(type.equals("district") && data.getDistrictId().equals(pctLevel.getOrganizationId())) {
							return true;
						} else if(type.equals("state") && data.getStateId().equals(pctLevel.getOrganizationId())) {
							return true;
						}
						return false;
					}
					return false;
				}
			});
		}
		if(pctLevels.size() < data.getLevels().size()) {
			Collections.sort(pctLevels, new ReportsPercentByLevelComparator());
			boolean exists = false;
			int lower = data.getLevels().get(0).getLevel().intValue();
			if(lower > 0) {
				lower = lower-1;
			}
			int	upper = data.getLevels().get(data.getLevels().size()-1).getLevel().intValue();
			for(int i=lower; i<upper; i++) {
				exists = false;
				for(ReportsPercentByLevel pctLevel: pctLevels) {
					if(pctLevel.getLevel().intValue() == (i+1)) {
						exists = true;
						break;
					}
				}
				if(!exists) {
					ReportsPercentByLevel pctLevel = new ReportsPercentByLevel();
					if(type.equals("school") && data.getAttendanceSchoolId() != null) {
						pctLevel.setOrganizationId(data.getAttendanceSchoolId());
					} else if(type.equals("district") && data.getDistrictId() != null) {
						pctLevel.setOrganizationId(data.getDistrictId());
					} else if(type.equals("state") && data.getStateId() != null) {
						pctLevel.setOrganizationId(data.getStateId());
					}
					
					pctLevel.setLevel(new Long((i+1)));
					pctLevel.setPercent(0);
					pctLevel.setStudentCount(0);
					pctLevels.add(pctLevel);
				}
			}
		}
		Collections.sort(pctLevels, new ReportsPercentByLevelComparator());
		/*if(pctLevels.size() >= 2){
			Collections.swap(pctLevels, 0, 1);
		}*/
		return pctLevels;
	}
	
	private class ReportsPercentByLevelComparator implements Comparator<ReportsPercentByLevel> {
		@Override
		public int compare(ReportsPercentByLevel o1, ReportsPercentByLevel o2) {
			return o1.getLevel().compareTo(o2.getLevel());
		}
	}
	
	protected File generateMedianBarChart(final ReportContext reportData, final StudentReport data, final String type, final String path, final boolean schPrivacy, final boolean dtPrivacy) throws IOException {
		String fullPath = null;
		if(schPrivacy && dtPrivacy) {
        	fullPath = FileUtil.buildFilePath(path, "privacy3_"+type+"_medianscore_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        } else  if(schPrivacy &&  !dtPrivacy) {
        	fullPath = FileUtil.buildFilePath(path, "privacy1_"+type+"_medianscore_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        } else  if(!schPrivacy &&  dtPrivacy) {
        	fullPath = FileUtil.buildFilePath(path, "privacy2_"+type+"_medianscore_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        } else {
        	fullPath = FileUtil.buildFilePath(path, type + "_medianscore_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        }
		String[] splitFullPath = fullPath.split("\\.");
		File f = File.createTempFile(splitFullPath[0], ".svg");
		if(data.getMedianScores() != null){
			DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
			boolean schoolFound = false;
			for(ReportsMedianScore medianScore: data.getMedianScores()) {
				if(type.equals(medianScore.getGradeName())) {
					if(data.getAttendanceSchoolId() != null && data.getAttendanceSchoolId().equals(medianScore.getOrganizationId())) {
						schoolFound = true;
						if(medianScore.getScore() == null) {
							dataset.add(0.1d, 0.1d, type, "School");
						} else {
							dataset.add(medianScore.getScore().doubleValue(), medianScore.getStandardError().doubleValue(), type,  "School");
						}
						break;
					} 
				}
			}
			if(!schoolFound && data.getAttendanceSchoolId() != null) {
				dataset.add(0.1d, 0.1d, type, "School");
			} 
			boolean distFound = false;
			for(ReportsMedianScore medianScore: data.getMedianScores()) {
				if(type.equals(medianScore.getGradeName())) {
					if(data.getDistrictId().equals(medianScore.getOrganizationId())) {
						distFound = true;
						if(medianScore.getScore() == null) {
							dataset.add(0.1d, 0.1d, type, "District");
						} else {
							dataset.add(medianScore.getScore().doubleValue(), medianScore.getStandardError().doubleValue(), type,  "District");
						}
						break;
					} 
				}
			}
			if(!distFound && data.getDistrictId() != null) {
				dataset.add(0.1d, 0.1d, type, "District");
			} 
			
			for(ReportsMedianScore medianScore: data.getMedianScores()) {
				if(type.equals(medianScore.getGradeName())) {
					if(data.getStateId().equals(medianScore.getOrganizationId())) {
						if(medianScore.getScore() == null) {
							dataset.add(0.1d, 0.1d, type, "State");
						} else {
							dataset.add(medianScore.getScore().doubleValue(), medianScore.getStandardError().doubleValue(), type,  "State");
						}
						break;
					}
				}
			}
			
			
			int width = reportData.getSubScoreChartWidth();
			int height = reportData.getSubScoreChartHeight();
			SummaryScoreBarPlot plot = new SummaryScoreBarPlot(dataset, getMeterRange(data.getLevels()), false, false, false);
			
			JFreeChart chart = new JFreeChart(plot);
			chart.setBackgroundPaint(new Color(0,0,0,0));
			chart.removeLegend();
			chart.setPadding(new RectangleInsets(0,0,0,7));
			chart.setBorderVisible(false);
			SVGGraphics2D g2 = new SVGGraphics2D(width, height-2);
			Rectangle r = new Rectangle(0, 0, width, height-2);
			chart.draw(g2, r);
			SVGUtils.writeToSVG(f, g2.getSVGElement());
		}
		return f;
	}
	
	protected File generateMedianScoreXAxis(final ReportContext reportData, final StudentReport data, final String path) throws IOException {
		String fullPath = FileUtil.buildFilePath(path, "xaxis_ss_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
		String[] splitFullPath = fullPath.split("\\.");
		File f = File.createTempFile(splitFullPath[0], ".svg");
		int width = reportData.getSubScoreChartWidth();
		int height = 13;

		SummaryScoreBarPlot plot = new SummaryScoreBarPlot(new DefaultStatisticalCategoryDataset(), getMeterRange(data.getLevels()), true, false, false);
		plot.getRangeAxis().setAxisLineVisible(false);
		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(new Color(0,0,0,0));
		chart.removeLegend();
		chart.setPadding(new RectangleInsets(0,0,0,7));
		chart.setBorderVisible(false);
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
		Rectangle r = new Rectangle(0, 0, width, height);
		chart.draw(g2, r);
		SVGUtils.writeToSVG(f, g2.getSVGElement());
		return f;
	}
	
	private List<String> getAllGrades(final StudentReport data) {
		List<String> allGrades = new ArrayList<String>();
		for(ReportsMedianScore median: data.getMedianScores()) {
			if(!allGrades.contains(median.getGradeName())) {
				allGrades.add(median.getGradeName());
			}
		}
		//sort in display order
		Collections.sort(allGrades, new AlphanumComparator());
		return allGrades;
	}
	private void addRatingsforAllgrades(final StudentReport data,List<String> allGrades) {
				
		List<String> fewerLessScoreGradesIds = new ArrayList<String>();
		if((data.getDistrictId() != null && data.getDistrictId().intValue() != 0)  
			|| (data.getAttendanceSchoolId()!=null && data.getAttendanceSchoolId().intValue() != 0)){			
			for(String grade: allGrades) {
				for(ReportsMedianScore median: data.getMedianScores()) {
					if(((data.getDistrictId()!=null && data.getDistrictId().equals(median.getOrganizationId())) || (data.getAttendanceSchoolId()!=null && data.getAttendanceSchoolId().equals(median.getOrganizationId()))
							)&& median.getGradeName().equals(grade)
							&& median.getScore() == null) {
							fewerLessScoreGradesIds.add(median.getGradeName());							
				     }				 
			     }
				
			}
		}
		List<Integer> reatingposition= new ArrayList<Integer>();
		if(allGrades!=null){
			for(String grade:fewerLessScoreGradesIds){
				reatingposition.add(allGrades.indexOf(grade));
			}
		}
		
		List<Long> allGradesIds = new ArrayList<Long>();
		for(ReportsMedianScore median: data.getMedianScores()) {
			if(!allGradesIds.contains(median.getGradeId())) {
				allGradesIds.add(median.getGradeId());
			}
		}
		Integer size=allGradesIds.size();
		for(ReportSubscores ReportSubscore:data.getTestLevelSubscoreBuckets()){
			List<Long> missedGrades=new ArrayList();
			
			if(ReportSubscore.getGradeRatings().size()<size){
				for(Long gradeId:allGradesIds){
					boolean gradeNotexist=true;
					for(Rating rating:ReportSubscore.getGradeRatings()){
						if(rating.getGradeID().equals(gradeId)){
							gradeNotexist=false;
						}
					}
					if(gradeNotexist){
						missedGrades.add(gradeId);
					}
				}
			}
			for(Long missedgradeId:missedGrades){
				Rating missedGradeRating=new Rating();
				missedGradeRating.setGradeID(missedgradeId);
				missedGradeRating.setRating(0);
				ReportSubscore.getGradeRatings().add(missedGradeRating);
				
			}
			Collections.sort(ReportSubscore.getGradeRatings());
			Collections.reverse(ReportSubscore.getGradeRatings());
			
			if(ReportSubscore!=null && ReportSubscore.getGradeRatings()!=null && allGrades!=null &&
					ReportSubscore.getGradeRatings().size()>0 && ReportSubscore.getGradeRatings().size()==allGrades.size()){	
				for(Integer gradePos:reatingposition){
					if(gradePos >= 0){
					Rating rating=ReportSubscore.getGradeRatings().get(gradePos);
					if(rating.getRating()!=0)
					rating.setRating(10);
					}
				}
			}
		}
		Collections.sort(data.getTestLevelSubscoreBuckets());		
	}
	private List<ReportStandardError> getstandardErrors(final StudentReport data,List<String> allGrades) {
		List<ReportStandardError> standardErrors = new ArrayList<ReportStandardError>();
		List<Long> allGradesIds = new ArrayList<Long>();
		for(ReportsMedianScore median: data.getMedianScores()) {
			if(!allGradesIds.contains(median.getGradeId())) {
				allGradesIds.add(median.getGradeId());
			}
		}
		//sort in display order
		Collections.sort(allGradesIds);
		for(Long gradeId:allGradesIds){
			ReportStandardError reportStandardError=new ReportStandardError();
		for(ReportsMedianScore median: data.getMedianScores()) {
			
			if(median.getGradeId().equals(gradeId)){
				if(reportStandardError.getGradeName()==null||reportStandardError.getGradeName().isEmpty()){
					reportStandardError.setGradeName(median.getGradeName());
				}
				if(reportStandardError.getGradeId()==null){
					reportStandardError.setGradeId(median.getGradeId());
				}
				
				if(median.getOrgTypeCode().equals("SCH")){
					if(median.getStandardError()!=null && median.getStudentCount()>=10){
				reportStandardError.setSchoolStandardError(median.getStandardError().setScale(1,BigDecimal.ROUND_HALF_EVEN).toPlainString());
					}else{
						reportStandardError.setSchoolStandardError("N/A");
					}
				}
				if(median.getOrgTypeCode().equals("DT")){
					if(median.getStandardError()!=null && median.getStudentCount()>=10){
				reportStandardError.setDistrictStandardError(median.getStandardError().setScale(1,BigDecimal.ROUND_HALF_EVEN).toPlainString());
					}else{
						reportStandardError.setDistrictStandardError("N/A");
					}
				}
				if(median.getOrgTypeCode().equals("ST")){
					if(median.getStandardError()!=null && median.getStudentCount()>=10){
				reportStandardError.setStateStandardError(median.getStandardError().setScale(1,BigDecimal.ROUND_HALF_EVEN).toPlainString());
					}else{
						reportStandardError.setStateStandardError("N/A");
					}
				}
			}
		}
		standardErrors.add(reportStandardError);
		}
		//sort in display order		
		Collections.sort(standardErrors);
		Collections.reverse(standardErrors);
		return standardErrors;
	}
	
}