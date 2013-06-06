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

import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.dao.EnergySaverDAO;
import org.moxhu.esavegame.dao.MongoDBEnergySaverDAO;
import org.moxhu.esavegame.domain.Agenda;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

public class AgendaServiceBean implements MoxhuObject {

	public AgendaServiceBean() {
		// TODO Auto-generated constructor stub
	}
	
	public void createAgenda(String userId, String weekHours) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		Agenda agenda = new Agenda();
		Avatar avatar = dao.getAvatarFromUserId(userId);
		agenda.setAvatar(avatar);
		int myWeek;
		try{
			myWeek = Integer.parseInt(weekHours);
		}catch(Exception e){
			LOGGER.error("Create Agenda: WeekHours is not a number" + weekHours);
			throw new BadRequestException("Create Agenda: Time is not number" + weekHours);			
		}
		agenda.setPercentageWeek(myWeek);
		//agenda.setPercentageMonday(monday);
	    //agenda.setPercentageTuesday(tuesday);
	    //agenda.setPercentageWednesday(wednesday);
	    //agenda.setPercentageThursday(thursday);
	    //agenda.setPercentageFriday(friday);
	    //agenda.setPercentageSaturday(saturday);
	    //agenda.setPercentageSunday(sunday);
		dao.saveAgenda(agenda);
	}
	
	public void createDayAgenda(String userId, String dayHours) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		Agenda agenda;
		Avatar avatar = dao.getAvatarFromUserId(userId);
		
		
		int monday = -1;
		int tuesday = -1;
		int wednesday = -1;
		int thursday = -1;
		int friday = -1;
		int saturday = -1;
		int sunday = -1;
		int myDay;
		
		try{
			myDay = Integer.parseInt(dayHours);
		}catch(Exception e){
			LOGGER.error("Create Day Agenda: DayHours is not a number" + dayHours);
			throw new BadRequestException("Create Day Agenda: Time is not number" + dayHours);			
		}
		
		try {
			agenda = this.getCurrentAgenda(userId);
			
			monday = agenda.getPercentageMonday();
			tuesday = agenda.getPercentageTuesday();
			wednesday = agenda.getPercentageWednesday();
			thursday = agenda.getPercentageThursday();
			friday = agenda.getPercentageFriday();
			saturday = agenda.getPercentageSaturday();
			sunday = agenda.getPercentageSunday();
			
		}catch(NotFoundException nfe) {
			//create new agenda
			agenda = new Agenda();
		}
		
		agenda.setAvatar(avatar);
		
		GregorianCalendar calendar = new GregorianCalendar();
		int dayOfWeek = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		if(dayOfWeek== GregorianCalendar.MONDAY) {
			monday = myDay; 
		}else if(dayOfWeek== GregorianCalendar.TUESDAY) {
			tuesday = myDay; 
		}else if(dayOfWeek== GregorianCalendar.WEDNESDAY) {
			wednesday = myDay; 
		}else if(dayOfWeek== GregorianCalendar.THURSDAY) {
			thursday = myDay; 
		}else if(dayOfWeek== GregorianCalendar.FRIDAY) {
			friday = myDay; 
		}else if(dayOfWeek== GregorianCalendar.SATURDAY) {
			saturday = myDay; 
		}else if(dayOfWeek== GregorianCalendar.SUNDAY) {
			sunday = myDay; 
		}

		agenda.setPercentageMonday(monday);
	    agenda.setPercentageTuesday(tuesday);
	    agenda.setPercentageWednesday(wednesday);
	    agenda.setPercentageThursday(thursday);
	    agenda.setPercentageFriday(friday);
	    agenda.setPercentageSaturday(saturday);
	    agenda.setPercentageSunday(sunday);
		dao.saveAgenda(agenda);
	}
	
	public void createWeekAgenda(String userId, String weekHours) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		Agenda agenda = new Agenda();
		Avatar avatar = dao.getAvatarFromUserId(userId);
		agenda.setAvatar(avatar);
		int myWeek;
		try{
			myWeek = Integer.parseInt(weekHours);
		}catch(Exception e){
			LOGGER.error("Create Week Agenda: WeekHours is not a number" + weekHours);
			throw new BadRequestException("Create Week Agenda: Agenda: Time is not number" + weekHours);			
		}
		agenda.setPercentageWeek(myWeek);
		//agenda.setPercentageMonday(monday);
	    //agenda.setPercentageTuesday(tuesday);
	    //agenda.setPercentageWednesday(wednesday);
	    //agenda.setPercentageThursday(thursday);
	    //agenda.setPercentageFriday(friday);
	    //agenda.setPercentageSaturday(saturday);
	    //agenda.setPercentageSunday(sunday);
		dao.saveAgenda(agenda);
	}
	
	public void updateAgenda(Agenda agenda) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
	    EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		dao.saveAgenda(agenda);
	}
	
	public Agenda getAgenda(String userId, String time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		long myTime = 0;
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		Avatar avatar = dao.getAvatarFromUserId(userId);
		try{
			myTime = Long.parseLong(time);
		}catch(Exception e){
			LOGGER.error("Get Agenda: Time is not number" + time);
			throw new BadRequestException("Get Agenda: Time is not number" + time);			
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(myTime));
		calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int week = calendar.get(GregorianCalendar.WEEK_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		GregorianCalendar calendar2 = new GregorianCalendar(year, month,
		        day);
		return dao.getAgenda(avatar,calendar2.getTime().getTime());
	}

	public Agenda getCurrentAgenda(String userId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		Avatar avatar = dao.getAvatarFromUserId(userId);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int week = calendar.get(GregorianCalendar.WEEK_OF_MONTH);
		int month = calendar.get(GregorianCalendar.MONTH);
		int year = calendar.get(GregorianCalendar.YEAR);
		GregorianCalendar calendar2 = new GregorianCalendar(year, month,
		        day);
		calendar2.set(GregorianCalendar.WEEK_OF_MONTH, week);
		calendar2.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY );
		return dao.getAgenda(avatar,calendar2.getTime().getTime());
	}
}
