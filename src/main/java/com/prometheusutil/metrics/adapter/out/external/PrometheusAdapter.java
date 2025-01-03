package com.prometheusutil.metrics.adapter.out.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prometheusutil.metrics.adapter.out.external.dto.ApiCallCntResponse;
import com.prometheusutil.metrics.application.port.out.FindPrometheusPort;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrometheusAdapter implements FindPrometheusPort {

    private final PrometheusClient prometheusClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String findAvgCpuUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "avg_over_time(system_cpu_usage{instance=\"%s\"}[1d]) * 100",
                instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findMaxCpuUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "max_over_time(system_cpu_usage{instance=\"%s\"}[1d]) * 100",
                instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0.";
        }
    }

    @Override
    public String findMinCpuUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "min_over_time(system_cpu_usage{instance=\"%s\"}[1d]) * 100",
                instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findAvgHeapUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "sum(avg_over_time(jvm_memory_used_bytes{instance=\"%s\", area=\"heap\"}[1d])) * 100 / "
                    + "sum(avg_over_time(jvm_memory_max_bytes{instance=\"%s\", area=\"heap\"}[1d]))",
                instance, instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findMaxHeapUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "sum(max_over_time(jvm_memory_used_bytes{instance=\"%s\", area=\"heap\"}[1d])) * 100 / "
                    + "sum(max_over_time(jvm_memory_max_bytes{instance=\"%s\", area=\"heap\"}[1d]))",
                instance, instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findMinHeapUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "sum(min_over_time(jvm_memory_used_bytes{instance=\"%s\", area=\"heap\"}[1d])) * 100 / "
                    + "sum(min_over_time(jvm_memory_max_bytes{instance=\"%s\", area=\"heap\"}[1d]))",
                instance, instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findAvgNonHeapUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "sum(avg_over_time(jvm_memory_used_bytes{instance=\"%s\", area=\"nonheap\"}[1d])) * 100 / "
                    + "sum(avg_over_time(jvm_memory_max_bytes{instance=\"%s\", area=\"nonheap\"}[1d]))",
                instance, instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findMaxNonHeapUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "sum(max_over_time(jvm_memory_used_bytes{instance=\"%s\", area=\"nonheap\"}[1d])) * 100 / "
                    + "sum(max_over_time(jvm_memory_max_bytes{instance=\"%s\", area=\"nonheap\"}[1d]))",
                instance, instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findMinNonHeapUsage(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "sum(min_over_time(jvm_memory_used_bytes{instance=\"%s\", area=\"nonheap\"}[1d])) * 100 / "
                    + "sum(min_over_time(jvm_memory_max_bytes{instance=\"%s\", area=\"nonheap\"}[1d]))",
                instance, instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getUsageValue(response);
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public String findAvgHikariConnectionCnt(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "round(avg_over_time(hikaricp_connections_idle{instance=\"%s\"}[1d]))",
                instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getTextValue(response);
        } catch (Exception e) {
            return "0";
        }
    }

    @Override
    public String findMaxHikariConnectionCnt(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "max_over_time(hikaricp_connections_idle{instance=\"%s\"}[1d])",
                instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getTextValue(response);
        } catch (Exception e) {
            return "0";
        }
    }

    @Override
    public String findMinHikariConnectionCnt(String instance, long startTime, long endTime) {
        try {
            String query = String.format(
                "min_over_time(hikaricp_connections_idle{instance=\"%s\"}[1d])",
                instance);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return getTextValue(response);
        } catch (Exception e) {
            return "0";
        }
    }

    @Override
    public long findErrorLogCnt(String job, long startTime, long endTime) {
        try {
            String query = String.format(
                "floor(sum(increase(logback_events_total{job=\"%s\", level=\"error\"}[1d])))",
                job);
            String response = prometheusClient.findPrometheusData(query, startTime, endTime, "1d");
            return Long.parseLong(getTextValue(response));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<ApiCallCntResponse> findApiCallCnt(String job, long startTime, long endTime) {
        try {
            String query = String.format(
                "round(sum(increase(http_server_requests_seconds_count{job=\"%s\", uri!~\".*actuator.*\", uri!=\"/**\"}[1d])) by (uri, method, status)) "
                    + "unless sum(increase(http_server_requests_seconds_count{job=\"%s\", uri!~\".*actuator.*\", uri!=\"/**\"}[1d])) by (uri, method, status) == 0",
                job, job);
            String apiResponse = prometheusClient
                .findPrometheusData(query, startTime, endTime, "1h");

            List<ApiCallCntResponse> result = new ArrayList<>();
            for (JsonNode jsonNode : objectMapper.readTree(apiResponse)
                .get("data").get("result")) {
                result.add(ApiCallCntResponse.builder()
                    .uri(jsonNode.get("metric").get("uri").asText())
                    .method(jsonNode.get("metric").get("method").asText())
                    .status(jsonNode.get("metric").get("status").asText())
                    .count(jsonNode.get("value").get(1).asLong())
                    .build());
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> findInstanceList(String job, long startTime, long endTime) {
        try {
            String query = String.format("up{job=\"%s\"}", job);
            String apiResponse = prometheusClient
                .findPrometheusData(query, startTime, endTime, "1h");

            List<String> result = new ArrayList<>();
            for (JsonNode jsonNode : objectMapper.readTree(apiResponse)
                .get("data").get("result")) {
                result.add(jsonNode.get("metric").get("instance").asText());
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private String getUsageValue(String apiResponse) throws JsonProcessingException {
        DecimalFormat df = new DecimalFormat("#.#");
        double value = objectMapper.readTree(apiResponse)
            .get("data")
            .get("result")
            .get(0).get("value")
            .get(1).asDouble();
        return df.format(value);
    }

    private String getTextValue(String apiResponse) throws JsonProcessingException {
        DecimalFormat df = new DecimalFormat("#.#");
        return objectMapper.readTree(apiResponse)
            .get("data")
            .get("result")
            .get(0).get("value")
            .get(1).asText();
    }
}
