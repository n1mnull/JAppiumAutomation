package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> waitForElementsPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage) {
        return waitForElementAndClick(locator, errorMessage, 5);
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage) {
        return waitForElementAndSendKeys(locator, value, errorMessage, 5);
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage) {
        return waitForElementNotPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements == 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void swipeUp(int timeOfSwipe) {

        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width/2;
            int startY = (int)(size.height * 0.8);
            int endY = (int)(size.height * 0.2);
            action
                    .press(x, startY)
                    .waitAction(timeOfSwipe)
                    .moveTo(x, endY)
                    .release()
                    .perform();
        } else
            System.out.println("Method swipeUp does nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElements(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        By by = getLocatorByString(locator);
        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Can`t find element by swapping up. \n" + errorMessage, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (!isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++alreadySwiped;
        }

    }

    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = waitForElementPresent(locator, "Cannot find element by locator", 1).getLocation().getY();
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMessage) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator + "/..", errorMessage);
            int rightX = element.getLocation().getX();
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) /2;
            int width = element.getSize().getWidth();

            int pointToClickX = (rightX + width) - 50;
            int pointToClickY = middleY;
            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.tap(pointToClickX, pointToClickY).perform();
        } else
            System.out.println("Method clickElementToTheRightUpperCorner does nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void swipeElementToLeft(String locator, String errorMessage) {
        if (driver instanceof AppiumDriver) {
            WebElement articleElement = waitForElementPresent(
                    locator,
                    errorMessage,
                    10);
            int leftX = articleElement.getLocation().getX();
            int rightX = leftX + articleElement.getSize().getWidth();
            int upperY = articleElement.getLocation().getY();
            int lowerY = upperY + articleElement.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.press(rightX, middleY);
            action.waitAction(300);

            if (Platform.getInstance().isAndroid()) {
                action.moveTo(leftX, middleY);
            } else {
                int offsetX = (-1 * articleElement.getSize().getHeight());
                action.moveTo(offsetX, 0);
            }
            action.release();
            action.perform();
        } else
            System.out.println("Method swipeElementToLeft does nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public int getAmountOfElements(String locator) {
        By by = getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locatorWithType) {

        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"),2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return By.id(locator);
        } else if (byType.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator " + locatorWithType);
        }
    }
}
