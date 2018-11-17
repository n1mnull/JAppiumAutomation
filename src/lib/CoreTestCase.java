package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities capabilities = getCapabilitiesByPlatformEnv();
        driver = getDriverByPlatformEnv(capabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void navigateBackButton() {
        driver.navigate().back();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(seconds);
    }


    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {

        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app","/Users/mac/Desktop/AQA/apks/org.wikipedia.apk");
            capabilities.setCapability("platformVersion", "8.0");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone SE");
            capabilities.setCapability("app","/Users/mac/Desktop/AQA/apks/Wikipedia.app");
            capabilities.setCapability("platformVersion", "11.3");
        } else {
            throw new Exception("Cannot get platform from env variable. Platform value: " + platform);
        }
        return capabilities;
    }

    private AppiumDriver getDriverByPlatformEnv(DesiredCapabilities capabilities) throws Exception {

        AppiumDriver appiumDriver;
        String platform = System.getenv("PLATFORM");

        if (platform.equals(PLATFORM_ANDROID)) {
            appiumDriver = new AndroidDriver(new URL(APPIUM_URL), capabilities);
        } else if (platform.equals(PLATFORM_IOS)) {
            appiumDriver = new IOSDriver(new URL(APPIUM_URL), capabilities);
        } else {
            throw new Exception("Cannot get platform from env variable. Platform value: " + platform);
        }
        return appiumDriver;
    }
}
