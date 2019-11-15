/**
 * 
 */
package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.JsonSummaryData;
import edu.ku.cete.domain.Row;
import edu.ku.cete.domain.UserModule;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.ModuleSummaryData;
import edu.ku.cete.domain.professionaldevelopment.Module;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.util.NumericUtil;

/**
 * 
 * @author craigshatswell_sta
 *
 */
public class ProfDevModuleJsonConverter {

	public static JsonResultSet convertToModuleJsonForAdmin(
			Collection<Module> modules, int totalCount, int page, int limitCount) {
		JsonResultSet jsonResultSet = null;
		jsonResultSet = new JsonResultSet();
		JsonSummaryData jsonSummaryData = new ModuleSummaryData();
		List<Row> rows = new ArrayList<Row>();
		if (modules != null && CollectionUtils.isNotEmpty(modules)) {
			for (Module module : modules) {
				Row row = convertToRow(module);
				rows.add(row);
			}
		}
		jsonSummaryData.setName("Total");
		jsonSummaryData.setFieldSummary1(modules.size());
		jsonResultSet.setJsonSummaryData(jsonSummaryData);
		jsonResultSet.setRows(rows);
		jsonResultSet.setRecords(totalCount);
		jsonResultSet
				.setTotal(NumericUtil.getPageCount(totalCount, limitCount));
		jsonResultSet.setPage(page);
		return jsonResultSet;
	}

	public static Row convertToRow(Module module) {
		Row row = new Row();
		row.setId(module.getId());
		row.setCell(module.getAttributes());
		return row;
	}

	/**
	 * @param modules
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToModuleJson(
			Collection<Module> modules, int totalCount, int page,
			int limitCount, Map<Long, Category> enrollmentCategoryMap) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;

		if (modules != null && CollectionUtils.isNotEmpty(modules)) {
			for (Module module : modules) {
				row = new JQGridRow();
				row.setId(module.getId());
				row.setCell(module.buildJSONRow(enrollmentCategoryMap));
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(NumericUtil.getPageCount(totalCount,
				limitCount));
		jqGridJSONModel.setPage(page);

		return jqGridJSONModel;
	}

	public static JQGridJSONModel convertToTranscriptsJson(
			Collection<UserModule> userModules, int totalCount, int page,
			int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;

		if (userModules != null && CollectionUtils.isNotEmpty(userModules)) {
			for (UserModule usermodule : userModules) {
				row = new JQGridRow();
				row.setId(usermodule.getId());
				row.setCell(usermodule.buildTranscriptsJSONRow());
				rows.add(row);
			}
		}

		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(NumericUtil.getPageCount(totalCount,
				limitCount));
		jqGridJSONModel.setPage(page);

		return jqGridJSONModel;
	}

	public static JQGridJSONModel convertToAdminReportsJson(
			Collection<ModuleReport> moduleReports, int totalCount, int page,
			int limitCount, UserDetails userDetails) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;

		boolean foundTrainingStatus = false;
		boolean foundTrainingDetails = false;
		boolean pdTrainingDetails = false;
		
		if (moduleReports != null && CollectionUtils.isNotEmpty(moduleReports)) {

			for (ModuleReport moduleReport : moduleReports) {
				if (moduleReport.getReportType().equals(
						DataReportTypeEnum.TRAINING_STATUS.getName())) { // "Training Status"
					foundTrainingStatus = true;
				} else if (moduleReport.getReportType().equals(
						DataReportTypeEnum.TRAINING_DETAILS.getName())) { // "Training Details"
					foundTrainingDetails = true;
				} else if (moduleReport.getReportType().equals(
						DataReportTypeEnum.DLM_PD_TRAINING_LIST.getName())) { // "DLM PD Training List Export"
					pdTrainingDetails = true;
				}
				row = new JQGridRow();
				row.setId(moduleReport.getId());
				row.setCell(moduleReport.buildAdminReportsJSONRow());
				rows.add(row);
			}

		}
		/*
		if (!foundTrainingStatus) {
			row = new JQGridRow();
			row.setId(-1L);
			row.setCell(new ModuleReport().buildEmptyAdminReportsJSONRow());
			rows.add(row);
		}

		if (!foundTrainingDetails) {
			row = new JQGridRow();
			row.setId(-1L);
			row.setCell(new ModuleReport()
					.buildEmptyAdminReportDetailsJSONRow());
			rows.add(row);
		}*/
		GrantedAuthority authority = new SimpleGrantedAuthority(
				"PD_TRAINING_EXPORT_FILE_CREATOR");
		
		if (userDetails.getAuthorities().contains(authority)) {
			if (!pdTrainingDetails) {
				row = new JQGridRow();
				row.setId(-1L);
				row.setCell(new ModuleReport()
						.buildEmptyExportDLMUsersJSONRow());
				rows.add(row);
			}
		}
		jqGridJSONModel.setRows(rows);
		jqGridJSONModel.setRecords(totalCount);
		jqGridJSONModel.setTotal(NumericUtil.getPageCount(totalCount,
				limitCount));
		jqGridJSONModel.setPage(page);

		return jqGridJSONModel;
	}
}
