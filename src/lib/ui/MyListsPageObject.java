package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL;

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getFolderXpathByName(String substring) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", substring);
    }

    // TEMPLATES METHODS
    private static String getSavedArticleXpathByTitle(String substring) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", substring);
    }

    public void openFolderByName(String nameOfFolder) {
        this.waitForElementAndClick(
                getFolderXpathByName(nameOfFolder),
                "Can`t find folder be name " + nameOfFolder,
                5
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.swipeElementToLeft(
                getSavedArticleXpathByTitle(articleTitle),
                "Can`t find saved article"
        );
        if (Platform.getInstance().isIOS()) {
            clickElementToTheRightUpperCorner(articleXpath, "Cannot find saved article");
        }
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        this.waitForElementNotPresent(
                getSavedArticleXpathByTitle(articleTitle),
                "Saved article still present with title " + articleTitle,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        this.waitForElementPresent(
                getSavedArticleXpathByTitle(articleTitle),
                "Can`t find saved article by title " + articleTitle,
                15
        );
    }

    public void openArticleByName(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        this.waitForElementAndClick(
                getSavedArticleXpathByTitle(articleTitle),
                "Can`t find article be name " + articleTitle,
                5
        );
    }

}
