package pom;

import io.qase.api.annotation.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private static final String URL = "https://funny-inctagram.site/";
    private final WebDriver webDriver;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private By loginButton = By.xpath(".//a[contains(@href, '/sign-in')]");
    private By signupButton = By.xpath(".//a[contains(@href, '/sign-up')]");
    private By languageCheckbox = By.xpath(".//button[@role = 'combobox']");
    private By english = By.xpath(".//span[text() = 'English']");
    private By russian = By.xpath(".//span[text() = 'Russian']");
    private By homeTab = By.xpath(".//a[text()= 'Home']");
    private By createTab = By.xpath(".//button[text()= 'Create']");
    private By myprofileTab = By.xpath(".//a[@href= '/profile']");
    private By messengerTab = By.xpath(".//a[@href= '/messenger']");
    private By searchTab = By.xpath(".//a[@href= '/search']");
    private By statisticsTab = By.xpath(".//a[@href= '/statistics']");
    private By favoritesTab = By.xpath(".//a[@href= '/favorites']");
    private By logoutButton = By.xpath(".//button[text()= 'Log Out']");

    @Step("Перейти на сайт")
    public void open() {
        webDriver.get(URL);
    }
    @Step("Кликнуть по кнопке Login в правом верхнем углу")
    public void clickLoginButton() {
        webDriver.findElement(loginButton).click();
    }

    @Step("Кликнуть по кнопке Sign Up в правом верхнем углу")
    public void clickSignUpButton() {
        webDriver.findElement(signupButton).click();
    }

    @Step("Кликнуть по чекбоксу выбора языка")
    public MainPage clickLanguageCheckbox() {
        webDriver.findElement(languageCheckbox).click();
        return this;
    }

    @Step("Выбрать язык English")
    public MainPage checkEnglish() {
        webDriver.findElement(english).click();
        return this;
    }

    @Step("Выбрать язык Russian")
    public MainPage checkRussian() {
        webDriver.findElement(russian).click();
        return this;
    }

}
