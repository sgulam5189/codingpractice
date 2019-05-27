package com.comcasttest.comcasttest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LCSController {
	
	@Autowired
	private LCSManager manager;
	
	@RequestMapping(value="/lcs", method=RequestMethod.POST)
	public  ResponseEntity<String> getLCSData(@RequestBody String jsonStr) throws JSONException,EmptyStringValueException,DuplicateValuesException{	
											
			JSONObject json = new JSONObject(jsonStr);
			 JSONArray array = json.getJSONArray(VALUES);
			 String finalValues = "";
		        List<String> list = new ArrayList<String>();
		        for(int i = 0 ; i < array.length() ; i++){
		            list.add(array.getJSONObject(i).getString(VALUE));
		        }	        
		        String[] stringArray = list.toArray(new String[0]);
		        if(ValidationUtil.isValidRequest(stringArray)) {
		        	  Set<String> lcsSet = new HashSet<>();
		        	  lcsSet = manager.findAllLCS(stringArray);
		        	  finalValues = processandFormReponse(lcsSet);		
						
		        }		
		        return ResponseEntity.ok(finalValues);
		
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleMissingRequestBody(Exception ex) {
		String message = "Request body is empty" ;
		
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}	
	
	@ExceptionHandler(EmptyStringValueException.class)
	public ResponseEntity<String> handleEmptyStringsValues(HttpServletRequest req, Exception e) 
	{
		String message = "Values have empty Strings , Values must not be empty" ;
		
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(DuplicateValuesException.class)
	public ResponseEntity<String> handleDuplicateValues(HttpServletRequest req, Exception e) 
	{
		String message = "Values must  be Set, Duplicate values found in the request" ;
		
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(JSONException.class)
	public ResponseEntity<String> handleJSONFormatException(HttpServletRequest req, Exception e) 
	{
		String message = "Json Format is not correct, Please pass well-formed Json values" ;
		
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handleUnSupportedMethod(Exception ex) {
		String message = "Method not Allowed, Only post method is Allowed" ;
		
		return new ResponseEntity<String>(message, HttpStatus.METHOD_NOT_ALLOWED);
	}	
	
	
	private static final String LCS = "lcs" ;
	private static final String VALUE = "value" ;
	private static final String VALUES = "values" ;
	
	/**
	 * This method will form the desired json 
	 * @param lcsSet (final LCS Set)
	 * @return String (final value with json structured)
	 * @throws JSONException
	 */
	private String processandFormReponse(Set<String> lcsSet) throws JSONException{
		List<String> finalList = new ArrayList<>(lcsSet);
		Collections.sort(finalList);
		JSONObject finaljson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(String str : finalList) {
			JSONObject row = new JSONObject();
			row.put(VALUE, str);
			jsonArray.put(row);
		}
		finaljson.put(LCS, jsonArray);
		String values = finaljson.toString();
		return values;
		
	}

}
