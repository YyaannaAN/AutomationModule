package basic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PanSetPage extends BasePage {

    @FindBy(xpath = "//div[@class='catalogCard j-catalog-card']")
    private List<WebElement> catalogCards;

    @FindBy(xpath = "//li[@class='catalog-grid__item']//span[@class='comparison-button__text']")
    private List<WebElement> toCompareButtons;

    @FindBy(xpath = "//span[@class='comparison-view__count j-count']")
    private WebElement compareCount;

    @FindBy(xpath = "//div[@class='compare-window']//table[@class='compare-table']/thead/tr/td[@class='compare-cell __product']")
    private List<WebElement> comparableProducts;

    @FindBy(xpath = "//div[@class='comparison-view']/a")
    private WebElement toComparison;

    @FindBy(xpath = "//div[@class='catalog__content']//div[@class='catalogCard-price']")
    private List<WebElement> pricesInCatalog;

    @FindBy(xpath = "//div[@class='compare-window']//table[@class='compare-table']//td//div[@class='catalogCard-price']")
    private List<WebElement> pricesInComparison;

    public List<WebElement> getCatalogCards() {
        return catalogCards;
    }
    public List<WebElement> getToCompareButtons() {
        return toCompareButtons;
    }
    public List<WebElement> getComparableProducts() {
        return comparableProducts;
    }

    public WebElement getElementClickable(String xpath) {
        return getDefaultWaiter()
                .until(ExpectedConditions.elementToBeClickable(
                        (By.xpath(xpath)))
                );
    }

    public WebElement getCompareCount() {
        String xpathCount = "//span[@class='comparison-view__count j-count']";
        return getElementClickable(xpathCount);
    }

    public String getElementValue(WebElement element) {
        return element.getText().trim();
    }

    public List<String> getValuesFromCatalog(List<WebElement> list) {
        List<String> result = new ArrayList<>();
        result.add(getElementValue(list.get(0)));
        result.add(getElementValue(list.get(1)));
        return result;
    }

    public List<String> getPricesFromCatalog() {
        return getValuesFromCatalog(pricesInCatalog);
    }

    public List<String> getPricesFromComparison() {
        return getValuesFromCatalog(pricesInComparison);
    }

    public void openComparisonPopup() {
        toComparison.click();
    }

    protected WebDriverWait getDefaultWaiter() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(15));
    }

    public void addToCompareItemSafe(int itemIndex, WebElement originalItem) {
        try {
            originalItem.click();
        } catch (StaleElementReferenceException e) {
            List<WebElement> productList = getToCompareButtons();
            WebElement item = productList.get(itemIndex);
            item.click();
        }
    }

    public PanSetPage(WebDriver webDriver) {
        super(webDriver);
    }
}
