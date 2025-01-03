package com.prometheusutil.metrics.adapter.out.external;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface PrometheusClient {

    @GetExchange("/api/v1/query")
    String findPrometheusData(@RequestParam String query, @RequestParam long start,
        @RequestParam long end, @RequestParam String step);
}
