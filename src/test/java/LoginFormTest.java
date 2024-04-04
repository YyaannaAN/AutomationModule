import basic.pages.popup.LoginFormPopup;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LoginFormTest extends BaseTest {
    private LoginFormPopup loginFormPopup;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        loginFormPopup = new LoginFormPopup(webDriver);
    }

    /**
     * Simple test of Login tab
     */
    @Test(groups = {"positive"})
    public void loginFormTitleTest() {
        webDriver.get(url);
        loginFormPopup.openLoginPopup();

        WebElement loginTab = loginFormPopup.getLoginTab();
        Assert.assertEquals(
                loginTab.getText(),
                "Вхід",
                "Login tab name should be correct."
        );
    }

    /**
     * Should be 2 fields in the login form.
     */
    @Test(groups = {"positive"})
    public void loginFormFieldsAndButtonPresenceTest() {
        webDriver.get(url);
        loginFormPopup.openLoginPopup();

        // Get form items for testing.
        WebElement login = loginFormPopup.getLogin();
        WebElement password = loginFormPopup.getPassword();
        WebElement submit = loginFormPopup.getSubmit();

        Assert.assertTrue(
                login.isDisplayed(),
                "Login field should be present in the form."
        );
        Assert.assertTrue(
                password.isDisplayed(),
                "Password field should be present in the form."
        );
        Assert.assertEquals(
                submit.getAttribute("value"),
                "Увійти",
                "Button should have correct label"
        );
        // Protected by captcha
        Assert.assertTrue(
                submit.isEnabled(),
                "Submit button should be enabled in the form."
        );
    }

    /**
     * Check login form for empty fields submission
     */
    @Test(groups = {"negative"})
    public void emptyFieldsSubmitTest() {
        webDriver.get(url);

        loginFormPopup.openLoginPopup();
        loginFormPopup.submitForm();

        List<WebElement> errorList = loginFormPopup.getErrorList();

        Assert.assertEquals(
                errorList.size(),
                2,
                "Should be 2 errors"
        );
        Assert.assertEquals(
                errorList.get(0).getText(),
                "Некоректна адреса електронної пошти",
                "Should match empty login error."
        );
        Assert.assertEquals(
                errorList.get(1).getText(),
                "Вкажіть пароль",
                "Should match empty password error."
        );
    }
}
