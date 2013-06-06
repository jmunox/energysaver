/*
 *
*  This file is part of the "EnergySaver Game Application".
*
* 	"Energy-Saver Game" is free software: you can redistribute it and/or modify
* 	it under the terms of the GNU General Public License as published by
* 	the Free Software Foundation, either version 3 of the License, or
* 	(at your option) any later version.
*
* 	"Energy-Saver Game" is distributed in the hope that it will be useful,
* 	but WITHOUT ANY WARRANTY; without even the implied warranty of
* 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* 	GNU General Public License for more details.
*
* 	You should have received a copy of the GNU General Public License
* 	along with "EnergySaver Game Application". If not, see <http://www.gnu.org/licenses/>
*
*	@author Jesus Muñoz-Alcantara @ moxhu
*	http://agoagouanco.com
*	http://moxhu.com
*/

package org.moxhu.esavegame.business;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.moxhu.MoxhuObject;

import org.moxhu.esavegame.dao.EnergySaverDAO;
import org.moxhu.esavegame.dao.MongoDBEnergySaverDAO;
import org.moxhu.esavegame.domain.User;
import org.moxhu.util.crypto.DesEncrypter;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;



public class LoginBean implements MoxhuObject{
	
    private SecretKey secretKey=null;
    private DesEncrypter encrypter=null;
    
    
    public LoginBean(){
    	loadEncrypter();
    }   


    public void changeEmail(User user, String newEmail) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException {
    	// normal encoding is usually UTF-8 or UTF-16.
	
		user.setEmail(newEmail);
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		dao.updateUser(user);
	
    }
    
    public User getUserByEmail(String email) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException {
    	// normal encoding is usually UTF-8 or UTF-16.
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		return dao.getUserByEmail(email);
	
    }
    
    
    public User getUserByUserId(String userId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException {
    	// normal encoding is usually UTF-8 or UTF-16.
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		return dao.getUserByUserId(userId);
	
    }

    
    public void createUser(String email, String password) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException {
    	// normal encoding is usually UTF-8 or UTF-16.
    	
    	String encryptedPasword="";
    	
        /*try {              
        	 //to base64
        	 String base64 = org.moxhu.util.Base64.encode(password.getBytes());
        } catch (Exception e) {
        	LOGGER.debug("[LoginBean]Error encrypting [Exception="+e+"]");
        	//System.out.println("Error encrypting [Exception="+e+"]");
        	}*/
    	
   		    		
            // Decrypt
    	//	byte [] base64 = org.moxhu.util.Base64.decode(password);
    		String realPassword =password;
		//	try {
				//realPassword = new String(base64, "UTF-8");
		//	} catch (Exception e) {
		//		e.printStackTrace();
		//		throw new RequestException(e.getMessage());
		//	} // normal encoding is usually UTF-8 or UTF-16.
    		
    		encryptedPasword = encrypter.encrypt(realPassword);
 
            //decryptedPassword = encrypter.decrypt(realPassword);
    		User user = new User();
    		user.setEmail(email);
    			user.setPassword(encryptedPasword);
    			EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
    			dao.createUser(user);
    		
    
    	
    	
		
    	//EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		//dao.createUser(user);
	
    }
    
    public void changePassword(User user, String oldPassword, String newPassword) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException {
    	
    	String oldEncryptedPasword="";
    	String newEncryptedPasword="";
    	
        /*try {              
        	 //to base64
        	 String base64 = org.moxhu.util.Base64.encode(password.getBytes());
        } catch (Exception e) {
        	LOGGER.debug("[LoginBean]Error encrypting [Exception="+e+"]");
        	//System.out.println("Error encrypting [Exception="+e+"]");
        	}*/
    	
   		    		
            // Decrypt
    		//byte [] base64 = org.moxhu.util.Base64.decode(oldPassword);
    		//byte [] base64_2 = org.moxhu.util.Base64.decode(newPassword);
    		String oldRealPassword = oldPassword; 
    		String newRealPassword = newPassword;
	//		try {
				//oldRealPassword = new String(base64, "UTF-8");
				//newRealPassword = new String(base64_2, "UTF-8");
		//	} catch (Exception e) {
			//	e.printStackTrace();
			//	throw new RequestException(e.getMessage());
			//} // normal encoding is usually UTF-8 or UTF-16.
    		
    		oldEncryptedPasword = encrypter.encrypt(oldRealPassword);
    		newEncryptedPasword = encrypter.encrypt(newRealPassword);
            //decryptedPassword = encrypter.decrypt(realPassword);
    		if(user.getPassword()==oldEncryptedPasword){
    			user.setPassword(newEncryptedPasword);
    			EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
    			dao.updateUser(user);
    		}else {
    			throw new UnauthorizedRequestException("Current Password does not match ");
    		}
    			
    }
    /**
   	 * 
   	 * 
   	 * @return
   	 */
    public User validateUserLogin(String email, String password) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
    	
    	String encryptedPasword="";
    	User user= null;
    	
        /*try {              
        	 //to base64
        	 String base64 = org.moxhu.util.Base64.encode(password.getBytes());
        } catch (Exception e) {
        	LOGGER.debug("[LoginBean]Error encrypting [Exception="+e+"]");
        	//System.out.println("Error encrypting [Exception="+e+"]");
        	}*/
    	
    		    		
            // Decrypt
    	//	byte [] base64 = org.moxhu.util.Base64.decode(password);
    		String realPassword =password; //new String(base64, "UTF-8"); // normal encoding is usually UTF-8 or UTF-16.
    		
    		encryptedPasword = encrypter.encrypt(realPassword);
    		LOGGER.debug("User Trying to Log in: [" + email  +"|" + encryptedPasword +"]");
            //decryptedPassword = encrypter.decrypt(realPassword)
    		
        EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
       user = dao.getUser(email, encryptedPasword);
       ACCESS_LOGGER.info("Login Successful [User:" + user.getUserId()+ "|" +user.getEmail() + "]");
    	return user;
    }
    

    /**
     * 
     * @return
     */ 
     private void loadEncrypter(){

     		try{

     			String secretString;
     			//8 chars minimum
     			secretString = "secretkey";
     			//secretKey =  KeyGenerator.getInstance("DES").generateKey();
     			//KeyGenerator.getInstance("DES").
     			DESKeySpec desKeySpec = new DESKeySpec( secretString.getBytes());
     			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
     			secretKey = factory.generateSecret(desKeySpec);
     			// Create encrypter/decrypter class
     			encrypter = new DesEncrypter(secretKey);
             
     		}catch(InvalidKeyException invKeyEx){
     			LOGGER.info("[LoginBean]Exception in KeySpec " + invKeyEx);
     		}catch(NoSuchAlgorithmException algorithmEx){
     			LOGGER.info("[LoginBean]Exception in KeyFactory " + algorithmEx);
     		}catch(InvalidKeySpecException invalidSpecEx){
     			LOGGER.info("[LoginBean]Exception generating Key " + invalidSpecEx);
     		}
     }

}
