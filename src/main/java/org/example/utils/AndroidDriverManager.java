package org.example.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDriverManager {
    private final String APPIUM_URL = "http://localhost:4723";
    private final String PLATFORM_NAME = "Android";
    private final String PLATFORM_VERSION = "10";
    private final String DEVICE_NAME = "moto-one";
    private final String AUTOMATION_NAME = "UiAutomator2";
    private final String APP_PACKAGE = "com.google.android.apps.nbu.files";
    private final String APP_ACTIVITY = "com.google.android.apps.nbu.files.home.HomeActivity";


    public AndroidDriver getDriver () {
        try {
            return new AndroidDriver (new URL(APPIUM_URL), getCapabilities());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private DesiredCapabilities getCapabilities() {
        var caps = new DesiredCapabilities();
        caps.setCapability("platformName", PLATFORM_NAME);
        caps.setCapability("appium:platformVersion", PLATFORM_VERSION);
        caps.setCapability("appium:deviceName", DEVICE_NAME);
        caps.setCapability("appium:automationName", AUTOMATION_NAME);
        caps.setCapability("appium:appPackage", APP_PACKAGE);
        caps.setCapability("appium:appActivity", APP_ACTIVITY);
        return caps;
    }
}
