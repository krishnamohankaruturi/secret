package edu.ku.cete.batch.upload.excel.poi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import edu.ku.cete.batch.upload.excel.Sheet;

public class ExcelSheet implements Sheet {

    private final org.apache.poi.ss.usermodel.Sheet delegate;
    private final int numberOfRows;
    private final String name;

    private int numberOfColumns = -1;
    private FormulaEvaluator evaluator;

    /**
     * Constructor which takes the delegate sheet.
     *
     * @param delegate the apache POI sheet
     * @return 
     */
     ExcelSheet(final org.apache.poi.ss.usermodel.Sheet delegate) {
        super();
        this.delegate = delegate;
        this.numberOfRows = this.delegate.getLastRowNum() + 1;
        this.name=this.delegate.getSheetName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getRow(final int rowNumber) {
        final Row row = this.delegate.getRow(rowNumber);
        if (row == null) {
            return null;
        }
        final List<String> cells = new LinkedList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (int i = 0; i < getNumberOfColumns(); i++) {
            Cell cell = row.getCell(i);
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cells.add(String.valueOf(sdf.format(date.getTime())));
                    } else {
                    	DataFormatter formatter = new DataFormatter();
                    	cells.add(formatter.formatCellValue(cell));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cells.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case Cell.CELL_TYPE_STRING:
                	if(cell.getStringCellValue().indexOf(",") > 0){
                		cells.add("\""+cell.getStringCellValue()+"\"");
                	}else{
                		cells.add(cell.getStringCellValue());
                	}
                	break;
                case Cell.CELL_TYPE_BLANK:
                    cells.add(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cells.add(getFormulaEvaluator().evaluate(cell).formatAsString());
                    break;
                default:
                    throw new IllegalArgumentException("Cannot handle cells of type " + cell.getCellType());
            }
        }
        return cells.toArray(new String[cells.size()]);
    }

    private FormulaEvaluator getFormulaEvaluator() {
        if (this.evaluator == null) {
            this.evaluator = delegate.getWorkbook().getCreationHelper().createFormulaEvaluator();
        }
        return this.evaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfColumns() {
        if (numberOfColumns < 0) {
            numberOfColumns = this.delegate.getRow(0).getLastCellNum();
        }
        return numberOfColumns;
    }
}
