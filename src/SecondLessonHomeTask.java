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
import java.util.List;

public class SecondLessonHomeTask {

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

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstHomeTask() {

        WebElement searchFieldElement = waitForElementPresent(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search' field element",
                15
        );

        String textSearchFieldElement = searchFieldElement.getAttribute("text");

        Assert.assertTrue(
                "Search field doesn`t contain 'Search…' text",
                textSearchFieldElement.contains("Wikipedia…")
        );
    }

    @Test
    public void secondHomeTask() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Android",
                "Can`t input 'Android' text to the search field",
                5
        );

        List<WebElement> searchFieldElement = waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Can`t find any 'Android' field element",
                15
        );

        Assert.assertTrue(
                "Search result contains more than 1 article about 'Android'",
                searchFieldElement.size() > 1
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can`t find 'Cancel Search' icon",
                5
        );

        waitForElementsPresent(
                By.id("org.wikipedia:id/search_empty_message"),
                "Can`t cancel Search and I don`t see 'Search and read the free encyclopedia in your language' text",
                5
        );

    }

    @Test
    public void thirdHomeTask() {

        String searchValue = "Android";

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchValue,
                "Can`t input 'Android' text to the search field",
                5
        );

        List<WebElement> searchArticleElementList = waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Can`t find any 'Android' field element",
                15
        );

        Assert.assertTrue(
                "Some of the articles doesn`t contain 'Android' text in their title",
                isAllHeaderContainSearchText(searchArticleElementList, searchValue)
        );
    }

    private boolean isAllHeaderContainSearchText(List<WebElement> searchFieldElement, String searchValue) {

        for (WebElement element : searchFieldElement) {
            if (!element.getAttribute("text").toLowerCase().contains(searchValue.toLowerCase()))
                return false;
        }
        return true;
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

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
