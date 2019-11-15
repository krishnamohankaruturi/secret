/**
 * 
 */
package edu.ku.cete.batch.upload;

import java.util.Map;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.InitializingBean;
import org.w3c.dom.Node;

import edu.ku.cete.batch.upload.xml.parser.XmlXpathParser;
import edu.ku.cete.controller.sif.CEDSCodeMapping;
import edu.ku.cete.controller.sif.SifUploadType;

/**
 * @author Rajendra Kumar Cherukuri
 *
 */
public class XmlItemMapper<T> implements ItemMapper<T>, InitializingBean {
	private FieldSetMapper<T> fieldSetMapper;
	private DelimitedLineTokenizer lineTokenizer;

	public LineTokenizer getLineTokenizer() {
		return lineTokenizer;
	}

	public void setLineTokenizer(DelimitedLineTokenizer lineTokenizer) {
		this.lineTokenizer = lineTokenizer;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public FieldSetMapper<T> getFieldSetMapper() {
		return fieldSetMapper;
	}

	public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
		this.fieldSetMapper = fieldSetMapper;
	}

	@Override
	public T mapItem(Map<String, String> mapping, Node item, int itemNumber, XmlXpathParser xpathParser)
			throws Exception {

		String[] keys = mapping.keySet().toArray(new String[mapping.keySet().size() + 1]);
		keys[keys.length - 1] = "linenumber"; // Add last item as line number.
		lineTokenizer.setNames(keys);

		StringBuffer values = new StringBuffer();
		for (int i = 0; i < keys.length; i++) {
			if (keys[i].equals("linenumber")) {
				values.append(itemNumber);
			} else {
				// cleanse any UTI characters
				String cleanXPath = mapping.get(keys[i]).replaceAll("\\p{C}", "");
				String value = xpathParser.getValue(cleanXPath, item);
				values.append(value);
				values.append(",");
			}
		}
		FieldSet fieldSet = lineTokenizer.tokenize(values.toString());
		return fieldSetMapper.mapFieldSet(fieldSet);
	}

}
