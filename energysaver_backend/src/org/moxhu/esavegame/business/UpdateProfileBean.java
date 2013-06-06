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

import java.util.Vector;

import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.dao.EnergySaverDAO;
import org.moxhu.esavegame.dao.MongoDBEnergySaverDAO;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.esavegame.domain.FeedbackCondition;
import org.moxhu.esavegame.domain.Gender;
import org.moxhu.esavegame.domain.GoalCondition;
import org.moxhu.esavegame.domain.Team;
import org.moxhu.esavegame.domain.User;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

public class UpdateProfileBean implements MoxhuObject {

	public UpdateProfileBean() {
		// TODO Auto-generated constructor stub
	}

	public Avatar updateProfile (User user,  String avatarName, String plugwiseId, String gender, String age, String location) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		Avatar avatar = user.getAvatar();
		if(avatar==null) {
			avatar = new Avatar();
			avatar.setUserId(user.getUserId());
		}
		avatar.setAvatarName(avatarName);
		avatar.setPlugwiseDevice(plugwiseId);
		avatar.setGender(Gender.getState(gender));
		avatar.setLocation(location);
		
		try{
			avatar.setAge(Integer.parseInt(age));
		}catch (Exception e){
			avatar.setAge(0);
		}
		
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		dao.updateAvatar(avatar);
		user.setAvatar(avatar);
		return avatar;
	}
	
	public void createAvatar(User user, String avatarName, String plugwiseId, String gender, String age, String location) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		Avatar avatar = new Avatar();
		avatar.setAvatarName(avatarName);
		avatar.setPlugwiseDevice(plugwiseId);
		avatar.setGender(Gender.getState(gender));
		avatar.setLocation(location);
		
		try{
			avatar.setAge(Integer.parseInt(age));
		}catch (Exception e){
			avatar.setAge(0);
		}
		avatar.setUserId(user.getUserId());
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		dao.createAvatar(avatar);
		user.setAvatar(avatar);
	}
	
	public Avatar getAvatarFromUserId(String userId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException {
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		return dao.getAvatarFromUserId(userId);
	}
	
	public void createTeam(String teamName, String goal, String feedback) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		Team team = new Team();
		team.setTeamName(teamName);
		team.setGoal(GoalCondition.getState(goal));
		team.setFeedback(FeedbackCondition.getState(feedback));
/*		team.addTeamMember(userId1);
		team.addTeamMember(userId2);
		team.addTeamMember(userId3);
		team.addTeamMember(userId4);*/
		
		dao.createTeam(team);
/*		Avatar a1 = dao.getAvatarFromUserId(userId1);
		a1.setTeam(team);
		Avatar a2 = dao.getAvatarFromUserId(userId2);
		a2.setTeam(team);
		Avatar a3 = dao.getAvatarFromUserId(userId3);
		a3.setTeam(team);
		Avatar a4 = dao.getAvatarFromUserId(userId4);
		a4.setTeam(team);
		dao.updateAvatar(a1);
		dao.updateAvatar(a2);
		dao.updateAvatar(a3);
		dao.updateAvatar(a4);*/
	}
	
	public void assignTeamToUser(String teamId,String userId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		Team team = dao.getTeam(teamId);
		team.addTeamMember(userId);
		Avatar avatar = dao.getAvatarFromUserId(userId);
		avatar.setTeam(team);
		dao.updateAvatar(avatar);
		dao.updateTeam(team);
	}
	
	public Vector<Team> getTeams() throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{;
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		return dao.getTeams();
	}
	
	
}
