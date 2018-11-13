package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_RESULT_WITH_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_RESULT_ELEMENT_BY_TITLE = "//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            EMPTY_RESULT_LABEL = "//*[@text='No results found']",
            SEARCH_EMPTY_MESSAGE = "org.wikipedia:id/search_empty_message";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_WITH_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Can`t find and click search init element");
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Can`t find search input after clicking search init element");
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine,"Can`t find and click search init element");
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath),"Can`t find search result with substring " +substring);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),"Can`t find search cancel button");
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON),"Search cancel button is still present");
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),"Can`t find and click search cancel button");
    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath),"Can`t find and click search result with substring " + substring);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Can`t find anything by the request",
                15
        );
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public List<WebElement> getSearchElements() {
        return this.waitForElementsPresent(
                By.xpath(SEARCH_RESULT_ELEMENT_BY_TITLE),
                "Can`t find any elements",
                15);
    }

    public ArrayList<String> getArticleTitles(List<WebElement> searchFieldElement) {
        ArrayList<String> result = new ArrayList<>(searchFieldElement.size());
        for (WebElement element : searchFieldElement) {
            result.add(element.getAttribute("text"));
        }
        return result;
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(By.xpath(EMPTY_RESULT_LABEL), "Can`t find empty result element", 15);
    }

    public void waitForEmptyMessageDisplay() {
        this.waitForElementPresent(By.id(SEARCH_EMPTY_MESSAGE), "Can`t find empty message", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed no to find any results");
    }
}