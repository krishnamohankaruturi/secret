package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.ksde.kids.result.KidsRecentRecords;

public class KidsRecentRecordsJsonConvertor {
	
	public static JQGridJSONModel convertToOrganizationJson(Collection<KidsRecentRecords> kidsRecentRecords){
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		long rowId=1L;
		if (kidsRecentRecords != null
				&& CollectionUtils.isNotEmpty(kidsRecentRecords)) {
			for (KidsRecentRecords kidsRecentRecord : kidsRecentRecords) {
				row = new JQGridRow();				
				row.setCell(kidsRecentRecord.buildJSONRow());
				row.setId(rowId);
				rows.add(row);
				rowId=rowId+1L;
			}
		}

		jqGridJSONModel.setRows(rows);		
		return jqGridJSONModel;
		}
}
