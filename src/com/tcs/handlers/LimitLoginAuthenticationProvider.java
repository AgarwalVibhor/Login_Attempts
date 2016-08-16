package com.tcs.handlers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tcs.dao.UserDetailsDaoInterface;
import com.tcs.model.UserAttempts;

public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Autowired
	private UserDetailsDaoInterface dao;
	
	@Autowired
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		try
		{
			Authentication auth = super.authenticate(authentication);
			
			// if control reaches here, login success, else an exception will be thrown.
			System.out.println("Hello..........");
			dao.resetFailAttempts(authentication.getName());
			return auth;
		}
		catch(BadCredentialsException e)
		{
			dao.updateFailAttempts(authentication.getName());
			throw e;
		}
		catch(LockedException e)
		{
			// this user is locked !
			String error = "";
			UserAttempts userAttempts = dao.getUserAttempts(authentication.getName());
			if(userAttempts != null)
			{
				Date lastModified = userAttempts.getLastModified();
				error = "User Account is locked ! <br><br> Username : " + authentication.getName() + "<br> Last " +
						"Attempt : " + lastModified;
			}
			else
			{
				error = e.getMessage();
			}
			
			throw new LockedException(error);
		}
	}

}
