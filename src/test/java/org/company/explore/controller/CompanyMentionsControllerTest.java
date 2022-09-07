package org.company.explore.controller;

import lombok.extern.slf4j.Slf4j;
import org.company.explore.IntegrationTest;
import org.company.explore.extractor.CsvCompanyExtractor;
import org.company.explore.service.CompanyMentionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.shaded.org.awaitility.Durations;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@Slf4j
class CompanyMentionsControllerTest extends IntegrationTest {

    @Autowired private CsvCompanyExtractor csvCompanyExtractor;
    @Autowired private CompanyMentionService companyMentionService;

    @Test
    void getCompanyMentions() throws InterruptedException {
        Awaitility.await().pollDelay(Durations.FIVE_SECONDS).until(() -> true);
        //process companies again
        log.debug("process companies again, after saving the .xml file");
        CsvCompanyExtractor csvCompanyExtractor1 = new CsvCompanyExtractor(csvCompanyExtractor.getPath());
        csvCompanyExtractor1.getCompaniesListFromPath().forEach(companyMentionService::findCompanyMentions);
        given()
                .get(getBaseUrl() + "/company-mentions?page=0&size=20")
                .then().log().all().extract().response()
                .then().assertThat().statusCode(200).assertThat().body(containsString("{\"id\":20,\"companyName\":\"Bank of America Corp (BofA)\",\"mentions\":[\"5BW9-V371-F11P-X3RN\"]}"))
                .assertThat().body(containsString("{\"id\":113,\"companyName\":\"Wells Fargo & Co\",\"mentions\":[\"5BW9-V371-F11P-X3RN\"]}"));
    }
}
