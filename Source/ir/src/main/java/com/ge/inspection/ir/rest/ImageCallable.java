package com.ge.inspection.ir.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.ge.inspection.ir.util.JSONUtil;

public class ImageCallable implements Callable<Map<String,String>>{

    @Value("${immuta.authenticate.url}")
    private String authenticateUrl;
    @Value("${immuta.image.url}")
    private String imageUrl;
    @Value("${immuta.authenticate.username}")
    private String username;
    @Value("${immuta.authenticate.password}")
    private String password;
    private String imgPath;
    	

		public ImageCallable(String imgPath, String authenticateUrl,
				String imageUrl, String username, String password) {
			this.imgPath=imgPath;
			this.authenticateUrl = authenticateUrl;
			this.imageUrl = imageUrl;
			this.username = username;
			this.password = password;
		}


		@Override
		public Map<String,String> call() throws Exception {
			
			 RestTemplate restTemplate = new RestTemplate();
		     
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_JSON_UTF8);  
			 
			 String requestJson="{\"username\":\""+username+"\",\"password\":\""+password+"\"}";

			 HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			 String response = restTemplate.postForObject(authenticateUrl, entity,String.class); 
		     Map<String,String> respMap=(Map<String, String>) JSONUtil.toObject(response, Map.class);
		     String token=respMap.get("token");
	    	
		     HttpHeaders imageHeaders = new HttpHeaders();
		    
			 imageHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8); 
			 imageHeaders.add("Authorization", "Bearer "+token);
			 imageHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			 HttpEntity<String> imageEntity = new HttpEntity<String>("parameters", imageHeaders);
			 restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			 
			// ResponseEntity<byte[]> responseEntity=restTemplate.exchange("http://10.10.34.48:8080/ir-0.0.1/inspection/media/mediaId=0002", HttpMethod.GET, imageEntity, byte[].class);
			 
			 ResponseEntity<byte[]> responseEntity=restTemplate.exchange(imageUrl.concat(imgPath), HttpMethod.GET, imageEntity, byte[].class);
			 byte[] imgByteArray=responseEntity.getBody();
			 String base64EncodedImg = DatatypeConverter.printBase64Binary(imgByteArray);
			 Map<String,String> imgMap=new HashMap<String, String>();
			 imgMap.put(imgPath, base64EncodedImg);
			 
			 return imgMap;
		}
    	
    }
