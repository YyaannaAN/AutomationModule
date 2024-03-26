package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SearchProductTest extends BaseTest {

    /**
     * Check that search field is invisible at the beginning,
     * but becomes visible after clicking on the search button.
     */
    @Test
    public void firstTest() {
        webDriver.get(url);
        String searchInputXpath = "//input[contains(concat(' ', normalize-space(@class), ' '), ' search__input ')]";
        String searchButtonXpath = "//button[contains(concat(' ', normalize-space(@class), ' '), ' search__button ')]";
        String closeButtonXpath = "//button[contains(concat(' ', normalize-space(@class), ' '), ' search__close ')]";

        WebElement input = webDriver.findElement(By.xpath(searchInputXpath));
        WebElement button = webDriver.findElement(By.xpath(searchButtonXpath));
        WebElement close = webDriver.findElement(By.xpath(closeButtonXpath));

        Assert.assertFalse(input.isDisplayed(), "Search field should be invisible before clicking on the search button");
        Assert.assertFalse(close.isDisplayed(), "Close search button should be invisible before clicking on the search button");
        button.click();
        Assert.assertTrue(input.isDisplayed(), "Search field should be visible after clicking on the search button");
        Assert.assertTrue(close.isDisplayed(), "Close search should be visible after clicking on the search button");
    }

    /**
     * Positive search of existing products
     */
    @Test
    public void sixthTest() {
        String text = "каструлі";
        webDriver.get(url);
        String searchInputXpath = "//input[contains(concat(' ', normalize-space(@class), ' '), ' search__input ')]";
        String searchButtonXpath = "//button[contains(concat(' ', normalize-space(@class), ' '), ' search__button ')]";

        WebElement button = webDriver.findElement(By.xpath(searchButtonXpath));

        button.click();

        WebElement input = webDriver.findElement(By.xpath(searchInputXpath));
        input.clear();
        input.sendKeys(text);
        input.sendKeys(Keys.ENTER);

        // These cards shows required "add compare" buttons with :hover pseudo class.
        String xpathToHover = "//div[@class='catalogCard j-catalog-card']";
        List<WebElement> catalogCards = getElementsByXpath(xpathToHover);
        Assert.assertFalse(catalogCards.isEmpty(), "The list of search result products should not be empty.");
    }

    /**
     * Negative search of non-existing products
     */
    @Test
    public void seventhTest() {
        String text = "akasjdfkasjkdfsajfd";
        webDriver.get(url);
        String searchInputXpath = "//input[contains(concat(' ', normalize-space(@class), ' '), ' search__input ')]";
        String searchButtonXpath = "//button[contains(concat(' ', normalize-space(@class), ' '), ' search__button ')]";

        WebElement button = webDriver.findElement(By.xpath(searchButtonXpath));

        button.click();

        WebElement input = webDriver.findElement(By.xpath(searchInputXpath));
        input.clear();
        input.sendKeys(text);

        Actions builder = new Actions(webDriver);
        builder
                .scrollToElement(input)
                .click(input)
                .sendKeys(Keys.ENTER)
                .perform();

        String notFoundXpath = "//*[text()='Немає товарів']";
        WebElement notFoundElement = webDriver.findElement(By.xpath(notFoundXpath));
        Assert.assertTrue(notFoundElement.isDisplayed(), "Not found message should be visible.");

        // Looking search result list elements.
        // NOTE: this check takes to long (default timeout specified in BaseTest)
        // because these elements will never appear
        String xpathCard = "//div[@class='catalogCard j-catalog-card']";
        List<WebElement> catalogCards = getElementsByXpath(xpathCard);
        Assert.assertTrue(catalogCards.isEmpty(), "The list of search result products should be empty.");
    }

}
