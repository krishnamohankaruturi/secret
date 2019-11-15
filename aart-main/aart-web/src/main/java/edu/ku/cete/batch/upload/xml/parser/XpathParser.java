/**
 * 
 */
package edu.ku.cete.batch.upload.xml.parser;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Rajendra Kumar Cherukuri
 *
 */
public interface XpathParser {
	public Document parse(File inputFile) throws Exception;
	public Document parse(String inputXmlString) throws Exception;

	public NodeList getRootElements(Document document) throws Exception;

	public String getValue(String xPath, Node node) throws Exception;

	public String getExpression();
}
