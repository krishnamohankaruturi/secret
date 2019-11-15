package edu.ku.cete.security;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.UserAuditService;

/**
 * @author nicholas
 */
public class LogoutFilterWrapper implements Filter {

    /** Logger for this class and subclasses. */
    private final Log logger = LogFactory.getLog(getClass());

    /** Logout filter. */
    private LogoutFilter filter;

    /** UserAuditService holder. */
    private UserAuditService userAuditService;

    /** Logout Sucessful urls by client type. */
    private Map<String, String> logoutSuccessfulUrls;

    /**
     * @return the logoutSuccessfulUrls
     */
    public final Map<String, String> getLogoutSuccessfulUrls() {
        return logoutSuccessfulUrls;
    }

    /**
     * @param newLogoutSuccessfulUrls the logoutSuccessfulUrls to set
     */
    public final void setLogoutSuccessfulUrls(final Map<String, String> newLogoutSuccessfulUrls) {
        this.logoutSuccessfulUrls = newLogoutSuccessfulUrls;
    }

    /**
     * Initialize.
     */
    @PostConstruct
    protected final void initialize() {
        logger.debug("In the filter init");

        final SecurityContextLogoutHandler context = new SecurityContextLogoutHandler();
        context.setInvalidateHttpSession(true);
        this.filter = new LogoutFilter(new CustomLogoutSuccessHandler(getUserAuditService()), new LogoutHandler[] {context});
    }

    /**
     * @param inFilterConfig {@link FilterConfig}
     * @throws ServletException {@link ServletException}
     */
    public final void init(final FilterConfig inFilterConfig) throws ServletException {

        this.filter.init(inFilterConfig);
    }

    /**
     * Destroy.
     */
    public final void destroy() {
        this.filter.destroy();
    }

    /**
     * @param inRequest {@link ServletRequest}
     * @param inResponse {@link ServletResponse}
     * @param inChain {@link FilterChain}
     * @throws IOException {@link IOException}
     * @throws ServletException {@link ServletException}
     */
    public final void doFilter(final ServletRequest inRequest, final ServletResponse inResponse, final FilterChain inChain)
            throws IOException, ServletException {

        this.filter.doFilter(inRequest, inResponse, inChain);
    }

    /**
     * @return the userAuditService
     */
    public final UserAuditService getUserAuditService() {
        return userAuditService;
    }

    /**
     * @param newUserAuditService the userAuditService to set
     */
    @Autowired
    public final void setUserAuditService(final UserAuditService newUserAuditService) {
        this.userAuditService = newUserAuditService;
    }

    /**
     * Success Handler.
     */
    private class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

        /** Logger for this class and subclasses. */
        private final Log logger = LogFactory.getLog(getClass());

        /** UserAuditService holder. */
        private UserAuditService userAuditService;

        /** Redirect strategy. */
        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        /**
         * Get the Service wired.
         * @param newUserAuditService {@link UserAuditService}
         */
        public CustomLogoutSuccessHandler(final UserAuditService newUserAuditService) {
            this.userAuditService = newUserAuditService;
        }

        @Override
        public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
                final Authentication authentication) throws IOException, ServletException {
        	UserDetailImpl principal = null;
        	
            //default internal logout url
            String logoutSuccessfulUrl = LogoutFilterWrapper.this.logoutSuccessfulUrls.get("internal");
            if (authentication == null) {
            	redirectStrategy.sendRedirect(request, response, logoutSuccessfulUrl);
            	return;
            }

            boolean isSSO = false;
            try {
	            principal = (UserDetailImpl) authentication.getPrincipal();
	            if(principal != null) {
	                logger.debug("Record logout for userid: " + principal.getUserId()+", username "+principal.getUsername());
	                isSSO = principal.isSSO();
		            userAuditService.logout(principal.getUserId());
	            }
            } catch (Exception e) {
                logger.error("Could not save logout to database. info", e);
            }

            if (isSSO) {
            	logoutSuccessfulUrl = LogoutFilterWrapper.this.logoutSuccessfulUrls.get("sso");
            }
            redirectStrategy.sendRedirect(request, response, logoutSuccessfulUrl);
        }
    }
}
