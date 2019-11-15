/**
 * 
 */
package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.Row;
import edu.ku.cete.domain.JsonSummaryData;
import edu.ku.cete.domain.common.StudentRosterSummaryData;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.professionaldevelopment.Module;
import edu.ku.cete.util.NumericUtil;

/**
 * @author mahesh
 *
 */
public class StudentRosterJsonConverter {
	
	/**
	 * @param modules
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToStudentRosterJson(
			Collection<StudentRoster> studentRosters, int totalCount, int page, int limitCount,
			StudentsTestsStatusConfiguration studentsTestsStatusConfiguration) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (studentRosters != null
				&& CollectionUtils.isNotEmpty(studentRosters)) {
			for (StudentRoster studentRoster : studentRosters) {
				row = new JQGridRow();
				row.setId(studentRoster.getId());
				row.setCell(studentRoster.buildJSONRow(studentsTestsStatusConfiguration));				
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
