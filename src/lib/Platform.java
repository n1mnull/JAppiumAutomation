package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;

    private Platform() {}

    public static Platform getInstance() {
        if (instance == null)
            instance = new Platform();
        return instance;
    }

    public AppiumDriver getDriver() throws Exception {

        if (isAndroid()) {
            return new AndroidDriver(new URL(APPIUM_URL), getAndroidDesiredCapabilities());
        } else if (isIOS()) {
            return new IOSDriver(new URL(APPIUM_URL), getiOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the Driver. Platform value: " + getPlatformVar());
        }
    }

    public boolean isAndroid()  {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()  {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app","/Users/mac/Desktop/AQA/apks/org.wikipedia.apk");
        capabilities.setCapability("platformVersion", "8.0");
        return capabilities;
    }

    private DesiredCapabilities getiOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("app","/Users/mac/Desktop/AQA/apks/Wikipedia.app");
        capabilities.setCapability("platformVersion", "11.4");
        return capabilities;
    }

    private boolean isPlatform(String myPlatform) {
        String platform = getPlatformVar();
        return myPlatform.equals(platform);
    }

    private String getPlatformVar() {
        return System.getenv("PLATFORM");
    }
}
