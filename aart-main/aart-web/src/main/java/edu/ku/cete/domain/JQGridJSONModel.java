package edu.ku.cete.domain;

import java.util.List;

/**
 * @author vittaly
 *
 */
public class JQGridJSONModel {

	/**
	 * page
	 */
	private Integer page;
	
	/**
	 * total
	 */
	private Integer total;
	
	/**
	 * records
	 */
	private Integer records;
	
	/**
	 * rows
	 */
	private List<JQGridRow> rows;
	
	/**
	 * @return
	 */
	public Integer getPage() {
		return page;
	}
	
	/**
	 * @param page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}
	
	/**
	 * @return
	 */
	public Integer getTotal() {
		return total;
	}
	
	/**
	 * @param total
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	/**
	 * @return
	 */
	public Integer getRecords() {
		return records;
	}
	
	/**
	 * @param records
	 */
	public void setRecords(Integer records) {
		this.records = records;
	}
	
	/**
	 * @return
	 */
	public List<JQGridRow> getRows() {
		return rows;
	}
	
	/**
	 * @param rows
	 */
	public void setRows(List<JQGridRow> rows) {
		this.rows = rows;
	}
	
}
