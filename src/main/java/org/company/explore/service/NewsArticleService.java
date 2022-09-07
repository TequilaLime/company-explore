package org.company.explore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.explore.entity.NewsArticleEntity;
import org.company.explore.entity.NewsArticleSearchable;
import org.company.explore.repository.NewsArticleRepository;
import org.company.explore.util.CommonUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final ModelMapper modelMapper;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public NewsArticleEntity saveEntity(NewsArticleEntity entity) {
        return newsArticleRepository.save(entity);
    }

    public Optional<NewsArticleEntity> findEntity(String id) {
        return newsArticleRepository.findById(id);
    }


    public List<NewsArticleEntity> getAllNewsArticles(String searchTerm, int totalLimit, int pageSize) {
        String[] splittedSearchTerm = searchTerm.split("[\\(||\\)]");
        int maxSearchTerms = 2;
        List<NewsArticleEntity> allItems = new ArrayList<>();
        for (int i = 0; i < splittedSearchTerm.length; i++) {// last minutes feature patch :)
            if (i < maxSearchTerms) {// I only need first 2 search terms
                allItems.addAll(CommonUtils.getAllItems(splittedSearchTerm[i], totalLimit, pageSize, this::getNewsArticles));
            }
        }
        return allItems;
    }

    private List<NewsArticleEntity> getNewsArticles(String searchTerm, int limit, int offset) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchTerm, NewsArticleSearchable.TITLE, NewsArticleSearchable.TEXT).operator(Operator.AND);
        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(PageRequest.of(offset, limit)).build();
        SearchHits<NewsArticleEntity> searchResults = elasticsearchRestTemplate.search(build, NewsArticleEntity.class);

        return searchResults.stream().map(s -> NewsArticleEntity.builder().id(s.getId()).build()).collect(Collectors.toList());
    }
}
