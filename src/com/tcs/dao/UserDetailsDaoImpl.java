package com.tcs.dao;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.LockedException;

import com.tcs.model.CustomRowMapper;
import com.tcs.model.UserAttempts;



public class UserDetailsDaoImpl extends JdbcDaoSupport implements UserDetailsDaoInterface {
	
	public static final String SQL_USERS_TABLE_UPDATE_LOCKED = "update users_table set accountNotLocked = ? " +
			"where username = ?";
	public static final String SQL_USERS_TABLE_COUNT = "select count(*) from users_table where username = ?";
	public static final String SQL_USERS_ATTEMPTS_TABLE_INSERT_ATTEMPTS = "insert into users_attempts_table " +
			"(username, attempts, lastModified) values(?,?,?)";
	public static final String SQL_USERS_ATTEMPTS_TABLE_UPDATE_ATTEMPTS = "update users_attempts_table set " +
			"attempts = attempts + 1, lastModified = ? where username = ?";
	public static final String SQL_USERS_ATTEMPTS_TABLE_GET = "select * from users_attempts_table where username = ?";
	public static final String SQL_USERS_ATTEMPTS_TABLE_RESET_ATTEMPTS = "update users_attempts_table " +
			"set attempts = 0, lastModified = null where username = ?";
	
	public static final int MAX_ATTEMPTS = 3;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void initialize()
	{
		setDataSource(dataSource);
	}

	@Override
	public void updateFailAttempts(String username) {
		
		UserAttempts userattempts = getUserAttempts(username);
		if(userattempts == null)
		{
			if(isUserExists(username))
			{
				getJdbcTemplate().update(SQL_USERS_ATTEMPTS_TABLE_INSERT_ATTEMPTS, new Object[]{username, 1,
						new Date()});
			}
		}
		else
		{
			if(isUserExists(username))
			{
				if(userattempts.getAttempts() < MAX_ATTEMPTS)
				{
					getJdbcTemplate().update(SQL_USERS_ATTEMPTS_TABLE_UPDATE_ATTEMPTS, new Object[]{new Date(),
							username});
				}
				else
				{
					getJdbcTemplate().update(SQL_USERS_TABLE_UPDATE_LOCKED, new Object[]{false, username});
					throw new LockedException("User Account is locked !!");
				}
			}
		}
		
	}
	
	private boolean isUserExists(String username){
		
		int count = 0;
		
		count = getJdbcTemplate().queryForObject(SQL_USERS_TABLE_COUNT, new Object[]{username}, Integer.class);
		
		if(count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void resetFailAttempts(String username) {
		
		getJdbcTemplate().update(SQL_USERS_ATTEMPTS_TABLE_RESET_ATTEMPTS, new Object[]{username});
		
	}

	@Override
	public UserAttempts getUserAttempts(String username) {
		
		try{
			UserAttempts userAttempts = getJdbcTemplate().queryForObject(SQL_USERS_ATTEMPTS_TABLE_GET, 
					new Object[]{username}, new CustomRowMapper());
			/*
			 * CustomRowMapper is implementing the RowMapper interface.
			 * Here the mapRow() method will be called automatically and UserAttempts object will be returned.
			 */
			return userAttempts;
		}
		catch(EmptyResultDataAccessException exception)
		{
			return null;
		}
		
	}

}
