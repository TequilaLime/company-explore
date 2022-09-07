package org.company.explore;

import lombok.extern.slf4j.Slf4j;
import org.company.explore.service.InitializationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CompanyExploreApplication implements ApplicationRunner {

    private final InitializationService initializationService;

    public CompanyExploreApplication(InitializationService initializationService) {
        this.initializationService = initializationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CompanyExploreApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("init articles and companies");
        initializationService.initialize();
    }
}
