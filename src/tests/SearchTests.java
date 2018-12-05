package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        String searchLine = "Java";
        String searchResult = "bject-oriented programming language";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult(searchResult);
    }

    @Test
    public void testCancelSearch() {

        String searchLine = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
//        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {

        String searchLine = "Linkin Park Discography";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResult = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found to few result" + amountOfSearchResult,
                amountOfSearchResult > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {

        String searchLine = "zxcvasdfqwer";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchByTitleAndDescriptionHomeTaskEx9() {
        String searchLine = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.waitForElementByTitleAndDescription("Java version history", "ikimedia list article");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "bject-oriented programming language");
        searchPageObject.waitForElementByTitleAndDescription("Java (software platform)", "et of several computer software products and specifications");

    }
}
