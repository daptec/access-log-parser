package com.stepup;

public class UserAgent {

    public enum OperatingSystem {
        WINDOWS, MACOS, LINUX, UNKNOWN
    }

    public enum Browser {
        EDGE, FIREFOX, CHROME, OPERA, OTHER
    }

    private final OperatingSystem os;
    private final Browser browser;

    public UserAgent(String userAgentString) {
        this.os = parseOperatingSystem(userAgentString);
        this.browser = parseBrowser(userAgentString);
    }

    private OperatingSystem parseOperatingSystem(String userAgentString) {
        if (userAgentString.contains("Windows")) {
            return OperatingSystem.WINDOWS;
        } else if (userAgentString.contains("Mac OS") || userAgentString.contains("Macintosh")) {
            return OperatingSystem.MACOS;
        } else if (userAgentString.contains("Linux")) {
            return OperatingSystem.LINUX;
        }
        return OperatingSystem.UNKNOWN;
    }

    private Browser parseBrowser(String userAgentString) {
        if (userAgentString.contains("Edge")) {
            return Browser.EDGE;
        } else if (userAgentString.contains("Firefox")) {
            return Browser.FIREFOX;
        } else if (userAgentString.contains("Chrome")) {
            return Browser.CHROME;
        } else if (userAgentString.contains("Opera")) {
            return Browser.OPERA;
        }
        return Browser.OTHER;
    }

    public OperatingSystem getOs() {
        return os;
    }

    public Browser getBrowser() {
        return browser;
    }
}
