package com.lowes.urlShortnerService.UrlHelper;

import org.springframework.stereotype.Service;

@Service
public class UrlConvertor {
	
	private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedCharacters = allowedString.toCharArray();
    private int base = allowedCharacters.length;
    
    public String encodeUrl(long urlId) {
    	String encodedUrl = "";
    	if(urlId==0) {
    		return String.valueOf(allowedCharacters[0]);
    	}
    	while(urlId > 0) {
    		encodedUrl += encodedUrl.concat(String.valueOf(allowedCharacters[(int) (urlId%base)]));
    		urlId /= base;
    	}
    	return encodedUrl;
    }
    
    public long decodeUrl(String encodedUrl) {
    	char[] encodedUrlAsCharArray = encodedUrl.toCharArray();
    	int encodedUrlLength = encodedUrlAsCharArray.length;
    	long urlId = 0;
    	int counter = 1;
    	for(int encodedUrlIndex=0; encodedUrlIndex < encodedUrlLength; encodedUrlIndex++) {
    		urlId += allowedString.indexOf(encodedUrlAsCharArray[encodedUrlIndex]) * Math.pow(base, encodedUrlLength-counter);
    		counter++;
    	}
    	return urlId;
    }

}
