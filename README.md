# company-explore
## Prerequisite `Docker up and running`
 
 ---
- [ ] Depending on your system please choose the correct elasticsearch image in `docker-compose.yml` & `IntegrationTest.java` files.

- [ ] Before running the application please execute `docker-compose up -d` command

- [ ] Before running the application please provide suitable paths for `.csv` file and `data set` folder in application.yml file.

---
- To build `.jar` for further installations etc ... execute command `mvn clean install`

- To run application on your local device, you shall find spring boot plugin available: 
    - `mvn spring-boot:run`
    
# Implementation & Tests

For this exercise, I purposely didn't try to use Abstractions and Generics much, as the exercise is small and narrow.

Unfortunately, I ran out of time to write more test cases to validate performance: precision/recall.
Moreover, the test data set was significantly sliced to single `.xml` file and the company list to 113 entries.
