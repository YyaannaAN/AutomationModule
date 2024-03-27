import basic.pages.components.LoginFormComponent;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LoginFormTest extends BaseTest {
    private LoginFormComponent loginFormComponent;

    @BeforeMethod
    public void beforeMethod() {
        loginFormComponent = new LoginFormComponent(webDriver);
    }

    /**
     * Simple test of Login tab
     */
    @Test
    public void loginFormTitleTest() {
        webDriver.get(url);
        loginFormComponent.openLoginPopup();

        WebElement loginTab = loginFormComponent.getLoginTab();
        Assert.assertEquals(
                loginTab.getText(),
                "Вхід",
                "Login tab name should be correct."
        );
    }

    /**
     * Should be 2 fields in the login form.
     */
    @Test
    public void loginFormFieldsAndButtonPresenceTest() {
        webDriver.get(url);
        loginFormComponent.openLoginPopup();

        // Get form items for testing.
        WebElement login = loginFormComponent.getLogin();
        WebElement password = loginFormComponent.getPassword();
        WebElement submit = loginFormComponent.getSubmit();

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

        loginFormComponent.openLoginPopup();
        loginFormComponent.submitForm();

        List<WebElement> errorList = loginFormComponent.getErrorList();

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
