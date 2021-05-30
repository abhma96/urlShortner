package com.lowes.urlShortnerService.model;

public class ShortenedUrlResponseModel {
	
	private String shortenedUrl;
	
	private Integer usedCounter;

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}

	public Integer getUsedCounter() {
		return usedCounter;
	}

	public void setUsedCounter(Integer usedCounter) {
		this.usedCounter = usedCounter;
	}
	
}
