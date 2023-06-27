package com.ibm.gejs.documentservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.apache.commons.collections4.map.MultiValueMap;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.ibm.gejs.documentservice.bean.DocumentServiceModel;
import com.ibm.gejs.documentservice.controller.DocumentServiceController;

@Service
public class DocumentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	DocumentServiceModel documentServiceModel;
	
	/**
	 * Calling AuthenticationService to generate SecurityToken
	 * @return
	 */
	public String doAuthenticate(String APIKEY) {
		 logger.info("Starting of doAuthenticate() in DocumentService");
		 String authenticationServiceURL = documentServiceModel.getAuthRestURL()+APIKEY;
		 String response =  restTemplate.postForObject(authenticationServiceURL, null, String.class);
		 logger.info("Ending of doAuthenticate() in DocumentService");
		 return response;
	}
	
	/**
	 * Calling FilenetFacade to perform CRUD Operations
	 * @throws IOException 
	 */
	public String doCallFilenetFacade(String token1, String mimeType, String className, String properties, MultipartFile[] files) throws IOException {
		logger.info("Starting of doCallFilenetFacade() in DocumentService");
		String facadeURL = documentServiceModel.getFacadeRestURL()+"token="+token1+"&MimeType="+mimeType+"&ClassName="+className;
		String response = null;
		
		//Converting MultipartFile to File to communicate with FilenetFacade REST Url
		LinkedMultiValueMap<String, Object> map =  new LinkedMultiValueMap<>();
		List<String> tempFileNames = new ArrayList<>();
	    String tempFileName;
	    FileOutputStream fo;
	    
	    try {
	    	 for (MultipartFile file : files) {
	             tempFileName = "C:\\HR_Account\\KEDM\\EclipseWorkspaceDEV\\DocumentService\\LogFile\\" + file.getOriginalFilename();
	             tempFileNames.add(tempFileName);
	             fo = new FileOutputStream(tempFileName);
	             fo.write(file.getBytes());
	             fo.close();
	             map.add("files", new FileSystemResource(tempFileName));
	         }
	    	   HttpHeaders headers = new HttpHeaders();
	           headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	           headers.add("ContentType", "");
	           headers.add("Properties", properties);

	           HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
	          logger.info("requestEntity: "+requestEntity);
	          response = restTemplate.postForObject(facadeURL, requestEntity, String.class); 
	    }
	    catch(Exception ex) {
	    	 ex.printStackTrace();
	    }
	    
	    //Removing temp Files
	    for (String fileName : tempFileNames) {
	        File f = new File(fileName);
	        f.delete();
	    }
		logger.info("Ending of doCallFilenetFacade() in DocumentService");
		 return response;
	}
}