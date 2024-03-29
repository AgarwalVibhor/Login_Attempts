package com.tcs.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;


public class CustomUserService extends JdbcDaoImpl{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
		
		super.setUsersByUsernameQuery(usersByUsernameQueryString);
	}
	
	@Override
	public void setAuthoritiesByUsernameQuery(String queryString) {
		
		super.setAuthoritiesByUsernameQuery(queryString);
	}
	
	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		/*
		 * By default, JdbcDaoImpl will always set AccountNotLocked to true, which is not what we want.
		 * Therefore, we override this method.
		 */
		
		/*
		 * This query will be returning List<T>
		 */
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[]{username},
				new RowMapper<UserDetails>(){
			
			@Override
			public UserDetails mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				
				String username = rs.getString("username");
				String password = rs.getString("password");
				boolean enabled = rs.getBoolean("enabled");
				boolean accountNonExpired = rs.getBoolean("accountNotExpired");
				boolean accountNonLocked = rs.getBoolean("accountNotLocked");
				boolean credentialsNonExpired = rs.getBoolean("credentialsNotExpired");
				return new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
						accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
				/*
				 * User is a class which implements the UserDetails interface.
				 */
			}
			
		});
	}
	
	@Override
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
	
		String returnUsername = userFromUserQuery.getUsername();
		if(super.isUsernameBasedPrimaryKey())
		{
			returnUsername = username;
		}
		return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
				userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(),
				userFromUserQuery.isAccountNonLocked(),combinedAuthorities);
	}
	

}
