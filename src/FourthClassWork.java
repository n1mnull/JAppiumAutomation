import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class FourthClassWork extends CoreTestCase {

    @Test
    public void testSearch() {

        String searchLine = "Java";
        String searchResult = "Object-oriented programming language";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult(searchResult);
    }

    @Test
    public void testCancelSearch() {

        String searchLine = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
//        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCompareArticleTitle() {
        String searchLine = "Java";
        String searchResult = "Object-oriented programming language";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(searchResult);

        String articleTitle = articlePageObject.getArticleTitle();

        assertEquals(
                "We don`t see article Title",
                "Java (programming language)",
                articleTitle
        );
    }

    @Test
    public void testSwipeArticle() {
        String searchLine = "Appium";
        String searchResult = "Appium";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(searchResult);

        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList() {

        String searchLine = "Java";
        String searchResult = "Object-oriented programming language";
        String nameOfFolder = "Learning programing";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(searchResult);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        articlePageObject.addArticleToMyList(nameOfFolder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyList();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(nameOfFolder);
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testAmountOfNotEmptySearch() {

        String searchLine = "Linkin Park Discography";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
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
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testChangeScreenOrientationOnSearchResult() {

        String searchLine = "Java";
        String searchResult = "Object-oriented programming language";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(searchResult);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String titleBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();
        assertEquals(
                "Article title have been changed after screen orientation",
                titleBeforeRotation,
                titleAfterRotation
        );

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();
        assertEquals(
                "Article title have been changed after screen orientation",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {

        String searchLine = "Java";
        String searchResult = "Object-oriented programming language";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult(searchResult);
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult(searchResult);
    }
}
