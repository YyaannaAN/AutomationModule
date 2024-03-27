import basic.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductPromoListsTest extends BaseTest {

    /**
     * Test information in the promo list and in the details.
     */
    @Test
    public void promoItemsPriceMatchTest() {
        int index = 0;
        webDriver.get(url);

        String catalogPriceXpath = "//section[@class='promo'][1]//div[@class='catalogCard-info']//div[@class='catalogCard-price']";
        List<WebElement> listPrice = webDriver.findElements(By.xpath(catalogPriceXpath));
        WebElement price = listPrice.get(index);
        String listItemPrice = price.getText().trim();

        String catalogLinkXpath = "//section[@class='promo'][1]//div[@class='catalogCard-info']/div[@class='catalogCard-title']/a";
        List<WebElement> list = webDriver.findElements(By.xpath(catalogLinkXpath));

        // anchor of the item
        WebElement itemDetailsLink = list.get(index);

        itemDetailsLink.click();

        // price
        String priceDetailsXpath = "//div[@class='product-price__box']/div[contains(concat(' ', normalize-space(@class), ' '), ' product-price__item ')]";
        WebElement detailsPrice = webDriver.findElement(By.xpath(priceDetailsXpath));
        String detailsItemPrice = detailsPrice.getText().trim();

        Assert.assertEquals(detailsItemPrice, listItemPrice, "Price from the list should match price in the item details");
    }
}
