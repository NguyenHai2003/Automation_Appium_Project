package com.company.test.pages.web;

import com.company.framework.drivers.DriverManager;
import com.company.framework.helpers.browser.BrowserHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage cho Mobile Web Browser
 * Sử dụng cho Mobile Web pages
 */
public class BasePageWeb {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePageWeb() {
        this.driver = (WebDriver) DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Lấy URL hiện tại
     */
    protected String getCurrentUrl() {
        return BrowserHelper.getCurrentUrl();
    }

    /**
     * Lấy title của trang
     */
    protected String getPageTitle() {
        return BrowserHelper.getPageTitle();
    }

    /**
     * Điều hướng đến URL
     */
    protected void navigateTo(String url) {
        BrowserHelper.navigateToUrl(url);
    }

    /**
     * Quay lại trang trước
     */
    protected void goBack() {
        BrowserHelper.goBack();
    }

    /**
     * Refresh trang
     */
    protected void refresh() {
        BrowserHelper.refresh();
    }

    /**
     * Chờ page load
     */
    protected void waitForPageLoad() {
        BrowserHelper.waitForUrlContains("", 10); // Simple wait
    }
}

