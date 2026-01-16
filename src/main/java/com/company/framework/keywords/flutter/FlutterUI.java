package com.company.framework.keywords.flutter;

import com.company.framework.constants.ConfigData;
import com.company.framework.drivers.DriverManager;
import com.company.framework.reports.AllureManager;
import com.company.framework.utils.LogUtils;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

import static com.company.framework.drivers.DriverManager.getDriver;

/**
 * FlutterUI - Class chứa các keywords cho Flutter App testing
 * Sử dụng FlutterFinder để tìm elements trong Flutter apps
 */
public class FlutterUI {

    private static final double STEP_ACTION_TIMEOUT = Double.parseDouble(ConfigData.STEP_ACTION_TIMEOUT);
    private static FlutterFinder flutterFinder;

    /**
     * Khởi tạo FlutterFinder với driver hiện tại
     */
    public static void initFlutterFinder() {
        if (flutterFinder == null) {
            flutterFinder = new FlutterFinder(getDriver());
            LogUtils.info("[FlutterUI] FlutterFinder initialized");
        }
    }

    /**
     * Lấy FlutterFinder instance
     */
    public static FlutterFinder getFlutterFinder() {
        initFlutterFinder();
        return flutterFinder;
    }

    public static void sleep(double second) {
        LogUtils.info("[FlutterUI] Sleeping for " + second + " seconds.");
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tìm Flutter element bằng value key
     */
    public static FlutterElement findElementByValueKey(String valueKey) {
        initFlutterFinder();
        LogUtils.info("[FlutterUI] Finding element by valueKey: " + valueKey);
        return flutterFinder.byValueKey(valueKey);
    }

    /**
     * Tìm Flutter element bằng text
     */
    public static FlutterElement findElementByText(String text) {
        initFlutterFinder();
        LogUtils.info("[FlutterUI] Finding element by text: " + text);
        return flutterFinder.byText(text);
    }

    /**
     * Tìm Flutter element bằng type
     */
    public static FlutterElement findElementByType(String type) {
        initFlutterFinder();
        LogUtils.info("[FlutterUI] Finding element by type: " + type);
        return flutterFinder.byType(type);
    }

    /**
     * Tìm Flutter element bằng semantics label
     */
    public static FlutterElement findElementBySemanticsLabel(String label) {
        initFlutterFinder();
        LogUtils.info("[FlutterUI] Finding element by semanticsLabel: " + label);
        return flutterFinder.bySemanticsLabel(label);
    }

    @Step("Click Flutter element by valueKey: {0}")
    public static void clickElementByValueKey(String valueKey) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Clicking element by valueKey: " + valueKey);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        FlutterElement element = findElementByValueKey(valueKey);
        element.click();
    }

    @Step("Click Flutter element by text: {0}")
    public static void clickElementByText(String text) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Clicking element by text: " + text);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        FlutterElement element = findElementByText(text);
        element.click();
    }

    @Step("Set text '{1}' on Flutter element by valueKey: {0}")
    public static void setTextByValueKey(String valueKey, String text) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Setting text '" + text + "' on element by valueKey: " + valueKey);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        FlutterElement element = findElementByValueKey(valueKey);
        element.sendKeys(text);
    }

    @Step("Get text from Flutter element by valueKey: {0}")
    public static String getTextByValueKey(String valueKey) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Getting text from element by valueKey: " + valueKey);
        FlutterElement element = findElementByValueKey(valueKey);
        String text = element.getText();
        AllureManager.saveTextLog("➡️ TEXT: " + text);
        return text;
    }

    @Step("Get text from Flutter element by text: {0}")
    public static String getTextByText(String text) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Getting text from element by text: " + text);
        FlutterElement element = findElementByText(text);
        String result = element.getText();
        AllureManager.saveTextLog("➡️ TEXT: " + result);
        return result;
    }

    /**
     * Kiểm tra element có tồn tại không
     */
    public static boolean isElementPresentByValueKey(String valueKey) {
        try {
            FlutterElement element = findElementByValueKey(valueKey);
            return element != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Kiểm tra element có tồn tại không (by text)
     */
    public static boolean isElementPresentByText(String text) {
        try {
            FlutterElement element = findElementByText(text);
            return element != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Tap vào Flutter element
     */
    public static void tap(FlutterElement element) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Executing tap on Flutter element");
        Point location = element.getLocation();
        Dimension size = element.getSize();
        Point centerOfElement = new Point(
                location.getX() + size.getWidth() / 2,
                location.getY() + size.getHeight() / 2
        );
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(500)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Collections.singletonList(sequence));
    }

    /**
     * Tap vào tọa độ
     */
    public static void tap(int x, int y) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Executing tap at coordinates (" + x + "," + y + ")");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(200)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Collections.singletonList(tap));
    }

    /**
     * Swipe trong Flutter app
     */
    public static void swipe(int startX, int startY, int endX, int endY, int durationMillis) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Executing swipe from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") with duration " + durationMillis + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMillis), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(0));
        getDriver().perform(Collections.singletonList(swipe));
    }

    /**
     * Swipe left
     */
    public static void swipeLeft() {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Executing swipeLeft.");
        Dimension size = getDriver().manage().window().getSize();
        int startX = (int) (size.width * 0.8);
        int startY = (int) (size.height * 0.3);
        int endX = (int) (size.width * 0.2);
        int endY = startY;
        int duration = 200;
        swipe(startX, startY, endX, endY, duration);
    }

    /**
     * Swipe right
     */
    public static void swipeRight() {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Executing swipeRight.");
        Dimension size = getDriver().manage().window().getSize();
        int startX = (int) (size.width * 0.2);
        int startY = (int) (size.height * 0.3);
        int endX = (int) (size.width * 0.8);
        int endY = startY;
        int duration = 200;
        swipe(startX, startY, endX, endY, duration);
    }

    /**
     * Scroll trong Flutter app
     */
    public static void scroll(FlutterElement element, int deltaX, int deltaY) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[FlutterUI] Scrolling element with delta (" + deltaX + "," + deltaY + ")");
        Point location = element.getLocation();
        Dimension size = element.getSize();
        Point center = new Point(
                location.getX() + size.getWidth() / 2,
                location.getY() + size.getHeight() / 2
        );
        swipe(center.getX(), center.getY(), center.getX() + deltaX, center.getY() + deltaY, 300);
    }
}

