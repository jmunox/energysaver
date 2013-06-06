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
import java.util.GregorianCalendar;

import org.moxhu.web.app.data.WebObject;

public abstract class TimeEvent implements WebObject {
	
	protected Date date;

	public TimeEvent(){
		date = new Date(System.currentTimeMillis());
	}
	/**
	 * Modifies the date for the object
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public void setDate(int year, int month, int day, int hour, int minute,
	        int second) {
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1,
		        day, hour, minute, second);
		date = calendar.getTime();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the date that this portal was last updated.
	 * 
	 * @return a Date instance representing the time of the most recent entry
	 */
	public Date getLastModified() {
		return (Date) this.date.clone();
	}
	
	
	public long getTimeUntilDay(){
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		GregorianCalendar calendar2 = new GregorianCalendar(year, month,
		        day);
		Date g2date = calendar2.getTime();
		
		return g2date.getTime();
	}

}
