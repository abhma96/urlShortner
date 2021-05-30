package com.lowes.urlShortnerService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.lowes.urlShortnerService.controller.UrlController;
import com.lowes.urlShortnerService.model.ShortenedUrlResponseModel;
import com.lowes.urlShortnerService.model.UrlToShortenRequestModel;
import com.lowes.urlShortnerService.service.UrlServiceImpl;

@RunWith(SpringRunner.class)
//@SpringBootTest(SpringBootTest.WebEnvironment.MOCK,classes = UrlShortnerServiceApplication.class)
@WebMvcTest(UrlController.class)
public class UrlControllerIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private UrlServiceImpl urlServiceImpl;
	
	@Test
	public void givenOriginalUrl_whenEncodeUrl_thenReturnShortenedUrl() throws Exception{
		UrlToShortenRequestModel testUrlToShortenRequest = new UrlToShortenRequestModel();
		testUrlToShortenRequest.setUrl("http://sampleUrl.com");
		ShortenedUrlResponseModel shortenedUrlTestResponse = new ShortenedUrlResponseModel();
		shortenedUrlTestResponse.setShortenedUrl("b");
		shortenedUrlTestResponse.setUsedCounter(0);
		
		when(urlServiceImpl.shortenUrl(testUrlToShortenRequest)).thenReturn(shortenedUrlTestResponse);
		
		mvc.perform(post("/shortenUrl")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"http://sampleUrl.com\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.shortenedUrl").value(shortenedUrlTestResponse.getShortenedUrl()));
		
	}
	
	

}
