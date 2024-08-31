package pom;

import api.profile.ProfileClient;
import api.user.User;
import api.user.UserClient;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ScreenshotCreatorOnFail;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfTest {
    WebDriver driver;
    String accessToken;

    Faker faker = new Faker();
    String userName = faker.name().firstName()+faker.name().firstName();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password(6, 15, true, true, true) + "Ll3&";
    User user = new User();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {


        // зарегистрировать тестового пользователя
        UserClient userClient = new UserClient();
        accessToken= userClient.registrateAndGetAccessToken(user.withUserName(userName).withEmail(email).withPassword(password));
        ProfileClient profileClient = new ProfileClient();
        System.out.println("статус код входа в профиль: " + profileClient.getUserProfileWithAuth(accessToken).getStatusCode());

        // UI тест - переход на страницу профиля
        driver = new ChromeDriver();
        driver.get("https://funny-inctagram.site/profile/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().addCookie(new Cookie("SL_G_WPT_TO","en"));
        // Передача accessToken в localStorage
//        String accessToken = "your_access_token_value_here";
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.localStorage.setItem('Bearer', arguments[0]);", accessToken);

        // Обновление страницы для применения изменений
        driver.navigate().refresh();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Fill out profile (основной сценарий- пользователь старше 13 лет заполняет только обязательные поля профиля)")
    public void fillMandatoryFieldsProfileTest() {
        String expected = "Your settings are saved!";
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickMyProfileTab()
                .clickProfileSettingsButton()
                .inputFirstNameField(firstName)
                .inputLastNameField(lastName)
                .clickSaveChangesButton();
        String actual = profilePage.getMessageAboutSave();
        assertEquals(expected,actual,"Не удалось сохранить настройки профиля");
    }

}
