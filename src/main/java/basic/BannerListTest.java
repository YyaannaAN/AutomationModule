package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BannerListTest extends BaseTest {

    /**
     * The list of items in banners grid should not be empty.
     */
    @Test(groups = {"positive"})
    public void getNotEmptyBannerListTest() {
        webDriver.get(url);
        String bannerLinksXpath = "//div[@class='banners__container']/div[@class='banners__grid']//a[@class='banner-a']";
        List<WebElement> list = webDriver.findElements(By.xpath(bannerLinksXpath));
        Assert.assertFalse(list.isEmpty(), "Search result list must have at least one item.");
    }

}

