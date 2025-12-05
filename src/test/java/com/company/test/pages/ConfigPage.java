package com.company.test.pages;

import com.company.framework.drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ConfigPage extends BasePage {
    public ConfigPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(accessibility = "Logout")
    @iOSXCUITFindBy(accessibility = "Logout")
    private WebElement itemLogout;

    public LoginPage logout() {
        itemLogout.click();

        return new LoginPage();
    }
}

