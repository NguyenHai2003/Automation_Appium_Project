package com.company.framework.keywords.nativeapp;

import com.company.framework.constants.ConfigData;
import com.company.framework.drivers.DriverManager;
import com.company.framework.reports.AllureManager;
import com.company.framework.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import static com.company.framework.drivers.DriverManager.getDriver;

/**
 * NativeUI - Class chứa các keywords cho Native App testing
 * Sử dụng cho Native App (Android/iOS native apps)
 *
 * Đây là class chính thức để test Native App.
 * Thay thế cho MobileUI (đã deprecated).
 */
public class NativeUI {

    private static final int DEFAULT_TIMEOUT = Integer.parseInt(ConfigData.TIMEOUT_EXPLICIT_DEFAULT);
    private static final double STEP_ACTION_TIMEOUT = Double.parseDouble(ConfigData.STEP_ACTION_TIMEOUT);

    public static void sleep(double second) {
        LogUtils.info("[NativeUI] Sleeping for " + second + " seconds.");
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void swipe(int startX, int startY, int endX, int endY, int durationMillis) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Executing swipe from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") with duration " + durationMillis + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMillis), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(0));
        getDriver().perform(Collections.singletonList(swipe));
    }

    public static void swipeLeft() {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Executing swipeLeft.");
        Dimension size = getDriver().manage().window().getSize();
        int startX = (int) (size.width * 0.8);
        int startY = (int) (size.height * 0.3);
        int endX = (int) (size.width * 0.2);
        int endY = startY;
        int duration = 200;
        swipe(startX, startY, endX, endY, duration);
    }

    public static void swipeRight() {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Executing swipeRight.");
        Dimension size = getDriver().manage().window().getSize();
        int startX = (int) (size.width * 0.2);
        int startY = (int) (size.height * 0.3);
        int endX = (int) (size.width * 0.8);
        int endY = startY;
        int duration = 200;
        swipe(startX, startY, endX, endY, duration);
    }

    private static Point getCenterOfElement(Point location, Dimension size) {
        return new Point(location.getX() + size.getWidth() / 2, location.getY() + size.getHeight() / 2);
    }

    public static void tap(WebElement element) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Executing tap on element: " + element);
        Point location = element.getLocation();
        Dimension size = element.getSize();
        Point centerOfElement = getCenterOfElement(location, size);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(500)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Collections.singletonList(sequence));
    }

    public static void tap(int x, int y) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Executing tap at coordinates (" + x + "," + y + ")");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(200)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Arrays.asList(tap));
    }

    @Step("Click element {0}")
    public static void clickElement(By locator) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Clicking element located by: " + locator);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        waitForElementToBeClickable(locator).click();
    }

    @Step("Set text '{1}' on element {0}")
    public static void setText(By locator, String text) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Setting text '" + text + "' on element: " + locator);
        if (ConfigData.SCREENSHOT_ALL_STEP.equalsIgnoreCase("true")) {
            AllureManager.saveScreenshotPNG();
        }
        WebElement element = waitForElementVisible(locator);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    @Step("Get text from element {0}")
    public static String getElementText(By locator) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[NativeUI] Getting text from element: " + locator);
        WebElement element = waitForElementVisible(locator);
        String text = element.getText();
        AllureManager.saveTextLog("➡️ TEXT: " + text);
        return text;
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        LogUtils.info("[NativeUI] Waiting for element to be clickable: " + locator);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementVisible(By locator) {
        LogUtils.info("[NativeUI] Waiting for element to be visible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static boolean isElementPresentAndDisplayed(By locator) {
        try {
            WebElement element = getDriver().findElement(locator);
            return element != null && element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

