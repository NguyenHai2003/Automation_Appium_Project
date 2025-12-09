# 🚀 HƯỚNG DẪN ÁP DỤNG FRAMEWORK VÀO DỰ ÁN MỚI

## 📋 Tổng Quan

Framework hiện tại là **Base Framework** có thể tái sử dụng cho nhiều dự án khác nhau. Khi tham gia vào một dự án cụ thể, bạn **KHÔNG CẦN** thay đổi core framework, chỉ cần **customize** các phần liên quan đến dự án.

---

## ✅ KHÔNG CẦN THAY ĐỔI (Core Framework)

### 1. Framework Core Classes
```
src/main/java/com/company/framework/
├── enums/         
├── drivers/          
├── helpers/          
├── keywords/           
├── reports/            
└── utils/              
```

**Lý do**: Đây là core framework, được thiết kế để tái sử dụng cho mọi dự án.

### 2. Base Test Classes
```
src/test/java/com/company/test/common/
├── native/BaseTestNativeApp.java    
├── hybrid/BaseTestHybridApp.java    
└── web/BaseTestMobileWeb.java      
```

**Lý do**: Base classes đã được thiết kế generic, có thể dùng cho mọi dự án.

### 3. Base Page Classes
```
src/test/java/com/company/test/pages/
├── native/BasePageNative.java
├── hybrid/BasePageHybrid.java 
└── web/BasePageWeb.java         
```

**Lý do**: Base pages cung cấp các methods chung, có thể extend cho mọi dự án.

---

## 🔧 CUSTOMIZE (Project-Specific)

### 1. Configuration Files ⚙️

#### `src/test/resources/configs/config.properties`
```properties
# Cần thay đổi theo dự án
APPIUM_DRIVER_LOCAL_SERVICE = true
TIMEOUT_EXPLICIT_DEFAULT = 10
STEP_ACTION_TIMEOUT = 1
SCREENSHOT_PATH = exports/screenshots/
RECORD_VIDEO_PATH = exports/videos/
# ... các config khác
```

#### `src/test/resources/configs/device.json`
```json
{
  "platforms": {
    "android": {
      "devices": {
        "your_device": {
          "platformName": "Android",
          "platformVersion": "14",
          "deviceName": "Your_Device_Name",
          "appPackage": "com.yourproject.app",
          "appActivity": "com.yourproject.MainActivity",
          "appAndroidPath": "src/test/resources/apps/your-app.apk"
        }
      }
    },
    "ios": {
      "devices": {
        "your_ios_device": {
          "platformName": "iOS",
          "platformVersion": "17",
          "deviceName": "iPhone 14",
          "bundleId": "com.yourproject.app"
        }
      }
    }
  }
}
```
---

### 2. Test Data 📊

#### `src/test/resources/testdata/data.json`
```json
{
  "login": {
    "validUser": {
      "username": "your_test_user",
      "password": "your_test_password"
    },
    "invalidUser": {
      "username": "invalid_user",
      "password": "wrong_password"
    }
  },
  "testData": {
    "productName": "Your Product",
    "price": "100000"
  }
}
```

#### `src/test/resources/testdata/data.xlsx`
- Tạo sheets và data theo dự án
- Cấu trúc columns theo test cases

---

### 3. Page Objects 📄

#### Tạo Pages mới cho dự án

**Ví dụ: LoginPage cho Native App**
```java
package com.company.test.pages.native;

import com.company.test.pages.native.BasePageNative;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPageNative extends BasePageNative {
    
    // Locators cho dự án cụ thể
    @AndroidFindBy(id = "com.yourproject:id/username")
    @iOSXCUITFindBy(accessibility = "Username Field")
    private WebElement usernameField;
    
    @AndroidFindBy(id = "com.yourproject:id/password")
    @iOSXCUITFindBy(accessibility = "Password Field")
    private WebElement passwordField;
    
    @AndroidFindBy(id = "com.yourproject:id/loginButton")
    @iOSXCUITFindBy(accessibility = "Login Button")
    private WebElement loginButton;
    
    // Methods cho dự án cụ thể
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }
    
    public void clickLogin() {
        loginButton.click();
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
```

