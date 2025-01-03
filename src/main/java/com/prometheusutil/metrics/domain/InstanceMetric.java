package com.prometheusutil.metrics.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InstanceMetric {

    private String name;
    private Usage cpu;
    private Usage heap;
    private Usage nonHeap;
    private Usage hikariConnection;

    @Builder
    public InstanceMetric(String name, Usage cpu, Usage heap, Usage nonHeap,
        Usage hikariConnection) {
        this.name = name;
        this.cpu = cpu;
        this.heap = heap;
        this.nonHeap = nonHeap;
        this.hikariConnection = hikariConnection;
    }
}
