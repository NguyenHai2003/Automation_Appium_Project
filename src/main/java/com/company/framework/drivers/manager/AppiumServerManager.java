package com.company.framework.drivers.manager;

import com.company.framework.constants.ConfigData;
import com.company.framework.helpers.SystemHelpers;
import com.company.framework.utils.LogUtils;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.Getter;

import java.time.Duration;

/**
 * Quản lý Appium Server - khởi động và dừng server
 */
public class AppiumServerManager {

    @Getter
    private static AppiumDriverLocalService service;
    private static String currentHost;
    private static String currentPort;

    /**
     * Khởi động Appium server với host và port được chỉ định
     *
     * @param host Địa chỉ host của Appium server
     * @param port Port của Appium server
     * @return AppiumDriverLocalService instance
     */
    public static AppiumDriverLocalService startServer(String host, String port) {
        if (service != null && service.isRunning()) {
            LogUtils.info("Appium server đã đang chạy trên " + currentHost + ":" + currentPort);
            return service;
        }

        // Set default values
        if (host == null || host.isEmpty()) {
            host = "127.0.0.1";
        }
        if (port == null || port.isEmpty()) {
            port = "4723";
        }

        currentHost = host;
        currentPort = port;

        int timeoutService = Integer.parseInt(ConfigData.TIMEOUT_SERVICE);

        SystemHelpers.killProcessOnPort(port);

        // Build the Appium service
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(host);
        builder.usingPort(Integer.parseInt(port));
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");
        builder.withTimeout(Duration.ofSeconds(timeoutService));

        // Start the server
        service = AppiumDriverLocalService.buildService(builder);
        service.start();

        if (service.isRunning()) {
            LogUtils.info("✅ Appium server đã khởi động thành công trên " + host + ":" + port);
        } else {
            LogUtils.error("❌ Không thể khởi động Appium server trên " + host + ":" + port);
            throw new RuntimeException("Failed to start Appium server");
        }

        return service;
    }

    /**
     * Dừng Appium server
     */
    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            LogUtils.info("✅ Appium server đã dừng trên " + currentHost + ":" + currentPort);

            // Kill process on port để đảm bảo
            if (currentPort != null) {
                SystemHelpers.killProcessOnPort(currentPort);
            }
        }
    }

    public static boolean isServerRunning() {
        return service != null && service.isRunning();
    }

}

