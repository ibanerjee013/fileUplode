package com.example.filedemo.parser;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class Parser {

	   @Autowired
	    JmsTemplate jmsTemplate;
	    
	    @Autowired
	    Queue queue;	
	public Map<String,String>  csvParser(String fileNameLocation) {

        

        Map<String,String> mp= new HashMap<String,String>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(fileNameLocation));
            String[] line;
           
            while ((line = reader.readNext()) != null) {
            	if(line[0].equals("transaction_id")) {
            		continue;
            		}
            	else {	
            		Double 	ammount= Double.valueOf(line[2]);
           
            	if(ammount>10000 || ammount<-10000)
            	{	mp.put(line[2], line[3]);
            	 String message=" [value= " + line[2] + "\n " +" Date ="  + line[3];
                 jmsTemplate.convertAndSend(queue, message);
            	}
            	
            	}
            	
            	
            }
            reader.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        }

return mp;
    }
	
	///New Parser
	
	public Map<String,String>  csvParser2(String fileNameLocation) {
		  Map<String,String> mp= new HashMap<String,String>();
		//String csvFile = "C:/Users/indra/Downloads/statementsBig.csv" ;
		 Path path = Paths.get(fileNameLocation);
		 String value; 
      String date;
	         // TODO: Add a check for valid file existing.

	    
	         try
	         {
	                  

	             // Read CSV file. For each row, instantiate and collect `DailyProduct`.
	             BufferedReader reader = Files.newBufferedReader( path );
	             Iterable < CSVRecord > records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse( reader );
	             
	             for ( CSVRecord record : records )
	             {   
	            	 Double ammount= Double.valueOf(record.get( "value" ));
	            	 if(ammount>10000 || ammount<-10000)
	                 { value = record.get( "value" );
	                  date = record.get( "date" );
	                  mp.put(value, date);
	               
	               
	                 // Instantiate `Person` object, and collect it.
	                              
	                 
	                 value=null; date =null;
	                 }
	             }
	         } catch ( IOException e )
	         {
	             e.printStackTrace();
	         }
	       return mp;
	     }
	
	
	
	
	
}
