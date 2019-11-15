package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.ksde.kids.result.KidsDashboardRecord;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.util.NumericUtil;

public class KidsDashboardJsonConverter {
	
	public static JQGridJSONModel convertToOrganizationJson(Collection<KidsDashboardRecord> kidsErrorMessages, 
			int totalCount, Integer page, Integer limitCount){
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (kidsErrorMessages != null
				&& CollectionUtils.isNotEmpty(kidsErrorMessages)) {
			for (KidsDashboardRecord kidsDasgDashboardRecord : kidsErrorMessages) {
				row = new JQGridRow();
				row.setId(kidsDasgDashboardRecord.getId());
				row.setCell(kidsDasgDashboardRecord.buildJSONRow());				
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
