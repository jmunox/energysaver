package org.moxhu.esavegame.business.test;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.moxhu.MoxhuObject;
import org.moxhu.esavegame.business.ConsultEnergyBlogBean;
import org.moxhu.esavegame.dao.EnergySaverDAO;
import org.moxhu.esavegame.dao.MongoDBEnergySaverDAO;
import org.moxhu.esavegame.domain.Avatar;
import org.moxhu.esavegame.domain.EnergyBlog;
import org.moxhu.web.app.exception.BadRequestException;
import org.moxhu.web.app.exception.NotFoundException;
import org.moxhu.web.app.exception.RequestException;
import org.moxhu.web.app.exception.UnauthorizedRequestException;




public class EnergyBlogExporter implements MoxhuObject{

	
	ConsultEnergyBlogBean energyBean;
	
	public void exportToFile() throws Exception{
		
		
		
			energyBean = new ConsultEnergyBlogBean();
		
		
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

	long[] days = {
			/*19*/ 1353279600000L,
			/*20*/ 1353366000000L,
			/*21*/ 1353452400000L,
			/*22*/ 1353538800000L,
			/*23*/ 1353625200000L,
			/*24*/ 1353711600000L,
			/*25*/ 1353798000000L,
			/* week 19 - 25 nov */
			/*26*/ 1353884400000L,
			/*27*/ 1353970800000L,
			/*28*/ 1354057200000L,
			/*29*/ 1354143600000L,
			/*30*/ 1354230000000L,
			/*1 dec*/ 1354316400000L,
			/*2 dec*/ 1354402800000L,
			/* week 26 nov - 2 dec*/
			/*3*/ 1354489200000L,
			/*4*/ 1354575600000L,
			/*5*/ 1354662000000L,
			/*6*/ 1354748400000L,
			/*7*/ 1354834800000L,
			/*8*/ 1354921200000L,
			/*9*/ 1355007600000L,
			/*week 3 - 9 dec */
			/*10*/ 1355094000000L,
			/*11*/ 1355180400000L,
			/*12*/ 1355266800000L,
			/*13*/ 1355353200000L,
			/*14*/ 1355439600000L,
			/*15*/ 1355526000000L,
			/*16*/ 1355612400000L,
			/*week 10 - 16 dec */

			/*17*/ 1355698800000L,
			/*18*/ 1355785200000L,
			/*19*/ 1355871600000L,
			/*20*/ 1355958000000L,
			/*21*/ 1356044400000L
	};
			String plugwisedevice;
			int countDays = 0;
			
			int countPlugs = 0;
			
			Vector<EnergyBlog> energyLogs = new Vector<EnergyBlog>(); 
			
			PrintWriter pw = null;
			float totalWatts = 1;
			String line = "";
			String formatedDate = "";
			Format formatter;
			
			while(countPlugs<plugwiseArray.length) {
				plugwisedevice = plugwiseArray[countPlugs];
				
			long startTime = 0L;
			countDays = 0;
			
			line += plugwisedevice + "\t";
			
			while(countDays < days.length) {
				startTime = days[countDays];
				countDays++;

				//LOGGER.debug(plugwisedevice + ", " +startTime +"\n");

			
			try {
				formatter = new SimpleDateFormat("EEE dd MMM yyyy");    // 02
				
				formatedDate = formatter.format(new Date(startTime));
				line += "\t" + formatedDate + "\t";
				EnergyBlog eblog = this.getEnergyBlogsForTimePeriod(plugwisedevice ,
						startTime);
				energyLogs.add(eblog);
				// The year
				
				line += eblog.getPowerConsumption() +"\t";
				LOGGER.debug(formatedDate + "," + eblog.getPowerConsumption() +"\n");
				
				
			}catch (NotFoundException nfe){
				line += " " +"\t";
				System.out.println(nfe.getMessage());
	        }catch (RequestException re){
	        	System.out.println(re.getMessage());
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} // while time
			
			line += "\n";
		
		
		countPlugs++;
	}//while countPlugs
			
			try {
				pw = new PrintWriter(new FileWriter("/home/jmunoza/wee/HTI/graduationproject/experiments/energyblogs/total_eblog_up_19nov_with_hours.txt"));
					pw.println(line);
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				pw.close();
			}

		/*
		 
		 String[] ids = TimeZone.getAvailableIDs(-6 * 60 * 60 * 1000);
		 
		  for(String timeZone : ids){
			System.out.println(timeZone);
		}
		 SimpleTimeZone cdt = new SimpleTimeZone(-6 * 60 * 60 * 1000, ids[12]);
		Calendar calendar = new GregorianCalendar(cdt);
		System.out.println(calendar.getTime());
		calendar.setTime(new Date("Tue May 04 19:35:42 CDT 2010"));
//		calendar.HOUR
		calendar.
		System.out.println(calendar.getTime());*/
		
		
	}
	
	
	public EnergyBlog getEnergyBlogsForTimePeriod(String plugId,
			long time) throws BadRequestException, NotFoundException, RequestException, UnauthorizedRequestException{
		
		EnergySaverDAO dao = new MongoDBEnergySaverDAO();
		Avatar avatar = null;
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
		
		return energyBean.calculateEnergyBlog(avatar, time);
		
	}
	
	public static void main(String[] args){
	
		EnergyBlogExporter cvsExporter = new EnergyBlogExporter();
		try {
			cvsExporter.exportToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
