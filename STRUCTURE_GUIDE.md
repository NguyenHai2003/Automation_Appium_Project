# 📚 HƯỚNG DẪN CẤU TRÚC FRAMEWORK

## 🎯 Tổng Quan

Framework được tổ chức để hỗ trợ 3 loại ứng dụng mobile:

- **Native App**: Ứng dụng native thuần túy (Android/iOS)
- **Hybrid App**: Ứng dụng kết hợp native và WebView
- **Mobile Web**: Ứng dụng web chạy trên mobile browser

---

## 📂 Cấu Trúc Thư Mục

### 1. Framework Core (`src/main/java/com/company/framework/`)

#### `enums/` - Định nghĩa các enum

- `Platform.java`: Android, iOS
- `AppType.java`: NATIVE, HYBRID, MOBILE_WEB
- `BrowserType.java`: CHROME, SAFARI

#### `drivers/` - Quản lý Driver

- `DriverManager.java`: Quản lý driver instance (ThreadLocal)
- `manager/AppiumServerManager.java`: Quản lý Appium server
- `factory/DriverFactory.java`: Factory để tạo driver
- `factory/DriverOptionsFactory.java`: Factory để tạo driver options

#### `helpers/` - Helper Classes

- `webview/WebViewHelper.java`: Helper cho WebView operations
- `browser/BrowserHelper.java`: Helper cho Browser operations
- `PropertiesHelpers.java`: Đọc/ghi Properties files
- `JsonHelpers.java`: Đọc/ghi JSON files
- `ExcelHelpers.java`: Đọc/ghi Excel files
- `SystemHelpers.java`: System utilities
- `CaptureHelpers.java`: Screenshot và video recording

#### `keywords/` - Keywords cho từng loại app

- `nativeapp/NativeUI.java`: Keywords cho Native App
- `webview/WebViewUI.java`: Keywords cho WebView trong Hybrid App
- `browser/BrowserUI.java`: Keywords cho Mobile Web Browser

#### `reports/` - Reporting

- `AllureManager.java`: Allure report integration

#### `utils/` - Utilities

- `LogUtils.java`: Logging utilities
- `DateUtils.java`: Date utilities

---

### 2. Test Code (`src/test/java/com/company/test/`)

#### `common/` - Base Test Classes

- `native/BaseTestNativeApp.java`: Base test cho Native App
- `hybrid/BaseTestHybridApp.java`: Base test cho Hybrid App
- `web/BaseTestMobileWeb.java`: Base test cho Mobile Web

#### `pages/` - Page Object Model

- `native/BasePageNative.java`: Base page cho Native App
- `hybrid/BasePageHybrid.java`: Base page cho Hybrid App
- `web/BasePageWeb.java`: Base page cho Mobile Web

#### `testcases/` - Test Cases

- Tổ chức theo từng loại app hoặc feature

#### `listeners/` - TestNG Listeners

- `TestListener.java`: TestNG listener
- `AllureListener.java`: Allure listener

---

## 🚀 Cách Sử Dụng

### 1. Test Native App

```java
package com.company.test.testcases.native;

import com.company.test.common.native.BaseTestNativeApp;
import com.company.framework.keywords.native.NativeUI;
import org.testng.annotations.Test;

public class LoginTestNative extends BaseTestNativeApp {

    @Test
    public void testLogin() {
        // Sử dụng NativeUI keywords
        NativeUI.clickElement(By.id("loginButton"));
        NativeUI.setText(By.id("username"), "testuser");
        NativeUI.tap(100, 200);
    }
}
```

### 2. Test Hybrid App

```java
package com.company.test.testcases.hybrid;

import com.company.test.common.hybrid.BaseTestHybridApp;
import com.company.framework.helpers.webview.WebViewHelper;
import com.company.framework.keywords.native.NativeUI;
import com.company.framework.keywords.webview.WebViewUI;
import org.testng.annotations.Test;

public class LoginTestHybrid extends BaseTestHybridApp {

    @Test
    public void testHybridLogin() {
        // Test native part
        NativeUI.clickElement(By.id("nativeButton"));

        // Switch to WebView
        WebViewHelper.switchToWebView(null);

        // Test WebView part
        WebViewUI.clickElementInWebView(By.id("webButton"));
        WebViewUI.setTextInWebView(By.id("webInput"), "test");

        // Switch back to native
        WebViewHelper.switchToNativeContext();
    }
}
```

### 3. Test Mobile Web

```java
package com.company.test.testcases.web;

import com.company.test.common.web.BaseTestMobileWeb;
import com.company.framework.keywords.browser.BrowserUI;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class LoginTestWeb extends BaseTestMobileWeb {

    @Test
    public void testWebLogin() {
        // Navigate to URL
        BrowserUI.navigateToUrl("https://example.com");

        // Interact with web elements
        BrowserUI.clickElement(By.id("loginButton"));
        BrowserUI.setText(By.id("username"), "testuser");

        // Browser navigation
        BrowserUI.goBack();
        BrowserUI.refresh();
    }
}
```

---

## 📋 TestNG XML Configuration

### Native App Test Suite

```xml
<suite name="Native App Suite">
    <test name="Native Tests">
        <parameter name="platformName" value="Android"/>
        <parameter name="platformVersion" value="14"/>
        <parameter name="deviceName" value="Pixel_8"/>
        <parameter name="appPackage" value="com.example.app"/>
        <parameter name="appActivity" value=".MainActivity"/>
        <classes>
            <class name="com.company.test.testcases.native.LoginTestNative"/>
        </classes>
    </test>
</suite>
```

### Hybrid App Test Suite

```xml
<suite name="Hybrid App Suite">
    <test name="Hybrid Tests">
        <parameter name="platformName" value="Android"/>
        <parameter name="platformVersion" value="14"/>
        <parameter name="deviceName" value="Pixel_8"/>
        <parameter name="appPackage" value="com.example.hybrid"/>
        <parameter name="appActivity" value=".MainActivity"/>
        <classes>
            <class name="com.company.test.testcases.hybrid.LoginTestHybrid"/>
        </classes>
    </test>
</suite>
```

### Mobile Web Test Suite

```xml
<suite name="Mobile Web Suite">
    <test name="Web Tests">
        <parameter name="platformName" value="Android"/>
        <parameter name="platformVersion" value="14"/>
        <parameter name="deviceName" value="Pixel_8"/>
        <parameter name="browserType" value="Chrome"/>
        <classes>
            <class name="com.company.test.testcases.web.LoginTestWeb"/>
        </classes>
    </test>
</suite>
```

---

## 🔑 Key Concepts

### 1. AppType Enum

- `NATIVE`: Cho native apps
- `HYBRID`: Cho hybrid apps
- `MOBILE_WEB`: Cho mobile web browsers

### 2. Context Switching (Hybrid App)

- Sử dụng `WebViewHelper.switchToWebView()` để switch sang WebView
- Sử dụng `WebViewHelper.switchToNativeContext()` để switch về Native
- Luôn đảm bảo switch về context ban đầu sau khi test

### 3. Driver Factory Pattern

- `DriverFactory.createDriver()`: Tạo driver dựa trên AppType
- Tự động chọn đúng driver options cho từng loại app

### 4. BaseTest Classes

- Mỗi loại app có BaseTest riêng
- Tự động setup/teardown driver và server

---

## 📚 Tài Liệu Tham Khảo

- [Appium Documentation](http://appium.io/docs/en/about-appium/intro/)
- [Selenium WebDriver](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/documentation-main.html)
