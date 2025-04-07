package com.stepup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {

    public enum HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH, UNKNOWN
    }

    private final String ipAddress;
    private final String dateTime;
    private final HttpMethod method;
    private final String requestPath;
    private final int statusCode;
    private final int responseSize;
    private final String referer;
    private final String userAgent;

    public LogEntry(String logLine) {
        String[] parts = logLine.split("\"");
        String[] prefix = parts[0].trim().split(" ");
        this.ipAddress = prefix[0];
        this.dateTime = logLine.substring(logLine.indexOf('[') + 1, logLine.indexOf(']'));
        String[] requestParts = parts[1].split(" ");
        this.method = parseMethod(requestParts[0]);
        this.requestPath = requestParts[1];
        String[] statusAndSize = parts[2].trim().split(" ");
        this.statusCode = Integer.parseInt(statusAndSize[0]);
        this.responseSize = Integer.parseInt(statusAndSize[1]);
        this.referer = parts.length > 3 ? parts[3] : "";
        this.userAgent = parts.length > 5 ? parts[5] : "";
    }

    private HttpMethod parseMethod(String methodStr) {
        try {
            return HttpMethod.valueOf(methodStr);
        } catch (IllegalArgumentException e) {
            return HttpMethod.UNKNOWN;
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent;
    }
}



