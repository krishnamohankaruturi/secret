package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.web.StudentReportDTO;

public class StudentReportDTOJsonConverter {

	public static JQGridJSONModel convertForLastNameSearch(List<StudentReportDTO> students, Integer totalCount, Integer page, Integer limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		if (students != null
				&& CollectionUtils.isNotEmpty(students)) {
			for (StudentReportDTO dto : students) {
				row = new JQGridRow();
				row.setId(dto.getStudentId());
				row.setCell(dto.buildAllStudentReportNameSearchJsonRow());
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

	public static JQGridJSONModel createEmptyModelForLastNameSearch() {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(0);
		jqGridJSONModel.setTotal(0);
		jqGridJSONModel.setPage(1);
		return jqGridJSONModel;
	}

	public static JQGridJSONModel convertForKAPStateIdSearch(List<StudentReportDTO> reports) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		if (reports != null
				&& CollectionUtils.isNotEmpty(reports)) {
			for (StudentReportDTO dto : reports) {
				row = new JQGridRow();
				row.setId(dto.getId());
				row.setCell(dto.buildAllStudentReportSSIDSearchJsonRowForKAP());
				rows.add(row);
			}
			jqGridJSONModel.setRows(rows);
			jqGridJSONModel.setRecords(reports.size());
		}
		
		jqGridJSONModel.setPage(1);
		return jqGridJSONModel;
	}
	
	public static JQGridJSONModel convertForDLMOrCPASSStateIdSearch(List<StudentReportDTO> reports) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		if (reports != null
				&& CollectionUtils.isNotEmpty(reports)) {
			for (StudentReportDTO dto : reports) {
				row = new JQGridRow();
				row.setId(dto.getId());
				row.setCell(dto.buildAllStudentReportSSIDSearchJsonRowForDLMOrCPASS());
				rows.add(row);
			}
			jqGridJSONModel.setRows(rows);
			jqGridJSONModel.setRecords(reports.size());
		}
		
		jqGridJSONModel.setPage(1);
		return jqGridJSONModel;
	}
}
