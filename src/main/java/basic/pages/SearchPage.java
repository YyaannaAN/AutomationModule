package basic.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends BasePage{

    public List<WebElement> getCatalogCards() {
        return catalogCards;
    }

    @FindBy(xpath = "//div[@class='catalogCard j-catalog-card']")
    private List<WebElement> catalogCards;

    @FindBy(xpath = "//*[text()='Немає товарів']")
    private WebElement notFoundElement;

    public WebElement getNotFoundElement() {
        return notFoundElement;
    }

    public SearchPage(WebDriver webDriver) {
        super(webDriver);
    }


}
