package com.ibm.gejs.documentservice.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.gejs.documentservice.service.DocumentService;

@RestController
public class DocumentServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceController.class);
	
	@Autowired
	DocumentService documentService;

	
	/**
	 * Rest Call for of the DocumentService
	 * @return
	 * @throws IOException 
	 */
		@RequestMapping(value = "/documentservice/createDocument", 
			method = RequestMethod.POST,
			consumes = {org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {org.springframework.http.MediaType.TEXT_PLAIN_VALUE})
		
			public String performDocumentService(
					@RequestHeader(value = "APIKEY") String APIKEY,
					@RequestParam("ContentStream") MultipartFile[] file, 
					@RequestParam("MIMEType") String mimeType, 
					@RequestParam("TargetDocumentClass")String className ,
					@RequestParam("Properties") String properties) 
							throws IOException {
			
						logger.info("Starting of the performDocumentService()");
						String accessToken;
						String response;
						
						// Generating accessToken
						accessToken = documentService.doAuthenticate(APIKEY);
						
						 if (!accessToken.equalsIgnoreCase(null) && !accessToken.isEmpty()) 
						 {
							  //Verifying accessToken and Creating document into Filenet
							 response =  documentService.doCallFilenetFacade(accessToken,mimeType,className,properties,file); 
						  } 
						 else { 
							 response = "API Key is NOT Valid"; 
							 }
						logger.info("Ending of the performDocumentService()");
						return accessToken;
					}
}
