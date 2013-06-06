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

package org.moxhu.esavegame.business.facade;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.moxhu.MoxhuObject;



/**
 * The TransactionVO represents the Model element of the MVC pattern. It stores
 * all the objects that will be used during the process of the request till the
 * response. It lets the javaBridge to manage the information needed to display a page
 * as a synchronized transaction process.
 * 
 * @author Jesus Muñoz

 */
public class TransactionVO implements MoxhuObject{

	/**
	 * Hashtable containing all the objects
	 */
	private Hashtable params;

	/**
	 * The identifier based on a time stamp.
	 */
	private long timeStamp;

	/**
	 * Constructor
	 */
	public TransactionVO() {
		timeStamp = System.currentTimeMillis();
		params = new Hashtable();
	}

	/**
	 * Gets the id of the transaction based on a time stamp when it was created.
	 * 
	 * @return
	 */
	public long getId() {
		return timeStamp;
	}

	/**
	 * Gets the object stored int the TransactionVO
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		return params.get(key);
	}

	/**
	 * Method to add objects in the TransactionVO. Handles when it is already
	 * added, not permitting to add it again, also does not lets adding null
	 * objects.
	 * 
	 * @param key
	 * @param value
	 */
	public void add(Object key, Object value) {
		if (get(key) != null) {
			LOGGER.error((new StringBuilder()).append(
			        "[" + this.getId()
			                + "]Add param failed, param already exists! ")
			        .append(key).toString());
			return;
		}
		if (value == null) {
			LOGGER.error((new StringBuilder()).append(
			        "[" + this.getId()
			                + "]Add param failed, value equals null! ").append(
			        key).toString());
			return;
		}
		try {
			params.put(key, value);
		} catch (Exception x) {
			params.remove(key);
			LOGGER.fatal("Unable to load param", x);
		}
		return;
	}

	/**
	 * Removes the object from the TransactionVO
	 * 
	 * @param key
	 */
	public void remove(Object key) {
		params.remove(key);
	}

	/**
	 * Gets the Key names as a String[]
	 * 
	 * @return
	 */
	public String[] getKeyNames() {
		Vector names = new Vector();
		Enumeration enu = params.keys();
		while (enu.hasMoreElements()) {
			names.add((String) enu.nextElement().toString());
		}
		return (String[]) names.toArray(new String[names.size()]);
	}

	/**
	 * Gets the set of all keys.
	 * 
	 * @return a Set of String instances
	 */
	public Set keySet() {
		return params.keySet();
	}

	/**
	 * Calls the {@link Hashtable#toString()} method
	 */
	public String toString() {
		return params.toString();
	}

	/**
	 * Calls the {@link Hashtable#contains(Object)} method
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains(Object value) {
		return params.contains(value);
	}

	/**
	 * Calls the {@link Hashtable#containsKey(Object)} method
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key) {
		return params.containsKey(key);
	}

	/**
	 * Calls the {@link Hashtable#containsValue(Object)}method
	 * 
	 * @param value
	 * @return
	 */
	public boolean containsValue(Object value) {
		return params.containsValue(value);
	}

}
