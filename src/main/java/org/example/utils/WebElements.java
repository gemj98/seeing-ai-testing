package org.example.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.example.utils.FingerGestureUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebElements {
    private AndroidDriver driver;
    private WebDriverWait wait;
    private WebDriverWait waitLonger;
    private FingerGestureUtils gestureUtils;

    public WebElements(AndroidDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMillis(1500), Duration.ofMillis(400));
        waitLonger = new WebDriverWait(driver, Duration.ofSeconds(10));
        gestureUtils = new FingerGestureUtils(driver);
    }

    public WebElement agreeButton() {
        return waitUntilFound(AppiumBy.id("com.google.android.apps.nbu.files:id/agree_button"), waitLonger);
    }

    public WebElement internalStorageButton() {
        return waitUntilFound(AppiumBy.androidUIAutomator("new UiSelector().text(\"Internal storage\")"));
    }

    public WebElement picturesFolder() {
        return waitUntilFound(AppiumBy.androidUIAutomator("new UiSelector().text(\"Pictures\")"));
    }

    public WebElement inputImagesFolder() {
        return waitUntilFound(AppiumBy.androidUIAutomator("new UiSelector().text(\"human_emotion_input_images\")"));
    }

    public WebElement testFileName(String imageName) {
        return swipeUpUntilFound(AppiumBy.androidUIAutomator(String.format("new UiSelector().textContains(\"%s\")", imageName)), 60);
    }

    public WebElement shareButton() {
        return waitUntilFound(AppiumBy.accessibilityId("Share"));
    }

    public WebElement seeingAiButton() {
        var start = waitUntilFound(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Bluetooth\")"))
                .getLocation();
        return swipeUpUntilFound(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Seeing AI\")"),
                start,
                99
        );
    }

    public void waitForProcessing() {
        waitUntilFound(AppiumBy.id("com.microsoft.seeingai:id/result_image_view"), waitLonger);
    }

    public String getTestOutput() {
        var outputText = "No person detected";
        var start = waitUntilFound(AppiumBy.id("com.microsoft.seeingai:id/result_cell_text")).getLocation();
        var personFound = swipeUpUntilFound(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Person\")"),
                start,
                12
        );
        if (personFound != null) {
            gestureUtils.swipe(FingerGestureUtils.Direction.UP, personFound, 6);
            var resultElement = waitUntilFound(AppiumBy.id("com.microsoft.seeingai:id/result_cell_text"));
            outputText = resultElement.getText();
        }
        return outputText;
    }

    public void backToSeeingAi() {
        for (int i = 0; i < 3; i++) {
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }
    }

    public void backToSeeingAi2() {
        driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
        waitUntilFound(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.android.launcher3:id/snapshot\").instance(0)")).click();
        for (int i = 0; i < 2; i++) {
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }
    }

    private WebElement waitUntilFound(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }


    private WebElement waitUntilFound(By by, WebDriverWait wait) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement swipeUpUntilFound(By by, int distance) {
        final int swipeLimit = 4;
        int swipeCount = 0;
        while (swipeCount++ < swipeLimit) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(by));
            } catch (TimeoutException e) {
                gestureUtils.swipe(FingerGestureUtils.Direction.UP, distance);
            }
        }
        return null;
    }

    private WebElement swipeUpUntilFound(By by, WebElement startSwipeElement, int distance) {
        final int swipeLimit = 3;
        int swipeCount = 0;
        while (swipeCount++ < swipeLimit) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(by));
            } catch (TimeoutException e) {
                gestureUtils.swipe(FingerGestureUtils.Direction.UP, startSwipeElement, distance);
            }
        }
        return null;
    }

    private WebElement swipeUpUntilFound(By by, Point start, int distance) {
        final int swipeLimit = 4;
        int swipeCount = 0;
        while (swipeCount++ < swipeLimit) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(by));
            } catch (TimeoutException e) {
                gestureUtils.swipe(FingerGestureUtils.Direction.UP, start, distance);
            }
        }
        return null;
    }
}
