package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListTests extends CoreTestCase {

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

        articlePageObject.addArticleToNewMyList(nameOfFolder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyList();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(nameOfFolder);
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }
}
