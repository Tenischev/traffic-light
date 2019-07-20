package ru.ifmo.ctddev.tenischev.traffic.light.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * This class is model DTO for traffic-light main page.
 *
 * @author setenish 01.06.2019.
 */
@Builder
public @Data class Page {

    /**
     * The build status.
     */
    private BuildInfo buildInfo;

    /**
     * The nightly test status.
     */
    private HealthReport healthReport;

    /**
     * The latest news.
     */
    private List<News> newsList;
}
