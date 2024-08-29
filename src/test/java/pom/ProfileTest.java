package pom;

import api.user.User;
import api.user.UserClient;
import constants.Months;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.qameta.allure.Story;
import jdk.jfr.Name;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ScreenshotCreatorOnFail;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static sql.DatabaseOperations.getConfirmationCodeByUserId;
import static sql.DatabaseOperations.getIdByEmail;

public class ProfileTest {
    WebDriver driver;

    Faker faker = new Faker();
    String userName = faker.name().firstName()+faker.name().firstName();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password(6, 15, true, true, true) + "Ll3&";
    User user = new User();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String country = "Albania";
    String city = "Banaj";
    String day = String.valueOf(faker.number().numberBetween(1, 28));
    Months month = Months.values()[faker.random().nextInt(11)];
    String yearAdult = String.valueOf(faker.number().numberBetween(1991, 2009));
    String yearChild = String.valueOf(faker.number().numberBetween(2012, 2023));
    String aboutMe = faker.text().text(6,50);
    static String validFirstNameError = "First Name may contain A-Z a-z, А-Я а-я";
    static String validLastNameError = "Last Name may contain A-Z a-z, А-Я а-я";
    static String minLengthNameError = "Minimum number of characters 1";
    static String maxLengthNameError = "Maximum number of characters 50";
    static String validAboutMeError = "About me may contain 0-9, a-z, A-Z, ! \" # $ % & ' () * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~";
    static String maxLengthAboutMeError = "Maximum number of characters 200";

