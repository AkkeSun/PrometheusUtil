package com.prometheusutil.metrics.application.port.in;

import com.prometheusutil.metrics.application.port.in.command.FindMetricsCommand;
import com.prometheusutil.metrics.application.service.find_metrics.FindMetricsServiceResponse;

public interface FindMetricsUseCase {

    FindMetricsServiceResponse findMetrics(FindMetricsCommand command);
}
