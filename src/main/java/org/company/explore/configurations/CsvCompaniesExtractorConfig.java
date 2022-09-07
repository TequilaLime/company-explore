package org.company.explore.configurations;


import org.company.explore.extractor.CsvCompanyExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvCompaniesExtractorConfig {

    @Value("${org.company.explore.csv.path}")
    private String path;

    @Bean
    public CsvCompanyExtractor csvCompanyExtractor() {
        return new CsvCompanyExtractor(path);
    }
}
