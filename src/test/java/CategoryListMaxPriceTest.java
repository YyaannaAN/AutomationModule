import basic.pages.LessThanFixedPricePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CategoryListMaxPriceTest extends BaseTest {

    private LessThanFixedPricePage catalogPage;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        catalogPage = new LessThanFixedPricePage(webDriver);
    }

    @DataProvider(name = "categoryMaxPriceTest")
    public Object[][] categoryPageData() {
        return new Object[][]{
            { "/vse-do-1500-hrn", 1500 },
            { "/vse-do-2500-hrn", 2500 },
            { "/vse-do-3500-hrn", 3500 },
        };
    }

    /**
     * Get max price from the list and check it to be lower than expected max price.
     */
    @Test(dataProvider = "categoryMaxPriceTest", groups = {"positive"}, priority = 1)
    public void MaxListPriceTest(String catalog, int expectedMaxPrice) {
        String viewParams = "/filter/sort_price_high=DESC;view_type=list_view/";
        String catalogUrl = url + catalog + viewParams;

        webDriver.get(catalogUrl);

        int maxPrice = catalogPage.getMaxPrice();

        Assert.assertFalse(
                maxPrice > expectedMaxPrice,
                "The price is higher than " + expectedMaxPrice + " UAH! AR: " + maxPrice
        );
    }
}
