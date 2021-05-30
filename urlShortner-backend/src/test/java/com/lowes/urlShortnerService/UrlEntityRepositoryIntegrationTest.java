package com.lowes.urlShortnerService;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.lowes.urlShortnerService.entity.UrlEntity;
import com.lowes.urlShortnerService.repository.IUrlEntityRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlEntityRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private IUrlEntityRepository iUrlEntityRepository;
	
	@Test
	public void whenFindByOriginalUrl_thenReturnUrlEntity() {
		UrlEntity sampleUrlEntity = new UrlEntity();
		sampleUrlEntity.setUrlId(1);
		sampleUrlEntity.setOriginalUrl("http://sampleUrl.com");
		sampleUrlEntity.setShortenedUrl("b");
		sampleUrlEntity.setCounter(0);
		testEntityManager.persist(sampleUrlEntity);
		testEntityManager.flush();
		String searchStr = "http://sampleUrl.com";
		
		UrlEntity urlEntity = iUrlEntityRepository.findByOriginalUrl(searchStr);
		
		assertThat(urlEntity.getOriginalUrl()).isEqualTo(sampleUrlEntity.getOriginalUrl());
		
	}

}
