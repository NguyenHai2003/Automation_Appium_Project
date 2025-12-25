# 📱 Automation Appium Project

## 📝 Giới Thiệu

Mobile Test Automation Framework với Appium Java - Hỗ trợ đầy đủ cho **Native App**, **Hybrid App** và **Mobile Web**.

Framework được thiết kế với cấu trúc rõ ràng, dễ hiểu cho người mới, với sự phân tách riêng biệt cho từng loại ứng dụng.

## 🚀 Công Nghệ Chính

- Appium 3.x.x
- Selenium 4.x.x
- Maven
- TestNG Framework
- Java >= 17
- appium_flutterfinder_java
- Properties, JSON, Excel
- Allure Report
- Log4j2
- Multi-Threading, Parallel Testing
- Keyword Driven Testing
- Data Driven Testing

---

## 🎯 Hỗ Trợ 3 Loại Ứng Dụng

### ✅ Native App

- Android Native Apps (UiAutomator2)
- iOS Native Apps (XCUITest)
- Flutter Apps

### ✅ Hybrid App

- Apps kết hợp Native và WebView
- Context switching tự động
- Hỗ trợ cả Native và WebView elements

### ✅ Mobile Web

- Chrome Mobile (Android)
- Safari Mobile (iOS)
- Browser navigation và interactions

---

## 🏗️ Cấu Trúc Dự Án

### 1. 📂 Framework Core (`src/main/java/com/company/framework/`)

| Thư mục             | Mục đích                                              |
| :------------------ | :---------------------------------------------------- |
| `enums/`            | Định nghĩa **Platform**, **AppType**, **BrowserType** |
| `drivers/`          | Quản lý Driver với Factory Pattern                    |
| `drivers/manager/`  | Quản lý Appium Server                                 |
| `drivers/factory/`  | Factory để tạo Driver và Options                      |
| `helpers/`          | Helper classes chung                                  |
| `helpers/webview/`  | **WebViewHelper** - Context switching cho Hybrid App  |
| `helpers/browser/`  | **BrowserHelper** - Navigation cho Mobile Web         |
| `keywords/`         | Keywords cho từng loại app                            |
| `keywords/nativeapp/`  | **NativeUI** - Keywords cho Native App                |
| `keywords/webview/` | **WebViewUI** - Keywords cho WebView                  |
| `keywords/browser/` | **BrowserUI** - Keywords cho Mobile Web               |
| `reports/`          | Allure Report integration                             |
| `utils/`            | Utilities (Logging, Date, etc.)                       |

### 2. 🧪 Test Code (`src/test/java/com/company/test/`)

| Thư mục          | Mục đích                                         |
| :--------------- | :----------------------------------------------- |
| `common/native/` | **BaseTestNativeApp** - Base test cho Native App |
| `common/hybrid/` | **BaseTestHybridApp** - Base test cho Hybrid App |
| `common/web/`    | **BaseTestMobileWeb** - Base test cho Mobile Web |
| `pages/native/`  | **BasePageNative** - Base page cho Native App    |
| `pages/hybrid/`  | **BasePageHybrid** - Base page cho Hybrid App    |
| `pages/web/`     | **BasePageWeb** - Base page cho Mobile Web       |
| `testcases/`     | Test cases (tổ chức theo loại app)               |
| `listeners/`     | TestNG Listeners                                 |

### 3. 📁 Resources (`src/test/resources/`)

| Thư mục     | Mục đích                        |
| :---------- | :------------------------------ |
| `configs/`  | Cấu hình môi trường và thiết bị |
| `suites/`   | TestNG XML suites               |
| `testdata/` | Test data (Excel, JSON)         |

---

## 📚 Tài Liệu Chi Tiết

### 📖 Các Tài Liệu Hướng Dẫn

1. **`STRUCTURE_GUIDE.md`** - Hướng dẫn cấu trúc framework:

    - Cấu trúc framework đầy đủ
    - Cách sử dụng từng component
    - Best practices
    - Troubleshooting

2. **`PROJECT_SETUP_GUIDE.md`** - Hướng dẫn áp dụng framework vào dự án mới:
    - ✅ Những gì **KHÔNG CẦN** thay đổi (Core Framework)
    - 🔧 Những gì **CẦN CUSTOMIZE** (Project-specific)
    - Quy trình setup dự án mới
    - Ví dụ cụ thể cho từng loại app

---

## ▶️ Cách Thực Thi

1. Đảm bảo **Appium Server** đang chạy (hoặc để framework tự khởi động)
2. Thiết lập **thiết bị ảo (Emulator/Simulator)** hoặc **thiết bị thực**
3. Cấu hình thông tin trong `src/test/resources/configs/`
4. Chạy test với Maven: `mvn test`

---
