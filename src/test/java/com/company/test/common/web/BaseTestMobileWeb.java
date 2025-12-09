package com.company.test.common.web;

import com.company.framework.constants.ConfigData;
import com.company.framework.drivers.DriverManager;
import com.company.framework.drivers.factory.DriverFactory;
import com.company.framework.drivers.manager.AppiumServerManager;
import com.company.framework.enums.BrowserType;
import com.company.framework.enums.Platform;
import com.company.framework.utils.LogUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * BaseTest cho Mobile Web Browser testing
 * Sử dụng cho Mobile Web (Chrome, Safari mobile)
 *
 * Ví dụ sử dụng:
 * <pre>
 * public class MyWebTest extends BaseTestMobileWeb {
 *     @Test
 *     public void testWebFeature() {
 *         BrowserUI.navigateToUrl("https://example.com");
 *         BrowserUI.clickElement(By.id("button"));
 *     }
 * }
 * </pre>
 */
public class BaseTestMobileWeb {

    /**
     * Setup driver cho Mobile Web Browser
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({
            "platformName", "platformVersion", "deviceName", "udid",
            "browserType", "host", "port", "wdaLocalPort", "systemPort"
    })
    public void setUpDriver(
            String platformName,
            String platformVersion,
            String deviceName,
            @Optional String udid,
            @Optional String browserType,
            String host,
            String port,
            @Optional String wdaLocalPort,
            @Optional String systemPort) {

        LogUtils.info("🚀 Setting up Mobile Web Browser driver...");

        // Khởi động Appium server nếu cần
        if (ConfigData.APPIUM_DRIVER_LOCAL_SERVICE.trim().equalsIgnoreCase("true")) {
            AppiumServerManager.startServer(host, port);
        }

        // Parse platform
        Platform platform = Platform.fromString(platformName);

        // Parse browser type
        BrowserType browser = BrowserType.CHROME; // Default
        if (browserType != null && !browserType.isEmpty()) {
            try {
                browser = BrowserType.fromString(browserType);
            } catch (IllegalArgumentException e) {
                LogUtils.warn("⚠️ Invalid browser type: " + browserType + ", using default: Chrome");
            }
        }

        // Tạo driver cho Mobile Web
        var driver = DriverFactory.createMobileWebDriver(
                platform,
                browser,
                host,
                port,
                platformVersion,
                deviceName,
                udid,
                systemPort,
                wdaLocalPort
        );

        // Set driver vào DriverManager
        DriverManager.setDriver(driver);

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LogUtils.info("✅ Mobile Web Browser driver setup completed");
        LogUtils.info("🌐 Browser: " + browser.getValue());
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

