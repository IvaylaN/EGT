package UI_test.tests;

import data.Dataproviders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class validatePracticeForm extends BaseTestMethods {

    private static final Logger logger = LoggerFactory.getLogger(validatePracticeForm.class);

    @Test(dataProvider = "getData", dataProviderClass = Dataproviders.class, priority = 1)
    public void validatePracticeFormWithCorrectData(String firstName, String lastName, String email, String gender,
                                                    String mobileNum, String dateOfBirth, String subject,
                                                    String hobbies, String address, String city) {

        logger.info("1. Load Demoga website and navigating to Practice form");
        homePage.navigateTo();

        logger.info("2. Filling in the Practice form");
        formsPage.enterDataInPracticeForm(firstName, lastName, email, gender,
                mobileNum, dateOfBirth, subject,
                hobbies, address, city);

        formsPage.clickSubmitBtn();

        logger.info("3. Validating submitted data");
        validatePage.checkModal();
        validatePage.validateModalData(firstName, lastName, email, gender,
                mobileNum, dateOfBirth, subject,
                hobbies, address, city);

        logger.info("4. Close the modal");
        validatePage.closeModal();
    }

    @Test(dataProvider = "getDataIncorrect", dataProviderClass = Dataproviders.class, priority = 2)
    public void validatePracticeFormWithIncorrectData(String firstName, String lastName, String email, String gender,
                                                      String mobileNum, String dateOfBirth, String subject,
                                                      String hobbies, String address, String city) {

        logger.info("1. Load Demoga website and navigating to Practice form");
        homePage.navigateTo();

        logger.info("2. Filling in the Practice form.");
        try {
            formsPage.enterDataInPracticeForm(firstName, lastName, email, gender,
                    mobileNum, dateOfBirth, subject, hobbies, address, city);
        } catch (IllegalArgumentException e) {
            logger.info("Invalid city detected: " + city);
            return;

        }
        Assert.fail("Expected error for invalid city was not thrown.");
    }

    @Test(priority = 3)
    public void validatePracticeWithoutRequiredFields() {

        logger.info("1. Load Demoga website and navigating to Practice form.");
        homePage.navigateTo();

        logger.info("2. Clicking Submit with empty fields.");
        formsPage.clickSubmitBtn();

        logger.info("3. Validate modal is not opened.");
        Assert.assertFalse(validatePage.isModalOpen(),
                "Modal should not open when fields are empty.");
    }
}

