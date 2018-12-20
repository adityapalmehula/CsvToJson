/**
* The JsonValidatorClass class to check all validation of json file
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.validators;
import com.ncu.exceptions.*;

import java.util.Scanner; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File; 
import java.io.FileInputStream;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.InputStream;
import java.util.Properties;

public class JsonValidatorClass{
	
	String outputFolderPath= System.getProperty("user.dir")+ File.separator+"jsons/";
	String jsonFileName;
	String convertAcceptExtension = "json";
	JsonValidatorClass currentobj;
	Properties prop = new Properties();
	InputStream input = null;
	String configMessages = System.getProperty("user.dir")+ File.separator + "configs/constants/exceptions.properties";
    int fileLength;
	String RegexValue;
	String configValidation = System.getProperty("user.dir")+ File.separator + "configs/constants/constants.properties";
    
	/* method to check all validation of json file  */

	public boolean jsonValidatorMethod(String takejsonFileName)
	{   
		Logger logger = Logger.getLogger(JsonValidatorClass.class);
		String log4jConfigFile = System.getProperty("user.dir")+ File.separator + "configs/logger/logger.properties";
		BasicConfigurator.configure();
		PropertyConfigurator.configure(log4jConfigFile);
		
		String jsonFileName=takejsonFileName;
		JsonValidatorClass objvalid=new JsonValidatorClass();
		objvalid.jsonFileName=takejsonFileName;

		try{

			input = new FileInputStream(configMessages);
		// load a properties file
			prop.load(input);

		// Generate "EmptyNameException" Exception if gives blank space as a file name
			objvalid.empltyFileMethod(jsonFileName);

        // Generate "FileFormatException" Exception if user does not . before extension
			objvalid.fileFormat(jsonFileName);
			
        // Generate "FileFormatException" Exception if user does not . before extension
			objvalid.dotMessingMethod(jsonFileName);

        // Generate "InvalidFileException" Exception if user give other then csv file extension. 
			objvalid.invalidFileMethod(jsonFileName);

        // Generate "SpecialCharacterException" Exception if user does not . before extension
			objvalid.specialCharacterMethod(jsonFileName);

        // Generate "FileNameLengthException" Exception if user more then 25 character as a file name .
			objvalid.lengthMethod(jsonFileName);

        // Method to check user given file name is not already into directory.

			Boolean fileExists= new File(outputFolderPath+jsonFileName).exists();
			if(fileExists)
			{   
				logger.error("Oops.. This File Is Already Exist Into Directory , Please Try With Different Name...!\n");
				return false;
			}
			
		}

        // All Excetion will taken in this section 
        catch(EmptyNameException e){
			logger.error("\n \n"+e+prop.getProperty("emptyNameMessage")+"\n");
			return false;
		}
        
        catch(FileFormatException e){
			logger.error("\n \n"+e+prop.getProperty("notDotJsonFormat")+"\n");
			return false;
		}

		catch(InvalidFileException e){
			logger.error("\n \n"+e+prop.getProperty("invalidJsonExtension")+"\n");
			return false;
		}

		catch(SpecialCharacterException e){
			logger.error("\n \n"+e+prop.getProperty("specialcharacterMessage")+"\n");
			return false;
		}
        
        catch(FileNameLengthException e){
			logger.error("\n \n"+e+prop.getProperty("longFileNameMessage")+"\n");
			return false;
		}

		catch(Exception e){
			logger.error("\n \n"+e+"\n");
			return false;
		}

		return true;
	}

	/* Generate "EmptyNameException" Exception if gives blank space as a file name  */

	private void empltyFileMethod(String jsonFileName) throws EmptyNameException {
		if (jsonFileName == null || jsonFileName.trim().isEmpty()) {
			throw new EmptyNameException("");
		}
	}

	private void fileFormat(String jsonFileName) throws FileFormatException{
		Pattern costPattern = Pattern.compile("[.]");
		Matcher costMatcher = costPattern.matcher(jsonFileName);
		Boolean value = costMatcher.find();
		if(!value){
			throw new FileFormatException("");
		}
	}

	private void dotMessingMethod(String jsonFileName) throws InvalidFileException {
		String [] haveExtenstion= jsonFileName.split("\\.");
		if (haveExtenstion.length<=1) {
			throw new InvalidFileException("");
		}
	}

	private void invalidFileMethod(String jsonFileName) throws InvalidFileException {
		String name = jsonFileName.split("\\.")[0];
		String currentExtension = jsonFileName.split("\\.")[1];
		if(!this.convertAcceptExtension.equals(currentExtension)){
			throw new InvalidFileException("");
		}
	}

	private void specialCharacterMethod(String jsonFileName) throws SpecialCharacterException{
		String nameGet = jsonFileName.split("\\.")[0];
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

	private void lengthMethod(String jsonFileName) throws FileNameLengthException {
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
		String namelength = jsonFileName.split("\\.")[0];
		if(namelength.length()>this.fileLength){
			throw new FileNameLengthException("");
		}
	}
}