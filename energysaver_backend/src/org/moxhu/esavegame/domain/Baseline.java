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



/**
 */
public class Baseline extends TimeEvent {

	private String userId = null;
	
	private int hoursBaseline = 40;
	
	private float watts = 0;


	public Baseline(){
		super();
	}

	public int getHoursBaseline() {
		return hoursBaseline;
	}

	public void setHoursBaseline(int hoursBaseline) {
		this.hoursBaseline = hoursBaseline;
	}



	public float getWatts() {
		return watts;
	}


	public void setWatts(float watts) {
		this.watts = watts;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	
	/**
	 * Gets a string representation of this object.
	 * 
	 * @return a String
	 */
	public String toString() {
		return getUserId() + "|baseline:hours=" + getHoursBaseline() + "|baseline:watts=" + getWatts();
	}
	
	@Override
	public String getRelativeUrl() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
