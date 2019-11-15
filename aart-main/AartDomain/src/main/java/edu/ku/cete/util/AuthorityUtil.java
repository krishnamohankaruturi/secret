/**
 * 
 */
package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import edu.ku.cete.domain.Authorities;

/**
 * @author neil.howerton
 *
 */
public class AuthorityUtil {

    public static final List<GrantedAuthority> mapAuthorities(List<Authorities> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        if (authorities != null && authorities.size() > 0) {
            for (Authorities authority : authorities) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }
        }
        return grantedAuthorities;
    }
}