    static Stream<Arguments> positiveFirstNameDates() {
        return Stream.of(
                arguments("Александра", "Русские буквы"),
                arguments("Aleksandra", "Латинские буквы"),
                arguments("АлександраАлександраАлександраАлександраАлександра","длина 50 символов"),
                arguments("A","длина 1 символ")
        );
    }
    static Stream<Arguments> negativeFirstNameDates() {
        return Stream.of(
                arguments("Aleks andra",validFirstNameError,"Пробел"),
                arguments("Aleks-andra",validFirstNameError,"Дефис"),
                arguments("Aleksandra666",validFirstNameError,"Цифры"),
                arguments("Aleks:andra",validAboutMeError,"Двоеточие"),
                arguments("Aleksandra!",validFirstNameError,"Спецсимволы"),
                arguments("",minLengthNameError,"поле пустое"),
                arguments("AleksandraAleksandraAleksandraAleksandraAleksandraA",maxLengthNameError,"длина 51 символ")
        );
    }
    static Stream<Arguments> positiveLastNameDates() {
        return Stream.of(
                arguments("Александра", "Русские буквы"),
                arguments("Aleksandra", "Латинские буквы"),
                arguments("АлександраАлександраАлександраАлександраАлександра","длина 50 символов"),
                arguments("A","длина 1 символ")
        );
    }
    static Stream<Arguments> negativeLastNameDates() {
        return Stream.of(
                arguments("Aleks andra",validLastNameError,"Пробел"),
                arguments("Aleks-andra",validLastNameError,"Дефис"),
                arguments("Aleksandra666",validLastNameError,"Цифры"),
                arguments("Aleks:andra",validLastNameError,"Двоеточие"),
                arguments("Aleksandra!",validLastNameError,"Спецсимволы"),
                arguments("",minLengthNameError,"поле пустое"),
                arguments("AleksandraAleksandraAleksandraAleksandraAleksandraA",maxLengthNameError,"длина 51 символ")
        );
    }
    static Stream<Arguments> positiveAboutMeDates() {
        return Stream.of(
                arguments("Александра", "Русские буквы"),
                arguments("Aleksandra", "Латинские буквы"),
                arguments("Aleksandra666","Цифры"),
                arguments("!  # $ % & ' () * + -  @ ^ _ ","Спецсимволы- 1 часть"),
                arguments(", . / : ; < = > ?  [ \\ ]  ` { | } ~","Спецсимволы"),
                arguments("АлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександра","длина 200 символов"),
                arguments("A","длина 1 символ")
        );
    }
    static Stream<Arguments> negativeAboutMeDates() {
        return Stream.of(
                arguments("АлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраАлександраA",maxLengthAboutMeError,"длина 201 символ")
        );
    }

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
                .checkEnglish();
        // зарегистрировать тестового пользователя
        UserClient userClient = new UserClient();
        userClient.registrateAndConfirmUser(user.withUserName(userName).withEmail(email).withPassword(password));
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputЕmailField(user.getEmail()).inputPasswordField(user.getPassword()).clickSigninButton();
    }

        @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Fill out profile (основной сценарий- пользователь старше 13 лет заполняет все поля профиля)")
    public void fillAllFieldsProfileTest() {
        String expected = "Your settings are saved!";
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickMyProfileTab()
                .clickProfileSettingsButton()
                .inputFirstNameField(firstName)
                .inputLastNameField(lastName)
                .selectCountryFromDropdown(country)
                .selectCityFromDropdown(city)
                .selectDateOfBirth(String.valueOf(day), month, yearAdult)
                .inputAboutMeField(aboutMe)
                        .clickSaveChangesButton();
        String actual = profilePage.getMessageAboutSave();
        assertEquals(expected,actual,"Не удалось сохранить настройки профиля");
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
    @Test
    @DisplayName("Fill out profile (основной сценарий- младше 13 лет заполняет обязательные поля и Date of birth)")
    public void userUnder13FillProfileTest() {
        String expected = "A user under 13 cannot create a profile. Privacy Policy";
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickMyProfileTab()
                .clickProfileSettingsButton()
                .inputFirstNameField(firstName)
                .inputLastNameField(lastName)
                .selectDateOfBirth(String.valueOf(day), month, yearChild);
        String actual = profilePage.getErrorMessageForUserUnder13();
        assertEquals(expected,actual,"Не появилось сообщение об ошибке");
    }
    @Test
    @DisplayName("Fill out profile (основной сценарий- пользователь оставляет все поля пустыми)")
    public void leaveEmptyFieldsProfileTest() {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickMyProfileTab()
                .clickProfileSettingsButton()
                .inputFirstNameField("")
                .inputLastNameField("");
        assertFalse(profilePage.isEnabledChangesButton(),"Кнопка \"Save Changes\" активна");
    }
    @Tag("Positive")
    @Story("Profile fill out")
    @DisplayName("Positive. FirstName")
    @Name("Positive. FirstName")
    @ParameterizedTest
    @MethodSource("positiveFirstNameDates")
    public void checkPositiveFirstNameDates(String validFirstName, String description) {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickProfileSettingsButton()
                .inputFirstNameField(validFirstName);
        assertFalse(profilePage.isErrorMessageFirstName(), "Ошибка валидации при вводе " + description);
    }

    @Tag("Negative")
    @Story("Profile fill out")
    @DisplayName("Negative. FirstName")
    @ParameterizedTest
    @MethodSource("negativeFirstNameDates")
    public void checkNegativeFirstNameDates(String invalidFirstName, String errorMessage, String description) {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickProfileSettingsButton()
                .inputLastNameField(invalidFirstName);
        assertEquals(profilePage.getErrorMessageFirstName(),errorMessage, "Не появляется ошибка валидации при " + description);
    }

    @Tag("Positive")
    @Story("Profile fill out")
    @DisplayName("Positive. LastName")
    @ParameterizedTest
    @MethodSource("positiveLastNameDates")
    public void checkPositiveLastNameDates(String validLastName, String description) {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickProfileSettingsButton()
                .inputLastNameField(validLastName);
        assertFalse(profilePage.isErrorMessageLastName(), "Ошибка валидации при вводе " + description);
    }

    @Tag("Negative")
    @Story("Profile fill out")
    @DisplayName("Negative. LastName")
    @ParameterizedTest
    @MethodSource("negativeLastNameDates")
    public void checkNegativeLastNameDates(String invalidLastName, String errorMessage, String description) {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickProfileSettingsButton()
                .inputLastNameField(invalidLastName);
        assertEquals(profilePage.getErrorMessageLastName(),errorMessage, "Не появляется ошибка валидации при " + description);
    }
    @Tag("Positive")
    @Story("Profile fill out")
    @DisplayName("Positive. AboutMeDates")
    @ParameterizedTest
    @MethodSource("positiveAboutMeDates")
    public void checkPositiveAboutMeDates(String validAboutMe, String description) {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickProfileSettingsButton()
                        .inputAboutMeField(validAboutMe);
        assertFalse(profilePage.isErrorMessageAboutMe(), "Ошибка валидации при вводе " + description);
    }

    @Tag("Negative")
    @Story("Profile fill out")
    @DisplayName("Negative. AboutMeDates")
    @ParameterizedTest
    @MethodSource("negativeAboutMeDates")
    public void checkNegativeAboutMeDates(String invalidAboutMe, String errorMessage, String description) {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickProfileSettingsButton()
                .inputAboutMeField(invalidAboutMe);
        assertEquals(profilePage.getErrorMessageAboutMe(),errorMessage, "Не появляется ошибка валидации при " + description);
    }

}

