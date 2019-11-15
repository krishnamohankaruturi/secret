package edu.ku.cete.service.impl.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.model.ExternalstudentreportsMapper;
import edu.ku.cete.model.OrganizationReportDetailsMapper;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.ExternalReportImportService;
import edu.ku.cete.web.OrganizationReportsImportDTO;
import edu.ku.cete.web.ReportsImportDTO;
import edu.ku.cete.web.StudentReportsImportDTO;
import net.schmizz.sshj.sftp.SFTPClient;

@Service
public class ExternalReportImportServiceImpl implements ExternalReportImportService {
	
	final static Log logger = LogFactory.getLog(ExternalReportImportServiceImpl.class);
	
	@Autowired
	private OrganizationReportDetailsMapper organizationReportDetailsMapper;
	
	@Autowired
	private ExternalstudentreportsMapper externalStudentReportsMapper;
	
	@Autowired
	private AssessmentProgramService apService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private AwsS3Service s3;
	
	@Value("${schoolReportsImportReportType}")
	private String schoolReportsImportReportType;
	
	@Value("${classroomReportsImportReportType}")
	private String classroomReportsImportReportType;
	
	@Value("${schoolCsvReportsImportReportType}")
	private String schoolCsvReportsImportReportType;
	
	@Value("${classroomCsvReportsImportReportType}")
	private String classroomCsvReportsImportReportType;
	
	@Value("${studentReportsImportReportType}")
	private String studentReportsImportReportType;
	
    @Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;
    
    @Value("${cpass.student.individual.report.type.code}")
	private String dbCPASSStudentReportsImportReportType;
    
	@Value("${studentSummaryReportsImportReportType}")
	private String studentSummaryReportsImportReportType;
	
	@Value("${report.filestore.directory}")
	private String reportFilestoreDirectory;
	
	@Value("${print.test.file.path}")
	private String topLevelDirectory;	
	
	@Value("${studentDCPSReportsImportReportType}")
	private String studentDCPSReportsImportReportType;
	
