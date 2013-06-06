package org.moxhu.esavegame.business.test;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Vector;

import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.dao.EnergySaverDAO;
import org.moxhu.esavegame.dao.MongoDBEnergySaverDAO;
import org.moxhu.esavegame.domain.Agenda;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.esavegame.domain.PowerTransaction;
import org.moxhu.exception.GeneralException;
import org.moxhu.util.config.Configurations;
import org.moxhu.util.db.mongodb.MongoDBConnectionManager;
import org.moxhu.web.app.ContextApplication;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;


public class HoursEditor implements MoxhuObject{

	public static final String PROPERTIES_FILE = "exporter.properties";
	
	DB db = null;
	
	public void editHours(){
		
		Properties props = new Properties();
		try{
			props.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			try {
			ContextApplication.getInstance().start();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			Configurations conf = new Configurations(props, PROPERTIES_FILE); 
		
			
			String[] plugwiseArray = { 
					"00728967",
					"00728981",
					"00728983",
					"00728C99",
					"00728D6F",
					"00728E46",
					"00728E66",
					"007290C9",
					"007296AB",
					"00729808",
					"00729886",
					"00729903",
					"00729915",
					"00729D6B",
					"00729D8D",
					"00729DD1",
					"0072A23E",
					"0072A278",
					"0072A2A1",
					"0072B401",
					"0072B645",
					"00AF6325",
					"00C3CEBA",
					"00D327F5",
					"00D327FF",
					"00D328D9",
					"00D33D78",
					"00D33D8C",
					"00D368EB",
					"01A44514",
					"01A4453C",
					"01A44573",
					"02588111",
					"0258816B",
					"02588B35",
					"02588BAE",
					"02588BD4",
					"025F6A28",
					"025F6A2C",
					"025F6A4F",
					"025F6AB0",
					"025F6AC3",
					"025F6BFC",
					"025F6C03",
					"025F6C11",
					"02604664",
					"026048B7",
					"026048ED",
					"026048F8",
					"02604908",
					"02604938",
					"0260493A",
					"02604952",
					"026049C0",
					"026049F1",
					"02604A05",
					"02604A24",
					"02604A9C",
					"0261AFF2",
					"0261B009",
					"0261B1C2",
					"0261B2C4",
					"0261B2EF",
					"0261B3E9",
					"0261B42C",
					"0261B464",
					"0261B561",
					"0261B570",
					"0261B6E1",
					"0261B6E4",
					"0261B6EA",
					"02620810"
			};

	int[] defaultHours = {
			40,
			40,
			40,
			30,
			30,
			40,
			40,
			20,
			40,
			40,
			5,
			40,
			40,
			40,
			30,
			40,
			40,
			30,
			10,
			30,
			30,
			30,
			30,
			40,
			30,
			40,
			40,
			30,
			30,
			10,
			30,
			10,
			40,
			40,
			40,
			30,
			40,
			40,
			30,
			30,
			30,
			40,
			30,
			40,
			30,
			30,
			40,
			30,
			30,
			40,
			10,
			10,
			30,
			30,
			30,
			30,
			30,
			30,
			40,
			10,
			30,
			20,
			20,
			30,
			10,
			40,
			30,
			10,
			10,
			30,
			40,
			30
	};
	
	long [] weeks = {
			1353279600000L,
			1353884400000L,
			1354489200000L,
			1355094000000L,
			1355698800000L
	};
	
			String plugwisedevice; 
			
			int countWeeks = 0;
			while(countWeeks<weeks.length) {
			int countPlugs = 0;
			while(countPlugs<plugwiseArray.length) {
				plugwisedevice = plugwiseArray[countPlugs];
				int defaultHourPercentage = Math.round((defaultHours[countPlugs] * 100) / 40); 
				
				

			
			
			
			
				
			//LOGGER.debug("Starting: " + plugwisedevice); 
			try {
				this.getAgenda(plugwisedevice ,
						weeks[countWeeks],  defaultHourPercentage);
			} catch (RequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			countPlugs++;
		}//while countPlugs
			
			countWeeks++;
		} // while time
		

		
		
	}
	
	public void getAgenda(String plugId, long time, int defaultHours) throws RequestException {
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		Avatar avatar = null;
		Agenda agenda = null;
		try {
			 avatar = dao.getAvatarFromPlugId(plugId);
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnauthorizedRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			agenda = dao.getAgenda(avatar, time);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.debug("No agenda "+ time +" for:" + plugId);
			agenda = new Agenda();
			agenda.setAvatar(avatar);
			agenda.setDate(new Date(time));
			agenda.setPercentageWeek(defaultHours);
			try {
				//dao.saveAgenda(agenda);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
		//LOGGER.debug(time +" ," + plugId + "," + agenda.getPercentageWeek() + "," + ((agenda.getPercentageWeek()*40)/100));
		System.out.println(time + " ," + ((agenda.getPercentageWeek()*40)/100));
		
	}
	
	
	public static void main(String[] args){
	
		HoursEditor editor = new HoursEditor();
		editor.editHours();
	}
}
