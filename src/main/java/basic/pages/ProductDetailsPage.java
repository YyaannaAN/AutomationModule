package basic.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailsPage extends BasePage {

    @FindBy(xpath = "//div[@class='product-price__box']/div[contains(concat(' ', normalize-space(@class), ' '), ' product-price__item ')]")
    private WebElement detailsPrice;


    public String getFirstProductDetailsPrice() {
        return detailsPrice.getText().trim();
    }

    public ProductDetailsPage(WebDriver webDriver) {
        super(webDriver);
    }
}
