package edu.ku.cete.batch.upload.excel.support;

import edu.ku.cete.batch.upload.excel.Sheet;

public interface ColumnNameExtractor {

    /**
     * Retrieves the names of the columns in the given Sheet.
     *
     * @param sheet the sheet
     * @return the column names
     */
    String[] getColumnNames(Sheet sheet);

}
