package com.prometheusutil.metrics.adapter.in.find_mectrics;

import com.prometheusutil.global.response.ApiResponse;
import com.prometheusutil.global.validation.groups.ValidationSequence;
import com.prometheusutil.metrics.application.port.in.FindMetricsUseCase;
import com.prometheusutil.metrics.application.service.find_metrics.FindMetricsServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class FindMetricsController {

    private final FindMetricsUseCase findMetricsUseCase;

    @GetMapping("/metrics")
    ApiResponse<FindMetricsResponse> findMetrics(
        @Validated(ValidationSequence.class) FindMetricsRequest request) {

        FindMetricsServiceResponse serviceResponse = findMetricsUseCase
            .findMetrics(request.toCommand());

        return ApiResponse.ok(new FindMetricsResponse().of(serviceResponse));
    }
}
