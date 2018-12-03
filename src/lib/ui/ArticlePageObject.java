package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            TITLE_TPL,
            FOOTER_ELEMENT,
            OPTION_BUTTON,
            OPTION_ADD_TO_MY_LIST_BUTTON,
            OPTION_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            CHOISE_EXIST_FOLDER_TPL;

    public ArticlePageObject(RemoteWebDriver driver) {
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
        if (Platform.getInstance().isAndroid()) {
            return this.waitForElementPresent(TITLE, "Can`t find article '" + TITLE + "' title on page", 15);
        } else if (Platform.getInstance().isIOS()) {
            return this.waitForElementPresent(getTitleXpathByName(article), "Can`t find article '" + getTitleXpathByName(article) + "' title on page", 15);
        } else {
            return waitForTitleElement();
        }
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid())
            return titleElement.getAttribute("text");
        else if (Platform.getInstance().isIOS())
            return titleElement.getAttribute("name");
        else
            return titleElement.getText();
    }

    public String getArticleTitle(String article) {
        if (Platform.getInstance().isMW()) {
            return getArticleTitle();
        }
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
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElements(
                    FOOTER_ELEMENT,
                    "Can`t find the end of article",
                    40
            );
        } else if (Platform.getInstance().isIOS()) {
            swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Can`t find the end of article",
                    40
            );
        } else {
            scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Can`t find the end of article",
                    40
            );
        }
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
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Can`t find 'Navigate up' button, can`t close article",
                    10
            );
        } else
            System.out.println("Method closeArticle do nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void addArticleToMySaved() {
        if (Platform.getInstance().isMW()) {
            removeArticleFromMySavedIfItAdded();
        }
        waitForElementAndClick(OPTION_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list");
    }

    public void removeArticleFromMySavedIfItAdded() {
        if (isElementPresent(OPTION_REMOVE_FROM_MY_LIST_BUTTON)) {
            waitForElementAndClick(
                    OPTION_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    1
            );
            waitForElementPresent(
                    OPTION_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add article to saved list after removing it from this list before"
            );
        }
    }

    public void closeModalWindowByClickBack() {
        waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                "Can`t close modal window by click on Back button",
                10
        );
    }
}