**Ví dụ: LoginPage cho Hybrid App**
```java
package com.company.test.pages.hybrid;

import com.company.test.pages.hybrid.BasePageHybrid;
import com.company.framework.helpers.webview.WebViewHelper;
import com.company.framework.keywords.webview.WebViewUI;
import org.openqa.selenium.By;

public class LoginPageHybrid extends BasePageHybrid {
    
    // Native elements
    @AndroidFindBy(id = "nativeLoginButton")
    private WebElement nativeLoginButton;
    
    public void clickNativeLogin() {
        nativeLoginButton.click();
    }
    
    // WebView interactions
    public void loginInWebView(String username, String password) {
        // Switch to WebView
        switchToWebView(null);
        
        // Interact with WebView
        WebViewUI.setTextInWebView(By.id("webUsername"), username);
        WebViewUI.setTextInWebView(By.id("webPassword"), password);
        WebViewUI.clickElementInWebView(By.id("webLoginButton"));
        
        // Switch back to native
        switchToNativeContext();
    }
}
```

**Ví dụ: LoginPage cho Mobile Web**
```java
package com.company.test.pages.web;

import com.company.test.pages.web.BasePageWeb;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageWeb extends BasePageWeb {
    
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "loginButton")
    private WebElement loginButton;
    
    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
```

---

### 4. Test Cases 🧪

#### Tạo Test Cases mới cho dự án

**Ví dụ: LoginTest cho Native App**
```java
package com.company.test.testcases.native;

import com.company.test.common.native.BaseTestNativeApp;
import com.company.test.pages.native.LoginPageNative;
import com.company.framework.helpers.JsonHelpers;
import org.testng.annotations.Test;

public class LoginTestNative extends BaseTestNativeApp {
    
    @Test
    public void testValidLogin() {
        LoginPageNative loginPage = new LoginPageNative();
        
        String username = JsonHelpers.getValueJsonObject("login", "validUser", "username");
        String password = JsonHelpers.getValueJsonObject("login", "validUser", "password");
        
        loginPage.login(username, password);
        
        // Assertions...
    }
}
```

**Ví dụ: LoginTest cho Hybrid App**
```java
package com.company.test.testcases.hybrid;

import com.company.test.common.hybrid.BaseTestHybridApp;
import com.company.test.pages.hybrid.LoginPageHybrid;
import org.testng.annotations.Test;

public class LoginTestHybrid extends BaseTestHybridApp {
    
    @Test
    public void testHybridLogin() {
        LoginPageHybrid loginPage = new LoginPageHybrid();
        
        // Test native part
        loginPage.clickNativeLogin();
        
        // Test WebView part
        loginPage.loginInWebView("username", "password");
    }
}
```

**Ví dụ: LoginTest cho Mobile Web**
```java
package com.company.test.testcases.web;

import com.company.test.common.web.BaseTestMobileWeb;
import com.company.test.pages.web.LoginPageWeb;
import com.company.framework.keywords.browser.BrowserUI;
import org.testng.annotations.Test;

public class LoginTestWeb extends BaseTestMobileWeb {
    
    @Test
    public void testWebLogin() {
        // Navigate to login page
        BrowserUI.navigateToUrl("https://yourproject.com/login");
        
        LoginPageWeb loginPage = new LoginPageWeb();
        loginPage.login("username", "password");
        
        // Assertions...
    }
}
```

---

### 5. TestNG XML Suites 📋

#### Tạo TestNG XML cho dự án

**Ví dụ: `src/test/resources/suites/YourProject_Native.xml`**
```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Your Project - Native App Suite" verbose="1">
    <test name="Native Tests">
        <parameter name="platformName" value="Android"/>
        <parameter name="platformVersion" value="14"/>
        <parameter name="deviceName" value="Your_Device"/>
        <parameter name="appPackage" value="com.yourproject.app"/>
        <parameter name="appActivity" value="com.yourproject.MainActivity"/>
        <parameter name="noReset" value="false"/>
        <parameter name="fullReset" value="false"/>
        <parameter name="autoGrantPermissions" value="true"/>
        <parameter name="host" value="127.0.0.1"/>
        <parameter name="port" value="4723"/>
        
        <classes>
            <class name="com.company.test.testcases.native.LoginTestNative"/>
            <class name="com.company.test.testcases.native.OtherTestNative"/>
        </classes>
    </test>
</suite>
```

