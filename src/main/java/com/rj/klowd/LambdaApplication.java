package com.rj.klowd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rj.klowd.config.CountryApiService;

@SpringBootApplication
public class LambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LambdaApplication.class, args);
	}

    @Bean
    RouterFunction<ServerResponse> fetchCountries(     
        @Value("${api.endpoint.main}") String apiEndpointMain,
        @Value("${api.endpoint.sub}") String apiEndpointSub) {
        CountryApiService service = new CountryApiService(apiEndpointMain, apiEndpointSub);   
        return RouterFunctions
            .route(
                RequestPredicates
                    .GET("/api/allcountry"), (request) -> service.getApiCountryData(request));
    }

}
