package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.util.NumericUtil;

public class BatchUploadJsonConverter {

	public static JQGridJSONModel convertToSummativeUploadJson(
			List<BatchUpload> uploads, int totalCount, int page, int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (uploads != null && CollectionUtils.isNotEmpty(uploads)) {
			for (BatchUpload upload : uploads) {
				row = new JQGridRow();
				row.setId(upload.getId());
				row.setCell(upload.buildJSONRow());				
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