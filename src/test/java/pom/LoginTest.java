package pom;

import api.user.User;
import api.user.UserClient;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ScreenshotCreatorOnFail;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static sql.DatabaseOperations.*;


@ExtendWith(ScreenshotCreatorOnFail.class)
public class LoginTest {

    WebDriver driver;
    Faker faker = new Faker();
    String userName = faker.name().firstName()+faker.name().firstName();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password(6, 15, true, true, true) + "Ll3&";
    User user = new User();
    String confirmationCode = null;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        ScreenshotCreatorOnFail.setDriver(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage
                .clickLanguageCheckbox()
                .checkEnglish()
                .clickLoginButton();
        // зарегистрировать тестового пользователя
        UserClient userClient = new UserClient();
        userClient.setBaseURL();
        userClient.registrateUser(user.withUserName(userName).withEmail(email).withPassword(password));
        confirmationCode = getConfirmationCodeByUserId(getIdByEmail(email));
        userClient.confirmRegistrationUser(confirmationCode);
    }

    @AfterEach
    void teardown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
   @Description("Log In (основной сценарий)")
    void loginTest()  {

        String expected = userName;
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        loginPage
                .inputЕmailField(email)
                .inputPasswordField(password)
                .clickSigninButton();
        assertEquals(expected, profilePage.getFillProfileHeader(), "Не удалось авторизоваться");
    }

    @Test
    @Description("Log In (Альтернативный сценарий №1 (ввод неверного email))")
    void loginIncorrectEmailTest() throws InterruptedException {

        String expected = "The email or password are incorrect. Try again please";
        LoginPage loginPage = new LoginPage(driver);
        try {
            loginPage
                    .inputЕmailField("d" + email)
                    .inputPasswordField(password)
                    .clickSigninButton();
            assertEquals(expected, loginPage.getEmailFieldErrorMessageText(), "Текст ошибки не соответствует ожидаемому");
        } catch (NoSuchElementException e) {
            fail("Не появилось сообщение об ошибке");
        }

    }
    @Test
    @Description("Log In (Альтернативный сценарий №1 (ввод неверного password))")
    void loginIncorrectPasswordTest() {

        String expected = "The email or password are incorrect. Try again please";
        LoginPage loginPage = new LoginPage(driver);
        try {
            loginPage
                    .inputЕmailField(email)
                    .inputPasswordField(password + "d")
                    .clickSigninButton();
            assertEquals(expected, loginPage.getPasswordFieldErrorMessageText(), "Текст ошибки не соответствует ожидаемому");
        } catch (NoSuchElementException e) {
            fail("Не появилось сообщение об ошибке");
        }

    }
}
