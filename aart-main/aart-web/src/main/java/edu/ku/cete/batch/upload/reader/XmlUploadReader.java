/**
 * 
 */
package edu.ku.cete.batch.upload.reader;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.ku.cete.batch.upload.XmlItemMapper;
import edu.ku.cete.batch.upload.xml.parser.XmlXpathParser;
import edu.ku.cete.controller.sif.SifUploadType;
import edu.ku.cete.domain.sif.SifXMLUpload;
import edu.ku.cete.service.AwsS3Service;

/**
 * @author Rajendra Kumar Cherukuri Is it thread safe? R & D required.
 */
public class XmlUploadReader<T> extends AbstractItemCountingItemStreamItemReader<T> implements ResourceAwareItemReaderItemStream<T>,
		InitializingBean {
	private static final Log logger = LogFactory.getLog(XmlUploadReader.class);
	public static final String DEFAULT_CHARSET = Charset.defaultCharset().name();
	
	@Autowired
	edu.ku.cete.report.model.SifXMLFileMapper sifXMLFileMapper;

	public XmlUploadReader() {
		setName(ClassUtils.getShortName(XmlUploadReader.class));
	}

	private Resource resource;
	private Document document;
	private String fileName;
	private boolean noInput = false;
	private int itemsToSkip = 0;
	private ItemCallbackHandler skippedItemsCallback;
	private XmlItemMapper<T> itemMapper;
	private int itemCount = 0;
	private NodeList rootElements;
	private XmlXpathParser xmlXpathParser;
	private Map<String, String> mapping;
	private String uploadTypeCode;
	
	private Long sifUploadId;
	
	@Autowired
	private AwsS3Service s3;

	public XmlXpathParser getXmlXpathParser() {
		return xmlXpathParser;
	}

	public void setXmlXpathParser(XmlXpathParser xmlXpathParser) {
		this.xmlXpathParser = xmlXpathParser;
	}

	@Override
	protected T doRead() throws Exception {
		if (noInput) {
			return null;
		}

		Node item = readItem();

		if (item == null) {
			return null;
		} else {
			try {
				return itemMapper.mapItem(mapping, item, itemCount, getXmlXpathParser());
			} catch (Exception ex) {
				throw new Exception("Parsing error at item: " + itemCount + " in resource=[" + resource.getDescription() + "], input=["
						+ item.toString() + "]", ex);
			}
		}
	}

	@Override
	protected void doOpen() throws Exception {

		Assert.notNull(resource, "Input resource must be set");

		noInput = true;
		/*if (!resource.exists()) {
			logger.warn("Input resource does not exist " + resource.getDescription());
			throw new IllegalStateException("Input resource must exist (reader is in 'strict' mode): " + resource);
		}

		if (!resource.isReadable()) {
			logger.warn("Input resource is not readable " + resource.getDescription());
			throw new IllegalStateException("Input resource must be readable (reader is in 'strict' mode): " + resource);
		}*/
		if(sifUploadId==null)
		{
			logger.warn("Input resource does not exist " + resource.getDescription());
			throw new IllegalStateException("Input resource must exist (reader is in 'strict' mode): " );
		
		}

		// Read the xml file
		//inputFile = resource.getFile();
		// Parse the entire file
		
		String expression = SifUploadType.valueOf(getUploadTypeCode()).getXpathRoot();
		setXmlXpathParser(new XmlXpathParser(expression));
		SifXMLUpload sif= sifXMLFileMapper.selectByPrimaryKey(sifUploadId);
		document = getXmlXpathParser().parse(sif.getXml());
		// Prepare root elements from document. (i.e. for xpath
		// "xStudents/xStudent" it fetches xStudent list).
		rootElements = getXmlXpathParser().getRootElements(document);
		logger.info("Items found:" + rootElements.getLength());

		for (int i = 0; i < itemsToSkip; i++) {
			// Not required as headers are read from database but not the file.
			// Node item = readItem();
			if (skippedItemsCallback != null) {
				mapping = skippedItemsCallback.processHeaders();
			}
		}
		noInput = false;
	}

	private Node readItem() throws Exception {

		if (document == null) {
			throw new ReaderNotOpenException("Reader must be open before it can be read.");
		}

		Node item = null;

		try {
			logger.debug("Reading item: " + itemCount);
			item = rootElements.item(itemCount);
			if (item == null) {
				return null;
			}
			itemCount++;
			logger.debug(item.toString());
		} catch (Exception e) {
			// Prevent IOException from recurring indefinitely
			// if client keeps catching and re-calling
			noInput = true;
			throw new Exception("Unable to read from resource: [" + resource + "]", e);
		}
		return item;
	}

	@Override
	protected void doClose() throws Exception {
		itemCount = 0;
		if (document != null) {
			document = null;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(itemMapper, "ItemMapper is required");

	}

	@Override
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public ItemCallbackHandler getSkippedItemsCallback() {
		return skippedItemsCallback;
	}

	public void setSkippedItemsCallback(ItemCallbackHandler skippedItemsCallback) {
		this.skippedItemsCallback = skippedItemsCallback;
	}

	public int getItemsToSkip() {
		return itemsToSkip;
	}

	public void setItemsToSkip(int itemsToSkip) {
		this.itemsToSkip = itemsToSkip;
	}

	public XmlItemMapper<T> getItemMapper() {
		return itemMapper;
	}

	public void setItemMapper(XmlItemMapper<T> itemMapper) {
		this.itemMapper = itemMapper;
	}

	public String getUploadTypeCode() {
		return uploadTypeCode;
	}

	public void setUploadTypeCode(String uploadTypeCode) {
		this.uploadTypeCode = uploadTypeCode;
	}

	public Long getSifUploadId() {
		return sifUploadId;
	}

	public void setSifUploadId(Long sifUploadId) {
		this.sifUploadId = sifUploadId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		//when setting the fileName - get the resource with that name and set as well
		setResource(s3.getObjectAsInputStreamResource(this.fileName));
	}
}
