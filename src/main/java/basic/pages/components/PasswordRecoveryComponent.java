package basic.pages.components;

import basic.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PasswordRecoveryComponent extends BasePage {

    @FindBy(xpath = "//span[@class='form-passRecover']/a")
    private WebElement recoverLink;

    @FindBy(xpath = "//section[@id='password-recovery']//div[@class='popup-title']")
    private WebElement recoverTitle;

    @FindBy(xpath = "//section[@id='password-recovery']//div[@class='popup-msg j-recovery-message']")
    private WebElement recoverMessage;

    @FindBy(xpath = "//form[@id='password-recovery-form']//input[@type='submit']")
    private WebElement submit;

    @FindBy(xpath = "//form[@id='password-recovery-form']//input[@name='user[email]']")
    private WebElement recoverEmail;

    @FindBy(id = "password-recovery")
    private WebElement recoverPopup;

    @FindBy(xpath = "//section[@id='password-recovery']//a[@class='popup-close']")
    private WebElement closeButton;

    @FindBy(xpath = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]")
    private List<WebElement> errorList;

    public WebElement getRecoverPopup() {
        return recoverPopup;
    }

    public List<WebElement> getErrorList() {
        return errorList;
    }

    public WebElement getRecoverEmail() {
        return recoverEmail;
    }

    public WebElement getRecoverTitle() {
        return recoverTitle;
    }

    public WebElement getRecoverMessage() {
        return recoverMessage;
    }

    public WebElement getSubmit() {
        return submit;
    }

    public void openRecoveryPopup() {
        recoverLink.click();
    }

    public void close() {
        closeButton.click();
    }

    public void submit() {
        getSubmit().click();
    }

    public void waitPopupClose() {
        By recoveryLocator = By.id("password-recovery");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 5 seconds timeout
        wait.until(ExpectedConditions.invisibilityOfElementLocated(recoveryLocator));
    }

    public PasswordRecoveryComponent(WebDriver webDriver) {
        super(webDriver);
    }
}
