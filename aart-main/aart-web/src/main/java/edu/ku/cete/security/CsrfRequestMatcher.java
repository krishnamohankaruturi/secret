package edu.ku.cete.security;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CsrfRequestMatcher implements RequestMatcher {

	private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
	// Disable CSFR protection on the following urls:
	private AntPathRequestMatcher[] requestMatchers = null;
	
	public CsrfRequestMatcher(String unprotectedUrls[]) {
		if(unprotectedUrls != null) {
			requestMatchers = new AntPathRequestMatcher[unprotectedUrls.length];
			for (int i=0; i< unprotectedUrls.length; i++) {
				requestMatchers[i] = new AntPathRequestMatcher(unprotectedUrls[i]);
			}
		}
	}

	public boolean matches(HttpServletRequest request) {
		// Skip allowed methods
		if (allowedMethods.matcher(request.getMethod()).matches()) {
			return false;
		}

		if(requestMatchers != null) {
			// If the request match one url the CSFR protection will be disabled
			for (AntPathRequestMatcher rm : requestMatchers) {
				if (rm.matches(request)) {
					return false;
				}
			}
		}
		return true;
	}

}
