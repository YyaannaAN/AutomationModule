import basic.pages.SearchPage;
import basic.pages.components.HeaderComponent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchProductTest extends BaseTest {
    private HeaderComponent headerComponent;
    private SearchPage searchPage;

    @BeforeMethod
    public void beforeMethod() {
        headerComponent = new HeaderComponent(webDriver);
        searchPage = new SearchPage(webDriver);
    }

    /**
     * Check that search field is invisible at the beginning,
     * but becomes visible after clicking on the search button.
     */
    @Test(groups = {"positive"})
    public void searchFieldAvailabilityTest() {
        webDriver.get(url);

        Assert.assertFalse(
                headerComponent.getInput().isDisplayed(),
                "Search field should be invisible before clicking on the search button"
        );
        Assert.assertFalse(
                headerComponent.getClose().isDisplayed(),
                "Close search button should be invisible before clicking on the search button"
        );
        headerComponent.getButton().click();
        Assert.assertTrue(
                headerComponent.getInput().isDisplayed(),
                "Search field should be visible after clicking on the search button"
        );
        Assert.assertTrue(
                headerComponent.getClose().isDisplayed(),
                "Close search should be visible after clicking on the search button"
        );
    }

    @DataProvider(name="dataTest")
    public Object [][] data(){
        return new Object [][] {
                {"каструлі"},
                {"ножі"},
                {"сертифікат"}
        };
    }
    /**
     * Positive search of existing products
     */
    @Test(dataProvider = "dataTest",groups = {"positive"})
    public void positiveProductSearchTest(String text) {
        webDriver.get(url);

        headerComponent.getButton().click();

        WebElement input = headerComponent.getInput();
        input.clear();
        input.sendKeys(text);
        input.sendKeys(Keys.ENTER);

        // These cards shows required "add compare" buttons with :hover pseudo class.

        Assert.assertFalse(
                searchPage.getCatalogCards().isEmpty(),
                "The list of search result products should not be empty."
        );
    }

    /**
     * Negative search of non-existing products
     */
    @Test(groups = {"negative"})
    public void negativeProductSearchTest() {
        String text = "akasjdfkasjkdfsajfd";
        webDriver.get(url);

        headerComponent.getButton().click();

        WebElement input = headerComponent.getInput();
        input.clear();
        input.sendKeys(text);

        Actions builder = new Actions(webDriver);
        builder
                .scrollToElement(input)
                .click(input)
                .sendKeys(Keys.ENTER)
                .perform();

        Assert.assertTrue(
                searchPage.getNotFoundElement().isDisplayed(),
                "Not found message should be visible."
        );

        Assert.assertTrue(
                searchPage.getCatalogCards().isEmpty(),
                "The list of search result products should be empty."
        );
    }
}
