import io.github.bonigarcia.wdm.WebDriverManager;
import io.qase.api.annotation.CaseTitle;
import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    WebDriver driver;
    String email = "test_svp@gmail.com";
    String password = "pass01A!";

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
                .clickLoginButton();

    }

    @AfterEach
    void teardown() {
        driver.quit();
    }


    @Test
    @QaseId(201)
    @CaseTitle("Log In (основной сценарий)")
    public void loginTest() throws InterruptedException {

        String expected = "new_userA";
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        loginPage
                .inputЕmailField(email)
                .inputPasswordField(password)
                .clickSigninButton();
                Thread.sleep(500);
        assertEquals(expected, profilePage.getFillProfileHeader(), "Не удалось авторизоваться");

    }
}
