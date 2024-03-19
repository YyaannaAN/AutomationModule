package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TeapotTest extends BaseTest {

    private String url = "https://krauff.store";

    protected WebDriverWait getDefaultWaiter() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(15));
    }

    public WebElement getElement(String xpath) {
        return getDefaultWaiter()
            .until(ExpectedConditions.presenceOfElementLocated(
                (By.xpath(xpath)))
            );
    }

    public WebElement getElementClickable(String xpath) {
        return getDefaultWaiter()
                .until(ExpectedConditions.elementToBeClickable(
                        (By.xpath(xpath)))
                );
    }

    public boolean waitElementInvisibility(String xpath) {
        return getDefaultWaiter()
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        (By.xpath(xpath)))
                );
    }


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
     * The list of items in banners grid should not be empty.
     */
    @Test(groups = {"negative"})
    public void secondTest() {
        webDriver.get(url);
        String bannerLinksXpath = "//div[@class='banners__container']/div[@class='banners__grid']//a[@class='banner-a']";
        List<WebElement> list = webDriver.findElements(By.xpath(bannerLinksXpath));
        Assert.assertFalse(list.isEmpty(), "Search result list must have at least one item.");
    }


    /**
     * Test information in the list and in the details.
     */
    @Test
    public void thirdTest() {
        int index = 0;
        webDriver.get(url);

        String catalogPriceXpath = "//section[@class='promo'][1]//div[@class='catalogCard-info']//div[@class='catalogCard-price']";
        List<WebElement> listPrice = webDriver.findElements(By.xpath(catalogPriceXpath));
        WebElement price = listPrice.get(index);
        String listItemPrice = price.getText().trim();

        String catalogLinkXpath = "//section[@class='promo'][1]//div[@class='catalogCard-info']/div[@class='catalogCard-title']/a";
        List<WebElement> list = webDriver.findElements(By.xpath(catalogLinkXpath));

        // anchor of the item
        WebElement itemDetailsLink = list.get(index);

        itemDetailsLink.click();

        // price
        String priceDetailsXpath = "//div[@class='product-price__box']/div[contains(concat(' ', normalize-space(@class), ' '), ' product-price__item ')]";
        WebElement detailsPrice = webDriver.findElement(By.xpath(priceDetailsXpath));
        String detailsItemPrice = detailsPrice.getText().trim();

        Assert.assertEquals(detailsItemPrice, listItemPrice, "Price from the list should match title in the item details");
    }

    @Test
    public void fourthTest() {
        String xpath = "//div[@class='top-reviews__carousel']//span[@class='review-item__name']";
        webDriver.get(url);

        //TODO: investigate if it is applicable to use first found element from the list in such way.
        WebElement author = webDriver.findElement(By.xpath(xpath));
        Assert.assertTrue(author.isDisplayed(), "Review author should be visible on the page");
        String name = author.getText().trim();
        Assert.assertFalse(name.isEmpty(), "Review author name should be present");

    }

    /**
     * Testing product comparison possibility.
     * add one product to compare
     * add another product to compare
     * open compare window
     * Check that 2 items available for comparison.
     */
    @Test
    public void fifthTest() {
        String customUrl = url + "/nabory-kastrul";
        webDriver.get(customUrl);

        // TODO: check this theory
        // These cards shows required "add compare" buttons with :hover pseudo class.
        String xpathToHover = "//div[@class='catalogCard j-catalog-card']";
        List<WebElement> catalogCards = getElementsByXpath(xpathToHover);
        WebElement hover1 = catalogCards.get(0);
        WebElement hover2 = catalogCards.get(1);

        // Actions required to: 1) scroll screen to the element. 2) hover element to see "add to compare" button. 3)click button
        Actions builder = new Actions(webDriver);
        builder
                .scrollToElement(hover1)
                .moveToElement(hover1)
                .perform();

        // Get list of products
        String xpathToProducts = "//li[@class='catalog-grid__item']//span[@class='comparison-button__text']";
        List<WebElement> toCompareButtons = getElementsByXpath(xpathToProducts);
        Assert.assertFalse(toCompareButtons.isEmpty(), "The list of products should not be empty");

        // Selecting 2 products for further comparison.
        WebElement item1 = toCompareButtons.get(0);
        WebElement item2 = toCompareButtons.get(1);
        List<String> catalogPrices = getPricesFromCatalog();


        // Add first product for comparison
        // with handling of StaleElementReferenceException
        addToCompareItemSafe(0, item1, xpathToProducts);

        // Screen is already scrolled to this region
        builder.moveToElement(hover2).perform();

        // Add second product for comparison
        // with handling of StaleElementReferenceException
        addToCompareItemSafe(1, item2, xpathToProducts);

        // Check counter number on the comparison icon.
        String xpathCount = "//span[@class='comparison-view__count j-count']";
        WebElement compareCount = getElementClickable(xpathCount);

        // Move to element and implicitly wait for count update
        builder
                .scrollToElement(compareCount)
                .moveToElement(compareCount)
                .perform();

        // Test counter before moving to comparison window
        Assert.assertEquals(compareCount.getText(), "2", "Counter number should be equal to 2.");

        // Switching to comparison popup.
        String toComparisonXpath = "//div[@class='comparison-view']/a";
        WebElement toComparison = webDriver.findElement(By.xpath(toComparisonXpath));
        toComparison.click();

        // Selecting the table columns of products for testing.
        String productsXpath = "//div[@class='compare-window']//table[@class='compare-table']/thead/tr/td[@class='compare-cell __product']";
        List<WebElement> comparableProducts = webDriver.findElements(By.xpath(productsXpath));

        // Perform test
        Assert.assertEquals(comparableProducts.size(), 2, "There should be 2 items for comparing");

        List<String> comparisonPrices = getPricesFromComparison();

        Assert.assertEquals(comparisonPrices.get(0), catalogPrices.get(0), "First product from catalog list should have the same price in comparison window");
        Assert.assertEquals(comparisonPrices.get(1), catalogPrices.get(1), "Second product from catalog list should have the same price in comparison window");
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

    /**
     * Simple test of Login tab
     */
    @Test
    public void eighthTest() {
        webDriver.get(url);
        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        Assert.assertEquals(loginTab.getText(), "Вхід", "Login tab name should be correct.");
    }

    /**
     * Should be 2 fields in the login form.
     */
    @Test
    public void ninthTest() {
        webDriver.get(url);
        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        loginTab.click();

        String xpathLogin = "//form[@id='login_form_id']//input[@name='user[email]']";
        String xpathPassword = "//form[@id='login_form_id']//input[@name='user[pass]']";
        String xpathSubmit = "//form[@id='login_form_id']//input[@type='submit'][@class='btn-input']";

        // Get form items for testing.
        WebElement login = webDriver.findElement(By.xpath(xpathLogin));
        WebElement password = webDriver.findElement(By.xpath(xpathPassword));
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

        Assert.assertTrue(login.isDisplayed(), "Login field should be present in the form.");
        Assert.assertTrue(password.isDisplayed(), "Password field should be present in the form.");
        Assert.assertEquals(submit.getAttribute("value"), "Увійти", "Button should have correct label");
        // Protected by captcha
        Assert.assertTrue(submit.isEnabled(), "Submit button should be enabled in the form.");
    }


    /**
     * Check login form for empty fields submission
     */
    @Test(groups={"negative"})
    public void tenthTest() {
        webDriver.get(url);

        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        loginTab.click();

        String xpathSubmit = "//form[@id='login_form_id']//input[@type='submit'][@class='btn-input']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));
        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 2, "Should be 2 errors");
        Assert.assertEquals(errorList.get(0).getText(), "Некоректна адреса електронної пошти", "Should match empty login error.");
        Assert.assertEquals(errorList.get(1).getText(), "Вкажіть пароль", "Should match empty password error.");
    }

    /**
     * Check register form for empty fields submission
     */
    @Test(groups={"negative"})
    public void eleventhTest() {
        webDriver.get(url);

        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathSignupTab = "//*[text()='Реєстрація']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement signupTab = webDriver.findElement(By.xpath(xpathSignupTab));
        signupTab.click();

        String xpathSubmit = "//form[@id='signup-form']//input[@type='submit'][@class='btn-input']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));
        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 3, "Should be 3 errors");
        Assert.assertEquals(errorList.get(0).getText(), "Вкажіть ім'я", "Should match empty name error.");
        Assert.assertEquals(errorList.get(1).getText(), "Некоректна адреса електронної пошти", "Should match empty email error.");
        Assert.assertEquals(errorList.get(2).getText(), "Довжина пароля повинна бути не менше 8 і не більше 15 символів.", "Should match empty password error.");
    }

    public void goToPasswordRecoveryPage() {
        webDriver.get(url);
        String xpathProfile = "//a[contains(concat(' ', normalize-space(@class), ' '), ' userbar__button ')]";
        String xpathLoginTab = "//*[text()='Вхід']/..";

        // Go to profile window to signin or signup
        WebElement profile = webDriver.findElement(By.xpath(xpathProfile));
        profile.click();

        // Go to Sign in form
        WebElement loginTab = webDriver.findElement(By.xpath(xpathLoginTab));
        loginTab.click();

        String xpathRecover = "//span[@class='form-passRecover']/a";

        // Go to Sign in form
        WebElement recoverLink = webDriver.findElement(By.xpath(xpathRecover));
        recoverLink.click();
    }

    /**
     * Check recovery email form exits with corresponding content.
     */
    @Test(groups={"positive"})
    public void twelfthTest() {
        goToPasswordRecoveryPage();

        String xpathRecoveryTitle = "//section[@id='password-recovery']//div[@class='popup-title']";
        WebElement recoverTitle = webDriver.findElement(By.xpath(xpathRecoveryTitle));

        String xpathRecoveryMessage = "//section[@id='password-recovery']//div[@class='popup-msg j-recovery-message']";
        WebElement recoverMessage = webDriver.findElement(By.xpath(xpathRecoveryMessage));

        String xpathSubmit = "//form[@id='password-recovery-form']//input[@type='submit']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

        // check Recovery page title
        Assert.assertTrue(recoverTitle.isDisplayed(), "Recovery page title should be visible.");
        Assert.assertEquals(recoverTitle.getText(), "Відновлення паролю", "Recovery page title should be correct.");

        // check Recovery page message
        Assert.assertTrue(recoverMessage.isDisplayed(), "Recovery page message should be visible.");
        Assert.assertEquals(recoverMessage.getText(), "Введіть адресу електронної пошти, яку ви вказали під час реєстрації. Ми надішлемо листа з інформацією для відновлення паролю.", "Recovery page message should be correct.");

        // check submit button
        Assert.assertEquals(submit.getAttribute("value"), "Відновити", "Button should have correct label");
        // Protected by captcha
        Assert.assertTrue(submit.isEnabled(), "Submit button should be enabled in the form.");

        // Get recovery email field
        String xpathRecoveryEmail = "//form[@id='password-recovery-form']//input[@name='user[email]']";
        WebElement recoveryEmail = webDriver.findElement(By.xpath(xpathRecoveryEmail ));

        Assert.assertTrue(recoveryEmail.isDisplayed(), "Recovery email field should be present in the form.");
    }

    /**
     * Check password recovery form for empty email field submission
     */
    @Test(groups={"negative"})
    public void thirteenthTest() {
        goToPasswordRecoveryPage();

        // Get recovery email field
        String xpathRecoveryEmail = "//form[@id='password-recovery-form']//input[@name='user[email]']";
        WebElement recoveryEmail = webDriver.findElement(By.xpath(xpathRecoveryEmail ));

        Assert.assertEquals(recoveryEmail.getText(), "", "Recovery email field should be blank.");

        // Get recovery form submit button
        String xpathSubmit = "//form[@id='password-recovery-form']//input[@type='submit']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 1, "Should be 1 error");
        Assert.assertEquals(errorList.get(0).getText(), "Некоректна адреса електронної пошти", "Should match empty email error.");
    }

    /**
     * Check password recovery form for incorrect email value submission
     */
    @Test(groups={"negative"})
    public void fourteenthTest() {
        goToPasswordRecoveryPage();

        // Get recovery email field
        String xpathRecoveryEmail = "//form[@id='password-recovery-form']//input[@name='user[email]']";
        WebElement recoveryEmail = webDriver.findElement(By.xpath(xpathRecoveryEmail ));

        Assert.assertEquals(recoveryEmail.getText(), "", "Recovery email field should be blank.");
        recoveryEmail.sendKeys("1235@sjdfk.sj");

        // Get recovery form submit button
        String xpathSubmit = "//form[@id='password-recovery-form']//input[@type='submit']";
        WebElement submit = webDriver.findElement(By.xpath(xpathSubmit));

        submit.click();

        String xpathErrors = "//div[@class='form-error']/div[contains(concat(' ', normalize-space(@class), ' '), ' form-error-box ')]";
        List<WebElement> errorList = getElementsByXpath(xpathErrors);

        Assert.assertEquals(errorList.size(), 1, "Should be 1 error");
        Assert.assertEquals(errorList.get(0).getText(), "Немає користувача з такою адресою е-пошти", "Should match empty email error.");
    }

    /**
     * Check password recovery window can be closed.
     */
    @Test(groups={"positive"})
    public void fifteenthTest() {
        goToPasswordRecoveryPage();

        By recoveryLocator = By.id("password-recovery");
        WebElement recoveryPopup = webDriver.findElement(recoveryLocator);

        Assert.assertTrue(recoveryPopup.isDisplayed(), "Password recovery window should be visible.");

        // Get close button

        String xpathClose = "//section[@id='password-recovery']//a[@class='popup-close']";
        WebElement close = webDriver.findElement(By.xpath(xpathClose ));
        close.click();

        // Wait until popup is closed.
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 5 seconds timeout
        wait.until(ExpectedConditions.invisibilityOfElementLocated(recoveryLocator));

        Assert.assertFalse(recoveryPopup.isDisplayed(), "Password recovery window should be closed.");
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
