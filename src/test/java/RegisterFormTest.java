import basic.pages.popup.RegisterFormPopup;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class RegisterFormTest extends BaseTest {
    private RegisterFormPopup registerFormPopup;

    @BeforeMethod
    public void beforeMethod() {
        registerFormPopup = new RegisterFormPopup(webDriver);
    }

    /**
     * Check register form for empty fields submission
     */
    @Test(groups = {"negative"})
    public void emptyFieldsSubmitTest() {
        webDriver.get(url);

        // Go to profile window to signin or signup
        registerFormPopup.getProfile().click();

        registerFormPopup.getSighupTab().click();

        registerFormPopup.getSubmit().click();

        List<WebElement> errorList = registerFormPopup.getErrorList();

        Assert.assertEquals(
                errorList.size(),
                3,
                "Should be 3 errors"
        );
        Assert.assertEquals(
                errorList.get(0).getText(),
                "Вкажіть ім'я",
                "Should match empty name error."
        );
        Assert.assertEquals(
                errorList.get(1).getText(),
                "Некоректна адреса електронної пошти",
                "Should match empty email error."
        );
        Assert.assertEquals(
                errorList.get(2).getText(),
                "Довжина пароля повинна бути не менше 8 і не більше 15 символів.",
                "Should match empty password error."
        );
    }
}
