package com.company.framework.keywords.browser;

import com.company.framework.constants.ConfigData;
import com.company.framework.drivers.DriverManager;
import com.company.framework.helpers.browser.BrowserHelper;
import com.company.framework.reports.AllureManager;
import com.company.framework.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BrowserUI - Class chứa các keywords cho Mobile Web Browser testing
 * Sử dụng cho Mobile Web (Chrome, Safari mobile)
 */
public class BrowserUI {

    private static final int DEFAULT_TIMEOUT = Integer.parseInt(ConfigData.TIMEOUT_EXPLICIT_DEFAULT);
    private static final double STEP_ACTION_TIMEOUT = Double.parseDouble(ConfigData.STEP_ACTION_TIMEOUT);

    /**
     * Điều hướng đến URL
     */
    @Step("Navigate to URL: {0}")
    public static void navigateToUrl(String url) {
        BrowserHelper.navigateToUrl(url);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
    }

    /**
     * Lấy URL hiện tại
     */
    @Step("Get current URL")
    public static String getCurrentUrl() {
        return BrowserHelper.getCurrentUrl();
    }

    /**
     * Lấy title của trang
     */
    @Step("Get page title")
    public static String getPageTitle() {
        return BrowserHelper.getPageTitle();
    }

    /**
     * Quay lại trang trước
     */
    @Step("Go back")
    public static void goBack() {
        BrowserHelper.goBack();
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
    }

    /**
     * Đi tới trang tiếp theo
     */
    @Step("Go forward")
    public static void goForward() {
        BrowserHelper.goForward();
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
    }

    /**
     * Refresh trang
     */
    @Step("Refresh page")
    public static void refresh() {
        BrowserHelper.refresh();
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
    }

    /**
     * Click element trong browser
     */
    @Step("Click element {0}")
    public static void clickElement(By locator) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[BrowserUI] Clicking element: " + locator);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        waitForElementToBeClickable(locator).click();
    }

    /**
     * Set text vào element trong browser
     */
    @Step("Set text '{1}' on element {0}")
    public static void setText(By locator, String text) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[BrowserUI] Setting text '" + text + "' on element: " + locator);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        WebElement element = waitForElementVisible(locator);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Lấy text từ element trong browser
     */
    @Step("Get text from element {0}")
    public static String getElementText(By locator) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[BrowserUI] Getting text from element: " + locator);
        WebElement element = waitForElementVisible(locator);
        String text = element.getText();
        AllureManager.saveTextLog("➡️ TEXT: " + text);
        return text;
    }

    /**
     * Gửi phím (keys) vào element trong browser
     * Có thể dùng để gửi Keys.ENTER, Keys.TAB, v.v.
     */
    @Step("Send keys '{1}' to element {0}")
    public static void sendKeys(By locator, CharSequence... keys) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[BrowserUI] Sending keys to element: " + locator);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        WebElement element = waitForElementVisible(locator);
        element.sendKeys(keys);
        LogUtils.info("[BrowserUI] Keys sent successfully");
    }

    /**
     * Chờ page load
     */
    public static void waitForPageLoad(int timeoutSeconds) {
        LogUtils.info("[BrowserUI] Waiting for page to load");
        WebDriver driver = (WebDriver) DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        LogUtils.info("[BrowserUI] Page loaded successfully");
    }

    /**
     * Chờ URL chứa text
     */
    public static boolean waitForUrlContains(String text, int timeoutSeconds) {
        return BrowserHelper.waitForUrlContains(text, timeoutSeconds);
    }

    // Wait methods
    public static WebElement waitForElementToBeClickable(By locator) {
        LogUtils.info("[BrowserUI] Waiting for element to be clickable: " + locator);
        WebDriver driver = (WebDriver) DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementVisible(By locator) {
        LogUtils.info("[BrowserUI] Waiting for element to be visible: " + locator);
        WebDriver driver = (WebDriver) DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static void sleep(double second) {
        LogUtils.info("[BrowserUI] Sleeping for " + second + " seconds.");
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

