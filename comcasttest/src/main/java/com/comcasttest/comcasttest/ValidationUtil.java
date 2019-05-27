package com.comcasttest.comcasttest;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {
	
	/**
	 * This method will validate the Request for String Array for LCS 
	 * @param stringArray
	 * @return true/false 
	 * incase of invalidate request it will have map values as 
	 * false,Error Message 
	 * 
	 */
	public static boolean isValidRequest(String[] stringArray) {		
		
		/*if(checkForNullArray(stringArray)) {
			requestValidationMap.put(false, "Request Strings are Null");
			return requestValidationMap;
		}*/
		
		if(checkforEmptyString(stringArray)) {
			throw new EmptyStringValueException();			
		}
		
		List<String> countList =  getDuplicatesList(stringArray);
		if(countList.size()>0) {
			throw new DuplicateValuesException();
		}
		
		return true;		
	}
	
	/**
	 * This method checks for Null Array
	 * @param stringArray
	 * @return
	 */
	public static boolean checkForNullArray(String[] stringArray) {		
		return stringArray.length==0?true:false;
	}
	
	/**
	 * Checks for Empty Strings 
	 * @param stringArray
	 * @return
	 */
	public static boolean checkforEmptyString(String[] stringArray) {
		for(String str : stringArray) {
			if(str.chars().allMatch(c->Character.isWhitespace(c))) {
				return true;
			}
		}
		return false;
	}
	

	/** This map will return the duplicates found in the String array
	 * @param stringArray
	 */
	public static List<String>  getDuplicatesList(String[] stringArray) {
		List<String> countList = new ArrayList<>();
		for (int i = 0; i < stringArray.length-1; i++) {
			for (int j = i+1; j < stringArray.length; j++) {
				if(stringArray[i].equals(stringArray[j]) && (i != j)) {
					countList.add(stringArray[i]);					
				}
			}				
		}
		return countList;
	}
	
	
}
