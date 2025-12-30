package com.company.framework.drivers.manager;

import com.company.framework.utils.LogUtils;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Manager class để tự động tải và quản lý ChromeDriver
 * Hỗ trợ tự động detect và download ChromeDriver phù hợp với Chrome version trên thiết bị
 */
public class ChromeDriverManager {

    private ChromeDriverManager() {
        // Ngăn chặn khởi tạo class
    }

    /**
     * Setup ChromeDriver tự động - tự động detect và download version phù hợp
     * Sử dụng WebDriverManager để tự động quản lý
     */
    public static void setupChromeDriver() {
        try {
            LogUtils.info("🔧 Đang setup ChromeDriver tự động...");
            WebDriverManager.chromedriver().setup();
            LogUtils.info("✅ ChromeDriver đã được setup thành công");
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi setup ChromeDriver: " + e.getMessage());
            throw new RuntimeException("Không thể setup ChromeDriver", e);
        }
    }

    /**
     * Setup ChromeDriver với version cụ thể
     * @param version Version của ChromeDriver cần tải (ví dụ: "120.0.6099.109")
     */
    public static void setupChromeDriver(String version) {
        try {
            LogUtils.info("🔧 Đang setup ChromeDriver version: " + version);
            WebDriverManager.chromedriver().driverVersion(version).setup();
            LogUtils.info("✅ ChromeDriver version " + version + " đã được setup thành công");
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi setup ChromeDriver version " + version + ": " + e.getMessage());
            throw new RuntimeException("Không thể setup ChromeDriver version " + version, e);
        }
    }

    /**
     * Setup ChromeDriver với version phù hợp với Chrome browser version
     * @param chromeVersion Version của Chrome browser (ví dụ: "120.0.6099.109")
     */
    public static void setupChromeDriverForChromeVersion(String chromeVersion) {
        try {
            LogUtils.info("🔧 Đang setup ChromeDriver phù hợp với Chrome version: " + chromeVersion);
            // WebDriverManager tự động tìm ChromeDriver version phù hợp với Chrome version
            WebDriverManager.chromedriver().browserVersion(chromeVersion).setup();
            LogUtils.info("✅ ChromeDriver phù hợp với Chrome " + chromeVersion + " đã được setup thành công");
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi setup ChromeDriver cho Chrome version " + chromeVersion + ": " + e.getMessage());
            // Fallback: thử setup tự động
            LogUtils.info("⚠️ Đang thử setup ChromeDriver tự động...");
            setupChromeDriver();
        }
    }

    /**
     * Lấy đường dẫn đến ChromeDriver đã được tải
     * @return Đường dẫn đến ChromeDriver executable
     */
    public static String getChromeDriverPath() {
        try {
            String driverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
            LogUtils.info("📁 ChromeDriver path: " + driverPath);
            return driverPath;
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi lấy ChromeDriver path: " + e.getMessage());
            return null;
        }
    }

    /**
     * Clear cache của WebDriverManager (xóa các driver đã tải)
     * Có thể xóa thủ công cache folder nếu cần
     */
    public static void clearCache() {
        try {
            LogUtils.info("🧹 Đang xóa cache của WebDriverManager...");
            LogUtils.info("ℹ️ Để xóa cache, vui lòng xóa thủ công folder: ~/.cache/selenium (Linux/Mac) hoặc %LOCALAPPDATA%\\selenium (Windows)");
            LogUtils.info("✅ Cache info đã được hiển thị");
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi xóa cache: " + e.getMessage());
        }
    }

    /**
     * Setup ChromeDriver với các tùy chọn nâng cao
     * @param useBetaVersion Sử dụng beta version
     * @param useDriverVersion Sử dụng driver version cụ thể (null nếu muốn auto-detect)
     * @param useBrowserVersion Sử dụng browser version cụ thể (null nếu muốn auto-detect)
     */
    public static void setupChromeDriverAdvanced(Boolean useBetaVersion, String useDriverVersion, String useBrowserVersion) {
        try {
            LogUtils.info("🔧 Đang setup ChromeDriver với tùy chọn nâng cao...");
            WebDriverManager wdm = WebDriverManager.chromedriver();

            if (useBetaVersion != null && useBetaVersion) {
                wdm.useBetaVersions();
                LogUtils.info("📌 Sử dụng beta version");
            }

            if (useDriverVersion != null && !useDriverVersion.isEmpty()) {
                wdm.driverVersion(useDriverVersion);
                LogUtils.info("📌 Sử dụng driver version: " + useDriverVersion);
            }

            if (useBrowserVersion != null && !useBrowserVersion.isEmpty()) {
                wdm.browserVersion(useBrowserVersion);
                LogUtils.info("📌 Sử dụng browser version: " + useBrowserVersion);
            }

            wdm.setup();
            LogUtils.info("✅ ChromeDriver đã được setup thành công với các tùy chọn nâng cao");
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi setup ChromeDriver: " + e.getMessage());
            throw new RuntimeException("Không thể setup ChromeDriver", e);
        }
    }

    /**
     * Kiểm tra xem ChromeDriver đã được tải chưa
     * @return true nếu ChromeDriver đã được tải, false nếu chưa
     */
    public static boolean isChromeDriverDownloaded() {
        try {
            String driverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
            return driverPath != null && !driverPath.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}

