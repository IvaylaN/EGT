package UI_test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ValidatePage extends BasePage {

    @FindBy(css = ".modal-content")
    private WebElement modal;

    @FindBy(id = "example-modal-sizes-title-lg")
    private WebElement modalTitle;

    @FindBy(css = ".modal-content table tbody tr")
    private List<WebElement> rows;

    @FindBy(id = "closeLargeModal")
    private WebElement closeBtn;

    public ValidatePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void checkModal() {
        Assert.assertTrue(isModalOpen(), "Modal did not open.");
        verifyModalTitle();
    }

    public boolean isModalOpen() {
        try {
            smallWait.until(ExpectedConditions.visibilityOf(modal));
            return modal.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void verifyModalTitle() {
        String titleText = mediumWait.until(ExpectedConditions.visibilityOf(modalTitle)).getText().trim();
        Assert.assertEquals(titleText, "Thanks for submitting the form", "Title is not correct");
    }

    public Map<String, String> buildExpectedData(String firstName, String lastName, String email, String gender,
                                                 String mobileNum, String dateOfBirth, String subject,
                                                 String hobbies, String address, String city) {

        Map<String, String> expectedData = new LinkedHashMap<>();

        expectedData.put("Student Name", firstName + " " + lastName);
        expectedData.put("Student Email", email);
        expectedData.put("Gender", gender);
        expectedData.put("Mobile", mobileNum);
        expectedData.put("Date of Birth", convertDate(dateOfBirth));
        expectedData.put("Subjects", subject);
        expectedData.put("Hobbies", formatHobbies(hobbies));
        expectedData.put("Address", address);
        String state = getStateByCity(city);
        expectedData.put("State and City", state + " " + city);

        return expectedData;
    }

    private String formatHobbies(String hobbies) {
        return hobbies.replace("и", ",")
                .replaceAll("\\s*,\\s*", ", ")
                .trim();
    }

    private String convertDate(String input) {
        try {
            DateTimeFormatter in = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
            DateTimeFormatter out = DateTimeFormatter.ofPattern("dd MMMM,yyyy", Locale.ENGLISH);
            return LocalDate.parse(input, in).format(out);
        } catch (Exception e) {
            return input;
        }
    }

    public String getStateByCity(String city) {
        Map<String, String> stateMap = new HashMap<>();
        stateMap.put("Delhi", "NCR");
        stateMap.put("Gurgaon", "NCR");
        stateMap.put("Noida", "NCR");
        stateMap.put("Jaipur", "Rajasthan");
        stateMap.put("Lucknow", "Uttar Pradesh");
        stateMap.put("Agra", "Uttar Pradesh");
        return stateMap.getOrDefault(city, "Unknown");
    }

    public void validateModalData(String firstName, String lastName, String email, String gender,
                                  String mobileNum, String dateOfBirth, String subject,
                                  String hobbies, String address, String city) {

        Map<String, String> expectedData = buildExpectedData(firstName, lastName, email, gender,
                mobileNum, dateOfBirth, subject, hobbies, address, city);

        Map<String, String> actual = getActualModalData();

        for (String key : expectedData.keySet()) {
            Assert.assertTrue(actual.containsKey(key), "Missing field in modal: " + key);

            Assert.assertEquals(actual.get(key), expectedData.get(key),
                    "Value mismatch for field: " + key
            );
        }
    }

    public Map<String, String> getActualModalData() {
        Map<String, String> actualData = new LinkedHashMap<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() == 2) {
                String label = cells.get(0).getText().trim();
                String value = cells.get(1).getText().trim();
                actualData.put(label, value);
            }
        }
        return actualData;
    }

    public void closeModal() {
        mediumWait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
    }
}
