package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.report.UserReportUpload;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.web.RosterDTO;

public class RosterDTOJsonConverter {

	/**
	 * @param modules
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToRosterDTOJson(
			Collection<RosterDTO> rosterDtos, int totalCount, Integer page, Integer limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (rosterDtos != null
				&& CollectionUtils.isNotEmpty(rosterDtos)) {
			for (RosterDTO rosterDto : rosterDtos) {
				row = new JQGridRow();
				row.setId(rosterDto.getRoster().getId());
				row.setCell(rosterDto.buildJSONRow());				
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
