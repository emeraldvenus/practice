package com.kayak.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kayak.domain.User;
import com.kayak.mybatis.inter.IUserOperation;

public class Test1 {

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("conf.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getUser(int userId){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);          
            User user =  userOperation.getUserById(userId);
            System.out.println(user);
            
        } finally {
            session.close();
        }
    }
	public void getUserByName(String userName){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);          
            List<User> users =  userOperation.getUserByName(userName);
            for(User user:users){
                System.out.println(user);
            }
            
        } finally {
            session.close();
        }
    }
	 public void addUser(){
	        User user=new User();
	        user.setUserName("xiaoxin");
	        user.setUserPassword("123321");
	        user.setUserEmail("xiaoxin@126.com");
	        SqlSession session = sqlSessionFactory.openSession();
	        try {
	            IUserOperation userOperation=session.getMapper(IUserOperation.class);
	            userOperation.addUser(user);
	            session.commit();
	            System.out.println("当前增加的用户 id为:"+user.getUserId());
	        } finally {
	            session.close();
	        }
	    }
	public static void main(String[] args) {
		Test1 test = new Test1();
//		test.getUser(1);
		test.addUser();
		}

}