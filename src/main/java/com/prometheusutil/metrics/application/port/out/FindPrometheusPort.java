package com.prometheusutil.metrics.application.port.out;

import com.prometheusutil.metrics.adapter.out.external.dto.ApiCallCntResponse;
import java.util.List;

public interface FindPrometheusPort {

    String findAvgCpuUsage(String instance, long startTime, long endTime);

    String findMaxCpuUsage(String instance, long startTime, long endTime);

    String findMinCpuUsage(String instance, long startTime, long endTime);


    String findAvgHeapUsage(String instance, long startTime, long endTime);

    String findMaxHeapUsage(String instance, long startTime, long endTime);

    String findMinHeapUsage(String instance, long startTime, long endTime);


    String findAvgNonHeapUsage(String instance, long startTime, long endTime);

    String findMaxNonHeapUsage(String instance, long startTime, long endTime);

    String findMinNonHeapUsage(String instance, long startTime, long endTime);


    String findAvgHikariConnectionCnt(String instance, long startTime, long endTime);

    String findMaxHikariConnectionCnt(String instance, long startTime, long endTime);

    String findMinHikariConnectionCnt(String instance, long startTime, long endTime);


    long findErrorLogCnt(String job, long startTime, long endTime);

    List<ApiCallCntResponse> findApiCallCnt(String job, long startTime, long endTime);

    List<String> findInstanceList(String job, long startTime, long endTime);
}
