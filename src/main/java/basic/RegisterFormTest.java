package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class RegisterFormTest extends BaseTest {
    /**
     * Check register form for empty fields submission
     */
    @Test(groups={"negative"})
    public void eleventhTest() {
        webDriver.get(url);

        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathSignupTab = "//*[text()='Реєстрація']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement signupTab = webDriver.findElement(By.xpath(xpathSignupTab));
        signupTab.click();

        String xpathSubmit = "//form[@id='signup-form']//input[@type='submit'][@class='btn-input']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));
        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 3, "Should be 3 errors");
        Assert.assertEquals(errorList.get(0).getText(), "Вкажіть ім'я", "Should match empty name error.");
        Assert.assertEquals(errorList.get(1).getText(), "Некоректна адреса електронної пошти", "Should match empty email error.");
        Assert.assertEquals(errorList.get(2).getText(), "Довжина пароля повинна бути не менше 8 і не більше 15 символів.", "Should match empty password error.");
    }
}
