package ru.ifmo.ctddev.tenischev.traffic.light.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * This class is DTO for health of project night build.
 *
 * @author setenish 01.06.2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class TestState {

    /**
     * List of health reports.
     */
    private List<HealthReport> healthReport;

    /**
     * Default constructor.
     */
    public TestState() {
        healthReport = new ArrayList<>();
    }
}
