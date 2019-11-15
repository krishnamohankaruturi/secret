package edu.ku.cete.batch.upload.excel.support;

import edu.ku.cete.batch.upload.excel.Sheet;

public interface RowSetFactory {

    /**
     * Create a rowset instance.
     *
     * @param sheet an Excel sheet.
     * @return a RowSet instance.
     */
    RowSet create(Sheet sheet);
}
