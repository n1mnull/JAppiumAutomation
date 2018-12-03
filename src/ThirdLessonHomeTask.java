import lib.CoreTestCase;
import lib.Platform;
import lib.data.Credential;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

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
        articlePageObject.waitForTitleElement(articlesTitle.get(0));
        String firstArticleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToNewMyList(folderName);
        } else {
            articlePageObject.addArticleToMySaved();
            if (Platform.getInstance().isIOS())
                articlePageObject.closeModalWindowByClickBack();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(Credential.getLogin(), Credential.getPassword());
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement();
            Assert.assertEquals(
                    "We are not on the same page after login",
                    firstArticleTitle,
                    articlePageObject.getArticleTitle()
            );
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        // try to add 2nd article
        searchPageObject.initSearchInput();

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            searchPageObject.typeSearchLine(searchLine);
        }
        searchPageObject.clickByArticleWithSubstring(articlesTitle.get(1));

        articlePageObject.waitForTitleElement(articlesTitle.get(1));
        String secondArticleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToExistMyList(folderName);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        //open My list tab
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyList();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }

        if (Platform.getInstance().isMW()) {
            myListsPageObject.swipeByArticleToDelete(secondArticleTitle);
        } else
            myListsPageObject.swipeByArticleToDelete(articlesTitle.get(1));

        myListsPageObject.openArticleByName(firstArticleTitle);

        assertEquals("Titles are different", articlePageObject.getArticleTitle(articlesTitle.get(0)), firstArticleTitle);
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
