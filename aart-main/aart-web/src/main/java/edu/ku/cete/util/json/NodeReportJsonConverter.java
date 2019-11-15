/**
 * 
 */
package edu.ku.cete.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.Row;
import edu.ku.cete.domain.report.NodeReport;
import edu.ku.cete.util.NumericUtil;

/**
 * @author mahesh
 *
 */
public class NodeReportJsonConverter {
	
	private LmAttributeConfiguration lmAttributeConfiguration;
	
	public NodeReportJsonConverter(
			LmAttributeConfiguration lmAttributeConfiguration2) {
		this.lmAttributeConfiguration = lmAttributeConfiguration2;
	}

	/**
	 * @return the lmAttributeConfiguration
	 */
	public final LmAttributeConfiguration getLmAttributeConfiguration() {
		return lmAttributeConfiguration;
	}

	/**
	 * This one cannot use the reportable interface because the attributes are configurable.
	 * @param nodeReportItems
	 * @param totalCount
	 * @param page
	 * @param limitCount
	 * @return
	 */
	public JsonResultSet convertToNodeReportJson(
			Collection<NodeReport> nodeReportItems,
			int totalCount, int page, int limitCount,
			LmAttributeConfiguration lmAttributeConfiguration) {
		JsonResultSet jsonResultSet = null;
		jsonResultSet = new JsonResultSet();
		List<Row> rows = new ArrayList<Row>();
		if (nodeReportItems != null
				&& CollectionUtils.isNotEmpty(nodeReportItems)) {
			for (NodeReport nodeReportItem : nodeReportItems) {
				Row row = convertToRow(nodeReportItem,lmAttributeConfiguration);
				rows.add(row);
			}
		}
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
	 * @param nodeReport
	 * @return
	 */
	public Row convertToRow(NodeReport nodeReport, LmAttributeConfiguration lmAttributeConfiguration) {
		Row row = new Row();
		nodeReport.setLmAttributeConfiguration(lmAttributeConfiguration);
		//INFO NodeReport has composite primary key only. student id is not 
		// really a primary key.
		//TODO add studentsTestSectionsId and 
		row.setId(nodeReport.getId());
		row.setCell(nodeReport.getAttributes(lmAttributeConfiguration));
		return row;
	}

	public static NodeReportJsonConverter getInstance(LmAttributeConfiguration lmAttributeConfiguration) {
		return new NodeReportJsonConverter(lmAttributeConfiguration);
	}
	
}
