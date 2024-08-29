package pom;

import constants.Months;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    private final WebDriver driver;
    Months month;
    String year;
    String data;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    private By profileHeader = By.xpath(".//main[text() = 'Profile Page']");
    private By fillProfileHeader = By.xpath(".//div[contains(@class, 'name')]/p");
    private By profileSettingsButton = By.xpath(".//a[text()= 'Profile Settings']");
    private By homeTab = By.xpath(".//a[contains(@class, 'Button_primary')][1]");
    private By createTab = By.xpath(".//button[text()='Create']");
    private By myProfileTab = By.xpath(".//a[contains(@class, 'Button_primary')][2]");
    private By messengerTab = By.xpath(".//a[contains(@class, 'Button_primary')][3]");
    private By searchTab = By.xpath(".//a[contains(@class, 'Button_primary')][4]");
    private By statisticsTab = By.xpath(".//a[contains(@class, 'Button_primary')][5]");
    private By favoritesTab = By.xpath(".//a[contains(@class, 'Button_primary')][6]");
    // Profile Settings
    // Tabs
    private By generalInformationTab = By.xpath(".//button[text()='General Information']");
    private By devicesTab = By.xpath(".//button[text()='Devices']");
    private By accountManagementTab = By.xpath(".//button[text()='Account Management']");
    private By myPaymentsTab = By.xpath(".//button[text()='My payments']");
    // Button
    private By addProfilePhotoButton = By.xpath(".//button[text()='Add a Profile Photo']");
    private By saveChangesButton = By.xpath(".//button[text()='Save Changes']");

    // Fields
    private By usernameField = By.xpath(".//input[@id = 'Username']");
    private By firstNameField = By.xpath(".//input[@id = 'First Name']");
    private By lastNameField = By.xpath(".//input[@id = 'Last Name']");
    private By dateOfBirthField = By.xpath(".//input[@placeholder = 'Date of birth']");
    // Выбор месяца, года и даты из календаря
    private By months = By.xpath(".//select[contains(@class, 'CustomHeader')][1]");
    private By monthsSelect = By.xpath(".//select/option[@value='" + month + "']");
    private By years = By.xpath(".//select[contains(@class, 'CustomHeader')][2]");
    private By yearsSelect = By.xpath(".//select/option[@value='" + year + "']");
    private By dates = By.xpath(".//div[contains(@class, 'week')]/div");
    private By selectCountryField = By.xpath(".//button[@id='Select your country']");
    private By allCountryList = By.xpath(".//button[@id='Select your country']/following-sibling::*/option");

    private By selectCityField = By.xpath(".//button[@id='Select your city']");
    private By allCitiesList = By.xpath(".//button[@id='Select your city']/following-sibling::*/option");
    private By aboutMeField = By.xpath(".//textarea");
    private By messageAboutSave = By.xpath(".//div[text() = 'Your settings are saved!']");
    private By errorMessageForUserUnder13 = By.xpath(".//span[text() = 'A user under 13 cannot create a profile. ']");
    private By errorMessageFirstName = By.xpath(".//div/label[@for ='First Name']/following-sibling::span");
    private By errorMessageLastName = By.xpath(".//div/label[@for ='Last Name']/following-sibling::span");
    private By errorMessageAboutMe = By.xpath(".//div/label[@for ='About Me']/following-sibling::span");

    public String getprofileHeaderText() {
        return driver.findElement(profileHeader).getText();
    }

    public String getFillProfileHeader() {
        return driver.findElement(fillProfileHeader).getText();
    }

    @Step("Кликнуть на кнопку Profile Settings")
    public ProfilePage clickProfileSettingsButton() {
        driver.findElement(profileSettingsButton).click();
        return this;
    }

    @Step("Кликнуть на вкладку Home")
    public ProfilePage clickHomeTab() {
        driver.findElement(homeTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку Create")
    public ProfilePage clickCreateTab() {
        driver.findElement(createTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку MyProfile")
    public ProfilePage clickMyProfileTab() {
        driver.findElement(myProfileTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку Messenger")
    public ProfilePage clickMessengerTab() {
        driver.findElement(messengerTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку Search")
    public ProfilePage clickSearchTab() {
        driver.findElement(searchTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку Statistics")
    public ProfilePage clickStatisticsTab() {
        driver.findElement(statisticsTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку Favorites")
    public ProfilePage clickFavoritesTab() {
        driver.findElement(favoritesTab).click();
        return this;
    }

    // Profile Settings
    @Step("Кликнуть на вкладку General Information")
    public ProfilePage clickGeneralInformationTab() {
        driver.findElement(generalInformationTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку Devices")
    public ProfilePage clickDevicesTab() {
        driver.findElement(devicesTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку Account Management")
    public ProfilePage clickAccountManagementTab() {
        driver.findElement(accountManagementTab).click();
        return this;
    }

    @Step("Кликнуть на вкладку My payments")
    public ProfilePage clickMyPaymentsTab() {
        driver.findElement(myPaymentsTab).click();
        return this;
    }

    @Step("Кликнуть на кнопку Add a Profile Photo")
    public ProfilePage clickAddProfilePhotoButton() {
        driver.findElement(addProfilePhotoButton).click();
        return this;
    }

    // Fields
    @Step("Ввести данные в поле Username")
    public ProfilePage inputUsernameField(String username) {
        driver.findElement(usernameField).sendKeys(username);
        return this;
    }

    @Step("Ввести данные в поле FirstName")
    public ProfilePage inputFirstNameField(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
        return this;
    }

    @Step("Ввести данные в поле LastName")
    public ProfilePage inputLastNameField(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
        return this;
    }

    @Step("Выбрать дату рождения в календаре")
    public ProfilePage selectDateOfBirth(String date, Months month, String year) {
        System.out.println(date + month + year);
        driver.findElement(dateOfBirthField).click();
        driver.findElement(years).click();
        driver.findElement(By.xpath(".//select/option[@value='" + year + "']")).click();
        driver.findElement(months).click();
        driver.findElement(By.xpath(".//select/option[@value='" + month + "']")).click();
        List<WebElement> webDates = driver.findElements(dates);
        for (WebElement webData : webDates) {
            if (webData.getText().equals(date)) {
                webData.click();
                break;
            }
        }
        // (webData.getCssValue("color").equals("rgb(242, 61, 97)") || webData.getCssValue("color").equals("rgb(255, 255, 255)"))
        return this;
    }

    @Step("Выбрать страну из списка")
    public ProfilePage selectCountryFromDropdown(String country) {
        List<WebElement> webElements = driver.findElements(allCountryList);
        for (WebElement webElement : webElements) {
            if (webElement.getText().equals(country)) {
                webElement.click();
                break;
            }
        }
        return this;
    }

    @Step("Выбрать город из списка")
    public ProfilePage selectCityFromDropdown(String city) {
        List<WebElement> webElements = driver.findElements(allCitiesList);
        for (WebElement webElement : webElements) {
            if (webElement.getText().equals(city)) {
                webElement.click();
                break;
            }
        }
        return this;
    }

    @Step("Ввести данные в поле About Me")
    public ProfilePage inputAboutMeField(String text) {
        driver.findElement(aboutMeField).sendKeys(text);
        return this;
    }

    @Step("Кликнуть на кнопку Save Changes")
    public ProfilePage clickSaveChangesButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(saveChangesButton));
        element.click();
        return this;
    }

    @Step("Получить текст сообщения о сохранении изменений")
    public String getMessageAboutSave() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(messageAboutSave));
        return element.getText();
    }
    @Step("Получить текст сообщения об ошибке для пользователя младше 13 лет")
    public String getErrorMessageForUserUnder13() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageForUserUnder13));
        return element.getText();
    }
    @Step("Кнопка Save Changes не кликабельна")
    public boolean isEnabledChangesButton() {
        return driver.findElement(saveChangesButton).isEnabled();
    }

    @Step ("Проверить появилось ли сообщение об ошибке в поле First Name")
    public boolean isErrorMessageFirstName() {
        boolean isError;
        try {
            isError = driver.findElement(errorMessageFirstName).isDisplayed();
        } catch (NoSuchElementException e) {
            isError = false;
        }
        return isError;
    }
    @Step ("Проверить появилось ли сообщение об ошибке в поле Last Name")
    public boolean isErrorMessageLastName() {
        boolean isError;
        try {
            isError = driver.findElement(errorMessageLastName).isDisplayed();
        } catch (NoSuchElementException e) {
            isError = false;
        }
        return isError;
    }
    @Step ("Проверить появилось ли сообщение об ошибке в поле About Me")
    public boolean isErrorMessageAboutMe() {
        boolean isError;
        try {
            isError = driver.findElement(errorMessageAboutMe).isDisplayed();
        } catch (NoSuchElementException e) {
            isError = false;
        }
        return isError;
    }
    @Step("Получить текст сообщения об ошибке поля First Name")
    public String getErrorMessageFirstName() {
        String errorText = null;
        try {
            errorText = driver.findElement(errorMessageFirstName).getText();
        } catch (NoSuchElementException e) {
            e.getMessage();
            System.out.println("Не удалось найти сообщение об ошибке");
        }
        return errorText;
    }

    @Step("Получить текст сообщения об ошибке поля Last Name")
    public String getErrorMessageLastName() {
        String errorText = null;
        try {
            errorText = driver.findElement(errorMessageLastName).getText();
        } catch (NoSuchElementException e) {
            e.getMessage();
            System.out.println("Не удалось найти сообщение об ошибке");
        }
        return errorText;
    }

    @Step("Получить текст сообщения об ошибке поля Last Name")
    public String getErrorMessageAboutMe() {
        String errorText = null;
        try {
            errorText = driver.findElement(errorMessageAboutMe).getText();
        } catch (NoSuchElementException e) {
            e.getMessage();
            System.out.println("Не удалось найти сообщение об ошибке");
        }
        return errorText;
    }
}
