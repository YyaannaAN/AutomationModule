package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoreReviewTest extends BaseTest {

    // TODO: add checks for other field (maybe review details).

    /**
     * Check review author
     */
    @Test
    public void reviewAuthorPresenceTest() {
        String xpath = "//div[@class='top-reviews__carousel']//span[@class='review-item__name']";
        webDriver.get(url);

        //TODO: investigate if it is applicable to use first found element from the list in such way.
        WebElement author = webDriver.findElement(By.xpath(xpath));
        Assert.assertTrue(author.isDisplayed(), "Review author should be visible on the page");
        String name = author.getText().trim();
        Assert.assertFalse(name.isEmpty(), "Review author name should be present");

    }

}
