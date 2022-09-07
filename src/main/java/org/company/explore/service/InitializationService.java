package org.company.explore.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.explore.extractor.CsvCompanyExtractor;
import org.company.explore.extractor.NewsArticleExtractor;
import org.company.explore.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitializationService {

    private final NewsArticleService newsArticleService;
    private final NewsArticleExtractor newsArticleExtractor;
    private final CsvCompanyExtractor csvCompanyExtractor;

    private final CompanyMentionService companyMentionService;

    public CompletableFuture<Void> initialize() {
        newsArticleExtractor.getNewsArticlesFromPath()
                .forEach(f -> CommonUtils.extractNewsArticleFromFile(f, newsArticleExtractor.getXmlMapper(), newsArticleService::saveEntity));
        return CompletableFuture.runAsync(() -> csvCompanyExtractor.getCompaniesListFromPath().forEach(companyMentionService::findCompanyMentions));
    }
}