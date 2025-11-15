package UITest.pages.tests;

import UITest.pages.pages.FormsPage;
import UITest.pages.pages.HomePage;
import UITest.pages.pages.ValidatePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

public class BaseTestMethods {
    protected WebDriver driver;
    protected HomePage homePage;
    protected FormsPage formsPage;
    protected ValidatePage validatePage;


    @BeforeMethod
    @Parameters({"browser", "env"})
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        driver = initDriver(browser);
        //cleanDirectory(SCREENSHOT_DIR);
        //cleanDirectory(REPORT_DIR);
        driver.manage().window().maximize();
        driver.get(getBaseUrl(env));
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        homePage = new HomePage(driver);
        formsPage = new FormsPage(driver);
        validatePage = new ValidatePage(driver);
    }

    private WebDriver initDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    private String getBaseUrl(String env) {
        switch (env.toLowerCase()) {
            case "test":
                return "https://test.demoqa.com/";
            case "dev":
            default:
                return "https://demoqa.com/";
        }
    }

    @AfterMethod
    public void cleanUp(ITestResult testResult) {
        //takeScreenshot(testResult);
        if (!testResult.isSuccess()) {
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
            ));
        }
        if (driver != null) {
            driver.quit();
        }
    }


}
