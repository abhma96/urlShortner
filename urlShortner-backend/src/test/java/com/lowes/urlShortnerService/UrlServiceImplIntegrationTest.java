package com.lowes.urlShortnerService;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.lowes.urlShortnerService.entity.UrlEntity;
import com.lowes.urlShortnerService.model.OriginalUrlResponseModel;
import com.lowes.urlShortnerService.model.ShortenedUrlResponseModel;
import com.lowes.urlShortnerService.model.UrlToShortenRequestModel;
import com.lowes.urlShortnerService.repository.IUrlEntityRepository;
import com.lowes.urlShortnerService.service.UrlServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceImplIntegrationTest {

	@TestConfiguration
	static class UrlServiceImplIntegrationTestConfiguration{
//		@Bean
//		public UrlServiceImpl urlServiceImpl() {
//			return new UrlServiceImpl() {
//
//			};
//		}
	}

	@Autowired
	private UrlServiceImpl urlServiceImpl;

	@MockBean
	private IUrlEntityRepository iUrlEntityRepository;

	@Before
	public void setUp() {
		UrlEntity sampleUrlEntity = new UrlEntity();
		sampleUrlEntity.setUrlId(1);
		sampleUrlEntity.setOriginalUrl("http://sampleUrl.com");
		sampleUrlEntity.setShortenedUrl("b");
		sampleUrlEntity.setCounter(0);

		Mockito.when(iUrlEntityRepository.findByOriginalUrl(sampleUrlEntity.getOriginalUrl()))
		.thenReturn(sampleUrlEntity);
	}
	
	@Test
	public void whenValidUrl_thenUrlShouldBeShortened() {
		UrlToShortenRequestModel urlToShortenTestRequest = new UrlToShortenRequestModel();
		urlToShortenTestRequest.setUrl("http://sampleUrl.com");
		String expectedShortUrl = "b";
		ShortenedUrlResponseModel shortenedUrlResponse = urlServiceImpl.shortenUrl(urlToShortenTestRequest);

		assertThat(shortenedUrlResponse.getShortenedUrl())
		.isEqualTo(expectedShortUrl);

	}

	@Test
	public void whenValidUrl_thenUrlEntityShouldBeFound() {
		String originalUrlToFind = "http://sampleUrl.com";
		String testShortUrl = "b";
		OriginalUrlResponseModel originalUrlResponseModel = urlServiceImpl.getOriginalUrl(testShortUrl);

		assertThat(originalUrlResponseModel.getOriginalUrl())
		.isEqualTo(originalUrlToFind);

	}



}
