/**
 * 
 */
package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.Row;
import edu.ku.cete.domain.JsonSummaryData;
import edu.ku.cete.domain.common.StudentRosterSummaryData;
import edu.ku.cete.domain.testsession.AutoRegisteredTestSessionDTO;
import edu.ku.cete.domain.testsession.TestSessionRoster;
import edu.ku.cete.util.NumericUtil;

/**
 * @author mahesh
 *
 */
public class TestSessionRosterJsonConverter {
	
	public static JsonResultSet convertToStudentRosterJson(Collection<TestSessionRoster> testSessionRosters,
			int totalCount, int page, int limitCount) {
		JsonResultSet jsonResultSet = null;
		jsonResultSet = new JsonResultSet();
		JsonSummaryData jsonSummaryData = new StudentRosterSummaryData();
		List<Row> rows = new ArrayList<Row>();
		if (testSessionRosters != null && CollectionUtils.isNotEmpty(testSessionRosters)) {
			for (TestSessionRoster testSessionRoster : testSessionRosters) {
				Row row = convertToRow(testSessionRoster);
				rows.add(row);
			}
		}
		jsonSummaryData.setName("Total");
		jsonSummaryData.setFieldSummary1(testSessionRosters.size());
		jsonResultSet.setJsonSummaryData(jsonSummaryData);
		jsonResultSet.setRows(rows);
		jsonResultSet.setRecords(totalCount);
		jsonResultSet.setTotal(NumericUtil.getPageCount(totalCount, limitCount));
		jsonResultSet.setPage(page);
		return jsonResultSet;
	}
	
	/**
	 * TOOD write a more generic converter for all report json objects.
	 * @param testSessionRoster
	 * @return
	 */
	public static Row convertToRow(TestSessionRoster testSessionRoster) {
		Row row = new Row();
		//INFO NodeReport has composite primary key only. student id is not 
		// really a primary key.
		//TODO add studentsTestSectionsId and 
		row.setId(testSessionRoster.getId());
		row.setCell(testSessionRoster.getAttributes());
		return row;
	}
	
	
	public static JQGridJSONModel convertToAutoRegisteredTestSessionsJson(
			Collection<AutoRegisteredTestSessionDTO> autoRegisteredTestSessions, int totalCount, int page, int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (autoRegisteredTestSessions != null
				&& CollectionUtils.isNotEmpty(autoRegisteredTestSessions)) {
			for (AutoRegisteredTestSessionDTO autoRegisteredTestSession : autoRegisteredTestSessions) {
				row = new JQGridRow();
				row.setId(autoRegisteredTestSession.getTestSessionId());
				row.setCell(autoRegisteredTestSession.buildJSONRow());				
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
