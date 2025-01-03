package com.prometheusutil.metrics.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Usage {

    private String avg;
    private String max;
    private String min;

    @Builder
    public Usage(String avg, String max, String min) {
        this.avg = avg;
        this.max = max;
        this.min = min;
    }
}
