package com.lowes.urlShortnerService.service;

import java.net.InetAddress;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lowes.urlShortnerService.UrlHelper.UrlConvertor;
import com.lowes.urlShortnerService.entity.UrlEntity;
import com.lowes.urlShortnerService.model.OriginalUrlResponseModel;
import com.lowes.urlShortnerService.model.ShortenedUrlGridResponseModel;
import com.lowes.urlShortnerService.model.ShortenedUrlResponseModel;
import com.lowes.urlShortnerService.model.URLGridRequestModel;
import com.lowes.urlShortnerService.model.UrlToShortenRequestModel;
import com.lowes.urlShortnerService.repository.IUrlEntityRepository;

@Service
public class UrlServiceImpl implements IUrlService{
	
	Logger urlServicelogger = Logger.getLogger(UrlServiceImpl.class.getName());
	
	@Autowired
	private UrlConvertor urlConvertor;
	
	@Autowired
	private IUrlEntityRepository iUrlEntityRepository;

	@Override
	public ShortenedUrlResponseModel shortenUrl(UrlToShortenRequestModel urlToShorten) {
		ShortenedUrlResponseModel shortenedUrlResponse = new ShortenedUrlResponseModel();
		UrlEntity savedUrlEntity = new UrlEntity();
		try {
			UrlEntity doesUrlExist = iUrlEntityRepository.findByOriginalUrl(urlToShorten.getUrl());
			if(Objects.isNull(doesUrlExist)) {
				UrlEntity newUrlEntity = new UrlEntity();
				newUrlEntity.setCounter(0);
				newUrlEntity.setOriginalUrl(urlToShorten.getUrl());
				savedUrlEntity = iUrlEntityRepository.save(newUrlEntity);
				newUrlEntity.setShortenedUrl(urlConvertor.encodeUrl(savedUrlEntity.getUrlId()));
				savedUrlEntity = iUrlEntityRepository.save(newUrlEntity);
				shortenedUrlResponse.setShortenedUrl(savedUrlEntity.getShortenedUrl());
				shortenedUrlResponse.setUsedCounter(savedUrlEntity.getCounter());
			}else {
				shortenedUrlResponse.setShortenedUrl(doesUrlExist.getShortenedUrl());
				shortenedUrlResponse.setUsedCounter(doesUrlExist.getCounter());
			}
		}catch(Exception e) {
			urlServicelogger.log(Level.SEVERE, e.getMessage());
		}
		return shortenedUrlResponse;
	}

	@Override
	public OriginalUrlResponseModel getOriginalUrl(String shortUrl) {
		OriginalUrlResponseModel originalUrlResponse = new OriginalUrlResponseModel();
		try {
			long urlId = urlConvertor.decodeUrl(shortUrl);
			Optional<UrlEntity> urlEntity = iUrlEntityRepository.findById(urlId);
			if(urlEntity.isPresent()) {
				originalUrlResponse.setOriginalUrl(urlEntity.get().getOriginalUrl());
				urlEntity.get().setCounter(urlEntity.get().getCounter()+1);
				iUrlEntityRepository.saveAndFlush(urlEntity.get());
			}else {
				originalUrlResponse.setOriginalUrl(null);
			}
		}catch(Exception e) {
			urlServicelogger.log(Level.SEVERE, e.getMessage());
		}
		return originalUrlResponse;
	}

	@Override
	public ShortenedUrlGridResponseModel getUrlsGrid(URLGridRequestModel urlGridRequest) {
		ShortenedUrlGridResponseModel shortenedUrlGridResponse = new ShortenedUrlGridResponseModel();
		try {			
			Pageable page = this.getPageConfig(urlGridRequest);
			List<UrlEntity> urlsList = iUrlEntityRepository.findAll(page).toList();
			long totalRecords = iUrlEntityRepository.count();
			if(!urlsList.isEmpty()) {
				shortenedUrlGridResponse.setMessage("Found!");
				shortenedUrlGridResponse.setStatus(1);
				shortenedUrlGridResponse.setResult(urlsList);
				shortenedUrlGridResponse.setTotalRecords(totalRecords);
			}else {
				shortenedUrlGridResponse.setMessage("No records found.");
				shortenedUrlGridResponse.setStatus(0);
				shortenedUrlGridResponse.setTotalRecords(0);
			}
		}catch(Exception e) {
			urlServicelogger.log(Level.SEVERE, e.getMessage());
			return shortenedUrlGridResponse;
		}
		return shortenedUrlGridResponse;
	}

	private Pageable getPageConfig(URLGridRequestModel urlGridRequest) {
		Pageable page = PageRequest.of(0, 10);
		String sortByColumn = "urlId";
		if(!urlGridRequest.getColumn().isEmpty()) {
			sortByColumn = urlGridRequest.getColumn();
		}
		if(urlGridRequest.getSortOrder().compareToIgnoreCase("ASC") == 0) {
			page = PageRequest.of(urlGridRequest.getPageNo(), 
					urlGridRequest.getPageSize(), 
					Sort.by(sortByColumn).ascending());
		}else if(urlGridRequest.getSortOrder().compareToIgnoreCase("DESC") == 0){
			page = PageRequest.of(urlGridRequest.getPageNo(), 
					urlGridRequest.getPageSize(), 
					Sort.by(sortByColumn).descending());
		}else {
			page = PageRequest.of(urlGridRequest.getPageNo(), 
					urlGridRequest.getPageSize(), 
					Sort.by(sortByColumn));
		}
		return page;
	}
}
