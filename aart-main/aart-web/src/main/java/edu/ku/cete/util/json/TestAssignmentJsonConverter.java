package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecord;
import edu.ku.cete.util.NumericUtil;

/**
 * 
 * @author n278i693
 * Changes for F845 Test Assignment Errors Dashboard
 */
public class TestAssignmentJsonConverter {
	
	public static JQGridJSONModel convertToOrganizationJson(Collection<TestAssignmentErrorRecord> testAssignmentErrors, 
			int totalCount, Integer page, Integer limitCount){
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (testAssignmentErrors != null
				&& CollectionUtils.isNotEmpty(testAssignmentErrors)) {
			for (TestAssignmentErrorRecord testAssignmentErrorRecord : testAssignmentErrors) {
				row = new JQGridRow();
				row.setId(testAssignmentErrorRecord.getId());
				row.setCell(testAssignmentErrorRecord.buildJSONRow());				
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
