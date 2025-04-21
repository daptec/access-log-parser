package com.stepup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Statistics {

    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final Set<String> existingPages;
    private final Set<String> notFoundPages;
    private final Map<String, Integer> osCounter;
    private final Map<String, Integer> browserCounter;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
        this.existingPages = new HashSet<>();
        this.notFoundPages = new HashSet<>();
        this.osCounter = new HashMap<>();
        this.browserCounter = new HashMap<>();
    }

    public void addEntry(LogEntry logEntry) {
        this.totalTraffic += logEntry.getResponseSize();
        LocalDateTime logTime = LocalDateTime.parse(logEntry.getDateTime(), DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z"));

        if (logTime.isBefore(minTime)) {
            minTime = logTime;
        }

        if (logTime.isAfter(maxTime)) {
            maxTime = logTime;
        }

        int statusCode = logEntry.getStatusCode();
        if (statusCode == 200) {
            existingPages.add(logEntry.getRequestPath());
        } else if (statusCode == 404) {
            notFoundPages.add(logEntry.getRequestPath());
        }

        UserAgent userAgent = new UserAgent(logEntry.getUserAgent());
        String os = userAgent.getOs().name();
        String browser = userAgent.getBrowser().name();

        osCounter.put(os, osCounter.getOrDefault(os, 0) + 1);
        browserCounter.put(browser, browserCounter.getOrDefault(browser, 0) + 1);
    }

    public double getTrafficRate() {
        long hoursDifference = java.time.Duration.between(minTime, maxTime).toHours();
        if (hoursDifference == 0) {
            return 0;
        }
        return (double) totalTraffic / hoursDifference;
    }

    public List<String> getExistingPages() {
        return new ArrayList<>(existingPages);
    }

    public List<String> getNotFoundPages() {
        return new ArrayList<>(notFoundPages);
    }

    public Map<String, Double> getOperatingSystemStats() {
        Map<String, Double> stats = new HashMap<>();
        int total = osCounter.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : osCounter.entrySet()) {
            stats.put(entry.getKey(), (double) entry.getValue() / total);
        }

        return stats;
    }

    public Map<String, Double> getBrowserStats() {
        Map<String, Double> stats = new HashMap<>();
        int total = browserCounter.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : browserCounter.entrySet()) {
            stats.put(entry.getKey(), (double) entry.getValue() / total);
        }

        return stats;
    }
}
