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

import java.io.File;

/**
 */
public class Avatar extends TimeEvent {


	private String avatarName = null;
	
	private String rootPath = null;

	private String avatarImage = null;
	
	private String plugwiseDevice = null;
	
	private Team team = null;

	private String userId = null;
	
	private String location = null;
	
	private Gender gender = null;
	
	private Baseline baseline = null;
	
	//private GoalCondition goal = null;

	//private FeedbackCondition feedback = null;
	
	private int age = 0;


	public Avatar(){
		super();
		team = new Team();
		baseline = new Baseline();
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Baseline getBaseline() {
		return baseline;
	}

	public void setBaseline(Baseline baseline) {
		this.baseline = baseline;
	}



	/**
	 * Gets the Internet avatarName/path for this portal.
	 * 
	 * @return a String representing an absolute path
	 */
	public String getAvatarName() {
		return avatarName;
	}
	
	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
		this.rootPath = avatarName.trim().toLowerCase();
	}



	/**
	 * Gets the URL of the  image 
	 * 
	 * @param a
	 *            pointing to an image
	 */
	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}

	/**
	 * Gets the image
	 * 
	 * @return a URL pointing to an image
	 */
	public String getAvatarImage() {
		return avatarImage;
	}

	/**
	 * Gets the relative URL where this user blog is deployed.
	 * 
	 * @return a URL as a String
	 */
	public String getRelativeUrl() {
		return "/" + this.getRootPath() + "/";
	}

	public String getRootPath() {
		return rootPath;
	}


	/**
	 * Gets the URL where this portal is deployed.
	 * 
	 * @return a URL as a String
	 */
	public String getUrl() {
		
		String url = new String();
		/*String context = ContextApplication.getContextPath();
		

		if (context != null) {
			if (!context.trim().equals("")) {
				url = context + "/" + this.getRootPath() + "/";
			}
		}*/
		return url;
	}

	/**
	 * Gets the location where the Portal images are stored.
	 * 
	 * @return an absolute, local path on the filing system
	 */
	public String getImagesDirectory() {
		return getRootPath() + File.separator + "mediafiles" + File.separator + "images";
	}

	
	/**
	 * Gets a string representation of this object.
	 * 
	 * @return a String
	 */
	public String toString() {
		return getUserId() + ":" + getRootPath();
	}
	
	public String getPlugwiseDevice() {
		return plugwiseDevice;
	}

	public void setPlugwiseDevice(String plugwiseDevice) {
		this.plugwiseDevice = plugwiseDevice;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Gender getGender() {
		return gender;
	}
	//male = false; female = true;
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	/*public GoalCondition getGoal() {
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
*/
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
