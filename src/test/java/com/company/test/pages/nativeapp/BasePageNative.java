package com.company.test.pages.nativeapp;

import com.company.framework.drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * BasePage cho Native App
 * Sử dụng cho Native App pages
 */
public class BasePageNative {

    public BasePageNative() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    // Common elements có thể được thêm vào đây
    @AndroidFindBy(accessibility = "Back")
    @iOSXCUITFindBy(accessibility = "Back")
    protected WebElement buttonBack;

    public void clickBack() {
        buttonBack.click();
    }
}

