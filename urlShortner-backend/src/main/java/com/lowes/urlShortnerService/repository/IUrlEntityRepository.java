package com.lowes.urlShortnerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lowes.urlShortnerService.entity.UrlEntity;

@Repository
public interface IUrlEntityRepository extends JpaRepository<UrlEntity, Long>{
	
	UrlEntity findByOriginalUrl(String originalUrl);

}
