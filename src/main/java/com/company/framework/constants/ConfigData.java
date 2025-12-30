package com.company.framework.constants;

import com.company.framework.helpers.PropertiesHelpers;
import com.company.framework.helpers.SystemHelpers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigData {

    private ConfigData() {
        // Ngăn chặn khởi tạo class
    }

    // Load all properties files
    static {
        PropertiesHelpers.loadAllFiles();
    }

    private static JsonNode rootNode;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String PROJECT_PATH = SystemHelpers.getCurrentDir();
    public static final String EXCEL_DATA_FILE_PATH = PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH");
    public static final String JSON_DATA_FILE_PATH = PropertiesHelpers.getValue("JSON_DATA_FILE_PATH");
    public static final String JSON_CONFIG_FILE_PATH = PropertiesHelpers.getValue("JSON_CONFIG_FILE_PATH");
    public static final String TEST_DATA_FOLDER_PATH = PropertiesHelpers.getValue("TEST_DATA_FOLDER_PATH");
    public static final String LOCATE = PropertiesHelpers.getValue("LOCATE");
    public static final String TIMEOUT_SERVICE = PropertiesHelpers.getValue("TIMEOUT_SERVICE");
    public static final String TIMEOUT_EXPLICIT_DEFAULT = PropertiesHelpers.getValue("TIMEOUT_EXPLICIT_DEFAULT");
    public static final String APPIUM_DRIVER_LOCAL_SERVICE = PropertiesHelpers.getValue("APPIUM_DRIVER_LOCAL_SERVICE");
    public static final String STEP_ACTION_TIMEOUT = PropertiesHelpers.getValue("STEP_ACTION_TIMEOUT");
    public static final String SCREENSHOT_FAIL = PropertiesHelpers.getValue("SCREENSHOT_FAIL");
    public static final String SCREENSHOT_PASS = PropertiesHelpers.getValue("SCREENSHOT_PASS");
    public static final String SCREENSHOT_ALL_STEP = PropertiesHelpers.getValue("SCREENSHOT_ALL_STEP");
    public static final String SCREENSHOT_PATH = PropertiesHelpers.getValue("SCREENSHOT_PATH");
    public static final String RECORD_VIDEO = PropertiesHelpers.getValue("RECORD_VIDEO");
    public static final String RECORD_VIDEO_PATH = PropertiesHelpers.getValue("RECORD_VIDEO_PATH");
    public static final String AUTO_SETUP_CHROMEDRIVER = PropertiesHelpers.getValue("AUTO_SETUP_CHROMEDRIVER");
    public static final String CHROMEDRIVER_VERSION = PropertiesHelpers.getValue("CHROMEDRIVER_VERSION");
    public static final String CHROME_BROWSER_VERSION = PropertiesHelpers.getValue("CHROME_BROWSER_VERSION");


    /**
     * Hàm nội bộ để tải file JSON nếu chưa được tải
     */
    private static void loadJsonConfig() {
        if (rootNode == null) {
            try {
                rootNode = mapper.readTree(new File(JSON_CONFIG_FILE_PATH));
                System.out.println("✅ Đã tải file cấu hình JSON vào bộ nhớ.");
            } catch (IOException e) {
                throw new RuntimeException("Lỗi nghiêm trọng: Không thể đọc file JSON tại " + JSON_CONFIG_FILE_PATH, e);
            }
        }
    }

    /**
     * Hàm lấy giá trị String
     */
    public static String getValueJsonConfig(String platform, String device, String propertyName) {
        loadJsonConfig();
        JsonNode node = rootNode.path("platforms")
                .path(platform.trim().toLowerCase())
                .path("devices")
                .path(device.trim().toLowerCase())
                .path(propertyName);

        String result = node.asText("");
        System.out.println("*** " + propertyName + ": " + result);
        return result;
    }

    /**
     * Hàm lấy giá trị Boolean
     */
    public static boolean getBooleanValueJsonConfig(String platform, String device, String propertyName) {
        loadJsonConfig();
        JsonNode node = rootNode.path("platforms")
                .path(platform.trim().toLowerCase())
                .path("devices")
                .path(device.trim().toLowerCase())
                .path(propertyName);

        if (node.isMissingNode()) {
            return false;
        }
        boolean result = node.asBoolean();
        System.out.println("*** " + propertyName + ": " + result);
        return result;
    }
}
