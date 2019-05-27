package com.comcasttest.comcasttest;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class LCSManager {

	 public static Set<String> findAllLCS(String arr[]) 
	   { 
	       int n = arr.length;      
	       
	       Set<String> set = new HashSet<>();    
	       
	       String skipString = "" ;	   
	       for(int i =0;i<n;i++) {
	    	   skipString = getLCSString(arr, skipString,set);
	    	   set.add(skipString);
	       }	       
	       
	       String longestString = Collections.max(set, Comparator.comparing(String::length));
	       
	       Set<String> finalSet = new HashSet<>();
	       for(String s : set) {
	    	   if(!(s.length()<longestString.length())) {
	    		   finalSet.add(s);
	    	
	    	   }
	       }
	       
	       
	       return finalSet; 
	   } 
	
	 private static String getLCSString(String[] arr,String skipString, Set<String> set) {
		 String res = ""; 
		 String s = arr[0]; 
		  int len = s.length(); 
		 int n = arr.length; 
		 for (int i = 0; i < len; i++) { 
	           for (int j = i + 1; j <= len; j++) {	 
	                
	               //  All substring of String s 
	               String stem = s.substring(i, j); 
	               int k = 1; 
	               for (k = 1; k < n; k++)  
	 
	            	   //checking it should contain in all strings
	            	   if (!arr[k].contains(stem)) 
	                       break; 
	                 
	               //main logic here
	               if (k == n && res.length() < stem.length() && !stem.equalsIgnoreCase(skipString) && !set.contains(stem)) 
	                   res = stem; 
	           } 
	       } 
		 
		 return res; 
	 }	
	
	 
}
