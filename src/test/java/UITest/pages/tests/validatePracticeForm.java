package UITest.pages.tests;

import org.testng.annotations.Test;

public class validatePracticeForm extends BaseTestMethods {

    @Test(dataProvider = "getData", dataProviderClass = Dataproviders.class, priority = 1)
    public void validatePracticeFormWithCorrectData(String firstName, String lastName, String email, String gender,
                                    String mobileNum, String dateOfBirth, String subject,
                                    String hobbies, String address, String city) {

        System.out.println("1. Load Demoga website and navigating to Practice form");
        homePage.navigateTo();

        System.out.println("2. Filling in the Practice form");
        formsPage.enterDataInPracticeForm(firstName, lastName, email, gender,
                mobileNum, dateOfBirth, subject,
                hobbies, address, city);

        formsPage.clickSubmitBtn();

        System.out.println("3. Validating submitted data");
        validatePage.checkModal();
        validatePage.validateModalData(firstName, lastName, email, gender,
                mobileNum, dateOfBirth, subject,
                hobbies, address, city);

        System.out.println("4. Close the modal");
        validatePage.closeModal();
    }

    @Test(dataProvider = "getDataIncorrect", dataProviderClass = Dataproviders.class, priority = 2)
    public void validatePracticeFormWithIncorrectData(String firstName, String lastName, String email, String gender,
                                                    String mobileNum, String dateOfBirth, String subject,
                                                    String hobbies, String address, String city) {

        System.out.println("1. Load Demoga website and navigating to Practice form");
        homePage.navigateTo();

        System.out.println("2. Filling in the Practice form");
        formsPage.enterDataInPracticeForm(firstName, lastName, email, gender,
                mobileNum, dateOfBirth, subject,
                hobbies, address, city);

        formsPage.clickSubmitBtn();

        System.out.println("3. Validating submitted data");
        validatePage.checkModal();
        validatePage.validateModalData(firstName, lastName, email, gender,
                mobileNum, dateOfBirth, subject,
                hobbies, address, city);

        System.out.println("4. Close the modal");
        validatePage.closeModal();
    }

    @Test(priority = 3)
    public void validatePracticeFormRequiredFields() {

        System.out.println("1. Load Demoga website and navigating to Practice form");
        homePage.navigateTo();

        System.out.println("2. Clicking Submit with empty fields.");
        formsPage.clickSubmitBtn();

        System.out.println("4. Validating required fields have error");
        formsPage.validateRequiredFieldsErrors();
    }

}

