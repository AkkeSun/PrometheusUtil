package com.prometheusutil.metrics.adapter.in.find_mectrics;

import com.prometheusutil.metrics.application.service.find_metrics.FindMetricsServiceResponse;
import com.prometheusutil.metrics.domain.ApiCallCnt;
import com.prometheusutil.metrics.domain.InstanceMetric;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
class FindMetricsResponse {

    private List<InstanceMetric> instanceMetrics;
    private long errorLogCnt;
    private List<ApiCallCnt> apiCalls;

    @Builder
    FindMetricsResponse(List<InstanceMetric> instanceMetrics, long errorLogCnt,
        List<ApiCallCnt> apiCalls) {
        this.instanceMetrics = instanceMetrics;
        this.errorLogCnt = errorLogCnt;
        this.apiCalls = apiCalls;
    }

    FindMetricsResponse of(FindMetricsServiceResponse serviceResponse) {
        return FindMetricsResponse.builder()
            .instanceMetrics(serviceResponse.instanceMetrics())
            .errorLogCnt(serviceResponse.errorLogCnt())
            .apiCalls(serviceResponse.apiCalls())
            .build();
    }
}
