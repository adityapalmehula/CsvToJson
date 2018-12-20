/**
* The FileNameLengthException class which generate    
* exception if user will give file name more then 25 characters 
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2018-12-15 
*/

package com.ncu.exceptions;

public class FileNameLengthException extends Exception{  
 public FileNameLengthException(String s){  
  super(s);  
 }  
}  