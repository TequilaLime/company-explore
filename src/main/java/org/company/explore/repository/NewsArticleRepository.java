package org.company.explore.repository;

import org.company.explore.entity.NewsArticleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsArticleRepository extends ElasticsearchRepository<NewsArticleEntity, String> { }


