import basic.pages.popup.RegisterFormPopup;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class RegisterFormTest extends BaseTest {
    private RegisterFormPopup registerFormPopup;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        registerFormPopup = new RegisterFormPopup(webDriver);
    }

    /**
     * Simple test of Register tab name
     */
    @Test(groups = {"positive"})
    public void tabNameTest() {
        webDriver.get(url);
        registerFormPopup.openPopup();

        WebElement sighupTab = registerFormPopup.getSighupTab();
        Assert.assertEquals(
                sighupTab.getText(),
                "Реєстрація",
                "Register tab name should be correct."
        );
    }


    /**
     * Check register form for empty fields submission
     */
    @Test(groups = {"negative"})
    public void emptyFieldsSubmitTest() {
        webDriver.get(url);
        registerFormPopup.openPopup();
        registerFormPopup.submit();


        List<WebElement> errorList = registerFormPopup.getErrorList();

        if(errorList.size() > 0) {
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
        else {
            String captchaError = registerFormPopup.getCaptchaError().getText();
            Assert.assertEquals(
                    captchaError,
                    "reCAPTCHA не пройдена.",
                    "Should match correct reCAPTCHA error.");
        }
    }
}
