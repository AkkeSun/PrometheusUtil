package com.prometheusutil.metrics.application.port.in.command;

import lombok.Builder;

@Builder
public record FindMetricsCommand(
    String job,
    String targetDate
) {

}
