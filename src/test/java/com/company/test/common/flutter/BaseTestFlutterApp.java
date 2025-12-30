package com.company.test.common.flutter;

import com.company.framework.constants.ConfigData;
import com.company.framework.drivers.DriverManager;
import com.company.framework.drivers.factory.DriverFactory;
import com.company.framework.drivers.manager.AppiumServerManager;
import com.company.framework.enums.AppType;
import com.company.framework.enums.Platform;
import com.company.framework.utils.LogUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * BaseTest cho Flutter App testing
 * Sử dụng cho Flutter apps (Android/iOS)
 */
public class BaseTestFlutterApp {

    /**
     * Setup driver cho Flutter App
     * Nếu deviceConfigName được cung cấp, sẽ đọc config từ device.json
     * Nếu không, sẽ sử dụng các parameters truyền vào từ TestNG
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({
            "platformName", "platformVersion", "deviceName", "udid",
            "appPackage", "appActivity", "appPath",
            "noReset", "fullReset", "autoGrantPermissions",
            "host", "port", "bundleId", "wdaLocalPort", "systemPort",
            "deviceConfigName"
    })
    public void setUpDriver(
            @Optional String platformName,
            @Optional String platformVersion,
            @Optional String deviceName,
            @Optional String udid,
            @Optional String appPackage,
            @Optional String appActivity,
            @Optional String appPath,
            @Optional String noReset,
            @Optional String fullReset,
            @Optional String autoGrantPermissions,
            String host,
            String port,
            @Optional String bundleId,
            @Optional String wdaLocalPort,
            @Optional String systemPort,
            @Optional String deviceConfigName) {

        LogUtils.info("🚀 Setting up Flutter App driver...");

        String finalPlatformName;
        String finalPlatformVersion;
        String finalDeviceName;
        String finalAppPackage;
        String finalAppActivity;
        String finalAppPath;
        boolean finalNoReset;
        boolean finalFullReset;
        boolean finalAutoGrantPermissions;

        // Nếu có deviceConfigName, đọc config từ device.json
        if (deviceConfigName != null && !deviceConfigName.trim().isEmpty()) {
            LogUtils.info("📋 Loading device configuration from device.json: " + deviceConfigName);

            // Xác định platform (nếu không có trong parameter, thử lấy từ JSON hoặc default là "android")
            if (platformName == null || platformName.trim().isEmpty()) {
                // Thử đọc từ JSON, nếu không có thì default
                try {
                    finalPlatformName = ConfigData.getValueJsonConfig("android", deviceConfigName, "platformName");
                    if (finalPlatformName == null || finalPlatformName.trim().isEmpty()) {
                        finalPlatformName = "Android";
                    }
                } catch (Exception e) {
                    finalPlatformName = "Android";
                }
            } else {
                finalPlatformName = platformName;
            }

            String platformKey = finalPlatformName.toLowerCase();

            // Đọc các config từ device.json
            finalPlatformVersion = ConfigData.getValueJsonConfig(platformKey, deviceConfigName, "platformVersion");
            finalDeviceName = ConfigData.getValueJsonConfig(platformKey, deviceConfigName, "deviceName");
            finalAppPackage = ConfigData.getValueJsonConfig(platformKey, deviceConfigName, "appPackage");
            finalAppActivity = ConfigData.getValueJsonConfig(platformKey, deviceConfigName, "appActivity");

            // Handle appPath - có thể là appAndroidPath hoặc appPath trong JSON
            String appAndroidPath = ConfigData.getValueJsonConfig(platformKey, deviceConfigName, "appAndroidPath");
            String appIOSPath = ConfigData.getValueJsonConfig(platformKey, deviceConfigName, "appPath");
            finalAppPath = (appAndroidPath != null && !appAndroidPath.trim().isEmpty()) ? appAndroidPath : appIOSPath;

            finalNoReset = ConfigData.getBooleanValueJsonConfig(platformKey, deviceConfigName, "noReset");
            finalFullReset = ConfigData.getBooleanValueJsonConfig(platformKey, deviceConfigName, "fullReset");
            finalAutoGrantPermissions = ConfigData.getBooleanValueJsonConfig(platformKey, deviceConfigName, "autoGrantPermissions");

            LogUtils.info("✅ Loaded Flutter device config from device.json");
        } else {
            // Sử dụng parameters từ TestNG (backward compatibility)
            LogUtils.info("📋 Using parameters from TestNG suite");
            finalPlatformName = platformName;
            finalPlatformVersion = platformVersion;
            finalDeviceName = deviceName;
            finalAppPackage = appPackage;
            finalAppActivity = appActivity;
            finalAppPath = appPath;
            finalNoReset = (noReset != null && !noReset.trim().isEmpty()) ? Boolean.parseBoolean(noReset) : false;
            finalFullReset = (fullReset != null && !fullReset.trim().isEmpty()) ? Boolean.parseBoolean(fullReset) : false;
            finalAutoGrantPermissions = (autoGrantPermissions != null && !autoGrantPermissions.trim().isEmpty()) ? Boolean.parseBoolean(autoGrantPermissions) : false;
        }

        // Khởi động Appium server nếu cần
        if (ConfigData.APPIUM_DRIVER_LOCAL_SERVICE.trim().equalsIgnoreCase("true")) {
            AppiumServerManager.startServer(host, port);
        }

        // Parse platform
        Platform platform = Platform.fromString(finalPlatformName);

        // Tạo driver config
        DriverFactory.DriverConfig config = new DriverFactory.DriverConfig();
        config.platformVersion = finalPlatformVersion;
        config.deviceName = finalDeviceName;
        config.udid = udid;
        config.appPackage = finalAppPackage;
        config.appActivity = finalAppActivity;
        config.appPath = finalAppPath;
        config.noReset = finalNoReset;
        config.fullReset = finalFullReset;
        config.autoGrantPermissions = finalAutoGrantPermissions;
        config.systemPort = systemPort;
        config.wdaLocalPort = wdaLocalPort;
        config.bundleId = bundleId;

        // Tạo driver với AppType.FLUTTER
        var driver = DriverFactory.createDriver(
                AppType.FLUTTER,
                platform,
                host,
                port,
                config
        );

        // Set driver vào DriverManager
        DriverManager.setDriver(driver);

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LogUtils.info("✅ Flutter App driver setup completed");
        LogUtils.info("💡 Tip: Sử dụng FlutterFinder để tìm elements trong Flutter apps");
    }

    /**
     * Tear down driver
     */
    @AfterMethod(alwaysRun = true)
    public void tearDownDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.quitDriver();
            LogUtils.info("✅ Driver quit successfully");
        }

        // Dừng Appium server nếu đã khởi động
        if (ConfigData.APPIUM_DRIVER_LOCAL_SERVICE.trim().equalsIgnoreCase("true")) {
            AppiumServerManager.stopServer();
        }
    }
}

