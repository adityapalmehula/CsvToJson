/**
* The CsvToJson program implements an application that
* take a csv file as a input and convert into json file  
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.main;
import com.ncu.validators.*;
import com.ncu.processors.*;

// java package import
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.util.Scanner;
import java.io.File;


public class JSONConverter{

	String takenCsvFileName;
	String takenJsonFileName;
	String exitValue = "exit";
	String permissionValue="y";
  String log4jConfigFile;

	/* main method - process will be start from here and calling  validatorMethod method to check 
	all validation of csv file */

	public static void main(String... s)
	{
		System.out.println("=======================================");
		System.out.println("||        Welcome NCU Students       ||");
		System.out.println("=======================================");
		JSONConverter objectJson=new JSONConverter();
		objectJson.takeCsvInput();
	}

	public void takeCsvInput(){

		JSONConverter takeObj=new JSONConverter();

        /* Taking csv file from user and Perform trim and lower case operation */
        Logger logger = Logger.getLogger(JSONConverter.class);
        String log4jConfigFile = System.getProperty("user.dir")+ File.separator + "configs/logger/logger.properties";
        this.log4jConfigFile = log4jConfigFile;
        BasicConfigurator.configure();
        PropertyConfigurator.configure(log4jConfigFile);

        Scanner sc = new Scanner(System.in);
        logger.info(" !...(*.*) Please Enter Your Csv File Name __:- ");

        String filename = sc.nextLine();
        String trimedFile = filename.trim();
        String csvFileName = trimedFile.toLowerCase();
        this.takenCsvFileName=csvFileName;

        /* Creating object of CsvValidatorClass class to check all validation related  to csv file */

        CsvValidatorClass csvObject=new CsvValidatorClass();
        boolean checkValidator=csvObject.validatorMethod(csvFileName);
        JSONConverter dObject=new JSONConverter();
        if(checkValidator){
            dObject.takeJsonInput(csvFileName);
        }else{
            dObject.takeCsvInput();
        }
    }

    /* Creating object of CsvValidatorClass class to check all validation related 
    to csv file */

    public void takeJsonInput(String csvFileNametaken)
    {   
    	  String finalCsvname=csvFileNametaken;
    	  JSONConverter takeObj=new JSONConverter();
    	  ConversionClass processObj=new ConversionClass();

    	  Logger logger = Logger.getLogger(JSONConverter.class);
        String log4jConfigFile = System.getProperty("user.dir")+ File.separator + "configs/logger/logger.properties";
        BasicConfigurator.configure();
        PropertyConfigurator.configure(log4jConfigFile);

        Scanner sc = new Scanner(System.in);
        logger.info("!...(*.*) What Will Be Your Converted File Name, Please Enter__:- ");
        String jFilename = sc.nextLine();
        String trimedFile = jFilename.trim();
        String jsonFileName = trimedFile.toLowerCase();
        this.takenJsonFileName=jsonFileName;
        JsonValidatorClass obj=new JsonValidatorClass();
        boolean checkjsonValidator=obj.jsonValidatorMethod(jsonFileName);
        if(checkjsonValidator)
        {
          boolean processValidator=processObj.processMethod(finalCsvname,jsonFileName);
          if(processValidator){
             takeObj.systemCloseMethod();
         }else{
             takeObj.takeJsonInput(finalCsvname);
         }
     }
     else{
      takeObj.takeJsonInput(finalCsvname);
  }
}
    /* Ask permission to close the program after successfully complete
    process */

    public void systemCloseMethod()
    {   

      Logger logger = Logger.getLogger(JSONConverter.class);
      String log4jConfigFile = System.getProperty("user.dir")
      + File.separator + "configs/logger/logger.properties";
      BasicConfigurator.configure();
      PropertyConfigurator.configure(log4jConfigFile);
      Scanner sc = new Scanner(System.in);
      logger.info("Do You Want Exit . Please Write - exit ");
      logger.info("Or Write Any Key To Continue Program - ");
      Scanner eobject = new Scanner(System.in);
      String permissionExit = eobject.nextLine();
      if(exitValue.equalsIgnoreCase(permissionExit)){
          System.exit(0);
      }else{
          JSONConverter processAgain=new JSONConverter();
          processAgain.takeCsvInput();
      }
  } 
}