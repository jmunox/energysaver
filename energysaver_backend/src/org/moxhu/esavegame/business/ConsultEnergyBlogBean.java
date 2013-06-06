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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.dao.EnergySaverDAO;
import org.moxhu.esavegame.dao.MongoDBEnergySaverDAO;
import org.moxhu.esavegame.domain.Agenda;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.esavegame.domain.EnergyBlog;
import org.moxhu.esavegame.domain.PowerTransaction;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

public class ConsultEnergyBlogBean implements MoxhuObject {
	
	protected EnergySaverDAO dao;
	
	
	public ConsultEnergyBlogBean() throws RequestException {
		dao = new MongoDBEnergySaverDAO();
	}
	
	
	public EnergyBlog getEnergyBlog(String userId, int day, int month, int year) throws RequestException, BadRequestException, NotFoundException, UnauthorizedRequestException {
		
		EnergyBlog eBlog = null;
		
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1,
		        day);
		
		long time = calendar.getTime().getTime();
		Avatar avatar = dao.getAvatarFromUserId(userId);
		try{
			eBlog = dao.getDailyEnergyBlog( avatar,  time);
		} catch(NotFoundException nte)
		{
			eBlog=this.calculateEnergyBlog(avatar, time);
		}
		return eBlog;
	}
	
	public EnergyBlog getEnergyBlog(String userId, long time) throws RequestException, BadRequestException, NotFoundException, UnauthorizedRequestException {
		
		EnergyBlog eBlog = null;
		
		Avatar avatar = dao.getAvatarFromUserId(userId);
		try{
			eBlog = dao.getDailyEnergyBlog( avatar,  time);
		} catch(NotFoundException nte)
		{
			//eBlog=this.calculateEnergyBlog(avatar, time);
		}
		return eBlog;
	}

	
	public EnergyBlog getCurrentEnergyBlog(String userId) throws RequestException, BadRequestException, NotFoundException, UnauthorizedRequestException{
		GregorianCalendar calendar = new GregorianCalendar();
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		return getEnergyBlog(userId,  day,  month+ 1,  year);
	}
	
	
	public EnergyBlog getLastEnergyBlog(String userId) throws RequestException, BadRequestException, NotFoundException, UnauthorizedRequestException{
		GregorianCalendar calendar = new GregorianCalendar();
		long time = calendar.getTime().getTime() - 86400000;
		calendar.setTime(new Date(time));
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		return getEnergyBlog(userId,  day,  month+ 1,  year);
	}
	
	
	public EnergyBlog getWeekConsumption(String userId, int day, int month, int year) throws RequestException, BadRequestException, UnauthorizedRequestException, NotFoundException{
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
		long time = calendar.getTime().getTime();
		EnergyBlog eWeek;
		Avatar avatar = dao.getAvatarFromUserId(userId);
		try{
			eWeek = dao.getWeeklyConsumption(avatar,  time);
		} catch(NotFoundException nte)
		{
			eWeek = this.calculateWeeklyConsumption(avatar, day, month, year);
		}
		return eWeek;
		
	}
	
	
	public EnergyBlog getCurrentWeekConsumption(String userId) throws RequestException, BadRequestException, UnauthorizedRequestException, NotFoundException{
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		return getWeekConsumption(userId,  day,  month+ 1,  year);
	}
	
	
	public EnergyBlog getLastWeekConsumption(String userId) throws RequestException, BadRequestException, UnauthorizedRequestException, NotFoundException{
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
		int week = calendar.get(GregorianCalendar.WEEK_OF_MONTH);
		calendar.set(GregorianCalendar.WEEK_OF_MONTH,  week -1);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		return getWeekConsumption(userId,  day,  month+ 1,  year);		
	}
	
	
	public EnergyBlog calculateEnergyBlog(Avatar avatar, long startTime) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		long endTime = startTime + 86400000;
		Agenda agenda = new Agenda();
		Date date = new Date(startTime);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		agenda.setDate(date);
		agenda = dao.getAgenda(avatar,agenda.getTimeBasedOnWeek());
		int hours = 0;
		switch(day){
			case GregorianCalendar.MONDAY:
				hours += Math.abs(agenda.getPercentageMonday());
				break;
			case GregorianCalendar.TUESDAY:
				hours += Math.abs(agenda.getPercentageTuesday());
				break;
			case GregorianCalendar.WEDNESDAY:
				hours += Math.abs(agenda.getPercentageWednesday());
				break;
			case GregorianCalendar.THURSDAY:
				hours += Math.abs(agenda.getPercentageThursday());
				break;
			case GregorianCalendar.FRIDAY:
				hours += Math.abs(agenda.getPercentageFriday());
				break;
			case GregorianCalendar.SATURDAY:
				hours += Math.abs(agenda.getPercentageSaturday());
				break;
			case GregorianCalendar.SUNDAY:
				hours += Math.abs(agenda.getPercentageSunday());
				break;
		}
		
		Vector<PowerTransaction> transactions; 
		transactions = dao.getPowerTransactionsForTimePeriod(avatar.getPlugwiseDevice() ,
				startTime,  endTime);
		float totalWatts = 1;
		for (int index = 0; index < transactions.size(); index++) {
			//System.out.println(transactions.get(index).getWatts());
			totalWatts += transactions.get(index).getWatts();
		}
		float average = totalWatts / transactions.size();
		//System.out.println(avatar.getPlugwiseDevice() +"  ,Time: " + startTime + " , TOTAL: " + totalWatts + " , average: " + average +" , hours: " + hours);
		EnergyBlog eBlog = new EnergyBlog();
		eBlog.setDate(date);
		eBlog.setAvatar(avatar);
		//average of power / hours in office
		eBlog.setPowerConsumption(average/hours);
		dao.saveDailyEnergyBlog(eBlog); 
		return eBlog;
	}
	
	
	private EnergyBlog calculateWeeklyConsumption(Avatar avatar, int day, int month, int year) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		long startTime = calendar.getTime().getTime();
		Date  date = new Date(startTime);
		int week = calendar.get(GregorianCalendar.WEEK_OF_MONTH);
		calendar.set(GregorianCalendar.WEEK_OF_MONTH,  week +1);
		long endTime = calendar.getTime().getTime();
		Vector<EnergyBlog> vectorEnergyBlogs = dao.getEnergyBlogsForTimePeriod(avatar, startTime, endTime);
		float totalWatts = 0;
		for (int index = 0; index < vectorEnergyBlogs.size(); index++) {
			totalWatts += vectorEnergyBlogs.get(index).getPowerConsumption();
		}
		EnergyBlog weeklyBlog= new EnergyBlog();
		weeklyBlog.setDate(date);
		weeklyBlog.setAvatar(avatar);
		//average of power / hours in office
		weeklyBlog.setPowerConsumption(totalWatts/7);
		dao.saveWeeklyConsumption(weeklyBlog);
		return weeklyBlog;
	}
	
}
