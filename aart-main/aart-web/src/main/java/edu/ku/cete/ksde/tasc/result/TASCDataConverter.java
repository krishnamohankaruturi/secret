/**
 * 
 */
package edu.ku.cete.ksde.tasc.result;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import edu.ku.cete.domain.enrollment.TASCRecord;

/**
 * @author ktaduru_sta
 *
 */
public class TASCDataConverter implements Converter {

	
	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return type.equals(TASCData.class);
	}
	
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		TASCData tascData = new TASCData();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("TASC_Record".equals(reader.getNodeName())) {
				TASCRecord scrsRecord = (TASCRecord) (context.convertAnother(
						tascData, TASCRecord.class));
				tascData.addRecord(scrsRecord);
			}
			reader.moveUp();
		}
		return tascData;
	}

}
