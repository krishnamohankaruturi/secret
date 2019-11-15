package edu.ku.cete.security;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.Collection;

import org.mitre.openid.connect.model.UserInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
/**
 * New implementation of the token to allow the principal object (UserDetailsImpl)
 * to be used instead of the Map used in OIDCAuthenticationToken.
 * 
 * @author craigshatswell_sta
 *
 */
public class AlternateOIDCAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 8675309;

	private final Object principal;
	private final String accessTokenValue; // string representation of the access token
	private final String refreshTokenValue; // string representation of the refresh token
	private transient JWT idToken; // this needs a custom serializer
	private final String issuer; // issuer URL (parsed from the id token)
	private final String sub; // user id (parsed from the id token)

	private final UserInfo userInfo; // user info container

	/**
	 * Constructs AlternateOIDCAuthenticationToken with a full set of authorities, marking this as authenticated.
	 * 
	 * Set to authenticated.
	 * @param subject
	 * @param issuer
	 * @param userInfo
	 * @param authorities
	 * @param idToken
	 * @param accessTokenValue
	 * @param refreshTokenValue
	 * @param principal
	 */
	public AlternateOIDCAuthenticationToken(String subject, String issuer,
			UserInfo userInfo, Collection<? extends GrantedAuthority> authorities,
			JWT idToken, String accessTokenValue, String refreshTokenValue, Object principal) {

		super(authorities);

		this.principal = principal;
		this.userInfo = userInfo;
		this.sub = subject;
		this.issuer = issuer;
		this.idToken = idToken;
		this.accessTokenValue = accessTokenValue;
		this.refreshTokenValue = refreshTokenValue;

		setAuthenticated(true);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return accessTokenValue;
	}

	/**
	 * Get the principal of this object, an immutable map of the subject and issuer.
	 */
	@Override
	public Object getPrincipal() {
		return principal;
	}

	public String getSub() {
		return sub;
	}

	/**
	 * @return the idTokenValue
	 */
	public JWT getIdToken() {
		return idToken;
	}

	/**
	 * @return the accessTokenValue
	 */
	public String getAccessTokenValue() {
		return accessTokenValue;
	}

	/**
	 * @return the refreshTokenValue
	 */
	public String getRefreshTokenValue() {
		return refreshTokenValue;
	}

	/**
	 * @return the issuer
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/*
	 * Custom serialization to handle the JSON object
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		if (idToken == null) {
			out.writeObject(null);
		} else {
			out.writeObject(idToken.serialize());
		}
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, ParseException {
		in.defaultReadObject();
		Object o = in.readObject();
		if (o != null) {
			idToken = JWTParser.parse((String)o);
		}
	}

}
