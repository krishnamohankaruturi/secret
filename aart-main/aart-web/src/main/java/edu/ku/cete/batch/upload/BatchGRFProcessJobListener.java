package edu.ku.cete.batch.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.amazonaws.services.s3.model.S3Object;

import au.com.bytecode.opencsv.CSVReader;
import edu.ku.cete.ExcelStreamReader.StreamingReader;
import edu.ku.cete.batch.support.InvalidRecordException;
import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.UploadFileService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.OrganizationGrfCalculationService;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.GrfColumnNamesEnum;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.TimerUtil;

public class BatchGRFProcessJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchGRFProcessJobListener.class);
	
    private Instant startTime;
    
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private UploadGrfFileService uploadGrfFileService;
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private ContentAreaService contAreaService;
	
	@Autowired
	private GradeCourseService gradeService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private UploadGrfFileWriterProcessService uploadGrfFileWriterProcessService;
	
	@Autowired
    private OrganizationGrfCalculationService orgGrfCalculationService;
	
	@Autowired
    private EmailService emailService;
	
	@Value("${csvRecordTypeCode}")
	private String	csvRecordTypeCode;
	
	@Value("${GRF.original.upload}")
	private String OriginalGrfUpload;
	
	@Value("${GRF.update.upload}")
	private String UpdatedGrfUpload;
	
	@Value("${uploadArkansasGrfFileType}")
	private String uploadArkansasGrfFileType;
	
	@Autowired
	private AwsS3Service s3;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeGRFProcess");
		startTime = new Instant();
		
		JobParameters params = jobExecution.getJobParameters();
		BatchUpload ubRecord = new BatchUpload();
		ubRecord.setStatus("IN-PROGRESS");
		ubRecord.setSuccessCount(0);
		ubRecord.setFailedCount(0);
		ubRecord.setModifiedDate(new Date());
		ubRecord.setId(params.getLong("batchUploadId"));
		batchUploadService.updateByPrimaryKeySelectiveBatchUpload(ubRecord);
		ubRecord = batchUploadService.selectByPrimaryKeyBatchUpload(params.getLong("batchUploadId"));
 		//jobExecution.getExecutionContext().put("user", user);
		jobExecution.getExecutionContext().put("batchUploadRecord", ubRecord);
		jobExecution.getExecutionContext().put("batchUploadId", ubRecord.getId());
		jobExecution.getExecutionContext().put("batchUploadUserId", ubRecord.getCreatedUser());
		jobExecution.getExecutionContext().put("grfUploadType", ubRecord.getGrfProcessType());		
		jobExecution.getExecutionContext().put("jobMessages",new CopyOnWriteArrayList<BatchUploadReason>());
		jobExecution.getExecutionContext().put("failedCount", 0);
		File convertedCsvFile = null;
		 
	     List<String> headerNames = new ArrayList<String>();
	     TimerUtil timerUtil = TimerUtil.getInstance();
	     //Validate header
	     if(validateFileHeader(params.getString("uploadTypeCode"), ubRecord.getGrfProcessType(), ubRecord.getFilePath(), headerNames)){
	    	 timerUtil.resetAndLogInfo(logger, "### Header Validation Duration : ");
	    	//Get the file and Import into temp table
		     try {
				getAndImoprtInTempTable(jobExecution, ubRecord.getFilePath(), ubRecord.getGrfProcessType(), params.getString("uploadTypeCode"), params.getLong("batchUploadId"), ubRecord.getCreatedUser(), params.getLong("assessmentProgramId"), headerNames);
				timerUtil.resetAndLogInfo(logger, "### Insertion in Temp Table Duration : ");

			} catch (IOException e) {
				e.printStackTrace();
				jobExecution.getExecutionContext().put("FailedCount", 1);
				   BatchUploadReason reason = new BatchUploadReason();
				   reason.setBatchUploadId(params.getLong("batchUploadId"));
				   reason.setErrorType("reject");
				   reason.setFieldName("");
				   reason.setReason("File Import Failed.");
				   ((CopyOnWriteArrayList<BatchUploadReason>)jobExecution.getExecutionContext().get("jobMessages")).add(reason);
				   throw new InvalidRecordException("File import Failed for GRF File."); 
			}
		     
			//Import the file into temporary table	
			//uploadGrfFileService.insertGrfList(convertedCsvFile.getPath(), params.getString("uploadTypeCode"), headerNames);
					
		}else{
			   jobExecution.getExecutionContext().put("FailedCount", 1);
			   BatchUploadReason reason = new BatchUploadReason();
			   reason.setBatchUploadId(params.getLong("batchUploadId"));
			   reason.setErrorType("reject");
			   reason.setFieldName("");
			   reason.setReason("Heading validation failed, please upload a correct file for selected options.");
			   ((CopyOnWriteArrayList<BatchUploadReason>)jobExecution.getExecutionContext().get("jobMessages")).add(reason);
			   throw new InvalidRecordException("Heading Validation Failed for GRF File."); 
		}

		jobExecution.getExecutionContext().put("convertedCsvFilePath", convertedCsvFile);
		
		//Run common the validations on temporary table		
		timerUtil.start();
		List<BatchUploadReason> resons = uploadGrfFileService.validateUploadFile(params.getLong("stateId"), ubRecord.getId(), ubRecord.getGrfProcessType(), params.getLong("assessmentProgramId"), params.getLong("reportYear"), ubRecord.getCreatedUser(),true);  		
		timerUtil.resetAndLogInfo(logger, "### Common Validation and Insertion in GRF Original Upload Table Duration : ");
		//If validation completes then process else reject
		if(resons.size() > 0){
			jobExecution.getExecutionContext().put("failedCount", resons.size());
			((CopyOnWriteArrayList<BatchUploadReason>)jobExecution.getExecutionContext().get("jobMessages")).addAll(resons);
			throw new InvalidRecordException("Validation Failed for GRF File.");
		}
		
		String grfUploadType = ubRecord.getGrfProcessType();     
	     if(OriginalGrfUpload.equalsIgnoreCase(grfUploadType)){
	    	 
	      //Need to hard delete from grf table instead of soft delete for the uploaded state and year
	      uploadGrfFileWriterProcessService.clearRecordsOnOriginalGRFUpload(params.getLong("stateId"), params.getLong("reportYear"), ubRecord.getAssessmentProgramId());
	      
	      //Need to delete calculated GRF data for State and District summary Reports
	      orgGrfCalculationService.deleteOrganizationGrfCalculation(params.getLong("stateId"), params.getLong("reportYear"),ubRecord.getAssessmentProgramId());
	      
	      
	      //Need to delete All DLM organization reports for the uploaded state and year
	      batchReportProcessService.deleteAllOrganizationReportsOnGRFUpload(params.getLong("stateId"), params.getLong("reportYear"), ubRecord.getAssessmentProgramId());
	      
	     //Need to delete All DLM Student reports for the uploaded state and year
	      batchReportProcessService.deleteAllStudentReportsOnGRFUpload(params.getLong("stateId"), params.getLong("reportYear"), ubRecord.getAssessmentProgramId());
	     }
		
		jobExecution.getExecutionContext().put("gradeChangeCount", 0);
		if(UpdatedGrfUpload.equalsIgnoreCase(grfUploadType)){
			//Set all the performance to 9 if grade change happened
			Integer gradeChangeCount = uploadGrfFileService.resetEEValuesOnGradeCahange(params.getLong("stateId"), ubRecord.getId(), params.getLong("reportYear"));
			
			jobExecution.getExecutionContext().put("gradeChangeCount", gradeChangeCount);
					
			//Run updated the validations on temporary table		
			/* timerUtil.start();
			 List<BatchUploadReason> updateresons = uploadGrfFileService.validateUploadFile(params.getLong("stateId"), ubRecord.getId(), ubRecord.getGrfProcessType(), params.getLong("assessmentProgramId"), params.getLong("reportYear"), ubRecord.getCreatedUser(), false);
			 timerUtil.resetAndLogInfo(logger, "### Updated Validation and Insertion in GRF Updated Upload Table End Duration  : ");
			if(updateresons.size() > 0){
				jobExecution.getExecutionContext().put("failedCount", updateresons.size());
				((CopyOnWriteArrayList<BatchUploadReason>)jobExecution.getExecutionContext().get("jobMessages")).addAll(updateresons);
				throw new InvalidRecordException("Validation Failed for GRF File.");
			}*/
		}
		//Get all the necessary info subjectids, gradeids, organizationids
		Map<String, Long> subjectMap = new HashMap<String, Long>();
		Map<String, Long> gradeMap = new HashMap<String, Long>();
		Map<String, Long> organizationMap = new HashMap<String, Long>();
		
		List<ContentArea> subjects = contAreaService.findByAssessmentProgram(params.getLong("assessmentProgramId"));
		
		for (ContentArea contentArea : subjects) {
			subjectMap.put(contentArea.getAbbreviatedName().toUpperCase(), contentArea.getId());
			
			List<GradeCourse> grades = gradeService.selectGradeByContentAreaId(contentArea.getId());
			
			for (GradeCourse gradeCourse : grades) {
				gradeMap.put(contentArea.getAbbreviatedName().toUpperCase()+"-"+gradeCourse.getAbbreviatedName().toUpperCase(), gradeCourse.getId());
			}
		}
		
		List<Organization> orgs = organizationService.getAllActiveAndInactiveChildrenByOrgTypeCode(params.getLong("stateId"), CommonConstants.ORGANIZATION_DISTRICT_CODE);
		orgs.addAll(organizationService.getAllActiveAndInactiveChildrenByOrgTypeCode(params.getLong("stateId"), CommonConstants.ORGANIZATION_SCHOOL_CODE));
		
		for (Organization organization : orgs) {
			organizationMap.put(organization.getDisplayIdentifier(), organization.getId());
		}
		
		Organization state = organizationService.get(params.getLong("stateId"));
		
		jobExecution.getExecutionContext().put("subjectMap", subjectMap);
		jobExecution.getExecutionContext().put("gradeMap", gradeMap);
		jobExecution.getExecutionContext().put("organizationMap", organizationMap);
		jobExecution.getExecutionContext().put("stateCode", state.getDisplayIdentifier());
		jobExecution.getExecutionContext().put("stateName", state.getOrganizationName());
		
		logger.debug("<-- beforeGRFProcess");
	}
	
	public void getAndImoprtInTempTable(JobExecution jobExecution, String filePath, String grfUploadType, String uploadGrfType, Long batchUploadId, Long createdUser, Long assessmentProgramId, List<String> columnName) throws IOException {
		S3Object object = s3.getObject(filePath);
		String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
				
		if (StringUtils.equalsIgnoreCase(fileExtension, CommonConstants.FILE_XLSX_EXTENSION)) {				
			//Read and import from xsl file
			readExcelFile(object.getObjectContent(), batchUploadId, columnName);
		
		} else if (StringUtils.equalsIgnoreCase(fileExtension,	CommonConstants.FILE_CSV_EXTENSION)) {
			//Read and import from csv file
			
			if(UpdatedGrfUpload.equalsIgnoreCase(grfUploadType) && StringUtils.equalsIgnoreCase(uploadGrfType,	uploadArkansasGrfFileType)) {
				readCSVArkansasGrfFile(object.getObjectContent(), batchUploadId, columnName);
			}else {
			readCSVFile(object.getObjectContent(), batchUploadId, columnName);
			}
		}
	}
	
	public void readCSVArkansasGrfFile(InputStream inputStream, Long batchUploadId, List<String> columnName) throws IOException {
		int index = 0;
		CsvListReader csvReader = null;
		try {					
			csvReader = new CsvListReader(new InputStreamReader(inputStream),
					new CsvPreference.Builder('"', '|', "\r\n").build());
			
			List<String> row = new ArrayList<String>();
			List<String> columns;
			
			while ((columns = csvReader.read()) != null) {
				if(index != 0){
					columns.add(0, "" + index);
					columns.add(1, "" + batchUploadId);
					row.add(("'"+StringUtils.join(columns, ",")
							                .replaceAll("'", "''")
							                .replaceAll(",", "','")
							                +"'").replaceAll(",''", ",null").replaceAll("' '", "null"));
			}
						index = index + 1;
				if(row.size() == 2000){
					uploadGrfFileService.insertToTempTable(row, columnName);
					row.clear();
				}
			}
		
			if(row.size() > 0){
				uploadGrfFileService.insertToTempTable(row, columnName);
				row.clear();
		   }
			csvReader.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			csvReader.close();
		}		
	}
	
	public void readCSVFile(InputStream inputStream, Long batchUploadId, List<String> columnName) throws IOException {
		int index = 0;
		CsvListReader csvReader = null;
		try {					
			csvReader = new CsvListReader(new InputStreamReader(inputStream),
					CsvPreference.STANDARD_PREFERENCE);
			
			List<String> row = new ArrayList<String>();
			List<String> columns;
			while ((columns = csvReader.read()) != null) {
				if(index != 0){
					columns.add(0, "" + index);
					columns.add(1, "" + batchUploadId);
					row.add(("'"+StringUtils.join(columns, ",")
							                .replaceAll("'", "''")
							                .replaceAll(",", "','")
							                +"'").replaceAll(",''", ",null"));
				}
				index = index + 1;
				if(row.size() == 2000){
					uploadGrfFileService.insertToTempTable(row, columnName);
					row.clear();
				}
			}
			if(row.size() > 0){
				uploadGrfFileService.insertToTempTable(row, columnName);
				row.clear();
		   }
			csvReader.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			csvReader.close();
		}		
	}
	
	
	public void readExcelFile(InputStream inputStream, Long batchUploadId, List<String> columnName) throws IOException {
		int rowIndex = 0;
		StringBuffer data = new StringBuffer();
		List<String> columnValues = new ArrayList<String>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		StreamingReader reader = StreamingReader.builder()
		        .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
		        .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
		        .sheetIndex(0)        // index of sheet to use (defaults to 0)    	      
		        .read(inputStream);            // InputStream or File for XLSX file (required)
		
		for (Row row : reader) {		
			  if (rowIndex != 0) {					
				  data.append(rowIndex + CommonConstants.SEPARATOR + batchUploadId+ CommonConstants.SEPARATOR);
					
				  int colIndex= 0;    	    	  
				  for (int i = 0; i < columnName.size()-2; i++) {
					  
					  Cell cellValue = row.getCell(i);
					  if (cellValue == null){
						  if(colIndex==(columnName.size()-3)) data.append("null");
						  else data.append("null" + CommonConstants.SEPARATOR);        		
					  }
					  else if(CommonConstants.EMPTY.equals(cellValue.getStringCellValue().trim())){
						  if(colIndex==(columnName.size()-3)) data.append("null");
						  else data.append("null" + CommonConstants.SEPARATOR);        	
					  }
					  else{						  
						  String cellVal = cellValue.getStringCellValue().trim();						  
						  if(cellVal.contains("'")) cellVal = cellVal.replaceAll("'", "''");
				
						  if(cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cellValue)){
							  Date date = cellValue.getDateCellValue();
							  cellVal = String.valueOf(sdf.format(date.getTime()));
							  
						  }
						
						  cellVal = "'" + cellVal + "'";
						  cellVal = cellVal.replaceAll("\"", "");
						  
						  
			    		  if(colIndex==(columnName.size()-3)){			    			  
			    			  data.append(cellVal.trim());
			    		  }else{
							  data.append(cellVal.trim() + CommonConstants.SEPARATOR);        			  
						  }			    		  
					  }					  
				 colIndex++; 		  
			    }			
			  columnValues.add(data.toString());
			  data = new StringBuffer();
	
			  if(columnValues.size() == 1000){
					uploadGrfFileService.insertToTempTable(columnValues, columnName);
					columnValues.clear();
			  }
			}
			  rowIndex++;  
		}
		
		if(columnValues.size() > 0){
			uploadGrfFileService.insertToTempTable(columnValues, columnName);
		}
		inputStream.close();
		reader.close();
	}

	private boolean validateFileHeader(String csvOrXmlfileType, String grfUploadType, String filePath, List<String> columnNames){
		Map<String, FieldSpecification> feildSpecMap = new HashMap<String, FieldSpecification>();
		feildSpecMap = uploadFileService.getFieldSpecificationRecordMap(csvOrXmlfileType, csvRecordTypeCode);
		List<String> headNames = new ArrayList<String>();
		List<String> lowerCase = new ArrayList<String>();
		S3Object object = s3.getObject(filePath);
		
		try {
			String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
			
			if (StringUtils.equalsIgnoreCase(fileExtension, CommonConstants.FILE_XLSX_EXTENSION)) {				
				headNames = getExcelFileHeaders(object.getObjectContent());
			
			} else if (StringUtils.equalsIgnoreCase(fileExtension,	CommonConstants.FILE_CSV_EXTENSION)) {
				if(UpdatedGrfUpload.equalsIgnoreCase(grfUploadType) && StringUtils.equalsIgnoreCase(csvOrXmlfileType,	uploadArkansasGrfFileType)) {
					headNames = getCSVArKansasFileHeaders(object.getObjectContent());
			}else {
				headNames = getCSVFileHeaders(object.getObjectContent());
				}
			}			
			
			if(headNames != null){
				
				columnNames.add(CommonConstants.LINE_NUMBER);
				columnNames.add(CommonConstants.BATCH_UPLOAD_ID);
				
				if(headNames.size() != feildSpecMap.keySet().size()){
					return false;
				}
				
				for (String headerName : headNames) {
					headerName = headerName.trim();
					String header = headerName.replaceAll("\"","");
					if(!StringUtils.equals(CommonConstants.LINE_NUMBER, header) && !StringUtils.equals(CommonConstants.BATCH_UPLOAD_ID, header)){
						FieldSpecification fieldName = feildSpecMap.get(header.toLowerCase());
						if(fieldName == null){
							return false;
						}else{
							lowerCase.add(header.toLowerCase());
							columnNames.add(GrfColumnNamesEnum.getByName(header).getValue().toString().toLowerCase());
						}
					}
				}				
								
				for (String fieldName : feildSpecMap.keySet()) {
					if(!lowerCase.contains(fieldName.trim().toLowerCase())){
						return false;
					}
				}
				
			}
			return true;
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		}		
	}
	
	private List<String> getExcelFileHeaders(InputStream inputStream) throws IOException{
		StreamingReader reader = StreamingReader.builder()
		        .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
		        .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
		        .sheetIndex(0)        // index of sheet to use (defaults to 0)    	      
		        .read(inputStream);
		
		List<String> header = new ArrayList<String>();
		int totalCellColumns = 0;
		
		for (Row row : reader) {
		
			for (Cell cell : row) {   
				totalCellColumns++;
			}
								  
			for (int i = 0; i < totalCellColumns ; i++) {
				  
				  Cell cellValue = row.getCell(i);
				  if (cellValue == null){
					 header.add("");        		
				  }
				  else if(CommonConstants.EMPTY.equals(cellValue.getStringCellValue().trim())){
					 header.add("");        	
				  }
				  else{				  
					  String cellVal = cellValue.getStringCellValue();	                  
					  header.add(cellVal.trim());				      		  
				  } 		  
			  }
			  break;//Only first row required
		}
		inputStream.close();
		reader.close();
		return header;
	}
	
	
	private List<String> getCSVArKansasFileHeaders(InputStream inputStream) throws IOException{
		BufferedReader csvReader = null;
		
		try {
			csvReader = new BufferedReader(new InputStreamReader(inputStream));
		 String line;
		 String[] nextLine;
		 int lineNumber = 0;
		             
	     while ((line = csvReader.readLine()) != null) {
	    	 lineNumber++;
	    	 if(lineNumber == 1) {
	    		 nextLine = line.split("\\|");
	    		 csvReader.close();
	    		 return Arrays.asList(nextLine);   		 
	    	 }
	      }
	     
		} catch (Exception e) {			
			csvReader.close();
		}
		return null;
	}
	
	private List<String> getCSVFileHeaders(InputStream inputStream) throws IOException{
		CSVReader csvReader = null;
		
		try {
		 csvReader = new CSVReader(new BufferedReader(new InputStreamReader(inputStream)));
		 String [] nextLine;
		 int lineNumber = 0;
	     while ((nextLine = csvReader.readNext()) != null) {
	    	 lineNumber++;
	    	 if(lineNumber == 1){
	    		 csvReader.close();
	    		 return Arrays.asList(nextLine);
	    	 }
	    	 
	      }
	     
		} catch (Exception e) {			
			csvReader.close();
		}
		return null;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());
		int successCount = 0, failedCount = 0;
		BatchUpload buRecord = (BatchUpload) jobExecution.getExecutionContext().get("batchUploadRecord");
        List<BatchUploadReason> jobMessages = (List<BatchUploadReason>) jobExecution.getExecutionContext().get("jobMessages");
        Integer gradeChangeCount = (Integer) jobExecution.getExecutionContext().get("gradeChangeCount");
        JobParameters params = jobExecution.getJobParameters();
        String grfUploadType = (String)jobExecution.getExecutionContext().get("grfUploadType");
        Integer rejectedCount = (Integer)jobExecution.getExecutionContext().get("failedCount");
		List<BatchUploadReason> batchuploadMsgList = new ArrayList<BatchUploadReason>();
		
		try{			
			uploadGrfFileService.deleteTempGrfFileByBatchUploadId((long)jobExecution.getExecutionContext().get("batchUploadId"));
					
			if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
				for (BatchUploadReason batchUploadReason : jobMessages) {
					String[] msgs = batchUploadReason.getReason().split("##");				
					for (String msg : msgs) {
						if(!msg.isEmpty()){
							BatchUploadReason reason = new BatchUploadReason();
							reason.setLine(batchUploadReason.getLine());
							reason.setBatchUploadId(batchUploadReason.getBatchUploadId());
							reason.setErrorType(batchUploadReason.getLine());
							
							if(batchUploadReason.getFieldName().isEmpty()){
								String[] column  = msg.split("~");
								reason.setReason(column.length>1?column[1]:msg);
								reason.setFieldName(column.length>0?column[0]:"");
							}else{
								reason.setReason(batchUploadReason.getReason());
								reason.setFieldName(batchUploadReason.getFieldName());
							}
							batchuploadMsgList.add(reason);
							
							if(batchuploadMsgList.size() == 1000){
								batchUploadService.insertBatchUploadReasons(batchuploadMsgList);
								batchuploadMsgList.clear();
							}
							
						}
					}				
				}
				if(batchuploadMsgList.size() > 0){
					batchUploadService.insertBatchUploadReasons(batchuploadMsgList);
				}
				
			  //DELETE already inserted record because batch got failed
			  uploadGrfFileWriterProcessService.deleteFailedBatchGrfFileRecords(params.getLong("batchUploadId"));
	
			}else{//success
				if(gradeChangeCount > 0){
					//Send email to ATS management
					 emailService.sendEmailForGRFGradeChange((String)jobExecution.getExecutionContext().get("stateName"));
				 }		 
				 
				//Added for F671
				 
				 //Insert entry in GRF audit table
				 GrfStateApproveAudit audit = new GrfStateApproveAudit();
				 audit.setActiveFlag(true);
				 audit.setStateId(params.getLong("stateId"));
				 audit.setSchoolYear(params.getLong("reportYear"));
				 audit.setCreatedDate(new Date());
				 audit.setCreatedUser(buRecord.getCreatedUser());
				 audit.setOperation(grfUploadType);
				 audit.setModifiedUser(buRecord.getCreatedUser());
				 audit.setModifiedDate(new Date());
				 audit.setUpdatedUserId(params.getLong("uploadedUserId"));
				 audit.setSource(SourceTypeEnum.UPLOAD.getCode());
					 
				 uploadGrfFileWriterProcessService.setGRFAuditInfo(audit);

		}
		}catch(Exception e){
			//DELETE already inserted record because batch got failed
			  uploadGrfFileWriterProcessService.deleteFailedBatchGrfFileRecords(params.getLong("batchUploadId"));
			  if(buRecord != null) {					
					buRecord.setSuccessCount(0);
					buRecord.setFailedCount(0);		
					buRecord.setStatus("FAILED");
					buRecord.setModifiedDate(new Date());
					batchUploadService.updateByPrimaryKeySelectiveBatchUpload(buRecord);
				}  
			logger.error("GRF Process listener failed due to "+e.getMessage());
		}
		
		if(buRecord != null) {
 			Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
			for(StepExecution sExecution: sExecutions) {
					successCount = sExecution.getWriteCount();
					failedCount = sExecution.getProcessSkipCount();
					break;
			}
			
			buRecord.setSuccessCount(successCount);
			buRecord.setFailedCount(rejectedCount);		
			buRecord.setStatus(jobExecution.getStatus().name());
			buRecord.setModifiedDate(new Date());
			batchUploadService.updateByPrimaryKeySelectiveBatchUpload(buRecord);
		}
		
		jobExecution.getExecutionContext().remove("jobMessages");
		logger.debug("***** Batch Upload successCount: "+successCount);
		logger.debug("***** Batch Upload failed/skippedCount: "+failedCount);
		logger.debug("Batch Upload Finish job: "+jobExecution.getExecutionContext().get("batchUploadId") +", duration:" + duration);
		logger.debug("Batch Upload Finish job: "+jobExecution.getExecutionContext().get("batchUploadId") +", duration after reasons write: " 
					+ getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
	
}
