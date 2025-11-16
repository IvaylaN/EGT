package UI_test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormsPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(FormsPage.class);

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

    @FindBy(id = "subjectsInput")
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

    //text field for firstname, lastname, email, mobile, address

    public void enterFirstName(String firstName) {
        logger.info("- First name: " + firstName);
        enterText(firstNameField, firstName);
        checkFieldValue(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        logger.info("- Last name: " + lastName);
        enterText(lastNameField, lastName);
        checkFieldValue(lastNameField, lastName);
    }

    public void enterEmail(String email) {
        logger.info("- Email: " + email);
        enterText(emailField, email);
        checkFieldValue(emailField, email);
    }

    public void enterMobileNum(String mobileNum) {
        logger.info("- Mobile Number: " + mobileNum);
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

    public void enterCurrentAddress(String address) {
        logger.info("- Current Address: " + address);
        enterText(addressField, address);
    }

    //gender option

    public void selectGender(String gender) {
        logger.info("- Gender: " + gender);
        selectGenderOption(gender);
    }

    public void selectGenderOption(String gender) {
        WebElement option = smallWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='" + gender + "']")));
        clickRadioBtn(option);
        boolean isSelected = driver.findElement(By.xpath
                ("//input[@type='radio' and @value='" + gender + "']")).isSelected();
        Assert.assertTrue(isSelected, "Gender is not selected correctly.");
    }

    //date of birth

    public void setDateOfBirth(String dateOfBirth) {
        logger.info("- Date of Birth: " + dateOfBirth);

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
        WebElement yearOption = smallWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//select[contains(@class,'year-select')]/option[@value='%d']", year))
        ));
        yearOption.click();
    }

    public void selectMonth(String month) {
        mediumWait.until(ExpectedConditions.elementToBeClickable(monthDropdown)).click();
        WebElement monthOption = smallWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//select[contains(@class,'month-select')]/option[text()='%s']", month))
        ));
        monthOption.click();
    }

    public void selectDay(String day) {
        WebElement dayElement = smallWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'react-datepicker__day')" +
                        " and not(contains(@class,'--outside-month')) " +
                        "and text()='" + day + "']")
        ));
        mediumWait.until(ExpectedConditions.elementToBeClickable(dayElement)).click();
    }

    //subject drop-down

    public void selectSubject(String subject) {
        logger.info("- Subjects: " + subject);
        scrollToElement(subjectsField);
        subjectsField.click();
        subjectsField.sendKeys(subject);
        selectOption(subject).click();
    }

    public WebElement selectOption(String subject) {
        String option = String.format("//div[contains(@id,'react-select') and text()='%s']", subject);
        return mediumWait.until(ExpectedConditions.elementToBeClickable(By.xpath(option)));
    }

    //hobbies - multiselect

    public void selectHobbies(String hobbies) {
        logger.info("- Hobbies: " + hobbies);
        String[] hobbiesArray = hobbies.split("\\s*и\\s*|,");

        for (String hobby : hobbiesArray) {
            hobby = hobby.trim();
            String xpath = String.format("//label[contains(text(), '%s')]", hobby);
            WebElement hobbyElement = smallWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            hobbyElement.click();
        }
    }

    //state and city

    public void selectCity(String city) {
        logger.info("- City: " + city);

        String state = getStateByCity(city);

        smallWait.until(ExpectedConditions.elementToBeClickable(stateDropdown)).click();
        clickElement(selectDropdownOption(state));

        smallWait.until(ExpectedConditions.elementToBeClickable(cityDropdown)).click();
        clickElement(selectDropdownOption(city));
    }

    public String getStateByCity(String city) {
        switch (city.trim().toLowerCase()) {
            case "delhi":
            case "gurgaon":
            case "noida":
                return "NCR";
            case "agra":
            case "lucknow":
            case "merrut":
                return "Uttar Pradesh";
            case "karnal":
            case "panipat":
                return "Haryana";
            case "jaipur":
            case "jaiselmer":
                return "Rajasthan";
            default:
                throw new IllegalArgumentException("Invalid city option " + city);
        }
    }

    private WebElement selectDropdownOption(String text) {
        String option = String.format("//div[contains(@id,'react-select') and text()='%s']", text);
        return smallWait.until(ExpectedConditions.elementToBeClickable(By.xpath(option)));
    }

    // submit btn
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

    /*public boolean isFieldMarkedAsError(String fieldName) {
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
                return isGenderError();
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
        WebElement field = smallWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
        String borderColor = field.getCssValue("border-color");
        return borderColor.equals("rgb(220, 53, 69)");
    }

    private boolean isGenderError() {
        try {
            WebElement genderGroup = driver.findElement(By.id("genterWrapper"));
            String classes = genderGroup.getAttribute("class");

            return classes.contains("is-invalid");
        } catch (Exception e) {
            return false;
        }
    }

    public void validateRequiredFieldsErrors() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(isFieldMarkedAsError("firstName"),
                "First Name should be in red when it is empty.");
        soft.assertTrue(isFieldMarkedAsError("lastName"),
                "Last Name should be in red when it is empty.");
        soft.assertTrue(isFieldMarkedAsError("gender"),
                "Gender should be in red when it is empty.");
        soft.assertTrue(isFieldMarkedAsError("mobile"),
                "Mobile Number should be in red when it is empty.");
        soft.assertAll();
    }*/
}
