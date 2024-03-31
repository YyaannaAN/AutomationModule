package basic.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EverythingLower1500Page extends BasePage{

    public List<WebElement> getProductListPrice() {
        return productListPrice;
    }

    @FindBy(xpath = "//td[@class='productsTable-cell __price']")
    private List<WebElement> productListPrice;

    public int getMaxPrice(){
        int maxPrice = 0;
        for(WebElement v : getProductListPrice()) {
            String result = v.getText().replaceAll("[^!0-9]", "");
            maxPrice= Integer.max(maxPrice, Integer.parseInt(result));
        }
        return maxPrice;
    }

    public EverythingLower1500Page(WebDriver webDriver) {
        super(webDriver);
    }
}
