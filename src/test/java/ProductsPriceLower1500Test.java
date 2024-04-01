import basic.pages.EverythingLower1500Page;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductsPriceLower1500Test extends BaseTest{

    private EverythingLower1500Page lower1500Page;

    @BeforeMethod
    public void beforeMethod() {
        lower1500Page = new EverythingLower1500Page(webDriver);
    }

    /**
     * get max price from the list and check it to be lower than 1500
     */
    @Test(groups = {"positive"})
    public void MaxListPriceTest() {
        String catalogUrl = url + "/vse-do-1500-hrn/filter/sort_price_high=DESC;view_type=list_view/";
        webDriver.get(catalogUrl);

        int maxPrice = lower1500Page.getMaxPrice();
        System.out.println(maxPrice);

        Assert.assertFalse(
                maxPrice > 1500 ,
                "The price is higher than 1500 uah! AR: " + maxPrice
        );
    }
}
