package com.stepup;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Statistics {

    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    private final Set<String> existingPages = new HashSet<>();
    private final Set<String> notFoundPages = new HashSet<>();

    private final Map<String, Integer> osCount = new HashMap<>();
    private final Map<String, Integer> browserCount = new HashMap<>();

    private int totalVisits = 0;
    private int errorCount = 0;
    private final Set<String> realUserIps = new HashSet<>();

    private final Map<Integer, Integer> visitsPerSecond = new HashMap<>();
    private final Set<String> refererDomains = new HashSet<>();
    private final Map<String, Integer> visitsPerUser = new HashMap<>();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();

        LocalDateTime logTime = LocalDateTime.parse(logEntry.getDateTime(), formatter);
        if (logTime.isBefore(minTime)) minTime = logTime;
        if (logTime.isAfter(maxTime)) maxTime = logTime;

        if (logEntry.getStatusCode() == 200) {
            existingPages.add(logEntry.getRequestPath());
        } else if (logEntry.getStatusCode() == 404) {
            notFoundPages.add(logEntry.getRequestPath());
        }

        UserAgent userAgent = new UserAgent(logEntry.getUserAgent());
        String os = userAgent.getOs().toString();
        osCount.put(os, osCount.getOrDefault(os, 0) + 1);

        String browser = userAgent.getBrowser().toString();
        browserCount.put(browser, browserCount.getOrDefault(browser, 0) + 1);

        boolean isBot = logEntry.getUserAgent().toLowerCase().contains("bot");

        if (!isBot) {
            totalVisits++;

            // Для пиковой посещаемости (в секунду)
            int secondKey = logTime.getHour() * 3600 + logTime.getMinute() * 60 + logTime.getSecond();
            visitsPerSecond.put(secondKey, visitsPerSecond.getOrDefault(secondKey, 0) + 1);

            // Уникальные IP-адреса пользователей
            realUserIps.add(logEntry.getIpAddress());

            // Подсчет посещений одним пользователем
            visitsPerUser.put(
                    logEntry.getIpAddress(),
                    visitsPerUser.getOrDefault(logEntry.getIpAddress(), 0) + 1
            );
        }

        if (logEntry.getStatusCode() >= 400 && logEntry.getStatusCode() < 600) {
            errorCount++;
        }

        // Парсинг домена из referer
        String referer = logEntry.getReferer();
        if (!referer.isEmpty()) {
            try {
                URI uri = new URI(referer);
                String host = uri.getHost();
                if (host != null) {
                    refererDomains.add(host.startsWith("www.") ? host.substring(4) : host);
                }
            } catch (URISyntaxException e) {
                // некорректный реферер — игнорируем
            }
        }
    }

    public int getPeakTrafficPerSecond() {
        return visitsPerSecond.values().stream().max(Integer::compareTo).orElse(0);
    }

    public List<String> getRefererDomains() {
        return new ArrayList<>(refererDomains);
    }

    public int getMaxVisitsPerUser() {
        return visitsPerUser.values().stream().max(Integer::compareTo).orElse(0);
    }
}


