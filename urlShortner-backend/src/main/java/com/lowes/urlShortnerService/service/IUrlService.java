package com.lowes.urlShortnerService.service;

import com.lowes.urlShortnerService.model.OriginalUrlResponseModel;
import com.lowes.urlShortnerService.model.ShortenedUrlGridResponseModel;
import com.lowes.urlShortnerService.model.ShortenedUrlResponseModel;
import com.lowes.urlShortnerService.model.URLGridRequestModel;
import com.lowes.urlShortnerService.model.UrlToShortenRequestModel;

public interface IUrlService {
	
	ShortenedUrlResponseModel shortenUrl(UrlToShortenRequestModel urlToShorten);
	OriginalUrlResponseModel getOriginalUrl(String shortUrl);
	ShortenedUrlGridResponseModel getUrlsGrid(URLGridRequestModel urlGridRequest);

}
