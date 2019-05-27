package com.comcasttest.comcasttest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@RunWith(SpringRunner.class)
@WebMvcTest(LCSController.class)
public class TestAPI {
		
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private LCSController controller;
	
	@MockBean
	private LCSManager manager;
	
	
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

	
	 @Test
	    public void testRestAPIforSuccessResponseWithMultipleValues() throws Exception {
		 String jsonString = "{\"values\":[ { \"value\" : \"comcastsam\" },{ \"value\" : \"commusamnicate\" }, {\"value\" : \"commutatiosamn\" } ]}";
		 final String baseUrl = "http://localhost:8080/lcs";
		  URI uri = new URI(baseUrl);
		  

		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
		      .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   String expectedResult  = "{\"lcs\":[{\"value\":\"com\"},{\"value\":\"sam\"}]}";
		   assertTrue(content.equals(expectedResult));   
		 
	    }
	 
	 
	 @Test
	    public void testForSuccessAPIWithSingleValueasLCS() throws Exception {
		 String jsonString = "{\r\n" + 
			 		"	\"values\": [{\r\n" + 
			 		"			\"value\": \"comcast\"\r\n" + 
			 		"		},\r\n" + 
			 		"		{\r\n" + 
			 		"			\"value\": \"broadcast\"\r\n" + 
			 		"		},\r\n" + 
			 		"		{\r\n" + 
			 		"			\"value\": \"chromecast\"\r\n" + 
			 		"		}\r\n" + 
			 		"	]\r\n" + 
			 		"}";
		 final String baseUrl = "http://localhost:8080/lcs";
		  URI uri = new URI(baseUrl);
		  

		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
		      .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();		 
		   String expectedResult  = "{\"lcs\":[{\"value\":\"cast\"}]}";
		   assertTrue(content.equals(expectedResult));   
		 
	    }

	 
	 @Test
	    public void testRestAPIforDuplicateStrings() throws Exception {
		 String jsonString = "{\r\n" + 
			 		"	\"values\": [{\r\n" + 
			 		"			\"value\": \"comcast\"\r\n" + 
			 		"		},\r\n" + 
			 		"		{\r\n" + 
			 		"			\"value\": \"broadcast\"\r\n" + 
			 		"		},\r\n" + 
			 		"		{\r\n" + 
			 		"			\"value\": \"comcast\"\r\n" + 
			 		"		}\r\n" + 
			 		"	]\r\n" + 
			 		"}";
		 final String baseUrl = "http://localhost:8080/lcs";
		  URI uri = new URI(baseUrl);
		  

		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
		      .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(400, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   String expectedResult  = "Values must  be Set, Duplicate values found in the request";
		   assertTrue(content.equals(expectedResult));   
		 
	    }
	 
	 
	 @Test
	    public void testRestAPIforEmptyRequestBody() throws Exception {
		 String jsonString = "";
		 final String baseUrl = "http://localhost:8080/lcs";
		  URI uri = new URI(baseUrl);
		  

		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
		      .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(400, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   String expectedResult  = "Request body is empty";
		   assertTrue(content.equals(expectedResult));   
		 
	    }
	 
	 
	 @Test
	    public void testRestAPIforEmptyStrings() throws Exception {
		 String jsonString = "{\r\n" + 
			 		"	\"values\": [{\r\n" + 
			 		"			\"value\": \" \"\r\n" + 
			 		"		},\r\n" + 
			 		"		{\r\n" + 
			 		"			\"value\": \" \"\r\n" + 
			 		"		},\r\n" + 
			 		"		{\r\n" + 
			 		"			\"value\": \"\"\r\n" + 
			 		"		}\r\n" + 
			 		"	]\r\n" + 
			 		"}";
		 final String baseUrl = "http://localhost:8080/lcs";
		  URI uri = new URI(baseUrl);
		  

		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
		      .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(400, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   String expectedResult  = "Values have empty Strings , Values must not be empty";
		   assertTrue(content.equals(expectedResult));   
		 
	    }
	 
	 
	 @Test
	    public void testRestAPIforInvalidJSON() throws Exception {
		 String jsonString = "{\r\n" + 
			 		"		},\r\n" + 
			 		"		{\r\n" + 
			 		"			\"value\": \"\"\r\n" + 
			 		"		}\r\n" + 
			 		"	]\r\n" + 
			 		"}";
		 final String baseUrl = "http://localhost:8080/lcs";
		  URI uri = new URI(baseUrl);
		  

		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
		      .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(400, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   String expectedResult  = "Json Format is not correct, Please pass well-formed Json values";
		   assertTrue(content.equals(expectedResult));   
		 
	    }


	@Test
	public void testLCSManagerforMultipleValues() {
		String[] arr= {"intelligent","gentlemenintero","divergentandinte"};
		
		LCSManager manager = new LCSManager();
		Set<String> s  = manager.findAllLCS(arr);
		assertTrue(s.size()==2);
	}
	
	@Test
	public void testLCSManagerforSingleValues() {
		String[] arr= {"comcast","broadcast","chromecast"};
		
		LCSManager manager = new LCSManager();
		Set<String> s  = manager.findAllLCS(arr);
		assertTrue(s.size()==1);
	}
	
	
	
	@Test 
	public void testForDuplicateValues(){		
		
		String[] duplicateString = {"philadelphia","philly","pennsylvania","pa","philly"};
		String[] nonDuplicateStrings = {"philadelphia", "philly","cityofbrotherlylove"};		
	
		List<String> duplicateList = ValidationUtil.getDuplicatesList(duplicateString);
		assertTrue(duplicateList.size()>0);
		
		List<String> list = ValidationUtil.getDuplicatesList(nonDuplicateStrings);
		assertTrue(list.size()==0);
	}
	
	@Test
	public void testForEmptyString() {
		String[] emptyStrings = {""," "};
		assertTrue(ValidationUtil.checkforEmptyString(emptyStrings));
		
		String[] nonEmptyStrings = {"nonempty","empty","nonmetoy"};
		assertFalse(ValidationUtil.checkforEmptyString(nonEmptyStrings));
	}

}
