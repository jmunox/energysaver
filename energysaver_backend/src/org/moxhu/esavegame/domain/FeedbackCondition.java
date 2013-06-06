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

package org.moxhu.esavegame.domain;

import org.moxhu.MoxhuObject;


public class FeedbackCondition implements MoxhuObject {


	public static final FeedbackCondition NONE = new FeedbackCondition("none");

	public static final FeedbackCondition INDIVIDUAL_COMPARISON = new FeedbackCondition("individual_comparison");


	public static final FeedbackCondition GROUP = new FeedbackCondition("group");


	public static final FeedbackCondition MIXED = new FeedbackCondition("mixed");

	
	/**
	 * Gets the type of state
	 * 
	 * @param name
	 * @return the StateVO instance
	 */
	public static FeedbackCondition getState(String name) {
		if (name == null) {
			return null;
		} else if (name.equals("none")) {
			return NONE;
		} else if (name.equals("individual_comparison")) {
			return INDIVIDUAL_COMPARISON;
		} else if (name.equals("group")) {
			return GROUP;
		} else if (name.equals("mixed")) {
			return MIXED;
		} else {
			return null;
		}
	}

	/** the name of the state */
	private String name;

	/**
	 * Creates a new instance.
	 * 
	 * @param name
	 *            the name of the state
	 */
	private FeedbackCondition(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this state.
	 * 
	 * @return the name as a String
	 */
	public String getName() {
		return name;
	}


	/**
	 * Determines whether the specified object is equal to this one.
	 * 
	 * @param o
	 *            the object to compare against
	 * @return true if Object o represents the same category, false otherwise
	 */
	public boolean equals(Object o) {
		if (!(o instanceof FeedbackCondition)) {
			return false;
		}

		FeedbackCondition fbcond = (FeedbackCondition) o;
		return fbcond.getName().equals(name);
	}

	/**
	 * Returns a String representation of this object.
	 * 
	 * @return a String
	 */
	public String toString() {
		return this.name;
	}
	
}
