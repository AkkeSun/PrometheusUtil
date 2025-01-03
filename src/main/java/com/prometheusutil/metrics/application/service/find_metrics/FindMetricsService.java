package com.prometheusutil.metrics.application.service.find_metrics;

import com.prometheusutil.global.exception.CustomNotFoundException;
import com.prometheusutil.global.exception.ErrorCode;
import com.prometheusutil.metrics.adapter.out.external.dto.ApiCallCntResponse;
import com.prometheusutil.metrics.application.port.in.FindMetricsUseCase;
import com.prometheusutil.metrics.application.port.in.command.FindMetricsCommand;
import com.prometheusutil.metrics.application.port.out.FindPrometheusPort;
import com.prometheusutil.metrics.domain.ApiCallCnt;
import com.prometheusutil.metrics.domain.ApiCallStatusAndCnt;
import com.prometheusutil.metrics.domain.InstanceMetric;
import com.prometheusutil.metrics.domain.Usage;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FindMetricsService implements FindMetricsUseCase {

    private final FindPrometheusPort findPrometheusPort;

    @Override
    public FindMetricsServiceResponse findMetrics(FindMetricsCommand command) {
        long startTime = getUnixTime(command.targetDate() + " 00:00:00");
        long endTime = getUnixTime(command.targetDate() + " 23:59:59");

        List<String> instanceList = findPrometheusPort
            .findInstanceList(command.job(), startTime, endTime);

        if (instanceList.isEmpty()) {
            throw new CustomNotFoundException(ErrorCode.DoesNotExist_INSTANCE);
        }

        List<InstanceMetric> instanceMetrics = new ArrayList<>();
        for (String instance : instanceList) {
            Usage cpu = Usage.builder()
                .max(findPrometheusPort.findMaxCpuUsage(instance, startTime, endTime))
                .min(findPrometheusPort.findMinCpuUsage(instance, startTime, endTime))
                .avg(findPrometheusPort.findAvgCpuUsage(instance, startTime, endTime))
                .build();
            Usage heap = Usage.builder()
                .max(findPrometheusPort.findMaxHeapUsage(instance, startTime, endTime))
                .min(findPrometheusPort.findMinHeapUsage(instance, startTime, endTime))
                .avg(findPrometheusPort.findAvgHeapUsage(instance, startTime, endTime))
                .build();
            Usage nonHeap = Usage.builder()
                .max(findPrometheusPort.findMaxNonHeapUsage(instance, startTime, endTime))
                .min(findPrometheusPort.findMinNonHeapUsage(instance, startTime, endTime))
                .avg(findPrometheusPort.findAvgNonHeapUsage(instance, startTime, endTime))
                .build();
            Usage hikariConnection = Usage.builder()
                .max(findPrometheusPort.findMaxHikariConnectionCnt(instance, startTime, endTime))
                .min(findPrometheusPort.findMinHikariConnectionCnt(instance, startTime, endTime))
                .avg(findPrometheusPort.findAvgHikariConnectionCnt(instance, startTime, endTime))
                .build();
            instanceMetrics.add(InstanceMetric.builder()
                .name(instance)
                .cpu(cpu)
                .heap(heap)
                .nonHeap(nonHeap)
                .hikariConnection(hikariConnection)
                .build());
        }
        long errorLogCnt = findPrometheusPort.findErrorLogCnt(command.job(), startTime, endTime);
        List<ApiCallCntResponse> apiCallCnt = findPrometheusPort
            .findApiCallCnt(command.job(), startTime, endTime);

        return FindMetricsServiceResponse.builder()
            .instanceMetrics(instanceMetrics)
            .errorLogCnt(errorLogCnt)
            .apiCalls(aggregateApiCallCnt(apiCallCnt))
            .build();
    }

    private long getUnixTime(String targetDate) {
        LocalDateTime time = LocalDateTime.parse(targetDate,
            DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
        return time.toEpochSecond(LocalDateTime.now().atZone(ZoneOffset.ofHours(9)).getOffset());
    }

    private List<ApiCallCnt> aggregateApiCallCnt(List<ApiCallCntResponse> apiResponse) {
        return apiResponse.stream()
            .collect(Collectors.groupingBy(
                response -> Arrays.asList(response.getUri(), response.getMethod()),
                Collectors.mapping(
                    response -> ApiCallStatusAndCnt.builder()
                        .status(response.getStatus())
                        .cnt(response.getCount())
                        .build(),
                    Collectors.toList()
                )
            ))
            .entrySet().stream()
            .map(entry -> {
                List<ApiCallStatusAndCnt> counts = entry.getValue();

                long totalCnt = counts.stream()
                    .mapToLong(ApiCallStatusAndCnt::getCnt)
                    .sum();
                long successCnt = counts.stream()
                    .filter(count -> "200".equals(count.getStatus()))
                    .mapToLong(ApiCallStatusAndCnt::getCnt)
                    .sum();

                String successRate = totalCnt > 0
                    ? String.format("%.2f", (successCnt * 100.0 / totalCnt))
                    : "0.00";

                return ApiCallCnt.builder()
                    .uri(entry.getKey().get(0))
                    .method(entry.getKey().get(1))
                    .statusCnt(counts)
                    .totalCnt(totalCnt)
                    .successRate(successRate)
                    .build();
            })
            .toList();
    }
}
