package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.JsonSummaryData;
import edu.ku.cete.domain.Row;
import edu.ku.cete.domain.common.StudenInformationRecordSummaryData;
import edu.ku.cete.domain.student.StudentInformationRecord;
import edu.ku.cete.domain.student.StudentRosterITIRecord;
import edu.ku.cete.util.NumericUtil;

/**
 * Class that converts studentInformationRecord objects to json.
 * @author vittaly
 *
 */
public class StudentRecordJsonConverter {

	public static JsonResultSet convertToStudentRecordJson(
			Collection<StudentInformationRecord> studentInformationRecords,
			int totalCount, int page, int limitCount) {
		JsonResultSet jsonResultSet = null;
		jsonResultSet = new JsonResultSet();
		JsonSummaryData jsonSummaryData = new StudenInformationRecordSummaryData();
		List<Row> rows = new ArrayList<Row>();
		if (studentInformationRecords != null
				&& CollectionUtils.isNotEmpty(studentInformationRecords)) {
			for (StudentInformationRecord studentInformationRecord : studentInformationRecords) {
				Row row = convertToRow(studentInformationRecord);
				rows.add(row);
			}
		}
		jsonSummaryData.setName("Total");
		jsonSummaryData.setFieldSummary1(studentInformationRecords.size());
		jsonResultSet.setJsonSummaryData(jsonSummaryData);
		jsonResultSet.setRows(rows);
		jsonResultSet.setRecords(totalCount);
		jsonResultSet.setTotal(
				NumericUtil.getPageCount(
						totalCount, limitCount));
		jsonResultSet.setPage(page);
		return jsonResultSet;
	}
	
	public static JsonResultSet convertToStudentRecordJsonITI(
			Collection<StudentRosterITIRecord> studentInformationRecords,
			int totalCount, int page, int limitCount) {
		JsonResultSet jsonResultSet = null;
		jsonResultSet = new JsonResultSet();
		JsonSummaryData jsonSummaryData = new StudenInformationRecordSummaryData();
		List<Row> rows = new ArrayList<Row>();
		if (studentInformationRecords != null
				&& CollectionUtils.isNotEmpty(studentInformationRecords)) {
			for (StudentRosterITIRecord studentInformationRecord : studentInformationRecords) {
				Row row = convertToRowITI(studentInformationRecord);
				rows.add(row);
			}
		}
		jsonSummaryData.setName("Total");
		jsonSummaryData.setFieldSummary1(studentInformationRecords.size());
		jsonResultSet.setJsonSummaryData(jsonSummaryData);
		jsonResultSet.setRows(rows);
		jsonResultSet.setRecords(totalCount);
		jsonResultSet.setTotal(
				NumericUtil.getPageCount(
						totalCount, limitCount));
		jsonResultSet.setPage(page);
		return jsonResultSet;
	}
	
	/**
	 * TOOD write a more generic converter for all report json objects.
	 * @param studentRoster
	 * @return
	 */
	public static Row convertToRow(StudentInformationRecord studentInformationRecord) {
		Row row = new Row();
		row.setId(studentInformationRecord.getId());
		row.setCell(studentInformationRecord.getAttributes());
		return row;
	}
	
	/**
	 * TOOD write a more generic converter for all report json objects.
	 * @param studentRoster
	 * @return
	 */
	public static Row convertToRowITI(StudentRosterITIRecord studentInformationRecord) {
		Row row = new Row();
		row.setId(studentInformationRecord.getId());
		row.setCell(studentInformationRecord.getAttributes());
		return row;
	}
}
