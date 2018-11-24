import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ThirdLessonHomeTask extends CoreTestCase {

    @Test
    public void testFirstHomeTaskEx5() {

        String searchLine = "Android";
        String folderName = "androidFolder";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        List<WebElement> searchFieldElement = searchPageObject.getSearchElements();
        ArrayList<String> articlesTitle = searchPageObject.getArticleTitles(searchFieldElement);
        searchPageObject.clickByArticleWithSubstring(articlesTitle.get(0));

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToNewMyList(folderName);

        this.navigateBackButton();

        // try to add 2nd article
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(articlesTitle.get(1));

        articlePageObject.addArticleToExistMyList(folderName);
        this.navigateBackButton();

        //open My list tab
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyList();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        myListsPageObject.openFolderByName(folderName);
        myListsPageObject.swipeByArticleToDelete(articlesTitle.get(1));
        myListsPageObject.openArticleByName(articlesTitle.get(0));

        assertEquals("Titles are different", articlePageObject.getArticleTitle(), articlesTitle.get(0));
    }

    @Test
    public void testSecondHomeTaskEx6() {
        String searchLine = "Android";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        List<WebElement> searchFieldElement = searchPageObject.getSearchElements();
        ArrayList<String> articlesTitle = searchPageObject.getArticleTitles(searchFieldElement);

        searchPageObject.clickByArticleWithSubstring(articlesTitle.get(0));

        searchPageObject.assertElementPresent(
                "id:org.wikipedia:id/view_page_title_text",
                "We have not found title of the article"
        );
    }

    @Test
    public void testThirdHomeTaskEx7() {

    }
}
