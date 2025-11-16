package UI_test.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final String URL = "https://demoqa.com/automation-practice-form";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(URL);
        checkURL();
        disableAds();
    }

    public void checkURL() {
        checkURL(URL);
    }
}
