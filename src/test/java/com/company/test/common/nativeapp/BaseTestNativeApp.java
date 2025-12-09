package com.company.test.common.nativeapp;

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
 * BaseTest cho Native App testing
 * Sử dụng cho Native App (Android/iOS native apps)
 *
 * Ví dụ sử dụng:
 * <pre>
 * public class MyNativeTest extends BaseTestNativeApp {
 *     @Test
 *     public void testNativeFeature() {
 *         // Test code here
 *     }
 * }
 * </pre>
 */
public class BaseTestNativeApp {

    /**
     * Setup driver cho Native App
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({
            "platformName", "platformVersion", "deviceName", "udid",
            "automationName", "appPackage", "appActivity", "appPath",
            "noReset", "fullReset", "autoGrantPermissions",
            "host", "port", "bundleId", "wdaLocalPort", "systemPort"
    })
    public void setUpDriver(
            String platformName,
            String platformVersion,
            String deviceName,
            @Optional String udid,
            @Optional String automationName,
            @Optional String appPackage,
            @Optional String appActivity,
            @Optional String appPath,
            boolean noReset,
            boolean fullReset,
            boolean autoGrantPermissions,
            String host,
            String port,
            @Optional String bundleId,
            @Optional String wdaLocalPort,
            @Optional String systemPort) {

        LogUtils.info("🚀 Setting up Native App driver...");

        // Khởi động Appium server nếu cần
        if (ConfigData.APPIUM_DRIVER_LOCAL_SERVICE.trim().equalsIgnoreCase("true")) {
            AppiumServerManager.startServer(host, port);
        }

        // Parse platform
        Platform platform = Platform.fromString(platformName);

        // Tạo driver config
        DriverFactory.DriverConfig config = new DriverFactory.DriverConfig();
        config.platformVersion = platformVersion;
        config.deviceName = deviceName;
        config.udid = udid;
        config.automationName = automationName;
        config.appPackage = appPackage;
        config.appActivity = appActivity;
        config.appPath = appPath;
        config.noReset = noReset;
        config.fullReset = fullReset;
        config.autoGrantPermissions = autoGrantPermissions;
        config.systemPort = systemPort;
        config.wdaLocalPort = wdaLocalPort;
        config.bundleId = bundleId;

        // Tạo driver
        var driver = DriverFactory.createDriver(
                AppType.NATIVE,
                platform,
                host,
                port,
                config
        );

        // Set driver vào DriverManager
        DriverManager.setDriver(driver);

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LogUtils.info("✅ Native App driver setup completed");
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

