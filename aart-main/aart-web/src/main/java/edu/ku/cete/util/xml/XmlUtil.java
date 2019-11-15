package edu.ku.cete.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtil {
	/**
	 * Converts the specified DOM node into an XML string. Assumes no CDATA sections are needed and will include the XML declaration.
	 * <br><br>
	 * Shortcut for <code>convertToXMLString(node, "", true)</code>.
	 * 
	 * @param node The XML node to convert.
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static <NODE extends Node> String convertToXMLString(NODE node) throws TransformerConfigurationException, TransformerException {
		return convertToXMLString(node, "", true);
	}
	
	/**
	 * Converts the specified DOM node into an XML string while using CDATA sections for tags specified. Will include XML declaration.
	 * <br><br>
	 * Shortcut for <code>convertToXMLString(node, cDataTagNames, true)</code>.
	 * 
	 * @param node The XML node to convert.
	 * @param cDataTagNames Any content inside the tags with the specified name(s) will be wrapped in CDATA sections. Separate multiple tags with whitespace.
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static <NODE extends Node> String convertToXMLString(NODE node, String cDataTagNames) throws TransformerConfigurationException, TransformerException {
		return convertToXMLString(node, cDataTagNames, true);
	}
	
	/**
	 * Converts the specified DOM node into an XML string. Choice of whether to include the XML declaration.
	 * <br><br>
	 * Shortcut for <code>convertToXMLString(node, "", includeXmlDecl)</code>.
	 * 
	 * @param node The XML node to convert.
	 * @param includeXmlDecl A boolean to flag whether to include the XML declaration
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static <NODE extends Node> String convertToXMLString(NODE node, boolean includeXmlDecl) throws TransformerConfigurationException, TransformerException {
		return convertToXMLString(node, "", includeXmlDecl);
	}
	
	/**
	 * Converts the specified DOM node into an XML string, while using CDATA sections for tags specified. Choice of whether to include the XML declaration.
	 * 
	 * @param node The XML node to convert.
	 * @param cDataTagNames Any content inside the tags with the specified name(s) will be wrapped in CDATA sections. Separate multiple tags with whitespace.
	 * @param includeXmlDecl A boolean to flag whether the XML declaration is included at the beginning of the XML.
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public static <NODE extends Node> String convertToXMLString(NODE node, String cDataTagNames, boolean includeXmlDecl)
			throws TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		if (!includeXmlDecl) {
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		}
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, cDataTagNames);
		
		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		
		DOMSource domSource = new DOMSource(node);
		
		transformer.transform(domSource, streamResult);
		
		return stringWriter.toString();
	}
	
	/**
	 * Strips out any illegal characters in an XML string. Valid character hex codes are:<br><br>
	 * 
	 * 0x9, 0xA, 0xD,<br>
	 * 0x20 - 0xD7FF,<br>
	 * 0xE000 - 0FFFD,<br>
	 * 0x10000 - 0x10FFFF
	 * <br><br>
	 * Any characters outside of these values will be omitted from the result.
	 * 
	 * @param in
	 * @return
	 */
	public static String sanitizeXmlChars(String in) {
		StringBuilder out = new StringBuilder();
		char current;
		
		if (in == null || ("".equals(in))) {
			return in;
		}
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}
	
	/**
	 * Opens a file at the specified path and parses the XML into a normalized Document object.
	 * @param filePath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document readFromFile(String filePath) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File(filePath);
		return readFromFile(xmlFile);
	}
	
	/**
	 * Parses the XML contained in the file into a normalized Document object.
	 * @param xmlFile
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document readFromFile(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	/**
	 * Parses the target string (without sanitizing it) and returns a normalized Document object.<br><br>
	 * 
	 * This is a shortcut for <code>readFromString(xml, false)</code>.
	 * @param xml
	 * @return 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document readFromString(String xml) throws ParserConfigurationException, SAXException, IOException {
		return readFromString(xml, false);
	}
	
	/**
	 * Sanitizes the string if specified, then parses it and returns a normalized Document object.
	 * @param xml
	 * @return 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document readFromString(String xml, boolean sanitizeFirst) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new InputSource(new StringReader(sanitizeFirst ? sanitizeXmlChars(xml) : xml)));
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	/**
	 * Finds the FIRST property specified on an element and returns its value. For example,
	 * {@code getChildWithAttributes(element, "property", <Map of "attr1"->"attr1">)}
	 * results in the element containing "5" when executed on the following structure:<br>
	 * <pre>{@code
	 * <element>
	 * <property attr1="attr1">5</property>
	 * <property attr2="attr2">10</property>
	 * </element>
	 * }</pre>
	 * 
	 * @param element The element to find the child on
	 * @param childTagName
	 * @return
	 */
	public static Element getChildWithAttributes(Element element, String childTagName, Map<String, String> attrs) {
		NodeList children = element.getElementsByTagName(childTagName);
		int length = children.getLength();
		if (length == 0) {
			return null;
		}
		
		if (attrs != null && attrs.size() > 0) {
			Set<Entry<String, String>> entries = attrs.entrySet();
			for (int x = 0; x < children.getLength(); x++) {
				boolean match = true;
				Element child = (Element) children.item(x);
				for (Entry<String, String> entry : entries) {
					String attr = entry.getValue();
					String childAttr = child.getAttribute(entry.getKey());
					match = match && (attr.equals(childAttr));
					if (!match) {
						break;
					}
				}
				if (match) {
					return child;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns a map containing key/value pairs of the properties on {@code element},
	 * with the non-null elements from the {@code props} array as the keys.
	 * @param element
	 * @param props
	 * @return
	 */
	public static Map<String, String> getChildProperties(Element element, String[] props) {
		Map<String, String> map = new HashMap<String, String>();
		for (String prop : props) {
			if (prop != null) {
				map.put(prop, getChildProperty(element, prop));
			}
		}
		return map;
	}
	
	/**
	 * Finds the FIRST property specified on an element and returns its value. For example, <code>getChildProperty(element, "property")</code>
	 * results in "5" when executed on the following structure:<br>
	 * <pre>{@code
	 * <element>
	 * <property>5</property>
	 * </element>
	 * }</pre>
	 * 
	 * Note: this method is a shortcut to <code>getChildPropertyWithAttributes(element, property, null)</code>
	 * 
	 * @param element The element to find the child property on
	 * @param property The name of the property to find
	 * @return The property if it exists. Null otherwise.
	 */
	public static String getChildProperty(Element element, String property) {
		return getChildPropertyWithAttributes(element, property, null);
	}
	
	/**
	 * Finds the FIRST property specified on an element and returns its value. For example, {@code getChildProperty(element, "property", <Map of "attr2"->"attr2">)}
	 * results in "10" when executed on the following structure:<br>
	 * <pre>{@code
	 * <element>
	 * <property attr1="attr1">5</property>
	 * <property attr2="attr2">10</property>
	 * </element>
	 * }</pre>
	 * 
	 * @param element The element to find the child property on
	 * @param property The name of the property to find
	 * @return The property if it exists. Null otherwise.
	 */
	public static String getChildPropertyWithAttributes(Element element,
			String property, Map<String, String> attrs) {
		NodeList children = element.getElementsByTagName(property);
		int length = children.getLength();
		if (length == 0) {
			return null;
		}
		
		if (attrs != null && attrs.size() > 0) {
			Set<Entry<String, String>> entries = attrs.entrySet();
			for (int x = 0; x < children.getLength(); x++) {
				boolean match = true;
				Element child = (Element) children.item(x);
				for (Entry<String, String> entry : entries) {
					String attr = entry.getValue();
					String childAttr = child.getAttribute(entry.getKey());
					match = match && (attr.equals(childAttr));
					if (!match) { // must be a complete match, so we can stop
						break;
					}
				}
				if (match) {
					return child.getTextContent().trim();
				}
			}
			return null;
		} else {
			return children.item(0).getTextContent().trim();
		}
	}
}