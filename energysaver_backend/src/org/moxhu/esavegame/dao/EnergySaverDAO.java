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

package org.moxhu.esavegame.dao;

import java.util.Vector;

import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.domain.Agenda;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.esavegame.domain.Baseline;
import org.moxhu.esavegame.domain.EnergyBlog;
import org.moxhu.esavegame.domain.PowerTransaction;
import org.moxhu.esavegame.domain.ErrorTransaction;
import org.moxhu.esavegame.domain.Team;
import org.moxhu.esavegame.domain.User;
import org.moxhu.esavegame.domain.UserLogTransaction;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

public interface EnergySaverDAO extends MoxhuObject{
	
	public PowerTransaction getPowerTransaction(String plugId, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void savePowerTransaction(PowerTransaction transaction) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void saveErrorTransaction(ErrorTransaction transaction) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public Vector<PowerTransaction> getPowerTransactionsForTimePeriod(String plugId, long startTime, long endTime) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public User getUserByEmail(String email) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public User getUserByUserId(String userId)throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public User getUser(String email, String password) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void updateUser(User user) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void createUser(User user) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public Team getTeam(String teamId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void updateTeam(Team team) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void createTeam(Team team) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public Vector<Team> getTeams() throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public Agenda getAgenda(Avatar avatar, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void saveAgenda(Agenda agenda) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public Avatar getAvatarFromPlugId(String plugId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public Avatar getAvatarFromUserId(String userId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void createAvatar(Avatar avatar) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void updateAvatar(Avatar avatar) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void saveWeeklyConsumption(EnergyBlog eBlog) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public EnergyBlog getWeeklyConsumption(Avatar avatar, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void saveDailyEnergyBlog(EnergyBlog eBlog) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public EnergyBlog getDailyEnergyBlog(Avatar avatar, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;

	public Vector<EnergyBlog> getEnergyBlogsForTimePeriod(Avatar avatar, long startTime, long endTime) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException;
	
	public void logUserAction(UserLogTransaction transaction) throws BadRequestException, NotFoundException, RequestException,
	UnauthorizedRequestException;
	
	public void saveBaseline(Baseline baseline) throws BadRequestException, NotFoundException, RequestException,
	UnauthorizedRequestException;
	
	public Baseline getBaseline(String userId) throws BadRequestException, NotFoundException, RequestException,
	UnauthorizedRequestException;
}
