package edu.ku.cete.test.kids;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.ksde.kids.KidsSettings;
import edu.ku.cete.ksde.kids.client.AuthenticationSoapHeader;
import edu.ku.cete.ksde.kids.client.KIDSWebService;
import edu.ku.cete.ksde.kids.client.KIDSWebServiceSoap;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.WebServiceConsumerService;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.AartResource;

public class KSDEWebServiceTest extends BaseTest {
	private static final QName SERVICE_NAME = new QName("http://tempuri.org/kids_data/KIDS_WebService", "KIDS_WebService");
	
    /**
     * wsConnectionUsername.
     */
    @Value("${wsConnectionUsername}")
    private String wsConnectionUsername;
    /**
     * wsConnectionPassword.
     */
    @Autowired
    private KidsSettings kidsSettings;
    
    @Value("${wsAdminUserName}")
    private String wsAdminUserName;
    
	@Autowired
	private UserService userService;
    
	@Autowired
	private WebServiceConsumerService wbcService;
	
	@Test
	public void testGetKidsByDate() throws Exception {
		User user = userService.getByUserName(wsAdminUserName);
		UserDetailImpl userDetailImpl =new UserDetailImpl(user);
        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetailImpl, userDetailImpl.getPassword(), userDetailImpl.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
		ModelAndView mv = new ModelAndView(AartResource.WEB_SERVICE_FOLDER
				+ java.io.File.separator + AartResource.IMMEDIATE_UPLOAD);
		
		mv = wbcService.invokeWebServices(mv);
	}
	
	@Test
	public void testGetKidsByDateOld() throws Exception {
		//System.setProperty("org.apache.cxf.stax.allowInsecureParser", "true"); 
		System.out.println("Invoking getKidsByDate...");
		KIDSWebServiceSoap port = getKidsWebService();  
		AuthenticationSoapHeader authHeader = new AuthenticationSoapHeader();
		authHeader.setUsername(wsConnectionUsername);
		authHeader.setPassword(kidsSettings.getEncryptedPassword());
		
        java.lang.String _getKidsByDate_strFromDateVal = "";
        javax.xml.ws.Holder<java.lang.String> _getKidsByDate_strFromDate = new javax.xml.ws.Holder<java.lang.String>(_getKidsByDate_strFromDateVal);
        java.lang.String _getKidsByDate_strToDate = "";
        java.lang.String _getKidsByDate_strSchoolYear = "";
        java.lang.Boolean _getKidsByDate_bolRequestCompleteVal = true;
        javax.xml.ws.Holder<java.lang.Boolean> _getKidsByDate_bolRequestComplete = new javax.xml.ws.Holder<java.lang.Boolean>(_getKidsByDate_bolRequestCompleteVal);
        javax.xml.ws.Holder<java.lang.String> _getKidsByDate_getKidsByDateResult = new javax.xml.ws.Holder<java.lang.String>("");
        port.getKidsByDate(_getKidsByDate_strFromDate, _getKidsByDate_strToDate, _getKidsByDate_strSchoolYear, _getKidsByDate_bolRequestComplete, authHeader, _getKidsByDate_getKidsByDateResult);

        System.out.println("getKidsByDate._getKidsByDate_strFromDate=" + _getKidsByDate_strFromDate.value);
        System.out.println("getKidsByDate._getKidsByDate_bolRequestComplete=" + _getKidsByDate_bolRequestComplete.value);
        System.out.println("getKidsByDate._getKidsByDate_getKidsByDateResult=" + _getKidsByDate_getKidsByDateResult.value);

	}

	private KIDSWebServiceSoap getKidsWebService() throws MalformedURLException {
		URL wsdlURL = new URL("http://localhost:9080/KidsMockService/services/KIDS_WebServiceSoap12?wsdl");
      
        KIDSWebService ss = new KIDSWebService(wsdlURL, SERVICE_NAME);
        KIDSWebServiceSoap port = ss.getKIDSWebServiceSoap12();
        Client client =  ClientProxy.getClient(port);   
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());
		return port;
	}

}
