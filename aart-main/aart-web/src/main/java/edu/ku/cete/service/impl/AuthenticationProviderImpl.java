package edu.ku.cete.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * This class is used to authenticate a user wishing to gain access to the AART system.
 * @author neil.howerton
 *
 */
public class AuthenticationProviderImpl extends DaoAuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

    /**
     * This is the method that is called when a user tries to log into the AART system.
     * @param auth {@link Authentication}
     * @return Authentication
     */
    @Override
    public final Authentication authenticate(final Authentication auth) throws AuthenticationException {
        logger.trace("Entering the authenticate() method of the AuthenticationProviderImpl");
        if (auth.getCredentials().toString().equals(StringUtils.EMPTY)) {
            logger.debug("User entered password that is a blank string. Throwing BadCredentialsException.");
            throw new BadCredentialsException("The username or password you entered is incorrect");
        } else {
            logger.debug("Calling super.authenticate with parameter auth: {}", auth);
            Authentication authentication = auth;
            if(auth.getClass().equals(PreAuthenticatedAuthenticationToken.class)){
            	authentication = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials());
            }
            return super.authenticate(authentication);
        }
    }
    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class) || authentication.equals(PreAuthenticatedAuthenticationToken.class);
 
    }
}
