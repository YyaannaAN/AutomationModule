import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTest {
    protected WebDriver webDriver;

    protected String url = "https://krauff.store";

    @BeforeClass
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }

    @BeforeMethod(alwaysRun = true)
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,720");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--incognito");

        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    @AfterMethod(alwaysRun = true)
    public void destroy() {
        webDriver.quit();
    }

    // HELPER FUNCTIONS ------------------------------------


//TODO: remove after usage
    public List<WebElement> getElementsByXpath(String xpath) {
        return webDriver.findElements(By.xpath(xpath));
    }


}
