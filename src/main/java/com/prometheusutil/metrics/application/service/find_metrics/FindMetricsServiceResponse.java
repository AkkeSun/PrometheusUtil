package com.prometheusutil.metrics.application.service.find_metrics;

import com.prometheusutil.metrics.domain.ApiCallCnt;
import com.prometheusutil.metrics.domain.InstanceMetric;
import java.util.List;
import lombok.Builder;

@Builder
public record FindMetricsServiceResponse(
    List<InstanceMetric> instanceMetrics,
    long errorLogCnt,
    List<ApiCallCnt> apiCalls
) {

}
