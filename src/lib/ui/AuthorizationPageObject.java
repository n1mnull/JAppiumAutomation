package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{

    protected static String
            LOGIN_BUTTON = "xpath://body/div/a[text()='Log in']",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton() {
        waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 15);
        waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button");
    }

    public void enterLoginData(String login, String password) {
        waitForElementAndSendKeys(LOGIN_INPUT, login,  "Cannot find and put a login to login field");
        waitForElementAndSendKeys(PASSWORD_INPUT, password,  "Cannot find and put a password to password field");
    }

    public void submitForm() {
        waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit button");
    }
}