	 @Value("${alternate.student.dcps.report.type.code}")
		private String dbDLMStudentDcpsReportsImportReportType;
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteClassroomRecords(Long assessmentProgramId, Long schoolYear, Long stateId){
		return organizationReportDetailsMapper.deleteByStateSchoolYearAssessmentProgramReportType(assessmentProgramId, schoolYear, stateId, classroomReportsImportReportType);
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteClassroomFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException{
		//delete classroom and classroom_csv
		AssessmentProgram ap = apService.findByAssessmentProgramId(assessmentProgramId);
		Organization state = orgService.get(stateId);
		String apAbbrName = ap.getAbbreviatedname();
		String stateDisplayId = state.getDisplayIdentifier();
		
		//generate the key prefix - the parent 'directory' 
		Path pdfPath = generateFilePathForDelete(classroomReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(pdfPath.toString());
		
		//do the same for the csv path
		Path csvPath = generateFilePathForDelete(classroomCsvReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(csvPath.toString());
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteSchoolRecords(Long assessmentProgramId, Long schoolYear, Long stateId){
		return organizationReportDetailsMapper.deleteByStateSchoolYearAssessmentProgramReportType(assessmentProgramId, schoolYear, stateId, schoolReportsImportReportType);
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteSchoolFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException{
		//delete school and school_csv
		AssessmentProgram ap = apService.findByAssessmentProgramId(assessmentProgramId);
		Organization state = orgService.get(stateId);
		String apAbbrName = ap.getAbbreviatedname();
		String stateDisplayId = state.getDisplayIdentifier();
		
		//generate the key prefix - the parent 'directory' 
		Path pdfPath = generateFilePathForDelete(schoolReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(pdfPath.toString());
		
		//do the same for the csv path
		Path csvPath = generateFilePathForDelete(schoolCsvReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(csvPath.toString());
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteStudentRecords(Long assessmentProgramId, Long schoolYear, Long stateId, String assessmentProgramIdentifier){
		String reportsImportReportType = "";
		if (assessmentProgramIdentifier != null && assessmentProgramIdentifier.equalsIgnoreCase("DLM")){
			reportsImportReportType = dbDLMStudentReportsImportReportType;
		} else if (assessmentProgramIdentifier != null && assessmentProgramIdentifier.equalsIgnoreCase("CPASS")){
			reportsImportReportType = dbCPASSStudentReportsImportReportType;
		}
		return externalStudentReportsMapper.deleteByStateSchoolYearAssessmentProgramReportType(assessmentProgramId, schoolYear, stateId, reportsImportReportType);
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteStudentFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException {
		AssessmentProgram ap = apService.findByAssessmentProgramId(assessmentProgramId);
		Organization state = orgService.get(stateId);
		String apAbbrName = ap.getAbbreviatedname();
		String stateDisplayId = state.getDisplayIdentifier();
		
		//generate the key prefix - the parent 'directory'
		Path path = generateFilePathForDelete(studentReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(path.toString());		
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteStudentSummaryRecords(Long assessmentProgramId, Long schoolYear, Long stateId){
		return externalStudentReportsMapper.deleteByStateSchoolYearAssessmentProgramReportType(assessmentProgramId, schoolYear, stateId, studentSummaryReportsImportReportType);
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteStudentSummaryFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException{
		AssessmentProgram ap = apService.findByAssessmentProgramId(assessmentProgramId);
		Organization state = orgService.get(stateId);
		String apAbbrName = ap.getAbbreviatedname();
		String stateDisplayId = state.getDisplayIdentifier();
		//generate the key prefix - the parent 'directory'
		Path path = generateFilePathForDelete(studentSummaryReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(path.toString());
	}
	
	private Path generateFilePathForDelete(String reportType, String assessmentProgram, Long schoolYear, String state){
		StringBuilder filePath = new StringBuilder();
		filePath.append(topLevelDirectory);
		filePath.append(reportFilestoreDirectory);
		filePath.append(assessmentProgram);
		filePath.append("/");
		filePath.append(schoolYear);
		filePath.append("/");
		filePath.append(reportType);
		filePath.append("/");
		filePath.append(state);
		filePath.append("/");
		return Paths.get(filePath.toString());
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void writeOrganizationReportFiles(OrganizationReportsImportDTO dto, SFTPClient sftpClient) {
		try{
			moveFileFromSftpToS3(dto, sftpClient);
		}catch(IOException ioe){
			String msg = "Error in moveFileFromSftpToS3";
			logger.info(msg, ioe);
			reportError(dto, msg, ioe);
			return;
		}
			
		//check for existing if report type is a csv and update record
		if (dto.getReportType().equalsIgnoreCase("school_csv")) {
			List<OrganizationReportDetails> schoolReport = organizationReportDetailsMapper.selectSchoolReport(dto.getAssessmentProgramId(), dto.getSchoolYear(), dto.getStateId(), dto.getOrganizationId());
			if (!schoolReport.isEmpty()){
				OrganizationReportDetails pulledReport = schoolReport.get(0);
				organizationReportDetailsMapper.updateForCsv(dto.getCsvFilePath(), dto.getBatchReportProcessId(),dto.getModifiedUser(), new Date(), pulledReport.getId());
			} else {
				reportError(dto, "Could not update the record because there was not existing entry for this PDF report.", null);
			}
		} else if (dto.getReportType().equalsIgnoreCase("classroom_csv")) {
			List<OrganizationReportDetails> classroomReport = organizationReportDetailsMapper.selectClassroomReport(dto.getAssessmentProgramId(), dto.getSchoolYear(), dto.getStateId(), dto.getTeacherID(), dto.getOrganizationId(), dto.getContentAreaId(), dto.getGradeId());
			if (!classroomReport.isEmpty()){
				OrganizationReportDetails pulledReport = classroomReport.get(0);
				organizationReportDetailsMapper.updateForCsv(dto.getCsvFilePath(), dto.getBatchReportProcessId(),dto.getModifiedUser(), new Date(), pulledReport.getId());
			} else {
				reportError(dto, "Could not update the record because there was not existing entry for this PDF report.", null);
			}
		} else {
			//construct OrganizationReportDetails from dto and insert
			OrganizationReportDetails report = dto.toOrganizationReportDetails();
			organizationReportDetailsMapper.insertSelective(report);
		}
			
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void writeStudentReportFiles(StudentReportsImportDTO dto, SFTPClient sftpClient) {
		try{
			moveFileFromSftpToS3(dto, sftpClient);
		}catch(IOException ioe){
			String msg = "Error in moveFileFromSftpToS3";
			logger.info(msg, ioe);
			reportError(dto, msg, ioe);
			return;
		}
		if (dto.getReportType().equalsIgnoreCase(studentReportsImportReportType)){
			dto.setReportTypeStudent(studentReportsImportReportType);
			if (dto.getAssessmentProgram().equalsIgnoreCase("DLM")){
				dto.setDbReportTypeStudent(dbDLMStudentReportsImportReportType);
			}else if (dto.getAssessmentProgram().equalsIgnoreCase("CPASS")){
				dto.setDbReportTypeStudent(dbCPASSStudentReportsImportReportType);
			}
		}
		if (dto.getReportType().equalsIgnoreCase(studentDCPSReportsImportReportType)){
			dto.setReportTypeStudent(studentDCPSReportsImportReportType);
			dto.setDbReportTypeStudent(dbDLMStudentDcpsReportsImportReportType);
			
		}
		//construct Externalstudentreports from dto and insert
		Externalstudentreports report = dto.toExternalStudentReports();
		externalStudentReportsMapper.insertSelective(report);
		
	}
	
	/**
	 * Creates a file in the temp directory, moves from SFTP server
	 * to the local server, sends the file to S3 and deletes the local file.
	 * 
	 * @param dto
	 * @param sftpClient
	 * @throws IOException
	 */
	private void moveFileFromSftpToS3(ReportsImportDTO dto, SFTPClient sftpClient) throws IOException{
		//DE18894 issue with the temp path so instead of using the whole
		//path of the target just use the file name only for creating the temp file
		File file = new File(dto.getTargetFilePath());
		String[] filename = file.getName().split("\\.");
		//create a local temp file
		Path temp = Files.createTempFile(filename[0], "."+filename[1]);
		File tempFile = temp.toFile();
		//move the file from the sftp server to the local temp file
		sftpClient.get(dto.getSourceFilePath(), tempFile.getAbsolutePath());
		//send the local temp file to s3
		s3.synchMultipartUpload(dto.getTargetFilePath(), tempFile);
		//delete the local temp file
		FileUtils.deleteQuietly(tempFile);
	}
	
	private void reportError(ReportsImportDTO dto, String errorMessage, Throwable t){
		List<BatchUploadReason> uploadBatchReasons = new ArrayList<>();
		BatchUploadReason reason = new BatchUploadReason();
		reason.setBatchUploadId(dto.getBatchReportProcessId());
		reason.setErrorType("reject");
		reason.setLine(dto.getLineNumber());
		reason.setReason(errorMessage + ((t!=null) ? t.getMessage() : ""));
		batchUploadService.insertBatchUploadReasons(uploadBatchReasons);
	}
	
	/**
	 * Deletes all files from S3 where the key begins with 
	 * the path parameter.
	 * 
	 * @param path
	 */
	private void deleteFiles(String path){
		//find all the keys with the key prefix
		List<KeyVersion> keysToDelete = s3.listAllKeysUsingPrefix(path);
		if (!keysToDelete.isEmpty()) {
			// the above check will prevent 400 malformed XML error
			s3.deleteObjectsUsingKeyVersions(keysToDelete);
		}
	}
	
	@Override
	public void deleteClassroomCsvFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException {
		//delete classroom_csv files only
		AssessmentProgram ap = apService.findByAssessmentProgramId(assessmentProgramId);
		Organization state = orgService.get(stateId);
		String apAbbrName = ap.getAbbreviatedname();
		String stateDisplayId = state.getDisplayIdentifier();
		
		//generate the key prefix - the parent 'directory'
		Path csvPath = generateFilePathForDelete(classroomCsvReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(csvPath.toString());		
	}
	@Override
	public void deleteSchoolCsvFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException {
		//delete school_csv files only
		AssessmentProgram ap = apService.findByAssessmentProgramId(assessmentProgramId);
		Organization state = orgService.get(stateId);
		String apAbbrName = ap.getAbbreviatedname();
		String stateDisplayId = state.getDisplayIdentifier();

		//generate the key prefix - the parent 'directory'
		Path csvPath = generateFilePathForDelete(schoolCsvReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(csvPath.toString());		
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteStudentDCPSFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException {
		AssessmentProgram ap = apService.findByAssessmentProgramId(assessmentProgramId);
		Organization state = orgService.get(stateId);
		String apAbbrName = ap.getAbbreviatedname();
		String stateDisplayId = state.getDisplayIdentifier();
		
		//generate the key prefix - the parent 'directory'
		Path path = generateFilePathForDelete(studentDCPSReportsImportReportType, apAbbrName, schoolYear, stateDisplayId);
		deleteFiles(path.toString());
	}

	@Override
	public int deleteStudentDCPSRecords(Long assessmentProgramId,
			Long schoolYear, Long stateId, String assessmentProgramIdentifier) {

		String reportsImportReportType = dbDLMStudentDcpsReportsImportReportType;
		return externalStudentReportsMapper.deleteByStateSchoolYearAssessmentProgramReportType(assessmentProgramId, schoolYear, stateId, reportsImportReportType);
		}
	
}
