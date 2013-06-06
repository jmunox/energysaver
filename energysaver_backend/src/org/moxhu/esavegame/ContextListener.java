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

package org.moxhu.esavegame;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.moxhu.MoxhuObject;
import org.moxhu.web.app.ContextApplication;


/**
 * The ContextListener class implements ServletContext listener and loads
 * initial configuration and data of the App when it is started. This listener
 * must be configured in the web.xml of the Web Application
 * 
 */
public class ContextListener implements ServletContextListener, MoxhuObject {


	/**
	 * This method is called when App is started.
	 * 
	 * @see ContextApplication.start() method
	 * @param event
	 */
	public void contextInitialized(ServletContextEvent event) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("[ContextListener] Starting ESaveGame");
		
		try {
	        ContextApplication.getInstance().start();
        } catch (Exception e) {
	        this.contextDestroyed(event);
        }

		long endTime = System.currentTimeMillis();
		LOGGER.warn("[ContextListener] ESaveGame started in "
		        + (endTime - startTime) + "ms");
	}

	/**
	 * This method is called when the App is shut down.
	 * 
	 * @see Context.stop() method
	 * 
	 * @param event
	 */
	public void contextDestroyed(ServletContextEvent event) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("[ContextListener] Stopping ESaveGame");

		ContextApplication.getInstance().stop();

		long endTime = System.currentTimeMillis();
		LOGGER.warn("[ContextListener] ESaveGame Stopped in "
		        + (endTime - startTime) + "ms");
	}

}