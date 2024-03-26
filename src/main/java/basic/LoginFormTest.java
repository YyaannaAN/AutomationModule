package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LoginFormTest extends BaseTest {
    /**
     * Simple test of Login tab
     */
    @Test
    public void eighthTest() {
        webDriver.get(url);
        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        Assert.assertEquals(loginTab.getText(), "Вхід", "Login tab name should be correct.");
    }

    /**
     * Should be 2 fields in the login form.
     */
    @Test
    public void ninthTest() {
        webDriver.get(url);
        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        loginTab.click();

        String xpathLogin = "//form[@id='login_form_id']//input[@name='user[email]']";
        String xpathPassword = "//form[@id='login_form_id']//input[@name='user[pass]']";
        String xpathSubmit = "//form[@id='login_form_id']//input[@type='submit'][@class='btn-input']";

        // Get form items for testing.
        WebElement login = webDriver.findElement(By.xpath(xpathLogin));
        WebElement password = webDriver.findElement(By.xpath(xpathPassword));
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

        Assert.assertTrue(login.isDisplayed(), "Login field should be present in the form.");
        Assert.assertTrue(password.isDisplayed(), "Password field should be present in the form.");
        Assert.assertEquals(submit.getAttribute("value"), "Увійти", "Button should have correct label");
        // Protected by captcha
        Assert.assertTrue(submit.isEnabled(), "Submit button should be enabled in the form.");
    }


    /**
     * Check login form for empty fields submission
     */
    @Test(groups={"negative"})
    public void tenthTest() {
        webDriver.get(url);

        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        loginTab.click();

        String xpathSubmit = "//form[@id='login_form_id']//input[@type='submit'][@class='btn-input']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));
        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 2, "Should be 2 errors");
        Assert.assertEquals(errorList.get(0).getText(), "Некоректна адреса електронної пошти", "Should match empty login error.");
        Assert.assertEquals(errorList.get(1).getText(), "Вкажіть пароль", "Should match empty password error.");
    }
}
