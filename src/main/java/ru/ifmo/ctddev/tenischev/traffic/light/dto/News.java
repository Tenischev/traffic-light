package ru.ifmo.ctddev.tenischev.traffic.light.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * This class is DTO for news.
 *
 * @author setenish 01.06.2019.
 */
public @Data class News implements Serializable {

    /**
     * The id of news.
     */
    private Long id;

    /**
     * The topic or title of news.
     */
    private String topic;

    /**
     * The text of news.
     */
    private String text;

    /**
     * The creation time of news.
     */
    private Timestamp time;

    /**
     * The publisher name of news.
     */
    private String publisher;
}
