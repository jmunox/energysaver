package org.moxhu.esavegame.business.test;

import org.moxhu.esavegame.business.LoginBean;
import org.moxhu.esavegame.domain.User;
import org.moxhu.web.app.ContextApplication;

public class TestLoginBean {

	public static void main(String[] args) throws Exception{
		ContextApplication.getInstance().start();
		LoginBean login = new LoginBean();
		String email = "jma1983@hotmail.com";
		String password = "QWert5y#1";
		
		System.out.println("email:"+ email);
		System.out.println("passw:"+ password);
		login.createUser(email,password);
		System.out.println("Test Create User Succesful");
		User user = login.validateUserLogin(email,password); 
		System.out.println("Test Login:"+ email);
		System.out.println("User.userId:"+ user.getUserId());
		System.out.println("User.email:"+ user.getEmail());
		System.out.println("User.passw:"+ user.getPassword());
		
		
	}
	
}
