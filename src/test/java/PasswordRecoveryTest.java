import basic.pages.components.LoginFormComponent;
import basic.pages.components.PasswordRecoveryComponent;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class PasswordRecoveryTest extends BaseTest {
    private PasswordRecoveryComponent passwordRecoveryComponent;
    private LoginFormComponent loginFormComponent;

    @BeforeMethod
    public void beforeMethod() {
        passwordRecoveryComponent = new PasswordRecoveryComponent(webDriver);
        loginFormComponent = new LoginFormComponent(webDriver);
    }

    public void goToPasswordRecoveryPage() {
        webDriver.get(url);
        loginFormComponent.openLoginPopup();
        passwordRecoveryComponent.openRecoveryPopup();
    }

    /**
     * Check recovery email form exits with corresponding content.
     */
    @Test(groups = {"positive"})
    public void requiredElementsPresenceTest() {
        goToPasswordRecoveryPage();

        WebElement recoverTitle = passwordRecoveryComponent.getRecoverTitle();
        WebElement recoverMessage = passwordRecoveryComponent.getRecoverMessage();
        WebElement submit = passwordRecoveryComponent.getSubmit();

        // check Recovery page title
        Assert.assertTrue(recoverTitle.isDisplayed(), "Recovery page title should be visible.");
        Assert.assertEquals(recoverTitle.getText(), "Відновлення паролю", "Recovery page title should be correct.");

        // check Recovery page message
        Assert.assertTrue(recoverMessage.isDisplayed(), "Recovery page message should be visible.");
        Assert.assertEquals(recoverMessage.getText(), "Введіть адресу електронної пошти, яку ви вказали під час реєстрації. Ми надішлемо листа з інформацією для відновлення паролю.", "Recovery page message should be correct.");

        // check submit button
        Assert.assertEquals(submit.getAttribute("value"), "Відновити", "Button should have correct label");
        // Protected by captcha
        Assert.assertTrue(submit.isEnabled(), "Submit button should be enabled in the form.");

        // Get recovery email field
        WebElement recoverEmail = passwordRecoveryComponent.getRecoverEmail();

        Assert.assertTrue(recoverEmail.isDisplayed(), "Recovery email field should be present in the form.");
    }

    /**
     * Check password recovery form for empty email field submission
     */
    @Test(groups = {"negative"})
    public void emptyEmailSubmitTest() {
        goToPasswordRecoveryPage();

        // Get recovery email field
        WebElement recoverEmail = passwordRecoveryComponent.getRecoverEmail();

        Assert.assertEquals(recoverEmail.getText(), "", "Recovery email field should be blank.");

        // Get recovery form submit button
        passwordRecoveryComponent.submit();
        List<WebElement> errorList = passwordRecoveryComponent.getErrorList();

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
        WebElement recoverEmail = passwordRecoveryComponent.getRecoverEmail();

        Assert.assertEquals(recoverEmail.getText(), "", "Recovery email field should be blank.");
        recoverEmail.sendKeys("1235@sjdfk.sj");

        // Get recovery form submit button
        passwordRecoveryComponent.submit();
        List<WebElement> errorList = passwordRecoveryComponent.getErrorList();

        Assert.assertEquals(errorList.size(), 1, "Should be 1 error");
        Assert.assertEquals(errorList.get(0).getText(), "Немає користувача з такою адресою е-пошти", "Should match empty email error.");
    }

    /**
     * Check password recovery window can be closed.
     */
    @Test(groups = {"positive"})
    public void popupCloseTest() {
        goToPasswordRecoveryPage();

        WebElement recoverPopup = passwordRecoveryComponent.getRecoverPopup();

        Assert.assertTrue(recoverPopup.isDisplayed(), "Password recovery window should be visible.");

        passwordRecoveryComponent.close();
        passwordRecoveryComponent.waitPopupClose();
        Assert.assertFalse(recoverPopup.isDisplayed(), "Password recovery window should be closed.");
    }
}
