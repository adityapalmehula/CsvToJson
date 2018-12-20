/**
* The ConversionClass is main Proccess file where   
* we have writen main login to convert csv file into json file
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.processors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter; 
import java.util.Scanner;
import java.util.Date;
import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ConversionClass{
	
	String csvFileName;
	String jsonFileName;
	String databasePath =System.getProperty("user.dir")+ File.separator+"csvs/";
	String outputFolderPath=System.getProperty("user.dir")+ File.separator+"jsons/";
	String line;
	String cvsSplitBy = ",";
	ConversionClass conversionObj;
	String permissionValue="y";

	@SuppressWarnings("unchecked")
	public boolean processMethod(String csvFileName,String jsonFileName){

		this.csvFileName=csvFileName;
		this.jsonFileName=jsonFileName;
		ConversionClass conObject =new ConversionClass();
		this.conversionObj=conObject;
		Logger logger = Logger.getLogger(ConversionClass.class);
		String log4jConfigFile = System.getProperty("user.dir")
		+ File.separator + "configs/logger/logger.properties";

		BasicConfigurator.configure();
		PropertyConfigurator.configure(log4jConfigFile);
		
		try{
			// Asking last permission to convert file
			    logger.info("Do You Want To Convert This Csv data into Json ..? Please Press y/n :-");

			    Scanner perobject = new Scanner(System.in);
			    String takePermission = perobject.nextLine();
			    if(permissionValue.equalsIgnoreCase(takePermission)){

		    // display before file convert 
				logger.info("-------------------------- Time Duration --------------------------");
				logger.info("Time Before Process :- "+new Date( ));
				long start = System.currentTimeMillis( );

			// Reading file from directory by using BufferedReader

				BufferedReader br = new BufferedReader(new FileReader(databasePath+csvFileName));
				String firstLine = br.readLine();
				String[] keyName = firstLine.split(cvsSplitBy);
				JSONArray jArry = new JSONArray();

			// writing file into user given file by using BufferedWriter 
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFolderPath+jsonFileName))) {
					while ((line = br.readLine()) != null) {
						JSONObject jObj = new JSONObject();
						String[] dataLine = line.split(cvsSplitBy);
						for(int i=0;i<dataLine.length;i++){
							jObj.put(keyName[i],dataLine[i]);
						}
						jArry.add(jObj);
					}
					bw.write(jArry.toJSONString());
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("Format of this Csv file is not valid plz try other csv file.....Plz try Again !");
		            return false;
				}
				logger.info("Time After Process :- "+new Date( ));
				long end = System.currentTimeMillis( );
				long diff = end - start;
				logger.info("Duration In MilliSecond -: " + diff+"ms");
				logger.info("-----------------------------------------------------------------");
			}else{
				return false;
			}

		}catch (Exception e) {
			logger.info("Internal Problem  Occurred Please Try Again..... !"+e+"\n"+"\n");
			return false;
		}
		logger.info("______! Successfully Converted This File Into Json - (*.*)");
		logger.info("\n \n 'Notice' - You Can Get Your "+this.jsonFileName+" On Path - "+"'"+outputFolderPath+"'"+"\n\n");
		return true;
	}

}