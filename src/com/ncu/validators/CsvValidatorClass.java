/**
* Created this class to check all validation of file name
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.validators;
import com.ncu.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CsvValidatorClass{

	String csvFileName;
	String databasePath = System.getProperty("user.dir")+ File.separator+"csvs/";
	String line;
	String acceptableExtension = "csv";
	BufferedReader br;
	String cvsSplitBy = ",";
	CsvValidatorClass object;
	String permissionValue="y";
	String configMessages = System.getProperty("user.dir")+ File.separator + "configs/constants/exceptions.properties";
	Properties prop = new Properties();
	InputStream input = null;
	int fileLength;
	String RegexValue;
	String configValidation = System.getProperty("user.dir")+ File.separator + "configs/constants/constants.properties";
    
	/* method to check all validation of csv file  */

	public boolean validatorMethod(String csvFileName)
	{   
		
		Logger logger = Logger.getLogger(CsvValidatorClass.class);
		String log4jConfigFile = System.getProperty("user.dir")+ File.separator + "configs/logger/logger.properties";
		BasicConfigurator.configure();
		PropertyConfigurator.configure(log4jConfigFile);

		this.csvFileName=csvFileName;
		CsvValidatorClass object=new CsvValidatorClass();
		object.csvFileName=csvFileName;
		try{

			input = new FileInputStream(configMessages);
		// load a properties file
			prop.load(input);

        // Generate "EmptyNameException" Exception if gives blank space as a file name
			object.empltyFileNameMethod(csvFileName);

        // Generate "FileFormatException" Exception if user does not . before extension
			object.messingDotMethod(csvFileName);

		// Generate "InvalidFileException" Exception if user does not give any extension after file name. 
			object.fileFormatMethod(csvFileName);

		// Generate "SpecialCharacterException" Exception if user does not . before extension
			object.specialCharacterMethod(csvFileName);
			
		// Generate "InvalidFileException" Exception if user give other then csv file extension. 
			object.acceptOnlyCsvMethod(csvFileName);
			
		// Generate "FileNameLengthException" Exception if user more then 25 character as a file name .
			object.fileLengthMethod(csvFileName);
			
		// Generate "FileNotAvailable" Exception if user given file is not available into our directory
			object.fileNotAvailableMethod(csvFileName);


		}

        // All Excetion will taken in this section 
		catch(EmptyNameException e){

			logger.error("\n \n"+e+prop.getProperty("emptyNameMessage")+"\n");
			return false;
		}

		catch(FileFormatException e){

			logger.error("\n \n"+e+prop.getProperty("extensionMessage")+"\n");
			return false;
		}

		catch(InvalidFileException e){
			logger.error("\n \n"+e+prop.getProperty("notCsvMessage")+"\n");
			return false;
		}
        
        catch(FileNameLengthException e){
			logger.error("\n \n"+e+prop.getProperty("longFileNameMessage")+"\n");
			return false;
		}

		catch(SpecialCharacterException e){
			logger.error("\n \n"+e+prop.getProperty("specialcharacterMessage")+"\n");
			return false;
		}

		catch(FileNotAvailable e){
			CsvValidatorClass fileShowObj=new CsvValidatorClass();
			fileShowObj.showAllFiles(e);
			return false;
		}

		catch(Exception e){
			logger.error("\n"+e+"\n"+"\n");
			return false;
		}
		
		return true;
	}

	/* Generate "EmptyNameException" Exception if gives blank space as a file name  */

	private void empltyFileNameMethod(String csvFileName) throws EmptyNameException {	
		if (csvFileName == null || csvFileName.trim().isEmpty()) {
			throw new EmptyNameException("");
		}
	}

	/* Generate "FileFormatException" Exception if user does not . before extension  */
	
	private void messingDotMethod(String csvFileName) throws FileFormatException {
		Pattern costPattern = Pattern.compile("[.]");
		Matcher costMatcher = costPattern.matcher(csvFileName);
		boolean value = costMatcher.find();
		if(!value){
			throw new FileFormatException("");
		}
	}

	/* Generate "InvalidFileException" Exception if user does not give any extension after file name. */

	private void fileFormatMethod(String csvFileName) throws InvalidFileException {
		String [] haveExtenstion= csvFileName.split("\\.");
		if (haveExtenstion.length<=1) {
			throw new InvalidFileException("");
		}

	}

	private void specialCharacterMethod(String csvFileName) throws SpecialCharacterException{
		String nameGet = csvFileName.split("\\.")[0];
		try{
		FileInputStream validSet = new FileInputStream(configValidation);
		Properties propSetvalue = new Properties();
	    propSetvalue.load(validSet);
	    String regexValue = propSetvalue.getProperty("fileRegex");
	    this.RegexValue=regexValue;
	    }catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	    Pattern  patternGet = Pattern.compile("["+this.RegexValue+"]");
		Matcher check = patternGet.matcher(nameGet);
		boolean finalValue = check.find();
        if (finalValue == true){
           throw new SpecialCharacterException("");
         }
	}

	/* Generate "InvalidFileException" Exception if user give other then csv file extension. */

	private void acceptOnlyCsvMethod(String csvFileName) throws InvalidFileException {
		String name = csvFileName.split("\\.")[0];
		String currentExtension = csvFileName.split("\\.")[1];
		if(!this.acceptableExtension.equals(currentExtension)){
			throw new InvalidFileException("");
		}
	}

	/* Generate "FileNameLengthException" Exception if user more then 25 character as a file name . */

	private void fileLengthMethod(String csvFileName) throws FileNameLengthException {
		try{
		FileInputStream validationSet = new FileInputStream(configValidation);
		Properties propSet = new Properties();
	    propSet.load(validationSet);
	    String lengthValue = propSet.getProperty("fileNameLength");
	    int getLength=Integer.parseInt(lengthValue); 
	    this.fileLength=getLength;
	    }catch(Exception e)
	    {
	    	System.out.println(e);
	    }
		String namelength = csvFileName.split("\\.")[0];
		if(namelength.length()>this.fileLength){
			throw new FileNameLengthException("");
		}
	}

	/* Generate "FileNotAvailable" Exception if user given file is not available into our directory */

	private void fileNotAvailableMethod(String csvFileName) throws FileNotAvailable {
		if(!new File(databasePath+csvFileName).exists()){
			throw new FileNotAvailable("");
		}
		
	}

	/* Method to display all files which available into directory  */

	public void showAllFiles(Exception expObj){ 

		Logger logger = Logger.getLogger(CsvValidatorClass.class);
		String log4jConfigFile = System.getProperty("user.dir")+ File.separator + "configs/logger/logger.properties";
		BasicConfigurator.configure();
		PropertyConfigurator.configure(log4jConfigFile);

		try{
			input = new FileInputStream(configMessages);
			prop.load(input);
			logger.info("\n \n"+expObj+prop.getProperty("notAvailableMessage")+"\n");
			logger.info("Do You Want To See List Of Available csv file Press - y/n  ");
		}catch(Exception e)
		{
			logger.error(e);
		}
		
		Scanner sobject = new Scanner(System.in);
		String permission = sobject.nextLine();

		if(permissionValue.equalsIgnoreCase(permission)){
			File file = new File(databasePath);
			logger.info("----------- All Available Files In Directory --------- ");

			File[] files = file.listFiles();
			for(File f: files){
				logger.info("-"+f.getName());
			}
			logger.info("------------------------------------------------------ ");
		}
	}
}