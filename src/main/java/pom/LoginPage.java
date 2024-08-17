package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private static final String URL = "https://funny-inctagram.site/";

    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    private By signinHeader = By.xpath(".//h1[text()= 'Sign In']");
    private By googleLink = By.xpath(".//a[contains(@href, 'google')]");
    private By githubLink = By.xpath(".//a[contains(@href, 'github')]");
    private By emailField = By.xpath(".//input[@name = 'email']");
    private By passwordField = By.xpath(".//input[@name ='password']");
    private By forgotPasswordLink = By.xpath(".//a[@href =  '/forgot-password']");
    private By signinButton = By.xpath(".//form/button");
    private By signupLink = By.xpath(".//a[contains(text(), 'Sign Up')]");

    @Step("Перейти на страницу профиля")
    public void openProfile() {
        driver.get(URL + "profile");
    }

    public String getSigninHeaderText() {

        return driver.findElement(signinHeader).getText();
    }
    @Step("Ввести данные в поле Еmail")
    public LoginPage inputЕmailField(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }
    @Step("Ввести данные в поле Password")
    public LoginPage inputPasswordField(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }
    @Step("Кликнуть по кнопке Sign In")
    public void clickSigninButton() {
        driver.findElement(signinButton).click();
    }

    @Step("Кликнуть по ссылке Sign Up")
    public void clickSignupButton() {
        driver.findElement(signupLink).click();
    }
    @Step("Кликнуть по ссылке Fogot password")
    public void clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
    }
}

