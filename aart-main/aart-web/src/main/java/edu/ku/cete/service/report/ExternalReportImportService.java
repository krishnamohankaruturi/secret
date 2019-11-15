package edu.ku.cete.service.report;

import java.io.IOException;

import edu.ku.cete.web.OrganizationReportsImportDTO;
import edu.ku.cete.web.StudentReportsImportDTO;
import net.schmizz.sshj.sftp.SFTPClient;

public interface ExternalReportImportService {
	public int deleteClassroomRecords(Long assessmentProgramId, Long schoolYear, Long stateId);
	public void deleteClassroomFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException;
	public int deleteSchoolRecords(Long assessmentProgramId, Long schoolYear, Long stateId);
	public void deleteSchoolFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException;
	public int deleteStudentRecords(Long assessmentProgramId, Long schoolYear, Long stateId, String assessmentProgramIdentifier);
	public void deleteStudentFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException;
	public int deleteStudentSummaryRecords(Long assessmentProgramId, Long schoolYear, Long stateId);
	public void deleteStudentSummaryFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException;
	public void writeOrganizationReportFiles(OrganizationReportsImportDTO dto, SFTPClient sftpClient);
	public void writeStudentReportFiles(StudentReportsImportDTO dto, SFTPClient sftpClient);
	public void deleteClassroomCsvFiles(Long assessmentProgramId, Long reportYear, Long stateId) throws IOException;
	public void deleteSchoolCsvFiles(Long assessmentProgramId, Long reportYear, Long stateId) throws IOException;
	public int deleteStudentDCPSRecords(Long assessmentProgramId, Long schoolYear, Long stateId, String assessmentProgramIdentifier);
	public void deleteStudentDCPSFiles(Long assessmentProgramId, Long schoolYear, Long stateId) throws IOException;
}
