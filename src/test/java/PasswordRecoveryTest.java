import basic.pages.popup.LoginFormPopup;
import basic.pages.popup.PasswordRecoveryPopup;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class PasswordRecoveryTest extends BaseTest {
    private PasswordRecoveryPopup passwordRecoveryPopup;
    private LoginFormPopup loginFormPopup;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        passwordRecoveryPopup = new PasswordRecoveryPopup(webDriver);
        loginFormPopup = new LoginFormPopup(webDriver);
    }

    public void goToPasswordRecoveryPage() {
        webDriver.get(url);
        loginFormPopup.openLoginPopup();
        passwordRecoveryPopup.openRecoveryPopup();
    }

    /**
     * Check recovery email form exits with corresponding content.
     */
    @Test(groups = {"positive"})
    public void requiredElementsPresenceTest() {
        goToPasswordRecoveryPage();

        WebElement recoverTitle = passwordRecoveryPopup.getRecoverTitle();
        WebElement recoverMessage = passwordRecoveryPopup.getRecoverMessage();
        WebElement submit = passwordRecoveryPopup.getSubmit();

        // check Recovery page title
        Assert.assertTrue(
                recoverTitle.isDisplayed(),
                "Recovery page title should be visible."
        );
        Assert.assertEquals(
                recoverTitle.getText(),
                "Відновлення паролю",
                "Recovery page title should be correct."
        );

        // check Recovery page message
        Assert.assertTrue(
                recoverMessage.isDisplayed(),
                "Recovery page message should be visible."
        );
        Assert.assertEquals(
                recoverMessage.getText(),
                "Введіть адресу електронної пошти, яку ви вказали під час реєстрації. Ми надішлемо листа з інформацією для відновлення паролю.",
                "Recovery page message should be correct."
        );

        // check submit button
        Assert.assertEquals(
                submit.getAttribute("value"),
                "Відновити",
                "Button should have correct label"
        );
        // Protected by captcha
        Assert.assertTrue(
                submit.isEnabled(),
                "Submit button should be enabled in the form."
        );

        // Get recovery email field
        WebElement recoverEmail = passwordRecoveryPopup.getRecoverEmail();

        Assert.assertTrue(
                recoverEmail.isDisplayed(),
                "Recovery email field should be present in the form."
        );
    }

    /**
     * Check password recovery form for empty email field submission
     */
    @Test(groups = {"negative"})
    public void emptyEmailSubmitTest() {
        goToPasswordRecoveryPage();

        // Get recovery email field
        WebElement recoverEmail = passwordRecoveryPopup.getRecoverEmail();

        Assert.assertEquals(recoverEmail.getText(), "", "Recovery email field should be blank.");

        // Get recovery form submit button
        passwordRecoveryPopup.submit();
        List<WebElement> errorList = passwordRecoveryPopup.getErrorList();

        Assert.assertEquals(errorList.size(), 1, "Should be 1 error");
        Assert.assertEquals(errorList.get(0).getText(), "Некоректна адреса електронної пошти", "Should match empty email error.");
    }

    /**
     * Check password recovery form for incorrect email value submission
     */
    @Test(groups = {"negative"})
    public void incorrectEmailErrorTest() {
        goToPasswordRecoveryPage();

        // Get recovery email field
        WebElement recoverEmail = passwordRecoveryPopup.getRecoverEmail();

        Assert.assertEquals(
                recoverEmail.getText(),
                "",
                "Recovery email field should be blank."
        );
        recoverEmail.sendKeys("1235@sjdfk.sj");

        // Get recovery form submit button
        passwordRecoveryPopup.submit();
        List<WebElement> errorList = passwordRecoveryPopup.getErrorList();

        Assert.assertEquals(
                errorList.size(),
                1,
                "Should be 1 error"
        );
        Assert.assertEquals(
                errorList.get(0).getText(),
                "Немає користувача з такою адресою е-пошти",
                "Should match empty email error."
        );
    }

    /**
     * Check password recovery window can be closed.
     */
    @Test(groups = {"positive"})
    public void popupCloseTest() {
        goToPasswordRecoveryPage();

        WebElement recoverPopup = passwordRecoveryPopup.getRecoverPopup();

        Assert.assertTrue(
                recoverPopup.isDisplayed(),
                "Password recovery window should be visible."
        );

        passwordRecoveryPopup.close();
        passwordRecoveryPopup.waitPopupClose();
        Assert.assertFalse(
                recoverPopup.isDisplayed(),
                "Password recovery window should be closed."
        );
    }
}
