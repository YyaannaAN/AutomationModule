import basic.pages.components.BannerListComponent;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BannerListTest extends BaseTest {
    private BannerListComponent bannerListComponent;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {

        bannerListComponent = new BannerListComponent(webDriver);
    }

    /**
     * The list of items in banners grid should not be empty.
     */
    @Test(groups = {"positive"}, priority = 2)
    public void getNotEmptyBannerListTest() {
        webDriver.get(url);
        Assert.assertFalse(
                bannerListComponent.getBannerList().isEmpty(),
                "Search result list must have at least one item."
        );
    }
}
