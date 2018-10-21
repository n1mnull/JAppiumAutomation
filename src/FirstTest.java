import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/home/i5-test/Desktop/autotestProject/JAppiumAutomation/apks/org.wikipedia.apk");
//        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can`t find search input field",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can`t find 'Object-oriented programming language' topic searching by Java",
                15
        );

        System.out.println("First test run");

//        WebElement searchFieldWikipedia = driver.findElementByXPath("//*[contains(@text,'Search Wikipedia')]");
//        searchFieldWikipedia.click();

//        WebElement searchLine = waitForElementPresentByXpath("//*[contains(@text,'Search…')]", "Can`t find search input field");
//                driver.findElementByXPath("//*[contains(@text,'Search…')]");
//        searchLine.sendKeys("Java");
    }

    @Test
    public void cancelSearchTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can`t find 'Search Wikipedia' input field",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can`t find search input field",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
//                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can`t find 'Cancel Search' input field",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );

    }

    @Test
    public void compareArticleTitleTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can`t find search input field",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can`t find 'Object-oriented programming language' topic searching by Java",
                15
        );

        WebElement titleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can`t find Java article title",
                15
        );

        String articleTitle = titleElement.getAttribute("text");
        Assert.assertEquals(
                "We don`t see article Title",
                "Java (programming language)",
                articleTitle
        );

    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

//    private WebElement waitForElementPresentById(By by, String errorMessage, long timeoutInSeconds) {
//        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
//        wait.withMessage(errorMessage + "\n");
//        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
//    }

//    private WebElement waitForElementByIdAndClick(String id, String errorMessage, long timeoutInSeconds) {
//        WebElement element = waitForElementPresentById(id, errorMessage, timeoutInSeconds);
//        element.click();
//        return element;
//    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

}
