package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage {

    public LogInPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#firstname")
    private WebElement firstname;

    @FindBy(css = "#lastname")
    private WebElement lastname;

    @FindBy(css = "#email_address")
    private WebElement emailId;

    @FindBy(css = "#password.input-text")
    private WebElement password;

    @FindBy(css = "#password-confirmation")
    private WebElement confirmPassword;

    @FindBy(css = ".recaptcha-checkbox-border")
    private WebElement iAmNotRobotChkbx;

    @FindBy(xpath = "//span[text()='Submit']")
    private WebElement submitBtn;

    public void signIn(String Name, String lastName, String mailId, String pass) {
        firstname.sendKeys(Name);
        lastname.sendKeys(lastName);
        emailId.sendKeys(mailId);
        password.sendKeys(pass);
        confirmPassword.sendKeys(pass);
        iAmNotRobotChkbx.click();

    }
}
