package ru.ifmo.ctddev.tenischev.traffic.light;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import ru.ifmo.ctddev.tenischev.traffic.light.dto.BuildInfo;
import ru.ifmo.ctddev.tenischev.traffic.light.dto.HealthReport;
import ru.ifmo.ctddev.tenischev.traffic.light.dto.TestState;

import ru.ifmo.ctddev.tenischev.traffic.light.dto.News;
import ru.ifmo.ctddev.tenischev.traffic.light.dto.Page;

/**
 * Controller for traffic-light.
 */
@Controller
public class TrafficLightController {

    private final Logger logger = LoggerFactory.getLogger(TrafficLightController.class);

    @Autowired
    private ObjectMapper mapper;

    @Value("${services.news-storage.url}${services.news-storage.amount-of-news}")
    private String newsStorageUrl;

    @Value("${services.jenkins.url}${services.jenkins.build}")
    private String currentBuildStatusUrl;

    @Value("${services.jenkins.url}${services.jenkins.tests}")
    private String nightlyTestsStatusUrl;

    private Random rand = new Random();

    /**
     * Main method responsible to display all information.
     *
     * @param response the response object
     * @param model    the model of page
     * @return name of thymeleaf template
     */
    @GetMapping("/traffic-light")
    public String trafficLight(HttpServletResponse response, Model model) {
        response.addHeader("Refresh", "30");

        BuildInfo buildStatus = getBuildStatus();
        TestState testState = getTestState();
        Page page = Page.builder()
                .buildInfo(buildStatus)
                .healthReport(testState.getHealthReport().stream().findAny().orElse(new HealthReport()))
                .newsList(getRecentNews())
                .build();
        model.addAttribute("page", page);

        return "trafficLight";
    }

    /**
     * Gets news of project.
     *
     * @return list of news
     */
    private List<News> getRecentNews() {
        RestTemplate restTemplate = new RestTemplate();
        List<News> response = Collections.emptyList();
        try {
            ResponseEntity<List<News>> responseEntity = restTemplate.exchange(
                    newsStorageUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<News>>() {
                    });
            response = responseEntity.getBody();
        } catch (Exception e) {
            logger.info("Ad news storage doesn't reply!", e);
        }
        return response;
    }

    /**
     * Gets status of nightly test job
     *
     * @return filtered status information
     */
    private TestState getTestState() {
        String content = getContent(nightlyTestsStatusUrl);

        TestState testState;
        try {
            testState = mapper.readValue(content, TestState.class);
        } catch (IOException e) {
            e.printStackTrace();
            testState = new TestState();
        }

        return testState;
    }

    /**
     * Gets status of last every hour build
     *
     * @return filtered status information
     */
    private BuildInfo getBuildStatus() {
        String content = getContent(currentBuildStatusUrl);

        BuildInfo buildInfo = null;
        try {
            buildInfo = mapper.readValue(content, BuildInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buildInfo;
    }

    /**
     * Retrieves content out of URI by GET method.
     *
     * @param uri the destination address
     * @return content out of specified address
     */
    private String getContent(String uri) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try {
                con.setRequestMethod("GET");
                try (InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream())) {
                    try (BufferedReader in = new BufferedReader(inputStreamReader)) {
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
