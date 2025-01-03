package com.prometheusutil.metrics.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiCallStatusAndCnt {

    private String status;
    private long cnt;

    @Builder
    public ApiCallStatusAndCnt(String status, long cnt) {
        this.status = status;
        this.cnt = cnt;
    }
}
