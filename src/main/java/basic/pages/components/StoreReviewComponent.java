package basic.pages.components;

import basic.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StoreReviewComponent extends BasePage {

    @FindBy(xpath = "//div[@class='top-reviews__carousel']//span[@class='review-item__name']")
    private WebElement author;

    public StoreReviewComponent(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return author.getText().trim();
    }
}
