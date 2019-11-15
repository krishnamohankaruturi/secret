package edu.ku.cete.batch.upload.excel.support;

public interface RowSetMetaData {

    /**
     * Retrieves the names of the columns for the current sheet.
     *
     * @return the column names.
     */
    String[] getColumnNames();

    /**
     * Retrieves the column name for the indicated column.
     *
     * @param idx the index of the column, 0 based
     * @return the column name
     */
    String getColumnName(int idx);

    /**
     * Retrieves the number of columns in the RowSet.
     *
     * @return the number of columns
     */
    int getColumnCount();

    /**
     * Retrieves the name of the sheet the RowSet is based on.
     *
     * @return the name of the sheet
     */
    String getSheetName();
}
