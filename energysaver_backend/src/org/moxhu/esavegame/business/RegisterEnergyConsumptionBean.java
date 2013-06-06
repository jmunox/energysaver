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
import org.moxhu.esavegame.domain.ErrorTransaction;
import org.moxhu.esavegame.domain.PowerTransaction;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

public class RegisterEnergyConsumptionBean implements MoxhuObject {
	
	public void registerInstantEnergyConsumption(String plugId, String power, String time, String ipAddress) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		//time from the CRNT has microseconds... convert then to miliseconds
		long militime = new Long(time.substring(0,13));
		float powerFloat;
		STATS_LOGGER.info("["+ ipAddress + "|" + plugId +" | " + time + " | " + power + "]");
		try{
			powerFloat = new Float(power);
		}catch(Exception e){
			LOGGER.info("["+ plugId +" | " + time + " | " + power);
			throw new BadRequestException("Float Value is not Correct:" );
		}
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		PowerTransaction transaction = new PowerTransaction();
		transaction.setPlugId(plugId);
		transaction.setWatts(powerFloat);
		transaction.setTime(militime);
		dao.savePowerTransaction(transaction);
	}
	
	
	public void registerError(String plugId, String exceptionCode, String time, String ipAddress ) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		//time from the CRNT has microseconds... convert then to miliseconds
		long militime = new Long(time.substring(0,13));
		int myException;
		STATS_LOGGER.error("["+ ipAddress + "|" + plugId +" | " + time + " | " + exceptionCode + "]");
		try{
			myException = Integer.parseInt(exceptionCode);
		}catch (Exception e){
			myException =0;
		}
		EnergySaverDAO dao = new MongoDBEnergySaverDAO(); 
		ErrorTransaction transaction = new ErrorTransaction();
		transaction.setPlugId(plugId);
		transaction.setErrorCode(myException);
		transaction.setTime(militime);
		dao.saveErrorTransaction(transaction);
	}

}
