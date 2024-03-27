package basic.pages.components;

import basic.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LoginFormComponent extends BasePage {

    @FindBy(xpath = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]")
    private WebElement profile;

    @FindBy(xpath = "//*[text()='Вхід']/..")
    private WebElement loginTab;

    @FindBy(xpath = "//form[@id='login_form_id']//input[@name='user[email]']")
    private WebElement login;

    @FindBy(xpath = "//form[@id='login_form_id']//input[@name='user[pass]']")
    private WebElement password;

    @FindBy(xpath = "//form[@id='login_form_id']//input[@type='submit'][@class='btn-input']")
    private WebElement submit;

    @FindBy(xpath = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]")
    private List<WebElement> errorList;

    public List<WebElement> getErrorList() {
        return errorList;
    }

    public WebElement getLogin() {
        return login;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getSubmit() {
        return submit;
    }

    public WebElement getProfile() {
        return profile;
    }

    public WebElement getLoginTab() {
        return loginTab;
    }

    public void openLoginPopup() {

        getProfile().click();
        getLoginTab().click();
    }

    public void submitForm(){
        getSubmit().click();
    }
    public LoginFormComponent(WebDriver webDriver) {
        super(webDriver);
    }
}
