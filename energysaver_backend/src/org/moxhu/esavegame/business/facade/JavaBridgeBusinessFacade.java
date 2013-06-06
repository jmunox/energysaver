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

package org.moxhu.esavegame.business.facade;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.business.AgendaServiceBean;
import org.moxhu.esavegame.business.ConsultEnergyBlogBean;
import org.moxhu.esavegame.business.LoggerServiceBean;
import org.moxhu.esavegame.business.LoginBean;
import org.moxhu.esavegame.business.UpdateProfileBean;
import org.moxhu.esavegame.domain.Agenda;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.esavegame.domain.EnergyBlog;
import org.moxhu.esavegame.domain.User;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

public class JavaBridgeBusinessFacade implements MoxhuObject {

	public TransactionVO validateUserLogin(String email, String password) {
		TransactionVO transaction = new TransactionVO();
		LoginBean login = new LoginBean();
		User user = null;
		try {
			if (email == null || email == "null" || email.trim() == "") {
				throw new BadRequestException("email is null" + email);
			}
			if (password == null || password == "null" || password.trim() == "") {
				throw new BadRequestException("password is empty");
			}
			email = email.toLowerCase().trim();
			password = password.trim();
			user = login.validateUserLogin(email, password);
			LoggerServiceBean.logUserAction(user, "login");
			transaction.add("user", user);
			transaction.add("avatar", user.getAvatar());
		} catch (BadRequestException e) {
			transaction.add("exception", e);
			transaction.add("exception.message", "Please enter a valid data");
			LOGGER.info("[" + email + "]" + e.getMessage());
		} catch (NotFoundException e) {
			transaction.add("exception", e);
			transaction.add("exception.message",
					"User/Password Combination not correct");
			LOGGER.info("[" + email + "]" + e.getMessage());
		} catch (UnauthorizedRequestException e) {
			transaction.add("exception", e);
			transaction.add("exception.message",
					"User/Password Combination not correct");
			LOGGER.info("[" + email + "]" + e.getMessage());
		} catch (Exception e) {
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		}
		return transaction;

	}

