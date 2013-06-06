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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.bson.types.ObjectId;
import org.moxhu.esavegame.domain.Agenda;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.esavegame.domain.Baseline;
import org.moxhu.esavegame.domain.EnergyBlog;
import org.moxhu.esavegame.domain.ErrorTransaction;
import org.moxhu.esavegame.domain.FeedbackCondition;
import org.moxhu.esavegame.domain.Gender;
import org.moxhu.esavegame.domain.GoalCondition;
import org.moxhu.esavegame.domain.PowerTransaction;
import org.moxhu.esavegame.domain.Team;
import org.moxhu.esavegame.domain.User;
import org.moxhu.esavegame.domain.UserLogTransaction;
import org.moxhu.exception.GeneralException;
import org.moxhu.util.db.mongodb.MongoDBConnectionManager;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class MongoDBEnergySaverDAO implements EnergySaverDAO {
	
	private DB db = null;
	
	public MongoDBEnergySaverDAO() throws RequestException {
		
		try {
			db = MongoDBConnectionManager.getInstance().getDBConnection();
		} catch (GeneralException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RequestException("Error in Database");
		}
	}

	@Override
	public PowerTransaction getPowerTransaction(String plugId, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		PowerTransaction powerTrans = null;
		DBCollection trans = db.getCollection("powertransactions");
		BasicDBObject query = new BasicDBObject("plugid", plugId).append("time", time);
		BasicDBObject object;
        try{
        	object = (BasicDBObject)trans.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("PowerTransaction not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting PowerTransaction " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.warn("Transaction does not exist: " + query);
        	throw new NotFoundException("Transaction does not exist: " + query);
        }
    	powerTrans = new PowerTransaction(); 
    	powerTrans.setPlugId(plugId);
    	powerTrans.setTime(object.getLong("time"));
    	powerTrans.setWatts(Float.parseFloat(object.getString("power")));
		return powerTrans;

	}

	@Override
	public void savePowerTransaction(PowerTransaction transaction) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		try{
		DBCollection transactions = db.getCollection("powertransactions");
		transactions.insert(new BasicDBObject().append("plugid", transaction.getPlugId()).append("power", transaction.getWatts()+ "").append("time", transaction.getTime()));
	       }catch (Exception e){
	        	LOGGER.error("[DAO] ERROR while saving PowerTransaction: [" + transaction.getPlugId() +" : " + transaction.getTime() + "]");
	        	throw new RequestException(e.getMessage());
	        }
	}

	@Override
	public Vector<PowerTransaction> getPowerTransactionsForTimePeriod(String plugId,
			long startTime, long endTime) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		Vector<PowerTransaction> vectorPowTrans = null;
		DBCollection trans = db.getCollection("powertransactions");
		//db.powertransactions.find({'plugid':'729903','time':{$gt:134005680000},'time':{$lt:1340143200000}}). 
		BasicDBObject query = new BasicDBObject("plugid", plugId);
		query.append("time", new BasicDBObject("$lt", endTime).append("$gt",startTime));
        try{
        	DBCursor cursor = trans.find(query);
        	
        	vectorPowTrans = new Vector<PowerTransaction>(cursor.size());
        	if(cursor.size()<1) {
        		throw new NotFoundException("PowerTrans Vector not found:" + query );
        	}
        	while (cursor.hasNext()) {
        		
        		BasicDBObject object = (BasicDBObject)cursor.next();
        		PowerTransaction powerTrans = new PowerTransaction(); 
        		powerTrans.setPlugId(plugId);
        		powerTrans.setTime(object.getLong("time"));
        		powerTrans.setWatts(Float.parseFloat(object.getString("power")));
        		vectorPowTrans.add(powerTrans);
			}
		cursor.close();
        }catch (NoSuchElementException nse){
       	 	throw new NotFoundException("PowerTrans Vector not found: " + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting PowerTrans Vector " + query, e);
        	throw new RequestException(e.getMessage());
        }
		return vectorPowTrans;
	}

	@Override
	public User getUserByEmail(String email) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		DBCollection users = db.getCollection("users");
		BasicDBObject query = new BasicDBObject("email", email);
        BasicDBObject object;
        try{
		 object = (BasicDBObject)users.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("User not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting User " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("User does not exist: " + query);
        	throw new NotFoundException("User does not exist: " + query);
        }
		User user = new User();
		ObjectId oid = (ObjectId)object.get("_id");
    	String userId = oid.toString();
		user.setUserId(userId);
		user.setEmail(email);
		user.setPassword(object.getString("password"));
		try{
			user.setAvatar(getAvatarFromUserId(userId));
		}catch(NotFoundException nfe) {
			//do nothing
		}
		return user;
	}
	
	@Override
	public User getUserByUserId(String userId)throws BadRequestException, NotFoundException, UnauthorizedRequestException, RequestException {
		DBCollection users = db.getCollection("users");
		BasicDBObject query = new BasicDBObject().append("_id", 
				new ObjectId(userId));
        BasicDBObject object;
        try{
		 object = (BasicDBObject)users.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("User not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting User " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("User does not exist: " + query);
        	throw new NotFoundException("User does not exist: " + query);
        }
		User user = new User();
		user.setUserId(userId);
		user.setEmail(object.getString("email"));
		user.setPassword(object.getString("password"));
		try{
			user.setAvatar(getAvatarFromUserId(userId));
		}catch(NotFoundException nfe) {
			//do nothing
		}
		return user;
	}
	
	@Override
	public User getUser(String email, String password) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		DBCollection users = db.getCollection("users");
		BasicDBObject query = new BasicDBObject("email", email).append("password", password);
        BasicDBObject object = null;
        try{
		 object = (BasicDBObject)users.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new UnauthorizedRequestException("User/Password Combination not correct" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting User " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("User/Password Combination not correct" + query);
        	throw new UnauthorizedRequestException("User/Password Combination not correct" + query );
        }
		User user = new User();
		ObjectId oid = (ObjectId)object.get("_id");
    	String userId = oid.toString();
		user.setUserId(userId);
		user.setEmail(email);
		user.setPassword(password);
		try{
			user.setAvatar(getAvatarFromUserId(userId));
		}catch(NotFoundException nfe) {
			
		}
		return user;
		
	}

	@Override
	public void updateUser(User user) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{

		try{
			DBCollection users = db.getCollection("users");
			users.update(new BasicDBObject().append("email", user.getEmail()),new BasicDBObject().append("email", user.getEmail()).append("password", user.getPassword()+ ""),true,false);
		       }catch (Exception e){
		        	LOGGER.error("[DAO] ERROR while saving User: [" + user.getEmail() +"]");
		        	throw new RequestException(e.getMessage());
		        }

	}

	@Override
	public void createUser(User user) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		try{
			getUserByEmail(user.getEmail());
			throw new UnauthorizedRequestException("User already exists");
		}catch(NotFoundException nfe){
		try{
			LOGGER.debug("Creating User:" + user.getEmail());
			DBCollection users = db.getCollection("users");
			users.insert(new BasicDBObject().append("email", user.getEmail()).append("password", user.getPassword()+ ""));
		       }catch (Exception e){
		        	LOGGER.error("[DAO] ERROR while saving new User: [" + user.getEmail() +"]");
		        	throw new RequestException(e.getMessage());
		        }
		}
	}


	@Override
	public Team getTeam(String teamId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		Team team = new Team();
		DBCollection teams = db.getCollection("teams");
		BasicDBObject queryTeam = new BasicDBObject().append("_id", 
				new ObjectId(teamId));
        BasicDBObject objectTeams;
        try{
        	objectTeams = (BasicDBObject)teams.findOne(queryTeam);
        	
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("Team not found" + queryTeam );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting team " + queryTeam, e);
        	throw new RequestException(e.getMessage());
        }
        if(objectTeams == null) {
        	LOGGER.error("Team does not exist: " + queryTeam);
        	throw new NotFoundException("Team does not exist: " + queryTeam);
        }
        team.setTeamName(objectTeams.getString("name"));
    	team.setGoal(GoalCondition.getState(objectTeams.getString("goal")));
    	team.setFeedback(FeedbackCondition.getState(objectTeams.getString("feedback")));
    	team.setTeamId(teamId);
        
        DBCollection teamsUsers = db.getCollection("teamuser");
        BasicDBObject query = new BasicDBObject().append("teamId",team.getTeamId());
		try{
			DBCursor cursor = teamsUsers.find(query);
			BasicDBObject object;
			while (cursor.hasNext()) {
	    		object = (BasicDBObject)cursor.next();
	    		team.addTeamMember(object.getString("userId"));
			}
			cursor.close();
		}catch (NoSuchElementException nse){
			throw new NotFoundException("Error updating Team-Users, not found" + query );
		}catch (Exception e){
			LOGGER.error("[DAO] ERROR while updating Team-Users " + query, e);
			throw new RequestException(e.getMessage());
		}
		
        return team;
	}
	
/*	@Override
	public Team getTeamForUser(String userId) throws BadRequestException,
			NotFoundException, RequestException {

		Team team = new Team();
		DBCollection teams = db.getCollection("teams");
		BasicDBObject queryTeam = new BasicDBObject("userId", userId);
		BasicDBObject objectTeams;
		try {
			objectTeams = (BasicDBObject) teams.findOne(queryTeam);
			team.setTeamName(objectTeams.getString("name"));
			team.setGoal(GoalCondition.getState(objectTeams.getString("goal")));
			team.setFeedback(FeedbackCondition.getState(objectTeams
					.getString("feedback")));
			BSONObject oid = (BSONObject) objectTeams.get("_id");
			String teamId = (String) (oid.get("$oid"));
			team.setTeamId(teamId);
		} catch (NoSuchElementException nse) {
			throw new NotFoundException("Team not found" + queryTeam);
		} catch (Exception e) {
			LOGGER.error("[DAO] ERROR while getting team " + queryTeam, e);
			throw new RequestException(e.getMessage());
		}
		return team;
	}*/

	@Override
	public void updateTeam(Team team) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		BasicDBObject insertObject = new BasicDBObject();
		try{
			DBCollection teams = db.getCollection("teams");
			insertObject.append("name", team.getTeamName());
			insertObject.append("goal",team.getGoal().getName());
			insertObject.append("feedback",team.getFeedback().getName());
			teams.update(new BasicDBObject().append("_id",new BasicDBObject().append("$oid",team.getTeamId())),insertObject, true, false);
    	}catch (NoSuchElementException nse){
    			throw new NotFoundException("Error updating Team, not found" + insertObject );
    	}catch (Exception e){
    		LOGGER.error("[DAO] ERROR while updating Team " + insertObject, e);
    		throw new RequestException(e.getMessage());
    	}	
		Vector<String> members = team.getTeamMembers();
		if(!members.isEmpty()) {
		try{
			DBCollection teamsUsers = db.getCollection("team-user");
			for (int index = 0; index < members.size(); index++) {
				BasicDBObject insertObject2 = new BasicDBObject();
				insertObject2.append("teamId",team.getTeamId());
				insertObject2.append("userId",members.get(index));
				teamsUsers.update(insertObject2,insertObject2, true, false);
			}
		}catch (NoSuchElementException nse){
			throw new NotFoundException("Error updating Team-Users, not found" + insertObject );
		}catch (Exception e){
			LOGGER.error("[DAO] ERROR while updating Team-Users " + insertObject, e);
			throw new RequestException(e.getMessage());
		}
		}
		
	}

	@Override
	public void createTeam(Team team) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		DBCollection teams = db.getCollection("teams");
		BasicDBObject insertObject = new BasicDBObject();
		insertObject.append("name", team.getTeamName());
		insertObject.append("goal",team.getGoal().getName());
		insertObject.append("feedback",team.getFeedback().getName());

		try{
		teams.insert(insertObject);
/*			Vector<String> members = team.getTeamMembers();
			DBCollection teamsUsers = db.getCollection("team-user");
			members = team.getTeamMembers();
			for (int index = 0; index < members.size(); index++) {
				BasicDBObject insertObject2 = new BasicDBObject();
				insertObject2.append("teamId",team.getTeamId());
				insertObject2.append("userId",members.get(index));
				teamsUsers.insert(insertObject2);
			}*/
		}catch (NoSuchElementException nse){
			throw new NotFoundException("Error updating Team-Users, not found" + insertObject );
		}catch (Exception e){
			LOGGER.error("[DAO] ERROR while updating Team-Users " + insertObject, e);
			throw new RequestException(e.getMessage());
		}
	}
	
	@Override
	public Vector<Team> getTeams() throws BadRequestException,
			NotFoundException, RequestException, UnauthorizedRequestException {
		Vector<Team> teamsVector = null;
		DBCollection teams = db.getCollection("teams");
		
        try{
        	DBCursor cursor = teams.find();
        	teamsVector = new Vector<Team>(cursor.size());
        	
        	while (cursor.hasNext()) {
        		
        		BasicDBObject objectTeams = (BasicDBObject)cursor.next();
        		Team team = new Team();
        		team.setTeamName(objectTeams.getString("name"));
            	team.setGoal(GoalCondition.getState(objectTeams.getString("goal")));
            	team.setFeedback(FeedbackCondition.getState(objectTeams.getString("feedback")));
        		ObjectId oid = (ObjectId)objectTeams.get("_id");
            	team.setTeamId(oid.toString());
            	teamsVector.add(team);
			}
		cursor.close();
        	
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting all teams ", e);
        	throw new RequestException(e.getMessage());
        }
		return null;
	}
	
	@Override
	public Agenda getAgenda(Avatar avatar, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
			DBCollection agendas = db.getCollection("agendas");
			BasicDBObject query = new BasicDBObject();
			query.append("userId", avatar.getUserId()).append("time", time);
			 BasicDBObject object;
		        try{
				 object = (BasicDBObject)agendas.findOne(query);
		        }catch (NoSuchElementException nse){
		        	 throw new NotFoundException("Agenda not found" + query );
		        }catch (Exception e){
		        	LOGGER.error("[DAO] ERROR while getting Agenda " + query, e);
		        	throw new RequestException(e.getMessage());
		        }
		        if(object == null) {
		        	LOGGER.error("Agenda not found: " + query);
		        	throw new NotFoundException("Agenda not found:" + query );
		        }
		        Agenda agenda = new Agenda();
				agenda.setDate(new Date(object.getLong("time")));
				agenda.setPercentageWeek(object.getInt("week"));
		        agenda.setPercentageMonday(object.getInt("monday"));
		        agenda.setPercentageTuesday(object.getInt("tuesday"));
		        agenda.setPercentageWednesday(object.getInt("wednesday"));
		        agenda.setPercentageThursday(object.getInt("thursday"));
		        agenda.setPercentageFriday(object.getInt("friday"));
		        agenda.setPercentageSaturday(object.getInt("saturday"));
		        agenda.setPercentageSunday(object.getInt("sunday"));
		        agenda.setAvatar(avatar);
		return agenda;
	}

	@Override
	public void saveAgenda(Agenda agenda) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		try{
			DBCollection agendas = db.getCollection("agendas");
			BasicDBObject insertObject = new BasicDBObject();
			insertObject.append("userId", agenda.getAvatar().getUserId()).append("time", agenda.getTimeBasedOnWeek());
			insertObject.append("monday",agenda.getPercentageMonday());
			insertObject.append("tuesday",agenda.getPercentageTuesday());
			insertObject.append("wednesday",agenda.getPercentageWednesday());
			insertObject.append("thursday",agenda.getPercentageThursday());
			insertObject.append("friday",agenda.getPercentageFriday());
			insertObject.append("saturday",agenda.getPercentageSaturday());
			insertObject.append("sunday",agenda.getPercentageSunday());
			insertObject.append("week",agenda.getPercentageWeek());
			BasicDBObject updateFilter = new BasicDBObject().append("userId", agenda.getAvatar().getUserId()).append("time", agenda.getTimeBasedOnWeek()); 
			agendas.update(updateFilter, insertObject,true, false);
			//agendas.insert(insertObject):
		       }catch (Exception e){
		        	LOGGER.error("[DAO] ERROR while saving new Agenda for user: [" + agenda.getAvatar().getUserId() +"]");
		        	throw new RequestException(e.getMessage());
		        }	    
	}

	@Override
	public Avatar getAvatarFromPlugId(String plugId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		DBCollection avatars = db.getCollection("avatars");
		BasicDBObject query = new BasicDBObject("plugId", plugId);
        BasicDBObject object;
        try{
		 object = (BasicDBObject)avatars.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("Avatar not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting Avatar " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("Avatar not found: " + query);
        	throw new NotFoundException("Avatar not found:" + query );
        }
		Avatar avatar = new Avatar();
		avatar.setPlugwiseDevice(object.getString("plugId"));
		avatar.setAvatarName(object.getString("name"));
		avatar.setUserId(object.getString("userId"));
		avatar.setLocation(object.getString("location"));
		avatar.setAge(object.getInt("age"));
		avatar.setGender(Gender.getState((object.getString("gender"))));
		//avatar.setGoal(GoalCondition.getState(object.getString("goal")));
		//avatar.setFeedback(FeedbackCondition.getState(object.getString("feedback")));
		String teamId = object.getString("teamId");
		if(teamId!=null) {
			try{
				avatar.setTeam(getTeam(teamId));
			}catch (NoSuchElementException nse){
				LOGGER.error("Team for Avatar not found" + query );
			}catch (Exception e){
				LOGGER.error("[DAO] ERROR while getting team " + query, e);
			}
		}
		
		try{
			avatar.setBaseline(getBaseline(avatar.getUserId()));
		}catch (NoSuchElementException nse){
			LOGGER.error("Baseline for Avatar not found" + query );
		}catch (Exception e){
			LOGGER.error("[DAO] ERROR while getting Baseline for Avatar " + query, e);
		}
		
		
		return avatar;
	}
	
	@Override
	public Avatar getAvatarFromUserId(String userId) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		DBCollection avatars = db.getCollection("avatars");
		BasicDBObject query = new BasicDBObject("userId", userId);
        BasicDBObject object;
        try{
		 object = (BasicDBObject)avatars.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("Avatar not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting Avatar " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("Avatar not found: " + query);
        	throw new NotFoundException("Avatar not found:" + query );
        }
		Avatar avatar = new Avatar();
		avatar.setPlugwiseDevice(object.getString("plugId"));
		avatar.setAvatarName(object.getString("name"));
		avatar.setUserId(object.getString("userId"));
		avatar.setLocation(object.getString("location"));
		avatar.setAge(object.getInt("age"));
		avatar.setGender(Gender.getState((object.getString("gender"))));
		//avatar.setGoal(GoalCondition.getState(object.getString("goal")));
		//avatar.setFeedback(FeedbackCondition.getState(object.getString("feedback")));
		String teamId = object.getString("teamId");
		
		if(teamId!=null) {
			try{
				avatar.setTeam(getTeam(teamId));
			}catch (NoSuchElementException nse){
				LOGGER.error("Team for Avatar not found" + query );
			}catch (Exception e){
				LOGGER.error("[DAO] ERROR while getting team " + query, e);
			}
		}
		
		try{
			avatar.setBaseline(getBaseline(avatar.getUserId()));
		}catch (NoSuchElementException nse){
			LOGGER.error("Baseline for Avatar not found" + query );
		}catch (Exception e){
			LOGGER.error("[DAO] ERROR while getting Baseline for Avatar " + query, e);
		}
        
		return avatar;
	}

	
	@Override
	public void createAvatar(Avatar avatar) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		try{
			getAvatarFromUserId(avatar.getUserId());
			throw new BadRequestException("User Already has an avatar");
		}catch (NotFoundException nse){
			try{
				LOGGER.debug("Creating Avatar for User:" + avatar.getUserId());
				DBCollection avatars = db.getCollection("avatars");
				BasicDBObject insertObject = new BasicDBObject();
				insertObject.append("userId", avatar.getUserId());
				insertObject.append("name", avatar.getAvatarName());
				insertObject.append("plugId",avatar.getPlugwiseDevice());
				insertObject.append("location",avatar.getLocation());
				insertObject.append("gender",avatar.getGender().toString());
				insertObject.append("age", avatar.getAge());
				//insertObject.append("goal",avatar.getGoal().toString());
				//insertObject.append("feedback",avatar.getFeedback().toString());
				insertObject.append("teamId", avatar.getTeam().getTeamId());
				
				avatars.insert(insertObject);
			       }catch (Exception e){
			        	LOGGER.error("[DAO] ERROR while saving new Avatar for user: [" + avatar.getUserId() +"]");
			        	throw new RequestException(e.getMessage());
			        } 
       }
	}
	
	@Override
	public void updateAvatar(Avatar avatar) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{

			try{
				DBCollection avatars = db.getCollection("avatars");
				BasicDBObject insertObject = new BasicDBObject();
				insertObject.append("userId", avatar.getUserId());
				insertObject.append("name", avatar.getAvatarName());
				insertObject.append("plugId",avatar.getPlugwiseDevice());
				insertObject.append("location",avatar.getLocation());
				insertObject.append("gender",avatar.getGender().toString());
				insertObject.append("age", avatar.getAge());
				//insertObject.append("goal",avatar.getGoal().toString());
				//insertObject.append("feedback",avatar.getFeedback().toString());
				insertObject.append("teamId", avatar.getTeam().getTeamId());
				
				avatars.update(new BasicDBObject().append("userId",avatar.getUserId()),insertObject, true, false);
			       }catch (Exception e){
			        	LOGGER.error("[DAO] ERROR while saving Avatar for user: [" + avatar.getUserId() +"]", e);
			        	throw new RequestException(e.getMessage());
			        } 
	}

	@Override
	public void saveWeeklyConsumption(EnergyBlog eBlog) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		DBCollection weeklyenergyblogs = db.getCollection("weeklyenergyblogs");
		BasicDBObject insertObject = new BasicDBObject("userId", eBlog.getAvatar().getUserId()).append("time", eBlog.getTimeUntilDay()).append("consumption", eBlog.getPowerConsumption());
        BasicDBObject object = new BasicDBObject().append("userId", eBlog.getAvatar().getUserId()).append("time", eBlog.getTimeUntilDay()); 
        try{
        	weeklyenergyblogs.update(object,insertObject, true, false);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("Weekly EnergyBlog not found" + object );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while saving weekly energyblog " + object, e);
        	throw new RequestException(e.getMessage());
        }
	}

	@Override
	public EnergyBlog getWeeklyConsumption(Avatar avatar, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		DBCollection weeklyenergyblogs = db.getCollection("weeklyenergyblogs");
		BasicDBObject query = new BasicDBObject("userId", avatar.getUserId()).append("time", time);
        BasicDBObject object;
        try{
		 object = (BasicDBObject)weeklyenergyblogs.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("Weekly EnergyBlog not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting weekly energyblog " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("Weekly consumption not found: " + query);
        	throw new NotFoundException("Weekly Consumption not found:" + query );
        }
        long timeDB =object.getLong("time");
		EnergyBlog blog = new EnergyBlog();
		blog.setDate(new Date(timeDB));
		blog.setAvatar(avatar);
		String strConsump = object.getString("consumption");
		float consumption = new Float(strConsump);
		blog.setPowerConsumption(consumption);
		return blog;
	}

	@Override
	public void saveDailyEnergyBlog(EnergyBlog eBlog) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		DBCollection dailyenergyblogs = db.getCollection("dailyenergyblogs");
		BasicDBObject insertObject = new BasicDBObject("userId", eBlog.getAvatar().getUserId()).append("time", eBlog.getTimeUntilDay()).append("consumption", eBlog.getPowerConsumption());
        BasicDBObject object = new BasicDBObject().append("userId", eBlog.getAvatar().getUserId()).append("time", eBlog.getTimeUntilDay()); 
        try{
        	dailyenergyblogs.update(object,insertObject, true, false);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("EnergyBlog not found" + object );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while saving energyblog " + object, e);
        	throw new RequestException(e.getMessage());
        }
	}

	@Override
	public EnergyBlog getDailyEnergyBlog(Avatar avatar, long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		DBCollection dailyenergyblogs = db.getCollection("dailyenergyblogs");
		BasicDBObject query = new BasicDBObject("userId", avatar.getUserId()).append("time", time);
        BasicDBObject object;
        try{
		 object = (BasicDBObject)dailyenergyblogs.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("EnergyBlog not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting energyblog " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("EnergyBlog not found: " + query);
        	throw new NotFoundException("EnergyBLog not found:" + query );
        }
        long timeDB =object.getLong("time");
		EnergyBlog blog = new EnergyBlog();
		blog.setDate(new Date(timeDB));
		blog.setAvatar(avatar);
		String strConsump = object.getString("consumption");
		float consumption = new Float(strConsump);
		blog.setPowerConsumption(consumption);
		return blog;
	}
	
	public Vector<EnergyBlog> getEnergyBlogsForTimePeriod(Avatar avatar, long startTime, long endTime) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException {
		
		Vector<EnergyBlog> vectorEnergyBlogs = null;
		DBCollection dailyenergyblogs = db.getCollection("dailyenergyblogs");
		BasicDBObject query = new BasicDBObject("userId", avatar.getUserId());
		query.append("time", new BasicDBObject("$lt", endTime).append("$gt",startTime));
        BasicDBObject object;
        try{
        	DBCursor cursor = dailyenergyblogs.find(query);
        	vectorEnergyBlogs = new Vector<EnergyBlog>(cursor.size());
        	while (cursor.hasNext()) {
        		object = (BasicDBObject)cursor.next();
        		long timeDB =object.getLong("time");
        		EnergyBlog blog = new EnergyBlog();
        		blog.setDate(new Date(timeDB));
        		blog.setAvatar(avatar);
        		String strConsump = object.getString("consumption");
        		float consumption = new Float(strConsump);
        		blog.setPowerConsumption(consumption);
        		vectorEnergyBlogs.add(blog);
			}
        	cursor.close();
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("EnergyBlog Vector not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting EnergyBlog Vector " + query, e);
        	throw new RequestException(e.getMessage());
        }
		return vectorEnergyBlogs;
	}

	@Override
	public void saveErrorTransaction(ErrorTransaction transaction)
			throws BadRequestException, NotFoundException, RequestException,
			UnauthorizedRequestException {
		try{
		DBCollection transactions = db.getCollection("errortransactions");
		transactions.insert(new BasicDBObject().append("plugid", transaction.getPlugId()).append("code", transaction.getErrorCode()+ "").append("time", transaction.getTime()).append("date", new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(transaction.getLastModified())));
	       }catch (Exception e){
	        	LOGGER.error("[DAO] ERROR while saving ErrorTransaction: [" + transaction.getPlugId() +" : " + transaction.getTime() + ":" +  transaction.getErrorCode() + "]");
	        	throw new RequestException(e.getMessage());
	        }
		
	}

	public void logUserAction(UserLogTransaction transaction) throws BadRequestException, NotFoundException, RequestException,
	UnauthorizedRequestException {
		try{
		DBCollection transactions = db.getCollection("userlog");
		transactions.insert(new BasicDBObject().append("user", transaction.getUser().getUserId()).append("plugid", transaction.getUser().getAvatar().getPlugwiseDevice()).append("event", transaction.getUserEvent()).append("time", transaction.getTime()).append("date", new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(transaction.getLastModified())));
	       }catch (Exception e){
	        	LOGGER.error("[DAO] ERROR while saving UserLog: [" +  transaction.getUser().getUserId() +" : " + transaction.getUser().getAvatar().getPlugwiseDevice() + "]");
	        	throw new RequestException(e.getMessage());
	        }
		
	}

	@Override
	public void saveBaseline(Baseline baseline) throws BadRequestException,
			NotFoundException, RequestException, UnauthorizedRequestException {
		try{
			DBCollection baselines = db.getCollection("baselines");
			BasicDBObject insertObject = new BasicDBObject();
			insertObject.append("userId", baseline.getUserId());
			insertObject.append("hours", baseline.getHoursBaseline());
			insertObject.append("power",baseline.getWatts()+ "");
			
			baselines.update(new BasicDBObject().append("userId",baseline.getUserId()),insertObject, true, false);
		       }catch (Exception e){
		        	LOGGER.error("[DAO] ERROR while saving Baseline for user: [" + baseline.getUserId() +"]", e);
		        	throw new RequestException(e.getMessage());
		        } 

		
	}

	@Override
	public Baseline getBaseline(String userId) throws BadRequestException,
			NotFoundException, RequestException, UnauthorizedRequestException {
		
		DBCollection baselines = db.getCollection("baselines");
		BasicDBObject query = new BasicDBObject("userId", userId);
        BasicDBObject object;
        try{
		 object = (BasicDBObject)baselines.findOne(query);
        }catch (NoSuchElementException nse){
        	 throw new NotFoundException("Baseline not found" + query );
        }catch (Exception e){
        	LOGGER.error("[DAO] ERROR while getting Baseline " + query, e);
        	throw new RequestException(e.getMessage());
        }
        if(object == null) {
        	LOGGER.error("Baseline not found: " + query);
        	throw new NotFoundException("Baseline not found:" + query );
        }
        Baseline myBaseline = new Baseline();
        myBaseline.setUserId(object.getString("userId"));
        myBaseline.setHoursBaseline(object.getInt("hours"));
        myBaseline.setWatts(Float.parseFloat(object.getString("power")));
		
        return myBaseline;
	}




}
