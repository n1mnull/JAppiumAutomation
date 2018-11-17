package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    private static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

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
        this.swipeElementToLeft(
                getSavedArticleXpathByTitle(articleTitle),
                "Can`t find saved article"
        );
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        this.waitForElementNotPresent(
                getFolderXpathByName(articleTitle),
                "Saved article still present with title " + articleTitle,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        this.waitForElementPresent(
                getFolderXpathByName(articleTitle),
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
