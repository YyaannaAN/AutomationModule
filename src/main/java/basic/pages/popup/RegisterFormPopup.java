package basic.pages.popup;

import basic.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RegisterFormPopup extends BasePage {
    @FindBy(xpath = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]")
    private WebElement profile;

    @FindBy(xpath = "//*[text()='Реєстрація']/..")
    private WebElement sighupTab;

    @FindBy(xpath = "//form[@id='signup-form']//input[@type='submit'][@class='btn-input']")
    private WebElement submit;

    @FindBy(xpath = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]")
    private List<WebElement> errorList;

    public WebElement getProfile() {
        return profile;
    }

    public WebElement getSighupTab() {
        return sighupTab;
    }

    public WebElement getSubmit() {
        return submit;
    }

    public List<WebElement> getErrorList() {
        return errorList;
    }

    public RegisterFormPopup(WebDriver webDriver) {
        super(webDriver);
    }
}
