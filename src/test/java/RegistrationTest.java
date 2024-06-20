import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qase.api.annotation.QaseId;
import io.qase.api.annotation.QaseTitle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.LoginPage;
import pom.MainPage;
import pom.ProfilePage;
import pom.RegistrationPage;
import static sql.DatabaseOperations.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {
    WebDriver driver;
    String username;
    String email;
    String password;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage
                .clickLanguageCheckbox()
                .checkEnglish()
                .clickSignUpButton();
    }

    @AfterEach
    void teardown(){
        driver.quit();
    }

    @Test
    @QaseId(221)
    @QaseTitle("Registration (основной сценарий)")
    public void registrationBaseTest() {
        Faker faker = new Faker();
        username = faker.internet().password(6, 20, true, false, false);
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 20, true, true, true);
        String expected = "Email sent";
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage
                .inputUsernameField(username)
                .inputEmailField(email)
                .inputPasswordField(password)
                .inputPasswordConfirmationField(password)
                .clickPrivacyPolicyCheckbox()
                .clickSignUpButton();
        updateIsConfirmed(getIdByEmail(email), true);
        assertEquals(expected, registrationPage.getEmailSentText(), "Не удалось зарегистрироваться");
    }
    @Test
    public void regAndLoginTest() throws InterruptedException {
        Faker faker = new Faker();
        username = faker.internet().password(6, 20, true, false, false);
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 20, true, true, true);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.registrationWithValidCredentials(username, email, password);
        updateIsConfirmed(getIdByEmail(email), true);

        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        loginPage
                .inputЕmailField(email)
                .inputPasswordField(password)
                .clickSigninButton();
        Thread.sleep(500);
        assertEquals(username, profilePage.getFillProfileHeader(), "Не удалось авторизоваться");

    }

}
