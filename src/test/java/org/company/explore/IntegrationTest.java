package org.company.explore;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.PostConstruct;

@Slf4j
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"testContainers"})
@ContextConfiguration(initializers = IntegrationTest.ContainersInitializer.class)
public class IntegrationTest {

    public static ElasticsearchContainer elasticsearchContainer =
            new ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.15.2-arm64")).withExposedPorts(9200, 9300);

    public static GenericContainer redisDBContainer =
            new GenericContainer(DockerImageName.parse("redis:7.0.4")).withExposedPorts(6379);


    static {
        elasticsearchContainer.start();
        redisDBContainer.start();
    }

    public static class ContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.elasticsearch.uris=" + elasticsearchContainer.getHttpHostAddress(),
                    "spring.redis.host=" + redisDBContainer.getHost(),
                    "spring.redis.port=" + redisDBContainer.getMappedPort(6379));
        }
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @LocalServerPort
    private int port;

    private String uri;

    public String getBaseUrl() {
        return uri;
    }


    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port + "/company-explore";
    }

    @BeforeEach
    public void before() {
        log.info("Setting up integration test class " + this.getClass().getSimpleName());
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
