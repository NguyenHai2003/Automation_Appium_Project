# 📱 Automation Appium Project

## 📝 Giới Thiệu

Mobile Test Automation with Appium Java - support multiple platform

## 🚀 Công Nghệ Chính
- Appium 3.x.x
- Selenium 4.x.x
- Maven
- TestNG Framework
- Java >= 17
- appium_flutterfinder_java
- Properties, JSON, Excel
- Extent Report, Allure Report
- Log4j, Slf4j
- Multi-Threading, Parallel Testing
- Keyword Driven Testing
- Data Driven Testing

---

## 🏗️ Cấu Trúc Dự Án


### 1. 📂 `src/main/java/com.company.framework` (Logic Nền tảng)

| Thư mục | Mục đích |
| :--- | :--- |
| `constants` | Định nghĩa các **Hằng số** chung (Thời gian chờ, thông tin mặc định). |
| `drivers` | Quản lý **Appium Driver** (khởi tạo, đóng, quản lý phiên). |
| `helpers` | Chứa các hàm hỗ trợ chung cho các tác vụ không phải Appium (ví dụ: thao tác chuỗi). |
| `keywords` | Định nghĩa các **Từ khóa hành động mức cao** được sử dụng lại bởi các Page Objects. |
| `reports` | Các lớp hỗ trợ tích hợp báo cáo Allure. |
| `utils` | Các lớp tiện ích. |

### 2. 🧪 `src/test/java/com.company.test` (Logic Kiểm thử)

| Thư mục | Mục đích |
| :--- | :--- |
| `common` | **Base Test Class** cho việc thiết lập và dọn dẹp môi trường TestNG. |
| `listeners` | Triển khai **TestNG Listeners** (ví dụ: `TestListener`) để xử lý các sự kiện kiểm thử (thành công, thất bại). |
| `pages` | Triển khai **Page Object Model (POM)**. Mỗi lớp tương ứng với một màn hình/trang trong ứng dụng, chứa các **Element Locators** và **Phương thức hành động** trên trang đó. |
| `testcases` | Chứa các **Test Cases** thực tế sử dụng các Page Objects để tạo thành kịch bản kiểm thử. |

### 3. 📁 `src/test/resources` (Tài nguyên Kiểm thử)

| Thư mục | Mục đích |
| :--- | :--- |
| `configs` | Tệp cấu hình **môi trường** và **thiết bị** . |
| `suites` | Các tệp **TestNG XML** để nhóm và chạy các bộ kiểm thử khác nhau. |
| `testdata` | Chứa **Dữ liệu kiểm thử** bên ngoài (Excel, JSON). |

---

## ▶️ Cách Thực Thi

Khung này sử dụng **Maven** để quản lý việc xây dựng và thực thi kiểm thử.

* Đảm bảo **Appium Server** đang chạy.
* Thiết lập **thiết bị ảo (Emulator/Simulator)** hoặc **thiết bị thực** và đảm bảo có thể kết nối.
* Cấu hình thông tin kết nối và thiết bị trong tệp **`src/test/resources/configs`**.
