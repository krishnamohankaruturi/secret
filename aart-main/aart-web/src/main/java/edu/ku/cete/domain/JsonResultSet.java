package edu.ku.cete.domain;

import java.util.List;

/**
 * @author mahesh
 *
 */
public class JsonResultSet {
	private int page;
	private int total;
	private int records;
	private List<Row> rows;
	private JsonSummaryData jsonSummaryData;
	/**
	 * @return the page
	 */
	public final int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public final void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the total
	 */
	public final int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public final void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the records
	 */
	public final int getRecords() {
		return records;
	}
	/**
	 * @param records the records to set
	 */
	public final void setRecords(int records) {
		this.records = records;
	}
	/**
	 * @return the rows
	 */
	public final List<Row> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public final void setRows(List<Row> rows) {
		this.rows = rows;
	}
	/**
	 * @return the jsonSummaryData
	 */
	public final JsonSummaryData getJsonSummaryData() {
		return jsonSummaryData;
	}
	/**
	 * @param jsonSummaryData the jsonSummaryData to set
	 */
	public final void setJsonSummaryData(JsonSummaryData jsonSummaryData) {
		this.jsonSummaryData = jsonSummaryData;
	}
//	public final JsonSummaryData getUserdata() {
//		return getJsonSummaryData();
//	}
//	public final void setUserdata(JsonSummaryData userData) {
//		setJsonSummaryData(userData);
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsonResultSet [page=");
		builder.append(page);
		builder.append(", total=");
		builder.append(total);
		builder.append(", records=");
		builder.append(records);
		builder.append(", ");
		if (rows != null) {
			builder.append("rows=");
			builder.append(rows);
			builder.append(", ");
		}
		if (jsonSummaryData != null) {
			builder.append("jsonSummaryData=");
			builder.append(jsonSummaryData);
		}
		builder.append("]");
		return builder.toString();
	}

}
