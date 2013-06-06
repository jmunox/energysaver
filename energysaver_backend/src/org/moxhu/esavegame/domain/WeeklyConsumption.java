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

public class WeeklyConsumption extends EnergyBlog {

	protected Agenda agenda;
	protected EnergyBlog monday;
	protected EnergyBlog tuesday;
	protected EnergyBlog wednesday;
	protected EnergyBlog thursday;
	protected EnergyBlog friday;
	protected EnergyBlog saturday;
	protected EnergyBlog sunnday;
	protected Avatar avatar;
	
	public WeeklyConsumption() {
		super();
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public EnergyBlog getMonday() {
		return monday;
	}

	public void setMonday(EnergyBlog monday) {
		this.monday = monday;
	}

	public EnergyBlog getTuesday() {
		return tuesday;
	}

	public void setTuesday(EnergyBlog tuesday) {
		this.tuesday = tuesday;
	}

	public EnergyBlog getWednesday() {
		return wednesday;
	}

	public void setWednesday(EnergyBlog wednesday) {
		this.wednesday = wednesday;
	}

	public EnergyBlog getThursday() {
		return thursday;
	}

	public void setThursday(EnergyBlog thursday) {
		this.thursday = thursday;
	}

	public EnergyBlog getFriday() {
		return friday;
	}

	public void setFriday(EnergyBlog friday) {
		this.friday = friday;
	}

	public EnergyBlog getSaturday() {
		return saturday;
	}

	public void setSaturday(EnergyBlog saturday) {
		this.saturday = saturday;
	}

	public EnergyBlog getSunnday() {
		return sunnday;
	}

	public void setSunnday(EnergyBlog sunnday) {
		this.sunnday = sunnday;
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

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}


}
