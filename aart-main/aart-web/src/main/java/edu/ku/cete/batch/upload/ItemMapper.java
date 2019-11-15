package edu.ku.cete.batch.upload;

import java.util.Map;

import org.w3c.dom.Node;

import edu.ku.cete.batch.upload.xml.parser.XmlXpathParser;

public interface ItemMapper<T> {
	T mapItem(Map<String, String> xmlXpaths, Node item, int itemNumber, XmlXpathParser xpathParser) throws Exception;
}
