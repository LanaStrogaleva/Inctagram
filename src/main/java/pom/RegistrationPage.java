package pom;

import io.qase.api.annotation.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private final WebDriver driver;
    public RegistrationPage (WebDriver driver) {
        this.driver = driver;
    }
    private By signUpHeader = By.xpath(".//h1[text() = 'Sign Up']");
    private By googleLink = By.xpath(".//div[contains(@class, 'other')]/button");
    private By githubLink = By.xpath(".//a[contains(@href, 'github')]");
    private By usernameField = By.xpath(".//input[@name ='username']");
    private By emailField = By.xpath(".//input[@name = 'email']");
    private By passwordField = By.xpath(".//input[@name ='password']");
    private By passwordConfirmationField = By.xpath(".//input[@name ='passwordConfirm']");
    private By privacyPolicyCheckbox = By.xpath(".//button[@role='checkbox']");
    private By signUpButton = By.xpath(".//button[text()='Sign Up']");
    private By signInLink = By.xpath(".//a[contains(text(), 'Sign In')]");
    private By emailSentHeader = By.xpath(".//h2");

    @Step("Кликнуть на ссылку Google")
    public RegistrationPage clickGoogleLink() {
        driver.findElement(googleLink).click();
        return  this;
    }

    @Step("Кликнуть на ссылку GitHub")
    public RegistrationPage clickGitHubLink() {
        driver.findElement(githubLink).click();
        return  this;
    }

    @Step("Ввести данные в поле Username")
    public RegistrationPage inputUsernameField(String username) {
        driver.findElement(usernameField).sendKeys(username);
        return this;
    }

    @Step("Ввести данные в поле Email")
    public RegistrationPage inputEmailField(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Ввести данные в поле Password")
    public RegistrationPage inputPasswordField(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return  this;
    }

    @Step("Ввести данные в поле Password Confirmation")
    public RegistrationPage inputPasswordConfirmationField(String passwordConfirm) {
        driver.findElement(passwordConfirmationField).sendKeys(passwordConfirm);
        return this;
    }

    @Step ("Кликнуть на чекбокс Privacy Policy")
    public RegistrationPage clickPrivacyPolicyCheckbox(){
        driver.findElement(privacyPolicyCheckbox).click();
        return  this;
    }
    @Step("Кликнуть на кнопку Sign Up")
    public RegistrationPage clickSignUpButton() {
        driver.findElement(signUpButton).click();
        return  this;
    }
    @Step("Кликнуть на ссылку Sign In")
    public RegistrationPage clickSignInLink() {
        driver.findElement(signInLink).click();
        return  this;
    }

    public String getEmailSentText() {
        return driver.findElement(emailSentHeader).getText();
    }
}
