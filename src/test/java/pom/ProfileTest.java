package pom;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTest {
    WebDriver driver;


    @BeforeEach
    public void setUp() {
//        // Запуск BrowserMob Proxy
//        proxy = new BrowserMobProxyServer();
//        proxy.start(0);
//
//        // Настройка перехвата запросов
//        proxy.addRequestFilter((request, contents, messageInfo) -> {
//            if (messageInfo.getOriginalUrl().contains("/api/v1/user/me")) {
//                // Изменение эндпоинта запроса
//                String newUrl = messageInfo.getOriginalUrl().replace("/api/v1/user/me", "/api/v1/user/profile");
//                request.setUri(newUrl);
//            }
//            return null;
//        });
//
//        // Настройка прокси для WebDriver
//        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
//        ChromeOptions options = new ChromeOptions();
//        options.setProxy(seleniumProxy);
//        // Игнорирование ошибок SSL
//        options.setAcceptInsecureCerts(true);

//        // Настройка WebDriverManager для управления драйвером Chrome
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver(options);
//        driver.manage().window().maximize();
   }

//    @Test
//    public void testAccessTokenAndRedirectToProfile() {
//        MainPage mainPage = new MainPage(driver);
//        // Установите URL вашего сайта
//        mainPage.open();
//
//        mainPage.clickLanguageCheckbox().checkEnglish().clickLoginButton();



        // Установите accessToken в localStorage
//        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI0ZTQzZDU5MS1lZmNkLTQ2NWYtYWY2MC1lODhlNzdjMTdiYWMiLCJpYXQiOjE3MTkwNzU1NTQsImV4cCI6MTcxOTA3NjU1NH0.hRG6MtHUbqnjBwNzpm3EZ6cvnRjsoxkEYCYQOuFzFng";
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("localStorage.setItem('accessToken', '" + accessToken + "');");
//        String refreshToken = accessToken;
//        JavascriptExecutor js1 = (JavascriptExecutor) driver;
//        js1.executeScript("document.cookie = 'refreshToken=" + refreshToken + "; path=/; secure;';");

        // Перейдите на страницу профиля
//        mainPage.clickMyprofileTab();
//        driver.get("https://funny-inctagram.site/profile");

//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.inputЕmailField("kyung.ondricka@hotmail.com").inputPasswordField("&x&*ku8Ca!F1").clickSigninButton();
        //mainPage.clickMyprofileTab();



//
//         Проверьте, что мы на странице профиля
//        assertTrue(driver.getCurrentUrl().contains("/profile"));

//    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

