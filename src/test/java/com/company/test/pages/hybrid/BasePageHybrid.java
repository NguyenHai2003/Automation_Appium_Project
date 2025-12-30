package com.company.test.pages.hybrid;

import com.company.framework.drivers.DriverManager;
import com.company.framework.helpers.webview.WebViewHelper;
import com.company.framework.utils.LogUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * BasePage cho Hybrid App
 * Sử dụng cho Hybrid App pages (kết hợp Native và WebView)
 */
public class BasePageHybrid {

    public BasePageHybrid() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    // Common native elements
    @AndroidFindBy(accessibility = "Back")
    @iOSXCUITFindBy(accessibility = "Back")
    protected WebElement buttonBack;

    /**
     * Click back button (native)
     */
    public void clickBack() {
        // Đảm bảo đang ở native context
        if (WebViewHelper.isWebViewContext()) {
            LogUtils.info("⚠️ Currently in WebView context, switching to native...");
            WebViewHelper.switchToNativeContext();
        }
        buttonBack.click();
    }

    /**
     * Switch to WebView context
     */
    protected void switchToWebView(String webViewName) {
        WebViewHelper.switchToWebView(webViewName);
    }

    /**
     * Switch to Native context
     */
    protected void switchToNativeContext() {
        WebViewHelper.switchToNativeContext();
    }

    /**
     * Kiểm tra xem đang ở context nào
     */
    protected boolean isWebViewContext() {
        return WebViewHelper.isWebViewContext();
    }

    /**
     * Thực thi action trong WebView context
     */
    protected void executeInWebView(Runnable action) {
        WebViewHelper.executeInWebView(action);
    }

    /**
     * Thực thi action trong Native context
     */
    protected void executeInNative(Runnable action) {
        WebViewHelper.executeInNative(action);
    }
}

