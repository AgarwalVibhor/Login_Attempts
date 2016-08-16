package com.tcs.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomRowMapper implements RowMapper<UserAttempts> {

	@Override
	public UserAttempts mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UserAttempts userAttempts = new UserAttempts();
		userAttempts.setId(rs.getInt("id"));
		userAttempts.setUsername(rs.getString("username"));
		userAttempts.setAttempts(rs.getInt("attempts"));
		userAttempts.setLastModified(rs.getDate("lastModified"));
		return userAttempts;
	}

}
