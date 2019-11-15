package edu.ku.cete.domain;

import java.util.Arrays;

public class Row {
	private long id;
	private String[] cell;
	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the cell
	 */
	public final String[] getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public final void setCell(String[] cell) {
		this.cell = cell;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Row [id=");
		builder.append(id);
		builder.append(", ");
		if (cell != null) {
			builder.append("cell=");
			builder.append(Arrays.toString(cell));
		}
		builder.append("]");
		return builder.toString();
	}
}
