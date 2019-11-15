package edu.ku.cete.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * 
 * @author akommineni
 * 
 */
public class PreAuthenticatedFilter extends AbstractPreAuthenticatedProcessingFilter {
	private static final Logger logger = LoggerFactory
			.getLogger(PreAuthenticatedFilter.class);

	private String montecarloPrincipalRequestHeader = "mcusername";
	private String montecarloCredentialsRequestHeader = "mcpassword";
	
	private String questarPrincipalRequestHeader = "questarusername";
	private String questarCredentialsRequestHeader = "questarpassword";

	private String ksdePrincipalRequestHeader = "ksdeusername";
	private String ksdeCredentialsRequestHeader = "ksdepassword";
	
	private String sifPrincipalRequestHeader = "sifusername";
	private String sifCredentialsRequestHeader = "sifpassword";

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = ((HttpServletRequest) request);
		
		// Need to do re-authentication in case if principal changes 
		String principal = (String) getPreAuthenticatedPrincipal(req);
		super.setCheckForPrincipalChanges(StringUtils.isNotBlank(principal));
		
		if (logger.isDebugEnabled()) {
			logger.debug("all headers from request");
			@SuppressWarnings("rawtypes")
			Enumeration headerNames = req.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				logger.debug(headerName + ": " + req.getHeader(headerName));
			}
		}
		super.doFilter(request, response, chain);
	}

	/**
	 * Read and returns the header named by {@code lcsusername} from the
	 * request.
	 * 
	 */
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String code = getControllerPathInServlet(request);
		String requestHeader = "";
		if (code.equals("montecarlo")) {
			requestHeader = montecarloPrincipalRequestHeader;
		} else if (code.equals("questar")) {
			requestHeader = questarPrincipalRequestHeader;
		} else if (code.equals("ksde")) {
			requestHeader = ksdePrincipalRequestHeader;
		} else if(code.equals("sif")){
			requestHeader = sifPrincipalRequestHeader;
		}
		String principal = request.getHeader(requestHeader);
		return principal;
	}

	/**
	 * Credentials aren't usually applicable, but if a {@code lcspassword} is
	 * set, this will be read and used as the credentials value.
	 */
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		String code = getControllerPathInServlet(request);
		String requestHeader = "";
		if (code.equals("montecarlo")) {
			requestHeader = montecarloCredentialsRequestHeader;
		} else if (code.equals("questar")) {
			requestHeader = questarCredentialsRequestHeader;
		} else if (code.equals("ksde")) {
			requestHeader = ksdeCredentialsRequestHeader;
		}  else if(code.equals("sif")){
			requestHeader = sifCredentialsRequestHeader;
		}
		String credentials = request.getHeader(requestHeader);
		return credentials;
	}
	
	/**
	 * Custom method used to figure out which set of headers to check for during authentication. Example:
	 * 
	 * A request going to /AART/montecarlo/getstudentsresponsesinfo.htm will return "montecarlo".
	 * A request going to /AART/questar/getResponses.htm will return "questar".
	 * A request going to /AART/ksde/getKSDEReturnExtract.htm will return "ksde".
	 * A request going to /AART/sif/uploadStudent.htm will return "sif".
	 * 
	 * @returns "montecarlo" or "questar" or "ksde" or "sif" based on where the request is going.
	 */
	private String getControllerPathInServlet(HttpServletRequest request) {
		String uri = request.getServletPath();
		Pattern pattern = Pattern.compile("/([^/]*)/.*");
		Matcher matcher = pattern.matcher(uri);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
}