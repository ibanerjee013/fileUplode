package com.example.filedemo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.filedemo.controller.FileController;
import com.example.filedemo.parser.Parser;
import com.example.filedemo.service.FileStorageService;


@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
@SpringBootTest
public class FileDemoApplicationTests {

	
	   @Autowired
	    MockMvc mockMvc;
	   
	   @MockBean
	   FileStorageService fileStorageService;
	   
	   
	 private final String  fileNameLocation="spring-boot-file-upload-download-rest-api-example-master/TestData/statements2.csv";
	   
	@Test
	public void contextLoads() throws Exception{
	
   RequestBuilder request = MockMvcRequestBuilders.post("/uploadFile").accept(MediaType.APPLICATION_JSON_UTF8);
   
   //MvcResult result= mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().)
		
		
		
		
		

		


	}
}

