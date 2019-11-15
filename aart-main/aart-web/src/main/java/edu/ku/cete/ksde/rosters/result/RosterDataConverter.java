package edu.ku.cete.ksde.rosters.result;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;

public class RosterDataConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(RosterData.class);
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) { // TODO Auto-generated method stub

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		RosterData scrsData = new RosterData();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("STCO_Record".equals(reader.getNodeName())) {
				WebServiceRosterRecord scrsRecord = (WebServiceRosterRecord) (context.convertAnother(
						scrsData, WebServiceRosterRecord.class));
				scrsData.addRoster(scrsRecord);
			}
			reader.moveUp();
		}
		return scrsData;
	}
}
