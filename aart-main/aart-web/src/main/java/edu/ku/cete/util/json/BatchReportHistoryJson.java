package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.ReportProcess;
import edu.ku.cete.util.NumericUtil;

public class BatchReportHistoryJson {

	/**
	 * @param reportHistory
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToBatchReportHistoryJson(
			Collection<ReportProcess> reportHistory, int totalCount, Integer page, Integer limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (reportHistory != null && CollectionUtils.isNotEmpty(reportHistory)) {
			for (ReportProcess report : reportHistory) {
				row = new JQGridRow();
				row.setId(report.getId());
				row.setCell(report.buildJSONRow());				
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
