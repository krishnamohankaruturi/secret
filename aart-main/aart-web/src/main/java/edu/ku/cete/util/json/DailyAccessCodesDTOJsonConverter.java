package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.DailyAccessCodesDTO;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JQGridRow;
import edu.ku.cete.util.NumericUtil;

/**
 * @author ktaduru_sta
 *
 */
public class DailyAccessCodesDTOJsonConverter {

	/**
	 * @param modules
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public static JQGridJSONModel convertToDailyAccessCodesDTOJson(
			Collection<DailyAccessCodesDTO> dailyAccessCodes, int totalCount, int page, int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		
		if (dailyAccessCodes != null
				&& CollectionUtils.isNotEmpty(dailyAccessCodes)) {
			for (DailyAccessCodesDTO dacDTO : dailyAccessCodes) {
				row = new JQGridRow();
				row.setId(dacDTO.getId());
				row.setCell(dacDTO.buildJSONRow());				
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
	
	public static JQGridJSONModel convertToDailyAccessCodeJson(
			Collection<DailyAccessCode> dailyAccessCodes, int totalCount, int page, int limitCount) {
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		List<JQGridRow> rows = new ArrayList<JQGridRow>();
		JQGridRow row = null;
		Long rowNum = 1L;
		if (dailyAccessCodes != null
				&& CollectionUtils.isNotEmpty(dailyAccessCodes)) {
			for (DailyAccessCode dac : dailyAccessCodes) {
				row = new JQGridRow();
				row.setId(rowNum++);
				row.setCell(dac.buildJSONRow());				
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
