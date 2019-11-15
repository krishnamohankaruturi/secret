package edu.ku.cete.batch.upload.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import edu.ku.cete.batch.upload.excel.support.DefaultRowSetFactory;
import edu.ku.cete.batch.upload.excel.support.RowSet;
import edu.ku.cete.batch.upload.excel.support.RowSetFactory;
import edu.ku.cete.service.AwsS3Service;

public abstract class AbstractExcelItemReader<T> extends
		AbstractItemCountingItemStreamItemReader<T> implements
		ResourceAwareItemReaderItemStream<T>, InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());
	private Resource resource;
	private int linesToSkip = 0;
	private int currentSheet = 0;
	private LineMapper<T> lineMapper;
	private LineCallbackHandler skippedLinesCallback;
	private boolean noInput = false;
	private boolean strict = true;
	private RowSetFactory rowSetFactory = new DefaultRowSetFactory();
	private RowSet rs;
	private int noOfColumns;
	private String fileName;
	
	@Autowired
	private AwsS3Service s3;
	
	public AbstractExcelItemReader() {
		super();
		this.setName(ClassUtils.getShortName(this.getClass()));
	}

	/**
	 * @return string corresponding to logical record according to
	 *         {@link #setRowMapper(RowMapper)} (might span multiple rows in
	 *         file).
	 */
	@Override
	protected T doRead() throws Exception {
		if (this.noInput || this.rs == null) {
			return null;
		}

		if (rs.next()) {
			try {
				String[] rowValues = new String[noOfColumns];
				if(rs.getCurrentRow() != null){
					//trimming extra column empty values
					for (int i = 0; i < noOfColumns; i++) {
						rowValues[i] = rs.getCurrentRow()[i].trim();
					}
				}
				return this.lineMapper.mapLine(StringUtils.join(rowValues, ","), rs.getCurrentRowIndex()+1);
			} catch (final Exception e) {
				throw new ExcelFileParseException(
						"Exception parsing Excel file.", e,
						this.resource.getDescription(), rs.getMetaData()
								.getSheetName(), rs.getCurrentRowIndex(),
						rs.getCurrentRow());
			}
		} else {
			this.currentSheet++;
			if (this.currentSheet >= 1) { //this.getNumberOfSheets()..Assuming we should not allow multiple sheets
				if (logger.isDebugEnabled()) {
					logger.debug("No more sheets in '"
							+ this.resource.getDescription() + "'.");
				}
				return null;
			} else {
				this.openSheet();
				return this.doRead();
			}
		}
	}

	@Override
	protected void doOpen() throws Exception {
		Assert.notNull(this.resource, "Input resource must be set");
		this.noInput = true;
		if (!this.resource.exists()) {
			if (this.strict) {
				throw new IllegalStateException(
						"Input resource must exist (reader is in 'strict' mode): "
								+ this.resource);
			}
			logger.warn("Input resource does not exist '"
					+ this.resource.getDescription() + "'.");
			return;
		}

		if (!this.resource.isReadable()) {
			if (this.strict) {
				throw new IllegalStateException(
						"Input resource must be readable (reader is in 'strict' mode): "
								+ this.resource);
			}
			logger.warn("Input resource is not readable '"
					+ this.resource.getDescription() + "'.");
			return;
		}

		this.openExcelFile(this.resource);
		this.openSheet();
		this.noInput = false;
		if (logger.isDebugEnabled()) {
			logger.debug("Opened workbook [" + this.resource.getFilename()
					+ "] with " + this.getNumberOfSheets() + " sheets.");
		}
	}

	private void openSheet() {
		final Sheet sheet = this.getSheet(this.currentSheet);
		this.rs = rowSetFactory.create(sheet);

		if (logger.isDebugEnabled()) {
			logger.debug("Opening sheet " + sheet.getName() + ".");
		}

		for (int i = 0; i < this.linesToSkip; i++) {
			if (rs.next() && this.skippedLinesCallback != null) {
				this.skippedLinesCallback.handleLine(StringUtils.join(rs.getCurrentRow(), ","));//To support existing call back method
				String[] header = StringUtils.join(rs.getCurrentRow(), ",").split(",");
				noOfColumns = header.length;						
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Openend sheet " + sheet.getName() + ", with "
					+ sheet.getNumberOfRows() + " rows.");
		}

	}

	/**
	 * Public setter for the input resource.
	 * 
	 * @param resource
	 *            the {@code Resource} pointing to the Excelfile
	 */
	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.lineMapper, "RowMapper must be set");
	}

	/**
	 * Set the number of lines to skip. This number is applied to all worksheet
	 * in the excel file! default to 0
	 * 
	 * @param linesToSkip
	 *            number of lines to skip
	 */
	public void setLinesToSkip(final int linesToSkip) {
		this.linesToSkip = linesToSkip;
	}

	/**
	 * 
	 * @param sheet
	 *            the sheet index
	 * @return the sheet or <code>null</code> when no sheet available.
	 */
	protected abstract Sheet getSheet(int sheet);

	/**
	 * The number of sheets in the underlying workbook.
	 * 
	 * @return the number of sheets.
	 */
	protected abstract int getNumberOfSheets();

	/**
	 * 
	 * @param resource
	 *            {@code Resource} pointing to the Excel file to read
	 * @throws Exception
	 *             when the Excel sheet cannot be accessed
	 */
	protected abstract void openExcelFile(Resource resource) throws Exception;

	/**
	 * In strict mode the reader will throw an exception on
	 * {@link #open(org.springframework.batch.item.ExecutionContext)} if the
	 * input resource does not exist.
	 * 
	 * @param strict
	 *            true by default
	 */
	public void setStrict(final boolean strict) {
		this.strict = strict;
	}

	/**
	 * Public setter for the <code>rowSetFactory</code>. Used to create a
	 * {@code RowSet} implemenation. By default the {@code DefaultRowSetFactory}
	 * is used.
	 * 
	 * @param rowSetFactory
	 *            the {@code RowSetFactory} to use.
	 */
	public void setRowSetFactory(RowSetFactory rowSetFactory) {
		this.rowSetFactory = rowSetFactory;
	}

	/**
	 * @param SkippedLinesCallback
	 *            will be called for each one of the initial skipped lines
	 *            before any items are read.
	 */
	
	public LineCallbackHandler getSkippedLinesCallback() {
		return skippedLinesCallback;
	}

	public void setSkippedLinesCallback(LineCallbackHandler skippedLinesCallback) {
		this.skippedLinesCallback = skippedLinesCallback;
	}
	
	/**
	 * Public setter for the {@code lineMapper}. Used to map a row read from the
	 * underlying Excel workbook.
	 */
	public LineMapper<T> getLineMapper() {
		return lineMapper;
	}

	public void setLineMapper(LineMapper<T> lineMapper) {
		this.lineMapper = lineMapper;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		//when setting the fileName - get the resource with that name and set as well
		setResource(s3.getObjectAsInputStreamResource(this.fileName));
	}
}
