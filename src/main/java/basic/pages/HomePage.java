package basic.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {
    private int index = 0;
    @FindBy(xpath = "//section[@class='promo'][1]//div[@class='catalogCard-info']//div[@class='catalogCard-price']")
    private List<WebElement> listPrice;

    @FindBy(xpath = "//section[@class='promo'][1]//div[@class='catalogCard-info']/div[@class='catalogCard-title']/a")
    private List<WebElement> list;
    public String getFirstProductListPrice() {
        WebElement price = listPrice.get(index);
        String listItemPrice = price.getText().trim();
        return listItemPrice;
    }

    public void goToFirstProductDetails() {
        WebElement itemDetailsLink = list.get(index);
        itemDetailsLink.click();
    }
    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }
}
