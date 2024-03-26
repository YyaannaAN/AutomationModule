package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ProductComparisonTest extends BaseTest {

    protected WebDriverWait getDefaultWaiter() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(15));
    }

    public WebElement getElement(String xpath) {
        return getDefaultWaiter()
                .until(ExpectedConditions.presenceOfElementLocated(
                        (By.xpath(xpath)))
                );
    }

    public WebElement getElementClickable(String xpath) {
        return getDefaultWaiter()
                .until(ExpectedConditions.elementToBeClickable(
                        (By.xpath(xpath)))
                );
    }

    public boolean waitElementInvisibility(String xpath) {
        return getDefaultWaiter()
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        (By.xpath(xpath)))
                );
    }


    /**
     * Testing product comparison possibility.
     * add one product to compare
     * add another product to compare
     * open compare window
     * Check that 2 items available for comparison.
     */
    @Test
    public void fifthTest() {
        String customUrl = url + "/nabory-kastrul";
        webDriver.get(customUrl);

        // TODO: check this theory
        // These cards shows required "add compare" buttons with :hover pseudo class.
        String xpathToHover = "//div[@class='catalogCard j-catalog-card']";
        List<WebElement> catalogCards = getElementsByXpath(xpathToHover);
        WebElement hover1 = catalogCards.get(0);
        WebElement hover2 = catalogCards.get(1);

        // Actions required to: 1) scroll screen to the element. 2) hover element to see "add to compare" button. 3)click button
        Actions builder = new Actions(webDriver);
        builder
                .scrollToElement(hover1)
                .moveToElement(hover1)
                .perform();

        // Get list of products
        String xpathToProducts = "//li[@class='catalog-grid__item']//span[@class='comparison-button__text']";
        List<WebElement> toCompareButtons = getElementsByXpath(xpathToProducts);
        Assert.assertFalse(toCompareButtons.isEmpty(), "The list of products should not be empty");

        // Selecting 2 products for further comparison.
        WebElement item1 = toCompareButtons.get(0);
        WebElement item2 = toCompareButtons.get(1);
        List<String> catalogPrices = getPricesFromCatalog();


        // Add first product for comparison
        // with handling of StaleElementReferenceException
        addToCompareItemSafe(0, item1, xpathToProducts);

        // Screen is already scrolled to this region
        builder.moveToElement(hover2).perform();

        // Add second product for comparison
        // with handling of StaleElementReferenceException
        addToCompareItemSafe(1, item2, xpathToProducts);

        // Check counter number on the comparison icon.
        String xpathCount = "//span[@class='comparison-view__count j-count']";
        WebElement compareCount = getElementClickable(xpathCount);

        // Move to element and implicitly wait for count update
        builder
                .scrollToElement(compareCount)
                .moveToElement(compareCount)
                .perform();

        // Test counter before moving to comparison window
        Assert.assertEquals(compareCount.getText(), "2", "Counter number should be equal to 2.");

        // Switching to comparison popup.
        String toComparisonXpath = "//div[@class='comparison-view']/a";
        WebElement toComparison = webDriver.findElement(By.xpath(toComparisonXpath));
        toComparison.click();

        // Selecting the table columns of products for testing.
        String productsXpath = "//div[@class='compare-window']//table[@class='compare-table']/thead/tr/td[@class='compare-cell __product']";
        List<WebElement> comparableProducts = webDriver.findElements(By.xpath(productsXpath));

        // Perform test
        Assert.assertEquals(comparableProducts.size(), 2, "There should be 2 items for comparing");

        List<String> comparisonPrices = getPricesFromComparison();

        Assert.assertEquals(comparisonPrices.get(0), catalogPrices.get(0), "First product from catalog list should have the same price in comparison window");
        Assert.assertEquals(comparisonPrices.get(1), catalogPrices.get(1), "Second product from catalog list should have the same price in comparison window");
    }

}
