package edu.ku.cete.ksde.kids.result;

import javax.xml.ws.Holder;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.ku.cete.ksde.kids.client.KIDSWebServiceSoap;


public class KidConverterTest {
	
	public static final void testWebservice() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(KIDSWebServiceSoap.class);
		factory.setAddress("http://localhost:8090/AARTService/services/KIDS_WebServiceSoap12");

		KIDSWebServiceSoap client = (KIDSWebServiceSoap) factory.create();

		// String buildings = client.getBuildings();

		Holder<String> strFromDate = null;
		String strToDate = null;
		String strSchoolYear = null;
		Holder<Boolean> bolRequestComplete = new Holder<Boolean>(true);
		Holder<String> getSCRSByDateResult = new Holder<String>("");

		// client.getSCRSByDate(strFromDate, strToDate, strSchoolYear,
		// bolRequestComplete, getSCRSByDateResult);

		
		Holder<String> getKidsByDateResult = new Holder<String>("");
		//TODO set the user name and password from the properties
		client.getKidsByDate(strFromDate, strToDate, strSchoolYear,
				bolRequestComplete, null, 
				getKidsByDateResult);

		String result = getKidsByDateResult.value;
		
		result = "<KIDS_Data>" +
				"<KIDS_Record>" +
				"<Create_Date>4/18/2012 9:57:16 PM</Create_Date>" +
				"<Record_Common_ID>74755086</Record_Common_ID> <Record_Type>TEST</Record_Type>" +
				"<State_Student_Identifier>1111111111</State_Student_Identifier>" +
				"<AYP_QPA_Bldg_No>1111</AYP_QPA_Bldg_No>" +
				"</KIDS_Record>" +
				"</KIDS_Data>";
		
		//TODO remove
		//result = result.replaceAll("<KIDS_Data>", "");
		//result = result.replaceAll("</KIDS_Data>", "");
		
				
		XStream xStream = new XStream(new DomDriver());
        xStream.alias("KIDS_Record", KidRecord.class);
        xStream.alias("KIDS_Data", KidsData.class);
        xStream.registerConverter(new KidConverter());
        xStream.registerConverter(new KidsDataConverter());
        KidsData kidsData = (KidsData) xStream.fromXML(result);
        
        
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testWebservice();
	}

}
