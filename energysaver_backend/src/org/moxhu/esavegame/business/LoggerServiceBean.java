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


import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.dao.EnergySaverDAO;
import org.moxhu.esavegame.dao.MongoDBEnergySaverDAO;
import org.moxhu.esavegame.domain.User;
import org.moxhu.esavegame.domain.UserLogTransaction;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

public class LoggerServiceBean implements MoxhuObject {

	public LoggerServiceBean() {
		// TODO Auto-generated constructor stub
	}
	
	public static void logUserAction(User user, String action) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		long time = System.currentTimeMillis();
		
		try{
			dao.logUserAction(new UserLogTransaction(user, time, action));
		}catch(Exception e){
			LOGGER.error("LogUserAction not working: " + user.getUserId() + ":"+ action, e);
						
		}
	}
	
}
