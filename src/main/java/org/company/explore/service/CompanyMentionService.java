package org.company.explore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.explore.dto.CompanyMention;
import org.company.explore.dto.CompanyMentionsCollection;
import org.company.explore.entity.CompanyMentionEntity;
import org.company.explore.entity.NewsArticleEntity;
import org.company.explore.extractor.CsvCompanyExtractor;
import org.company.explore.repository.CompanyMentionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyMentionService {

    private static final Integer TOTAL_SEARCH_LIMIT = 1000; //down sizing from 100_000 to 1000
    private static final Integer PAGE_SIZE = 50;

    private final CompanyMentionRepository companyMentionRepository;
    private final NewsArticleService newsArticleService;

    @Async
    public void findCompanyMentions(CsvCompanyExtractor.Company company) {
        log.debug("company id: {} with name {}", company.getId(), company.getName());
        CompanyMentionEntity entity = CompanyMentionEntity.builder().id(company.getId()).companyName(company.getName()).build();
        List<NewsArticleEntity> allNewsArticles = newsArticleService.getAllNewsArticles(entity.getCompanyName(), TOTAL_SEARCH_LIMIT, PAGE_SIZE);
        entity.setMentions(allNewsArticles);
        if (allNewsArticles.isEmpty()) {
            log.debug("nothing to save");
            return;
        }
        log.debug("mentions are found, saving or updating information");
        companyMentionRepository.findById(entity.getId()).ifPresentOrElse(existingEntity -> {
                    if (existingEntity.equals(entity)) {
                        log.debug("2 entities are equals, there is no need for update");
                    } else {
                        log.debug("entity already exists and not equal to new => just update the entity");
                        existingEntity.setMentions(entity.getMentions());
                        companyMentionRepository.save(existingEntity);
                    }
                },
                () -> {
                    log.debug("saving new entity");
                    companyMentionRepository.save(entity);
                });

    }

    public CompanyMentionsCollection getCompanyMentions(Integer page, Integer size) {
        Page<CompanyMentionEntity> all = companyMentionRepository.findAll(PageRequest.of(page, size, Sort.by("id").ascending()));
        return CompanyMentionsCollection.builder()
                .items(all.getContent().stream()
                        .map(entity -> CompanyMention.builder()
                                .id(entity.getId())
                                .companyName(entity.getCompanyName())
                                .mentions(entity.getMentions().stream().map(NewsArticleEntity::getId).collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .totalCount(all.getTotalElements()).build();
    }
}
