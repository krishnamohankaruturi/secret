package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.util.DataReportTypeEnum;

public class DataReportJsonConverter {
	public static JQGridJSONModel convertToJQGrid(
			Collection<ModuleReport> moduleReports, int totalCount, int page, int totalPages) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (moduleReports != null
				&& CollectionUtils.isNotEmpty(moduleReports)) {
			for (ModuleReport moduleReport : moduleReports) {
				row = new JQGridRow();
				row.setId(moduleReport.getId());
				if (moduleReport.getId()< 0) { // signal an empty row
					DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
					row.setCell(ModuleReport.buildEmptyJSONRow(type));
				} else {
					List<String> cells = moduleReport.buildDataReportsJSONRow();
					if(moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_ELA_AND_MATH_RETURN_FILE.getId() ||
							moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_SOCIAL_STUDIES_RETURN_FILE.getId() ||
							moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_SCIENCE_RETURN_FILE.getId()){
						cells.add("Kansas");
						cells.add("KAP");
					}else if(moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_DLM_ELA_AND_MATH_RETURN_FILE.getId() ||
							moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_DLM_ELA_RETURN_FILE.getId() ||
							moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_DLM_MATH_RETURN_FILE.getId()){
						cells.add("Kansas");
						cells.add("DLM");
					}else if(moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_CPASS_AGFNR_RETURN_FILE.getId() ||
							moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_CPASS_GKS_RETURN_FILE.getId() ||
							moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_CPASS_MFG_RETURN_FILE.getId()){
						cells.add("Kansas");
						cells.add("CPass");
					} else if (moduleReport.getReportTypeId() == DataReportTypeEnum.KSDE_KELPA_STATE_RETURN_FILE.getId()) {
						cells.add("Kansas");
						cells.add("KELPA2");
					}
					row.setCell(cells);
				}
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(totalPages);
		jqGridJSONModel.setPage(page);
		
		return jqGridJSONModel;
	}
	
	public static JQGridJSONModel generateEmptyJQGridForTypes(List<DataReportTypeEnum> types,
			int totalCount, int page, int totalPages) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		long emptyAlternateId = -1L;
		for (DataReportTypeEnum type : types) {
			JQGridRow row = new JQGridRow();
			row.setId(emptyAlternateId);
			row.setCell(ModuleReport.buildEmptyJSONRow(type));
			rows.add(row);
			emptyAlternateId=emptyAlternateId-1L;
		}
		
		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(totalPages);
		jqGridJSONModel.setPage(page);
		return jqGridJSONModel;
	}
}