package org.moxhu.util.crypto;

import java.util.Calendar;
import java.util.Random;

//import jw.token.SimpleToken;
//import jw.token.Token;

/**
 * Simple token factory. It produces string tokens from a random generator
 * @author araman
 * 
 * Modified for Simple String tokens...
 * Orignally CAPTCHA�s tokens 
 * @see
 * http://www.javaworld.com/javaworld/jw-03-2005/jw-0307-captcha.html
 * http://www.javaworld.com/javaworld/jw-03-2005/captcha/jw-0307-captcha.zip
 */
public class SimpleTokenFactory{

    private static Random generator = new Random(Calendar.getInstance().getTimeInMillis());
    
    /** 
     * New SimpleTokenFactory
     */
    public SimpleTokenFactory() {
	}

	/**
     * Creates and returns a new token
     * @return Token 
	 */
    public static String generateToken(int beginIndex, int endIndex) {
    	
        String strToken = new String();

        //generate random token, check if it is longer than 5 digits
       while(strToken.length()<(endIndex)){
    	   strToken=generateToken();
    	   if(strToken.length()<(endIndex))
    	   System.out.println(strToken.length());
       }
        strToken =strToken.substring(beginIndex,endIndex);

        return strToken;
	}
    
    public static String generateToken(){
    	//System.out.println(Calendar.getInstance().getTimeInMillis());
    	
        int tempToken = 0;
        //get the next random int and convert it to a string
        tempToken = generator.nextInt();
        if ( tempToken < 0 ) {
            tempToken = -1 * tempToken;
        }
        return Integer.toString(tempToken);
    }   
}