import basic.pages.components.StoreReviewComponent;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StoreReviewTest extends BaseTest {
    private StoreReviewComponent storeReviewComponent;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        storeReviewComponent = new StoreReviewComponent(webDriver);
    }

    // TODO: add checks for other field (maybe review details).

    /**
     * Check review author (at least one review with author name)
     */
    @Test(groups = {"positive"})
    public void reviewAuthorPresenceTest() {
        webDriver.get(url);
        Assert.assertTrue(
                storeReviewComponent.getAuthor().isDisplayed(),
                "Review author should be visible on the page"
        );

        Assert.assertFalse(
                storeReviewComponent.getAuthorName().isEmpty(),
                "Review author name should be present"
        );
    }
}
