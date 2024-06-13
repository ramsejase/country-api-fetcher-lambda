package com.rj.klowd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.rj.klowd.config.CountryApiService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class LambdaApplicationTests {
     @Autowired
    private ApplicationContext context;

    public void testRouterFunction() throws Exception {

        CountryApiService config = new CountryApiService("base-url", "endpoint-url");
        // Create a new RouterFunctionsTest instance
        WebTestClient client = WebTestClient.bindToApplicationContext(context).build();


        // Test the router function by sending a request to the / route and verifying the response
        ResponseSpec response = client.get().uri("/api/start").exchange();

        // Verify the response
        assertEquals(HttpStatus.OK, response.expectStatus().isOk());
    }
}