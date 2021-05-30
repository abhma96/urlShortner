package com.lowes.urlShortnerService.model;

public class UrlToShortenRequestModel {
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "{url=" + url + "}";
	}
	
	
}
