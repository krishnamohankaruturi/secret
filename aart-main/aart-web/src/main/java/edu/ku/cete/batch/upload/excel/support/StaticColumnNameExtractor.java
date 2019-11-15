package edu.ku.cete.batch.upload.excel.support;

import edu.ku.cete.batch.upload.excel.Sheet;

public class StaticColumnNameExtractor implements ColumnNameExtractor {

    private final String[] columnNames;

    public StaticColumnNameExtractor(String[] columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public String[] getColumnNames(Sheet sheet) {
        return this.columnNames;
    }

}
