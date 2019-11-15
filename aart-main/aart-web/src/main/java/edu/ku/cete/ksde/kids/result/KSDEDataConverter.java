package edu.ku.cete.ksde.kids.result;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class KSDEDataConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(KSDEData.class);
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) { // TODO Auto-generated method stub

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		KSDEData ksdeData = new KSDEData();
		long seqNo = 1;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("KIDS_Record".equals(reader.getNodeName())) {
				KSDERecord ksdeRecord = (KSDERecord) (context.convertAnother(
						ksdeData, KSDERecord.class));
				ksdeRecord.setSeqNo(seqNo++);
				ksdeData.addKid(ksdeRecord);
			}
			reader.moveUp();
		}
		return ksdeData;
	}

}
