package UITest.pages.tests;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

import java.text.SimpleDateFormat;

public class Dataproviders {

    @DataProvider(name = "getData")
    public Object[][] getData() {
        return new Object[][]{
                {"Ivan", "Petrov", "ivan.petrov@example.com", "Male", "0888123456",
                        "01 Jan 1990", "Maths", "Sports и Reading",
                        "123 Test Street", "Delhi"},
        };
    }

    @DataProvider(name = "getDataIncorrect")
    public Object[][] getDataIncorrect() {
        return new Object[][]{
                {"Ivan", "Petrov", "ivan.petrov@example.com", "Male", "0888123456",
                        "01 Jan 1990", "Maths", "Sports и Reading",
                        "123 Test Street", "Sofia"}
        };
    }

    private final Faker faker = new Faker();

    @DataProvider(name = "getDataWithFaker")
    public Object[][] getDataWithFaker() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", java.util.Locale.ENGLISH);

        return new Object[][]{
                {
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.options().option("Male", "Female", "Other"),
                        faker.number().digits(10),
                        sdf.format(faker.date().birthday(20, 40)),
                        faker.options().option("Maths", "English", "Biology",
                                "Chemistry", "History", "Physics", "Commerce", "Civiks"),
                        faker.options().option("Sports", "Reading", "Music"),
                        faker.address().streetAddress(),
                        faker.options().option("Delhi", "Gurgaon", "Noida")
                }
        };
    }
}