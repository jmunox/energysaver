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

public class Agenda extends TimeEvent {
	
	public final static int HOURS_WEEK = 40;

	protected int percentageMonday = 1;
	protected int percentageTuesday = 1;
	protected int percentageWednesday = 1;
	protected int percentageThursday = 1;
	protected int percentageFriday = 1;
	protected int percentageSaturday = 1;
	protected int percentageSunday = 1;
	protected int percentageWeek = 1;
	
	protected Avatar avatar;
	
	public Agenda(){
		super();
	}
	
	
	
	public Avatar getAvatar() {
		return avatar;
	}




	public int getPercentageFriday() {
		return percentageFriday;
	}




	public int getPercentageMonday() {
		return percentageMonday;
	}




	public int getPercentageSaturday() {
		return percentageSaturday;
	}




	public int getPercentageSunday() {
		return percentageSunday;
	}




	public int getPercentageThursday() {
		return percentageThursday;
	}




	public int getPercentageTuesday() {
		return percentageTuesday;
	}




	public int getPercentageWednesday() {
		return percentageWednesday;
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




	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}




	public void setPercentageFriday(int percentage) {
		if(percentage!=0)
			this.percentageFriday = percentage;
	}




	public void setPercentageMonday(int percentage) {
		if(percentage!=0)
			this.percentageMonday = percentage;
	}




	public void setPercentageSaturday(int percentage) {
		if(percentage!=0)
			this.percentageSaturday = percentage;
	}




	public void setPercentageSunday(int percentage) {
		if(percentage!=0)
			this.percentageSunday = percentage;
	}




	public void setPercentageThursday(int percentage) {
		if(percentage!=0)
			this.percentageThursday = percentage;
	}


	
	public void setPercentageTuesday(int percentage) {
		if(percentage!=0)
			this.percentageTuesday = percentage;
	}

	public void setPercentageWednesday(int percentage) {
		if(percentage!=0)
			this.percentageWednesday = percentage;
	}
	
	
	public long getTimeBasedOnWeek(){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int week = calendar.get(GregorianCalendar.WEEK_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		GregorianCalendar calendar2 = new GregorianCalendar(year, month,
		        day);
		calendar2.set(GregorianCalendar.WEEK_OF_MONTH, week);
		calendar2.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY );
		Date g2date = calendar2.getTime();
		return g2date.getTime();
		
	}



	public int getPercentageWeek() {
		return percentageWeek;
	}



	public void setPercentageWeek(int percentageWeek) {
		if(percentageWeek<5)
				percentageWeek=5;
		this.percentageWeek = percentageWeek;
		int hoursPerWeek = Math.round((percentageWeek*HOURS_WEEK)/100);
		int workingDays = 5;
		if(hoursPerWeek > 168)
			hoursPerWeek = 168;
		if(hoursPerWeek > 120)
			workingDays = 7;
		int perDay = Math.round(hoursPerWeek/workingDays);
		this.setPercentageMonday(perDay);
		this.setPercentageTuesday(perDay);
		this.setPercentageWednesday(perDay);
		this.setPercentageThursday(perDay);
		this.setPercentageFriday(perDay);
		if(hoursPerWeek > 120){
			this.setPercentageSaturday(perDay);
			this.setPercentageSunday(perDay);	
		}
		
	}



}
