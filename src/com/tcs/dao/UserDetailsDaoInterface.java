package com.tcs.dao;

import com.tcs.model.UserAttempts;

public interface UserDetailsDaoInterface {
	
	public void updateFailAttempts(String username);
	
	public void resetFailAttempts(String username);
	
	public UserAttempts getUserAttempts(String username);

}
