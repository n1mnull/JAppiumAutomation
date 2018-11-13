package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTION_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTION_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
            CHOISE_EXIST_FOLDER_TPL = "//*[@resource-id='org.wikipedia:id/item_container']//*[@text='{FOLDER_NAME}']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getFolderXpathByName(String substring) {
        return CHOISE_EXIST_FOLDER_TPL.replace("{FOLDER_NAME}", substring);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.id(TITLE), "Can`t find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElements(
                By.xpath(FOOTER_ELEMENT),
                "Can`t find the end of article",
                20
        );
    }

    public void addArticleToNewMyList(String nameOfFolder) {
        this.waitForElementPresent(
                By.xpath(OPTION_BUTTON),
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTION_BUTTON),
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTION_ADD_TO_MY_LIST_BUTTON),
                "Can`t find 'Add to reading list' option",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Can`t find 'Got it' button",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Can`t find 'Input' field",
                5
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                nameOfFolder,
                "Can`t find 'Input' field",
                5
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Can`t find 'OK' button",
                5
        );
    }

    public void addArticleToExistMyList(String nameOfFolder) {
        this.waitForElementPresent(
                By.xpath(OPTION_BUTTON),
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTION_BUTTON),
                "Can`t find 'Article option' button",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTION_ADD_TO_MY_LIST_BUTTON),
                "Can`t find 'Add to reading list' option",
                5
        );

        this.waitForElementAndClick(
                By.xpath(getFolderXpathByName(nameOfFolder)),
                "Can`t find " + nameOfFolder + " folder to save article",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Can`t find 'Navigate up' button, can`t close article",
                10
        );
    }
}
