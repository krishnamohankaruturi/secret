package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.util.NumericUtil;

public class GroupsJsonConverter {
	
	public static JQGridJSONModel convertToGroupsJson(List<Groups> groups){
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (groups != null
				&& CollectionUtils.isNotEmpty(groups)) {
			for (Groups group : groups) {
				row = new JQGridRow();
				row.setId(group.getGroupId());
				row.setCell(group.buildJSONRowForRolesPage());				
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(rows.size());
		return jqGridJSONModel;
	}
	
	public static JQGridJSONModel convertToGroupsJson(
			List<Groups> groups,  Integer page, Integer limitCount) {
		JQGridJSONModel jqGridJSONModel = convertToGroupsJson(groups);

		jqGridJSONModel.setTotal(
				NumericUtil.getPageCount(
						jqGridJSONModel.getRecords(), limitCount));
		jqGridJSONModel.setPage(page);
		
		return jqGridJSONModel;
	}
}
