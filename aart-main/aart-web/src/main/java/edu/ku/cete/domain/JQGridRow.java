package edu.ku.cete.domain;

import java.util.List;

/**
 * @author vittaly
 *
 */
public class JQGridRow {

	/**
	 * id
	 */
	private Long id;
	
	/**
	 * cell
	 */
	private List<String> cell;
		
	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return
	 */
	public List<String> getCell() {
		return cell;
	}
	
	/**
	 * @param cell
	 */
	public void setCell(List<String> cell) {
		this.cell = cell;
	}
	
}
