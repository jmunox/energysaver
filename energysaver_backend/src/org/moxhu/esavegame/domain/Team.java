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

import java.util.Vector;


public class Team extends TimeEvent {

	public Team(){
		super();
	}
	
	private String teamId = null;
	
	private String teamName = null;
	
	private Vector<String> teamMembers = new Vector<String>(4);
	
	private GoalCondition goal = null;
	

	private FeedbackCondition feedback = null;

	
	
	/**
	 * Gets the relative URL where this portal is deployed.
	 * 
	 * @return a URL as a String
	 */
	public String getRelativeUrl() {
		return "/" + this.getTeamName() + "/";
	}

	/**
	 * Gets the URL where this portal is deployed.
	 * 
	 * @return a URL as a String
	 */
	public String getUrl() {
		
		String url = new String();
/*		String context = ContextApplication.getContextPath();
		

		if (context != null) {
			if (!context.trim().equals("")) {
				url = context + "/team/" + this.getTeamName() + "/";
			}
		}*/
		return url;
	}

	public GoalCondition getGoal() {
		return goal;
	}


	public void setGoal(GoalCondition goal) {
		this.goal = goal;
	}


	public FeedbackCondition getFeedback() {
		return feedback;
	}


	public void setFeedback(FeedbackCondition feedback) {
		this.feedback = feedback;
	}


	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public void addTeamMember(String userId){
		this.teamMembers.add(userId);
	}
	
	public Vector<String> getTeamMembers(){
		return this.teamMembers;
	}
	
	public boolean isUserTeamMember(String userId){
		return this.teamMembers.contains(userId);
	}
	
	
}
