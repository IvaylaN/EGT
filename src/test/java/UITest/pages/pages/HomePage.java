package UITest.pages.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final String URL = "https://demoqa.com/automation-practice-form";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(URL);
        checkURL();
        disableDemoqaAds();
    }

    public void disableDemoqaAds() {
        bigWait.until(ExpectedConditions.presenceOfElementLocated(By.id("fixedban")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "const ad = document.getElementById('fixedban');" +
                        "if (ad) { ad.style.display = 'none'; ad.style.visibility = 'hidden'; ad.remove(); }" +
                        "const footer = document.querySelector('footer');" +
                        "if (footer) { footer.style.display = 'none'; footer.style.visibility = 'hidden'; }"
        );
    }

    public void checkURL() {
        checkURL(URL);
    }
}
