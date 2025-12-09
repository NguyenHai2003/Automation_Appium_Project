package com.company.framework.helpers.browser;

import com.company.framework.drivers.DriverManager;
import com.company.framework.utils.LogUtils;
import io.appium.java_client.AppiumDriver;

/**
 * Helper class để xử lý Mobile Web Browser
 * Cung cấp các methods để navigate và tương tác với browser
 */
public class BrowserHelper {

    /**
     * Điều hướng đến URL
     *
     * @param url URL cần điều hướng đến
     */
    public static void navigateToUrl(String url) {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        LogUtils.info("🌐 Đang điều hướng đến URL: " + url);
        driver.get(url);
        LogUtils.info("✅ Đã điều hướng thành công");
    }

    /**
     * Lấy URL hiện tại
     *
     * @return URL hiện tại
     */
    public static String getCurrentUrl() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        String url = driver.getCurrentUrl();
        LogUtils.info("📍 Current URL: " + url);
        return url;
    }

    /**
     * Lấy title của trang hiện tại
     *
     * @return Title của trang
     */
    public static String getPageTitle() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        String title = driver.getTitle();
        LogUtils.info("📄 Page title: " + title);
        return title;
    }

    /**
     * Quay lại trang trước
     */
    public static void goBack() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        LogUtils.info("⬅️ Đang quay lại trang trước");
        driver.navigate().back();
        LogUtils.info("✅ Đã quay lại thành công");
    }

    /**
     * Đi tới trang tiếp theo (forward)
     */
    public static void goForward() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        LogUtils.info("➡️ Đang đi tới trang tiếp theo");
        driver.navigate().forward();
        LogUtils.info("✅ Đã đi tới thành công");
    }

    /**
     * Refresh trang hiện tại
     */
    public static void refresh() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        LogUtils.info("🔄 Đang refresh trang");
        driver.navigate().refresh();
        LogUtils.info("✅ Đã refresh thành công");
    }

    /**
     * Kiểm tra xem URL có chứa text không
     *
     * @param text Text cần kiểm tra
     * @return true nếu URL chứa text
     */
    public static boolean urlContains(String text) {
        String currentUrl = getCurrentUrl();
        boolean contains = currentUrl.contains(text);
        LogUtils.info("🔍 URL contains '" + text + "': " + contains);
        return contains;
    }

    /**
     * Kiểm tra xem URL có bằng với URL mong đợi không
     *
     * @param expectedUrl URL mong đợi
     * @return true nếu URL khớp
     */
    public static boolean urlEquals(String expectedUrl) {
        String currentUrl = getCurrentUrl();
        boolean equals = currentUrl.equals(expectedUrl);
        LogUtils.info("🔍 URL equals '" + expectedUrl + "': " + equals);
        return equals;
    }

    /**
     * Chờ URL chứa text (với timeout)
     *
     * @param text Text cần chờ
     * @param timeoutSeconds Timeout tính bằng giây
     * @return true nếu URL chứa text trong thời gian timeout
     */
    public static boolean waitForUrlContains(String text, int timeoutSeconds) {
        LogUtils.info("⏳ Đang chờ URL chứa '" + text + "' trong " + timeoutSeconds + " giây");

        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            if (urlContains(text)) {
                LogUtils.info("✅ URL đã chứa '" + text + "'");
                return true;
            }
            try {
                Thread.sleep(500); // Check mỗi 500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        LogUtils.warn("⏰ Timeout: URL không chứa '" + text + "' sau " + timeoutSeconds + " giây");
        return false;
    }
}

