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

import java.util.Date;


public class PowerTransaction extends TimeEvent{

	
	private String plugId = null;
	
	private float watts = 0;


	public PowerTransaction(){
		super();
	}
	/**
	 * Constructor
	 */
	public PowerTransaction(String plugId, long time,  float watts) {
		this.setPlugId(plugId);
		this.setTime(time);
		this.setWatts(watts);
		//TODO time into Date
	}


	public String getPlugId() {
		return plugId;
	}

	public void setPlugId(String plugId){
		this.plugId = plugId;
	}


	public long getTime() {
		return this.getLastModified().getTime();
	}


	public void setTime(long time) {
		this.setDate(new Date(time));
	}


	public float getWatts() {
		return watts;
	}


	public void setWatts(float watts) {
		this.watts = watts;
	}


	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getRelativeUrl() {
		// TODO Auto-generated method stub
		return null;
	}


}
