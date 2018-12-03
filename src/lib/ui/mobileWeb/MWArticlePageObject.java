package lib.ui.mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTION_ADD_TO_MY_LIST_BUTTON = "css:#page-actions #ca-watch.mw-ui-icon-mf-watch";
        CLOSE_ARTICLE_BUTTON = ""; //unused
        OPTION_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions #ca-watch.mw-ui-icon-mf-watched";

        OPTION_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
        CHOISE_EXIST_FOLDER_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_container']//*[@text='{FOLDER_NAME}']";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
