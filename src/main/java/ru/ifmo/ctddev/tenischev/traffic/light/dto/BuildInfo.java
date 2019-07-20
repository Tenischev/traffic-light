package ru.ifmo.ctddev.tenischev.traffic.light.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * This class is DTO for information about latest build.
 *
 * @author setenish 01.06.2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class BuildInfo {

    /**
     * Build result
     */
    private String result;

    /**
     * Time of build
     */
    private Timestamp timestamp;
}
