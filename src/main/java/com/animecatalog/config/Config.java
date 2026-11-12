package com.animecatalog.config;

/**
 * Author: cyqatester
 */

public final class Config {
    private Config() {}

    public static String apiBaseUrl() {
        return System.getProperty("api.baseUrl",
                env("API_BASE_URL", "http://localhost:8080"));
    }

    public static String adminUsername() {
        return System.getProperty("api.adminUsername",
                env("API_ADMIN_USERNAME", "superadmin"));
    }

    public static int apiTimeoutMs() {
        return Integer.parseInt(System.getProperty("api.timeoutMs",
                env("API_TIMEOUT_MS", "15000")));
    }

    public static String uiBaseUrl() {
        return System.getProperty("ui.baseUrl", env("UI_BASE_URL", ""));
    }

    public static String browser() {
        return System.getProperty("browser", env("BROWSER", "chrome"));
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", env("HEADLESS", "true")));
    }

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }
}
