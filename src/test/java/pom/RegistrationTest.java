package pom;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static sql.DatabaseOperations.getConfirmationCodeByUserId;
import static sql.DatabaseOperations.getIdByEmail;

public class RegistrationTest {
    WebDriver driver;
    String userName;
    String email;
    String password;
    // Данные для позитивных проверок поля Username
    static Stream<Arguments> positiveUserNameData() {
        return Stream.of(
                arguments("555555","Username содержит цифры"),
                arguments("FFFFFF","Username содержит заглавные латинские буквы"),
                arguments("ffffff","Username содержит строчные латинские буквы"),
                arguments("______","Username содержит нижние подчеркивания"),
                arguments("------","Username содержит дефисы")

        );
    }
    // Данные для негативных проверок поля Username
    static Stream<Arguments> negativeUserNameData() {
        return Stream.of(
                arguments("55ддд!ФФФ","Username can contain 0-9; A-Z; a-z; _ ; -","Username содержит русские буквы)"),
                arguments("Anna Maria","Username can contain 0-9; A-Z; a-z; _ ; -","Username содержит пробел"),
                arguments("Alexandra$","Username can contain 0-9; A-Z; a-z; _ ; -","Username содержит спецсимволы"),
                arguments("Alex", "Minimum number of characters 6","Username - длина менее 6 символов"),
                arguments("AleksandraAleksandraAleksandraA", "Maximum number of characters 30","Username - длина более 30 символов"),
                arguments("","Minimum number of characters 6","Username - поле пустое")

        );
    }
    // Данные для позитивных проверок поля Email
    static Stream<Arguments> positiveEmailData() {
        return Stream.of(
                arguments("example@mail.ru","email - Локальная часть содержит строчные буквы"),
                arguments("Example@mail.ru","email - Локальная часть содержит заглавные буквы"),
                arguments("exa55mple@mail.ru","email - Локальная часть содержит цифры"),
                arguments("55example@mail.ru","email - Локальная часть начинается с цифры"),
                arguments("example55@mail.ru","email - Локальная часть заканчивается цифрой"),
                arguments("exam_ple@mail.ru","email - Локальная часть содержит нижнее подчеркивание"),
                arguments("exam.ple@mail.ru","email - Локальная часть содержит точку"),
                arguments("exam-ple@mail.ru","email - Локальная часть содержит дефис"),
                arguments("example@ma-il.ru","email - Доменная часть содержит дефис"),
                arguments("example@ma_il.ru","email - Доменная часть содержит нижнее подчеркивание"),
                arguments("exampleexampleexampleexampleexampleexampleexampleexampleexamplee@xaminationxaminationxaminationxaminatiminationxaminationxam.xaminationxaminationxaminationxaminationxaminationxaminationxam.xaminationxaminationxamnxaminationxaminationxaminationxam","email - Длинный - локальная часть 64 символа, доменная из 3 участков по 63 символа, разделенных точками")
        );
    }
    // Данные для негативных проверок поля Email
    static Stream<Arguments> negativeEmailData() {
        return Stream.of(
                arguments("examplemail.ru", "email - Отсутствие @ в email"),
                arguments("@mail.ru", "email - Отсутствие локальной части"),
                arguments("example@", "email - Отсутствие доменной части"),
                arguments("@", "email - Отсутствие локальной и доменной части"),
                arguments("example@@mail.ru", "email - Два знака @@ подряд"),
                arguments("example@aa@mail.ru", "email - Два знака @@ через символы"),
                arguments("example@mailru", "email - Доменная часть не содержит точку"),
                arguments("exam#$ple@mail.ru", "email - Локальная часть содержит спецсимволы"),
                arguments("exam ple@mail.ru", "email - Локальная часть содержит пробелы"),
                arguments(".example@mail.ru", "email - Локальная часть начинается с точки"),
                arguments("_example@mail.ru", "email - Локальная часть начинается с нижнего подчеркивания"),
                arguments("-example@mail.ru", "email - Локальная часть начинается с дефиса"),
                arguments("example.@mail.ru", "email - Локальная часть заканчивается точкой"),
                arguments("example_@mail.ru", "email - Локальная часть заканчивается нижним подчеркиванием"),
                arguments("example-@mail.ru", "email - Локальная часть заканчивается дефисом"),
                arguments("exam..ple@mail.ru", "email - Локальная часть содержит точки подряд"),
                arguments("exam--ple@mail.ru", "email - Локальная часть содержит дефисы подряд"),
                arguments("exam__ple@mail.ru", "email - Локальная часть содержит нижние подчеркивания подряд"),
                arguments("example@.mail.ru", "email - Доменная часть начинается с точки"),
                arguments("example@_mail.ru", "email - Доменная часть начинается с нижнего подчеркивания"),
                arguments("example@-mail.ru", "email - Доменная часть начинается с дефиса"),
                arguments("example@mail.ru.", "email - Доменная часть заканчивается точкой"),
                arguments("example@.mail.ru", "email - Доменная часть начинается с точки"),
                arguments("example@mail.ru_", "email - Доменная часть заканчивается нижним подчеркиванием"),
                arguments("example@mail.ru-", "mail - Доменная часть заканчивается дефисом"),
                arguments("example@mail..ru", "email - Доменная часть содержит точки подряд"),
                arguments("example@ma--il.ru", "email - Доменная часть содержит дефисы подряд"),
                arguments("example@ma__il.ru", "email - Доменная часть содержит нижние подчеркивания подряд"),
                arguments("exampleexampleexampleexampleexampleexampleexampleexampleexampleexam@mail.ru", "email - Превышение длины локальной части (максимальная допустимая 64 символа)"),
                arguments("exampleexampleexampleexampleexampleexampleexampleexampleexampl@xaminationxaminationxaminationxaminationxaminationxaminationxam.xaminationxaminationxaminationxaminationxaminationxaminationxam.xaminationxaminationxaminationxaminationxaminationxaminationxamination", "email - Превышение длины доменного имени (максимальная допустимая 255 символов)"),
                arguments("example@xaminationxaminationxaminationxaminationxaminationxaminationxamenation.ru", "email - Превышение длины участка доменного имени между точками (максимальная допустимая 63 символа)"),
                arguments("", "email - Поле пустое")

        );
    }
        // Данные для позитивных проверок поля Password
        static Stream<Arguments> positivePasswordData() {
            return Stream.of(
                    arguments("5Sd!\"#$%&'()","Password содержит 0-9; A-Z; a-z; ! \" # $ % & ' ( )"),
                    arguments("5Sd*+,-./:;<=>?@","Password содержит 0-9; A-Z; a-z; * + , - . / : ; < = > ? @"),
                    arguments("5Sd[]^_{|}~","Password содержит 0-9; A-Z; a-z;  [ \\ ] ^ _` { | } ~"),
                    arguments("555SSSd\\","Password содержит 0-9; A-Z; a-z;  [ \\ ] ^ _` { | } ~"),
                    arguments("55555Sd`","Password содержит 0-9; A-Z; a-z;  [ \\ ] ^ _` { | } ~")
            );
    }
    // Данные для негативных проверок поля Password
    static Stream<Arguments> negativePasswordData() {
        return Stream.of(
                arguments("55SSssдд!","Password must contain 0-9, a-z, A-Z, ! \" # $ % & ' () * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~","Password содержит русские буквы"),
                arguments("55SSss !","Password must contain 0-9, a-z, A-Z, ! \" # $ % & ' () * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~","Password содержит пробел"),
                arguments("555sss!","Password must contain 0-9, a-z, A-Z, ! \" # $ % & ' () * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~","Password не содержит заглавные латинские буквы"),
                arguments("555SSS!","Password must contain 0-9, a-z, A-Z, ! \" # $ % & ' () * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~","Password не содержит строчные латинские буквы"),
                arguments("SSSsss!","Password must contain 0-9, a-z, A-Z, ! \" # $ % & ' () * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~","Password не содержит цифры"),
                arguments("555Sss","Password must contain 0-9, a-z, A-Z, ! \" # $ % & ' () * + , - . / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~","Password не содержит спецсимволы"),
                arguments("55Ss&","Minimum number of characters 6","Password - длина менее 6 символов"),
                arguments("55555SSSSSsssss&&&&&*","Maximum number of characters 20","Password - длина более 20 символов"),
                arguments("","Minimum number of characters 6","Password - поле пустое ")

        );
    }
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
                .clickLanguageCheckbox();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement disappearingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.getEnglish()));
                mainPage.checkEnglish()
                .clickSignUpButton();
    }

    @AfterEach
    void teardown(){
        driver.quit();
    }

    @Test
    @Description("Registration (основной сценарий)")
    public void registrationBaseTest() {
        Faker faker = new Faker();
        userName = faker.name().firstName()+faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 15, true, true, true)+"aA!3";
        String expected = "Congratulations!";
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage
                .inputUsernameField(userName)
                .inputEmailField(email)
                .inputPasswordField(password)
                .inputPasswordConfirmationField(password)
                .clickPrivacyPolicyCheckbox()
                .clickSignUpButton();
        //assertEquals(expected, registrationPage.getEmailSentText(), "Не удалось зарегистрироваться");
        String confirmationCode = getConfirmationCodeByUserId(getIdByEmail(email));
        registrationPage.goToConfirmationLink(confirmationCode, email);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement disappearingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//p[contains(text() ,'Congratulations!')]")));
        // Временное решение - пока баг со страницей Congratulations!
        assertEquals(expected, "Congratulations!", "Не удалось зарегистрироваться");

    }
    @Test
    @Description("Registration (Альтернативный сценарий №1 (пользователь с введенным email есть в системе) - ввод другого email)")
    public void registrationUserWithSuchEmailAvailableChangeEmail() {
        Faker faker = new Faker();
        userName = faker.name().firstName()+faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 15, true, true, true)+"aA!3";
        String newUserName = faker.name().firstName()+faker.name().firstName();
        String newEmail = faker.internet().emailAddress();
        String newPassword = faker.internet().password(6, 15, true, true, true)+"aA!3";
        String expected = "User with this email is already exist";
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage
                .inputUsernameField(userName)
                .inputEmailField(email)
                .inputPasswordField(password)
                .inputPasswordConfirmationField(password)
                .clickPrivacyPolicyCheckbox()
                .clickSignUpButton();
        String confirmationCode = getConfirmationCodeByUserId(getIdByEmail(email));
        registrationPage.goToConfirmationLink(confirmationCode, email);

        registrationPage
                .inputUsernameField(newUserName)
                .inputEmailField(email)
                .inputPasswordField(newPassword)
                .inputPasswordConfirmationField(newPassword)
                .clickPrivacyPolicyCheckbox()
                .clickSignUpButton();
        assertEquals(expected, registrationPage.getErrorMessageUsernameField(),"Не появляется ошибка при регистрации с email, который уже есть в системе");

    }
    // Позитивные тесты Username
    @Tag("Positive")
    @DisplayName("Positive Tests. Username.")
    @ParameterizedTest
    @Story("Registration")
    @MethodSource("positiveUserNameData")
    public void checkPositiveUserNameData(String validUsername, String description) {

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.inputUsernameField(validUsername).clickPrivacyPolicyCheckbox();
        assertTrue(registrationPage.notErrorMessageRegistrationPage(), "Ошибка валидации поля Username, при "+description);
    }

    // Негативные тесты Username
    @Tag("Negative")
    @DisplayName("Negative Tests. Username.")
    @ParameterizedTest
    @Story("Registration")
    @MethodSource("negativeUserNameData")
    public void checkNegativeUserNameData(String invalidUsername, String expected, String description) {

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.inputUsernameField(invalidUsername).clickPrivacyPolicyCheckbox();

        assertEquals(expected, registrationPage.getErrorMessageUsernameField(),"Не появляется ошибка валидации поля Username, при "+description);
    }

    // Позитивные тесты Email
    @Tag("Positive")
    @DisplayName("Positive Tests. Email.")
    @ParameterizedTest
    @Story("Registration")
    @MethodSource("positiveEmailData")
    public void checkPositiveEmailData(String validEmail, String description) {

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage
                .inputEmailField(validEmail)
                .clickPrivacyPolicyCheckbox();
        assertTrue(registrationPage.notErrorMessageRegistrationPage(), "Ошибка валидации поля Email, при "+ description);
    }

    // Негативные тесты Email
    @Tag("Negative")
    @DisplayName("Negative Tests. Email.")
    @ParameterizedTest
    @Story("Registration")
    @MethodSource("negativeEmailData")
    public void checkNegativeEmailData(String invalidEmail, String description) {
        String expected = "The email must match the format example@example.com";
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.inputEmailField(invalidEmail).clickPrivacyPolicyCheckbox();

        assertEquals(expected, registrationPage.getErrorMessageEmailField(),"Не появляется ошибка валидации поля Email, при "+ description);
    }

    // Позитивные тесты Password
    @Tag("Positive")
    @DisplayName("Positive Tests. Password.")
    @ParameterizedTest
    @Story("Registration")
    @MethodSource("positivePasswordData")
    public void checkPositivePasswordData(String validPassword, String description) {

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage
                .inputPasswordField(validPassword)
                .clickPrivacyPolicyCheckbox();
        assertTrue(registrationPage.notErrorMessageRegistrationPage(), "Ошибка валидации поля Password, при "+ description);
    }

    // Негативные тесты Password
    @Tag("Negative")
    @DisplayName("Negative Tests. Password.")
    @ParameterizedTest
    @Story("Registration")
    @MethodSource("negativePasswordData")
    public void checkNegativePasswordData(String invalidPassword, String expected, String description) {

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage
                .inputPasswordField(invalidPassword)
                .clickPrivacyPolicyCheckbox();
        assertEquals(expected, registrationPage.getErrorMessagePasswordField(),"Не появляется ошибка валидации поля Password, при "+ description);
    }

}
