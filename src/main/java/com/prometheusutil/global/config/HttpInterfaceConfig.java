package com.prometheusutil.global.config;

import com.prometheusutil.metrics.adapter.out.external.PrometheusClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

    @Value("${prometheus.url}")
    private String prometheusUrl;

    @Bean
    PrometheusClient sdCrawlingV3Client() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
            .withConnectTimeout(Duration.ofSeconds(15))
            .withReadTimeout(Duration.ofSeconds(20));

        RestClient restClient = RestClient.builder()
            .baseUrl(prometheusUrl)
            .requestFactory(ClientHttpRequestFactories.get(settings))
            .build();

        return HttpServiceProxyFactory.builder()
            .exchangeAdapter(RestClientAdapter.create(restClient))
            .build()
            .createClient(PrometheusClient.class);
    }
}
