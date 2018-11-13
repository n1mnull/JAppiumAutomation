import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SecondLessonHomeTask extends CoreTestCase {

    @Test
    public void testFirstHomeTaskEx2() {

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
    public void testSecondHomeTaskEx3() {

        String searchLine = "Android";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        List<WebElement> searchFieldElements = searchPageObject.getSearchElements();
        assertTrue(
                "Search result contains more than 1 article about 'Android'",
                searchFieldElements.size() > 1
        );
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForEmptyMessageDisplay();
    }

    @Test
    public void testThirdHomeTaskEx4() {

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
}
