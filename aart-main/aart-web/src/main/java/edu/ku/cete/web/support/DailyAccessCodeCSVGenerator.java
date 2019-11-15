package edu.ku.cete.web.support;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.util.CommonConstants;
@Component
public class DailyAccessCodeCSVGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DailyAccessCodeCSVGenerator.class);

	public void generateCSV(List<DailyAccessCode> accessCodes, OutputStream out) throws Exception {
		Assert.notNull(accessCodes, "Access codes list was null.");
		Assert.notEmpty(accessCodes, "Access codes list was empty.");
		Assert.notNull(accessCodes.get(0), "Access codes had null first element.");
		
		Map<Integer, List<DailyAccessCode>> csvRows = convertDacData(accessCodes);

		String assessmentProgramCode = accessCodes.get(0).getAssessmentProgramCode();
		
		List<String> columnHeadersList = new ArrayList<String>();
		
		if(StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
			columnHeadersList.add("Course");
        }else
        {
        	columnHeadersList.add("Subject");
        }
        if(StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
        columnHeadersList.add("Grade Band");
        }else
        {
        	columnHeadersList.add("Grade");
        }
        List<String[]> lines = new ArrayList<String[]>();
		if (csvRows != null && csvRows.size() > 0) {
			int rowNum = 0;
			List<DailyAccessCode> accesscodes = null;
			List<String> stageNames = new ArrayList<String>();
			String lastColumn = null;
			//
			while(rowNum < csvRows.size()) {
				accesscodes = csvRows.get(rowNum);
				
				for (DailyAccessCode accessCode : accesscodes) {
					if (accessCode.getStageCode().equals("Prfrm")) {
						lastColumn = accessCode.getStageName();
					} else if(columnHeadersList.indexOf(accessCode.getStageName()+" Code") == -1) {
						columnHeadersList.add(accessCode.getStageName()+" Code");
						stageNames.add(accessCode.getStageName());
					}
				}
				rowNum++;
			}
			if(lastColumn != null) {
				columnHeadersList.add(lastColumn+" Code");
				stageNames.add(lastColumn);
			}
			lines.add(columnHeadersList.toArray(new String[columnHeadersList.size()]));
			
			List<String> row = new ArrayList<String>();
			rowNum = 0;
			while(rowNum < csvRows.size()) {
				row.clear();
				boolean codeExits = false;
				accesscodes = csvRows.get(rowNum);
				for (String stageName: stageNames) {
					codeExits = false;
					for (DailyAccessCode accessCode : accesscodes) {
						if(row.size() == 0) {
							row.add(wrapForCSV(accessCode.getContentAreaName()));
							if(StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
							row.add(wrapForCSV("Grade Band "+accessCode.getGradeBandCode()));
							}else {
							row.add(wrapForCSV("Grade "+accessCode.getGradeCode()));	
							}
						}
						if(accessCode.getStageName().equalsIgnoreCase(stageName)) {
							row.add(wrapForCSV(accessCode.getAccessCode()));
							codeExits = true;
							break;
						}
					}

					if(!codeExits){
						row.add("");
					}
				}
				lines.add(row.toArray(new String[row.size()]));
				rowNum++;
			}
		} else {
			lines.add(columnHeadersList.toArray(new String[columnHeadersList.size()]));
		
			lines.add(new String[]{"No data"});
		}
		
		
		OutputStream stream = new BufferedOutputStream(out);
		OutputStreamWriter outputWriter = new OutputStreamWriter(stream,"UTF-8");
		CSVWriter writer = new CSVWriter(outputWriter);
		
		for (String[] line: lines) {
		    writer.writeNext(line);
		}
		
		writer.flush();
		writer.close();
	}
	
	public Map<Integer, List<DailyAccessCode>> convertDacData(List<DailyAccessCode> accessCodes) {
		LOGGER.debug("--> convertDacData");
		Assert.notNull(accessCodes, "Access codes list was null.");
		Assert.notNull(accessCodes.get(0), "Access codes list was empty. Preventing null exception.");
		
		String assessmentProgramCode = accessCodes.get(0).getAssessmentProgramCode();
		for (DailyAccessCode accessCode : accessCodes) {
			if (accessCode.getStageCode().equals("Prfrm")) {
				accessCode.setPartNumber(accessCodes.size());
			}
		}
		Collections.sort(accessCodes, new Comparator<DailyAccessCode>() {
			@Override
			public int compare(DailyAccessCode o1, DailyAccessCode o2) {
				int c;
				c = o1.getOperationalTestwindowId().compareTo(o2.getOperationalTestwindowId());
				if (c == 0) {
					c = o1.getContentAreaId().compareTo(o2.getContentAreaId());
				}
				if (c == 0) {
					if(StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
					c = o1.getGradeBandId().compareTo(o2.getGradeBandId());
					}else {
					c = o1.getGradeCourseId().compareTo(o2.getGradeCourseId());
					}
				}
				if (c == 0) {
					c = o1.getPartNumber().compareTo(o2.getPartNumber());
				}
				return c;
			}
		});

		Map<Integer, List<DailyAccessCode>> csvRows = new HashMap<Integer, List<DailyAccessCode>>();

		Integer pageNumber = 0;
		DailyAccessCode prevAccessCode = null;
		if(StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
			for (DailyAccessCode accessCode : accessCodes) {
				if (prevAccessCode == null || (prevAccessCode != null 
							&& prevAccessCode.getContentAreaId().equals(accessCode.getContentAreaId()) 
							&& prevAccessCode .getGradeBandId().equals(accessCode.getGradeBandId()))) {
					if (prevAccessCode == null) {
						csvRows.put(pageNumber, new ArrayList<DailyAccessCode>());
					}
					csvRows.get(pageNumber).add(accessCode);
				} else {
					csvRows.put(++pageNumber, new ArrayList<DailyAccessCode>());
					csvRows.get(pageNumber).add(accessCode);
				}
				prevAccessCode = accessCode;
			}
		} else {
			for (DailyAccessCode accessCode : accessCodes) {
				if (prevAccessCode == null || (prevAccessCode != null 
							&& prevAccessCode.getContentAreaId().equals(accessCode.getContentAreaId()) 
							&& prevAccessCode .getGradeCourseId().equals(accessCode.getGradeCourseId()))) {
					if (prevAccessCode == null) {
						csvRows.put(pageNumber, new ArrayList<DailyAccessCode>());
					}
					csvRows.get(pageNumber).add(accessCode);
				} else {
					csvRows.put(++pageNumber, new ArrayList<DailyAccessCode>());
					csvRows.get(pageNumber).add(accessCode);
				}
				prevAccessCode = accessCode;
			}
		}

		LOGGER.debug("number of rows: "+csvRows.size());
		LOGGER.debug("<-- convertDacData");
		
		return csvRows;
	}
	
	private String wrapForCSV(String s) {
		if (s != null && !s.isEmpty() && s.indexOf(',') > -1) {
			return "\"" + s + "\"";
		}
		return s;
	}
}