	public TransactionVO registerUser(String email, String password,
			String avatarName, String plugwiseId, String gender, String age,
			String location) {
		TransactionVO transaction = new TransactionVO();
		LoginBean login = new LoginBean();
		User user = null;
		try {
			if (email == null || email == "null" || email.trim() == "") {
				throw new BadRequestException("email is null" + email);
			}
			if (password == null || password == "null" || password.trim() == "") {
				throw new BadRequestException("password is empty");
			}
			if (avatarName == null || avatarName == "null"
					|| avatarName.trim() == "") {
				throw new BadRequestException("avatarName is null" + avatarName);
			}
			if (plugwiseId == null || plugwiseId == "null"
					|| plugwiseId.trim() == "") {
				throw new BadRequestException("plugwiseId is empty");
			}
			if (gender == null || gender == "null" || gender.trim() == "") {
				throw new BadRequestException("gender is null" + email);
			}
			if (age == null || age == "null" || age.trim() == "") {
				throw new BadRequestException("age is empty");
			}
			if (location == null || location == "null" || location.trim() == "") {
				throw new BadRequestException("location is empty");
			}
			email = email.toLowerCase().trim();
			password = password.trim();
			login.createUser(email, password);
			try {
				user = login.getUserByEmail(email);
				UpdateProfileBean profileBean = new UpdateProfileBean();
				profileBean.createAvatar(user, avatarName, plugwiseId, gender,
						age, location);
				LoggerServiceBean.logUserAction(user, "registerUser");
				transaction.add("user", user);
				transaction.add("avatar", user.getAvatar());
			} catch (BadRequestException e) {
				transaction.add("exception", e);
				transaction.add("exception.message",
						"User Already has an avatar");
				LOGGER.error("[" + email + "]" + e.getMessage(), e);
			} catch (NotFoundException e) {
				LOGGER.error("[" + email + "]" + e.getMessage(), e);
			} catch (UnauthorizedRequestException e) {
				LOGGER.error("[" + email + "]" + e.getMessage(), e);
			} catch (Exception e) {
				transaction.add("exception", e);
				transaction
						.add("exception.message",
								"Error while creating your profile. Please Login and Try to edit your profile.");
				LOGGER.error("[" + email + "]" + e.getMessage(), e);
			}

		} catch (BadRequestException e) {
			transaction.add("exception", e);
			transaction.add("exception.message", "Please enter valid data.");
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (UnauthorizedRequestException e) {
			transaction.add("exception", e);
			transaction.add("exception.message",
					"User already exists. Try login.");
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (Exception e) {
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		}
		return transaction;

	}

	public TransactionVO updateAvatar(String email, String avatarName,
			String plugwiseId, String gender, String age, String location) {
		TransactionVO transaction = new TransactionVO();
		LoginBean login = new LoginBean();
		UpdateProfileBean profileBean = new UpdateProfileBean();
		User user = null;
		try {
			if (email == null || email == "null" || email.trim() == "") {
				throw new BadRequestException("email is null" + email);
			}
			if (avatarName == null || avatarName == "null"
					|| avatarName.trim() == "") {
				throw new BadRequestException("avatarName is null" + avatarName);
			}
			if (plugwiseId == null || plugwiseId == "null"
					|| plugwiseId.trim() == "") {
				throw new BadRequestException("plugwiseId is empty");
			}
			if (gender == null || gender == "null" || gender.trim() == "") {
				throw new BadRequestException("gender is null" + email);
			}
			if (age == null || age == "null" || age.trim() == "") {
				throw new BadRequestException("age is empty");
			}
			if (location == null || location == "null" || location.trim() == "") {
				throw new BadRequestException("location is empty");
			}
			email = email.toLowerCase().trim();
			user = login.getUserByEmail(email);
			profileBean.updateProfile(user, avatarName, plugwiseId, gender,
					age, location);
			transaction.add("user", user);
			transaction.add("avatar", user.getAvatar());
			if (user.getAvatar().getTeam().getTeamId() != null) {
				transaction.add("team", user.getAvatar().getTeam());
			}
			LoggerServiceBean.logUserAction(user, "updateAvatar");
		} catch (BadRequestException e) {
			transaction.add("exception", e);
			transaction.add("exception.message", "Please enter valid data.");
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (UnauthorizedRequestException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}
		return transaction;
	}

	public TransactionVO getProfile(String email) {
		TransactionVO transaction = new TransactionVO();
		LoginBean login = new LoginBean();
		User user = null;
		email = email.toLowerCase();
		try {
			user = login.getUserByEmail(email);
			transaction.add("user", user);
			transaction.add("avatar", user.getAvatar());
			if (user.getAvatar().getTeam().getTeamId() != null) {
				transaction.add("team", user.getAvatar().getTeam());
			}
			LoggerServiceBean.logUserAction(user, "getProfile");
		} catch (BadRequestException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + email + "]" + e.getMessage());
		} catch (UnauthorizedRequestException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}
		return transaction;

	}

	public TransactionVO changeEmail(String oldEmail, String newEmail) {
		TransactionVO transaction = new TransactionVO();
		LoginBean login = new LoginBean();
		User user = null;
		try {
			user = login.getUserByEmail(oldEmail);
			login.changeEmail(user, newEmail);
			transaction.add("user", user);
			transaction.add("avatar", user.getAvatar());
			LoggerServiceBean.logUserAction(user, "changeEmail");
		} catch (BadRequestException e) {
			LOGGER.error("[" + oldEmail + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + oldEmail + "]" + e.getMessage(), e);
		} catch (UnauthorizedRequestException e) {
			LOGGER.error("[" + oldEmail + "]" + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[" + oldEmail + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}
		return transaction;

	}

	public TransactionVO changePassword(String email, String oldPassword,
			String newPassword) {
		TransactionVO transaction = new TransactionVO();
		LoginBean login = new LoginBean();
		User user = null;
		try {
			user = login.getUserByEmail(email);
			login.changePassword(user, oldPassword, newPassword);
			transaction.add("user", user);
			transaction.add("avatar", user.getAvatar());
			LoggerServiceBean.logUserAction(user, "changePassword");
		} catch (BadRequestException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
		} catch (UnauthorizedRequestException e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction.add("exception.message",
					"Current Password does not match ");
		} catch (Exception e) {
			LOGGER.error("[" + email + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}
		return transaction;

	}

	public TransactionVO createTeam(String teamName, String goal,
			String feedback) {
		TransactionVO transaction = new TransactionVO();
		UpdateProfileBean profileBean = new UpdateProfileBean();
		try {
			profileBean.createTeam(teamName, goal, feedback);
			transaction.add("teams", profileBean.getTeams());
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnauthorizedRequestException e) {

		} catch (Exception e) {
			LOGGER.error("[Team: " + teamName + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");// TODO
																												// Auto-generated
																												// catch
																												// block
		}
		return transaction;
	}

	public TransactionVO assignTeamToUser(String teamId, String userId) {
		TransactionVO transaction = new TransactionVO();
		UpdateProfileBean profileBean = new UpdateProfileBean();
		try {
			profileBean.assignTeamToUser(teamId, userId);
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnauthorizedRequestException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(
					"[Team: " + teamId + "| user: " + userId + "]"
							+ e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");// TODO
																												// Auto-generated
																												// catch
																												// block
		}
		return transaction;
	}
	
	public TransactionVO getLastWeekFeedback(String userId) {

		TransactionVO transaction = new TransactionVO();
		ConsultEnergyBlogBean consultEnergy = null;
		UpdateProfileBean profileBean = null;
		LoginBean login = new LoginBean();
		String email = null;
		try {
			
			User user = login.getUserByUserId(userId);
			email = user.getEmail();
			transaction.add("user", user);
			Avatar avatar = user.getAvatar();
			transaction.add("avatar", avatar);
			
			LoggerServiceBean.logUserAction(user, "getLastWeekFeedback");
			
			if (avatar.getTeam().getTeamId() != null) {
				
				transaction.add("team", avatar.getTeam());
				profileBean = new UpdateProfileBean();
				consultEnergy = new ConsultEnergyBlogBean();
				Vector<String> members = avatar.getTeam().getTeamMembers();
				Map<String, Float> unsortMap = new HashMap<String, Float>();
				// calculate standard deviation and variance
				
				double total = 0;
				double sTotal = 0;
				double scalar = 1 / (double) (members.size() - 1);
				if (!members.isEmpty()) {
					members.trimToSize();
					int count = 2;
					for (int index = 0; index < members.size(); index++) {
						try {
							String avatarIndex = "avatar";
							Avatar avatarX = profileBean
									.getAvatarFromUserId(members.get(index));
							if (!avatarX.getUserId().equals(avatar.getUserId())) {
								avatarIndex = avatarIndex + count;
								transaction.add(avatarIndex, avatarX);
								count++;
							}
							EnergyBlog eBlogOther = consultEnergy
									.getLastWeekConsumption(members.get(index));
							float standardBaseline = ((avatarX.getBaseline().getWatts()*5) / avatarX.getBaseline().getHoursBaseline()); 
							float alphaPower = eBlogOther.getPowerConsumption() / standardBaseline;
							total += alphaPower*10;
							sTotal += Math.pow(
									alphaPower*10, 2);
							unsortMap.put(avatarIndex,
									alphaPower);
							transaction.add("eblog" + index, eBlogOther);
							transaction.add("alphaPower" + index, alphaPower);
						} catch (BadRequestException e) {
							LOGGER.error("["+ email+"]" + e.getMessage(), e);
						} catch (NotFoundException e) {
							LOGGER.error("["+ email+"]" + e.getMessage());
						} catch (UnauthorizedRequestException e) {
							LOGGER.error("["+ email+"]" + e.getMessage(), e);
						}catch (Exception e) {
							LOGGER.error("["+ email+"]" + e.getMessage(), e);
						}
					}

					double mean = total / unsortMap.size();
					double variance = (scalar * (sTotal - (Math.pow(total, 2) / unsortMap
							.size())));
					double stanDev = Math.pow(variance, 0.5);
					LOGGER.debug("Mean= " + mean + "| SD = " + stanDev
							+ "| variance" + variance);
					double levelOfWater = ((mean + (stanDev * mean)) / mean)
							+ mean; //do we care about differences in the group contribution or just the total group contribution
					levelOfWater = mean;
					float water = 0;
					water = Math.round(levelOfWater) / 10;
					transaction.add("water", "" + water);
					LOGGER.debug("Water= " + water);

					List list = new LinkedList(unsortMap.entrySet());

					// sort list based on comparator
					Collections.sort(list, new Comparator() {
						public int compare(Object o1, Object o2) {
							return ((Comparable) ((Map.Entry) (o1)).getValue())
									.compareTo(((Map.Entry) (o2)).getValue());
						}
					});

					// put sorted list into map again
					Map<String, Float> sortedMap = new LinkedHashMap();
					for (Iterator it = list.iterator(); it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						sortedMap.put((String) entry.getKey(),
								(Float) entry.getValue());
					}

					int index = 1;
					for (Map.Entry entry : sortedMap.entrySet()) {
						LOGGER.debug("Key : " + entry.getKey() + " Value : "
								+ entry.getValue());
						if (index == 1)
							transaction.add("king", entry.getKey());
						else if (index == sortedMap.size())
							transaction.add("donkey", entry.getKey());
						index++;
					}

					transaction
					.add("title",
							"These results are based on the consumption of last week.");
				}
			}

		} catch (BadRequestException e) {
			LOGGER.warn("[" + userId + "|" + email + "]" + e.getMessage());
		} catch (NotFoundException e) {
			LOGGER.warn("[" + userId + "|" + email + "]" + e.getMessage());
			transaction.add("exception", e);
			transaction.add("exception.message",
					"User not found:" + userId);
		} catch (UnauthorizedRequestException e) {
			LOGGER.warn("[" + userId + "|" + email + "]" + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("[" + userId + "|" + email + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}

		return transaction;
	}

	public TransactionVO getCurrentWeekFeedback(String userId) {
		TransactionVO transaction = new TransactionVO();
		ConsultEnergyBlogBean consultEnergy = null;
		UpdateProfileBean profileBean = new UpdateProfileBean();
		try {
			Avatar avatar = profileBean.getAvatarFromUserId(userId);
			transaction.add("avatar", avatar);
			LoginBean login = new LoginBean();
			User user = login.getUserByUserId(userId);
			LoggerServiceBean.logUserAction(user, "getLastWeekFeedback");
			
			consultEnergy = new ConsultEnergyBlogBean();
			Vector<String> members = avatar.getTeam().getTeamMembers();
			if (!members.isEmpty()) {
				for (int index = 0; index < members.size(); index++) {
					Avatar avatar1 = profileBean.getAvatarFromUserId(members
							.get(index));
					transaction.add("avatar" + index, avatar1);
					EnergyBlog eBlogOther = consultEnergy
							.getCurrentWeekConsumption(members.get(index));
					transaction.add("eblog" + index, eBlogOther);
				}
			}

		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnauthorizedRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("[ user: " + userId + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}

		return transaction;
	}

	public TransactionVO getYesterdayFeedback(String userId) {
		TransactionVO transaction = new TransactionVO();
		ConsultEnergyBlogBean consultEnergy = null;
		UpdateProfileBean profileBean = null;
		LoginBean login = new LoginBean();
		String email = null;
		try {
			
			User user = login.getUserByUserId(userId);
			email = user.getEmail();
			transaction.add("user", user);
			Avatar avatar = user.getAvatar();
			transaction.add("avatar", avatar);
			
			LoggerServiceBean.logUserAction(user, "getYesterdayFeedback");
			
			if (avatar.getTeam().getTeamId() != null) {
				
				transaction.add("team", avatar.getTeam());
				profileBean = new UpdateProfileBean();
				consultEnergy = new ConsultEnergyBlogBean();
				Vector<String> members = avatar.getTeam().getTeamMembers();
				Map<String, Float> unsortMap = new HashMap<String, Float>();
				// calculate standard deviation and variance
				String day =""; 
				double total = 0;
				double sTotal = 0;
				double scalar = 1 / (double) (members.size() - 1);
				if (!members.isEmpty()) {
					int count = 2;
					members.trimToSize();
					for (int index = 0; index < members.size(); index++) {
						try {
							String avatarIndex = "avatar";
							Avatar avatarX = profileBean
									.getAvatarFromUserId(members.get(index));
							if (!avatarX.getUserId().equals(avatar.getUserId())) {
								avatarIndex = avatarIndex + count;
								transaction.add(avatarIndex, avatarX);
								count++;
							}
							EnergyBlog eBlogOther = consultEnergy
									.getLastEnergyBlog(members.get(index));
							SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
							day = dateFormat.format(eBlogOther.getLastModified());
							float standardBaseline = (avatarX.getBaseline().getWatts() / (avatarX.getBaseline().getHoursBaseline()/5)); 
							float alphaPower = eBlogOther.getPowerConsumption() / standardBaseline;
							total += alphaPower*10;
							sTotal += Math.pow(
									alphaPower*10, 2);
							unsortMap.put(avatarIndex,
									alphaPower);
							transaction.add("eblog" + index, eBlogOther);
							transaction.add("alphaPower" + index, alphaPower);
						} catch (BadRequestException e) {
							LOGGER.error("["+ email+"]" + e.getMessage(), e);
						} catch (NotFoundException e) {
							LOGGER.error("["+ email+"]" + e.getMessage());
						} catch (UnauthorizedRequestException e) {
							LOGGER.error("["+ email+"]" + e.getMessage(), e);
						}catch (Exception e) {
							LOGGER.error("["+ email+"]" + e.getMessage(), e);
						}
					}

					double mean = total / unsortMap.size();
					double variance = (scalar * (sTotal - (Math.pow(total, 2) / unsortMap
							.size())));
					double stanDev = Math.pow(variance, 0.5);
					LOGGER.debug("Mean= " + mean + "| SD = " + stanDev
							+ "| variance" + variance);
					double levelOfWater = ((mean + (stanDev * mean)) / mean)
							+ mean; //do we care about differences in the group contribution or just the total group contribution
					levelOfWater = mean;
					float water = 0;
					water = Math.round(levelOfWater)  / 10;
					transaction.add("water", "" + water);
					LOGGER.debug("Water= " + water);

					List list = new LinkedList(unsortMap.entrySet());

					// sort list based on comparator
					Collections.sort(list, new Comparator() {
						public int compare(Object o1, Object o2) {
							return ((Comparable) ((Map.Entry) (o1)).getValue())
									.compareTo(((Map.Entry) (o2)).getValue());
						}
					});

					// put sorted list into map again
					Map<String, Float> sortedMap = new LinkedHashMap();
					for (Iterator it = list.iterator(); it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						sortedMap.put((String) entry.getKey(),
								(Float) entry.getValue());
					}

					int index = 1;
					for (Map.Entry entry : sortedMap.entrySet()) {
						LOGGER.debug("Key : " + entry.getKey() + " Value : "
								+ entry.getValue());
						if (index == 1)
							transaction.add("king", entry.getKey());
						else if (index == sortedMap.size())
							transaction.add("donkey", entry.getKey());
						index++;
					}
					
					transaction
							.add("title",
									"These results are based on yesterday's consumption.</li><li>- " + day );
				}
			}

		} catch (BadRequestException e) {
			LOGGER.warn("[" + userId + "|" + email + "]" + e.getMessage());
		} catch (NotFoundException e) {
			LOGGER.warn("[" + userId + "|" + email + "]" + e.getMessage());
			transaction.add("exception", e);
			transaction.add("exception.message",
					"User not found:" + userId);
		} catch (UnauthorizedRequestException e) {
			LOGGER.warn("[" + userId + "|" + email + "]" + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("[" + userId + "|" + email + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}

		return transaction;
	}

	public TransactionVO getTodayFeedback(String userId) {
		TransactionVO transaction = new TransactionVO();
		ConsultEnergyBlogBean consultEnergy = null;
		UpdateProfileBean profileBean = new UpdateProfileBean();
		try {
			Avatar avatar = profileBean.getAvatarFromUserId(userId);
			transaction.add("avatar", avatar);
			consultEnergy = new ConsultEnergyBlogBean();
			Vector<String> members = avatar.getTeam().getTeamMembers();
			if (!members.isEmpty()) {
				for (int index = 0; index < members.size(); index++) {
					Avatar avatar1 = profileBean.getAvatarFromUserId(members
							.get(index));
					transaction.add("avatar" + index, avatar1);
					EnergyBlog eBlogOther = consultEnergy
							.getCurrentEnergyBlog(members.get(index));
					transaction.add("eblog" + index, eBlogOther);
				}
			}

		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnauthorizedRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("[ user: " + userId + "]" + e.getMessage(), e);
			transaction.add("exception", e);
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
		}

		return transaction;
	}

	public TransactionVO createAgenda(String userId, String weekHours) {
		TransactionVO transaction = new TransactionVO();
		AgendaServiceBean agendaService = new AgendaServiceBean();
		try {
			if (userId == null || userId == "null" || userId.trim() == "") {
				throw new BadRequestException("userId is null" + userId);
			}
			if (weekHours == null || weekHours == "null"
					|| weekHours.trim() == "") {
				throw new BadRequestException("weekHours is empty");
			}
			LoginBean login = new LoginBean();
			User user = login.getUserByUserId(userId);
			LoggerServiceBean.logUserAction(user, "createAgenda");
			
			agendaService.createAgenda(userId, weekHours);
		} catch (BadRequestException e) {
			transaction.add("exception.message", "Please enter a valid data:"
					+ e.getMessage());
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (UnauthorizedRequestException e) {
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (Exception e) {
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		}
		return transaction;
	}
	
	public TransactionVO createDailyAgenda(String userId, String dayHours) {
		TransactionVO transaction = new TransactionVO();
		AgendaServiceBean agendaService = new AgendaServiceBean();
		try {
			if (userId == null || userId == "null" || userId.trim() == "") {
				throw new BadRequestException("userId is null" + userId);
			}
			if (dayHours == null || dayHours == "null"
					|| dayHours.trim() == "") {
				throw new BadRequestException("weekHours is empty");
			}
			LoginBean login = new LoginBean();
			User user = login.getUserByUserId(userId);
			LoggerServiceBean.logUserAction(user, "createDailyAgenda");
			agendaService.createDayAgenda(userId, dayHours);
		} catch (BadRequestException e) {
			transaction.add("exception.message", "Please enter a valid data:"
					+ e.getMessage());
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (UnauthorizedRequestException e) {
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (Exception e) {
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		}
		return transaction;
	}
	
	public TransactionVO createWeeklyAgenda(String userId, String weekHours) {
		TransactionVO transaction = new TransactionVO();
		AgendaServiceBean agendaService = new AgendaServiceBean();
		try {
			if (userId == null || userId == "null" || userId.trim() == "") {
				throw new BadRequestException("userId is null" + userId);
			}
			if (weekHours == null || weekHours == "null"
					|| weekHours.trim() == "") {
				throw new BadRequestException("weekHours is empty");
			}
			LoginBean login = new LoginBean();
			User user = login.getUserByUserId(userId);
			LoggerServiceBean.logUserAction(user, "createWeeklyAgenda");
			agendaService.createWeekAgenda(userId, weekHours);
		} catch (BadRequestException e) {
			transaction.add("exception.message", "Please enter a valid data:"
					+ e.getMessage());
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (UnauthorizedRequestException e) {
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (Exception e) {
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		}
		return transaction;
	}



	public TransactionVO getCurrentAgenda(String userId) {
		TransactionVO transaction = new TransactionVO();
		AgendaServiceBean agendaService = new AgendaServiceBean();
		try {
			Agenda agenda = agendaService.getCurrentAgenda(userId);
			transaction.add("agenda", agenda);
			LoginBean login = new LoginBean();
			User user = login.getUserByUserId(userId);
			LoggerServiceBean.logUserAction(user, "getCurrentAgenda");
		} catch (BadRequestException e) {
			transaction.add("exception.message", "Please enter a valid data:"
					+ e.getMessage());
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		} catch (NotFoundException e) {
			LOGGER.info("[" + userId + "]" + e.getMessage());
		} catch (UnauthorizedRequestException e) {
			LOGGER.info("[" + userId + "]" + e.getMessage(), e);
		} catch (Exception e) {
			transaction
					.add("exception.message",
							"The server encountered a problem while handling your request. Please try again.");
			transaction.add("exception", e);
			LOGGER.error("[" + userId + "]" + e.getMessage(), e);
		}
		return transaction;
	}

	public TransactionVO getCurrentFeedBack(String userId) {

		TransactionVO transactionVO = null;

		GregorianCalendar calendar = new GregorianCalendar();
		int dayOfWeek = calendar.get(GregorianCalendar.DAY_OF_WEEK);

		if (dayOfWeek == GregorianCalendar.MONDAY) {
			transactionVO = this.getLastWeekFeedback(userId);
		} else {
			transactionVO = this.getYesterdayFeedback(userId);
		}

		return transactionVO;

	}

}
