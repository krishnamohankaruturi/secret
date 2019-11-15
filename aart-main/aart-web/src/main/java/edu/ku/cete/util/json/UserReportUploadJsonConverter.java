package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.TestCollectionDTO;
import edu.ku.cete.domain.report.UserReportUpload;
import edu.ku.cete.util.NumericUtil;

public class UserReportUploadJsonConverter {

	/**
	 * @param modules
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToUserReportUploadJson(
			Collection<UserReportUpload> userReportUploads, int totalCount, int page, int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (userReportUploads != null
				&& CollectionUtils.isNotEmpty(userReportUploads)) {
			for (UserReportUpload userReportUpload : userReportUploads) {
				row = new JQGridRow();
				row.setId(userReportUpload.getId());
				row.setCell(userReportUpload.buildJSONRow());				
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(
				NumericUtil.getPageCount(
						totalCount, limitCount));
		jqGridJSONModel.setPage(page);
		
		return jqGridJSONModel;
	}
	
}
