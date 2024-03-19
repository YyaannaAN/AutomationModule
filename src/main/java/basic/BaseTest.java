package basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    public WebDriver webDriver;

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
}
