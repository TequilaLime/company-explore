package org.company.explore.configurations;

import org.company.explore.extractor.NewsArticleExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsArticleExtractorConfig {

    @Value("${org.company.explore.data.path}")
    private String path;

    @Bean
    NewsArticleExtractor newsArticleExtractor() {
        return new NewsArticleExtractor(path);
    }
}
