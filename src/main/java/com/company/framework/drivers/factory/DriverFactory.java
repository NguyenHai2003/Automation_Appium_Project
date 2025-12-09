package com.company.framework.drivers.factory;

import com.company.framework.enums.AppType;
import com.company.framework.enums.BrowserType;
import com.company.framework.enums.Platform;
import com.company.framework.utils.LogUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Factory class để tạo AppiumDriver cho các loại ứng dụng khác nhau
 */
public class DriverFactory {

    /**
     * Tạo driver cho Native App
     */
    public static AppiumDriver createNativeDriver(
            Platform platform,
            String host,
            String port,
            String platformVersion,
            String deviceName,
            String udid,
            String automationName,
            String appPackage,
            String appActivity,
            String appPath,
            boolean noReset,
            boolean fullReset,
            boolean autoGrantPermissions,
            String systemPort,
            String wdaLocalPort,
            String bundleId) {

        LogUtils.info("🔧 Đang tạo Native Driver cho platform: " + platform.getValue());

        try {
            URL serverUrl = new URL("http://" + host + ":" + port);

            if (platform == Platform.ANDROID) {
                var options = DriverOptionsFactory.createAndroidOptions(
                        platformVersion, deviceName, udid, automationName,
                        appPackage, appActivity, appPath,
                        noReset, fullReset, autoGrantPermissions, systemPort);

                AndroidDriver driver = new AndroidDriver(serverUrl, options);
                LogUtils.info("✅ Android Native Driver đã được tạo thành công");
                return driver;

            } else if (platform == Platform.IOS) {
                var options = DriverOptionsFactory.createIOSOptions(
                        platformVersion, deviceName, udid, automationName,
                        bundleId, appPath, noReset, fullReset, wdaLocalPort);

                IOSDriver driver = new IOSDriver(serverUrl, options);
                LogUtils.info("✅ iOS Native Driver đã được tạo thành công");
                return driver;

            } else {
                throw new IllegalArgumentException("Platform không được hỗ trợ: " + platform);
            }

        } catch (MalformedURLException e) {
            LogUtils.error("❌ Lỗi URL không hợp lệ: " + e.getMessage());
            throw new RuntimeException("Không thể tạo driver do URL không hợp lệ", e);
        }
    }

    /**
     * Tạo driver cho Hybrid App (tương tự Native nhưng có thể switch context)
     */
    public static AppiumDriver createHybridDriver(
            Platform platform,
            String host,
            String port,
            String platformVersion,
            String deviceName,
            String udid,
            String automationName,
            String appPackage,
            String appActivity,
            String appPath,
            boolean noReset,
            boolean fullReset,
            boolean autoGrantPermissions,
            String systemPort,
            String wdaLocalPort,
            String bundleId) {

        LogUtils.info("🔧 Đang tạo Hybrid Driver cho platform: " + platform.getValue());

        // Hybrid app sử dụng cùng driver như Native app
        // Sự khác biệt là ở việc switch context trong quá trình test
        return createNativeDriver(platform, host, port, platformVersion, deviceName, udid,
                automationName, appPackage, appActivity, appPath, noReset, fullReset,
                autoGrantPermissions, systemPort, wdaLocalPort, bundleId);
    }

    /**
     * Tạo driver cho Mobile Web Browser
     */
    public static AppiumDriver createMobileWebDriver(
            Platform platform,
            BrowserType browserType,
            String host,
            String port,
            String platformVersion,
            String deviceName,
            String udid,
            String systemPort,
            String wdaLocalPort) {

        LogUtils.info("🔧 Đang tạo Mobile Web Driver cho platform: " + platform.getValue() +
                ", browser: " + browserType.getValue());

        try {
            URL serverUrl = new URL("http://" + host + ":" + port);

            if (platform == Platform.ANDROID) {
                var options = DriverOptionsFactory.createAndroidBrowserOptions(
                        platformVersion, deviceName, udid, browserType.getValue(), systemPort);

                AndroidDriver driver = new AndroidDriver(serverUrl, options);
                LogUtils.info("✅ Android Mobile Web Driver đã được tạo thành công");
                return driver;

            } else if (platform == Platform.IOS) {
                var options = DriverOptionsFactory.createIOSBrowserOptions(
                        platformVersion, deviceName, udid, browserType.getValue(), wdaLocalPort);

                IOSDriver driver = new IOSDriver(serverUrl, options);
                LogUtils.info("✅ iOS Mobile Web Driver đã được tạo thành công");
                return driver;

            } else {
                throw new IllegalArgumentException("Platform không được hỗ trợ: " + platform);
            }

        } catch (MalformedURLException e) {
            LogUtils.error("❌ Lỗi URL không hợp lệ: " + e.getMessage());
            throw new RuntimeException("Không thể tạo driver do URL không hợp lệ", e);
        }
    }

    /**
     * Tạo driver dựa trên AppType
     */
    public static AppiumDriver createDriver(
            AppType appType,
            Platform platform,
            String host,
            String port,
            DriverConfig config) {

        switch (appType) {
            case NATIVE:
                return createNativeDriver(platform, host, port,
                        config.platformVersion, config.deviceName, config.udid,
                        config.automationName, config.appPackage, config.appActivity,
                        config.appPath, config.noReset, config.fullReset,
                        config.autoGrantPermissions, config.systemPort,
                        config.wdaLocalPort, config.bundleId);

            case HYBRID:
                return createHybridDriver(platform, host, port,
                        config.platformVersion, config.deviceName, config.udid,
                        config.automationName, config.appPackage, config.appActivity,
                        config.appPath, config.noReset, config.fullReset,
                        config.autoGrantPermissions, config.systemPort,
                        config.wdaLocalPort, config.bundleId);

            case MOBILE_WEB:
                BrowserType browserType = config.browserType != null ?
                        BrowserType.fromString(config.browserType) : BrowserType.CHROME;
                return createMobileWebDriver(platform, browserType, host, port,
                        config.platformVersion, config.deviceName, config.udid,
                        config.systemPort, config.wdaLocalPort);

            default:
                throw new IllegalArgumentException("AppType không được hỗ trợ: " + appType);
        }
    }

    /**
     * Inner class để chứa driver configuration
     */
    public static class DriverConfig {
        public String platformVersion;
        public String deviceName;
        public String udid;
        public String automationName;
        public String appPackage;
        public String appActivity;
        public String appPath;
        public boolean noReset;
        public boolean fullReset;
        public boolean autoGrantPermissions;
        public String systemPort;
        public String wdaLocalPort;
        public String bundleId;
        public String browserType;
    }
}

