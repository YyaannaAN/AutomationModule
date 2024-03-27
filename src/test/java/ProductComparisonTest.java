import basic.pages.PanSetPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ProductComparisonTest extends BaseTest {
    private PanSetPage panSetPage;

    @BeforeMethod
    public void beforeMethod() {
        panSetPage = new PanSetPage(webDriver);
    }

    /**
     * Testing product comparison possibility.
     * add one product to compare
     * add another product to compare
     * open compare window
     * Check that 2 items available for comparison.
     */
    @Test
    public void productCompareTest() {
        String customUrl = url + "/nabory-kastrul";
        webDriver.get(customUrl);

        // TODO: check this theory
        // These cards shows required "add compare" buttons with :hover pseudo class.
        List<WebElement> catalogCards = panSetPage.getCatalogCards();
        WebElement hover1 = catalogCards.get(0);
        WebElement hover2 = catalogCards.get(1);

        // Actions required to: 1) scroll screen to the element. 2) hover element to see "add to compare" button. 3)click button
        Actions builder = new Actions(webDriver);
        builder
                .scrollToElement(hover1)
                .moveToElement(hover1)
                .perform();

        // Get list of products
        List<WebElement> toCompareButtons = panSetPage.getToCompareButtons();
        Assert.assertFalse(toCompareButtons.isEmpty(), "The list of products should not be empty");

        // Selecting 2 products for further comparison.
        WebElement item1 = toCompareButtons.get(0);
        WebElement item2 = toCompareButtons.get(1);
        List<String> catalogPrices = panSetPage.getPricesFromCatalog();


        // Add first product for comparison
        // with handling of StaleElementReferenceException
        panSetPage.addToCompareItemSafe(0, item1);

        // Screen is already scrolled to this region
        builder.moveToElement(hover2).perform();

        // Add second product for comparison
        // with handling of StaleElementReferenceException
        panSetPage.addToCompareItemSafe(1, item2);

        // Check counter number on the comparison icon.
        WebElement compareCount = panSetPage.getCompareCount();

        // Move to element and implicitly wait for count update
        builder
                .scrollToElement(compareCount)
                .moveToElement(compareCount)
                .perform();

        // Test counter before moving to comparison window
        Assert.assertEquals(compareCount.getText(), "2", "Counter number should be equal to 2.");

        // Switching to comparison popup.
        panSetPage.openComparisonPopup();
        // Selecting the table columns of products for testing.
        List<WebElement> comparableProducts = panSetPage.getComparableProducts();

        // Perform test
        Assert.assertEquals(comparableProducts.size(), 2, "There should be 2 items for comparing");

        List<String> comparisonPrices = panSetPage.getPricesFromComparison();

        Assert.assertEquals(
                comparisonPrices.get(0),
                catalogPrices.get(0),
                "First product from catalog list should have the same price in comparison window"
        );
        Assert.assertEquals(
                comparisonPrices.get(1),
                catalogPrices.get(1),
                "Second product from catalog list should have the same price in comparison window"
        );
    }
}
