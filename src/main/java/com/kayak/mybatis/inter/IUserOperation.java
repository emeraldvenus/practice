package com.kayak.mybatis.inter;

import java.util.List;

import com.kayak.domain.User;

public interface IUserOperation {
	public User getUserById(int userId);
	public User getAllUser();
	public List<User>  getUserByName(String userName);
	public void addUser(User user);
}	