**Ví dụ: `src/test/resources/suites/YourProject_Web.xml`**
```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Your Project - Mobile Web Suite" verbose="1">
    <test name="Web Tests">
        <parameter name="platformName" value="Android"/>
        <parameter name="platformVersion" value="14"/>
        <parameter name="deviceName" value="Your_Device"/>
        <parameter name="browserType" value="Chrome"/>
        <parameter name="host" value="127.0.0.1"/>
        <parameter name="port" value="4723"/>
        
        <classes>
            <class name="com.company.test.testcases.web.LoginTestWeb"/>
        </classes>
    </test>
</suite>
```

---

## 📁 Cấu Trúc Thư Mục Cho Dự Án Mới

```
YourProject/
├── src/main/java/com/company/framework/    ✅ GIỮ NGUYÊN (Core Framework)
│
├── src/test/
│   ├── java/com/company/test/
│   │   ├── common/                         ✅ GIỮ NGUYÊN (Base Tests)
│   │   ├── pages/
│   │   │   ├── native/                     🔧 TẠO MỚI (Project Pages)
│   │   │   │   ├── BasePageNative.java     ✅ GIỮ NGUYÊN
│   │   │   │   ├── LoginPageNative.java    🔧 TẠO MỚI
│   │   │   │   └── HomePageNative.java     🔧 TẠO MỚI
│   │   │   ├── hybrid/                     🔧 TẠO MỚI
│   │   │   └── web/                        🔧 TẠO MỚI
│   │   └── testcases/
│   │       ├── native/                     🔧 TẠO MỚI
│   │       ├── hybrid/                     🔧 TẠO MỚI
│   │       └── web/                        🔧 TẠO MỚI
│   │
│   └── resources/
│       ├── configs/
│       │   ├── config.properties           🔧 CUSTOMIZE
│       │   └── device.json                  🔧 CUSTOMIZE
│       ├── suites/
│       │   └── YourProject_*.xml           🔧 TẠO MỚI
│       ├── testdata/
│       │   ├── data.json                    🔧 CUSTOMIZE
│       │   └── data.xlsx                    🔧 CUSTOMIZE
│       └── apps/
│           └── your-app.apk                 🔧 THÊM APP
```

---

## 🎯 Quy Trình Áp Dụng Framework Vào Dự Án Mới

### Bước 1: Copy Framework Base
```bash
# Copy toàn bộ framework vào project mới
# Hoặc clone từ repository
```

### Bước 2: Cấu Hình Dự Án
1. ✅ Update `config.properties` với config của dự án
2. ✅ Update `device.json` với devices và app info
3. ✅ Thêm app file (.apk/.ipa) vào `src/test/resources/apps/`

### Bước 3: Tạo Test Data
1. ✅ Tạo `data.json` với test data của dự án
2. ✅ Tạo `data.xlsx` với test data (nếu cần)

### Bước 4: Tạo Page Objects
1. ✅ Tạo các Page classes extend từ BasePage
2. ✅ Define locators cho từng page
3. ✅ Implement methods cho từng page

### Bước 5: Tạo Test Cases
1. ✅ Tạo test classes extend từ BaseTest
2. ✅ Implement test methods
3. ✅ Sử dụng Page Objects và Keywords

### Bước 6: Tạo TestNG Suites
1. ✅ Tạo XML files cho từng suite
2. ✅ Configure parameters
3. ✅ Add test classes

### Bước 7: Chạy Test
```bash
mvn test -DsuiteXmlFile=src/test/resources/suites/YourProject_Native.xml
```

---

## 📝 Tóm Tắt

| Component | Action | Lý Do |
|-----------|--------|-------|
| Core Framework | ✅ **GIỮ NGUYÊN** | Tái sử dụng cho mọi dự án |
| Base Tests | ✅ **GIỮ NGUYÊN** | Generic, dùng được cho mọi dự án |
| Base Pages | ✅ **GIỮ NGUYÊN** | Base classes, extend cho dự án |
| Config Files | 🔧 **CUSTOMIZE** | Dự án cụ thể |
| Test Data | 🔧 **CUSTOMIZE** | Dữ liệu test của dự án |
| Page Objects | 🔧 **TẠO MỚI** | Pages của dự án |
| Test Cases | 🔧 **TẠO MỚI** | Test cases của dự án |
| TestNG Suites | 🔧 **TẠO MỚI** | Cấu hình test cho dự án |
