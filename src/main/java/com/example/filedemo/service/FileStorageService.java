package com.example.filedemo.service;

import com.example.filedemo.exception.FileStorageException;
import com.example.filedemo.exception.MyFileNotFoundException;

import com.example.filedemo.parser.Parser;
import com.example.filedemo.pojo.MessagePogo;
import com.example.filedemo.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.jms.Queue;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final String CSV="csv";
    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    @Autowired
    private Parser parser;
    @Autowired
    private MessagePogo message;
    
    @Autowired
    JmsTemplate jmsTemplate;
    
    @Autowired
    Queue queue;
    
    public String storeFile(MultipartFile file) {
        // Normalize file name
    	String fileName=null;
    	if(!file.isEmpty())
         fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Map<String,String> csvParse= new HashMap<String,String>();
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..") ) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            if(CSV.equals(getExtensionByStringHandling(fileName).get()) ) {
            	 // Copy file to the target location (Replacing existing file with the same name)
            
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
               String fileNameLocation= targetLocation.toString();
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                
                csvParse= parser.csvParser2(fileNameLocation);
                Files.delete(targetLocation);
                            
            }
            else {
            	throw new FileStorageException("Sorry! File type is invalid  " + fileName);
            }
            
          //  message.setCsvParse(csvParse);
            System.out.println("Message shared with JMS");
			
			  for (Map.Entry<String,String> entry : csvParse.entrySet()) {
			  
			  String message=" [value= " + entry.getKey() + "\n " +" Date =" +
			  entry.getValue(); jmsTemplate.convertAndSend(queue, message); message=null; 
			  }
			
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

	    
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
    
    /*
	 * public Resource loadFileAsResource(String fileName) { try { Path filePath =
	 * this.fileStorageLocation.resolve(fileName).normalize(); Resource resource =
	 * new UrlResource(filePath.toUri()); if(resource.exists()) { return resource; }
	 * else { throw new MyFileNotFoundException("File not found " + fileName); } }
	 * catch (MalformedURLException ex) { throw new
	 * MyFileNotFoundException("File not found " + fileName, ex); } }
	 */
}
