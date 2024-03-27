import basic.pages.HomePage;
import basic.pages.ProductDetailsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductPromoListsTest extends BaseTest {
    private ProductDetailsPage productDetailsPage;
    private HomePage homePage;

    @BeforeMethod
    public void beforeMethod() {
        productDetailsPage = new ProductDetailsPage(webDriver);
        homePage = new HomePage(webDriver);
    }

    /**
     * Test information in the promo list and in the details.
     */
    @Test
    public void promoItemsPriceMatchTest() {
        webDriver.get(url);
        String listPrice = homePage.getFirstProductListPrice();

        homePage.goToFirstProductDetails();

        Assert.assertEquals(
                productDetailsPage.getFirstProductDetailsPrice(),
                listPrice,
                "Price from the list should match price in the item details"
        );
    }
}
