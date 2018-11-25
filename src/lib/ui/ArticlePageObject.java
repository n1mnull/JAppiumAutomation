package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            TITLE_TPL,
            FOOTER_ELEMENT,
            OPTION_BUTTON,
            OPTION_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            CHOISE_EXIST_FOLDER_TPL;

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getFolderXpathByName(String substring) {
        return CHOISE_EXIST_FOLDER_TPL.replace("{FOLDER_NAME}", substring);
    }

    private static String getTitleXpathByName(String substring) {
        return TITLE_TPL.replace("{TITLE}", substring);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Can`t find article '" + TITLE + "' title on page", 15);
    }

    public WebElement waitForTitleElement(String article) {
        if (Platform.getInstance().isAndroid())
            return this.waitForElementPresent(TITLE, "Can`t find article '" + TITLE + "' title on page", 15);
        else
            return this.waitForElementPresent(getTitleXpathByName(article), "Can`t find article '" + getTitleXpathByName(article) + "' title on page", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid())
            return titleElement.getAttribute("text");
        else
            return titleElement.getAttribute("name");
    }

    public String getArticleTitle(String article) {
        WebElement titleElement = waitForTitleElement(article);
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else {
            String titleWithDescription = titleElement.getAttribute("name");
            if (titleWithDescription.indexOf("\n") != -1)
                return titleWithDescription.substring(0, titleWithDescription.indexOf("\n"));
            else
                return titleWithDescription;
        }
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid())
            this.swipeUpToFindElements(
                FOOTER_ELEMENT,
                "Can`t find the end of article",
                40
            );
        else
            swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Can`t find the end of article",
                    40
            );
    }

    public void addArticleToNewMyList(String nameOfFolder) {
        this.waitForElementPresent(
                OPTION_BUTTON,
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                OPTION_BUTTON,
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                OPTION_ADD_TO_MY_LIST_BUTTON,
                "Can`t find 'Add to reading list' option",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Can`t find 'Got it' button",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Can`t find 'Input' field",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                nameOfFolder,
                "Can`t find 'Input' field",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Can`t find 'OK' button",
                5
        );
    }

    public void addArticleToExistMyList(String nameOfFolder) {
        this.waitForElementPresent(
                OPTION_BUTTON,
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                OPTION_BUTTON,
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                OPTION_ADD_TO_MY_LIST_BUTTON,
                "Can`t find 'Add to reading list' option",
                5
        );

        this.waitForElementAndClick(
                getFolderXpathByName(nameOfFolder),
                "Can`t find " + nameOfFolder + " folder to save article",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Can`t find 'Navigate up' button, can`t close article",
                10
        );
    }

    public void addArticleToMySaved() {
        waitForElementAndClick(OPTION_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list");
    }

    public void closeModalWindowByClickBack() {
        waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                "Can`t close modal window by click on Back button",
                10
        );
    }
}
