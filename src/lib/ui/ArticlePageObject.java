package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTION_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTION_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            CHOISE_EXIST_FOLDER_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_container']//*[@text='{FOLDER_NAME}']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getFolderXpathByName(String substring) {
        return CHOISE_EXIST_FOLDER_TPL.replace("{FOLDER_NAME}", substring);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Can`t find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElements(
                FOOTER_ELEMENT,
                "Can`t find the end of article",
                20
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
}
