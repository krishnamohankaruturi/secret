package edu.ku.cete.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mitre.openid.connect.client.NamedAdminAuthoritiesMapper;
import org.mitre.openid.connect.client.OIDCAuthoritiesMapper;
import org.mitre.openid.connect.client.UserInfoFetcher;
import org.mitre.openid.connect.model.PendingOIDCAuthenticationToken;
import org.mitre.openid.connect.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.base.Strings;
import com.nimbusds.jwt.JWT;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.util.CommonConstants;
/**
 * AlternateOIDCAuthenticationProvider provides the correct principal 
 * object for Educator Portal.  It handles creating the UserDetailsImpl object
 * by looking up the user by the username (which is the email).
 * 
 * @author craigshatswell_sta
 *
 */
public class AlternateOIDCAuthenticationProvider implements AuthenticationProvider {
	private static Logger logger = LoggerFactory.getLogger(AlternateOIDCAuthenticationProvider.class);

	private UserInfoFetcher userInfoFetcher = new UserInfoFetcher();

	private OIDCAuthoritiesMapper authoritiesMapper = new NamedAdminAuthoritiesMapper();
	
	@Autowired
	private AppConfigurationService appConfigurationService;
	
	@Autowired
	private APIDashboardErrorService apiDashboardErrorService;
	
	@Autowired
	private UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		if (!supports(authentication.getClass())) {
			return null;
		}

		if (authentication instanceof PendingOIDCAuthenticationToken) {

			PendingOIDCAuthenticationToken token = (PendingOIDCAuthenticationToken) authentication;

			// get the ID Token value out
			JWT idToken = token.getIdToken();

			// load the user info if we can
			UserInfo userInfo = userInfoFetcher.loadUserInfo(token);

			if (userInfo == null) {
				// user info not found -- could be an error, could be fine
			} else {
				// if we found userinfo, double check it
				if (!Strings.isNullOrEmpty(userInfo.getSub()) && !userInfo.getSub().equals(token.getSub())) {
					// the userinfo came back and the user_id fields don't match what was in the id_token
					throw new UsernameNotFoundException("user_id mismatch between id_token and user_info call: " + token.getSub() + " / " + userInfo.getSub());
				}
			}
			User user = null;
			try {
				user = (User) userService.getByUserName(userInfo.getEmail());
			} catch (UsernameNotFoundException e) {
				Map<String, AppConfiguration> appConfigurationMap =  appConfigurationService.selectIdMapByAttributeType(CommonConstants.APP_CONFIGURATION_ATTR_TYPE_FOR_SSO_ERROR_MESSAGES);
        		AppConfiguration userNotFoundMessage = appConfigurationMap.get(CommonConstants.APP_CONFIGURATION_SSO_USER_NOT_FOUND);
        		String formattedUserNotFoundMessage = String.format(userNotFoundMessage.getAttributeValue(), userInfo.getEmail());
        		logger.error(formattedUserNotFoundMessage);
        		
        		APIDashboardError error = apiDashboardErrorService.buildDashboardError(ApiRequestTypeEnum.AUTH, ApiRecordTypeEnum.USER, userInfo.getEmail(), null, null, null, null, formattedUserNotFoundMessage);
        		List<APIDashboardError> errors = new ArrayList<>();
        		errors.add(error);
        		apiDashboardErrorService.insertErrors(errors);
        		
        		throw new UsernameNotFoundException(formattedUserNotFoundMessage);
			}
			//create the UserDetailImpl object as the principal and send to create the token
			UserDetailImpl userDetails = new UserDetailImpl(user);
			userDetails.setIsSSO(true);
			
			//get authorities
			Collection<GrantedAuthority> authorities = user.getAuthorities();
			authorities.addAll(authoritiesMapper.mapAuthorities(idToken, userInfo));
			
			return createAuthenticationToken(token, authorities, userInfo, userDetails);
		}

		return null;
	}
	
	/**
	 * Override this method to get the user details object into the token.
	 * 
	 * @param token
	 * @param authorities
	 * @param userInfo
	 * @param userDetails
	 * @return
	 */
	protected Authentication createAuthenticationToken(PendingOIDCAuthenticationToken token, Collection<? extends GrantedAuthority> authorities, UserInfo userInfo, UserDetails userDetails) {
		return new AlternateOIDCAuthenticationToken(token.getSub(),
				token.getIssuer(),
				userInfo, authorities,
				token.getIdToken(), token.getAccessTokenValue(), token.getRefreshTokenValue(), userDetails);
	}

	/**
	 * @param userInfoFetcher
	 */
	public void setUserInfoFetcher(UserInfoFetcher userInfoFetcher) {
		this.userInfoFetcher = userInfoFetcher;
	}

	/**
	 * @param authoritiesMapper
	 */
	public void setAuthoritiesMapper(OIDCAuthoritiesMapper authoritiesMapper) {
		this.authoritiesMapper = authoritiesMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.AuthenticationProvider#supports
	 * (java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return PendingOIDCAuthenticationToken.class.isAssignableFrom(authentication);
	}
}