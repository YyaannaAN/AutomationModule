package basic.pages.components;

import basic.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BasePage {

    @FindBy(xpath = "//input[contains(concat(' ', normalize-space(@class), ' '), ' search__input ')]")
    private WebElement input;

    @FindBy(xpath = "//button[contains(concat(' ', normalize-space(@class), ' '), ' search__button ')]")
    private WebElement button;
    @FindBy(xpath = "//button[contains(concat(' ', normalize-space(@class), ' '), ' search__close ')]")
    private WebElement close;

    public WebElement getInput() {
        return input;
    }

    public WebElement getButton() {
        return button;
    }

    public WebElement getClose() {
        return close;
    }

    public HeaderComponent(WebDriver webDriver) {
        super(webDriver);
    }
}
