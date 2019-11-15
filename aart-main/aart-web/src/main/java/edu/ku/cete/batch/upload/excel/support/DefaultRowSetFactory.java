package edu.ku.cete.batch.upload.excel.support;

import edu.ku.cete.batch.upload.excel.Sheet;

public class DefaultRowSetFactory implements RowSetFactory {

    private ColumnNameExtractor columnNameExtractor = new RowNumberColumnNameExtractor();

    @Override
    public RowSet create(Sheet sheet) {
        DefaultRowSetMetaData metaData = new DefaultRowSetMetaData(sheet);
        metaData.setColumnNameExtractor(columnNameExtractor);
        return new DefaultRowSet(sheet, metaData);
    }

    public void setColumnNameExtractor(ColumnNameExtractor columnNameExtractor) {
        this.columnNameExtractor = columnNameExtractor;
    }
}
