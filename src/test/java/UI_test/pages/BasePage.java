package UI_test.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait smallWait;
    protected WebDriverWait mediumWait;
    protected WebDriverWait bigWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        smallWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        mediumWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    protected void clickElement(WebElement element) {
        smallWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void clickRadioBtn(WebElement element) {
        bigWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void enterText(WebElement element, String text) {
        smallWait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected void checkURL(String url) {
        mediumWait.until(ExpectedConditions.urlToBe(url));
    }

    protected void checkFieldValue(WebElement element, String text) {
        String actual = element.getAttribute("value");
        Assert.assertEquals(actual, text, "Field value do no match, expected." + text + "but found " + actual);
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    protected void disableAds() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script =
                    "document.querySelectorAll('#fixedban, footer, iframe, [id^=\"google_ads\"], [id^=\"ad\"]')" +
                            ".forEach(el => el.remove());";

            js.executeScript(script);
        } catch (Exception ignored) {
        }
    }
}


