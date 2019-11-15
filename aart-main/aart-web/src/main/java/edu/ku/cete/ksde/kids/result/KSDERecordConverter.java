package edu.ku.cete.ksde.kids.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanWrapperImpl;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.util.ParsingConstants;

public class KSDERecordConverter implements Converter {

	private Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();

	public final Map<String, FieldSpecification> getFieldSpecificationMap() {
		return fieldSpecificationMap;
	}

	public final void setFieldSpecificationMap(Map<String, FieldSpecification> fieldSpecificationMp) {
		if (MapUtils.isNotEmpty(fieldSpecificationMp)) {
			fieldSpecificationMap = fieldSpecificationMp;
		}
	}

	@Override
	public final boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(KSDERecord.class);
	}

	@Override
	public final void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		KSDERecord kid = (KSDERecord) value;
		writer.startNode("KIDS_Record");
		writer.startNode("State_Student_Identifier");
		writer.setValue(kid.getStateStudentIdentifier() + ParsingConstants.BLANK);
		writer.endNode();
	}

	/**
	 * @param reader
	 *            {@link HierarchicalStreamReader}
	 * @param kid
	 *            {@link KSDERecord}
	 */

	private void readAll(HierarchicalStreamReader reader, KSDERecord kid) {
		String nodeName = reader.getNodeName();
		String nodeValue = reader.getValue();
		FieldSpecification fieldSpecification = fieldSpecificationMap.get(nodeName.toLowerCase());
		if (fieldSpecification != null) {
			BeanWrapperImpl beanImpl = new BeanWrapperImpl(kid);
			beanImpl.setPropertyValue(fieldSpecification.getFieldName(),
					(nodeValue == null || nodeValue.isEmpty()) ? null : nodeValue);
		}
	}

	@Override
	public final Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		KSDERecord kid = new KSDERecord();
		long i = 0;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			readAll(reader, kid);
			reader.moveUp();
			i++;
		}
		return kid;
	}

}
