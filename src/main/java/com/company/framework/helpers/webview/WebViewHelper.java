package com.company.framework.helpers.webview;

import com.company.framework.drivers.DriverManager;
import com.company.framework.utils.LogUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.util.Set;

/**
 * Helper class để xử lý WebView trong Hybrid App
 * Cung cấp các methods để switch context giữa Native và WebView
 */
public class WebViewHelper {

    /**
     * Lấy danh sách tất cả các context có sẵn
     *
     * @return Set các context names
     */
    public static Set<String> getAvailableContexts() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        Set<String> contexts;
        if (driver instanceof AndroidDriver) {
            contexts = ((AndroidDriver) driver).getContextHandles();
        } else if (driver instanceof IOSDriver) {
            contexts = ((IOSDriver) driver).getContextHandles();
        } else {
            throw new UnsupportedOperationException("Driver type không hỗ trợ context switching");
        }
        LogUtils.info("📱 Available contexts: " + contexts);
        return contexts;
    }

    /**
     * Lấy context hiện tại
     *
     * @return Tên context hiện tại
     */
    public static String getCurrentContext() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        String context;
        if (driver instanceof AndroidDriver) {
            context = ((AndroidDriver) driver).getContext();
        } else if (driver instanceof IOSDriver) {
            context = ((IOSDriver) driver).getContext();
        } else {
            throw new UnsupportedOperationException("Driver type không hỗ trợ context switching");
        }
        LogUtils.info("📍 Current context: " + context);
        return context;
    }

    /**
     * Switch sang WebView context
     *
     * @param webViewName Tên của WebView (ví dụ: "WEBVIEW_com.example.app")
     *                    Nếu null, sẽ tự động tìm WebView đầu tiên
     */
    public static void switchToWebView(String webViewName) {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        Set<String> contexts = getAvailableContexts();

        if (webViewName == null || webViewName.isEmpty()) {
            // Tự động tìm WebView context đầu tiên
            for (String context : contexts) {
                if (context.contains("WEBVIEW")) {
                    webViewName = context;
                    break;
                }
            }
        }

        if (webViewName == null || !contexts.contains(webViewName)) {
            throw new IllegalStateException("WebView context không tìm thấy: " + webViewName);
        }

        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).context(webViewName);
        } else if (driver instanceof IOSDriver) {
            ((IOSDriver) driver).context(webViewName);
        } else {
            throw new UnsupportedOperationException("Driver type không hỗ trợ context switching");
        }
        LogUtils.info("✅ Đã switch sang WebView context: " + webViewName);
    }

    /**
     * Switch về Native context
     */
    public static void switchToNativeContext() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver chưa được khởi tạo");
        }

        Set<String> contexts = getAvailableContexts();
        String nativeContext = null;

        // Tìm Native context (thường là "NATIVE_APP")
        for (String context : contexts) {
            if (context.contains("NATIVE") || !context.contains("WEBVIEW")) {
                nativeContext = context;
                break;
            }
        }

        if (nativeContext == null) {
            // Fallback: lấy context đầu tiên không phải WebView
            for (String context : contexts) {
                if (!context.contains("WEBVIEW")) {
                    nativeContext = context;
                    break;
                }
            }
        }

        if (nativeContext == null) {
            throw new IllegalStateException("Native context không tìm thấy");
        }

        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).context(nativeContext);
        } else if (driver instanceof IOSDriver) {
            ((IOSDriver) driver).context(nativeContext);
        } else {
            throw new UnsupportedOperationException("Driver type không hỗ trợ context switching");
        }
        LogUtils.info("✅ Đã switch về Native context: " + nativeContext);
    }

    /**
     * Kiểm tra xem context hiện tại có phải WebView không
     *
     * @return true nếu đang ở WebView context
     */
    public static boolean isWebViewContext() {
        String currentContext = getCurrentContext();
        boolean isWebView = currentContext != null && currentContext.contains("WEBVIEW");
        LogUtils.info("🔍 Is WebView context: " + isWebView);
        return isWebView;
    }

    /**
     * Kiểm tra xem context hiện tại có phải Native không
     *
     * @return true nếu đang ở Native context
     */
    public static boolean isNativeContext() {
        return !isWebViewContext();
    }

    /**
     * Switch context nếu cần thiết
     *
     * @param targetContext Tên context mong muốn
     */
    public static void switchContextIfNeeded(String targetContext) {
        String currentContext = getCurrentContext();
        if (!currentContext.equals(targetContext)) {
            AppiumDriver driver = DriverManager.getDriver();
            if (driver instanceof AndroidDriver) {
                ((AndroidDriver) driver).context(targetContext);
            } else if (driver instanceof IOSDriver) {
                ((IOSDriver) driver).context(targetContext);
            } else {
                throw new UnsupportedOperationException("Driver type không hỗ trợ context switching");
            }
            LogUtils.info("🔄 Đã switch context từ " + currentContext + " sang " + targetContext);
        } else {
            LogUtils.info("ℹ️ Đã ở đúng context: " + targetContext);
        }
    }

    /**
     * Thực thi action trong WebView context, sau đó quay về Native context
     *
     * @param action Runnable action cần thực thi trong WebView
     */
    public static void executeInWebView(Runnable action) {
        String originalContext = getCurrentContext();
        try {
            switchToWebView(null);
            action.run();
        } finally {
            switchContextIfNeeded(originalContext);
        }
    }

    /**
     * Thực thi action trong Native context, sau đó quay về context ban đầu
     *
     * @param action Runnable action cần thực thi trong Native
     */
    public static void executeInNative(Runnable action) {
        String originalContext = getCurrentContext();
        try {
            switchToNativeContext();
            action.run();
        } finally {
            switchContextIfNeeded(originalContext);
        }
    }
}

