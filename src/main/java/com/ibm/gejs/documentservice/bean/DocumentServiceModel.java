package com.ibm.gejs.documentservice.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gejs")
public class DocumentServiceModel {
	
	public String authRestURL;
	public String facadeRestURL;
	public String docServiceEndPoint;
	public String cloudApiKey;
	
	public DocumentServiceModel(String authRestURL, String facadeRestURL, String docServiceEndPoint,String cloudApiKey) {
		this.authRestURL = authRestURL;
		this.facadeRestURL = facadeRestURL;
		this.docServiceEndPoint = docServiceEndPoint;
		this.cloudApiKey = cloudApiKey;
	}
	public DocumentServiceModel() {
		this.docServiceEndPoint = "";	
	}
	
	public String getAuthRestURL() {
		return authRestURL;
	}
	public void setAuthRestURL(String authRestURL) {
		this.authRestURL = authRestURL;
	}
	public String getFacadeRestURL() {
		return facadeRestURL;
	}
	public void setFacadeRestURL(String facadeRestURL) {
		this.facadeRestURL = facadeRestURL;
	}
	public String getDocServiceEndPoint() {
		return docServiceEndPoint;
	}
	public void setDocServiceEndPoint(String docServiceEndPoint) {
		this.docServiceEndPoint = docServiceEndPoint;
	}
	public String getCloudApiKey() {
		return cloudApiKey;
	}
	public void setCloudApiKey(String cloudApiKey) {
		this.cloudApiKey = cloudApiKey;
	}
}
