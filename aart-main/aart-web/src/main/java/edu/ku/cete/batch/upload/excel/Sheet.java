package edu.ku.cete.batch.upload.excel;

public interface Sheet {

    /**
     * Get the number of rows in this sheet.
     *
     * @return the number of rows.
     */
    int getNumberOfRows();

    /**
     * Get the name of the sheet.
     *
     * @return the name of the sheet.
     */
    String getName();

    /**
     * Get the row as a String[]. Returns null if the row doesn't exist.
     *
     * @param rowNumber the row number to read.
     * @return a String[] or null
     */
    String[] getRow(int rowNumber);

    /**
     * The number of columns in this sheet.
     *
     * @return number of columns
     */
    int getNumberOfColumns();
}
