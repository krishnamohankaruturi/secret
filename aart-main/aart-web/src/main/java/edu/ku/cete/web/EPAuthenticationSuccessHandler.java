package edu.ku.cete.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

public class EPAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	public EPAuthenticationSuccessHandler(){
		
	}
    public EPAuthenticationSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            if (savedRequest != null) {
            	if(savedRequest.getRedirectUrl().contains("configuration.htm?view=externalLink")){
            		session.setAttribute("externalLink", true);
            		session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
            		getRedirectStrategy().sendRedirect(request, response, "/logIn.htm");
            	} else if (savedRequest.getRedirectUrl().contains("dashboardAccess.htm")){
            		session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
            		getRedirectStrategy().sendRedirect(request, response, "/templates/dashboardAccess.htm");
            	} else {
            		session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
            		getRedirectStrategy().sendRedirect(request, response, "/logIn.htm");
            	}
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
