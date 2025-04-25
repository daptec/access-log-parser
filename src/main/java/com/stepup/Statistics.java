package com.stepup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {

    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final Set<String> existingPages;
    private final Set<String> nonExistingPages;
    private final Map<String, Integer> osStats;
    private final Map<String, Integer> browserStats;
    private int totalVisits;
    private int errorRequests;
    private final Set<String> realUserIPs;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
        this.existingPages = new HashSet<>();
        this.nonExistingPages = new HashSet<>();
        this.osStats = new HashMap<>();
        this.browserStats = new HashMap<>();
        this.totalVisits = 0;
        this.errorRequests = 0;
        this.realUserIPs = new HashSet<>();
    }

    public void addEntry(LogEntry logEntry) {
        this.totalTraffic += logEntry.getResponseSize();

        LocalDateTime logTime = LocalDateTime.parse(
                logEntry.getDateTime(),
                DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z")
        );

        if (logTime.isBefore(minTime)) {
            minTime = logTime;
        }

        if (logTime.isAfter(maxTime)) {
            maxTime = logTime;
        }

        if (logEntry.getStatusCode() == 200) {
            existingPages.add(logEntry.getRequestPath());
        }

        if (logEntry.getStatusCode() == 404) {
            nonExistingPages.add(logEntry.getRequestPath());
        }

        UserAgent ua = new UserAgent(logEntry.getUserAgent());

        String os = ua.getOs().name();
        osStats.put(os, osStats.getOrDefault(os, 0) + 1);

        String browser = ua.getBrowser().name();
        browserStats.put(browser, browserStats.getOrDefault(browser, 0) + 1);

        if (!logEntry.getUserAgent().toLowerCase().contains("bot")) {
            totalVisits++;
            realUserIPs.add(logEntry.getIpAddress());
        }

        int code = logEntry.getStatusCode();
        if (code >= 400 && code < 600) {
            errorRequests++;
        }
    }

    public double getTrafficRate() {
        long hours = java.time.Duration.between(minTime, maxTime).toHours();
        if (hours == 0) return 0;
        return (double) totalTraffic / hours;
    }

    public List<String> getExistingPages() {
        return new ArrayList<>(existingPages);
    }

    public List<String> getNonExistingPages() {
        return new ArrayList<>(nonExistingPages);
    }

    public Map<String, Double> getOperatingSystemStats() {
        return computeProportions(osStats);
    }

    public Map<String, Double> getBrowserStats() {
        return computeProportions(browserStats);
    }

    private Map<String, Double> computeProportions(Map<String, Integer> rawMap) {
        int total = rawMap.values().stream().mapToInt(Integer::intValue).sum();
        if (total == 0) return Collections.emptyMap();
        return rawMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / total
                ));
    }

    public double getAverageVisitsPerHour() {
        long hours = java.time.Duration.between(minTime, maxTime).toHours();
        if (hours == 0) return 0;
        return (double) totalVisits / hours;
    }

    public double getAverageErrorsPerHour() {
        long hours = java.time.Duration.between(minTime, maxTime).toHours();
        if (hours == 0) return 0;
        return (double) errorRequests / hours;
    }

    public double getAverageVisitsPerUser() {
        if (realUserIPs.isEmpty()) return 0;
        return (double) totalVisits / realUserIPs.size();
    }
}

