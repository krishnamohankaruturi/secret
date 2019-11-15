package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.UserService;

/**
 *
 * @author neil.howerton
 * This over rides the userDetailsService of Spring. The methods are invoked by Spring
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	@Value("${password.excludedUserList}")
	private String excludedUserList;

    @Autowired
    private UserService userService;


    /**
     * @param userId {@link String}
     * @return {@link UserDetails}
     * 
     * TODO Why load authorities on every page...? is there a way to avoid it ?
     * This is getting invoked by Spring
     */
    @Override
    public final UserDetails loadUserByUsername(String userName) {
        //Get the user
        User user = userService.getByUserName((userName));

        List<String> userArrayList = new ArrayList<String>(); 
		String[] userList = excludedUserList.split(",");
		Collections.addAll(userArrayList, userList);
		if(userArrayList.contains(userName)){
   			user.setStatusCode("Active");
		}	
        
        UserDetails userDetails = new UserDetailImpl(user);
        return userDetails;
    }

}
