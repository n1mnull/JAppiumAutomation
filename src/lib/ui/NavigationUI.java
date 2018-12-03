package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LIST_LINK,
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open Navigation button");
        } else
            System.out.println("Method openNavigation do nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void clickMyList() {
        if (Platform.getInstance().isMW()) {
            tryClickElementWuthFewAttempts(
                    MY_LIST_LINK,
                    "Can`t navigate 'My Lists' tab",
                    5);
            return;
        }
        this.waitForElementAndClick(
                MY_LIST_LINK,
                "Can`t navigate 'My Lists' tab",
                5
        );
    }

}
