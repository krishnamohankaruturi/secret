package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.ExternalReportImportService;
import edu.ku.cete.util.SFTPUtil;
import edu.ku.cete.web.OrganizationReportsImportDTO;
import edu.ku.cete.web.StudentReportsImportDTO;
import net.schmizz.sshj.sftp.SFTPClient;
@Service
public class ExternalReportImportWriter implements BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(ExternalReportImportWriter.class);
	
	@Autowired
	private ExternalReportImportService externalReportImportService;
	
	@Autowired
	private SFTPUtil sftpUtil;	
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - ExternalReportImport items");
		
		Object objectForType = null;
		SFTPClient sftpClient = sftpUtil.getSftpClient();		
		
		if (objects.size() > 0 ){
			objectForType = objects.get(0);

			if (objectForType instanceof OrganizationReportsImportDTO){
				for( Object object : objects){
					OrganizationReportsImportDTO dto = (OrganizationReportsImportDTO) object;
					externalReportImportService.writeOrganizationReportFiles(dto, sftpClient);
				}
			} else if (objectForType instanceof StudentReportsImportDTO){
				for( Object object : objects){
					StudentReportsImportDTO dto = (StudentReportsImportDTO) object;
					externalReportImportService.writeStudentReportFiles(dto, sftpClient);
				}
			}
		}
	}
	
}
