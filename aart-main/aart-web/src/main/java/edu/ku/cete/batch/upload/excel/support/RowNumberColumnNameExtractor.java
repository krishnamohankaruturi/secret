package edu.ku.cete.batch.upload.excel.support;

import edu.ku.cete.batch.upload.excel.Sheet;

public class RowNumberColumnNameExtractor implements ColumnNameExtractor {

    private int headerRowNumber;

    @Override
    public String[] getColumnNames(final Sheet sheet) {
        return sheet.getRow(headerRowNumber);
    }

    public void setHeaderRowNumber(int headerRowNumber) {
        this.headerRowNumber = headerRowNumber;
    }
}