package com.pondland;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.CommonActions;
import pages.LogInPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountCreation {

    WebDriver driver;
    WebDriverWait wait;
    LogInPage logInPage;

    Actions actions1;

    List<String> expectedErrors;

    @BeforeSuite
    public void launchBrowser() {
        driver = new ChromeDriver();
    }

    @BeforeTest
    public void launchApplication() {
        driver.get("https://www.poundland.co.uk/");
        driver.manage().window().maximize();

    }

    @AfterSuite
    public void killSession() {
        driver.quit();
    }

    @BeforeClass
    public void initializePages() {
        logInPage = new LogInPage(driver);
        expectedErrors = new ArrayList<>();
        actions1 = new Actions(driver);
    }

    @Test
    public void switchToSignInPage() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //WebElement sighInBtn = driver.findElement(By.cssSelector(".top-nav__link"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept All Cookies']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Create an Account']"))).click();

        //Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        WebElement closeBtn = driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']"));

        if (wait.until(ExpectedConditions.visibilityOf(closeBtn)).isDisplayed()) {
            closeBtn.click();
        }
        expectedErrors.add("Passwords do not match.");
        expectedErrors.add("This is a required field.");

    }

    @Test
    public void verifyNavigateToSignUpPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //WebElement sighInBtn = driver.findElement(By.cssSelector(".top-nav__link"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept All Cookies']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Create an Account']"))).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        WebElement closeBtn = driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']"));

        if (wait.until(ExpectedConditions.visibilityOf(closeBtn)).isDisplayed()) {
            closeBtn.click();
        }

        String expectedPageTitle = "Create New Customer Account";

        String actualPageTitle = driver.getTitle();

        System.out.println(actualPageTitle);

        Assert.assertEquals(expectedPageTitle, actualPageTitle);
    }

    @Test(dependsOnMethods = "switchToSignInPage")
    public void verifyAllElementsArePresent() {
        SoftAssert assert1 = new SoftAssert();

        WebElement firstname = driver.findElement(By.cssSelector("#firstname"));
        //System.out.println("First Name: " + firstname.isEnabled());
        assert1.assertTrue(firstname.isEnabled());

        WebElement lastname = driver.findElement(By.cssSelector("#lastname"));
        //System.out.println("Last Name: " + lastname.isEnabled());
        assert1.assertTrue(lastname.isEnabled());

        WebElement emailId = driver.findElement(By.cssSelector("#email_address"));
       // System.out.println("Email Id : " + emailId.isEnabled());
        assert1.assertTrue(emailId.isEnabled());

        WebElement password = driver.findElement(By.cssSelector("#password.input-text"));
        //System.out.println("Password : " + password.isEnabled());
        assert1.assertTrue(password.isEnabled());

        WebElement confirmPassword = driver.findElement(By.cssSelector("#password-confirmation"));
        //System.out.println("Confirm Password: " + confirmPassword.isEnabled());
        assert1.assertTrue(confirmPassword.isEnabled());

        //WebElement iAmNotRobotChkbx = driver.findElement(By.cssSelector(".recaptcha-checkbox-border"));
        //iAmNotRobotChkbx.isDisplayed();

        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));
        //System.out.println("Submit button: " + submitBtn.isDisplayed());
        assert1.assertTrue(submitBtn.isDisplayed());

        assert1.assertAll();
    }

    @Test(dependsOnMethods = "switchToSignInPage")
    public void verifyErrorMsg() {

        WebElement firstname = driver.findElement(By.cssSelector("#firstname"));
        wait.until(ExpectedConditions.elementToBeClickable(firstname)).sendKeys("Rupali");

        WebElement lastname = driver.findElement(By.cssSelector("#lastname"));
        // wait.until(ExpectedConditions.elementToBeClickable(lastname)).sendKeys("Bojewar");

//        WebElement emailId = driver.findElement(By.cssSelector("#email_address"));
//        wait.until(ExpectedConditions.elementToBeClickable(emailId)).sendKeys("rupali.bojewar@gmail.com");
//
//        WebElement password = driver.findElement(By.cssSelector("#password.input-text"));
//        wait.until(ExpectedConditions.elementToBeClickable(password)).sendKeys("Xyz@56789");=p
        
//
//        WebElement confirmPassword = driver.findElement(By.cssSelector("#password-confirmation"));
//        wait.until(ExpectedConditions.elementToBeClickable(confirmPassword)).sendKeys("Xyz@56789");

        // WebElement iAmNotRobotChkbx = driver.findElement(By.cssSelector(".recaptcha-checkbox-border"));
        // wait.until(ExpectedConditions.elementToBeClickable(iAmNotRobotChkbx)).click();

        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));
        submitBtn.click();

        String expectedErrMsg = "This is a required field.";

        System.out.println("Expected error message:" + expectedErrMsg);

        List<WebElement> errormsges = driver.findElements(By.cssSelector("[class='mage-error']"));

        for (WebElement w : errormsges) {
            System.out.println(w.getText());
        }

        Assert.assertTrue(errormsges.contains(expectedErrMsg));
    }

    @Test(dependsOnMethods = "switchToSignInPage")
    public void verifyErrorMsg1() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));

        actions1.scrollToElement(submitBtn).perform();

        submitBtn.click();

        Assert.assertTrue(errorMsgStatus("password"));
    }

    public boolean errorMsgStatus(String label) {

        WebElement msg = driver.findElement(By.cssSelector("div[id^='" + label + "']"));

        if (msg.isDisplayed())
            return true;
        else
            return false;

    }

    @Test(dependsOnMethods = "switchToSignInPage")
    public void passwordProgressMsg() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        List<String> expectedProgressMsgs = new ArrayList<>();
        expectedProgressMsgs.add("Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
        expectedProgressMsgs.add("Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.");


        WebElement password = driver.findElement(By.cssSelector("#password.input-text"));
        actions1.scrollToElement(password).perform();

        password.sendKeys("1234567");

        String actualProgressMsg = driver.findElement(By.cssSelector("#password-error")).getText();

        System.out.println(actualProgressMsg);

        Assert.assertTrue(expectedProgressMsgs.contains(actualProgressMsg));
    }

    @Test(dependsOnMethods = "switchToSignInPage")
    public void verifyConfirmPasswordErrorMsg() {

        WebElement password = driver.findElement(By.cssSelector("#password.input-text"));
        actions1.scrollToElement(password);
        wait.until(ExpectedConditions.elementToBeClickable(password)).sendKeys("Xyz@56789");

        WebElement confirmPassword = driver.findElement(By.cssSelector("#password-confirmation"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmPassword)).sendKeys("Xyz@5678");

        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));
        submitBtn.click();

        String actualErrorMsg = driver.findElement(By.cssSelector("#password-confirmation-error")).getText();

        System.out.println(actualErrorMsg);

        Assert.assertTrue(expectedErrors.contains(actualErrorMsg));
    }

    @Test
    public void verifyNewlyCreatedAccount() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept All Cookies']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#email"))).sendKeys("rupali.bojewar@gmail.com");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[placeholder='Password']"))).sendKeys("Abc@1234");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Forgot Password?']/ancestor::div[@class=\"actions-toolbar\"]/div/button"))).click();

        String expectedPageTitle = "Poundland.co.uk | Amazing value every day";

        String actualPageTitle = driver.getTitle();

        Assert.assertEquals(actualPageTitle,expectedPageTitle);

    }


}
