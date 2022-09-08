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
    
# End-points 

API end-point available:

The end-point returns the company id, name, and a list of article ids where, presumably, the company was mentioned.

`GET http://localhost:8080/company-explore/company-mentions?page=0&size=20`

`curl --location --request GET 'http://localhost:8080/company-explore/company-mentions?page=0&size=20`

    
# Implementation & Tests

Throughout the whole week, I only had an opportunity to sit down and work for one evening, therefore I feel like the solution came up with limited implementation. 

For this exercise, I purposely didn't try to use Abstractions and Generics much, as the exercise is small and narrow.

Unfortunately, I ran out of time to write more test cases to validate performance: precision/recall.
Moreover, the test data set was significantly sliced to single `.xml` file and the company list to 113 entries.
