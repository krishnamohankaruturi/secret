package edu.ku.cete.ksde.kids.result;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class KidsDataConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(KidsData.class);
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) { // TODO Auto-generated method stub

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		KidsData kidsData = new KidsData();
		long seqNo = 1;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("KIDS_Record".equals(reader.getNodeName())) {
				KidRecord kidRecord = (KidRecord) (context.convertAnother(
						kidsData, KidRecord.class));
				kidRecord.setSeqNo(seqNo++);
				kidsData.addKid(kidRecord);
			}
			reader.moveUp();
		}
		return kidsData;
	}

}
