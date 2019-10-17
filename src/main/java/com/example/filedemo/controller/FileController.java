package com.example.filedemo.controller;

import com.example.filedemo.payload.UploadFileResponse;
import com.example.filedemo.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.jms.Queue;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private String fileName=null;
    @Autowired
    private FileStorageService fileStorageService;
    
  

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
    	
    	if(!file.isEmpty() )
    	       fileName = fileStorageService.storeFile(file);
    	

        return new UploadFileResponse(fileName, file.getContentType(), file.getSize());
    }

	

}
