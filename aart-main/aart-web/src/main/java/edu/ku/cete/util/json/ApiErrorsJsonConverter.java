package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.apierrors.ApiErrorsRecord;
import edu.ku.cete.util.NumericUtil;

/**
 * 
 * @author n278i693
 * Changes for F851 API Errors Dashboard
 */
public class ApiErrorsJsonConverter {
	
	public static JQGridJSONModel convertToOrganizationJson(Collection<ApiErrorsRecord> apiErrorMessages, 
			int totalCount, Integer page, Integer limitCount){
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (apiErrorMessages != null
				&& CollectionUtils.isNotEmpty(apiErrorMessages)) {
			for (ApiErrorsRecord apiErrorsRecord : apiErrorMessages) {
				row = new JQGridRow();
				row.setId(apiErrorsRecord.getId());
				row.setCell(apiErrorsRecord.buildJSONRow());				
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
