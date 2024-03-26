package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class PasswordRecoveryTest extends BaseTest {
    public void goToPasswordRecoveryPage() {
        webDriver.get(url);
        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        loginTab.click();

        String xpathRecover = "//span[@class='form-passRecover']/a";

        // Go to Sign in form
        WebElement recoverLink = webDriver.findElement(By.xpath(xpathRecover));
        recoverLink.click();
    }

    /**
     * Check recovery email form exits with corresponding content.
     */
    @Test(groups={"positive"})
    public void twelfthTest() {
        goToPasswordRecoveryPage();

        String xpathRecoveryTitle = "//section[@id='password-recovery']//div[@class='popup-title']";
        WebElement recoverTitle = webDriver.findElement(By.xpath(xpathRecoveryTitle));

        String xpathRecoveryMessage = "//section[@id='password-recovery']//div[@class='popup-msg j-recovery-message']";
        WebElement recoverMessage = webDriver.findElement(By.xpath(xpathRecoveryMessage));

        String xpathSubmit = "//form[@id='password-recovery-form']//input[@type='submit']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

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
        String xpathRecoveryEmail = "//form[@id='password-recovery-form']//input[@name='user[email]']";
        WebElement recoveryEmail = webDriver.findElement(By.xpath(xpathRecoveryEmail ));

        Assert.assertTrue(recoveryEmail.isDisplayed(), "Recovery email field should be present in the form.");
    }

    /**
     * Check password recovery form for empty email field submission
     */
    @Test(groups={"negative"})
    public void thirteenthTest() {
        goToPasswordRecoveryPage();

        // Get recovery email field
        String xpathRecoveryEmail = "//form[@id='password-recovery-form']//input[@name='user[email]']";
        WebElement recoveryEmail = webDriver.findElement(By.xpath(xpathRecoveryEmail ));

        Assert.assertEquals(recoveryEmail.getText(), "", "Recovery email field should be blank.");

        // Get recovery form submit button
        String xpathSubmit = "//form[@id='password-recovery-form']//input[@type='submit']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 1, "Should be 1 error");
        Assert.assertEquals(errorList.get(0).getText(), "Некоректна адреса електронної пошти", "Should match empty email error.");
    }

    /**
     * Check password recovery form for incorrect email value submission
     */
    @Test(groups={"negative"})
    public void fourteenthTest() {
        goToPasswordRecoveryPage();

        // Get recovery email field
        String xpathRecoveryEmail = "//form[@id='password-recovery-form']//input[@name='user[email]']";
        WebElement recoveryEmail = webDriver.findElement(By.xpath(xpathRecoveryEmail ));

        Assert.assertEquals(recoveryEmail.getText(), "", "Recovery email field should be blank.");
        recoveryEmail.sendKeys("1235@sjdfk.sj");

        // Get recovery form submit button
        String xpathSubmit = "//form[@id='password-recovery-form']//input[@type='submit']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 1, "Should be 1 error");
        Assert.assertEquals(errorList.get(0).getText(), "Немає користувача з такою адресою е-пошти", "Should match empty email error.");
    }

    /**
     * Check password recovery window can be closed.
     */
    @Test(groups={"positive"})
    public void fifteenthTest() {
        goToPasswordRecoveryPage();

        By recoveryLocator = By.id("password-recovery");
        WebElement recoveryPopup = webDriver.findElement(recoveryLocator);

        Assert.assertTrue(recoveryPopup.isDisplayed(), "Password recovery window should be visible.");

        // Get close button

        String xpathClose = "//section[@id='password-recovery']//a[@class='popup-close']";
        WebElement close = webDriver.findElement(By.xpath(xpathClose ));
        close.click();

        // Wait until popup is closed.
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 5 seconds timeout
        wait.until(ExpectedConditions.invisibilityOfElementLocated(recoveryLocator));

        Assert.assertFalse(recoveryPopup.isDisplayed(), "Password recovery window should be closed.");
    }
}
