package edu.ku.cete.ksde.kids.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author m802r921
 *
 */
public class KidConverter implements Converter {
	/**
	 * field specifications for scrs.
	 */
	private Map<String, FieldSpecification> fieldSpecificationMap
	= new HashMap<String, FieldSpecification>();
	/**
	 * Kansas assessment codes.
	 */
	private Map<String, Category> kansasAssessmentInputNames = new HashMap<String, Category>();
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(
    		KidConverter.class);
    /**
	 * @return the fieldSpecificationMap
	 */
	public final Map<String, FieldSpecification> getFieldSpecificationMap() {
		return fieldSpecificationMap;
	}
	/**
	 * @param fieldSpecificationMp the fieldSpecificationMap to set
	 */
	public final void setFieldSpecificationMap(
			Map<String, FieldSpecification> fieldSpecificationMp) {
		//Do not set empty ones.
		if (MapUtils.isNotEmpty(fieldSpecificationMp)) {
			fieldSpecificationMap = fieldSpecificationMp;
		}
	}

	/**
	 * @return the kansasAssessmentInputNames
	 */
	public final Map<String, Category> getKansasAssessmentInputNames() {
		return kansasAssessmentInputNames;
	}
	/**
	 * @param kansasAssessInputNames the kansasAssessmentInputNames to set
	 */
	public final void setKansasAssessmentInputNames(Map<String, Category> kansasAssessInputNames) {
		this.kansasAssessmentInputNames = kansasAssessInputNames;
	}

	@Override
	public final boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(KidRecord.class);
	}

	@Override
	public final void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		KidRecord kid = (KidRecord) value;
		// writer.startNode("KIDS_Data");
		writer.startNode("KIDS_Record");
		writer.startNode("State_Student_Identifier");
		writer.setValue(kid.getStateStudentIdentifier()
				+ ParsingConstants.BLANK);
		writer.endNode();
	}
	/**
	 * @param reader {@link HierarchicalStreamReader}
	 * @param kid {@link KidRecord}
	 */
	
	private void readAll(HierarchicalStreamReader reader, KidRecord kid) {
		String nodeName = reader.getNodeName();
		String nodeValue = reader.getValue();
		FieldSpecification fieldSpecification = fieldSpecificationMap.get(nodeName.toLowerCase());
		if (fieldSpecification != null) {
			fieldSpecification.validate(kid, nodeValue);
		} else {
			//NOTE check if contains will work for string as string is immutable.
			//NOTE we are adding only the recognized assessment codes.
			if (StringUtils.hasText(nodeName)
					&& StringUtils.hasText(nodeValue)
					&& kansasAssessmentInputNames.containsKey(nodeName)) {
				kid.addAssessmentInputName(kansasAssessmentInputNames.get(nodeName));
				//TODO node value is test mode and needs to be addressed later.
			}
		}
	}

	@Override
	public final Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		KidRecord kid = new KidRecord();
		//reader.moveDown();
		int i = 0;
		//TODO should this be hard coded ? Should this keep changing ..?
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			readAll(reader, kid);
			reader.moveUp();
			i++;
		}
		return kid;
	}

}