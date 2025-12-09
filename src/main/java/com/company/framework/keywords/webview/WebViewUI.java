package com.company.framework.keywords.webview;

import com.company.framework.constants.ConfigData;
import com.company.framework.drivers.DriverManager;
import com.company.framework.helpers.webview.WebViewHelper;
import com.company.framework.reports.AllureManager;
import com.company.framework.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WebViewUI - Class chứa các keywords cho WebView trong Hybrid App
 * Sử dụng cho Hybrid App khi cần tương tác với WebView
 */
public class WebViewUI {

    private static final int DEFAULT_TIMEOUT = Integer.parseInt(ConfigData.TIMEOUT_EXPLICIT_DEFAULT);
    private static final double STEP_ACTION_TIMEOUT = Double.parseDouble(ConfigData.STEP_ACTION_TIMEOUT);

    /**
     * Click element trong WebView context
     * Tự động switch sang WebView context nếu cần
     */
    @Step("Click element {0} in WebView")
    public static void clickElementInWebView(By locator) {
        clickElementInWebView(locator, null);
    }

    /**
     * Click element trong WebView context với tên WebView cụ thể
     */
    @Step("Click element {0} in WebView {1}")
    public static void clickElementInWebView(By locator, String webViewName) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[WebViewUI] Clicking element in WebView: " + locator);

        // Switch to WebView context
        if (webViewName != null) {
            WebViewHelper.switchToWebView(webViewName);
        } else {
            WebViewHelper.switchToWebView(null);
        }

        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }

        WebElement element = waitForElementToBeClickable(locator);
        element.click();
        LogUtils.info("[WebViewUI] Element clicked successfully");
    }

    /**
     * Set text vào element trong WebView
     */
    @Step("Set text '{1}' on element {0} in WebView")
    public static void setTextInWebView(By locator, String text) {
        setTextInWebView(locator, text, null);
    }

    /**
     * Set text vào element trong WebView với tên WebView cụ thể
     */
    @Step("Set text '{1}' on element {0} in WebView {2}")
    public static void setTextInWebView(By locator, String text, String webViewName) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[WebViewUI] Setting text '" + text + "' in WebView: " + locator);

        // Switch to WebView context
        if (webViewName != null) {
            WebViewHelper.switchToWebView(webViewName);
        } else {
            WebViewHelper.switchToWebView(null);
        }

        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }

        WebElement element = waitForElementVisible(locator);
        element.click();
        element.clear();
        element.sendKeys(text);
        LogUtils.info("[WebViewUI] Text set successfully");
    }

    /**
     * Lấy text từ element trong WebView
     */
    @Step("Get text from element {0} in WebView")
    public static String getTextInWebView(By locator) {
        return getTextInWebView(locator, null);
    }

    /**
     * Lấy text từ element trong WebView với tên WebView cụ thể
     */
    @Step("Get text from element {0} in WebView {1}")
    public static String getTextInWebView(By locator, String webViewName) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[WebViewUI] Getting text from element in WebView: " + locator);

        // Switch to WebView context
        if (webViewName != null) {
            WebViewHelper.switchToWebView(webViewName);
        } else {
            WebViewHelper.switchToWebView(null);
        }

        WebElement element = waitForElementVisible(locator);
        String text = element.getText();
        AllureManager.saveTextLog("➡️ TEXT: " + text);
        return text;
    }

    /**
     * Chờ page load trong WebView
     */
    public static void waitForPageLoad(int timeoutSeconds) {
        LogUtils.info("[WebViewUI] Waiting for page to load in WebView");
        WebDriver driver = (WebDriver) DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        LogUtils.info("[WebViewUI] Page loaded successfully");
    }

    /**
     * Thực thi JavaScript trong WebView
     */
    public static Object executeJavaScript(String script) {
        LogUtils.info("[WebViewUI] Executing JavaScript: " + script);
        WebDriver driver = (WebDriver) DriverManager.getDriver();
        Object result = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
        LogUtils.info("[WebViewUI] JavaScript executed successfully");
        return result;
    }

    // Wait methods
    public static WebElement waitForElementToBeClickable(By locator) {
        LogUtils.info("[WebViewUI] Waiting for element to be clickable: " + locator);
        WebDriver driver = (WebDriver) DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementVisible(By locator) {
        LogUtils.info("[WebViewUI] Waiting for element to be visible: " + locator);
        WebDriver driver = (WebDriver) DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static void sleep(double second) {
        LogUtils.info("[WebViewUI] Sleeping for " + second + " seconds.");
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

