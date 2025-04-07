package com.stepup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Statistics {

    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
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
    }

    public double getTrafficRate() {
        long hoursDifference = java.time.Duration.between(minTime, maxTime).toHours();

        if (hoursDifference == 0) {
            return 0;
        }

        return (double) totalTraffic / hoursDifference;
    }
}
