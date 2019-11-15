package edu.ku.cete.service.report;

import java.util.List;
import edu.ku.cete.domain.report.UserReportUpload;

/**
 * @author vittaly
 *
 */
public interface UserUploadService {

	/**
	 * @param userId
	 * @return
	 */
	List<UserReportUpload>	getLatestUploadedDataByUser(Long userId, String sortByColumn, String sortType);
	
	/**
	 * @param fileName
	 * @param fileContent
	 * @param fileTypeId
	 * @param userId
	 * @return
	 */
	UserReportUpload insertReportFileData(String fileName, String fileContent, Long fileTypeId, Long userId );
	
	void deleteReportFileData(Long reportId);
}
