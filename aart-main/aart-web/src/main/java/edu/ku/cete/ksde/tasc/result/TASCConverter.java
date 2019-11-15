/**
 * 
 */
package edu.ku.cete.ksde.tasc.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import edu.ku.cete.domain.enrollment.TASCRecord;
import edu.ku.cete.domain.enrollment.TASCRosterRecord;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author ktaduru_sta
 *
 */
public class TASCConverter implements Converter {
	private Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();	
	
	public Map<String, FieldSpecification> getFieldSpecificationMap() {
		return fieldSpecificationMap;
	}

	public void setFieldSpecificationMap(Map<String, FieldSpecification> fieldSpecificationMap) {
		if (MapUtils.isNotEmpty(fieldSpecificationMap)) {
			this.fieldSpecificationMap = fieldSpecificationMap;
		}
		
	}

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return type.equals(TASCRecord.class);
	}
	
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		TASCRosterRecord scrsRecord = (TASCRosterRecord) source;
		writer.startNode("TASC_Record");
		writer.startNode("State_Student_Identifier");
		writer.setValue(scrsRecord.getTascStateStudentIdentifier()
				+ ParsingConstants.BLANK);
		writer.endNode();

	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		TASCRecord scrsRecord = new TASCRecord();
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			readAll(reader, scrsRecord);
			reader.moveUp();
		}
		return scrsRecord;
	}

	private void readAll(HierarchicalStreamReader reader, TASCRecord scrsRecord) {
		String nodeName = reader.getNodeName();
		String nodeValue = reader.getValue();
		FieldSpecification fieldSpecification = fieldSpecificationMap.get(nodeName.toLowerCase());
		if (fieldSpecification != null) {
			fieldSpecification.validate(scrsRecord, nodeValue);
		}
	}
	
}
