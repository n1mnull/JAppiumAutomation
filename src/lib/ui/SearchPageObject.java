package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_RESULT_WITH_SUBSTRING_TPL,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_ELEMENT,
            SEARCH_RESULT_ELEMENT_BY_TITLE,
            SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESCRIPTION_TPL,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_EMPTY_MESSAGE;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_WITH_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    // TEMPLATES METHODS
    private static String getResultSearchElementByTitleDescription(String titleArticle, String descriptionArticle) {
        return SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{ARTICLE_TITLE}", titleArticle).replace("{ARTICLE_DESCRIPTION}", descriptionArticle);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Can`t find and click search init element " + SEARCH_INIT_ELEMENT);
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Can`t find search input after clicking search init element");
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                searchLine,"Can`t find and click search init element");
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                searchResultXpath,
                "Can`t find search result with substring " +substring);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Can`t find search cancel button");
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present");
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Can`t find and click search cancel button");
    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                searchResultXpath,
                "Can`t find and click search result with substring " + substring);
    }

    public void waitForElementByTitleAndDescription(String titleArticle, String descriptionArticle) {
        String searchResultXpath = getResultSearchElementByTitleDescription(titleArticle, descriptionArticle);
        this.waitForElementPresent(
                searchResultXpath,
                "Can`t find and click search result with titleArticle " + titleArticle + "descriptionArticle " + descriptionArticle,
                15
        );
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Can`t find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public List<WebElement> getSearchElements() {
        return this.waitForElementsPresent(
                getResultSearchElement(""),
                "Can`t find any elements",
                15);
    }

    public ArrayList<String> getArticleTitles(List<WebElement> searchFieldElement) {
        ArrayList<String> result = new ArrayList<>(searchFieldElement.size());
        for (WebElement element : searchFieldElement) {
            if (Platform.getInstance().isAndroid()) {
                result.add(element.getAttribute("text"));
            } else if (Platform.getInstance().isIOS()) {
                String titleWithDescription = element.getAttribute("name");
                if (titleWithDescription.indexOf("\n") != -1)
                    result.add(titleWithDescription.substring(0, titleWithDescription.indexOf("\n")));
                else
                    result.add(titleWithDescription);
            } else {
                result.add(element.getText());
            }
        }
        return result;
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Can`t find empty result element", 15);
    }

    public void waitForEmptyMessageDisplay() {
        this.waitForElementPresent(SEARCH_EMPTY_MESSAGE, "Can`t find empty message", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed no to find any results");
    }
}
