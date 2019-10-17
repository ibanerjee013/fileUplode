package com.example.filedemo.pojo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MessagePogo {

	private  Map<String,String> csvParse= new HashMap<String,String>();
	
	public void MessagePogo()
	{
		
	}

	public Map<String, String> getCsvParse() {
		return csvParse;
	}

	public void setCsvParse(Map<String, String> csvParse) {
		this.csvParse = csvParse;
	}

	@Override
	public String toString() {
		return "MessagePogo [csvParse=" + csvParse + "]";
	}
	
	
	 
}
