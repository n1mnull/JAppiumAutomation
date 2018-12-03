package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_WITH_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No result found']";
        SEARCH_RESULT_ELEMENT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{ARTICLE_TITLE}')][contains(@name, '{ARTICLE_DESCRIPTION}')]";

        SEARCH_RESULT_ELEMENT_BY_TITLE = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_MESSAGE = "id:org.wikipedia:id/search_empty_message";
    }

    public iOSSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
