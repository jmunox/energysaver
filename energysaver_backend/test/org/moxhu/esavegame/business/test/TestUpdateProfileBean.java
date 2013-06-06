package org.moxhu.esavegame.business.test;

import org.moxhu.esavegame.business.LoginBean;
import org.moxhu.esavegame.business.UpdateProfileBean;
import org.moxhu.esavegame.domain.User;
import org.moxhu.web.app.ContextApplication;

public class TestUpdateProfileBean {
	
	public static void main(String[] args) throws Exception{
		ContextApplication.getInstance().start();
		LoginBean login = new LoginBean();
		String email = "jma1983@hotmail.com";
		String password = "QWert5y#1";
		System.out.println("Login...");
		User user = login.validateUserLogin(email,password);
		UpdateProfileBean profile = new UpdateProfileBean();
		System.out.println("Create Avatar...");
		profile.createAvatar(user, "Jesus", "C3CEBA", "male", "28", "PT3.31"); 
		System.out.println("User.userId:"+ user.getAvatar().getUserId());
		System.out.println("Avatar Name:"+ user.getAvatar().getAvatarName());
		System.out.println("Plugwise:"+ user.getAvatar().getPlugwiseDevice());
		
		
	}

}
