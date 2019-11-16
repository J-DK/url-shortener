package com.url.shortener.repository;


import com.url.shortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository<Url, Long> {

  Url getUrlByKey(String key);
}
