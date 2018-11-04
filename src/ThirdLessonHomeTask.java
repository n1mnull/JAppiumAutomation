import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThirdLessonHomeTask {

    private AppiumDriver driver;
    long defaultWaitTime = 10;

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
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstHomeTask() {

        String searchLine = "Android";
        String folderName = "androidFolder";

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                10
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Can`t input 'Android' text to the search field"
        );

        List<WebElement> searchFieldElement = waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
//                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Can`t find any 'Android' field element"
        );

        ArrayList<String> articlesTitle = getArticleTitles(searchFieldElement);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ articlesTitle.get(0)+"']"),
                "Can`t find 1st article"
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can`t find 'Article option' button"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can`t find 'Add to reading list' option",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can`t find 'Got it' button"
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can`t find 'Input' field"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folderName,
                "Can`t find 'Input' field"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Can`t find 'OK' button"
        );

        driver.navigate().back();

//        waitForElementAndClick(
//                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
//                "Can`t find 'Navigate up' button, can`t close article"
//        );

        // try to add 2nd article

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                10
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Can`t input 'Android' text to the search field"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + articlesTitle.get(1)+"']"),
                "Can`t find 2st article"
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can`t find 'Article option' button"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can`t find 'Add to reading list' option"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + folderName + "']"),
                "Can`t find folder " + folderName + " on saved reading list"
        );

        driver.navigate().back();
//        waitForElementAndClick(
//                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
//                "Can`t find 'Navigate up' button, can`t close article"
//        );

        //open My list tab

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can`t navigate 'My Lists' tab"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/item_title"),
//                By.xpath("//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + folderName + "']"),
                "Can`t navigate 'My Lists' tab"
        );

        swipeElementToLeft(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + articlesTitle.get(1)+"']"),
                "Can`t delete 2nd saved article"
        );

//        waitForElementAndClick(
//                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_action_primary']//*[@text='" + folderName + "']"),
//                "Can`t find '...' of article in the reading list"
//        );
//
//        waitForElementAndClick(
//                By.xpath("//*[@text='Remove from " + folderName +"']"),
//                "Can`t find '...' of article in the reading list"
//        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + articlesTitle.get(0)+"']"),
//                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + articlesTitle.get(0)+"']"),
                "Can`t find 1st saved article"
        );

        WebElement titleArticle = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can`t find title of the article"
        );

        Assert.assertEquals("Titles are different", titleArticle.getAttribute("text"), articlesTitle.get(0));

    }

    @Test
    public void secondHomeTask() {
        String searchLine = "Android";

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                10
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Can`t input '" + searchLine + "' text to the search field"
        );

        List<WebElement> searchFieldElement = waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Can`t find any '" + searchLine + "' field element"
        );

        ArrayList<String> articlesTitle = getArticleTitles(searchFieldElement);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ articlesTitle.get(0)+"']"),
                "Can`t find 1st article"
        );

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "We have not found title of the article"
        );
    }

    @Test
    public void thirdHomeTask() {

    }

    private void assertElementPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements == 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private boolean isAllHeaderContainSearchText(List<WebElement> searchFieldElement, String searchValue) {

        for (WebElement element : searchFieldElement) {
            if (!element.getAttribute("text").contains(searchValue))
                return false;
        }
        return true;
    }

    private ArrayList<String> getArticleTitles(List<WebElement> searchFieldElement) {
        ArrayList<String> result = new ArrayList<>(searchFieldElement.size());
        for (WebElement element : searchFieldElement) {
            result.add(element.getAttribute("text"));
        }
        return result;
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, defaultWaitTime);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
//        System.out.println("Wait element present " + by.toString());
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage) {
        return waitForElementsPresent(by, errorMessage, defaultWaitTime);
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        System.out.println("Wait elements present " + by.toString());
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private WebElement waitForElementAndClick(By by, String errorMessage) {
        return waitForElementAndClick(by, errorMessage, defaultWaitTime);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        System.out.println("Try to click " + by.toString());
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage) {
        return waitForElementAndSendKeys(by, value, errorMessage, defaultWaitTime);
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        System.out.println("SendKeys to " + by.toString());
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errorMessage) {
        return waitForElementAndClear(by, errorMessage, defaultWaitTime);
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        System.out.println("Clear " + by.toString());
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void swipeElementToLeft(By by, String errorMessage) {

        WebElement articleElement = waitForElementPresent(
                by,
                errorMessage,
                10);
        int leftX = articleElement.getLocation().getX();
        int rightX = leftX + articleElement.getSize().getWidth();
        int upperY = articleElement.getLocation().getY();
        int lowerY = upperY + articleElement.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(rightX, middleY)
                .waitAction(150)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }
}
