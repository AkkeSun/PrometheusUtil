package com.prometheusutil.metrics.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiCallCnt {

    private String method;
    private String uri;
    private long totalCnt;
    private String successRate;
    private List<ApiCallStatusAndCnt> statusCnt;

    @Builder
    public ApiCallCnt(String method, String uri, long totalCnt, String successRate,
        List<ApiCallStatusAndCnt> statusCnt) {
        this.method = method;
        this.uri = uri;
        this.totalCnt = totalCnt;
        this.successRate = successRate;
        this.statusCnt = statusCnt;
    }
}
