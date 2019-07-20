package ru.ifmo.ctddev.tenischev.traffic.light.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * This class is DTO for health of project night build.
 *
 * @author setenish 01.06.2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class HealthReport {

    /**
     * The text of health status.
     */
    private String description;

    /**
     * The percentage of health.
     */
    private Integer score;
}
