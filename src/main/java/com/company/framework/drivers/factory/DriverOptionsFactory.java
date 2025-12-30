package com.company.framework.drivers.factory;

import com.company.framework.enums.Platform;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.util.Objects;

/**
 * Factory class để tạo Driver Options cho các platform khác nhau
 */
public class DriverOptionsFactory {

    /**
     * Tạo UiAutomator2Options cho Android Native/Hybrid App
     */
    public static UiAutomator2Options createAndroidOptions(
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
            String systemPort) {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(Platform.ANDROID.getValue());
        options.setPlatformVersion(platformVersion);
        options.setDeviceName(deviceName);

        if (udid != null && !udid.isEmpty()) {
            options.setUdid(udid);
        }

        if (appPackage != null && !appPackage.isEmpty()) {
            options.setAppPackage(appPackage);
        }

        if (appActivity != null && !appActivity.isEmpty()) {
            options.setAppActivity(appActivity);
        }

        if (appPath != null && !appPath.isEmpty()) {
            options.setApp(appPath);
        }

        options.setAutomationName(Objects.requireNonNullElse(automationName, "UiAutomator2"));
        options.setNoReset(noReset);
        options.setFullReset(fullReset);

        if (autoGrantPermissions) {
            options.setCapability("autoGrantPermissions", true);
        }

        if (systemPort != null && !systemPort.isEmpty()) {
            options.setSystemPort(Integer.parseInt(systemPort));
        }

        return options;
    }

    /**
     * Tạo UiAutomator2Options cho Android Mobile Browser
     */
    public static UiAutomator2Options createAndroidBrowserOptions(
            String platformVersion,
            String deviceName,
            String udid,
            String browserName,
            String systemPort) {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(Platform.ANDROID.getValue());
        options.setPlatformVersion(platformVersion);
        options.setDeviceName(deviceName);

        if (udid != null && !udid.isEmpty()) {
            options.setUdid(udid);
        }

        if (browserName != null && !browserName.isEmpty()) {
            options.withBrowserName(browserName);
        } else {
            options.withBrowserName("Chrome");
        }

        if (systemPort != null && !systemPort.isEmpty()) {
            options.setSystemPort(Integer.parseInt(systemPort));
        }

        return options;
    }

    /**
     * Tạo XCUITestOptions cho iOS Native/Hybrid App
     */
    public static XCUITestOptions createIOSOptions(
            String platformVersion,
            String deviceName,
            String udid,
            String automationName,
            String bundleId,
            String appPath,
            boolean noReset,
            boolean fullReset,
            String wdaLocalPort) {

        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName(Platform.IOS.getValue());
        options.setPlatformVersion(platformVersion);
        options.setDeviceName(deviceName);

        if (udid != null && !udid.isEmpty()) {
            options.setUdid(udid);
        }

        if (bundleId != null && !bundleId.isEmpty()) {
            options.setBundleId(bundleId);
        }

        if (appPath != null && !appPath.isEmpty()) {
            options.setApp(appPath);
        }

        options.setAutomationName(Objects.requireNonNullElse(automationName, "XCUITest"));
        options.setNoReset(noReset);
        options.setFullReset(fullReset);

        if (wdaLocalPort != null && !wdaLocalPort.isEmpty()) {
            options.setWdaLocalPort(Integer.parseInt(wdaLocalPort));
        }

        return options;
    }

    /**
     * Tạo XCUITestOptions cho iOS Mobile Browser
     */
    public static XCUITestOptions createIOSBrowserOptions(
            String platformVersion,
            String deviceName,
            String udid,
            String browserName,
            String wdaLocalPort) {

        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName(Platform.IOS.getValue());
        options.setPlatformVersion(platformVersion);
        options.setDeviceName(deviceName);

        if (udid != null && !udid.isEmpty()) {
            options.setUdid(udid);
        }

        if (browserName != null && !browserName.isEmpty()) {
            options.withBrowserName(browserName);
        } else {
            options.withBrowserName("Safari");
        }

        if (wdaLocalPort != null && !wdaLocalPort.isEmpty()) {
            options.setWdaLocalPort(Integer.parseInt(wdaLocalPort));
        }

        return options;
    }
}

