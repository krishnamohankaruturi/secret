/**
 * 
 */
package edu.ku.cete.batch.upload.xml.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Rajendra Kumar Cherukuri
 *
 */
public class XmlXpathParser implements XpathParser {

	private String expression;

	public XmlXpathParser(String expression) {
		this.expression = expression;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.batch.upload.xml.parser.XpathParser#parse(java.io.File)
	 */
	@Override
	public Document parse(File inputFile) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(inputFile);
		document.getDocumentElement().normalize();
		return document;
	}
	@Override
	public Document parse(String inputXmlString) throws Exception
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(inputXmlString.getBytes("utf-8"));
		Document document = dBuilder.parse(stream);
		document.getDocumentElement().normalize();
		return document;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.batch.upload.xml.parser.XpathParser#getItems(org.w3c.dom.
	 * Document, java.lang.String)
	 */
	@Override
	public NodeList getRootElements(Document document) throws Exception {
		if (StringUtils.isNotBlank(getExpression())) {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile(getExpression()).evaluate(document, XPathConstants.NODESET);
			return nodeList;
		}
		return null;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String getValue(String xPathValue, Node node) throws Exception {
		if (StringUtils.isNotBlank(xPathValue)) {
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node item = (Node) xPath.compile(xPathValue).evaluate(node, XPathConstants.NODE);
			if (item != null) {
				return item.getTextContent();
			}
			return null;
		}
		return null;
	}

}
