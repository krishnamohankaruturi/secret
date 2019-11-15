package edu.ku.cete.report;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.charts.LevelCategoryDataset;
import edu.ku.cete.report.charts.StudentLevelBarChartPlot;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.util.FileUtil;

@Component
public class OrganizationDetailReportGenerator extends ReportGenerator {

	@Value("/templates/xslt/reports/schooldetail.xsl")
	private Resource schoolXslFile;
	
	@Value("/templates/xslt/reports/districtdetail.xsl")
	private Resource districtXslFile;
	
	@Autowired
	private AwsS3Service s3;

	public String generateReportFile(StudentReport data) throws Exception {
		File percentLevelChart = null;
		File schoolMeter = null;
		File districtMeter = null;
		File stateMeter = null;
		File schoolSubScore = null;
		File districtSubScore = null;
		File stateSubScore = null;
		File axisLineSubScore = null;
		File pdfFile = null;
		
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
			if(data.getAttendanceSchoolId() != null) {
				reportData.setSmallMeterWidth(190);
				reportData.setSmallMeterHeight(90);
			} else {
				reportData.setSmallMeterWidth(220);
				reportData.setSmallMeterHeight(120);
			}
			reportData.setPercentLevelChartWidth(470);
			reportData.setPercentLevelChartHeight(110);
			
			districtMeter = generateMeter(data.getAssessmentProgramCode(), data.getLevels(), getMeterValue(data, "district"), "district", outDir, reportData.getSmallMeterWidth(),reportData.getSmallMeterHeight());
			stateMeter = generateMeter(data.getAssessmentProgramCode(), data.getLevels(), getMeterValue(data, "state"), "state", outDir, reportData.getSmallMeterWidth(),reportData.getSmallMeterHeight());
			percentLevelChart = generatePercentLevelChart(reportData, data, outDir);
			
			//to not to generate page2
			if(data.getSubscoreBuckets() != null && data.getSubscoreBuckets().isEmpty()) {
				data.setSubscoreBuckets(null);
			}
			
			List<ReportSubscores> allSubScores = getSubScores(data, "state");
			
			districtSubScore = generateSubScoreChart(reportData, data, "district", outDir, allSubScores);
			stateSubScore = generateSubScoreChart(reportData, data, "state", outDir, allSubScores);
			axisLineSubScore = generateSubScoreXAxis(reportData, data, outDir);
			reportData.setDistrictMeter(districtMeter.toURI().toString());
			reportData.setStateMeter(stateMeter.toURI().toString());
			reportData.setPercentLevelChart(percentLevelChart.toURI().toString());
			reportData.setLogoPath(getLogo(data.getAssessmentProgramCode()).toURI().toString());
			reportData.setFooterLogoPath(getFooterLogo(data.getAssessmentProgramCode()).toURI().toString());
			reportData.setDistrictSubScoreChart(districtSubScore.toURI().toString());
			reportData.setAxisSubScoreChart(axisLineSubScore.toURI().toString());
			reportData.setStateSubScoreChart(stateSubScore.toURI().toString());
			
			if(data.getAttendanceSchoolId() != null) {
				schoolMeter = generateMeter(data.getAssessmentProgramCode(), data.getLevels(), getMeterValue(data, "school"), "school", outDir, reportData.getSmallMeterWidth(),reportData.getSmallMeterHeight());
				reportData.setSchoolMeter(schoolMeter.toURI().toString());
				schoolSubScore = generateSubScoreChart(reportData, data, "school", outDir, allSubScores);
				reportData.setSchoolSubScoreChart(schoolSubScore.toURI().toString());
			}
			
			if(reportData.getSubScoreChartHeight() == 0) {
				reportData.setSubScoreChartHeight(35);
			}
			
			XStream xstream = new XStream();
			xstream.alias("reportDetails", ReportContext.class);
			xstream.aliasField("data", ReportContext.class, "data");
			TraxSource source = new TraxSource(reportData, xstream);

			String fullPath = "";
			if(data.getAttendanceSchoolId() != null) {
				fullPath = FileUtil.buildFilePath(outDir, "SCHOOL_DETAIL_" + sanitizeForPath(data.getAttendanceSchoolName()) + ".pdf");
				String[] splitFullPath = fullPath.split("\\.");
				pdfFile = File.createTempFile(splitFullPath[0], ".pdf");

				generatePdf(pdfFile, schoolXslFile.getFile(), source);
			} else {
				fullPath = FileUtil.buildFilePath(outDir, "DISTRICT_DETAIL_" + sanitizeForPath(data.getDistrictName()) + ".pdf");
				String[] splitFullPath = fullPath.split("\\.");
				pdfFile = File.createTempFile(splitFullPath[0], ".pdf");
				
				generatePdf(pdfFile, districtXslFile.getFile(), source);
			}
			s3.synchMultipartUpload(fullPath, pdfFile);
			return getPathForDB(fullPath);
		} finally {
			FileUtils.deleteQuietly(pdfFile);
			FileUtils.deleteQuietly(schoolMeter);
			FileUtils.deleteQuietly(districtMeter);
			FileUtils.deleteQuietly(stateMeter);
			FileUtils.deleteQuietly(percentLevelChart);
			
			FileUtils.deleteQuietly(schoolSubScore);
			FileUtils.deleteQuietly(districtSubScore);
			FileUtils.deleteQuietly(stateSubScore);
			FileUtils.deleteQuietly(axisLineSubScore);
		}
	}

	private String createOrgDir(StudentReport data) throws IOException {
		String orgDir = null;
		if(data.getAttendanceSchoolId() == null) {
			orgDir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+data.getCurrentSchoolYear()+File.separator+ "DD" + File.separator
					+ data.getStateId() + File.separator + data.getDistrictId()
					+ File.separator + data.getContentAreaCode()
					+ File.separator + data.getGradeCode());
		}
		else {
			orgDir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+data.getCurrentSchoolYear()+File.separator+ "SD" + File.separator
				+ data.getStateId() + File.separator + data.getDistrictId()
				+ File.separator + data.getAttendanceSchoolId()
				+ File.separator + data.getContentAreaCode()
				+ File.separator + data.getGradeCode());
		}
		return orgDir;
	}
	
	private Long getMeterValue(StudentReport data, String type) {
		Long meterValue = null;
		if (type.equals("student")) {
			meterValue = data.getScaleScore();
		} else if (type.equals("school") || type.equals("district") || type.equals("state")) {
			for (ReportsMedianScore median : data.getMedianScores()) {
				if (type.equals("school") && data.getAttendanceSchoolId().equals(median.getOrganizationId())) {
					meterValue = median.getScore();
					break;
				} else if (type.equals("district") && data.getDistrictId().equals(median.getOrganizationId())) {
					meterValue = median.getScore();
					break;
				} else if (type.equals("state") && data.getStateId().equals(median.getOrganizationId())) {
					meterValue = median.getScore();
					break;
				}
			}
		}
		return meterValue;
	}
	
	private File generatePercentLevelChart(ReportContext reportData, StudentReport data, String outDir) throws IOException {
		LevelCategoryDataset result = new LevelCategoryDataset();
		
		List<ReportsPercentByLevel> statePctLevels = getPercentLevels(data, "state");
		
		//School
		if(data.getAttendanceSchoolId() != null && data.getAttendanceSchoolId().intValue() != 0) {
			populateSchoolLevels(result, data, reportData);
		}
		//District
		populateDistrictLevels(result, data, reportData);
		
		//State
		for(int i=0; i<statePctLevels.size(); i++) {
			ReportsPercentByLevel pcts=statePctLevels.get(i);
			if(i<2) {
				result.addValue(-1*pcts.getPercent(), pcts.getLevel(), "State");
			} else {
				result.addValue(pcts.getPercent(), pcts.getLevel(), "State");
			}
		}
		
		if(!data.getLevels().isEmpty()) {
			result.setFirstLevel(data.getLevels().get(0).getLevel());
			result.setLastLevel(data.getLevels().get(data.getLevels().size()-1).getLevel());
		}
		
		StudentLevelBarChartPlot plot = new StudentLevelBarChartPlot(result, data.getAssessmentProgramCode());
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		chart.setBackgroundPaint(new Color(0,0,0,0));
		SVGGraphics2D g2 = new SVGGraphics2D(reportData.getPercentLevelChartWidth(), reportData.getPercentLevelChartHeight());
        Rectangle r = new Rectangle(0, 0, reportData.getPercentLevelChartWidth(), reportData.getPercentLevelChartHeight());
        chart.draw(g2, r);
        String fullPath = FileUtil.buildFilePath(outDir, "percentlevel_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
        String[] splitFullPath = fullPath.split("\\.");
        File f = File.createTempFile(splitFullPath[0], ".svg");
        SVGUtils.writeToSVG(f, g2.getSVGElement());
        
		return f;
	}

	private void populateSchoolLevels(LevelCategoryDataset result, StudentReport data, ReportContext reportData) {
		int numberOfStudents=0;
		for(ReportsMedianScore median: data.getMedianScores()) {
			if(data.getAttendanceSchoolId().equals(median.getOrganizationId())
					&& median.getStudentCount() != null) {
				numberOfStudents = median.getStudentCount().intValue();
			}
		}
		List<ReportsPercentByLevel> schoolPctLevels = getPercentLevels(data, "school");
		
		int actualLevelCount = 0;
		boolean lessThanTwo = false;
		boolean suppressLevels = false;
		for(ReportsPercentByLevel pctLevel: schoolPctLevels) {
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
		
		for(int i=0; i<schoolPctLevels.size(); i++) {
			ReportsPercentByLevel pcts=schoolPctLevels.get(i);
			if(suppressLevels) {
				if(i<2) {
					result.addValue(-1*25, pcts.getLevel(), "School **");
				} else {
					result.addValue(25, pcts.getLevel(), "School **");
				}
				result.setSupressLabelValue(getSupressLabelValue(pcts, schoolPctLevels, numberOfStudents), pcts.getLevel(), "School **");
				reportData.setSuppressLevels(1);
			} else {
				if(i<2) {
					result.addValue(-1*pcts.getPercent(), pcts.getLevel(), "School");
				} else {
					result.addValue(pcts.getPercent(), pcts.getLevel(), "School");
				}
			}
		}
	}
	
	private void populateDistrictLevels(LevelCategoryDataset result, StudentReport data, ReportContext reportData) {
		int numberOfStudents=0;
		for(ReportsMedianScore median: data.getMedianScores()) {
			if(data.getDistrictId().equals(median.getOrganizationId())
					&& median.getStudentCount() != null) {
				numberOfStudents = median.getStudentCount().intValue();
			}
		}
		List<ReportsPercentByLevel> districtPctLevels = getPercentLevels(data, "district");
		
		int actualLevelCount = 0;
		boolean lessThanTwo = false;
		boolean suppressLevels = false;
		for(ReportsPercentByLevel pctLevel: districtPctLevels) {
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
		
		for(int i=0; i<districtPctLevels.size(); i++) {
			ReportsPercentByLevel pcts=districtPctLevels.get(i);
			if(suppressLevels) {
				if(i<2) {
					result.addValue(-1*25, pcts.getLevel(), "District **");
				} else {
					result.addValue(25, pcts.getLevel(), "District **");
				}
				result.setSupressLabelValue(getSupressLabelValue(pcts, districtPctLevels, numberOfStudents), pcts.getLevel(), "District **");
				reportData.setSuppressLevels(1);
			} else {
				if(i<2) {
					result.addValue(-1*pcts.getPercent(), pcts.getLevel(), "District");
				} else {
					result.addValue(pcts.getPercent(), pcts.getLevel(), "District");
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
	
	private List<ReportsPercentByLevel> getPercentLevels(final StudentReport data, final String type) {
		List<ReportsPercentByLevel> pctLevels = new ArrayList<ReportsPercentByLevel>();
		if(data.getPercentByLevels() != null){
			pctLevels.addAll(data.getPercentByLevels());
			CollectionUtils.filter(pctLevels, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					ReportsPercentByLevel pctLevel = (ReportsPercentByLevel) object;
					if(type.equals("school") && data.getAttendanceSchoolId() != null && data.getAttendanceSchoolId().equals(pctLevel.getOrganizationId())) {
						return true;
					} else if(type.equals("district") && data.getDistrictId().equals(pctLevel.getOrganizationId())) {
						return true;
					} else if(type.equals("state") && data.getStateId().equals(pctLevel.getOrganizationId())) {
						return true;
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
		if(pctLevels.size() >= 2){
			Collections.swap(pctLevels, 0, 1);
		}
		return pctLevels;
	}
	
	private class ReportsPercentByLevelComparator implements Comparator<ReportsPercentByLevel> {
		@Override
		public int compare(ReportsPercentByLevel o1, ReportsPercentByLevel o2) {
			return o1.getLevel().compareTo(o2.getLevel());
		}
	}
}