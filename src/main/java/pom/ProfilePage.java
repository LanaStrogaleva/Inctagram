package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
    private  final WebDriver driver;

    public ProfilePage (WebDriver driver) {
        this.driver = driver;
    }

    private By profileHeader = By.xpath(".//main[text() = 'Profile Page']");
    private By fillProfileHeader = By.xpath(".//div[contains(@class, 'name')]/p");


    public String getprofileHeaderText() {
        return driver.findElement(profileHeader).getText();
    }

    public String getFillProfileHeader() {
        return driver.findElement(fillProfileHeader).getText();
    }
}
