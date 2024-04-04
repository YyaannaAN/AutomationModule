package basic.pages.components;

import basic.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BannerListComponent extends BasePage {

    @FindBy(xpath = "//div[@class='banners__container']/div[@class='banners__grid']//a[@class='banner-a']")
    private List<WebElement> bannerList;

    public BannerListComponent(WebDriver webDriver) {
        super(webDriver);
    }

    public List<WebElement> getBannerList() {
        return bannerList;
    }
}
