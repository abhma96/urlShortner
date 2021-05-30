package com.lowes.urlShortnerService.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowes.urlShortnerService.model.OriginalUrlResponseModel;
import com.lowes.urlShortnerService.model.ShortenedUrlGridResponseModel;
import com.lowes.urlShortnerService.model.ShortenedUrlResponseModel;
import com.lowes.urlShortnerService.model.URLGridRequestModel;
import com.lowes.urlShortnerService.model.UrlToShortenRequestModel;
import com.lowes.urlShortnerService.service.IUrlService;

@RestController
@CrossOrigin("*")
public class UrlController {
	
	@Autowired
	private IUrlService iUrlService;
	
	@PostMapping("shortenUrl")
	public ResponseEntity<?> shortenUrl(@RequestBody UrlToShortenRequestModel urlToShortenRequest){
		ShortenedUrlResponseModel shortenedUrlResponse = new ShortenedUrlResponseModel();
		try {
			shortenedUrlResponse = iUrlService.shortenUrl(urlToShortenRequest);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(shortenedUrlResponse, HttpStatus.OK);
	}
	
	@GetMapping("{shortenedUrlString}")
	public ResponseEntity<?> getOriginalUrl(@PathVariable("shortenedUrlString") String shortenedUrlString){
		OriginalUrlResponseModel originalUrlResponse = new OriginalUrlResponseModel();
		try {
			originalUrlResponse = iUrlService.getOriginalUrl(shortenedUrlString);
		}catch(Exception e) {
			
		}
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create(originalUrlResponse.getOriginalUrl())).build();
	}
	
	@PostMapping("getAllUrls")
	public ResponseEntity<?> getAllUrlsGrid(@RequestBody URLGridRequestModel urlGridRequest){
		ShortenedUrlGridResponseModel shortenedUrlGridResponse = new ShortenedUrlGridResponseModel();
		try {
			shortenedUrlGridResponse = iUrlService.getUrlsGrid(urlGridRequest);
		}catch(Exception e) {
			shortenedUrlGridResponse.setMessage(e.getMessage());
			shortenedUrlGridResponse.setStatus(-1);
			return new ResponseEntity<>(shortenedUrlGridResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(shortenedUrlGridResponse, HttpStatus.OK);
	}
	

}
