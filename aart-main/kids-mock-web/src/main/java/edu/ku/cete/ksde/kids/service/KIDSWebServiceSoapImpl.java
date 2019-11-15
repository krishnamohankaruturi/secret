package edu.ku.cete.ksde.kids.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@javax.jws.WebService(
        serviceName = "KIDS_WebService",
        portName = "KIDS_WebServiceSoap12",
        targetNamespace = "http://tempuri.org/kids_data/KIDS_WebService",
        wsdlLocation = "http://localhost:8080/KidsMockService/services/KIDS_WebServiceSoap12?wsdl",
        endpointInterface = "edu.ku.cete.ksde.kids.service.KIDSWebServiceSoap")
public class KIDSWebServiceSoapImpl implements KIDSWebServiceSoap {
	private static final Logger LOG = LoggerFactory.getLogger(KIDSWebServiceSoapImpl.class);
	
	public KIDSWebServiceSoapImpl() {
		System.setProperty("org.apache.cxf.stax.allowInsecureParser", "true"); 
	}

	public String getOTLComplete(int subject, String strSchoolYear, AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getOTLComplete");
		LOG.debug(""+subject);
		LOG.debug(strSchoolYear);
        try {
            java.lang.String _return = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_OTL_Complete.xml");
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public int countKidsByDate(String strFromDate, String strToDate, String strSchoolYear,AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation countKidsByDate");
        LOG.debug(strFromDate);
        LOG.debug(strToDate);
        LOG.debug(strSchoolYear);
        try {
            int _return = 1485771146;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public String getTASCByTeacherID(String teacherID, String strSchoolYear,AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getTASCByTeacherID");
        LOG.debug(teacherID);
        LOG.debug(strSchoolYear);
        try {
        	String tascXmlString = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_TASC_by_teacherId.xml");
            return tascXmlString;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public String getTASCBySSID(String ssid, String strSchoolYear,AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getTASCBySSID");
        LOG.debug(ssid);
        LOG.debug(strSchoolYear);
        try {
        	String tascXmlString = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_TASC_by_ssid.xml");
            return tascXmlString;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public void getTASCByDate(Holder<String> strFromDate, String strToDate,
			String strSchoolYear, Holder<Boolean> bolRequestComplete,AuthenticationSoapHeader authenticationSoapHeader,
			Holder<String> getTASCByDateResult) {
		LOG.info("Executing operation getTASCBySSID");
        LOG.debug(strFromDate.value);
        LOG.debug(strToDate);
        LOG.debug(strSchoolYear);
        LOG.debug(""+bolRequestComplete.value);
        try {
        	String tascXmlString = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_TASC_by_date.xml");
            getTASCByDateResult.value = tascXmlString;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public String getmessage(AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getmessage");
        try {
            java.lang.String _return = "_return-1551770198";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}
	
	public String getSTCOByTeacherID(String teacherID, String strSchoolYear,AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getSTCOByTeacherID");
		LOG.debug(teacherID);
		LOG.debug(strSchoolYear);
        try {
            java.lang.String _return = "_return-769968907";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public void getSTCOByDate(Holder<String> strFromDate, String strToDate,
			String strSchoolYear, Holder<Boolean> bolRequestComplete,AuthenticationSoapHeader authenticationSoapHeader,
			Holder<String> getSTCOByDateResult) {
		LOG.info("Executing operation getSTCOByDate");
        LOG.debug(strFromDate.value);
        LOG.debug(strToDate);
        LOG.debug(strSchoolYear);
        LOG.debug(""+bolRequestComplete.value);
        try {
        	String stcoXmlString = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_STCO_by_date.xml");
            java.lang.String getSTCOByDateResultValue = stcoXmlString;
            getSTCOByDateResult.value = getSTCOByDateResultValue;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public String getSTCOBySSID(String ssid, String strSchoolYear,AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getSTCOBySSID");
		LOG.debug(ssid);
		LOG.debug(strSchoolYear);
        try {
            java.lang.String _return = "_return442681464";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public String getBuildings(AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getBuildings");
        try {
        	String buildingsXmlString = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_Buildings.xml");
            java.lang.String _return = buildingsXmlString;
            return _return;
        } catch (java.lang.Exception ex) {
            throw new RuntimeException(ex);
        }
	}

	public String getOrganizations(AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getOrganizations");
        try {
            java.lang.String _return = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_Organizations.xml");
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	public String getKidsBySSID(String ssid, String strSchoolYear,AuthenticationSoapHeader authenticationSoapHeader) {
		LOG.info("Executing operation getKidsBySSID:"+ssid+","+strSchoolYear);
        try {
            java.lang.String _return = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_kids_by_SSID.xml");
            LOG.info("Executing operation getKidsBySSID");
            return _return;
        } catch (java.lang.Exception ex) {
            throw new RuntimeException(ex);
        }
	}

	public void getKidsByDate(Holder<String> strFromDate, String strToDate,
			String strSchoolYear, Holder<Boolean> bolRequestComplete,AuthenticationSoapHeader authenticationSoapHeader,
			Holder<String> getKidsByDateResult) {
		LOG.info("Executing operation getKidsByDate");
		LOG.debug(strFromDate.value);
		LOG.debug(strToDate);
		LOG.debug(strSchoolYear);
		LOG.debug(""+bolRequestComplete.value);
        try {
        	java.lang.String getKidsByDateResultValue = readFileAsString(this.getClass().getClassLoader(), "edu/ku/cete/ksde/kids/data/get_kids_by_date.xml");
            getKidsByDateResult.value = getKidsByDateResultValue;
        } catch (java.lang.Exception ex) {
            throw new RuntimeException(ex);
        }
        LOG.info("Executing operation getKidsByDate");
	}
	
	private String readFileAsString(ClassLoader classLoader, String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filePath)));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
