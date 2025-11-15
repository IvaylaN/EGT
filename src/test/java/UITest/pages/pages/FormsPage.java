package UITest.pages.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormsPage extends BasePage {

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "userNumber")
    private WebElement mobileField;

    @FindBy(id = "dateOfBirthInput")
    private WebElement dateOfBirthField;

    @FindBy(xpath = "//input[@id='subjectsInput']")
    private WebElement subjectsField;

    @FindBy(id = "currentAddress")
    private WebElement addressField;

    @FindBy(id = "submit")
    private WebElement submitBtn;

    @FindBy(css = ".react-datepicker__month-select")
    private WebElement monthDropdown;

    @FindBy(css = ".react-datepicker__year-select")
    private WebElement yearDropdown;

    @FindBy(id = "state")
    private WebElement stateDropdown;

    @FindBy(id = "city")
    private WebElement cityDropdown;

    public FormsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(String firstName) {
        System.out.println("- First name: " + firstName);
        enterText(firstNameField, firstName);
        checkFieldValue(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        System.out.println("- Last name: " + lastName);
        enterText(lastNameField, lastName);
        checkFieldValue(lastNameField, lastName);
    }

    public void enterEmail(String email) {
        System.out.println("- Email: " + email);
        enterText(emailField, email);
        checkFieldValue(emailField, email);
    }

    public void selectGender(String gender) {
        System.out.println("- Gender: " + gender);
        selectGenderOption(gender);
        isGenderSelected(gender);
    }

    public void selectGenderOption(String gender) {
        String formatGender = gender.trim().toLowerCase();
        String option = String.format(
                "//label[@for=(//input[@type='radio' and translate(@value," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='%s']/@id)]", formatGender);
        WebElement label = smallWait.until(ExpectedConditions.elementToBeClickable(By.xpath(option)));
        clickRadioBtn(label);
    }

    public boolean isGenderSelected(String gender) {
        String xpathGender = String.format(
                "//input[@type='radio' and translate(@value," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='%s']", gender.toLowerCase());
        WebElement genderRadio = driver.findElement(By.xpath(xpathGender));
        return genderRadio.isSelected();
    }

    public void enterMobileNum(String mobileNum) {
        System.out.println("- Mobile Number: " + mobileNum);
        enterText(mobileField, mobileNum);
        checkMobileNumber();
    }

    public void checkMobileNumber() {
        boolean isValid = isMobileNumberValid(mobileField);
        Assert.assertTrue(isValid, "Mobile number is invalid. Expected 10 digits, but field is empty or incorrect.");
    }

    public boolean isMobileNumberValid(WebElement element) {
        String value = element.getAttribute("value");
        return value != null && value.matches("\\d{10}");
    }

    public void setDateOfBirth(String dateOfBirth) {
        System.out.println("- Date of Birth: " + dateOfBirth);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateOfBirth, formatter);

        int year = date.getYear();
        String month = date.getMonth().name().substring(0, 1) +
                date.getMonth().name().substring(1).toLowerCase();

        String day = String.valueOf(date.getDayOfMonth());
        dateOfBirthField.click();

        selectYear(year);
        selectMonth(month);
        selectDay(day);
    }

    public void selectYear(int year) {
        mediumWait.until(ExpectedConditions.elementToBeClickable(yearDropdown)).click();
        WebElement yearOption = driver.findElement(By.xpath(
                String.format("//select[contains(@class,'year-select')]/option[@value='%d']", year)
        ));
        yearOption.click();
    }

    public void selectMonth(String month) {
        mediumWait.until(ExpectedConditions.elementToBeClickable(monthDropdown)).click();
        WebElement monthOption = driver.findElement(By.xpath(
                String.format("//select[contains(@class,'month-select')]/option[text()='%s']", month)
        ));
        monthOption.click();
    }

    public void selectDay(String day) {
        WebElement dayElement = driver.findElement(By.xpath(
                String.format("//div[contains(@class,'react-datepicker__day') and text()='%s']", day)
        ));
        mediumWait.until(ExpectedConditions.elementToBeClickable(dayElement)).click();
    }

    public void selectSubject(String subject) {
        System.out.println("- Subjects: " + subject);
        scrollToElement(subjectsField);
        subjectsField.click();
        subjectsField.sendKeys(subject);
        selectOption(subject).click();
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public WebElement selectOption(String subject) {
        String option = String.format("//div[contains(@id,'react-select') and text()='%s']", subject);
        return mediumWait.until(ExpectedConditions.elementToBeClickable(By.xpath(option)));
    }

    public void selectHobbies(String hobbies) {
        System.out.println("- Hobbies: " + hobbies);
        String[] hobbiesArray = hobbies.split("\\s*и\\s*|,");

        for (String hobby : hobbiesArray) {
            hobby = hobby.trim();
            String xpath = String.format("//label[contains(text(), '%s')]", hobby);
            WebElement hobbyElement = smallWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            hobbyElement.click();
        }
    }

    public void enterCurrentAddress(String address) {
        System.out.println("- Current Address: " + address);
        enterText(addressField, address);
    }

    public void selectCity(String city) {
        System.out.println("- City: " + city);
        String state = "";
        switch (city.trim().toLowerCase()) {
            case "delhi":
            case "gurgaon":
            case "noida":
                state = "NCR";
                break;
            case "agra":
            case "lucknow":
            case "merrut":
                state = "Uttar Pradesh";
                break;
            case "karnal":
            case "panipat":
                state = "Haryana";
                break;
            case "jaipur":
            case "jaiselmer":
                state = "Rajasthan";
                break;
            default:
                throw new IllegalArgumentException("The city \"" + city + "\" is not in the list!");
        }
        removeAds();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", stateDropdown);

        stateDropdown.click();
        WebElement stateOption = driver.findElement(By.xpath(String.format("//div[contains(@id,'react-select') " +
                "and text()='%s']", state)));
        stateOption.click();

        cityDropdown.click();
        WebElement cityOption = driver.findElement(By.xpath(String.format("//div[contains(@id,'react-select') " +
                "and text()='%s']", city)));
        cityOption.click();
    }

    public void removeAds() {
        try {
            driver.findElement(By.id("fixedban")).click();
            ((JavascriptExecutor) driver)
                    .executeScript("document.getElementById('fixedban').remove();");
        } catch (Exception ignored) {
        }
    }

    public void clickSubmitBtn() {
        clickElement(submitBtn);
    }

    public void enterDataInPracticeForm(String firstName, String lastName, String email, String gender,
                                        String mobileNum, String dateOfBirth, String subject,
                                        String hobbies, String address, String city) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        selectGender(gender);
        enterMobileNum(mobileNum);
        setDateOfBirth(dateOfBirth);
        selectSubject(subject);
        selectHobbies(hobbies);
        enterCurrentAddress(address);
        selectCity(city);
    }

    public boolean isFieldMarkedAsError(String fieldName) {
        String selector;

        switch (fieldName.toLowerCase()) {
            case "firstname":
                selector = "#firstName";
                break;
            case "lastname":
                selector = "#lastName";
                break;
            case "mobile":
                selector = "#userNumber";
                break;
            case "gender":
                selector = "input[name='gender'][required]";
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }

        WebElement field = driver.findElement(By.cssSelector(selector));
        String borderColor = field.getCssValue("border-color");

        return borderColor.equals("rgb(220, 53, 69)");
    }

    public void validateRequiredFieldsErrors() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(isFieldMarkedAsError("firstName"),
                "First Name is empty.");
        soft.assertTrue(isFieldMarkedAsError("lastName"),
                "Last Name is empty.");
        soft.assertTrue(isFieldMarkedAsError("gender"),
                "Gender is empty.");
        soft.assertTrue(isFieldMarkedAsError("mobile"),
                "Mobile Number is empty.");
        soft.assertAll();
    }
}
