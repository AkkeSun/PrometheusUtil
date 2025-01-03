package com.prometheusutil;

import com.prometheusutil.metrics.application.port.in.FindMetricsUseCase;
import com.prometheusutil.metrics.application.port.in.command.FindMetricsCommand;
import com.prometheusutil.metrics.application.service.find_metrics.FindMetricsServiceResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrometheusUtilApplicationTests {

    @Autowired
    FindMetricsUseCase findMetricsUseCase;

    @Test
    void contextLoads() {
        FindMetricsServiceResponse metrics = findMetricsUseCase.findMetrics(
            FindMetricsCommand.builder()
                .job("chameleon")
                .targetDate(
                    LocalDateTime.now().minusDays(1)
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build());

        System.out.println("check");
    }

}
