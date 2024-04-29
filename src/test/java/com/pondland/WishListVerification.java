package com.pondland;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.LogInPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WishListVerification {
    WebDriver driver;
    WebDriverWait wait;
    LogInPage logInPage;
    Actions actions1;


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
        actions1 = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void sighIn(){

    }

    @Test
    public void verifyAddedProductInWishList(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept All Cookies']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

    }
}
