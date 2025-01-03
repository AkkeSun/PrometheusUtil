package com.prometheusutil.metrics.adapter.in.find_mectrics;

import com.prometheusutil.global.validation.DateTime;
import com.prometheusutil.global.validation.groups.ValidationGroups.CustomGroups;
import com.prometheusutil.global.validation.groups.ValidationGroups.NotBlankGroups;
import com.prometheusutil.metrics.application.port.in.command.FindMetricsCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
class FindMetricsRequest {

    @NotBlank(message = "job 은 필수값 입니다.", groups = {NotBlankGroups.class})
    private String job;

    @NotBlank(message = "조회 날짜는 필수값 입니다.", groups = {NotBlankGroups.class})
    @DateTime(message = "조회 날짜는 yyyyMMdd 형식만 입력 가능합니다.", groups = {CustomGroups.class})
    private String targetDate;

    @Builder
    FindMetricsRequest(String job, String targetDate) {
        this.job = job;
        this.targetDate = targetDate;
    }

    FindMetricsCommand toCommand() {
        return FindMetricsCommand.builder()
            .job(job)
            .targetDate(targetDate)
            .build();
    }
}
