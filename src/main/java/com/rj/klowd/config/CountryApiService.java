package com.rj.klowd.config;

import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rj.klowd.model.Country;
import com.rj.klowd.model.CountryApiResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


public class CountryApiService {

    String apiEndpointMain;
    String apiEndpointSub;

    public CountryApiService(String apiEndpointMain,  String apiEndpointSub) {
        this.apiEndpointMain = apiEndpointMain;
        this.apiEndpointSub = apiEndpointSub;
    }

    public java.util.function.Supplier<Mono<CountryApiResponse>> apiJsonGet = () -> {
        
        return getWebClient()
            .get()
            .uri(apiEndpointSub)
            // this is not working in native app
            // .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CountryApiResponse.class);
	};
    
    public Mono<ServerResponse> getApiCountryData(ServerRequest request) {

        return ServerResponse.ok()
            // .contentType(MediaType.APPLICATION_JSON)
            .body(apiJsonGet.get()
                .map(val -> val.data())
                .flatMapMany(Flux::fromIterable), Country.class);
    }

    WebClient getWebClient() {

        HttpClient httpClient = HttpClient.create().wiretap(true); // optional: enables logging
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB buffer size
                .build();

        return WebClient.builder()
                .clientConnector(connector)
                .exchangeStrategies(strategies)
                .baseUrl(apiEndpointMain)
                .build();
    }
}