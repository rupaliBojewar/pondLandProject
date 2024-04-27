package com.pondland;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CommonActions;
import pages.LogInPage;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class AccountCreation {

    WebDriver driver;
    WebDriverWait wait;
    LogInPage logInPage;

    CommonActions actions;

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
        //driver.quit();
    }

    @BeforeClass
    public void initializePages() {
        logInPage = new LogInPage(driver);
        actions = new CommonActions();
    }

    public void switchToSignInPage() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //WebElement sighInBtn = driver.findElement(By.cssSelector(".top-nav__link"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept All Cookies']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Create an Account']"))).click();

        Thread.sleep(2000);

        // WebElement closeBtn=driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']"));

        if (wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']")))).isDisplayed()) {
            driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']")).click();
        }

    }

    @Test
    public void verifyNavigateToSignUpPage() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //WebElement sighInBtn = driver.findElement(By.cssSelector(".top-nav__link"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept All Cookies']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Create an Account']"))).click();

        //WebElement closeBtn = driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']"));

        if (driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']")).isDisplayed()) {
            driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']")).click();
            //closeBtn.click();
        }

        String expectedPageTitle = "Create New Customer Account";

        String actualPageTitle = driver.getTitle();

        System.out.println(actualPageTitle);

        Assert.assertEquals(expectedPageTitle, actualPageTitle);
    }

    @Test
    public void verifyAllElementsArePresent() {

    }

    @Test
    public void verifyErrorMsg() throws InterruptedException {
        switchToSignInPage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement firstname = driver.findElement(By.cssSelector("#firstname"));
        wait.until(ExpectedConditions.elementToBeClickable(firstname)).sendKeys("Rupali");

        WebElement lastname = driver.findElement(By.cssSelector("#lastname"));
        // wait.until(ExpectedConditions.elementToBeClickable(lastname)).sendKeys("Bojewar");

        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true)", submitBtn);
        //jse.executeScript("arguments[0].scrollIntoView(true)",submitBtn);
        WebElement emailId = driver.findElement(By.cssSelector("#email_address"));
        wait.until(ExpectedConditions.elementToBeClickable(emailId)).sendKeys("rupali.bojewar@gmail.com");

        WebElement password = driver.findElement(By.cssSelector("#password.input-text"));
        wait.until(ExpectedConditions.elementToBeClickable(password)).sendKeys("Xyz@56789");

        WebElement confirmPassword = driver.findElement(By.cssSelector("#password-confirmation"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmPassword)).sendKeys("Xyz@56789");

        // WebElement iAmNotRobotChkbx = driver.findElement(By.cssSelector(".recaptcha-checkbox-border"));
        // wait.until(ExpectedConditions.elementToBeClickable(iAmNotRobotChkbx)).click();

        // WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));
        submitBtn.click();

        String actualErrMsg = "This is a required field.";

        List<WebElement> errormsges = driver.findElements(By.cssSelector("[class='mage-error']"));

        Assert.assertTrue(errormsges.contains(actualErrMsg));
    }

    @Test
    public void verifyErrorMsg1() throws InterruptedException {
        switchToSignInPage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true)", submitBtn);
        submitBtn.click();

        Assert.assertTrue(errorMsgStatus("email"));

    }

    public boolean errorMsgStatus(String label) {

        WebElement msg = driver.findElement(By.cssSelector("div[id^='" + label + "']"));

        if (msg.isDisplayed())
            return true;
        else
            return false;

    }
}
