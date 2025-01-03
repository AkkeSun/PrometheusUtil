package com.prometheusutil.metrics.adapter.out.external.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiCallCntResponse {

    private String status;
    private String method;
    private String uri;
    private long count;

    @Builder
    public ApiCallCntResponse(String status, String method, String uri, long count) {
        this.status = status;
        this.method = method;
        this.uri = uri;
        this.count = count;
    }
}
