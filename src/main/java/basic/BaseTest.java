package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    public WebDriver webDriver;

    protected String url = "https://krauff.store";

    @BeforeMethod(alwaysRun = true)
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1980,720");

        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod(alwaysRun = true)
    public void destroy() {
        webDriver.quit();
    }

    // HELPER FUNCTIONS ------------------------------------

    public void addToCompareItemSafe(int itemIndex, WebElement originalItem, String xpathToProducts) {
        try {
            originalItem.click();
        } catch (StaleElementReferenceException e) {
            List<WebElement> productList = webDriver.findElements(By.xpath(xpathToProducts));
            WebElement item = productList.get(itemIndex);
            item.click();
        }
    }

    public List<WebElement> getElementsByXpath(String xpath) {
        return webDriver.findElements(By.xpath(xpath));
    }

    public String getElementValue(WebElement element) {
        return element.getText().trim();
    }

    public List<String> getValuesFromCatalog(String xpath) {
        List<WebElement> list = getElementsByXpath(xpath);
        List<String> result = new ArrayList<>();
        result.add(getElementValue(list.get(0)));
        result.add(getElementValue(list.get(1)));
        return result;
    }

    public List<String> getPricesFromCatalog() {
        String xpathPricesInCatalog = "//div[@class='catalog__content']//div[@class='catalogCard-price']";
        return getValuesFromCatalog(xpathPricesInCatalog);
    }

    public List<String> getPricesFromComparison() {
        String xpathPricesInComparison = "//div[@class='compare-window']//table[@class='compare-table']//td//div[@class='catalogCard-price']";
        return getValuesFromCatalog(xpathPricesInComparison);
    }
}
