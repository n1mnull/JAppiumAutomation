package tests;

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

public class MyListTests extends CoreTestCase {

    private static final String NAME_OF_FOLDER = "Learning programing";

    @Test
    public void testSaveFirstArticleToMyList() {

        String searchLine = "Java";
        String searchResult = "bject-oriented programming language";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(searchResult);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToNewMyList(NAME_OF_FOLDER);
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
                    "We are not on the sae page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle()
            );
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyList();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(NAME_OF_FOLDER);
        }
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }
}
