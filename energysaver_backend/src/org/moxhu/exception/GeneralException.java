/**
 * 
 */
package org.moxhu.exception;

/**
 * @author Jesus
 *
 */
public class GeneralException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1315875763167495987L;

	/**
     * Constructor
     * @param str    a string that explains what the exception condition is
     */
    public GeneralException (String str) {
        super(str);
    } 

    /**
     * Default constructor. Takes no arguments
     */
    public GeneralException () {
        super();
    }

}
